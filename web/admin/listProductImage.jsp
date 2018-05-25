<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>
<title>产品图片管理</title>

<div class="workingArea">
	<ol class="breadcrumb">
		<li><a href="admin_category_list">所有分类</a></li>
		<li><a href="admin_product_list?cid=${product.category.id}">${product.name}</a></li>
		<li class="active">${product.name}</li>
		<li class="active">产品图片管理</li>
	</ol>
	<table class="addPictureTable" align="center">
		<tr>
			<td class="addPictureTableTD">
				<div>
					<div class="panel panel-warning addPictureDiv">
						<div class="panel-heading">新增产品<b class="text-primary"> 单个 </b>图片</div>
						<div class="panel-body">
							<form method="post" class="addFormSingle" action="admin_productImage_add" enctype="multipart/form-data">
								<table class="addtable">
									<tr>
										<td>请选择本地图片 尺寸400*400 为佳</td>
									</tr>
									<tr>
										<td><input id="filepathSingle" type="file" name="filepath"></td>
									</tr>
									<tr class="submitTR">
										<td align="center" >
											<input type="hidden" name="type" value="type_single">
											<input type="hidden" name="pid" value="${product.id}">
											<button type="submit" class="btn btn-success">提 交</button>
										</td>
									</tr>
								</table>
							</form>
						</div>
					</div>
					<table class="table table-striped table-bordered table-hover  table-condensed">
						<thead>
							<tr class="success">
								<th>ID</th>
								<th>产品单个图片缩略图</th>
								<th>删除</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="pisSingle" var="pi">
								<tr>
									<td>${productImage.id}</td>
									<td>
										<a title="点击查看原图" href="img/productSingle/${productImage.id}.jpg"><img height="50px" src="img/productSingle/${productImage.id}.jpg"></a>
									</td>
									<td><a deleteLink="true" href="admin_productImage_delete?id=${productImage.id}"><span class="glyphicon glyphicon-trash"></span></a></td>
							</c:forEach>
						</tbody>
						
					</table>
				</div>
			
			</td>
		</tr>
	</table>
</div>