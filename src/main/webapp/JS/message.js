// JavaScript Document
function back(){
	$.ajax({
		url:"Message_Back",
		type:"post",
		data:{},
		datatype:"json",
		error:function(error){
			alert(error+"请求失败");
		},
		success:function(){
			window.location.href = "Main.html";
		}
	})
}
function submit(){
	$.ajax({
		url:"Back_Publish",
		type:"post",
		data:{
			Bmessage:$("#Bmessage").val()
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
function deleteback(Bid){
	$.ajax({
		url:"User_pastBackDelete",
		type:"post",
		data:{
			Bid:Bid
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
function showBox(obj){
	var box = document.getElementById("box");
	box.innerHTML = '';
	box.innerHTML +=
		'<div class="TitleBox">'+
			'<h1 id="Mtitle">'+obj.Mtitle+'</h1>'+
				'<div>'+
					'<br><br>'+
					'<p id="Mtime">'+obj.Mtime+'</p>'+
				'</div>'+
		'</div>'+
		'<div class="ContentBox">'+
			'<div style="width:10%">'+
				'<p id="Uname">'+obj.Uname+'</p>'+
				'<img alt="用户图像" id="Uimg" src="'+obj.Uimgurl+'">'+
			'</div>'+
			'<div style="width:10%">'+
			'</div>'+
			'<div style="width:80%">'+
				'<p id="Mmessage">'+obj.Mmessage+'</p>'+
			'</div>'+
		'</div>'
}
function showMiddleAdmin(page){
	$.ajax({
		url:"Message_Query",
		type:"post",
		data:{
			flag:1,
			Page:page
		},
		datatype:"json",
		error:function(error){
			alert(error+"请求失败");
		},
		success:function(result){
			var obj = JSON.parse(result);
			var middle = document.getElementById("middle");
			middle.innerHTML = '';
			$.each(obj,function(index,value){
				middle.innerHTML += 
					'<div class="Bid">'+
						'<div style="width:35%">'+
							'<p id="backUname">'+value.Uname+'</p>'+
							'<img alt="回复用户图像" id="backUimg" src="'+value.Uimgurl+'">'+
						'</div>'+
						'<div style="width:35%">'+
							'<p id="Btime">'+value.Btime+'</p>'+
							'<p id="Bmessage">'+value.Bmessage+'</p>'+
						'</div>'+
						'<div style="width:30%">'+
							'<button id="Bid" onClick="deleteback('+value.Bid+')">删除</button>'+
						'</div>'+
					'</div>'
			})
		}
	})
}
function showMiddleUser(page){
	$.ajax({
		url:"Message_Query",
		type:"post",
		data:{
			flag:1,
			Page:page
		},
		datatype:"json",
		error:function(error){
			alert(error+"请求失败");
		},
		success:function(result){
			var obj = JSON.parse(result);
			console.log(obj);
			var middle = document.getElementById("middle");
			middle.innerHTML = '';
			middle.innerHTML +=
				'<div style="display: flex;justify-content: space-around;">'+
					'<div style="width: 70%">'+
						'<textarea id="Bmessage" style="width: 99%;height: 100%;resize: none;padding: 0px;    border-width: 0px;" placeholder="回复内容"></textarea>'+
					'</div>'+
					'<div style="width: 30%">'+
						'<input type="submit" value="发表" id = "submit" onClick="submit()" style="padding: 0px;border-width: 0px;">'+
					'</div>'+
				'</div>'
			$.each(obj,function(index,value){
				middle.innerHTML += 
					'<div class="Bid">'+
						'<div style="width:50%">'+
							'<p id="backUname">'+value.Uname+'</p>'+
							'<img alt="回复用户图像" id="backUimg" src="'+value.Uimgurl+'">'+
						'</div>'+
						'<div style="width:50%">'+
							'<p id="Btime">'+value.Btime+'</p>'+
							'<p id="Bmessage">'+value.Bmessage+'</p>'+
						'</div>'+
					'</div>'
			})
		}
	})
}
function showBottomAdmin(pageNum,page){
	var ul = document.getElementById("ul");
	if(page != 1 || pageNum != 1){
		ul.innerHTML +=
		'<li class = "li"><a class = "lia" href="javascript:showMiddleAdmin(-2)">首页</a></li>'+
		'<li class = "li"><a class = "lia" href="javascript:showMiddleAdmin(-4)">上一页</a></li>'
	}
	for(var i = 0;i < pageNum;i++){
		ul.innerHTML +=
			'<li class = "li"><a class = "lia" href="javascript:showMiddleAdmin('+(i-2)+')">'+(i+1)+'</a></li>'
	}
	if(page != pageNum || pageNum != 1){
		ul.innerHTML +=
		'<li class = "li"><a class = "lia" href="javascript:showMiddleAdmin(-3)">下一页</a></li>'+
		'<li class = "li"><a class = "lia" href="javascript:showMiddleAdmin('+(pageNum-3)+')">尾页</a></li>'
	}
}
function showBottomUser(pageNum,page){
	var ul = document.getElementById("ul");
	if(page != 1 || pageNum != 1){
		ul.innerHTML +=
		'<li class = "li"><a class = "lia" href="javascript:showMiddleUser(-2)">首页</a></li>'+
		'<li class = "li"><a class = "lia" href="javascript:showMiddleUser(-4)">上一页</a></li>'
	}
	for(var i = 0;i < pageNum;i++){
		ul.innerHTML +=
			'<li class = "li"><a class = "lia" href="javascript:showMiddleUser('+(i-2)+')">'+(i+1)+'</a></li>'
	}
	if(page != pageNum || pageNum != 1){
		ul.innerHTML +=
		'<li class = "li"><a class = "lia" href="javascript:showMiddleUser(-3)">下一页</a></li>'+
		'<li class = "li"><a class = "lia" href="javascript:showMiddleUser('+(pageNum-3)+')">尾页</a></li>'
	}
}
$(function(){
	$.ajax({
		url:"Message_Query",
		type:"post",
		data:{
			flag:0
		},
		datatype:"json",
		error:function(error){
			alert(error+"请求失败");
		},
		success:function(result){
			var obj = JSON.parse(result);
			showBox(obj);
		}
	})
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
				var page = -5;
				var pageNum = 0;
				$.ajax({
					url:"Message_Query",
					type:"post",
					data:{
						flag:1,
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
						page = -2;//
						showMiddleAdmin(page);
						showBottomAdmin(pageNum);
					}
				})
			}
			else{
				page = -5;
				pageNum = 0;
				$.ajax({
					url:"Main_Query",
					type:"post",
					data:{
						flag:1,
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
						showMiddleUser(page);
						showBottomUser(pageNum);
					}
				})
			}
		}
	})
})