angular.module('cpweb', [])
.controller('cmdController', function($scope, $http) {
    $http.get('/commandList').
        then(function(response) {
            $scope.commands = response.data;
        });
});