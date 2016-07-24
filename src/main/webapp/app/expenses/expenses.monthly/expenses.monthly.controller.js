(function() {

    'use strict';

    angular
        .module('expensesApp')
        .controller('ExpensesMonthlyController',ExpensesMonthlyController);

    ExpensesMonthlyController.$inject = ['$scope','ExpensesMonthlyService'];

    function ExpensesMonthlyController($scope, ExpensesMonthlyService) {
        var vm = this;
        $scope.message = 'Monthly expenses';
        $scope.labels = [];
        $scope.data = [];
        vm.init = init;

        vm.init();

        function init() {
            ExpensesMonthlyService.getMonthlyExpenses()
                .then(function(data) {
                $scope.currentMonth = data.data.currentMonth;
                $scope.expenses = data.data.monthlyExpenses;
                }, function (error) {

                });
            ExpensesMonthlyService.getMonthlyCategoryInfo()
                .then(function (data) {
                    $scope.categoryMonthlyInfo = data.data;
                    for(var p in $scope.categoryMonthlyInfo) {
                        $scope.data.push($scope.categoryMonthlyInfo[p]);
                        $scope.labels.push(p);
                    }
                });

        }

    }


})();
