!function(r, undefined) {
  r.ui.FormBar = Backbone.View.extend({
    templateName: 'ui/formbar',

    defaults: {
      text: '',
      buttonLabel: '',
      key: '',
      value: '',
    },

    events: {
      'submit .form-bar': 'onSubmit',
    },

    initialize: function() {
      var templateProps = _.defaults(this.options, this.defaults);
      this.render(templateProps);
    },

    render: function(templateProps) {
      var content = r.templates.make(this.templateName, templateProps);
      this.$el.html(content);
    },

    onSubmit: function(e) {
      e.preventDefault();
      this.trigger('submit', get_form_fields(e.target));
    },
  });
}(r);
