--DROP TABLE IF EXISTS users;
--DROP TABLE IF EXISTS menu_items;
--DROP TABLE IF EXISTS orders;
--DROP TABLE IF EXISTS orders_menu_items;
--DROP TABLE IF EXISTS deliveries;
--DROP TABLE users,menu_items,orders,orders_menu_items,deliveries;

CREATE TABLE IF NOT EXISTS users(
	id BIGSERIAL PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	email VARCHAR(255) UNIQUE NOT NULL,
	password VARCHAR(255) NOT NULL,
	telegram VARCHAR(255),
	phone VARCHAR(255),
	address VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS  menu_items(
	id BIGSERIAL PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	menu_section VARCHAR(255) NOT NULL,
	price DECIMAL(10,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS  orders(
	id BIGSERIAL PRIMARY KEY,
	date_time TIMESTAMP NOT NULL,
	status VARCHAR(255) NOT NULL,
	user_id BIGINT REFERENCES users(id),
	total_price DECIMAL(10,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS orders_menu_items(
	id BIGSERIAL PRIMARY KEY,
	order_id BIGINT REFERENCES orders(id),
	menu_item_id BIGINT REFERENCES menu_items(id)
);

CREATE TABLE IF NOT EXISTS  deliveries(
	id BIGSERIAL PRIMARY KEY,
	address VARCHAR(255) NOT NULL,
	phone VARCHAR(255) NOT NULL,
	date_time TIMESTAMP NOT NULL,
	order_id BIGINT NOT NULL REFERENCES orders(id)
);
