/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  Tzh
 * Created: 23 de mai. de 2024
 */

create table if not exists tb_usuario (
    id serial PRIMARY KEY,
    email varchar(100),
    password varchar(32),
    name varchar(100)
);

create table if not exists tb_product(
    id serial PRIMARY KEY,
    name varchar(100),
    price float,
    qnt int


)

create table if not exists tb_cart (
    id serial PRIMARY KEY,
    user_id int references tb_usuario(id) ON DELETE cascade,
    total float
);

create table if not exists tb_cart_product (
    id serial PRIMARY KEY,
    cart_id int references tb_cart(id) ON DELETE CASCADE,
    product_id int references tb_product(id) ON DELETE CASCADE,
    product_name varchar(100),
    quantity int NOT NULL,
    unit_price float NOT NULL,
    subtotal float
);