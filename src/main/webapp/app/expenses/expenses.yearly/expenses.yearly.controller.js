(function () {
    'use strict';

    angular
        .module('expensesApp')
        .controller('ExpensesYearlyController',ExpensesYearlyController);

    ExpensesYearlyController.$inject = ['$scope','ExpensesYearlyService'];

    function ExpensesYearlyController($scope,ExpensesYearlyService) {
        var vm = this;

        $scope.yearlyExpenses = [];
        $scope.months = [];
        $scope.categories = [];
        $scope.data = [2,0,0,2,3,1,0,22,44,10,1];
        $scope.dataJanuary = [];
        $scope.dataFebruary = [];
        $scope.dataMarch = [];
        $scope.dataApril = [];
        $scope.dataMay = [];
        $scope.dataJune = [];
        $scope.dataJuly = [];
        $scope.dataAugust = [];
        $scope.dataSeptember = [];
        $scope.dataOctober = [];
        $scope.dataNovember = [];
        $scope.dataDecember = [];

        vm.init = init;
        vm.getJanuaryExpenses = getJanuaryExpenses
        vm.getJulyExpenses = getJulyExpenses;
        vm.getFebruaryExpenses = getFebruaryExpenses;
        vm.getMarchExpenses = getMarchExpenses;
        vm.getAprilExpenses = getAprilExpenses;
        vm.getMayExpenses = getMayExpenses;
        vm.getJuneExpenses = getJuneExpenses;
        vm.getAugustExpenses = getAugustExpenses;
        vm.getSeptemberExpenses = getSeptemberExpenses;
        vm.getOctoberExpenses = getOctoberExpenses;
        vm.getNovemberExpenses = getNovemberExpenses;
        vm.getDecemberExpenses = getDecemberExpenses;
        vm.init();
        vm.getJanuaryExpenses();
        vm.getFebruaryExpenses();
        vm.getMarchExpenses();
        vm.getAprilExpenses();
        vm.getMayExpenses();
        vm.getJuneExpenses();
        vm.getJulyExpenses();
        vm.getAugustExpenses();
        vm.getSeptemberExpenses();
        vm.getOctoberExpenses();
        vm.getNovemberExpenses();
        vm.getDecemberExpenses();

        function getDecemberExpenses() {
            ExpensesYearlyService.getSpecificMonthExpense('DECEMBER')
                 .then(function (data) {
                   $scope.dataDecember = data.data.amount;
                   $scope.dataDecemberTotal = data.data.total;
            });
        }

        function getNovemberExpenses() {
            ExpensesYearlyService.getSpecificMonthExpense('NOVEMBER')
                 .then(function (data) {
                    $scope.dataNovember = data.data.amount;
                    $scope.dataNovemberTotal = data.data.total;
            });
        }

        function getOctoberExpenses() {
            ExpensesYearlyService.getSpecificMonthExpense('OCTOBER')
                .then(function (data) {
                   $scope.dataOctober = data.data.amount;
                   $scope.dataOctoberTotal = data.data.total;
            });
        }

        function getSeptemberExpenses() {
            ExpensesYearlyService.getSpecificMonthExpense('SEPTEMBER')
                .then(function (data) {
                  $scope.dataSeptember = data.data.amount;
                  $scope.dataSeptemberTotal = data.data.total;
            });
        }

        function getAugustExpenses() {
            ExpensesYearlyService.getSpecificMonthExpense('AUGUST')
                .then(function (data) {
                   $scope.dataAugust = data.data.amount;
                   $scope.dataAugustTotal = data.data.total;
            });
        }

        function getJuneExpenses() {
            ExpensesYearlyService.getSpecificMonthExpense('JUNE')
                .then(function (data) {
                   $scope.dataJune = data.data.amount;
                   $scope.dataJuneTotal = data.data.total;
            });
        }

        function getMayExpenses() {
            ExpensesYearlyService.getSpecificMonthExpense('MAY')
                .then(function (data) {
                   $scope.dataMay = data.data.amount;
                   $scope.dataMayTotal = data.data.total;
            });
        }

        function getAprilExpenses() {
            ExpensesYearlyService.getSpecificMonthExpense('APRIL')
                .then(function (data) {
                   $scope.dataApril = data.data.amount;
                   $scope.dataAprilTotal = data.data.total;
            });
        }

        function getMarchExpenses() {
            ExpensesYearlyService.getSpecificMonthExpense('MARCH')
                .then(function (data) {
                    $scope.dataMarch = data.data.amount;
                    $scope.dataMarchTotal = data.data.total;
            });
        }

        function getFebruaryExpenses() {
            ExpensesYearlyService.getSpecificMonthExpense('FEBRUARY')
                 .then(function (data) {
                     $scope.dataFebruary = data.data.amount;
                     $scope.dataFebruaryTotal = data.data.total;
            });
        }

        function getJanuaryExpenses() {
            ExpensesYearlyService.getSpecificMonthExpense('JANUARY')
                .then(function (data) {
                   $scope.dataJanuary = data.data.amount;
                   console.log($scope.dataJanuary);
                    $scope.dataJanuaryTotal = data.data.total;
                });
        }

        function getJulyExpenses() {
            ExpensesYearlyService.getSpecificMonthExpense('JULY')
                .then(function (data) {
                   $scope.dataJuly = data.data.amount;
                   console.log($scope.dataJuly);
                   $scope.dataJulyTotal = data.data.total;
            });
        }

        function init() {
            ExpensesYearlyService.getCategories()
                .then(function (data) {
                    $scope.categories = data.data;
            });

            ExpensesYearlyService.getMonths()
                .then(function (data) {
                    $scope.months = data.data;
            });

        }

    }


})();
