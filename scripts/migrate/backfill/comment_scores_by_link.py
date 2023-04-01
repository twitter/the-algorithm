from datetime import datetime

from pylons import app_globals as g

from r2.models import (
    CommentSortsCache,
    CommentScoresByLink,
)

# estimate 93,233,408 rows in CommentSortsCache


def run():
    start_time = datetime.now(g.tz)
    epoch_micro_seconds = int(start_time.strftime("%s")) * 1000000
    count = 0

    for rowkey, columns in CommentSortsCache._cf.get_range(column_count=1000):
        # CommentSortsCache rowkey is "{linkid36}{sort}"
        # CommentScoresByLink rowkey is the same

        if len(columns) == 1000:
            columns = CommentSortsCache._cf.xget(rowkey)
            # convert str column values to floats
            float_columns = {k: float(v) for k, v in columns}
        else:
            # convert str column values to floats
            float_columns = {k: float(v) for k, v in columns.iteritems()}

        # write with a timestamp to not overwrite any writes since our read
        CommentScoresByLink._cf.insert(
            rowkey, float_columns, timestamp=epoch_micro_seconds)

        count += 1
        if count % 1000 == 0:
            print "processed %s rows, last seen was %s" % (count, rowkey)
