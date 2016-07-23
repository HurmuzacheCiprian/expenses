(function() {
    'use strict';

    angular
        .module('expensesApp')
        .service('ExpensesMonthlyService',ExpensesMonthlyService);

    ExpensesMonthlyService.$inject = ['$http'];

    function ExpensesMonthlyService($http) {
        return {
            getMonthlyExpenses: getMonthlyExpenses,
            getMonthlyCategoryInfo: getMonthlyCategoryInfo
        }

        function getMonthlyExpenses() {
            return $http({
                url: '/expenses/monthly-expenses',
                method: 'GET'
            });
        }

        function getMonthlyCategoryInfo() {
            return $http({
                url: '/expenses/monthly-categories-info',
                method: 'GET'
            });
        }

    }


})();
