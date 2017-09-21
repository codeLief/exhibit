<%@ page contentType="text/html;charset=UTF-8" language="java"
	trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8" />
<title>EXHIBIT</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<link rel="stylesheet" href="page/css/bootstrap.min.css" />
<link rel="stylesheet" href="page/css/page.css" />
<script src="page/js/jquery.min.js" type="text/javascript"></script>


<style type="text/css">


ul.select-ul {
	list-style: none;
	margin-left: -42px;
	margin-top: 33px;
}

.search_categories {
	height: 18px;
}

.search_categories ul li {
	float: left;
	width: 60px;
	line-height: 10px;
	text-align: center;
}

.search_categories ul li span.search_line {
	margin-left: 10px;
	color: #CCC;
}

ul,li {
	list-style-type: none;
}

.ha-active {
	background-color: #f5f5f5
}

a.showSub {
	text-decoration: none;
	font-size: 18px;
}

td.showMainPro {
	word-wrap: break-word;
	word-break: break-all;
}

.select-ul li.active {
	background: url(page/images/search_active.png);
	display: block;
	height: 30px;
}

#envChoice li.active {
	background-color: #f5f5f5;
	border-radius: 7px;
}

div.head {
	background: url("page/images/header-bg.png") repeat-x scroll 0 0
		transparent;
	filter: progid:DXImageTransform.Microsoft.gradient(enabled=false );
	background-attachment: fixed;
	width: 100%;
	height: 41px;
	right: 0;
	left: 0;
	z-index: 100000;
}
</style>

