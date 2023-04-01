!function(r, Backbone, store){
  'use strict'

  var AdminBar = Backbone.View.extend({
    events: {
      'click .show-button': 'toggleVisibility',
      'click .hide-button': 'toggleVisibility',
      'click .timings-button': 'toggleTimings',
      'click .expand-button': 'toggleFullTimings',
      'click .timelines': 'toggleZoom',
      'click .admin-off': 'adminOff',
    },

    initialize: function(options) {
      this.hidden = store.safeGet('adminbar.hidden') === true
      this.showTimings = store.safeGet('adminbar.timings.show') === true
      this.showFullTimings = store.safeGet('adminbar.timings.full') === true
      this.zoomTimings = store.safeGet('adminbar.timings.zoom') !== false
      this.timingScale = store.safeGet('adminbar.timings.scale') || 8.0

      this.timings = options.timings
      this.browserTimings = options.browserTimings

      this.serverTimingGraph = new TimingBarGraph({
        collection: this.timings,
        el: this.$('.timeline-server'),
      })

      this.browserTimingGraph = new TimingBarGraph({
        collection: this.browserTimings,
        el: this.$('.timeline-browser'),
      })

      this.timings.on('reset', this.render, this)
      this.browserTimings.on('reset', this.render, this)
    },

    adminOff: function() {
      window.location = '/adminoff'
    },

    render: function() {
      this.$el.toggleClass('hidden', this.hidden)

      this.$('.timings-bar')
        .toggle(this.showTimings)
        .toggleClass('mini-timings', !this.showFullTimings)
        .toggleClass('full-timings', this.showFullTimings)

      this.$('.status-bar .timings-button .state')
        .text(this.showTimings ? '-' : '+')

      this.$('.timings-bar .expand-button')
        .text(this.showFullTimings ? '-' : '+')

      this.$('.timelines').toggleClass('zoomed', this.zoomTimings)

      $('body').css({
        'margin-top': this.$el.outerHeight(),
        'position': 'relative',
      })

      if (this.timings.isEmpty()) {
        return
      }

      var bt = this.browserTimings
      var browserEndBound = bt.endTime

      if (!this.zoomTimings && (bt.endTime - bt.startTime) < this.timingScale) {
        browserEndBound = bt.startTime + this.timingScale
      }

      this.browserTimingGraph.setBounds(bt.startTime, browserEndBound)

      if (this.showFullTimings && !bt.isEmpty()) {
        this.serverTimingGraph.setBounds(bt.startTime, browserEndBound)
      } else {
        var scaleStart = this.timings.startTime
        var scaleEnd = this.timings.endTime

        if (!this.zoomTimings && (scaleEnd - scaleStart) < this.timingScale) {
          scaleEnd = scaleStart + this.timingScale
        }

        this.serverTimingGraph.setBounds(scaleStart, scaleEnd)
      }

      // if showing full times, avoid rendering until both timelines loaded
      // to avoid a flicker when the server timing graph rescales.
      if (!this.showFullTimings || !bt.isEmpty()) {
        this.serverTimingGraph.render()
        this.browserTimingGraph.render()
      }
    },

    toggleVisibility: function() {
      this.hidden = !this.hidden
      store.safeSet('adminbar.hidden', this.hidden)
      this.render()
    },

    toggleTimings: function() {
      this.showTimings = !this.showTimings
      store.safeSet('adminbar.timings.show', this.showTimings)
      this.render()
    },

    toggleFullTimings: function(value) {
      this.showFullTimings = !this.showFullTimings
      store.safeSet('adminbar.timings.full', this.showFullTimings)
      this.render()
    },

    toggleZoom: function(value) {
      this.zoomTimings = !this.zoomTimings
      store.safeSet('adminbar.timings.zoom', this.zoomTimings)
      this.render()
    }
  })

  var TimingBarGraph = Backbone.View.extend({
    setBounds: function(start, end) {
      this.options.startBound = start
      this.options.endBound = end
    },

    render: function() {
      var startBound = this.options.startBound || this.collection.startTime
      var endBound = this.options.endBound || this.collection.endTime
      var boundDuration = endBound - startBound

      var pos = function(time) {
        var frac = time / boundDuration
        return (frac * 100).toFixed(2)
      }

      if (this.collection.endTime < this.options.startBound) {
        this.$el.append($('<div class="event out-of-bounds">'))
        return
      }

      this.$el.empty()

      var eventsEl = $('<ol class="events">')

      this.collection.each(function(timing) {
        var key = timing.get('key'),
          keyParts = key.split('.')

        if (keyParts[keyParts.length-1] === 'total') {
          return
        }

        var eventDuration = (timing.get('end') - timing.get('start')).toFixed(2)

        eventsEl.append($('<li class="event">')
          .addClass(keyParts[0])
          .addClass(keyParts[1])
          .addClass(keyParts[2])
          .attr('title', key + ': ' + eventDuration + 's')
          .css({
            left: pos(timing.get('start') - startBound) + '%',
            right: pos(endBound - timing.get('end')) + '%',
            zIndex: 1000 - Math.min(800, Math.floor(timing.duration() * 100)),
          })
        )
      }, this)

      this.$el.append(eventsEl)

      var elapsed = this.collection.endTime - this.collection.startTime

      if (elapsed) {
        this.$el.append($('<span class="elapsed">')
          .text(elapsed.toFixed(2) + 's'))
      }

      return this
    }
  })

  var timings = new r.Timings()
  var browserTimings = new r.NavigationTimings()

  var bar = new AdminBar({
    el: $('#admin-bar'),
    timings: timings,
    browserTimings: browserTimings,
  }).render()

  r.adminbar = {
    AdminBar: AdminBar,
    TimingBarGraph: TimingBarGraph,
    bar: bar,
  }

  $(function() {
    if (!r.timings) { return }
    timings.reset(r.timings)

    setTimeout(function() {
      browserTimings.fetch()
    }, 0)
  })
}(r, Backbone, store)
