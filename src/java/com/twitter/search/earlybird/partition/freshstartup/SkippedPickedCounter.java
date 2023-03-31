package com.twitter.search.earlybird.partition.freshstartup;

class SkippedPickedCounter {
  private long skipped;
  private long picked;
  private String name;

  public SkippedPickedCounter(String name) {
    this.skipped = 0;
    this.picked = 0;
    this.name = name;
  }

  @Override
  public String toString() {
    return String.format("[%s - picked: %,d, skipped: %,d]",
        name, picked, skipped);
  }

  void incrementSkipped() {
    skipped++;
  }
  void incrementPicked() {
    picked++;
  }
}
