ALTER TABLE carrinho_compras DROP CONSTRAINT carrinho_compras_id_cliente_fkey;
ALTER TABLE carrinho_compras DROP COLUMN id_cliente;

ALTER TABLE carrinho_compras
ADD COLUMN id_pedido INTEGER,
ADD CONSTRAINT fk_pedido
    FOREIGN KEY (id_pedido)
    REFERENCES pedidos(id_pedido);