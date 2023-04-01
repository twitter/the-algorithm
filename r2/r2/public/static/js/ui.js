r.ui.init = function() {
    // welcome bar
    if ($.cookie('reddit_first')) {
        // save welcome seen state and delete obsolete cookie
        $.cookie('reddit_first', null, {domain: r.config.cur_domain})
        store.safeSet('ui.shown.welcome', true)
    } else if (store.safeGet('ui.shown.welcome') != true) {
        $('.infobar.welcome').show()
        store.safeSet('ui.shown.welcome', true)
    }

    var smallScreen = r.ui.isSmallScreen();

    // mweb beta banner
    var mwebOptInCookieName = "__cf_mob_redir";
    var onFrontPage = $.url().attr('path') == '/';
    if (smallScreen && onFrontPage && r.config.renderstyle != 'compact' && !r.ui.inMobileWebBlacklist()) {
        var a = document.createElement('a');
        a.href = window.location;
        a.host = 'm.' + r.config.cur_domain;
        a.search += (a.search ? '&' : '?') + 'ref=mobile_beta_banner&ref_source=desktop'
        var url = a.href;

        var $bar = $(_.template(
          '<a href="<%- url %>" class="mobile-web-redirect"><%- button_text %></a>', {
            url: url,
            button_text: r._("switch to mobile version"),
          }));

        $bar.on('click', function() {
           $.cookie(mwebOptInCookieName, '1', {
               domain: r.config.cur_domain,
               path:'/',
               expires: 90
            });

           // redirect
           return true;
        });

        $('#header').before($bar)
    }

    $('.help-bubble').each(function(idx, el) {
        $(el).data('HelpBubble', new r.ui.Bubble({el: el}))
    })

    $('.submit_text').each(function(idx, el) {
        $(el).data('SubredditSubmitText', new r.ui.SubredditSubmitText({el: el}))
    })

    /* Open links in new tabs if they have the preference set or are logged out
     * and on a "large" screen. */
    if (r.config.new_window && (r.config.logged || !smallScreen)) {
        $(document.body).on('click', 'a.may-blank, .may-blank-within a', function(e) {

            if (!this.target) {
                // Trident doesn't support `rel="noreferrer"` and requires a
                // fallback to make sure `window.opener` is unset
                var isWebLink = _.contains(['http:', 'https:'], this.protocol);
                if (this.href && isWebLink && r.utils.onTrident()) {
                    var w = window.open(this.href, '_blank');
                    // some popup blockers appear to return null for
                    // `window.open` even inside click handlers.
                    if (w !== null) {
                        // try to nullify `window.opener` so the new tab can't
                        // navigate us
                        w.opener = null;
                        // suppress normal link opening behaviour
                        e.preventDefault();
                        return false;
                    }
                }

                this.target = '_blank';
                // Required so the tabs can't navigate us via `window.opener`
                this.rel = 'noreferrer';
            }

            return true; // continue bubbling
        })
    }

    r.ui.PermissionEditor.init()

    r.ui.initLiveTimestamps()

    r.ui.initNewCommentHighlighting()

    r.ui.initReadNext();

    r.ui.initTimings();
}

r.ui.inMobileWebBlacklist = function() {
  return _.any(r.config.mweb_blacklist_expressions, function(regex) {
    return (new RegExp(regex)).test(window.location.pathname)
  });
}

r.ui.isSmallScreen = function() {
 return window.matchMedia
          // 736px is the width of the iPhone 6+.
          ? matchMedia('(max-device-width: 736px)').matches
          : $(window).width() < 736;
}

r.ui.TimeTextScrollListener = r.ScrollUpdater.extend({
    initialize: function(options) {
        this.timeText = options.timeText
        this.timeText.updateCache($(this.selector))
    },
    selector: '.live-timestamp:visible',
    endUpdate: function($els) {
        this.timeText.updateCache($els)
    }
})

