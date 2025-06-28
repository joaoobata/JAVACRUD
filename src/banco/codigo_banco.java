/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

/**
 *
 * @author 2830482311004
 */
public class codigo_banco {
    /**
drop database loja;
CREATE DATABASE loja;
USE loja;

CREATE TABLE categorias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(300) NOT NULL
);

CREATE TABLE produtos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    categoria_id INT NOT NULL,
    cod_barras VARCHAR(200) NOT NULL,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);

CREATE TABLE fornecedores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    nome_fantasia VARCHAR(200) NOT NULL,
    cnpj VARCHAR(18) UNIQUE NOT NULL
);

CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(18) UNIQUE NOT NULL,
    email VARCHAR(200) UNIQUE NOT NULL
);

 CREATE TABLE nota (
  id INT AUTO_INCREMENT PRIMARY KEY,
  data TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  valor_total DECIMAL(12,2) NOT NULL,
  tipo ENUM('E','S') NOT NULL,         -- E=Entrada, S=Saída
  cliente_id INT NULL,
  fornecedor_id INT NULL,
  FOREIGN KEY (cliente_id)   REFERENCES clientes(id),
  FOREIGN KEY (fornecedor_id) REFERENCES fornecedores(id)
);


CREATE TABLE nota_itens (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nota_id    INT NOT NULL,
  produto_id INT NOT NULL,
  quantidade INT NOT NULL,
  valor_unitario DECIMAL(10,2) NOT NULL,
  FOREIGN KEY (nota_id)    REFERENCES nota(id),
  FOREIGN KEY (produto_id) REFERENCES produtos(id)
);

INSERT INTO categorias (nome, descricao) VALUES
('Bebidas', 'Refrigerantes, sucos, água, cervejas e outras bebidas'),
('Alimentos', 'Produtos alimentícios em geral'),
('Limpeza', 'Produtos para limpeza doméstica e profissional'),
('Higiene Pessoal', 'Sabonetes, shampoos, cremes e itens de cuidado pessoal'),
('Papelaria', 'Materiais de escritório, cadernos, canetas'),
('Eletrônicos', 'Celulares, acessórios, fones, carregadores'),
('Roupas', 'Vestuário masculino, feminino e infantil'),
('Calçados', 'Sapatos, tênis, sandálias e chinelos'),
('Acessórios', 'Bolsas, mochilas, carteiras, relógios'),
('Perfumaria', 'Perfumes, desodorantes e cosméticos');	
     */
}
    