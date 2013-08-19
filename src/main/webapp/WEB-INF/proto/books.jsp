<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Livres</title>
<script src="../angular.min.js"></script>
<script src="../jquery-1.9.1.js"></script>
<script src="../jquery-ui-1.10.1.custom.min.js"></script>
<script>
	$(function() {
		$("#bookdetail").dialog({
			autoOpen:false,
			modal:true,
			title:"D�tails"
		})
		$("#bookedit").dialog({
			dialogClass: "no-close",
			autoOpen:false,
			modal:true,
			title:"Edition/cr�ation d'un livre"
		})
	})
	function BookCtrl($scope,$http){
		$http.get('books!list.do').success(function(data){
			$scope.books = data
		})
		
		$scope.detail = function(id) {
				$http.get('books!detail.do?id='+id).success(function(data,status){
					$("#bookdetail").dialog('open');
					$scope.book = data.book;
					$scope.bookSelectedCategories = data.bookCategories;
				});
		}
		
		$scope.edit = function(id){
			$("#bookdetail").dialog('close');
			if(id!=null){
				$http.get('books!edit.do?id='+id).success(function(data,status){
					$scope.bookEdit = data.book;
					$scope.categories = data.categories; 
					$scope.bookSelectedCategories = []; 
					angular.forEach(data.bookCategories, 
							function(o){this.push(o.id);},
							$scope.bookSelectedCategories);
				});
			}
			$("#bookedit").dialog('open');
		}
		
		$scope.save = function(bookEdit,bookSelectedCategories){
			var b = {
				'bookVo':bookEdit,
				'categoriesId':bookSelectedCategories
			};
			$http({method:"POST", url:"books!save.do", data:b}).success(function(data){
				alert("saved")		
			});
		}
		
		$scope.cancel = function(dlgId){
			$scope.bookEdit = '';
			$scope.categories = []; 
			$scope.bookSelectedCategories = []; 
			$("#"+dlgId).dialog('close');
		}
	}
</script>
<link type="text/css" rel="stylesheet" href="../proto.css" />
<link type="text/css" rel="stylesheet" href="../jquery-ui-1.10.1.custom.css" />
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
		<button ng-click="edit(null)">Cr�er un livre</button>
	</div>
	<div id="bookdetail">
		Titre: {{book.title}}<br />
		Auteur: {{book.author}}<br />
		Isbn: {{book.isbn}}<br />
		Cat�gories:<br />
		<ul ng-repeat="c in bookSelectedCategories">
			<li>{{c.name}}</li>
		</ul>
		<button ng-click="edit(book.id)">Editer</button>
	</div>
	<div id="bookedit">
		<form name="bookEditForm" novalidate>
			<input type="hidden" id="bookId" ng-model="bookEdit.id" class="{{codeError}}" />
			Titre: <input type="text" id="bookTitle" ng-model="bookEdit.title" class="{{codeError}}"  required/><br/>
			Auteur: <input type="text" id="bookAuthor" ng-model="bookEdit.author" class="{{codeError}}"  /><br/>
			Isbn (non �ditable): <input type="text" id="bookIsbn" ng-model="bookEdit.isbn" class="{{codeError}}"  required/><br/>
			Cat�gories: <select ng-model="bookSelectedCategories" ng-options="c.id as c.description for c in categories" multiple="multiple"></select><br />
			<button ng-click="save(bookEdit,bookSelectedCategories)" ng-disabled="bookEditForm.$invalid">Sauver</button>
			<button ng-click="cancel('bookedit')">Annuler</button>
		</form>
	</div>
</body>
</html>