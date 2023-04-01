r.analytics = {
  init: function() {
    // these guys are relying on the custom 'onshow' from jquery.reddit.js
    $(document).delegate(
      '.organic-listing .promotedlink.promoted',
      'onshow',
      _.bind(function(ev) {
        this.fireTrackingPixel(ev.target);
      }, this)
    );

    $('.promotedlink.promoted:visible').trigger('onshow');

    // dont track sponsor's activity
    r.analytics.addEventPredicate('ads', function() {
      return !r.config.is_sponsor;
    });

    // virtual page tracking for ads funnel
    if (r.config.ads_virtual_page) {
      r.analytics.fireFunnelEvent('ads', r.config.ads_virtual_page);
    }

    r.analytics.contextData = {
      dnt: window.DO_NOT_TRACK,
      language: document.getElementsByTagName('html')[0].getAttribute('lang'),
      link_id: r.config.cur_link ? r.utils.fullnameToId(r.config.cur_link) : null,
      loid: null,
      loid_created: null,
      referrer_url: document.referrer || '',
      referrer_domain: null,
      sr_id: r.config.cur_site ? r.utils.fullnameToId(r.config.cur_site) : null,
      sr_name: r.config.post_site || null,
      user_id: null,
      user_name: null,
      user_in_beta: r.config.pref_beta,
    };

    if (r.config.user_id) {
      r.analytics.contextData.user_id = r.config.user_id;
      r.analytics.contextData.user_name = r.config.logged;
    } else {
      var tracker = new redditlib.Tracker();
      var loggedOutData = tracker.getTrackingData();
      if (loggedOutData && loggedOutData.loid) {
        r.analytics.contextData.loid = loggedOutData.loid;
        if (loggedOutData.loidcreated) {
          r.analytics.contextData.loid_created = decodeURIComponent(loggedOutData.loidcreated);
        }
      }
    }

    if (document.referrer) {
      var referrerDomain = document.referrer.match(/\/\/([^\/]+)/);
      if (referrerDomain && referrerDomain.length > 1) {
        r.analytics.contextData.referrer_domain = referrerDomain[1];
      }
    }

    if ($('body').hasClass('comments-page')) {
      r.analytics.contextData.page_type = 'comments';
    } else if ($('body').hasClass('listing-page')) {
      r.analytics.contextData.page_type = 'listing';

      if (r.config.cur_listing) {
        r.analytics.contextData.listing_name = r.config.cur_listing;
      }
    }

    if (r.config.feature_screenview_events) {
      r.analytics.screenviewEvent();
    }

    if (r.config.expando_preference) {
      r.analytics.contextData.expando_preference = r.config.expando_preference;
    }

    if (r.config.pref_no_profanity) {
      r.analytics.contextData.media_preference_hide_nsfw = r.config.pref_no_profanity
    }

    r.analytics.firePageTrackingPixel(r.analytics.stripAnalyticsParams);

    r.hooks.get('analytics').call();
  },

  _eventPredicates: {},

  addEventPredicate: function(category, predicate) {
    var predicates = this._eventPredicates[category] || [];

    predicates.push(predicate);

    this._eventPredicates[category] = predicates;
  },

  shouldFireEvent: function(category/*, arguments*/) {
    var args = _.rest(arguments);

    return !this._eventPredicates[category] ||
        this._eventPredicates[category].every(function(fn) {
          return fn.apply(this, args);
        });
  },

  _isGALoaded: false,

  isGALoaded: function() {
    // We've already passed this test, just return `true`
    if (this._isGALoaded) {
      return true;
    }

    // GA hasn't tried to load yet, so we can't know if it
    // will succeed.
    if (_.isArray(_gaq)) {
      return undefined;
    }

    var test = false;

    _gaq.push(function() {
      test = true;
    });

    // Remember the result, so we only have to run this test once
    // if it passes.
    this._isGALoaded = test;

    return test;
  },

  _wrapCallback: function(callback) {
    var original = callback;

    original.called = false;
    callback = function() {
      if (!original.called) {
        original();
        original.called = true;
      }
    };

    // GA may timeout.  ensure the callback is called.
    setTimeout(callback, 500);

    return callback;
  },

  fireFunnelEvent: function(category, action, options, callback) {
    options = options || {};
    callback = callback || window.Function.prototype;

    var page = '/' + _.compact([category, action, options.label]).join('-');

    // if it's for Gold tracking and we have new _ga available
    // then use it to track the event; otherwise, fallback to old version
    if (options.tracker &&
        '_ga' in window &&
        window._ga.getByName &&
        window._ga.getByName(options.tracker)) {
      window._ga(options.tracker + '.send', 'pageview', {
        'page': page,
        'hitCallback': callback
      });

      if (options.value) {
        window._ga(options.tracker + '.send', 'event', category, action, options.label, options.value);
      }

      return;
    }

    if (!window._gaq || !this.shouldFireEvent.apply(this, arguments)) {
      callback();
      return;
    }

    var isGALoaded = this.isGALoaded();

    if (!isGALoaded) {
      callback = this._wrapCallback(callback);
    }

    // Virtual page views are needed for a funnel to work with GA.
    // see: http://gatipoftheday.com/you-can-use-events-for-goals-but-not-for-funnels/
    _gaq.push(['_trackPageview', page]);

    // The goal can have a conversion value in GA.
    if (options.value) {
      _gaq.push(['_trackEvent', category, action, options.label, options.value]);
    }

    _gaq.push(callback);
  },

  fireGAEvent: function(category, action, opt_label, opt_value, opt_noninteraction, callback) {
    opt_label = opt_label || '';
    opt_value = opt_value || 0;
    opt_noninteraction = !!opt_noninteraction;
    callback = callback || function() {};

    if (!window._gaq || !this.shouldFireEvent.apply(this, arguments)) {
      callback();
      return;
    }

    var isGALoaded = this.isGALoaded();

    if (!isGALoaded) {
      callback = this._wrapCallback(callback);
    }

    _gaq.push(['_trackEvent', category, action, opt_label, opt_value, opt_noninteraction]);
    _gaq.push(callback);
  },

  fireTrackingPixel: function(el) {
    var $el = $(el);
    var onCommentsPage = $('body').hasClass('comments-page');

    if ($el.data('trackerFired') || onCommentsPage) {
      return;
    }

    var pixel = new Image();
    var impPixel = $el.data('impPixel');

    if (impPixel) {
      pixel.src = impPixel;
    }

    if (!adBlockIsEnabled) {
      var thirdPartyTrackingUrl = $el.data('thirdPartyTrackingUrl');
      if (thirdPartyTrackingUrl) {
        var thirdPartyTrackingImage = new Image();
        thirdPartyTrackingImage.src = thirdPartyTrackingUrl;
      }

      var thirdPartyTrackingUrl2 = $el.data('thirdPartyTrackingTwoUrl');
      if (thirdPartyTrackingUrl2) {
        var thirdPartyTrackingImage2 = new Image();
        thirdPartyTrackingImage2.src = thirdPartyTrackingUrl2;
      }
    }

    var adServerPixel = new Image();
    var adServerImpPixel = $el.data('adserverImpPixel');
    var adServerClickUrl = $el.data('adserverClickUrl');

    if (adServerImpPixel) {
      adServerPixel.src = adServerImpPixel;
    }

    $el.data('trackerFired', true);
  },

  fireUITrackingPixel: function(action, srname, extraParams) {
    var pixel = new Image();
    pixel.src = r.config.uitracker_url + '?' + $.param(
      _.extend(
        {
          act: action,
          sr: srname,
          r: Math.round(Math.random() * 2147483647), // cachebuster
        },
        r.analytics.breadcrumbs.toParams(),
        extraParams
      )
    );
  },

  firePageTrackingPixel: function(callback) {
    var url = r.config.tracker_url;
    if (!url) {
      return;
    }
    var params = {
      dnt: this.contextData.dnt,
    };

    if (this.contextData.loid) {
      params.loid = this.contextData.loid;
    }
    if (this.contextData.loid_created) {
      params.loidcreated = decodeURIComponent(this.contextData.loid_created);
    }

    var querystring = [
      'r=' + Math.random(),
    ];

    if (this.contextData.referrer_domain) {
      querystring.push(
        'referrer_domain=' + encodeURIComponent(this.contextData.referrer_domain)
      );
    }

    for(var p in params) {
      if (params.hasOwnProperty(p)) {
        querystring.push(
          encodeURIComponent(p) + '=' + encodeURIComponent(params[p])
        );
      }
    }

    var pixel = new Image();
    pixel.onload = pixel.onerror = callback;
    pixel.src = url + '&' + querystring.join('&');
  },

  // If we passed along referring tags to this page, after it's loaded, remove them from the URL so that 
  // the user can have a clean, copy-pastable URL. This will also help avoid erroneous analytics if they paste the URL
  // in an email or something.
  stripAnalyticsParams: function() {
    var hasReplaceState = !!(window.history && window.history.replaceState);
    var params = $.url().param();
    var stripParams = ['ref', 'ref_source', 'ref_campaign'];
    // strip utm tags as well
    _.keys(params).forEach(function(paramKey){
      if (paramKey.indexOf('utm_') === 0){
        stripParams.push(paramKey);
      }
    });

    var strippedParams = _.omit(params, stripParams);

    if (hasReplaceState && !_.isEqual(params, strippedParams)) {
      var a = document.createElement('a');
      a.href = window.location.href;
      a.search = $.param(strippedParams);
      if (!a.search) {
        // Safari leaves a trailing ? when search is empty
        a.href = a.href.replace(/\?(#.+)?$/, a.hash);
      }

      window.history.replaceState({}, document.title, a.href);
    }
  },

  addContextData: function(properties, payload) {
    /* jshint sub: true */
    payload = payload || {};

    if (this.contextData.user_id) {
      payload['user_id'] = this.contextData.user_id;
      payload['user_name'] = this.contextData.user_name;
    } else {
      payload['loid'] = this.contextData.loid;
      payload['loid_created'] = decodeURIComponent(this.contextData.loid_created);
    }

    properties.forEach(function(contextProperty) {
      /* jshint eqnull: true */
      if (this.contextData[contextProperty] != null) {
        payload[contextProperty] = this.contextData[contextProperty];
      }
    }.bind(this));

    return payload;
  },

  screenviewEvent: function() {
    var eventTopic = 'screenview_events';
    var eventType = 'cs.screenview';
    var payload = this.addContextData([
      'sr_name',
      'sr_id',
      'listing_name',
      'language',
      'dnt',
      'referrer_domain',
      'referrer_url',
      'user_in_beta',
    ]);

    if (r.config.event_target) {
      for (var key in r.config.event_target) {
        var value = r.config.event_target[key];
        if (value !== null) {
          payload[key] = value;
        }
      }
    }

    var rank_by_link = {};
    $('.linklisting .thing.link').each(function() {
        var $thing = $(this);
        var fullname = $thing.data('fullname');
        var rank = parseInt($thing.data('rank')) || '';
        rank_by_link[fullname] = rank;
    });
    if (!_.isEmpty(rank_by_link)) {
        payload['rank_by_link'] = rank_by_link;
    }

    // event collector
    r.events.track(eventTopic, eventType, payload).send();
  },

  loginRequiredEvent: function(actionName, actionDetail, targetType, targetFullname) {
    var eventTopic = 'login_events';
    var eventType = 'cs.loggedout_' + actionName;
    var payload = this.addContextData([
      'sr_name',
      'sr_id',
      'listing_name',
      'referrer_domain',
      'referrer_url',
    ]);

    payload['process_notes'] = 'LOGIN_REQUIRED';

    if (actionDetail) {
      payload['details_text'] = actionDetail;
    }

    if (targetType) {
      payload['target_type'] = targetType;
    }

    if (targetFullname) {
      payload['target_fullname'] = targetFullname;
      payload['target_id'] = r.utils.fullnameToId(targetFullname);
    }

    // event collector
    r.events.track(eventTopic, eventType, payload).send();
  },

  timeoutForbiddenEvent: function(actionName, actionDetail, targetType, targetFullname) {
    var eventTopic = 'forbidden_actions';
    var eventType = 'cs.forbidden_' + actionName;
    var payload = this.addContextData([
      'sr_name',
      'sr_id',
    ]);

    payload['process_notes'] = 'IN_TIMEOUT';

    if (actionDetail) {
      payload['details_text'] = actionDetail;
    }

    if (targetType) {
      payload['target_type'] = targetType;
    }

    if (targetFullname) {
      payload['target_fullname'] = targetFullname;
      payload['target_id'] = r.utils.fullnameToId(targetFullname);
    }

    // event collector
    r.events.track(eventTopic, eventType, payload).send();
  },

  expandoEvent: function(actionName, targetData) {
    var eventTopic = 'expando_events';
    var eventType = 'cs.' + actionName;
    var payload = this.addContextData([
      'page_type',
      'listing_name',
      'referrer_domain',
      'referrer_url',
      'expando_preference',
      'media_preference_hide_nsfw',
    ]);

    if ('linkIsNSFW' in targetData) {
      payload['nsfw'] = targetData.linkIsNSFW;
    }

    if ('linkType' in targetData) {
      payload['target_type'] = targetData.linkType;
      
    }

    if ('provider' in targetData) {
      payload['provider'] = targetData.provider;
    }

    if ('linkFullname' in targetData) {
      payload['target_fullname'] = targetData.linkFullname;
      payload['target_id'] = r.utils.fullnameToId(targetData.linkFullname);
    }

    if ('linkCreated' in targetData) {
      payload['target_create_ts'] = targetData.linkCreated;
    }

    if ('linkURL' in targetData) {
      payload['target_url'] = targetData.linkURL;
    }

    if ('linkDomain' in targetData) {
      payload['target_url_domain'] = targetData.linkDomain;
    }

    if ('authorFullname' in targetData) {
      payload['target_author_id'] = r.utils.fullnameToId(targetData.authorFullname);
    }
      
    if ('subredditName' in targetData) {
      payload['sr_name'] = targetData.subredditName;
    }

    if ('subredditFullname' in targetData) {
      payload['sr_id'] = r.utils.fullnameToId(targetData.subredditFullname);
    }

    r.events.track(eventTopic, eventType, payload).send();
  },

  sendEvent: function(eventTopic, actionName, defaultFields, customFields, done) {
    var eventType = 'cs.' + actionName;
    var payload = this.addContextData(defaultFields, customFields);
    r.events.track(eventTopic, eventType, payload).send(done);
  },
};

r.analytics.breadcrumbs = {
  selector: '.thing, .side, .sr-list, .srdrop, .tagline, .md, .organic-listing, .gadget, .sr-interest-bar, .trending-subreddits, a, button, input',
  maxLength: 3,
  sendLength: 2,

  init: function() {
    this.hasSessionStorage = this._checkSessionStorage();
    this.data = this._load();

    var refreshed = this.data[0] && this.data[0].url == window.location;
    if (!refreshed) {
      this._storeBreadcrumb();
    }

    $(document).delegate('a, button', 'click', $.proxy(function(ev) {
      this.storeLastClick($(ev.target));
    }, this));
  },

  _checkSessionStorage: function() {
    // Via modernizr.com's sessionStorage check.
    try {
      sessionStorage.setItem('__test__', 'test');
      sessionStorage.removeItem('__test__');
      return true;
    } catch(e) {
      return false;
    }
  },

  _load: function() {
    if (!this.hasSessionStorage) {
      return [{stored: false}];
    }

    var data;

    try {
      data = JSON.parse(sessionStorage.breadcrumbs);
    } catch (e) {
      data = [];
    }

    if (!_.isArray(data)) {
      data = [];
    }

    return data;
  },

  store: function() {
    if (this.hasSessionStorage) {
      sessionStorage.breadcrumbs = JSON.stringify(this.data);
    }
  },

  _storeBreadcrumb: function() {
    var cur = {
      url: location.toString(),
    };

    if ('referrer' in document) {
      var referrerExternal = !document.referrer.match('^' + r.config.currentOrigin);
      var referrerUnexpected = this.data[0] && document.referrer != this.data[0].url;

      if (referrerExternal || referrerUnexpected) {
        cur.ref = document.referrer;
      }
    }

    this.data.unshift(cur);
    this.data = this.data.slice(0, this.maxLength);
    this.store();
  },

  storeLastClick: function(el) {
    try {
      this.data[0].click =
        r.utils.querySelectorFromEl(el, this.selector);
      this.store();
    } catch (e) {
      // Band-aid for Firefox NS_ERROR_DOM_SECURITY_ERR until fixed.
    }
  },

  lastClickFullname: function() {
    var lastClick = _.find(this.data, function(crumb) {
      return crumb.click;
    });

    if (lastClick) {
      var match = lastClick.click.match(/.*data-fullname="(\w+)"/);
      return match && match[1];
    }
  },

  toParams: function() {
    params = [];
    for (var i = 0; i < this.sendLength; i++) {
      _.each(this.data[i], function(v, k) {
        params['c' + i + '_' + k] = v;
      });
    }
    return params;
  },

};


r.hooks.get('setup').register(function() {
  r.analytics.breadcrumbs.init();
});

