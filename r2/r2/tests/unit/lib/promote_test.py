import datetime
import unittest

from mock import MagicMock, Mock, patch

from r2.lib.promote import (
    get_nsfw_collections_srnames,
    get_refund_amount,
    refund_campaign,
    srnames_from_site,
)
from r2.models import (
    Account,
    Collection,
    FakeAccount,
    Frontpage,
    PromoCampaign,
    Subreddit,
    MultiReddit,
)
from r2.tests import RedditTestCase, NonCache


subscriptions_srnames = ["foo", "bar"]
subscriptions = map(lambda srname: Subreddit(name=srname), subscriptions_srnames)
multi_srnames = ["bing", "bat"]
multi_subreddits = map(lambda srname: Subreddit(name=srname), multi_srnames)
nice_srname = "mylittlepony"
nsfw_srname = "pr0n"
questionably_nsfw = "sexstories"
quarantined_srname = "croontown"
naughty_subscriptions = [
    Subreddit(name=nice_srname),
    Subreddit(name=nsfw_srname, over_18=True),
    Subreddit(name=quarantined_srname, quarantine=True),
]
nsfw_collection_srnames = [questionably_nsfw, nsfw_srname]
nsfw_collection = Collection(
    name="after dark",
    sr_names=nsfw_collection_srnames,
    over_18=True
)

class TestSRNamesFromSite(RedditTestCase):
    def setUp(self):
        self.logged_in = Account(name="test")
        self.logged_out = FakeAccount()

        self.patch_g(memoizecache=NonCache())

    def test_frontpage_logged_out(self):
        srnames = srnames_from_site(self.logged_out, Frontpage)

        self.assertEqual(srnames, {Frontpage.name})

    @patch("r2.models.Subreddit.user_subreddits")
    def test_frontpage_logged_in(self, user_subreddits):
        user_subreddits.return_value = subscriptions
        srnames = srnames_from_site(self.logged_in, Frontpage)

        self.assertEqual(srnames, set(subscriptions_srnames) | {Frontpage.name})

    def test_multi_logged_out(self):
        multi = MultiReddit(path="/user/test/m/multi_test", srs=multi_subreddits)
        srnames = srnames_from_site(self.logged_out, multi)

        self.assertEqual(srnames, set(multi_srnames))

    @patch("r2.models.Subreddit.user_subreddits")
    def test_multi_logged_in(self, user_subreddits):
        user_subreddits.return_value = subscriptions
        multi = MultiReddit(path="/user/test/m/multi_test", srs=multi_subreddits)
        srnames = srnames_from_site(self.logged_in, multi)

        self.assertEqual(srnames, set(multi_srnames))

    def test_subreddit_logged_out(self):
        srname = "test1"
        subreddit = Subreddit(name=srname)
        srnames = srnames_from_site(self.logged_out, subreddit)

        self.assertEqual(srnames, {srname})

    @patch("r2.models.Subreddit.user_subreddits")
    def test_subreddit_logged_in(self, user_subreddits):
        user_subreddits.return_value = subscriptions
        srname = "test1"
        subreddit = Subreddit(name=srname)
        srnames = srnames_from_site(self.logged_in, subreddit)

        self.assertEqual(srnames, {srname})

    @patch("r2.models.Subreddit.user_subreddits")
    def test_quarantined_subscriptions_are_never_included(self, user_subreddits):
        user_subreddits.return_value = naughty_subscriptions
        subreddit = Frontpage
        srnames = srnames_from_site(self.logged_in, subreddit)

        self.assertEqual(srnames, {subreddit.name} | {nice_srname})
        self.assertTrue(len(srnames & {quarantined_srname}) == 0)

    @patch("r2.models.Subreddit.user_subreddits")
    def test_nsfw_subscriptions_arent_included_when_viewing_frontpage(self, user_subreddits):
        user_subreddits.return_value = naughty_subscriptions
        srnames = srnames_from_site(self.logged_in, Frontpage)

        self.assertEqual(srnames, {Frontpage.name} | {nice_srname})
        self.assertTrue(len(srnames & {nsfw_srname}) == 0)

    @patch("r2.models.Collection.get_all")
    def test_get_nsfw_collections_srnames(self, get_all):
        get_all.return_value = [nsfw_collection]
        srnames = get_nsfw_collections_srnames()

        self.assertEqual(srnames, set(nsfw_collection_srnames))

    @patch("r2.lib.promote.get_nsfw_collections_srnames")
    def test_remove_nsfw_collection_srnames_on_frontpage(self, get_nsfw_collections_srnames):
        get_nsfw_collections_srnames.return_value = set(nsfw_collection.sr_names)
        srname = "test1"
        subreddit = Subreddit(name=srname)
        Subreddit.user_subreddits = MagicMock(return_value=[
            Subreddit(name=nice_srname),
            Subreddit(name=questionably_nsfw),
        ])

        frontpage_srnames = srnames_from_site(self.logged_in, Frontpage)
        swf_srnames = srnames_from_site(self.logged_in, subreddit)

        self.assertEqual(frontpage_srnames, {Frontpage.name, nice_srname})
        self.assertTrue(len(frontpage_srnames & {questionably_nsfw}) == 0)


