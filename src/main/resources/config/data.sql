insert into app_role (id, role_name) values (1, 'ADMIN');

insert into app_role (id, role_name) values (2, 'BASIC');

insert into app_user (id, username, password, first_name, last_name, email, phone_number, last_logindate, rol_id)  values (1, 'jmorgan', 'johnweakpassword', 'John', 'Morgan', 'john_morgan@gmail.com', '+1 6879524', '2022-03-17', 1);

insert into app_user (id, username, password, first_name, last_name, email, phone_number, last_logindate, rol_id)  values (2, 'asmith', 'alanweakpassword', 'Alan', 'Smith', 'alan_smith@gmail.com', '+34 984562', '2022-03-16', 2);

