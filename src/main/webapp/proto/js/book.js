

function BookCtrl($scope,$http){
	function call(m,u,d,s){
		$http({method: m,url:u,data:d}).success(s).error(function(data,status,header,config){
			if(status==403){
				alert("Tu n'as pas le droit d'effectuer cette opération. Ton nom a été envoyé à ton chef!");
			} else {
				alert("Erreur "+status);
			}
		})
	};
	
        var selectedRow = '';
        $scope.selectedBook = {}
        $scope.selectedBookCategories =[]
        $scope.editedBook = {}
        $http({method:'GET',url:'booksJson!list.do'}).success(function(data){
          $scope.books = data;
          $scope.hideDetail = true;
        });
        $http.get('booksJson!categoriesList.do').success(function(data){
          $scope.categories = data;
        })

        $scope.rowselect = function(bid){
          if(selectedRow!=''){
            $("tr#"+selectedRow).removeClass("success")
          }
          if(selectedRow != "book"+bid){
            //$http.get('booksJson!detail.do?id='+bid).success(
            call('GET','booksJson!detail.do?id='+bid,null,function(data){
            	if(data.exception != null){
					alert("exception !!");
		            selectedRow='';
		            $scope.hideDetail = true;
				}else{
					selectedRow = "book"+bid;
	            	$("tr#"+selectedRow).addClass("success")
	              $scope.selectedBook = data.book;
	              $scope.selectedBookCategories = data.bookCategories;
	              $scope.hideDetail = false;
	              angular.forEach(data.bookCategories, function(o){this.push(o.id);},$scope.editedBookCategories);
				}
            });
            		//)
          } else {
            selectedRow='';
            $scope.hideDetail = true;
          }
        }

        $scope.create = function(){
          $scope.formTitle = "Création d'un nouveau livre";
          $scope.editedBook={}
          $scope.editedBookCategories = [];
          $scope.errors = {};
          $(".error").each(function(i,e){$(e).removeClass("error");});
          $('#bookEdit').modal()
        }

        $scope.edit = function(bid){
          $http.get('booksJson!detail.do?id='+bid).success(function(data){
            $scope.editedBook = data.book;
            $scope.editedBookCategories = [];
            $scope.errors = {};
            $(".error").each(function(i,e){$(e).removeClass("error");});
            angular.forEach(data.bookCategories, function(o){this.push(o.id);},$scope.editedBookCategories);
            $scope.formTitle = "Edition de "+data.book.title;
          })
          //$('#bookEdit').modal() enlevé parce que data-toggle et href
        }

        $scope.save = function(b,c){
          $scope.errors = {};
          $(".error").each(function(i,e){$(e).removeClass("error");});
          var d = {
            'bookVo':b==""?{}:b, //Quand rien n'est rempli, booEdit==""
            'categoriesId':c
          };
          call('POST','booksJson!save.do?struts.enableJSONValidation=true',d,function(data){
            if(data.fieldErrors){
              $scope.errors=data.fieldErrors
              for(var key in data.fieldErrors){
                $("[name='"+key+"']").addClass("error");
              }
            } else {
              $('#bookEdit').modal('hide')
				$http.get('booksJson!list.do').success(function(data){
					$scope.books = data;
				});
              selectedRow='';
              $scope.hideDetail = true;
            }
          })
/*          .error(function(data,status,header,config){
			if(status==403){
				alert("Tu n'as pas le droit d'effectuer cette opération. Ton nom a été envoyé à ton chef!");
			} else {
				alert("Erreur "+status);
			}
		});*/
        }
      }
