-- Nexos > Últimas atualizações > aba "Edições": guarda o que foi alterado em cada música
-- (título, autor, tom e letra). Rodar uma vez no SQL Editor do Supabase.
-- Para desfazer: drop table if exists musica_edicao;

create table if not exists musica_edicao (
  id bigserial primary key,
  musica_id bigint,
  titulo text not null,
  -- exemplo: {"tom":{"de":"C","para":"D"},"titulo":{"de":"Antigo","para":"Novo"},"letra":{"adicionadas":2,"removidas":1}}
  campos jsonb not null,
  created_at timestamptz not null default now()
);
create index if not exists musica_edicao_data_idx on musica_edicao (created_at desc);

alter table musica_edicao enable row level security;
drop policy if exists musica_edicao_todos on musica_edicao;
create policy musica_edicao_todos on musica_edicao for all using (true) with check (true);

grant select, insert on musica_edicao to anon, authenticated;
grant usage, select on sequence musica_edicao_id_seq to anon, authenticated;
