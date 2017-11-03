var toDo = angular.module('toDo');

toDo.factory('loginService', function($http, $location) {

	var abc = {};
	
	abc.loginUser = function(user) {
		return $http({
			method : "POST",
			url : 'login',
			data : user
		})
	}
	return abc;

});