r.ui.initLiveTimestamps = function() {
    // We only want a global timestamp scroll listener to instantiate on
    // pages with `thing`s. Since we don't have a router yet, we'll scope
    // the element to `.sitetable`s, which will contain it. This is kind of a
    // dirty hack and should be obsoleted by a router + view system.
    if ($('.sitetable').length) {
      var listener = new r.ui.TimeTextScrollListener({
        el: '.sitetable',
        timeText: new r.TimeText,
      })
      listener.start()

      // Every time we add a new `thing`, we'll need to re-grab our element caches.
      $(document).on('new_things_inserted', function() {
          listener.restart()
      })
    }
}

r.ui.initNewCommentHighlighting = function() {
  if (!$('body').hasClass('comments-page')) {
    return;
  }

  $visitSelector = $('#comment-visits');
  if ($visitSelector.length === 0) {
    return;
  }

  $(document).on('new_things_inserted', r.ui.highlightNewComments);
  $visitSelector.on('change', r.ui.highlightNewComments);
  r.ui.highlightNewComments();
}

r.ui.highlightNewComments = function() {
  var $comments = $('.comment');
  var selectedVisitTimestamp = $('#comment-visits').val();
  var selectedVisit;

  if (selectedVisitTimestamp) {
    selectedVisit = Date.parse(selectedVisitTimestamp);
  }

  $comments.each(function() {
    var $commentEl = $(this);
    var $timeEl = $commentEl.find('> .entry .tagline time:first-of-type');
    var commentTime = r.utils.parseTimestamp($timeEl);
    var shouldHighlight = !!selectedVisit && commentTime > selectedVisit;

    $commentEl.toggleClass('new-comment', shouldHighlight);
  });
}

r.ui.initReadNext = function() {
    // 2 week expiration
    var ttl = (1000 * 60 * 60 * 24 * 14);
    var $readNextContainer = $('.read-next-container');
    var isDismissed = !!store.safeGet('readnext.dismissed');
    var expiration = parseInt(store.safeGet('readnext.expiration'), 10);
    var now = Date.now();

    if (isDismissed) {
        if (!expiration) {
            expiration = now + ttl;
            store.safeSet('readnext.expiration', expiration);
        } else if (expiration < now) {
            store.safeSet('readnext.dismissed', false);
            isDismissed = false;
        }
    }

    var currentLinkFullname = r.config.cur_link;

    if (isDismissed || !$readNextContainer.length) {
        return;
    }

    this.readNext = new r.ui.ReadNext({
        el: $readNextContainer,
        fixToBottom: !r.ui.isSmallScreen(),
        currentLinkFullname: currentLinkFullname,
        ttl: ttl,
    });
};

