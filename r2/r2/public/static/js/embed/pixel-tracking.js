!function(App, window, undefined) {

  var PixelTracker = App.PixelTracker = function(options) {
    this._pixelTrackingUrl = options.url;
    this._anonymousPixelTrackingUrl = options.anonymousUrl;
  };

  PixelTracker.prototype.send = function(payload, options, callback) {
    // Overload #send(payload, callback)
    if (typeof options === 'function') {
      callback = options;
      options = {};
    }

    options = options || {};
    callback = callback || function() {};

    var url = options.anonymous ?
      this._anonymousPixelTrackingUrl : this._pixelTrackingUrl;

    if (!payload || !url) {
      callback();

      return;
    }

    payload.uuid = payload.uuid || App.utils.uuid();

    var image = new Image();
    var buster = Math.round(Math.random() * 2147483647);

    image.onload = callback;
    image.src = url +
                '?r=' + buster +
                '&data=' + encodeURIComponent(JSON.stringify(payload));
  };

}((window.rembeddit = window.rembeddit || {}), this);
