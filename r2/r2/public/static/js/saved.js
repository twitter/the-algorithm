r.saved = {}

r.saved.SaveCategories = Backbone.Collection.extend({
    model: Backbone.Model.extend({idAttribute: 'category'}),

    url: '/api/saved_categories.json',

    fetchOnce: function() {
        if (!this._fetched) {
            this._fetched = this.fetch()
        }
        return this._fetched
    },

    comparator: function(item) {
        return item.get('category')
    },

    parse: function(response) {
        return response.categories
    }
})

r.saved.SaveDialog = r.ui.Bubble.extend({
    tagName: 'form',
    className: 'hover-bubble anchor-left save-selector',
    confirmTemplate: _.template('<label for="savedcategory"><%- label %></label><span class="throbber"></span><select><option value=""><%- placeholder %></option></select><input maxlength="20" class="savedcategory"  name="savedcategory" placeholder="<%- textplaceholder %>"><input type="submit" value="<%- save %>"><div class="error"></div>'),

    events: {
        "click": "clicked",
        "submit": "save",
        "mouseout": "mouseout",
        "mouseover": "cancelTimeout",
        "change select": "change"
    },

    mouseout: function() {
        if (!this.$el.find('select, .savedcategory').is(":focus")) {
            this.queueHide()
        }
    },

    clicked: function(e) {
        e.stopPropagation()
    },

    initialize: function(options) {
        this.options = options
        this.options.trackHover = false
        r.ui.Bubble.prototype.initialize.apply(this)
        r.saved.categories.fetchOnce().then(_.bind(this.show, this))
        $('body').on('click.savedialog', _.bind(this.hideNow, this))
    },

    hideNow: function() {
        r.ui.Bubble.prototype.hideNow.apply(this)
        $('body').off('click.savedialog')
        this.remove()
    },

    error: function() {
        this.$el.find('select, .savedcategory').attr('disabled', false)
        this.$el.removeClass('working')
        this.$el.find('.error').text(r._('Invalid category name'))
    },

    change: function(e) {
        var input = this.$el.find('.savedcategory')
        var selected = this.$el.find('option:selected').val()
        input.val(selected).focus()
    },

    success: function() {
        var $category = this.$parent.parents('.thing').find('.save-category')
        if ($category.length && this.category) {
            $category.text('category: ' + this.category)
            $category.attr('href', '/user/' + r.config.logged + '/saved/' + this.category)
            $category.show()
        } else {
            $category.hide()
        }
        r.saved.SaveButton.setSaved(this.$parent)
        if (this.category) {
            r.saved.categories.add({category: this.category})
            r.saved.categories.sort()
        }
        this.hide()
    },

    save: function(e) {
        e.preventDefault()
        this.category = this.$el.find('.savedcategory').val()
        this.$el.find('select, .savedcategory').attr('disabled', true)
        if (!this.category) {
            return this.success()
        }
        this.$el.addClass('working')
        r.ajax({
            type: 'POST',
            url: '/api/save',
            data: {'id': this.$parent.thing_id(), 'category': this.category},
            success: this.success,
            error: this.error,
            context: this
        })
    },

    addCategory: function(category) {
        var value = category.get('category')
        this.$el.find('select').append($('<option>').val(value).text(value))
    },

    show: function() {
        r.ui.Bubble.prototype.show.apply(this)
        this.$el.find('.savedcategory').focus()
    },

    render: function() {
        this.$el.html(this.confirmTemplate({
            label: r._('save category'),
            placeholder: r._('no category'),
            save: r._('save'),
            textplaceholder: r._('new category')
        }))
        r.saved.categories.each(this.addCategory, this)
        this.$el.find('select').first().prop('selected', true)
    }
})

r.saved.SaveButton = {
    request: function($el, type, callback) {
        r.ajax({
            type: 'POST',
            url:  '/api/' + type,
            data: {'id': $el.thing_id()},
            success: _.bind(callback, this, $el)
        })
    },

    toggleSaved: function($el) {
        this.isSaved($el) ? this.unsave($el) : this.save($el)
    },

    unsave: function($el) {
        this.request($el, 'unsave', this.setUnsaved)
    },

    save: function($el) {
        this.request($el, 'save', this.setSaved)
        if (r.config.gold) {
            new r.saved.SaveDialog({parent: $el, group: r.saved.SaveButton})
        }
    },

    isSaved: function($el) {
        return $el.thing().hasClass('saved')
    },

    setUnsaved: function($el) {
        var $category = $el.parents('.thing').find('.save-category').hide()
        $el.text(r._('save'))
        $el.thing().removeClass('saved')
    },

    setSaved: function($el) {
        $el.text(r._('unsave'))
        $el.thing().addClass('saved')
    }
}

r.saved.categories = new r.saved.SaveCategories()

r.saved.init = function() {
    $('body').on('click', '.save-button a, a.save-button', function(e) {
        e.stopPropagation()
        e.preventDefault()
        r.saved.SaveButton.toggleSaved($(this))
    })
}
