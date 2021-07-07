-- 创建数据库并设置字符集为utf-8，使能插入中文数据
create database BBs default character set utf8;
use BBs;
/*
用户信息表  Users
账号（int 自动增长量,not null,主键）    
用户名    
密码  
用户图像路径
*/
create table Users(
	Uaccount int auto_increment not null primary key,
    Uname varchar(20) unique,
    Upassword varchar(20) not null,
    Uimgurl varchar(100)
)ENGINE = INNODB COLLATE =utf8_bin;
/*
管理员信息表    Admin   数据由人为插入
口令(char(12))
*/
create table Admin(
	Apassword char(12) not null primary key
)ENGINE = INNODB COLLATE =utf8_bin;
/*
留言信息表  Message
留言号（int,自动生成,主键）  
账号（int,外键，级联更新）    
留言标题    
留言时间（自动记录当前时间）    
留言信息
*/
create table Message(
	Mid int auto_increment primary key,
	Uaccount int,
    Mtitle varchar(100),
    Mtime date,
    Mmessage varchar(1000),
    constraint foreign key(Uaccount) references Users(Uaccount) on update cascade
)ENGINE = INNODB COLLATE =utf8_bin;
/*
回复信息表  Back
回复号（int,自动生成,主键）  
留言号（int,外键，级联更新）  
回复账号（int,外键，级联更新）   
回复时间（自动记录当前时间）    
回复信息
*/
create table Back(
	Bid int auto_increment primary key,
	Mid int,
	Uaccount int,
    Btime date,
    Bmessage varchar(1000),
    constraint foreign key(Mid) references Message(Mid) on update cascade,
    constraint foreign key(Uaccount) references Users(Uaccount) on update cascade
)ENGINE = INNODB COLLATE =utf8_bin;
insert into Admin(Apassword) values('Admin1234567');
/*
管理员登录验证功能Admin_Signin
    传入口令
    查询Admin表，看是否存在元组，满足传入的口令
        有，则放回1
        无，则返回0
*/
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
/*
用户登录验证功能User_Signin
    传入用户名密码
    查询Users表，看是否存在元组，满足传入的用户名密码
        有，则返回1，并返回账号
        无，则返回0
*/
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
/*
注册功能User_Signup
    传入用户名密码
    查询Users表，看是否存在元组，满足传入的用户名
        无，则将用户名，密码插入User表中，并放回1，并返回账号
        有，则返回0
*/
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
/*
个人图像功能User_img
    传入账号
    查询Users表，返回该账号的图像路径
*/
DELIMITER \\
create procedure User_img(in Uaccount int)
begin
	select Uimgurl from Users where Users.Uaccount = Uaccount;
end
\\
/*
主页留言查询功能Main_Query
    查询Message表和Users表的连接表，
    返回留言号，用户名，用户图像路径，留言标题，留言时间的结果集，
    按留言号降序排序
*/
DELIMITER \\
create procedure Main_Query()
begin
	select Mid,Uname,Uimgurl,Mtitle,Mtime from Message join Users 
    on Message.Uaccount = Users.Uaccount order by Mid desc;
end
\\
/*
删除留言功能Main_Delete
    传入留言号
    删除Message表中该留言号的元组
    返回1
*/
DELIMITER \\
create procedure Main_Delete(in Mid int,out flag int)
begin
	delete from Back where Back.Mid = Mid;
	delete from Message where Message.Mid = Mid;
    set flag = 1;
end
\\
/*
主页留言发表功能Main_Publish
    传入留言标题和留言信息和账号
    自动记录当前时间，自动生成留言号
    将留言号，账号，留言标题，留言时间，留言信息插入到Message表中
    返回1
*/
DELIMITER \\
create procedure Main_Publish(in Mtitle varchar(100) ,in Mmessage varchar(1000),in Uaccount int,out flag int)
begin
	insert into Message(Uaccount,Mtitle,Mtime,Mmessage) values(Uaccount,Mtitle,curdate(),Mmessage);
    set flag = 1;
