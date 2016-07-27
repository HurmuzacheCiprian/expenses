(function () {
    'use strict';

    angular
        .module('expensesApp')
        .service('ExpensesYearlyService',ExpensesYearlyService);

    ExpensesYearlyService.$inject = ['$http'];

    function ExpensesYearlyService($http) {
        return {
            getYearlyExpenses: getYearlyExpenses,
            getCategories: getCategories
        }

        function getCategories() {
            return $http({
                url: '/categories',
                method: 'GET'
            });
        }

        function getYearlyExpenses() {
            return $http({
                url: '/expenses/yearly-expenses',
                method: 'GET'
            });
        }

    }


})();