</head>
<body>

	<div class="head"></div>
	<div class="row-fluid"
		style="min-height:400px;padding: 0px 8%;max-width:100%">
		<div class="span9" style="float:left">

			<ul class="breadcrumb">
				<li>选择环境</li>
			</ul>
	
			<ul id="envChoice" class="nav nav-pills" style="margin-top:20px;display: none;">
				<li class="span3" onclick="setTab1(this, {envId})"><a rel="{envId}"
				href="#">{name} 环境</a></li>
			</ul>
			<div class="row-fluid" style="margin-top: 10px;">
				<div class="contentWrap clearfix">
					<ul id="versionChoice" class="nav nav-tabs" role="tablist"></ul>
				</div>
			</div>
			<div class="select-div">
				<div class="search_categories">
					<ul class="select-ul">
						<li id="one1" onclick="setTab2(this, 1)"><a href="#">服务名</a><span
							class="search_line">|</span></li>
						<li id="one2" onclick="setTab2(this, 2)"><a href="#">应用名</a><span
							class="search_line">|</span></li>
						<li id="one3" onclick="setTab2(this, 3)"><a href="#">地址名</a></li>
					</ul>
				</div>
				<div class="col-lg" style="padding: 18px 60% 0px 0px;">
					SELECT:
					<div class="input-group"
						style="float: right;margin-left: 62px;margin-top: -26px;">
						<input type="text" class="form-control" id="selectLike"
							onblur="query();"
							onkeypress="javascript:if(event.keyCode==13){query();}">
						<span class="input-group-btn">
							<button class="btn btn-default" type="button" onclick="query();">Go</button>
						</span>
					</div>
				</div>
			</div>
			<div class="tabbable" id="tabs"
				style="padding: 90px 0 0;display: none;">
				<ul class="nav nav-tabs">
					<li class="active"><a data-toggle="tab"
						href="javascript:void(0)"
						onclick="javascript:setShowDivId('provider', this, null);query();" id="provider_table">提供者</a></li>
					<li><a data-toggle="tab" href="javascript:void(0)"
						onclick="javascript:setShowDivId('consumer', this, null);query();" id="consumer_table">消费者</a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane" id="provider">
						<table class="table table-hover" style="border: 1px solid #DDD;">
							<thead style="background-color: rgba(245,245,245,1);">
								<tr>
									<th width="4%">#</th>
									<th width="16%">地址</th>
									<th width="16%">项目名</th>
									<th width="20%">接口</th>
									<th width="20%">提供方法</th>
									<th width="8%">提供人</th>
									<th width="8%">提供者数量</th>
									<th width="8%">消费者数量</th>
								</tr>
							</thead>
							
							<!-- provider table -->
							
							<tbody id="provider_tr_no" style="display: none;">
								<tr>
									<td colspan="40">
										<div class="text-center">抱歉，没有相关数据</div>
									</td>
								</tr>
								<tr style="background: #eeeeee; display: none;"
									id="showProvider">
									<td colspan="100">
										<table class="table table-striped table-bordered">
											<thead>
												<tr class="success">
													<th>服务器</th>
													<th>端口号</th>
													<th>超时时间</th>
													<th>接口描述</th>
													<th>状态</th>
													<th>最后检查时间</th>
													<th>上次注销时间</th>
													<th>注册时间</th>
													<th>操作</th>
												</tr>
											</thead>
											<tbody id="showProvider_tr">
												<tr>
													<td>{sip}</td>
													<td>{sport}</td>
													<td>{sreadTimeout}</td>
													<td>{sdesc}</td>
													<td>{status | statusFilter}</td>
													<td>{lastCheckTime | dateFilter}</td>
													<td>{destroyTime | dateFilter}</td>
													<td>{createAt | dateFilter}</td>
													<td>
														<a data-toggle="tab" href="javascript:void(0)"
															onclick="javascript:removePd('{id}', '{providerId}');">删除</a>
													</td>
												</tr>
											</tbody>
										</table>
									</td>
								</tr>
							</tbody>
							<tbody id="provider_tr">
								<tr>
									<td><a data-toggle="tab" class="showSub"
										href="javascript:void(0)" onclick="showSub(this, '{id}')">+</a></td>
									<td>{surl}</td>
									<td>{sappName}</td>
									<td class="showMainPro">{sinterface}</td>
									<td class="showMainPro">{smethods}</td>
									<td>{author}</td>
									<td><a data-toggle="tab" href="javascript:void(0)"
										onclick="javascript:showSub($(this).parent().parent().find('td>a:first'), '{id}')" id="providerCount">{providerCount}</a></td>
									<td><a data-toggle="tab" href="javascript:void(0)" onclick="javascript:if({consumerCount} == 0){alert('没有消费者');return;}setShowDivId('consumer', ('#consumer_table'), '{id}');query();">{consumerCount}</a></td>
								</tr>
							</tbody>
						</table>
					</div>
					
					<!-- consumer table -->
					<div class="tab-pane" id="consumer">
						<table class="table table-hover" style="border: 1px solid #DDD;">
							<thead style="background-color: rgba(245,245,245,1);">
								<tr>
									<th width="15%">消费地址</th>
									<th width="17%">消费接口</th>
									<th width="10%">消费服务器</th>
									<th width="15%">消费应用名</th>
									<th width="5%">状态</th>
									<th width="6%">消费人</th>
									<th width="9%">最后检查时间</th>
									<th width="10%">上次注销时间</th>
									<th width="9%">注册时间</th>
									<th width="4%">操作</th>
								</tr>
							</thead>
							<tbody id="consumer_tr_no" style="display: none;">
								<tr>
									<td colspan="40">
										<div class="text-center">抱歉，没有相关数据</div>
									</td>
								</tr>
							</tbody>
							<tbody id="consumer_tr">
								<tr>
									<td>{surl}</td>
									<td class="showMainPro">{sinterface}</td>
									<td>{cip}</td>
									<td>{cappName}</td>
									<td>{status | statusFilter}</td>
									<td>{author}</td>
									<td>{updateAt | dateFilter}</td>
									<td>{destroyTime | dateFilter}</td>
									<td>{createAt | dateFilter}</td>
									<td>
										<a data-toggle="tab" href="javascript:void(0)"
															onclick="javascript:removeC('{id}', this);">删除</a>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="text-right" id="pagination">
				<ul class="page" maxshowpageitem="7" pagelistcount="10"  id="page" style="float: right;"></ul>
			</div>
	</div>
	</div>
	
	<script src="page/js/common.js" type="text/javascript"></script>
	<script src="page/js/page.js" type="text/javascript"></script>
	<script src="page/js/he.js" type="text/javascript"></script>
	<script src="page/js/filter.js" type="text/javascript"></script>
</body>
</html>