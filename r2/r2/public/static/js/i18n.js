r.i18n = {
    jed: new Jed({
        'locale_data': {
            'messages': {
                '': {
                    'domain': 'messages',
                    'lang': 'en'
                }
            }
        }
    }),

    setPluralForms: function (pluralForms) {
        this.jed.options.locale_data.messages[''].plural_forms = pluralForms
    },

    addMessages: function (messages) {
        _.extend(this.jed.options.locale_data.messages, messages)
    }
}

r._ = _.bind(r.i18n.jed.gettext, r.i18n.jed)
r.P_ = _.bind(r.i18n.jed.ngettext, r.i18n.jed)
r.N_ = _.identity
r.NP_ = function (singular, plural) {
    return [singular, plural]
}
