CREATE DATABASE bank;

USE bank;

CREATE TABLE bank.user (

  user_pk           INT AUTO_INCREMENT,
  user_email        VARCHAR(45),
  user_password     VARCHAR(45),

  PRIMARY KEY (user_pk)

);

CREATE TABLE bank.account (

  account_pk      INT AUTO_INCREMENT,
  amount          INT,
  user_fk         INT NOT NULL,

PRIMARY KEY (account_pk),

FOREIGN KEY (user_fk) references bank.user(user_pk)

);



CREATE TABLE bank.account_history (

  account_pk        INT,
  user_fk           INT,
  amount_before     INT,
  amount_after      INT,
  transaction_date  DATETIME

);

CREATE TABLE bank.session (

  session_pk        INT AUTO_INCREMENT,
  user_fk           INT ,
  sid               VARCHAR(50),
  expiration_time   LONG,

  PRIMARY KEY (session_pk),

  FOREIGN KEY (user_fk) REFERENCES bank.user(user_pk)

);


CREATE TRIGGER update_account_trigger

before UPDATE ON bank.account FOR each row

INSERT INTO bank.account_history VALUES (

  old.account_pk,
  old.user_fk,
  old.amount,
  new.amount,
  now()

);


