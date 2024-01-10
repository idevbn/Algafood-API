SET foreign_key_checks = 0;

DELETE FROM tb_cidade;
DELETE FROM tb_cozinha;
DELETE FROM tb_estado;
DELETE FROM tb_forma_pagamento;
DELETE FROM tb_grupo;
DELETE FROM tb_grupo_permissao;
DELETE FROM tb_permissao;
DELETE FROM tb_produto;
DELETE FROM tb_restaurante;
DELETE FROM tb_restaurante_forma_pagamento;
DELETE FROM tb_usuario;
DELETE FROM tb_usuario_grupo;
DELETE FROM tb_restaurante_usuario_responsavel;
DELETE FROM tb_pedido;
DELETE FROM tb_item_pedido;
DELETE FROM tb_foto_produto;

SET foreign_key_checks = 1;

ALTER TABLE tb_cidade auto_increment = 1;
ALTER TABLE tb_cozinha auto_increment = 1;
ALTER TABLE tb_estado auto_increment = 1;
ALTER TABLE tb_forma_pagamento auto_increment = 1;
ALTER TABLE tb_grupo auto_increment = 1;
ALTER TABLE tb_permissao auto_increment = 1;
ALTER TABLE tb_produto auto_increment = 1;
ALTER TABLE tb_restaurante auto_increment = 1;
ALTER TABLE tb_usuario auto_increment = 1;
ALTER TABLE tb_pedido auto_increment = 1;
ALTER TABLE tb_item_pedido auto_increment = 1;

INSERT INTO tb_cozinha (nome) VALUES ('Tailandesa');
INSERT INTO tb_cozinha (nome) VALUES ('Indiana');

INSERT INTO tb_restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) VALUES (1, 'Pad Thai', 11.50, 1, utc_timestamp, utc_timestamp, true, true);
INSERT INTO tb_restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) VALUES (2, 'Bistrô Vadapav', 9.80, 2, utc_timestamp, utc_timestamp, true, true);
INSERT INTO tb_restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) VALUES (3, 'Namaskar', 7.39, 2, utc_timestamp, utc_timestamp, true, true);

INSERT INTO tb_forma_pagamento (id, descricao, data_atualizacao) VALUES (1, 'Dinheiro', utc_timestamp);
INSERT INTO tb_forma_pagamento (id, descricao, data_atualizacao) VALUES (2, 'Cartão de crédito', utc_timestamp);
INSERT INTO tb_forma_pagamento (id, descricao, data_atualizacao) VALUES (3, 'Cartão de débito', utc_timestamp);
INSERT INTO tb_forma_pagamento (id, descricao, data_atualizacao) VALUES (4, 'Pix', utc_timestamp);

INSERT INTO tb_estado (id, nome) VALUES (1, 'PB');
INSERT INTO tb_estado (id, nome) VALUES (2, 'RN');
INSERT INTO tb_estado (id, nome) VALUES (3, 'CE');
INSERT INTO tb_estado (id, nome) VALUES (4, 'PE');

INSERT INTO tb_cidade (nome, estado_id) VALUES ('Parahyba', 1);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('Passa-e-fica', 2);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('Itapipoca', 3);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('Garanhuns', 4);

INSERT INTO tb_permissao (nome, descricao) VALUES ('EDITAR_COZINHAS', 'Permite editar cozinhas');
INSERT INTO tb_permissao (nome, descricao) VALUES ('EDITAR_FORMAS_PAGAMENTO', 'Permite criar ou editar formas de pagamento');
INSERT INTO tb_permissao (nome, descricao) VALUES ('EDITAR_CIDADES', 'Permite criar ou editar cidades');
INSERT INTO tb_permissao (nome, descricao) VALUES ('EDITAR_ESTADOS', 'Permite criar ou editar estados');
INSERT INTO tb_permissao (nome, descricao) VALUES ('CONSULTAR_USUARIOS', 'Permite consultar usuários');
INSERT INTO tb_permissao (nome, descricao) VALUES ('EDITAR_USUARIOS', 'Permite criar ou editar usuários');
INSERT INTO tb_permissao (nome, descricao) VALUES ('EDITAR_RESTAURANTES', 'Permite criar, editar ou gerenciar restaurantes');
INSERT INTO tb_permissao (nome, descricao) VALUES ('CONSULTAR_PEDIDOS', 'Permite consultar pedidos');
INSERT INTO tb_permissao (nome, descricao) VALUES ('GERENCIAR_PEDIDOS', 'Permite gerenciar pedidos');
INSERT INTO tb_permissao (nome, descricao) VALUES ('GERAR_RELATORIOS', 'Permite gerar relatórios');

INSERT INTO tb_restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES (1, 1), (1, 2), (1, 3), (1, 4), (2, 1), (2, 4), (3, 1), (3, 2), (3, 3);

INSERT INTO tb_produto (id, ativo, nome, descricao, preco, restaurante_id) VALUES  (1, 1, 'Samosa', 'Pastéis fritos que podem ser recheados com lentilhas ou algum tipo de carne, temperados com especiarias indianas', 12.25, 3);
INSERT INTO tb_produto (id, ativo, nome, descricao, preco, restaurante_id) VALUES  (2, 1, 'Pad Thai', 'Composto por noodles misturados num wok bem quente com rebentos de soja, camarão, ovo e cebola e temperados com molho de soja, açúcar e malaguetas.', 21.45, 1);
INSERT INTO tb_produto (id, ativo, nome, descricao, preco, restaurante_id) VALUES  (3, 1, 'Palak Paneer', 'Consiste basicamente de três ingredientes: espinafre, queijo e gengibre.', 24.95, 2);

