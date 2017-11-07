var toDo = angular.module('toDo');

toDo.factory('homePageService', function($http, $location) {

	var abc = {};
	
	abc.addNote = function(note) {
		console.log("home page service");
		return $http({
			method : "POST",
			url : 'AddNotes',
			data : note
		})
	}
	return abc;

});