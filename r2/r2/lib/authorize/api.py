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

"""
For talking to authorize.net credit card payments via their XML api.

This file consists mostly of wrapper classes for dealing with their
API, while the actual useful functions live in interaction.py

NOTE: This is using the Customer Information Manager (CIM) API
http://developer.authorize.net/api/cim/
"""

import re
from httplib import HTTPSConnection
from urlparse import urlparse

from BeautifulSoup import BeautifulStoneSoup
from pylons import app_globals as g
from xml.sax.saxutils import escape

from r2.lib.export import export
from r2.lib.utils import iters, Storage

__all__ = ["PROFILE_LIMIT", "TRANSACTION_NOT_FOUND"]

TRANSACTION_NOT_FOUND = 16

# response codes http://www.authorize.net/support/ReportingGuide_XML.pdf
TRANSACTION_APPROVED = 1
TRANSACTION_DECLINED = 2
TRANSACTION_ERROR = 3
TRANSACTION_IN_REVIEW = 4

# response reason codes
TRANSACTION_DUPLICATE = 11
# transactions with identical amount, credit card, and invoice submitted within
# some time window will raise an error
# https://support.authorize.net/authkb/index?page=content&id=A425


# list of the most common errors.
Errors = Storage(
    TRANSACTION_FAIL="E00027",
    DUPLICATE_RECORD="E00039", 
    RECORD_NOT_FOUND="E00040",
    TOO_MANY_PAY_PROFILES="E00042",
    TOO_MANY_SHIP_ADDRESSES="E00043",
)

PROFILE_LIMIT = 10 # max payment profiles per user allowed by authorize.net


@export
class AuthorizeNetException(Exception):
    def __init__(self, msg, code=None):
        # don't let CC info show up in logs
        msg = re.sub("<cardNumber>\d+(\d{4})</cardNumber>", 
                     "<cardNumber>...\g<1></cardNumber>",
                     msg)
        msg = re.sub("<cardCode>\d+</cardCode>",
                     "<cardCode>omitted</cardCode>",
                     msg)
        self.code = code
        super(AuthorizeNetException, self).__init__(msg)


class TransactionError(Exception):
    def __init__(self, message):
        self.message = message


class DuplicateTransactionError(TransactionError):
    def __init__(self, transaction_id):
        self.transaction_id = transaction_id
        message = ('DuplicateTransactionError with transaction_id %d' %
                   transaction_id)
        super(DuplicateTransactionError, self).__init__(message)


class AuthorizationHoldNotFound(Exception): pass


# xml tags whose content shouldn't be escaped 
_no_escape_list = ["extraOptions"]


class SimpleXMLObject(object):
    """
    All API transactions are done with authorize.net using XML, so
    here's a class for generating and extracting structured data from
    XML.
    """
    _keys = []
    def __init__(self, **kw):
        self._used_keys = self._keys if self._keys else kw.keys()
        for k in self._used_keys:
            if not hasattr(self, k):
                setattr(self, k, kw.get(k, ""))

    @staticmethod
    def simple_tag(name, content, **attrs):
        attrs = " ".join('%s="%s"' % (k, v) for k, v in attrs.iteritems())
        if attrs:
            attrs = " " + attrs
        return ("<%(name)s%(attrs)s>%(content)s</%(name)s>" %
                dict(name=name, content=content, attrs=attrs))

    def toXML(self):
        content = []
        def process(k, v):
            if isinstance(v, SimpleXMLObject):
                v = v.toXML()
            elif v is not None:
                v = unicode(v)
                if k not in _no_escape_list:
                    v = escape(v) # escape &, <, and >
            if v is not None:
                content.append(self.simple_tag(k, v))

        for k in self._used_keys:
            v = getattr(self, k)
            if isinstance(v, iters):
                for val in v:
                    process(k, val)
            else:
                process(k, v)
        return self._wrapper("".join(content))

    @classmethod
    def fromXML(cls, data):
        kw = {}
        for k in cls._keys:
            d = data.find(k.lower())
            if d and d.contents:
                kw[k] = unicode(d.contents[0])
        return cls(**kw)


    def __repr__(self):
        return "<%s {%s}>" % (self.__class__.__name__,
                              ",".join("%s=%s" % (k, repr(getattr(self, k)))
                                       for k in self._used_keys))

    def _name(self):
        name = self.__class__.__name__
        return name[0].lower() + name[1:]
    
    def _wrapper(self, content):
        return content


