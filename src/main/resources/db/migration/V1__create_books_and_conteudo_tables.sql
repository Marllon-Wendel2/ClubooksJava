CREATE TABLE livros (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        autor VARCHAR(255) NOT NULL,
        titulo VARCHAR(255) NOT NULL,
        capa BLOB
    );


    CREATE TABLE conteudo (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        capitulo VARCHAR(255) NOT NULL,
        titulo VARCHAR(255) NOT NULL,
        texto LONGTEXT,
        livro_id BIGINT NOT NULL,
        FOREIGN KEY (livro_id) REFERENCES livros(id) ON DELETE CASCADE
    );
