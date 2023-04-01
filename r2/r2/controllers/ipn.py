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

from datetime import datetime, timedelta

import json

from pylons import request
from pylons import tmpl_context as c
from pylons import app_globals as g
from pylons.i18n import _
from sqlalchemy.exc import IntegrityError
import stripe

from r2.controllers.reddit_base import RedditController
from r2.lib.base import abort
from r2.lib.csrf import csrf_exempt
from r2.lib.emailer import _system_email
from r2.lib.errors import MessageError
from r2.lib.filters import _force_unicode, _force_utf8
from r2.lib.hooks import get_hook
from r2.lib.pages import GoldGiftCodeEmail
from r2.lib.strings import strings
from r2.lib.utils import constant_time_compare, randstr, timeago
from r2.lib.validator import (
    nop,
    textresponse,
    validatedForm,
    VByName,
    VDecimal,
    VFloat,
    VInt,
    VLength,
    VModhash,
    VOneOf,
    VPrintable,
    VUser,
)
from r2.models import (
    Account,
    account_by_payingid,
    account_from_stripe_customer_id,
    accountid_from_subscription,
    admintools,
    append_random_bottlecap_phrase,
    cancel_subscription,
    Comment,
    creddits_lock,
    create_claimed_gold,
    create_gift_gold,
    create_gold_code,
    Email,
    get_discounted_price,
    has_prev_subscr_payments,
    Link,
    make_gold_message,
    NotFound,
    retrieve_gold_transaction,
    send_system_message,
    Thing,
    update_gold_transaction,
    generate_token,
)

BLOB_TTL = 86400 * 30
stripe.api_key = g.secrets['stripe_secret_key']


def generate_blob(data):
    passthrough = generate_token(15)

    g.hardcache.set("payment_blob-" + passthrough,
                    data, BLOB_TTL)
    g.log.info("just set payment_blob-%s", passthrough)
    return passthrough


def get_blob(code):
    key = "payment_blob-" + code
    with g.make_lock("payment_blob", "payment_blob_lock-" + code):
        blob = g.hardcache.get(key)
        if not blob:
            raise NotFound("No payment_blob-" + code)
        if blob.get('status', None) != 'initialized':
            raise ValueError("payment_blob %s has status = %s" %
                             (code, blob.get('status', None)))
        blob['status'] = "locked"
        g.hardcache.set(key, blob, BLOB_TTL)
    return key, blob


def update_blob(code, updates=None):
    blob = g.hardcache.get("payment_blob-%s" % code)
    if not blob:
        raise NotFound("No payment_blob-" + code)
    if blob.get('account_id', None) != c.user._id:
        raise ValueError("%s doesn't have access to payment_blob %s" %
                         (c.user._id, code))

    for item, value in updates.iteritems():
        blob[item] = value
    g.hardcache.set("payment_blob-%s" % code, blob, BLOB_TTL)


def has_blob(custom):
    if not custom:
        return False

    blob = g.hardcache.get('payment_blob-%s' % custom)
    return bool(blob)

def dump_parameters(parameters):
    for k, v in parameters.iteritems():
        g.log.info("IPN: %r = %r" % (k, v))

def check_payment_status(payment_status):
    if payment_status is None:
        payment_status = ''

    psl = payment_status.lower()

    if psl == 'completed':
        return (None, psl)
    elif psl == 'refunded':
        return ("Ok", psl)
    elif psl == 'pending':
        return ("Ok", psl)
    elif psl == 'reversed':
        return ("Ok", psl)
    elif psl == 'canceled_reversal':
        return ("Ok", psl)
    elif psl == 'failed':
        return ("Ok", psl)
    elif psl == 'denied':
        return ("Ok", psl)
    elif psl == '':
        return (None, psl)
    else:
        raise ValueError("Unknown IPN status: %r" % payment_status)

def check_txn_type(txn_type, psl):
    if txn_type == 'subscr_signup':
        return ("Ok", None)
    elif txn_type == 'subscr_cancel':
        return ("Ok", "cancel")
    elif txn_type == 'subscr_eot':
        return ("Ok", None)
    elif txn_type == 'subscr_failed':
        return ("Ok", None)
    elif txn_type == 'subscr_modify':
        return ("Ok", None)
    elif txn_type == 'send_money':
        return ("Ok", None)
    elif txn_type in ('new_case',
        'recurring_payment_suspended_due_to_max_failed_payment'):
        return ("Ok", None)
    elif txn_type == 'subscr_payment' and psl == 'completed':
        return (None, "new")
    elif txn_type == 'web_accept' and psl == 'completed':
        return (None, None)
    elif txn_type == "paypal_here":
        return ("Ok", None)
    else:
        raise ValueError("Unknown IPN txn_type / psl %r" %
                         ((txn_type, psl),))


def existing_subscription(subscr_id, paying_id, custom):
    if subscr_id is None:
        return None

    account_id = accountid_from_subscription(subscr_id)

    if not account_id and has_blob(custom):
        # New subscription contains the user info in hardcache
        return None

    should_set_subscriber = False
    if account_id is None:
        # Payment from legacy subscription (subscr_id not set), fall back
        # to guessing the user from the paying_id
        account_id = account_by_payingid(paying_id)
        should_set_subscriber = True
        if account_id is None:
            return None

    try:
        account = Account._byID(account_id, data=True)

        if account._deleted:
            g.log.info("IPN renewal for deleted account %d (%s)", account_id,
                       subscr_id)
            return "deleted account"

        if should_set_subscriber:
            if hasattr(account, "gold_subscr_id") and account.gold_subscr_id:
                g.log.warning("Attempted to set subscr_id (%s) for account (%d) "
                              "that already has one." % (subscr_id, account_id))
                return None

            account.gold_subscr_id = subscr_id
            account._commit()
    except NotFound:
        g.log.info("Just got IPN renewal for non-existent account #%d" % account_id)

    return account

