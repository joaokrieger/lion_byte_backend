ALTER TABLE carrinho_compras
ADD COLUMN id_usuario INTEGER,
ADD CONSTRAINT fk_carrinho_compras_usuarios
    FOREIGN KEY (id_usuario)
    REFERENCES usuarios (id_usuario);
