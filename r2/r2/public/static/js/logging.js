r.logging = {}

r.logging.pageAgeLimit = 5*60  // seconds
r.logging.sendThrottle = 8  // seconds

r.logging.exceptionMessageTemplate = _.template('Client Error: "<%= errorType %>" thrown at ' +
                                        'L<%= line %>:<%= character %> ' +
                                        'in <%= file %> Message: "<%= message %>"')

r.logging.defaultExceptionValues = {
  message: 'UNKNOWN MESSAGE',
  file: 'UNKNOWN FILE',
  line: '?',
  character: '?',
  errorType: 'UNKNOWN ERROR TYPE',
}

r.logging.sendException = function(exception) {
  if (!exception) {
    throw 'No exception object was passed in.'
  }

  _.defaults(exception, r.logging.defaultExceptionValues)
  var errorMessage = r.logging.exceptionMessageTemplate(exception)

  r.logging.sendError(errorMessage, { tag: 'unknown' })
}

r.logging.init = function() {
    _.each(['debug', 'log', 'warn', 'error'], function(name) {
        // suppress debug messages unless config.debug is set
        r[name] = (name != 'debug' || r.config.debug)
                && window.console && console[name]
                ? _.bind(console[name], console)
                : function() {}
    })
    r.sendError = r.logging.sendError
}

r.logging.serverLogger = {
    logCount: 0,
    _queuedLogs: [],

    queueLog: function(logData) {
        // Just in case we get an error before config is initialized. Can happen while parsing files.
        if (!r.config) {
          return
        }

        if (!r.warn) {
          r.warn = function(){}
        }

        if (this.logCount >= 3) {
            r.warn('Not sending debug log; already sent', this.logCount)
            return
        }

        // don't send messages for pages older than 5 minutes to prevent CDN 
        // cached pages from slamming us if we need to turn off logs
        var pageAge = (new Date / 1000) - r.config.server_time
        if (Math.abs(pageAge) > r.logging.pageAgeLimit) {
            r.warn('Not sending debug log; page too old:', pageAge)
            return
        }

        if (!r.config.send_logs) {
            r.warn('Server-side debug logging disabled')
            return
        }

        logData.url = window.location.toString()
        this._queuedLogs.push(logData)
        this.logCount++

        // defer so that errors get batched until JS yields
        _.defer(_.bind(function() {
            this._sendLogs()
        }, this))
    },

    _sendLogs: _.throttle(function() {
        var queueCount = this._queuedLogs.length
        r.ajax({
            type: 'POST',
            url: '/web/log/error.json',
            data: {logs: JSON.stringify(this._queuedLogs)},
            headers: {
                'X-Loggit': true
            },
            success: function() {
                r.log('Sent', queueCount, 'debug logs to server')
            },
            error: function(xhr, err, status) {
                r.warn('Error sending debug logs to server:', err, ';', status)
            }
        })
        this._queuedLogs = []
    }, r.logging.sendThrottle * 1000)
}

r.logging.sendError = function() {
    var args = _.toArray(arguments)
    var lastArg = _.last(args)
    var options = {}

    if (_.isObject(lastArg)) {
      options = lastArg
      args.pop()
    }

    var log = _.defaults({
      msg: args.join(' ')
    }, options)

    if (r.error) {
      r.error.apply(r, arguments)
    }

    r.logging.serverLogger.queueLog(log)
}

r.hooks.get('setup').register(function() {
    r.logging.init();
});
