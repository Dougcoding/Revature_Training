CREATE DATABASE project_0;
USE project_0;

DROP TABLE IF EXISTS bank_account;
CREATE TABLE bank_account (
account_id int NOT NULL primary key,
first_name varchar(255),
last_name varchar(255),
checking_id int NOT NULL,
savings_id int NOT NULL,
PRIMARY KEY (account_id),
constraint foreign key (checking_id)
	references checking(checking_id),
constraint foreign key (savings_id)
	references savings(savings_id)
);


DROP TABLE IF EXISTS checking;
CREATE TABLE checking (
checking_id int NOT NULL,
checking_id int,
PRIMARY KEY (checking_id)
);

DROP TABLE IF EXISTS savings;
CREATE TABLE savings (
savings_id int NOT NULL,
savings_balance int,
PRIMARY KEY (savings_id)
);
-- add_remove 1 for add
-- checking_savings 1 for checking
DROP TABLE IF EXISTS deposit_withdrawal;
CREATE TABLE deposit_withdrawal (
transaction_id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
account_id int,
add_remove bool,-- add_remove 1 is add, 0 is remove
checking_savings bool,-- checking_savings 1 is checking 0 is savings
amount int,
date timestamp not null default now(),
PRIMARY KEY (transaction_id),
constraint foreign key (account_id)
	references bank_account(account_id)
);

INSERT INTO bank_account (account_id, first_name, last_name, checking_id, savings_id)
VALUES (1,"douglas","lam",101,201);
INSERT INTO checking (checking_id, checking_balance)
VALUES (101, 1500);
INSERT INTO savings (savings_id, savings_balance)
VALUES (201,20000);
INSERT INTO deposit_withdrawal (account_id,add_remove,checking_savings,amount)
VALUES (1,1,1,700);

INSERT INTO bank_account (account_id, first_name, last_name, checking_id, savings_id)
VALUES (2,"jack", "bauer", 102,202);
INSERT INTO checking (checking_id, checking_balance)
VALUES (102, 3500);
INSERT INTO savings (savings_id, savings_balance)
VALUES (202,80000);
INSERT INTO deposit_withdrawal (account_id,add_remove,checking_savings,amount)
VALUES (2,1,1,700);

update checking set checking_balance = checking_balance + 700 where checking_id = (select checking_id from bank_account where account_id=1);

update bank_account set last_name='lamb' where account_id=1;
truncate bank_account;
truncate checking;
truncate savings;
truncate deposit_withdrawal;


select * from bank_account;
select * from checking;
select * from savings;
select * from deposit_withdrawal;
-- select account_id, full_name -> '$.first_name' from bank_account;
-- select account_id, full_name ->> '$.first_name' from bank_account;
select now();
