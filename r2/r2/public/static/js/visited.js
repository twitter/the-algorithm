r.visited = {
    key: 'visited',

    init: function() {
        this.sendVisits = _.throttle(this._sendVisits, 100)
        if (r.config.logged && r.config.store_visits) {
            $('.content').on('click mousedown keydown', '.link:not(.visited) a.title, .link:not(.visited) a.thumbnail', _.bind(this.onVisit, this))

            // listen for custom "visit" event for third-party extensions to trigger in a non-UI specific way
            $('.content').on('visit', '.link:not(.visited)', _.bind(this.onVisit, this))

            // send any pending visits
            this.sendVisits()
        }
    },

    onVisit: function(ev) {
        if (ev.type === 'keydown' && ev.which !== 13) {
            // only handle enter key presses
            return;
        }
        if (ev.type === 'mousedown') {
            // Hold out for a "click" event for left clicks (so we can ignore
            // dragging,) and throw out right clicks.
            if(ev.which === 1 || ev.which === 3) {
                return;
            }
        }
        // IE might generate a `click` event for middle click as well!
        if (ev.type === 'click' && ev.which !== 1) {
            return;
        }

        this.storeVisit($(ev.target).closest('.thing').data('fullname'))
        this.sendVisits()
    },

    storeVisit: function(fullname) {
        var fullnames = store.safeGet(this.key) || []
        fullnames.push(fullname)
        store.safeSet(this.key, fullnames)
    },

    _sendVisits: function() {
        var fullnames = store.safeGet(this.key) || []
        if (!fullnames.length) {
            return
        }

        fullnames = _.last(_.uniq(fullnames), 100)

        r.ajax({
            type: 'POST',
            url: '/api/store_visits',
            data: {
                'links': fullnames.join(',')
            }
        })

        store.safeSet(this.key, [])
        $.things.apply($, fullnames).addClass("visited");
    }
}
