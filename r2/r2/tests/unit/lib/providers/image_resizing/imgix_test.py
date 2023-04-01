#!/usr/bin/env python
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

from r2.tests import RedditTestCase

from r2.lib.providers.image_resizing import NotLargeEnough
from r2.lib.providers.image_resizing.imgix import ImgixImageResizingProvider
from r2.lib.utils import UrlParser


URLENCODED_COMMA = '%2C'


class TestImgixResizer(RedditTestCase):
    def setUp(self):
        self.provider = ImgixImageResizingProvider()
        self.patch_g(
            imgix_domain='example.com',
            imgix_signing=False,
        )

    def test_no_resize(self):
        image = dict(url='http://s3.amazonaws.com/a.jpg', width=1200,
                      height=800)
        url = self.provider.resize_image(image)
        self.assertEqual(url, 'https://example.com/a.jpg')

    def test_too_small(self):
        image = dict(url='http://s3.amazonaws.com/a.jpg', width=12,
                      height=8)
        with self.assertRaises(NotLargeEnough):
            self.provider.resize_image(image, 108)

    def test_resize(self):
        image = dict(url='http://s3.amazonaws.com/a.jpg', width=1200,
                      height=800)
        for width in (108, 216, 320, 640, 960, 1080):
            url = self.provider.resize_image(image, width)
            self.assertEqual(url, 'https://example.com/a.jpg?w=%d' % width)

    def test_cropping(self):
        image = dict(url='http://s3.amazonaws.com/a.jpg', width=1200,
                      height=800)
        max_ratio = 0.5
        url = self.provider.resize_image(image, max_ratio=max_ratio)
        crop = URLENCODED_COMMA.join(('faces', 'entropy'))
        self.assertEqual(url,
                ('https://example.com/a.jpg?fit=crop&crop=%s&arh=%s'
                    % (crop, max_ratio)))

        width = 108
        url = self.provider.resize_image(image, width, max_ratio=max_ratio)
        self.assertEqual(url,
                ('https://example.com/a.jpg?fit=crop&crop=%s&arh=%s&w=%s'
                    % (crop, max_ratio, width)))

    def test_sign_url(self):
        u = UrlParser('http://examples.imgix.net/frog.jpg?w=100')
        signed_url = self.provider._sign_url(u, 'abcdef')
        self.assertEqual(signed_url.unparse(),
                'http://examples.imgix.net/frog.jpg?w=100&s=cd3bdf071108af73b15c21bdcee5e49c')

        u = UrlParser('http://examples.imgix.net/frog.jpg')
        u.update_query(w=100)
        signed_url = self.provider._sign_url(u, 'abcdef')
        self.assertEqual(signed_url.unparse(),
                'http://examples.imgix.net/frog.jpg?w=100&s=cd3bdf071108af73b15c21bdcee5e49c')

    def test_censor(self):
        image = dict(url='http://s3.amazonaws.com/a.jpg', width=1200,
                      height=800)
        url = self.provider.resize_image(image, censor_nsfw=True)
        self.assertEqual(url, 'https://example.com/a.jpg?blur=600&px=32')
