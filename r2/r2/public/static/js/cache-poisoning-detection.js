!function(global, $, r) {
  'use strict'

  var CANARY_COOKIE = 'pc';

  /***
   * Generate a random canary to use to detect cache poisoning
   *
   * Stuffing the username into a cookie and comparing against `r.config.logged`
   * would work, but cleanup would me messier. There are also extensions
   * that swap out user credentials themselves that might not take the canary
   * cookie into account. For that reason, we use a persistent, random id as
   * the canary.
   *
   * There are 36**2 (1296) possible canaries, large enough to make
   * false-negatives unlikely, but small enough at reddit's scale
   * to make them not uniquely identifying. Essentially, we eat the
   * possibility of missing 1 in every 1296 instances of poisoned caches to
   * protect privacy.
   */
  function generateCanary() {
    return randString(2);
  }

  function randString(stringLen) {
    var id = '';
    var chars = 'abcdefghijklmnopqrstuvwxyz0123456789';

    for (var i = 0; i < stringLen; i++) {
      id += chars.charAt(Math.floor(Math.random() * chars.length));
    }

    return id;
  }

  r.cachePoisoning = {};

  /***
   * Create or refresh the poisoning canary cookie
   */
  r.cachePoisoning.updateCanaryCookie = function() {
    var canary = $.cookie(CANARY_COOKIE);
    if (!canary) {
      canary = generateCanary();
    }

    // Clear out busted path-relative cookies
    $.cookie(CANARY_COOKIE, null, {
        secure: r.config.https_forced,
        domain: r.config.cur_domain,
    });

    // Create the cookie, or extend the lifetime of the existing one
    $.cookie(CANARY_COOKIE, canary, {
        secure: r.config.https_forced,
        domain: r.config.cur_domain,
        path: '/',
        expires: 365,
    });
  };

  /***
   * Check if this is likely a response from a poisoned cache
   */
  r.cachePoisoning.checkPoisoned = function() {
    var clientCanary = $.cookie(CANARY_COOKIE);
    if (clientCanary && r.config.poisoning_canary) {
      if(clientCanary !== r.config.poisoning_canary) {
        return true;
      }
    }
    return false;
  };

  /***
   * Parse the header list from a string to a `{name -> [value, ...]` map
   */
  r.cachePoisoning._parseHeaders = function(headersString) {
    // based on jQuery's ajax.js
    var rheaders = /^(.*?):[ \t]*([^\r\n]*)$/mg;
    var headersHash = {};
    var match;
    while ( (match = rheaders.exec( headersString )) ) {
      var headerName = match[1].toLowerCase();
      if (headersHash[headerName] === undefined) {
        headersHash[headerName] = [];
      }
      headersHash[headerName].push(match[2]);
    }
    return headersHash;
  };

  r.cachePoisoning.logPoisoning = function() {
    var poisonDetails = {
      // A MAC tied to the poisoner and their canary
      report_mac: r.config.poisoning_report_mac,
      poisoner_name: r.config.logged,
      poisoner_id: r.config.user_id,
      // So we can test if one cache policy is more poisonous than another
      cache_policy: r.config.cache_policy,
      poisoner_canary: r.config.poisoning_canary,
      victim_canary: $.cookie(CANARY_COOKIE),
      // So we know just how stale the cached response was
      render_time: r.config.server_time,
      route_name: r.config.pageInfo.actionName,
      source: 'web',
      url: window.location.href || '',
      resp_headers: {},
    };

    // re-request the page via XHR so we can get the value of
    // the various caching headers (`CF-Cache-Status`, etc.)
    // Note that this will be missing `Set-Cookie` headers as well as a few
    // others, we need to use Flash sockets if we want them.
    // this would be fairly straightforward, but a lot of work.
    $.ajax({
      url: window.location.href,
      // Stop jQuery from adding the `X-Requested-With` header in case caches
      // key on it.
      xhr: function() {
          // Get new xhr object using default factory
          var xhr = jQuery.ajaxSettings.xhr();
          // Copy the browser's native setRequestHeader method
          var setRequestHeader = xhr.setRequestHeader;
          // Replace with a wrapper
          xhr.setRequestHeader = function(name, value) {
              if (name === 'X-Requested-With') return;
              setRequestHeader.call(this, name, value);
          }
          // pass it on to jQuery
          return xhr;
      },
      complete: function(xhr) {
        // modhashes change with every response, if the modhash in the response
        // has changed, the response was different and we can't use it to guess
        // the headers sent with _this page's_ response. This could use any
        // identifier that's unique to a single response, though.
        if ((xhr.responseText || "").indexOf(r.config.modhash) !== -1) {
          // jQuery only gives us the string-serialized response headers, parse
          // them back to a hash.
          var hdrs = xhr.getAllResponseHeaders();
          poisonDetails.resp_headers = r.cachePoisoning._parseHeaders(hdrs);
        }

        poisonDetails.resp_headers = JSON.stringify(poisonDetails.resp_headers);
        r.ajax({
          type: 'POST',
          url: '/web/poisoning.json',
          data: poisonDetails,
          headers: {
            'X-Loggit': true,
          },
          success: function() {
            r.log('Sent cache poisoning report to server');
          },
          error: function(xhr, err, status) {
            r.warn('Error sending cache poisoning report to server');
          }
        });
      }
    });
  }

  r.cachePoisoning.init = function() {
    if (r.config.logged) {
      if (r.cachePoisoning.checkPoisoned()) {
        r.cachePoisoning.logPoisoning();
      }
    }
    r.cachePoisoning.updateCanaryCookie();
  };

}(window, jQuery, r);

