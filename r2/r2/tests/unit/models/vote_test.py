from mock import patch, MagicMock
from datetime import datetime

import pytz

from r2.lib.utils import tup
from r2.models.vote import Vote
from r2.tests import RedditTestCase


class TestVoteValidator(RedditTestCase):

    def setUp(self):
        self.user = MagicMock(name="user")
        self.user._id36 = 'userid36'
        self.thing = MagicMock(name="thing")
        self.vote_data = {}
        super(RedditTestCase, self).setUp()

    def cast_vote(self, **kw):
        kw.setdefault("date", datetime.now(pytz.UTC))
        kw.setdefault("direction", Vote.DIRECTIONS.up)
        kw.setdefault("get_previous_vote", False)
        kw.setdefault("data", self.vote_data)
        return Vote(
            user=self.user,
            thing=self.thing,
            **kw
        )

    def assert_vote_effects(
        self, vote,
        affects_score=True,
        affects_karma=True,
        affected_thing_attr="_ups",
        notes=None,
    ):
        notes = set(tup(notes) if notes else [])
        self.assertEqual(vote.effects.affects_score, affects_score)
        self.assertEqual(vote.effects.affects_karma, affects_karma)
        self.assertEqual(vote.affected_thing_attr, affected_thing_attr)
        self.assertEqual(set(vote.effects.notes), notes)
        return vote

    def test_upvote_effects(self):
        vote = self.cast_vote()
        self.assertTrue(vote.is_upvote)
        self.assertFalse(vote.is_downvote)
        self.assertFalse(vote.is_self_vote)
        self.assert_vote_effects(vote)

    def test_downvote_effects(self):
        vote = self.cast_vote(direction=Vote.DIRECTIONS.down)
        self.assertFalse(vote.is_upvote)
        self.assertTrue(vote.is_downvote)
        self.assertFalse(vote.is_self_vote)
        self.assert_vote_effects(vote, affected_thing_attr="_downs")
