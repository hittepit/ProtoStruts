$(function() {
	$("#bookdetail").dialog({
		autoOpen:false,
		modal:true
	});
	$("#bookedit").dialog({
		autoOpen:false,
		modal:true,
        width:'auto'
	});
});
function exception(data){
	return data.exception != null;
}
function BookCtrl($scope,$http){
	function call(m,u,d,s){
		$http({method: m,url:u,data:d}).success(s).error(function(data,status,header,config){
			if(status==401){
				alert("Pas de droit d'effectuer cette action!");
			} else {
				alert("Erreur "+status);
			}
		})
	};

	$http.get('booksJson!list.do').success(function(data){
		$scope.books = data;
	});
	
	$scope.detail = function(id) {
			call('GET','booksJson!detail.do?id='+id,null,function(data,status){
					$("#bookdetail").dialog('open');
					$scope.book = data.book;
					$scope.bookSelectedCategories = data.bookCategories;
			});
	};
	
	$scope.edit = function(id){
		$scope.bookVo = '';
		$scope.categories = []; 
		$scope.bookSelectedCategories = []; 
		$(".error").each(function(i,e){$(e).removeClass("error");});
		$("#messages").removeClass();
		$scope.messages = null;
		$scope.error=null;
		$("#bookdetail").dialog('close');
		$http.get("booksJson!categoriesList.do").success(function(data,status){
			$scope.categories = data; 
			if(id!=null){
				$("input#bookVoIsbn").attr("readonly","readonly");
				$("input#bookVoIsbn").attr("disabled","disabled");
				$http.get('booksJson!edit.do?id='+id).success(function(data,status){
					$scope.bookVo = data.book;
					$scope.bookSelectedCategories = []; 
					angular.forEach(data.bookCategories, 
							function(o){this.push(o.id);},
							$scope.bookSelectedCategories);
				});
			} else {
				$("input#bookVoIsbn").removeAttr("readonly");
				$("input#bookVoIsbn").removeAttr("disabled");
			}
		});
		$("#bookedit").dialog('open');
	};
	
	$scope.save = function(bookEdit,bookSelectedCategories){
		var b = {
			'bookVo':bookEdit==""?{}:bookEdit, //Quand rien n'est rempli, booEdit==""
			'categoriesId':bookSelectedCategories
		};
		$(".error").each(function(i,e){$(e).removeClass("error");});
		$("#messages").removeClass();
		$scope.messages = null;
		$scope.error==null;
		call("POST", 
			"booksJson!save.do?struts.enableJSONValidation=true", b, 
			function(data){
				if(data.fieldErrors){
					$scope.error=data.fieldErrors;
					for(var key in data.fieldErrors){
						$("[name='"+key+"']").addClass("error");
					}
					$("#messages").addClass("errorMessages");
					$scope.messages = data.actionErrors;
				} else {
					$scope.bookVo = '';
					$scope.categories = []; 
					$scope.bookSelectedCategories = []; 
					$("#bookedit").dialog('close');
					$http.get('booksJson!list.do').success(function(data){
						$scope.books = data;
					});
				}
		});
	};
	
	$scope.cancel = function(dlgId){
		$("#"+dlgId).dialog('close');
	};
}