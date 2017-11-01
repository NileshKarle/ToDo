var toDo = angular.module('toDo');

toDo.factory('registerService', function($http, $location) {

	var abc = {};
	abc.registerUser = function(user) {
		return $http({
			method : "POST",
			url : 'register',
			data : user
		});
	}
	return abc;
	
});
/*toDo.factory('registrationServices', function($http,$location) {
	var register = {};
	
	register.registerUser2 = function(user) {
		$http({
			method : "POST",
			url : 'register',
			data : user,
		}).then(function(response) {
			console.log(response.data.responseMessage);
			//$location.path('loginPage')
		}, function(response) {
			console.log(response.data.responseMessage);
		});
	}
	return register;
});*/