;(function(r, global, undefined) {
  var jail = document.getElementById('gtm-jail');

  r.gtm = {

    trigger: function(eventName, payload) {
      if (payload) {
        this.set(payload);
      }

      r.frames.postMessage(jail.contentWindow, 'event.gtm', {
        event: eventName,
      });
    },

    set: function(data) {
      r.frames.postMessage(jail.contentWindow, 'data.gtm', data);
    },

  };

})((this.r = this.r || {}), this);