r.ui.ReadNext = Backbone.View.extend({
    events: {
        'click .read-next-button.next': 'next',
        'click .read-next-button.prev': 'prev',
        'click .read-next-dismiss': 'dismiss',
    },

    initialize: function() {
        this.$readNext = this.$el.find('.read-next');
        this.$links = this.$readNext.find('.read-next-link');
        this.numLinks = this.$links.length;

        this.state = new Backbone.Model({
            fixed: false,
            index: -1,
        });

        this.state.on('change', this.render.bind(this));
        
        if (this.options.fixToBottom) {
            this.updateScroll = this.updateScroll.bind(this);
            window.addEventListener('scroll', this.updateScroll);
            this.updateScroll();
        }

        var currentLinkId = '#read-next-link-' + this.options.currentLinkFullname;
        var startingIndex = this.$links.index($(currentLinkId)) + 1;

        this.state.set({
            index: startingIndex,
        });

        this.resetRefIndicies(startingIndex);
        this.$readNext.addClass('active');
    },

    resetRefIndicies: function(startingIndex) {
        var a = document.createElement('a');

        this.$links.toArray().forEach(function(link, i) {
            var url = $.url(link.href);
            var params = url.param();
            if (!params.ref) {
                return;
            }
            var relativeIndex = this.moduloIndex(i - startingIndex);
            params.ref = params.ref.split('_')[0] + '_' + relativeIndex;
            a.href = link.href;
            a.search = $.param(params);
            link.href = a.href;
        }, this);
    },

    moduloIndex: function(i) {
        var numLinks = this.numLinks;
        return (i + numLinks) % numLinks;
    },

    next: function() {
        var currentIndex = this.state.get('index');
        this.state.set({
            index: this.moduloIndex(currentIndex + 1),
        });
        r.analytics.fireGAEvent('readnext', 'nav-next');
    },

    prev: function() {
        var currentIndex = this.state.get('index');
        var numLinks = this.numLinks;
        this.state.set({
            index: this.moduloIndex(currentIndex - 1),
        });
        r.analytics.fireGAEvent('readnext', 'nav-prev');
    },

    dismiss: function() {
        this.$el.fadeOut();
        window.removeEventListener('scroll', this.updateScroll);
        r.analytics.fireGAEvent('readnext', 'dismiss');
        store.safeSet('readnext.dismissed', true);
        var expiration = Date.now() + this.options.ttl;
        store.safeSet('readnext.expiration', expiration);
    },

    updateScroll: function() {
        var scrollPosition = window.scrollY;
        var nodePosition = this.$el.position().top;

        // stick to bottom    
        var scrollOffset = window.innerHeight;
        var nodeOffset = this.$readNext.height();
        scrollPosition += scrollOffset;
        nodePosition += nodeOffset;

        this.state.set({
            fixed: scrollPosition >= nodePosition,
        });
    },

    render: function() {
        var currentIndex = this.state.get('index');
        var fixedPosition = this.state.get('fixed');

        this.$links.removeClass('active');
        this.$links.eq(currentIndex).addClass('active');

        if (fixedPosition) {
            this.$readNext.addClass('fixed');
        } else {
            this.$readNext.removeClass('fixed');
        } 
    },
});


r.ui.initTimings = function() {
  // return if we're not configured for sending stats
  if (!r.config.pageInfo.actionName || !r.config.stats_domain) {
    return
  }

  // Sample based on the configuration sample rate
  if (Math.random() > r.config.stats_sample_rate / 100) {
    return
  }

  var browserTimings = new r.NavigationTimings()

  $(function() {
    _.defer(function() {
      browserTimings.fetch()

      var timingData = browserTimings.filter(function(t) {
        return t.get('key') !== 'start'
      }).reduce(function(o, t) {
        if (!t.isValid()) { return o }

        var val = t.duration()

        if (val > 0) {
        // Add 'Timing' because some of these keys clobber globals in pylons
          var key = t.get('key') + 'Timing'
          o[key] = val
        }

        return o
      }, {})

      timingData.actionName = r.config.pageInfo.actionName
      timingData.verification = r.config.pageInfo.verification

      $.ajax({
        type: 'POST',
        url: r.config.stats_domain,
        data: JSON.stringify({ rum: timingData  }),
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
      })
    })
  })
}

r.ui.showWorkingDeferred = function(el, deferred) {
    if (!deferred) {
        return
    }

    var flickerDelay = 200,
        key = '_workingCount',
        $el = $(el)

    // keep a count of active calls on this element so we can track multiple
    // deferreds at the same time.
    $el.data(key, ($el.data(key) || 0) + 1)

    // prevent flicker
    var flickerTimeout = setTimeout(function() {
        $el.addClass('working')
    }, flickerDelay)

    deferred.always(function() {
        clearTimeout(flickerTimeout)
        var count = Math.max(0, $el.data(key) - 1)
        $el.data(key, count)
        if (count == 0) {
            $el.removeClass('working')
        }
    })

    return deferred
}

