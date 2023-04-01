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

import collections
from datetime import datetime, timedelta
import json
from uuid import uuid1

from pycassa.types import CompositeType, AsciiType
from pycassa.system_manager import TIME_UUID_TYPE
from pylons import app_globals as g
import pytz

from r2.lib import hooks
from r2.lib.db import tdb_cassandra
from r2.lib.db.tdb_cassandra import (
    ASCII_TYPE,
    UTF8_TYPE,
)
from r2.lib.utils import Enum, epoch_timestamp

from r2.models import Account


class Vote(object):
    DIRECTIONS = Enum("up", "down", "unvote")
    SERIALIZED_DIRECTIONS = {
        DIRECTIONS.up: 1,
        DIRECTIONS.down: -1,
        DIRECTIONS.unvote: 0,
    }
    DESERIALIZED_DIRECTIONS = {
        v: k for k, v in SERIALIZED_DIRECTIONS.iteritems()}

    def __init__(self, user, thing, direction, date, data=None, effects=None,
            get_previous_vote=True, event_data=None):
        if not thing.is_votable:
            raise TypeError("Can't create vote on unvotable thing %s" % thing)

        if direction not in self.DIRECTIONS:
            raise ValueError("Invalid vote direction: %s" % direction)

        self.user = user
        self.thing = thing
        self.direction = direction
        self.date = date.replace(tzinfo=g.tz)
        self.data = data
        self.event_data = event_data

        # see if the user has voted on this thing before
        if get_previous_vote:
            self.previous_vote = VoteDetailsByThing.get_vote(user, thing)
            if self.previous_vote:
                # XXX: why do we keep the old date?
                self.date = self.previous_vote.date.replace(tzinfo=g.tz)
        else:
            self.previous_vote = None

        self.effects = VoteEffects(self, effects)

    def __eq__(self, other):
        return (self.user == other.user and
            self.thing == other.thing and
            self.direction == other.direction)

    def __ne__(self, other):
        return not self == other

    @classmethod
    def serialize_direction(cls, direction):
        """Convert the DIRECTIONS enum to values used when storing."""
        if direction not in cls.DIRECTIONS:
            raise ValueError("Invalid vote direction: %s" % direction)

        return cls.SERIALIZED_DIRECTIONS[direction]

    @classmethod
    def deserialize_direction(cls, direction):
        """Convert stored vote direction value back to DIRECTIONS enum."""
        direction = int(direction)

        if direction not in cls.DESERIALIZED_DIRECTIONS:
            raise ValueError("Invalid vote direction: %s" % direction)

        return cls.DESERIALIZED_DIRECTIONS[direction]

    @property
    def _id(self):
        return "%s_%s" % (self.user._id36, self.thing._id36)

    @property
    def affected_thing_attr(self):
        """The attr on the thing this vote will increment."""
        if not self.effects.affects_score:
            return None

        if self.is_upvote:
            return "_ups"
        elif self.is_downvote:
            return "_downs"

    @property
    def is_upvote(self):
        return self.direction == self.DIRECTIONS.up

    @property
    def is_downvote(self):
        return self.direction == self.DIRECTIONS.down

    @property
    def is_self_vote(self):
        """Whether the voter is also the author of the thing voted on."""
        return self.user._id == self.thing.author_id

    @property
    def is_automatic_initial_vote(self):
        """Whether this is the automatic vote cast on things when posted."""
        return self.is_self_vote and not self.previous_vote

    @property
    def delay(self):
        """How long after the thing was posted that the vote was cast."""
        if self.is_automatic_initial_vote:
            return timedelta(0)
        
        return self.date - self.thing._date

    def apply_effects(self):
        """Apply the effects of the vote to the thing that was voted on."""
        # remove the old vote
        if self.previous_vote and self.previous_vote.affected_thing_attr:
            self.thing._incr(self.previous_vote.affected_thing_attr, -1)

        # add the new vote
        if self.affected_thing_attr:
            self.thing._incr(self.affected_thing_attr, 1)

        if self.effects.affects_karma:
            change = self.effects.karma_change
            if self.previous_vote:
                change -= self.previous_vote.effects.karma_change

            if change:
                self.thing.author_slow.incr_karma(
                    kind=self.thing.affects_karma_type,
                    sr=self.thing.subreddit_slow,
                    amt=change,
                )

        hooks.get_hook("vote.apply_effects").call(vote=self)

    def commit(self):
        """Apply the vote's effects and persist it."""
        if self.previous_vote and self == self.previous_vote:
            return

        self.apply_effects()
        VotesByAccount.write_vote(self)

        # Always update the search index if the thing has fewer than 20 votes.
        # When the thing has more votes queue an update less often.
        if self.thing.num_votes < 20 or self.thing.num_votes % 10 == 0:
            self.thing.update_search_index(boost_only=True)

        if self.event_data:
            g.events.vote_event(self)

        g.stats.simple_event('vote.total')


