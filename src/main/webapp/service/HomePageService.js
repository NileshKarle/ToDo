var toDo = angular.module('toDo');

toDo.factory('homePageService', function($http, $location) {

	var abc = {};
	
	abc.addNote = function(notes) {
		console.log("home page service");
		return $http({
			method : "POST",
			url : 'AddNotes',
			headers: {
				'token':localStorage.getItem('token')
			},
			data : notes
		})
	}
	
	abc.allNotes = function() {
		console.log("home page service2 all notes");
		return $http({
			method : "GET",
			url : 'AllNodes',
			headers: {
				'token':localStorage.getItem('token')
			}
		})
	}
	
	return abc;

});