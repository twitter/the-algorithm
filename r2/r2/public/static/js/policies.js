r.ui.TOCScroller = Backbone.View.extend({
    scrollSpeed: .4,

    events: {
        'click a': 'scrollToLinkHash'
    },

    initialize: function(options) {
        this.$doc = $(options.doc)
        this.$toc = $(options.toc)
        this.$progress = $('<div class="location">').appendTo(this.$toc)
        this.updateProgress = _.throttle(this._updateProgress, 10)
    },

    start: function() {
        $(window).bind('scroll', $.proxy(this, 'updateProgress'))
        $(window).bind('hashchange', _.bind(function(ev) {
            this.updateProgress()
        }, this))
        this.updateProgress()
    },

    scrollToLinkHash: function(ev) {
        var newHash = $(ev.target).attr('href').match(/#.*/)[0]
        this.smoothScrollToHash(newHash)
        this.updateProgress()
        ev.preventDefault()
    },

    smoothScrollToHash: function(newHash) {
        var $heading = this._headingForHash(newHash),
            lastScrollTop = $(document).scrollTop(),
            scrollTarget = Math.min(this._scrollMax(), $heading.offset().top)

        // we need to animate both `html` and `body` to deal with Webkit/non-Webkit inconsistency
        $('html, body')
            .stop(true)
            .animate({scrollTop: scrollTarget}, {
                duration: this.scrollSpeed * Math.abs(scrollTarget - lastScrollTop),
                step: function(now, fx) {
                    var curScrollTop = $(this).scrollTop()
                    if (lastScrollTop > 0 && curScrollTop == 0) {
                        // step called on the element of (html/body) that can't scroll
                        return
                    }

                    if (Math.abs(curScrollTop - lastScrollTop) > 5) {
                        // if the user has scrolled manually, interrupt the animation
                        $('html, body').stop()
                    }
                    lastScrollTop = fx.now
                },
                complete: function() {
                    // this will be called twice due to the two-element
                    // selector above (workaround for firefox/chrome viewport
                    // overflow inconsistency), which doesn't matter because it
                    // is idempotent.
                    location.replace(location.toString().replace(/#.*$/, '') + newHash)
                }
            })
    },

    _scrollMax: function() {
        return $(document).height() - $(window).height()
    },

    _headingForHash: function(hash) {
        return this.$doc.find(document.getElementById(hash.substr(1)))
    },

    _tocFor: function($heading) {
        // heading ids can have periods in them, so we'll use getElementsByClassName :(
        return $(this.$toc[0].getElementsByClassName($heading.attr('id'))).children('a')
    },

    _updateProgress: function() {
        var scrollTop = $(document).scrollTop(),
            scrollMax = this._scrollMax()

        var $hashHeading = this._headingForHash(location.hash).filter('h2')
        if ($hashHeading.length) {
            var distance = Math.min(scrollMax, $hashHeading.offset().top) - scrollTop
            if (Math.abs(distance) <= 10) {
                this.$progress.css('top', this._tocFor($hashHeading).position().top)
                return
            }
        }

        var $nextHeading = $(_.find(this.$doc.children('h2'), function(el) {
                return $(el).offset().top >= scrollTop
            }, this)),
            $prevHeading = $nextHeading.prevAll('h2').first()

        if (!$nextHeading.length || scrollTop == scrollMax) {
            $prevHeading = $nextHeading = $('h2:last')
        }

        if (!$prevHeading.length) {
            this.$progress.css('top', 0)
        } else {
            var nextTop = $nextHeading.offset().top,
                prevTop = $prevHeading.offset().top,
                sectionPercent = (scrollTop - prevTop) / (nextTop - prevTop),
                nextToc = this._tocFor($nextHeading),
                prevToc = this._tocFor($prevHeading),
                nextTocTop = nextToc.position().top,
                prevTocTop = prevToc.position().top

            sectionPercent = r.utils.clamp(sectionPercent, 0, 1)
            var beadTop = prevTocTop + (nextTocTop - prevTocTop) * sectionPercent
            this.$progress.css('top', beadTop)
        }
    }
})

$(function() {
    new r.ui.scrollFixed($('.policy-page .doc-info'))
    new r.ui.TOCScroller({
        el: $('.policy-page'),
        doc: $('.policy-page .md'),
        toc: $('.policy-page .doc-info .toc')
    }).start()
})
