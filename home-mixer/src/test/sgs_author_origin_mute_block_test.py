"""Decision table for SGSAuthorFilter mute and block ids.

SGSAuthorFilter asks Social Graph for users the viewer mutes, blocks, or is
blocked by, then drops a candidate when one of those ids is on the card.

AuthorIdFeature is the poster. On a retweet that is the retweeter. The
original author is SourceUserIdFeature. The quoted author is QuotedUserIdFeature.

FeedbackFatigueFilter already uses the original author for "see fewer" on
tweets. This filter used only AuthorId, so a followed user retweeting or
quoting a muted or blocked account still served.

home-mixer has no local Scala harness for this filter (Social Graph client).
This file is the decision table.
"""


def user_ids_for_sgs(author_id, source_user_id, quoted_user_id):
    ids = []
    for user_id in (author_id, source_user_id, quoted_user_id):
        if user_id is not None and user_id not in ids:
            ids.append(user_id)
    return ids


def should_drop(author_id, source_user_id, quoted_user_id, muted_or_blocked):
    return any(
        user_id in muted_or_blocked
        for user_id in user_ids_for_sgs(author_id, source_user_id, quoted_user_id)
    )


def should_drop_before(author_id, source_user_id, quoted_user_id, muted_or_blocked):
    del source_user_id, quoted_user_id
    return author_id is not None and author_id in muted_or_blocked


def main():
    poster = 10
    origin = 99
    quoted = 77
    other = 5
    muted = {origin, quoted}

    cases = [
        # kind, author, source, quoted, before drop, after drop
        ("original, poster muted", origin, None, None, True, True),
        ("original, poster clear", poster, None, None, False, False),
        ("retweet, origin muted", poster, origin, None, False, True),
        ("retweet, poster muted", origin, other, None, True, True),
        ("retweet, both clear", poster, other, None, False, False),
        ("quote, quoted muted", poster, None, quoted, False, True),
        ("quote, poster muted", quoted, None, other, True, True),
        ("quote, both clear", poster, None, other, False, False),
        ("retweet of quote, origin muted", poster, origin, other, False, True),
        ("retweet of quote, quoted muted", poster, other, quoted, False, True),
        ("no ids", None, None, None, False, False),
    ]

    for name, author, source, quoted_id, want_before, want_after in cases:
        got_before = should_drop_before(author, source, quoted_id, muted)
        got_after = should_drop(author, source, quoted_id, muted)
        assert got_before == want_before, f"{name}: before {got_before} != {want_before}"
        assert got_after == want_after, f"{name}: after {got_after} != {want_after}"

    blocked_by_origin = {origin}
    assert not should_drop_before(poster, origin, None, blocked_by_origin)
    assert should_drop(poster, origin, None, blocked_by_origin)

    print("ok")


if __name__ == "__main__":
    main()
