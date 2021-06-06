CREATE TABLE accounts
(
    id             VARCHAR(50) PRIMARY KEY,
    account_number VARCHAR(50) not null,
    bic            VARCHAR(50) not null,
    version        bigint      not null
);

insert into accounts
values ('1', 'account_number1', 'bic1', 1),
       ('2', 'account_number2', 'bic2', 1),
       ('3', 'account_number3', 'bic3', 1)