!(function(global, $, r, undefined) {
  'use strict';

  var CALLBACK_PREFIX = '__image_upload__';

  var DEFAULTS = {
    max: 1024 * 500,
    errors: {
      unknown: r._('something went wrong.'),
    }
  };


  var ImageUpload = function(element, options) {
    this.initialize(element, options);
  };

  _.extend(ImageUpload.prototype, {
    _callbacks: {},

    initialize: function(element, options) {
      this.el = element;
      this.$el = $(element);
      this.$file = this.$el.find('[type="file"]');
      this.file = this.$file.get(0);
      this.options = _.defaults({}, this.$el.data(), options, DEFAULTS);
      this._bindEvents();
      this.ajax = !!global.FormData;

      return this;
    },

    _bindEvents: function() {
      this.$file.on('change', this._handleChange.bind(this));
      this.$el.find('.c-image-upload-btn')
        .on('click',this._triggerDialog.bind(this));
    },

    _triggerDialog: function() {
      this.$file.click();
    },

    _handleChange: function() {
      if (this.file.value) {
        var files = this.file.files;

        if (files && files.length && files[0].size > this.options.maxSize) {
          this.$el.trigger('failed.imageUpload', [{
            message: r._('too big. keep it under ' + r.utils.formatFileSize(this.options.maxSize)),
          }]);

          this._reset();
        } else {
          $.ajax({
            url: this.options.url,
            type: 'POST',
            dataType: 'json',
            data: _.extend({}, this.options.params, {
              filepath: files[0].name,
              uh: r.config.modhash,
              ajax: this.ajax,
              raw_json: '1',
            }),
          })
          .fail(function(xhr) {
            var resp = xhr.responseJSON;

            this.$el.trigger('failed.imageUpload', [{
              message: resp.message || this.options.errors.unknown,
            }]);
          }.bind(this))
          .done(this._submit.bind(this));
        }
      }

      return true;
    },

    _updateProgress: function(percentage) {
      this._showProgress();

      if (percentage) {
        this._percentage = percentage;
        this.$el.find('.c-progress-bar').css({ width: (percentage + '%') });
      }
    },

    _showProgress: function() {
      this.$el.find('.c-progress').show();
    },

    _hideProgress: function() {
      this.$el.find('.c-progress').hide();
      this.$el.find('.c-progress-bar').css({ width: 0 });
    },

    _submit: function(overrides) {
      overrides = overrides || {};

      this.$el.attr('action', overrides.action);

      overrides.fields.forEach(function(field) {
        var name = field.name;
        var value = field.value;
        var unset = value === '' || value === null
        var $input = this.$el.find('[name="' + name + '"]');

        if (!$input.length) {
          // Skip null values
          if (unset) {
            return;
          }

          $('<input type="hidden">')
            .attr('name', name)
            .val(value)
            .insertBefore(this.$file);
        } else {
          if (unset) {
            $input.remove();
          } else {
            $input.val(value);
          }
        }
      }.bind(this));

      if (!this.ajax) {
        var $redirect = this.$el.find('[name="success_action_redirect"]');
        var redirect = $redirect.val();
        var callback = _.uniqueId(CALLBACK_PREFIX);

        redirect = r.utils.replaceUrlParams(redirect, {callback: callback});

        $redirect.val(redirect);

        global.__s3_callbacks__[callback] = this._callbacks[callback] = this._iframeComplete.bind(this);
        this._fakeProgressTo(10, 80);
        this.$el.submit();
      } else {
        var fields = {};
        var data = new FormData();
        var fileInput;

        this.$el.find('input').each(function() {
          var el = this;
          var $el = $(el);
          var type = $el.attr('type');

          if (type !== 'file') {
            data.append($el.attr('name'),  $el.val());
          }
        });

        data.append(this.file.name, this.file.files[0]);

        this._showProgress();

        $.ajax({
          url: this.$el.attr('action'),
          type: this.$el.attr('method'),
          contentType: false,
          processData: false,
          data: data,
          dataType: 'xml',
          success: this._ajaxSuccess.bind(this),
          error: this._ajaxError.bind(this),
          progress: this._ajaxProgress.bind(this),
          complete: this._reset.bind(this),
          xhr: function() {
            var xhr = $.ajaxSettings.xhr();

            if (xhr instanceof global.XMLHttpRequest) {
              xhr.addEventListener('progress', this.progress, false);
            }
            
            if (xhr.upload) {
              xhr.upload.addEventListener('progress', this.progress, false);
            }
            
            return xhr;
          },
        });
      }

      this.$el.trigger('uploading.imageUpload');
    },

    _fakeProgressTo: function(start, end) {
      var _fakeProgressInterval = this._fakeProgress = setInterval(function() {
        if (this._percentage > end) {
          clearInterval(_fakeProgressInterval);
          return;
        }

        this._updateProgress((this._percentage || start) + 1);
      }.bind(this), 200);
    },

    _ajaxProgress: function(e) {
      var percentage = Math.round((e.loaded / e.total) * 100);

      this._updateProgress(percentage);

      this.$el.trigger('progress.imageUpload', [{
        complete: e.loaded,
        total: e.total,
        percentage: percentage,
      }]);
    },

    _updatePreview: function (url, callback) {
      callback = callback || $.noop;

      var $preview = this.$el.find('.c-image-upload-preview');
      var src = r.utils.replaceUrlParams(url, {
        cb: (+new Date()),
      });

      $preview.one('load', callback.bind(this));
      $preview.attr('src', src);
    },

    _ajaxSuccess: function(xml) {
      var $xml = $(xml);
      var url = $xml.find('Location').text();

      this._updatePreview(url, this._hideProgress);
      this._updateProgress(100);
      this.$el.trigger('success.imageUpload', [{
        url: url,
      }]);
    },

    _ajaxError: function(xhr) {
      var $xml = $(xhr.responseXML);
      var message = this.options.errors.unknown;

      if ($xml && $xml.length) {
        message = $xml.find('Message').text();
      }

      this._hideProgress();
      this.$el.trigger('failed.imageUpload', [{
        message: message,
      }]);
    },

    _iframeComplete: function(data) {
      this._updateProgress(100);
      this._updatePreview(data.url, this._hideProgress);
    },

    _reset: function() {
      this.$file.resetInput();
      this.$el.trigger('reset.imageUpload');
    },

  });

  function Plugin(option /* ,args... */) {
    var args = _.toArray(arguments).slice(1);

    if (option && /^get/.test(option)) {
      var data = this.data('c.imageUpload');

      return data && data[option].apply(data, args);
    }

    return this.each(function() {
      var $el = $(this);
      var data = $el.data('c.imageUpload');
      var options = typeof option === 'object' && option;

      if (!data) {
        data = new ImageUpload(this, options);
        $el.data('c.imageUpload', data);
      }

      if (typeof option === 'string') {
        data[option].apply(data, args);
      }
    });
  };

  $.fn.imageUpload = Plugin;
  $.fn.imageUpload.Constructor = ImageUpload;

})(this, this.jQuery, this.r);
