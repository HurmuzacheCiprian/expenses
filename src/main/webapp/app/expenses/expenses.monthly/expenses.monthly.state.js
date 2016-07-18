(function() {
    'use strict';

    angular
        .module('expensesApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('monthlyExpenses', {
            parent: 'expenses',
            url: '/monthly',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/expenses/expenses.monthly/expenses.monthly.html',
                    controller: 'ExpensesMonthlyController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
