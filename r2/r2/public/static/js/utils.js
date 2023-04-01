r.utils = {

    /**
     * update the given url's query params
     * @param  {String} url
     * @param  {Object} newParams
     * @return {String}
     */
    replaceUrlParams: function(url, newParams) {
      var a = document.createElement('a');
      var urlObj = $.url(url);
      var params = urlObj.param();

      Object.keys(newParams).forEach(function(key) {
        params[key] = newParams[key]
      });

      a.href = url;
      a.search = $.param(params);
      return a.href;
    },

    // Returns human readable file sizes
    // http://stackoverflow.com/a/25613067/704286
    formatFileSize: function(size) {
      var suffixes = ['bytes', 'KiB', 'MiB', 'GiB', 'TiB', 'EiB', 'ZiB'];
      var order = size ? parseInt(Math.log2(size) / 10, 10) : 0;

      return (size / (1 << (order * 10))).toFixed(3).replace(/\.?0+$/, '') + ' ' + suffixes[order];
    },

    fullnameToId: function(fullname) {
        var parts = fullname.split('_');
        var id36 = parts && parts[1];

        return id36 && parseInt(id36, 36);
    },

    escapeSelector: function(str) {
        return str.replace(/([ #;?%&,.+*~\':"!^$[\]()=>|\/@])/g,'\\$1');
    },

    clamp: function(val, min, max) {
        return Math.max(min, Math.min(max, val))
    },

    staticURL: function (item) {
        return r.config.static_root + '/' + item
    },

    s3HTTPS: function(url) {
        if (location.protocol == 'https:') {
            return url.replace('http://', 'https://s3.amazonaws.com/')
        } else {
            return url
        }
    },

    parseTimestamp: function($el) {
      var timestamp = $el.data('timestamp')
      var isoTimestamp

      if (!timestamp) {
        isoTimestamp = $el.attr('datetime')
        timestamp = Date.parse(isoTimestamp)
        $el.data('timestamp', timestamp)
      }

      return timestamp
    },

    joinURLs: function(/* arguments */) {
        return _.map(arguments, function(url, idx) {
            if (idx > 0 && url && url[0] != '/') {
                url = '/' + url
            }
            return url
        }).join('')
    },

    _scOn: "<!-- SC_ON -->",
    _scOff: "<!-- SC_OFF -->",
    _scBetweenTags1: />\s+/g,
    _scBetweenTags2: /\s+</g,
    _scSpaces: /\s+/g,
    _scDirectives: /(<!-- SC_ON -->|<!-- SC_OFF -->)/,
    spaceCompress: function (content) {
        var res = '';
        var compressionOn = true;
        var splitContent = content.split(this._scDirectives);
        for (var i=0; i<splitContent.length; ++i) {
            var part = splitContent[i];
            if (part === this._scOn) {
                compressionOn = true;
            } else if (part === this._scOff) {
                compressionOn = false;
            } else if (compressionOn) {
                part = part.replace(this._scSpaces, ' ');
                part = part.replace(this._scBetweenTags1, '>');
                part = part.replace(this._scBetweenTags2, '<');
                res += part;
            } else {
                res += part;
            }
        }
        return res;
    },

    tup: function(list) {
        if (!_.isArray(list)) {
            list = [list]
        }
        return list
    },

    structuredMap: function(obj, func) {
        if (_.isArray(obj)) {
            return _.map(obj, function(value) {
                return r.utils.structuredMap(value, func)
            })
        } else if (_.isObject(obj)) {
            var mapped = {}
            _.each(obj, function(value, key) {
                mapped[func(key, 'key')] = r.utils.structuredMap(value, func)
            })
            return mapped
        } else {
            return func(obj, 'value')
        }
    },

  unescapeJson: function(json) {
    return r.utils.structuredMap(json, function(val) {
      if (_.isString(val)) {
        return _.unescape(val)
      } else {
        return val
      }
    })
  },

    querySelectorFromEl: function(targetEl, selector) {
        return $(targetEl).parents().addBack()
            .filter(selector || '*')
            .map(function(idx, el) {
                var parts = [],
                    $el = $(el),
                    elFullname = $el.data('fullname'),
                    elId = $el.attr('id'),
                    elClass = $el.attr('class')

                parts.push(el.nodeName.toLowerCase())

                if (elFullname) {
                    parts.push('[data-fullname="' + elFullname + '"]')
                } else {
                    if (elId) {
                        parts.push('#' + elId)
                    } else if (elClass) {
                        parts.push('.' + _.compact(elClass.split(/\s+/)).join('.'))
                    }
                }

                return parts.join('')
            })
            .toArray().join(' ')
    },

    serializeForm: function(form) {
        var params = {}
        $.each(form.serializeArray(), function(index, value) {
            params[value.name] = value.value
        })
        return params
    },

    _pyStrFormatRe: /%\((\w+)\)s/g,
    pyStrFormat: function(format, params) {
        return format.replace(this._pyStrFormatRe, function(match, fieldName) {
            if (!(fieldName in params)) {
                throw 'missing format parameter'
            }
            return params[fieldName]
        })
    },

    _mdLinkRe: /\[(.*?)\]\((.*?)\)/g,
    formatMarkdownLinks: function(str) {
        return _.escape(str).replace(this._mdLinkRe, function(match, text, url) {
            return '<a href="' + url + '">' + text + '</a>'
        })
    },

    prettyNumber: function(number) {
        // Add commas to separate every third digit
        var numberAsInt = parseInt(number)
        if (numberAsInt) {
            return numberAsInt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")
        } else {
            return number
		}
	},

    LRUCache: function(maxItems) {
        var _maxItems = maxItems > 0 ? maxItems : 16
        var _cacheIndex = []
        var _cache = {}

        var _updateIndex = function(key) {
            _deleteFromIndex(key)
            _cacheIndex.push(key)
            if (_cacheIndex.length > _maxItems) {
                delete _cache[_cacheIndex.shift()]
            }
        }

        var _deleteFromIndex = function(key) {
            var index = _.indexOf(_cacheIndex, key)
            if (index >= 0) {
                _cacheIndex.splice(index, 1)
            }
        }

        this.remove = function(key) {
            _deleteFromIndex(key)
            delete _cache[key]
        }

        this.set = function(key, data) {
            if (_.isUndefined(data)) {
                this.remove(key)
            } else {
                _cache[key] = data
                _updateIndex(key)
            }
        }

        this.get = function(key) {
            var value = _cache[key]
            if (!_.isUndefined(value)) {
                _updateIndex(key)
            }
            return value
        }

        this.ajax = function(key, options) {
            var cached = this.get(key)
            if (!_.isUndefined(cached)) {
                return (new $.Deferred()).resolve(cached)
            } else {
                return $.ajax(options).done(_.bind(this.set, this, key))
            }
        }
    },

    parseError: function(error) {
        var name = error[0];
        var message = error[1];
        var field = error[2];

        return {
            name: name,
            message: message,
            field: field,
        }
    },

    onTrident: function() {
        return 'ActiveXObject' in window;
    },

}

// Nothing is true. Everything is permitted.
String.prototype.format = function (params) {
    return r.utils.pyStrFormat(this, params)
}
