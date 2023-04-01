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
import multiprocessing

from r2.lib.mr_tools._mr_tools import mr_map, mr_reduce, format_dataspec
from r2.lib.mr_tools._mr_tools import stdin, emit

def join_things(fields, deleted=False, spam=True):
    """A reducer that joins thing table dumps and data table dumps"""
    # Because of how Python handles scope, if we want to modify these outside
    # the closure function below, they need to be inside a mutable object.
    # http://stackoverflow.com/a/23558809/120999
    counters = {
        'processed': 0,
        'skipped': 0,
    }
    def process(thing_id, vals):
        data = {}
        thing = None

        for val in vals:
            if val[0] == 'thing':
                thing = format_dataspec(val,
                                        ['data_type', # e.g. 'thing'
                                         'thing_type', # e.g. 'link'
                                         'ups',
                                         'downs',
                                         'deleted',
                                         'spam',
                                         'timestamp'])
            elif val[0] == 'data':
                val = format_dataspec(val,
                                      ['data_type', # e.g. 'data'
                                       'thing_type', # e.g. 'link'
                                       'key', # e.g. 'sr_id'
                                       'value'])
                if val.key in fields:
                    data[val.key] = val.value

        if (
            # silently ignore if we didn't see the 'thing' row
            thing is not None

            # remove spam and deleted as appriopriate
            and (deleted or thing.deleted == 'f')
            and (spam or thing.spam == 'f')

            # and silently ignore items that don't have all of the
            # data that we need
            and all(field in data for field in fields)):

            counters['processed'] += 1
            yield ((thing_id, thing.thing_type, thing.ups, thing.downs,
                    thing.deleted, thing.spam, thing.timestamp)
                   + tuple(data[field] for field in fields))
        else:
            counters['skipped'] += 1

    mr_reduce(process)
    # Print to stderr to avoid getting this caught up in the pipe of
    # compute_time_listings.
    print >> sys.stderr, '%s items processed, %s skipped' % (
                         counters['processed'], counters['skipped'])

class Mapper(object):
    def __init__(self):
        pass

    def process(self, values):
        raise NotImplemented

    def __call__(self, line):
        line = line.strip('\n')
        vals = line.split('\t')
        return list(self.process(vals)) # a list of tuples

def mr_map_parallel(processor, fd = stdin,
                    workers = multiprocessing.cpu_count(),
                    chunk_size = 1000):
    # `process` must be an instance of Mapper and promise that it is
    # safe to execute in a fork()d process.  Also note that we fuck
    # up the result ordering, but relying on result ordering breaks
    # the mapreduce contract anyway. Note also that like many of the
    # mr_tools functions, we break on newlines in the emitted output

    if workers == 1:
        return mr_map(process, fd=fd)

    pool = multiprocessing.Pool(workers)

    for res in pool.imap_unordered(processor, fd, chunk_size):
        for subres in res:
            emit(subres)

def test():
    from r2.lib.mr_tools._mr_tools import keyiter

    for key, vals in keyiter():
        print key, vals
        for val in vals:
            print '\t', val

class UpperMapper(Mapper):
    def process(self, values):
        yield map(str.upper, values)

def test_parallel():
    return mr_map_parallel(UpperMapper())
