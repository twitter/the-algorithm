!function(r) {
  r.access = {};

  var initialized = false
  var initHookQueue = [];

  _.extend(r.access, {
    init: function() {
      initialized = true;

      initHookQueue.forEach(function(fn) {
        fn();
      });

      initHookQueue.length = 0;
    },

    isLinkRestricted: function(el) {
      return false;
    },

    initHook: function(fn) {
      if (initialized) {
        fn();
      } else {
        initHookQueue.push(fn);
      }
    },
  });
}(r);
