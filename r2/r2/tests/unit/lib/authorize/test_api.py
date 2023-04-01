#!/usr/bin/env python
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

from mock import MagicMock, Mock, patch
from unittest import TestCase

from r2.lib.authorize.api import (TRANSACTION_NOT_FOUND,
                                  TRANSACTION_ERROR,
                                  TRANSACTION_DUPLICATE,
                                  AuthorizationHoldNotFound,
                                  AuthorizeNetException,
                                  DuplicateTransactionError,
                                  TransactionError,
                                  create_customer_profile,
                                  get_customer_profile,
                                  create_payment_profile,
                                  update_payment_profile,
                                  delete_payment_profile,
                                  create_authorization_hold,
                                  capture_authorization_hold,
                                  void_authorization_hold,
                                  refund_transaction)
from r2.tests import RedditTestCase


class AuthorizeNetExceptionTest(RedditTestCase):

    def test_exception_message(self):
        from r2.lib.authorize.api import AuthorizeNetException
        card_number = "<cardNumber>1111222233334444</cardNumber>"
        expected = "<cardNumber>...4444</cardNumber>"
        full_msg = "Wrong Card %s was given"

        exp = AuthorizeNetException(full_msg % (card_number))

        self.assertNotEqual(str(exp), (full_msg % card_number))
        self.assertEqual(str(exp), (full_msg % expected))

class SimpleXMLObjectTest(RedditTestCase):

    def setUp(self):
        from r2.lib.authorize.api import SimpleXMLObject
        self.basic_object = SimpleXMLObject(name="Test",
                                           test="123",
                                           )

    def test_to_xml(self):
        self.assertEqual(self.basic_object.toXML(),
                         "<test>123</test><name>Test</name>",
                         "Unexpected XML produced")

    def test_simple_tag(self):
        from r2.lib.authorize.api import SimpleXMLObject
        xml_output = SimpleXMLObject.simple_tag("cat", "Jini", breed="calico",
                                                               demenor="evil",
                                                               )
        self.assertEqual(xml_output,
                         '<cat breed="calico" demenor="evil">Jini</cat>')

    def test_from_xml(self):
        from r2.lib.authorize.api import SimpleXMLObject
        from BeautifulSoup import BeautifulStoneSoup
        class TestXML(SimpleXMLObject):
            _keys = ["color", "breed"]

        parsed = BeautifulStoneSoup("<dog>" +
                                    "<color>black</color>" +
                                    "<breed>mixed</breed>" +
                                    "<something>else</something>" +
                                    "</dog>")
        constructed = TestXML.fromXML(parsed)
        expected = SimpleXMLObject(color="black",
                                   breed="mixed",
                                   )
        self.assertEqual(constructed.toXML(), expected.toXML(),
                         "Constructed does not match expected")

    def test_address(self):
        from r2.lib.authorize import Address
        address = Address(firstName="Bob",
                          lastName="Smith",
                          company="Reddit Inc.",
                          address="123 Main St.",
                          city="San Francisco",
                          state="California",
                          zip="12345",
                          country="USA",
                          phoneNumber="415-555-1234",
                          faxNumber="415-555-4321",
                          customerPaymentProfileId="1234567890",
                          customerAddressId="2233",
                          )
        expected = ("<firstName>Bob</firstName>" +
                   "<lastName>Smith</lastName>" +
                   "<company>Reddit Inc.</company>" +
                   "<address>123 Main St.</address>" +
                   "<city>San Francisco</city>" +
                   "<state>California</state>" +
                   "<zip>12345</zip>" +
                   "<country>USA</country>" +
                   "<phoneNumber>415-555-1234</phoneNumber>" +
                   "<faxNumber>415-555-4321</faxNumber>" +
                   "<customerPaymentProfileId>1234567890</customerPaymentProfileId>" +
                   "<customerAddressId>2233</customerAddressId>")

        self.assertEqual(address.toXML(), expected)

    def test_credit_card(self):
        from r2.lib.authorize import CreditCard
        card = CreditCard(cardNumber="1111222233334444",
                          expirationDate="11/22/33",
                          cardCode="123"
                          )
        expected = ("<cardNumber>1111222233334444</cardNumber>" +
                    "<expirationDate>11/22/33</expirationDate>" +
                    "<cardCode>123</cardCode>")
        self.assertEqual(card.toXML(), expected)

    def test_payment_profile(self):
        from r2.lib.authorize.api import PaymentProfile
        profile = PaymentProfile(billTo="Joe",
                                 customerPaymentProfileId="222",
                                 card="1111222233334444",
                                 validationMode="42",
                                 )
        expected = ("<billTo>Joe</billTo>" +
                    "<payment>" +
                        "<creditCard>1111222233334444</creditCard>" +
                    "</payment>" +
                    "<customerPaymentProfileId>222</customerPaymentProfileId>" +
                    "<validationMode>42</validationMode>")
        self.assertEqual(profile.toXML(), expected)

    def test_transaction(self):
        from r2.lib.authorize.api import Transaction
        transaction = Transaction(amount="42.42",
                                  customerProfileId="112233",
                                  customerPaymentProfileId="1111",
                                  transId="2222",
                                  order="42",
                                  )

        expected = ("<transaction>" +
                        "<amount>42.42</amount>" +
                        "<customerProfileId>112233</customerProfileId>" +
                        "<customerPaymentProfileId>1111</customerPaymentProfileId>" +
                        "<transId>2222</transId>" +
                        "<order>42</order>" +
                    "</transaction>")
        self.assertEqual(transaction.toXML(), expected)


