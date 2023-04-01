from mock import MagicMock, patch

from pylons import app_globals as g

from r2.tests import RedditTestCase
from r2.lib import signing


class SigningTests(RedditTestCase):
    def setUp(self):
        super(RedditTestCase, self).setUp()
        g.secrets['request_signature_secret'] = "super_secret_do_not_share"

    def test_get_token(self):
        # since the secret is static, so are these, so we're testing backward
        # compat of the algo as well as verifying it varies with all inputs!
        self.assertEqual(
            signing.get_secret_token("test", 1, 1),
            "008c42a8952d949b9c95109eea5016bb00a5a0ac141b35a0691fe6a01f084241",
        )
        self.assertEqual(
            signing.get_secret_token("test", 2, 1),
            "5081cd2623e0391da6b81d9590e9272e00bd17c29b4e3fb9b0044ff999cf5ae2",
        )
        self.assertRaises(
            AssertionError,
            lambda: signing.get_secret_token("test", 1, 2),
        )
        self.assertEqual(
            signing.get_secret_token("test2", 1, 1),
            "07e87fdff4b8300b5282993cf30f8d652383854bf37a96da018354f7f5481832",
        )

    def make_sig_header(self, body, platform="test", version=1, epoch=None):
        return signing.sign_v1_message(
            body,
            platform=platform,
            version=version,
            epoch=epoch,
        )

    def _assert_validity(self, body, header, success, error, **expected):
        request = MagicMock(body=body, headers={})
        if header:
            request.headers[signing.SIGNATURE_BODY_HEADER] = header
        signature = signing.valid_post_signature(request)
        self.assertEqual(signature.is_valid(), bool(success))
        if error:
            self.assertIn(error.code, [code for code, _ in signature.errors])
        else:
            self.assertEqual(len(signature.errors), 0)
        has_mac = expected.pop("has_mac", False)
        for k, v in expected.iteritems():
            got = getattr(signature, k)
            self.assertEqual(got, v, "signature.%s: %s != %s" % (k, got, v))

        if has_mac:
            self.assertTrue(bool(signature.mac))
        else:
            self.assertIsNone(signature.mac)

    def assert_valid(self, body, header, **expected):
        expected['success'] = True
        expected['error'] = None
        expected['has_mac'] = True
        return self._assert_validity(body, header, **expected)

    def assert_invalid(self, body, header, error, **expected):
        expected.setdefault("global_version", -1)
        expected.setdefault("version", -1)
        expected.setdefault("platform", None)
        expected.setdefault('has_mac', False)
        expected['success'] = False
        expected['error'] = error
        return self._assert_validity(body, header, **expected)

    def test_signing(self):
        epoch_time = 1234567890
        header = self.make_sig_header(
            '{"user": "reddit", "password": "hunter2"}',
            epoch=epoch_time,
        )
        self.assertEqual(
            header,
            "1:test:1:1234567890:"
            "0fc3d90d83ac7433a5376c17f2aea9b470c368740c91c513e819e3a4980349de"
        )

    def test_valid_header(self):
        body = '{"user": "reddit", "password": "hunter2"}'
        platform = "something"
        version = 2
        header = self.make_sig_header(
            "Body:{}".format(body),
            platform=platform,
            version=version,
        )
        self.assert_valid(
            body,
            header,
            version=version,
            platform=platform,
            global_version=1,
        )

    def test_no_header(self):
        body = '{"user": "reddit", "password": "hunter2"}'
        self.assert_invalid(body, "", signing.ERRORS.INVALID_FORMAT)

    def test_garbage_header(self):
        body = '{"user": "reddit", "password": "hunter2"}'
        self.assert_invalid(
            body,
            header="idontneednosignature",
            error=signing.ERRORS.INVALID_FORMAT,
        )

    def test_future_header(self):
        body = '{"user": "reddit", "password": "hunter2"}'
        self.assert_invalid(
            body,
            header="2:awesomefuturespec",
            error=signing.ERRORS.UNKOWN_GLOBAL_VERSION,
            global_version=2,
        )

    @patch.object(signing, "is_invalid_token", return_value=True)
    def test_invalid(self, _):
        body = '{"user": "reddit", "password": "hunter2"}'
        platform = "something"
        version = 2
        # this is a perfectly valid signature (from `test_valid_header`)
        # and a properly constructed request, but we've patched
        # is_invalid_token
        header = self.make_sig_header(body, platform=platform, version=version)
        self.assert_invalid(
            body,
            header=header,
            error=signing.ERRORS.INVALIDATED_TOKEN,
            global_version=1,
            version=version,
            platform=platform,
            has_mac=True,
        )

    def test_invalid_header(self):
        body = '{"user": "reddit", "password": "hunter2"}'
        platform = "test"
        version = 1
        header = "1:%s:%s:deadbeef" % (platform, version)
        self.assert_invalid(
            body,
            header=header,
            error=signing.ERRORS.UNPARSEABLE,
            global_version=1,
        )

    def test_expired_header(self):
        body = '{"user": "reddit", "password": "hunter2"}'
        platform = "test"
        version = 1
        header = "1:%s:%s:0:deadbeef" % (platform, version)
        self.assert_invalid(
            body,
            header=header,
            error=signing.ERRORS.EXPIRED_TOKEN,
            global_version=1,
            platform=platform,
            version=version,
            has_mac=True,
        )
