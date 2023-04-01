# The contents of this file are subject to the Common Public Attribution
# License Version 1.0. (the "License"); you may not use this file except in
# compliance with the License. You may obtain a copy of the License at
# http://code.reddit.com/LICENSE. The License is based on the Mozilla Public
# License Version 1.1, but Sections 14 and 15 have been added to cover use of
# software over a computer network and provide for limited attribution for the
# Original Developer. In addition, Exhibit A has been modified to be consistent
# with Exhibit B.
#
# Software distributed under the License is distributed on an "AS IS" basis,
# WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
# the specific language governing rights and limitations under the License.
#
# The Original Code is reddit.
#
# The Original Developer is the Initial Developer.  The Initial Developer of
# the Original Code is reddit Inc.
#
# All portions of the code written by reddit are Copyright (c) 2006-2015 reddit
# Inc. All Rights Reserved.
###############################################################################

from Crypto.Cipher import AES
from Crypto.Random import get_random_bytes
import base64
import hashlib
import urllib

from pylons import request
from pylons import tmpl_context as c
from pylons import app_globals as g

from r2.lib.filters import _force_utf8


KEY_SIZE = 16  # AES-128
SALT_SIZE = KEY_SIZE * 2  # backwards compatibility


def _pad_message(text):
    """Return `text` padded out to a multiple of block_size bytes.

    This uses the PKCS7 padding algorithm. The pad-bytes have a value of N
    where N is the number of bytes of padding added. If the input string is
    already a multiple of the block size, it will be padded with one full extra
    block to make an unambiguous output string.

    """
    block_size = AES.block_size
    padding_size = (block_size - len(text) % block_size) or block_size
    padding = chr(padding_size) * padding_size
    return text + padding


def _unpad_message(text):
    """Return `text` with padding removed. The inverse of _pad_message."""
    if not text:
        return ""

    padding_size = ord(text[-1])
    if padding_size > AES.block_size:
        return ""

    unpadded, padding = text[:-padding_size], text[-padding_size:]
    if any(ord(x) != padding_size for x in padding):
        return ""

    return unpadded


def _make_cipher(initialization_vector, secret):
    """Return a block cipher object for use in `encrypt` and `decrypt`."""
    return AES.new(secret[:KEY_SIZE], AES.MODE_CBC,
                   initialization_vector[:AES.block_size])


def encrypt(plaintext):
    """Return the message `plaintext` encrypted.

    The encrypted message will have its salt prepended and will be URL encoded
    to make it suitable for use in URLs and Cookies.

    NOTE: this function is here for backwards compatibility. Please do not
    use it for new code.

    """

    salt = _make_salt()
    return _encrypt(salt, plaintext, g.tracking_secret)


def _make_salt():
    # we want SALT_SIZE letters of salt text, but we're generating random bytes
    # so we'll calculate how many bytes we need to get SALT_SIZE characters of
    # base64 output. because of padding, this only works for SALT_SIZE % 4 == 0
    assert SALT_SIZE % 4 == 0
    salt_byte_count = (SALT_SIZE / 4) * 3
    salt_bytes = get_random_bytes(salt_byte_count)
    return base64.b64encode(salt_bytes)


def _encrypt(salt, plaintext, secret):
    cipher = _make_cipher(salt, secret)

    padded = _pad_message(plaintext)
    ciphertext = cipher.encrypt(padded)
    encoded = base64.b64encode(ciphertext)

    return urllib.quote_plus(salt + encoded, safe="")


def decrypt(encrypted):
    """Decrypt `encrypted` and return the plaintext.

    NOTE: like `encrypt` above, please do not use this function for new code.

    """

    return _decrypt(encrypted, g.tracking_secret)


def _decrypt(encrypted, secret):
    encrypted = urllib.unquote_plus(encrypted)
    salt, encoded = encrypted[:SALT_SIZE], encrypted[SALT_SIZE:]
    ciphertext = base64.b64decode(encoded)
    cipher = _make_cipher(salt, secret)
    padded = cipher.decrypt(ciphertext)
    return _unpad_message(padded)


def get_site():
    """Return the name of the current "site" (subreddit)."""
    return c.site.analytics_name if c.site else ""


def get_srpath():
    """Return the srpath of the current request.

    The srpath is Subredditname-Action. e.g. sophiepotamus-GET_listing.

    """
    name = get_site()
    action = None
    if c.render_style in ("mobile", "compact"):
        action = c.render_style
    else:
        action = request.environ['pylons.routes_dict'].get('action')

    if not action:
        return name
    return '-'.join((name, action))


def _get_encrypted_user_slug():
    """Return an encrypted string containing context info."""
    # The cname value (formerly c.cname) is expected by The traffic system.
    cname = False
    data = [
        c.user._id36 if c.user_is_loggedin else "",
        get_srpath(),
        c.lang or "",
        cname,
    ]
    return encrypt("|".join(_force_utf8(s) for s in data))


def get_pageview_pixel_url():
    """Return a URL to use for tracking pageviews for the current request."""
    return g.tracker_url + "?v=" + _get_encrypted_user_slug()


def get_impression_pixel_url(codename):
    """Return a URL to use for tracking impressions of the given advert."""
    # TODO: use HMAC here
    mac = codename + hashlib.sha1(codename + g.tracking_secret).hexdigest()
    v_param = "?v=%s&" % _get_encrypted_user_slug()
    hash_and_id_params = urllib.urlencode({"hash": mac,
                                           "id": codename,})
    return g.adframetracker_url + v_param + hash_and_id_params
