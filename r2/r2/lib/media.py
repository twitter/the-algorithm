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

import sys

import base64
import cStringIO
import hashlib
import json
import math
import os
import re
import subprocess
import tempfile
import traceback
import urllib
import urllib2
import urlparse
import gzip

import BeautifulSoup
from PIL import Image, ImageFile
import lxml.html
import requests

from pylons import app_globals as g

from r2 import models
from r2.config import feature
from r2.lib import amqp, hooks
from r2.lib.db.tdb_cassandra import NotFound
from r2.lib.memoize import memoize
from r2.lib.nymph import optimize_png
from r2.lib.template_helpers import format_html
from r2.lib.utils import (
    TimeoutFunction,
    TimeoutFunctionException,
    UrlParser,
    coerce_url_to_protocol,
    domain,
    extract_urls_from_markdown,
    get_requests_resp_json,
    is_subdomain,
)
from r2.models.link import Link
from r2.models.media_cache import (
    ERROR_MEDIA,
    Media,
    MediaByURL,
)
from urllib2 import (
    HTTPError,
    URLError,
)

_IMAGE_PREVIEW_TEMPLATE = """
<img class="%(css_class)s" src="%(url)s" width="%(width)s" height="%(height)s">
"""


def _image_to_str(image):
    s = cStringIO.StringIO()
    image.save(s, image.format)
    return s.getvalue()


def str_to_image(s):
    s = cStringIO.StringIO(s)
    image = Image.open(s)
    return image


def _image_entropy(img):
    """calculate the entropy of an image"""
    hist = img.histogram()
    hist_size = sum(hist)
    hist = [float(h) / hist_size for h in hist]

    return -sum(p * math.log(p, 2) for p in hist if p != 0)


def _crop_image_vertically(img, target_height):
    """crop image vertically the the specified height. determine
    which pieces to cut off based on the entropy pieces."""
    x,y = img.size

    while y > target_height:
        #slice 10px at a time until square
        slice_height = min(y - target_height, 10)

        bottom = img.crop((0, y - slice_height, x, y))
        top = img.crop((0, 0, x, slice_height))

        #remove the slice with the least entropy
        if _image_entropy(bottom) < _image_entropy(top):
            img = img.crop((0, 0, x, y - slice_height))
        else:
            img = img.crop((0, slice_height, x, y))

        x,y = img.size

    return img


def _square_image(img):
    """if the image is taller than it is wide, square it off."""
    width = img.size[0]
    return _crop_image_vertically(img, width)


def _apply_exif_orientation(image):
    """Update the image's orientation if it has the relevant EXIF tag."""
    try:
        exif_tags = image._getexif() or {}
    except AttributeError:
        # image format with no EXIF tags
        return image

    # constant from EXIF spec
    ORIENTATION_TAG_ID = 0x0112
    orientation = exif_tags.get(ORIENTATION_TAG_ID)

    if orientation == 1:
        # 1 = Horizontal (normal)
        pass
    elif orientation == 2:
        # 2 = Mirror horizontal
        image = image.transpose(Image.FLIP_LEFT_RIGHT)
    elif orientation == 3:
        # 3 = Rotate 180
        image = image.transpose(Image.ROTATE_180)
    elif orientation == 4:
        # 4 = Mirror vertical
        image = image.transpose(Image.FLIP_TOP_BOTTOM)
    elif orientation == 5:
        # 5 = Mirror horizontal and rotate 90 CCW
        image = image.transpose(Image.FLIP_LEFT_RIGHT)
        image = image.transpose(Image.ROTATE_90)
    elif orientation == 6:
        # 6 = Rotate 270 CCW
        image = image.transpose(Image.ROTATE_270)
    elif orientation == 7:
        # 7 = Mirror horizontal and rotate 270 CCW
        image = image.transpose(Image.FLIP_LEFT_RIGHT)
        image = image.transpose(Image.ROTATE_270)
    elif orientation == 8:
        # 8 = Rotate 90 CCW
        image = image.transpose(Image.ROTATE_90)

    return image


