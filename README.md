# Spotifei

### Gustavo Novais Cheida RA: 24.124.065-4
### Victor Ferraretto Novais RA: 24.124.070-4

##  Introdução

O Spotifei é um projeto desenvolvido em Java, utilizando interfaces gráficas (Java Swing) para simular uma plataforma de áudio digital, como músicas e podcasts. O objetivo é criar uma experiência interativa onde os usuários possam navegar, buscar, curtir, descurtir e gerenciar playlists, bem como visualizar históricos e estatísticas.

Toda a parte de persistência de dados é feita através da conexão com um banco de dados PostgreSQL, onde estão armazenadas informações como:

• Dados de usuários e administradores

• Músicas e artistas

• Playlists personalizadas

• Curtidas, descurtidas e histórico de buscas

Para executar corretamente a aplicação, é necessário seguir alguns passos de configuração que detalharemos nas seções seguintes.

##  Requisitos e configuração inicial

### Requisitos obrigatórios

Antes de rodar o Spotifei, certifique-se de que os seguintes softwares estejam instalados em sua máquina:

• Java JDK 8 ou superior

• NetBeans (recomendado para compilar e executar o projeto com facilidade)

• PostgreSQL (recomendado com pgAdmin para interface gráfica do banco)

• Driver JDBC para PostgreSQL: postgresql-42.7.5.jar (já incluso no repositório)


### Clonagem do repositório

• Clone ou baixe o repositório diretamente do GitHub:

• Abra o projeto no NetBeans

• Compile o projeto por completo antes de executá-lo

### Configuração do banco de dados

1. Crie um novo banco de dados chamado spotifei no PostgreSQL

2. Acesse a pasta sql do projeto e execute o script SQL fornecido (via pgAdmin ou outro cliente)

Este script criará todas as tabelas necessárias e ainda inserições iniciais com dados de:

• Usuários

• Administradores

• Músicas

• Artistas

• Playlists

Atenção:

### Para que a aplicação conecte-se corretamente ao banco de dados, é necessário alterar o nome de usuário e a senha utilizados na conexão, substituindo pelos dados da sua máquina local.

## Funcionalidades da Plataforma

### Acesso à plataforma

Exemplos de login após execução do script:

• Usuário comum: maria / senha456

• Administrador: carlosAd / senha123

### Funcionalidades para Usuários Comuns

• Buscar músicas por nome, artista ou gênero: o usuário digita o termo e a aplicação retorna os resultados correspondentes.

• Listar informações de músicas buscadas: dados como nome, artista, gênero e duração.

• Curtir e descurtir músicas: funcionalidade de avaliação que alimenta estatísticas futuras.

• Gerenciar playlists --> as funcionalidades são: criar nova playlist, editar nome de playlist existente, excluir playlist e adicionar ou remover músicas da playlist

• Visualizar histórico --> As funcionalidades são: exibe as últimas 10 buscas feitas, lista de músicas curtidas e lista de músicas descurtidas

### Funcionalidades para Administradores

• Cadastrar e excluir músicas: inserir novas músicas na plataforma ou remover existentes.

• Cadastrar artistas: gerenciamento completo de artistas.

• Consultar usuários: ver dados dos usuários registrados.

• Visualizar estatísticas do sistema: top 5 músicas mais curtidas, top 5 músicas mais descurtidas, total de usuários e total de músicas cadastradas

## Estrutura Interna do Projeto

### Padrão de Projeto

O Spotifei utiliza o padrão MVC (Model-View-Controller), separando responsabilidades em três camadas principais:

• Model: representa os dados e entidades do sistema

• View: representa a interface visual com o usuário

• Controller: lógica de negócio e interação entre Model e View

### Estrutura de Pacotes

View --> contém todas as interfaces gráficas:

• Adm.java

• Avaliacao.java

• BuscarMusica.java

• CadastrarArtista.java

• CadastrarMusica.java

• Cadastro.java

• ConsultarUsuarios.java

• ExcluirMusica.java

• Historico.java

• Home.java

• Login.java

• Playlist.java

• VizualizarEstatisticas.java

Model --> define as classes que representam entidades do banco de dados:

• Artista.java

• Autentificacao.java

• Musica.java

• Pessoa.java

• Sessao.java

• Usuario.java

### Nota: Usuario e Artista são subclasses de Pessoa. A classe Autentificacao é implementada em Usuario para validar login e identificar o tipo (usuário ou admin).

DAO --> responsáveis por acesso direto ao banco de dados:

• ArtistaDAO.java

• Conexao.java

• CurtidasDAO.java

• DescurtidasDAO.java

• HistoricoDAO.java

• MusicaDAO.java

• PlaylistDAO.java

• UsuarioDAO.java

Controller --> define a lógica de negócio do sistema:

• ControllerCadastro.java

• ControllerCurtidas.java

• ControllerDescurtidas.java

• ControllerLogin.java

• ControllerMusica.java

• ControllerPlaylist.java

• ControllerUsuario.java

### Observação:
• O driver postgresql-42.7.5.jar é essencial e deve estar vinculado ao classpath do projeto.
