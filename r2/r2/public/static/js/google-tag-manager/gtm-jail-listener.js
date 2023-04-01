;(function(global, r, undefined) {
  var jail = document.createElement('iframe');

  r.frames.proxy('gtm', [jail.contentWindow, window.parent]);

  jail.style.display = 'none';
  jail.referrer = 'no-referrer';
  jail.id = 'jail';
  jail.name = window.name;
  jail.src = '/gtm?id=' + global.CONTAINER_ID;

  document.body.appendChild(jail);
})(this, this.r);
