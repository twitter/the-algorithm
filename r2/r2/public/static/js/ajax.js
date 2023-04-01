!function(r, undefined) {
    r.ajax = function(request) {
        var url = request.url

        if (request.type == 'GET' && _.isEmpty(request.data)) {
            var preloaded = r.preload.read(url)
            if (preloaded != null) {
                if (request.dataFilter) {
                    preloaded = request.dataFilter(preloaded, 'json')
                }

                request.success(preloaded)

                var deferred = new jQuery.Deferred
                deferred.resolve(preloaded)
                return deferred
            }
        }

        var isLocal = url && (url[0] == '/' || url.lastIndexOf(r.config.currentOrigin, 0) == 0)
        if (isLocal) {
            if (!request.headers) {
                request.headers = {}
            }
            request.headers['X-Modhash'] = r.config.modhash
        }

        return $.ajax(request)
    };
}(r);
