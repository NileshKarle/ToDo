var toDo = angular.module('toDo');

/*toDo.controller('loginController',['$scope', function($scope, loginService, $location) {

	$scope.user = function() {
		console.log("line1  ");
		var httpLoginUser = loginService.loginUser($scope.user);
		console.log("line2..");
		httpLoginUser.then(function(response) {
			console.log(response.data);
			console.log("home page");
			$location.path("home");
		}, function(response) {
			console.log("login page");
			$location.path("login");
		});
	}
	
}]);*/

toDo.controller('loginController', function($scope, loginService,$location){
	$scope.loginUser = function(){
		var a=loginService.loginUser($scope.user,$scope.error);
			a.then(function(response) {
				console.log(response.data.responseMessage);
				localStorage.setItem('token',response.data.responseMessage)
				$location.path('/home')
			},function(response){
				$scope.error=response.data.responseMessage;
			});
	}
});