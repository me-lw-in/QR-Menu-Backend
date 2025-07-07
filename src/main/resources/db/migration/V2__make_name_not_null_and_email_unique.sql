alter table users
    add constraint unique_email unique (email);

alter table users
    modify name varchar(100) not null;

