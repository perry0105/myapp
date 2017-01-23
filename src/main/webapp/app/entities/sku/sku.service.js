(function() {
    'use strict';
    angular
        .module('myappApp')
        .factory('Sku', Sku);

    Sku.$inject = ['$resource'];

    function Sku ($resource) {
        var resourceUrl =  'api/skus/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
