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
			title:"Détails"
		})
		$("#bookedit").dialog({
			dialogClass: "no-close",
			autoOpen:false,
			modal:true,
			title:"Edition/création d'un livre"
		})
	})
	function BookCtrl($scope,$http){
		$http.get('booksJson!list.do').success(function(data){
			$scope.books = data
		})
		
		$scope.detail = function(id) {
				$http.get('booksJson!detail.do?id='+id).success(function(data,status){
					$("#bookdetail").dialog('open');
					$scope.book = data.book;
					$scope.bookSelectedCategories = data.bookCategories;
				});
		}
		
		$scope.edit = function(id){
			$("input").each(function(i,e){$(e).removeClass("error")})
			$("#messages").removeClass()
			$scope.messages = null
			$scope.error=null;
			$("#bookdetail").dialog('close');
			if(id!=null){
				$http.get('booksJson!edit.do?id='+id).success(function(data,status){
					$scope.bookVo = data.book;
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
			$("input").each(function(i,e){$(e).removeClass("error")});
			$("#messages").removeClass();
			$scope.messages = null;
			$scope.error==null;
			$http({method:"POST", url:"booksJson!save.do?struts.enableJSONValidation=true", data:b}).success(function(data){
				if(data.fieldErrors){
					$scope.error=data.fieldErrors
					for(var key in data.fieldErrors){
						$("input[id='"+key+"']").addClass("error")
					}
					$("#messages").addClass("errorMessages")
					$scope.messages = data.actionErrors
				} else {
					$scope.bookEdit = '';
					$scope.categories = []; 
					$scope.bookSelectedCategories = []; 
					$("#bookedit").dialog('close');
					$http.get('booksJson!list.do').success(function(data){
						$scope.books = data
					})
				}
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
		<button ng-click="edit(null)">Créer un livre</button>
	</div>
	<div id="bookdetail">
		Titre: {{book.title}}<br />
		Auteur: {{book.author}}<br />
		Isbn: {{book.isbn}}<br />
		Catégories:<br />
		<ul ng-repeat="c in bookSelectedCategories">
			<li>{{c.name}}</li>
		</ul>
		<button ng-click="edit(book.id)">Editer</button>
	</div>
	<div id="bookedit">
		<div id="messages">
			<ul ng-repeat="m in messages">
				<li>{{m}}</li>
			</ul>
		</div>
		<form name="bookEditForm" novalidate>
			<input type="hidden" id="bookId" ng-model="bookVo.id" />
			Titre: <input type="text" id="bookVo.title" ng-model="bookVo.title"  required/>
				<span class="errorMessages">{{error['bookVo.title'][0]}}</span><br/>
			Auteur: <input type="text" id="bookVo.author" ng-model="bookVo.author"  /><br/>
			Isbn (non éditable): <input type="text" id="bookVo.isbn" ng-model="bookVo.isbn"  required/><br/>
			Catégories: <select ng-model="bookSelectedCategories" ng-options="c.id as c.description for c in categories" multiple="multiple"></select><br />
			<button ng-click="save(bookVo,bookSelectedCategories)" >Sauver</button>
			<button ng-click="cancel('bookedit')">Annuler</button>
		</form>
	</div>
</body>
</html>