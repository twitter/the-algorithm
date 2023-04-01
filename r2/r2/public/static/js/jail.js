;(function(r, global, undefined) {
  var jail = document.getElementById('gtm-sandbox');

  r.jail = {

    postMessage: function(id, eventName, data) {

    },

    trigger: function(eventName, payload) {
      if (payload) {
        this.set(payload);
      }

      r.frames.postMessage(sandbox.contentWindow, 'event.gtm', {
        event: eventName,
      });
    },

    set: function(data) {
      r.frames.postMessage(sandbox.contentWindow, 'data.gtm', data);
    },

  };

})((this.r = this.r || {}), this);
