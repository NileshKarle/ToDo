var toDoApplication = angular.module('ToDo');

toDoApplication.controller('registerController', function($scope, registerService, $location) {

	$scope.user = {};
	
	$scope.register = function() {

		registerService.register($scope.user);
		
	}

});