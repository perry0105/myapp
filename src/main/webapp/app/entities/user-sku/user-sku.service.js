(function() {
    'use strict';
    angular
        .module('myappApp')
        .factory('UserSku', UserSku);

    UserSku.$inject = ['$resource'];

    function UserSku ($resource) {
        var resourceUrl =  'api/user-skus/:id';

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