def _prepare_image(image):
    image = _apply_exif_orientation(image)

    image = _square_image(image)

    if feature.is_enabled('hidpi_thumbnails'):
        hidpi_dims = [int(d * g.thumbnail_hidpi_scaling) for d in g.thumbnail_size]

        # If the image width is smaller than hidpi requires, set to non-hidpi
        if image.size[0] < hidpi_dims[0]:
            thumbnail_size = g.thumbnail_size
        else:
            thumbnail_size = hidpi_dims
    else:
        thumbnail_size = g.thumbnail_size

    image.thumbnail(thumbnail_size, Image.ANTIALIAS)
    return image


def _clean_url(url):
    """url quotes unicode data out of urls"""
    url = url.encode('utf8')
    url = ''.join(urllib.quote(c) if ord(c) >= 127 else c for c in url)
    return url


def _initialize_request(url, referer, gzip=False):
    url = _clean_url(url)

    if not url.startswith(("http://", "https://")):
        return

    req = urllib2.Request(url)
    if gzip:
        req.add_header('Accept-Encoding', 'gzip')
    if g.useragent:
        req.add_header('User-Agent', g.useragent)
    if referer:
        req.add_header('Referer', referer)
    return req


def _fetch_url(url, referer=None):
    request = _initialize_request(url, referer=referer, gzip=True)
    if not request:
        return None, None
    response = urllib2.urlopen(request)
    response_data = response.read()
    content_encoding = response.info().get("Content-Encoding")
    if content_encoding and content_encoding.lower() in ["gzip", "x-gzip"]:
        buf = cStringIO.StringIO(response_data)
        f = gzip.GzipFile(fileobj=buf)
        response_data = f.read()
    return response.headers.get("Content-Type"), response_data


@memoize('media.fetch_size', time=3600)
def _fetch_image_size(url, referer):
    """Return the size of an image by URL downloading as little as possible."""

    request = _initialize_request(url, referer)
    if not request:
        return None

    parser = ImageFile.Parser()
    response = None
    try:
        response = urllib2.urlopen(request)

        while True:
            chunk = response.read(1024)
            if not chunk:
                break

            parser.feed(chunk)
            if parser.image:
                return parser.image.size
    except urllib2.URLError:
        return None
    finally:
        if response:
            response.close()


def optimize_jpeg(filename):
    with open(os.path.devnull, 'w') as devnull:
        subprocess.check_call(("/usr/bin/jpegoptim", filename), stdout=devnull)


def thumbnail_url(link):
    """Given a link, returns the url for its thumbnail based on its fullname"""
    if link.has_thumbnail:
        if hasattr(link, "thumbnail_url"):
            return link.thumbnail_url
        else:
            return ''
    else:
        return ''


def _filename_from_content(contents):
    hash_bytes = hashlib.sha256(contents).digest()
    return base64.urlsafe_b64encode(hash_bytes).rstrip("=")


def upload_media(image, file_type='.jpg', category='thumbs'):
    """Upload an image to the media provider."""
    f = tempfile.NamedTemporaryFile(suffix=file_type, delete=False)
    try:
        img = image
        do_convert = True
        if isinstance(img, basestring):
            img = str_to_image(img)
            if img.format == "PNG" and file_type == ".png":
                img.verify()
                f.write(image)
                f.close()
                do_convert = False

        if do_convert:
            img = img.convert('RGBA')
            if file_type == ".jpg":
                # PIL does not play nice when converting alpha channels to jpg
                background = Image.new('RGBA', img.size, (255, 255, 255))
                background.paste(img, img)
                img = background.convert('RGB')
                img.save(f, quality=85) # Bug in the JPG encoder with the optimize flag, even if set to false
            else:
                img.save(f, optimize=True)

        if file_type == ".png":
            optimize_png(f.name)
        elif file_type == ".jpg":
            optimize_jpeg(f.name)
        contents = open(f.name).read()
        file_name = _filename_from_content(contents) + file_type
        return g.media_provider.put(category, file_name, contents)
    finally:
        os.unlink(f.name)
    return ""


def upload_stylesheet(content):
    file_name = _filename_from_content(content) + ".css"
    return g.media_provider.put('stylesheets', file_name, content)


