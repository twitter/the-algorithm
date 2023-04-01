;(function(App, r, window, undefined) {
  App.VERSION = '0.1';

  var RE_HOST = /^https?:\/\/([^\/|?]+).*/;
  var config = window.REDDIT_EMBED_CONFIG;
  var thing = config.thing;

  if (document.referrer && document.referrer.match(RE_HOST)) {
    r.frames.addPostMessageOrigin(RegExp.$1);
  }

  r.frames.listen('embed');

  function checkHeight() {
    var height = document.body.clientHeight;

    if (height && App.height !== height) {
      App.height = height;

      r.frames.postMessage(window.parent, 'resize.embed', height, { targetOrigin: '*' });
    }
  }

  function clipComments() {
    var height = config.comment_max_height;
    var flex = 30;

    if (!height) {
      return;
    }

    function expandComment(e) {
      var el = this;

      el.previousSibling.style.maxHeight = '';
      el.parentNode.className =
        el.parentNode.className.replace(' reddit-embed-comment-fade', '');
    }

    var blockquotes = document.getElementsByTagName('blockquote');

    for (var i = 0, l = blockquotes.length; i < l; i++) {
      if (blockquotes[i].clientHeight > height + flex) {
        blockquotes[i].style.maxHeight = height + 'px';
        blockquotes[i].parentNode.className += ' reddit-embed-comment-fade';
        blockquotes[i].nextSibling.addEventListener('click', expandComment, false);
      }
    }
  }

  function createPayloadFactory(location) {
    return function payloadFactory(type, action, payload) {
      var now = new Date();
      var data = {
        'event_topic': 'embed',
        'event_name': 'embed_' + action,
        'event_ts': now.getTime(),
        'event_ts_utc_offset': now.getTimezoneOffset() / -60,
        'user_agent': navigator.userAgent,
        'sr_id': thing.sr_id,
        'sr_name': thing.sr_name,
        'embed_id': thing.id,
        'embed_version': App.VERSION,
        'embed_type': type,
        'embed_control': config.showedits,
        'embed_host_url': location.href,
        'comment_edited': thing.edited,
        'comment_deleted': thing.deleted,
        'uuid': App.utils.uuid(),
      };

      // If the creation field doesn't exist (due to manual modification, bad
      // oEmbed plugin, etc.), don't send the field at all to avoid messing up
      // the data pipeline.
      if (config.created !== "null") {
        data['embed_ts_created'] = config.created;
      }
  
      for (var name in payload) {
        data[name] = payload[name];
      }
  
      return data;
    };
  }

  setInterval(checkHeight, 100);

  r.frames.receiveMessage(window.parent, 'pong.embed', function(e) {
    var type = e.detail.type;
    var options = e.detail.options;
    var location = e.detail.location;
    var createPayload = createPayloadFactory(location);

    clipComments();

    if (options.track === false) {
      return;
    }

    var tracker = new App.PixelTracker({
      url: config.eventtracker_url,
      anonymousUrl: config.anon_eventtracker_url,
    });

    tracker.send(createPayload(type, 'view'), {anonymous: true});
  
    function trackLink(e) {
      var el = this;
      var base = document.getElementsByTagName('base');
      var target = el.target || (base && base[0] && base[0].target);
      var newTab = target === '_blank';
      var payload = createPayload(type, 'click', {
        'redirect_url': el.href,
        'redirect_type': el.getAttribute('data-redirect-type'),
        'redirect_dest': el.host,
        'redirect_thing_id': el.getAttribute('data-redirect-thing'),
      });
      var redirectParams = {
        "data": JSON.stringify(payload),
        "url": el.href,
      };

      if (el.href.indexOf(config.event_clicktracker_url) === -1) {
        // Use a DOM object for easier query manipulation
        var tmpLink = document.createElement('a');
        tmpLink.href = config.event_clicktracker_url;
        tmpLink.search = '?' + App.utils.serialize(redirectParams);

        // Rewrite our URL to our event-driven URL
        el.href = tmpLink.href;
      }

      if (!newTab) {
        window.top.location.href = el.href;
      }

      return newTab;
    }

    function trackAction(e) {
      var el = this;
      var action = el.getAttribute('data-track-action');

      tracker.send(createPayload(type, action), {anonymous: true});

      return false;
    }

    var trackLinks = document.getElementsByTagName('a');

    for (var i = 0, l = trackLinks.length; i < l; i++) {
      var link = trackLinks[i];
  
      if (link.getAttribute('data-redirect-type')) {
        trackLinks[i].addEventListener('click', trackLink, false);
      } else if (link.getAttribute('data-track-action')) {
        trackLinks[i].addEventListener('click', trackAction, false);
      }
    }

  });

  r.frames.postMessage(window.parent, 'ping.embed', {
    config: config,
  });

})((window.rembeddit = window.rembeddit || {}), window.r, this);