r.ui.refreshListing = function() {
    var url = $.url(),
        params = url.param()
    params['bare'] = 'y'
    return $.ajax({
        type: 'GET',
        url: url.attr('base') + url.attr('path'),
        data: params
    }).done(function(resp) {
        $('body > .content')
            .html(resp)
            .find('.promotedlink.promoted:visible')
                .trigger('onshow')
    })
}

r.ui.Form = function(el) {
    r.ui.Base.call(this, el)
    this.$el.submit($.proxy(function(e) {
        e.preventDefault()
        this.submit(e)
    }, this))

    this.$el.find('[data-validate-url]')
        .validator({ https: !!r.config.https_endpoint })
        .on('initialize.validator', function(e) {
            var $el = $(this);

            if ($el.hasClass('c-has-error')) {
                $el.stateify('showError');
            }
        })
        .on('valid.validator', function(e) {
            $(this).stateify('set', 'success');
        })
        .on('invalid.validator', function(e, resp) {
            // resp may not always be set if client side validation triggered, like
            // from input type=email
            if (resp) {
              var error = r.utils.parseError(resp.errors[0]);

              $(this).stateify('set', 'error', error.message);
            }
        })
        .on('loading.validator', function(e) {
            $(this).stateify('set', 'loading');
        })
        .on('cleared.validator', function(e) {
            $(this).stateify('clear');
        });
}
r.ui.Form.prototype = $.extend(new r.ui.Base(), {
    showStatus: function(msg, isError) {
        this.$el.find('.status, .c-alert')
            .show()
            .toggleClass('error', !!isError)
            .text(msg)
    },

    showErrors: function(errors) {
        var messages = [];

        $.each(errors, $.proxy(function(i, err) {
            var obj = r.utils.parseError(err);
            var $el = this.$el.find('.error.' + obj.name + (obj.field ? '.field-' + obj.field : ''));
            var $v2el = this.$el.filter('.form-v2').find('[name="' + obj.field + '"]');

            if ($el.length) {
                $el.show().text(obj.message);
            } else if ($v2el.length) {
                $v2el.stateify('set', 'error', obj.message);
            } else {
                messages.push(obj.message);
            }
        }, this))

        if (messages.length) {
            this.showStatus(messages.join(', '), true);
        }
    },

    resetErrors: function() {
        this.$el.find('.error').hide()
    },

    checkCaptcha: function(errors) {
        if (this.$el.has('input[name="captcha"]').length) {
            var badCaptcha = $.grep(errors, function(el) {
                return el[0] == 'badCaptcha'
            })
            if (badCaptcha) {
                $.request("new_captcha", {id: this.$el.attr('id')})
            }
        }
    },

    serialize: function() {
        return this.$el.serializeArray()
    },

    submit: function() {
        this.resetErrors()
        r.ui.showWorkingDeferred(this.$el, this._submit())
            .done($.proxy(this, 'handleResult'))
            .fail($.proxy(this, '_handleNetError'))
    },

    _submit: function() {},

    handleResult: function(result) {
        this.checkCaptcha(result.json.errors)
        this._handleResult(result)
    },

    _handleResult: function(result) {
        this.showErrors(result.json.errors)
    },

    _handleNetError: function(xhr) {
        var message = r._('an error occurred (status: %(status)s)')
                         .format({status: xhr.status})
        this.showStatus(message, true)
    }
})

