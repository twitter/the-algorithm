r.login = {
    post: function(form, action) {
        var username = $('input[name="user"]', form.$el).val(),
            endpoint = r.config.https_endpoint || ('http://'+r.config.ajax_domain),
            apiTarget = endpoint+'/api/'+action+'/'+username

        if (r.config.currentOrigin == endpoint || $.support.cors) {
            var params = form.serialize()
            params.push({name:'api_type', value:'json'})
            return $.ajax({
                url: apiTarget,
                type: 'POST',
                dataType: 'json',
                data: params,
                xhrFields: {
                    withCredentials: true
                }
            })
        } else {
            var iframe = $('<iframe>'),
                postForm = form.$el.clone(true),
                frameName = ('resp'+Math.random()).replace('.', '')

            iframe
                .css('display', 'none')
                .attr('name', frameName)
                .appendTo('body')

            iframe[0].contentWindow.name = frameName

            postForm
                .unbind()
                .css('display', 'none')
                .attr('action', apiTarget)
                .attr('target', frameName)
                .appendTo('body')
            
            $('<input>')
                .attr({
                    type: 'hidden',
                    name: 'api_type',
                    value: 'json'
                })
                .appendTo(postForm)

            $('<input>')
                .attr({
                    type: 'hidden',
                    name: 'hoist',
                    value: r.login.hoist.type
                })
                .appendTo(postForm)

            var deferred = r.login.hoist.watch(action)
            if (!r.config.debug) {
                deferred.done(function() {
                    iframe.remove()
                    postForm.remove()
                })
            }

            postForm.submit()
            return deferred
        }
    }
}

r.login.hoist = {
    type: 'cookie',
    watch: function(name) {
        var cookieName = 'hoist_'+name,
            deferred = new $.Deferred

        var interval = setInterval(function() {
            data = $.cookie(cookieName)
            if (data) {
                try {
                    data = JSON.parse(data)
                } catch(e) {
                    data = null
                }
                $.cookie(cookieName, null, {domain:r.config.cur_domain, path:'/'})
                clearInterval(interval)
                deferred.resolve(data)
            }
        }, 100)

        return deferred
    }
}

r.login.ui = {
    init: function() {
        if (!r.config.logged) {
            $('.content .login-form, .content #login-form, .side .login-form').each(function(i, el) {
                new r.ui.LoginForm(el)
            })

            $('.content .register-form, .content #register-form').each(function(i, el) {
                new r.ui.RegisterForm(el)
            })

            this.popup = new r.ui.LoginPopup();

            $(document).delegate('.login-required', 'click', $.proxy(this, 'loginRequiredAction'))
        }
    },

    _getActionDetails: function(el) {
      var $el = $(el);

      if ($el.hasClass('up')) {
        return {
            eventName: 'upvote',
            description: r._('You need to be logged in to upvote things.')
        };
      } else if ($el.hasClass('down')) {
        return {
            eventName: 'downvote',
            description: r._('You need to be logged in to downvote things.')
        };
      } else if ($el.hasClass('arrow')) {
        return {
            eventName: 'arrow',
            description: r._('You need to be logged in to vote on things.')
        };
      } else if ($el.hasClass('give-gold')) {
        return {
            eventName: 'give-gold',
            description: r._('You need to be logged in to give gold.')
        };
      } else if ($el.parents("#header").length && $el.attr('href').indexOf('login') !== -1) {
        return {
            eventName: 'login-or-register'
        };
      } else if ($el.parents('.subscribe-button').length) {
        return {
            eventName: 'subscribe-button',
            description: r._('You need to be logged in to subscribe to subreddits.')
        };
      } else if ($el.parents('.submit-link').length) {
        return {
            eventName: 'submit-link',
            description: r._('You need to be logged in to submit things.')
        };
      } else if ($el.parents('.submit-text').length) {
        return {
            eventName: 'submit-text',
            description: r._('You need to be logged in to submit things.')
        };
      } else {
        return {
            eventName: $el.attr('class'),
            description: r._('You need to be logged in to do that.')
        };
      }
    },

  _logEvent: function(e) {
      var target = $(e.target);
      var thing = target.thing();

      var targetType = target.data('type') || thing.data('type');
      var targetFullname = target.data('fullname') || thing.data('fullname');
      var actionName = target.data('event-action');
      var actionDetail = target.data('event-detail');

      // set default action for modal
      if (!actionName) {
        actionName = 'modal';
        actionDetail = null;
      }

      // set target using page context
      if (!targetFullname && targetType == 'subreddit') {
        targetFullname = r.config.cur_site;
      } else if (!targetFullname && targetType == 'link') {
        targetFullname = r.config.cur_link;
      }

      r.analytics.loginRequiredEvent(actionName, actionDetail, targetType, targetFullname);
    },

    loginRequiredAction: function(e) {
        if (r.config.logged) {
            return true
        } else {
            var el = $(e.target);
            var href = el.attr('href');
            var actionDetails = this._getActionDetails(el);
            var dest;

            if (href && href != '#' && !/\/login\/?$/.test(href)) {
                // User clicked on a link that requires login to continue
                dest = href
            } else {
                // User clicked on a thing button that requires login
                var thing = el.thing()
                if (thing.length) {
                    dest = thing.find('.comments').attr('href')
                }
            }

            this.popup.showLogin(actionDetails.description, dest && $.proxy(function(result) {
                window.location = dest
            }, this))

            this._logEvent(e);
            r.analytics.fireGAEvent('login-required-popup', 'opened', actionDetails.eventName);

            return false
        }
    }
}

