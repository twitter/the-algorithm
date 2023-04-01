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
from itertools import imap, groupby
from heapq import nlargest

stdin = sys.stdin
stderr = sys.stderr

class _Chunker(object):
    __slots__ = ('_size', '_done', '_it')

    def __init__(self, it, long size=25):
        self._it = iter(it)
        self._size = range(size)
        self._done = 0

    def next(self):
        if self._done:
            raise StopIteration

        cdef list chunk = []

        for x in self._size:
            try:
                chunk.append(next(self._it))
            except StopIteration:
                if chunk:
                    self._done = 1
                    return chunk
                else:
                    raise
        return chunk

cdef class in_chunks(object):
    cdef it
    cdef int size

    def __init__(self, it, int size=25):
        self.it = it
        self.size = size

    def __iter__(self):
        return _Chunker(self.it, self.size)

cdef class Storage(dict):
    def __getattr__(self, attr):
        return self[attr]

def valiter(grouper):
    key, group = grouper
    return key, imap(lambda x: x[1:], group)

cpdef list _keyiter_splitter(str x):
    x = x.strip('\n')
    return x.split('\t')

def keyiter(stream=stdin):
    lines = imap(_keyiter_splitter, stream)
    groups = groupby(lines, lambda x: x[0])
    return imap(valiter, groups)

def emit(vals):
    print '\t'.join(map(str, vals))

def emit_all(vals):
    for val in vals:
        emit(val)

def status(msg, **opts):
    if opts:
        msg = msg % opts
    stderr.write("%s\n" % msg)

cpdef Storage format_dataspec(msg, specs):
    # spec() =:= name | (name, fn)
    # specs  =:= [ spec() ]
    cdef Storage ret = Storage()
    for val, spec in zip(msg, specs):
        if isinstance(spec, basestring):
            # the spec is just a name
            name = spec
            ret[name] = val
        else:
            # the spec is a tuple of the name and the function to pass
            # the string through to make the real value
            name, fn = spec
            ret[name] = fn(val)
    return Storage(**ret)

cdef class dataspec_m(object):
    cdef specs

    def __init__(self, *specs):
        self.specs = specs

    def __call__(self, fn):
        specs = self.specs
        def wrapped_fn_m(args):
            return fn(format_dataspec(args, specs))
        return wrapped_fn_m

cdef class dataspec_r(object):
    cdef specs

    def __init__(self, *specs):
        self.specs = specs

    def __call__(self, fn):
        specs = self.specs
        def wrapped_fn_r(key, msgs):
            return fn(key, imap(lambda msg: format_dataspec(msg, specs),
                                msgs))
        return wrapped_fn_r

cpdef mr_map(process, fd = stdin):
    for line in fd:
        vals = line.strip('\n').split('\t')
        for res in process(vals):
            emit(res)

cpdef mr_reduce(process, fd = stdin):
    for key, vals in keyiter(fd):
        for res in process(key, vals):
            emit(res)

cpdef mr_foldl(process, init, emit = False, fd = stdin):
    acc = init
    for key, vals in keyiter(fd):
        acc = process(key, vals, acc)

    if emit:
        emit(acc)

    return acc

cpdef mr_max(process, int idx = 0, int num = 10, emit = False, fd = stdin):
    """a reducer that, in the process of reduction, only returns the
       top N results"""
    cdef list maxes = []
    for key, vals in keyiter(fd):
        for newvals in in_chunks(process(key, vals)):
            for val in newvals:
                if len(maxes) < num or val[idx] > maxes[-1][idx]:
                    maxes.append(val)
            maxes.sort(reverse=True)
            maxes = maxes[:num]

    if emit:
        emit_all(maxes)

    return maxes

cpdef _sbool(str x):
    return x == 't'

def dataspec_m_rel(*fields):
    return dataspec_m(*((('rel_id', int),
                         'rel_type',
                         ('thing1_id', int),
                         ('thing2_id', int),
                         'name',
                         ('timestamp', float))
                        + fields))

def dataspec_m_thing(*fields):
    return dataspec_m(*((('thing_id', int),
                         'thing_type',
                         ('ups', int),
                         ('downs', int),
                         ('deleted', _sbool),
                         ('spam', _sbool),
                         ('timestamp', float))
                        + fields))

def mr_reduce_max_per_key(sort_key, post = None, num = 10, fd = sys.stdin):
    def process(key, vals):
        cdef list maxes = nlargest(num, vals, key=sort_key)

        if post:
            # if we were passed a "post" function, he takes
            # responsibility for emitting
            post(key, maxes)
            return []

        return [ ([key] + item)
                 for item in maxes ]

    return mr_reduce(process, fd = fd)
