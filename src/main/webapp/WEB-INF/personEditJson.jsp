<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit person</title>
<script src="angular.min.js"></script>
<script src="jquery-1.9.1.js"></script>
<script>
	function PersonCtrl($scope,$http){
//		var id = '<s:property value="person" />';
		var id = parseInt('<s:property value="person" />'); //Il me faut un nombre
		var saveUrl = '<s:url action="personEditJson" method="save" includeParams="none"><s:param name="person" value="person.id"/></s:url>'
		$scope.person = {'id':id,'firstname':'<s:property value="person.firstname"/>','lastname':'<s:property value="person.lastname"/>'}
		$scope.error = null
		$scope.messages=null
		$scope.save = function(person) {
			var postData = {'person':person}
			//retirer les classes css sur tous les input
			$("input").each(function(i,e){$(e).removeClass("error")})
			$("#messages").removeClass()
			$scope.messages = null
			$http({method:'POST',url:saveUrl,data:postData}).success(function(data,status){
				if(data.status=="KO"){
					$scope.error=data.fieldErrors
					$("#messages").addClass("errorMessages")
					$scope.messages = data.actionErrors
					for(var key in $scope.error){
						$("input[name='"+key+"']").addClass("error")
					}
				} else {
//					$("#messages").addClass("okMessages")
//					$scope.messages = data.actionMessages
					window.location = '<s:url action="personList" />';
				}
			});
		}
	}
</script>
<link rel="stylesheet" href="proto.css" />
</head>
<body ng-controller="PersonCtrl">
	<div id="messages">
		<ul ng-repeat="m in messages">
			<li>{{m}}</li>
		</ul>
	</div>
	<form>
		Prénom: <s:textfield name="person.firstname" ng-model="person.firstname" /> <span class="errorMessages">{{error['person.firstname'][0]}}</span><br />
		Nom:    <s:textfield name="person.lastname" ng-model="person.lastname"/> <span class="errorMessages">{{error['person.lastname'][0]}}</span><br />
		<button ng-click="save(person)">Sauver</button>
	</form>
</body>
</html>