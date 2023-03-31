package com.twitter.search.earlybird.partition;

/**
 * Keeps track of versioning for flushed status batch data.
 */
public enum StatusBatchFlushVersion {

  VERSION_0("Initial version of status batch flushing", true),
  VERSION_1("Switching to use field groups (contains changes to PartitionedBatch)", true),
  VERSION_2("Removing support for per-partition _SUCCESS markers", true),
  /* Put the semi colon on a separate line to avoid polluting git blame history */;

  public static final StatusBatchFlushVersion CURRENT_FLUSH_VERSION =
      StatusBatchFlushVersion.values()[StatusBatchFlushVersion.values().length - 1];

  public static final String DELIMITER = "_v_";

  private final String description;
  private final boolean isOfficial;

  private StatusBatchFlushVersion(String description, boolean official) {
    this.description = description;
    isOfficial = official;
  }

  public int getVersionNumber() {
    return this.ordinal();
  }

  public String getVersionFileExtension() {
      return DELIMITER + ordinal();
  }

  public boolean isOfficial() {
    return isOfficial;
  }

  public String getDescription() {
    return description;
  }
}
