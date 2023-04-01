r.newsletter = {
  post: function(form) {
    var email = $('input[name="email"]', form.$el).val();
    var apiTarget = form.$el.attr('action');

    var params = form.serialize();
    params.push({name:'api_type', value:'json'});

    return r.ajax({
      url: apiTarget,
      type: 'POST',
      dataType: 'json',
      data: params,
      xhrFields: {
        withCredentials: true
      }
    });
  }
};

r.newsletter.ui = {
  _setupNewsletterBar: function() {
    var newsletterBarSeen = !!store.safeGet('newsletterbar.seen');
    if (newsletterBarSeen || r.ui.isSmallScreen()) {
      return;
    }

    $('.newsletterbar').show();

    $('.newsletter-close').on('click', function() {
      $('.newsletterbar').hide();
    });

    store.safeSet('newsletterbar.seen', true);
  },

  _setupNewsletter: function() {
    if (!$('body').hasClass('newsletter')) {
      return;
    }

    $('.faq-toggle').click(function(e) {
      e.preventDefault();
      $(this).toggleClass('active');
      $('.faq').slideToggle();
      r.analytics.fireGAEvent('newsletter-form', 'faq-toggle');
    });
  },

  init: function() {
    $('.newsletter-signup').each(function(i, el) {
      new r.newsletter.ui.NewsletterForm(el)
    });

    this._setupNewsletterBar();

    this._setupNewsletter();
  },
};

r.newsletter.ui.NewsletterForm = function() {
  r.ui.Form.apply(this, arguments)
};

r.newsletter.ui.NewsletterForm.prototype = $.extend(new r.ui.Form(), {
  showStatus: function() {
    this.$el.find('.error').css('opacity', 1)
    r.ui.Form.prototype.showStatus.apply(this, arguments)
  },
  
  _submit: function() {
    r.analytics.fireGAEvent('newsletter-form', 'submit');
    return r.newsletter.post(this);
  },

  _showSuccess: function() {
      var parentEl = this.$el.parents('.newsletter-container');
      parentEl.find('.result-message').text(r._('check your inbox to confirm your subscription'));
      parentEl.addClass('success');
      parentEl.find('header').fadeTo(250, 1);
  },

  _handleResult: function(result) {
    if (result.json.errors.length) {
      return r.ui.Form.prototype._handleResult.call(this, result);
    }

    var parentEl = this.$el.parents('.newsletter-container');
    parentEl.find('header, form').fadeTo(250, 0, function() {
      this._showSuccess();
    }.bind(this));
  }
})
