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

"""An implementation of the RFC-6238 Time-Based One Time Password algorithm."""

import time
import hmac
import base64
import struct
import hashlib


PERIOD = 30


def make_hotp(secret, counter):
    """Generate an RFC-4226 HMAC-Based One Time Password."""
    key = base64.b32decode(secret)

    # compute the HMAC digest of the counter with the secret key
    counter_encoded = struct.pack(">q", counter)
    hmac_result = hmac.HMAC(key, counter_encoded, hashlib.sha1).digest()

    # do HOTP dynamic truncation (see RFC4226 5.3)
    offset = ord(hmac_result[-1]) & 0x0f
    truncated_hash = hmac_result[offset:offset + 4]
    code_bits, = struct.unpack(">L", truncated_hash)
    htop = (code_bits & 0x7fffffff) % 1000000

    # pad it out as necessary
    return "%06d" % htop


def make_totp(secret, skew=0, timestamp=None):
    """Generate an RFC-6238 Time-Based One Time Password."""
    timestamp = timestamp or time.time()
    counter = timestamp // PERIOD
    return make_hotp(secret, counter - skew)


def generate_secret():
    """Make a secret key suitable for use in TOTP."""
    from Crypto.Random import get_random_bytes
    bytes = get_random_bytes(20)
    encoded = base64.b32encode(bytes)
    return encoded


if __name__ == "__main__":
    # based on RFC-6238 Appendix B (trimmed to six-digit OTPs)
    secret = base64.b32encode("12345678901234567890")
    assert make_totp(secret, timestamp=59) == "287082"
    assert make_totp(secret, timestamp=1111111109) == "081804"
    assert make_totp(secret, timestamp=1111111111) == "050471"
    assert make_totp(secret, timestamp=1234567890) == "005924"
    assert make_totp(secret, timestamp=2000000000) == "279037"
    assert make_totp(secret, timestamp=20000000000) == "353130"
