CREATE TABLE tb_restaurante_usuario_responsavel (
    restaurante_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,

    PRIMARY KEY (restaurante_id, usuario_id)
) engine=InnoDB DEFAULT charset=utf8;

ALTER TABLE tb_restaurante_usuario_responsavel ADD CONSTRAINT fk_restaurante_usuario_restaurante
FOREIGN KEY (restaurante_id) REFERENCES tb_restaurante (id);

ALTER TABLE tb_restaurante_usuario_responsavel ADD CONSTRAINT fk_restaurante_usuario_usuario
FOREIGN KEY (usuario_id) REFERENCES tb_usuario (id);