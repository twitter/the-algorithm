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

"""Implements the AutoModerator functionality, a rules system for subreddits.

AutoModerator allows subreddits to define "rules", which are conditions
checked against submissions and comments in that subreddit. If a rule's
conditions are satisfied by a post, actions can be automatically taken on it,
such as removing the post, setting flair on it, posting a comment, etc.

A subreddit's rules are defined through YAML on a mod-only page on the
subreddit's wiki. This collection of multiple rules is implemented with the
Ruleset class. Each individual rule is a Rule object, which is made up of at
least one RuleTarget object, which defines the conditions and/or actions to
apply to an item. Rules may have additional RuleTargets which represent
"related items" that also can have conditions and/or actions. Currently,
support exists for up to two additional RuleTargets, one for the item's
author, and another for the parent link (if the original item was a comment).
"""

from collections import namedtuple
from datetime import datetime
from hashlib import md5
import re
import traceback
import yaml

from pylons import app_globals as g

from r2.lib import amqp
from r2.lib.db import queries
from r2.lib.errors import RedditError
from r2.lib.filters import _force_unicode
from r2.lib.menus import CommentSortMenu
from r2.lib.utils import (
    SimpleSillyStub,
    TimeoutFunction,
    TimeoutFunctionException,
    lowercase_keys_recursively,
    timeinterval_fromstr,
    tup,
)
from r2.lib.validator import VMarkdown
from r2.models import (
    admintools,
    Account,
    Comment,
    DeletedUser,
    Frontpage,
    Inbox,
    LastModified,
    Link,
    Message,
    ModAction,
    Report,
    Subreddit,
    Thing,
    WikiPage,
)
from r2.models.automoderator import PerformedRulesByThing
from r2.models.wiki import wiki_id


if g.automoderator_account:
    ACCOUNT = Account._by_name(g.automoderator_account)
else:
    ACCOUNT = None

DISCLAIMER = "*I am a bot, and this action was performed automatically. Please [contact the moderators of this subreddit](/message/compose/?to=/r/{{subreddit}}) if you have any questions or concerns.*"

rules_by_subreddit = {}

unnumbered_placeholders_regex = re.compile(r"\{\{(match(?:-[^\d-]+?)?)\}\}")
match_placeholders_regex = re.compile(r"\{\{match-(?:([^}]+?)-)?(\d+)\}\}")
def replace_placeholders(string, data, matches):
    """Replace placeholders in the string with appropriate values."""
    item = data["item"]
    replacements = {
        "{{author}}": data["author"].name,
        "{{body}}": getattr(item, "body", ""),
        "{{subreddit}}": data["subreddit"].name,
        "{{author_flair_text}}": data["author"].flair_text(
            data["subreddit"]._id, obey_disabled=True),
        "{{author_flair_css_class}}": data["author"].flair_css_class(
            data["subreddit"]._id, obey_disabled=True),
    }

    if isinstance(item, Comment):
        context = None
        if item.parent_id:
            context = 3
        replacements.update({
            "{{kind}}": "comment",
            "{{permalink}}": item.make_permalink_slow(
                context=context, force_domain=True),
            "{{title}}": data["link"].title,
        })
        media_item = data["link"]
    elif isinstance(item, Link):
        replacements.update({
            "{{kind}}": "submission",
            "{{domain}}": item.link_domain(),
            "{{permalink}}": item.make_permalink_slow(force_domain=True),
            "{{title}}": item.title,
            "{{url}}": item.url,
        })
        media_item = item

    if media_item.media_object:
        oembed = media_item.media_object.get("oembed")
        if oembed:
            replacements.update({
                "{{media_author}}": oembed.get("author_name", ""),
                "{{media_title}}": oembed.get("title", ""),
                "{{media_description}}": oembed.get("description", ""),
                "{{media_author_url}}": oembed.get("author_url", ""),
            })

    for placeholder, replacement in replacements.iteritems():
        string = string.replace(placeholder, _force_unicode(replacement))

    # do the {{match-XX}} and {{match-field-XX}} replacements
    field_replacements = {}
    if matches:
        # replace any unnumbered matches with -1 versions
        string = unnumbered_placeholders_regex.sub(r"{{\1-1}}", string)

        # find all {{match-field-XX}} placeholders
        to_replace = match_placeholders_regex.finditer(string)
        for placeholder in to_replace:
            if placeholder.group(0) in field_replacements:
                continue

            source_match_name = placeholder.group(1)
            if not source_match_name:
                # they didn't specify a source, so just take one arbitrarily
                source_match_name = matches.keys()[0]

            source_match = matches.get(source_match_name, None)
            if not source_match:
                continue

            try:
                group_index = int(placeholder.group(2))
                replacement = source_match.group(group_index)
            except IndexError:
                continue

            field_replacements[placeholder.group(0)] = replacement

    for placeholder, replacement in field_replacements.iteritems():
        string = string.replace(placeholder, _force_unicode(replacement))

    return string


class AutoModeratorSyntaxError(ValueError):
    def __init__(self, message, yaml):
        yaml_lines = yaml.splitlines()
        if len(yaml_lines) > 10:
            yaml = "\n".join(yaml_lines[:10]) + "\n..."
        self.message = "%s in rule:\n\n%s" % (message, yaml)


class AutoModeratorRuleTypeError(AutoModeratorSyntaxError):
    pass


# used in Ruleset.__init__()
RuleDefinition = namedtuple("RuleDefinition", ["yaml", "values"])


