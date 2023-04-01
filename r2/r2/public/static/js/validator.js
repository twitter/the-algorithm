;(function($) {
  'use strict';

  var Validator = function(element, options) {
    this.initialize(element, options);
  };

  Validator.DEFAULTS = {
    delay: 600,
    loadingTimeout: 250,
    https: false,
  };

  _.extend(Validator.prototype, {

    _loadingTimeout: false,

    initialize: function(element, options) {
      var $el = this.$el = $(element);
      var events = $el.data('validate-on') || 'keyup change blur';

      this.options = $.extend({}, Validator.DEFAULTS, options);

      $el.on(events, _.debounce($.proxy(this._validate, this), this.options.delay));

      $el.trigger('initialize.validator');

      return this;
    },

    _validate: function(e) {
      // Don't validate on enter/tab key
      if (e.keyCode === 9 || e.keyCode === 13) {
        return;
      }

      // Don't validate individual field on blur when submit is pressed.
      if (e.type === 'blur' &&
          e.relatedTarget && $(e.relatedTarget).is('[type=submit]')) {
        return;
      }

      var _this = this;
      var $el = this.$el;
      var min = $el.data('validate-min');
      var value = $el.val();

      // Don't validate on `keyup` below the specified `min`.
      if (e.type === 'keyup' && value.length < min) {
        delete this._keyupValue;
        $el.trigger('cleared.validator');

        return;
      }

      // Don't validate again on `change|blur` if that value was already checked by `keyup`.
      if (/change|blur/.test(e.type) && this._keyupValue === value) {
        return;
      }

      // Clear validation state on empty values
      if (/^\s*$/.test(value) && !$el.data('validate-noclear')) {
        $el.trigger('cleared.validator');
        return;
      }

      var $form = $el.parents('form');
      var url = $el.data('validate-url');
      var validateWith = ($el.data('validate-with') || '').split(/,s*/);
      var data = {};

      if (this.options.https) {
        var parser = document.createElement('a');

        parser.href = url;
        parser.protocol = 'https:';
        url = parser.href;
      }

      data[$el.attr('name')] = value;

      // Store the latest value checked on `keyup`.
      if (e.type === 'keyup') {
        this._keyupValue = value;
      }

      _.each(validateWith, function(name) {
        data[name] = $form.find('[name="' + name + '"]').val();
      });

      this._loadingTimeout = setTimeout(function() {
        $el.trigger('loading.validator');
      }, this.options.loadingTimeout);

      if (this.xhr && this.xhr.readyState !== 4) {
        this.xhr.abort();
      }

      this.xhr = $.ajax({
        type: 'POST',
        url: url,
        data: data,
        dataType: 'json',
        success: function(resp, text, xhr) {
          var json = resp && resp.json;

          if (json && json.errors && json.errors.length) {
            $el.trigger('invalid.validator', json);
          } else {
            $el.trigger('valid.validator');
          }
        },
        error: function(xhr) {
          // Request aborted.
          // Do nothing.
          if (xhr.status === 0) {
            return;
          }

          // Can't determine validation due to a conflict (likely due to another field)
          // Example: confirmation passwords require a valid password to already exist.
          if (xhr.status === 409) {
            $el.trigger('cleared.validator');

            return;
          }

          // Something unknown went wrong.
          // Mark as success and let the full submit worry about it.
          $el.trigger('valid.validator');
        },
        complete: function() {
          clearTimeout(_this._loadingTimeout);

          $el.trigger('loaded.validator');
        },
      });
    },

  });


  function Plugin(option /* ,args... */) {
    var args = Array.prototype.slice.call(arguments, 1);

    return this.each(function() {
      var $el = $(this);
      var data = $el.data('c.validator');
      var options = typeof option === 'object' && option;

      if (!data) {
        data = new Validator(this, options);
        $el.data('c.validator', data);
      }

      if (typeof option === 'string') {
        data[option].apply(data, args);
      }
    });
  }

  $.fn.validator = Plugin;
  $.fn.validator.Constructor = Validator;

}(window.jQuery));
