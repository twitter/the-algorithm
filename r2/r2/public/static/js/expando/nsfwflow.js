/*
  NSFW flow for expanded media

  In non-NSFW listings, we sometimes want to show an interstitial over NSFW
  expando content

  1. If the user has the "no_profanity" preference turned ON (normal)
  2. If a user has not seen the interstitial before (warning)

  In the former case, the user is given an option to turn the preference OFF
  inline.  In the latter case, the user is informed that the content will be
  displayed automatically in the future and given an option to turn the
  preference ON.

  On media embeds (i.e. embedly stuff) the content is replaced by a flat grey
  box.  On media previews (i.e. our images) a blurred version of the image
  is displayed with the interstitial prompt overlaid.
 */
!function(r, undefined) {
  var _state = {
    over18Listing: false,
    noProfanity: true,
    nsfwMediaAcknowledged: false,
    loggedIn: false,
  };

  r.hooks.get('setup').register(function() {
    _state.over18Listing = r.config.listing_over_18;
    _state.noProfanity = r.config.pref_no_profanity;
    _state.nsfwMediaAcknowledged = r.config.nsfw_media_acknowledged;
    _state.loggedIn = !!r.config.logged;
  });

  /*
  The form used to update the user's no_profanity preference AND turn the
  nsfw_media_acknowledged flag ON.  This is normally shown only after the
  "Always show NSFW media?" link is clicked as a method of *undoing* the change,
  but is shown by default on 1st-time warnings as a method of turning ON the
  no_profanity preference.
   */
  var ExpandoInlinePreferenceForm = r.ui.FormBar.extend({
    doneString: r._('We will ask before showing NSFW media.'),

    defaults: {
      text: '',
      buttonLabel: '',
      key: 'show_nsfw_media',
      value: '',
    },

    render: function(templateProps) {
      this._lastRenderProps = templateProps;
      ExpandoInlinePreferenceForm.__super__.render.call(this, templateProps);
    },

    onSubmit: function(e) {
      ExpandoInlinePreferenceForm.__super__.onSubmit.call(this, e);
      var templateProps = _.defaults({
        text: this.doneString,
      }, this._lastRenderProps);
      this.render(templateProps);
      this.$el.addClass('expando-nsfw-flow-complete');
      this.$el.find('button').remove();
      this.$el.find('form').attr('disabled', true);
    },
  });


  var ExpandoNSFWGate = Backbone.View.extend({
    templateName: 'expando/nsfwgate',
    
    events: {
      'click .expando-nsfw-gate-show-once': 'onButtonClick',
      'click .expando-nsfw-gate-text': 'onTextClick',
    },

    defaults: {
      buttonLabel: r._('Click to see NSFW'),
      text: '',
      type: 'normal',
      style: 'interstitial',
    },

    initialize: function() {
      this.render(this.options);
    },

    render: function(templateProps) {
      templateProps = _.defaults(templateProps, this.defaults);
      var content = r.templates.make(this.templateName, templateProps);
      this.$el.html(content); 
    },

    onButtonClick: function(e) {
      e.preventDefault();
      this.trigger('click:button');
    },

    onTextClick: function(e) {
      e.preventDefault();
      this.trigger('click:text');
    },
  });

  /*
  View controller
   */
  var ExpandoNSFWFlow = Backbone.View.extend({
    strings: {
      // normal, linkified text
      normal: r._('Always show NSFW media?'),
      changedToShow: r._('Ok, we changed your preferences to always show NSFW media.'),
      changedToShowLabel: r._('Undo'),

      // first time/warning
      warning: r._('Your account is set up to always show this content in the future.'),
      alwaysAsk: r._('Change your preferences to hide NSFW media?'),
      alwaysAskLabel: r._('Hide NSFW'),
    },

    initialize: function() {      
      var width = this.options.width;
      var height = this.options.height;
      var isOverlay = this.options.isOverlay;
      var isWarning = this.options.isWarning;
      
      this.isOverlay = this.options.isOverlay;
      this.isWarning = this.options.isWarning;

      var interstitialOptions = {
        width: width ? width + 'px' : '100%',
        height: height ? height + 'px' : '100%',
        style: isOverlay ? 'overlay' : 'interstitial',
        type: isWarning ? 'warning' : 'normal',
      };

      if (_state.loggedIn) {
        if (this.isWarning) {
          interstitialOptions.text = this.strings.warning;
        } else {
          interstitialOptions.text = this.strings.normal;
        }
      }

      this.interstitial = new ExpandoNSFWGate(interstitialOptions);
      this.$el.append(this.interstitial.el);
      this.listenTo(this.interstitial, 'click:button', this.onShowOnce);

      if (!_state.loggedIn) { return; }

      if (this.isWarning) {
        this.initFormBar();
      } else {
        this.listenTo(this.interstitial, 'click:text', this.onShowAlways);
      }
    },

    initFormBar: function() {
      var text, buttonLabel;

      if (this.isWarning) {
        text = this.strings.alwaysAsk;
        buttonLabel = this.strings.alwaysAskLabel;
      } else {
        text = this.strings.changedToShow;
        buttonLabel = this.strings.changedToShowLabel;
      }
      
      this.formBar = new ExpandoInlinePreferenceForm({
        text: text,
        buttonLabel: buttonLabel,
        value: 'off',
      });
      this.$el.append(this.formBar.el);
      this.listenTo(this.formBar, 'submit', this.updatePref);
    },

    onShowOnce: function() {
      if (!_state.nsfwMediaAcknowledged) {
        _state.nsfwMediaAcknowledged = true
        this._updateNsfwMediaPrefs({}, this.show.bind(this));
      } else {
        this.show();
      }
    },

    onShowAlways: function() {      
      this.updatePref({ show_nsfw_media: 'on' });
      this.initFormBar();
    },

    updatePref: function(fields) {
      _state.noProfanity = fields.show_nsfw_media === 'off';
      this._updateNsfwMediaPrefs(fields, function() {
        if (fields.show_nsfw_media === 'on') {
          this.show();
        }
      }.bind(this));
    },

    _updateNsfwMediaPrefs: function(fields, cb) {
      $.request('set_nsfw_media_pref', fields, function(res) {
        if (cb) { cb(); }
      }, false, 'json', false);
    },

    show: function() {
      this.trigger('show');
      if (this.isOverlay) {
        this.interstitial.$el.fadeOut();
      } else {
        this.interstitial.$el.remove();
      }
    },
  });

  /*
  hook into expando events to inject the nsfw flow UI, so the main expando.js
  code dosen't need to know about all the NSFW flow logic.
   */
  r.hooks.get('expando-pre-init').register(function() {
    $(document.body).on('expando:create', function(e) {
      var expando = e.expando;

      var showInterstitial = (
        // must be on a mixed-content listing
        !_state.over18Listing &&
        // and either have the "safe(r) for work" preference on
        // or this is your first time seeing a nsfw media interstitial
        (_state.noProfanity || !_state.nsfwMediaAcknowledged) &&
        // and this is an NSFW link
        expando.isNSFW && expando.linkType == 'link'
      );

      if (!showInterstitial) { return; }

      var nsfwFlow;

      function showNsfwFlow(e) {
        e.preventDefault();

        if (!expando.autoexpanded) {
          expando.$expando.html(expando.cachedHTML);
        }

        var $expando = expando.$expando;
        var $media = $expando.children();
        var width = $media.width();
        var height = $media.height();
        // if it's a `.media-preview`, interstial is shown _over_ the blurred image
        var isOverlay = $media.hasClass('media-preview');
        var isWarning = !_state.noProfanity;

        nsfwFlow = new ExpandoNSFWFlow({
          width: width,
          height: height,
          isOverlay: isOverlay,
          isWarning: isWarning,
        });

        expando.$expando.addClass('expando-with-nsfw-interstitial');
        expando.listenTo(nsfwFlow, 'show', destroyNsfwFlow);

        if (nsfwFlow.isOverlay) {
          $media.append(nsfwFlow.el);
        } else {
          $expando.html(nsfwFlow.el);
        }

        expando.showExpandoContent();
      }

      function hideNsfwFlow(e) {
        e.preventDefault();
        expando.hideExpandoContent();
      }

      function destroyNsfwFlow() {
        expando.stopListening(nsfwFlow, 'show', destroyNsfwFlow);
        expando.$expando.removeClass('expando-with-nsfw-interstitial')
        if (!nsfwFlow.isOverlay) {
          expando.$expando.prepend(expando.cachedHTML);
        }
        expando.fireExpandEvent();
        expando.$el.off('expando:show', showNsfwFlow);
        expando.$el.off('expando:hide', hideNsfwFlow);
      }

      expando.$el.on('expando:show', showNsfwFlow);
      expando.$el.on('expando:hide', hideNsfwFlow);
    });
  });
}(r);
