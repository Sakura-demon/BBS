// JavaScript Document
/*
function userImg(){
	var Uimgurl = document.getElementById("file-input").value;
	$.ajax({
		url:"User_imgUpdate",
		type:"post",
		data:{
			Uimgurl:Uimgurl
		},
		datatype:"json",
		error:function(error){
			alert(error+"请求失败");
		},
		success:function(result){
			var obj = JSON.parse(result);
			var flag = obj.flag;
			if(flag == 1){
				window.location.reload();
			}
			else{
				alert("修改失败");
			}
		}
	})
}
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
function back(){
	window.location.href = "Main.html";
}
$(function(){
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