def months_and_days_from_pennies(pennies, discount=False):
    if discount:
        year_pennies = get_discounted_price(g.gold_year_price).pennies
        month_pennies = get_discounted_price(g.gold_month_price).pennies
    else:
        year_pennies = g.gold_year_price.pennies
        month_pennies = g.gold_month_price.pennies

    if pennies >= year_pennies:
        years = pennies / year_pennies
        months = 12 * years
        days  = 366 * years
    else:
        months = pennies / month_pennies
        days   = 31 * months
    return (months, days)

def send_gift(buyer, recipient, months, days, signed, giftmessage,
              thing_fullname, note=None):
    admintools.adjust_gold_expiration(recipient, days=days)

    # increment num_gildings for all types of gildings not to themselves
    if buyer != recipient:
        buyer._incr("num_gildings")

    if thing_fullname:
        thing = Thing._by_fullname(thing_fullname, data=True)
        thing._gild(buyer)
        if isinstance(thing, Comment):
            gilding_type = 'comment gild'
        else:
            gilding_type = 'post gild'
    else:
        thing = None
        get_hook('user.gild').call(recipient=recipient, gilder=buyer)
        gilding_type = 'user gild'

    if signed:
        sender = buyer.name
        md_sender = "/u/%s" % sender
        repliable = True
    else:
        sender = "An anonymous redditor"
        md_sender = "An anonymous redditor"

        if buyer.name in g.live_config["proxy_gilding_accounts"]:
            repliable = False
        else:    
            repliable = True

    create_gift_gold(buyer._id, recipient._id, days, c.start_time, signed, note, gilding_type)

    if months == 1:
        amount = "a month"
    else:
        amount = "%d months" % months

    if not thing:
        subject = 'Let there be gold! %s just sent you reddit gold!' % sender
        message = strings.youve_got_gold % dict(sender=md_sender, amount=amount)
    else:
        url = thing.make_permalink_slow()
        if isinstance(thing, Comment):
            subject = 'Your comment has been gilded!'
            message = strings.youve_been_gilded_comment 
            message %= {'sender': md_sender, 'url': url}
        else:
            subject = 'Your submission has been gilded!'
            message = strings.youve_been_gilded_link 
            message %= {'sender': md_sender, 'url': url}

    if giftmessage and giftmessage.strip():
        message += ("\n\n" + strings.giftgold_note + 
                    _force_unicode(giftmessage) + '\n\n----')

    message += '\n\n' + strings.gold_benefits_msg
    if g.lounge_reddit:
        message += '\n\n' + strings.lounge_msg
    message = append_random_bottlecap_phrase(message)

    if not signed:
        if not repliable:
            message += '\n\n' + strings.unsupported_respond_to_gilder
        else:
            message += '\n\n' + strings.respond_to_anonymous_gilder

    try:
        send_system_message(recipient, subject, message, author=buyer,
                            distinguished='gold-auto', repliable=repliable,
                            signed=signed)
    except MessageError:
        g.log.error('send_gift: could not send system message')

    g.log.info("%s gifted %s to %s" % (buyer.name, amount, recipient.name))
    return thing


def send_gold_code(buyer, months, days,
                   trans_id=None, payer_email='', pennies=0, buyer_email=None):
    if buyer:
        paying_id = buyer._id
        buyer_name = buyer.name
    else:
        paying_id = buyer_email
        buyer_name = buyer_email
    code = create_gold_code(trans_id, payer_email,
                            paying_id, pennies, days, c.start_time)
    # format the code so it's easier to read (XXXXX-XXXXX)
    split_at = len(code) / 2
    code = code[:split_at] + '-' + code[split_at:]

    if months == 1:
        amount = "a month"
    else:
        amount = "%d months" % months

    subject = _('Your gold gift code has been generated!')
    message = _('Here is your gift code for %(amount)s of reddit gold:\n\n'
                '%(code)s\n\nThe recipient (or you!) can enter it at '
                'https://www.reddit.com/gold or go directly to '
                'https://www.reddit.com/thanks/%(code)s to claim it.'
              ) % {'amount': amount, 'code': code}

    if buyer:
        # bought by a logged-in user, send a reddit PM
        message = append_random_bottlecap_phrase(message)
        send_system_message(buyer, subject, message, distinguished='gold-auto')
    else:
        # bought by a logged-out user, send an email
        contents = GoldGiftCodeEmail(message=message).render(style='email')
        _system_email(buyer_email, contents, Email.Kind.GOLD_GIFT_CODE)
                      
    g.log.info("%s bought a gold code for %s", buyer_name, amount)
    return code


