$(function() {
    function showSaveButton(field) {
        $(field).parent().parent().addClass("edited");
        $(field).parent().parent().find(".status").empty();
    }

    function onEdit() {
        if ($(this).data("saved") != $(this).val()) {
            showSaveButton(this);
        }
    }

    function onDelete(e) {
        e.preventDefault()
        return post_form(this.parentNode, e.data.action);
    }

    function onFocus() {
        showSaveButton(this);
    }

    function onSubmit(e) {
        e.preventDefault()
        $(this).removeClass("edited");
        return post_form(this, e.data.action);
    }

    function toggleFlairSelector() {
        open_menu(this);
        $(this).addClass("active");
        return false;
    }

    function getFlairAttrs($el) {
        if ($el.data('name')) {
            return {name: $el.data('name')}
        }
        return {link: $el.thing_id()}
    }

    function selectFlairInSelector(e) {
        $(".flairselector li").removeClass("selected");
        $(this).addClass("selected");
        var form = $(this).parent().parent().siblings("form")[0];
        $(form).children('input[name="flair_template_id"]').val(this.id);
        var customizer = $(form).children(".customizer");
        var input = customizer.children("input");
        if ($(this).hasClass("texteditable")) {
            customizer.addClass("texteditable");
            input.removeAttr("disabled");
            input.css("display", "block");
            input.val($.trim($(this).find(".flair, .linkflairlabel").text())).select();
            input.keyup(function() {
                $(".flairselection .flair, .flairselection .linkflairlabel")
                    .text($(input).val()).attr("title", $(input).val());
            });
        } else {
            customizer.removeClass("texteditable");
            input.attr("disabled", "disabled").hide();
        }
        var remover = $(".flairselector .flairremove").detach();
        $(".flairselection").html($(this).first().children().clone())
            .append(remover);
        $(".flairselector .flairremove").css("display", "inline-block");
        return false;
    }

    function removeFlairInSelector(e) {
        var form = $(this).parent().parent();
        $(form).children('input[name="flair_template_id"]').val("");
        $(form).children(".customizer").hide();
        var remover = $(".flairselector .flairremove").detach();
        $(remover).hide();
        $(".flairselector li").removeClass("selected");
        $(".flairselection").empty().append(remover);
    }

    function postFlairSelection(e) {
        $(this).find(".status").html(r.config.status_msg.submitting).show()
        var $btn = $(this.parentNode.parentNode).find('.flairselectbtn')
        simple_post_form(this, "selectflair", getFlairAttrs($btn));
        return false;
    }

    function openFlairSelector(e) {
        if (r.access.isLinkRestricted(e.target)) {
            return false;
        }

        close_menus(e);

        var button = this;
        var selector = $(button).siblings(".flairselector")[0];

        function columnize(col) {
            var min_cols = 1;
            var max_cols = 3;
            var min_col_width = 150;
            var max_col_height = 10;
            var length = $(col).children().length;
            var num_cols =
                Math.max(
                    min_cols,
                    Math.min(max_cols, Math.ceil(length / max_col_height)));
            var height = Math.ceil(length / num_cols);
            var col_width = Math.max(min_col_width, $(col).width());

            // Fix the width of the ul before splitting it into columns. This
            // This prevents it from shrinking if its widest element gets moved
            // into one of the other generated columns.
            $(col).width(col_width);

            if (num_cols > 1) {
                $(col).css('float', 'left');  // force IE7 to lay out properly

                var num_short_cols = num_cols * height - length;

                for (var i = 1; i < num_cols; i++) {
                    var h = height;
                    if (i <= num_short_cols) {
                        h--;
                    }
                    var start = length - h;
                    length -= h;
                    var tail = $(col).children().slice(start).remove();
                    $(tail).width(col_width);
                    $(col).after($("<ul>")
                        .css('float', 'left')  // force IE7 to lay out properly
                        .append(tail));
                }
            }

            // return new width; add a little padding to each column, plus
            // some extra padding in case a vertical scrollbar appears
            return num_cols * (col_width + 5) + 50;
        }

        function handleResponse(r) {
            $(selector).html(r);

            var ul = $(".flairselector ul");
            var width = Math.max(
                200, ul.length ? columnize(ul) : $(".error").width() + 20);
            var left = Math.max(
                100, $(button).position().left + $(button).width() - width);

            $(selector)
                .height("auto")
                .width(width)
                .css("left", left + "px")
                .click(false)
                .find(".flairselection")
                    .click(false)
                .end()
                .find("form")
                    // don't bubble clicks in the form up to the .click(false)
                    .click(function(e) { e.stopPropagation(); })
                    .submit(postFlairSelection)
                .end()
                .find(".customizer input")
                    .attr("disabled", "disabled")
                .end()
                .find("li.selected")
                    .each(selectFlairInSelector)
                .end()
                .find("li:not(.error)")
                    .click(selectFlairInSelector)
                .end()
                .find(".flairremove")
                    .click(removeFlairInSelector)
                .end();
        }

        $(selector)
            .html('<img class="flairthrobber" src="' + r.utils.staticURL('throbber.gif') + '" />')
            .addClass("active")
            .height(18).width(18)
            .css("padding-left", 4)
            .css("padding-top", 4)
            .css("padding-bottom", 4)
            .css("padding-right", 4)
            .css("left",
                 ($(button).position().left + $(button).width() - 18) + "px")
            .css("top", $(button).position().top + "px");

        var attrs = getFlairAttrs($(this))
        $.request("flairselector",  attrs, handleResponse, true, "html");
        return false;
    }

    // Attach event handlers to the various flair forms that may be on page.

    $("#tabbedpane-grant").on("submit",  ".flair-entry", {
        action: "flair",
      }, onSubmit)

    $("#tabbedpane-grant").on("click", ".flairdeletebtn", {
        action: "deleteflair",
      }, onDelete)


    $("#tabbedpane-templates, #tabbedpane-link_templates").on("submit", ".flair-entry", {
          action: "flairtemplate",
      }, onSubmit)

    $("form.clearflairtemplates").on("submit", {
        action: "clearflairtemplates"
      }, onSubmit)

    $(".flairlist")
        .on("focus", ".flaircell input", onFocus)
        .on("keyup", ".flaircell input", onEdit)
        .on("change", ".flaircell input", onEdit)
        .on("click", ".flairtemplate .flairdeletebtn", {
            action: "deleteflairtemplate",
          }, onDelete)

    // Event handlers for sidebar flair prefs.
    $(".flairtoggle").submit(function() {
        return post_form(this, 'setflairenabled');
    });
    $(".flairtoggle input").change(function() { $(this).parent().submit(); });

    $(document).on("click", ".tagline .flairselectbtn, .thing .flairselectbtn", openFlairSelector);

    $(".flairselector .dropdown").click(toggleFlairSelector);
});
