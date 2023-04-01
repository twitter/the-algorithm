!function(r) {
  'use strict';

  function postData(eventInfo) {
    $.ajax({
      method: 'POST',
      url: eventInfo.url + '?' + jQuery.param(eventInfo.query),
      data: eventInfo.data,
      contentType: 'text/plain',
      complete: eventInfo.done,
    });
  }

  function calculateHash(key, data) {
    var hash = CryptoJS.HmacSHA256(data, key);
    return hash.toString(CryptoJS.enc.Hex);
  }

  var tracker;

  r.events = {
    init: function() {
      if (r.config.events_collector_key &&
          r.config.events_collector_secret &&
          r.config.events_collector_url) {
        tracker = new EventTracker(
          r.config.events_collector_key,
          r.config.events_collector_secret,
          postData,
          r.config.events_collector_url,
          'reddit.com',
          calculateHash
        );
      }
    },

    track: function(eventTopic, eventName, eventPayload) {
      if (tracker) {
        tracker.track(eventTopic, eventName, eventPayload);
      }
      return this;
    },

    send: function(done) {
      if (tracker) {
        tracker.send(done);
      } else if (typeof done === 'function') {
        done();
      }
      return this;
    }
  };

}(r);
