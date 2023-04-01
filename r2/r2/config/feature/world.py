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

from pylons import request
from pylons import tmpl_context as c
from pylons import app_globals as g


class World(object):
    """A World is the proxy to the app/request state for Features.

    Proxying through World allows for easy testing and caching if needed.
    """

    @staticmethod
    def stacked_proxy_safe_get(stacked_proxy, key, default=None):
        """Get a field from a StackedObjectProxy

        Always succeeds, even if the proxy has not yet been initialized.
        Normally, if the proxy hasn't been initialized, a `TypeError` is
        raised to indicate a programming error. To avoid crashing on feature
        checks that are done too early (e.g., during initial DB set-up of
        the pylons environment), this function will instead return `default`
        for an uninitialized proxy.

        (Initialized proxies ALWAYS return a value, either a set value
        or an empty string)

        """
        try:
            return getattr(stacked_proxy, key)
        except TypeError:
            return default

    def current_user(self):
        if c.user_is_loggedin:
            return self.stacked_proxy_safe_get(c, 'user')

    def current_subreddit(self):
        site = self.stacked_proxy_safe_get(c, 'site')
        if not site:
            # In non-request code (eg queued jobs), there isn't necessarily a
            # site name (or other request-type data).  In those cases, we don't
            # want to trigger any subreddit-specific code.
            return ''
        return site.name

    def current_subdomain(self):
        return self.stacked_proxy_safe_get(c, 'subdomain')

    def current_oauth_client(self):
        client = self.stacked_proxy_safe_get(c, 'oauth2_client', None)
        return getattr(client, '_id', None)

    def current_loid_obj(self):
        return self.stacked_proxy_safe_get(c, 'loid')

    def current_loid(self):
        loid = self.current_loid_obj()
        if not loid:
            return None
        return loid.loid

    def is_admin(self, user):
        if not user or not hasattr(user, 'name'):
            return False

        return user.name in self.stacked_proxy_safe_get(g, 'admins', [])

    def is_employee(self, user):
        if not user:
            return False
        return user.employee

    def user_has_beta_enabled(self, user):
        if not user:
            return False
        return user.pref_beta

    def has_gold(self, user):
        if not user:
            return False

        return user.gold

    def is_user_loggedin(self, user):
        if not (user or self.current_user()):
            return False
        return True

    def url_features(self):
        return set(request.GET.getall('feature'))

    def live_config(self, name):
        live = self.stacked_proxy_safe_get(g, 'live_config', {})
        return live.get(name)

    def live_config_iteritems(self):
        live = self.stacked_proxy_safe_get(g, 'live_config', {})
        return live.iteritems()

    def simple_event(self, name):
        stats = self.stacked_proxy_safe_get(g, 'stats', None)
        if stats:
            return stats.simple_event(name)
