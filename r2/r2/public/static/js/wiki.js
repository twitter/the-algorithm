r.wiki = {
    request: function(req) {
        if (r.config.logged) {
            req.data.uh = r.config.modhash
        }
        req.data.page = r.config.wiki_page
        $.ajax(req)
    },

    baseApiUrl: function() {
        return r.wiki.baseUrl(true)
    },

    baseUrl: function(api) {
        var base_url = ''
        if (api) {
            base_url += '/api'
        }
        base_url += '/wiki'
        if (!r.config.is_fake) {
            base_url = '/r/' + r.config.post_site + base_url
        }
        return base_url
    },

    init: function() {
        $('body.wiki-page').on('click', '.revision_hide', this.toggleHide)
        $('body.wiki-page').on('click', '.revision_delete', this.toggleDelete)
        $('body.wiki-page').on('click', '.toggle-source', this.toggleSource)
    },

    toggleSource: function(event) {
        event.preventDefault()
        $('.wiki-page .source').toggle('slow')
    },

    toggleDelete: function(event) {
        event.preventDefault()
        var $this = $(this),
            url = r.wiki.baseApiUrl() + '/delete',
            $this_parent = $this.parents('.revision'),
            deleted = $this_parent.hasClass('deleted')
        $this_parent.toggleClass('deleted')
        r.wiki.request({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: {
                revision: $this.data('revision'),
                deleted: !deleted
            },
            error: function() {
                $this_parent.toggleClass('deleted')
            },
            success: function(data) {
                if (!data.status) {
                    $this_parent.removeClass('deleted')
                } else {
                    $this_parent.addClass('deleted')
                }
            }
        })
    },

    toggleHide: function(event) {
        event.preventDefault()

        if (r.access.isLinkRestricted(this)) {
            return;
        }

        var $this = $(this),
            url = r.wiki.baseApiUrl() + '/hide',
            $this_parent = $this.parents('.revision')
        $this_parent.toggleClass('hidden')
        r.wiki.request({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: {
                revision: $this.data('revision')
            },
            error: function() {
                $this_parent.toggleClass('hidden')
            },
            success: function(data) {
                if (!data.status) {
                    $this_parent.removeClass('hidden')
                } else {
                    $this_parent.addClass('hidden')
                }
            }
        })
    },

    addUser: function(event) {
        event.preventDefault()
        $('#usereditallowerror').hide()
        var $this = $(event.target),
            url = r.wiki.baseApiUrl() + '/alloweditor/add'
        r.wiki.request({
            url: url,
            type: 'POST',
            data: {
                username: $this.find('[name="username"]').val()
            },
            dataType: 'json',
            error: function() {
                $('#usereditallowerror').show()
            },
            success: function(data) {
                location.reload()
            }
        })
    },

    submitEdit: function(event) {
        event.preventDefault()
        var $this = $(event.target),
            url = r.wiki.baseApiUrl() + '/edit',
            conflict = $('#wiki_edit_conflict'),
            special = $('#wiki_special_error')
        conflict.hide()
        special.hide()
        params = r.utils.serializeForm($this)
        $('#wiki_save_button').attr("disabled", true)
        $this.addClass("working")
        r.wiki.request({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: params,
            error: function() {
                $this.removeClass("working")
                $('#wiki_save_button').removeAttr("disabled")
            },
            success: function() {
                window.location = r.wiki.baseUrl() + '/' + r.config.wiki_page
            },
            statusCode: {
                409: function(xhr) {
                    var info = JSON.parse(xhr.responseText)
                        ,content = $this.children('#wiki_page_content')
                        ,diff = conflict.children('#yourdiff')
                    conflict.children('#youredit').val(content.val())
                    diff.html($.unsafe(info.diffcontent))
                    $this.children('#previous').val(info.newrevision)
                    content.val(info.newcontent)
                    conflict.fadeIn('slow')
                },
                415: function(xhr) {
                    var errors = JSON.parse(xhr.responseText).special_errors
                        ,specials = special.children('#specials')
                    specials.empty()
                    for(i in errors) {
                        specials.append($('<pre>').text($.unsafe(errors[i])))
                    }
                    special.fadeIn('slow')
                },
                429: function(xhr) {
                    var message = JSON.parse(xhr.responseText).message
                        ,specials = special.children('#specials')
                    specials.empty()
                    specials.text(message)
                    special.fadeIn('slow')
                }
            }
        })
    },

    goCompare: function() {
        v1 = $('input:radio[name=v1]:checked').val()
        v2 = $('input:radio[name=v2]:checked').val()
        url = r.wiki.baseUrl() + '/' + r.config.wiki_page + '?v=' + v1
        if (v2 != v1) {
            url += '&v2=' + v2
        }
        window.location = url
    },

    helpon: function(elem) {
        $(elem).parents("form").children(".markhelp:first").show();
    },

    helpoff: function(elem) {
        $(elem).parents("form").children(".markhelp:first").hide();
    }

}
