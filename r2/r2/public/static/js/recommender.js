r.recommend = {
    init: function() {
        $('.explore-item').each(function(idx, el) {
            new r.recommend.ExploreItem({el: el})
        })
    }
}

r.recommend.Recommendation = Backbone.Model.extend()

/**
 * Example usage:
 * // generate recs for people who like book subreddits
 * var recs = r.recommend.RecommendationList()
 * recs.fetchForSrs(['books', 'writing'])  // triggers reset event
 * // get a new set of recs
 * recs.fetchNewRecs()  // triggers reset event
 * // the user also likes /r/excerpts so generate recs for it too
 * recs.fetchForSrs(['books', 'writing', 'excerpts'])
 * // keep fetching until none are left
 * while (recs.models.length > 0) {
 *   recs.fetchNewRecs()
 * }
 * // allow previously seen recs to appear again (but results might not be the
 * // same as above because srNames has changed)
 * recs.clearHistory()
 * recs.fetchRecs()
 */
r.recommend.RecommendationList = Backbone.Collection.extend({

    // { srName: 'books' }
    model: r.recommend.Recommendation,

    // names of subreddits for which recommendations are generated
    // (if user likes srNames, he will also like...)
    srNames: [],

    // names of subreddits that should be excluded from future recs because
    // the user has already seen and dismissed them
    dismissed: [],

    // loads recs for srNames and stores srNames so they can be used in future
    // fetches. fires reset event
    fetchForSrs: function(srNames) {
        if (!srNames.length) {  // skip unnecessary request
            this.srNames = []
            this.reset([])
            return
        }
        this.srNames = srNames
        this.fetchRecs()
    },
    
    // adds current recs to the dismissed list so they won't be shown again
    // and refetches. fires reset event
    fetchNewRecs: function() {
        var currentRecs = this.pluck('sr_name')
        this.dismissed = _.union(this.dismissed, currentRecs)
        this.fetchRecs()
    },

    // requests data from the server based on values of member vars
    fetchRecs: function() {
        var url = '/api/recommend/sr/' + this.srNames.join(',')
        this.fetch({ url: url,
                     data: {'omit': this.dismissed.join(',')},
                     reset: true,
                     error: _.bind(function() {
                         this.reset([])
                     }, this)})
    },

    // allows previously dismissed recs to be shown again
    clearHistory: function() {
        this.dismissed = []
    }
})

r.recommend.RecommendationsView = Backbone.View.extend({
    collection: r.recommend.RecommendationList,

    tagName: 'div',

    itemTemplate: _.template('<li class="rec-item"><a href="/r/<%- sr_name %>" title="<%- sr_name %>" target="_blank">/r/<%- sr_name %></a><button class="add add-rec" data-srname="<%- sr_name %>"></button></li>'),

    initialize: function() {
        this.listenTo(this.collection, 'add remove reset', this.render)
    },

    events: {
        'click .add-rec': 'onAddClick',
        'click .more': 'showMore',
        'click .reset': 'resetRecommendations'
    },

    render: function() {
        this.$('.recommendations').empty()
        // if there are results, show them
        if (this.collection.models.length > 0) {
            this.$('.recs').show()
            this.$('.endoflist').hide()
            var el = this.$el
            var view = this
            this.collection.each(function(rec) {
                this.$('.recommendations').append(view.itemTemplate({sr_name: rec.get('sr_name')}))
            }, this)
            this.$el.css({opacity: 1.0})
        // if recs are empty but the dismissed list is not, all available recs
        // have been seen and we give user an option to start over
        } else if (this.collection.dismissed.length > 0) {
            this.$('.recs').hide()
            this.$('.endoflist').show()
        // if there were no results at all, hide the panel
        } else {
            this.$el.css({opacity: 0})
        }
        return this
    },

    resetRecommendations: function() {
        this.collection.clearHistory()
        this.collection.fetchRecs()
    },

    // get sr name of selected rec and fire it in a custom event
    onAddClick: function(ev) {
        var srName = $(ev.target).data('srname')
        this.trigger('recs:select', {'srName': srName})
    },

    showMore: function(ev) {
        this.collection.fetchNewRecs()
    }
})

r.recommend.ExploreItem = Backbone.View.extend({
    events: {
        'click .explore-feedback-dismiss': 'dismissSubreddit',
        'click a': 'recordClick'
    },  

    dismissSubreddit: function(ev) {
        var listing = $(ev.target).closest('.explore-item')
        var sr_name = listing.data('sr_name')
        var src = listing.data('src')
        r.ajax({
            type: 'POST',
            url: '/api/recommend/feedback',
            data: { type: 'dis',
                    srnames: sr_name,
                    src: src,
                    page: 'explore' }
        })
        this.$('.explore-feedback-dismiss').css({'font-weight':'bold'})
        $(this.el).fadeOut('fast')
    },

    recordClick: function(ev) {
        var listing = $(ev.target).closest('.explore-item')
        var sr_name = listing.data('sr_name')
        var src = listing.data('src')
        r.ajax({
            type: 'POST',
            url: '/api/recommend/feedback',
            data: { type: 'clk',
                    srnames: sr_name,
                    src: src,
                    page: 'explore' }
        })
    }
})
