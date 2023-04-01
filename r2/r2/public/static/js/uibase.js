r.ui = {}

r.ui.Base = function(el) {
    this.$el = $(el)
}

r.ui.collapsibleSideBox = function(id) {
    var $el = $('#'+id)
    return new r.ui.Collapse($el.find('.title'), $el.find('.content'), id)
}

r.ui.Collapse = function(el, target, key) {
    r.ui.Base.call(this, el)
    this.target = target
    this.key = 'ui.collapse.' + key
    this.isCollapsed = store.safeGet(this.key) == true
    this.$el.click($.proxy(this, 'toggle', null, false))
    this.toggle(this.isCollapsed, true)
}
r.ui.Collapse.prototype = {
    animDuration: 200,

    toggle: function(collapsed, immediate) {
        if (collapsed == null) {
            collapsed = !this.isCollapsed
        }

        var duration = immediate ? 0 : this.animDuration
        if (collapsed) {
            $(this.target).slideUp(duration)
        } else {
            $(this.target).slideDown(duration)
        }

        this.isCollapsed = collapsed
        store.safeSet(this.key, collapsed)
        this.update()
    },

    update: function() {
        this.$el.find('.collapse-button').text(this.isCollapsed ? '+' : '-')
    }
}

r.ui.Summarize = function(el, maxCount) {
    r.ui.Base.call(this, el)
    this.maxCount = maxCount

    this._updateItems()
    if (this.$hiddenItems.length > 0) {
        this.$toggleButton = $('<button class="expand-summary">')
            .click($.proxy(this, '_toggle'))
        this.$el.after(this.$toggleButton)
        this._summarize()
    }
}
r.ui.Summarize.prototype = {
    _updateItems: function() {
        var $important = this.$el.children('.important'),
            $unimportant = this.$el.children(':not(.important)'),
            unimportantToShow = this.maxCount
                                ? Math.max(0, this.maxCount - $important.length)
                                : 0,
            $unimportantToShow = $unimportant.slice(0, unimportantToShow - 1)

        this.$summaryItems = $important.add($unimportantToShow)
        this.$hiddenItems = $unimportant.slice(unimportantToShow)
    },

    _summarize: function() {
        this.$el.addClass('summarized')
        this.$hiddenItems.hide()

        this.$toggleButton.text(r._('… and %(count)s more ⇒').format({
            count: this.$hiddenItems.length
        }))
    },

    _expand: function() {
        this.$el.removeClass('summarized')
        this.$hiddenItems.show()
        this.$toggleButton.text(r._('⇐ less'))
    },

    _toggle: function(e) {
        if (this.$el.hasClass('summarized')) {
            this._expand()
        } else {
            this._summarize()
        }
        e.preventDefault()
    }
}
