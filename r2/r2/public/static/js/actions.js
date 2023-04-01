!function(r) {
  r.actions = {
    trigger: function(actionName, payload) {
      payload = payload || {};
      payload.action = actionName;
      var eventName = 'action:' + actionName;
      var $e = $.Event(eventName, payload);
      
      $(document.body).trigger($e);

      if ($e.isDefaultPrevented()) {
        $(document.body).trigger($.Event(eventName + ':failure', payload));
      } else {
        $(document.body).trigger($.Event(eventName + ':success', payload));
      }
      
      $(document.body).trigger($.Event(eventName + ':complete', payload));
    },

    on: function(actionName, fn) {
      $(document.body).on('action:' + actionName, fn);
    },

    off: function(actionName, fn) {
      $(document.body).off('action:' + actionName, fn);
    },
  };
}(r);
