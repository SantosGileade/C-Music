-- Portal dos Cantores: uma música que o cantor já ensaiou (em um ensaio que já aconteceu)
-- não pode ser escolhida de novo para outro ensaio.
-- Rodar uma vez no SQL Editor do Supabase, depois de supabase/portal_domingo.sql
-- (usa a função portal_musica_ensaiada criada lá).
-- Para desfazer, rode de novo a linha da policy original:
--   drop policy if exists portal_selecao_inserir on portal_selecao;
--   create policy portal_selecao_inserir on portal_selecao for insert with check (portal_selecao_aberta(ensaio_id));

drop policy if exists portal_selecao_inserir on portal_selecao;
create policy portal_selecao_inserir on portal_selecao for insert
  with check (portal_selecao_aberta(ensaio_id) and not portal_musica_ensaiada(cantor_id, titulo, artista));