class Ruleset(object):
    """A subreddit's collection of Rules."""
    def __init__(self, yaml_text="", timer=None):
        """Create a collection of Rules from YAML documents."""
        if timer is None:
            timer = SimpleSillyStub()

        self.init_time = datetime.now(g.tz)
        self.rules = []

        if not yaml_text:
            return

        # We want to maintain the original YAML source sections, so we need
        # to manually split up the YAML by the document delimiter (line
        # starting with "---") and then try to load each section to see if
        # it's valid
        yaml_sections = [section.strip("\r\n")
            for section in re.split("^---", yaml_text, flags=re.MULTILINE)]

        rule_defs = []

        for section_num, section in enumerate(yaml_sections, 1):
            try:
                parsed = yaml.safe_load(section)
            except Exception as e:
                raise ValueError(
                    "YAML parsing error in section %s: %s" % (section_num, e))

            # only keep the section if the parsed result is a dict (otherwise
            # it's generally just a comment)
            if isinstance(parsed, dict):
                rule_defs.append(RuleDefinition(yaml=section, values=parsed))

        timer.intermediate("yaml_parsing")

        if any("standard" in rule_def.values for rule_def in rule_defs):
            # load standard rules from wiki page
            standard_rules = {}
            try:
                wp = WikiPage.get(Frontpage, "automoderator_standards")
                standard_defs = yaml.safe_load_all(wp.content)
                for standard_def in standard_defs:
                    name = standard_def.pop("standard")
                    standard_rules[name] = standard_def
            except Exception as e:
                g.log.error("Error while loading automod standards: %s", e)
                standard_rules = None

        timer.intermediate("init_standard_rules")

        for rule_def in rule_defs:
            # use standard rule as a base if they defined one
            standard_name = rule_def.values.pop("standard", None)
            if standard_name:
                # error while loading the standards, skip this rule
                if standard_rules is None:
                    continue

                standard_values = None
                if isinstance(standard_name, basestring):
                    standard_values = standard_rules.get(standard_name, None)
                if not standard_values:
                    raise AutoModeratorSyntaxError(
                        "Invalid standard: `%s`" % standard_name,
                        rule_def.yaml,
                    )

                new_values = standard_values.copy()
                new_values.update(rule_def.values)
                rule_def = rule_def._replace(values=new_values)

            type = rule_def.values.get("type", "any")
            if type == "any":
                # try to create two Rules for comments and links
                rule = None
                for type_value in ("comment", "submission"):
                    rule_def.values["type"] = type_value
                    try:
                        rule = Rule(rule_def.values, rule_def.yaml)
                    except AutoModeratorRuleTypeError as type_error:
                        continue

                    # only keep the rule if it had any checks
                    if rule.has_any_checks(targets_only=True):
                        self.rules.append(rule)

                # if both types hit exceptions we should actually error
                if not rule:
                    raise type_error
            else:
                self.rules.append(Rule(rule_def.values, rule_def.yaml))

        timer.intermediate("init_rules")

        # drop any rules that don't have a check and an action
        self.rules = [rule for rule in self.rules
            if rule.has_any_checks() and rule.has_any_actions()]

        self.rules.sort(key=lambda r: r.priority, reverse=True)

    def __iter__(self):
        """Iterate over the rules in the collection."""
        for rule in self.rules:
            yield rule

    def __len__(self):
        return len(self.rules)

    @property
    def removal_rules(self):
        """Iterate over the rules that could cause removal of their target."""
        for rule in self:
            if rule.is_removal_rule:
                yield rule

    @property
    def nonremoval_rules(self):
        """Iterate over the rules that won't cause removal of their target."""
        for rule in self:
            if not rule.is_removal_rule:
                yield rule

    def apply_to_item(self, item):
        # fetch supplemental data to use throughout
        data = {}
        data["item"] = item
        data["subreddit"] = item.subreddit_slow

        author = item.author_slow
        if not author._deleted:
            data["author"] = author
        else:
            data["author"] = DeletedUser()

        if isinstance(item, Comment):
            data["link"] = Link._byID(item.link_id, data=True)
            link_author = data["link"].author_slow
            if not link_author._deleted:
                data["link_author"] = link_author
            else:
                data["link_author"] = DeletedUser()
            data["is_submitter"] = (author == link_author)

        # get the list of rule IDs that have already been performed
        already_performed = PerformedRulesByThing.get_already_performed(item)

        # stop checking removal rules as soon as one triggers
        for rule in self.removal_rules:
            if rule.is_unrepeatable and rule.unique_id in already_performed:
                continue

            if rule.check_item(item, data):
                rule.perform_actions(item, data)
                break

        # check all other rules, regardless of how many trigger
        for rule in self.nonremoval_rules:
            if rule.is_unrepeatable and rule.unique_id in already_performed:
                continue

            if rule.check_item(item, data):
                rule.perform_actions(item, data)


class RuleComponent(object):
    """Data related to individual key/value components making up a rule."""

    def __init__(self, valid_types=None, valid_values=None,
            valid_regex=None, valid_targets=None, default=None,
            component_type=None, aliases=None):
        """
        Keyword arguments:
        valid_types -- valid value types for this key
        valid_values -- if present, a set of valid options for this key
        valid_regex -- if present, a regex the value must satisfy
        valid_targets -- this key can only be defined for these target types
        default -- if this key isn't defined, default to this value
        component_type -- "action" or "check" if relevant for this key
        aliases -- other keys you can use (only if "normal" one isn't used)
        """
        self.valid_types = valid_types
        self.valid_values = valid_values
        if valid_regex:
            self.valid_regex = re.compile(valid_regex)
        else:
            self.valid_regex = None
        self.valid_targets = tup(valid_targets)
        self.default = default
        self.component_type = component_type
        self.aliases = aliases or []

    def validate(self, value):
        """Return whether a value satisfies this key's constraints."""
        if self.valid_types:
            if not isinstance(value, self.valid_types):
                return False
        if self.valid_values:
            if value not in self.valid_values:
                return False
        if self.valid_regex:
            if not self.valid_regex.search(str(value)):
                return False

        return True


