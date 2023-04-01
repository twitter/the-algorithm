import json
from collections import defaultdict
from datetime import datetime, timedelta

from pylons import app_globals as g

from r2.lib.db.sorts import epoch_seconds
from r2.lib.db.tdb_cassandra import write_consistency_level
from r2.lib.utils import in_chunks
from r2.models.vote import VoteDetailsByComment, VoteDetailsByLink, VoterIPByThing


def backfill_vote_details(cls):
    ninety_days = timedelta(days=90).total_seconds()
    for chunk in in_chunks(cls._all(), size=100):
        detail_chunk = defaultdict(dict)
        try:
            with VoterIPByThing._cf.batch(write_consistency_level=cls._write_consistency_level) as b:
                for vote_list in chunk:
                    thing_id36 = vote_list._id
                    thing_fullname = vote_list.votee_fullname
                    details = vote_list.decode_details()
                    for detail in details:
                        voter_id36 = detail["voter_id"]
                        if "ip" in detail and detail["ip"]:
                            ip = detail["ip"]
                            redacted = dict(detail)
                            del redacted["ip"]
                            cast = detail["date"]
                            now = epoch_seconds(datetime.utcnow().replace(tzinfo=g.tz))
                            ttl = ninety_days - (now - cast)
                            oneweek = ""
                            if ttl < 3600 * 24 * 7:
                                oneweek = "(<= one week left)"
                            print "Inserting %s with IP ttl %d %s" % (redacted, ttl, oneweek)
                            detail_chunk[thing_id36][voter_id36] = json.dumps(redacted)
                            if ttl <= 0:
                                print "Skipping bogus ttl for %s: %d" % (redacted, ttl)
                                continue
                            b.insert(thing_fullname, {voter_id36: ip}, ttl=ttl)
        except Exception:
            # Getting some really weird spurious errors here; complaints about negative
            # TTLs even though they can't possibly be negative, errors from cass
            # that have an explanation of "(why=')"
            # Just going to brute-force this through.  We might lose 100 here and there
            # but mostly it'll be intact.
            pass
        for votee_id36, valuedict in detail_chunk.iteritems():
            cls._set_values(votee_id36, valuedict)


def main():
    cfs = [VoteDetailsByComment, VoteDetailsByLink]
    for cf in cfs:
        backfill_vote_details(cf)

if __name__ == '__builtin__':
    main()
