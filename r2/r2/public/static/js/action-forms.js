r.actionForm = {
  init: function() {
    $('div.content').on(
      'click',
      '.action-thing, .cancel-action-thing',
      this.toggleActionForm.bind(this)
    );

    $('div.content').on(
      'submit',
      '.action-form',
      this.submitAction.bind(this)
    );
  },

  toggleActionForm: function(e) {
    var el = e.target;
    var $el = $(el);

    if (r.access.isLinkRestricted(el)) {
      return;
    }

    var $thing = $el.thing();
    var $thingForm = $thing.find('> .entry .action-form');
    var formSelector = $el.data('action-form');

    e.stopPropagation();
    e.preventDefault();

    if ($thingForm.length > 0) {
      if ($el.parents('.drop-choices').length) {
        $thingForm.show();
      } else {
        $thingForm.toggle();
      }
    } else {
      var $form = $(formSelector);
      var $clonedForm = $form.clone();
      var $insertionPoint = $thing.find('> .entry .buttons');
      var thingFullname = $thing.thing_id();

      $clonedForm.attr('id', 'action-thing-' + thingFullname);
      $clonedForm.find('input[name="thing_id"]').val(thingFullname);
      $clonedForm.insertAfter($insertionPoint);
      $clonedForm.show();
    }
  },

  submitAction: function(e) {
    var $actionForm = $(e.target).thing().find('.action-form');
    var action = $actionForm.data('form-action');

    return post_pseudo_form($actionForm, action);
  }

};

r.fraud = {

  init: function() {
    $('div.content').on(
      'click',
      '.action-thing',
      this.showReason.bind(this)
    );

    $('div.content').on(
      'change',
      '.fraud-action-form input',
      this.validate.bind(this)
    );
  },

  validate: function(e) {
    var $el = $(e.target);
    var $form = $el.parents('form');
    var $submit = $form.find('[type="submit"]');
    var $refund = $form.find('input[name=refund]');
    var fraud = $form.find('input[name=fraud]:checked').val();
    var allowRefund = fraud === 'True';

    if (allowRefund) {
      $refund.removeAttr('disabled').focus();
    } else {
      $refund.prop('checked', false).attr('disabled', 'disabled');
    }

    if (!!fraud) {
      $submit.removeAttr('disabled');
    } else {
      $submit.attr('disabled', 'disabled');
    }
  },

  showReason: function(e) {
    var $el = $(e.target);
    var $thing = $el.thing();
    var $form = $thing.find('> .entry .action-form');
    var $reason = $form.find('.fraud-reason');
    var reason = $el.attr('title');

    $reason.text(reason);
  },

};

r.report = {

  init: function() {
    $('div.content').on(
      'change',
      '.report-action-form input',
      this.validate.bind(this)
    );

    $('div.content').on(
      'click',
      '.reported-stamp.has-reasons',
      this.toggleReasons.bind(this)
    );
  },

  toggleReasons: function(e) {
    if (r.access.isLinkRestricted(e.target)) {
      return;
    }

    $(e.target).parent().find('.report-reasons').toggle();
  },

  validate: function(e) {
    var $thing = $(e.target).thing();
    var $form = $thing.find('> .entry .report-action-form');
    var $submit = $form.find('[type="submit"]');
    var $reason = $form.find('[name=reason]:checked');
    var $other = $form.find('[name="other_reason"]');
    var isOther = $reason.val() === 'other';

    $submit.removeAttr('disabled');

    if (isOther) {
      $other.removeAttr('disabled').focus();
    } else {
      $other.attr('disabled', 'disabled');
    }
  }

};

$(function () {
  r.actionForm.init();
  r.fraud.init();
  r.report.init();
});
