(function() {
    'use strict';

    angular
        .module('expensesApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('expenses', {
            abstract: true,
            parent: 'app'
        });
    }
})();
