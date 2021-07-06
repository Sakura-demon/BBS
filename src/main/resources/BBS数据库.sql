create database BBs default character set utf8;
use BBs;
create table Users(
	Uaccount int auto_increment not null primary key,
    Uname varchar(20) unique,
    Upassword varchar(20) not null,
    Uimgurl varchar(100)
);
create table Admin(
	Apassword char(12) not null primary key
);
create table Message(
	Mid int auto_increment primary key,
	Uaccount int,
    Mtitle varchar(100),
    Mtime date,
    Mmessage varchar(1000),
    constraint foreign key(Uaccount) references Users(Uaccount) on update cascade
);
create table Back(
	Bid int auto_increment primary key,
	Mid int,
	Uaccount int,
    Btime date,
    Bmessage varchar(1000),
    constraint foreign key(Mid) references Message(Mid) on update cascade,
    constraint foreign key(Uaccount) references Users(Uaccount) on update cascade
);
insert into Admin(Apassword) values('Admin1234567');
DELIMITER \\
create procedure Admin_Signin(in Apassword char(12),out flag int)
begin
	if exists(select * from Admin where Admin.Apassword = Apassword)
		then set flag = 1;
	else
		set flag = 0;
	end if;
end
\\
DELIMITER \\
create procedure User_Signin(in Uname char(12),in Upassword varchar(20),out flag int)
begin
	if exists(select * from Users where Users.Uname = Uname and Users.Upassword = Upassword)
		then set flag = 1;
        select Uaccount from Users where Users.Uname = Uname and Users.Upassword = Upassword;
	else
		set flag = 0;
	end if;
end
\\
DELIMITER \\
create procedure User_Signup(in Uname char(12),in Upassword varchar(20),in Uimgurl varchar(100),out flag int)
begin
	if exists(select * from Users where Users.Uname = Uname and Users.Upassword = Upassword)
		then set flag = 0;
	else
		insert into users(Uname,Upassword,Uimgurl) values(Uname,Upassword,Uimgurl);
		set flag = 1;
        select Uaccount from Users where Users.Uname = Uname and Users.Upassword = Upassword;
	end if;
end
\\
DELIMITER \\
create procedure User_img(in Uaccount int)
begin
	select Uimgurl from Users where Users.Uaccount = Uaccount;
end
\\
DELIMITER \\
create procedure Main_Query()
begin
	select Mid,Uname,Uimgurl,Mtitle,Mtime from Message join Users 
    on Message.Uaccount = Users.Uaccount order by Mid desc;
end
\\
DELIMITER \\
create procedure Main_Mid(in Mid int)
begin
	select Mhtmlurl from Message where Message.Mid = Mid;
end
\\
DELIMITER \\
create procedure Main_Delete(in Mid int,out flag int)
begin
	delete from Back where Back.Mid = Mid;
	delete from Message where Message.Mid = Mid;
    set flag = 1;
end
\\
DELIMITER \\
create procedure Main_Publish(in Mtitle varchar(100) ,in Mmessage varchar(1000),in Uaccount int,out flag int)
begin
	insert into Message(Uaccount,Mtitle,Mtime,Mmessage) values(Uaccount,Mtitle,curdate(),Mmessage);
    set flag = 1;
end
\\
DELIMITER \\
create procedure User_imgUpdate(in Uaccount int,in Uimgurl varchar(100),out flag int)
begin
	update Users set Users.Uimgurl = Uimgurl where Users.Uaccount = Uaccount;
    set flag = 1;
end
\\
DELIMITER \\
create procedure User_nameUpdate(in Uaccount int,in Uname varchar(20),out flag int)
begin
	update Users set Users.Uname = Uname where Users.Uaccount = Uaccount;
    set flag = 1;
end
\\
DELIMITER \\
create procedure User_passwordUpdate(in Uaccount int,in Upassword varchar(20),out flag int)
begin
	update Users set Users.Upassword = Upassword where Users.Uaccount = Uaccount;
    set flag = 1;
end
\\
DELIMITER \\
create procedure User_Info(in Uaccount int)
begin
	select Uname,Uimgurl from Users where Users.Uaccount = Uaccount;
end
\\
DELIMITER \\
/*
create procedure User_pastMessageQuery(in Uaccount int)
begin
	select Mid,Uname,Uimgurl,Mtitle,Mtime from Users 
    join Message on Message.Uaccount = Users.Uaccount
    where Users.Uaccount = Uaccount;
end
\\
DELIMITER \\
create procedure User_pastBackQuery(in Uaccount int)
begin
	select Message.Mid,Uname,Uimgurl,Mtitle,Mtime,Bid,Bmessage,Btime from Users 
    join Message on Message.Uaccount = Users.Uaccount
    join Back on Back.Mid = Message.Mid
    where Users.Uaccount = Uaccount;
end
\\
*/
DELIMITER \\
create procedure User_pastBackDelete(in Bid int,out flag int)
begin
	delete from Back where Back.Bid = Bid;
    set flag = 1;
end
\\
DELIMITER \\
create procedure Message_Query(in Mid int)
begin
	select Mtitle,Mtime,Uimgurl,Uname,Mmessage from Message
    join Users on Message.Uaccount = Users.Uaccount
    where Message.Mid = Mid;
end
\\
DELIMITER \\
create procedure Back_Query(in Mid int)
begin
	select Bid,Uname,Uimgurl,Btime,Bmessage from Back
    join Users on Back.Uaccount = Users.Uaccount
    where Back.Mid = Mid;
end
\\
DELIMITER \\
create procedure Back_Publish(in Mid int,in Uaccount int,in Bmessage varchar(1000),out flag int)
begin
	insert into back(Mid,Uaccount, Btime, Bmessage) values(Mid,Uaccount,curdate(),Bmessage);
    set flag = 1;
end
\\