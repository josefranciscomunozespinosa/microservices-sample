
insert into student values(10001,'Jose Francisco', 'E1234567', null, null);
insert into student values(10002,'Maria Angeles', 'A1234568', null, null);
insert into student values(10003,'Marcos Alonso', 'B12345678', null, null);
insert into student values(10004,'Diego Ortiz', 'C12345678', null, null);
insert into student values(10005,'Jaime Hernandez', 'D12345678', null, null);

insert into course values(1001,'Programación', 'Spring');
insert into course values(1002,'Inglés I', 'Inglés básico');
insert into course values(1003,'Inglés II', 'Inglés medio');
insert into course values(1004,'Inglés II', 'Inglés avanzado');

insert into student_course values (10001, 1001);
insert into student_course values (10001, 1003);
insert into student_course values (10002, 1001);
insert into student_course values (10002, 1004);
insert into student_course values (10003, 1002);
insert into student_course values (10004, 1001);
insert into student_course values (10004, 1002);
insert into student_course values (10004, 1003);
insert into student_course values (10004, 1004);