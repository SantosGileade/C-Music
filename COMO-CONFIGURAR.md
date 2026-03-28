# 🎵 C-Music — Como Configurar para Salvar Sempre

## O que você vai ter no final
- Um link público (tipo `https://seu-usuario.github.io/c-music`) que abre no celular de qualquer pessoa da equipe
- Músicas e playlists salvas para sempre, acessíveis de qualquer dispositivo
- **Zero custo** — tudo grátis

---

## PARTE 1 — Banco de Dados (Supabase) — ~5 minutos

### Passo 1: Crie a conta
1. Acesse **https://supabase.com**
2. Clique em **Start your project** → entre com o Google
3. Clique em **New Project**
4. Escolha um nome (ex: `c-music`), defina uma senha e clique em **Create new project**
5. Aguarde 1-2 minutos para o projeto criar

### Passo 2: Crie as tabelas
1. No menu lateral, clique em **SQL Editor**
2. Clique em **+ New query**
3. Cole e execute o seguinte SQL:

```sql
create table if not exists musica (
  id bigserial primary key,
  titulo text not null,
  autor text,
  tom text,
  letra text,
  created_at timestamptz default now()
);

create table if not exists playlist (
  id bigserial primary key,
  title text not null,
  publico boolean default true,
  created_at timestamptz default now()
);

create table if not exists musica_playlist (
  id bigserial primary key,
  musica_id bigint references musica(id) on delete cascade,
  playlist_id bigint references playlist(id) on delete cascade,
  ordem integer not null default 1,
  tocando boolean default false,
  constraint uq_musica_playlist unique(musica_id, playlist_id)
);

alter table musica enable row level security;
alter table playlist enable row level security;
alter table musica_playlist enable row level security;

create policy "all" on musica for all using (true) with check (true);
create policy "all" on playlist for all using (true) with check (true);
create policy "all" on musica_playlist for all using (true) with check (true);
```

4. Clique em **Run** (▶)

### Passo 3: Pegue as credenciais
1. Ainda no Supabase, vá em **Settings** (ícone de engrenagem no menu) → **API**
2. Copie a **Project URL** (parece com `https://xxxxxxxx.supabase.co`)
3. Copie a **anon public key** (começa com `eyJ...`)

### Passo 4: Configure no app
1. Abra o `index.html` no navegador
2. Clique no botão ⚙️ no canto superior direito
3. Cole a URL e a chave
4. Clique em **Conectar e Salvar**
5. 🟢 Pronto! Agora tudo que você cadastrar fica salvo!

---

## PARTE 2 — Hospedar no GitHub Pages — ~5 minutos

### Por que fazer isso?
Para acessar de qualquer celular pelo navegador, sem precisar abrir arquivo.

### Passo 1: Crie um repositório no GitHub
1. Acesse **https://github.com** (crie conta se não tiver)
2. Clique em **+ New repository**
3. Nome: `c-music`
4. Marque como **Public**
5. Clique em **Create repository**

### Passo 2: Suba o arquivo
**Opção A — pelo navegador (mais fácil):**
1. Na página do repositório vazio, clique em **uploading an existing file**
2. Arraste o `index.html`
3. Clique em **Commit changes**

**Opção B — pelo terminal:**
```bash
git init
git add index.html
git commit -m "C-Music app"
git branch -M main
git remote add origin https://github.com/SEU-USUARIO/c-music.git
git push -u origin main
```

### Passo 3: Ative o GitHub Pages
1. No repositório, vá em **Settings**
2. Role até **Pages** no menu lateral
3. Em **Source**, selecione **Deploy from a branch**
4. Branch: **main**, pasta: **/ (root)**
5. Clique em **Save**

### Passo 4: Acesse o link!
Após 1-2 minutos, seu app estará em:
```
https://SEU-USUARIO.github.io/c-music
```

Salve esse link nos favoritos do celular!

---

## Dicas

- **Adicionar ao início do iPhone:** Abra o link no Safari → compartilhar → "Adicionar à Tela de Início" — vira ícone igual app!
- **Sincronizado:** Qualquer pessoa que abrir o mesmo link e configurar o mesmo Supabase vê as mesmas músicas
- **Supabase pausa projetos inativos** no plano gratuito após 1 semana sem uso. Se isso acontecer, basta entrar no Supabase e reativar o projeto (leva segundos)
- **Backup:** Suas músicas ficam no banco do Supabase. Você pode exportar pelo painel deles quando quiser

---

## Problemas comuns

| Problema | Solução |
|----------|---------|
| App abre mas não salva | Configure o ⚙️ com as credenciais do Supabase |
| "Falha na conexão" no settings | Verifique se executou o SQL (Passo 2 da Parte 1) |
| App sumiu do celular | O projeto Supabase pausou — entre em supabase.com e reative |
| Link do GitHub Pages não funciona | Aguarde mais alguns minutos, pode demorar até 5 min na primeira vez |
