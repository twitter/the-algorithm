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

class BooleanOp(object):
    def __init__(self, *ops):
        self.ops = ops

    def __repr__(self):
        return '<%s_ %s>' % (self.__class__.__name__, str(self.ops))

class or_(BooleanOp): pass
class and_(BooleanOp): pass
class not_(BooleanOp): pass

class op(object):
    def __init__(self, lval, lval_name, rval):
        self.lval = lval
        self.rval = rval
        self.lval_name = lval_name

    def __repr__(self):
        return '<%s: %s, %s>' % (self.__class__.__name__, self.lval, self.rval)

    # sorts in a consistent order, required for Query._cache_key()
    def __cmp__(self, other):
        return cmp(repr(self), repr(other))

class eq(op): pass
class ne(op): pass
class lt(op): pass
class lte(op): pass
class gt(op): pass
class gte(op): pass
class in_(op): pass

class Slot(object):
    def __init__(self, lval):
        if isinstance(lval, Slot):
            self.name = lval.name
            self.lval = lval
        else:
            self.name = lval

    def __repr__(self):
        return '<%s: %s>' % (self.__class__.__name__, self.name)

    def __eq__(self, other):
        return eq(self, self.name, other)

    def __ne__(self, other):
        return ne(self, self.name, other)

    def __lt__(self, other):
        return lt(self, self.name, other)

    def __le__(self, other):
        return lte(self, self.name, other)

    def __gt__(self, other):
        return gt(self, self.name, other)

    def __ge__(self, other):
        return gte(self, self.name, other)

    def in_(self, other):
        return in_(self, self.name, other)

class Slots(object):
    def __getattr__(self, attr):
        return Slot(attr)

    def __getitem__(self, attr):
        return Slot(attr)
        
def op_iter(ops):
    for o in ops:
        if isinstance(o, op):
            yield o
        elif isinstance(o, BooleanOp):
            for p in op_iter(o.ops):
                yield p

class query_func(Slot): pass
class lower(query_func): pass
class ip_network(query_func): pass
class base_url(query_func): pass
class domain(query_func): pass
class year_func(query_func): pass

class timeago(object):
    def __init__(self, interval):
        self.interval = interval

    def __repr__(self):
        return '<interval: %s>' % self.interval

class sort(object):
    def __init__(self, col):
        self.col = col

    def __repr__(self):
        return '<sort:%s %s>' % (self.__class__.__name__, str(self.col))

    def __eq__(self, other):
        return self.__class__ == other.__class__ and self.col == other.col

    def __ne__(self, other):
        return not self.__eq__(other)


class asc(sort): pass
class desc(sort):pass
class shuffled(desc): pass
