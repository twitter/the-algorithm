$(function() {

  r.warn_on_unload = function() {
    /*
     * To add a warning message to a form if the
     * user tries to leave a page where a form is in a
     * dirty state, add the following classes to your form:
     *
     * warn-on-unload - this class will prompt the user if
     * they try to leave a page with a dirty form
     */
    $(window).on('beforeunload', function (e) {
      var form = $("form.warn-on-unload");

      if(!$(form).length) {
        return;
      }

      var elements = form.find("input[type=text]," +
                               "input[type=checkbox]," +
                               "input[type=url]," +
                               "textarea")
                         .not(":hidden");

      var isDirty = false;
      elements.each(function() {

        switch(this.type) {
          case "checkbox":
            isDirty = (this.defaultChecked !== this.checked);
            break;
          case "textarea":
          case "text":
          case "url":
            isDirty = (this.defaultValue !== this.value);
            break;
          default:
            return true;
        }

        if(isDirty) {
          return false;
        }

      });

      if(isDirty) {
        return r._("You have unsaved changes!");
      }
    });
  };

  $("form.warn-on-unload").on("keypress", function(e) {
    $(window).off('beforeunload');
    r.warn_on_unload();
  });

  // Remove beforeunload event handler if a user clears their
  // comment, exclude the newlink form textareas
  $(".usertext.warn-on-unload textarea")
    .not(":hidden")
    .not("form#newlink .usertext textarea")
    .on("blur", function(e) {

    if(this.defaultValue === this.value) {
      $(window).off('beforeunload');
    }
  });

});
