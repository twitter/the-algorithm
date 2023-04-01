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


"""Create exhaustive sitemaps for Reddit.

This module exists to make fairly exhaustive sitemaps as defined by the
sitemap protocol (http://www.sitemaps.org/protocol.html)

We currently support two types of sitemaps:

The sitemap index which takes the form of:

------------------------------------------------------------------------
<?xml version="1.0" encoding="UTF-8"?>
<sitemapindex xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
  <sitemap>
    <loc>http://reddit.com/r/subreddit_sitemap?index=0</loc>
  </sitemap>
  <sitemap>
    <loc>http://reddit.com/r/subreddit_sitemap?index=1</loc>
  </sitemap>
  <sitemap>
    <loc>http://reddit.com/r/permalink_sitemap?index=0</loc>
  </sitemap>
  <sitemap>
    <loc>http://reddit.com/r/permalink_sitemap?index=1</loc>
  </sitemap>
</sitemapindex>
------------------------------------------------------------------------

Next are subreddit sitemaps which take the form of:

------------------------------------------------------------------------
 <?xml version="1.0" encoding="UTF-8"?>
 <urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
   <url>
     <loc>http://reddit.com/r/{some_postfix}</loc>
   </url>
 </urlset>
------------------------------------------------------------------------


Each sitemap and sitemap index will have 50000 links or fewer.
"""

from lxml import etree
from pylons import app_globals as g

from r2.lib.template_helpers import add_sr
from r2.lib.utils import in_chunks

SITEMAP_NAMESPACE = "http://www.sitemaps.org/schemas/sitemap/0.9"
LINKS_PER_SITEMAP = 50000


def _absolute_url(path):
    return add_sr(path, force_https=True, sr_path=False)


def _stringify_xml(root_element):
    return etree.tostring(
        root_element,
        pretty_print=g.debug,
        xml_declaration=True,
        encoding='UTF-8'
    )


def _subreddit_links(subreddits):
    for subreddit in subreddits:
        path = '/r/{0}/'.format(subreddit)
        yield _absolute_url(path)


def _subreddit_sitemap(subreddits):
    urlset = etree.Element('urlset', xmlns=SITEMAP_NAMESPACE)
    for link in _subreddit_links(subreddits):
        url_elem = etree.SubElement(urlset, 'url')
        loc_elem = etree.SubElement(url_elem, 'loc')
        loc_elem.text = link
    return _stringify_xml(urlset)


def subreddit_sitemaps(subreddits):
    """Create an array of sitemaps.

    Each sitemap has up to 50000 links, being the maximum allowable number of
    links according to the sitemap standard.
    """
    for subreddit_chunks in in_chunks(subreddits, LINKS_PER_SITEMAP):
        yield _subreddit_sitemap(subreddit_chunks)


def sitemap_index(count):
    sm_elem = etree.Element('sitemapindex', xmlns=SITEMAP_NAMESPACE)
    for i in xrange(count):
        sitemap_elem = etree.SubElement(sm_elem, 'sitemap')
        loc_elem = etree.SubElement(sitemap_elem, 'loc')
        url = '{0}/subreddit_sitemap/{1}.xml'.format(
            g.sitemap_s3_static_host, i)
        loc_elem.text = url
    return _stringify_xml(sm_elem)
