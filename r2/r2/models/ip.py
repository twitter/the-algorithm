import datetime

from pycassa.batch import Mutator
from pycassa.system_manager import ASCII_TYPE
from pylons import app_globals as g
import pytz

from r2.lib.contrib.ipaddress import ip_address
from r2.lib.db import tdb_cassandra


__all__ = ["IPsByAccount", "AccountsByIP"]


CONNECTION_POOL = g.cassandra_pools['main']


CF_TTL = datetime.timedelta(days=100).total_seconds()


class IPsByAccount(tdb_cassandra.View):

    _use_db = True
    _extra_schema_creation_args = {
        "key_validation_class": ASCII_TYPE,
        "default_validation_class": ASCII_TYPE,
    }
    _compare_with = tdb_cassandra.DateType()
    _ttl = CF_TTL

    @classmethod
    def set(cls, account_id, ip, date=None):
        if date is None:
            date = datetime.datetime.now(g.tz)
        cls._set_values(str(account_id), {date: ip})

    @classmethod
    def get(cls,
            account_id,
            column_start=None,
            column_finish=None,
            column_count=100,
            column_reversed=True):
        """Get the last accessed times of an account by IP address.

        Returns a list of dicts of the last accessed times of an account by
        IP address, most recent first.

        Example:

            >>> IPsByAccount.get(52)
            [
                {datetime.datetime(2016, 1, 24, 6, 23, 0, 326000, tzinfo=<UTC>): '127.0.0.3'},
                {datetime.datetime(2016, 1, 24, 6, 22, 58, 983000, tzinfo=<UTC>): '127.0.0.2'},
            ]

        Pagination is done based on the date of the entry.  For instance, to
        continue getting results from the previous set:

            >>> IPsByAccount.get(52, column_start=datetime.datetime(
                    2016, 1, 24, 6, 22, 58, 983000))
            [
                {datetime.datetime(2016, 1, 24, 6, 21, 50, 121000, tzinfo=<UTC>): '127.0.0.1'},
            ]
        """
        column_start = column_start or ""
        column_finish = column_finish or ""
        results = []
        query = tdb_cassandra.ColumnQuery(
            cls, (str(account_id),),
            column_start=column_start,
            column_finish=column_finish,
            column_count=column_count,
            column_reversed=column_reversed)
        for date_ip in query:
            for dt, ip in date_ip.iteritems():
                results.append({dt.replace(tzinfo=pytz.utc): ip})
        return results


class AccountsByIP(tdb_cassandra.View):

    _use_db = True
    _extra_schema_creation_args = {
        "key_validation_class": ASCII_TYPE,
        "default_validation_class": ASCII_TYPE,
    }
    _compare_with = tdb_cassandra.DateType()
    _ttl = CF_TTL

    @classmethod
    def set(cls, ip, account_id, date=None):
        if date is None:
            date = datetime.datetime.now(g.tz)
        cls._set_values(ip, {date: str(account_id)})

    @classmethod
    def get(cls,
            ip,
            column_start=None,
            column_finish=None,
            column_count=100,
            column_reversed=True):
        """Get the times an IP address has accessed various account IDs.

        Returns a list of dicts of the times an IP address has accessed
        various account IDs, most recent first:

        Example:

            >>> AccountsByIP.get('127.0.0.1')
            [
                {datetime.datetime(2016, 1, 22, 23, 28, 21, 286000, tzinfo=<UTC>): 52},
                {datetime.datetime(2016, 1, 22, 23, 28, 24, 301000, tzinfo=<UTC>): 53},
            ]

        Pagination is also supported.  See the documentation for
        ``IPsByAccount.get``.
        """
        column_start = column_start or ""
        column_finish = column_finish or ""
        results = []
        query = tdb_cassandra.ColumnQuery(
            cls, (ip,),
            column_start=column_start,
            column_finish=column_finish,
            column_count=column_count,
            column_reversed=column_reversed)
        for date_account in query:
            for dt, account in date_account.iteritems():
                results.append({dt.replace(tzinfo=pytz.utc): int(account)})
        return results


def set_account_ip(account_id, ip, date=None):
    """Set an IP address as having accessed an account.

    Updates all underlying datastores.
    """
    # don't store private IPs, send event + string so we can investigate this
    if ip_address(ip).is_private:
        g.stats.simple_event('ip.private_ip_storage_prevented')
        g.stats.count_string('private_ip_storage_prevented', ip)
        return

    if date is None:
        date = datetime.datetime.now(g.tz)
    m = Mutator(CONNECTION_POOL)
    m.insert(IPsByAccount._cf, str(account_id), {date: ip}, ttl=CF_TTL)
    m.insert(AccountsByIP._cf, ip, {date: str(account_id)}, ttl=CF_TTL)
    m.send()