class IpnController(RedditController):
    # Used when buying gold with creddits
    @validatedForm(VUser(),
                   VModhash(),
                   months = VInt("months"),
                   passthrough = VPrintable("passthrough", max_length=50))
    def POST_spendcreddits(self, form, jquery, months, passthrough):
        if months is None or months < 1:
            form.set_text(".status", _("nice try."))
            return

        days = months * 31

        if not passthrough:
            raise ValueError("/spendcreddits got no passthrough?")

        blob_key, payment_blob = get_blob(passthrough)
        if payment_blob["goldtype"] not in ("gift", "code", "onetime"):
            raise ValueError("/spendcreddits payment_blob %s has goldtype %s" %
                             (passthrough, payment_blob["goldtype"]))

        if payment_blob["account_id"] != c.user._id:
            fmt = ("/spendcreddits payment_blob %s has userid %d " +
                   "but c.user._id is %d")
            raise ValueError(fmt % passthrough,
                             payment_blob["account_id"],
                             c.user._id)

        if payment_blob["goldtype"] == "gift":
            signed = payment_blob["signed"]
            giftmessage = _force_unicode(payment_blob["giftmessage"])
            recipient_name = payment_blob["recipient"]

            try:
                recipient = Account._by_name(recipient_name)
            except NotFound:
                raise ValueError("Invalid username %s in spendcreddits, buyer = %s"
                                 % (recipient_name, c.user.name))

            if recipient._deleted:
                form.set_text(".status", _("that user has deleted their account"))
                return

        redirect_to_spent = False
        thing = None

        with creddits_lock(c.user):
            if not c.user.employee and c.user.gold_creddits < months:
                msg = "%s is trying to sneak around the creddit check"
                msg %= c.user.name
                raise ValueError(msg)

            if payment_blob["goldtype"] == "gift":
                thing_fullname = payment_blob.get("thing")
                thing = send_gift(c.user, recipient, months, days, signed,
                                  giftmessage, thing_fullname)
                form.set_text(".status", _("the gold has been delivered!"))
            elif payment_blob["goldtype"] == "code":
                try:
                    send_gold_code(c.user, months, days)
                except MessageError:
                    msg = _("there was an error creating a gift code. "
                            "please try again later, or contact %(email)s "
                            "for assistance.") % {'email': g.goldsupport_email}
                    form.set_text(".status", msg)
                    return
                form.set_text(".status",
                              _("the gift code has been messaged to you!"))
            elif payment_blob["goldtype"] == "onetime":
                admintools.adjust_gold_expiration(c.user, days=days)
                form.set_text(".status", _("the gold has been delivered!"))

            redirect_to_spent = True

            if not c.user.employee:
                c.user.gold_creddits -= months
                c.user._commit()

        form.find("button").hide()

        payment_blob["status"] = "processed"
        g.hardcache.set(blob_key, payment_blob, BLOB_TTL)

        if thing:
            gilding_message = make_gold_message(thing, user_gilded=True)
            jquery.gild_thing(thing_fullname, gilding_message, thing.gildings)
        elif redirect_to_spent:
            form.redirect("/gold/thanks?v=spent-creddits")

    @csrf_exempt
    @textresponse(paypal_secret = VPrintable('secret', 50),
                  payment_status = VPrintable('payment_status', 20),
                  txn_id = VPrintable('txn_id', 20),
                  paying_id = VPrintable('payer_id', 50),
                  payer_email = VPrintable('payer_email', 250),
                  mc_currency = VPrintable('mc_currency', 20),
                  mc_gross = VDecimal('mc_gross'),
                  custom = VPrintable('custom', 50))
    def POST_ipn(self, paypal_secret, payment_status, txn_id, paying_id,
                 payer_email, mc_currency, mc_gross, custom):

        parameters = request.POST.copy()

        # Make sure it's really PayPal
        if not constant_time_compare(paypal_secret,
                                     g.secrets['paypal_webhook']):
            raise ValueError

        # Return early if it's an IPN class we don't care about
        response, psl = check_payment_status(payment_status)
        if response:
            return response

        # Return early if it's a txn_type we don't care about
        response, subscription = check_txn_type(parameters['txn_type'], psl)
        if subscription is None:
            subscr_id = None
        elif subscription == "new":
            subscr_id = parameters['subscr_id']
        elif subscription == "cancel":
            cancel_subscription(parameters['subscr_id'])
        else:
            raise ValueError("Weird subscription: %r" % subscription)

        if response:
            return response

        if mc_currency != 'USD':
            raise ValueError("Somehow got non-USD IPN %r" % mc_currency)

        if not (txn_id and paying_id and payer_email and mc_gross):
            dump_parameters(parameters)
            raise ValueError("Got incomplete IPN")

        pennies = int(mc_gross * 100)
        months, days = months_and_days_from_pennies(pennies)

        # Special case: autorenewal payment
        existing = existing_subscription(subscr_id, paying_id, custom)
        if existing:
            if existing != "deleted account":
                try:
                    create_claimed_gold ("P" + txn_id, payer_email, paying_id,
                                         pennies, days, None, existing._id,
                                         c.start_time, subscr_id)
                except IntegrityError:
                    return "Ok"
                admintools.adjust_gold_expiration(existing, days=days)

                subject, message = subscr_pm(pennies, months, new_subscr=False)
                message = append_random_bottlecap_phrase(message)
                send_system_message(existing.name, subject, message,
                                    distinguished='gold-auto')

                g.log.info("Just applied IPN renewal for %s, %d days" %
                           (existing.name, days))
            return "Ok"

        # More sanity checks that all non-autorenewals should pass:

        if not custom:
            dump_parameters(parameters)
            raise ValueError("Got IPN with txn_id=%s and no custom"
                             % txn_id)

        self.finish(parameters, "P" + txn_id,
                    payer_email, paying_id, subscr_id,
                    custom, pennies, months, days)

    def finish(self, parameters, txn_id,
               payer_email, paying_id, subscr_id,
               custom, pennies, months, days):

        try:
            blob_key, payment_blob = get_blob(custom)
        except ValueError:
            g.log.error("whoops, %s was locked", custom)
            return

        buyer = None
        buyer_email = None
        buyer_id = payment_blob.get('account_id', None)
        if buyer_id:
            try:
                buyer = Account._byID(buyer_id, data=True)
            except NotFound:
                dump_parameters(parameters)
                raise ValueError("Invalid buyer_id %d in IPN with custom='%s'"
                                 % (buyer_id, custom))
        else:
            buyer_email = payment_blob.get('email')
            if not buyer_email:
                dump_parameters(parameters)
                error = "No buyer_id or email in IPN with custom='%s'" % custom
                raise ValueError(error)

        if subscr_id:
            buyer.gold_subscr_id = subscr_id

        instagift = False
        if payment_blob['goldtype'] == 'onetime':
            admintools.adjust_gold_expiration(buyer, days=days)

            subject = _("Eureka! Thank you for investing in reddit gold!")
            message = _("Thank you for buying reddit gold. Your patronage "
                        "supports the site and makes future development "
                        "possible. For example, one month of reddit gold "
                        "pays for 5 instance hours of reddit's servers.")
            message += "\n\n" + strings.gold_benefits_msg
            if g.lounge_reddit:
                message += "\n\n" + strings.lounge_msg
        elif payment_blob['goldtype'] == 'autorenew':
            admintools.adjust_gold_expiration(buyer, days=days)
            subject, message = subscr_pm(pennies, months, new_subscr=True)
        elif payment_blob['goldtype'] == 'creddits':
            buyer._incr("gold_creddits", months)
            buyer._commit()
            subject = _("Eureka! Thank you for investing in reddit gold "
                        "creddits!")

            message = _("Thank you for buying creddits. Your patronage "
                        "supports the site and makes future development "
                        "possible. To spend your creddits and spread reddit "
                        "gold, visit [/gold](/gold) or your favorite "
                        "person's user page.")
            message += "\n\n" + strings.gold_benefits_msg + "\n\n"
            message += _("Thank you again for your support, and have fun "
                         "spreading gold!")
        elif payment_blob['goldtype'] == 'gift':
            recipient_name = payment_blob.get('recipient', None)
            try:
                recipient = Account._by_name(recipient_name)
            except NotFound:
                dump_parameters(parameters)
                raise ValueError("Invalid recipient_name %s in IPN/GC with custom='%s'"
                                 % (recipient_name, custom))
            signed = payment_blob.get("signed", False)
            giftmessage = _force_unicode(payment_blob.get("giftmessage", ""))
            thing_fullname = payment_blob.get("thing")
            send_gift(buyer, recipient, months, days, signed, giftmessage,
                      thing_fullname)
            instagift = True
            subject = _("Thanks for giving the gift of reddit gold!")
            message = _("Your classy gift to %s has been delivered.\n\n"
                        "Thank you for gifting reddit gold. Your patronage "
                        "supports the site and makes future development "
                        "possible.") % recipient.name
            message += "\n\n" + strings.gold_benefits_msg + "\n\n"
            message += _("Thank you again for your support, and have fun "
                         "spreading gold!")
        elif payment_blob['goldtype'] == 'code':
            pass
        else:
            dump_parameters(parameters)
            raise ValueError("Got status '%s' in IPN/GC" % payment_blob['status'])

        if payment_blob['goldtype'] == 'code':
            send_gold_code(buyer, months, days, txn_id, payer_email,
                           pennies, buyer_email)
        else:
            # Reuse the old "secret" column as a place to record the goldtype
            # and "custom", just in case we need to debug it later or something
            secret = payment_blob['goldtype'] + "-" + custom

            if instagift:
                status="instagift"
            else:
                status="processed"

            create_claimed_gold(txn_id, payer_email, paying_id, pennies, days,
                                secret, buyer_id, c.start_time,
                                subscr_id, status=status)

            message = append_random_bottlecap_phrase(message)

            try:
                send_system_message(buyer, subject, message,
                                    distinguished='gold-auto')
            except MessageError:
                g.log.error('finish: could not send system message')

        payment_blob["status"] = "processed"
        g.hardcache.set(blob_key, payment_blob, BLOB_TTL)


