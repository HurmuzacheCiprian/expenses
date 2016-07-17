(function() {
    'use strict';
    angular
        .module('expensesApp')
        .service('ExpensesDailyService',ExpensesDailyService);

    ExpensesDailyService.$inject = ['$http'];

    function ExpensesDailyService($http) {
        return {
            register: register,
            getCategories: getCategories
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
