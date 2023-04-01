;(function(window, undefined) {
  if (window.CustomEvent) {
    return;
  }

  function CustomEvent(eventName, options) {
    options = options || {bubbles: false, cancelable: false, detail: undefined};

    var e = document.createEvent('CustomEvent');

    e.initCustomEvent(eventName, options.bubbles, options.cancelable, options.detail);

    return e;
  }

  CustomEvent.prototype = window.Event.prototype;

  window.CustomEvent = CustomEvent;
})(this);