class Webhook(object):
    def __init__(self, passthrough=None, transaction_id=None, subscr_id=None,
                 pennies=None, months=None, payer_email='', payer_id='',
                 goldtype=None, buyer=None, recipient=None, signed=False,
                 giftmessage=None, thing=None, buyer_email=None):
        self.passthrough = passthrough
        self.transaction_id = transaction_id
        self.subscr_id = subscr_id
        self.pennies = pennies
        self.months = months
        self.payer_email = payer_email
        self.payer_id = payer_id
        self.goldtype = goldtype
        self.buyer = buyer
        self.buyer_email = buyer_email
        self.recipient = recipient
        self.signed = signed
        self.giftmessage = giftmessage
        self.thing = thing

    def load_blob(self):
        payment_blob = validate_blob(self.passthrough)
        self.goldtype = payment_blob['goldtype']
        self.buyer = payment_blob.get('buyer')
        self.buyer_email = payment_blob.get('email')
        self.recipient = payment_blob.get('recipient')
        self.signed = payment_blob.get('signed', False)
        self.giftmessage = payment_blob.get('giftmessage')
        thing = payment_blob.get('thing')
        self.thing = thing._fullname if thing else None

    def __repr__(self):
        return '<%s: transaction %s>' % (self.__class__.__name__, self.transaction_id)


