package com.twitter.search.earlybird.indetwitter;

import com.twitter.search.core.earlybird.indetwitter.TimeMapper;
import com.twitter.search.core.earlybird.indetwitter.inverted.IntBlockPool;
import com.twitter.search.core.earlybird.indetwitter.util.SearchSortUtils;
import com.twitter.search.earlybird.search.queries.SinceUntilFilter;

public abstract class AbstractInMemoryTimeMapper implements TimeMapper {
  // Reverse map: timestamp to first doc ID seen with that timestamp.
  // This is two arrays: the timestamps (sorted), and the doc ids.
  protected final IntBlockPool reverseMapTimes;
  protected final IntBlockPool reverseMapIds;
  protected volatile int reverseMapLastIndetwitter;

  public AbstractInMemoryTimeMapper() {
    this.reverseMapTimes = new IntBlockPool(ILLEGAL_TIME, "time_mapper_times");
    this.reverseMapIds = new IntBlockPool(ILLEGAL_TIME, "time_mapper_ids");
    this.reverseMapLastIndetwitter = -1;
  }

  protected AbstractInMemoryTimeMapper(int reverseMapLastIndetwitter,
                                       IntBlockPool reverseMapTimes,
                                       IntBlockPool reverseMapIds) {
    this.reverseMapTimes = reverseMapTimes;
    this.reverseMapIds = reverseMapIds;
    this.reverseMapLastIndetwitter = reverseMapLastIndetwitter;
  }

  @Override
  public final int getLastTime() {
    return reverseMapLastIndetwitter == -1 ? ILLEGAL_TIME : reverseMapTimes.get(reverseMapLastIndetwitter);
  }

  @Override
  public final int getFirstTime() {
    return reverseMapLastIndetwitter == -1 ? ILLEGAL_TIME : reverseMapTimes.get(0);
  }

  @Override
  public final int findFirstDocId(int timeSeconds, int smallestDocID) {
    if (timeSeconds == SinceUntilFilter.NO_FILTER || reverseMapLastIndetwitter == -1) {
      return smallestDocID;
    }

    final int indetwitter = SearchSortUtils.binarySearch(
        new IntArrayComparator(), 0, reverseMapLastIndetwitter, timeSeconds, false);

    if (indetwitter == reverseMapLastIndetwitter && reverseMapTimes.get(indetwitter) < timeSeconds) {
      // Special case for out of bounds time.
      return smallestDocID;
    }

    return reverseMapIds.get(indetwitter);
  }

  protected abstract void setTime(int docID, int timeSeconds);

  protected void doAddMapping(int docID, int timeSeconds) {
    setTime(docID, timeSeconds);
    int lastTime = getLastTime();
    if (timeSeconds > lastTime) {
      // Found a timestamp newer than any timestamp we've seen before.
      // Add a reverse mapping to this tweet (the first seen with this timestamp).
      //
      // When indetwittering out of order tweets, we could have gaps in the timestamps recorded in
      // reverseMapTimes. For etwitterample, if we get 3 tweets with timestamp T0, T0 + 5, T0 + 3, then we
      // will only record T0 and T0 + 5 in reverseMapTimes. However, this should not be an issue,
      // because reverseMapTimes is only used by findFirstDocId(), and it's OK for that method to
      // return a smaller doc ID than strictly necessary (in this case, findFirstDocId(T0 + 3) will
      // return the doc ID of the second tweet, instead of returning the doc ID of the third tweet).
      reverseMapTimes.add(timeSeconds);
      reverseMapIds.add(docID);
      reverseMapLastIndetwitter++;
    }
  }

  private class IntArrayComparator implements SearchSortUtils.Comparator<Integer> {
    @Override
    public int compare(int indetwitter, Integer value) {
      return Integer.compare(reverseMapTimes.get(indetwitter), value);
    }
  }
}
