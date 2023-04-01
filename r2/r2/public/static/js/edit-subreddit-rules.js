/*
requires Backbone
requires r.errors
requires r.models.SubredditRule
requires r.models.SubredditRuleCollection
requires r.ui.TextCounter
 */

!function(r, Backbone, undefined) {
  var SubredditRuleBaseView = Backbone.View.extend({
    countersInitialized: false,
    state: '',

    DEFAULT_STATE: '',
    EDITING_STATE: 'editing',

    events: {
      'click .subreddit-rule-delete-button': function onDelete(e) {
        e.preventDefault();
        this.delete();
      },

      'click .subreddit-rule-edit-button': function onEdit(e) {
        e.preventDefault();
        this.edit();
      },

      'click .subreddit-rule-cancel-button': function onCancel(e) {
        e.preventDefault();
        this.cancel();
      },

      'submit form': function onSubmit(e) {
        e.preventDefault();
        this.submit();
      },
    },

    initialize: function(options) {
      this.formTemplate = options.formTemplate;
    },

    delegateEvents: function() {
      SubredditRuleBaseView.__super__.delegateEvents.apply(this, arguments);

      this.listenTo(this.model, 'request', this.disableForm);
      this.listenTo(this.model, 'invalid', function(model, error) {
        this.model.revert();
        this.showErrors([error]);
      });
      this.listenTo(this.model, 'error', function(model, errors) {
        this.model.revert();
        this.showErrors(errors);
      });
    },

    setState: function(state) {
      if (this.state !== state) {
        this.state = state;
        this.render();
      }
    },

    initCounters: function() {
      if (this.countersInitialized) {
        return;
      }

      var shortNameCounter = this.$el.find('.form-group-short_name')[0];
      var descriptionCounter = this.$el.find('.form-group-description')[0];
      
      if (!shortNameCounter) {
        return;
      }

      this.shortNameCounter = new r.ui.TextCounter({
        el: shortNameCounter,
        maxLength: this.model.SHORT_NAME_MAX_LENGTH,
        initialText: this.model.get('short_name'),
      });
      this.descriptionCounter = new r.ui.TextCounter({
        el: descriptionCounter,
        maxLength: this.model.DESCRIPTION_MAX_LENGTH,
        initialText: this.model.get('description'),
      });
      this.countersInitialized = true;
    },

    removeCounters: function() {
      if (!this.countersInitialized) {
        return;
      }

      this.shortNameCounter.remove();
      this.descriptionCounter.remove();
      this.shortNameCounter = null;
      this.shortNameCounter = null;
      this.countersInitialized = false;
    },  
  
    delete: function() {
      this.model.destroy();
    },

    submit: function() {
      var $form = this.$el.find('form');
      var formData = get_form_fields($form);
      this.model.save(formData);
    },

    disableForm: function() {
      r.errors.clearAPIErrors(this.$el);
      this.$el.find('input, button')
              .attr('disabled', true);
    },

    enableForm: function() {
      this.$el.find('input, button')
              .removeAttr('disabled');
    },

    showErrors: function(errors) {
      r.errors.clearAPIErrors(this.$el);
      r.errors.showAPIErrors(this.$el, errors);
      this.enableForm();
    },

    render: function() {
      this.removeCounters();
      this.renderTemplate();
      this.initCounters();
    },

    focus: function() {
      this.$el.find('input, textarea').get(0).focus();
    },
  });


  var SubredditRuleView = SubredditRuleBaseView.extend({
    DELETING_STATE: 'deleting',

    initialize: function(options) {
      SubredditRuleView.__super__.initialize.apply(this, arguments);
      this.ruleTemplate = options.ruleTemplate;
    },

    delegateEvents: function() {
      SubredditRuleView.__super__.delegateEvents.apply(this, arguments);
      this.listenTo(this.model, 'sync:update', this.cancel);
      this.listenTo(this.model, 'sync:delete', this.remove);
    },

    delete: function() {
      if (this.state === this.DELETING_STATE) {
        this.model.destroy();
      } else if (this.state === this.DEFAULT_STATE) {
        this.setState(this.DELETING_STATE);
      }
    },

    edit: function() {
      if (this.state === this.DEFAULT_STATE) {
        this.setState(this.EDITING_STATE);
        this.focus();
      }
    },

    cancel: function() {
      if (this.state === this.EDITING_STATE ||
          this.state === this.DELETING_STATE) {
        this.$el.removeClass('mod-action-deleting');
        this.setState(this.DEFAULT_STATE);
      }
    },

    render: function() {
      SubredditRuleView.__super__.render.call(this);

      if (this.state === this.DELETING_STATE) {
        this.$el.addClass('mod-action-deleting');
        this.$el.find('.subreddit-rule-delete-confirmation').removeAttr('hidden');
        this.$el.find('.subreddit-rule-buttons button').attr('disabled', true);
      }
      if (this.state === this.EDITING_STATE) {
        this.$el.find('.form-group-kind input[value=' + this.model.get('kind') + ']').prop('checked', true);
      }
    },

    renderTemplate: function() {
      var modelData = this.model.toJSON();
      var kind = modelData.kind;

      modelData.kind = r.config.kind_labels[kind];

      if (this.state === this.EDITING_STATE) {
        this.$el.html(this.formTemplate(modelData));
      } else {
        this.$el.html(this.ruleTemplate(modelData));
      }
    },
  });


  var AddSubredditRuleView = SubredditRuleBaseView.extend({
    DISABLED_STATE: 'disabled',

    initialize: function(options) {
      AddSubredditRuleView.__super__.initialize.apply(this, arguments);
      this.collection = options.collection;
      this.initializeNewModel();
      this.$collapsedDisplay = this.$el.find('.subreddit-rule-add-form-buttons');
      this.$maxRulesNotice = this.$collapsedDisplay.find('.subreddit-rule-too-many-notice');

      this.$el.removeAttr('hidden');

      if (this.collection._disabled) {
        this.setState(this.DISABLED_STATE);
      }
    },

    delegateEvents: function() {
      AddSubredditRuleView.__super__.delegateEvents.apply(this, arguments);
      this.listenTo(this.collection, 'enabled', function() {
        if (this.state === this.DISABLED_STATE) {
          this.setState(this.DEFAULT_STATE);
        }
      })
      this.listenTo(this.collection, 'disabled', function() {
        this.setState(this.DISABLED_STATE);
      });
      this.listenTo(this.model, 'sync:create', this._handleRuleCreated);
    },

    initializeNewModel: function() {
      var Model = this.collection.model;
      this.model = new Model(undefined, { collection: this.collection });
    },

    _handleRuleCreated: function(model) {
      this.undelegateEvents();
      this.initializeNewModel();
      this.delegateEvents();
      this.cancel();
      this.trigger('success', model);
    },

    edit: function() {
      if (this.state === this.DEFAULT_STATE) {
        this.setState(this.EDITING_STATE);
      }
    },

    cancel: function() {
      if (this.state === this.EDITING_STATE) {
        this.setState(this.DEFAULT_STATE);
      }
    },

    render: function() {
      this.$collapsedDisplay.detach();
      AddSubredditRuleView.__super__.render.call(this);

      if (this.state === this.DISABLED_STATE) {
        this.$maxRulesNotice.removeAttr('hidden');
        this.$el.append(this.$collapsedDisplay);
        this.disableForm();
      } else if (this.state === this.DEFAULT_STATE) {
        this.$maxRulesNotice.attr('hidden', true);
        this.$el.append(this.$collapsedDisplay);
        this.enableForm();
      } else if (this.state === this.EDITING_STATE) {
        this.focus();
      }
    },

    renderTemplate: function() {
      if (this.state !== this.EDITING_STATE) {
        this.$el.empty();
      } else {
        var modelData = this.model.toJSON();
        this.$el.html(this.formTemplate(modelData));
      }
    },
  });

  
  var SubredditRulesPage = Backbone.View.extend({
    initialize: function(options) {
      this.ruleTemplate = options.ruleTemplate;
      this.formTemplate = options.formTemplate;
      var collectionOptions = {
        subredditName: r.config.post_site,
        subredditFullname: r.config.cur_site,
      };
      this.collection = new r.models.SubredditRuleCollection(null, collectionOptions);

      this.newRuleForm = new AddSubredditRuleView({
        el: options.addForm,
        collection: this.collection,
        formTemplate: this.formTemplate,
      });

      // initialize views for the rules prerendered on the page
      var ruleItems = this.$el.find('.subreddit-rule-item').toArray();
      ruleItems.forEach(function(el) {
        var model = this.createSubredditRuleModel(el);
        this.createSubredditRuleView(el, model);
      }, this);

      if (!this.collection.length) {
        this.newRuleForm.edit();
      }

      r.hooks.get('new-report-form').register(function() {
        this._updateRuleCache();
      }.bind(this));
    },

    delegateEvents: function() {
      SubredditRulesPage.__super__.delegateEvents.apply(this, arguments);

      this.listenTo(this.newRuleForm, 'success', function(model) {
        var props = model.toJSON();
        var newModel = new r.models.SubredditRule(props);
        this.addNewRule(newModel);
      });

      this.listenTo(this.collection, 'sync', function() {
        this._updateRuleCache();
      });
    },

    createSubredditRuleModel: function(el) {
      var $el = $(el);

      return new r.models.SubredditRule({
        priority: parseInt($el.data('priority'), 10),
        short_name: $el.find('.subreddit-rule-title').text(),
        description: $el.data('description'),
        description_html: $el.find('.subreddit-rule-description').html(),
        kind: $el.data('kind'),
      });
    },

    createSubredditRuleView: function(el, model) {
      this.collection.add(model);

      return new SubredditRuleView({
        el: el,
        model: model,
        ruleTemplate: this.ruleTemplate,
        formTemplate: this.formTemplate,
      });
    },

    addNewRule: function(model) {
      var el = $.parseHTML('<div class="subreddit-rule-item"></div>')[0];
      var view = this.createSubredditRuleView(el, model);
      view.render();
      this.$el.append(el);
    },

    _updateRuleCache: function() {
      try {
        var newRules = this.collection.toApiJSON();
        var storageKey = r.rulesSessionStorageKey;
        var rulesCache = window.sessionStorage.getItem(storageKey);
        rulesCache = rulesCache ? JSON.parse(rulesCache) : {};
        rulesCache[this.collection.subredditFullname] = newRules;
        rulesCache = JSON.stringify(rulesCache);
        window.sessionStorage.setItem(storageKey, rulesCache);
      } catch (err) {
      }
    },
  });


  $(function() {
    var $page = $('.subreddit-rules-page');

    if (!$page.hasClass('editable')) {
      return;
    }

    var ruleTemplate = document.getElementById('subreddit-rule-template');
    var formTemplate = document.getElementById('subreddit-rule-form-template');
    var addForm = document.getElementById('subreddit-rule-add-form');
    var ruleList = document.getElementById('subreddit-rule-list');

    if (!ruleTemplate || !formTemplate) {
      throw 'Subreddit rule templates not found!';
    }

    new SubredditRulesPage({
      el: ruleList,
      addForm: addForm,
      ruleTemplate: _.template(ruleTemplate.innerHTML),
      formTemplate: _.template(formTemplate.innerHTML),
    });
  });
}(r, Backbone);
