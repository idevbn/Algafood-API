INSERT INTO tb_cozinha (nome) VALUES ('Tailandesa');
INSERT INTO tb_cozinha (nome) VALUES ('Indiana');

INSERT INTO  tb_restaurante (id, nome, taxa_frete, cozinha_id) VALUES (1, 'Pad Thai', 11.50, 1);
INSERT INTO  tb_restaurante (id, nome, taxa_frete, cozinha_id) VALUES (2, 'Bistrô Vadapav', 9.80, 2);
INSERT INTO  tb_restaurante (id, nome, taxa_frete, cozinha_id) VALUES (3, 'Namaskar', 7.39, 2);

INSERT INTO tb_forma_pagamento (id, descricao, restaurante_id) VALUES (1, 'Cartão de crédito', 1);
INSERT INTO tb_forma_pagamento (id, descricao, restaurante_id) VALUES (2, 'Cartão de débito', 2);
INSERT INTO tb_forma_pagamento (id, descricao, restaurante_id) VALUES (3, 'Pix', 3);

INSERT INTO tb_estado (id, nome) VALUES (1, 'PB');
INSERT INTO tb_estado (id, nome) VALUES (2, 'RN');
INSERT INTO tb_estado (id, nome) VALUES (3, 'CE');
INSERT INTO tb_estado (id, nome) VALUES (4, 'PE');

INSERT INTO tb_cidade (nome, estado_id) VALUES ('Parahyba', 1);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('Passa-e-fica', 2);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('Itapipoca', 3);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('Garanhuns', 4);

INSERT INTO tb_permissao (nome, descricao) VALUES ('CONSULTAR_COZINHAS', 'Permissão para consultar cozinhas');
INSERT INTO tb_permissao (nome, descricao) VALUES ('EDITAR_COZINHAS', 'Permissão para editar cozinhas');