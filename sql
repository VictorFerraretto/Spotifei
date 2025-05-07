
CREATE TABLE Pessoa (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    telefone VARCHAR(15) NOT NULL
);

CREATE TABLE Usuario (
    id SERIAL PRIMARY KEY,
    pessoa_id INT NOT NULL,
    username VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    tipo_usuario VARCHAR(10) DEFAULT 'comum',
    FOREIGN KEY (pessoa_id) REFERENCES Pessoa(id)
);

CREATE TABLE Artista (
    id SERIAL PRIMARY KEY,
    pessoa_id INT NOT NULL,
    nome_artistico VARCHAR(255) NOT NULL,
    genero VARCHAR(100),
    FOREIGN KEY (pessoa_id) REFERENCES Pessoa(id)
);

CREATE TABLE Musica (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    artista_id INT NOT NULL,
    genero VARCHAR(100),
    lancamento DATE,
    FOREIGN KEY (artista_id) REFERENCES Artista(id)
);

-- Inserção de 5 usuários (1 admin e 4 comuns)

INSERT INTO Pessoa (nome, email, telefone) VALUES
('Carlos Silva', 'carlos@email.com', '999999999'),  -- ID 1
('Maria Souza', 'maria@email.com', '988888888'),    -- ID 2
('João Almeida', 'joao@email.com', '977777777'),    -- ID 3
('Ana Costa', 'ana@email.com', '966666666'),        -- ID 4
('Lucas Pereira', 'lucas@email.com', '955555555');  -- ID 5

INSERT INTO Usuario (pessoa_id, username, senha, tipo_usuario) VALUES
(1, 'carlos_admin', 'senha123', 'admin'),
(2, 'maria_user', 'senha456', 'comum'),
(3, 'joao_user', 'senha789', 'comum'),
(4, 'ana_user', 'senha321', 'comum'),
(5, 'lucas_user', 'senha654', 'comum');

-- Inserção de 42 artistas (Pessoa + Artista)

-- Inserção na tabela Pessoa (IDs de 6 a 47)
INSERT INTO Pessoa (nome, email, telefone) VALUES
('Beyoncé', 'beyonce@email.com', '900000001'),
('The Weeknd', 'weeknd@email.com', '900000002'),
('Anitta', 'anitta@email.com', '900000003'),
('Drake', 'drake@email.com', '900000004'),
('Lady Gaga', 'gaga@email.com', '900000005'),
('Ed Sheeran', 'ed@email.com', '900000006'),
('Adele', 'adele@email.com', '900000007'),
('Bruno Mars', 'bruno@email.com', '900000008'),
('Dua Lipa', 'dua@email.com', '900000009'),
('Rihanna', 'rihanna@email.com', '900000010'),
('Shakira', 'shakira@email.com', '900000011'),
('Coldplay', 'coldplay@email.com', '900000012'),
('Maroon 5', 'maroon5@email.com', '900000013'),
('Billie Eilish', 'billie@email.com', '900000014'),
('Justin Bieber', 'justin@email.com', '900000015'),
('Post Malone', 'post@email.com', '900000016'),
('Taylor Swift', 'taylor@email.com', '900000017'),
('Luísa Sonza', 'luisa@email.com', '900000018'),
('Zé Neto & Cristiano', 'zec@email.com', '900000019'),
('Gusttavo Lima', 'gusttavo@email.com', '900000020'),
('Marília Mendonça', 'marilia@email.com', '900000021'),
('Henrique & Juliano', 'henrique@email.com', '900000022'),
('Wesley Safadão', 'safadao@email.com', '900000023'),
('MC Kevinho', 'kevinho@email.com', '900000024'),
('MC Don Juan', 'donjuan@email.com', '900000025'),
('MC Hariel', 'hariel@email.com', '900000026'),
('Projota', 'projota@email.com', '900000027'),
('Emicida', 'emicida@email.com', '900000028'),
('Criolo', 'criolo@email.com', '900000029'),
('Jorge & Mateus', 'jorge@email.com', '900000030'),
('Maiara & Maraisa', 'maisa@email.com', '900000031'),
('Luan Santana', 'luan@email.com', '900000032'),
('Alok', 'alok@email.com', '900000033'),
('Vintage Culture', 'vintage@email.com', '900000034'),
('Anavitoria', 'anavitoria@email.com', '900000035'),
('Tiago Iorc', 'tiago@email.com', '900000036'),
('Melim', 'melim@email.com', '900000037'),
('Ivete Sangalo', 'ivete@email.com', '900000038'),
('Claudia Leitte', 'claudia@email.com', '900000039'),
('Ludmilla', 'lud@email.com', '900000040'),
('MC Mirella', 'mirella@email.com', '900000041'),
('MC Poze do Rodo', 'poze@email.com', '900000042');

