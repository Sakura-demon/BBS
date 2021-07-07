// JavaScript Document
/*
	进入个人页面就使用ajax向User_Info后台Servlet请求获取该用户的个人图片，名称，账号
    用户图像
    用户名称    右边一个铅笔图标，点击出现一个文本框和一个确认按钮，直接修改用户名称，点击确认发送用户名称信息给C模块
    用户账号
    用户密码，不显示    右边一个铅笔图标，点击出现一个文本框和一个确认按钮，直接修改用户密码，点击确认发送用户密码信息给C模块
    过去留言按钮    点击跳转到用户过去留言页面，使用ajax传输status = 0和flag = 0让后台记录选择了该按钮
    过去回复按钮    点击跳转到用户过去留言页面，使用ajax传输status = 0和flag = 1让后台记录选择了该按钮
    退出用户按钮    点击跳转到登录页面
*/
function message(){
	$.ajax({
			url:"User_messageOrback",
			type:"post",
			data:{
				flag:0,
				status:0
			},
			datatype:"json",
			error:function(error){
				alert(error+"请求失败");
			},
			success:function(result){
				window.location.href = "UserPast.html";
			}
	})
}
function Back(){
	$.ajax({
			url:"User_messageOrback",
			type:"post",
			data:{
				flag:1,
				status:0
			},
			datatype:"json",
			error:function(error){
				alert(error+"请求失败");
			},
			success:function(result){
				window.location.href = "UserPast.html";
			}
	})
}
function Uname(){
	var Unamebox = document.getElementById("Unamebox");
	Unamebox.innerHTML = '';
	Unamebox.innerHTML +=
		'<input type="text" id="Uname" style="width:100%">'+
		'<input type="submit" id="submit">'
	$("#submit").click(function(){
		$.ajax({
			url:"User_nameUpdate",
			type:"post",
			data:{
				Uname:$("#Uname").val()
			},
			datatype:"json",
			error:function(error){
				alert(error+"请求失败");
			},
			success:function(result){
				var obj = JSON.parse(result);
				var flag = obj.flag;
				if(flag == 0){
					alert("修改失败");
				}
				window.location.reload();
			}
		})
	})
}
function Upassword(){
	var Upasswordbox = document.getElementById("Upasswordbox");
	Upasswordbox.innerHTML = '';
	Upasswordbox.innerHTML +=
		'<input type="text" id="Upassword" style="width:100%">'+
		'<input type="submit" id="submit">'
	$("#submit").click(function(){
		$.ajax({
			url:"User_passwordUpdate",
			type:"post",
			data:{
				Upassword:$("#Upassword").val()
			},
			datatype:"json",
			error:function(error){
				alert(error+"请求失败");
			},
			success:function(result){
				var obj = JSON.parse(result);
				var flag = obj.flag;
				if(flag == 0){
					alert("修改失败");		
				}
				window.location.reload();
			}
		})
	})
}
function Signout(){
	window.location.href = "index.html";
}
$(function(){
	$.ajax({
		url:"User_img",
		type:"post",
		data:{},
		datatype:"json",
		error:function(error){
			alert(error+"请求失败");
		},
		success:function(result){
			var Uimgurl = JSON.parse(result).Uimgurl;
			$("#UserImg").attr({src:Uimgurl});
		}
	})
	$.ajax({
		url:"User_Info",
		type:"post",
		data:{},
		datatype:"json",
		error:function(error){
			alert(error+"请求失败");
		},
		success:function(result){
			var obj = JSON.parse(result);
			$("#userImg").attr({"src":obj.Uimgurl});
			var Uname = document.getElementById("Uname");
			Uname.innerHTML += obj.Uname;
			var Uaccount = document.getElementById("Uaccount");
			Uaccount.innerHTML += obj.Uaccount
		}
	})
})