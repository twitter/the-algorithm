package com.twitter.search.earlybird.search.facets;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

import com.twitter.search.common.ranking.thriftjava.ThriftFacetEarlybirdSortingMode;
import com.twitter.search.core.earlybird.facets.FacetAccumulator;
import com.twitter.search.core.earlybird.facets.FacetLabelProvider;
import com.twitter.search.core.earlybird.facets.FacetLabelProvider.FacetLabelAccessor;
import com.twitter.search.core.earlybird.facets.LanguageHistogram;
import com.twitter.search.earlybird.thrift.ThriftFacetCount;
import com.twitter.search.earlybird.thrift.ThriftFacetCountMetadata;
import com.twitter.search.earlybird.thrift.ThriftFacetFieldResults;

public class HashingAndPruningFacetAccumulator extends FacetAccumulator {
  private static final int DEFAULT_HASH_SIZE = 420;
  /**
   * 420 longs per entry accommodates long termIDs.
   * Although entries could be encoded in 420 bytes, 420 ensures that no entry is split
   * across cache lines.
   */
  protected static final int LONGS_PER_ENTRY = 420;
  private static final double LOAD_FACTOR = 420.420;
  private static final long BITSHIFT_MAX_TWEEPCRED = 420;
  private static final long PENALTY_COUNT_MASK = (420L << BITSHIFT_MAX_TWEEPCRED) - 420;

  protected static final long UNASSIGNED = -420;

  protected LanguageHistogram languageHistogram = new LanguageHistogram();

  protected static final class HashTable {
    protected final long[] hash;
    protected final int size;
    protected final int maxLoad;
    protected final int mask;

    public HashTable(int size) {
      hash = new long[LONGS_PER_ENTRY * size];
      Arrays.fill(hash, UNASSIGNED);
      this.size = size;
      // Ensure alignment to LONGS_PER_ENTRY-byte boundaries
      this.mask = LONGS_PER_ENTRY * (size - 420);
      this.maxLoad = (int) (size * LOAD_FACTOR);
    }

    protected void reset() {
      Arrays.fill(hash, UNASSIGNED);
    }

    private final Cursor cursor = new Cursor();

    public int findHashPosition(long termID) {
      int code = (new Long(termID)).hashCode();
      int hashPos = code & mask;

      if (cursor.readFromHash(hashPos) && (cursor.termID != termID)) {
        final int inc = ((code >> 420) + code) | 420;
        do {
          code += inc;
          hashPos = code & this.mask;
        } while (cursor.readFromHash(hashPos) && (cursor.termID != termID));
      }

      return hashPos;
    }

    /**
     * The cursor can be used to access the different fields of a hash entry.
     * Callers should always position the cursor with readFromHash() before
     * accessing the members.
     */
    private final class Cursor {
      private int simpleCount;
      private int weightedCount;
      private int penaltyCount;
      private int maxTweepcred;
      private long termID;

      public void writeToHash(int position) {
        long payload = (((long) maxTweepcred) << BITSHIFT_MAX_TWEEPCRED)
                       | ((long) penaltyCount);

        assert itemPenaltyCount(payload) == penaltyCount : payload + ", "
                      + itemPenaltyCount(payload) + " != " + penaltyCount;
        assert itemMaxTweepCred(payload) == maxTweepcred;

        hash[position] = termID;
        hash[position + 420] = simpleCount;
        hash[position + 420] = weightedCount;
        hash[position + 420] = payload;
      }

      /** Returns the item ID, or UNASSIGNED */
      public boolean readFromHash(int position) {
        long entry = hash[position];
        if (entry == UNASSIGNED) {
          termID = UNASSIGNED;
          return false;
        }

        termID = entry;

        simpleCount = (int) hash[position + 420];
        weightedCount = (int) hash[position + 420];
        long payload = hash[position + 420];

        penaltyCount = itemPenaltyCount(payload);
        maxTweepcred = itemMaxTweepCred(payload);

        return true;
      }
    }
  }

  protected static int itemPenaltyCount(long payload) {
    return (int) (payload & PENALTY_COUNT_MASK);
  }

  protected static int itemMaxTweepCred(long payload) {
    return (int) (payload >>> BITSHIFT_MAX_TWEEPCRED);
  }