class RuleTarget(object):
    """The conditions and actions that apply to an individual item."""

    # valid options for changing how a field value is searched for a match
    _match_regexes = {
        "full-exact": u"^%s$",
        "full-text": ur"^\W*%s\W*$",
        "includes": u"%s",
        "includes-word": ur"(?:^|\W|\b)%s(?:$|\W|\b)",
        "starts-with": u"^%s",
        "ends-with": u"%s$",
    }

    # full list of modifiers that can be applied to a match
    _match_modifiers = set(_match_regexes.keys()) | {
        "case-sensitive",
        "regex",
    }

    # valid fields to match against for each target object type
    _match_fields_by_type = {
        Link: {
            "id",
            "title",
            "domain",
            "url",
            "body",
            "media_author",
            "media_author_url",
            "media_title",
            "media_description",
            "flair_text",
            "flair_css_class",
        },
        Comment: {
            "id",
            "body",
        },
        Account: {
            "id",
            "name",
            "flair_text",
            "flair_css_class",
        }
    }

    # the match regex to default to for particular fields
    _match_field_defaults = {
        "id": "full-exact",
        "url": "includes",
        "media_author": "full-exact",
        "media_author_url": "includes",
        "flair_text": "full-exact",
        "flair_css_class": "full-exact",
    }

    _operator_regex = r"(==?|<|>)"
    _oper_int_regex = r"^(%s)?\s*-?\d+$" % _operator_regex
    _oper_period_regex = r"(%s)?\s*\d+\s*((minute|hour|day|week|month|year)s?)?$" % _operator_regex

    # all the possible components that can be defined for targets
    _potential_components = {
        "reports": RuleComponent(
            valid_types=int,
            valid_targets=(Link, Comment),
            component_type="check",
        ),
        "body_longer_than": RuleComponent(
            valid_types=int,
            valid_targets=(Link, Comment),
            component_type="check",
        ),
        "body_shorter_than": RuleComponent(
            valid_types=int,
            valid_targets=(Link, Comment),
            component_type="check",
        ),
        "ignore_blockquotes": RuleComponent(
            valid_types=bool,
            valid_targets=(Link, Comment),
        ),
        "action": RuleComponent(
            valid_values={"approve", "remove", "spam", "filter", "report"},
            valid_targets=(Link, Comment),
            component_type="action",
        ),
        "action_reason": RuleComponent(
            valid_types=basestring,
            valid_targets=(Link, Comment),
            aliases=["report_reason"],
        ),
        "set_flair": RuleComponent(
            valid_types=(basestring, list),
            valid_targets=(Link, Account),
            component_type="action",
        ),
        "overwrite_flair": RuleComponent(
            valid_types=bool,
            valid_targets=(Link, Account),
            default=False,
        ),
        "is_top_level": RuleComponent(
            valid_types=bool,
            valid_targets=Comment,
            component_type="check",
        ),
        "is_edited": RuleComponent(
            valid_types=bool,
            valid_targets=(Link, Comment),
            component_type="check",
        ),
        "set_locked": RuleComponent(
            valid_types=bool,
            valid_targets=Link,
            component_type="action",
        ),
        "set_sticky": RuleComponent(
            valid_types=(bool, int),
            valid_values=set(
                [True, False] + range(1, Subreddit.MAX_STICKIES+1)),
            valid_targets=Link,
            component_type="action",
        ),
        "set_nsfw": RuleComponent(
            valid_types=bool,
            valid_targets=Link,
            component_type="action",
        ),
        "set_contest_mode": RuleComponent(
            valid_types=bool,
            valid_targets=Link,
            component_type="action",
        ),
        "set_suggested_sort": RuleComponent(
            valid_values=CommentSortMenu.suggested_sort_options + ("best",),
            valid_targets=Link,
            component_type="action",
        ),
        "comment_karma": RuleComponent(
            valid_regex=_oper_int_regex,
            valid_targets=Account,
            component_type="check",
        ),
        "post_karma": RuleComponent(
            valid_regex=_oper_int_regex,
            valid_targets=Account,
            component_type="check",
            aliases=["link_karma"],
        ),
        "combined_karma": RuleComponent(
            valid_regex=_oper_int_regex,
            valid_targets=Account,
            component_type="check",
        ),
        "account_age": RuleComponent(
            valid_regex=_oper_period_regex,
            valid_targets=Account,
            component_type="check",
        ),
        "satisfy_any_threshold": RuleComponent(
            valid_types=bool,
            valid_targets=Account,
            default=False,
        ),
        "is_gold": RuleComponent(
            valid_types=bool,
            valid_targets=Account,
            component_type="check",
        ),
        "is_submitter": RuleComponent(
            valid_types=bool,
            valid_targets=Account,
            component_type="check",
        ),
        "is_contributor": RuleComponent(
            valid_types=bool,
            valid_targets=Account,
            component_type="check",
        ),
        "is_moderator": RuleComponent(
            valid_types=bool,
            valid_targets=Account,
            component_type="check",
        ),
    }

    def __init__(self, target_type, values, parent, approve_banned=True):
        """Create a RuleTarget that applies to objects of type target_type.

        Keyword arguments:
        target_type -- the type of object this will apply to
        values -- a dict of the values for each rule component
        approve_banned -- whether to approve banned users' posts
        
        """
        self.target_type = target_type
        self.parent = parent

        if not values:
            values = {}
        else:
            values = values.copy()

        self.set_values(values)

        # determine patterns that will be matched against fields
        self.match_patterns = self.get_match_patterns(values)
        self.matches = {}

        self.approve_banned = approve_banned

    def set_values(self, values):
        """Set values for all possible rule components on the RuleTarget.

        If a value for a valid component for this target_type was not specified,
        set to the default value for that component. Set attr values to None for
        all components that only apply to other types."""
        self.checks = set()
        self.actions = set()

        for key, component in self._potential_components.iteritems():
            if self.target_type in component.valid_targets:
                # pop the key and all aliases out of the values
                # but only keep the first value we find
                value = None
                sources = [key] + component.aliases
                for source in sources:
                    from_source = values.pop(source, None)
                    if value is None:
                        value = from_source

                if value is not None:
                    if not component.validate(value):
                        raise AutoModeratorSyntaxError(
                            "invalid value for `%s`: `%s`" % (key, value),
                            self.parent.yaml,
                        )
                            
                    setattr(self, key, value)

                    if component.component_type == "check":
                        self.checks.add(key)
                    elif component.component_type == "action":
                        self.actions.add(key)
                else:
                    setattr(self, key, component.default)
            else:
                if key in values:
                    raise AutoModeratorRuleTypeError(
                        "Can't use `%s` on this type" % key,
                        self.parent.yaml,
                    )

                setattr(self, key, None)

        # special handling for set_flair
        if self.set_flair is not None:
            if isinstance(self.set_flair, basestring):
                self.set_flair = [self.set_flair, ""]

            # handle 0 or 1 item lists
            while len(self.set_flair) < 2:
                self.set_flair.append("")

            self.set_flair = {
                "text": self.set_flair[0],
                "class": self.set_flair[1],
            }

        # ugly hack to allow people to use "best" instead of "confidence"
        if self.set_suggested_sort == "best":
            self.set_suggested_sort = "confidence"

    _match_field_key_regex = re.compile(r"^(~?[^\s(]+)\s*(?:\((.+)\))?$")
    def parse_match_fields_key(self, key):
        """Parse a key defining a match against fields into its components."""
        matches = self._match_field_key_regex.match(key)
        if not matches:
            raise AutoModeratorSyntaxError(
                "Invalid search check: `%s`" % key,
                self.parent.yaml,
            )
        parsed = {}
        name = matches.group(1)

        all_valid_fields = set.union(*self._match_fields_by_type.values())
        fields = [field.strip()
            for field in name.lstrip("~").partition("#")[0].split("+")]
        for field in fields:
            if field not in all_valid_fields:
                raise AutoModeratorSyntaxError(
                    "Unknown field: `%s`" % field,
                    self.parent.yaml,
                )

        valid_fields = self._match_fields_by_type[self.target_type]
        fields = {field for field in fields if field in valid_fields}

        if not fields:
            raise AutoModeratorRuleTypeError(
                "Can't search `%s` on this type" % key,
                self.parent.yaml,
            )

        modifiers = matches.group(2)
        if modifiers:
            modifiers = [mod.strip() for mod in modifiers.split(",")]
        else:
            modifiers = []
        for mod in modifiers:
            if mod not in self._match_modifiers:
                raise AutoModeratorSyntaxError(
                    "Unknown modifier `%s` in `%s`" % (mod, key),
                    self.parent.yaml,
                )

        return {
            "name": name,
            "fields": fields,
            "modifiers": modifiers,
            "match_success": not name.startswith("~"),
        }

    def get_match_patterns(self, values):
        """Generate the regexes used to match against fields."""
        self.match_fields = set()
        match_patterns = {}
        
        for key in values:
            parsed_key = self.parse_match_fields_key(key)

            # add fields to the list of fields we're going to check
            self.match_fields |= parsed_key["fields"]

            match_values = values[key]
            if not match_values:
                continue
            if not isinstance(match_values, list):
                match_values = list((match_values,))
            # cast all values to strings in case any numbers were included
            match_values = [unicode(val) for val in match_values]

            # escape regex special chars unless this is a regex
            if "regex" not in parsed_key["modifiers"]:
                match_values = [re.escape(val) for val in match_values]

            value_str = u"(%s)" % "|".join(match_values)

            for mod in parsed_key["modifiers"]:
                if mod in self._match_regexes:
                    match_mod = mod
                    break
            else:
                if len(parsed_key["fields"]) == 1:
                    field = list(parsed_key["fields"])[0]
                    # default to handling subdomains for checks against domain only
                    if field == "domain":
                        value_str = ur"(?:.*?\.)?" + value_str
                    match_mod = self._match_field_defaults.get(
                        field, "includes-word")
                else:
                    match_mod = "includes-word"

            pattern = self._match_regexes[match_mod] % value_str

            flags = re.DOTALL | re.UNICODE
            if "case-sensitive" not in parsed_key["modifiers"]:
                flags |= re.IGNORECASE

            try:
                match_patterns[key] = re.compile(pattern, flags)
            except Exception as e:
                raise AutoModeratorSyntaxError(
                    "Generated an invalid regex for `%s`: %s" % (key, e),
                    self.parent.yaml,
                )

        return match_patterns

    @property
    def needs_media_data(self):
        """Whether the component requires data from the media embed."""
        for key in self.match_patterns:
            fields = self.parse_match_fields_key(key)["fields"]
            if all(field.startswith("media_") for field in fields):
                return True

        # check if any of the fields that support placeholders have media ones
        potential_placeholders = [self.action_reason]
        if self.set_flair:
            potential_placeholders.extend(self.set_flair.values())
        if any(text and "{{media_" in text for text in potential_placeholders):
            return True

        return False

    def check_item(self, item, data):
        """Return whether an item satisfies all of the defined conditions."""
        if not self.check_nonpattern_conditions(item, data):
            return False

        if isinstance(item, Account):
            if not self.check_account_thresholds(item, data):
                return False

        if not self.check_match_patterns(item, data):
            return False
            
        return True

    def check_nonpattern_conditions(self, item, data):
        """Check all the non-regex conditions against the item."""
        # check number of reports if necessary
        if self.reports and item.reported < self.reports:
            return False

        if hasattr(item, "body"):
            body = self.get_field_value_from_item(item, data, "body")

            # check body length restrictions if necessary
            if (self.body_longer_than is not None or
                        self.body_shorter_than is not None):
                # remove non-word chars on either end of the string
                pattern = re.compile(r'^\W+', re.UNICODE)
                body_text = pattern.sub('', body)
                pattern = re.compile(r'\W+$', re.UNICODE)
                body_text = pattern.sub('', body_text)

                if (self.body_longer_than is not None and
                        len(body_text) <= self.body_longer_than):
                    return False
                if (self.body_shorter_than is not None and
                        len(body_text) >= self.body_shorter_than):
                    return False

        # check whether it's a reply or top-level comment if necessary
        if self.is_top_level is not None:
            item_is_top_level = (not item.parent_id)
            if self.is_top_level != item_is_top_level:
                return False

        # check whether it's been edited if necessary
        if self.is_edited is not None:
            if self.is_edited != hasattr(item, "editted"):
                return False

        if self.is_submitter is not None:
            # default to True in case someone happens to check is_submitter
            # on a submission's author by accident
            is_submitter = data.get("is_submitter", True)
            if self.is_submitter != is_submitter:
                return False

        if self.is_moderator is not None:
            is_mod = bool(data["subreddit"].is_moderator(item))
            if is_mod != self.is_moderator:
                return False

        if self.is_contributor is not None:
            is_contrib = bool(data["subreddit"].is_contributor(item))
            if is_contrib != self.is_contributor:
                return False

        if self.is_gold is not None:
            if item.gold != self.is_gold:
                return False

        return True

    def check_account_thresholds(self, account, data):
        """Check karma/age thresholds against an account."""
        thresholds = ["comment_karma", "post_karma", "combined_karma",
            "account_age"]
        # figure out which thresholds/values we need to check against
        checks = {}
        for threshold in thresholds:
            compare_value = getattr(self, threshold, None)
            if compare_value is not None:
                checks[threshold] = str(compare_value)

        # if we don't need to actually check anything, just return True
        if not checks:
            return True

        # banned accounts should never satisfy threshold checks
        if account._spam:
            return False

        for check, compare_value in checks.iteritems():
            match = re.match(self._operator_regex, compare_value)
            if match:
                operator = match.group(1)
                compare_value = compare_value[len(operator):].strip()
            if not match or operator == "==":
                operator = "="

            # special handling for time period comparison value
            if check == "account_age":
                # if it's just a number, default to days
                try:
                    compare_value = int(compare_value)
                    compare_value = "%s days" % compare_value
                except ValueError:
                    pass

                compare_value = timeinterval_fromstr(compare_value)
            else:
                compare_value = int(compare_value)

            value = self.get_field_value_from_item(account, data, check)

            if operator == "<":
                result = value < compare_value
            elif operator == ">":
                result = value > compare_value
            elif operator == "=":
                result = value == compare_value

            # if satisfy_any_threshold is True, we can return True as soon
            # as a single check is successful. If it's False, they all need
            # to be satisfied, so we can return False as soon as one fails.
            if result == self.satisfy_any_threshold:
                return result

        # if we make it to here, the return statement inside the loop was
        # never triggered, so that means that if satisfy_any_threshold is
        # True, all the checks must have been False, and if it's False
        # they all must have been True
        return (not self.satisfy_any_threshold)

    def check_match_patterns(self, item, data):
        """Check all the regex patterns against the item's field values."""
        if len(self.match_patterns) == 0:
            return True

        self.matches = {}
        checked_anything = False
        for key, match_pattern in self.match_patterns.iteritems():
            match = None
            parsed_key = self.parse_match_fields_key(key)

            if isinstance(item, Link) and not item.is_self:
                # don't check body for link submissions
                parsed_key["fields"].discard("body")
            elif isinstance(item, Link) and item.is_self:
                # don't check url for text submissions
                parsed_key["fields"].discard("url")

            for source in parsed_key["fields"]:
                string = self.get_field_value_from_item(item, data, source)
                match = match_pattern.search(string)
                checked_anything = True

                if match:
                    self.matches[parsed_key["name"]] = match
                    break

            if bool(match) != parsed_key["match_success"]:
                return False

        # if we didn't actually check anything, that means that all checks
        # must have been discarded (for example, url checks when the item
        # is a self-post). That should be considered a failure.
        if checked_anything:
            return True
        else:
            return False

    def perform_actions(self, item, data):
        """Execute the defined actions on the item."""
        # only approve if it's currently removed or reported, and hasn't
        # been removed by a moderator
        ban_info = getattr(item, "ban_info", {})
        mod_banned = ban_info.get("moderator_banned")
        should_approve = ((item._spam and not mod_banned) or 
            (self.reports and item.reported))
        if self.action == "approve" and should_approve:
            approvable_author = not data["author"]._spam or self.approve_banned
            if approvable_author:
                # TODO: shouldn't need to set train_spam/insert values
                was_removed = item._spam
                admintools.unspam(item, moderator_unbanned=True,
                    unbanner=ACCOUNT.name, train_spam=True, insert=item._spam)

                log_action = None
                if isinstance(item, Link):
                    log_action = "approvelink"
                elif isinstance(item, Comment):
                    log_action = "approvecomment"

                if log_action:
                    if self.action_reason:
                        reason = replace_placeholders(
                            self.action_reason, data, self.parent.matches)
                    else:
                        reason = "unspam" if was_removed else "approved"
                    ModAction.create(data["subreddit"], ACCOUNT, log_action,
                        target=item, details=reason)

                g.stats.simple_event("automoderator.approve")

        if self.action in {"remove", "spam", "filter"}:
            spam = (self.action == "spam")
            keep_in_modqueue = (self.action == "filter")
            admintools.spam(
                item,
                auto=keep_in_modqueue,
                moderator_banned=True,
                banner=ACCOUNT.name,
                train_spam=spam,
            )

            # TODO: shouldn't need to do all of this here
            log_action = None
            if isinstance(item, Link):
                log_action = "removelink"
            elif isinstance(item, Comment):
                log_action = "removecomment"
                queries.unnotify(item)

            if log_action:
                if self.action_reason:
                    reason = replace_placeholders(
                        self.action_reason, data, self.parent.matches)
                else:
                    reason = "spam" if spam else "remove"
                ModAction.create(data["subreddit"], ACCOUNT, log_action,
                    target=item, details=reason)

            g.stats.simple_event("automoderator.%s" % self.action)

        if self.action == "report":
            if self.action_reason:
                reason = replace_placeholders(
                    self.action_reason, data, self.parent.matches)
            else:
                reason = None
            Report.new(ACCOUNT, item, reason)
            admintools.report(item)

            g.stats.simple_event("automoderator.report")

        if self.set_nsfw is not None:
            if item.over_18 != self.set_nsfw:
                item.over_18 = self.set_nsfw
                item._commit()
                # TODO: shouldn't need to do this here
                log_details = None
                if not self.set_nsfw:
                    log_details = "remove"
                ModAction.create(data["subreddit"], ACCOUNT, "marknsfw",
                    target=item, details=log_details)
                item.update_search_index()

        if self.set_contest_mode is not None:
            if item.contest_mode != self.set_contest_mode:
                item.contest_mode = self.set_contest_mode
                item._commit()

        if self.set_locked is not None:
            if item.locked != self.set_locked:
                item.locked = self.set_locked
                item._commit()

                log_action = 'lock' if self.set_locked else 'unlock'
                ModAction.create(data["subreddit"], ACCOUNT, log_action,
                    target=item)

        if self.set_sticky is not None:
            if item.is_stickied(data["subreddit"]) != bool(self.set_sticky):
                if self.set_sticky:
                    # if set_sticky is a bool, don't specify a slot
                    if isinstance(self.set_sticky, bool):
                        num = None
                    else:
                        num = self.set_sticky

                    data["subreddit"].set_sticky(item, ACCOUNT, num)
                else:
                    data["subreddit"].remove_sticky(item, ACCOUNT)

        if self.set_suggested_sort is not None:
            if not item.suggested_sort:
                item.suggested_sort = self.set_suggested_sort
                item._commit()

                # TODO: shouldn't need to do this here
                ModAction.create(data["subreddit"], ACCOUNT,
                    action="setsuggestedsort", target=item)

        if self.set_flair:
            # don't overwrite existing flair unless that was specified
            can_update_flair = False
            if isinstance(item, Link):
                if item.flair_text or item.flair_css_class:
                    can_update_flair = self.overwrite_flair
                else:
                    can_update_flair = True
            elif isinstance(item, Account):
                if data["subreddit"].is_flair(item):
                    can_update_flair = self.overwrite_flair
                else:
                    can_update_flair = True

            if can_update_flair:
                text = replace_placeholders(
                    self.set_flair["text"], data, self.parent.matches)
                cls = replace_placeholders(
                    self.set_flair["class"], data, self.parent.matches)

                # apply same limits as API to text and class
                text = text[:64]
                cls = re.sub(r"[^\w -]", "", cls)
                classes = cls.split()[:10]
                classes = [cls[:100] for cls in classes]
                cls = " ".join(classes)

                if isinstance(item, Link):
                    item.set_flair(text, cls)
                elif isinstance(item, Account):
                    item.set_flair(data["subreddit"], text, cls)

                g.stats.simple_event("automoderator.set_flair")

    def get_field_value_from_item(self, item, data, field):
        """Get a field value from the item to check against."""
        value = ''
        if field == 'id':
            value = item._id36
        elif field == 'body':
            # pull out the item's body and remove blockquotes if necessary
            body = item.body
            if self.ignore_blockquotes:
                body = '\n'.join(
                    line for line in body.splitlines()
                    if not line.startswith('>') and
                    len(line) > 0)
            value = body
        elif field == 'domain':
            if not item.is_self:
                value = item.link_domain()
            else:
                value = "self." + data["subreddit"].name
        elif (field.startswith('media_') and
                getattr(item, 'media_object', None)):
            try:
                if field == 'media_author':
                    value = item.media_object['oembed']['author_name']
                elif field == 'media_author_url':
                    value = item.media_object['oembed']['author_url']
                elif field == 'media_description':
                    value = item.media_object['oembed']['description']
                elif field == 'media_title':
                    value = item.media_object['oembed']['title']
            except KeyError:
                value = ''
        elif field == "account_age":
            value = item._age
        elif field == "post_karma":
            value = max(item.link_karma, g.link_karma_display_floor)
        elif field == "comment_karma":
            value = max(item.comment_karma, g.comment_karma_display_floor)
        elif field == "combined_karma":
            post = self.get_field_value_from_item(item, data, "post_karma")
            comment = self.get_field_value_from_item(item, data, "comment_karma")
            value = post + comment
        elif field == "flair_text" and isinstance(item, Account):
            value = item.flair_text(data["subreddit"]._id, obey_disabled=True)
        elif field == "flair_css_class" and isinstance(item, Account):
            value = item.flair_css_class(
                data["subreddit"]._id, obey_disabled=True)
        else:
            value = getattr(item, field, "")

        return value


