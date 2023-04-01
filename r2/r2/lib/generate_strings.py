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


funny_translatable_strings = {
    "500_page": ["Funny 500 page message %d" % i for i in xrange(1, 11)],
    "create_subreddit": [
        "Reason to create a reddit %d" % i for i in xrange(1, 21)],
}

def generate_strings():
    """Print out automatically generated strings for translation."""

    # used by error pages and in the sidebar for why to create a subreddit
    for category, strings in funny_translatable_strings.iteritems():
        for string in strings:
            print "# TRANSLATORS: Do not translate literally. Come up with a funny/relevant phrase (see the English version for ideas.) Accepts markdown formatting."
            print "print _('" + string + "')"

    # these are used in r2.lib.pages.trafficpages
    INTERVALS = ("hour", "day", "month")
    TYPES = ("uniques", "pageviews", "traffic", "impressions", "clicks")
    for interval in INTERVALS:
        for type in TYPES:
            print "print _('%s by %s')" % (type, interval)


if __name__ == "__main__":
    generate_strings()