def _scrape_media(url, autoplay=False, maxwidth=600, force=False,
                  save_thumbnail=True, use_cache=False, max_cache_age=None,
                  use_youtube_scraper=False):
    media = None
    autoplay = bool(autoplay)
    maxwidth = int(maxwidth)

    # Use media from the cache (if available)
    if not force and use_cache:
        mediaByURL = MediaByURL.get(url,
                                    autoplay=autoplay,
                                    maxwidth=maxwidth,
                                    max_cache_age=max_cache_age)
        if mediaByURL:
            media = mediaByURL.media

    # Otherwise, scrape it if thumbnail is not present
    if not media or not media.thumbnail_url:
        media_object = secure_media_object = None
        thumbnail_image = thumbnail_url = thumbnail_size = None

        scraper = Scraper.for_url(url, autoplay=autoplay,
                                  use_youtube_scraper=use_youtube_scraper)
        try:
            thumbnail_image, preview_object, media_object, secure_media_object = (
                scraper.scrape())
        except (HTTPError, URLError) as e:
            if use_cache:
                MediaByURL.add_error(url, str(e),
                                     autoplay=autoplay,
                                     maxwidth=maxwidth)
            return None

        # the scraper should be able to make a media embed out of the
        # media object it just gave us. if not, null out the media object
        # to protect downstream code
        if media_object and not scraper.media_embed(media_object):
            print "%s made a bad media obj for url %s" % (scraper, url)
            media_object = None

        if (secure_media_object and
            not scraper.media_embed(secure_media_object)):
            print "%s made a bad secure media obj for url %s" % (scraper, url)
            secure_media_object = None

        # If thumbnail can't be found, attempt again using _ThumbnailOnlyScraper
        # This should fix bugs that occur when embed.ly caches links before the 
        # thumbnail is available
        if (not thumbnail_image and 
                not isinstance(scraper, _ThumbnailOnlyScraper)):
            scraper = _ThumbnailOnlyScraper(url)
            try:
                thumbnail_image, preview_object, _, _ = scraper.scrape()
            except (HTTPError, URLError) as e:
                use_cache = False

        if thumbnail_image and save_thumbnail:
            thumbnail_size = thumbnail_image.size
            thumbnail_url = upload_media(thumbnail_image)
        else:
            # don't cache if thumbnail is absent
            use_cache = False

        media = Media(media_object, secure_media_object, preview_object,
                      thumbnail_url, thumbnail_size)

    if use_cache and save_thumbnail and media is not ERROR_MEDIA:
        # Store the media in the cache, possibly extending the ttl
        MediaByURL.add(url,
                       media,
                       autoplay=autoplay,
                       maxwidth=maxwidth)

    return media


def _get_scrape_url(link):
    if not link.is_self:
        sr_name = link.subreddit_slow.name
        if not feature.is_enabled("imgur_gif_conversion", subreddit=sr_name):
            return link.url
        p = UrlParser(link.url)
        # If it's a gif link on imgur, replacing it with gifv should
        # give us the embedly friendly video url
        if is_subdomain(p.hostname, "imgur.com"):
            if p.path_extension().lower() == "gif":
                p.set_extension("gifv")
                return p.unparse()
        return link.url

    urls = extract_urls_from_markdown(link.selftext)
    second_choice = None
    for url in urls:
        p = UrlParser(url)
        if p.is_reddit_url():
            continue
        # If we don't find anything we like better, use the first image.
        if not second_choice:
            second_choice = url
        # This is an optimization for "proof images" in AMAs.
        if is_subdomain(p.netloc, 'imgur.com') or p.has_image_extension():
            return url

    return second_choice


