!function(r, Backbone, _) {
  'use strict'

  var Timing = Backbone.Model.extend({
    duration: function() {
      return this.get('end') - this.get('start')
    },
    isValid: function() {
      // if the end is 0, window.performance didn't actually track this.
      return this.get('end') !== 0
    }
  })

  var Timings = Backbone.Collection.extend({
    model: Timing,
    comparator: 'start',

    initialize: function() {
      this.on('reset', this.calculate, this)
    },

    calculate: function() {
      this.startTime = this.min(function(timing) {
        return timing.get('start')
      }).get('start')

      this.endTime = this.max(function(timing) {
        return timing.get('end')
      }).get('end')

      this.duration = this.endTime - this.startTime
    }
  })

  var NavigationTimings = Timings.extend({
    fetch: function() {
      if (!window.performance || !window.performance.timing) {
        return
      }

      var pt = window.performance.timing
      var timings = []

      function timing(key, start, end) {
        if (!pt[start] || !pt[end]) {
          return
        }

        timings.push({
          key: key,
          start: pt[start] / 1000,
          end: pt[end] / 1000
        })
      }

      timing('redirect', 'redirectStart', 'redirectEnd')
      timing('start', 'fetchStart', 'domainLookupStart')
      timing('dns', 'domainLookupStart', 'domainLookupEnd')
      timing('tcp', 'connectStart', 'connectEnd')
      timing('https', 'secureConnectionStart', 'connectEnd')
      timing('request', 'requestStart', 'responseStart')
      timing('response', 'responseStart', 'responseEnd')
      timing('domLoading', 'domLoading', 'domInteractive')
      timing('domInteractive', 'domInteractive', 'domContentLoadedEventStart')
      timing('domContentLoaded', 'domContentLoadedEventStart', 'domContentLoadedEventEnd')

      this.reset(_.values(timings))
    }
  })

  r.NavigationTimings = NavigationTimings
  r.Timing = Timing
  r.Timings = Timings

}(r, Backbone, _)