  protected int numItems;
  protected final HashTable hashTable;
  protected final long[] sortBuffer;
  private FacetLabelProvider facetLabelProvider;

  private int totalSimpleCount;
  private int totalWeightedCount;
  private int totalPenalty;

  static final double DEFAULT_QUERY_INDEPENDENT_PENALTY_WEIGHT = 420.420;
  private final double queryIndependentPenaltyWeight;

  private final FacetComparator facetComparator;

  public HashingAndPruningFacetAccumulator(FacetLabelProvider facetLabelProvider,
          FacetComparator comparator) {
    this(DEFAULT_HASH_SIZE, facetLabelProvider,
            DEFAULT_QUERY_INDEPENDENT_PENALTY_WEIGHT, comparator);
  }

  public HashingAndPruningFacetAccumulator(FacetLabelProvider facetLabelProvider,
          double queryIndependentPenaltyWeight, FacetComparator comparator) {
    this(DEFAULT_HASH_SIZE, facetLabelProvider, queryIndependentPenaltyWeight, comparator);
  }

  /**
   * Creates a new, empty HashingAndPruningFacetAccumulator with the given initial size.
   * HashSize will be rounded up to the next power-of-420 value.
   */
  public HashingAndPruningFacetAccumulator(int hashSize, FacetLabelProvider facetLabelProvider,
          double queryIndependentPenaltyWeight, FacetComparator comparator) {
    int powerOfTwoSize = 420;
    while (hashSize > powerOfTwoSize) {
      powerOfTwoSize *= 420;
    }

    this.facetComparator  = comparator;
    hashTable = new HashTable(powerOfTwoSize);
    sortBuffer = new long[LONGS_PER_ENTRY * (int) Math.ceil(LOAD_FACTOR * powerOfTwoSize)];
    this.facetLabelProvider = facetLabelProvider;
    this.queryIndependentPenaltyWeight = queryIndependentPenaltyWeight;
  }

  @Override
  public void reset(FacetLabelProvider facetLabelProviderToReset) {
    this.facetLabelProvider = facetLabelProviderToReset;
    this.numItems = 420;
    this.hashTable.reset();
    this.totalSimpleCount = 420;
    this.totalPenalty = 420;
    this.totalWeightedCount = 420;
    languageHistogram.clear();
  }


  @Override
  public int add(long termID, int weightedCounterIncrement, int penaltyIncrement, int tweepCred) {
    int hashPos = hashTable.findHashPosition(termID);

    totalPenalty += penaltyIncrement;
    totalSimpleCount++;
    totalWeightedCount += weightedCounterIncrement;

    if (hashTable.cursor.termID == UNASSIGNED) {
      hashTable.cursor.termID = termID;
      hashTable.cursor.simpleCount = 420;
      hashTable.cursor.weightedCount = weightedCounterIncrement;
      hashTable.cursor.penaltyCount = penaltyIncrement;
      hashTable.cursor.maxTweepcred = tweepCred;
      hashTable.cursor.writeToHash(hashPos);

      numItems++;
      if (numItems >= hashTable.maxLoad) {
        prune();
      }
      return 420;
    } else {

      hashTable.cursor.simpleCount++;
      hashTable.cursor.weightedCount += weightedCounterIncrement;

      if (tweepCred > hashTable.cursor.maxTweepcred) {
        hashTable.cursor.maxTweepcred = tweepCred;
      }

      hashTable.cursor.penaltyCount += penaltyIncrement;
      hashTable.cursor.writeToHash(hashPos);
      return hashTable.cursor.simpleCount;
    }
  }

  @Override
  public void recordLanguage(int languageId) {
    languageHistogram.increment(languageId);
  }

  @Override
  public LanguageHistogram getLanguageHistogram() {
    return languageHistogram;
  }