-- Inserção na tabela Artista (IDs de 1 a 42)
INSERT INTO Artista (pessoa_id, nome_artistico, genero) VALUES
(6, 'Beyoncé', 'Pop'),
(7, 'The Weeknd', 'R&B'),
(8, 'Anitta', 'Funk'),
(9, 'Drake', 'Hip Hop'),
(10, 'Lady Gaga', 'Pop'),
(11, 'Ed Sheeran', 'Pop'),
(12, 'Adele', 'Pop'),
(13, 'Bruno Mars', 'Pop'),
(14, 'Dua Lipa', 'Pop'),
(15, 'Rihanna', 'Pop'),
(16, 'Shakira', 'Latino'),
(17, 'Coldplay', 'Rock'),
(18, 'Maroon 5', 'Pop Rock'),
(19, 'Billie Eilish', 'Pop'),
(20, 'Justin Bieber', 'Pop'),
(21, 'Post Malone', 'Hip Hop'),
(22, 'Taylor Swift', 'Pop'),
(23, 'Luísa Sonza', 'Pop'),
(24, 'Zé Neto & Cristiano', 'Sertanejo'),
(25, 'Gusttavo Lima', 'Sertanejo'),
(26, 'Marília Mendonça', 'Sertanejo'),
(27, 'Henrique & Juliano', 'Sertanejo'),
(28, 'Wesley Safadão', 'Forró'),
(29, 'MC Kevinho', 'Funk'),
(30, 'MC Don Juan', 'Funk'),
(31, 'MC Hariel', 'Funk'),
(32, 'Projota', 'Rap'),
(33, 'Emicida', 'Rap'),
(34, 'Criolo', 'Rap'),
(35, 'Jorge & Mateus', 'Sertanejo'),
(36, 'Maiara & Maraisa', 'Sertanejo'),
(37, 'Luan Santana', 'Sertanejo'),
(38, 'Alok', 'Eletrônica'),
(39, 'Vintage Culture', 'Eletrônica'),
(40, 'Anavitoria', 'MPB'),
(41, 'Tiago Iorc', 'MPB'),
(42, 'Melim', 'Pop'),
(43, 'Ivete Sangalo', 'Axé'),
(44, 'Claudia Leitte', 'Axé'),
(45, 'Ludmilla', 'Funk'),
(46, 'MC Mirella', 'Funk'),
(47, 'MC Poze do Rodo', 'Funk');

-- Inserção de 50 músicas reais (com artista_id de 1 a 42)

INSERT INTO Musica (titulo, artista_id, genero, lancamento) VALUES
('Halo', 1, 'Pop', '2008-01-01'),
('Blinding Lights', 2, 'R&B', '2019-11-29'),
('Envolver', 3, 'Funk', '2021-11-11'),
('God''s Plan', 4, 'Hip Hop', '2018-01-19'),
('Bad Romance', 5, 'Pop', '2009-10-26'),
('Shape of You', 6, 'Pop', '2017-01-06'),
('Someone Like You', 7, 'Pop', '2011-01-24'),
('Uptown Funk', 8, 'Pop', '2014-11-10'),
('Levitating', 9, 'Pop', '2020-10-01'),
('Diamonds', 10, 'Pop', '2012-09-26'),
('Hips Don’t Lie', 11, 'Latino', '2006-02-28'),
('Yellow', 12, 'Rock', '2000-06-26'),
('Sugar', 13, 'Pop Rock', '2015-01-13'),
('Bad Guy', 14, 'Pop', '2019-03-29'),
('Peaches', 15, 'Pop', '2021-03-19'),
('Circles', 16, 'Hip Hop', '2019-08-30'),
('Love Story', 17, 'Pop', '2008-09-15'),
('Modo Turbo', 18, 'Pop', '2020-12-21'),
('Largado às Traças', 19, 'Sertanejo', '2018-01-01'),
('Apelido Carinhoso', 20, 'Sertanejo', '2017-12-01'),
('Infiel', 21, 'Sertanejo', '2015-01-01'),
('Cuida Bem Dela', 22, 'Sertanejo', '2014-01-01'),
('Camarote', 23, 'Forró', '2014-01-01'),
('Olha a Explosão', 24, 'Funk', '2016-01-01'),
('Amar, Amei', 25, 'Funk', '2018-01-01'),
('Ilusão', 26, 'Funk', '2021-01-01'),
('Mulher', 27, 'Rap', '2020-01-01'),
('Boa Esperança', 28, 'Rap', '2015-01-01'),
('Não Existe Amor em SP', 29, 'Rap', '2011-01-01'),
('Sosseguei', 30, 'Sertanejo', '2015-01-01'),
('10%', 31, 'Sertanejo', '2016-01-01'),
('Escreve Aí', 32, 'Sertanejo', '2015-01-01'),
('Hear Me Now', 33, 'Eletrônica', '2016-10-21'),
('Slow Down', 34, 'Eletrônica', '2021-06-01'),
('Trevo (Tu)', 35, 'MPB', '2016-01-01'),
('Amei Te Ver', 36, 'MPB', '2015-01-01'),
('Meu Abrigo', 37, 'Pop', '2018-01-01'),
('Festa', 38, 'Axé', '2001-01-01'),
('Exttravasa', 39, 'Axé', '2007-01-01'),
('Verdinha', 40, 'Funk', '2019-01-01'),
('Ice', 41, 'Funk', '2020-01-01'),
('Vida Louca', 42, 'Funk', '2021-01-01'),
('Positions', 1, 'Pop', '2020-10-23'),
('Save Your Tears', 2, 'R&B', '2020-03-20'),
('Bang', 3, 'Pop', '2015-10-09'),
('Hotline Bling', 4, 'Hip Hop', '2015-07-31'),
('Rain On Me', 5, 'Pop', '2020-05-22');
