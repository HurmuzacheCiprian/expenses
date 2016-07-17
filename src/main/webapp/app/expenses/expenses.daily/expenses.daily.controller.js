(function() {
    'use strict';

    angular
        .module('expensesApp')
        .controller('ExpensesDailyController',ExpensesDailyController);

    ExpensesDailyController.$inject = ['$scope'];

    function ExpensesDailyController($scope) {
        $scope.today = new Date();
        $scope.colors = ['color-one','color-two','color-three','color-four','color-five','color-six','color-seven','color-eight'];
        $scope.categories = ['FOOD', 'SHOPPING', 'CAR', 'BILLS'];
        $scope.expense = {};
        $scope.colorIcon = colorIcon;
        $scope.register = register;
        $scope.expenses = [{
            category: 'FOOD',
            amount: 84
        }, {
            category: 'SPORTS',
            amount: 500
        }, {
            category: 'CAR',
            amount: 150
        }, {
            category: 'BILLS',
            amount: 250
        }, {
            category: 'FOOD',
            amount: 100
        }, {
            category: 'HOLIDAY',
            amount: 750
        }];
        $scope.totalExpenses = 5500;
        $scope.message = "Ordonate dup pret descrescator si grupate dupa categorie";

        function colorIcon(index) {
            return $scope.colors[index%$scope.colors.length];
        }

        function register() {
            console.log($scope.expense);
        }
    }

})();
