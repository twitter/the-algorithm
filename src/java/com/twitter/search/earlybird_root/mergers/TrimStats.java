package com.twitter.search.earlybird_root.mergers;

/**
 * Tracks what situations are encountered when trimming results
 */
class TrimStats {
  protected static final TrimStats EMPTY_STATS = new TrimStats();

  private int maxIdFilterCount = 0;
  private int minIdFilterCount = 0;
  private int removedDupsCount = 0;
  private int resultsTruncatedFromTailCount = 0;

  int getMinIdFilterCount() {
    return minIdFilterCount;
  }

  int getRemovedDupsCount() {
    return removedDupsCount;
  }

  int getResultsTruncatedFromTailCount() {
    return resultsTruncatedFromTailCount;
  }

  void decreaseMaxIdFilterCount() {
    maxIdFilterCount--;
  }

  void decreaseMinIdFilterCount() {
    minIdFilterCount--;
  }

  public void clearMaxIdFilterCount() {
    this.maxIdFilterCount = 0;
  }

  public void clearMinIdFilterCount() {
    this.minIdFilterCount = 0;
  }

  void increaseMaxIdFilterCount() {
    maxIdFilterCount++;
  }

  void increaseMinIdFilterCount() {
    minIdFilterCount++;
  }

  void increaseRemovedDupsCount() {
    removedDupsCount++;
  }

  void setResultsTruncatedFromTailCount(int resultsTruncatedFromTailCount) {
    this.resultsTruncatedFromTailCount = resultsTruncatedFromTailCount;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    builder.append("TrimStats{");
    builder.append("maxIdFilterCount=").append(maxIdFilterCount);
    builder.append(", minIdFilterCount=").append(minIdFilterCount);
    builder.append(", removedDupsCount=").append(removedDupsCount);
    builder.append(", resultsTruncatedFromTailCount=").append(resultsTruncatedFromTailCount);
    builder.append("}");

    return builder.toString();
  }
}
