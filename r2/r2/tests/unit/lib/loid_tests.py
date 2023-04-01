from mock import MagicMock, ANY, call
from urllib import quote
from r2.tests import RedditTestCase
from r2.lib import hooks
from r2.lib.loid import LoId, LOID_COOKIE, LOID_CREATED_COOKIE, isodate
from r2.lib.utils import to_epoch_milliseconds


class LoidTests(RedditTestCase):

    def setUp(self):
        super(LoidTests, self).setUp()
        self.mock_eventcollector()

    def test_ftue_autocreate(self):
        request = MagicMock()
        context = MagicMock()
        request.cookies = {}
        loid = LoId.load(request, context, create=True)
        self.assertIsNotNone(loid.loid)
        self.assertIsNotNone(loid.created)
        self.assertTrue(loid.new)

        loid.save()

        context.cookies.add.assert_has_calls([
            call(
                LOID_COOKIE,
                quote(loid.loid),
                expires=ANY,
            ),
            call(
                LOID_CREATED_COOKIE,
                isodate(loid.created),
                expires=ANY,
            )
        ])
        self.amqp.assert_event_item(
            dict(
                event_topic="loid_events",
                event_type="ss.create_loid",
                payload={
                    'loid_new': True,
                    'loid': loid.loid,
                    'loid_created': to_epoch_milliseconds(loid.created),
                    'loid_version': 0,

                    'user_id': context.user._id,
                    'user_name': context.user.name,

                    'request_url': request.fullpath,
                    'domain': request.host,
                    'geoip_country': context.location,
                    'oauth2_client_id': context.oauth2_client._id,
                    'oauth2_client_app_type': context.oauth2_client.app_type,
                    'oauth2_client_name': context.oauth2_client.name,
                    'referrer_domain': self.domain_mock(),
                    'referrer_url': request.headers.get(),
                    'user_agent': request.user_agent,
                    'user_agent_parsed': request.parsed_agent.to_dict(),
                    'obfuscated_data': {
                        'client_ip': request.ip,
                    }
                },
            )
        )

    def test_ftue_nocreate(self):
        request = MagicMock()
        context = MagicMock()
        request.cookies = {}
        loid = LoId.load(request, context, create=False)
        self.assertFalse(loid.new)
        self.assertFalse(loid.serializable)
        loid.save()
        self.assertFalse(bool(context.cookies.add.called))

    def test_returning(self):
        request = MagicMock()
        context = MagicMock()
        request.cookies = {LOID_COOKIE: "foo", LOID_CREATED_COOKIE: "bar"}
        loid = LoId.load(request, context, create=False)
        self.assertEqual(loid.loid, "foo")
        self.assertNotEqual(loid.created, "bar")
        self.assertFalse(loid.new)
        self.assertTrue(loid.serializable)
        loid.save()
        self.assertFalse(bool(context.cookies.add.called))
