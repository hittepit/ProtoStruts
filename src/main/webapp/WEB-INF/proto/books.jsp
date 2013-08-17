<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Livres</title>
<script src="../angular.min.js"></script>
<script src="../jquery-1.9.1.js"></script>
<script>
	function BookCtrl($scope,$http){
		$http.get('books!list.do').success(function(data){
			$scope.books = data
		})
		
		$scope.detail = function(id) {
				$http.get('books!detail.do?id='+id).success(function(data,status){
					$scope.book = data.book;
					$scope.categories = data.categories;
					//$("#bookdetail").attr('display', 'show');
				});
		}
	}
</script>
<link type="text/css" rel="stylesheet" href="../proto.css" />
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
	</div>
	<div id="bookdetail">
		<h2>Détails</h2>
		Titre: {{book.title}}<br />
		Auteur: {{book.author}}<br />
		Isbn: {{book.isbn}}<br />
		Catégories:<br />
		<ul ng-repeat="c in categories">
			<li>{{c.name}}</li>
		</ul>
	</div>
</body>
</html>