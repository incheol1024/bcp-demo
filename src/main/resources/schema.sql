DROP TABLE IF EXISTS DEMO;
CREATE TABLE DEMO (ID INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR);
create table demo_entity (id bigint not null, name varchar(255), primary key (id));
create table demo_entity2 (id bigint not null, name varchar(255), number integer, DEMO_ID bigint, primary key (id));
create sequence hibernate_sequence start with 1 increment by 1;
alter table demo_entity2 add constraint FKmideofie1h4d9oqdxxf3v5308 foreign key (DEMO_ID) references demo_entity;

DROP TABLE people IF EXISTS;

CREATE TABLE people  (
    person_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    first_name VARCHAR(20),
    last_name VARCHAR(20)
);


INSERT INTO DEMO (NAME) VALUES ('San Francisco');

-- INSERT INTO DEMO_ENTITY (ID, NAME) VALUES (1, 'demo name');
-- INSERT INTO DEMO_ENTITY (ID, NAME) VALUES (2, 'demo name name');
-- INSERT INTO DEMO_ENTITY2 (id, NAME , NUMBER, DEMO_ID) VALUES (1,'demo2 name', 5, 1);
-- INSERT INTO DEMO_ENTITY2 (id, NAME , NUMBER, DEMO_ID) VALUES (2,'demo2 name name', 6, 1);
