create schema if not exists credit_card_test;
use credit_card_test;

create table if not exists credit_cards (
	id int(11) not null auto_increment primary key,
    current_balance decimal(7,2) not null,
    expiration_date date not null,
    credit_limit decimal(7,2) not null,
    card_number varchar(255),
    apr decimal(7,2) not null,
    user_id int(11)
);

create table if not exists users (
	id int(11) not null auto_increment primary key,
    name varchar(255) not null,
    address varchar(255)
);

alter table credit_cards add constraint fk_credit_cards_users foreign key (user_id) references users(id);