class Auth(SimpleXMLObject):
    _keys = ["name", "transactionKey"]


@export
class Address(SimpleXMLObject):
    _keys = ["firstName", "lastName", "company", "address",
             "city", "state", "zip", "country", "phoneNumber",
             "faxNumber",
             "customerPaymentProfileId",
             "customerAddressId" ]
    def __init__(self, **kw):
        kw['customerPaymentProfileId'] = kw.get("customerPaymentProfileId",
                                                 None)
        kw['customerAddressId'] = kw.get("customerAddressId", None)
        SimpleXMLObject.__init__(self, **kw)


@export
class CreditCard(SimpleXMLObject):
    _keys = ["cardNumber", "expirationDate", "cardCode"]


class Profile(SimpleXMLObject):
    _keys = ["merchantCustomerId", "description",
             "email", "customerProfileId", "paymentProfiles", "validationMode"]

    def __init__(self, description, merchantCustomerId, customerProfileId,
                 paymentProfiles, validationMode=None):
        SimpleXMLObject.__init__(
            self,
            merchantCustomerId=merchantCustomerId,
            description=description,
            email="",
            paymentProfiles=paymentProfiles,
            validationMode=validationMode,
            customerProfileId=customerProfileId,
        )


class PaymentProfile(SimpleXMLObject):
    _keys = ["billTo", "payment", "customerPaymentProfileId", "validationMode"]
    def __init__(self, billTo, card, customerPaymentProfileId=None,
                 validationMode=None):
        SimpleXMLObject.__init__(
            self,
            billTo=billTo,
            customerPaymentProfileId=customerPaymentProfileId,
            payment=SimpleXMLObject(creditCard=card),
            validationMode=validationMode,
        )

    @classmethod
    def fromXML(cls, res):
        paymentId = int(res.customerpaymentprofileid.contents[0])
        billTo = Address.fromXML(res.billto)
        card = CreditCard.fromXML(res.payment)
        return cls(billTo, card, paymentId)


@export
class Order(SimpleXMLObject):
    _keys = ["invoiceNumber", "description", "purchaseOrderNumber"]


class Transaction(SimpleXMLObject):
    _keys = ["amount", "customerProfileId", "customerPaymentProfileId",
             "transId", "order"]

    def __init__(self, amount, customerProfileId, customerPaymentProfileId,
                 transId=None, order=None):
        SimpleXMLObject.__init__(
            self, amount=amount, customerProfileId=customerProfileId,
            customerPaymentProfileId=customerPaymentProfileId, transId=transId,
            order=order)

    def _wrapper(self, content):
        return self.simple_tag(self._name(), content)


# only authorize (no charge is made)
@export
class ProfileTransAuthOnly(Transaction): pass


# charge only (requires previous auth_only)
@export
class ProfileTransPriorAuthCapture(Transaction): pass


# refund a transaction
@export
class ProfileTransRefund(Transaction): pass


# void a transaction
@export
class ProfileTransVoid(Transaction): pass