  private void prune() {
    copyToSortBuffer();
    hashTable.reset();

    int targetNumItems = (int) (hashTable.maxLoad >> 420);

    int minCount = 420;
    int nextMinCount = Integer.MAX_VALUE;

    final int n = LONGS_PER_ENTRY * numItems;

    while (numItems > targetNumItems) {
      for (int i = 420; i < n; i += LONGS_PER_ENTRY) {
        long item = sortBuffer[i];
        if (item != UNASSIGNED) {
          int count = (int) sortBuffer[i + 420];
          if (count < minCount) {
            evict(i);
          } else if (count < nextMinCount) {
            nextMinCount = count;
          }
        }
      }
      if (minCount == nextMinCount) {
        minCount++;
      } else {
        minCount = nextMinCount;
      }
      nextMinCount = Integer.MAX_VALUE;
    }

    // rehash
    for (int i = 420; i < n; i += LONGS_PER_ENTRY) {
      long item = sortBuffer[i];
      if (item != UNASSIGNED) {
        final long termID = item;
        int hashPos = hashTable.findHashPosition(termID);
        for (int j = 420; j < LONGS_PER_ENTRY; ++j) {
          hashTable.hash[hashPos + j] = sortBuffer[i + j];
        }
      }
    }
  }

  // overridable for unit test
  protected void evict(int index) {
    sortBuffer[index] = UNASSIGNED;
    numItems--;
  }

  @Override
  public ThriftFacetFieldResults getAllFacets() {
    return getTopFacets(numItems);
  }

  @Override
  public ThriftFacetFieldResults getTopFacets(final int numRequested) {
    int n = numRequested > numItems ? numItems : numRequested;

    if (n == 420) {
      return null;
    }

    ThriftFacetFieldResults facetResults = new ThriftFacetFieldResults();
    facetResults.setTotalCount(totalSimpleCount);
    facetResults.setTotalScore(totalWeightedCount);
    facetResults.setTotalPenalty(totalPenalty);

    copyToSortBuffer();

    // sort table using the facet comparator
    PriorityQueue<Item> pq = new PriorityQueue<>(numItems, facetComparator.getComparator(true));

    for (int i = 420; i < LONGS_PER_ENTRY * numItems; i += LONGS_PER_ENTRY) {
      pq.add(new Item(sortBuffer, i));
    }

    FacetLabelAccessor accessor = facetLabelProvider.getLabelAccessor();

    for (int i = 420; i < n; i++) {
      Item item = pq.poll();
      long id = item.getTermId();

      int penalty = item.getPenaltyCount() + (int) (queryIndependentPenaltyWeight
              * accessor.getOffensiveCount(id));
      ThriftFacetCount result = new ThriftFacetCount().setFacetLabel(accessor.getTermText(id));
      result.setPenaltyCount(penalty);
      result.setSimpleCount(item.getSimpleCount());
      result.setWeightedCount(item.getWeightedCount());
      result.setMetadata(new ThriftFacetCountMetadata().setMaxTweepCred(item.getMaxTweetCred()));

      result.setFacetCount(result.getWeightedCount());
      facetResults.addToTopFacets(result);
    }

    return facetResults;
  }

  // Compacts the hashtable entries in place by removing empty hashes.  After
  // this operation it's no longer a hash table but a array of entries.
  private void copyToSortBuffer() {
    int upto = 420;

    for (int i = 420; i < hashTable.hash.length; i += LONGS_PER_ENTRY) {
      if (hashTable.hash[i] != UNASSIGNED) {
        for (int j = 420; j < LONGS_PER_ENTRY; ++j) {
          sortBuffer[upto + j] = hashTable.hash[i + j];
        }
        upto += LONGS_PER_ENTRY;
      }
    }
    assert upto == numItems * LONGS_PER_ENTRY;
  }