class VoteEffects(object):
    """Contains details about how a vote affects the thing voted on."""
    def __init__(self, vote, effects=None):
        """Initialize a new set of vote effects.

        If a dict of previously-determined effects are passed in as `effects`,
        those will be used instead of calculating the effects.
        """
        self.note_codes = {}
        self.validator = None

        if effects:
            self.affects_score = effects.pop("affects_score")
            self.affects_karma = effects.pop("affects_karma")
            self.other_effects = effects
        else:
            hook = hooks.get_hook("vote.get_validator")
            self.validator = hook.call_until_return(vote=vote, effects=self)

            self.affects_score = self.determine_affects_score(vote)
            self.affects_karma = self.determine_affects_karma(vote)
            self.other_effects = self.determine_other_effects(vote)

        self.karma_change = 0
        if self.affects_karma:
            if vote.is_upvote:
                self.karma_change = 1
            elif vote.is_downvote:
                self.karma_change = -1

    def add_note(self, code, message=None):
        self.note_codes[code] = message

    @property
    def notes(self):
        notes = []

        for code, message in self.note_codes.iteritems():
            note = code
            if message:
                note += " (%s)" % message
            notes.append(note)

        return notes

    def determine_affects_score(self, vote):
        """Determine whether the vote should affect the thing's score."""
        # If it's the automatic upvote on the user's own post, it won't affect
        # the score because we create it with a score of 1 already.
        if vote.is_automatic_initial_vote:
            self.add_note("AUTOMATIC_INITIAL_VOTE")
            return False

        if vote.previous_vote:
            if not vote.previous_vote.effects.affects_score:
                self.add_note("PREVIOUS_VOTE_NO_EFFECT")
                return False

        if self.validator:
            affects_score = self.validator.determine_affects_score()
            if affects_score is not None:
                return affects_score

        return True

    def determine_affects_karma(self, vote):
        """Determine whether the vote should affect the author's karma."""
        from r2.models import Comment

        if not self.affects_score:
            return False

        if vote.previous_vote:
            if not vote.previous_vote.effects.affects_karma:
                self.add_note("PREVIOUS_VOTE_NO_KARMA")
                return False

        if not bool(vote.thing.affects_karma_type):
            self.add_note("KARMALESS_THING")
            return False

        # never give karma on stickied comments. Only check distinguished
        # comments to avoid fetching the link on most votes, for performance.
        if isinstance(vote.thing, Comment) and vote.thing.is_distinguished:
            link = vote.thing.link_slow
            if vote.thing._id == link.sticky_comment_id:
                self.add_note("COMMENT_STICKIED")
                return False

        if self.validator:
            affects_karma = self.validator.determine_affects_karma()
            if affects_karma is not None:
                return affects_karma

        return True

    def determine_other_effects(self, vote):
        """Determine any other effects of the vote."""
        other_effects = {}

        if self.validator:
            other_effects.update(self.validator.other_effects)

        return other_effects

    @property
    def serializable_data(self):
        """Return the effects data in a format suitable for storing."""
        data = {
            "affects_score": self.affects_score,
            "affects_karma": self.affects_karma,
        }

        for key, value in self.other_effects.iteritems():
            data[key] = value

        if self.notes:
            data["notes"] = ", ".join(self.notes)

        return data


class VotesByAccount(tdb_cassandra.DenormalizedRelation):
    _use_db = False
    _read_consistency_level = tdb_cassandra.CL.ONE

    @classmethod
    def rel(cls, thing_cls):
        from r2.models import Comment, Link
        if thing_cls == Link:
            return LinkVotesByAccount
        elif thing_cls == Comment:
            return CommentVotesByAccount

        raise TypeError("Can't find %r class for %r" % (cls, thing_cls))

    @classmethod
    def write_vote(cls, vote):
        rel = cls.rel(vote.thing.__class__)
        rel.create(vote.user, vote.thing, vote=vote)

    @classmethod
    def value_for(cls, thing1, thing2, vote):
        return str(Vote.serialize_direction(vote.direction))


class LinkVotesByAccount(VotesByAccount):
    _use_db = True
    _views = []
    _last_modified_name = "LinkVote"
    # this is taken care of in r2.lib.voting:cast_vote
    _write_last_modified = False


class CommentVotesByAccount(VotesByAccount):
    _use_db = True
    _views = []
    _last_modified_name = "CommentVote"
    # this is taken care of in r2.lib.voting:cast_vote
    _write_last_modified = False


