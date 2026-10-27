-- Backing vocal: categorias de voz (globais) e a divisão de vozes de cada música.
-- Rodar uma vez no SQL Editor do Supabase.

create table if not exists backing_categoria (
  id bigserial primary key,
  nome text not null,
  cor text not null default '#3b9cf5',
  ordem integer not null default 0,
  created_at timestamptz not null default now()
);

create table if not exists backing_musica (
  musica_id bigint primary key references musica(id) on delete cascade,
  linhas jsonb not null default '[]'::jsonb,
  atualizado_em timestamptz not null default now()
);

alter table backing_categoria enable row level security;
alter table backing_musica enable row level security;

create policy "backing_categoria_all" on backing_categoria for all using (true) with check (true);
create policy "backing_musica_all" on backing_musica for all using (true) with check (true);

insert into backing_categoria (nome, cor, ordem)
select v.nome, v.cor, v.ordem
from (values
  ('Voz principal', '#3b9cf5', 1),
  ('Contracanto',   '#8b5cf6', 2),
  ('Segunda voz',   '#14b8a6', 3),
  ('Voz feminina',  '#f5a623', 4)
) as v(nome, cor, ordem)
where not exists (select 1 from backing_categoria);
