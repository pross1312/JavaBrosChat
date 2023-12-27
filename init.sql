use master;
go
drop database java;
go
create database Java;
go
use java;
go

CREATE TYPE USERNAME_TYPE
FROM varchar(64) NOT NULL;
go
CREATE TYPE GROUP_ID_TYPE
FROM varchar(64) NOT NULL;
go

create table Account(
	username USERNAME_TYPE primary key,
	password varchar(512) not null,
	type int not null,
	is_locked bit not null,
)
go
insert into Account values('admin', '2CSU8F1pF7oC96qilonMtES7c/IDgIdssF0fN1N7eJI=', 1, 0)
insert into Account values('__REMOVED__', '', 0, 1)/** TRASH ACCOUNT **/
go

create table UserInfo(
	username USERNAME_TYPE primary key,
	fullname nvarchar(100) not null,
	email varchar(50) not null,
	address nvarchar(100) not null,
	birthdate date not null,
	gender int not null
)
go
insert into UserInfo values('__REMOVED__', '', '', '', '', 0) /** TRASH ACCOUNT **/


alter table UserInfo
add constraint FK_USERINFO_ACCOUNT
	foreign key(username) references Account(username)
go

create table LoginRecord(
	username USERNAME_TYPE,
	ts datetime not null,
	primary key(username, ts)
)
go
alter table LoginRecord
add constraint FK_LOGINRECORD_ACCOUNT
	foreign key(username) references Account(username)
go
create table RegistrationRecord(
	username USERNAME_TYPE primary key,
	ts datetime not null,
)
go
alter table RegistrationRecord
add constraint FK_REGISTRATION_USER
	foreign key(username) references UserInfo
go
create table UserFriend(
	username USERNAME_TYPE,
	friend USERNAME_TYPE,
	start_history_msg int not null, /**TODO: reconsider, for friend message table **/
	last_read_msg int not null,     /**TODO: reconsider, for friend message table **/
	primary key(username, friend)
)
go
alter table UserFriend
add constraint UF_check_valid check(username <> friend),
	constraint FK_USERFRIEND_USER1 foreign key(username) references UserInfo(username),
	constraint FK_USERFRIEND_USER2 foreign key(friend) references UserInfo(username)
go
create table FriendRequest(
	initiator USERNAME_TYPE,
	target USERNAME_TYPE,
	date DateTime,
	primary key(initiator, target)
)
go
alter table FriendRequest
add constraint FK_FR_USER1 foreign key(initiator) references UserInfo(username),
	constraint FK_FR_USER2 foreign key(target) references UserInfo(username),
	constraint FK_CHECK_VALID CHECK(initiator <> target)
	
go


create table GroupChat(
	id GROUP_ID_TYPE,
	name varchar(50) not null,
	date datetime not null,
	primary key(id),
)
go
create table GroupChatMember(
	group_id GROUP_ID_TYPE,
	username USERNAME_TYPE,
	joined_date datetime not null,
	is_admin bit not null,
	start_history_msg int not null,
	last_read_msg int not null,
	primary key(group_id, username)
)
go
alter table GroupChatMember
add constraint FK_GCM_GROUPCHAT foreign key(group_id) references GroupChat(id),
	constraint FK_GCM_USER foreign key(username) references UserInfo(username),
	constraint FK_MSG_HISTORY_VALID CHECK(last_read_msg >= start_history_msg)
go
create table GroupChatMessage(
	id int,
	group_id GROUP_ID_TYPE,
	sender USERNAME_TYPE,
	sent_date datetime not null,
	cipher_msg varbinary(MAX) not null,
	primary key(id, group_id, sender)
)
go
alter table GroupChatMessage
add constraint FK_GCMSG_GROUPCHAT foreign key(group_id) references GroupChat(id),
	constraint FK_GCMSG_USER foreign key(sender) references UserInfo(username)
	
go
create table FriendChat(
	id int,
	sender USERNAME_TYPE,
	friend USERNAME_TYPE,
	sent_date datetime not null,
	msg varbinary(MAX) not null,
	primary key(id, sender, friend)
)
go
alter table FriendChat
add constraint FK_FC_USER1 foreign key(sender) references UserInfo(username),
	constraint FK_FC_USER2 foreign key(friend) references UserInfo(username),
	constraint FC_VALID CHECK(sender <> friend)

go
create table SpamReport(
	reporter USERNAME_TYPE,
	target USERNAME_TYPE,
	reason varchar(500) not null,
	date datetime not null,
	primary key(reporter, date)
)
go
alter table SpamReport
add constraint VALID CHECK(target <> reporter),
	constraint FK_SPAMREPORT_USER1 foreign key(reporter) references UserInfo(username),
	constraint FK_SPAMREPORT_USER2 foreign key(target) references UserInfo(username)

go

create table UserIdentity(
    username USERNAME_TYPE,
	public_key varbinary(2048) not null,
	primary key(username)
)
go
alter table UserIdentity
add constraint FK_ID_USER foreign key(username) references UserInfo(username)
go

create table UserChatSecret(
    username USERNAME_TYPE,
    target USERNAME_TYPE,
    aes_params varbinary(300) not null,
    primary key(username, target)
)
go
alter table UserChatSecret
add constraint FK_UCS_USER1 foreign key(username) references UserInfo(username),
    constraint FK_UCS_USER2 foreign key(target) references UserInfo(username)
go

