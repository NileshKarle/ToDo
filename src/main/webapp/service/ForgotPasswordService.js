var toDo = angular.module('toDo');

toDo.factory('forgotpasswordService', function($http, $location) {

	var abc = {};
	abc.changePassword = function(user) {
		console.log("it is in the forgot password service.....")
		return $http({
			method : "POST",
			url : 'UpdatedPassword',
			data : user
		})
	}
	return abc;

});