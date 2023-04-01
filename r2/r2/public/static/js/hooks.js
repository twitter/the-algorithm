/*
  Provides a very simple hook system for one-off event hooks.

  r.hooks.get('init').register(someFunction);
  r.hooks.get('init').call();
 */

!function(r) {
  var hooks = {};

  function Hook(name) {
    this.name = name;
    this.called = false;
    this._callbacks = [];
  }

  Hook.prototype.register = function(callback) {
    if (this.called) {
      callback.call(window);
    } else {
      this._callbacks.push(callback);
    }
  };

  Hook.prototype.call = function() {
    if (this.called) {
      throw 'Hook ' + this.name + ' already called.';
    } else {
      var callbacks = this._callbacks;
      this.called = true;
      this._callbacks = null;

      for (var i = 0; i < callbacks.length; i++) {
        callbacks[i].call(window);
      }
    }
  };

  r.hooks = {
    get: function(name) {
      if (name in hooks) {
        return hooks[name];
      } else {
        var hook = new Hook(name);
        hooks[name] = hook;
        return hook;
      }
    },

    call: function(name) {
      return r.hooks.get(name).call();
    },
  };
}((window.r = window.r || {}));
