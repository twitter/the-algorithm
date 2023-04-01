;(function($, undefined) {
    /* Special interpolation to allow python %-style substitution. Only strings like %(blah)s are allowed. */
    var INJECT_TEMPLATE = _.template(_.unescape(r.config.embed_inject_template), false, { "escape": /%\(([^\n\)]+)\)s/g});

    var embedBodyTemplate = _.template(
      '<h4  class="modal-title">' +
        _.escape(r._('Embed preview:')) +
      '</h4>' +
      '<div id="embed-preview">' +
          '<%= html %>' +
      '</div>' +
      '<% if (!root) { %>' +
          '<div class="c-checkbox">' +
              '<label class="remember">' +
                  '<input type="checkbox" name="parent" <% if (parent) { %> checked <% } %>>' +
                  _.escape(r._('Include parent comment.')) +
              '</label>' +
          '</div>' +
      '<% } %>' +
      '<div class="c-checkbox">' +
          '<label>' +
              '<input type="checkbox" name="live" <% if (!live) { %> checked <% } %> data-rerender="false">' +
              _.escape(r._('Do not show comment if edited.')) +
          '</label>' +
          '&nbsp;' +
          '<a href="javascript: void 0;" class="c-toggle" data-toggle="#live-help">' +
            _.escape(r._('Learn more')) +
          '</a>' +
          '<div id="live-help" class="c-help-block c-toggle-content">' +
            '<p>' +
              _.escape(r._('When checked, if an embedded comment is later edited, the embedded comment text will be replaced by a link back to the current version of the comment on reddit.')) +
            '</p>' +
            '<p>' +
              '<a href="https://www.reddit.com/r/reddit.com/wiki/embeds">' +
                _.escape(r._('This parameter can be changed after embedding.')) +
              '</a>' +
            '</p>' +
          '</div>' +
      '</div>'
    );

    var embedFooterTemplate = _.template(
      '<h4 class="modal-title">' +
          '<label for="embed-code">' +
              _.escape(r._('Copy this code and paste it into your website:')) +
          '</label>' +
      '</h4>' +
      '<textarea class="c-form-control" id="embed-code" rows="3" readonly>' +
          '<%- html %>' +
      '</textarea>'
    );

    var embedCodeTemplate = _.template(
      '<div class="reddit-embed" ' +
         ' data-embed-media="<%- media %>" ' +
         '<% if (parent) { %> data-embed-parent="true" <% } %>' +
         '<% if (live) { %> data-embed-live="true" <% } %>' +
         ' data-embed-created="<%- new Date().toISOString() %>">' +
        '<a href="<%- comment %>">Comment</a> from discussion <a href="<%- link %>"><%- title %></a>.' +
      '</div>'
    );

    function absolute(url) {
      if (/^https?:\/\//.test(url)) {
        return url;
      }

      return 'https://' + location.host + '/' + (url.replace(/^\//, ''));
    }

    function getEmbedOptions(data) {
      var defaults = {
        live: true,
        parent: false,
        media: location.host,
        created: (new Date()).toISOString(),
      };

      data = _.defaults({}, data, defaults);
      data.comment = absolute(data.comment);
      data.link = absolute(data.link);

      return _.extend({
        html: INJECT_TEMPLATE(data),
      }, data);
    }

    function serializeOptions(options) {
      return JSON.stringify(_.pick(options, 'live', 'parent'));
    }

    function initFrame(popup, options) {
      var $preview = popup.$.find('#embed-preview');
      var serializedOptions = typeof options !== 'string' ?
        serializeOptions(options) : options;

      window.rembeddit.preview = true;
      window.rembeddit.init({track: false}, function() {
        var height = 0;

        var reflow = setInterval(function() {
          var $next = $preview.find('iframe:last-child');
          var newHeight = $next.height();

          if (height !== newHeight) {
            height = newHeight;
          } else {
            clearInterval(reflow);

            $preview.find('iframe')
                    .hide()
                  .last()
                    .show()
                    .attr('data-options', serializedOptions);

            $preview.css('height', 'auto');
          }
        }, 100);
      });
    }

    var tracker = new window.rembeddit.PixelTracker({
      url: r.config.eventtracker_url,
    });

    $('body').on('click', '.embed-comment', function(e) {
      var $el = $(e.target);
      var data = $el.data();
      var embedOptions = getEmbedOptions(data);
      var popup = new r.ui.Popup({
        className: 'embed-modal',
        content: embedBodyTemplate(embedOptions),
        footer: embedFooterTemplate(embedOptions),
      });
      var $textarea = popup.$.find('textarea');
      var $preview = popup.$.find('#embed-preview');
      var created = false;

      popup.$.find('[data-toggle]').togglable();

      popup.$.on('change', '[type="checkbox"]', function(e) {
        var option = e.target.name;
        var $option = $(e.target);
        var prev = $el.data(option);

        if (prev === undefined) {
          prev = embedOptions[option];
        }

        $el.data(e.target.name, !prev);

        var data = $el.data();
        var options = getEmbedOptions(data);
        var serializedOptions = serializeOptions(options);
        var html = options.html;
        var height = $preview.height();

        $textarea.val(html);

        if ($option.data('rerender') !== false) {
          var selector = '[data-options="' + r.utils.escapeSelector(serializedOptions) + '"]';
          var $cached = $preview.find(selector);

          if ($cached.length) {
            $cached.show().siblings().hide();
          } else {
            $preview.height(height).append($(html).hide());

            initFrame(popup, serializedOptions);
          }
        }
      });

      $textarea.on('focus', function(e) {
        $(this).select().one('mouseup', function(e) {
          e.preventDefault();
        });

        if (!created) {
          var data = $el.data();
          var options = getEmbedOptions(data);
          var now = new Date();
          var ts = now.getTime();

          tracker.send({
            'event_topic': 'embed',
            'event_name': 'embed_create',
            'event_ts': ts,
            'event_ts_utc_offset': now.getTimezoneOffset() / -60,
            'embed_type': options.parent ? 'comment_and_parent' : 'comment',
            'user_agent': navigator.userAgent,
            'user_id': r.config.user_id,
            'logged_in_status': !!r.config.logged,
            'sr_id': r.utils.fullnameToId(r.config.cur_site),
            'sr_name': r.config.post_site,
            'embed_id': r.utils.fullnameToId($el.thing_id()),
            'embed_created_ts': ts,
            'embed_control': options.live,
            'embed_host_url': location.href,
            'embed_version': window.rembeddit.VERSION,
          });

          created = true;
        }
      });

      popup.on('closed.r.popup', function() {
        popup.$.remove();
      });

      popup.on('show.r.popup', function() {
        $preview.find('.reddit-embed').hide();
      });

      popup.on('opened.r.popup', function() {
        initFrame(popup, embedOptions);
      });

      popup.show();

    });

})(window.jQuery);
