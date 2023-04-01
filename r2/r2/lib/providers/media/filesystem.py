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

import os
import shutil
import urlparse

from pylons import app_globals as g

from r2.lib.configparse import ConfigValue
from r2.lib.providers.media import MediaProvider


class FileSystemMediaProvider(MediaProvider):
    """A simple media provider that writes to the filesystem.

    It is assumed that an external HTTP server will take care of serving the
    media objects once written.

    `media_fs_root` is the root directory on the filesystem to write the objects
    into.

    `media_fs_base_url_http` is the base URL on which to find the media
    objects. It should be an absolute URL to the root directory of the media
    object server that is accessible via both HTTP and HTTPS.

    """
    config = {
        ConfigValue.str: [
            "media_fs_root",
            "media_fs_base_url_http",
        ],
    }

    def make_inaccessible(self, url):
        # When it comes to file system, there isn't really the concept of
        # "making a file inaccessible" separate from deletion without
        # losing track of it. For the sake of not creating orphaned files, 
        # not implementing this method
        g.log.warning(
            'FileSystemMediaProvider.make_inaccessible is consciously '
            'not implemented and does not raise an error.'
        )
        return True

    def put(self, category, name, contents, headers=None):
        assert os.path.dirname(name) == ""
        path = os.path.join(g.media_fs_root, name)
        with open(path, "w") as f:
            if isinstance(contents, basestring):
                f.write(contents)
            else:
                shutil.copyfileobj(contents, f)
        return urlparse.urljoin(g.media_fs_base_url_http, name)
        
    def purge(self, url):
        """Remove the content from disk. Content can not be recovered."""

        name = url.split('/')[-1]
        path = os.path.join(g.media_fs_root, name)
        os.remove(path)
        return True
