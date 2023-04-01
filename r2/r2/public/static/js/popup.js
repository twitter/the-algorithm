;(function(ui, $, _, window, undefined) {
  var SIZE_CLASS_LOOKUP = {
    large: 'modal-dialog-lg',
    medium: '',
    small: 'modal-dialog-sm',
  };

  var DEFAULTS = {
    template: template,
    animate: true,
    close: true,
    modal: true,
    shortcuts: true,
    footer: false,
    content: '',
    className: '',
    title: false,
    size: 'medium',
  };

  var template = _.template(
    '<div class="modal <% if (animate) { %> fade <% } %> <%- className %>">' +
      '<div class="modal-dialog <% if (size) { %><%- SIZE_CLASS_LOOKUP[size] %><% } %>">' +
        '<div class="modal-content">' +
          '<% if (title || close) { %>' +
            '<div class="modal-header">' +
              '<% if (close) { %>' +
                '<a href="javascript: void 0;" class="c-close c-hide-text" data-dismiss="modal">' +
                  _.escape(r._('close this window')) +
                '</a>' +
              '<% } %>' +
              '<% if (title) { %>' +
                '<%= title %>' +
              '<% } %>' +
            '</div>' +
          '<% } %>' +
          '<div class="modal-body">' +
            '<%= content %>' +
          '</div>' +
          '<% if (footer) { %>' +
            '<div class="modal-footer">' +
              '<%= footer %>' +
            '</div>' +
          '<% } %>' +
        '</div>' +
      '</div>' +
    '</div>'
  );

  var Popup = ui.Popup = function(options) {
    options = _.extend({}, DEFAULTS, options, {SIZE_CLASS_LOOKUP: SIZE_CLASS_LOOKUP});

    var html = template(options);
    var listener = this.listener = $({});
    var $el = this.$ = $(html);

    $el.modal({
      show: false,
      backdrop: !!options.modal,
      keyboard: options.shortcuts,
    });

    [
      ['show.bs.modal', 'show.r.popup'],
      ['shown.bs.modal', 'opened.r.popup'],
      ['hide.bs.modal', 'hide.r.popup'],
      ['hidden.bs.modal', 'closed.r.popup'],
    ].forEach(function(tuple) {
      $el.on(tuple[0], function() {
        listener.trigger(tuple[1]);
      });
    });

    // ensures element is 'focusable', otherwise closing with esc key only
    // works when an input inside the modal is focused.
    $el.attr('tabindex', $el.attr('tabindex') || 0);
  };

  [
    'show',
    'hide',
    'toggle'
  ].forEach(function(fn) {
    Popup.prototype[fn] = function() {
      this.$.modal(fn);
    };
  });

  [
    ['on', 'on'],
    ['once', 'one'],
    ['off', 'off'],
  ].forEach(function(tuple) {
    Popup.prototype[tuple[0]] = function() {
      this.listener[tuple[1]].apply(this.listener, arguments);
    };
  });

})(((this.r = this.r || {}) && (r.ui = r.ui || {})), this.jQuery, this._, this);
