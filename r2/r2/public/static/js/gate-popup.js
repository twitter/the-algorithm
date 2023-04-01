!function(r, undefined) {
  r.ui = r.ui || {};

  var activePopup = null;

  function GatePopup(options) {
    var realPopup;
    
    var fakePopup = {
      show: function() {
        if (!realPopup) {
          initPopup();
        }

        if (activePopup === this) {
          return;
        } else if (activePopup) {
          activePopup.hide();
        }
        activePopup = this;
        realPopup.show();
      },

      hide: function() {
        if (this === activePopup) {
          activePopup = null;
        }
        realPopup.hide();
      },
    };
    
    function initPopup() {
      var content = options.content || $('#' + options.templateId).html();

      realPopup = new r.ui.Popup({
        size: 'large',
        content: content,
        className: options.className,
      });

      realPopup.$.on('click', '.interstitial .c-btn', function(e) {
        realPopup.hide();
        return false;
      });

      realPopup.on('closed.r.popup', function() {
        if (activePopup === fakePopup) {
          activePopup = null;
        }
      });
    }

    return fakePopup;
  };

  r.ui.createGatePopup = function(options) {
    if (!options) {
      throw r.errors.createError('GATE_POPUP_JS_ERROR', 'no options given');
    } else if (!(options.templateId || options.content)) {
      throw r.errors.createError('GATE_POPUP_JS_ERROR', 'missing templateId or content option');
    }

    return new GatePopup(options);
  };
}(r);
