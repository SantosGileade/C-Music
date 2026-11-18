-- Portal dos Cantores: cada cantor escolhe no máximo 1 música por ensaio (regra no servidor).
-- Rodar uma vez no SQL Editor do Supabase.
--
-- Se aparecer "could not create unique index", é porque algum cantor já tem 2 músicas no mesmo
-- ensaio. Nesse caso, apague a sobra pela área Administração e rode de novo.
--
-- Para desfazer: drop index if exists portal_selecao_um_por_cantor_uq;

create unique index if not exists portal_selecao_um_por_cantor_uq
  on portal_selecao (ensaio_id, cantor_id);
