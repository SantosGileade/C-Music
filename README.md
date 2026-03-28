***Sobre o Projeto***

O C-Music foi criado para resolver um problema comum entre os lideres de cânticos: organizar previamente as músicas que serão tocadas em um evento.

Com ele, o líder de cânticos pode:

-Cadastrar músicas

-Criar playlists

-Organizar a ordem das músicas

-Controlar qual música está sendo tocada


⚠️ Caso você esteja pretendendo baixar o código, fique a vontade. Mas fique ciente de que ainda está em desenvolvimento e possa ser que encontre alguma anormalidade ou falta de features importantes. 

***Estrutura do Sistema***

Painel de Músicas -> Responsável pelo gerenciamento das músicas.

Funcionalidades:

- Adicionar música
- Editar música
- Excluir música
- Listar músicas
- Buscar músicas


Painel de Playlist -> Permite agrupar músicas para um evento.

Funcionalidades:

- Criar playlist
- Associar músicas à playlist


Painel de Organização -> Define a ordem das músicas dentro da playlist.

Funcionalidades:

- Adicionar músicas à playlist
- Definir ordem de execução
- Controlar música em execução


⚙ Regras de Negócio

- Não é permitido repetir músicas na mesma playlist

- A ordem das músicas deve ser única dentro da playlist

- Apenas uma música pode estar com tocando = true por playlist


Para garantir isso:

- O sistema desativa automaticamente todas as músicas antes de marcar uma como tocando

-> Exclusões podem falhar caso existam vínculos entre entidades

Tecnologias Utilizadas
- Java
- MySQL
- Spring Boot
- AWS (infraestrutura)

# Pré-requisitos para executar o projeto

- Java 17+
- Maven (ou usar o wrapper do projeto)
- MySQL

-> No seu MySQL somente criar um banco: CREATE DATABASE c_music;

***No src/main/resources/application.properties, deixe exatamente assim:***

spring.application.name=c-music

spring.datasource.url=${DB_URL:jdbc:mysql://localhost:3306/cmusic}

spring.datasource.username=${DB_USER:root}

spring.datasource.password=${DB_PASS:root}

management.endpoints.web.exposure.include=mappings

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true

Recomenda-se configurar as variáveis de ambiente no sistema para evitar exposição de credenciais sensíveis no código. 

# Como Executar o Projeto
***Clone o repositório***

git clone https://github.com/Weslleyawsm/c-music.git

***Acesse a pasta***

cd c-music

***Rode o projeto***

- Você pode cliar no botão de play (um verdinho que do lado esquero do código)
- Você pode pelo terminal no Intellij digitar .\mvnw.cmd spring-boot:run (você deve estar na pasta do projeto)
- você pode no terminal do linux digitar ./mvnw spring-boot:run (você deve estar na pasta do projeto)

Base URL -> {protocolo}://{host}:{port}

Se vc for testar na sua maquina, provavelmente será -> http://localhost:8080

---

**MUSICA**

Base Path ->  /api/v1/musica


Criar musica:  POST /api/v1/musica

Body

{

"titulo": "string",

"autor": "string",

"letra": "string"

}


Listar musicas: GET /api/v1/musica/listar?titulo={titulo}&page=0&size=10


Buscar por ID: GET /api/v1/musica/{id}


Atualizar: PUT /api/v1/musica/{id}

Body:

{

"titulo": "string",

"autor": "string",

"letra": "string"

}

Deletar: DELETE /api/v1/musica/{id}

---

**Playlist**

Base Path ->  /api/v1/playlist

Criar playlist: POST /api/v1/playlist

Body:

{

"title": "string",

"publico": true

}

Listar playlists: GET /api/v1/playlist/listar

Buscar por ID: GET /api/v1/playlist/{id}

Buscar por título: GET /api/v1/playlist/listar/{title}

Atualizar: PUT /api/v1/playlist/{id}

Body

{

"title": "string",

"publico": true

}

Deletar: DELETE /api/v1/playlist/{id}

---

MUSICA PLAYLIST

Base Path: /api/v1/musica-playlist

Adicionar música na playlist: POST /api/v1/musica-playlist

Body

{

"musicaId": 1,

"playlistId": 1,

"ordem": 1,

"tocando": true

}

Listar todas: GET /api/v1/musica-playlist/listar

Buscar por ID: GET /api/v1/musica-playlist/{id}

Listar músicas de uma playlist: GET /api/v1/musica-playlist/playlist/{playlistId}

Atualizar: PUT /api/v1/musica-playlist/{id}

{

"musicaId": 1,

"playlistId": 1,

"ordem": 1,

"tocando": true

}

Remover da playlist: DELETE /api/v1/musica-playlist/{id}
