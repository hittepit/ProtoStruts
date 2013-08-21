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
			title:"Edition/création d'un livre",
	        width:'auto'
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
			$scope.bookVo = '';
			$scope.categories = []; 
			$scope.bookSelectedCategories = []; 
			$("input").each(function(i,e){$(e).removeClass("error")})
			$("#messages").removeClass()
			$scope.messages = null
			$scope.error=null;
			$("#bookdetail").dialog('close');
			$http.get("booksJson!categoriesList.do").success(function(data,status){
				$scope.categories = data; 
				if(id!=null){
					$http.get('booksJson!edit.do?id='+id).success(function(data,status){
						$scope.bookVo = data.book;
						$scope.bookSelectedCategories = []; 
						angular.forEach(data.bookCategories, 
								function(o){this.push(o.id);},
								$scope.bookSelectedCategories);
					});
				}
			});
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
					$scope.bookVo = '';
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
			<table>
			<tr>
				<td>Titre: </td>
				<td><input type="text" id="bookVo.title" ng-model="bookVo.title"  required/></td>
				<td><span class="errorMessages">{{error['bookVo.title'][0]}}</span></td>
			</tr>
			<tr>
				<td>Auteur: </td>
				<td><input type="text" id="bookVo.author" ng-model="bookVo.author"  /></td>
				<td>&nbsp;
			</tr>
			<tr>
				<td>Isbn (non éditable): </td>
				<td><input type="text" id="bookVo.isbn" ng-model="bookVo.isbn"  required/></td>
				<td><span class="errorMessages">{{error['bookVo.isbn'][0]}}</span></td>
			</tr>
			<tr>
				<td>Catégories: </td>
				<td><select ng-model="bookSelectedCategories" ng-options="c.id as c.description for c in categories" multiple="multiple"></select></td>
				<td>&nbsp;</td>
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