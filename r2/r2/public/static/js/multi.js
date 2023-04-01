r.multi = {
    init: function() {
        this.multis = new r.multi.GlobalMultiCache()
        this.mine = new r.multi.MyMultiCollection()

        // this collection gets fetched frequently by hover bubbles.
        this.mine.fetch = _.throttle(this.mine.fetch, 60 * 1000)

        var detailsEl = $('.multi-details')
        if (detailsEl.length) {
            var multi = this.multis.touch(detailsEl.data('path'))
            multi.fetch()

            var detailsView = new r.multi.MultiDetails({
                model: multi,
                el: detailsEl
            }).render()
            var subredditList = new r.multi.SubredditList({
                model: multi,
                el: detailsEl
            })

            if (location.hash == '#created') {
                detailsView.focusAdd()
            }

            // if page has a recs box, wire it up to refresh with the multi.
            var recsEl = $('#multi-recs')
            if (recsEl.length) {
                detailsView.initRecommendations(recsEl)
            }
        }

        var subscribeBubbleGroup = {}
        $('.subscribe-button').each(function(idx, el) {
            new r.multi.SubscribeButton({
                el: el,
                bubbleGroup: subscribeBubbleGroup
            })
        })

        $('.listing-chooser').each(function(idx, el) {
            new r.multi.ListingChooser({el: el})
        })
    }
}

r.multi.MultiRedditList = Backbone.Collection.extend({
    model: Backbone.Model.extend({
        initialize: function() {
            this.id = this.get('name').toLowerCase()
        }
    }),

    comparator: function(model) {
        return model.id
    },

    getByName: function(name) {
        return this.get(name.toLowerCase())
    }
})

r.multi.MultiReddit = Backbone.Model.extend({
    idAttribute: 'path',
    url: function() {
        return r.utils.joinURLs('/api/multi', this.id)
    },

    defaults: {
        visibility: 'private'
    },

    initialize: function(attributes, options) {
        this.uncreated = options && !!options.isNew
        this.subreddits = new r.multi.MultiRedditList(this.get('subreddits'), {
            url: this.url() + '/r/',
            parse: true
        })
        this.on('change:subreddits', function(model, value) {
            this.subreddits.set(value, {parse: true})
        }, this)
        this.subreddits.on('request', function(model, xhr, options) {
            this.trigger('request', model, xhr, options)
        }, this)
    },

    parse: function(response) {
        return response.data
    },

    toJSON: function() {
        data = Backbone.Model.prototype.toJSON.apply(this)
        data.subreddits = this.subreddits.toJSON()
        return data
    },

    isNew: function() {
        return this.uncreated
    },

    name: function() {
        return this.get('path').split('/').pop()
    },

    sync: function(method, model, options) {
        var res = Backbone.sync.apply(this, arguments)
        if (method == 'create') {
            res.done(_.bind(function() {
                // upon successful creation, unset new flag
                this.uncreated = false
            }, this))
        }
        return res
    },

    addSubreddit: function(names, options) {
        names = r.utils.tup(names)
        if (names.length == 1) {
            this.subreddits.create({name: names[0]}, options)
        } else {
            // batch add by syncing the entire multi
            var subreddits = this.subreddits,
                tmp = subreddits.clone()
            tmp.add(_.map(names, function(srName) {
                return {name: srName}
            }))

            // temporarily swap out the subreddits collection so we can
            // serialize and send the new data without updating the UI
            // this is similar to how the "wait" option is handled in
            // Backbone.Model.set
            this.subreddits = tmp
            this.save(null, options)
            this.subreddits = subreddits
        }
    },

    removeSubreddit: function(name, options) {
        this.subreddits.getByName(name).destroy(options)
    },

    _copyOp: function(op, newCollection, newName) {
        var deferred = new $.Deferred
        Backbone.ajax({
            type: 'POST',
            url: '/api/multi/' + op,
            data: {
                from: this.get('path'),
                to: newCollection.pathByName(newName)
            },
            success: _.bind(function(resp) {
                if (op == 'rename') {
                    this.trigger('destroy', this, this.collection)
                }
                var multi = r.multi.multis.reify(resp)
                newCollection.add(multi)
                deferred.resolve(multi)
            }, this),
            error: _.bind(deferred.reject, deferred)
        })
        return deferred
    },

    copyTo: function(newCollection, name) {
        return this._copyOp('copy', newCollection, name)
    },

    renameTo: function(newCollection, name) {
        return this._copyOp('rename', newCollection, name)
    },

    getSubredditNames: function() {
        return this.subreddits.pluck('name')
    }
})

