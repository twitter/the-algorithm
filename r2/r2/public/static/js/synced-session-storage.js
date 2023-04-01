!function(r) {

  // this key will be used to sync sessionStorages through localStorage
  var SYNC_EVENT_KEY = '__synced_session_storage__';
  var PERSIST_SYNCED_KEYS_KEY = '__synced_session_storage_keys__';

  var isStorageSupported = true;
  try {
    sessionStorage.setItem(
      PERSIST_SYNCED_KEYS_KEY,
      sessionStorage.getItem(PERSIST_SYNCED_KEYS_KEY) || ''
    )
  } catch (err) {
    isStorageSupported = false;
  }

  /*
    SessionStorage is too restrictive; each new tab in sessionStorage is
    considered a separate session.  LocalStorage never expires; manually
    expiring data is a pain, and often we just want data to last for the
    (logical) session. Enter SyncedSessionStorage.

    SyncedSessionStorage is a wrapper around SessionStorage that uses
    LocalStorage events for syncing data across multiple open tabs.
   */
  function SyncedSessionStorage(sync_key) {
    this._bootstrapped = false;
    this._synced_storage_keys = {};

    // We want the API to match localStorage/sessionStorage, so the
    // public methods intentionally don't catch errors, but we *should* catch
    // potential errors during instantiation so we can at least be sure
    // r.syncedSessionStorage exists.
    if (!this.isSupported) {
      return this;
    }

    var persisted_synced_keys = sessionStorage.getItem(PERSIST_SYNCED_KEYS_KEY);

    if (persisted_synced_keys) {
      // existing session in this tab, use the existing sessionStorage data
      this._bootstrapped = true;    
      this._synced_storage_keys = JSON.parse(persisted_synced_keys);
    } else {
      // send a request to bootstrap from existing sessions if there are any
      // any existing sessions will send back a 'bootstrap' event containing
      // the bootstrap data
      if (isStorageSupported) {
        this._sync({
          type: 'init',
        });
      }
    }

    $(window).on('storage', function(e) {
      // the localStorage.removeItem will come in with a null newValue
      if (e.originalEvent.key === SYNC_EVENT_KEY && e.originalEvent.newValue) {
        var event = JSON.parse(e.originalEvent.newValue);
        this._handleSync(event);
      }
    }.bind(this));
  }

  SyncedSessionStorage.prototype = {
    constructor: SyncedSessionStorage,

    isSupported: isStorageSupported,

    getItem: function(key) {
      if (key in this._synced_storage_keys) {
        return sessionStorage.getItem(key);
      } else {
        return null;
      }
    },

    setItem: function(key, value) {
      this._setItem(key, value);
      this._sync({
        type: 'set',
        key: key,
        value: value.toString(),
      });
    },

    removeItem: function(key) {
      if (key in this._synced_storage_keys) {
        this._removeItem(key);
        this._sync({
          type: 'remove',
          key: key,
        });
      }
    },

    _sync: function(event) {
      // set and remove event data from localStorage, triggering storage event
      localStorage.setItem(SYNC_EVENT_KEY, JSON.stringify(event));
      localStorage.removeItem(SYNC_EVENT_KEY);
    },

    _handleSync: function(event) {
      // handle the storage event triggered from other tabs
      if (event.type === 'set') {
        this._setItem(event.key, event.value);
      } else if (event.type === 'remove') {
        this._removeItem(event.key);
      } else if (event.type === 'init') {
        this._sendBootstrapEvent();
      } else if (event.type === 'bootstrap') {
        this._handleBootstrapEvent(event.payload);
      }
    },

    _sendBootstrapEvent: function() {
      // when a new session needs bootstrapping, send entire current state
      // if we've seen an 'init' event before receiving a 'bootstrap' event, it
      // almost certainly means this was just the first tab open in the session
      if (!this._bootstrapped) {
        this._bootstrapped = true;
      }

      var payload = {};
      
      for (var key in this._synced_storage_keys) {
        payload[key] = sessionStorage.getItem(key);
      }

      this._sync({
        type: 'bootstrap',
        payload: payload,
      });
    },

    _handleBootstrapEvent: function(payload) {
      if (this._bootstrapped) { return; }

      for (var key in payload) {
        this._synced_storage_keys[key] = 1;
        sessionStorage.setItem(key, payload[key]);
      }

      this._bootstrapped = true;
    },

    _removeItem: function(key) {
      sessionStorage.removeItem(key);
      delete this._synced_storage_keys[key];
      sessionStorage.setItem(
        PERSIST_SYNCED_KEYS_KEY,
        JSON.stringify(this._synced_storage_keys)
      )
    },

    _setItem: function(key, value) {
      sessionStorage.setItem(key, value);
      this._synced_storage_keys[key] = 1;
      sessionStorage.setItem(
        PERSIST_SYNCED_KEYS_KEY,
        JSON.stringify(this._synced_storage_keys)
      )
    },
  };

  r.syncedSessionStorage = new SyncedSessionStorage();
}(r);
