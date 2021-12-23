create table doctor
    (id bigint auto_increment, active boolean not null, first_name varchar(255), hourly_rate integer not null, last_name varchar(255), medical_animal_specialization varchar(255), medical_specialization varchar(255), nip bigint not null, primary key (id));
create table patient
    (id bigint auto_increment, age integer not null, email varchar(255), name_of_animal varchar(255), name_of_owner varchar(255), race varchar(255), type varchar(255), primary key (id));
create table role
    (id bigint auto_increment, name varchar(255), primary key (id));
create table user_roles
    (user_id bigint not null, role_id bigint not null, primary key (user_id, role_id));
create table users
    (id bigint auto_increment, email varchar(255), password varchar(255), username varchar(255), primary key (id));
create table visit
    (id bigint auto_increment, status varchar(255), visit_time timestamp not null, doctor_id bigint not null, patient_id bigint not null, primary key (id));
create table visit_token
    (id bigint auto_increment, expiry_date timestamp not null, token varchar(255) not null, visit_id bigint, primary key (id));

alter table users add constraint users_uq_username unique (username);
alter table users add constraint users_uq_email unique (email);
alter table visit_token add constraint visit_token_uq_token unique (token);

alter table user_roles add constraint user_roles_role_id foreign key (role_id) references role;
alter table user_roles add constraint user_roles_user_id foreign key (user_id) references users;
alter table visit add constraint visit_doctor_id foreign key (doctor_id) references doctor;
alter table visit add constraint visit_patient_id foreign key (patient_id) references patient;
alter table visit_token add constraint visit_token_visit_id foreign key (visit_id) references visit;
