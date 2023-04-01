!function(r, _, $) {
  r.spotlight = {
    _bindEvents: function() {
      // unbind everything and then selectively rebind
      this.$listing.off('.spotlight');
      this.$listing.find('.arrow.prev').off('.spotlight');
      this.$listing.find('.arrow.next').off('.spotlight');
      $(document).off('.spotlight');
      $(window).off('.spotlight');

      this.$listing.on('click.spotlight', function(e) {
        var $target = $(e.target);
        if ($target.is('.thumbnail, .title')) {
          this.adWasClicked = true;
        }
      }.bind(this));

      if (this.$listing.length) {
        this.$listing.find('.arrow.prev').on('click.spotlight', this.prev);
        this.$listing.find('.arrow.next').on('click.spotlight', this.next);
      }

      if (this.showPromo) {
        // IE 9 and below do not have this prop or work with
        // visibilitychange.
        if ('hidden' in document) {
          $(document).on('visibilitychange.spotlight', this._requestOrSaveTimestamp.bind(this));
        } else {
          $(window).on('focus.spotlight blur.spotlight', this._requestOrSaveTimestamp.bind(this));
        }
      }
    },

    setup: function(organicLinks, interestProb, showPromo, srnames) {
      this.organics = [];
      this.lineup = [];
      this.adWasClicked = false;
      this.interestProb = interestProb;
      this.showPromo = showPromo;
      this.srnames = srnames;
      this.loid = $.cookie('loid');
      this.lastTabChangeTimestamp = Date.now();
      this.MIN_PROMO_TIME = 3000;
      this.next = this._advance.bind(this, 1);
      this.prev = this._advance.bind(this, -1);
      this.$listing = $('.organic-listing');
      this.adBlockIsEnabled = $('#siteTable_organic').is(":hidden");

      if (this.adBlockIsEnabled) {
        this.showPromo = false;
      }

      this._bindEvents();

      organicLinks.forEach(function(name) {
        this.organics.push(name);
        this.lineup.push({ fullname: name, });
      }, this);

      if (interestProb) {
        this.lineup.push('.interestbar');
      }

      var selectedThing;
      var lastClickFullname = r.analytics.breadcrumbs.lastClickFullname();
      var $lastClickThing = $(lastClickFullname ? '.id-' + lastClickFullname : null);

      if ($lastClickThing.length && this.$listing.has($lastClickThing).length) {
        r.debug('restoring spotlight selection to last click');
        selectedThing = { fullname: lastClickFullname, };
      } else {
        var shouldForcePromo = this._isDocumentVisible() && this.showPromo;
        selectedThing = this.chooseRandom(shouldForcePromo);
      }

      this.lineup = _.chain(this.lineup)
        .reject(function(el) {
          return _.isEqual(selectedThing, el);
        })
        .shuffle()
        .unshift(selectedThing)
        .value();

      this.lineup.pos = 0;
      this._advance(0);
    },

    _requestOrSaveTimestamp: function() {
      if ( this._isDocumentVisible() ) {
        this.requestNewPromo();
      } else {
        this.lastTabChangeTimestamp = Date.now();
      }
    },

    _isDocumentVisible: function () {
      if ('hidden' in document) {
        return !document.hidden;
      } else {
        return document.hasFocus();
      }
    },

    requestNewPromo: function() {
      var $promotedLink = this.$listing.find('.promotedlink');
      // if there isn't an ad visible currently don't fetch a new one
      if (!$promotedLink.is(':visible')) {
        return;
      }
      // we don't want to fetch a new ad when the user has clicked so they 
      // can have a chance to vote or comment on the last ad.
      if (this.adWasClicked) {
        return;
      }

      
      var $clearLeft = $promotedLink.next('.clearleft');

      if (this.adBlockIsEnabled ||
          Date.now() - this.lastTabChangeTimestamp < this.MIN_PROMO_TIME) {
        return;
      }

      if ($promotedLink.length && $promotedLink.offset().top < window.scrollY) {
        return;
      }

      var newPromo = this.requestPromo({
        refresh: true,
      });

      newPromo.then(function($promo) {
        if (!$promo || !$promo.length) {
          return;
        }

        var $link = $promo.eq(0);
        var fullname = $link.data('fullname');

        if ($promotedLink.length) {
          this.organics[this.lineup.pos] = fullname;
          this.lineup[this.lineup.pos] = newPromo;
        } else {
          this.organics[this.lineup.pos + 1] = fullname;
          this.lineup[this.lineup.pos + 1] = newPromo;
        }

        if (!$link.hasClass('adsense-wrap')) {
          if ($promotedLink.length) {
            $promotedLink.add($clearLeft).remove(); 
            $promo.show();            
          } else {
            this.next()
          }
        }
        // force a redraw to prevent showing duplicate ads
        this.$listing.hide().show();
      }.bind(this));
    },

    requestPromo: function(options) {
      options = options || {};

      return $.ajax({
        type: 'POST',
        url: '/api/request_promo',
        timeout: 1000,
        data: {
          srnames: this.srnames,
          r: r.config.post_site,
          loid: this.loid,
          is_refresh: options.refresh,
        },
      }).pipe(function(promo) {
        var prevPromo = this.$listing.find('.promotedlink')
        if (promo) {
          if (this.showPromo) {
            $('#siteTable_organic').show('slow');
          }

          var $item = $(promo);
          // adsense will throw error if inserted while hidden
          if (!$item.hasClass('adsense-wrap')) {
            $item.hide().appendTo(this.$listing);
          } else {
            var $promotedLink = this.$listing.find('.promotedlink');
            $promotedLink.remove()
            $item.appendTo(this.$listing);
          }
          return $item;
        } else {
          if (!prevPromo.length && !this.organics.length) {
            // spotlight box must be hidden when no ad is returned
            // and there is no other content.
            $('#siteTable_organic').hide();
          }
          return false;
        }
      }.bind(this));
    },

    chooseRandom: function(forcePromo) {
      if (forcePromo) {
        return this.requestPromo();
      } else if (Math.random() < this.interestProb) {
        return '.interestbar';
      } else {
        var name = this.organics[_.random(this.organics.length)];
        return (name) ? { fullname: name, } : null;
      }
    },

    _materialize: function(item) {
      if (!item || item instanceof $ || item.promise) {
        return item;
      }

      var itemSel;

      if (typeof item === 'string') {
        itemSel = item;
      } else if (item.campaign) {
        itemSel = '[data-cid="' + item.campaign + '"]';
      } else {
        itemSel = '[data-fullname="' + item.fullname + '"]';
      }

      var $item = this.$listing.find(itemSel);

      if ($item.length) {
        return $item;
      } else {
        r.error('unable to locate spotlight item', itemSel, item);
      }
    },

    _advancePos: function(dir) {
      return (this.lineup.pos + dir + this.lineup.length) % this.lineup.length;
    },

    _materializePos: function(pos) {
      return this.lineup[pos] = this._materialize(this.lineup[pos]);
    },

    _advance: function(dir) {
      var $nextprev = this.$listing.find('.nextprev');
      var $visible = this.$listing.find('.thing:visible');
      var nextPos = this._advancePos(dir);
      var $next = this._materializePos(nextPos);

      var showWorking = setTimeout(function() {
        $nextprev.toggleClass('working', $next.state && $next.state() == 'pending');
      }, 200);

      this.lineup.pos = nextPos;
      var $nextLoad = $.when($next);

      $nextLoad.always(function($next) {
        clearTimeout(showWorking);

        if (this.lineup.pos != nextPos) {
          // we've been passed!
          return;
        }

        if ($nextLoad.state() == 'rejected' || !$next) {
          if (this.lineup.length > 1) {
            this._advance(dir || 1);
            return;
          } else {
            this.$listing.hide();
            return;
          }
        }

        $nextprev.removeClass('working');
        this.$listing.removeClass('loading');

        // match the listing background to that of the displayed thing
        if ($next) {
          var nextColor = $next.css('background-color');
          if (nextColor) {
            this.$listing.css('background-color', nextColor);
          }
        }

        $visible.hide();
        $next.show();
        this.help($next);

        // prefetch forward and backward if advanced beyond default state
        if (this.lineup.pos != 0) {
          this._materializePos(this._advancePos(1));
          this._materializePos(this._advancePos(-1));
        }
      }.bind(this));
    },

    help: function($thing) {
      var $help = $('#spotlight-help');

      if (!$help.length) {
        return;
      }

      // this function can be called before the help bubble has initialized
      $(function() {
        var help = $help.data('HelpBubble');

        // `r.ui.refreshListing` replaces the help and it needs
        // to be reinitialized.
        if (!help) {
          help = new r.ui.Bubble({el: $help.get(0)});
        }

        help.hide(function() {
          $help.find('.help-section').hide();
          if ($thing.hasClass('promoted')) {
            $help.find('.help-promoted').show();
          } else if ($thing.hasClass('interestbar')) {
            $help.find('.help-interestbar').show();
          } else if ($thing.hasClass('adsense-wrap')) {
            $help.find('.help-adserver').show()
          } else {
            $help.find('.help-organic').show();
          }
        });
      });
    },
  };
}(r, _, jQuery);
