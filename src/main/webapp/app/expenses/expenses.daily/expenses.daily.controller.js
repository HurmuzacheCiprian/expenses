(function() {
    'use strict';

    angular
        .module('expensesApp')
        .controller('ExpensesDailyController',ExpensesDailyController);

    ExpensesDailyController.$inject = ['$scope','ExpensesDailyService'];

    function ExpensesDailyController($scope,ExpensesDailyService) {
        $scope.today = new Date();
        $scope.colors = ['color-one','color-two','color-three','color-four','color-five','color-six','color-seven','color-eight'];
        $scope.categories = [];
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

        init();

        function init() {
            ExpensesDailyService.getCategories()
                .then(function(data) {
                    $scope.categories = data.data;
                }, function(error) {
                    $scope.categories = [];
                });
        }

        function colorIcon(index) {
            return $scope.colors[index%$scope.colors.length];
        }

        function register() {
            ExpensesDailyService.register($scope.expense)
                .then(function(data) {
                    $scope.successMessage = 'Expense saved!';
                    $scope.errorMessage = undefined;
                    $sope.expense = {};
                }, function(error) {
                    $scope.successMessage = undefined;
                    $scope.errorMessage = 'Error while saving the expense. Please try again later!';
                });

        }
    }

})();