#-----
class AuthorizeNetRequest(SimpleXMLObject):
    _keys = ["merchantAuthentication"]

    @property
    def merchantAuthentication(self):
        return Auth(name=g.secrets['authorizenetname'],
                    transactionKey=g.secrets['authorizenetkey'])

    def _wrapper(self, content):
        return ('<?xml version="1.0" encoding="utf-8"?>' +
                self.simple_tag(self._name(), content,
                             xmlns="AnetApi/xml/v1/schema/AnetApiSchema.xsd"))

    def make_request(self):
        u = urlparse(g.authorizenetapi)
        conn = HTTPSConnection(u.hostname, u.port)
        conn.request("POST", u.path, self.toXML().encode('utf-8'),
                     {"Content-type": "text/xml"})
        res = conn.getresponse()
        res = self.handle_response(res.read())
        conn.close()
        return res

    def is_error_code(self, res, code):
        return (res.message.code and res.message.code.contents and
                res.message.code.contents[0] == code)

    _autoclose_re = re.compile("<([^/]+)/>")
    def _autoclose_handler(self, m):
        return "<%(m)s></%(m)s>" % dict(m=m.groups()[0])

    def handle_response(self, res):
        res = self._autoclose_re.sub(self._autoclose_handler, res)
        res = BeautifulStoneSoup(res, 
                                 markupMassage=False, 
                                 convertEntities=BeautifulStoneSoup.XML_ENTITIES)
        if res.resultcode.contents[0] == u"Ok":
            return self.process_response(res)
        else:
            return self.process_error(res)

    def process_response(self, res):
        raise NotImplementedError

    def process_error(self, res):
        raise NotImplementedError


# --- real request classes below

class CreateCustomerProfileRequest(AuthorizeNetRequest):
    _keys = AuthorizeNetRequest._keys + ["profile", "validationMode"]

    def __init__(self, profile, validationMode=None):
        AuthorizeNetRequest.__init__(
            self, profile=profile, validationMode=validationMode)

    def process_response(self, res):
        customer_id = int(res.customerprofileid.contents[0])
        return customer_id

    def process_error(self, res):
        message_text = res.find("text").contents[0]

        if self.is_error_code(res, Errors.DUPLICATE_RECORD):
            # authorize.net has a record for this user but we don't. get the id
            # from the error message
            matches = re.match(
                "A duplicate record with ID (\d+) already exists", message_text)
            if matches:
                match_groups = matches.groups()
                customer_id = match_groups[0]
                return customer_id

        raise AuthorizeNetException(message_text)


class CreateCustomerPaymentProfileRequest(AuthorizeNetRequest):
    _keys = AuthorizeNetRequest._keys + ["customerProfileId", "paymentProfile",
        "validationMode"]

    def __init__(self, customerProfileId, paymentProfile, validationMode=None):
        AuthorizeNetRequest.__init__(
            self, customerProfileId=customerProfileId,
            paymentProfile=paymentProfile, validationMode=validationMode)

    def process_response(self, res):
        pay_id = int(res.customerpaymentprofileid.contents[0])
        return pay_id

    def process_error(self, res):
        message_text = res.find("text").contents[0]
        raise AuthorizeNetException(message_text)


class GetCustomerProfileRequest(AuthorizeNetRequest):
    _keys = AuthorizeNetRequest._keys + ["customerProfileId"]

    def __init__(self, customerProfileId):
        AuthorizeNetRequest.__init__(
            self, customerProfileId=customerProfileId)

    def process_response(self, res):
        merchantCustomerId = res.merchantcustomerid.contents[0]
        description = res.description.contents[0]
        profile_id = int(res.customerprofileid.contents[0])

        payment_profiles = []
        for profile in res.findAll("paymentprofiles"):
            address = Address.fromXML(profile)
            credit_card = CreditCard.fromXML(profile.payment)
            customerPaymentProfileId = int(address.customerPaymentProfileId)

            payment_profile = PaymentProfile(
                billTo=address,
                card=credit_card,
                customerPaymentProfileId=customerPaymentProfileId,
            )
            payment_profiles.append(payment_profile)

        profile = Profile(
            description=description,
            merchantCustomerId=merchantCustomerId,
            customerProfileId=profile_id,
            paymentProfiles=payment_profiles,
        )
        return profile

    def process_error(self, res):
        message_text = res.find("text").contents[0]
        code = res.find('code').contents[0]
        raise AuthorizeNetException(message_text, code=code)


