/*
  If the current post is locked, show a modal on restricted actions.

  requires r.config (base.js)
  requires r.access (access.js)
  requires r.ui.Popup (popup.js)
*/
!function(r) {
  // initialized early so click handlers can be bound on declaration
  r.locked = {};

  _.extend(r.locked, {
    init: function() {
      $('body').on('click', '.locked-error', this._handleClick);

      this._popup = r.ui.createGatePopup({
        templateId: 'locked-popup',
        className: 'locked-error-modal',
      });
    },

    _handleClick: function onClick(e) {
      if (r.access.isLinkRestricted(e.target)) {
        return;
      }

      this._popup.show();
      return false;
    }.bind(r.locked),
  });
}(r);
