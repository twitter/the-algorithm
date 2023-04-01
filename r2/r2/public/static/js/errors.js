!function(r) {
  var errors = {
    'UNKNOWN_ERROR': r._('unknown error %(message)s'),
    'NO_TEXT': r._('we need something here'),
    'TOO_LONG': r._('this is too long (max: %(max_length)s)'),
    'TOO_SHORT': r._('this is too short (min: %(min_length)s)'),
    'SR_RULE_EXISTS': r._('A subreddit rule by that name already exists.'),
    'SR_RULE_TOO_MANY': r._('This subreddit already has the maximum number of rules.'),
  };

  function CustomErrorPrototype() {}
  CustomErrorPrototype.prototype = Error.prototype;

  // used by the custom ApiError, tries to get a more accurate stack value by
  // removing the top line (which should be the instantiation of the original
  // generic Error object inside of ApiError)
  function _getStack(err) {
    var stack = err.stack;
    return stack && stack.split('\n').slice(1).join('\n');
  }

  // creating custom Errors that behave correctly is weird
  // http://stackoverflow.com/questions/8802845/inheriting-from-the-error-object-where-is-the-message-property
  function ApiError(displayName, displayMessage, field, source) {
    // allow use without new constructor
    if (!(this instanceof ApiError)) {
      return new ApiError(displayName, displayMessage, field, source);
    }

    var err = Error.call(this);

    // IE11 doesn't set the stack property until the error is thrown.
    // If we do nothing it will create a proper stack on its own.
    if ('stack' in err) {
      if ('captureStackTrace' in Error) {
        // Chrome provides captureStackTrace for custom error traces
        Error.captureStackTrace(this, ApiError);
      } else {
        // For Firefox/Safari/Other browsers, defer setting stack until accessed
        try {
          Object.defineProperty(this, 'stack', {
            configurable: true,
            get: function() {
              var stack = _getStack(err);
              return this.stack = stack;
            },
          })
        } catch (e) {
          // If that fails set the stack property immediately.
          this.stack = _getStack(err);
        }
      }
    }

    // if we want to throw the error client-side, the message attribute
    // should include both the error key and the display message.
    var errorMessage = displayName + ' | ' + displayMessage;

    this.name = 'ApiError';
    this.message = errorMessage;
    this.displayName = displayName;
    this.displayMessage = displayMessage;
    this.field = field || '';
    this.source = source || 'api';
  }

  ApiError.prototype = new CustomErrorPrototype();


  r.errors = {
    create: function(name, message, field, source) {
      return new ApiError(name, message, field, source);
    },

    formatAPIError: function(apiErrorArray) {
      var key = apiErrorArray[0];
      var message = apiErrorArray[1];
      var field = apiErrorArray[2];

      return new ApiError(key, message, field);
    },

    getAPIErrorsFromResponse: function(res) {
      if (res && res.json && res.json.errors && res.json.errors.length) {
        return res.json.errors.map(r.errors.formatAPIError);
      } else if (!res || (res.error && typeof res.error === 'string')) {
        var message = !res ? 'unknown' : res.error;
        // return an array here for consistency
        return [
          r.errors.createAPIError('', 'UNKNOWN_ERROR', { message: message }),
        ];
      }
    },

    createAPIError: function(field, key, messageParams) {
      var message = errors[key] || 'unknown';

      if (messageParams) {
        message = message.format(messageParams);
      }

      return new ApiError(key, message, field, 'client');
    },

    _getErrorFieldSelector: function(apiError) {
      var selector = '.error.' + apiError.displayName;

      if (apiError.field) {
        selector += '.field-' + apiError.field;
      }
      
      return selector;
    },

    showAPIError: function(form, apiError) {
      var selector = this._getErrorFieldSelector(apiError);
      $(form).find(selector)
             .text(apiError.displayMessage)
             .css('display', 'inline');
    },

    showAPIErrors: function(form, apiErrors) {
      apiErrors.forEach(function(apiError) {
        r.errors.showAPIError(form, apiError);
      });
    },

    clearAPIErrors: function(form, apiErrors) {
      var selector;
      
      if (!apiErrors) {
        selector = '.error';
      } else {
        selector = apiErrors.map(this._getErrorFieldSelector).join(', ');
      }

      $(form).find(selector)
             .text('')
             .css('display', 'none');
    },
  };
}(r);
