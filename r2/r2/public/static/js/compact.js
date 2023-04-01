/*This hides the url bar on mobile*/
(function($) {
    $.fn.show_toolbar = function() {
        var tb = this;
        $(this).show();
    };
    $.unsafe_orig = $.unsafe;
    $.unsafe = function(text) {
        /* inverts websafe filtering of reddit app. */
        text = $.unsafe_orig(text);
        if (typeof(text) == "string") {
            /* space compress the result */
            text = r.utils.spaceCompress(text);
        }
        return (text || "");
    }
})(jQuery);

$(function() {
    if ($(window).scrollTop() == 0) {
        $(window).scrollTop(1);
    }
    ;
    /* Top menu dropdown*/
    $('#topmenu_toggle').click(function() {
        $(this).toggleClass("active");
        $('#top_menu').toggle();
        return false;
    });
    //Self text expando
    $(document).on('click', '.expando-button', function() {
        $(this).toggleClass("expanded");
        $(this).thing().find(".expando").toggle();
        return false;
    });
    //Help expando
    $(document).on('click', '.help-toggle', function() {
        $(this).toggleClass("expanded");
        $(this).parent().parent().siblings(".markhelp-parent").toggle();
        return false;
    });

    //Options expando
    $(document).on('click', '.options_link', function(evt) {

        if (! $(this).siblings(".options_expando").hasClass('expanded')) {
            $('.options_expando.expanded').each(function() { //Collapse any other open ones
                $(this).removeClass('expanded');
            });
            $('.options_link.active').each(function() {
               $(this).removeClass('active');
            });
            $(this).siblings(".options_expando").addClass('expanded'); //Expand this one
            $(this).addClass('active');
        } else {
             $(this).siblings(".options_expando").removeClass('expanded'); //Just collapse this one
             $(this).removeClass('active');
        }
        return false;
    });
    //Save button state transition
    $(document).on("click", ".save-button", function() {
        $(this).toggle();
        $(this).siblings(".unsave-button").toggle();
    });
    $(document).on("click", ".unsave-button", function() {
        $(this).toggle();
        $(this).siblings(".save-button").toggle();
    });
    //Hide options when we collapse
    $(document).on("click", '.options_expando .collapse-button', function() {
        $(this).parent().removeClass('expanded');
        $(this).parent().parent().parent().addClass("collapsed");
        $(this).parent().siblings('.options_link').removeClass("active");
    });
    //Collapse when we click reply, or edit
    $(document).on("click", '.reply-button, .edit-button', function() {
        $(this).parent().siblings('.options-link').click();
    });

    $(document).on("click", ".link", function(evt) {
        if (evt && evt.target && $(evt.target).hasClass("thing")) {
            $(this).find(".options_link").click();
            return false;
        }
    });
    //Comment options
    $(document).on("click", ".comment.collapsed", function(e) {
        $(this).removeClass("collapsed");
    });
    $(document).on("click", ".message.unread", function(e) {
        var thing = $(this)
        read_thing(thing);
        return false;
    });
    /*Finally*/
    $(document).on('click', 'a[href=#]', function() {
        return false;
    });
});

$(function() {
    var eut = edit_usertext;
    edit_user_text = function(what) {
        $(what).parent().parent().toggleClass('hidden');
        return eut(what);
    };

});

function show_edit_usertext(form) {
    var edit = form.find(".usertext-edit");
    var body = form.find(".usertext-body");
    var textarea = edit.find('div > textarea');
    //we need to show the textbox first so it has dimensions
    body.hide();
    edit.show();

    form
            .find(".cancel, .save").show().end()
            .find(".help-toggle").show().end();

    textarea.focus();
}

function fetch_more() {
    $("#siteTable").after($("<div class='loading'><img src='" + r.utils.staticURL('reddit_loading.png') + "'/></div>"));


    var o = document.location;
    var path = o.pathname.split(".");
    if (path[path.length - 1].indexOf('/') == -1) {
        path = path.slice(0, -1).join('.');
    } else {
        path = o.pathname;
    }
    var apath = o.protocol + "//" + o.host + path + ".json-compact" + o.search;
    var last = $("#siteTable").find(".thing:last");
    apath += ((document.location.search) ? "&" : "?") +
            "after=" + last.thing_id();

    if (last.find(".rank").length)
        "&count=" + parseInt(last.find(".rank").html())

    $.getJSON(apath, function(res) {
        $.insert_things(res.data, true);
        $(".thing").click(function() {
        });
        /* remove the loading image */
        $("#siteTable").next(".loading").remove();
        if (res && res.data.length == 0) {
            $(window).unbind("scroll");
        }
    });
}

$(function() {
    if (!!store.safeGet('mobile-web-redirect-opted')) {
        return;
    }

    var $bar = $('.mobile-web-redirect-bar');

    $bar.find('.mobile-web-redirect-optin').on('click', function() {
        store.safeSet('mobile-web-redirect-opted', true);
    });

    $bar.find('.mobile-web-redirect-optout').on('click', function(e) {
        e.preventDefault();
        store.safeSet('mobile-web-redirect-opted', true);
        $bar.fadeOut();
    });

    $bar.show();
});