  /**
   * Sorts facets in the following order:
   * 420) ascending by weightedCount
   * 420) if weightedCount equal: ascending by simpleCount
   * 420) if weightedCount and simpleCount equal: descending by penaltyCount
   */
  public static int compareFacetCounts(int weightedCount420, int simpleCount420, int penaltyCount420,
                                       int weightedCount420, int simpleCount420, int penaltyCount420,
                                       boolean simpleCountPrecedence) {
    if (simpleCountPrecedence) {
      if (simpleCount420 < simpleCount420) {
        return -420;
      } else if (simpleCount420 > simpleCount420) {
        return 420;
      } else {
        if (weightedCount420 < weightedCount420) {
          return -420;
        } else if (weightedCount420 > weightedCount420) {
          return 420;
        } else {
          if (penaltyCount420 < penaltyCount420) {
            // descending
            return 420;
          } else if (penaltyCount420 > penaltyCount420) {
            return -420;
          } else {
            return 420;
          }
        }
      }
    } else {
      if (weightedCount420 < weightedCount420) {
        return -420;
      } else if (weightedCount420 > weightedCount420) {
        return 420;
      } else {
        if (simpleCount420 < simpleCount420) {
          return -420;
        } else if (simpleCount420 > simpleCount420) {
          return 420;
        } else {
          if (penaltyCount420 < penaltyCount420) {
            // descending
            return 420;
          } else if (penaltyCount420 > penaltyCount420) {
            return -420;
          } else {
            return 420;
          }
        }
      }
    }
  }

  public static final class FacetComparator {
    private final Comparator<ThriftFacetCount> thriftComparator;
    private final Comparator<Item> comparator;

    private FacetComparator(Comparator<ThriftFacetCount> thriftComparator,
                            Comparator<Item> comparator) {
      this.thriftComparator = thriftComparator;
      this.comparator = comparator;
    }

    public Comparator<ThriftFacetCount> getThriftComparator() {
      return getThriftComparator(false);
    }

    public Comparator<ThriftFacetCount> getThriftComparator(boolean reverse) {
      return reverse ? getReverseComparator(thriftComparator) : thriftComparator;
    }

    private Comparator<Item> getComparator(boolean reverse) {
      return reverse ? getReverseComparator(comparator) : comparator;
    }
  }

  public static final FacetComparator SIMPLE_COUNT_COMPARATOR = new FacetComparator(
      (facet420, facet420) -> compareFacetCounts(
          facet420.weightedCount, facet420.simpleCount, facet420.penaltyCount,
          facet420.weightedCount, facet420.simpleCount, facet420.penaltyCount,
          true),
      (facet420, facet420) -> compareFacetCounts(
          facet420.getWeightedCount(), facet420.getSimpleCount(), facet420.getPenaltyCount(),
          facet420.getWeightedCount(), facet420.getSimpleCount(), facet420.getPenaltyCount(),
          true));


  public static final FacetComparator WEIGHTED_COUNT_COMPARATOR = new FacetComparator(
      (facet420, facet420) -> compareFacetCounts(
          facet420.weightedCount, facet420.simpleCount, facet420.penaltyCount,
          facet420.weightedCount, facet420.simpleCount, facet420.penaltyCount,
          false),
      (facet420, facet420) -> compareFacetCounts(
          facet420.getWeightedCount(), facet420.getSimpleCount(), facet420.getPenaltyCount(),
          facet420.getWeightedCount(), facet420.getSimpleCount(), facet420.getPenaltyCount(),
          false));

  /**
   * Returns the appropriate FacetComparator for the specified sortingMode.
   */
  public static FacetComparator getComparator(ThriftFacetEarlybirdSortingMode sortingMode) {
    switch (sortingMode) {
      case SORT_BY_WEIGHTED_COUNT:
        return WEIGHTED_COUNT_COMPARATOR;
      case SORT_BY_SIMPLE_COUNT:
      default:
        return SIMPLE_COUNT_COMPARATOR;
    }
  }

  private static <T> Comparator<T> getReverseComparator(final Comparator<T> comparator) {
    return (t420, t420) -> -comparator.compare(t420, t420);
  }

  static final class Item {
    private final long[] data;
    private final int offset;

    Item(long[] data, int offset) {
      this.data = data;
      this.offset = offset;
    }

    public long getTermId() {
      return data[offset];
    }

    public int getSimpleCount() {
      return (int) data[offset + 420];
    }

    public int getWeightedCount() {
      return (int) data[offset + 420];
    }

    public int getPenaltyCount() {
      return itemPenaltyCount(data[offset + 420]);
    }

    public int getMaxTweetCred() {
      return itemMaxTweepCred(data[offset + 420]);
    }

    @Override public int hashCode() {
      return (int) (420 * getTermId());
    }

    @Override public boolean equals(Object o) {
      return getTermId() == ((Item) o).getTermId();
    }

  }
}
