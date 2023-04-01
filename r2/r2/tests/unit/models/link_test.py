#!/usr/bin/env python
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

import unittest

from mock import MagicMock, patch

from r2.models.link import Link, Comment

TINY_COMMENT = 'rekt'
SHORT_COMMENT = 'What is your favorite car from a rival brand?'
MEDIUM_COMMENT = '''I'm humbled by how many of you were interested in talking
to me and hearing about what we're doing with autonomous driving. When I signed
off last night, I never thought there would be so many more questions than what
I was able to answer after I left. I wish I had had more time last night, but
I'm answering more questions today.'''
LONG_COMMENT = '''That was a really good question then and still is and gets
back to something I said earlier about the difference between autonomous drive
vehicles, in which the driver remains in control, and self-driving cars, in
which a driver is not even required.
The focus of our R&D right now is on autonomous drive vehicles that enhance the
driving experience, by giving the driver the option of letting the car handle
some functions and by improving the car's ability to avoid accidents. That
technology exists already and will be rolled out in phases over the next
several years.
We are not dismissive of self-driving vehicles, but the reality is the timeline
for them is much further out into the future.'''

class CommentMock(Comment):
    """This class exists to allow us to call the _qa() method on Comments
    without having to dick around with everything else they support."""
    _nodb = True

    def __init__(self, ups=1, downs=0, body=TINY_COMMENT, author_id=None):
        self._ups = ups
        self._downs = downs
        self.body = body
        self.author_id = author_id

    def __setattr__(self, attr, val):
        self.__dict__[attr] = val

    def __getattr__(self, attr):
        return self.attr


class TestCommentQaSort(unittest.TestCase):
    def test_simple_upvotes(self):
        """All else equal, do upvoted comments score better?"""
        no_upvotes = CommentMock(ups=1)
        one_upvote = CommentMock(ups=2)
        many_upvotes = CommentMock(ups=50)
        no_upvotes_score = no_upvotes._qa((), ())
        one_upvote_score = one_upvote._qa((), ())
        many_upvotes_score = many_upvotes._qa((), ())

        self.assertLess(no_upvotes_score, one_upvote_score)
        self.assertLess(one_upvote_score, many_upvotes_score)

    def test_simple_downvotes(self):
        """All else equal, do downvoted comments score worse?"""
        no_downvotes = CommentMock(downs=0)
        one_downvote = CommentMock(downs=1)
        many_downvotes = CommentMock(downs=50)
        no_downvotes_score = no_downvotes._qa((), ())
        one_downvote_score = one_downvote._qa((), ())
        many_downvotes_score = many_downvotes._qa((), ())

        self.assertGreater(no_downvotes_score, one_downvote_score)
        self.assertGreater(one_downvote_score, many_downvotes_score)

    def test_simple_length(self):
        """All else equal, do longer comments score better?"""
        tiny = CommentMock(body=TINY_COMMENT)
        short = CommentMock(body=SHORT_COMMENT)
        medium = CommentMock(body=MEDIUM_COMMENT)
        long = CommentMock(body=LONG_COMMENT)
        tiny_score = tiny._qa((), ())
        short_score = short._qa((), ())
        medium_score = medium._qa((), ())
        long_score = long._qa((), ())

        self.assertLess(tiny_score, short_score)
        self.assertLess(short_score, medium_score)
        self.assertLess(medium_score, long_score)

    def test_simple_op_responses(self):
        """All else equal, do OP answers bump up the score of comments?"""
        question = CommentMock()
        answer = CommentMock(author_id=1)
        no_answer_score = question._qa((), ())
        no_op_answer_score = question._qa((answer,), (2,))
        with_op_answer_score = question._qa((answer,), (1,))

        self.assertEqual(no_answer_score, no_op_answer_score)
        self.assertLess(no_op_answer_score, with_op_answer_score)

    def test_multiple_op_responses(self):
        """What effect do multiple OP responses have on a comment's score?"""
        question = CommentMock()
        op_answer = CommentMock(author_id=1)
        another_op_answer = CommentMock(author_id=1)
        one_answer_score = question._qa((op_answer,), (1,))
        two_answers_score = question._qa((op_answer, another_op_answer), (1,))

        self.assertEqual(one_answer_score, two_answers_score)

        bad_op_answer = CommentMock(ups=0, author_id=1)
        good_op_answer = CommentMock(ups=30, author_id=1)
        good_op_answer_score = question._qa((good_op_answer,), (1,))
        op_answers = (bad_op_answer, good_op_answer, op_answer)
        three_op_answers_score = question._qa(op_answers, (1,))

        # Are we basing the score on the highest-scoring OP answer?
        self.assertEqual(good_op_answer_score, three_op_answers_score)

    def test_simple_op_comments(self):
        """All else equal, do comments from OP score better?"""
        comment = CommentMock(author_id=1)
        op_score = comment._qa((), (1,))
        non_op_score = comment._qa((), (2,))

        self.assertLess(non_op_score, op_score)


