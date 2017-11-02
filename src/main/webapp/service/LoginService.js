var toDo = angular.module('toDo');

toDo.factory('loginService', function($http, $location) {

	var abc = {};
	
	abc.loginUser = function(user) {
		console.log("service line1..");
		return $http({
			method : "POST",
			url : 'login',
			data : user
		})
	}
	return abc;

});