class TestPromoteRefunds(unittest.TestCase):
    def setUp(self):
        self.link = Mock()
        self.campaign = MagicMock(spec=PromoCampaign)
        self.campaign._id = 1
        self.campaign.owner_id = 1
        self.campaign.trans_id = 1
        self.campaign.bid_pennies = 1
        self.campaign.start_date = datetime.datetime.now()
        self.campaign.end_date = (datetime.datetime.now() +
            datetime.timedelta(days=1))
        self.campaign.total_budget_dollars = 200.
        self.refund_amount = 100.
        self.billable_amount = 100.
        self.billable_impressions = 1000

    @patch('r2.lib.promote.authorize.refund_transaction')
    @patch('r2.lib.promote.PromotionLog.add')
    @patch('r2.lib.promote.queries.unset_underdelivered_campaigns')
    @patch('r2.lib.promote.emailer.refunded_promo')
    def test_refund_campaign_success(self, emailer_refunded_promo,
            queries_unset, promotion_log_add, refund_transaction):
        """Assert return value and that correct calls are made on success."""
        refund_transaction.return_value = (True, None)

        # the refund process attemtps a db lookup. We don't need it for the
        # purpose of the test.
        with patch.object(Account, "_byID"):
            success = refund_campaign(
                link=self.link,
                camp=self.campaign,
                refund_amount=self.refund_amount,
                billable_amount=self.billable_amount,
                billable_impressions=self.billable_impressions,
            )

        self.assertTrue(refund_transaction.called)
        self.assertTrue(promotion_log_add.called)
        queries_unset.assert_called_once_with(self.campaign)
        emailer_refunded_promo.assert_called_once_with(self.link)
        self.assertTrue(success)

    @patch('r2.lib.promote.authorize.refund_transaction')
    @patch('r2.lib.promote.PromotionLog.add')
    def test_refund_campaign_failed(self, promotion_log_add,
            refund_transaction):
        """Assert return value and that correct calls are made on failure."""
        refund_transaction.return_value = (False, None)

        # the refund process attemtps a db lookup. We don't need it for the
        # purpose of the test.
        with patch.object(Account, "_byID"):
            success = refund_campaign(
                link=self.link,
                camp=self.campaign,
                refund_amount=self.refund_amount,
                billable_amount=self.billable_amount,
                billable_impressions=self.billable_impressions,
            )

        self.assertTrue(refund_transaction.called)
        self.assertTrue(promotion_log_add.called)
        self.assertFalse(success)

    def test_get_refund_amount_when_zero(self):
        """
        Assert that correct value is returned when existing refund_amount is
        zero.
        """
        campaign = MagicMock(spec=('total_budget_dollars',))
        campaign.total_budget_dollars = 200.
        refund_amount = get_refund_amount(campaign, self.billable_amount)
        self.assertEquals(refund_amount,
            campaign.total_budget_dollars - self.billable_amount)

    def test_get_refund_amount_rounding(self):
        """Assert that inputs are correctly rounded up to the nearest penny."""
        # If campaign.refund_amount is less than a fraction of a penny,
        # the refund_amount should be campaign.total_budget_dollars.
        self.campaign.refund_amount = 0.00000001
        refund_amount = get_refund_amount(self.campaign, self.billable_amount)
        self.assertEquals(refund_amount, self.billable_amount)

        self.campaign.refund_amount = 0.00999999
        refund_amount = get_refund_amount(self.campaign, self.billable_amount)
        self.assertEquals(refund_amount, self.billable_amount)

        # If campaign.refund_amount is just slightly more than a penny,
        # the refund amount should be campaign.total_budget_dollars - 0.01.
        self.campaign.refund_amount = 0.01000001
        refund_amount = get_refund_amount(self.campaign, self.billable_amount)
        self.assertEquals(refund_amount, self.billable_amount - 0.01)

        # Even if campaign.refund_amount is just barely short of two pennies,
        # the refund amount should be campaign.total_budget_dollars - 0.01.
        self.campaign.refund_amount = 0.01999999
        refund_amount = get_refund_amount(self.campaign, self.billable_amount)
        self.assertEquals(refund_amount, self.billable_amount - 0.01)