class Rule(object):
    """The overall rule, made up of 1 or more RuleTargets."""
    _valid_type_map = {
        "comment": Comment,
        "submission": Link,
        "link submission": Link,
        "text submission": Link,
    }

    _valid_components = {
        "type": RuleComponent(
            valid_values=set(_valid_type_map.keys() + ["any"]),
            default="any",
            component_type="check",
        ),
        "priority": RuleComponent(valid_types=int, default=0),
        "moderators_exempt": RuleComponent(valid_types=bool),
        "comment": RuleComponent(valid_types=basestring, component_type="action"),
        "comment_stickied": RuleComponent(valid_types=bool, default=False),
        "modmail": RuleComponent(valid_types=basestring, component_type="action"),
        "modmail_subject": RuleComponent(
            valid_types=basestring,
            default="AutoModerator notification",
        ),
        "message": RuleComponent(valid_types=basestring, component_type="action"),
        "message_subject": RuleComponent(
            valid_types=basestring,
            default="AutoModerator notification",
        ),
    }

    def __init__(self, values, yaml_source=None):
        values = lowercase_keys_recursively(values)

        if yaml_source:
            self.yaml = yaml_source
        else:
            self.yaml = yaml.dump(values)

        self.unique_id = md5(self.yaml.encode("utf-8")).hexdigest()

        self.checks = set()
        self.actions = set()

        # pop off the values that are special for the top level
        for key, component in self._valid_components.iteritems():
            if key in values:
                value = values.pop(key)
                if not component.validate(value):
                    raise AutoModeratorSyntaxError(
                        "invalid value for `%s`: `%s`" % (key, value),
                        self.yaml,
                    )
                setattr(self, key, value)

                if component.component_type == "check":
                    self.checks.add(key)
                elif component.component_type == "action":
                    self.actions.add(key)
            else:
                setattr(self, key, component.default)

        self.base_target_type = self._valid_type_map[self.type]

        self.targets = {}

        author = values.pop("author", None)
        if not isinstance(author, dict):
            # if they just specified string(s) for author
            # that's the same as checking against name
            if isinstance(author, (list, basestring)):
                author = {"name": author}
            else:
                author = {}

        # support string(s) for ~author as well
        not_author = values.pop("~author", None)
        if isinstance(not_author, (list, basestring)):
            author["~name"] = not_author

        approve_banned = False
        if author:
            self.targets["author"] = RuleTarget(Account, author, self)
            # only approve banned users' posts if an author name check is done
            approve_banned = ("name" in self.targets["author"].match_fields)

        parent_submission = values.pop("parent_submission", None)
        if parent_submission:
            if self.base_target_type == Comment:
                target = RuleTarget(Link, parent_submission, self)
                self.targets["parent_submission"] = target
            else:
                raise AutoModeratorRuleTypeError(
                    "can't specify `parent_submission` on a submission",
                    self.yaml,
                )

        # TODO: in the future, "imported" rules can also exist here as targets

        # send all the remaining values through to the base target
        self.targets["base"] = RuleTarget(
            self.base_target_type,
            values,
            self,
            approve_banned=approve_banned,
        )

    @property
    def is_removal_rule(self):
        """Whether the rule could result in removing the item."""
        return self.targets["base"].action in {"spam", "remove", "filter"}

    @property
    def is_inapplicable_to_mods(self):
        """Whether the rule should not be applied to moderators' posts."""
        if self.moderators_exempt is not None:
            return self.moderators_exempt

        if self.is_removal_rule or self.targets["base"].action == "report":
            return True

        return False

    @property
    def is_unrepeatable(self):
        """Whether repeating the rule's actions is undesirable."""
        # we don't want to repeatedly execute rules that post or message
        if self.comment or self.modmail or self.message:
            return True

        # duplicate reports won't go through anyway
        if self.targets["base"].action == "report":
            return True

        return False

    @property
    def needs_media_data(self):
        """Whether the rule requires data from the media embed."""
        for attr in ("comment", "modmail", "message"):
            text = getattr(self, attr, None)
            if text and "{{media_" in text:
                return True

        if any(comp.needs_media_data for comp in self.targets.values()):
            return True

        return False

    @property
    def matches(self):
        return self.targets["base"].matches

    def has_any_checks(self, targets_only=False):
        for target in self.targets.values():
            if target.checks or target.match_fields:
                return True

        if targets_only:
            return False

        return bool(self.checks)

    def has_any_actions(self, targets_only=False):
        for target in self.targets.values():
            if target.actions:
                return True

        if targets_only:
            return False

        return bool(self.actions)

    def get_target_item(self, item, data, key):
        """Return the subject for a particular target's conditions/actions."""
        if key == "base":
            return item
        elif key == "author":
            return data["author"]
        elif key == "parent_submission":
            return data["link"]

    def item_is_correct_type(self, item):
        """Check that the item is the correct type to apply this rule to."""
        if not isinstance(item, self.base_target_type):
            return False

        if self.type == "link submission":
            return not item.is_self
        elif self.type == "text submission":
            return item.is_self

        return True

    def should_check_item(self, item, data):
        """Return if it is necessary to check this rule against the item."""
        if not self.item_is_correct_type(item):
            return False

        # don't check comments made by this account to prevent loops
        if isinstance(item, Comment) and data["author"] == ACCOUNT:
            return False

        if (self.is_inapplicable_to_mods and
                data["subreddit"].is_moderator(data["author"])):
            return False

        # if the item is already removed by a moderator, no need to
        # check any rules
        ban_info = getattr(item, "ban_info", {})
        if item._spam and ban_info.get("moderator_banned"):
            return False

        if self.is_removal_rule:
            # don't consider removing items another moderator has
            # already approved
            if (getattr(item, "verdict", "").endswith("-approved") and
                    ban_info.get("unbanner") != ACCOUNT.name):
                return False

        if self.needs_media_data:
            if isinstance(item, Link) and not item.media_object:
                return False
            elif isinstance(item, Comment) and not data["link"].media_object:
                return False

        return True

    def check_item(self, item, data):
        """Return whether the item satisfies all the targets' conditions."""
        if not self.should_check_item(item, data):
            return False

        g.stats.simple_event("automoderator.check_rule")

        for key, target in self.targets.iteritems():
            target_item = self.get_target_item(item, data, key)
            if not target.check_item(target_item, data):
                return False

        return True

    def perform_actions(self, item, data):
        """Execute all the rule's actions against the item."""
        for key, target in self.targets.iteritems():
            target_item = self.get_target_item(item, data, key)
            target.perform_actions(target_item, data)

        if self.comment:
            comment = self.build_message(self.comment, item, data, disclaimer=True)

            # TODO: shouldn't have to do all this manually
            if isinstance(item, Comment):
                link = data["link"]
                parent_comment = item
            else:
                link = item
                parent_comment = None
            new_comment, inbox_rel = Comment._new(
                ACCOUNT, link, parent_comment, comment, None)
            new_comment.distinguished = "yes"
            new_comment.sendreplies = False
            new_comment._commit()

            # If the comment isn't going to be put into the user's inbox
            # due to them having sendreplies disabled, force it. For a normal
            # mod, distinguishing the comment would do this, but it doesn't
            # happen here since we're setting .distinguished directly.
            if isinstance(item, Link) and not inbox_rel:
                inbox_rel = Inbox._add(data["author"], new_comment, "selfreply")

            queries.new_comment(new_comment, inbox_rel)

            if self.comment_stickied:
                try:
                    link.set_sticky_comment(new_comment, set_by=ACCOUNT)
                except RedditError:
                    # This comment isn't valid to set to sticky, ignore
                    pass

            g.stats.simple_event("automoderator.comment")

        if self.modmail:
            message = self.build_message(self.modmail, item, data, permalink=True)
            subject = replace_placeholders(
                self.modmail_subject, data, self.matches)
            subject = subject[:100]

            new_message, inbox_rel = Message._new(ACCOUNT, data["subreddit"],
                subject, message, None)
            new_message.distinguished = "yes"
            new_message._commit()
            queries.new_message(new_message, inbox_rel)

            g.stats.simple_event("automoderator.modmail")

        if self.message and not data["author"]._deleted:
            message = self.build_message(self.message, item, data,
                disclaimer=True, permalink=True)
            subject = replace_placeholders(
                self.message_subject, data, self.matches)
            subject = subject[:100]

            new_message, inbox_rel = Message._new(ACCOUNT, data["author"],
                subject, message, None)
            queries.new_message(new_message, inbox_rel)

            g.stats.simple_event("automoderator.message")

        PerformedRulesByThing.mark_performed(item, self)

    def build_message(self, text, item, data, disclaimer=False, permalink=False):
        """Generate the text to post as a comment or send as a message."""
        message = text
        if disclaimer:
            message = "%s\n\n%s" % (message, DISCLAIMER)
        if permalink and "{{permalink}}" not in message:
            message = "{{permalink}}\n\n%s" % message
        message = replace_placeholders(message, data, self.matches)

        message = VMarkdown('').run(message)

        return message[:10000]


