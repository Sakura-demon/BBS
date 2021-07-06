// JavaScript Document
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
			console.log(obj);
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
function bottomAdmin(pageNum,page){
	var ul = document.getElementById("ul");
	if(page != 1 || pageNum != 1){
		ul.innerHTML +=
		'<li class = "li"><a class = "lia" href="javascript:showpageAdmin(-2)">首页</a></li>'+
		'<li class = "li"><a class = "lia" href="javascript:showpageAdmin(-4)">上一页</a></li>'
	}
	for(var i = 0;i < pageNum;i++){
		ul.innerHTML +=
			'<li class = "li"><a class = "lia" href="javascript:showpageAdmin('+(i-2)+')">'+(i+1)+'</a></li>'
	}
	if(page != pageNum || pageNum != 1){
		ul.innerHTML +=
		'<li class = "li"><a class = "lia" href="javascript:showpageAdmin(-3)">下一页</a></li>'+
		'<li class = "li"><a class = "lia" href="javascript:showpageAdmin('+(pageNum-3)+')">尾页</a></li>'
	}
}
function bpttomUser(pageNum,page){
	var ul = document.getElementById("ul");
	if(page != 1 || pageNum != 1){
		ul.innerHTML +=
		'<li class = "li"><a class = "lia" href="javascript:showpageUser(-2)">首页</a></li>'+
		'<li class = "li"><a class = "lia" href="javascript:showpageUser(-4)">上一页</a></li>'
	}
	for(var i = 0;i < pageNum;i++){
		ul.innerHTML +=
			'<li class = "li"><a class = "lia" href="javascript:showpageUser('+(i-2)+')">'+(i+1)+'</a></li>'
	}
	if(page != pageNum || pageNum != 1){
		ul.innerHTML +=
		'<li class = "li"><a class = "lia" href="javascript:showpageUser(-3)">下一页</a></li>'+
		'<li class = "li"><a class = "lia" href="javascript:showpageUser('+(pageNum-3)+')">尾页</a></li>'
	}
}
function showpageAdmin(page){
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
								'<div>'+
									'<p>'+value.Uname+'</p>'+
									'<img alt="用户图像" src="'+value.Uimgurl+'">'+
								'</div>'+
								'<div>'+
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
		}
	})
}
function showpageUser(page){
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
				var page = -5;
				var pageNum = 0;
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
						var totalNum = obj[0].length;
						pageNum = Math.ceil(totalNum/10);
						page = -2;
						showpageAdmin(page);
						bottomAdmin(pageNum);
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
				page = -5;
				pageNum = 0;
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
						var totalNum = obj[0].length;
						pageNum = Math.ceil(totalNum/10);
						page = -2;
						showpageUser(page);
						bpttomUser(pageNum);
					}
				})
			}
		}
	})
})