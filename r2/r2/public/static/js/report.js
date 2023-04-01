$(function() {
  var sessionStorageKey = 'subreddit-rules';

  var templates;
  var cachedRules;

  function _getTemplate(id) {
    var elem = document.getElementById(id);
    return _.template(elem.innerHTML);
  }

  function init() {
    var subredditRulesTemplate = _getTemplate('subreddit-rules-report-template');
    var subredditDefaultTemplate = _getTemplate('subreddit-default-report-template');
    var redditTemplate = _getTemplate('reddit-report-template');
    var reasonTemplate = _getTemplate('report-reason-template');

    templates = {
      subredditRules: function(data) {
        var rulesStr = data.rules.map(reasonTemplate).join('\n');
        var formStr = subredditRulesTemplate(data);
        var formEl = $.parseHTML(formStr);
        var rulesEls = $.parseHTML(rulesStr);
        $(formEl).find('.report-reason-list').prepend(rulesEls);
        return formEl;
      },

      subredditDefault: function(data) {
        var formStr = subredditDefaultTemplate(data);
        var formEl = $.parseHTML(formStr);
        return formEl;
      },

      reddit: function(data) {
        var formStr = redditTemplate(data);
        var formEl = $.parseHTML(formStr);
        return formEl;
      },
    };

    try {
      cachedRules = window.sessionStorage.getItem(sessionStorageKey);
      cachedRules = JSON.parse(cachedRules);
    } finally {
      cachedRules = cachedRules || {};
    }

    // temporary, after release this will get cleaned up and r.reports will
    // be moved into this file
    r.rulesSessionStorageKey = sessionStorageKey;
    r.hooks.get('new-report-form').call();
  }

  function renderFromTemplate(data, thingType) {
    var hasSubreddit = !!data.sr_name;
    var hasRules = data.rules && data.rules.length > 0;
    var template;
    var templateData;

    if (!hasSubreddit) {
      template = templates.reddit;
    } else if (hasRules) {
      template = templates.subredditRules;
    } else {
      template = templates.subredditDefault;
    }

    if (hasRules) {
      templateData = _.clone(data);
      templateData.rules = data.rules.filter(function(rule) {
        return !rule.kind || rule.kind === thingType;
      })
    } else {
      templateData = data;
    }

    return template(templateData);
  }

  function showForm($reportForm, form) {
    $reportForm.empty();
    $reportForm.append(form);
    $(form).css('display', 'block');
  }

  function toggleReportForm() {
    var $reportForm = $(this).closest('.reportform');
    $reportForm.toggleClass('active');
    return false
  }

  function toggleOther() {
    var $reportForm = $(this).closest('.reportform');
    var $submit = $reportForm.find('[type="submit"]');
    var $reason = $reportForm.find('[name=reason]:checked');
    var $other = $reportForm.find('[name="other_reason"]');
    var isOther = $reason.val() === 'other';

    $submit.removeAttr('disabled');

    if (isOther) {
      $other.removeAttr('disabled').focus();
    } else {
      $other.attr('disabled', 'disabled');
    }
    return false
  }

  function getReportAttrs($el) {
    return {thing: $el.thing_id()}
  }

  function openReportForm(e) {
    if (r.access.isLinkRestricted(e.target)) {
      return;
    }

    var $thing = $(this).closest('.thing');
    var srFullname = $thing.data('subreddit-fullname');
    var thingType = $thing.data('type');
    var $flatList = $(this).closest('.flat-list');
    var $reportForm = $flatList.siblings('.reportform').eq(0);
    $reportForm.toggleClass('active');

    if (!$reportForm.hasClass('active')) {
      return;
    }

    // Automatically focus the radio input when this changes.
    // known bug: doesn't work if user selects the existing value.
    $reportForm.on('change', 'select[name=site_reason]', function() {
      $reportForm.find('.site-reason-radio').focus().prop('checked', true);
    });

    $reportForm.on('click', 'select[name=site_reason]', function() {
      $reportForm.find('.site-reason-radio').prop('checked', true);
      toggleOther.call(this);
    });

    $reportForm.html('<img class="flairthrobber" />')
    var $imgChild = $reportForm.children("img");
    $imgChild.attr('src', r.utils.staticURL('throbber.gif'));

    var attrs = getReportAttrs($(this))
    var useHtmlAPI = !(templates && r.config.feature_new_report_dialog);

    if (useHtmlAPI) {
      // deprecated; get rendered html from server
      $.request("report_form", attrs, function(res) {
        var form = $.parseHTML(res);
        showForm($reportForm, form);
      }, true, "html", true);
    } else if (!srFullname) {
      // if no subreddit, render the reddit form (never needs to hit API)
      var formData = { fullname: attrs.thing };
      var form = renderFromTemplate(formData, thingType);
      showForm($reportForm, form);
    } else if (srFullname in cachedRules) {
      // render from cached if available (only needs to hit API once per subreddit)
      var formData = cachedRules[srFullname];
      formData.fullname = attrs.thing;
      var form = renderFromTemplate(formData, thingType);
      showForm($reportForm, form);
    } else {
      // fetch from the API and cache for later
      attrs.api_type = 'json';
      $.request("report_form", attrs, function(res) {
        var data = res.json.data;
        cachedRules[srFullname] = data;
        data.fullname = attrs.thing;

        try {
          var rulesJSON = JSON.stringify(cachedRules);
          window.sessionStorage.setItem(sessionStorageKey, rulesJSON);
        } finally {
          var form = renderFromTemplate(data, thingType);
          showForm($reportForm, form);
        }
      }, true, 'json', true);  
    }

    return false;
  }

  r.hooks.get('setup').register(function() {
    if (r.config.feature_new_report_dialog) {
      try {
        init();
      } catch (err) {
        // only meant to catch transient errors. falls back to the HTML api
      }
    }

    // temp fix for broken subreddit-report-form
    $('div.content').on('submit', '.subreddit-report-form', function(e) {
      var $actionForm = $(e.target);
      return post_pseudo_form($actionForm, 'report');
    });

    $("div.content").on("click", ".tagline .reportbtn, .thing .reportbtn", openReportForm);
    $("div.content").on("click", ".btn.report-cancel", toggleReportForm);
    $("div.content").on("change", "input[name='reason']", toggleOther);
  });
});