class GoldPaymentController(RedditController):
    name = ''
    webhook_secret = ''
    event_type_mappings = {}
    abort_on_error = True

    @csrf_exempt
    @textresponse(secret=VPrintable('secret', 50))
    def POST_goldwebhook(self, secret):
        self.validate_secret(secret)
        status, webhook = self.process_response()

        try:
            event_type = self.event_type_mappings[status]
        except KeyError:
            g.log.error('%s %s: unknown status %s' % (self.name,
                                                      webhook,
                                                      status))
            if self.abort_on_error:
                self.abort403()
            else:
                return
        self.process_webhook(event_type, webhook)

    def validate_secret(self, secret):
        if not constant_time_compare(secret, self.webhook_secret):
            g.log.error('%s: invalid webhook secret from %s' % (self.name,
                                                                request.ip))
            self.abort403() 

    @classmethod
    def process_response(cls):
        """Extract status and webhook."""
        raise NotImplementedError

    def process_webhook(self, event_type, webhook):
        if event_type == 'noop':
            return

        existing = retrieve_gold_transaction(webhook.transaction_id)
        if not existing and webhook.passthrough:
            try:
                webhook.load_blob()
            except GoldException as e:
                g.log.error('%s: payment_blob %s', webhook.transaction_id, e)
                if self.abort_on_error:
                    self.abort403()
                else:
                    return
        msg = None

        if event_type == 'cancelled':
            subject = _('reddit gold payment cancelled')
            msg = _('Your reddit gold payment has been cancelled, contact '
                    '%(gold_email)s for details') % {'gold_email':
                                                     g.goldsupport_email}
            if existing:
                # note that we don't check status on existing, probably
                # should update gold_table when a cancellation happens
                reverse_gold_purchase(webhook.transaction_id)
        elif event_type == 'succeeded':
            if (existing and
                    existing.status in ('processed', 'unclaimed', 'claimed')):
                g.log.info('POST_goldwebhook skipping %s' % webhook.transaction_id)
                return

            self.complete_gold_purchase(webhook)
        elif event_type == 'failed':
            subject = _('reddit gold payment failed')
            msg = _('Your reddit gold payment has failed, contact '
                    '%(gold_email)s for details') % {'gold_email':
                                                     g.goldsupport_email}
        elif event_type == 'deleted_subscription':
            # the subscription may have been deleted directly by the user using
            # POST_delete_subscription, in which case gold_subscr_id is already
            # unset and we don't need to message them
            if webhook.buyer and webhook.buyer.gold_subscr_id:
                subject = _('reddit gold subscription cancelled')
                msg = _('Your reddit gold subscription has been cancelled '
                        'because your credit card could not be charged. '
                        'Contact %(gold_email)s for details')
                msg %= {'gold_email': g.goldsupport_email}
                webhook.buyer.gold_subscr_id = None
                webhook.buyer._commit()
        elif event_type == 'refunded':
            if not (existing and existing.status == 'processed'):
                return

            subject = _('reddit gold refund')
            msg = _('Your reddit gold payment has been refunded, contact '
                   '%(gold_email)s for details') % {'gold_email':
                                                    g.goldsupport_email}
            reverse_gold_purchase(webhook.transaction_id)

        if msg:
            if existing:
                buyer = Account._byID(int(existing.account_id), data=True)
            elif webhook.buyer:
                buyer = webhook.buyer
            else:
                return

            try:
                send_system_message(buyer, subject, msg)
            except MessageError:
                g.log.error('process_webhook: send_system_message error')

    @classmethod
    def complete_gold_purchase(cls, webhook):
        """After receiving a message from a payment processor, apply gold.

        Shared endpoint for all payment processing systems. Validation of gold
        purchase (sender, recipient, etc.) should happen before hitting this.

        """

        secret = webhook.passthrough
        transaction_id = webhook.transaction_id
        payer_email = webhook.payer_email
        payer_id = webhook.payer_id
        subscr_id = webhook.subscr_id
        pennies = webhook.pennies
        months = webhook.months
        goldtype = webhook.goldtype
        buyer = webhook.buyer
        buyer_email = webhook.buyer_email
        recipient = webhook.recipient
        signed = webhook.signed
        giftmessage = webhook.giftmessage
        thing = webhook.thing

        days = days_from_months(months)

        # locking isn't necessary for code purchases
        if goldtype == 'code':
            send_gold_code(buyer, months, days, transaction_id,
                           payer_email, pennies, buyer_email)
            # the rest of the function isn't needed for a code purchase
            return

        gold_recipient = recipient or buyer
        with gold_recipient.get_read_modify_write_lock() as lock:
            gold_recipient.update_from_cache(lock)

            secret_pieces = [goldtype]
            if goldtype == 'gift':
                secret_pieces.append(recipient.name)
            secret_pieces.append(secret or transaction_id)
            secret = '-'.join(secret_pieces)

            if goldtype in ('onetime', 'autorenew'):
                admintools.adjust_gold_expiration(buyer, days=days)
                if goldtype == 'onetime':
                    subject = "thanks for buying reddit gold!"
                    if g.lounge_reddit:
                        message = strings.lounge_msg
                    else:
                        message = ":)"
                else:
                    if has_prev_subscr_payments(subscr_id):
                        secret = None
                        subject, message = subscr_pm(pennies, months, new_subscr=False)
                    else:
                        subject, message = subscr_pm(pennies, months, new_subscr=True)

            elif goldtype == 'creddits':
                buyer._incr('gold_creddits', months)
                subject = "thanks for buying creddits!"
                message = ("To spend them, visit %s://%s/gold or your "
                           "favorite person's userpage." % (g.default_scheme,
                                                            g.domain))

            elif goldtype == 'gift':
                send_gift(buyer, recipient, months, days, signed, giftmessage,
                          thing)
                subject = "thanks for giving reddit gold!"
                message = "Your gift to %s has been delivered." % recipient.name

            try:
                create_claimed_gold(transaction_id, payer_email, payer_id,
                                    pennies, days, secret, buyer._id,
                                    c.start_time, subscr_id=subscr_id,
                                    status='processed')
            except IntegrityError:
                g.log.error('gold: got duplicate gold transaction')

            try:
                message = append_random_bottlecap_phrase(message)
                send_system_message(buyer, subject, message,
                                    distinguished='gold-auto')
            except MessageError:
                g.log.error('complete_gold_purchase: send_system_message error')


def handle_stripe_error(fn):
    def wrapper(cls, form, *a, **kw):
        try:
            return fn(cls, form, *a, **kw)
        except stripe.CardError as e:
            form.set_text('.status',
                          _('error: %(error)s') % {'error': e.message})
        except stripe.InvalidRequestError as e:
            form.set_text('.status', _('invalid request'))
        except stripe.APIConnectionError as e:
            form.set_text('.status', _('api error'))
        except stripe.AuthenticationError as e:
            form.set_text('.status', _('connection error'))
        except stripe.StripeError as e:
            form.set_text('.status', _('error'))
            g.log.error('stripe error: %s' % e)
        except:
            raise
        form.find('.stripe-submit').removeAttr('disabled').end()
    return wrapper


