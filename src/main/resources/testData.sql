insert into user (id,email,name,password,surname) values ('1','admin@test','Admins','$2a$15$O2Kdn.QojtG1mFWdGo97FuqK9moL.S29TjOJBLZCsOpJ6kE2p2uVa','Test')
insert into user (id,email,name,password,surname) values ('2','user@test','User','$2a$15$O2Kdn.QojtG1mFWdGo97FuqK9moL.S29TjOJBLZCsOpJ6kE2p2uVa','UserTest')
insert into user (id,email,name,password,surname) values ('3','org@test','Org','$2a$15$O2Kdn.QojtG1mFWdGo97FuqK9moL.S29TjOJBLZCsOpJ6kE2p2uVa','OrgTest')
insert into user (id,email,name,password,surname) values ('4','man@test','Man','$2a$15$O2Kdn.QojtG1mFWdGo97FuqK9moL.S29TjOJBLZCsOpJ6kE2p2uVa','ManTest')
insert into user (id,email,name,password,surname) values ('5','pro@test','Pro','$2a$15$O2Kdn.QojtG1mFWdGo97FuqK9moL.S29TjOJBLZCsOpJ6kE2p2uVa','ProTest')
insert into user (id,email,name,password,surname) values ('6','pro2@test','Pro2','$2a$15$O2Kdn.QojtG1mFWdGo97FuqK9moL.S29TjOJBLZCsOpJ6kE2p2uVa','ProTest2')

insert into user_roles(user_id,role) values ('1','ADMIN')
insert into user_roles(user_id,role) values ('2','USER')
insert into user_roles(user_id,role) values ('3','ORGANIZER')
insert into user_roles(user_id,role) values ('4','MANAGER')
insert into user_roles(user_id,role) values ('5','PROTOCOLIST')
insert into user_roles(user_id,role) values ('6','PROTOCOLIST')

insert into teams(id,name,manager_id) values ('1','Sapņu komanda','1')

insert into players(id,name,surname,position,team_id) values ('1','Jānis','Ozols','GK',1)
insert into players(id,name,surname,position,team_id) values ('2','Pēteris','Liepa','CB',1)
insert into players(id,name,surname,position,team_id) values ('3','Uldis','Jansons','LW',1)
insert into players(id,name,surname,position,team_id) values ('4','Emīls','Borisenko','RW',1)
insert into players(id,name,surname,position,team_id) values ('5','Kristers','Ratnieks','LC',1)
insert into players(id,name,surname,position,team_id) values ('6','Jānis','Noviks','RC',1)

insert into teams(id,name,manager_id) values ('2','Dinozauri','4')

insert into players(id,name,surname,position,team_id) values ('7','Kristiāns','Jaunzems','GK',2)
insert into players(id,name,surname,position,team_id) values ('8','Krišjānis','Barons','CB',2)
insert into players(id,name,surname,position,team_id) values ('9','Reinis','Ozols','LW',2)
insert into players(id,name,surname,position,team_id) values ('10','Juris','Baranovs','LW',2)
insert into players(id,name,surname,position,team_id) values ('11','Anatolijs','Rutka','RW',2)
insert into players(id,name,surname,position,team_id) values ('12','Kristaps','Lazda','LC',2)

insert into tournaments(id,name,organizer_id) values ('1','Latvijas kauss','1')

insert into tournaments_teams(tournament_id,team_id) values('1','1')
insert into tournaments_teams(tournament_id,team_id) values('1','2')

insert into games(id,away_team_goals,date,ended,home_team_goals,away_team_id,home_team_id,protocolist_id,tournament_id) values('1','0','2021-01-01 00:00:00',false,'0','2','1','5','1')

insert into protocol(id,game_id) values ('1','1')

update games set protocol_id = '1',ended = true where id = 1

insert into player_stats(id,goals,shots,player_id,protocol_id) values ('1','5','6','7','1')
insert into player_stats(id,goals,shots,player_id,protocol_id) values ('2','1','1','8','1')
insert into player_stats(id,goals,shots,player_id,protocol_id) values ('3','0','0','9','1')
insert into player_stats(id,goals,shots,player_id,protocol_id) values ('4','11','20','10','1')
insert into player_stats(id,goals,shots,player_id,protocol_id) values ('5','5','10','11','1')
insert into player_stats(id,goals,shots,player_id,protocol_id) values ('6','12','17','12','1')
insert into player_stats(id,goals,shots,player_id,protocol_id) values ('7','10','11','1','1')
insert into player_stats(id,goals,shots,player_id,protocol_id) values ('8','12','20','2','1')
insert into player_stats(id,goals,shots,player_id,protocol_id) values ('9','0','4','3','1')
insert into player_stats(id,goals,shots,player_id,protocol_id) values ('10','2','5','4','1')
insert into player_stats(id,goals,shots,player_id,protocol_id) values ('11','3','3','5','1')
insert into player_stats(id,goals,shots,player_id,protocol_id) values ('12','6','12','6','1')

insert into protocol_home_team_player_stats(protocol_id,home_team_player_stats_id) values ('1','7')
insert into protocol_home_team_player_stats(protocol_id,home_team_player_stats_id) values ('1','8')
insert into protocol_home_team_player_stats(protocol_id,home_team_player_stats_id) values ('1','9')
insert into protocol_home_team_player_stats(protocol_id,home_team_player_stats_id) values ('1','10')
insert into protocol_home_team_player_stats(protocol_id,home_team_player_stats_id) values ('1','11')
insert into protocol_home_team_player_stats(protocol_id,home_team_player_stats_id) values ('1','12')

insert into protocol_away_team_player_stats(protocol_id,away_team_player_stats_id) values ('1','1')
insert into protocol_away_team_player_stats(protocol_id,away_team_player_stats_id) values ('1','2')
insert into protocol_away_team_player_stats(protocol_id,away_team_player_stats_id) values ('1','3')
insert into protocol_away_team_player_stats(protocol_id,away_team_player_stats_id) values ('1','4')
insert into protocol_away_team_player_stats(protocol_id,away_team_player_stats_id) values ('1','5')
insert into protocol_away_team_player_stats(protocol_id,away_team_player_stats_id) values ('1','6')

update games set home_team_goals = '33',away_team_goals = '34' where id = 1