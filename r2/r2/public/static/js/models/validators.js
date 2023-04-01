/*
Provides Backbone models a method of defining attribute validators.

requires r.errors
 */
!function(r, undefined) {
  r.models = r.models || {};

  r.models.validators = {
    validate: function(model, validators) {
      for (var i = 0; i < validators.length; i++) {
        validator = validators[i];
        error = validator(model);

        if (error) {
          return error;
        }
      }
    },
  };

  r.models.validators.StringLength = function (attrName, minLength, maxLength) {
    minLength = Math.max(0, parseInt(minLength, 10));
    maxLength = Math.max(0, parseInt(maxLength, 10));

    return function validate(model) {
      var value = model.get(attrName);

      if (typeof value !== 'string') {
        return r.errors.createAPIError(attrName, 'NO_TEXT');
      } else if (value.length < minLength) {
        return r.errors.createAPIError(attrName, 'TOO_SHORT', {
          min_length: minLength,
        });
      } else if (maxLength && value.length > maxLength) {
        return r.errors.createAPIError(attrName, 'TOO_LONG', {
          max_length: maxLength,
        });
      }
    };
  };
}(r);