class StripeController(GoldPaymentController):
    name = 'stripe'
    webhook_secret = g.secrets['stripe_webhook']
    event_type_mappings = {
        'charge.succeeded': 'succeeded',
        'charge.failed': 'failed',
        'charge.refunded': 'refunded',
        'charge.dispute.created': 'noop',
        'charge.dispute.updated': 'noop',
        'charge.dispute.closed': 'noop',
        'charge.dispute.funds_withdrawn': 'noop',
        'charge.updated': 'noop',
        'customer.created': 'noop',
        'customer.card.created': 'noop',
        'customer.card.updated': 'noop',
        'customer.card.deleted': 'noop',
        'customer.source.updated': 'noop',
        'transfer.created': 'noop',
        'transfer.paid': 'noop',
        'balance.available': 'noop',
        'invoice.created': 'noop',
        'invoice.updated': 'noop',
        'invoice.payment_succeeded': 'noop',
        'invoice.payment_failed': 'noop',
        'invoiceitem.deleted': 'noop',
        'customer.subscription.created': 'noop',
        'customer.deleted': 'noop',
        'customer.updated': 'noop',
        'customer.subscription.deleted': 'deleted_subscription',
        'customer.subscription.trial_will_end': 'noop',
        'customer.subscription.updated': 'noop',
        'review.opened': 'noop',
        'dummy': 'noop',
    }

    @classmethod
    def process_response(cls):
        event_dict = json.loads(request.body)
        stripe_secret = g.secrets['stripe_secret_key']
        event = stripe.Event.construct_from(event_dict, stripe_secret)
        status = event.type

        if status == 'invoice.created':
            # sent 1 hr before a subscription is charged or immediately for
            # a new subscription
            invoice = event.data.object
            customer_id = invoice.customer
            account = account_from_stripe_customer_id(customer_id)
            # if the charge hasn't been attempted (meaning this is 1 hr before
            # the charge) check that the account can receive the gold
            if (not invoice.attempted and
                (not account or (account and account._banned))):
                # there's no associated account - delete the subscription
                # to cancel the charge
                g.log.error('no account for stripe invoice: %s', invoice)
                try:
                    cancel_stripe_subscription(customer_id)
                except stripe.InvalidRequestError:
                    pass
        elif status == 'customer.subscription.deleted':
            subscription = event.data.object
            customer_id = subscription.customer
            buyer = account_from_stripe_customer_id(customer_id)
            webhook = Webhook(subscr_id=customer_id, buyer=buyer)
            return status, webhook

        event_type = cls.event_type_mappings.get(status)
        if not event_type:
            raise ValueError('Stripe: unrecognized status %s' % status)
        elif event_type == 'noop':
            return status, None

        charge = event.data.object
        description = charge.description
        invoice_id = charge.invoice
        transaction_id = 'S%s' % charge.id
        pennies = charge.amount
        months, days = months_and_days_from_pennies(pennies)

        if status == 'charge.failed' and invoice_id:
            # we'll get an additional failure notification event of
            # "invoice.payment_failed", don't double notify
            return 'dummy', None
        elif status == 'charge.failed' and not description:
            # create_customer can POST successfully but fail to create a
            # customer because the card is declined. This will trigger a
            # 'charge.failed' notification but without description so we can't
            # do anything with it
            return 'dummy', None
        elif invoice_id:
            # subscription charge - special handling
            customer_id = charge.customer
            buyer = account_from_stripe_customer_id(customer_id)
            if not buyer and status == 'charge.refunded':
                # refund may happen after the subscription has been cancelled
                # and removed from the user's account. the refund process will
                # be able to find the user from the transaction record
                webhook = Webhook(transaction_id=transaction_id)
                return status, webhook
            elif not buyer:
                charge_date = datetime.fromtimestamp(charge.created, tz=g.tz)

                # don't raise exception if charge date is within the past hour
                # db replication lag may cause the account lookup to fail
                if charge_date < timeago('1 hour'):
                    raise ValueError('no buyer for charge: %s' % charge.id)
                else:
                    abort(404, "not found")
            webhook = Webhook(transaction_id=transaction_id,
                              subscr_id=customer_id, pennies=pennies,
                              months=months, goldtype='autorenew',
                              buyer=buyer)
            return status, webhook
        else:
            try:
                passthrough = description[:20]
            except (AttributeError, ValueError):
                g.log.error('stripe_error on charge: %s', charge)
                raise

            webhook = Webhook(passthrough=passthrough,
                transaction_id=transaction_id, pennies=pennies, months=months)
            return status, webhook

    @classmethod
    @handle_stripe_error
    def create_customer(cls, form, token, description):
        customer = stripe.Customer.create(card=token, description=description)

        if (customer['active_card']['address_line1_check'] == 'fail' or
            customer['active_card']['address_zip_check'] == 'fail'):
            form.set_text('.status',
                          _('error: address verification failed'))
            form.find('.stripe-submit').removeAttr('disabled').end()
            return None
        elif customer['active_card']['cvc_check'] == 'fail':
            form.set_text('.status', _('error: cvc check failed'))
            form.find('.stripe-submit').removeAttr('disabled').end()
            return None
        else:
            return customer

    @classmethod
    @handle_stripe_error
    def charge_customer(cls, form, customer, pennies, passthrough,
                        description):
        charge = stripe.Charge.create(
            amount=pennies,
            currency="usd",
            customer=customer['id'],
            description='%s-%s' % (passthrough, description),
        )
        return charge

    @classmethod
    @handle_stripe_error
    def set_creditcard(cls, form, user, token):
        if not user.has_stripe_subscription:
            return

        customer = stripe.Customer.retrieve(user.gold_subscr_id)
        customer.card = token
        customer.save()
        return customer

    @classmethod
    @handle_stripe_error
    def set_subscription(cls, form, customer, plan_id):
        subscription = customer.update_subscription(plan=plan_id)
        return subscription

    @classmethod
    @handle_stripe_error
    def cancel_subscription(cls, form, user):
        if not user.has_stripe_subscription:
            return

        customer = cancel_stripe_subscription(user.gold_subscr_id)

        user.gold_subscr_id = None
        user._commit()
        subject = _('your gold subscription has been cancelled')
        message = _('if you have any questions please email %(email)s')
        message %= {'email': g.goldsupport_email}
        send_system_message(user, subject, message)
        return customer

    @csrf_exempt
    @validatedForm(token=nop('stripeToken'),
                   passthrough=VPrintable("passthrough", max_length=50),
                   pennies=VInt('pennies'),
                   months=VInt("months"),
                   period=VOneOf("period", ("monthly", "yearly")))
    def POST_goldcharge(self, form, jquery, token, passthrough, pennies, months,
                        period):
        """
        Submit charge to stripe.

        Called by GoldPayment form. This submits the charge to stripe, and gold
        will be applied once we receive a webhook from stripe.

        """

        try:
            payment_blob = validate_blob(passthrough)
        except GoldException as e:
            # This should never happen. All fields in the payment_blob
            # are validated on creation
            form.set_text('.status',
                          _('something bad happened, try again later'))
            g.log.debug('POST_goldcharge: %s' % e.message)
            return

        if period:
            plan_id = (g.STRIPE_MONTHLY_GOLD_PLAN if period == 'monthly'
                       else g.STRIPE_YEARLY_GOLD_PLAN)
            if c.user.has_gold_subscription:
                form.set_text('.status',
                              _('your account already has a gold subscription'))
                return
        else:
            plan_id = None
            penny_months, days = months_and_days_from_pennies(pennies)
            if not months or months != penny_months:
                form.set_text('.status', _('stop trying to trick the form'))
                return

        if c.user_is_loggedin:
            description = c.user.name
        else:
            description = payment_blob["email"]
        customer = self.create_customer(form, token, description)
        if not customer:
            return

        if period:
            subscription = self.set_subscription(form, customer, plan_id)
            if not subscription:
                return

            c.user.gold_subscr_id = customer.id
            c.user._commit()

            status = _('subscription created')
            subject = _('reddit gold subscription')
            body = _('Your subscription is being processed and reddit gold '
                     'will be delivered shortly.')
        else:
            charge = self.charge_customer(form, customer, pennies,
                                          passthrough, description)
            if not charge:
                return

            status = _('payment submitted')
            subject = _('reddit gold payment')
            body = _('Your payment is being processed and reddit gold '
                     'will be delivered shortly.')

        form.set_text('.status', status)
        if c.user_is_loggedin:
            body = append_random_bottlecap_phrase(body)
            send_system_message(c.user, subject, body, distinguished='gold-auto')
            form.redirect("/gold/thanks?v=stripe")

    @validatedForm(VUser(),
                   VModhash(),
                   token=nop('stripeToken'))
    def POST_modify_subscription(self, form, jquery, token):
        customer = self.set_creditcard(form, c.user, token)
        if not customer:
            return

        form.set_text('.status', _('your payment details have been updated'))

    @validatedForm(VUser(),
                   VModhash(),
                   user=VByName('user'))
    def POST_cancel_subscription(self, form, jquery, user):
        if user != c.user and not c.user_is_admin:
            self.abort403()
        customer = self.cancel_subscription(form, user)
        if not customer:
            return

        form.set_text(".status", _("your subscription has been cancelled"))

