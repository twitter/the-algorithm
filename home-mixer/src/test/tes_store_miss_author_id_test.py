"""Decision table for TES / cache miss identity ids.

home-mixer has no local Scala harness for TweetEntityServiceFeatureHydrator
(Strato / Tweetypie clients). This file is the keep-or-drop table for:

- featureMapOnStoreMiss
- ScoredTweetsTweetMixerResponseFeatureTransformer author copy
- HasAuthorFilter

Tweet-mixer TweetResult.authorId is the post author. tweetMetadata.authorId is
the signal author (the user whose like or follow sourced the candidate). Those
are different ids.
"""


def feature_map_on_store_miss(existing):
    """Keep source ids on TES/cache miss. Empty defaults only when the source had none."""
    return {
        "author_id": existing.get("author_id"),
        "source_tweet_id": existing.get("source_tweet_id"),
        "source_user_id": existing.get("source_user_id"),
        "in_reply_to_tweet_id": existing.get("in_reply_to_tweet_id"),
        "is_retweet": existing.get("is_retweet", False),
    }


def tes_author_id(tes_hit, tes_author_id, existing_author_id):
    if tes_hit:
        return tes_author_id
    return feature_map_on_store_miss({"author_id": existing_author_id})["author_id"]


def tweet_mixer_author_feature(tweet_result_author_id, signal_author_id):
    """Use TweetResult.authorId, not tweetMetadata.authorId."""
    return tweet_result_author_id


def has_author_filter_keeps(author_id):
    return author_id is not None


def test_tes_miss_keeps_existing_author():
    assert tes_author_id(tes_hit=False, tes_author_id=None, existing_author_id=42) == 42


def test_tes_miss_without_existing_author_stays_empty():
    assert tes_author_id(tes_hit=False, tes_author_id=None, existing_author_id=None) is None


def test_tes_hit_uses_store_author():
    assert tes_author_id(tes_hit=True, tes_author_id=7, existing_author_id=42) == 7


def test_tes_miss_keeps_source_ids():
    kept = feature_map_on_store_miss(
        {
            "author_id": 42,
            "source_tweet_id": 99,
            "source_user_id": 8,
            "in_reply_to_tweet_id": 11,
            "is_retweet": True,
        }
    )
    assert kept["source_tweet_id"] == 99
    assert kept["source_user_id"] == 8
    assert kept["in_reply_to_tweet_id"] == 11
    assert kept["is_retweet"] is True


def test_old_default_map_would_drop():
    # Before the fix, miss wrote AuthorId=None. HasAuthorFilter then removed the post.
    old_author = None
    assert has_author_filter_keeps(old_author) is False
    assert has_author_filter_keeps(42) is True


def test_tweet_mixer_uses_post_author_not_signal_author():
    assert tweet_mixer_author_feature(tweet_result_author_id=42, signal_author_id=1001) == 42
    assert tweet_mixer_author_feature(tweet_result_author_id=None, signal_author_id=1001) is None


if __name__ == "__main__":
    tests = [
        test_tes_miss_keeps_existing_author,
        test_tes_miss_without_existing_author_stays_empty,
        test_tes_hit_uses_store_author,
        test_tes_miss_keeps_source_ids,
        test_old_default_map_would_drop,
        test_tweet_mixer_uses_post_author_not_signal_author,
    ]
    for test in tests:
        test()
        print("ok", test.__name__)
    print("all passed")
