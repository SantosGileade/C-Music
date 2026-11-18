-- Portal dos Cantores: "Quero cantar no próximo domingo".
-- Rodar uma vez no SQL Editor do Supabase (depois dos arquivos anteriores do portal).
-- Para desfazer: rode supabase/portal_cantores_desfazer.sql (já inclui estas peças) ou apague só esta parte:
--   drop table if exists portal_domingo cascade;
--   drop function if exists portal_musica_ensaiada(bigint, text, text);
--   drop function if exists portal_proximo_domingo();
--   drop function if exists portal_hoje();

-- ---------- datas (horário de Brasília) ----------
create or replace function portal_hoje() returns date
language sql stable as $$
  select (now() at time zone 'America/Sao_Paulo')::date
$$;

-- o "próximo domingo": se hoje for domingo, é hoje; senão, o domingo que vem
create or replace function portal_proximo_domingo() returns date
language sql stable as $$
  select portal_hoje() + ((7 - extract(dow from portal_hoje())::int) % 7)
$$;
grant execute on function portal_hoje() to anon, authenticated;
grant execute on function portal_proximo_domingo() to anon, authenticated;

-- ---------- regra: só vale música que o cantor já ensaiou ----------
-- "ensaiada" = o cantor escolheu essa música (título + artista) para um ensaio que já aconteceu
-- (marcado como realizado, ou cuja data/hora já passou) e que não foi cancelado.
create or replace function portal_musica_ensaiada(p_cantor bigint, p_titulo text, p_artista text) returns boolean
language sql stable security definer set search_path = public as $$
  select exists (
    select 1
      from portal_selecao s
      join portal_ensaio e on e.id = s.ensaio_id
     where s.cantor_id = p_cantor
       and lower(trim(s.titulo)) = lower(trim(p_titulo))
       and lower(trim(coalesce(s.artista, ''))) = lower(trim(coalesce(p_artista, '')))
       and e.status <> 'cancelado'
       and (e.status = 'realizado' or e.data_hora < now())
  );
$$;
grant execute on function portal_musica_ensaiada(bigint, text, text) to anon, authenticated;

-- ---------- pedidos para o domingo ----------
create table if not exists portal_domingo (
  id bigserial primary key,
  cantor_id bigint not null references portal_cantor(id) on delete cascade,
  musica_id bigint references portal_musica(id) on delete set null,
  titulo text not null check (length(trim(titulo)) between 1 and 200),
  artista text check (artista is null or length(artista) <= 200),
  tom text check (tom is null or length(tom) <= 10),
  domingo date not null default portal_proximo_domingo(),
  created_at timestamptz not null default now()
);
-- cada cantor marca no máximo 1 música por domingo
create unique index if not exists portal_domingo_um_por_cantor_uq on portal_domingo (cantor_id, domingo);
create index if not exists portal_domingo_data_idx on portal_domingo (domingo);

alter table portal_domingo enable row level security;

drop policy if exists portal_domingo_leitura on portal_domingo;
create policy portal_domingo_leitura on portal_domingo for select using (true);

-- o servidor só aceita: para o PRÓXIMO domingo e de música já ensaiada pelo cantor
drop policy if exists portal_domingo_inserir on portal_domingo;
create policy portal_domingo_inserir on portal_domingo for insert
  with check (domingo = portal_proximo_domingo() and portal_musica_ensaiada(cantor_id, titulo, artista));

-- pode desfazer o pedido enquanto o domingo não passou
drop policy if exists portal_domingo_apagar on portal_domingo;
create policy portal_domingo_apagar on portal_domingo for delete using (domingo >= portal_hoje());

-- tempo real (o Nexos do líder de cânticos atualiza sozinho)
do $$
begin
  alter publication supabase_realtime add table portal_domingo;
exception when duplicate_object then null;
end $$;