class CoinbaseController(GoldPaymentController):
    name = 'coinbase'
    webhook_secret = g.secrets['coinbase_webhook']
    event_type_mappings = {
        'completed': 'succeeded',
        'cancelled': 'cancelled',
        'mispaid': 'noop',
        'expired': 'noop',
        'payout': 'noop',
    }
    abort_on_error = False

    @classmethod
    def process_response(cls):
        event_dict = json.loads(request.body)

        # handle non-payment events we can ignore
        if 'payout' in event_dict:
            return 'payout', None

        order = event_dict['order']
        transaction_id = 'C%s' % order['id']
        status = order['status']    # new/completed/cancelled
        pennies = int(order['total_native']['cents'])
        months, days = months_and_days_from_pennies(pennies, discount=True)
        passthrough = order['custom']
        webhook = Webhook(passthrough=passthrough,
            transaction_id=transaction_id, pennies=pennies, months=months)
        return status, webhook


class RedditGiftsController(GoldPaymentController):
    """Handle notifications of gold purchases from reddit gifts.

    Payment is handled by reddit gifts. Once an order is complete they can hit
    this route to apply gold to a user's account.

    The post should include data in the form:
    {
        'transaction_id', transaction_id,
        'goldtype': goldtype,
        'buyer': buyer name,
        'pennies': pennies,
        'months': months,
        ['recipient': recipient name,]
        ['giftmessage': message,]
        ['signed': bool,]
    }

    """

    name = 'redditgifts'
    webhook_secret = g.secrets['redditgifts_webhook']
    event_type_mappings = {'succeeded': 'succeeded'}

    def process_response(self):
        data = request.POST

        transaction_id = 'RG%s' % data['transaction_id']
        pennies = int(data['pennies'])
        months = int(data['months'])
        status = 'succeeded'

        goldtype = data['goldtype']
        buyer = Account._by_name(data['buyer'])

        if goldtype == 'gift':
            gift_kw = {
                'recipient': Account._by_name(data['recipient']),
                'giftmessage': _force_unicode(data.get('giftmessage', None)),
                'signed': data.get('signed') == 'True',
            }
        else:
            gift_kw = {}

        webhook = Webhook(transaction_id=transaction_id, pennies=pennies,
                          months=months, goldtype=goldtype, buyer=buyer,
                          **gift_kw)
        return status, webhook


