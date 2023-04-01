(function($) {
    $.fn.make_totp_qrcode = function (secret) {
        var form = $('#pref-otp'),
            newform = $('#pref-otp-qr'),
            placeholder = $('<div>')

        var username = encodeURIComponent("/u/" + r.config.logged);
        var params = $.param({
          "secret": secret,
          "issuer": r.config.cur_domain,
        });
        var uri = 'otpauth://totp/' + username + '?' + params;

        newform.find('#otp-secret-info').append(
            placeholder,
            $('<p class="secret">').text(secret)
        )

        placeholder.qrcode({
            width: 256,
            height: 256,
            text: uri
        })

        newform.show()
        form.hide()
    }
})(jQuery)
