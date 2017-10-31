var toDoApp = angular.module('ToDo');

toDoApp.factory('registerService', function($http, $location) {

	register.register = function(user) {
		$http({
			method : "POST",
			url : 'register',
			data : user
		})
		
	}
});