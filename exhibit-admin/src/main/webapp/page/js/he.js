//环境
var env;

// 查询类型 服务名、地址名、应用名
var selectType;

// 搜索关键字
var selectLike;

// 查询消费者根据提供着id
var selectConByProId;

// 显示table
var showDivId = 'provider';

var pageNo = 1;

var pageSize = 10;

var showProvider_tr_html = $('#showProvider_tr').html();

var provider_tr_html = $('#provider_tr').html();

var consumer_tr_html = $('#consumer_tr').html();

var env_html = $('#envChoice').html();

function init(){
	
	//初始化env
	var result = listEnv();
	
	var _show_env = $('#envChoice');
	var no_show_html = "<span style='color: red;'><h2>没有可选择环境 </h2></span></ul>"
	
	if(result == null){
		
		_show_env.html(no_show_html);
	}else{
		
		var _data = result.list;
		
		if (_data.length < 1) {

			_show_env.html(no_show_html);
		}else{
			
			var show_html = "";
			for ( var int = 0; int < _data.length; int++) {
				show_html += env_html.format(_data[int]);
			}

			_show_env.html(show_html);
		}
	}
	_show_env.show();
	
}

init();

function setTab1(obj, val) {
	env = val;
	$(obj).parent().children().attr('class', '');
	$(obj).attr('class', 'active');
}

function setTab2(obj, val) {

	selectType = val;

	$(obj).parent().children().attr('class', '');
	$(obj).attr('class', 'active');
}

function showSub(obj, providerId) {

	var _this = $(obj);
	var _thisT = _this.text();

	if (_thisT === '+') {

		var show_html = "";

		var _parent = $(obj).parent().parent();

		var _show = $('#showProvider');

		var _show_tr = $('#showProvider_tr');

		var result = listProviderDetail({
			"providerId" : providerId
		});

		if (result == null) {
			alert('查询失败');
			return;
		}

		var _data = result.list;

		if (_data.length < 1) {

			alert('没有数据');
			return;
		}

		for ( var int = 0; int < _data.length; int++) {
			show_html += showProvider_tr_html.format(_data[int]);
		}

		_show_tr.html(show_html);

		_parent.after(_show);

		_show.show();

		$('.showSub').text('+');

		_this.text('-');

	} else if (_thisT === '-') {

		var _show = $('#showProvider');

		var _show_tr = $('#showProvider_tr');

		_show.hide();

		_show_tr.html('');

		_this.text('+');

	}
}

function setShowDivId(id, obj, proId) {

	showDivId = id;

	selectConByProId = proId;

	$(obj).parent().parent().children().removeClass('active');
	$(obj).parent().addClass('active');
}

function showDiv() {

	var _show = $('#' + showDivId);

	_show.parent().children().removeClass('active');

	_show.addClass('active');
}

function query() {

	debugger;

	if (env == undefined) {

		alert('请选择环境!');
		return;
	}

	selectLike = $('#selectLike').val();

	// 重要
	$('#provider_tr_no').append($('#showProvider'));

	$('#consumer_tr').html('');
	$('#provider_tr').html('');

	var total = 0;

	var show_no = null;
	
	$('#pagination').hide();
	
	if (showDivId === 'provider') {

		var result = pageProvider({
			"sEnv" : env,
			"selectType" : selectType,
			"selectLike" : selectLike,
			"pageNo" : pageNo,
			"pageSize" : pageSize
		});

		var _data = result.list;

		show_no = $('#provider_tr_no');
		
		show_no.hide();
		
		$('#showProvider').hide();
		total = result.total;

		if(total > 0){
			
			var show_html = "";

			for ( var int = 0; int < _data.length; int++) {
				show_html += provider_tr_html.format(_data[int]);
			}

			$('#provider_tr').html(show_html);
		}
	} else if (showDivId == 'consumer') {

		var result = pageConsumer({
			"sEnv" : env,
			"selectType" : selectType,
			"selectLike" : selectLike,
			"providerId" : selectConByProId,
			"pageNo" : pageNo,
			"pageSize" : pageSize
		});

		var _data = result.list;

		show_no = $('#consumer_tr_no');
		
		show_no.hide();
		
		total = result.total;
		
		if(total > 0){
			var show_html = "";
			
			for ( var int = 0; int < _data.length; int++) {
				show_html += consumer_tr_html.format(_data[int]);
			}


			$('#consumer_tr').html(show_html);
		}
	}

	$('#tabs').show();

	showDiv();

	if(total > 0){
		$('#pagination').show();
		showPagination(total);
	}else{
		show_no.show();
	}
}
function nextPage(no) {

	if (pageNo !== no) {
		pageNo = no;
		query();
	}
}

function showPagination(total) {

	$("#page").initPage(total, pageNo, nextPage);
}

function removePd(id, proId){
	
	var result = removeProviderDetail({
		"proId":id
	});
	
	if(result != null){
		
		var _cuParent = $('#showProvider').prev();
		
		var cuObje = _cuParent.find('td>a:first')
		
		var cuPc = _cuParent.find("td>a[id=providerCount]");
		
		var npc = cuPc.text() - 1;
		cuPc.text(npc);
		showSub(cuObje, proId);
		
		if(npc > 0){
			
			showSub(cuObje, proId);
		}
	}
}

function removeC(conId, obj){
	
	var result = removeConsumer({
		"conId":conId
	});
	
	if(result != null){
		query();
	}
}
