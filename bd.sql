-- Reinicia o banco de dados para aplicar as novas regras
DROP DATABASE IF EXISTS eletro_marketplace;
CREATE DATABASE eletro_marketplace;
USE eletro_marketplace;

-- ============================
-- TABELA CATEGORIAS
-- ============================
CREATE TABLE categorias (
    id_categoria BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL
);

-- ============================
-- TABELA VENDEDORES
-- ============================
CREATE TABLE vendedores (
    id_vendedor BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(200) UNIQUE NOT NULL
);

-- ============================
-- TABELA PRODUTOS
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

    FOREIGN KEY (id_categoria)
        REFERENCES categorias(id_categoria)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    FOREIGN KEY (id_vendedor)
        REFERENCES vendedores(id_vendedor)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- ============================
-- TABELA PEDIDOS
-- ============================
CREATE TABLE pedidos (
    id_pedido BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    -- Alterado para permitir NULL (necessário para o ON DELETE SET NULL funcionar)
    id_produto BIGINT UNSIGNED NULL, 
    id_vendedor BIGINT UNSIGNED NOT NULL,
    quantidade INT NOT NULL DEFAULT 1,
    data_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- Agora, se o produto sumir, o pedido permanece com id_produto = NULL
    CONSTRAINT fk_pedido_produto
        FOREIGN KEY (id_produto)
        REFERENCES produtos(id_produto)
        ON DELETE SET NULL
        ON UPDATE CASCADE,

    CONSTRAINT fk_pedido_vendedor
        FOREIGN KEY (id_vendedor)
        REFERENCES vendedores(id_vendedor)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);