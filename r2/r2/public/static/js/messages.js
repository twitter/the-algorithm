r.messages = {}

/**
 * After triggering a mark-as-read request, poll our account to see if our messages have been cleared.
 * If our messages have not been cleared after a number of iterations, it's likely we had a
 * message race or other issue, redirect back to stop polling and potentially display the new message.
 */
r.messages.pollUnread = _.debounce(function(count) {
  count = count + 1 || 1;

  // Took too long, redirect in case of issue.
  if (count > 20) {
    document.location = "/message/unread";
    return;
  }

  r.ajax({
    type: 'GET',
    url: '/api/me.json',
    success: function(response) {
      if (!response['data']['has_mail']) {
        document.location = "/message/unread";
      } else {
        r.messages.pollUnread(count);
      }
    },
  });
}, 2000);

r.messages.init = function() {
  $('a.mark-all-read').on('click', function(e) {
    var $this = $(this);

    e.preventDefault();
    e.stopPropagation();

    if ($this.hasClass('disabled')) {
      return;
    }

    $this.addClass('disabled');
    $this.parent().addClass('working');

    r.ajax({
      type: 'POST',
      url: '/api/read_all_messages',
      data: {},
      success: function(response) {
        r.messages.pollUnread();
      },
      error: function(response) {
        $this.parent().removeClass('working');
      },
    });
  });
}
