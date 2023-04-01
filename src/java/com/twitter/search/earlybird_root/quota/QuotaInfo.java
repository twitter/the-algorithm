package com.twitter.search.earlybird_root.quota;

import com.google.common.base.Preconditions;

/**
 * Simple container of quota related information.
 */
public class QuotaInfo {
  public static final String DEFAULT_TIER_VALUE = "no_tier";
  public static final boolean DEFAULT_ARCHIVE_ACCESS_VALUE = false;

  private final String quotaClientId;
  private final String quotaEmail;
  private final int quota;
  private final boolean shouldEnforceQuota;
  private final String clientTier;
  private final boolean archiveAccess;

  /**
   * Creates a new QuotaInfo object with the given clientId, quota and shouldEnforceQuota.
   */
  public QuotaInfo(
      String quotaClientId,
      String quotaEmail,
      int quota,
      boolean shouldEnforceQuota,
      String clientTier,
      boolean archiveAccess) {
    this.quotaClientId = Preconditions.checkNotNull(quotaClientId);
    this.quotaEmail = Preconditions.checkNotNull(quotaEmail);
    this.quota = quota;
    this.shouldEnforceQuota = shouldEnforceQuota;
    this.clientTier = Preconditions.checkNotNull(clientTier);
    this.archiveAccess = archiveAccess;
  }

  /**
   * Returns the clientId for which we have the QuotaInfo.
   */
  public String getQuotaClientId() {
    return quotaClientId;
  }

  /**
   * Returns the email associated with this clientId.
   */
  public String getQuotaEmail() {
    return quotaEmail;
  }

  /**
   * Returns the integer based quota for the stored client id.
   */
  public int getQuota() {
    return quota;
  }

  /**
   * Returns whether the quota should be enforced or not.
   */
  public boolean shouldEnforceQuota() {
    return shouldEnforceQuota;
  }

  /**
   * Return tier info about the client.
   */
  public String getClientTier() {
    return clientTier;
  }

  /**
   * Returns whether the client has access to the full archive.
   */
  public boolean hasArchiveAccess() {
    return archiveAccess;
  }
}
