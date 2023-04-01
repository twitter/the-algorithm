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

import unittest


MESSAGE = "the quick brown fox jumped over..."
BLOCK_O_PADDING = ("\x10\x10\x10\x10\x10\x10\x10\x10"
                   "\x10\x10\x10\x10\x10\x10\x10\x10")
SECRET = "abcdefghijklmnopqrstuvwxyz"
ENCRYPTED = ("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaIbzth1QTzJxzHbHGnJywG5V1uR3tWtSB"
             "8hTyIcfg6rUZC4Wo0pT8jkEt9o1c%2FkTn")


class TestPadding(unittest.TestCase):
    def test_pad_empty_string(self):
        from r2.lib.tracking import _pad_message
        padded = _pad_message("")
        self.assertEquals(padded, BLOCK_O_PADDING)

    def test_pad_round_string(self):
        from r2.lib.tracking import _pad_message, KEY_SIZE
        padded = _pad_message("x" * KEY_SIZE)
        self.assertEquals(len(padded), KEY_SIZE * 2)
        self.assertEquals(padded[KEY_SIZE:], BLOCK_O_PADDING)

    def test_unpad_empty_message(self):
        from r2.lib.tracking import _unpad_message
        unpadded = _unpad_message("")
        self.assertEquals(unpadded, "")

    def test_unpad_evil_message(self):
        from r2.lib.tracking import _unpad_message
        evil = ("a" * 88) + chr(57)
        result = _unpad_message(evil)
        self.assertEquals(result, "")

    def test_padding_roundtrip(self):
        from r2.lib.tracking import _unpad_message, _pad_message
        tested = _unpad_message(_pad_message(MESSAGE))
        self.assertEquals(MESSAGE, tested)


class TestEncryption(unittest.TestCase):
    def test_salt(self):
        from r2.lib.tracking import _make_salt, SALT_SIZE
        self.assertEquals(len(_make_salt()), SALT_SIZE)

    def test_encrypt(self):
        from r2.lib.tracking import _encrypt, SALT_SIZE
        encrypted = _encrypt(
            "a" * SALT_SIZE,
            MESSAGE,
            SECRET,
        )
        self.assertEquals(encrypted, ENCRYPTED)

    def test_decrypt(self):
        from r2.lib.tracking import _decrypt
        decrypted = _decrypt(ENCRYPTED, SECRET)
        self.assertEquals(MESSAGE, decrypted)
