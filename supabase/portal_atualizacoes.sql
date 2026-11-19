-- Nexos > Últimas atualizações: mostra também as músicas que os cantores guardaram no Portal.
-- Esta coluna guarda se o operador já adicionou a música ("✅ Já adicionada").
-- Rodar uma vez no SQL Editor do Supabase.
-- Para desfazer: alter table portal_musica drop column if exists visto_em;

alter table portal_musica add column if not exists visto_em timestamptz;
