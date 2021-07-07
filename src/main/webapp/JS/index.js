// JavaScript Document
/*
	点击管理员，刷新出现
		input（type=password 默认灰色字体口令）
		submit
			点击使用ajax向Admin_Signin后台Servlet传输管理员口令
			请求成功后判断后台返回信息
			如果为1表示验证成功，跳转到主页
			如果为-1表示输入的口令不符合规范，提示输入的口令不符合规范
			如果都不是表示输入的口令错误，提示输入的口令错误
		button（点击返回选择管理员登录或用户登录)
	点击用户，刷新出现
		input（type=text 默认灰色字体账号）
		input（type=password 默认灰色字体密码）
		submit
			点击使用ajax向User_Signin后台Servlet传输用户名和密码
			请求成功后判断后台返回信息
			如果为1表示验证成功，跳转到主页
			如果为-1表示输入的用户名不符合规范，提示输入的用户名不符合规范
			如果都不是表示输入的用户名或密码错误，提示输入的用户名或密码错误
		button（点击返回选择管理员登录或用户登录)
		a“注册”，点击刷新出现
			h1“Sign up”
			input（type=text 默认灰色字体账号）
			p（提示输入规范的用户名）
			input（type=password 默认灰色字体密码）
			submit
				点击使用ajax向User_Signup后台Servlet传输用户名和密码
				请求成功后判断后台返回信息
				如果为1表示注册成功
					h1（提示注册成功）
					p（提示点击下面按钮进入主页）
					button（点击跳转主页）
				如果为-1表示输入的用户名不符合规范，提示输入的用户名不符合规范
				如果都不是表示输入的用户名已存在，提示输入的用户名已存在
*/
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