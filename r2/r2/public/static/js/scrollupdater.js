!function(r, $){
    r.ScrollUpdater = Backbone.View.extend({
        selector: null,
        startUpdate: function () {},
        update: function ($el) {},
        endUpdate: function ($els) {},

        start: function() {
            this._resetScrollState()
            this._listen()
            return this
        },

        restart: function() {
            this._resetScrollState()
            return this
        },

        _resetScrollState: function() {
            this._elements = this.$el.find(this.selector)
            _.sortBy(this._elements, function(el) {
                return $(el).offset().top
            })

            this._curIndex = 0
            this._lastScroll = null
            this._toUpdate = []
            this._totalTime = 0

            // Trigger once now to detect any elements currently in view.
            _.defer($.proxy(this, '_updateThings'))
        },

        _listen: function() {
            var throttledUpdate = _.throttle($.proxy(this, '_updateThings'), 20)
            $(window).on('scroll', throttledUpdate)
        },

        _updateThings: function(ev) {
            if (!this._elements.length) {
                return
            }

            var startTime = new Date()

            // update the current page of elements and half a page in the
            // direction of motion
            var $win = $(window),
                winHeight = $win.height(),
                scrollTop = $win.scrollTop(),
                ceiling = scrollTop,
                floor = scrollTop + winHeight

            if (scrollTop < this._lastScroll) {
                ceiling = Math.max(ceiling - Math.floor(winHeight / 2), 0)
            } else {
                floor += Math.ceil(winHeight / 2)
            }

            // scan to ceiling to set the cursor
            var idx = this._curIndex,
                $cur = $(this._elements[idx])

            if ($cur.offset().top < ceiling) {
                // forward
                while (idx < this._elements.length-1 && $cur.offset().top < ceiling) {
                    $cur = $(this._elements[idx])
                    idx++
                }
            } else {
                // backward
                while (idx > 0 && $cur.offset().top > ceiling) {
                    $cur = $(this._elements[idx])
                    idx--
                }
            }

            // update forward to floor
            var count = 0
            do {
                $cur = $(this._elements[idx])
                this._toUpdate.push($cur)
                idx++
                count++
            } while (idx <= this._elements.length-1 && $cur.offset().top <= floor)

            this._curIndex = idx - 1
            this._lastScroll = scrollTop

            var endTime = new Date()
            this._totalTime += endTime - startTime

            this._doUpdates()
        },

        cutoff: 1000 / 60,
        _doUpdates: function() {
            this.startUpdate()

            var startTime = new Date(),
                endTime = startTime,
                count = 0,
                els = []

            while (endTime - startTime < this.cutoff) {
                if (!this._toUpdate.length) {
                    break
                }
                var $el = this._toUpdate.shift()
                els.push($el)
                this.update($el)
                count++
                endTime = new Date()
            }

            this._totalTime += endTime - startTime

            if (this._toUpdate.length) {
                _.defer($.proxy(this, '_doUpdates'))
            }

            this.endUpdate($(els))
        }
    })
}(r, jQuery)