# TODO: implement
class DeleteCustomerPaymentProfileRequest(AuthorizeNetRequest):
    _keys = AuthorizeNetRequest._keys + ["customerProfileId",
        "customerPaymentProfileId"]

    def __init__(self, customerProfileId, customerPaymentProfileId):
        AuthorizeNetRequest.__init__(
            self, customerProfileId=customerProfileId,
            customerPaymentProfileId=customerPaymentProfileId)

    def process_response(self, res):
        return True

    def process_error(self, res):
        if self.is_error_code(res, Errors.RECORD_NOT_FOUND):
            return True

        message_text = res.find("text").contents[0]
        raise AuthorizeNetException(message_text)


class UpdateCustomerPaymentProfileRequest(AuthorizeNetRequest):
    _keys = AuthorizeNetRequest._keys + ["customerProfileId", "paymentProfile",
        "validationMode"]

    def __init__(self, customerProfileId, paymentProfile, validationMode=None):
        AuthorizeNetRequest.__init__(
            self, customerProfileId=customerProfileId,
            paymentProfile=paymentProfile, validationMode=validationMode)

    def process_response(self, res):
        return self.paymentProfile.customerPaymentProfileId

    def process_error(self, res):
        message_text = res.find("text").contents[0]
        raise AuthorizeNetException(message_text)


class CreateCustomerProfileTransactionRequest(AuthorizeNetRequest):
    _keys = AuthorizeNetRequest._keys + ["transaction", "extraOptions"]

    # unlike every other response we get back, this api function
    # returns CSV data of the response with no field labels.  these
    # are used in package_response to zip this data into a usable
    # storage.
    response_keys = ("response_code",
                     "response_subcode",
                     "response_reason_code",
                     "response_reason_text",
                     "authorization_code",
                     "avs_response",
                     "trans_id",
                     "invoice_number",
                     "description",
                     "amount", "method",
                     "transaction_type",
                     "customerID",
                     "firstName", "lastName",
                     "company", "address", "city", "state",
                     "zip", "country", 
                     "phoneNumber", "faxNumber", "email",
                     "shipTo_firstName", "shipTo_lastName",
                     "shipTo_company", "shipTo_address",
                     "shipTo_city", "shipTo_state",
                     "shipTo_zip", "shipTo_country",
                     "tax", "duty", "freight",
                     "tax_exempt", "po_number", "md5",
                     "cav_response")

    # list of casts for the response fields given above
    response_types = dict(response_code=int,
                          response_subcode=int,
                          response_reason_code=int,
                          trans_id=int)

    def __init__(self, **kw):
        self._extra = kw.get("extraOptions", {})
        AuthorizeNetRequest.__init__(self, **kw)

    @property
    def extraOptions(self):
        return "<![CDATA[%s]]>" % "&".join("%s=%s" % x
                                            for x in self._extra.iteritems())

    def process_response(self, res):
        return (True, self.package_response(res))

    def process_error(self, res):
        return (False, self.package_response(res))

    def package_response(self, res):
        content = res.directresponse.contents[0]
        s = Storage(zip(self.response_keys, content.split(',')))
        for name, cast in self.response_types.iteritems():
            try:
                s[name] = cast(s[name])
            except ValueError:
                pass
        return s


class GetSettledBatchListRequest(AuthorizeNetRequest):
    _keys = AuthorizeNetRequest._keys + ["includeStatistics", 
                                         "firstSettlementDate", 
                                         "lastSettlementDate"]
    def __init__(self, start_date, end_date, **kw):
        AuthorizeNetRequest.__init__(self, 
                                     includeStatistics=1,
                                     firstSettlementDate=start_date.isoformat(),
                                     lastSettlementDate=end_date.isoformat(),
                                     **kw)

    def process_response(self, res):
        return res

    def process_error(self, res):
        message_text = res.find("text").contents[0]
        raise AuthorizeNetException(message_text)


def create_customer_profile(merchant_customer_id, description):
    profile = Profile(
        description=description,
        merchantCustomerId=merchant_customer_id,
        paymentProfiles=None,
        customerProfileId=None,
    )

    request = CreateCustomerProfileRequest(profile=profile)

    try:
        customer_id = request.make_request()
    except AuthorizeNetException:
        return None

    return customer_id


