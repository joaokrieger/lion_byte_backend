CREATE TABLE produtos (
    id_produto SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(255),
    preco DECIMAL(10, 2) NOT NULL,
    quantidade INT,
    categoria VARCHAR(255)
);

CREATE TABLE clientes (
    id_cliente SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    endereco VARCHAR(255),
    email VARCHAR(255),
    telefone VARCHAR(20)
);

CREATE TABLE pedidos (
    id_pedido SERIAL PRIMARY KEY,
    id_cliente INT NOT NULL,
    data DATE,
    total DECIMAL(10, 2),
    status VARCHAR(50),
    FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente)
);

CREATE TABLE carrinho_compras (
    id_produto INT NOT NULL,
    id_cliente INT NOT NULL,
    quantidade INT,
    FOREIGN KEY (id_produto) REFERENCES produtos(id_produto),
    FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente)
);

CREATE TABLE fornecedores (
    id_fornecedor SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    endereco VARCHAR(255),
    contato VARCHAR(255)
);
