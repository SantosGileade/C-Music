-- Desfaz o Portal dos Cantores: apaga SOMENTE o que começa com "portal_".
-- As tabelas do Nexos (musica, playlist, musica_playlist, mensagem, ...) não são tocadas.
-- ATENÇÃO: apaga também os cantores, as músicas deles e as escolhas de ensaio.

drop function if exists portal_admin_excluir_ensaio(text, bigint);
drop function if exists portal_admin_salvar_ensaio(text, bigint, text, text, timestamptz, text);
drop function if exists portal_admin_excluir_cantor(text, bigint);
drop function if exists portal_admin_salvar_cantor(text, bigint, text, text, boolean);
drop function if exists portal_exigir_admin(text);
drop function if exists portal_admin_verificar(text);

drop table if exists portal_selecao cascade;
drop table if exists portal_musica cascade;
drop table if exists portal_ensaio cascade;
drop table if exists portal_cantor cascade;
drop table if exists portal_config cascade;

drop function if exists portal_selecao_aberta(bigint);
drop function if exists portal_touch();
