(function() {
    'use strict';
    angular
        .module('expensesApp')
        .service('ExpensesDailyService',ExpensesDailyService);

    ExpensesDailyService.$inject = ['$http'];

    function ExpensesDailyService($http) {
        return {
            register: register,
            getCategories: getCategories,
            getExpenses: getExpenses,
            remove: remove
        }

        function remove(expenseId) {
            return $http({
                url: '/expenses?expenseId='+expenseId,
                method: 'DELETE',
            });
        }

        function getExpenses() {
            return $http({
                url: '/expenses',
                method: 'GET'
            });
        }

        function register(data) {
            return $http({
                url: '/expenses',
                method: 'POST',
                data:data
            });
        }

        function getCategories() {
            return $http({
                url: '/util/categories',
                method: 'GET'
            });
        }

    }

})();
