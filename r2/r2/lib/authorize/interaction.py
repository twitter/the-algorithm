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

from sqlalchemy.orm.exc import MultipleResultsFound

from pylons import request
from pylons import app_globals as g

from r2.lib.db.thing import NotFound
from r2.lib.utils import Storage
from r2.lib.export import export
from r2.models.bidding import Bid, CustomerID, PayID
from r2.lib.authorize import api


__all__ = []


FREEBIE_PAYMENT_METHOD_ID = -1


@export
def get_or_create_customer_profile(user):
    profile_id = CustomerID.get_id(user._id)
    if not profile_id:
        profile_id = api.create_customer_profile(
            merchant_customer_id=user._fullname, description=user.name)
        CustomerID.set(user, profile_id)

    profile = api.get_customer_profile(profile_id)

    if not profile or profile.merchantCustomerId != user._fullname:
        raise ValueError("error getting customer profile")

    for payment_profile in profile.paymentProfiles:
        PayID.add(user, payment_profile.customerPaymentProfileId)

    return profile


def add_payment_method(user, address, credit_card, validate=False):
    profile_id = CustomerID.get_id(user._id)
    payment_method_id = api.create_payment_profile(
        profile_id, address, credit_card, validate)

    if payment_method_id:
        PayID.add(user, payment_method_id)
        return payment_method_id


def update_payment_method(user, payment_method_id, address, credit_card,
                          validate=False):
    profile_id = CustomerID.get_id(user._id)
    payment_method_id = api.update_payment_profile(
        profile_id, payment_method_id, address, credit_card, validate)
    return payment_method_id


@export
def delete_payment_method(user, payment_method_id):
    profile_id = CustomerID.get_id(user._id)
    success = api.delete_payment_profile(profile_id, payment_method_id)
    if success:
        PayID.delete(user, payment_method_id)


@export
def add_or_update_payment_method(user, address, credit_card, pay_id=None):
    if pay_id:
        return update_payment_method(user, pay_id, address, credit_card,
                                     validate=True)
    else:
        return add_payment_method(user, address, credit_card, validate=True)


@export
def is_charged_transaction(trans_id, campaign):
    if not trans_id: return False # trans_id == 0 means no bid
    try:
        bid = Bid.one(transaction=trans_id, campaign=campaign)
    except NotFound:
        return False
    except MultipleResultsFound:
        g.log.error('Multiple bids for trans_id %s' % trans_id)
        return False

    return bid.is_charged() or bid.is_refund()


@export
def auth_freebie_transaction(amount, user, link, campaign_id):
    transaction_id = -link._id

    try:
        # attempt to update existing freebie transaction
        bid = Bid.one(thing_id=link._id, transaction=transaction_id,
                      campaign=campaign_id)
    except NotFound:
        bid = Bid._new(transaction_id, user, FREEBIE_PAYMENT_METHOD_ID,
                       link._id, amount, campaign_id)
    else:
        bid.bid = amount
        bid.auth()

    return transaction_id, ""


@export
def auth_transaction(amount, user, payment_method_id, link, campaign_id):
    if payment_method_id not in PayID.get_ids(user._id):
        return None, "invalid payment method"

    profile_id = CustomerID.get_id(user._id)
    invoice = "T%dC%d" % (link._id, campaign_id)

    try:
        transaction_id = api.create_authorization_hold(
            profile_id, payment_method_id, amount, invoice, request.ip)
    except api.DuplicateTransactionError as e:
        transaction_id = e.transaction_id
        try:
            bid = Bid.one(transaction_id, campaign=campaign_id)
        except NotFound:
            bid = Bid._new(transaction_id, user, payment_method_id, link._id,
                           amount, campaign_id)
        g.log.error("%s on campaign %d" % (e.message, campaign_id))
        return transaction_id, None
    except api.TransactionError as e:
        return None, e.message

    bid = Bid._new(transaction_id, user, payment_method_id, link._id, amount,
                   campaign_id)
    return transaction_id, None


@export
def charge_transaction(user, transaction_id, campaign_id):
    bid = Bid.one(transaction=transaction_id, campaign=campaign_id)
    if bid.is_charged():
        return True, None

    if transaction_id < 0:
        bid.charged()
        return True, None

    profile_id = CustomerID.get_id(user._id)

    try:
        api.capture_authorization_hold(
            customer_id=profile_id,
            payment_profile_id=bid.pay_id,
            amount=bid.bid,
            transaction_id=transaction_id,
        )
    except api.AuthorizationHoldNotFound:
        # authorization hold has expired
        bid.void()
        return False, api.TRANSACTION_NOT_FOUND
    except api.TransactionError as e:
        return False, e.message

    bid.charged()
    return True, None


@export
def void_transaction(user, transaction_id, campaign_id):
    bid = Bid.one(transaction=transaction_id, campaign=campaign_id)

    if transaction_id <= 0:
        bid.void()
        return True, None

    profile_id = CustomerID.get_id(user._id)
    try:
        api.void_authorization_hold(profile_id, bid.pay_id, transaction_id)
    except api.TransactionError as e:
        return False, e.message

    bid.void()
    return True, None


@export
def refund_transaction(user, transaction_id, campaign_id, amount):
    bid =  Bid.one(transaction=transaction_id, campaign=campaign_id)
    if transaction_id < 0:
        bid.refund(amount)
        return True, None

    profile_id = CustomerID.get_id(user._id)
    try:
        api.refund_transaction(
            customer_id=profile_id,
            payment_profile_id=bid.pay_id,
            amount=amount,
            transaction_id=transaction_id,
        )
    except api.TransactionError as e:
        return False, e.message

    bid.refund(amount)
    return True, None