class VoteDetailsByThing(tdb_cassandra.View):
    _use_db = False
    _fetch_all_columns = True
    _extra_schema_creation_args = dict(key_validation_class=ASCII_TYPE,
                                       default_validation_class=UTF8_TYPE)

    @classmethod
    def create(cls, user, thing, vote):
        # we don't use the user or thing args, but they need to be there for
        # calling this automatically when updating views of a DenormalizedRel
        vote_data = vote.data.copy()

        # pull the IP out of the data to store it separately with a TTL
        ip = vote_data.pop("ip")

        effects_data = vote.effects.serializable_data
        # split the notes out to store separately
        notes = effects_data.pop("notes", None)

        data = json.dumps({
            "direction": Vote.serialize_direction(vote.direction),
            "date": int(epoch_timestamp(vote.date)),
            "data": vote_data,
            "effects": effects_data,
        })

        cls._set_values(vote.thing._id36, {vote.user._id36: data})

        # write the IP data and notes separately so they can be TTLed
        if ip:
            VoterIPByThing.create(vote, ip)

        if notes:
            VoteNote.set(vote, notes)

    @classmethod
    def get_vote(cls, user, thing):
        details = cls.get_details(thing, [user])
        if details:
            return details[0]

        return None

    @staticmethod
    def convert_old_details(old_data):
        if "valid_thing" not in old_data:
            return old_data

        converted_data = {}
        converted_data["direction"] = int(old_data.pop("direction"))
        converted_data["date"] = int(old_data.pop("date"))

        valid_thing = old_data.pop("valid_thing", True)
        valid_user = old_data.pop("valid_user", True)
        converted_data["effects"] = {
            "affects_score": valid_thing,
            "affects_karma": valid_user,
        }

        if old_data:
            converted_data["data"] = old_data

        return converted_data

    @classmethod
    def get_details(cls, thing, voters=None):
        from r2.models import Comment, Link
        if isinstance(thing, Link):
            details_cls = VoteDetailsByLink
        elif isinstance(thing, Comment):
            details_cls = VoteDetailsByComment
        else:
            raise ValueError

        voter_id36s = None
        if voters:
            voter_id36s = [voter._id36 for voter in voters]

        try:
            row = details_cls._byID(thing._id36, properties=voter_id36s)
            raw_details = row._values()
        except tdb_cassandra.NotFound:
            return []

        try:
            row = VoterIPByThing._byID(thing._fullname, properties=voter_id36s)
            ips = row._values()
        except tdb_cassandra.NotFound:
            ips = {}

        details = []
        for voter_id36, json_data in raw_details.iteritems():
            data = json.loads(json_data)
            data = cls.convert_old_details(data)

            user = Account._byID36(voter_id36, data=True)
            direction = Vote.deserialize_direction(data.pop("direction"))
            date = datetime.utcfromtimestamp(data.pop("date"))
            effects = data.pop("effects")
            data["ip"] = ips.get(voter_id36)

            vote = Vote(user, thing, direction, date, data, effects,
                get_previous_vote=False)
            details.append(vote)
        details.sort(key=lambda d: d.date)

        return details


@tdb_cassandra.view_of(LinkVotesByAccount)
class VoteDetailsByLink(VoteDetailsByThing):
    _use_db = True


@tdb_cassandra.view_of(CommentVotesByAccount)
class VoteDetailsByComment(VoteDetailsByThing):
    _use_db = True


class VoterIPByThing(tdb_cassandra.View):
    _use_db = True
    _ttl = timedelta(days=100)
    _fetch_all_columns = True
    _extra_schema_creation_args = dict(key_validation_class=ASCII_TYPE,
                                       default_validation_class=UTF8_TYPE)

    @classmethod
    def create(cls, vote, ip):
        cls._set_values(vote.thing._fullname, {vote.user._id36: ip})


class VoteNote(tdb_cassandra.View):
    _use_db = True
    _connection_pool = 'main'
    _compare_with = TIME_UUID_TYPE
    _ttl = timedelta(days=100)

    @classmethod
    def _rowkey(cls, vote):
        return '%s_%s' % (vote.user._fullname, vote.thing._fullname)

    @classmethod
    def set(cls, vote, note):
        rowkey = cls._rowkey(vote)
        column = {uuid1(): note}
        cls._set_values(rowkey, column)

    @classmethod
    def get(cls, vote):
        rowkey = cls._rowkey(vote)
        try:
            all_notes = cls._byID(rowkey)
        except tdb_cassandra.NotFound:
            return None

        return ", ".join(all_notes._values().values())
