(function() {
    'use strict';

    angular
        .module('expensesApp')
        .controller('ExpensesDailyController',ExpensesDailyController);

    ExpensesDailyController.$inject = ['$scope','ExpensesDailyService'];

    function ExpensesDailyController($scope,ExpensesDailyService) {
        $scope.today = null;
        $scope.colors = ['color-one','color-two','color-three','color-four','color-five','color-six','color-seven','color-eight'];
        $scope.categories = [];
        $scope.expense = {};
        $scope.emptyExpense = false;
        $scope.limit = {};
        $scope.colorIcon = colorIcon;
        $scope.register = register;
        $scope.expenses = [];
        $scope.totalExpenses = null;
        $scope.registerLimit = registerLimit;
        $scope.remove = remove;
        $scope.message = "Ordonate dup pret descrescator si grupate dupa categorie";
        var vm = this;
        vm.init = init;

        vm.init();

        function init() {
            ExpensesDailyService.getCategories()
                .then(function(data) {
                    $scope.categories = data.data;
                }, function(error) {
                    $scope.categories = [];
                });
            ExpensesDailyService.getExpenses()
                .then(function(data) {
                    $scope.today = data.data.date;
                    $scope.expenses = data.data.expenses;
                    if(angular.isUndefined(data.data.expenses) || data.data.expenses.length == 0) {
                        $scope.emptyExpense = true;
                    }
                    $scope.totalExpenses = data.data.totalAmount;
                }, function(error) {

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
                    $scope.expense = {name: '', description: ''};
                    vm.init();
                }, function(error) {
                    $scope.successMessage = undefined;
                    $scope.errorMessage = 'Error while saving the expense. Please try again later!';
                });

        }

        function registerLimit() {

        }

        function remove(expense) {
            ExpensesDailyService.remove(expense.id)
                .then(function(data) {
                    vm.init();
                })
        }
    }

})();
