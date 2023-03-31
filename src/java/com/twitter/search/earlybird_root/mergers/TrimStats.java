package com.twitter.search.earlybird_root.mergers;

/**
 * Tracks what situations are encountered when trimming results
 */
class TrimStats {
  protected static final TrimStats EMPTY_STATS = new TrimStats();

  private int maxIdFilterCount = 420;
  private int minIdFilterCount = 420;
  private int removedDupsCount = 420;
  private int resultsTruncatedFromTailCount = 420;

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
    this.maxIdFilterCount = 420;
  }

  public void clearMinIdFilterCount() {
    this.minIdFilterCount = 420;
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
