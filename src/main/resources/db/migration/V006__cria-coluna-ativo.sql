ALTER TABLE tb_restaurante ADD ativo TINYINT(1) NOT NULL;

UPDATE tb_restaurante SET ativo = true;