def _set_media(link, force=False, **kwargs):
    sr = link.subreddit_slow
    
    # Do not process thumbnails for quarantined subreddits
    if sr.quarantine:
        return

    if not link.is_self:
        if not force and (link.has_thumbnail or link.media_object):
            return

    if not force and link.promoted:
        return

    scrape_url = _get_scrape_url(link)

    if not scrape_url:
        if link.preview_object:
            # If the user edited out an image from a self post, we need to make
            # sure to remove its metadata.
            link.set_preview_object(None)
            link._commit()
        return

    youtube_scraper = feature.is_enabled("youtube_scraper", subreddit=sr.name)
    media = _scrape_media(scrape_url, force=force,
                          use_youtube_scraper=youtube_scraper, **kwargs)

    if media and not link.promoted:
        # While we want to add preview images to self posts for the new apps,
        # let's not muck about with the old-style thumbnails in case that
        # breaks assumptions.
        if not link.is_self:
            link.thumbnail_url = media.thumbnail_url
            link.thumbnail_size = media.thumbnail_size

            link.set_media_object(media.media_object)
            link.set_secure_media_object(media.secure_media_object)
        link.set_preview_object(media.preview_object)

        link._commit()

        hooks.get_hook("scraper.set_media").call(link=link)

        if media.media_object or media.secure_media_object:
            amqp.add_item("new_media_embed", link._fullname)


def force_thumbnail(link, image_data, file_type=".jpg"):
    image = str_to_image(image_data)
    image = _prepare_image(image)
    thumb_url = upload_media(image, file_type=file_type)

    link.thumbnail_url = thumb_url
    link.thumbnail_size = image.size
    link._commit()


def force_mobile_ad_image(link, image_data, file_type=".jpg"):
    image = str_to_image(image_data)
    image_width = image.size[0]
    x,y = g.mobile_ad_image_size
    max_height = image_width * y / x
    image = _crop_image_vertically(image, max_height)
    image.thumbnail(g.mobile_ad_image_size, Image.ANTIALIAS)
    image_url = upload_media(image, file_type=file_type)

    link.mobile_ad_url = image_url
    link.mobile_ad_size = image.size
    link._commit()


def upload_icon(image_data, size):
    image = str_to_image(image_data)
    image.format = 'PNG'
    image.thumbnail(size, Image.ANTIALIAS)
    icon_data = _image_to_str(image)
    file_name = _filename_from_content(icon_data)
    return g.media_provider.put('icons', file_name + ".png", icon_data)


def allowed_media_preview_url(url):
    p = UrlParser(url)
    if p.has_static_image_extension():
        return True
    for allowed_domain in g.media_preview_domain_whitelist:
        if is_subdomain(p.hostname, allowed_domain):
            return True
    return False


def get_preview_image(preview_object, include_censored=False):
    """Returns a media_object for rendering a media preview image"""
    min_width, min_height = g.preview_image_min_size
    max_width, max_height = g.preview_image_max_size
    source_width = preview_object['width']
    source_height = preview_object['height']

    if source_width <= max_width and source_height <= max_height:
        width = source_width
        height = source_height
    else:
        max_ratio = float(max_height) / max_width
        source_ratio = float(source_height) / source_width
        if source_ratio >= max_ratio:
            height = max_height
            width = int((height * source_width) / source_height)
        else:
            width = max_width
            height = int((width * source_height) / source_width)

    if width < min_width and height < min_height:
        return None

    url = g.image_resizing_provider.resize_image(preview_object, width)
    img_html = format_html(
        _IMAGE_PREVIEW_TEMPLATE,
        css_class="preview",
        url=url,
        width=width,
        height=height,
    )

    if include_censored:
        censored_url = g.image_resizing_provider.resize_image(
            preview_object,
            width,
            censor_nsfw=True,
        )
        censored_img_html = format_html(
            _IMAGE_PREVIEW_TEMPLATE,
            css_class="censored-preview",
            url=censored_url,
            width=width,
            height=height,
        )
        img_html += censored_img_html

    media_object = {
        "type": "media-preview",
        "width": width,
        "height": height,
        "content": img_html,
    }

    return media_object


def _make_custom_media_embed(media_object):
    # this is for promoted links with custom media embeds.
    return MediaEmbed(
        height=media_object.get("height"),
        width=media_object.get("width"),
        content=media_object.get("content"),
    )


