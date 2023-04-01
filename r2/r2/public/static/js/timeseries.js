r.timeseries = {
    _tickSizeByInterval: {
        'hour': [1, 'day'],
        'day': [7, 'day'],
        'month': [1, 'month']
    },
    _units : ['', 'k', 'M', 'B'],
    _tooltip: null,
    _currentHover: null,

    init: function () {
        $('table.timeseries').each($.proxy(function (i, table) {
            var series = this.makeTimeSeriesChartsFromTable(table)
            this.addBarsToTable(table, series)
        }, this))
    },

    _formatTick: function (val, axis) {
        if (val == 0)
            return '0'
        else if (val < 10)
            return val.toFixed(2)

        for (var i = 1; i < this._units.length; i++) {
            if (val / Math.pow(1000, i - 1) < 1000) {
                break
            }
        }

        val /= Math.pow(1000, i - 1)

        return val.toFixed(axis.tickDecimals) + this._units[i - 1]
    },

    makeTimeSeriesChartsFromTable: function (table) {
        var table = $(table),
            series = this._readFromTable(table),
            options = this._configureFlot(table),
            chartRow = $('<div>')

        $.each(series, function (i, chart) {
            var figure = $('<div class="timeseries">')
                            .append($('<span class="title">').text(chart.caption))
                            .append('<div class="timeseries-placeholder">')
                            .appendTo(chartRow)

            chart.placeholder = figure.find('.timeseries-placeholder')
        })
        $('#charts').append(chartRow)

        $.each(series, function(i, chart) {
            $.plot(chart.placeholder, [chart], options)
        })

        table.addClass('charted')

        return series
    },

    addBarsToTable: function (table, series) {
        var table = $(table),
            newColHeader = $('<th scope="col">')

        table.find('thead tr').append(newColHeader)

        table.find('tbody tr').each(function (i, row) {
            var row = $(row),
                data = row.children('td'),
                newcol = $('<td>')

            $.each(series, function (i, s) {
                var datum = data.eq(s.index).data('value'),
                    bar = $('<div>').addClass('timeseries-tablebar')
                                    .css('background-color', s.color)
                                    .width((datum / s.maxValue) * 100)

                if (datum > 0)
                    newcol.append(bar)

                if (datum !== 0 && datum === s.maxValue) {
                    row.addClass('max')
                       .css('border-color', s.color)
                }
            })

            row.append(newcol)
        })
    },

    _configureFlot: function (table) {
        var interval = table.data('interval'),
            tickUnit = this._tickSizeByInterval[interval],
            unprocessed = $('#timeseries-unprocessed').data('last-processed'),
            markings = []

        if (unprocessed) {
            markings.push({
                color: '#eee',
                xaxis: {
                    from: unprocessed
                }
            })

            markings.push({
                color: '#aaa',
                xaxis: {
                    from: unprocessed,
                    to: unprocessed
                }
            })
        }

        return {
            grid: {
                'markings': markings
            },

            xaxis: {
                mode: 'time',
                tickSize: tickUnit
            },

            yaxis: {
                min: 0,
                tickFormatter: $.proxy(this, '_formatTick')
            }
        }
    },

    _readFromTable: function (table) {
        var maxPoints = parseInt(table.data('maxPoints'), 10),
            headers = table.find('thead tr:last-child th:not(:first-child)'),
            series = []

        // initialize the series
        headers.each(function (i, header) {
            var header = $(header),
                caption = header.attr('title'),
                color = header.data('color')

            if (!color) {
                return
            }

            series.push({
                'lines': {
                    'show': true,
                    'steps': true,
                    'fill': true
                },

                'color': color,
                'caption': caption,
                'data': [],
                'maxValue': 0,
                'index': i
            })
        })

        // read the data from the table
        var rows = table.find('tbody tr')
        rows.each(function (i, row) {
            var row = $(row),
                timestamp = row.children('th').data('value'),
                data = row.children('td')

            $.each(series, function (j, s) {
                var datum = data.eq(s.index).data('value')
                // if we have a maximum number of data points to chart, choose
                // the most recent ones from the table
                if (!maxPoints || i > rows.length - maxPoints)
                    s.data.push([timestamp, datum])
                s.maxValue = Math.max(s.maxValue, datum)
            })
        })

        return series
    }
}
