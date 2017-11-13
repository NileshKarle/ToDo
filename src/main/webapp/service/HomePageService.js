var toDo = angular.module('toDo');

toDo.factory('homePageService', function($http, $location) {

	var abc = {};
	
	abc.addNote = function(notes) {
		console.log(notes.noteStatus);
		return $http({
			method : "POST",
			url : 'AddNotes',
			headers: {
				'token':localStorage.getItem('token')
			},
			data : notes
		})
	}
	
	abc.deleteNoteForever = function(id){
		return $http({
			method : "DELETE",
			url : 'DeleteNotes/'+id,
			headers: {
				'token':localStorage.getItem('token')
			}
		})
	}
	
	abc.logout = function(){
		return $http({
			method : "POSt",
			url : 'logout',
			headers: {
				'token':localStorage.getItem('token')
			}
		})
	}
	
	abc.updateNote=function(notes){
		console.log("inside the update service...");
		return $http({
			method : "POST",
			url : 'Update',
			headers: {
				'token':localStorage.getItem('token')
			},
			data: notes
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