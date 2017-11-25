var toDo = angular.module('toDo');

toDo.factory('dummyService', function($http, $location) {

	var abc = {};
	
	abc.redirect = function() {
		return $http({
			method : "POST",
			url : 'socialPageRedirect'
		})
	}
	return abc;

});