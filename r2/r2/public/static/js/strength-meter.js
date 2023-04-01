;(function($) {
  'use strict';

  var StrengthMeter = function(element, options) {
    this.initialize(element, options);
  };

  StrengthMeter.DEFAULTS = {
    template: '<div class="strength-meter">' +
                '<div class="strength-meter-fill"></div>' +
              '</div>',
    delay: 100,
    minimumDisplay: 5,
    trigger: 'keyup change blur',
  };

  var KNOWN_WEAK = [
    /^password(\d+)?$/i,
    /^letmein(\d+)?$/i,
    /^welcome(\d+)?$/i,
    /^secret(\d+)?$/i,
    /^reddit(\d+)?$/i,
    /^(reddit)\1+/i,
    /^(test)\1+$/i,
    /^abcd?e?f?1234?5?6?$/i,
    /^iloveyou$/i,
    /^admin$/i,
    /^trustno1$/i,
    /^.werty$/i,
    /^sunshine$/i,
    /^monkey$/i,
    /^shadow$/i,
    /^princess$/i,
    /^dragon$/i,
  ];

  var ALPHA_ORDERED = 'abcdefghijklmnopqrstuvwxyz';
  var QWERTY_ORDERED = 'qwertyuiopasdfghjklzxcvbnm';
  var QAZWSX_ORDERED = 'qazwsxedcrfvtgbyhnujmikolp';
  var NUMERIC_ORDERED = '01234567890';
  var SYMBOLS_ORDERED = '!@#$%^&*()';

  function isWeak(password, related) {
    return _.any(related, function(string) {
        return (string && password.indexOf(string) !== -1);
      }) ||
      _.any(KNOWN_WEAK, function(regex) {
        return regex.test(password);
      });
  }

  function testConsecutive(characterClass) {
    return function(string) {
      var regex = new RegExp(characterClass + '+', 'g');
      var consecutiveGroups = string.match(regex) || [];

      return _.reduce(consecutiveGroups, function(total, group) {
        return total += group.length;
      }, 0);
    };
  }

  function testRepeat(string) {
    var charArray = _.toArray(string);
    var unique = _.unique(charArray).length;
    var totalRepeatDistance = 0;

    for (var i = 0, l = charArray.length; i < l; i++) {
      for (var j = 0; j < l; j++) {
        if (charArray[i] === charArray[j] && i !== j) {
          // The distance between repeat characters adjusted for total length
          totalRepeatDistance += Math.abs(l / (j - i));
        }
      }
    }

    return Math.ceil(unique === 0 ? totalRepeatDistance : totalRepeatDistance / unique);
  }

  function testOrdered(ordered) {
    return function(string) {
      var insensitive = string.toLowerCase();
      var total = 0;

      for (var i = 0, l = ordered.length - 3; i < l; i++) {
        var forward = ordered.substring(i, i + 3);
        var reverse = _.toArray(forward).reverse().join('');

        if (insensitive.indexOf(forward) != -1 || insensitive.indexOf(reverse) != -1) {
          total++;
        }
      }

      return total;
    };
  }

  var TESTS = [{
    test: /./g,
    weight: 4,
  }, {
    test: /[A-Z]/g,
    weight: function (string, n) {
      return !n || string.length === n ?
        0 : ((string.length - n) * 2);
    },
  }, {
    test: /[a-z]/g,
    weight: function (string, n) {
      return !n || string.length === n ?
        0 : ((string.length - n) * 2);
    },
  }, {
    test: /\d/g,
    weight: function(string, n) {
      return !n || string.length === n ?
        0 : (n * 4);
    },
  }, {
    test: /\W|_/g,
    weight: 6,
  }, {
    test: /^[a-z]+$/i,
    weight: -1,
  }, {
    test: /^\d+$/i,
    weight: -1,
  }, {
    test: testOrdered(ALPHA_ORDERED),
    weight: -3,
  }, {
    test: testOrdered(QWERTY_ORDERED),
    weight: -3,
  }, {
    test: testOrdered(QAZWSX_ORDERED),
    weight: -3,
  }, {
    test: testOrdered(NUMERIC_ORDERED),
    weight: -3,
  }, {
    test: testOrdered(SYMBOLS_ORDERED),
    weight: -3,
  }, {
    test: testConsecutive('[a-z]'),
    weight: -2,
  }, {
    test: testConsecutive('[A-Z]'),
    weight: -2,
  }, {
    test: testConsecutive('\\d'),
    weight: -2,
  }, {
    test: testRepeat,
    weight: -1,
  }];

  function testRunner(string, definition) {
    var n = 0;

    if (definition.test instanceof Function) {
      n = definition.test(string);
    } else {
      var matches = string.match(definition.test);

      n = matches ? matches.length : 0;
    }

    if (definition.weight instanceof Function) {
      return definition.weight(string, n);
    } else {
      return definition.weight * n;
    }
  }

  function getScore(password, related) {
    password = password || '';

    if (isWeak(password, related)) {
      return 0;
    }

    return _.reduce(TESTS, function(total, definition) {
      return total += testRunner(password, definition);
    }, 0);
  }

  _.extend(StrengthMeter.prototype, {

    _cancelScore: false,

    initialize: function(element, options) {
      this.options = $.extend({}, StrengthMeter.DEFAULTS, options);

      var $el = this.$el = $(element);
      var $meter = this.$meter = $(this.options.template);
      var trigger = this.options.trigger;

      if (trigger !== 'manual') {
        $el.on(this.options.trigger, _.debounce(this.score.bind(this), this.options.delay));
      }

      $meter.insertAfter($el);

      var meterWidth = $meter.outerWidth();
      var meterPadding = (meterWidth + 5) + 'px';

      $el.css({'padding-right': meterPadding});
      $el.trigger('initialize.strengthMeter');

      return this;
    },

    score: function() {
      var value = this.$el.val();
      var related = _.map(this.options.related, function(related) {
        return $(related).val() || '';
      });
      var score = getScore(value, related);
      var displayScore = Math.min(100, Math.max(this.options.minimumDisplay, score));

      this.$el.trigger('score.strengthMeter', displayScore);

      if (!this._cancelScore) {
        this.$meter.find('.strength-meter-fill').css({width: displayScore + '%'});
      }

      this._cancelScore = false;
    },

    cancelScore: function() {
      this._cancelScore = true;
    },

  });


  function Plugin(option /* ,args... */) {
    var args = Array.prototype.slice.call(arguments, 1);

    return this.each(function() {
      var $el = $(this);
      var data = $el.data('c.strengthMeter');
      var options = typeof option === 'object' && option;

      if (!data) {
        data = new StrengthMeter(this, options);
        $el.data('c.strengthMeter', data);
      }

      if (typeof option === 'string') {
        data[option].apply(data, args);
      }
    });
  }

  $.fn.strengthMeter = Plugin;
  $.fn.strengthMeter.Constructor = StrengthMeter;

}(window.jQuery));
