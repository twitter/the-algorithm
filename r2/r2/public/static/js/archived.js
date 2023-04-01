/*
  If a link is too old, don't allow commenting or voting.
 */
!function(r) {
  r.archived = {
    init: function() {
      this._popup = r.ui.createGatePopup({
        templateId: 'archived-popup',
        className: 'archived-error-modal',
      });

      $('body').on('click', '.archived', this._handleClick.bind(this));
    },

    _handleClick: function(e) {
      this._popup.show();
      return false;
    },
  };

  r.hooks.get('reddit').register(function() {
    r.archived.init();
  });
}(r);