class ApiFunctionTest(TestCase):

    def setUp(self):
        # Set up a few commonly used variables
        self.customer_id = 1
        self.payment_profile_id = 1000
        self.amount = 100
        self.transaction_id = 99

    @patch('r2.lib.authorize.api.CreateCustomerProfileRequest')
    @patch('r2.lib.authorize.api.Profile')
    def test_create_customer_profile(self, Profile, CreateRequest):
        merchant_customer_id = 99
        description = 'some description'

        # Set up profile mock
        profile = Mock()
        Profile.return_value = profile
        # Set up request mock
        _request = MagicMock()
        _request.make_request.return_value = self.customer_id
        CreateRequest.return_value = _request

        # Scenario: successful call
        return_value = create_customer_profile(merchant_customer_id,
                                               description)
        Profile.assert_called_once_with(description=description,
                                        merchantCustomerId=merchant_customer_id,
                                        paymentProfiles=None,
                                        customerProfileId=None)
        CreateRequest.assert_called_once_with(profile=profile)
        self.assertTrue(_request.make_request.called)
        self.assertEqual(return_value, self.customer_id)

        # Scenario: call raises AuthorizeNetException
        _request.make_request.side_effect = AuthorizeNetException('')
        return_value = create_customer_profile(merchant_customer_id,
                                               description)
        self.assertEqual(return_value, None)

    @patch('r2.lib.authorize.api.GetCustomerProfileRequest')
    def test_get_customer_profile(self, GetRequest):
        profile_mock = Mock()
        _request = Mock()
        _request.make_request.return_value = profile_mock
        GetRequest.return_value = _request

        # Scenario: call is successful
        return_value = get_customer_profile(self.customer_id)
        GetRequest.assert_called_once_with(customerProfileId=self.customer_id)
        self.assertTrue(_request.make_request.called)
        self.assertEqual(return_value, profile_mock)

        # Scenario: call raises AuthorizeNetException
        _request.make_request.side_effect = AuthorizeNetException('')
        return_value = get_customer_profile(self.customer_id)
        self.assertEqual(return_value, None)

    @patch('r2.lib.authorize.api.CreateCustomerPaymentProfileRequest')
    @patch('r2.lib.authorize.api.PaymentProfile')
    def test_create_payment_profile(self, PaymentProfile, CreateRequest):
        payment_profile = Mock()
        PaymentProfile.return_value = payment_profile
        _request = Mock()
        _request.make_request.return_value = self.payment_profile_id
        CreateRequest.return_value = _request

        # Scenario: call is successful, no validationMode is passed
        return_value = create_payment_profile(self.customer_id, 'address',
                                              'credit_card')
        PaymentProfile.assert_called_once_with(billTo='address',
                                               card='credit_card')
        CreateRequest.assert_called_once_with(customerProfileId=self.customer_id,
                                              paymentProfile=payment_profile,
                                              validationMode=None)
        self.assertTrue(_request.make_request.called)
        self.assertEqual(return_value, self.payment_profile_id)

        # Scenario: call is successful, validationMode is passed
        create_payment_profile(self.customer_id, 'address', 'credit_card',
                               'liveMode')
        CreateRequest.assert_called_with(customerProfileId=self.customer_id,
                                         paymentProfile=payment_profile,
                                         validationMode='liveMode')

        # Scenario: call raises AuthorizeNetException
        _request.make_request.side_effect = AuthorizeNetException('')
        self.assertRaises(AuthorizeNetException, create_payment_profile,
                          self.customer_id, 'address', 'credit_card')

    @patch('r2.lib.authorize.api.UpdateCustomerPaymentProfileRequest')
    @patch('r2.lib.authorize.api.PaymentProfile')
    def test_update_payment_profile(self, PaymentProfile, UpdateRequest):
        _request = Mock()
        _request.make_request.return_value = self.payment_profile_id
        UpdateRequest.return_value = _request

        # Scenario: call is successful
        return_value = update_payment_profile(self.customer_id,
                                              self.payment_profile_id,
                                              'address', 1234)
        self.assertTrue(UpdateRequest.called)
        self.assertTrue(_request.make_request.called)
        self.assertEqual(return_value, self.payment_profile_id)

        # Scenario: call raises AuthorizeNetException
        _request.make_request.side_effect = AuthorizeNetException('')
        self.assertRaises(AuthorizeNetException, update_payment_profile,
                          self.customer_id, self.payment_profile_id, 'address',
                          1234)

    @patch('r2.lib.authorize.api.DeleteCustomerPaymentProfileRequest')
    def test_delete_payment_profile(self, DeleteRequest):
        _request = Mock()
        DeleteRequest.return_value = _request

        # Scenario: call is successful
        return_value = delete_payment_profile(self.customer_id,
                                              self.payment_profile_id)
        DeleteRequest.assert_called_once_with(customerProfileId=self.customer_id,
                                              customerPaymentProfileId=self.payment_profile_id)
        self.assertTrue(return_value)

        # Scenario: call raises AuthorizeNetException
        _request.make_request.side_effect = AuthorizeNetException('')
        return_value = delete_payment_profile(self.customer_id,
                                              self.payment_profile_id)
        self.assertFalse(return_value)


    @patch('r2.lib.authorize.api.CreateCustomerProfileTransactionRequest')
    def test_create_authorization_hold(self, CreateRequest):
        _response = Mock()
        _response.trans_id = self.transaction_id
        _request = Mock()
        _request.make_request.return_value = (True, _response)
        CreateRequest.return_value = _request

        # Scenario: call is successful; pass customer_ip
        return_value = create_authorization_hold(self.customer_id,
                                                 self.payment_profile_id,
                                                 self.amount, 12345,
                                                 '127.0.0.1')
        self.assertTrue(CreateRequest.called)
        args, kwargs = CreateRequest.call_args
        self.assertEqual(kwargs['extraOptions'], {'x_customer_ip': '127.0.0.1'})
        self.assertEqual(return_value, self.transaction_id)

        # Scenario: call raises transaction_error
        _request.make_request.return_value = (False, _response)
        self.assertRaises(TransactionError, create_authorization_hold,
                          self.customer_id, self.payment_profile_id,
                          self.amount, 12345, '127.0.0.1')

        # Scenario: call returns duplicate errors
        _response.response_code = TRANSACTION_ERROR
        _response.response_reason_code = TRANSACTION_DUPLICATE
        _request.make_request.return_value = (True, _response)
        self.assertRaises(DuplicateTransactionError, create_authorization_hold,
                          self.customer_id, self.payment_profile_id,
                          self.amount, 12345)

    @patch('r2.lib.authorize.api.CreateCustomerProfileTransactionRequest')
    def test_capture_authorization_hold(self, CreateRequest):
        _response = Mock()
        _request = Mock()
        _request.make_request.return_value = (True, _response)
        CreateRequest.return_value = _request

        # Scenario: call is successful
        return_value = capture_authorization_hold(self.customer_id,
                                                  self.payment_profile_id,
                                                  self.amount,
                                                  self.transaction_id)
        self.assertTrue(CreateRequest.called)
        self.assertTrue(_request.make_request.called)
        self.assertEqual(return_value, None)

        # Scenario: call raises TransactionError
        _request.make_request.return_value = (False, _response)
        self.assertRaises(TransactionError, capture_authorization_hold,
                          self.customer_id, self.payment_profile_id,
                          self.amount, self.transaction_id)

        # Scenario: _request call returns not found error
        _response.get.return_value = TRANSACTION_NOT_FOUND
        self.assertRaises(AuthorizationHoldNotFound, capture_authorization_hold,
                          self.customer_id, self.payment_profile_id,
                          self.amount, self.transaction_id)

    @patch('r2.lib.authorize.api.CreateCustomerProfileTransactionRequest')
    def test_void_authorization_hold(self, CreateRequest):
        _response = Mock()
        _response.trans_id = self.transaction_id
        _request = Mock()
        _request.make_request.return_value = (True, _response)
        CreateRequest.return_value = _request

        # Scenario: call is successful
        return_value = void_authorization_hold(self.customer_id,
                                               self.payment_profile_id,
                                               self.transaction_id)
        self.assertTrue(CreateRequest.called)
        self.assertTrue(_request.make_request.called)
        self.assertEqual(return_value, self.transaction_id)

        # Scenario: call raises TransactionError
        _request.make_request.return_value = (False, _response)
        self.assertRaises(TransactionError, void_authorization_hold,
                          self.customer_id, self.payment_profile_id,
                          self.transaction_id)

    @patch('r2.lib.authorize.api.CreateCustomerProfileTransactionRequest')
    def test_refund_transaction(self, CreateRequest):
        _request = Mock()
        _request.make_request.return_value = (True, None)
        CreateRequest.return_value = _request

        # Scenario: call is successful
        refund_transaction(self.customer_id, self.payment_profile_id,
                           self.amount, self.transaction_id)
        self.assertTrue(_request.make_request.called)

        # Scenario: call raises TransactionError
        _request.make_request.return_value = (False, Mock())
        self.assertRaises(TransactionError, refund_transaction,
                          self.customer_id, self.payment_profile_id,
                          self.amount, self.transaction_id)