r.multi.MyMultiCollection = Backbone.Collection.extend({
    url: '/api/multi/mine',
    model: r.multi.MultiReddit,
    comparator: function(model) {
        return model.get('path').toLowerCase()
    },

    parse: function(data) {
        return _.map(data, function(multiData) {
            return r.multi.multis.reify(multiData)
        })
    },

    pathByName: function(name) {
        return '/user/' + r.config.logged + '/m/' + name
    }
})

r.multi.GlobalMultiCache = Backbone.Collection.extend({
    model: r.multi.MultiReddit,

    touch: function(path) {
        var multi = this.get(path)
        if (!multi) {
            multi = new this.model({
                path: path
            })
            this.add(multi)
        }
        return multi
    },

    reify: function(response) {
        var data = this.model.prototype.parse(response),
            multi = this.touch(data.path)

        multi.set(data)
        return multi
    }
})

r.multi.MultiSubredditItem = Backbone.View.extend({
    tagName: 'li',

    template: _.template('<a href="/r/<%- sr_name %>">/r/<%- sr_name %></a><button class="remove-sr">x</button>'),

    events: {
        'click .remove-sr': 'removeSubreddit'
    },

    render: function() {
        this.$el.append(this.template({
            sr_name: this.model.get('name')
        }))

        if (r.config.logged) {
            this.bubble = new r.multi.MultiSubscribeBubble({
                parent: this.$el,
                group: this.options.bubbleGroup,
                srName: this.model.get('name')
            })
        }

        return this
    },

    remove: function() {
        if (this.bubble) {
            this.bubble.remove()
        }
        Backbone.View.prototype.remove.apply(this)
    },

    removeSubreddit: function(ev) {
        this.options.multi.removeSubreddit(this.model.get('name'))
    }
})

