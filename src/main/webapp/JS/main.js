// JavaScript Document
/*
    进入主页时使用ajax向AdminOrUser后台Servlet请求判断是管理员还是用户
    如果返回0，管理员
    如果返回1，用户
    管理员
    div
        div
            a
                img主页图片，点击跳转主页
                BBS主页
        div
            button（退出，点击跳转到登录页面）
    div（使用ajax向Main_Query后台Servlet发送-5获得留言总条数，以每页10条计算出总页数，使用ajax向Main_Query后台Servlet发送当前页数获取数据，然后刷新生成发表留言框和过去留言框）
        发表留言框，留言主题和内容，两个文本域和提交按钮
        过去留言框
            div
                div
                    a（使用JQ循环设置相应数据的留言号，点击使用ajax向Main_Mid后台Servlet传输留言号，并跳转留言页面）
                        div
                            p用户名
                            img用户图像 
                        div
                            h1留言标题
                            p留言时间
                div
                    button（删除，点击删除该条留言）
    用户
    div
        div
            a
                img主页图片，点击跳转主页
                BBS主页
        div
            a
                img个人图片，点击跳转用户页面
    div（使用ajax向Main_Query后台Servlet发送-5获得留言总条数，以每页10条计算出总页数，使用ajax向Main_Query后台Servlet发送当前页数获取数据，然后刷新生成发表留言框和过去留言框）
        发表留言框，留言主题和内容，两个文本域和提交按钮
        过去留言框
            div
                div
                    a（使用JQ循环设置相应数据的留言号，点击使用ajax向Main_Mid后台Servlet传输留言号，并跳转留言页面）
                        div
                            p用户名
                            img用户图像 
                        div
                            h1留言标题
                            p留言时间
    统一分页方法（使用ajax向Main_Query后台Servlet发送-6获得当前页数）
    div
        ul
            li（当前页数等于1时不显示首页和上一页，当前页数等于尾页时不显示尾页和下一页，按照前面获得计算出的总页数循环生成相应页码）
                a（点击使用ajax向Main_Query后台Servlet发送当前页数获取数据，然后刷新生成发表留言框和过去留言框，-2表示首页/第一页，-4表示上一页，-2~正无穷表示页码，-3表示下一页，-2~正无穷表示页码）
*/
function Signout(){
	window.location.href = "index.html";
}
function submit(){
	$.ajax({
		url:"Main_Publish",
		type:"post",
		data:{
			Mtitle:$("#Mtitle").val(),
			Mmessage:$("#Mmessage").val()
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
				alert("发表失败");
			}
		}
	})
}
function deletemessage(Mid){
	$.ajax({
		url:"Main_Delete",
		type:"post",
		data:{
			Mid:Mid
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
				alert("删除失败");
			}
		}
	})
}
function message(Mid){
	$.ajax({
		url:"Main_Mid",
		type:"post",
		data:{
			Mid:Mid
		},
		datatype:"json",
		error:function(error){
			alert(error+"请求失败");
		},
		success:function(){
			window.location.href = "Message.html";
		}
	})
}
function bottomAdmin(pageNum){
	$.ajax({
		url:"Main_Query",
		type:"post",
		data:{
			Page:-6
		},
		datatype:"json",
		error:function(error){
			alert(error+"请求失败");
		},
		success:function(result){
			var obj = JSON.parse(result);
			var page = obj[0].curPage;
			var ul = document.getElementById("ul");
			ul.innerHTML = '';
			if(page != 1){
			ul.innerHTML +=
				'<li class = "li"><a class = "lia" href="javascript:showpageAdmin(-2,'+pageNum+')">首页</a></li>'+
				'<li class = "li"><a class = "lia" href="javascript:showpageAdmin(-4,'+pageNum+')">上一页</a></li>'
			}
			for(var i = 0;i < pageNum;i++){
				if(i+1 == page){
					ul.innerHTML +=
						'<li class = "li"><a class = "lia" style="background: #30A5DA;" href="javascript:showpageAdmin('+(i-2)+','+pageNum+')">'+	(i+1)+'</a></li>'
				}else{
					ul.innerHTML +=
						'<li class = "li"><a class = "lia" href="javascript:showpageAdmin('+(i-2)+','+pageNum+')">'+(i+1)+'</a></li>'
				}
			}
			if(page != pageNum){
				ul.innerHTML +=
					'<li class = "li"><a class = "lia" href="javascript:showpageAdmin(-3,'+pageNum+')">下一页</a></li>'+
					'<li class = "li"><a class = "lia" href="javascript:showpageAdmin('+(pageNum-3)+','+pageNum+')">尾页</a></li>'
			}
		}
	})
}
function bottomUser(pageNum){
	$.ajax({
		url:"Main_Query",
		type:"post",
		data:{
			Page:-6
		},
		datatype:"json",
		error:function(error){
			alert(error+"请求失败");
		},
		success:function(result){
			var obj = JSON.parse(result);
			var page = obj[0].curPage;
			var ul = document.getElementById("ul");
			ul.innerHTML = '';
			if(page != 1){
			ul.innerHTML +=
				'<li class = "li"><a class = "lia" href="javascript:showpageUser(-2,'+pageNum+')">首页</a></li>'+
				'<li class = "li"><a class = "lia" href="javascript:showpageUser(-4,'+pageNum+')">上一页</a></li>'
			}
			for(var i = 0;i < pageNum;i++){
				if(i+1 == page){
					ul.innerHTML +=
						'<li class = "li"><a class = "lia" style="background: #30A5DA;" href="javascript:showpageUser('+(i-2)+','+pageNum+')">'+(i+1)+'</a></li>'
				}else{
					ul.innerHTML +=
						'<li class = "li"><a class = "lia" href="javascript:showpageUser('+(i-2)+','+pageNum+')">'+(i+1)+'</a></li>'
				}
			}
			if(page != pageNum){
				ul.innerHTML +=
					'<li class = "li"><a class = "lia" href="javascript:showpageUser(-3,'+pageNum+')">下一页</a></li>'+
					'<li class = "li"><a class = "lia" href="javascript:showpageUser('+(pageNum-3)+','+pageNum+')">尾页</a></li>'
			}
		}
	})
}
function showpageAdmin(page,pageNum){	
	$.ajax({
		url:"Main_Query",
		type:"post",
		data:{
			Page:page
		},
		datatype:"json",
		error:function(error){
			alert(error+"请求失败");
		},
		success:function(result){
			var obj = JSON.parse(result);
			var box = document.getElementById("box");
			box.innerHTML = '';
			$.each(obj,function(index,value){
				box.innerHTML += 
					'<div class = "Mid">'+
						'<div style="width:70%">'+
							'<a class = "a" href="javascript:message('+value.Mid+')">'+
								'<div style="width:50%">'+
									'<p>'+value.Uname+'</p>'+
									'<img alt="用户图像" src="'+value.Uimgurl+'">'+
								'</div>'+
								'<div style="width:50%">'+
									'<h1>'+value.Mtitle+'</h1>'+
									'<p>'+value.Mtime+'</p>'+
								'</div>'+
							'</a>'+
						'</div>'+
						'<div style = "width:30%">'+
							'<button class="messagebtn" onClick="deletemessage('+value.Mid+')">删除</button>'+
						'</div>'+
					'</div>'
			})
		bottomAdmin(pageNum);
		}
	})
}
function showpageUser(page,pageNum){
	$.ajax({
		url:"Main_Query",
		type:"post",
		data:{
			Page:page
		},
		datatype:"json",
		error:function(error){
			alert(error+"请求失败");
		},
		success:function(result){
			var obj = JSON.parse(result);
			var box = document.getElementById("box");
			box.innerHTML = '';
			box.innerHTML +=
				'<div style="display: flex;justify-content: space-around;">'+
					'<div style="width: 70%">'+
						'<input id="Mtitle" type="text" style="width: 99%;height: 20%;border-width: 1px" placeholder="留言标题">'+
						'<textarea id="Mmessage" style="width: 99%;height: 70%;resize: none" placeholder="留言内容"></textarea>'+
					'</div>'+
					'<div style="width: 30%">'+
						'<input type="submit" value="发表" id = "submit" onClick="submit()">'+
					'</div>'+
				'</div>'
			$.each(obj,function(index,value){
				box.innerHTML += 
					'<div class = "Mid">'+
						'<div style="width:100%">'+
							'<a class = "a" href="javascript:message('+value.Mid+')">'+
								'<div style="width:50%">'+
									'<p>'+value.Uname+'</p>'+
									'<img alt="用户图像" src="'+value.Uimgurl+'">'+
								'</div>'+
								'<div style="width:50%">'+
									'<h1>'+value.Mtitle+'</h1>'+
									'<p>'+value.Mtime+'</p>'+
								'</div>'+
							'</a>'+
						'</div>'+
					'</div>'
			})
		bottomUser(pageNum);
		}
	})
}
$(function(){
	$.ajax({
		url:"AdminOrUser",
		type:"post",
		data:{},
		datatype:"json",
		error:function(error){
			alert(error+"请求失败");
		},
		success:function(result){
			var obj = JSON.parse(result);
			if(obj.flag == 0){
				var user = document.getElementById("User");
				user.innerHTML = '';
				user.innerHTML +=
					'<button class="Signout" id="Signout" onClick="Signout()">退出</button>'
				var pageNum = 0;
				$.ajax({
					url:"Main_Query",
					type:"post",
					data:{
						Page:-5
					},
					datatype:"json",
					error:function(error){
						alert(error+"请求失败");
					},
					success:function(result){
						var obj = JSON.parse(result);
						var totalNum = obj[0].length;
						pageNum = Math.ceil(totalNum/10);
						if(pageNum == 0){
							var box = document.getElementById("box");
							box.innerHTML = '';
							box.innerHTML += 
								'<h1>暂无数据</h1>'
						}else{
							showpageAdmin(-2,pageNum);
						}
					}
				})
			}
			else{
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
				pageNum = 0;
				$.ajax({
					url:"Main_Query",
					type:"post",
					data:{
						Page:-5
					},
					datatype:"json",
					error:function(error){
						alert(error+"请求失败");
					},
					success:function(result){
						var obj = JSON.parse(result);
						var totalNum = obj[0].length;
						pageNum = Math.ceil(totalNum/10);
						if(pageNum == 0){
							var box = document.getElementById("box");
							box.innerHTML = '';
							box.innerHTML +=
								'<div style="display: flex;justify-content: space-around;">'+
									'<div style="width: 70%">'+
										'<input id="Mtitle" type="text" style="width: 99%;height: 20%;border-width: 1px" placeholder="留言标题">'+
										'<textarea id="Mmessage" style="width: 99%;height: 70%;resize: none" placeholder="留言内容"></textarea>'+
									'</div>'+
									'<div style="width: 30%">'+
										'<input type="submit" value="发表" id = "submit" onClick="submit()">'+
									'</div>'+
								'</div>'
							box.innerHTML += 
								'<h1>暂无数据</h1>'
						}else{
							showpageUser(-2,pageNum);
						}
					}
				})
			}
		}
	})
})