def get_media_embed(media_object):
    if not isinstance(media_object, dict):
        return

    embed_hook = hooks.get_hook("scraper.media_embed")
    media_embed = embed_hook.call_until_return(media_object=media_object)
    if media_embed:
        return media_embed

    if media_object.get("type") == "custom":
        return _make_custom_media_embed(media_object)

    if "oembed" in media_object:
        if media_object.get("type") == "youtube.com":
            return _YouTubeScraper.media_embed(media_object)

        return _EmbedlyScraper.media_embed(media_object)


class MediaEmbed(object):
    """A MediaEmbed holds data relevant for serving media for an object."""

    width = None
    height = None
    content = None
    scrolling = False

    def __init__(self, height, width, content, scrolling=False,
                 public_thumbnail_url=None, sandbox=True):
        """Build a MediaEmbed.

        :param height int - The height of the media embed, in pixels
        :param width int - The width of the media embed, in pixels
        :param content string - The content of the media embed - HTML.
        :param scrolling bool - Whether the media embed should scroll or not.
        :param public_thumbnail_url string - The URL of the most representative
            thumbnail for this media. This may be on an uncontrolled domain,
            and is not necessarily our own thumbs domain (and should not be
            served to browsers).
        :param sandbox bool - True if the content should be sandboxed
            in an iframe on the media domain.
        """

        self.height = int(height)
        self.width = int(width)
        self.content = content
        self.scrolling = scrolling
        self.public_thumbnail_url = public_thumbnail_url
        self.sandbox = sandbox


class Scraper(object):
    @classmethod
    def for_url(cls, url, autoplay=False, maxwidth=600, use_youtube_scraper=False):
        scraper = hooks.get_hook("scraper.factory").call_until_return(url=url)
        if scraper:
            return scraper

        if use_youtube_scraper and _YouTubeScraper.matches(url):
            return _YouTubeScraper(url, maxwidth=maxwidth)

        embedly_services = _fetch_embedly_services()
        for service_re in embedly_services:
            if service_re.match(url):
                return _EmbedlyScraper(url,
                                       autoplay=autoplay,
                                       maxwidth=maxwidth)

        return _ThumbnailOnlyScraper(url)

    def scrape(self):
        # should return a 4-tuple of:
        #     thumbnail, preview_object, media_object, secure_media_obj
        raise NotImplementedError

    @classmethod
    def media_embed(cls, media_object):
        # should take a media object and return an appropriate MediaEmbed
        raise NotImplementedError