r.ui.LoginForm = function() {
    r.ui.Form.apply(this, arguments)
}
r.ui.LoginForm.prototype = $.extend(new r.ui.Form(), {
    showErrors: function(errors) {
        r.ui.Form.prototype.showErrors.call(this, errors)
        if (errors.length) {
            this.$el.find('.recover-password')
                .addClass('attention')
        }
    },

    showStatus: function() {
        this.$el.find('.error').css('opacity', 1)
        r.ui.Form.prototype.showStatus.apply(this, arguments)
    },
    
    resetErrors: function() {
        if (this.$el.hasClass('login-form-side')) {
            // Dim the error in place so the form doesn't change size.
            var errorEl = this.$el.find('.error')
            if (errorEl.is(':visible')) {
                errorEl.fadeTo(100, .35)
            }
        } else {
            r.ui.Form.prototype.resetErrors.apply(this, arguments)
        }
    },

    _submit: function() {
        r.analytics.fireGAEvent('login-form', 'submit');
        return r.login.post(this, 'login')
    },

    _handleResult: function(result) {
        if (!result.json.errors.length) {
            // Success. Load the destination page with the new session cookie.
            if (this.successCallback) {
                this.successCallback(result)
            } else {
                this.$el.addClass('working')
                var base = r.config.extension ? '/.'+r.config.extension : '/',
                    defaultDest = /\/login\/?$/.test($.url().attr('path')) ? base : window.location,
                    destParam = this.$el.find('input[name="dest"]').val()
                var redir = destParam || defaultDest
                if (window.location === redir) {
                    window.location.reload();
                } else {
                    window.location = redir;
                }
            }
        } else {
            r.ui.Form.prototype._handleResult.call(this, result)
        }
    },

    _handleNetError: function(xhr) {
        r.ui.Form.prototype._handleNetError.apply(this, arguments)
        if (xhr.status == 0 && r.config.currentOrigin != r.config.https_endpoint) {
            $('<p>').append(
                $('<a>')
                    .text(r._('try using our secure login form.'))
                    .attr('href', r.config.https_endpoint + '/login')
            ).appendTo(this.$el.find('.status'))
        }
    },

    focus: function() {
        this.$el.find('input[name="user"]').focus()
    }
})


