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

"""
Try to regenerate the permacache items devoted to listings after a
storage failure in Cassandra
"""

"""
cat > mr_permacache <<HERE
#!/bin/sh
cd ~/reddit/r2
paster run staging.ini ./mr_permacache.py -c "\$1"
HERE
chmod u+x mr_permacache

LINKDBHOST=prec01
COMMENTDBHOST=db02s1
VOTEDBHOST=db03s1
SAVEHIDEDBHOST=db01s1

## links
time psql -F"\t" -A -t -d newreddit -U ri -h $LINKDBHOST \
     -c "\\copy (select t.thing_id, 'thing', 'link',
                        t.ups, t.downs, t.deleted, t.spam, extract(epoch from t.date)
                   from reddit_thing_link t) to 'reddit_thing_link.dump'"
time psql -F"\t" -A -t -d newreddit -U ri -h $LINKDBHOST \
     -c "\\copy (select d.thing_id, 'data', 'link',
                        d.key, d.value
                   from reddit_data_link d
                  where d.key = 'author_id' or d.key = 'sr_id') to 'reddit_data_link.dump'"
pv reddit_data_link.dump reddit_thing_link.dump | sort -T. -S200m | ./mr_permacache "join_links()" > links.joined
pv links.joined | ./mr_permacache "link_listings()" | sort -T. -S200m > links.listings

## comments
psql -F"\t" -A -t -d newreddit -U ri -h $COMMENTDBHOST \
     -c "\\copy (select t.thing_id, 'thing', 'comment',
                        t.ups, t.downs, t.deleted, t.spam, extract(epoch from t.date)
                   from reddit_thing_comment t) to 'reddit_thing_comment.dump'"
psql -F"\t" -A -t -d newreddit -U ri -h $COMMENTDBHOST \
     -c "\\copy (select d.thing_id, 'data', 'comment',
                        d.key, d.value
                   from reddit_data_comment d
                  where d.key = 'author_id') to 'reddit_data_comment.dump'"
cat reddit_data_comment.dump reddit_thing_comment.dump | sort -T. -S200m | ./mr_permacache "join_comments()" > comments.joined
cat links.joined | ./mr_permacache "comment_listings()" | sort -T. -S200m > comments.listings

## linkvotes
psql -F"\t" -A -t -d newreddit -U ri -h $VOTEDBHOST \
     -c "\\copy (select r.rel_id, 'vote_account_link',
                        r.thing1_id, r.thing2_id, r.name, extract(epoch from r.date)
                   from reddit_rel_vote_account_link r) to 'reddit_linkvote.dump'"
pv reddit_linkvote.dump | ./mr_permacache "linkvote_listings()" | sort -T. -S200m > linkvotes.listings

#savehide
psql -F"\t" -A -t -d newreddit -U ri -h $SAVEHIDEDBHOST \
     -c "\\copy (select r.rel_id, 'savehide',
                        r.thing1_id, r.thing2_id, r.name, extract(epoch from r.date)
                   from reddit_rel_savehide r) to 'reddit_savehide.dump'"
pv reddit_savehide.dump | ./mr_permacache "savehide_listings()" | sort -T. -S200m > savehide.listings

## load them up
# the individual .listings files are sorted so even if it's not sorted
# overall we don't need to re-sort them
mkdir listings
pv *.listings | ./mr_permacache "top1k_writefiles('listings')"
./mr_permacache "write_permacache_from_dir('$PWD/listings')"

"""

import os, os.path, errno
import sys
import itertools
from hashlib import md5

from r2.lib import mr_tools
from r2.lib.mr_tools import dataspec_m_thing, dataspec_m_rel, join_things


from dateutil.parser import parse as parse_timestamp

from r2.models import *
from r2.lib.db.sorts import epoch_seconds, score, controversy, _hot
from r2.lib.utils import fetch_things2, in_chunks, progress, UniqueIterator, tup
from r2.lib import comment_tree
from r2.lib.db import queries

from r2.lib.jsontemplates import make_fullname # what a strange place
                                               # for this function

def join_links():
    join_things(('author_id', 'sr_id'))

def link_listings():
    @dataspec_m_thing(('author_id', int),
                      ('sr_id', int))
    def process(link):
        assert link.thing_type == 'link'

        author_id = link.author_id
        timestamp = link.timestamp
        fname = make_fullname(Link, link.thing_id)

        yield 'user-submitted-%d' % author_id, timestamp, fname
        if not link.spam:
            sr_id = link.sr_id
            ups, downs = link.ups, link.downs

            yield ('sr-hot-all-%d' % sr_id, _hot(ups, downs, timestamp),
                   timestamp, fname)
            yield 'sr-new-all-%d' % sr_id, timestamp, fname
            yield 'sr-top-all-%d' % sr_id, score(ups, downs), timestamp, fname
            yield ('sr-controversial-all-%d' % sr_id,
                   controversy(ups, downs), timestamp, fname)
            for time in '1 year', '1 month', '1 week', '1 day', '1 hour':
                if timestamp > epoch_seconds(timeago(time)):
                    tkey = time.split(' ')[1]
                    yield ('sr-top-%s-%d' % (tkey, sr_id),
                           score(ups, downs), timestamp, fname)
                    yield ('sr-controversial-%s-%d' % (tkey, sr_id),
                           controversy(ups, downs),
                           timestamp, fname)

    mr_tools.mr_map(process)

