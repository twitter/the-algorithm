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

from r2.lib.authorize.api import (AuthorizationHoldNotFound,
                                  DuplicateTransactionError,
                                  TRANSACTION_NOT_FOUND,
                                  TransactionError,)
from r2.lib.authorize.interaction import (get_or_create_customer_profile,
                                          add_payment_method,
                                          update_payment_method,
                                          delete_payment_method,
                                          add_or_update_payment_method,
                                          auth_freebie_transaction,
                                          auth_transaction,
                                          charge_transaction,
                                          void_transaction,
                                          refund_transaction,)
from r2.lib.db.thing import NotFound
from r2.models import Account, Link
from r2.tests import RedditTestCase


class InteractionTest(RedditTestCase):

    def setUp(self):
        self.user = Mock(spec=Account)
        self.user._id = 1
        self.user.name = 'name'
        self.user._fullname = 'fullname'

    @patch('r2.lib.authorize.interaction.api.get_customer_profile')
    @patch('r2.lib.authorize.interaction.api.create_customer_profile')
    def test_get_or_create_customer_profile(self, create_customer_profile,
                                            get_customer_profile):
        """Test get_or_create_customer_profile"""
        create_customer_profile.return_value = 123

        profile = MagicMock()
        profile.merchantCustomerId = self.user._fullname
        get_customer_profile.return_value = profile

        get_or_create_customer_profile(self.user)

        # Assert that on the first pass, a customer is created and retrieved
        self.assertEqual(create_customer_profile.call_count, 1)
        self.assertEqual(get_customer_profile.call_count, 1)

        with patch('r2.lib.authorize.interaction.CustomerID.get_id') as get_id:
            get_id.return_value = create_customer_profile.return_value

            get_or_create_customer_profile(self.user)

            # Assert that on the second pass, a customer is only retrieved
            self.assertEqual(create_customer_profile.call_count, 1)
            self.assertEqual(get_customer_profile.call_count, 2)

    @patch('r2.lib.authorize.interaction.PayID.add')
    @patch('r2.lib.authorize.interaction.api.create_payment_profile')
    @patch('r2.lib.authorize.interaction.CustomerID.get_id')
    def test_add_payment_method(self, get_id, create_payment_profile, add):
        """Test add_payment_method"""
        payment_method_id = 999
        create_payment_profile.return_value = payment_method_id

        return_value = add_payment_method(self.user, Mock(), Mock())

        # Assert that get_id is called once
        get_id.assert_called_once_with(self.user._id)
        # Assert that create_payment_profile is called
        self.assertTrue(create_payment_profile.called)
        # Assert that add is called
        self.assertTrue(add.called)
        # Assert that function returns payment_method_id value
        self.assertEqual(return_value, payment_method_id)

    @patch('r2.lib.authorize.interaction.api.update_payment_profile')
    def test_update_payment_method(self, update_payment_profile):
        """Test update_payment_method"""
        update_payment_method(self.user, Mock(), Mock(), Mock())

        # Assert that update_payment_profile was called once
        self.assertEqual(update_payment_profile.call_count, 1)

    @patch('r2.lib.authorize.interaction.PayID.delete')
    @patch('r2.lib.authorize.interaction.api.delete_payment_profile')
    def test_delete_payment_method(self, delete_payment_profile, delete):
        """Test delete_payment_method"""
        delete_payment_method(self.user, Mock())

        # Assert that delete_payment_profile was called once
        self.assertEqual(delete_payment_profile.call_count, 1)
        # Assert that delete was called once
        self.assertEqual(delete.call_count, 1)

        # Reset delete mock and run test again
        delete.reset_mock()
        delete_payment_method(self.user, Mock())

        # Assert that delete_payment_profile was called twice
        self.assertEqual(delete_payment_profile.call_count, 2)
        # Assert that delete was not called again
        self.assertEqual(delete.call_count, 1)

    @patch('r2.lib.authorize.interaction.add_payment_method')
    @patch('r2.lib.authorize.interaction.update_payment_method')
    def test_add_or_update_payment_method(self, update_payment_method,
                                          add_payment_method):
        """Test add_or_update_payment_method"""
        # If pay_id is None, assert that payment method is only added
        add_or_update_payment_method(self.user, Mock(), Mock())
        self.assertTrue(add_payment_method.called)
        self.assertFalse(update_payment_method.called)

        # Reset mocks
        add_payment_method.reset_mock()
        update_payment_method.reset_mock()

        # If pay_id is set, assert that payment method is only updated
        add_or_update_payment_method(self.user, Mock(), Mock(), 999)
        self.assertFalse(add_payment_method.called)
        self.assertTrue(update_payment_method.called)

    @patch('r2.lib.authorize.interaction.Bid._new')
    def test_auth_freebie_transaction(self, _new):
        """Test auth_freebie_transaction"""
        link = Mock(spec=Link)
        link._id = 99
        amount = 100
        campaign_id = 99

        # Can't test that NotFound is thrown since the exception is handled,
        # so assert that _new is called
        return_value = auth_freebie_transaction(amount, self.user, link,
                                                campaign_id)
        self.assertTrue(_new.called)
        # Assert that return value of auth_freebie_transaction is correct
        self.assertEqual(return_value, (-link._id, ''))

        # When a Bid is found, assert that auth is called
        with patch('r2.lib.authorize.interaction.Bid.one') as one:
            one_mock = MagicMock()
            one.return_value = one_mock
            auth_freebie_transaction(amount, self.user, link, campaign_id)
            self.assertTrue(one_mock.auth.called)

    @patch('r2.lib.authorize.interaction.request')
    @patch('r2.lib.authorize.interaction.api.create_authorization_hold')
    @patch('r2.lib.authorize.interaction.CustomerID.get_id')
    @patch('r2.lib.authorize.interaction.PayID.get_ids')
    def test_auth_transaction(self, get_ids, get_id, create_authorization_hold,
                              request):
        """Test auth_transaction"""
        link = Mock(spec=Link)
        link._id = 99
        amount = 100
        payment_method_id = 50
        campaign_id = 99
        request.ip = '127.0.0.1'
        transaction_id = 123

        # If get_ids is empty, assert that the proper value is returned
        get_ids.return_value = []
        return_value = auth_transaction(amount, self.user, payment_method_id,
                                        link, campaign_id)
        self.assertEqual(return_value, (None, 'invalid payment method'))

        # Make get_ids return a valid payment_method_id
        get_ids.return_value.append(payment_method_id)
        # Assign arbitrary CustomerID, which comes from Authorize
        get_id.return_value = 1000
        create_authorization_hold.return_value = transaction_id

        # Scenario: create_authorization_hold raises DuplicateTransactionError
        duplicate_transaction_error = DuplicateTransactionError(transaction_id=transaction_id)
        create_authorization_hold.side_effect = duplicate_transaction_error
        # Why does patch.multiple return an AttributeError?
        with patch('r2.lib.authorize.interaction.Bid.one') as one:
            one.side_effect = NotFound()
            return_value = auth_transaction(amount, self.user,
                                            payment_method_id, link,
                                            campaign_id)
            # If create_authorization_hold raises NotFound, assert return value
            self.assertEqual(return_value, (transaction_id, None))

        # Scenario: create_authorization_hold successfully returns
        with patch('r2.lib.authorize.interaction.Bid._new') as _new:
            return_value = auth_transaction(amount, self.user,
                                            payment_method_id, link,
                                            campaign_id)
            self.assertTrue(_new.called)
            # If create_authorization_hold works, assert return value
            self.assertEqual(return_value, (transaction_id, None))

        # Scenario: creat_authorization_hold raises TransactionError
        create_authorization_hold.side_effect = TransactionError('')
        return_value = auth_transaction(amount, self.user, payment_method_id,
                                        link, campaign_id)
        # If create_authorization_hold raises TransactionError, assert return
        self.assertEqual(return_value[0], None)

    @patch('r2.lib.authorize.interaction.api.capture_authorization_hold')
    @patch('r2.lib.authorize.interaction.Bid.one')
    def test_charge_transaction(self, one, capture_authorization_hold):
        transaction_id = 123
        campaign_id = 99
        bid = Mock()

        one.return_value = bid

        # Scenario: bid.is_charged() return True
        bid.is_charged.return_value = True
        return_value = charge_transaction(self.user, transaction_id,
                                          campaign_id)
        self.assertEqual(return_value, (True, None))

        # Scenario: transaction_id < 0
        bid.is_charged.return_value = False
        return_value = charge_transaction(self.user, -transaction_id,
                                          campaign_id)
        self.assertTrue(bid.charged.called)
        self.assertEqual(return_value, (True, None))

        # Scenario: capture_authorization_hold is successful
        return_value = charge_transaction(self.user, transaction_id,
                                          campaign_id)
        self.assertTrue(bid.charged.called)
        self.assertEqual(return_value, (True, None))

        # Scenario: capture_authorization_hold raises AuthorizationHoldNotFound
        capture_authorization_hold.side_effect = AuthorizationHoldNotFound('')
        return_value = charge_transaction(self.user, transaction_id,
                                          campaign_id)
        self.assertTrue(bid.void.called)
        self.assertEqual(return_value, (False, TRANSACTION_NOT_FOUND))

        # Scenario: capture_authorization_hold raises TransactionError
        capture_authorization_hold.side_effect = TransactionError('')
        return_value = charge_transaction(self.user, transaction_id,
                                          campaign_id)
        self.assertEqual(return_value[0], False)

    @patch('r2.lib.authorize.interaction.Bid.one')
    def test_void_transaction(self, one):
        bid = Mock()
        bid.pay_id = 111
        transaction_id = 123
        campaign_id = 99

        one.return_value = bid

        # Scenario: transaction_id < 0
        return_value = void_transaction(self.user, -transaction_id, campaign_id)
        self.assertTrue(bid.void.called)
        self.assertEqual(return_value, (True, None))

        with patch('r2.lib.authorize.interaction.api.void_authorization_hold') as void:
            # Scenario: void_authorization_hold is successful
            return_value = void_transaction(self.user, transaction_id,
                                            campaign_id)
            self.assertTrue(bid.void.called)
            self.assertEqual(return_value, (True, None))

            # Scenario: void_authorization_hold raises TransactionError
            void.side_effect = TransactionError('')
            return_value = void_transaction(self.user, transaction_id,
                                            campaign_id)
            self.assertEqual(return_value[0], False)

    @patch('r2.lib.authorize.interaction.Bid.one')
    def test_refund_transaction(self, one):
        bid = Mock()
        bid.pay_id = 111
        transaction_id = 123
        campaign_id = 99
        amount = 100

        one.return_value = bid

        # Scenario: transaction_id < 0
        return_value = refund_transaction(self.user, -transaction_id,
                                          campaign_id, amount)
        bid.refund.assert_called_once_with(amount)
        self.assertEqual(return_value, (True, None))

        with patch('r2.lib.authorize.interaction.api.refund_transaction') as refund:
            # Scenario: refund_transaction is successful
            bid.reset_mock()
            return_value = refund_transaction(self.user, transaction_id,
                                              campaign_id, amount)
            bid.refund.assert_called_once_with(amount)
            self.assertEqual(return_value, (True, None))

            # Scenario: refund_transaction raises TransactionError
            bid.reset_mock()
            refund.side_effect = TransactionError('')
            return_value = refund_transaction(self.user, transaction_id,
                                              campaign_id, amount)
            self.assertEqual(return_value[0], False)