class GoldException(Exception): pass


def validate_blob(custom):
    """Validate payment_blob and return a dict with everything looked up."""
    ret = {}

    if not custom:
        raise GoldException('no custom')

    payment_blob = g.hardcache.get('payment_blob-%s' % str(custom))
    if not payment_blob:
        raise GoldException('no payment_blob')

    if 'account_id' in payment_blob and 'account_name' in payment_blob:
        try:
            buyer = Account._byID(payment_blob['account_id'], data=True)
            ret['buyer'] = buyer
        except NotFound:
            raise GoldException('bad account_id')

        if not buyer.name.lower() == payment_blob['account_name'].lower():
            raise GoldException('buyer mismatch')
    elif 'email' in payment_blob:
        ret['email'] = payment_blob['email']
    else:
        raise GoldException('no account_id or email')

    goldtype = payment_blob['goldtype']
    ret['goldtype'] = goldtype

    if goldtype == 'gift':
        recipient_name = payment_blob.get('recipient', None)
        if not recipient_name:
            raise GoldException('gift missing recpient')
        try:
            recipient = Account._by_name(recipient_name)
            ret['recipient'] = recipient
        except NotFound:
            raise GoldException('bad recipient')
        thing_fullname = payment_blob.get('thing', None)
        if thing_fullname:
            try:
                ret['thing'] = Thing._by_fullname(thing_fullname)
            except NotFound:
                raise GoldException('bad thing')
        ret['signed'] = payment_blob.get('signed', False)
        giftmessage = payment_blob.get('giftmessage')
        giftmessage = _force_unicode(giftmessage) if giftmessage else None
        ret['giftmessage'] = giftmessage
    elif goldtype not in ('onetime', 'autorenew', 'creddits', 'code'):
        raise GoldException('bad goldtype')

    return ret


def days_from_months(months):
    if months >= 12:
        assert months % 12 == 0
        years = months / 12
        days = years * 366
    else:
        days = months * 31
    return days


def subtract_gold_days(user, days):
    user.gold_expiration -= timedelta(days=days)
    if user.gold_expiration < datetime.now(g.display_tz):
        admintools.degolden(user)
    user._commit()


def subtract_gold_creddits(user, num):
    user._incr('gold_creddits', -num)


def reverse_gold_purchase(transaction_id):
    transaction = retrieve_gold_transaction(transaction_id)

    if not transaction:
        raise GoldException('gold_table %s not found' % transaction_id)

    buyer = Account._byID(int(transaction.account_id), data=True)
    recipient = None
    days = transaction.days
    months = days / 31

    if transaction.subscr_id:
        goldtype = 'autorenew'
    else:
        secret = transaction.secret
        pieces = secret.split('-')
        goldtype = pieces[0]

    if goldtype == 'gift':
        recipient_name, secret = pieces[1:]
        recipient = Account._by_name(recipient_name)

    gold_recipient = recipient or buyer
    with gold_recipient.get_read_modify_write_lock() as lock:
        gold_recipient.update_from_cache(lock)

        if goldtype in ('onetime', 'autorenew'):
            subtract_gold_days(buyer, days)

        elif goldtype == 'creddits':
            subtract_gold_creddits(buyer, months)

        elif goldtype == 'gift':
            subtract_gold_days(recipient, days)
            subject = 'your gifted gold has been reversed'
            message = 'sorry, but the payment was reversed'
            send_system_message(recipient, subject, message)
    update_gold_transaction(transaction_id, 'reversed')


def cancel_stripe_subscription(customer_id):
    customer = stripe.Customer.retrieve(customer_id)
    if hasattr(customer, 'deleted'):
        return customer
    customer.delete()
    return customer


def subscr_pm(pennies, months, new_subscr=True):
    price = "$%0.2f" % (pennies/100.0)
    if new_subscr:
        if months % 12 == 0:
            message = _("You have created a yearly Reddit Gold subscription "
                "for %(price)s per year.\n\nThis subscription will renew "
                "automatically yearly until you cancel. You may cancel your "
                "subscription at any time by visiting %(subscr_url)s.\n\n")
        else:
            message = _("You have created a monthly Reddit Gold subscription "
                "for %(price)s per month.\n\nThis subscription will renew "
                "automatically monthly until you cancel. You may cancel your "
                "subscription at any time by visiting %(subscr_url)s.\n\n")
    else:
        if months == 1:
            message = _("Your Reddit Gold subscription has been renewed "
                "for 1 month for %(price)s.\n\nThis subscription will renew "
                "automatically monthly until you cancel. You may cancel your "
                "subscription at any time by visiting %(subscr_url)s.\n\n")
        else:
            message = _("Your Reddit Gold subscription has been renewed "
                "for 1 year for %(price)s.\n\nThis subscription will renew "
                "automatically yearly until you cancel. You may cancel your "
                "subscription at any time by visiting %(subscr_url)s.\n\n")

    subject = _("Reddit Gold Subscription")
    message += _("If you cancel, you will not be billed for any additional "
        "months of service, and service will continue until the end of the "
        "billing period. If you cancel, you will not receive a refund for any "
        "service already paid for.\n\nIf you have any questions, please "
        "contact %(gold_email)s.")

    message %= {
        "price": price,
        "subscr_url": "https://www.reddit.com/gold/subscription",
        "gold_email": g.goldsupport_email,
    }
    return subject, message
