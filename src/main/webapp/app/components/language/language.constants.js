(function () {
    'use strict';

    angular
        .module('expensesApp')

        /*
         Languages codes are ISO_639-1 codes, see http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes
         They are written in English to avoid character encoding issues (not a perfect solution)
         */
        .constant('LANGUAGES', [
            'en',
            'cs',
            'da',
            'nl',
            'fr',
            'de',
            'hu',
            'it',
            'ro',
            'ru',
            'sk',
            'es',
            'sv',
            'tr'
            // jhipster-needle-i18n-language-constant - JHipster will add/remove languages in this array
        ]
    );
})();
