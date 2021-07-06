create
    definer = root@localhost procedure Main_Query()
begin
	select Mid,Uname,Uimgurl,Mtitle,Mtime from Message join Users 
    on Message.Uaccount = Users.Uaccount order by Mid desc;
end;

