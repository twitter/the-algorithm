/*
  requires Backbone
  requires r.errors
  requires r.models.validators.js
 */
!function(models, Backbone, undefined) {
  r.models = r.models || {};


  var SHORT_NAME_MAX_LENGTH = 50;
  var DESCRIPTION_MAX_LENGTH = 500;


  function ValidRulesLength(attrName, maxLength) {
    return function validate(collection) {
      if (collection.length > maxLength) {
        return r.errors.createAPIError(attrName, 'SR_RULE_TOO_MANY');
      }
    }
  }


  function ValidRule(attrName) {
    var vLength = r.models.validators.StringLength(attrName, 1, SHORT_NAME_MAX_LENGTH);

    return function validate(model) {
      var collection = model.collection;
      var isNew = model.isNew();

      if (collection && isNew) {
        var vRulesLength = ValidRulesLength(attrName, collection.maxLength - 1);
        var collectionError = vRulesLength(collection);

        if (collectionError) {
          return collectionError;
        }
      }

      var lengthError = vLength(model, attrName);

      if (lengthError) {
        return lengthError;
      }

      if (collection) {
        var query = {};
        query[model.idAttribute] = model.get(model.idAttribute);
        var matches = collection.where(query);
        var isDuplicate = matches && matches.length > (isNew ? 0 : 1);

        if (isDuplicate) {
          return r.errors.createAPIError(attrName, 'SR_RULE_EXISTS');
        }
      }
    };
  };


  var SubredditRule = Backbone.Model.extend({
    idAttribute: 'short_name',

    SHORT_NAME_MAX_LENGTH: SHORT_NAME_MAX_LENGTH,
    DESCRIPTION_MAX_LENGTH: DESCRIPTION_MAX_LENGTH,

    validators: [
      ValidRule('short_name'),
      r.models.validators.StringLength('description', 0, DESCRIPTION_MAX_LENGTH),
    ],

    api: {
      create: function(model) {
        var data = model.toJSON();
        delete data.description_html;
        return {
          url: 'add_subreddit_rule',
          data: data,
        };
      },

      update: function(model) {
        var data = model.toJSON();
        data.old_short_name = model._old_short_name;
        delete data.description_html;
        return {
          url: 'update_subreddit_rule',
          data: data,
        };
      },

      delete: function(model) {
        var data = { short_name: model._old_short_name };
        return {
          url: 'remove_subreddit_rule',
          data: data,
        };
      },
    },

    defaults: function() {
      return {
        short_name: '',
        description: '',
        description_html: '',
        priority: 0,
        kind: 'all',
      };
    },

    toApiJSON: function() {
      // same as toJSON, but strips it down to only what we'd get from the API
      var data = this.toJSON();
      delete data.description_html;
      if (data.kind === 'all') {
        delete data.kind;
      }
      return data;
    },

    initialize: function() {
      var short_name = this.get('short_name');
      this._old_short_name = short_name;

      if (this.isNew()) {
        this.once('sync:create', function(model) {
          model.updateOldShortName();
        });
      }

      this.on('sync:update', function(model) {
        model.updateOldShortName();
      });
    },

    updateOldShortName: function() {
      this._old_short_name = this.get('short_name');
    },

    isNew: function() {
      return !this._old_short_name;
    },

    revert: function() {
      return this.set(this.previousAttributes(), { silent: true });
    },

    sync: function(method, model) {
      if (!this.api[method]) {
        throw new Error('Invalid action');
      }
      
      var req = this.api[method](model);
      req.data.api_type = 'json';
      this.trigger('request', this);

      $.request(req.url, req.data, function(res) {
        var errors = r.errors.getAPIErrorsFromResponse(res);
        
        if (errors) {
          this.trigger('error', this, errors);
          return;
        }

        if (res && res.json && res.json.data && res.json.data) {
          var description_html = _.unescape(res.json.data.description_html || '');
          this.set({ description_html: description_html });
        }

        this.trigger('sync:' + method, this);
        this.trigger('sync', this, method);
      }.bind(this), undefined, undefined, undefined, function(res) {
        var errors = r.errors.getAPIErrorsFromResponse(res);
        this.trigger('error', this, errors);
      }.bind(this));
    },

    validate: function(attrs) {
      return r.models.validators.validate(this, this.validators);
    },
  });


  var RULES_COLLECTION_MAX_LENGTH = 10;

  var SubredditRuleCollection = Backbone.Collection.extend({
    model: SubredditRule,
    maxLength: RULES_COLLECTION_MAX_LENGTH,
    subredditName: null,
    subredditFullname: null,

    initialize: function(models, options) {
      this._disabled = this.length >= this.maxLength;
      
      if (options && options.subredditName) {
        this.subredditName = options.subredditName;
      }
      if (options && options.subredditFullname) {
        this.subredditFullname = options.subredditFullname;
      }

      this.on('add', function() {
        if (!this._disabled && this.length >= this.maxLength) {
          this._disabled = true;
          this.trigger('disabled');
        }
      }.bind(this));

      this.on('remove', function() {
        if (this._disabled && this.length < this.maxLength) {
          this._disabled = false;
          this.trigger('enabled');
        }
      }.bind(this));
    },

    toApiJSON: function() {
      return {
        sr_name: this.subredditName,
        rules: this.models.map(function(model) {
          return model.toApiJSON();
        }),
      };
    },
  });


  r.models.SubredditRule = SubredditRule;
  r.models.SubredditRuleCollection = SubredditRuleCollection;
}(r, Backbone);