create table GroupChatSecret(
    username USERNAME_TYPE,
    target GROUP_ID_TYPE,
    aes_params varbinary(300) not null,
    primary key(username, target)
)
go
alter table GroupChatSecret
add constraint FK_GCS_USER foreign key(username) references UserInfo(username),
    constraint FK_UCS_GROUP foreign key(target) references GroupChat(id)
go

CREATE FUNCTION list_friends_info(@usr USERNAME_TYPE)
RETURNS TABLE AS
RETURN
	SELECT uf.*
	FROM UserFriend
	JOIN UserInfo uf ON uf.username = UserFriend.friend
	WHERE UserFriend.username = @usr
go
CREATE PROCEDURE add_msg_to_group
	@group_id GROUP_ID_TYPE,
	@sender USERNAME_TYPE,
	@sent_date datetime,
	@cipher_msg varbinary(MAX)
AS
	declare @id int
	select @id = max(id) from GroupChatMessage where group_id = @group_id
	if @id is null
	begin
		set @id = 0;
	end
	set @id = @id + 1
   	insert into GroupChatMessage VALUES(@id, @group_id, @sender, @sent_date, @cipher_msg)
   	update GroupChatMember set last_read_msg = last_read_msg + 1 where group_id = @group_id and username = @sender
GO
CREATE PROCEDURE add_member_to_group 
    @g_id GROUP_ID_TYPE,   
	@username USERNAME_TYPE,
	@joined_date datetime,
	@is_admin bit
AS   
   	insert into GroupChatMember(group_id, username, joined_date, is_admin, start_history_msg, last_read_msg)
   	values(@g_id, @username, @joined_date, @is_admin, 0, 0)
GO
CREATE FUNCTION list_group_of_user(@usr USERNAME_TYPE)
RETURNS TABLE AS
RETURN
	select gc.*
	from GroupChat gc JOIN GroupChatMember gcm on gc.id = gcm.group_id
	where gcm.username = @usr
go
CREATE FUNCTION get_all_group_msg(@username USERNAME_TYPE, @group_id GROUP_ID_TYPE) 
RETURNS TABLE AS
RETURN
	select gcmsg.*
	from GroupChatMessage gcmsg
	join GroupChatMember gcm on gcm.group_id = gcmsg.group_id and
		  gcm.username = @username and gcm.group_id = @group_id
	where gcmsg.id >= gcm.start_history_msg
go
CREATE FUNCTION get_unread_group_msg(@username USERNAME_TYPE, @group_id GROUP_ID_TYPE) 
RETURNS TABLE AS
RETURN
	select gcmsg.*
	from GroupChatMessage gcmsg
	join GroupChatMember gcm on gcm.group_id = gcmsg.group_id and
		  gcm.username = @username and gcm.group_id = @group_id
	where gcmsg.id > gcm.last_read_msg
go
CREATE PROCEDURE update_group_chat_last_read
	@username USERNAME_TYPE,
	@group_id GROUP_ID_TYPE
AS BEGIN
	declare @cur int = 0
	select @cur = max(id) from GroupChatMessage where group_id = @group_id
	if @cur is null
	begin
		set @cur = 0
	end
	update GroupChatMember set last_read_msg = @cur where username = @username and group_id = @group_id
END
go
CREATE PROCEDURE update_friend_chat_last_read
	@username USERNAME_TYPE,
	@friend USERNAME_TYPE
AS BEGIN
	declare @cur int = 0
	select @cur = max(id) from FriendChat
	where (sender = @username and friend = @friend) or (sender = @friend and friend = @username) 
	if @cur is null
	begin
		set @cur = 0
	end
	update UserFriend set last_read_msg = @cur where username = @username and friend = @friend
END
go
CREATE FUNCTION get_unread_friend_msg(@username USERNAME_TYPE, @friend USERNAME_TYPE) 
RETURNS TABLE AS
RETURN
	select fc.*
	from FriendChat fc
	join UserFriend uf on uf.username = @username and uf.friend = @friend
	where fc.sender = @friend and fc.friend = @username and fc.id > uf.last_read_msg
go
CREATE FUNCTION get_all_friend_msg(@username USERNAME_TYPE, @friend USERNAME_TYPE) 
RETURNS TABLE AS
RETURN
	select fc.*
	from FriendChat fc
	join UserFriend uf on uf.username = @username and uf.friend = @friend
                          or uf.username = @friend and uf.friend = @username
	where fc.id >= uf.start_history_msg
go
CREATE PROCEDURE add_msg_to_friend
	@sender USERNAME_TYPE,
	@friend USERNAME_TYPE,
	@sent_date datetime,
	@cipher_msg varbinary(MAX)
AS
	declare @id int
	select @id = max(id) from FriendChat
	where (sender = @sender and friend = @friend) or (sender = @friend and friend = @sender)
	if @id is null
	begin
		set @id = 0;
	end
	set @id = @id + 1
   	insert into FriendChat VALUES(@id, @sender, @friend, @sent_date, @cipher_msg)
   	update UserFriend set last_read_msg = last_read_msg + 1 where username = @sender and friend = @friend
GO
CREATE PROCEDURE update_identity
	@username USERNAME_TYPE,
	@public_key varbinary(300)
AS BEGIN
    if not exists(select * from UserIdentity where username = @username) begin
        insert into UserIdentity(username, public_key) values(@username, @public_key)
    end
END
