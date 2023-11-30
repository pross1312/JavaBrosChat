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
	primary key(username, friend)
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

create table GroupChat(
	id varchar(256),
	name varchar(50) not null,
	date datetime not null,
	primary key(id),
)
go
create table GroupChatMember(
	group_id varchar(256),
	username varchar(50),
	joined_date datetime not null,
	is_admin bit not null,
	start_history_msg int not null,
	last_read_msg int not null,
	primary key(group_id, username)
)
go
alter table GroupChatMember
add constraint FK_GCM_GROUPCHAT foreign key(group_id) references GroupChat,
	constraint FK_GCM_USER foreign key(username) references UserInfo,
	constraint FK_MSG_HISTORY_VALID CHECK(last_read_msg >= start_history_msg)
go
create table GroupChatMessage(
	id int,
	group_id varchar(256),
	sender varchar(50),
	sent_date datetime not null,
	msg varchar(MAX) not null,
	media_id varchar(50),
	primary key(id, group_id, sender)
)
go
alter table GroupChatMessage
add constraint FK_GCMSG_GROUPCHAT foreign key(group_id) references GroupChat,
	constraint FK_GCMSG_USER foreign key(group_id, sender) references GroupChatMember(group_id, username)
go

create table SpamReport(
	reporter varchar(50),
	target varchar(50),
	reason varchar(500) not null,
	date datetime not null,
	primary key(reporter, date)
)
go
alter table SpamReport
add constraint VALID CHECK(target <> reporter),
	constraint FK_SPAMREPORT_USER1 foreign key(reporter) references UserInfo,
	constraint FK_SPAMREPORT_USER2 foreign key(target) references UserInfo

go

CREATE FUNCTION list_friends_info(@usr varchar(50))
RETURNS TABLE AS
RETURN
	SELECT uf.*
	FROM UserFriend
	JOIN UserInfo uf ON uf.username = UserFriend.friend
	WHERE UserFriend.username = @usr
go
CREATE PROCEDURE add_msg_to_group
	@group_id varchar(256),
	@sender varchar(50),
	@sent_date datetime,
	@msg varchar(MAX),
	@media_id varchar(50)
AS
	declare @id int
	select @id = max(id) from GroupChatMessage where group_id = @group_id
	if @id is null
	begin
		set @id = 0;
	end
	set @id = @id + 1
   	insert into GroupChatMessage VALUES(@id, @group_id, @sender, @sent_date, @msg, @media_id)
   	update GroupChatMember set last_read_msg = last_read_msg + 1 where group_id = @group_id and username = @sender
GO
CREATE PROCEDURE add_member_to_group 
    @g_id varchar(256),   
	@username varchar(50),
	@joined_date datetime,
	@is_admin bit
AS   
   	insert into GroupChatMember(group_id, username, joined_date, is_admin, start_history_msg, last_read_msg)
   	values(@g_id, @username, @joined_date, @is_admin, 0, 0)
GO
CREATE FUNCTION list_group_of_user(@usr varchar(50))
RETURNS TABLE AS
RETURN
	select gc.*
	from GroupChat gc JOIN GroupChatMember gcm on gc.id = gcm.group_id
	where gcm.username = @usr
go
CREATE FUNCTION get_unread_msg(@username varchar(50), @group_id varchar(50)) 
RETURNS TABLE AS
RETURN
	select gcmsg.*
	from GroupChatMessage gcmsg
	join GroupChatMember gcm on gcm.group_id = gcmsg.group_id and
		  gcm.username = @username and gcm.group_id = @group_id
	where gcmsg.id > gcm.last_read_msg
go
CREATE PROCEDURE update_last_read
	@username varchar(50),
	@group_id varchar(256)
AS BEGIN
	declare @cur int = 0
	select @cur = max(id) from GroupChatMessage where group_id = @group_id
	if @cur is null
	begin
		set @cur = 0
	end
	update GroupChatMember set last_read_msg = @cur where username = @username and group_id = @group_id
END
	