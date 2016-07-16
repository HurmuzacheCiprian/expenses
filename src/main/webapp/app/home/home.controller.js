(function() {
    'use strict';

    angular
        .module('expensesApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state'];

    function HomeController ($scope, Principal, LoginService, $state) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        $scope.items = [{
            dayOfWeek: 'Wednesday',
            expenses: [{
                category: 'FOOD',
                price: 50
            },{
                category: 'CLOTHES',
                price: 170
            }]
        }, {
            dayOfWeek: 'Thursday',
            expenses: [{
                category: 'CAR',
                price: 100
            }]
        }, {
            dayOfWeek: 'Friday',
            expenses: [{
                category: 'TECH',
                price: 3600
            }]
        }];

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
    }
})();
