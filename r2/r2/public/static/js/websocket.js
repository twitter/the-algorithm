r.WebSocket = function (url) {
    this._url = url
    this._connectionAttempts = 0

    this.on({
        'message:refresh': this._onRefresh,
    }, this)
}
_.extend(r.WebSocket.prototype, Backbone.Events, {
    _backoffTime: 2000,
    _maximumRetries: 9,
    _retryJitterAmount: 3000,

    start: function () {
        var websocketsAvailable = 'WebSocket' in window
        if (websocketsAvailable) {
            this._connect()
        }
    },

    _connect: function () {
        r.debug('websocket: connecting')
        this.trigger('connecting')

        this._connectionStart = Date.now()
        this._socket = new WebSocket(this._url)
        this._socket.onopen = _.bind(this._onOpen, this)
        this._socket.onmessage = _.bind(this._onMessage, this)
        this._socket.onclose = _.bind(this._onClose, this)

        this._connectionAttempts += 1
    },

    _sendStats: function (payload) {
      if (!r.config.stats_domain) {
        return
      }

      $.ajax({
        type: 'POST',
        url: r.config.stats_domain,
        data: JSON.stringify(payload),
        contentType: 'application/json; charset=utf-8',
      })
    },

    _onOpen: function (ev) {
        r.debug('websocket: connected')
        this.trigger('connected')
        this._connectionAttempts = 0

        this._sendStats({
          websocketPerformance: {
            connectionTiming: Date.now() - this._connectionStart,
          },
        })
    },

    _onMessage: function (ev) {
        var parsed = JSON.parse(ev.data)
        r.debug('websocket: received "' + parsed.type + '" message')
        this.trigger('message message:' + parsed.type, parsed.payload)
    },

    _onRefresh: function () {
        // delay a random amount to reduce thundering herd
        var delay = Math.random() * 300 * 1000
        setTimeout(function () { location.reload() }, delay)
    },

    _onClose: function (ev) {
        if (this._connectionAttempts < this._maximumRetries) {
            var baseDelay = this._backoffTime * Math.pow(2, this._connectionAttempts),
                jitter = (Math.random() * this._retryJitterAmount) - (this._retryJitterAmount / 2),
                delay = Math.round(baseDelay + jitter)
            r.debug('websocket: connection lost, reconnecting in ' + delay + 'ms')
            r.debug("(can't connect? Make sure you've allowed https access in your browser.)")
            this.trigger('reconnecting', delay)
            setTimeout(_.bind(this._connect, this), delay)
        } else {
            r.debug('websocket: maximum retries exceeded. bailing out')
            this.trigger('disconnected')
        }

        this._sendStats({
          websocketError: {
            error: 1,
          },
        })
    }
})
