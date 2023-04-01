r.templating = {}

r.templating.TemplateSet = function() {
    this.index = {}
}
r.templating.TemplateSet.prototype = {
    _templateSettings: {
        variable: 'thing'
    },

    _key: function(name, style) {
        return name + '.' + style
    },

    _create: function(templateData) {
        return _.template(templateData, null, this._templateSettings)
    },

    set: function(templates) {
        _.each(templates, function(tplInfo) {
            // if uncompressedJS, the template was embedded in the HTML
            // rather than an external resource and was escaped for safety
            if (r.config.uncompressedJS) {
                tplInfo = r.utils.unescapeJson(tplInfo)
            }

            var key = this._key(tplInfo.name, tplInfo.style)
            this.index[key] = tplInfo.template
        }, this)
    },

    _defaultStyle: function(nameAndStyle) {
        // `nameAndStyle` can be an array of [name, style] or simply a name,
        // defaulting the style to r.config.renderstyle.
        if (!_.isArray(nameAndStyle)) {
            nameAndStyle = [nameAndStyle, r.config.renderstyle]
        }
        return nameAndStyle
    },

    get: function(nameAndStyle) {
        nameAndStyle = this._defaultStyle(nameAndStyle)
        var key = this._key(nameAndStyle[0], nameAndStyle[1])

        if (!this.index[key]) {
            throw '"' + nameAndStyle[0] + '.' + nameAndStyle[1] + '"' + ' template not found.'
        }

        template = this.index[key]
        if (!_.isFunction(template)) {
            template = this.index[key] = this._create(template)
        }
        return template
    },

    make: function(nameAndStyle, data, parentEl) {
        html = this.get(nameAndStyle)(data)
        if (parentEl) {
            $(parentEl).append(html)
        }
        return html
    }
}

r.templates = new r.templating.TemplateSet()
