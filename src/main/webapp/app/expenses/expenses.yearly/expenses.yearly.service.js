(function () {
    'use strict';

    angular
        .module('expensesApp')
        .service('ExpensesYearlyService',ExpensesYearlyService);

    ExpensesYearlyService.$inject = ['$http'];

    function ExpensesYearlyService($http) {
        return {
            getCategories: getCategories,
            getMonths: getMonths,
            getSpecificMonthExpense: getSpecificMonthExpense
        }

        function getSpecificMonthExpense(month) {
            return $http({
                url: '/expenses/month-expenses?month='+month,
                method: 'GET'
            });
        }

        function getMonths() {
            return $http({
                url: '/categories/months',
                method: 'GET'
            });
        }

        function getCategories() {
            return $http({
                url: '/categories',
                method: 'GET'
            });
        }


    }


})();
