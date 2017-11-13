var toDo = angular.module('toDo');
toDo.controller('EmailVerificationController', function($scope, EmailVerificationService ,$location){
	
	$scope.checkEmail = function(){
		
		var b=EmailVerificationService.checkEmail($scope.user,$scope.error2);
		
			b.then(function(response) {
				$location.path('/login')
			},function(response){
				$scope.error2=response.data.responseMessage;
			});
			
	}
});