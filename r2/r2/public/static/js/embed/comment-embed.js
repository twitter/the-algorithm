;(function(App, window, undefined) {

  var RE_ABS = /^https?:\/\//i;
  var RE_COMMENT = /\/?r\/[\w_]+\/comments\/(?:\S+\/){2,}[\w_]+\/?/i;
  var PROTOCOL = location.protocol === 'file:' ? 'https:' : '';

  function isComment(anchor) {
    return RE_ABS.test(anchor.href) && RE_COMMENT.test(anchor.pathname);
  }

  function getCommentPathname(anchor) {
    return isComment(anchor) && anchor.pathname.replace(/^\//, '');
  }

  function getCommentUrl(links, host) {
    var pathname;

    for (var i = 0, l = links.length; i < l; i++) {
      if ((pathname = getCommentPathname(links[i]))) {
        break;
      }
    }

    return '//' + host + '/' + pathname;
  }

  function getEmbedUrl(commentUrl, el) {
    var context = 0;
    var showedits = el.getAttribute('data-embed-live');

    if (el.getAttribute('data-embed-parent') === 'true') {
      context++;
    }

    var query = 'embed=true' +
                '&context=' + context +
                '&depth=' + (++context) +
                '&showedits=' + (showedits === 'true') +
                '&created=' + el.getAttribute('data-embed-created') +
                '&showmore=false';

    return PROTOCOL + (commentUrl.replace(/\/$/,'')) + '?' + query;
  }

  App.init = function(options, callback) {
    options = options || {};
    callback = callback || function() {};

    var embeds = document.querySelectorAll('.reddit-embed');

    [].forEach.call(embeds, function(embed) {
      if (embed.getAttribute('data-initialized')) {
        return;
      }

      embed.setAttribute('data-initialized', true);

      var iframe = document.createElement('iframe');
      var anchors = embed.getElementsByTagName('a');
      var commentUrl = getCommentUrl(anchors, embed.getAttribute('data-embed-media'));

      if (!commentUrl) {
        return;
      }

      r.frames.addPostMessageOrigin(embed.getAttribute('data-embed-media'));
      r.frames.listen('embed');

      iframe.height = iframe.style.height = 0;
      iframe.width = iframe.style.width = '100%';
      iframe.scrolling = 'no';
      iframe.frameBorder = 0;
      iframe.allowTransparency = true;
      iframe.style.display = 'none';
      iframe.style.maxWidth = '800px';
      iframe.style.minWidth = '220px';
      iframe.style.margin = '10px 0';
      iframe.style.borderRadius = '5px';
      iframe.style.boxShadow = '0 0 5px 0.5px rgba(0, 0, 0, 0.05)';
      iframe.style.borderColor = 'rgba(199,199,199, 0.55)';
      iframe.style.borderWidth = '1px';
      iframe.style.borderStyle = 'solid';
      iframe.style.boxSizing = 'border-box';
      iframe.src = getEmbedUrl(commentUrl, embed);

      r.frames.receiveMessageOnce(iframe, 'ping.embed', function(e) {
        embed.parentNode.removeChild(embed);
        iframe.style.display = 'block';

        callback(e);
        r.frames.postMessage(iframe.contentWindow, 'pong.embed', {
          type: embed.getAttribute('data-embed-parent') === 'true' ?
            'comment_and_parent' : 'comment',
          location: location,
          options: options,
        });
      });

      var resizer = r.frames.receiveMessage(iframe, 'resize.embed', function(e) {
        if (!iframe.parentNode) {
          resizer.off();

          return;
        }

        iframe.height = iframe.style.height = (e.detail + 'px');
      });

      embed.parentNode.insertBefore(iframe, embed);
    });
  };

  if (App.preview) {
    return;
  }

  App.init();

})((window.rembeddit = window.rembeddit || {}), window.r, this);
