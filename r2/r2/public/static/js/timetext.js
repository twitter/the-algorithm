!function(r, $){
    if (!Date.now) {
        Date.now = function now() {
            return new Date().getTime()
        }
    }

    var clientTimeInit = Date.now();

    var CHUNKS = [
        [60 * 60 * 24 * 365, r.NP_('a year ago', '%(num)s years ago')],
        [60 * 60 * 24 * 30, r.NP_('a month ago', '%(num)s months ago')],
        [60 * 60 * 24, r.NP_('a day ago', '%(num)s days ago')],
        [60 * 60, r.NP_('an hour ago', '%(num)s hours ago')],
        [60, r.NP_('a minute ago', '%(num)s minutes ago')],
    ]

    var defaults = {
        maxage: 24 * 60 * 60
    }

    function TimeText(opts) {
        this.opts = _.defaults(opts || {}, defaults)

        this.elCache = $([])

        this.refresh = _.throttle(this._refresh, 1000)

        setInterval($.proxy(this.refresh, this), 20 * 1000)
        this.refresh()
    }

    TimeText.prototype._refresh = function(){
        var now = TimeText.now()

        this.elCache.each($.proxy(function (i, el) {
            this.refreshOne(el, now)
        }, this))
    }

    TimeText.prototype.updateCache = function(elCache) {
        this.elCache = elCache
        this.refresh()
    }

    TimeText.prototype.refreshOne = function (el, now) {
        if (!now){
            now = TimeText.now()
        }

        var $el = $(el)
        var timestamp = r.utils.parseTimestamp($el)
        var text
        var age
        
        age = (now - timestamp) / 1000

        if (this.opts.maxage !== false && age > this.opts.maxage) {
            $el.removeClass('live-timestamp')
            return
        }

        text = this.formatTime($el, age, timestamp, now)
        $el.text(text)
    }

    TimeText.prototype.formatTime = function($el, age, timestamp, now) {
        var text = r._('just now')
        $.each(CHUNKS, function(ix, chunk) {
            var count = Math.floor(age / chunk[0])
            var keys

            if (count > 0) {
                keys = chunk[1]
                text = r.P_(keys[0], keys[1], count).format({num: count})
                return false
            }
        })
        return text
    }

    TimeText.clientOffset = 0;

    TimeText.now = function() {
        return Date.now() - TimeText.clientOffset;
    }

    TimeText.init = function() {
        var serverTimeInit = r.config.server_time * 1000;
        var delta = clientTimeInit - serverTimeInit;
        var msPerHour = 1000 * 60 * 60;
        var clientHourOffset = Math.round(delta / msPerHour);

        this.clientOffset = clientHourOffset * msPerHour;
    }

    r.TimeText = TimeText
}(r, jQuery)
