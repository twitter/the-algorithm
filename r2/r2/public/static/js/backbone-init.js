!function(r, undefined) {
  r.sync = function(method, model, options) {
    var wrappedDataFilter = options.dataFilter
    options.dataFilter = function(data, type) {
      var filteredData

      if (type === 'json') {
        filteredData = r.utils.unescapeJson(data)
      } else {
        filteredData = data
      }

      if (wrappedDataFilter) {
        return wrappedDataFilter(filteredData)
      } else {
        return filteredData
      }
    }
    return r.backboneSync.call(Backbone, method, model, options)
  }

  r.setupBackbone = function() {
      Backbone.emulateJSON = true
      Backbone.ajax = r.ajax

      if (!r.backboneSync) {
          r.backboneSync = Backbone.sync
          Backbone.sync = r.sync
      }
  }
}(r);