r.ui.RegisterForm = function() {
    r.ui.Form.apply(this, arguments)

    this.$user = this.$el.find('[name="user"]');

    if (!this.$user.is('[data-validate-url]')) {
        this.checkUsernameDebounced = _.debounce($.proxy(this, 'checkUsername'), 500);
        this.$user.on('keyup', $.proxy(this, 'usernameChanged'));
    }

    this.$el.find('[name="passwd2"]').on('keyup', $.proxy(this, 'checkPasswordMatch'));
    this.$el.find('[name="passwd"][data-validate-url]')
        .strengthMeter({
            related: [
                '#user_reg',
                '#email_reg',
            ],
            delay: 0,
            trigger: 'loaded.validator',
        })
        .on('score.strengthMeter', function(e, score) {
            var $el = $(this);

            if ($el.stateify('getCurrentState') === 'error') {
                return;
            }

            var message;

            if (score > 90) {
                message = r._('Password is strong');
            } else if (score > 70) {
                message = r._('Password is good');
            } else if (score > 30) {
                message = r._('Password is fair');
            } else {
                message = r._('Password is weak');
            }

            $el.stateify('showMessage', message);
        });

    this.$submit = this.$el.find('.submit button');
}
r.ui.RegisterForm.prototype = $.extend(new r.ui.Form(), {
    maxName: 0,
    usernameChanged: function() {
        var name = this.$user.val()
        if (name == this._priorName) {
            return
        } else {
            this._priorName = name
        }

        this.$el.find('.error.field-user').hide()
        this.$el.removeClass('name-checking name-available name-taken')

        this.maxName = Math.max(this.maxName, name.length)
        if (name && this.maxName >= 3) {
            this.$el.addClass('name-checking')
            this.checkUsernameDebounced()
        }

        this.$submit.attr('disabled', false)
    },

    checkPasswordMatch: _.debounce(function() {
        var $confirm = this.$el.find('[name="passwd2"]');
        var $password = this.$el.find('[name="passwd"]');
        var confirm = $confirm.val();
        var password = $password.val();

        if (!confirm || $password.stateify('getCurrentState') !== 'success') {
            $confirm.stateify('clear');
            return;
        }

        if (confirm === password) {
            $confirm.stateify('set', 'success');
        } else {
            $confirm.stateify('set', 'error', r._('passwords do not match'));
        }

    }, $.fn.validator.Constructor.DEFAULTS.delay),

    checkUsername: function() {
        var name = this.$user.val()

        if (name) {
            $.ajax({
                url: '/api/username_available.json',
                data: {user: name},
                success: $.proxy(this, 'displayUsernameStatus'),
                complete: $.proxy(function() { this.$el.removeClass('name-checking') }, this)
            })
        } else {
            this.$el.removeClass('name-available name-taken')
        }
    },

    displayUsernameStatus: function(result) {
        if (result.json && result.json.errors) {
            this.showErrors(result.json.errors)
            this.$submit.attr('disabled', true)
        } else {
            this.$el.addClass(result ? 'name-available' : 'name-taken')
            this.$submit.attr('disabled', result == false)
        }
    },

    _submit: function() {
        r.analytics.fireGAEvent('register-form', 'submit');
        return r.login.post(this, 'register')
    },

    _handleResult: r.ui.LoginForm.prototype._handleResult,
    focus: r.ui.LoginForm.prototype.focus
})

r.ui.LoginPopup = function() {
    var content = $('#login-popup').prop('innerHTML');

    r.ui.Popup.call(this, {
        size: 'large',
        content: content,
        className: 'login-modal',
    });

    this.login = new r.ui.LoginForm(this.$.find('#login-form'));
    this.register = new r.ui.RegisterForm(this.$.find('#register-form'));
};

r.ui.LoginPopup.prototype = _.extend({}, r.ui.Popup.prototype, {

    show: function(notice, callback) {
        this.login.successCallback = callback;
        this.register.successCallback = callback;

        this.$.find('#cover-msg').text(notice).toggle(!!notice);

        r.ui.Popup.prototype.show.call(this);

        return false;
    },

    showLogin: function() {
        var login = this.login;

        this.show.apply(this, arguments);
        this.once('opened.r.popup', function() {
            login.focus();
        });
    },

    showRegister: function() {
        var register = this.register;

        this.show.apply(this, arguments);
        this.once('opened.r.popup', function() {
            register.focus();
        });
    },

});
