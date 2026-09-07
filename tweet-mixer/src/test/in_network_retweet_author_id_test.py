"""Decision table for in-network retweet AuthorId.

Earlybird metadata:
  fromUserId              = user who posted this tweet (retweeter on a retweet)
  referencedTweetAuthorId = original author on a retweet

Haplolite cache write (EarlybirdRealtimeCGTweetCandidateSource.convertToTimelineEntry):
  userId       = fromUserId
  sourceUserId = referencedTweetAuthorId when the tweet is a retweet

AuthorIdFeature must be the poster. Home-mixer HasAuthorFilter / InNetworkFeature
and TweetResult.authorId all treat it as the retweeter, not the original author.

This file is the harness. tweet-mixer has no local Scala test target for these
transformers (Earlybird / Haplolite thrift clients).
"""


def earlybird_author_id(is_retweet, from_user_id, referenced_tweet_author_id):
    del is_retweet, referenced_tweet_author_id
    return from_user_id


def haplolite_author_id(is_retweet, user_id, source_user_id):
    del is_retweet, source_user_id
    return user_id


def in_network(author_id, viewer_id, followed_ids):
    return author_id == viewer_id or author_id in followed_ids


def main():
    retweeter = 10
    original = 99
    viewer = 1
    followed = {10}

    cases = [
        ("retweet", True, retweeter, original, retweeter, True),
        ("original tweet", False, retweeter, 0, retweeter, True),
        ("reply", False, retweeter, original, retweeter, True),
    ]

    for name, is_rt, from_user, referenced, want_author, want_in_network in cases:
        got = earlybird_author_id(is_rt, from_user, referenced)
        assert got == want_author, f"earlybird {name}: author {got} != {want_author}"
        haplo = haplolite_author_id(is_rt, from_user, referenced if is_rt else None)
        assert haplo == want_author, f"haplolite {name}: author {haplo} != {want_author}"
        got_in_network = in_network(got, viewer, followed)
        assert got_in_network == want_in_network, (
            f"{name}: in-network {got_in_network} != {want_in_network}"
        )

    # A followed user retweets someone the viewer does not follow.
    # Before: AuthorId = original (99) → InNetworkFeature = false → OON filters.
    # After:  AuthorId = retweeter (10) → in-network, keep.
    swapped = original
    assert not in_network(swapped, viewer, followed)
    assert in_network(earlybird_author_id(True, retweeter, original), viewer, followed)

    print("ok")


if __name__ == "__main__":
    main()
