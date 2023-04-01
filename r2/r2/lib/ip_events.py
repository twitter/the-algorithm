from collections import Counter

from r2.lib.geoip import location_by_ips, organization_by_ips
from r2.lib.utils import tup
from r2.models.ip import IPsByAccount, AccountsByIP


def ips_by_account_id(account_id, limit=None):
    ips = IPsByAccount.get(account_id, column_count=limit or 1000)
    flattened_ips = [j for i in ips for j in i.iteritems()]
    locations = location_by_ips(set(ip for _, ip in flattened_ips))
    orgs = organization_by_ips(set(ip for _, ip in flattened_ips))

    # Deduplicate and summarize total usage time
    counts = Counter((ip for _, ip in flattened_ips))
    seen = set()
    results = []
    for visit_time, ip in flattened_ips:
        if ip in seen:
            continue
        results.append(
            (ip, visit_time, locations.get(ip) or {},
                orgs.get(ip), counts.get(ip))
        )
        seen.add(ip)
    return results


def account_ids_by_ip(ip, after=None, before=None, limit=1000):
    """Get a list of account IDs that an IP has accessed.

    Parameters:
    after -- a `datetime.datetime` from which results should start
    before -- a `datetime.datetime` from which results should end.  If `after`
        is specified, this will be ignored.
    limit -- number of results to return
    """
    ips = tup(ip)
    results = []
    flattened_accounts = {}
    for ip in ips:
        if before and not after:
            # One less result will be returned for `before` queries, so we
            # increase the limit by one.
            account_ip = AccountsByIP.get(
                ip, column_start=before, column_count=limit + 1,
                column_reversed=False)
            account_ip = sorted(account_ip, reverse=True)
        else:
            account_ip = AccountsByIP.get(
                ip, column_start=after, column_count=limit)
        flattened_account_ip = [j for i in account_ip
                                for j in i.iteritems()]
        flattened_accounts[ip] = flattened_account_ip

    for ip, flattened_account_ip in flattened_accounts.iteritems():
        for last_visit, account in flattened_account_ip:
            results.append((account, last_visit, [ip]))
    return results
