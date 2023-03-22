ALTER TABLE tb_pedido ADD COLUMN codigo VARCHAR(36) NOT NULL AFTER id;

UPDATE tb_pedido SET codigo = uuid();

ALTER TABLE tb_pedido ADD CONSTRAINT uk_pedido_codigo UNIQUE (codigo);