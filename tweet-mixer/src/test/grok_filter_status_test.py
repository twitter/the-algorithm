"""Decision table for tweet-mixer GrokFilter store-miss handling.

GrokFilter drops a candidate when GrokFilterFeature is true. The hydrator
reads ColdStartPostsMetadata from Manhattan and used to treat a missing row
the same as Tier1Ineligible, so ordinary posts with no cold-start metadata
were hidden from Home recommended tweets.

Sibling home-mixer IsColdStartPostFeatureHydrator treats a store miss as
false (not a cold-start post). The hydrator's own exception path and
GrokFilter's getOrElse default also keep the post. Only the miss branch
failed closed.

Keep this table in sync with GrokFilterFeatureHydrator.shouldDrop.
"""

TIER1_INELIGIBLE = "Tier1Ineligible"
TIER1_INELIGIBLE_HIGH_QUALITY = "Tier1IneligibleHighQuality"
OTHER_CONFIRMED_STATUS = "OtherConfirmedStatus"


def should_drop(status):
    """Mirror of GrokFilterFeatureHydrator.shouldDrop."""
    return status == TIER1_INELIGIBLE


def should_drop_before(status):
    """The catch-all that treated a miss as hidden."""
    if status is not None and status != TIER1_INELIGIBLE:
        return False
    return True


CASES = (
    ("no Manhattan row", None, False),
    ("Tier1Ineligible", TIER1_INELIGIBLE, True),
    ("other confirmed status", OTHER_CONFIRMED_STATUS, False),
    ("Tier1IneligibleHighQuality stays visible", TIER1_INELIGIBLE_HIGH_QUALITY, False),
)


def test_store_miss_is_not_hidden():
    for name, status, expected in CASES:
        got = should_drop(status)
        assert got is expected, "%s: expected %s, got %s" % (name, expected, got)


def test_old_catch_all_hid_store_misses():
    assert should_drop_before(None) is True
    assert should_drop_before(TIER1_INELIGIBLE) is True
    assert should_drop_before(OTHER_CONFIRMED_STATUS) is False
    assert should_drop(None) is False


if __name__ == "__main__":
    test_store_miss_is_not_hidden()
    test_old_catch_all_hid_store_misses()
    print("ok: %d cases" % len(CASES))
