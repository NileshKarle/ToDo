var toDo = angular.module('toDo');

toDo.factory('homePageService', function($http, $location) {

	var abc = {};
	
/*	abc.addNote = function(notes) {
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
	*/
	/*abc.changeColor=function(note){
		return $http({
			method : "POST",
			url : 'note/changeColor',
			headers : {
				'token' : localStorage.getItem('token')
			},
			data : note
		})
	}*/
	
/*	abc.deleteNoteForever = function(id){
		return $http({
			method : "DELETE",
			url : 'note/DeleteNotes/'+id,
			headers: {
				'token':localStorage.getItem('token')
			}
		})
	}
	*/
/*	abc.updateNote=function(notes){
		return $http({
			method : "POST",
			url : 'note/noteUpdate',
			headers: {
				'token':localStorage.getItem('token')
			},
			data: notes
		})
	}
	*/
	/*abc.getUser=function(){
		return $http({
			method : "POST",
			url : 'currentUser',
			headers: {
				'token':localStorage.getItem('token')
			}
		})
	}*/
	
/*	abc.changeProfile=function(User){
		return $http({
			method : "POST",
			url : 'profileChange',
			headers: {
				'token':localStorage.getItem('token')
			},
			data : User
		})
	}*/
	
/*	abc.allNotes = function() {
		return $http({
			method : "GET",
			url : 'note/AllNodes',
			headers: {
				'token':localStorage.getItem('token')
			}
		})
	}*/
	
	abc.service = function(url,method,object) {
		return $http({
			method : method,
			url : url,
			headers: {
				'token':localStorage.getItem('token')
			},
			data:object
		})
	}
	
	abc.getData = function(url,method) {
		return $http({
			method : method,
			url : url,
			headers: {
				'token':localStorage.getItem('token')
			}
		})
	}
	
	return abc;

});