r.multi.SubredditList = Backbone.View.extend({
    events: {
        'submit .add-sr': 'addSubreddit'
    },

    initialize: function() {
        this.listenTo(this.model.subreddits, 'add', this.addOne)
        this.listenTo(this.model.subreddits, 'remove', this.removeOne)
        this.listenTo(this.model.subreddits, 'sort', this.resort)
        new r.ui.ConfirmButton({el: this.$('button.delete')})

        this.listenTo(this.model.subreddits, 'add remove', function() {
            r.ui.showWorkingDeferred(this.$el, r.ui.refreshListing())
        })

        this.model.on('request', function(model, xhr) {
            r.ui.showWorkingDeferred(this.$el, xhr)
        }, this)

        this.itemView = this.options.itemView || r.multi.MultiSubredditItem
        this.itemViews = {}
        this.bubbleGroup = {}
        this.$('.subreddits').empty()
        this.model.subreddits.each(this.addOne, this)
    },
    
    addOne: function(sr) {
        var view = new this.itemView({
            model: sr,
            multi: this.model,
            bubbleGroup: this.bubbleGroup
        })
        this.itemViews[sr.id] = view
        this.$('.subreddits').append(view.render().$el)
    },

    resort: function() {
        this.model.subreddits.each(function(sr) {
            this.itemViews[sr.id].$el.appendTo(this.$('.subreddits'))
        }, this)
    },

    removeOne: function(sr) {
        this.itemViews[sr.id].remove()
        delete this.itemViews[sr.id]
    },

    addSubreddit: function(ev) {
        ev.preventDefault()

        var nameEl = this.$('.add-sr .sr-name'),
            srNames = nameEl.val()
        srNames = srNames.split(/[+,\-\s]+/)
        // Strip any /r/ or r/ prefixes.
        srNames = srNames.map(function(sr) { return sr.replace(/^\/?r\//, '') })
        srNames = _.compact(srNames)
        if (!srNames.length) {
            return
        }

        nameEl.val('')
        this.$('.add-error').css('visibility', 'hidden')
        this.model.addSubreddit(srNames, {
            wait: true,
            success: _.bind(function() {
                this.$('.add-error').hide()
            }, this),
            error: _.bind(function(model, xhr) {
                var resp = JSON.parse(xhr.responseText)
                this.$('.add-error')
                    .text(resp.explanation)
                    .css('visibility', 'visible')
                    .show()
            }, this)
        })
    }
})

r.multi.MultiDetails = Backbone.View.extend({
    events: {
        'change [name="visibility"]': 'setVisibility',
        'change [name="key_color"]': 'setKeyColor',
        'change [name="icon_name"]': 'setIconName',
        'click .show-copy': 'showCopyMulti',
        'click .show-rename': 'showRenameMulti',
        'click .edit-description': 'editDescription',
        'submit .description': 'saveDescription',
        'confirm .delete': 'deleteMulti'
    },

    initialize: function() {
        this.listenTo(this.model, 'change', this.render)
        this.listenTo(this.model.subreddits, 'add remove reset', this.render)

        this.addBubble = new r.multi.MultiAddNoticeBubble({
            parent: this.$('.add-sr .sr-name'),
            trackHover: false
        })
    },

    // create child model and view to manage recommendations
    initRecommendations: function(recsEl) {
        var recs = new r.recommend.RecommendationList()
        this.recsView = new r.recommend.RecommendationsView({
            collection: recs,
            el: recsEl
        })
 
        // fetch initial data
        if (!this.model.subreddits.isEmpty()) {
            recs.fetchForSrs(this.model.getSubredditNames())
        }
 
        // update recs when multi changes
        this.listenTo(this.model.subreddits, 'add remove reset',
            function() {
                var srNames = this.model.getSubredditNames()
                recs.fetchForSrs(srNames)
            })
        // update multi when a rec is selected
        this.recsView.bind('recs:select',
            function(data) {
                this.model.addSubreddit(data['srName'])
            }, this)
    },

    render: function() {
        var canEdit = this.model.get('can_edit')
        if (canEdit) {
            if (this.model.subreddits.isEmpty()) {
                this.addBubble.show()
            } else {
                this.addBubble.hide()
            }
        }

        this.$el.toggleClass('readonly', !canEdit)
        this.$el.toggleClass('public', this.model.get('visibility') == 'public')

        if (this.model.has('description_html')) {
            this.$('.description .usertext-body').html(
                this.model.get('description_html')
            )
        }

        this.$('.count').text(this.model.subreddits.length)

        return this
    },

    setVisibility: function() {
        this.model.save({
            visibility: this.$('[name="visibility"]:checked').val()
        })
    },

    setKeyColor: function() {
        this.model.save({
            key_color: this.$('[name="key_color"]').val()
        })
    },

    setIconName: function() {
        this.model.save({
            icon_name: this.$('[name="icon_name"]').val()
        })
    },

    showCopyMulti: function() {
        this.$('form.rename-multi').hide()

        var $copyForm = this.$('form.copy-multi')

        $copyForm
            .show()
            .find('.multi-name')
                .val(this.model.name())
                .select()
                .focus()

        if (!this.copyForm) {
            this.copyForm = new r.multi.MultiCopyForm({
                el: $copyForm,
                navOnCreate: true,
                sourceMulti: this.model
            })
        }
    },

    showRenameMulti: function() {
        this.$('form.copy-multi').hide()

        var $renameForm = this.$('form.rename-multi')

        $renameForm
            .show()
            .find('.multi-name')
                .val(this.model.name())
                .select()
                .focus()

        if (!this.renameForm) {
            this.renameForm = new r.multi.MultiRenameForm({
                el: $renameForm,
                navOnCreate: true,
                sourceMulti: this.model
            })
        }
    },

    deleteMulti: function() {
        this.model.destroy({
            success: function() {
                window.location = '/'
            }
        })
    },

    editDescription: function() {
        show_edit_usertext(this.$el)
    },

    saveDescription: function(ev) {
        ev.preventDefault()
        this.model.save({
            'description_md': this.$('.description textarea').val()
        }, {
            success: _.bind(function() {
                hide_edit_usertext(this.$el)
            }, this)
        })
    },

    focusAdd: function() {
        this.$('.add-sr .sr-name').focus()
    }
})

r.multi.MultiAddNoticeBubble = r.ui.Bubble.extend({
    className: 'multi-add-notice hover-bubble anchor-right',
    template: _.template('<h3><%- awesomeness_goes_here %></h3><p><%- add_multi_sr %></p>'),

    render: function() {
        this.$el.html(this.template({
            awesomeness_goes_here: r._('awesomeness goes here'),
            add_multi_sr: r._('add a subreddit to your multi.')
        }))
    }
})

r.multi.SubscribeButton = Backbone.View.extend({
    events: {
        'mouseenter': 'createBubble'
    },

    createBubble: function() {
        if (this.bubble) {
            return
        }

        this.bubble = new r.multi.MultiSubscribeBubble({
            parent: this.$el,
            group: this.options.bubbleGroup,
            srName: String(this.$el.data('sr_name'))
        })

        var bubbleClass = this.$el.data('bubble_class')
        if (bubbleClass) {
            this.bubble.$el.removeClass('anchor-right')
            this.bubble.$el.addClass(bubbleClass)
        }

        this.bubble.queueShow()
    }
})

r.multi.MultiSubscribeBubble = r.ui.Bubble.extend({
    className: 'multi-selector hover-bubble anchor-right',
    template: _.template('<div class="title"><strong><%- title %></strong><a class="sr" href="/r/<%- sr_name %>">/r/<%- sr_name %></a></div><div class="throbber"></div>'),
    itemTemplate: _.template('<label><input class="add-to-multi" type="checkbox" data-path="<%- path %>" <%- checked %>><%- name %><a href="<%- path %>" target="_blank" title="<%- open_multi %>">&rsaquo;</a></label>'),
    itemCreateTemplate: _.template('<label><form class="create-multi"><input type="text" class="multi-name" placeholder="<%- create_msg %>"><div class="error create-multi-error"></div></form></label>'),

    events: {
        'click .add-to-multi': 'toggleSubscribed'
    },

    initialize: function() {
        this.listenTo(this, 'show', this.load)
        this.listenTo(r.multi.mine, 'reset add', _.debounce(this.render, 100))
        r.ui.Bubble.prototype.initialize.apply(this)
    },

    load: function() {
        r.ui.showWorkingDeferred(this.$el, r.multi.mine.fetch())
    },

    render: function() {
        this.$el.html(this.template({
            title: r._('categorize'),
            sr_name: this.options.srName
        }))

        var content = $('<div class="multi-list">')
        r.multi.mine.chain()
            .sortBy(function(multi) {
                // sort multireddits containing this subreddit to the top.
                return multi.subreddits.getByName(this.options.srName)
            }, this)
            .each(function(multi) {
                content.append(this.itemTemplate({
                    name: multi.get('name'),
                    path: multi.get('path'),
                    checked: multi.subreddits.getByName(this.options.srName)
                             ? 'checked' : '',
                    open_multi: r._('open this multi')
                }))
            }, this)
        content.append(this.itemCreateTemplate({
            create_msg: r._('create a new multi')
        }))
        this.$el.append(content)

        this.createForm = new r.multi.MultiCreateForm({
            el: this.$('form.create-multi')
        })
    },

    toggleSubscribed: function(ev) {
        var checkbox = $(ev.target),
            multi = r.multi.mine.get(checkbox.data('path'))
        if (checkbox.is(':checked')) {
            multi.addSubreddit(this.options.srName)
        } else {
            multi.removeSubreddit(this.options.srName)
        }
    }
})

r.multi.MultiCreateForm = Backbone.View.extend({
    events: {
        'submit': 'createMulti'
    },

    createMulti: function(ev) {
        ev.preventDefault()

        var name = this.$('input.multi-name').val()
        name = $.trim(name)
        if (!name) {
            return
        }

        var deferred = this._createMulti(name)

        deferred
            .done(_.bind(function(multi) {
                this.trigger('create', multi)
                if (this.options.navOnCreate) {
                    window.location = multi.get('path') + '#created'
                }
            }, this))
            .fail(_.bind(function(xhr) {
                var resp = JSON.parse(xhr.responseText)
                this.showError(resp.explanation)
            }, this))

        r.ui.showWorkingDeferred(this.$el, deferred)
    },

    _createMulti: function(name) {
        var newMulti = new r.multi.MultiReddit({
                path: r.multi.mine.pathByName(name)
            }, {isNew: true})

        var deferred = new $.Deferred
        r.multi.mine.create(newMulti, {
            wait: true,
            success: _.bind(deferred.resolve, deferred),
            error: function(multi, xhr) {
                deferred.reject(xhr)
            }
        })

        return deferred
    },

    showError: function(error) {
        this.$('.error').text(_.unescape(error)).show()
    },

    focus: function() {
        this.$('.multi-name').focus()
    }
})

r.multi.MultiCopyForm = r.multi.MultiCreateForm.extend({
    _createMulti: function(name) {
        return this.options.sourceMulti.copyTo(r.multi.mine, name)
    }
})

r.multi.MultiRenameForm = r.multi.MultiCopyForm.extend({
    _createMulti: function(name) {
        return this.options.sourceMulti.renameTo(r.multi.mine, name)
    }
})

r.multi.ListingChooser = Backbone.View.extend({
    events: {
        'click .create button': 'createClick',
        'click .grippy': 'toggleCollapsed'
    },

    initialize: function() {
        this.$el.addClass('initialized')

        // transition collapsed state to server pref
        if (store.safeGet('ui.collapse.listingchooser') == true) {
            this.toggleCollapsed(true)
        }
        store.safeSet('ui.collapse.listingchooser')

        // HACK: fudge page heights for long lists of multis / short pages
        var thisHeight = this.$('.contents').height(),
            bodyHeight = $('body').height()
        if (thisHeight > bodyHeight) {
            $('body').css('padding-bottom', thisHeight - bodyHeight + 100)
        }
    },

    createClick: function(ev) {
        if (!this.$('.create').is('.expanded')) {
            ev.preventDefault()
            this.$('.create').addClass('expanded')
            this.createForm = new r.multi.MultiCreateForm({
                el: this.$('.create form'),
                navOnCreate: true
            })
            this.createForm.focus()
        }
    },

    toggleCollapsed: function(value) {
        $('body').toggleClass('listing-chooser-collapsed', value)
        Backbone.ajax({
            type: 'POST',
            url: '/api/set_left_bar_collapsed.json',
            data: {
                'collapsed': $('body').is('.listing-chooser-collapsed')
            }
        })
    }
})
