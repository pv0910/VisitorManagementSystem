use namaste_visitor;

create table login(emp_id varchar(30), password varchar(40), user_type varchar(20), PRIMARY KEY(emp_id));

select * from login;
delete from login where emp_id="19EARCS066";

insert into login values ("20G12", "Raju", "security");
insert into login values ("22TEC07", "Manish", "employee");

create table entries(visiting_ID varchar(30), first_name varchar(30), last_name varchar(30), mob_num varchar(15), aadhar_num varchar(30), purpose varchar(100), entry_time datetime, exit_time datetime, PRIMARY KEY(visiting_ID));
select * from entries;

insert into entries values("1", "Ramesh", "Verma", "9822343", "4567854", "guest", "2022-08-08 12:04:00", "2022-08-08 14:10:22");