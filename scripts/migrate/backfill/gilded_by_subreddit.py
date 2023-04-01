from collections import defaultdict
from datetime import datetime

from pylons import app_globals as g

from r2.lib.db.operators import desc
from r2.lib.utils import fetch_things2
from r2.models import (
    calculate_server_seconds,
    Comment,
    Link,
    Subreddit,
)

LINK_GILDING_START = datetime(2014, 2, 1, 0, 0, tzinfo=g.tz)
COMMENT_GILDING_START = datetime(2012, 10, 1, 0, 0, tzinfo=g.tz)

queries = [
    Link._query(
        Link.c.gildings != 0, Link.c._date > LINK_GILDING_START, data=True,
        sort=desc('_date'),
    ),
    Comment._query(
        Comment.c.gildings != 0, Comment.c._date > COMMENT_GILDING_START,
        data=True, sort=desc('_date'),
    ),
]

seconds_by_srid = defaultdict(int)
gilding_price = g.gold_month_price.pennies

for q in queries:
    for things in fetch_things2(q, chunks=True, chunk_size=100):
        print things[0]._fullname

        for thing in things:
            seconds_per_gilding = calculate_server_seconds(gilding_price, thing._date)
            seconds_by_srid[thing.sr_id] += int(thing.gildings * seconds_per_gilding)

for sr_id, seconds in seconds_by_srid:
    sr = Subreddit._byID(sr_id, data=True)
    print "%s: %s seconds" % (sr.name, seconds)
    sr._incr("gilding_server_seconds", seconds)