INSERT INTO tb_grupo (nome) VALUES ('Gerente'), ('Vendedor'), ('Secretária'), ('Cadastrador');

INSERT INTO tb_restaurante (id, nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_atualizacao, ativo) VALUES (4, 'Thai PB', 8.75, 1, 1, '58123100', 'Rua da Parahyba', '1936', 'Varadouro', utc_timestamp, utc_timestamp, true);

INSERT INTO tb_usuario (nome, email, senha, data_cadastro) VALUES ('José Peres', 'jose.peres@email.com', '$2a$10$ICf.LJbDXrWCUxsBF5a0j.sWEGt.MMSnIKRyz4SMrlcXjX0UMGXbq', utc_timestamp);
INSERT INTO tb_usuario (nome, email, senha, data_cadastro) VALUES ('Fernanda Cavalcanti', 'nanda_cavalcanti@email.com', '$2a$10$ICf.LJbDXrWCUxsBF5a0j.sWEGt.MMSnIKRyz4SMrlcXjX0UMGXbq', utc_timestamp);
INSERT INTO tb_usuario (nome, email, senha, data_cadastro) VALUES ('Julio Macedo', 'macedojulio@email.com', '$2a$10$ICf.LJbDXrWCUxsBF5a0j.sWEGt.MMSnIKRyz4SMrlcXjX0UMGXbq', utc_timestamp);
INSERT INTO tb_usuario (nome, email, senha, data_cadastro) VALUES ('Mariana Neves', 'mari.neves@email.com', '$2a$10$ICf.LJbDXrWCUxsBF5a0j.sWEGt.MMSnIKRyz4SMrlcXjX0UMGXbq', utc_timestamp);
INSERT INTO tb_usuario (nome, email, senha, data_cadastro) VALUES ('Pessoa Teste', 'idevbn@gmail.com', '$2a$10$ICf.LJbDXrWCUxsBF5a0j.sWEGt.MMSnIKRyz4SMrlcXjX0UMGXbq', utc_timestamp);

# Adiciona todas as permissoes no grupo do gerente
INSERT INTO tb_grupo_permissao (grupo_id, permissao_id)
SELECT 1, id FROM tb_permissao;

# Adiciona permissoes no grupo do vendedor
INSERT INTO tb_grupo_permissao (grupo_id, permissao_id)
SELECT 2, id FROM tb_permissao WHERE nome LIKE 'CONSULTAR_%';

INSERT INTO tb_grupo_permissao (grupo_id, permissao_id) VALUES (2, 10);

# Adiciona permissoes no grupo do auxiliar
INSERT INTO tb_grupo_permissao (grupo_id, permissao_id)
SELECT 3, id FROM tb_permissao WHERE nome LIKE 'CONSULTAR_%';

# Adiciona permissoes no grupo cadastrador
INSERT INTO tb_grupo_permissao (grupo_id, permissao_id)
SELECT 4, id FROM tb_permissao WHERE nome LIKE '%_RESTAURANTES' OR nome LIKE '%_PRODUTOS';

INSERT INTO tb_usuario_grupo (usuario_id, grupo_id) VALUES (1, 2), (1, 3), (2, 1), (2, 4), (3, 2), (3, 3), (4, 1), (4, 4), (5, 1);

INSERT INTO tb_usuario (nome, email, senha, data_cadastro) VALUES ('Manoel Lima', 'manoel.loja@gmail.com', '$2a$10$ICf.LJbDXrWCUxsBF5a0j.sWEGt.MMSnIKRyz4SMrlcXjX0UMGXbq', utc_timestamp);

INSERT INTO tb_restaurante_usuario_responsavel (restaurante_id, usuario_id) VALUES (1, 6), (2, 6);

INSERT INTO tb_pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status, data_criacao, subtotal, taxa_frete, valor_total) VALUES (1, '6805ebbd-a172-4237-9d6a-015999ed89e4', 1, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil', 'CRIADO', utc_timestamp, 298.90, 10, 308.90);
INSERT INTO tb_item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao) VALUES (1, 1, 1, 1, 78.9, 78.9, null);
INSERT INTO tb_item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao) VALUES (2, 1, 2, 2, 110, 220, 'Menos picante, por favor');

INSERT INTO tb_pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status, data_criacao, subtotal, taxa_frete, valor_total) VALUES (2, '5ef9ddf5-cef1-4572-86bd-bd6455ce1752', 4, 1, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro', 'CRIADO', utc_timestamp, 79, 0, 79);
INSERT INTO tb_item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao) VALUES (3, 2, 3, 1, 79, 79, 'Ao ponto');

INSERT INTO tb_pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status, data_criacao, subtotal, taxa_frete, valor_total) VALUES (3, '4b13595b-1580-4e6f-98a5-888ae491062a', 3, 5, 4, 3, '49200-000', 'Rua ABC', '1000', 'Casa', 'das Flores', 'CRIADO', utc_timestamp, 236.70, 5, 241.70);
INSERT INTO tb_item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao) VALUES (4, 3, 1, 1, 78.9, 78.9, 'Mais molho');
INSERT INTO tb_item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao) VALUES (5, 3, 2, 2, 78.9, 157.8, 'Menos picante, por favor');