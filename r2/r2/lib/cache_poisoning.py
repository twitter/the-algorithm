import hashlib
import hmac
from pylons import app_globals as g

# A map of cache policies to their respective cache headers
# loggedout omitted because loggedout responses are intentionally cacheable
CACHE_POLICY_DIRECTIVES = {
    "loggedin_www": {
        "cache-control": {"private", "no-cache"},
        "pragma": {"no-cache"},
        "expires": set(),
    },
    "loggedin_www_new": {
        "cache-control": {"private", "max-age=0", "must-revalidate"},
        "pragma": set(),
        "expires": {"-1"},
    },
    "loggedin_mweb": {
        "cache-control": {"private", "no-cache"},
        "pragma": set(),
        "expires": set(),
    },
}


def make_poisoning_report_mac(
        poisoner_canary,
        poisoner_name,
        poisoner_id,
        cache_policy,
        source,
        # Can't MAC based on URL, some caches don't care about the
        # order of query params and suchlike.
        route_name,
):
    """
    Make a MAC to send with cache poisoning reports for this page
    """
    mac_key = g.secrets["cache_poisoning"]
    mac_data = (
        poisoner_canary,
        poisoner_name,
        str(poisoner_id),
        cache_policy,
        source,
        route_name,
    )
    return hmac.new(mac_key, "|".join(mac_data), hashlib.sha1).hexdigest()


def cache_headers_valid(policy_name, headers):
    """Check if a response's headers make sense given a cache policy"""

    policy_headers = CACHE_POLICY_DIRECTIVES[policy_name]

    for header_name, expected_vals in policy_headers.items():
        # Cache-Control is a little special, you can have multiple directives
        # in multiple headers
        found_vals = set(headers.get(header_name, []))
        if header_name == "cache-control":
            parsed_cache_control = set()
            for cache_header in found_vals:
                for split_header in cache_header.split(","):
                    cache_directive = split_header.strip().lower()
                    parsed_cache_control.add(cache_directive)
            if parsed_cache_control != expected_vals:
                return False
        elif found_vals != expected_vals:
            return False
    return True