def run():
    @g.stats.amqp_processor("automoderator_q")
    def process_message(msg):
        if not ACCOUNT:
            return

        fullname = msg.body
        with g.make_lock("automoderator", "automod_" + fullname, timeout=5):
            item = Thing._by_fullname(fullname, data=True)
            if not isinstance(item, (Link, Comment)):
                return

            subreddit = item.subreddit_slow
            
            wiki_page_id = wiki_id(subreddit._id36, "config/automoderator")
            wiki_page_fullname = "WikiPage_%s" % wiki_page_id
            last_edited = LastModified.get(wiki_page_fullname, "Edit")
            if not last_edited:
                return

            # initialize rules for the subreddit if we haven't already
            # or if the page has been edited since we last initialized
            need_to_init = False
            if subreddit._id not in rules_by_subreddit:
                need_to_init = True
            else:
                rules = rules_by_subreddit[subreddit._id]
                if last_edited > rules.init_time:
                    need_to_init = True

            if need_to_init:
                timer = g.stats.get_timer("automoderator.init_ruleset")
                timer.start()

                wp = WikiPage.get(subreddit, "config/automoderator")
                timer.intermediate("get_wiki_page")

                try:
                    rules = Ruleset(wp.content, timer)
                except (AutoModeratorSyntaxError, AutoModeratorRuleTypeError):
                    print "ERROR: Invalid config in /r/%s" % subreddit.name
                    return

                rules_by_subreddit[subreddit._id] = rules

                timer.stop()

            if not rules:
                return

            try:
                TimeoutFunction(rules.apply_to_item, 2)(item)
                print "Checked %s from /r/%s" % (item, subreddit.name)
            except TimeoutFunctionException:
                print "Timed out on %s from /r/%s" % (item, subreddit.name)
            except KeyboardInterrupt:
                raise
            except:
                print "Error on %s from /r/%s" % (item, subreddit.name)
                print traceback.format_exc()

    amqp.consume_items('automoderator_q', process_message, verbose=False)