class _ThumbnailOnlyScraper(Scraper):
    def __init__(self, url):
        self.url = url
        # Having the source document's protocol on hand makes it easier to deal
        # with protocol-relative urls we extract from it.
        self.protocol = UrlParser(url).scheme

    def scrape(self):
        thumbnail_url, image_data = self._find_thumbnail_image()
        if not thumbnail_url:
            return None, None, None, None

        # When isolated from the context of a webpage, protocol-relative URLs
        # are ambiguous, so let's absolutify them now.
        if thumbnail_url.startswith('//'):
            thumbnail_url = coerce_url_to_protocol(thumbnail_url, self.protocol)

        if not image_data:
            _, image_data = _fetch_url(thumbnail_url, referer=self.url)

        if not image_data:
            return None, None, None, None

        uid = _filename_from_content(image_data)
        image = str_to_image(image_data)
        storage_url = upload_media(image, category='previews')
        width, height = image.size
        preview_object = {
            'uid': uid,
            'url': storage_url,
            'width': width,
            'height': height,
        }

        thumbnail = _prepare_image(image)

        return thumbnail, preview_object, None, None

    def _extract_image_urls(self, soup):
        for img in soup.findAll("img", src=True):
            yield urlparse.urljoin(self.url, img["src"])

    def _find_thumbnail_image(self):
        """Find what we think is the best thumbnail image for a link.

        Returns a 2-tuple of image url and, as an optimization, the raw image
        data.  A value of None for the former means we couldn't find an image;
        None for the latter just means we haven't already fetched the image.
        """
        content_type, content = _fetch_url(self.url)

        # if it's an image, it's pretty easy to guess what we should thumbnail.
        if content_type and "image" in content_type and content:
            return self.url, content

        if content_type and "html" in content_type and content:
            soup = BeautifulSoup.BeautifulSoup(content)
        else:
            return None, None

        # Allow the content author to specify the thumbnail using the Open
        # Graph protocol: http://ogp.me/
        og_image = (soup.find('meta', property='og:image') or
                    soup.find('meta', attrs={'name': 'og:image'}))
        if og_image and og_image.get('content'):
            return og_image['content'], None
        og_image = (soup.find('meta', property='og:image:url') or
                    soup.find('meta', attrs={'name': 'og:image:url'}))
        if og_image and og_image.get('content'):
            return og_image['content'], None

        # <link rel="image_src" href="http://...">
        thumbnail_spec = soup.find('link', rel='image_src')
        if thumbnail_spec and thumbnail_spec['href']:
            return thumbnail_spec['href'], None

        # ok, we have no guidance from the author. look for the largest
        # image on the page with a few caveats. (see below)
        max_area = 0
        max_url = None
        for image_url in self._extract_image_urls(soup):
            # When isolated from the context of a webpage, protocol-relative
            # URLs are ambiguous, so let's absolutify them now.
            if image_url.startswith('//'):
                image_url = coerce_url_to_protocol(image_url, self.protocol)
            size = _fetch_image_size(image_url, referer=self.url)
            if not size:
                continue

            area = size[0] * size[1]

            # ignore little images
            if area < 5000:
                g.log.debug('ignore little %s' % image_url)
                continue

            # ignore excessively long/wide images
            if max(size) / min(size) > 1.5:
                g.log.debug('ignore dimensions %s' % image_url)
                continue

            # penalize images with "sprite" in their name
            if 'sprite' in image_url.lower():
                g.log.debug('penalizing sprite %s' % image_url)
                area /= 10

            if area > max_area:
                max_area = area
                max_url = image_url

        return max_url, None


class _EmbedlyScraper(Scraper):
    """Use Embedly to get information about embed info for a url.

    http://embed.ly/docs/api/embed/endpoints/1/oembed
    """
    EMBEDLY_API_URL = "https://api.embed.ly/1/oembed"

    def __init__(self, url, autoplay=False, maxwidth=600):
        self.url = url
        self.maxwidth = int(maxwidth)
        self.embedly_params = {}

        if autoplay:
            self.embedly_params["autoplay"] = "true"

    def _fetch_from_embedly(self, secure):
        param_dict = {
            "url": self.url,
            "format": "json",
            "maxwidth": self.maxwidth,
            "key": g.embedly_api_key,
            "secure": "true" if secure else "false",
        }

        param_dict.update(self.embedly_params)
        params = urllib.urlencode(param_dict)

        timer = g.stats.get_timer("providers.embedly.oembed")
        timer.start()
        content = requests.get(self.EMBEDLY_API_URL + "?" + params).content
        timer.stop()

        return json.loads(content)

    def _make_media_object(self, oembed):
        if oembed.get("type") in ("video", "rich"):
            return {
                "type": domain(self.url),
                "oembed": oembed,
            }
        return None

    def scrape(self):
        oembed = self._fetch_from_embedly(secure=False)
        if not oembed:
            return None, None, None, None

        if oembed.get("type") == "photo":
            thumbnail_url = oembed.get("url")
        else:
            thumbnail_url = oembed.get("thumbnail_url")
        if not thumbnail_url:
            return None, None, None, None

        content_type, content = _fetch_url(thumbnail_url, referer=self.url)
        uid = _filename_from_content(content)
        image = str_to_image(content)
        storage_url = upload_media(image, category='previews')
        width, height = image.size
        preview_object = {
            'uid': uid,
            'url': storage_url,
            'width': width,
            'height': height,
        }

        thumbnail = _prepare_image(image)

        secure_oembed = self._fetch_from_embedly(secure=True)
        if not self.validate_secure_oembed(secure_oembed):
            secure_oembed = {}

        return (
            thumbnail,
            preview_object,
            self._make_media_object(oembed),
            self._make_media_object(secure_oembed),
        )

    def validate_secure_oembed(self, oembed):
        """Check the "secure" embed is safe to embed, and not a placeholder"""
        if not oembed.get("html"):
            return False

        # Get the embed.ly iframe's src
        iframe_src = lxml.html.fromstring(oembed['html']).get('src')
        if not iframe_src:
            return False
        iframe_src_url = UrlParser(iframe_src)

        # Per embed.ly support: If the URL for the provider is HTTP, we're
        # gonna get a placeholder image instead
        provider_src_url = UrlParser(iframe_src_url.query_dict.get('src'))
        return not provider_src_url.scheme or provider_src_url.scheme == "https"

    @classmethod
    def media_embed(cls, media_object):
        oembed = media_object["oembed"]

        html = oembed.get("html")
        width = oembed.get("width")
        height = oembed.get("height")
        public_thumbnail_url = oembed.get('thumbnail_url')
        if not (html and width and height):
            return

        return MediaEmbed(
            width=width,
            height=height,
            content=html,
            public_thumbnail_url=public_thumbnail_url,
        )


