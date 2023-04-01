r.filter = {}

r.filter.init = function() {
    var detailsEl = $('.filtered-details')
    if (detailsEl.length) {
        var multi = new r.filter.Filter({
            path: detailsEl.data('path')
        })
        detailsEl.find('.subreddits a').each(function(i, e) {
            multi.subreddits.add({name: $(e).data('name')})
        })
        multi.fetch({
            error: _.bind(r.multi.mine.create, r.multi.mine, multi, {wait: true})
        })

        var detailsView = new r.multi.SubredditList({
            model: multi,
            itemView: r.filter.FilteredSubredditItem,
            el: detailsEl
        }).render()
    }
}

r.filter.Filter = r.multi.MultiReddit.extend({
    url: function() {
        return r.utils.joinURLs('/api/filter', this.id)
    }
})

r.filter.FilteredSubredditItem = r.multi.MultiSubredditItem.extend({
    render: function() {
        this.$el.append(this.template({
            sr_name: this.model.get('name')
        }))
        return this
    }
})
