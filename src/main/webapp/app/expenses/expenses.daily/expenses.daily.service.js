(function() {
    'use strict';
    angular
        .module('expensesApp')
        .service('ExpensesDailyService',ExpensesDailyService);

    ExpensesDailyService.$inject = ['$http'];

    function ExpensesDailyService($http) {
        return {
            register: register
        }

        function register() {

        }

    }

})();
