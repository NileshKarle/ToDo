var toDo = angular.module('toDo');

toDo.factory('forgotpasswordService', function($http, $location) {

	var abc = {};
	abc.changePassword = function(user) {
		return $http({
			method : "POST",
			url : 'forgotPassword',
			data : user
		})
	}
	return abc;

});