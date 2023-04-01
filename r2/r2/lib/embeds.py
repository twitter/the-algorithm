from datetime import datetime
import math
from pylons import request
from pylons import tmpl_context as c
from pylons import app_globals as g
from pylons.controllers.util import abort
import pytz

from r2.controllers.reddit_base import UnloggedUser
from r2.lib import js
from r2.models import Account, NotFound
from r2.models.subreddit import Subreddit

# Note: This template is shared between python and javascript. See underscore
# templating in embed.js for more info. (Specific note: Only %()s is supported
# presently to use underscore templating.)
_COMMENT_EMBED_TEMPLATE = (
    '<div class="reddit-embed" '
        'data-embed-media="%(media)s" '
        'data-embed-parent="%(parent)s" '
        'data-embed-live="%(live)s" '
        'data-embed-created="%(created)s">'
        '<a href="%(comment)s">Comment</a> '
        'from discussion '
        '<a href="%(link)s">%(title)s</a>.'
    '</div>'
)


def get_inject_template(omitscript=False):
    template = _COMMENT_EMBED_TEMPLATE
    if not omitscript:
        script_urls = js.src("comment-embed", absolute=True, mangle_name=False)
        scripts = "".join('<script%s src="%s"></script>' % (
            ' async' if len(script_urls) == 1 else '',
            script_url
        ) for script_url in script_urls)
        template += scripts
    return template


def edited_after(thing, iso_timestamp, showedits):
    if not thing:
        return False

    if not isinstance(getattr(thing, "editted", False), datetime):
        return False

    try:
        created = datetime.strptime(iso_timestamp, "%Y-%m-%dT%H:%M:%S.%fZ")
    except ValueError:
        return not showedits

    created = created.replace(tzinfo=pytz.utc)

    return created < thing.editted


def prepare_embed_request():
    """Given a request, determine if we are embedding. If so, prepare the
    request for embedding.

    Returns the value of the embed GET parameter.
    """
    is_embed = request.GET.get('embed')

    if not is_embed:
        return None

    if request.host != g.media_domain:
        # don't serve up untrusted content except on our
        # specifically untrusted domain
        abort(404)

    c.allow_framing = True

    return is_embed


def set_up_comment_embed(sr, thing, showedits):
    try:
        author = Account._byID(thing.author_id) if thing.author_id else None
    except NotFound:
        author = None

    iso_timestamp = request.GET.get("created", "")

    c.embed_config = {
        "eventtracker_url": g.eventtracker_url or "",
        "anon_eventtracker_url": g.anon_eventtracker_url or "",
        "event_clicktracker_url": g.event_clicktracker_url or "",
        "created": iso_timestamp,
        "showedits": showedits,
        "thing": {
            "id": thing._id,
            "sr_id": sr._id,
            "sr_name": sr.name,
            "edited": edited_after(thing, iso_timestamp, showedits),
            "deleted": thing.deleted or author._deleted,
        },
        "comment_max_height": 200,
    }

    c.render_style = "iframe"
    c.user = UnloggedUser([c.lang])
    c.user_is_loggedin = False
    c.forced_loggedout = True


def is_embed():
    return c.render_style == "iframe"