r.ui.Bubble = Backbone.View.extend({
    showDelay: 150,
    hideDelay: 750,
    animateDuration: 150,

    initialize: function() {
        this.$parent = this.options.parent || this.$el.parent()
        if (this.options.trackHover != false) {
            this.$el.hover($.proxy(this, 'queueShow'), $.proxy(this, 'queueHide'))
            this.$parent.hover($.proxy(this, 'queueShow'), $.proxy(this, 'queueHide'))
            this.$parent.click($.proxy(this, 'queueShow'))
        }
    },

    position: function() {
        var parentPos = this.$parent.offset(),
            bodyOffset = $('body').offset(),
            offsetX, offsetY
        if (this.$el.is('.anchor-top') || this.$el.is('.anchor-top-centered')) {
            offsetX = this.$parent.outerWidth(true) - this.$el.outerWidth(true)
            offsetY = this.$parent.outerHeight(true) + 5
            this.$el.css({
                left: Math.max(parentPos.left + offsetX, 0),
                top: parentPos.top + offsetY - bodyOffset.top
            })
        } else if (this.$el.is('.anchor-top-left')) {
            offsetY = this.$parent.outerHeight(true) + 5
            this.$el.css({
                left: parentPos.left,
                top: parentPos.top + offsetY - bodyOffset.top
            })
        } else if (this.$el.is('.anchor-right-fixed')) {
            offsetX = 32
            offsetY = 0

            parentPos.top -= $(document).scrollTop()
            parentPos.left -= $(document).scrollLeft()

            this.$el.css({
                top: r.utils.clamp(parentPos.top - offsetY, 0, $(window).height() - this.$el.outerHeight()),
                left: r.utils.clamp(parentPos.left - offsetX - this.$el.width(), 0, $(window).width())
            })
        } else if (this.$el.is('.anchor-left')) {
            offsetX = this.$parent.outerWidth(true) + 16
            offsetY = 0
            this.$el.css({
                left: parentPos.left + offsetX,
                top: parentPos.top + offsetY - bodyOffset.top
            })
        }  else { // anchor-right
            offsetX = 16
            offsetY = 0
            parentPos.right = $(window).width() - parentPos.left
            this.$el.css({
                right: parentPos.right + offsetX,
                top: parentPos.top + offsetY - bodyOffset.top
            })
        }
    },

    show: function() {
        this.cancelTimeout()
        if (this.$el.is(':visible')) {
            return
        }

        this.trigger('show')

        $('body').append(this.$el)

        this.$el.css('visibility', 'hidden').show()
        this.render()
        this.position()
        this.$el.css({
            'opacity': 1,
            'visibility': 'visible'
        })

        var isSwitch = this.options.group && this.options.group.current && this.options.group.current != this
        if (isSwitch) {
            this.options.group.current.hideNow()
        } else {
            this._animate('show')
        }

        if (this.options.group) {
            this.options.group.current = this
        }
    },

    hideNow: function() {
        this.cancelTimeout()
        if (this.options.group && this.options.group.current == this) {
            this.options.group.current = null
        }
        this.$el.hide()
    },

    hide: function(callback) {
        if (!this.$el.is(':visible')) {
            callback && callback()
            return
        }

        this._animate('hide', $.proxy(function() {
            this.hideNow()
            callback && callback()
        }, this))
    },

    _animate: function(action, callback) {
        if (!this.animateDuration) {
            callback && callback()
            return
        }

        var animProp, animOffset
        if (this.$el.is('.anchor-top') || this.$el.is('.anchor-top-centered') || this.$el.is('.anchor-top-left')) {
            animProp = 'top'
            animOffset = '-=5'
        } else if (this.$el.is('.anchor-right-fixed')) {
            animProp = 'right'
            animOffset = '-=5'
        } else if (this.$el.is('.anchor-left')) {
            animProp = 'left'
            animOffset = '+=5'
        } else { // anchor-right
            animProp = 'right'
            animOffset = '-=5'
        }
        var curOffset = this.$el.css(animProp)

        hideProps = {'opacity': 0}
        hideProps[animProp] = animOffset
        showProps = {'opacity': 1}
        showProps[animProp] = curOffset

        var start, end
        if (action == 'show') {
            start = hideProps
            end = showProps
        } else if (action == 'hide') {
            start = showProps
            end = hideProps
        }

        this.$el
            .css(start)
            .animate(end, this.animateDuration, callback)
    },

    cancelTimeout: function() {
        if (this.timeout) {
            clearTimeout(this.timeout)
            this.timeout = null
        }
    },

    queueShow: function() {
        this.cancelTimeout()
        this.timeout = setTimeout($.proxy(this, 'show'), this.showDelay)
    },

    queueHide: function() {
        this.cancelTimeout()
        this.timeout = setTimeout($.proxy(this, 'hide'), this.hideDelay)
    }
})

