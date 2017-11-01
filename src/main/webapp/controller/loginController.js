var toDo = angular.module('toDo');

toDo.controller('loginController',['$scope', function($scope, loginService, $location) {

	$scope.loginUser = function() {
		var httpLoginUser = loginService.loginUser($scope.user);

		httpLoginUser.then(function(response) {
			console.log("home page");
			$location.path("home");
		}, function(response) {
			console.log("login page");
			$location.path("login");
		});
	}
}]);