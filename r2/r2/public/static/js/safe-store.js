!function(r, store, undefined) {
  store.safeGet = function(key, errorValue) {
      if (store.disabled) {
          return errorValue
      }

      // errorValue defaults to undefined, equivalent to the key being unset.
      try {
          return store.get(key)
      } catch (err) {
          r.sendError('Unable to read storage key "%(key)s" (%(err)s)'.format({
              key: key,
              err: err
          }))
          // TODO: reset value to errorValue?
          return errorValue
      }
  }

  store.safeSet = function(key, val) {
      if (store.disabled) {
          return false
      }

      // swallow exceptions upon storage set for non-trivial operations. returns
      // a boolean value indicating success.
      try {
          store.set(key, val)
          return true
      } catch (err) {
          r.warn('Unable to set storage key "%(key)s" (%(err)s)'.format({
              key: key,
              err: err
          }))
          return false
      }
  }
}(r, store);
