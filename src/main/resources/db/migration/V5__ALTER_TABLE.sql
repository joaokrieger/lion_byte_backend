ALTER TABLE produtos
ADD COLUMN id_fornecedor INTEGER,
ADD CONSTRAINT fk_fornecedor
    FOREIGN KEY (id_fornecedor)
    REFERENCES fornecedores(id_fornecedor);

ALTER TABLE produtos
ADD CONSTRAINT fk_categoria
    FOREIGN KEY (id_categoria)
    REFERENCES categorias(id_categoria);
