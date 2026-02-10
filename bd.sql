DROP DATABASE IF EXISTS eletro_marketplace;
CREATE DATABASE eletro_marketplace;
USE eletro_marketplace;

-- ============================
-- TABELA CATEGORIAS (Adicionado campo 'ativo')
-- ============================
CREATE TABLE categorias (
    id_categoria BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    ativo BOOLEAN DEFAULT TRUE -- Flag de Soft Delete
);

-- ============================
-- TABELA VENDEDORES (Adicionado campo 'ativo')
-- ============================
CREATE TABLE vendedores (
    id_vendedor BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(200) UNIQUE NOT NULL,
    ativo BOOLEAN DEFAULT TRUE -- Flag de Soft Delete
);

-- ============================
-- TABELA PRODUTOS (Adicionado campo 'ativo')
-- ============================
CREATE TABLE produtos (
    id_produto BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    descricao TEXT,
    preco DECIMAL(10,2) NOT NULL,
    estoque INT DEFAULT 0,
    id_categoria BIGINT UNSIGNED NOT NULL,
    id_vendedor BIGINT UNSIGNED NOT NULL,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ativo BOOLEAN DEFAULT TRUE, -- Flag de Soft Delete

    FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria),
    FOREIGN KEY (id_vendedor) REFERENCES vendedores(id_vendedor)
);

-- ============================
-- TABELA PEDIDOS
-- ============================
CREATE TABLE pedidos (
    id_pedido BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    id_produto BIGINT UNSIGNED NOT NULL, -- Voltamos para NOT NULL para integridade do histórico
    id_vendedor BIGINT UNSIGNED NOT NULL,
    quantidade INT NOT NULL DEFAULT 1,
    data_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (id_produto) REFERENCES produtos(id_produto),
    FOREIGN KEY (id_vendedor) REFERENCES vendedores(id_vendedor)
);

select * from pedidos