r.ui.PermissionEditor = function(el) {
    r.ui.Base.call(this, el)
    var params = {}
    this.$el.find('input[type="hidden"]').each(function(idx, el) {
        params[el.name] = el.value
    })
    var permission_type = params.type
    var name = params.name
    this.form_id = permission_type + "-permissions-" + name
    this.permission_info = r.permissions[permission_type]
    this.sorted_perm_keys = $.map(this.permission_info,
                                  function(v, k) { return k })
    this.sorted_perm_keys.sort()
    this.original_perms = this._parsePerms(params.permissions)
    this.embedded = this.$el.find("form").length == 0
    this.$menu = null
    if (this.embedded) {
        this.$permissions_field = this.$el.find('input[name="permissions"]')
        this.$menu_controller = this.$el.siblings('.permissions-edit')
    } else {
        this.$menu_controller = this.$el.closest('tr').find('.permissions-edit')
    }
    this.$menu_controller.find('a').click($.proxy(this, 'show'))
    this.updateSummary()
}
r.ui.PermissionEditor.init = function() {
    function activate(target) {
        $(target).find('.permissions').each(function(idx, el) {
            $(el).data('PermissionEditor', new r.ui.PermissionEditor(el))
        })
    }
    activate('body')
    for (var permission_type in r.permissions) {
        $('.' + permission_type + '-table')
            .on('insert-row', 'tr', function(e) { activate(this) })
    }
}
r.ui.PermissionEditor.prototype = $.extend(new r.ui.Base(), {
    _parsePerms: function(permspec) {
        var perms = {}
        permspec.split(",").forEach(function(str) {
            perms[str.substring(1)] = str[0] == "+"
        })
        return perms.all ? {"all": true} : perms
    },

    _serializePerms: function(perms) {
        if (perms.all) {
            return "+all"
        } else {
            var parts = []
            for (var perm in perms) {
                parts.push((perms[perm] ? "+" : "-") + perm)
            }
            return parts.join(",")
        }
    },

    _getNewPerms: function() {
        if (!this.$menu) {
            return null
        }
        var perms = {}
        this.$menu.find('input[type="checkbox"]').each(function(idx, el) {
            perms[$(el).attr("name")] = $(el).prop("checked")
        })
        return perms
    },

    _makeMenuLabel: function(perm) {
        var update = $.proxy(this, "updateSummary")
        var info = this.permission_info[perm]
        var $input = $('<input type="checkbox">')
            .attr("name", perm)
            .prop("checked", this.original_perms[perm])
        var $label = $('<label>')
            .append($input)
            .click(function(e) { e.stopPropagation() })
        if (perm == "all") {
            $input.change(function() {
                var disabled = $input.is(":checked")
                $label.siblings()
                    .toggleClass("disabled", disabled)
                    .find('input[type="checkbox"]').prop("disabled", disabled)
                update()
            })
            $label.append(
                document.createTextNode(r._('full permissions')))
        } else if (info) {
            $input.change(update)
            $label.append(document.createTextNode(r._(info.title)))
            $label.attr("title", r._(info.description))
        }
        return $label
    },

    show: function(e) {
        if (r.access.isLinkRestricted(e.target)) {
            return;
        }

        close_menus(e)
        this.$menu = $('<div class="permission-selector drop-choices">')
        this.$menu.append(this._makeMenuLabel("all"))
        for (var i in this.sorted_perm_keys) {
            this.$menu.append(this._makeMenuLabel(this.sorted_perm_keys[i]))
        }

        this.$menu
            .on("close_menu", $.proxy(this, "hide"))
            .find("input").first().change().end()
        if (!this.embedded) {
            var $form = this.$el.find("form").clone()
            $form.attr("id", this.form_id)
            $form.click(function(e) { e.stopPropagation() })
            this.$menu.append('<hr>', $form)
            this.$permissions_field =
                this.$menu.find('input[name="permissions"]')
        }
        this.$menu_controller.parent().append(this.$menu)
        open_menu(this.$menu_controller[0])
        return false
    },

    hide: function() {
        if (this.$menu) {
            if (this.embedded) {
                this.original_perms = this._getNewPerms()
                this.$permissions_field
                    .val(this._serializePerms(this.original_perms))
            }
            this.$menu.remove()
            this.$menu = null
            this.updateSummary()
        }
    },

    _renderBit: function(perm) {
        var info = this.permission_info[perm]
        var text
        if (perm == "all") {
            text = r._("full permissions")
        } else if (info) {
            text = r._(info.title)
        } else {
            text = perm
        }
        var $span = $('<span class="permission-bit"/>').text(text)
        if (info) {
            $span.attr("title", r._(info.description))
        }
        return $span
    },

    updateSummary: function() {
        var new_perms = this._getNewPerms()
        var spans = []
        if (new_perms && new_perms.all) {
            spans.push(this._renderBit("all")
                .toggleClass("added", this.original_perms.all != true))
        } else {
            if (this.original_perms.all && !new_perms) {
                spans.push(this._renderBit("all"))
            } else if (!this.original_perms.all) {
                for (var perm in this.original_perms) {
                    if (this.original_perms[perm]) {
                        if (this.embedded && !(new_perms && !new_perms[perm])) {
                            spans.push(this._renderBit(perm))
                        }
                        if (!this.embedded) {
                            spans.push(this._renderBit(perm)
                                .toggleClass("removed",
                                             new_perms != null
                                             && !new_perms[perm]))
                        }
                    }
                }
            }
            if (new_perms) {
                for (var perm in new_perms) {
                    if (this.permission_info[perm] && new_perms[perm]
                        && !this.original_perms[perm]) {
                        spans.push(this._renderBit(perm)
                            .toggleClass("added", !this.embedded))
                    }
                }
            }
        }
        if (!spans.length) {
            spans.push($('<span class="permission-bit">')
                .text(r._('no permissions'))
                .addClass("none"))
        }
        var $new_summary = $('<div class="permission-summary">')
        for (var i = 0; i < spans.length; i++) {
            if (i > 0) {
                $new_summary.append(", ")
            }
            $new_summary.append(spans[i])
        }
        $new_summary.toggleClass("edited", this.$menu != null)
        this.$el.find(".permission-summary").replaceWith($new_summary)

        if (new_perms && this.$permissions_field) {
            this.$permissions_field.val(this._serializePerms(new_perms))
        }
    },

    onCommit: function(perms) {
        this.$el.find('input[name="permissions"]').val(perms)
        this.original_perms = this._parsePerms(perms)
        this.hide()
    }
})

