-- Alterando a tabela tb_forma_pagamento, adicionando a coluna 'data_atualizacao',
-- para que esta seja utilizada como ETag.
ALTER TABLE tb_forma_pagamento ADD COLUMN data_atualizacao DATETIME NULL;

-- Atualizando os valores da nova coluna para o utc_ timestamp:
UPDATE tb_forma_pagamento SET data_atualizacao = utc_timestamp;

-- Com os valores antigos atualizados, atualizar a tabela para que a coluna
-- recém criada não contenha valores nulos:
ALTER TABLE tb_forma_pagamento MODIFY data_atualizacao DATETIME NOT NULL; 