package com.twitter.search.earlybird_root.quota;

import java.util.Optional;

/** A manager that determines how quota restrictions should be applied for each client. */
public interface ClientIdQuotaManager {
  /**
   * Returns the quota for the given client, if one is set.
   *
   * @param clientId The ID of the client.
   * @return The quota for the given client (in requests per second), if one is set.
   */
  Optional<QuotaInfo> getQuotaForClient(String clientId);

  /**
   * Returns the common pool quota. A common pool quota must always be set.
   *
   * @return The common pool quota (in requests per second).
   */
  QuotaInfo getCommonPoolQuota();

}
