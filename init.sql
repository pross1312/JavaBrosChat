use master;
go
drop database java;
go
create database Java;
go
use java;
go

create table Account(
	username varchar(50) primary key,
	password varchar(512) not null,
	type int not null,
	is_locked bit not null,
)
go
insert into Account values('admin', '2CSU8F1pF7oC96qilonMtES7c/IDgIdssF0fN1N7eJI=', 1, 0)

go

create table UserInfo(
	username varchar(50) primary key,
	fullname nvarchar(100) not null,
	email varchar(50) not null,
	address nvarchar(100) not null,
	birthdate date not null,
	gender int not null
)
go
alter table UserInfo
add constraint FK_USERINFO_ACCOUNT
	foreign key(username) references Account
go

create table LoginRecord(
	username varchar(50),
	ts datetime not null,
	primary key(username, ts)
)
go
alter table LoginRecord
add constraint FK_LOGINRECORD_ACCOUNT
	foreign key(username) references Account
go
create table RegistrationRecord(
	username varchar(50) primary key,
	ts datetime not null,
)
go
alter table RegistrationRecord
add constraint FK_REGISTRATION_USER
	foreign key(username) references UserInfo
go
create table UserFriend(
	username varchar(50),
	friend varchar(50),
	primary key(username)
)
go
alter table UserFriend
add constraint UF_check_valid check(username <> friend),
	constraint FK_USERFRIEND_USER1 foreign key(username) references UserInfo,
	constraint FK_USERFRIEND_USER2 foreign key(friend) references UserInfo
go
create table FriendRequest(
	initiator varchar(50),
	target varchar(50),
	date DateTime,
	primary key(initiator, target)
)
go
alter table FriendRequest
add constraint FK_FR_USER1 foreign key(initiator) references UserInfo,
	constraint FK_FR_USER2 foreign key(target) references UserInfo,
	constraint FK_CHECK_VALID CHECK(initiator <> target)
	
go

CREATE FUNCTION list_friends_info(@usr varchar(50))
RETURNS TABLE AS
RETURN
	SELECT uf.*
	FROM UserFriend
	JOIN UserInfo uf ON uf.username = UserFriend.friend
	WHERE UserFriend.username = @usr
go