def get_customer_profile(customer_id):
    request = GetCustomerProfileRequest(customerProfileId=customer_id)

    try:
        profile = request.make_request()
    except AuthorizeNetException:
        return None

    return profile


def create_payment_profile(customer_id, address, credit_card, validate=False):
    payment_profile = PaymentProfile(billTo=address, card=credit_card)

    request = CreateCustomerPaymentProfileRequest(
        customerProfileId=customer_id,
        paymentProfile=payment_profile,
        validationMode="liveMode" if validate else None,
    )

    payment_profile_id = request.make_request()

    return payment_profile_id


def update_payment_profile(customer_id, payment_profile_id, address,
                           credit_card, validate=False):
    payment_profile = PaymentProfile(
        billTo=address,
        card=credit_card,
        customerPaymentProfileId=payment_profile_id,
    )

    request = UpdateCustomerPaymentProfileRequest(
        customerProfileId=customer_id,
        paymentProfile=payment_profile,
        validationMode="liveMode" if validate else None,
    )

    payment_profile_id = request.make_request()

    return payment_profile_id


# TODO: implement
def delete_payment_profile(customer_id, payment_profile_id):
    request = DeleteCustomerPaymentProfileRequest(
        customerProfileId=customer_id,
        customerPaymentProfileId=payment_profile_id,
    )

    try:
        success = request.make_request()
    except AuthorizeNetException:
        return False
    else:
        return True


def create_authorization_hold(customer_id, payment_profile_id, amount, invoice,
                              customer_ip=None):
    order = Order(invoiceNumber=invoice)
    transaction = ProfileTransAuthOnly(
        amount="%.2f" % amount,
        customerProfileId=customer_id,
        customerPaymentProfileId=payment_profile_id,
        transId=None,
        order=order,
    )
    if customer_ip:
        extra = {"x_customer_ip": customer_ip}
    else:
        extra = {}

    request = CreateCustomerProfileTransactionRequest(
        transaction=transaction, extraOptions=extra)
    success, res = request.make_request()

    if (res.trans_id and
            res.response_code == TRANSACTION_ERROR and
            res.response_reason_code == TRANSACTION_DUPLICATE):
        raise DuplicateTransactionError(res.trans_id)

    if success:
        return res.trans_id
    else:
        raise TransactionError(res.response_reason_text)


def capture_authorization_hold(customer_id, payment_profile_id, amount,
                               transaction_id):
    transaction = ProfileTransPriorAuthCapture(
        amount="%.2f" % amount,
        customerProfileId=customer_id,
        customerPaymentProfileId=payment_profile_id,
        transId=transaction_id,
    )

    request = CreateCustomerProfileTransactionRequest(
        transaction=transaction)
    success, res = request.make_request()
    response_reason_code = res.get("response_reason_code")

    if success:
        return
    elif response_reason_code == TRANSACTION_NOT_FOUND:
        raise AuthorizationHoldNotFound()
    else:
        raise TransactionError(res.response_reason_text)


def void_authorization_hold(customer_id, payment_profile_id, transaction_id):
    transaction = ProfileTransVoid(
        amount=None,
        customerProfileId=customer_id,
        customerPaymentProfileId=payment_profile_id,
        transId=transaction_id,
    )

    request = CreateCustomerProfileTransactionRequest(
        transaction=transaction)
    success, res = request.make_request()

    if success:
        return res.trans_id
    else:
        raise TransactionError(res.response_reason_text)


def refund_transaction(customer_id, payment_profile_id, amount, transaction_id):
    transaction = ProfileTransRefund(
        amount="%.2f" % amount,
        customerProfileId=customer_id,
        customerPaymentProfileId=payment_profile_id,
        transId=transaction_id,
    )
    request = CreateCustomerProfileTransactionRequest(
        transaction=transaction)
    success, res = request.make_request()

    if not success:
        raise TransactionError(res.response_reason_text)
