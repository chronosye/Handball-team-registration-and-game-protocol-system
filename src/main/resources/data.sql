insert into user (id,email,name,password,surname) values ('1','admin@test','Admins','$2a$15$O2Kdn.QojtG1mFWdGo97FuqK9moL.S29TjOJBLZCsOpJ6kE2p2uVa','Test')
insert into user (id,email,name,password,surname) values ('2','user@test','User','$2a$15$O2Kdn.QojtG1mFWdGo97FuqK9moL.S29TjOJBLZCsOpJ6kE2p2uVa','UserTest')

insert into user_roles(user_id,role) values ('1','ADMIN')
insert into user_roles(user_id,role) values ('2','USER')