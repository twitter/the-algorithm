r.preload = {
    timestamp: new Date(),
    maxAge: 5 * 60 * 1000,
    data: {},

    isExpired: function() {
        return new Date() - this.timestamp > this.maxAge
    },

    set: function(data) {
        _.extend(this.data, data)
    },

    read: function(url) {
        var data = this.data[url]

        // short circuit "client side" fragment urls (which don't expire)
        if (url[0] == '#') {
            return data
        }

        if (this.isExpired()) {
            return
        }

        return data
    }
}

