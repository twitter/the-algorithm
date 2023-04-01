/**
 * @fileoverview The custom behavior for the standalone private message
 * composition form.
 */

!function(r, undefined) {
  'use strict';

  /** How long to run the state transition animations. @const */
  var ANIM_MS = 0;


  /** How long to wait for the subreddit rules to load. @const */
  var RULES_TIMEOUT_MS = 10000;


  /** This looks like a subreddit name. @const */
  var SUBREDDIT = /^(?:#|\/?r\/)(.*)/;


  var mc = r.messagecompose = {

    /**
     * The interesting bits of the compose message form.
     * @const {Object<string, jQuery|undefined>}
     */
    dom: {
      /** The compose message form.  */
      $form: undefined,

      $to: undefined,

      $subject: undefined,

      /** The subject-rule selector. */
      $rule: undefined,

      /** The "Other" rule option for a custom subject. */
      $other: undefined,

      /** The blank default and invalid rule option. */
      $blank: undefined,
    },


    /**
     * The current or previous XMLHttpRequest loading rules.
     * @private {XMLHttpRequest|undefined}
     */
    rulesReq: undefined,


    /**
     * The URL of the currently running rules request, if any.
     * @private {string|undefined}
     */
    rulesReqInProgressUrl: undefined,

    /**
     * The subject the user expects to see for the "Other" rule.
     * @private {string|undefined}
     */
    customSubject: undefined,

    /**
     * Module initialization.
     * @private
     */
    init: function() {
      mc.dom.$form = $('#compose-message');
      mc.dom.$to = mc.dom.$form.find('[name="to"]');
      mc.dom.$subject = mc.dom.$form.find('[name="subject"]');
      mc.dom.$rule = mc.dom.$form.find('select.rule_subject');
      mc.dom.$other = mc.dom.$rule.find('option.other');
      mc.dom.$blank = mc.dom.$rule.find('option.blank');

      if (mc.dom.$form.length) {
        // Clean up the form after sucessful submission.
        // This is delayed until after the ajax implementation of the form
        // submission (post_form in reddit.js) so that it does't interfere with the
        // form submission, but can still clean up after. I haven't found any other
        // hooks in to the form submission that let us run code after the form is
        // reset.
        mc.dom.$form.on('submit', function() {
          var handler = function() {
            $(document).off('ajaxSuccess.messagecompose', handler);
            // If there are no errors marked in the form, reset the subject too.
            if (mc.dom.$form.find('.error:visible').length == 0) {
              mc.customSubject = undefined;
              mc.onToUpdate();
              mc.dom.$subject.val('');
            }
          };
          $(document).on('ajaxSuccess.messagecompose', handler);
        });

        if (mc.dom.$subject.val()) {
          mc.customSubject = mc.dom.$subject.val();
        }

        if (mc.dom.$to.length) {
          mc.dom.$to.change(mc.onToUpdate);
          // The page is not pre-rendered with the rules for the selected
          // subreddit, so we have to load them up front.
          mc.onToUpdate();
        }

        mc.dom.$rule.change(mc.onSelectedRuleChange);
        mc.dom.$subject.change(mc.onSubjectChange);
      }
    },

    /**
     * Loads the rules for the given subreddit.
     * Returns a promise that resolves with the rules JSON object or rejects if the
     * subreddit doesn't exist or otherwise fails to load.
     *
     * @private
     * @param {string} sr The name of the subreddit.
     * @return {Promise<{
     *     sr_name: string,
     *     rules: Array<{short_name:string}>|undefined,
     *     site_rules: Array<string>|undefined,
     *  }>}
     */
    loadSubredditRules: function(sr) {
      var url = '/r/' + sr + '/about/rules.json';

      // If we're already loading this subreddit, continue, otherwise, abort
      // the old one and start over.
      if (mc.rulesReqInProgressUrl === url) {
        return mc.rulesReq;
      }

      if (mc.rulesReq) {
        mc.rulesReq.abort();
      }

      (mc.rulesReq = r.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        timeout: RULES_TIMEOUT_MS
      })).always(function() {
        mc.rulesReqInProgressUrl = undefined;
        mc.rulesReq = undefined;
      }).then(function(rulesJson, textStatus, jqXHR) {
        // The API gets redirected to search if the subreddit does not exist.
        // Detect that by looking for a lack of the rules or a result kind of Listing.
        if (!rulesJson['rules'] || rulesJson['kind'] === 'Listing') {
          return $.Deferred().reject(jqXHR, rulesJson, 'No subreddit');
        } else if (rulesJson['error']) {
          return $.Deferred().reject(jqXHR, rulesJson, rulesJson['error']);
        }

        // Annotate the rules with the subreddit, so we can reason about them later.
        rulesJson['sr_name'] = sr;
        return rulesJson;
      });

      mc.rulesReqInProgressUrl = url;
      return mc.rulesReq;
    },


    /**
     * Renders the rules of the subreddit into the subject field, while maintaining
     * any user entered value.
     *
     * @private
     * @param {!Object} rulesJson
     */
    renderSubredditSubject: function(rulesJson) {
      // Old underscore.js. _.property isn't defined.
      var property = function(p) { return function (v) {return v[p]; }; };

      var rules = _(rulesJson['rules'])
        .sortBy(property('priority'))
        .map(property('short_name'))
        .concat(rulesJson['site_rules']);

      // Clear out any old rules.
      mc.dom.$rule.find('option.rule').remove();

      // Add the new rules.
      rules.forEach(function(r) {
        var rule = document.createElement('option');
        $(rule).val(r).text(r);
        rule.className = 'rule';
        mc.dom.$other.before(rule);
      });

      // If the user already entered a subject, keep it as the 'Other', unless they
      // entered a rule, then select that rule.
      if (rules.length) {
        mc.dom.$rule.show(ANIM_MS);
        if (_(rules).contains(mc.dom.$subject.val())) {
          // Move the custom subject to the selected rule.
          mc.dom.$rule.val(mc.dom.$subject.val());
        } else if (/\S/.test(mc.dom.$subject.val())
            || _(mc.dom.$subject).contains(document.activeElement)
            || rulesJson['sr_name'] !== 'reddit.com') {
          // Select Other if the user has entered anything in the custom
          // subject OR if the keyboard focus is already on the subject OR if
          // the subreddit is not reddit.com.
          //
          // This call happens after a network round trip to load the rules, so
          // there's plenty of time for the user to have started typing.
          mc.dom.$other.prop('selected', true);
        } else {
          // To reddit.com. Select Blank by default to encourage consistent subjects.
          mc.dom.$blank.show();
          mc.dom.$blank.prop('selected', true);
        }
      }
      mc.dom.$rule.change();
    },


    /**
     * Changes the state of the form to be correct for a non-subreddit recipient.
     * @private
     */
    renderGeneralSubject: function() {
      // If the focus is on the rule selector, switch it to the subject.
      if (_(mc.dom.$rule).contains(document.activeElement)) {
        mc.dom.$subject.focus();
      }

      if (mc.customSubject && /\S/.test(mc.customSubject)) {
        mc.dom.$subject.val(mc.customSubject);
      } else {
        // Move the rule to the custom value, if the user has selected one.
        mc.dom.$rule.val(mc.dom.$subject.val());
      }

      mc.dom.$rule.hide(ANIM_MS);
      mc.dom.$subject.show(ANIM_MS);
    },


    /** Handles an update to the recipient of the message. */
    onToUpdate: function() {
      var toValue = mc.dom.$to.val();
      // Is this probably a subreddit?
      var m = SUBREDDIT.exec(toValue);
      if (m) {
        mc.loadSubredditRules(m[1])
          .then(mc.renderSubredditSubject, mc.renderGeneralSubject);
      } else {
        mc.renderGeneralSubject();
      }
    },


    /**
     * Handles a change to which rule based subject is selected.
     * @private
     * @param {!Event} e
     */
    onSelectedRuleChange: function(e) {
      if (e.target.options[e.target.selectedIndex] === mc.dom.$other[0]) {
        // Handle selecting "Other" by showing the custom subject field.
        if (mc.customSubject && /\S/.test(mc.customSubject)) {
          mc.dom.$subject.val(mc.customSubject);
        }
        mc.dom.$subject.show(ANIM_MS);
        mc.dom.$subject.focus();
      } else {
        // The user selected a normal rule. Merge the custom subject back into
        // the rule.
        mc.dom.$subject.hide(ANIM_MS);
        if (_(mc.dom.$subject).contains(document.activeElement)) {
          mc.dom.$rule.focus();
        }

        // Use the rule as the subject of the message.
        mc.dom.$subject.val(mc.dom.$rule.val());
      }

      // Destroy the blank default option once a different one is chosen.
      if (e.target.options[e.target.selectedIndex] === mc.dom.$blank[0]) {
        mc.dom.$blank.hide();
      }
    },


    /**
     * Handles the user changing the subject.
     * @private
     * @param {!Event} e
     */
    onSubjectChange: function(e) {
      mc.customSubject = mc.dom.$subject.val();
    },
  };


  // No exports, this is a leaf module for now.

  r.hooks.get('reddit-init').register(r.messagecompose.init);

}(r);
