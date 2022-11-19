CREATE TABLE tb_estado (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(80) NOT NULL,

  PRIMARY KEY(id)
) engine=InnoDB default charset=utf8;

ALTER TABLE tb_cidade ADD COLUMN estado_id BIGINT NOT NULL;

UPDATE tb_cidade tbc SET tbc.estado_id = (SELECT tbe.id FROM tb_estado tbe WHERE tbe.nome = tbc.nome_estado);

ALTER TABLE tb_cidade ADD CONSTRAINT fk_cidade_estado FOREIGN KEY (estado_id) REFERENCES tb_estado (id);

ALTER TABLE tb_cidade DROP COLUMN nome_estado;

ALTER TABLE tb_cidade CHANGE nome_cidade nome VARCHAR(80) NOT NULL;