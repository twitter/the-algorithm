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

from reddit_base import RedditController
import StringIO
import r2.lib.captcha as captcha
from pylons import response

from r2.controllers.api_docs import api_doc, api_section
from r2.controllers.oauth2 import allow_oauth2_access

class CaptchaController(RedditController):
    @allow_oauth2_access
    @api_doc(api_section.captcha, uri='/captcha/{iden}')
    def GET_captchaimg(self, iden):
        """
        Request a CAPTCHA image given an `iden`.

        An iden is given as the `captcha` field with a `BAD_CAPTCHA`
        error, you should use this endpoint if you get a
        `BAD_CAPTCHA` error response.

        Responds with a 120x50 `image/png` which should be displayed
        to the user.

        The user's response to the CAPTCHA should be sent as `captcha`
        along with your request.

        To request a new CAPTCHA,
        use [/api/new_captcha](#POST_api_new_captcha).
        """
        image = captcha.get_image(iden)
        f = StringIO.StringIO()
        image.save(f, "PNG")
        response.content_type = "image/png;"
        return f.getvalue()
    
