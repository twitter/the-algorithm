!function(r) {
  function isPluginExpandoButton(elem) {
    // temporary fix for RES http://redd.it/392zol
    return elem.tagName === 'A';
  }

  var Expando = Backbone.View.extend({
    buttonSelector: '.expando-button',
    expandoSelector: '.expando',
    expanded: false,

    events: {
      'click .expando-button': 'toggleExpando',
    },

    constructor: function() {
      Backbone.View.prototype.constructor.apply(this, _.toArray(arguments));

      this.afterInitialize();
    },

    initialize: function() {
      this.$button = this.$el.find(this.buttonSelector);
      this.$expando = this.$el.find(this.expandoSelector);
    },

    afterInitialize: function() {
      this.expand();
    },

    toggleExpando: function(e) {
      if (isPluginExpandoButton(e.target)) { return; }

      this.expanded ? this.collapse() : this.expand();
    },

    expand: function() {
      this.$button.addClass('expanded')
                  .removeClass('collapsed');
      this.expanded = true;
      this.show();
    },

    show: function() {
      this.$expando.show();
    },

    collapse: function() {
      this.$button.addClass('collapsed')
                  .removeClass('expanded');
      this.expanded = false;
      this.hide();
    },

    hide: function() {
      this.$expando.hide();
    }
  });

  var LinkExpando = Expando.extend({
    events: _.extend({}, Expando.prototype.events, {
      'click .open-expando': 'expand',
    }),

    initialize: function() {
      Expando.prototype.initialize.call(this);

      this.cachedHTML = this.$expando.data('cachedhtml');
      this.loaded = !!this.cachedHTML;
      this.id = this.$el.thing_id();
      this.isNSFW = this.$el.hasClass('over18');
      this.linkType = this.$el.hasClass('self') ? 'self' : 'link';
      this.autoexpanded = this.options.autoexpanded;

      if (this.autoexpanded) {
        this.loaded = true;
        this.cachedHTML = this.$expando.html();
      }

      var $e = $.Event('expando:create', { expando: this });
      $(document.body).trigger($e);

      if ($e.isDefaultPrevented()) { return; }

      $(document).on('hide_thing_' + this.id, function() {
        this.collapse();
      }.bind(this));

      // expando events
      var linkURL = this.$el.children('.entry').find('a.title').attr('href');

      if (/^\//.test(linkURL)) {
        var protocol = window.location.protocol;
        var hostname = window.location.hostname;
        linkURL = protocol + '//' + hostname + linkURL;
      }

      // event context
      var eventData = {
        linkIsNSFW: this.isNSFW,
        linkType: this.linkType,
        linkURL: linkURL,
      };
      
      // note that hyphenated data attributes will be converted to camelCase
      var thingData = this.$el.data();

      if ('fullname' in thingData) {
        eventData.linkFullname = thingData.fullname;
      }

      if ('timestamp' in thingData) {
        eventData.linkCreated = thingData.timestamp;
      }

      if ('domain' in thingData) {
        eventData.linkDomain = thingData.domain;
      }

      if ('authorFullname' in thingData) {
        eventData.authorFullname = thingData.authorFullname;
      }

      if ('subreddit' in thingData) {
        eventData.subredditName = thingData.subreddit;
      }

      if ('subredditFullname' in thingData) {
        eventData.subredditFullname = thingData.subredditFullname;
      }

      this._expandoEventData = eventData;
    },

    collapse: function() {
      LinkExpando.__super__.collapse.call(this);
      this.autoexpanded = false;
    },

    show: function() {
      if (!this.loaded) {
        return $.request('expando', { link_id: this.id }, function(res) {
          var expandoHTML = $.unsafe(res);
          this.cachedHTML = expandoHTML;
          this.loaded = true;
          this.show();
        }.bind(this), false, 'html', true);
      }

      var $e = $.Event('expando:show', { expando: this });
      this.$el.trigger($e);

      if ($e.isDefaultPrevented()) { return; }

      if (!this.autoexpanded) {
        this.$expando.html(this.cachedHTML);
      }

      if (!this._expandoEventData.provider) {
        // this needs to be deferred until the actual embed markup is available.
        var $media = this.$expando.children();

        if ($media.is('iframe')) {
          this._expandoEventData.provider = 'embedly';
        } else {
          this._expandoEventData.provider = 'reddit';
        }
      }

      this.showExpandoContent();
      this.fireExpandEvent();
    },

    showExpandoContent: function() {
      this.$expando.removeClass('expando-uninitialized');
      this.$expando.show();
    },

    fireExpandEvent: function() {
      if (this.autoexpanded) {
        this.autoexpanded = false;
        r.analytics.expandoEvent('expand_default', this._expandoEventData);
      } else {
        r.analytics.expandoEvent('expand_user', this._expandoEventData);
      }
    },

    hide: function() {
      var $e = $.Event('expando:hide', { expando: this });
      this.$el.trigger($e);

      if ($e.isDefaultPrevented()) { return; }

      this.hideExpandoContent();
      this.fireCollapseEvent();
    },

    hideExpandoContent: function() {
      this.$expando.hide().empty();
    },

    fireCollapseEvent: function() {
      r.analytics.expandoEvent('collapse_user', this._expandoEventData);
    },
  });

  var SearchResultLinkExpando = Expando.extend({
    buttonSelector: '.search-expando-button',
    expandoSelector: '.search-expando',

    events: {
      'click .search-expando-button': 'toggleExpando',
    },

    afterInitialize: function() {
      var expandoHeight = this.$expando.innerHeight();
      var contentHeight = this.$expando.find('.search-result-body').innerHeight();

      if (contentHeight <= expandoHeight) {
        this.$button.remove();
        this.$expando.removeClass('collapsed');
        this.undelegateEvents();
      } else if (this.options.expanded) {
        this.expand();
      }
    },

    show: function() {
      this.$expando.removeClass('collapsed');
    },

    hide: function() {
      this.$expando.addClass('collapsed');
    },
  });

  $(function() {
    r.hooks.get('expando-pre-init').call();

    var listingSelectors = [
      '.linklisting',
      '.organic-listing',
      '.selfserve-subreddit-links',
    ];

    function initExpando($thing, autoexpanded) {
      if ($thing.data('expando')) {
        return;
      }

      $thing.data('expando', true);

      var view = new LinkExpando({
        el: $thing[0],
        autoexpanded: autoexpanded,
      });
    }

    $(listingSelectors.join(',')).on('click', '.expando-button', function(e) {
      if (isPluginExpandoButton(e.target)) { return; }

      var $thing = $(this).closest('.thing')
      initExpando($thing, false);
    });

    $('.link .expando-button.expanded').each(function() {
      var $thing = $(this).closest('.thing');
      initExpando($thing, true);
    });

    var searchResultLinkThings = $('.search-expando-button').closest('.search-result-link');

    searchResultLinkThings.each(function() {
      new SearchResultLinkExpando({ el: this });
    });
  });
}(r);
