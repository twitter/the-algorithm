!function($) {
  'use strict';

  var Togglable = function(element, options) {
    this.initialize(element, options);
  };

  _.extend(Togglable.prototype, {

    initialize: function(element, options) {
      var $el = this.$el = $(element);

      $el.on('click', function(e) {
        var $el = $(e.target);
        var selector = $el.data('toggle');

        $el.toggleClass('c-toggle-toggled');
        $(selector).toggleClass('c-toggle-content-toggled');
      });

      return this;
    },

  });


  function Plugin(option /* ,args... */) {
    var args = Array.prototype.slice.call(arguments, 1);

    return this.each(function() {
      var $el = $(this);
      var data = $el.data('c.toggle');
      var options = typeof option === 'object' && option;

      if (!data) {
        data = new Togglable(this, options);
        $el.data('c.toggle', data);
      }

      if (typeof option === 'string') {
        data[option].apply(data, args);
      }
    });
  }

  $.fn.togglable = Plugin;
  $.fn.togglable.Constructor = Togglable;

}(window.jQuery);