r.ui.scrollFixed = function(el) {
    this.$el = $(el)
    this.$standin = null
    this.onScroll()
    $(window).bind('scroll resize', _.bind(_.throttle(this.onScroll, 20), this))
}
r.ui.scrollFixed.prototype = {
    onScroll: function() {
        if (!this.$el.is('.scroll-fixed')) {
            var margin = this.$el.outerHeight(true) - this.$el.outerHeight(false)
            this.origTop = this.$el.offset().top - margin
        }

        var enoughSpace = this.$el.height() < $(window).height()
        if (enoughSpace && $(window).scrollTop() > this.origTop) {
            if (!this.$standin) {
                this.$standin = $('<' + this.$el.prop('nodeName') + '>')
                    .css({
                        width: this.$el.width(),
                        height: this.$el.height()
                    })
                    .attr('class', this.$el.attr('class'))
                    .addClass('scroll-fixed-standin')

                this.$el
                    .addClass('scroll-fixed')
                    .css({
                        position: 'fixed',
                        top: 0
                    })
                this.$el.before(this.$standin)
            }
        } else {
            if (this.$standin) {
                this.$el
                    .removeClass('scroll-fixed')
                    .css({
                        position: '',
                        top: ''
                    })
                this.$standin.remove()
                this.$standin = null
            }
        }
    }
}