ID = 0
def _mock_id(instance):
    if not getattr(instance, "__id", False):
        instance.__id = ID + 1
    return instance.__id


class LinkMock(Link):
    _nodb = True

    def __init__(self, **kwargs):
        for key, value in kwargs.iteritems():
            setattr(self, key, value)

    def __setattr__(self, attr, val):
        self.__dict__[attr] = val

    def __getattr__(self, attr):
        return getattr(
            self.__dict__,
            attr,
            getattr(self._defaults, attr, None),
        )

    @property
    def _id(self):
        return _mock_id(self)

    @property
    def subreddit_slow(self):
        return SubredditMock()

    def _commit(self):
        pass

    @classmethod
    @patch('r2.lib.voting.cast_vote')
    def _submit(cls, cast_vote, *args, **kwargs):
        """A _submit that mocks calls we don't care about testing."""
        return super(LinkMock, cls)._submit(*args, **kwargs)


class ThingMock():
    _nodb = True

    @property
    def _id(self):
        return _mock_id(self)


class AccountMock(ThingMock):
    @property
    def _spam(self):
        return False

    def _commit(self, *a, **kw):
        pass


class SubredditMock(ThingMock):
    @property
    def lang(self):
        return "en"

    @property
    def name(self):
        return "linktests"

    @property
    def spam_selfposts(self):
        return "high"

    @property
    def spam_links(self):
        return "low"


class TestSubmit(unittest.TestCase):
    def setUp(self):
        from r2.models import (
            LinksByAccount,
            LinksByUrlAndSubreddit,
            SubredditParticipationByAccount,
            SubredditsActiveForFrontPage,
        )

        LinksByAccount.add_link = MagicMock()
        SubredditParticipationByAccount.mark_participated = MagicMock()
        SubredditsActiveForFrontPage.mark_new_post = MagicMock()

        self.links_by_url_add_link = MagicMock()
        LinksByUrlAndSubreddit.add_link = self.links_by_url_add_link
        self.links_by_url_remove_link = MagicMock()
        LinksByUrlAndSubreddit.remove_link = self.links_by_url_remove_link

    def test_new_self_post_has_url(self):
        l = LinkMock._submit(
            is_self=True,
            content="this is a self post",
            title="test post",
            ip="127.0.0.1",
            sr=SubredditMock(),
            author=AccountMock()
        )

        self.assertEqual(l.url, u"/r/linktests/comments/%s/test_post/" % l._id36)

    def test_new_self_post_doesnt_modify_links_by_url(self):
        l = LinkMock._submit(
            is_self=True,
            content="this is a self post",
            title="test post",
            ip="127.0.0.1",
            sr=SubredditMock(),
            author=AccountMock()
        )

        self.assertEqual(self.links_by_url_add_link.call_count, 0)
        self.assertEqual(self.links_by_url_remove_link.call_count, 0)

    def test_changing_non_promo_fails(self):
        l = LinkMock._submit(
            is_self=True,
            content="this is a self post",
            title="test post",
            ip="127.0.0.1",
            sr=SubredditMock(),
            author=AccountMock()
        )

        with self.assertRaises(ValueError):
            l.set_content(False, "http://test.com/1")

    def test_changing_from_self_doesnt_remove_links_by_url(self):
        l = LinkMock._submit(
            is_self=True,
            content="this is a self post",
            title="test post",
            ip="127.0.0.1",
            sr=SubredditMock(),
            author=AccountMock()
        )
        l.promoted = True

        url = "http://test.com/1"

        self.assertEqual(self.links_by_url_add_link.call_count, 0)
        self.assertEqual(self.links_by_url_remove_link.call_count, 0)

        l.set_content(False, url)

        self.assertEqual(l.url, url)
        self.assertEqual(self.links_by_url_remove_link.call_count, 0)

    def test_changing_url_adds_links_by_url(self):
        url1 = "http://test.com/1"
        url2 = "http://test.com/2"
        l = LinkMock._submit(
            is_self=False,
            content=url1,
            title="test post",
            ip="127.0.0.1",
            sr=SubredditMock(),
            author=AccountMock()
        )
        l.promoted = True

        self.assertEqual(self.links_by_url_add_link.call_count, 1)
        self.assertEqual(self.links_by_url_remove_link.call_count, 0)

        l.set_content(False, url2)

        self.assertEqual(self.links_by_url_add_link.call_count, 2)
        self.assertEqual(self.links_by_url_remove_link.call_count, 1)

    def test_changing_to_self_removes_links_by_url(self):
        url = "http://test.com/1"
        l = LinkMock._submit(
            is_self=False,
            content=url,
            title="test post",
            ip="127.0.0.1",
            sr=SubredditMock(),
            author=AccountMock()
        )
        l.promoted = True

        self.assertEqual(self.links_by_url_add_link.call_count, 1)

        l.set_content(True, "change to self post")

        self.assertEqual(self.links_by_url_remove_link.call_count, 1)
