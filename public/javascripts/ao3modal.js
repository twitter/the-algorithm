/*

THIS FILE GETS MINIFIED!  It is included as "ao3modal.min.js".
USE A MINIFIER AND UPDATE THE .min.js FILE AFTER MAKING ANY CHANGES HERE!

*/

jQuery(document).ready(function() {
    window.ao3modal = (function($) {

        var _loading = false,
            _bgDiv = $('<div>', {'id': 'modal-bg'}).addClass('modal-closer'),
            _loadingDiv = $('<div>').addClass('loading'),
            _wrapDiv = $('<div>', {'id': 'modal-wrap'}).addClass('modal-closer'),
            _modalDiv = $('<div>', {'id': 'modal'}),
            _contentDiv = $('<div>').addClass('content userstuff'),
            _closeButton = $('<a>').addClass('action modal-closer')
                .click(function(event) { event.preventDefault(); })
                .attr('href', '#')
                .text('Close'),
            _title = $('<span>').addClass('title'),
            _tallHeight,
            _mobile = (function(uastring) { // detectmobilebrowsers.com (added ipad to regex)
                return /(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(ad|hone|od)|iris|kindle|lge |maemo|midp|mmp|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino/i.test(uastring) ||
                    /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(uastring.substr(0,4));
            })(navigator.userAgent||navigator.vendor||window.opera),
            _mobileScrollTop = -1, // for returning to previous page scroll position after closing modal
            _scrollbarWidth = _mobile ? 0 : (function() { // used as a margin when scrollbar is hidden
                var inner = $('<p>').css({'width': '100%', 'height': 200}),
                    outer = $('<div>').css({
                        'height': 150,
                        'left': 0,
                        'overflow': 'hidden',
                        'top': 0,
                        'visibility': 'hidden',
                        'width': 200
                    }).append(inner).appendTo($('body')),
                    w1 = inner[0].offsetWidth,
                    w2;

                outer.css('overflow', 'scroll');
                w2 = inner[0].offsetWidth;
                if (w1 == w2) w2 = outer[0].clientWidth;

                outer.remove();
                return (w1 - w2);
            })(),
            _keyboard;

        function _toggleScroll(on) {
            if (_mobile) { return; }
            $('body').css({
                'margin-right': on ? '' : _scrollbarWidth,
                'overflow': on ? '' : 'hidden',
                'height': on ? '' : _bgDiv.height()
            });
        }

        function _calcSize() {
            var img, scaledHeight, maxHeight,
                hidden = !_modalDiv.is(':visible');

            if (_mobile && _mobileScrollTop < 0) {
                _mobileScrollTop = $(window).scrollTop();
            }

            if (hidden) { _modalDiv.css('opacity', 0).show(); }

            _modalDiv.removeClass('tall');

            if (_modalDiv.hasClass('img')) {
                img = _contentDiv.children('img').first();

                _modalDiv.toggleClass('tall', _modalDiv.height() >= 0.95*_bgDiv.height());
            } else if (!_mobile) {
                scaledHeight = _bgDiv.height()*_tallHeight.scale;
                maxHeight = Math.min(scaledHeight, _tallHeight.max);

                _modalDiv.toggleClass('tall', (!maxHeight || _modalDiv[0].scrollHeight > maxHeight));
            }

            if (hidden) { _modalDiv.hide().css('opacity', ''); }

            if (_mobile) {
                _wrapDiv.css('top', _mobileScrollTop);
            } else {
                _wrapDiv.css('top', $(window).scrollTop());
            }
        }

        function _setContent(content, title) {
            _loading = false;

            _contentDiv.empty();
            if (content instanceof $) { _contentDiv.append(content); }
            else { _contentDiv.html(content); }

            title = (title instanceof $) ? title : $('<span>').addClass('title').text(title || '');
            _title.replaceWith(title);
            _title = title;

            if (!_contentDiv.is(':empty')) {
                _calcSize();

                if (!_contentDiv.is(':visible')) {
                    _keyboard.toggleHandlers(true);
                    _loadingDiv.hide();
                    _modalDiv.fadeIn(function() {
                        _contentDiv.focus();
                    });
                }
            }
        }

        function _show(href, title) {
            _modalDiv.hide();
            _wrapDiv.show();
            if ($.support.opacity) { _bgDiv.add(_loadingDiv).fadeIn(); }
            else { _bgDiv.add(_loadingDiv).show(); }

            _toggleScroll(false);

            _loading = true;

            if (href.indexOf('#') === 0) {
                _setContent($(href).html(), title || '');
            } else if (href.indexOf('.jpg') == href.length-'.jpg'.length ||
                href.indexOf('.gif') == href.length-'.gif'.length ||
                href.indexOf('.bmp') == href.length-'.bmp'.length ||
                href.indexOf('.png') == href.length-'.png'.length) {

                _modalDiv.addClass('img');

                var img = $('<img>').appendTo($('body')).attr('src', href),
                    imgLoad = function() {

                        var srcLink = $('<a>').addClass('title')
                            .attr({'href': href, 'title': 'View original', 'target': '_blank'})
                            .text(title ||
                                (href.indexOf('/') != -1 ? href.substring(href.lastIndexOf('/')+1) : href)
                            ).css('text-decoration', 'underline');

                        img.remove();
                        if (!_loading) { return; }

                        if (title) { img.attr('alt', title); }

                        _setContent(img[0], srcLink);
                    };

                if (img[0].complete) { imgLoad(); }
                else { img.load(imgLoad); }
            } else {
                $.ajax({
                    url: href,
                    success: function(data) {
                        if (!_loading) { return; }
                        _setContent(data, title);
                    }
                });
            }
        }

        function _hide() {
            _loading = false;
            _title.text('');

            _keyboard.toggleHandlers(false);

            _wrapDiv.fadeOut(function() {
                _setContent('');
                _toggleScroll(true);
                _modalDiv.css('width', '').removeClass('tall img');
                if (_mobile && _mobileScrollTop >= 0) {
                    $('html, body').animate({'scrollTop': _mobileScrollTop}, 'fast');
                    _mobileScrollTop = -1;
                }
            });

            if ($.support.opacity) { _bgDiv.fadeOut(); }
            else { _bgDiv.hide(); }
        }

        function _addLink(elements) {
            elements.each(function() {
                var img = $(this).is('img') ? $(this).removeClass('modal') : false,
                    a = !img ? $(this) : $('<a>')
                        .css('border', 'none')
                        .attr({
                            'title': (img.attr('title') || img.attr('alt')),
                            'href': img.attr('src')
                        }).replaceAll(img)
                        .append(img);

                a.addClass('modal modal-attached')
                    .attr('aria-controls', '#modal')
                    .click(function(event){
                        _show($(this).attr('href'), $(this).attr('title'));
                        event.preventDefault();
                    });

                // if link refers to in-page modal content, find it and hide it
                if (a.attr('href').indexOf('#') === 0) {
                    $(a.attr('href')).hide();
                }
            });
        }

        // add modal elements to page
        $('body').append(
            _bgDiv.append(_loadingDiv),
            _wrapDiv.append(
                _modalDiv.append(
                    _contentDiv,
                    $('<div>').addClass('footer')
                        .append(
                            _title,
                            _closeButton
                        )
                ).trap()
            )
        );

        // find the height scale and max-height of modal windows whose content is taller than the viewport
        // values should come from the css ruleset for #modal.tall
        _tallHeight = _mobile ? null : (function() {
            var heights = {}, modalDiv = _modalDiv.clone();

            modalDiv.css('margin-top', 9001).addClass('tall').appendTo(_bgDiv);
            heights.scale = !modalDiv.height() ? 0.8 : parseInt(modalDiv.css('height'))/100;
            heights.max = parseInt(modalDiv.css('max-height'));

            modalDiv.remove();
            return heights;
        })();

        // enable exit controls
        $('body').click(function(event) {
            if ($(event.target).hasClass('modal-closer') ||
                (_modalDiv.hasClass('img') && $(event.target).hasClass('content'))) {
                _hide();
            }
        });

        // recalculate modal size on viewport resize
        $(window).resize(function() {
            if (_modalDiv.is(':visible')) { _calcSize(); }
        });

        // set up keyboard handling
        _keyboard = (function() {
            var scrollValues = {
                    38: -20, // up arrow
                    40: 20, // down arrow
                    33: -200, // page up
                    34: 200 // page down
                },
                tabbed = false,
                handlers = {
                    'keydown': function(event) {
                        if (scrollValues[event.which]) {
                            _contentDiv[0].scrollTop += scrollValues[event.which];
                            event.preventDefault();
                        } else if (_modalDiv.is(':visible')) {
                            var target = event.target,
                                tag = target.tagName.toLowerCase(),
                                escKey = event.which == 27,
                                enterKey = event.which == 13,
                                targetIsInput = /^(input|textarea|a|button)$/.test(tag),
                                    // ignore enter keypresses on links & inputs
                                targetInModal = !!$(target).closest('#modal')[0];

                            if (escKey || enterKey && (!targetInModal || !targetIsInput)) {
                                _hide();
                            }

                            // key events triggered from outside the modal should also die,
                            // except for ctrl combinations like ctrl+c (or cmd+c on macOS)
                            var keyShortcut = event.ctrlKey || event.metaKey;
                            if (escKey || (!targetInModal && !keyShortcut) || enterKey && !targetIsInput) {
                                event.preventDefault();
                                event.stopPropagation();
                            }
                        }
                    },
                    'keypress': function(event) {
                        if (scrollValues[event.which]) { event.preventDefault(); }
                    },
                    'keyup': function(event) {
                        if (scrollValues[event.which]) { event.preventDefault(); }
                        else if (event.which == 9 && !tabbed) {
                            _closeButton.focus();
                            tabbed = true;
                            event.preventDefault();
                        }
                    }
                };

            return {
                toggleHandlers: function(on) {
                    tabbed = false;
                    for (var eventType in handlers) {
                        if (handlers.hasOwnProperty(eventType)) {
                            if (on) {
                                $('body').on(eventType, handlers[eventType]);
                            } else {
                                $('body').off(eventType, handlers[eventType]);
                            }
                        }
                    }
                }
            };
        })();

        // set modal-classed links to open modal boxes
        _addLink($('a.modal, img.modal'));

        // ensure handlers are attached to dynamically added modal invokers
        $('body').on('click', 'a.modal, img.modal', function(event) {
            var $this = $(this);
            if ($this.is('.modal-attached')) { return; }
            _addLink($this);
            event.preventDefault();
            if ($this.is('img')) {
                $this.parent().click();
            } else {
                $this.click();
            }
        });

        return {
            show: _show,
            setContent: _setContent,
            hide: _hide,
            addLink: _addLink
        };

    })(jQuery);

});
