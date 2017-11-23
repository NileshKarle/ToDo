var toDo = angular.module('toDo');

toDo.controller('dummyController', function($scope, dummyService,$location){
	
	redirect();
	
	function redirect (){
		var a=dummyService.redirect();
			a.then(function(response) {
				console.log(response.data.responseMessage);
				localStorage.setItem('token',response.data.responseMessage)
				$location.path("/home");
			},function(response){
				$scope.error=response.data.responseMessage;
			});
	}
});