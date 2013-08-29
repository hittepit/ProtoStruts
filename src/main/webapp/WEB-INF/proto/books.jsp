<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Livres</title>
<script src="${pageContext.request.contextPath}/js/angular.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/jquery-ui-1.10.1.custom.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/book.js" type="text/javascript" charset="UTF-8"></script>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/styles/proto.css" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/styles/jquery-ui-1.10.1.custom.css" />
</head>
<body ng-controller="BookCtrl">
	<h1>Livres disponibles</h1>
	<div id="booklist">
		<table>
			<tr>
				<th>Titre</th>
				<th>Auteur</th>
				<th>Isbn</th>
			</tr>
			<tr ng-repeat="book in books">
				<td><a ng-click="detail(book.id)">{{book.title}}</a></td>
				<td>{{book.author}}</td>
				<td>{{book.isbn}}</td>
			</tr>
		</table>
		<button ng-click="edit(null)">Créer un livre</button>
	</div>
	<div id="bookdetail" title="Détails">
		Titre: {{book.title}}<br />
		Auteur: {{book.author}}<br />
		Isbn: {{book.isbn}}<br />
		Catégories:<br />
		<ul ng-repeat="c in bookSelectedCategories">
			<li>{{c.name}}</li>
		</ul>
		<button ng-click="edit(book.id)">Editer</button>
	</div>
	<div id="bookedit" title="Edition/création d'un livre">
		<div id="messages">
			<ul ng-repeat="m in messages">
				<li>{{m}}</li>
			</ul>
		</div>
		<form name="bookEditForm" novalidate>
			<input type="hidden" id="bookId" ng-model="bookVo.id" />
			<table>
			<tr>
				<td>Titre: </td>
				<td><input type="text" id="bookVoTitle" name="bookVo.title" ng-model="bookVo.title"  required/></td>
				<td><span class="errorMessages">{{error['bookVo.title'][0]}}</span></td>
			</tr>
			<tr>
				<td>Auteur: </td>
				<td><input type="text" id="bookVoAuthor" name="bookVo.author" ng-model="bookVo.author"  /></td>
				<td>&nbsp;
			</tr>
			<tr>
				<td>Isbn: </td>
				<td><input type="text" id="bookVoIsbn" name="bookVo.isbn" ng-model="bookVo.isbn"  required /><span ></</span></td>
				<td><span class="errorMessages">{{error['bookVo.isbn'][0]}}</span></td>
			</tr>
			<tr>
				<td>Catégories: </td>
				<td><select name="categoriesId" ng-model="bookSelectedCategories" ng-options="c.id as c.description for c in categories" multiple="multiple"></select></td>
				<td><span class="errorMessages">{{error['categoriesId'][0]}}</span></td>
			</tr>
			<tr>
				<td><button ng-click="save(bookVo,bookSelectedCategories)" >Sauver</button></td>
				<td><button ng-click="cancel('bookedit')">Annuler</button></td>
				<td>&nbsp;</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>