class _YouTubeScraper(Scraper):
    OEMBED_ENDPOINT = "https://www.youtube.com/oembed"
    URL_MATCH = re.compile(r"https?://((www\.)?youtube\.com/watch|youtu\.be/)")

    def __init__(self, url, maxwidth):
        self.url = url
        self.maxwidth = maxwidth

    @classmethod
    def matches(cls, url):
        return cls.URL_MATCH.match(url)

    def _fetch_from_youtube(self):
        params = {
            "url": self.url,
            "format": "json",
            "maxwidth": self.maxwidth,
        }

        with g.stats.get_timer("providers.youtube.oembed"):
            content = requests.get(self.OEMBED_ENDPOINT, params=params).content

        return json.loads(content)

    def _make_media_object(self, oembed):
        if oembed.get("type") == "video":
            return {
                "type": "youtube.com",
                "oembed": oembed,
            }
        return None

    def scrape(self):
        oembed = self._fetch_from_youtube()
        if not oembed:
            return None, None, None, None
        thumbnail_url = oembed.get("thumbnail_url")

        if not thumbnail_url:
            return None, None, None, None

        _, content = _fetch_url(thumbnail_url, referer=self.url)
        uid = _filename_from_content(content)
        image = str_to_image(content)
        storage_url = upload_media(image, category='previews')
        width, height = image.size
        preview_object = {
            'uid': uid,
            'url': storage_url,
            'width': width,
            'height': height,
        }

        thumbnail = _prepare_image(image)
        media_object = self._make_media_object(oembed)

        return (
            thumbnail,
            preview_object,
            media_object,
            media_object,
        )

    @classmethod
    def media_embed(cls, media_object):
        oembed = media_object["oembed"]

        html = oembed.get("html")
        width = oembed.get("width")
        height = oembed.get("height")
        public_thumbnail_url = oembed.get('thumbnail_url')

        if not (html and width and height):
            return

        return MediaEmbed(
            width=width,
            height=height,
            content=html,
            public_thumbnail_url=public_thumbnail_url,
        )


@memoize("media.embedly_services2", time=3600)
def _fetch_embedly_service_data():
    resp = requests.get("https://api.embed.ly/1/services/python")
    return get_requests_resp_json(resp)


def _fetch_embedly_services():
    if not g.embedly_api_key:
        if g.debug:
            g.log.info("No embedly_api_key, using no key while in debug mode.")
        else:
            g.log.warning("No embedly_api_key configured. Will not use "
                          "embed.ly.")
            return []

    service_data = _fetch_embedly_service_data()

    return [
        re.compile("(?:%s)" % "|".join(service["regex"]))
        for service in service_data
    ]


def run():
    @g.stats.amqp_processor('scraper_q')
    def process_link(msg):
        fname = msg.body
        link = Link._by_fullname(fname, data=True)

        try:
            TimeoutFunction(_set_media, 30)(link, use_cache=True)
        except TimeoutFunctionException:
            print "Timed out on %s" % fname
        except KeyboardInterrupt:
            raise
        except:
            print "Error fetching %s" % fname
            print traceback.format_exc()

    amqp.consume_items('scraper_q', process_link)
