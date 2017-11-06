var toDo = angular.module('toDo');
toDo.controller('forgotpasswordController', function($scope, forgotpasswordService ,$location){
	
	$scope.changePassword = function(){
		
		var b=forgotpasswordService.changePassword($scope.user,$scope.error2);
		
			b.then(function(response) {
				$location.path('/login')
			},function(response){
				$scope.error2=response.data.responseMessage;
			});
			
	}
});