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

class ImageResizingProvider(object):
    """Provider for generating resizable image urls.

    """
    def resize_image(self, image, width=None, censor_nsfw=False, max_ratio=None):
        """Turn a url of an image in storage into one that will produce a
        resized image.

        `image` must be a dictionary with keys:
            url: the storage url given by the media provider after uploading
            width: in pixels
            height: in pixels

        `width` is optionally a number of pixels wide for the resultant image;
        if not specified, the dimensions will be the same as the source image.

        `censor_nsfw` is a boolean indicating whether the resizer should
        attempt to censor the image (e.g. by blurring it) due to it being NSFW.

        `max_ratio` is the maximum value of the height of the resultant image
        divided by the width; if not specified, the aspect ratio will be the
        same as the source image.

        The return value should be an absolute URL with the `https` scheme if
        supported, but should also work if accessed with `http`.

        Throws NotLargeEnough if the source image is smaller than the requested
        width.
        """
        raise NotImplementedError

    def purge_url(self, url):
        """Purge an image (by url) from the provider.

        Providers should override and implement this method if they do
        something like keep a cache of resized versions that are
        requested. This will allow the cached version to be deleted, in
        cases where it needs to be re-generated or removed entirely.
        """
        pass


class NotLargeEnough(Exception): pass
