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
function bottomMessage(pageNum){
	$.ajax({
		url:"User_pastMessageQuery",
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
			if(page != 1 || pageNum != 1){
			ul.innerHTML +=
				'<li class = "li"><a class = "lia" href="javascript:showpageMessage(-2,'+pageNum+')">首页</a></li>'+
				'<li class = "li"><a class = "lia" href="javascript:showpageMessage(-4,'+pageNum+')">上一页</a></li>'
			}
			for(var i = 0;i < pageNum;i++){
				if(i+1 == page){
					ul.innerHTML +=
						'<li class = "li"><a class = "lia" style="background: #30A5DA;" href="javascript:showpageMessage('+(i-2)+','+pageNum+')">'+	(i+1)+'</a></li>'
				}else{
					ul.innerHTML +=
						'<li class = "li"><a class = "lia" href="javascript:showpageMessage('+(i-2)+','+pageNum+')">'+(i+1)+'</a></li>'
				}
			}
			if(page != pageNum || pageNum != 1){
				ul.innerHTML +=
					'<li class = "li"><a class = "lia" href="javascript:showpageMessage(-3,'+pageNum+')">下一页</a></li>'+
					'<li class = "li"><a class = "lia" href="javascript:showpageMessage('+(pageNum-3)+','+pageNum+')">尾页</a></li>'
			}
		}
	})
}
function showpageMessage(page,pageNum){	
	$.ajax({
		url:"User_pastMessageQuery",
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
		bottomMessage(pageNum);
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
function showMiddleBack(page,pageNum){
	$.ajax({
		url:"User_pastBackQuery",
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
			var box = document.getElementById("box");
			box.innerHTML = '';
			$.each(obj,function(index,value){
				
				box.innerHTML += 
					'<div class="Bid">'+
						'<a class = "a" href="javascript:message('+value.Mid+')" style="width:90%">'+
							'<div style="width:30%">'+
								'<p id="backUname">'+value.Uname+'</p>'+
								'<img alt="回复用户图像" id="backUimg" src="'+value.Uimgurl+'">'+
							'</div>'+
							'<div style="width:30%">'+
								'<h1>'+value.Mtitle+'</h1>'+
								'<p>'+value.Mtime+'</p>'+
							'</div>'+
							'<div style="width:30%">'+
								'<p id="Btime">'+value.Btime+'</p>'+
								'<p id="Bmessage">'+value.Bmessage+'</p>'+
							'</div>'+
						'</a>'+
						'<div style="width:10%">'+
							'<button id="Bid" onClick="deleteback('+value.Bid+')">删除</button>'+
						'</div>'+
					'</div>'
			})
			showBottomBack(pageNum);
		}
	})
}
function showBottomBack(pageNum){
	$.ajax({
		url:"User_pastBackQuery",
		type:"post",
		data:{
			flag:1,
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
			if(page != 1 || pageNum != 1){
			ul.innerHTML +=
				'<li class = "li"><a class = "lia" href="javascript:showMiddleBack(-2,'+pageNum+')">首页</a></li>'+
				'<li class = "li"><a class = "lia" href="javascript:showMiddleBack(-4,'+pageNum+')">上一页</a></li>'
			}
			for(var i = 0;i < pageNum;i++){
				if(i+1 == page){
					ul.innerHTML +=
						'<li class = "li"><a class = "lia" style="background: #30A5DA;" href="javascript:showMiddleBack('+(i-2)+','+pageNum+')">'+	(i+1)+'</a></li>'
				}else{
					ul.innerHTML +=
						'<li class = "li"><a class = "lia" href="javascript:showMiddleBack('+(i-2)+','+pageNum+')">'+(i+1)+'</a></li>'
				}
			}
			if(page != pageNum || pageNum != 1){
				ul.innerHTML +=
					'<li class = "li"><a class = "lia" href="javascript:showMiddleBack(-3,'+pageNum+')">下一页</a></li>'+
					'<li class = "li"><a class = "lia" href="javascript:showMiddleBack('+(pageNum-3)+','+pageNum+')">尾页</a></li>'
			}
		}
	})
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
		url:"User_messageOrback",
		type:"post",
		data:{
			status:1
		},
		datatype:"json",
		error:function(error){
			alert(error+"请求失败");
		},
		success:function(result){
			var obj = JSON.parse(result);
			if(obj.flag == 0){
				var user = document.getElementById("User");
				var pageNum = 0;
				$.ajax({
					url:"User_pastMessageQuery",
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
						showpageMessage(-2,pageNum);
					}
				})
			}
			else{
				var pageNum = 0;
				$.ajax({
					url:"User_pastBackQuery",
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
						showMiddleBack(-2,pageNum);
					}
				})
			}
		}
	})
})