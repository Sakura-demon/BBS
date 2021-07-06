// JavaScript Document
$(function(){
	$("#Admin").click(function(){
		var Choice = document.getElementById("Choice");
		Choice.innerHTML = 
			'<input type="password" placeholder="口令" class="text" id="password">'+
			'<br>'+
			'<br>'+
			'<br>'+
			'<input type="submit" id="submit">'+
			'<br>'+
			'<br>'+
			'<button id="back">返回</button>';
		Choice.style.display = "block";
		$("#submit").click(function(){
			if($("#password").val() == ""){
				$("#password").attr({placeholder:"请输入12位口令",class:"error"});
			}
			else{
				$.ajax({
					url:"Admin_Signin",
					type:"post",
					data:{"Apassword":$("#password").val()},
					datatype:"json",
					error:function(error){
						alert(error,"请求失败");
					},
					success:function(result){
						var flag = JSON.parse(result).flag;
						if(flag == 1){
							window.location.href = "Main.html";
						}
						else if(flag == -1){
							$("#password").val("");
							$("#password").attr({placeholder:"请输入12位口令",class:"error"});
						}
						else{
							$("#password").val("");
							$("#password").attr({placeholder:"口令错误",class:"error"});
						}
					}
				})
			}
		})
	})
})
$(function(){
	$("#User").click(function(){
		var Choice = document.getElementById("Choice");
		Choice.innerHTML = 
			'<input type="text" placeholder="用户名" class="text" id="name">'+
			'<br>'+
			'<input type="password" placeholder="密码" class="text" id="password">'+
			'<br>'+
			'<br>'+
			'<br>'+
			'<input type="submit" id="submit">'+
			'<br>'+
			'<br>'+
			'<button id="back">返回</button>'+
			'<br>'+
			'<a id="Signup">注册</a>';
		Choice.style.display = "block";
		$("#submit").click(function(){
			if($("#name").val() == "" && $("#password").val() == ""){
				$("#name").attr({placeholder:"请输入12位的用户名",class:"error"});
				$("#password").attr({placeholder:"请输入密码",class:"error"});
			}
			else{
				$.ajax({
					url:"User_Signin",
					type:"post",
					data:{
						"Uname":$("#name").val(),
						"Upassword":$("#password").val()
					},
					datatype:"json",
					error:function(error){
						alert(error+"请求失败");
					},
					success:function(result){
						var flag = JSON.parse(result).flag;
						if(flag == 1){
							window.location.href = "Main.html";
						}
						else if(flag == -1){
							$("#name").val("");
							$("#password").val("");
							$("#name").attr({placeholder:"请输入12位的用户名",class:"error"});
						}
						else{
							$("#name").val("");
							$("#password").val("");
							$("#password").attr({placeholder:"用户名或密码错误",class:"error"});
						}
					}
				})
			}
		})
		$("#Signup").click(function(){
			var h1 = document.getElementById("h1");
			var Choicebox = document.getElementById("Choice");
			h1.innerHTML = "Sign up";
			Choicebox.innerHTML = 
				'<input type="text" placeholder="用户名" class="text" id="name">'+
				'<p style="font-size: 1px">请输入12位用户名</p>'+
				'<br>'+
				'<input type="password" placeholder="密码" class="text" id="password">'+
				'<br>'+
				'<br>'+
				'<br>'+
				'<input type="submit" id="submit">'+
				'<br>'+
				'<br>'+
				'<button id="back">返回</button>'
			Choicebox.style.display = "block";
			$("#submit").click(function(){
				if($("#name").val() == "" && $("#password").val() == ""){
					$("#name").attr({placeholder:"请输入12位的用户名",class:"error"});
					$("#password").attr({placeholder:"请输入密码",class:"error"});
				}
				else{
					$.ajax({
						url:"User_Signup",
						type:"post",
						data:{
							"Upname":$("#name").val(),
							"Uppassword":$("#password").val()
						},
						datatype:"json",
						error:function(error){
							alert(error+"请求失败");
						},
						success:function(result){
							var flag = JSON.parse(result).flag;
							if(flag == 1){
								Choicebox.innerHTML = 
									'<h1>注册成功</h1>'+
									'<p>点击下面按钮进入主页</p>'+
									'<button id = "Main">进入</button>';
								$("#Main").click(function(){
									window.location.href = "Main.html";
								})
							}
							else if(flag == -1){
								$("#name").val("");
								$("#password").val("");
								$("#name").attr({placeholder:"请输入12位的用户名",class:"error"});
							}
							else{
								$("#name").val("");
								$("#password").val("");
								$("#password").attr({placeholder:"该用户名已存在",class:"error"});
							}
						}
					})
				}
			})
		})
	})
})
$(function(){
	$("#Choice").on("click","#back",function(){
		window.location.reload();
	})
})