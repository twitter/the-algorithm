;(function(App, r, window, undefined) {
  var hasOwnProperty = Object.prototype.hasOwnProperty;

  App.utils = App.utils || {};

  // uuid.js
  App.utils.uuid = r.uuid;

  // Given an object, serialize it into a set of urlencoded query parameters
  App.utils.serialize = function(obj) {
    var params = [];

    for (var p in obj) {
      if (obj.hasOwnProperty(p)) {
        params.push(encodeURIComponent(p) + '=' + encodeURIComponent(obj[p]));
      }
    }

    return params.join('&');
  }

})((window.rembeddit = window.rembeddit || {}), r, this);
