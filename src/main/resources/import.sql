INSERT INTO cozinha (nome) values ('Chinesa');
INSERT INTO cozinha (nome) values ('Tailandesa');
INSERT INTO restaurante (nome, taxa_frete, cozinha_id) values ('Restaurante chinese',17.90, 1);
INSERT INTO restaurante (nome, taxa_frete, cozinha_id) values ('MacDonald',20.90, 2);
INSERT INTO restaurante (nome, taxa_frete, cozinha_id) values ('Tuk tul comida indiana',0.0, 2);

INSERT INTO estado (nome) values ('São Paulo');
INSERT INTO estado (nome) values ('Bahia');
INSERT INTO estado (nome) values ('Distrito Federal');

INSERT INTO cidade (nome, estado_id) values ('Santos', 1);
INSERT INTO cidade (nome, estado_id) values ('Santo André', 1);
INSERT INTO cidade (nome, estado_id) values ('Salvador', 2);
INSERT INTO cidade (nome, estado_id) values ('Ceilândia', 3);

INSERT INTO forma_pagamento (descricao) values ('Cartão de Crédito');
INSERT INTO forma_pagamento (descricao) values ('Cartão de Débito');
INSERT INTO forma_pagamento (descricao) values ('Dinheiro');

INSERT INTO permissao (nome, descricao) values ('ADMIN', 'Usuário com privilégios de administrador' );
INSERT INTO permissao (nome, descricao) values ('USUARIO', 'Usuário com privilégios de consulta');







