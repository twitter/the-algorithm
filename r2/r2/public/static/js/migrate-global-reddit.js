/*
Adds temporary logging of gets/sets through the legacy global `reddit` object.
 */
!function(r, undefined) {
  r.hooks.get('setup').register(function() {
    try {
      // create a new object to detect if anywhere is still using the
      // the legacy config global object
      window.reddit = {};

      function migrateWarn(message) {
        r.sendError(message, { tag: 'reddit-config-migrate-error' })
      }

      var keys = Object.keys(r.config);

      keys.forEach(function(key) {
        Object.defineProperty(window.reddit, key, {
          configurable: false,
          enumerable: true,
          get: function() {
            var message = "config property %(key)s accessed through global reddit object.";
            migrateWarn(message.format({ key: key }));
            return r.config[key];
          },
          set: function(value) {
            var message = "config property %(key)s set through global reddit object.";
            migrateWarn(message.format({ key: key }));
            return r.config[key] = value;
          },
        });
      });
    } catch (err) {
      // for the odd browser that doesn't support getters/setters, just let
      // it function as-is.
      window.reddit = r.config;
    }
  });
}(r);
