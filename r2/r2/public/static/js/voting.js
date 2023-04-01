!function(r) {
  var UP_CLS = "up";
  var DOWN_CLS = "down";
  var theFakeClick;
  var MouseEvent = window.MouseEvent;
  var createEvent = document.createEvent;

  if (createEvent) {
    // document.createEvent throws if not called from document;
    createEvent = createEvent.bind(document);
  }

  try {
    // Some browsers (e.g. IE11) throw an error here.
    if (MouseEvent) {
      theFakeClick = new MouseEvent('click', {bubbles: true});
    }
  } catch (e) {
    // We'll handle this below as if MouseEvent doesn't exist.
  }

  try {
    // To be on the safe side, we'll wrap this one in a try/catch as well.
    // It needs to be in a separate try/catch because if creating theFakeClick
    // with MouseEvent fails, we still want to fall back to trying with createEvent.
    if (!theFakeClick && createEvent) {
      theFakeClick = createEvent('MouseEvent');
    }
  } catch (e) {
    // We'll handle this below as if createEvent doesn't exist.
  }

  if (!theFakeClick) {
    // If no method for creating a custom mouse event exists, just use an object.
    theFakeClick = {};
  }

  window.MouseEvent = function(type, init) {
    return theFakeClick;
  }

  document.createEvent = function(type) {
    if (type === 'MouseEvent' || type === 'MouseEvents') {
      return theFakeClick;
    } else {
      return createEvent(type);
    }
  }

  $(function() {
    $(document.body).on('click', '.arrow', function vote(e) {
      var $el = $(this);
      
      if (!r.config.logged || r.access.isLinkRestricted(this)) {
        return;
      }

      if ($el.hasClass('archived')) {
        return;
      }

      var $thing = $el.thing();
      var id = $thing.thing_id();
      var dir = $el.hasClass(UP_CLS) ? 1 : $el.hasClass(DOWN_CLS) ? -1 : 0;
      var isTrusted;

      if (!e || !e.originalEvent) {
        isTrusted = false;
      } else if (MouseEvent instanceof Function &&
                 'isTrusted' in MouseEvent.prototype) {
        isTrusted = e.originalEvent.isTrusted;
      } else if (MouseEvent instanceof Function) {
        isTrusted = (e.originalEvent instanceof MouseEvent &&
                     e.originalEvent !== theFakeClick);
      } else {
        isTrusted = (e.originalEvent !== theFakeClick);
      }

      var voteData = {
        id: id,
        dir: dir,
        vh: r.config.vote_hash,
        isTrusted: isTrusted,
      };

      var rank = $thing.data('rank');
      if (rank) {
        voteData.rank = parseInt(rank);
      }

      $.request("vote", voteData);
      $thing.updateThing({ voted: dir });
    });
  });
}(r);