end
\\
/*
用户名称修改功能User_nameUpdate
    传入账号和用户名称
    修改Users表中该账号的用户名称
    返回1
*/
DELIMITER \\
create procedure User_nameUpdate(in Uaccount int,in Uname varchar(20),out flag int)
begin
	update Users set Users.Uname = Uname where Users.Uaccount = Uaccount;
    set flag = 1;
end
\\
/*
用户密码修改功能User_passwordUpdate
    传入账号和用户密码
    修改Users表中该账号的用户密码
    返回1
*/
DELIMITER \\
create procedure User_passwordUpdate(in Uaccount int,in Upassword varchar(20),out flag int)
begin
	update Users set Users.Upassword = Upassword where Users.Uaccount = Uaccount;
    set flag = 1;
end
\\
/*
个人页面功能User_Info
    传入账号
    查询Users表
    返回该账号的用户名称，用户图像路径结果集
*/
DELIMITER \\
create procedure User_Info(in Uaccount int)
begin
	select Uname,Uimgurl from Users where Users.Uaccount = Uaccount;
end
\\
/*
个人页面过去留言查询功能User_pastMessageQuery
    传入账号
    查询Users表和Message表的连接表
    返回账号等于该账号的留言号，用户名，用户图像路径，留言标题，留言时间的结果集
*/
DELIMITER \\
create procedure User_pastMessageQuery(in Uaccount int)
begin
	select Mid,Uname,Uimgurl,Mtitle,Mtime from Users 
    join Message on Message.Uaccount = Users.Uaccount
    where Users.Uaccount = Uaccount;
end
\\
/*
个人页面过去回复查询功能User_pastBackQuery
    传入账号
    查询Back表和Users表和Message表的连接表，
    返回回复账号等于该账号的留言号，用户名，用户图像路径，留言标题，留言时间，回复号，回复信息，回复时间的结果集
*/
DELIMITER \\
create procedure User_pastBackQuery(in Uaccount int)
begin
	select Message.Mid,Uname,Uimgurl,Mtitle,Mtime,Bid,Bmessage,Btime from Users 
    join Message on Message.Uaccount = Users.Uaccount
    join Back on Back.Mid = Message.Mid
    where Users.Uaccount = Uaccount;
end
\\
/*
个人页面过去回复删除功能User_pastBackDelete
    传入回复号
    删除Back表中该回复号的信息
    返回1
*/
DELIMITER \\
create procedure User_pastBackDelete(in Bid int,out flag int)
begin
	delete from Back where Back.Bid = Bid;
    set flag = 1;
end
\\
/*
留言页面查询功能Message_Query
    传入留言号
    查询Messag表和Users表的连接表，
    返回该留言号的留言标题，留言时间，用户图像路径，用户名称，留言信息
*/
DELIMITER \\
create procedure Message_Query(in Mid int)
begin
	select Mtitle,Mtime,Uimgurl,Uname,Mmessage from Message
    join Users on Message.Uaccount = Users.Uaccount
    where Message.Mid = Mid;
end
\\
/*
留言页面回复查询功能Back_Query
    传入留言号
    查询Back表和Users表的连接表
    返回该留言号的回复号，回复号的回复账号的回复用户名称，回复用户图像路径，回复时间（自动记录当前时间），回复信息
*/
DELIMITER \\
create procedure Back_Query(in Mid int)
begin
	select Bid,Uname,Uimgurl,Btime,Bmessage from Back
    join Users on Back.Uaccount = Users.Uaccount
    where Back.Mid = Mid;
end
\\
/*
留言页面回复发表功能Back_Publish
    传入留言号、用户账号和回复信息
    自动记录当前时间，自动生成回复号
    将回复号，留言号，用户账号，回复时间，回复信息插入Back表中
    返回1
*/
DELIMITER \\
create procedure Back_Publish(in Mid int,in Uaccount int,in Bmessage varchar(1000),out flag int)
begin
	insert into back(Mid,Uaccount, Btime, Bmessage) values(Mid,Uaccount,curdate(),Bmessage);
    set flag = 1;
end
\\