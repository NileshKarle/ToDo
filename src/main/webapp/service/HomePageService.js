var toDo = angular.module('toDo');

toDo.factory('homePageService', function($http, $location) {

	var abc = {};
	
	abc.addNote = function(notes) {
		console.log(notes.noteStatus);
		return $http({
			method : "POST",
			url : 'note/AddNotes',
			headers: {
				'token':localStorage.getItem('token')
			},
			data : notes
		})
	}
	
	abc.changeColor=function(note){
		return $http({
			method : "POST",
			url : 'note/changeColor',
			headers : {
				'token' : localStorage.getItem('token')
			},
			data : note
		})
	}
	
	abc.deleteNoteForever = function(id){
		return $http({
			method : "DELETE",
			url : 'note/DeleteNotes/'+id,
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
			url : 'note/noteUpdate',
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
			url : 'note/AllNodes',
			headers: {
				'token':localStorage.getItem('token')
			}
		})
	}
	
	return abc;

});