r.ui.ConfirmButton = Backbone.View.extend({
    confirmTemplate: _.template('<span class="confirmation"><span class="prompt"><%- are_you_sure %></span><button class="yes"><%- yes %></button> / <button class="no"><%- no %></button></div>'),
    events: {
        'click': 'click'
    },

    initialize: function() {
        // wrap the specified element in a <span> and move its classes over to
        // the wrapper. this is intended for progressive enhancement of a bare
        // <button> element.
        this.$target = this.$el
        this.$target.wrap('<span>')
        this.setElement(this.$target.parent())
        this.$el
            .attr('class', this.$target.attr('class'))
            .addClass('confirm-button')
        this.$target.attr('class', null)
    },

    click: function(ev) {
        var target = $(ev.target)
        if (this.$target.is(target)) {
            this.$target.hide()
            this.$el.append(this.confirmTemplate({
                are_you_sure: r._('are you sure?'),
                yes: r._('yes'),
                no: r._('no')
            }))
        } else if (target.is('.no')) {
            this.$('.confirmation').remove()
            this.$target.show()
        } else if (target.is('.yes')) {
            this.$target.trigger('confirm')
        }
    }
})

r.ui.SubredditSubmitText = Backbone.View.extend({
    initialize: function() {
        this.lookup = _.throttle(this._lookup, 500)
        this.cache = new r.utils.LRUCache()
        this.$input = $('#sr-autocomplete')
        this.$input.on('sr-changed change input', _.bind(this.lookup, this))
        this.$sr = this.$el.find('.sr').first()
        this.$content = this.$el.find('.content').first()
        if (this.$content.text().trim()) {
            this.$sr.text(r.config.post_site)
            this.show()
        }
    },

    _lookup: function() {
        this.$content.empty()
        var sr = this.$input.val()
        this.$sr.text(sr)
        this.$el.addClass('working')
        if (this.req && this.req.abort) {
            this.req.abort()
        }
        this.req = this.cache.ajax(sr, {
            url: '/r/' + sr + '/api/submit_text/.json',
            dataType: 'json'
        }).done(_.bind(this.settext, this, sr))
          .fail(_.bind(this.error, this))
    },

    show: function() {
        this.$el.addClass('enabled')
    },

    hide: function() {
        this.$el.removeClass('enabled')
    },

    error: function() {
        delete this.req
        this.hide()
    },

    settext: function(sr, data) {
        delete this.req
        if (!data.submit_text || !data.submit_text.trim()) {
            this.hide()
        } else {
            this.$sr.text(sr)
            this.$content.html($.unsafe(data.submit_text_html))
            this.$el.removeClass('working')
            this.show()
        }
    }
})

r.ui.TextCounter = Backbone.View.extend({
    events: {
      'input input': 'onInput',
      'input textarea': 'onInput',
    },

    initialize: function(options) {
      this.error = false;
      this.maxLength = options.maxLength;
      this.$counterDisplay = this.$el.find('.text-counter-display');
      this.$counterParent = this.$counterDisplay.parent();
      this.$input = this.$el.find('.text-counter-input');
      this.update(options.initialText || '');
    },

    onInput: function(e) {
      this.update(e.target.value);
    },

    update: function(inputText) {
      var remaining = this.maxLength - inputText.length;

      this.$counterDisplay.text(remaining);

      if (remaining < 0 && !this.error) {
        this.$counterParent.addClass('has-error');
        this.error = true;
        this.trigger('invalid');
      } else if (remaining >= 0 && this.error) {
        this.$counterParent.removeClass('has-error');
        this.error = false;
        this.trigger('valid');
      }
    },
  });
