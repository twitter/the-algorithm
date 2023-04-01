(function ($) {

  var base = $.fn.tooltip.Constructor.prototype;
  var _getCalculatedOffset = base.getCalculatedOffset;

  base.getCalculatedOffset = function(placement, pos, actualWidth, actualHeight) {
    var offset;

    if (placement === 'top-right') {
        offset = {top: pos.top - actualHeight - 10, left: pos.left + pos.width - actualWidth};
        if (this.$element.outerWidth() <= 18) {
          offset.left += 4;
        }
    } else {
        return _getCalculatedOffset.apply(this, arguments);
    }

    return offset;
  };

  base.replaceArrow = function(delta, dimension, isHorizontal) {
    var offsetMultiplier = (1 - delta / dimension);

    if (offsetMultiplier !== 1) {
      this.arrow()
        .css(isHorizontal ? 'left' : 'top', 50 * offsetMultiplier + '%');
    }

    this.arrow()
      .css(isHorizontal ? 'top' : 'left', '');
  };

})(window.jQuery);
