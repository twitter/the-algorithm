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

from collections import defaultdict

from pylons import app_globals as g

from r2.lib.utils import SimpleSillyStub
from r2.lib.utils.comment_tree_utils import get_tree_details, calc_num_children
from r2.models.link import Comment


"""Storage for comment trees

CommentTree is a class that provides an interface to the actual storage.
Whatever the underlying storage is, it must be able to generate the following
structures:
* tree: dict of comment id -> list of child comment ids. The `None` entry is
  top level comments
* cids: list of all comment ids in the comment tree
* depth: dict of comment id -> depth
* parents: dict of comment id -> parent comment id
* num_children: dict of comment id -> number of descendant comments, not just
  direct children

CommentTreePermacache uses permacache as the storage, and stores just the tree
structure. The cids, depth, parents and num_children are generated on the fly
from the tree.

Attempts were made to move to a different data model that would take advantage
of the column based storage of Cassandra and eliminate the need for locking when
adding a comment to the comment tree.

CommentTreeStorageV2: for each comment, write a column where the column name is
(parent_comment id, comment_id) and the column value is a counter giving the
size of the subtree rooted at the comment. This data model was abandoned because
counters ended up being unreliable and the shards put too much GC pressure on
the Cassandra JVM.

CommentTreeStorageV3: for each comment, write a column where the column name is
(depth, parent_comment_id, comment_id) and the column value is not used. This
data model was abandoned because of more unexpected GC problems after longer
time periods and generally insufficient regular-case performance.

"""


class CommentTreePermacache(object):
    @classmethod
    def _permacache_key(cls, link):
        return 'comments_' + str(link._id)

    @classmethod
    def _mutation_context(cls, link):
        """Return a lock for use during read-modify-write operations"""
        key = 'comment_lock_' + str(link._id)
        return g.make_lock("comment_tree", key)

    @classmethod
    def prepare_new_storage(cls, link):
        """Write an empty tree to permacache"""
        with cls._mutation_context(link) as lock:
            # read-modify-write, so get the lock
            existing_tree = cls._load_tree(link)
            if not existing_tree:
                # don't overwrite an existing non-empty tree
                tree = {}
                cls._write_tree(link, tree, lock)

    @classmethod
    def _load_tree(cls, link):
        key = cls._permacache_key(link)
        tree = g.permacache.get(key)
        return tree or {}   # assume empty tree on miss

    @classmethod
    def _write_tree(cls, link, tree, lock):
        assert lock.have_lock
        key = cls._permacache_key(link)
        g.permacache.set(key, tree)

    @classmethod
    def get_tree_pieces(cls, link, timer):
        tree = cls._load_tree(link)
        timer.intermediate('load')

        cids, depth, parents = get_tree_details(tree)
        num_children = calc_num_children(tree)
        num_children = defaultdict(int, num_children)
        timer.intermediate('calculate')

        return cids, tree, depth, parents, num_children

    @classmethod
    def add_comments(cls, link, comments):
        with cls._mutation_context(link) as lock:
            # adding comments requires read-modify-write, so get the lock
            tree = cls._load_tree(link)
            cids, _, _ = get_tree_details(tree)

            # skip any comments that are already in the stored tree and convert
            # to a set to remove any duplicate comments
            comments = {
                comment for comment in comments
                if comment._id not in cids
            }

            if not comments:
                return

            # warn on any comments whose parents are missing from the tree
            # because they will never be displayed unless their parent is
            # added. this can happen in normal operation if there are multiple
            # queue consumers and a child is processed before its parent.
            parent_ids = set(cids) | {comment._id for comment in comments}
            possible_orphan_comments = {
                comment for comment in comments
                if (comment.parent_id and comment.parent_id not in parent_ids)
            }
            if possible_orphan_comments:
                g.log.error("comment_tree_inconsistent: %s %s", link,
                    possible_orphan_comments)
                g.stats.simple_event('comment_tree_inconsistent')

            for comment in comments:
                tree.setdefault(comment.parent_id, []).append(comment._id)

            cls._write_tree(link, tree, lock)

    @classmethod
    def rebuild(cls, link, comments):
        """Generate a tree from comments and overwrite any existing tree."""
        with cls._mutation_context(link) as lock:
            # not reading, but we should block other read-modify-write
            # operations to avoid being clobbered by their write
            tree = {}
            for comment in comments:
                tree.setdefault(comment.parent_id, []).append(comment._id)

            cls._write_tree(link, tree, lock)


class CommentTree:
    def __init__(self, link, cids, tree, depth, parents, num_children):
        self.link = link
        self.cids = cids
        self.tree = tree
        self.depth = depth
        self.parents = parents
        self.num_children = num_children

    @classmethod
    def by_link(cls, link, timer=None):
        if timer is None:
            timer = SimpleSillyStub()

        pieces = CommentTreePermacache.get_tree_pieces(link, timer)
        cids, tree, depth, parents, num_children = pieces
        comment_tree = cls(link, cids, tree, depth, parents, num_children)
        return comment_tree

    @classmethod
    def on_new_link(cls, link):
        CommentTreePermacache.prepare_new_storage(link)

    @classmethod
    def add_comments(cls, link, comments):
        CommentTreePermacache.add_comments(link, comments)

    @classmethod
    def rebuild(cls, link):
        # retrieve all the comments for the link
        q = Comment._query(
            Comment.c.link_id == link._id,
            Comment.c._deleted == (True, False),
            Comment.c._spam == (True, False),
            optimize_rules=True,
        )
        comments = list(q)

        # remove any comments with missing parents
        comment_ids = {comment._id for comment in comments}
        comments = [
            comment for comment in comments
            if not comment.parent_id or comment.parent_id in comment_ids 
        ]

        CommentTreePermacache.rebuild(link, comments)

        link.num_comments = sum(1 for c in comments if not c._deleted)
        link._commit()
