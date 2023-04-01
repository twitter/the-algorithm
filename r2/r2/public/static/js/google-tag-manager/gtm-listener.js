;(function(gtm, global, r, undefined) {
  var dataLayer = global.googleTagManager = global.googleTagManager || [];

  r.frames.listen('gtm');

  r.frames.receiveMessage('data.gtm', function(e) {
    dataLayer.push(e.detail);
  });

  r.frames.receiveMessage('event.gtm', function(e) {
    dataLayer.push(e.detail);
  });

})((this.gtm = this.gtm || {}), this, this.r);
