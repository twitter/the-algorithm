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


def get_tree_details(dict tree):
    cdef:
        list cids = []
        dict depth = {}
        dict parents = {}
        list child_ids

    for parent_id in sorted(tree):
        child_ids = tree[parent_id]

        cids.extend(child_ids)

        parents.update({child_id: parent_id for child_id in child_ids})

        child_depth = depth.get(parent_id, -1) + 1
        depth.update({child_id: child_depth for child_id in child_ids})

    return cids, depth, parents


def calc_num_children(dict tree):
    cdef:
        dict num_children = {}
        list child_ids

    for parent_id in sorted(tree, reverse=True):
        if parent_id is None:
            continue

        child_ids = tree[parent_id]
        num_children[parent_id] = sum(
            1 + num_children.get(child_id, 0) for child_id in tree[parent_id])
    return num_children