def join_comments():
    join_things(('author_id',))

def comment_listings():
    @dataspec_m_thing(('author_id', int),)
    def process(comment):
        assert comment.thing_type == 'comment'

        yield ('user-commented-%d' % comment.author_id,
               comment.timestamp, make_fullname(Comment, comment.thing_id))

    mr_tools.mr_map(process)

def rel_listings(names, thing2_cls = Link):
    # names examples: {'1': 'liked',
    #                  '-1': 'disliked'}
    @dataspec_m_rel()
    def process(rel):
        if rel.name in names:
            yield ('%s-%s' % (names[rel.name], rel.thing1_id), rel.timestamp,
                   make_fullname(thing2_cls, rel.thing2_id))
    mr_tools.mr_map(process)

def linkvote_listings():
    rel_listings({'1': 'liked',
                  '-1': 'disliked'})

def savehide_listings():
    rel_listings({'save': 'saved',
                  'hide': 'hidden'})

def insert_to_query(q, items):
    q._insert_tuples(items)

def store_keys(key, maxes):
    # we're building queries from queries.py, but we could avoid this
    # by making the queries ourselves if we wanted to avoid the
    # individual lookups for accounts and subreddits
    userrel_fns = dict(liked = queries.get_liked,
                       disliked = queries.get_disliked,
                       saved = queries.get_saved,
                       hidden = queries.get_hidden)
    if key.startswith('user-'):
        acc_str, keytype, account_id = key.split('-')
        account_id = int(account_id)
        fn = queries.get_submitted if keytype == 'submitted' else queries.get_comments
        q = fn(Account._byID(account_id), 'new', 'all')
        insert_to_query(q, [(fname, float(timestamp))
                            for (timestamp, fname)
                            in maxes ])
    elif key.startswith('sr-'):
        sr_str, sort, time, sr_id = key.split('-')
        sr_id = int(sr_id)

        if sort == 'controversy':
            # I screwed this up in the mapper and it's too late to fix
            # it
            sort = 'controversial'

        q = queries.get_links(Subreddit._byID(sr_id), sort, time)
        insert_to_query(q, [tuple([item[-1]] + map(float, item[:-1]))
                            for item in maxes])

    elif key.split('-')[0] in userrel_fns:
        key_type, account_id = key.split('-')
        account_id = int(account_id)
        fn = userrel_fns[key_type]
        q = fn(Account._byID(account_id))
        insert_to_query(q, [tuple([item[-1]] + map(float, item[:-1]))
                            for item in maxes])

def top1k_writefiles(dirname):
    """Divide up the top 1k of each key into its own file to make
       restarting after a failure much easier. Pairs with
       write_permacache_from_dir"""
    def hashdir(name, levels = [3]):
        # levels is a list of how long each stage if the hashdirname
        # should be. So [2,2] would make dirs like
        # 'ab/cd/thelisting.txt' (and this function would just return
        # the string 'ab/cd', so that you have the dirname that you
        # can create before os.path.joining to the filename)
        h = md5(name).hexdigest()

        last = 0
        dirs = []
        for l in levels:
            dirs.append(h[last:last+l])
            last += l

        return os.path.join(*dirs)

    def post(key, maxes):
        # we're taking a hash like 12345678901234567890123456789012
        # and making a directory name two deep out of the first half
        # of the characters. We may want to tweak this as the number
        # of listings

        hd = os.path.join(dirname, hashdir(key))
        try:
            os.makedirs(hd)
        except OSError as e:
            if e.errno != errno.EEXIST:
                raise
        filename = os.path.join(hd, key)

        with open(filename, 'w') as f:
            for item in maxes:
                f.write('%s\t' % key)
                f.write('\t'.join(item))
                f.write('\n')
        
    mr_tools.mr_reduce_max_per_key(lambda x: map(float, x[:-1]), num=1000,
                                   post=post)

def top1k_writepermacache(fd = sys.stdin):
    mr_tools.mr_reduce_max_per_key(lambda x: map(float, x[:-1]), num=1000,
                                   post=store_keys,
                                   fd = fd)

def write_permacache_from_dir(dirname):
    # we want the whole list so that we can display accurate progress
    # information. If we're operating on more than tens of millions of
    # files, we should either bail out or tweak this to not need the
    # whole list at once
    allfiles = []
    for root, dirs, files in os.walk(dirname):
        for f in files:
            allfiles.append(os.path.join(root, f))

    for fname in progress(allfiles, persec=True):
        try:
            write_permacache_from_file(fname)
            os.unlink(fname)
        except:
            mr_tools.status('failed on %r' % fname)
            raise

    mr_tools.status('Removing empty directories')
    for root, dirs, files in os.walk(dirname, topdown=False):
        for d in dirs:
            dname = os.path.join(root, d)
            try:
                os.rmdir(dname)
            except OSError as e:
                if e.errno == errno.ENOTEMPTY:
                    mr_tools.status('%s not empty' % (dname,))
                else:
                    raise

def write_permacache_from_file(fname):
    with open(fname) as fd:
        top1k_writepermacache(fd = fd)
