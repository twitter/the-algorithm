package com.twitter.search.core.earlybird.indetwitter.inverted;

import java.io.IOEtwitterception;
import java.util.Comparator;

import javatwitter.annotation.Nullable;

import com.google.common.base.Preconditions;

import org.apache.lucene.indetwitter.Terms;
import org.apache.lucene.indetwitter.TermsEnum;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.StringHelper;

import com.twitter.search.common.hashtable.HashTable;
import com.twitter.search.common.schema.base.EarlybirdFieldType;
import com.twitter.search.common.util.hash.KeysSource;
import com.twitter.search.common.util.io.flushable.DataDeserializer;
import com.twitter.search.common.util.io.flushable.DataSerializer;
import com.twitter.search.common.util.io.flushable.FlushInfo;
import com.twitter.search.common.util.io.flushable.Flushable;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdIndetwitterSegmentAtomicReader;

public class InvertedRealtimeIndetwitter etwittertends InvertedIndetwitter {
  public static final int FItwitterED_HASH_SEED = 0;

  public final class TermHashTable etwittertends HashTable<BytesRef> {

    private final TermPointerEncoding termPointerEncoding;

    public TermHashTable(int size, TermPointerEncoding termPointerEncoding) {
      super(size);
      this.termPointerEncoding = termPointerEncoding;
    }

    public TermHashTable(int[] termsHash, TermPointerEncoding termPointerEncoding) {
      super(termsHash);
      this.termPointerEncoding = termPointerEncoding;
    }

    @Override
    public boolean matchItem(BytesRef term, int candidateTermID) {
      return ByteTermUtils.postingEquals(
          getTermPool(),
          termPointerEncoding.getTetwittertStart(termsArray.termPointers[candidateTermID]), term);
    }

    @Override
    public int hashCodeForItem(int itemID) {
      return ByteTermUtils.hashCode(
          getTermPool(), termPointerEncoding.getTetwittertStart(termsArray.termPointers[itemID]));
    }

    /*
     * Use a fitwittered hash seed to compute the hash code for the given item. This is necessary because
     * we want the TermHashTable to be consistent for lookups in indetwitteres that have been flushed and
     * loaded across restarts and redeploys.
     *
     * Note: previously we used item.hashcode(), however that hash function relies on the seed value
     * StringHelper.GOOD_FAST_HASH_SEED, which is initialized to System.currentTimeMillis() when the
     * JVM process starts up.
     */
    public long lookupItem(BytesRef item) {
      int itemHashCode = StringHelper.murmurhash3_twitter86_32(item, FItwitterED_HASH_SEED);

      return super.lookupItem(item, itemHashCode);
    }
  }


  /**
   * Skip list comparator used by {@link #termsSkipList}. The key would be the bytesRef of the term,
   *   and the value would be the termID of a term.
   *
   *   Notice this comparator is keeping states,
   *   so different threads CANNOT share the same comparator.
   */
  public static final class TermsSkipListComparator implements SkipListComparator<BytesRef> {
    private static final Comparator<BytesRef> BYTES_REF_COMPARATOR = Comparator.naturalOrder();

    private static final int SENTINEL_VALUE = HashTable.EMPTY_SLOT;

    // Initializing two BytesRef to use for later comparisons.
    //   Notice different threads cannot share the same comparator.
    private final BytesRef bytesRef1 = new BytesRef();
    private final BytesRef bytesRef2 = new BytesRef();

    /**
     * We have to pass each part of the indetwitter in since during load process, the comparator
     *   needs to be build before the indetwitter.
     */
    private final InvertedRealtimeIndetwitter invertedIndetwitter;

    public TermsSkipListComparator(InvertedRealtimeIndetwitter invertedIndetwitter) {
      this.invertedIndetwitter = invertedIndetwitter;
    }

    @Override
    public int compareKeyWithValue(BytesRef key, int targetValue, int targetPosition) {
      // No key could represent SENTINEL_VALUE and SENTINEL_VALUE is greatest.
      if (targetValue == SENTINEL_VALUE) {
        return -1;
      } else {
        getTerm(targetValue, bytesRef1);
        return BYTES_REF_COMPARATOR.compare(key, bytesRef1);
      }
    }

    @Override
    public int compareValues(int v1, int v2) {
      // SENTINEL_VALUE is greatest.
      if (v1 != SENTINEL_VALUE && v2 != SENTINEL_VALUE) {
        getTerm(v1, bytesRef1);
        getTerm(v2, bytesRef2);
        return BYTES_REF_COMPARATOR.compare(bytesRef1, bytesRef2);
      } else if (v1 == SENTINEL_VALUE && v2 == SENTINEL_VALUE) {
        return 0;
      } else if (v1 == SENTINEL_VALUE) {
        return 1;
      } else {
        return -1;
      }
    }

    @Override
    public int getSentinelValue() {
      return SENTINEL_VALUE;
    }

    /**
     * Get the term specified by the termID.
     *   This method should be the same as {@link InvertedRealtimeIndetwitter#getTerm}
     */
    private void getTerm(int termID, BytesRef tetwittert) {
      invertedIndetwitter.getTerm(termID, tetwittert);
    }
  }

  private static final int HASHMAP_SIZE = 64 * 1024;

  private SkipListContainer<BytesRef> termsSkipList;

  private final TermPointerEncoding termPointerEncoding;
  private final ByteBlockPool termPool;
  private final SkipListPostingList postingList;

  private int numTerms;
  private int numDocs;
  private int sumTotalTermFreq;
  private int sumTermDocFreq;
  private int matwitterPosition;

  private volatile TermHashTable hashTable;
  private TermsArray termsArray;

  /**
   * Creates a new in-memory real-time inverted indetwitter for the given field.
   */
  public InvertedRealtimeIndetwitter(EarlybirdFieldType fieldType,
                               TermPointerEncoding termPointerEncoding,
                               String fieldName) {
    super(fieldType);
    this.termPool = new ByteBlockPool();

    this.termPointerEncoding = termPointerEncoding;
    this.hashTable = new TermHashTable(HASHMAP_SIZE, termPointerEncoding);

    this.postingList = new SkipListPostingList(
        fieldType.hasPositions()
            ? SkipListContainer.HasPositions.YES
            : SkipListContainer.HasPositions.NO,
        fieldType.isStorePerPositionPayloads()
            ? SkipListContainer.HasPayloads.YES
            : SkipListContainer.HasPayloads.NO,
        fieldName);

    this.termsArray = new TermsArray(
        HASHMAP_SIZE, fieldType.isStoreFacetOffensiveCounters());

    // Create termsSkipList to maintain order if field is support ordered terms.
    if (fieldType.isSupportOrderedTerms()) {
      // Terms skip list does not support position.
      this.termsSkipList = new SkipListContainer<>(
          new TermsSkipListComparator(this),
          SkipListContainer.HasPositions.NO,
          SkipListContainer.HasPayloads.NO,
          "terms");
      this.termsSkipList.newSkipList();
    } else {
      this.termsSkipList = null;
    }
  }

  void setTermsSkipList(SkipListContainer<BytesRef> termsSkipList) {
    this.termsSkipList = termsSkipList;
  }

  SkipListContainer<BytesRef> getTermsSkipList() {
    return termsSkipList;
  }

  private InvertedRealtimeIndetwitter(
      EarlybirdFieldType fieldType,
      int numTerms,
      int numDocs,
      int sumTermDocFreq,
      int sumTotalTermFreq,
      int matwitterPosition,
      int[] termsHash,
      TermsArray termsArray,
      ByteBlockPool termPool,
      TermPointerEncoding termPointerEncoding,
      SkipListPostingList postingList) {
    super(fieldType);
    this.numTerms = numTerms;
    this.numDocs = numDocs;
    this.sumTermDocFreq = sumTermDocFreq;
    this.sumTotalTermFreq = sumTotalTermFreq;
    this.matwitterPosition = matwitterPosition;
    this.termsArray = termsArray;
    this.termPool = termPool;
    this.termPointerEncoding = termPointerEncoding;
    this.hashTable = new TermHashTable(termsHash, termPointerEncoding);
    this.postingList = postingList;
  }

  void insertToTermsSkipList(BytesRef termBytesRef, int termID) {
    if (termsSkipList != null) {
      // Use the comparator passed in while building the skip list since we only have one writer.
      termsSkipList.insert(termBytesRef, termID, SkipListContainer.FIRST_LIST_HEAD);
    }
  }

  @Override
  public int getNumTerms() {
    return numTerms;
  }

  @Override
  public int getNumDocs() {
    return numDocs;
  }

  @Override
  public int getSumTotalTermFreq() {
    return sumTotalTermFreq;
  }

  @Override
  public int getSumTermDocFreq() {
    return sumTermDocFreq;
  }

  @Override
  public Terms createTerms(int matwitterPublishedPointer) {
    return new RealtimeIndetwitterTerms(this, matwitterPublishedPointer);
  }

  @Override
  public TermsEnum createTermsEnum(int matwitterPublishedPointer) {
    // Use SkipListInMemoryTermsEnum if termsSkipList is not null, which indicates field required
    // ordered term.
    if (termsSkipList == null) {
      return new RealtimeIndetwitterTerms.InMemoryTermsEnum(this, matwitterPublishedPointer);
    } else {
      return new RealtimeIndetwitterTerms.SkipListInMemoryTermsEnum(this, matwitterPublishedPointer);
    }
  }

  int getPostingListPointer(int termID) {
    return termsArray.getPostingsPointer(termID);
  }

  @Override
  public int getLargestDocIDForTerm(int termID) {
    if (termID == EarlybirdIndetwitterSegmentAtomicReader.TERM_NOT_FOUND) {
      return TermsArray.INVALID;
    } else {
      return postingList.getDocIDFromPosting(termsArray.largestPostings[termID]);
    }
  }

  @Override
  public int getDF(int termID) {
    if (termID == HashTable.EMPTY_SLOT) {
      return 0;
    } else {
      return this.postingList.getDF(termID, termsArray);
    }
  }

  @Override
  public int getMatwitterPublishedPointer() {
    return this.postingList.getMatwitterPublishedPointer();
  }

  @Override
  public int lookupTerm(BytesRef term) {
    return HashTable.decodeItemId(hashTable.lookupItem(term));
  }

  @Override
  public FacetLabelAccessor getLabelAccessor() {
    final TermsArray termsArrayCopy = this.termsArray;

    return new FacetLabelAccessor() {
      @Override protected boolean seek(long termID) {
        if (termID == HashTable.EMPTY_SLOT) {
          return false;
        }
        int termPointer = termsArrayCopy.termPointers[(int) termID];
        hasTermPayload = termPointerEncoding.hasPayload(termPointer);
        int tetwittertStart = termPointerEncoding.getTetwittertStart(termPointer);
        int termPayloadStart = ByteTermUtils.setBytesRef(termPool, termRef, tetwittertStart);
        if (hasTermPayload) {
          ByteTermUtils.setBytesRef(termPool, termPayload, termPayloadStart);
        }
        offensiveCount = termsArrayCopy.offensiveCounters != null
            ? termsArrayCopy.offensiveCounters[(int) termID] : 0;

        return true;
      }
    };
  }

  @Override
  public boolean hasMatwitterPublishedPointer() {
    return true;
  }

  @Override
  public void getTerm(int termID, BytesRef tetwittert) {
    getTerm(termID, tetwittert, termsArray, termPointerEncoding, termPool);
  }

  /**
   * Etwittertract to helper method so the logic can be shared with
   *   {@link TermsSkipListComparator#getTerm}
   */
  private static void getTerm(int termID, BytesRef tetwittert,
                              TermsArray termsArray,
                              TermPointerEncoding termPointerEncoding,
                              ByteBlockPool termPool) {
    int tetwittertStart = termPointerEncoding.getTetwittertStart(termsArray.termPointers[termID]);
    ByteTermUtils.setBytesRef(termPool, tetwittert, tetwittertStart);
  }

  /**
   * Called when postings hash is too small (> 50% occupied).
   */
  void rehashPostings(int newSize) {
    TermHashTable newTable = new TermHashTable(newSize, termPointerEncoding);
    hashTable.rehash(newTable);
    hashTable = newTable;
  }

  /**
   * Returns per-term array containing the number of documents indetwittered with that term that were
   * considered to be offensive.
   */
  @Nullable
  int[] getOffensiveCounters() {
    return this.termsArray.offensiveCounters;
  }

  /**
   * Returns access to all the terms in this indetwitter as a {@link KeysSource}.
   */
  public KeysSource getKeysSource() {
    final int localNumTerms = this.numTerms;
    final TermsArray termsArrayCopy = this.termsArray;

    return new KeysSource() {
      private int termID = 0;
      private BytesRef tetwittert = new BytesRef();

      @Override
      public int getNumberOfKeys() {
        return localNumTerms;
      }

      /** Must not be called more often than getNumberOfKeys() before rewind() is called */
      @Override
      public BytesRef netwittertKey() {
        Preconditions.checkState(termID < localNumTerms);
        int tetwittertStart = termPointerEncoding.getTetwittertStart(termsArrayCopy.termPointers[termID]);
        ByteTermUtils.setBytesRef(termPool, tetwittert, tetwittertStart);
        termID++;
        return tetwittert;
      }

      @Override
      public void rewind() {
        termID = 0;
      }
    };
  }

  /**
   * Returns byte pool containing term tetwittert for all terms in this indetwitter.
   */
  public ByteBlockPool getTermPool() {
    return this.termPool;
  }

  /**
   * Returns per-term array containing pointers to where the tetwittert of each term is stored in the
   * byte pool returned by {@link #getTermPool()}.
   */
  public int[] getTermPointers() {
    return this.termsArray.termPointers;
  }

  /**
   * Returns the hash table used to look up terms in this indetwitter.
   */
  InvertedRealtimeIndetwitter.TermHashTable getHashTable() {
    return hashTable;
  }


  TermsArray getTermsArray() {
    return termsArray;
  }

  TermsArray growTermsArray() {
    termsArray = termsArray.grow();
    return termsArray;
  }

  @SuppressWarnings("unchecked")
  @Override
  public FlushHandler getFlushHandler() {
    return new FlushHandler(this);
  }

  TermPointerEncoding getTermPointerEncoding() {
    return termPointerEncoding;
  }

  SkipListPostingList getPostingList() {
    return postingList;
  }

  void incrementNumTerms() {
    numTerms++;
  }

  void incrementSumTotalTermFreq() {
    sumTotalTermFreq++;
  }

  public void incrementSumTermDocFreq() {
    sumTermDocFreq++;
  }

  public void incrementNumDocs() {
    numDocs++;
  }

  void setNumDocs(int numDocs) {
    this.numDocs = numDocs;
  }

  void adjustMatwitterPosition(int position) {
    if (position > matwitterPosition) {
      matwitterPosition = position;
    }
  }

  int getMatwitterPosition() {
    return matwitterPosition;
  }

  public static class FlushHandler etwittertends Flushable.Handler<InvertedRealtimeIndetwitter> {
    private static final String NUM_DOCS_PROP_NAME = "numDocs";
    private static final String SUM_TOTAL_TERM_FREQ_PROP_NAME = "sumTotalTermFreq";
    private static final String SUM_TERM_DOC_FREQ_PROP_NAME = "sumTermDocFreq";
    private static final String NUM_TERMS_PROP_NAME = "numTerms";
    private static final String POSTING_LIST_PROP_NAME = "postingList";
    private static final String TERMS_SKIP_LIST_PROP_NAME = "termsSkipList";
    private static final String MAtwitter_POSITION = "matwitterPosition";

    protected final EarlybirdFieldType fieldType;
    protected final TermPointerEncoding termPointerEncoding;

    public FlushHandler(EarlybirdFieldType fieldType,
                        TermPointerEncoding termPointerEncoding) {
      this.fieldType = fieldType;
      this.termPointerEncoding = termPointerEncoding;
    }

    public FlushHandler(InvertedRealtimeIndetwitter objectToFlush) {
      super(objectToFlush);
      this.fieldType = objectToFlush.fieldType;
      this.termPointerEncoding = objectToFlush.getTermPointerEncoding();
    }

    @Override
    protected void doFlush(FlushInfo flushInfo, DataSerializer out)
        throws IOEtwitterception {
      InvertedRealtimeIndetwitter objectToFlush = getObjectToFlush();
      flushInfo.addIntProperty(NUM_TERMS_PROP_NAME, objectToFlush.getNumTerms());
      flushInfo.addIntProperty(NUM_DOCS_PROP_NAME, objectToFlush.numDocs);
      flushInfo.addIntProperty(SUM_TERM_DOC_FREQ_PROP_NAME, objectToFlush.sumTermDocFreq);
      flushInfo.addIntProperty(SUM_TOTAL_TERM_FREQ_PROP_NAME, objectToFlush.sumTotalTermFreq);
      flushInfo.addIntProperty(MAtwitter_POSITION, objectToFlush.matwitterPosition);

      out.writeIntArray(objectToFlush.hashTable.slots());
      objectToFlush.termsArray.getFlushHandler()
          .flush(flushInfo.newSubProperties("termsArray"), out);
      objectToFlush.getTermPool().getFlushHandler()
          .flush(flushInfo.newSubProperties("termPool"), out);
      objectToFlush.getPostingList().getFlushHandler()
          .flush(flushInfo.newSubProperties(POSTING_LIST_PROP_NAME), out);

      if (fieldType.isSupportOrderedTerms()) {
        Preconditions.checkNotNull(objectToFlush.termsSkipList);

        objectToFlush.termsSkipList.getFlushHandler()
            .flush(flushInfo.newSubProperties(TERMS_SKIP_LIST_PROP_NAME), out);
      }
    }

    @Override
    protected InvertedRealtimeIndetwitter doLoad(FlushInfo flushInfo, DataDeserializer in)
        throws IOEtwitterception {
      int[] termsHash = in.readIntArray();
      TermsArray termsArray = (new TermsArray.FlushHandler())
          .load(flushInfo.getSubProperties("termsArray"), in);
      ByteBlockPool termPool = (new ByteBlockPool.FlushHandler())
          .load(flushInfo.getSubProperties("termPool"), in);
      SkipListPostingList postingList = (new SkipListPostingList.FlushHandler())
          .load(flushInfo.getSubProperties(POSTING_LIST_PROP_NAME), in);

      InvertedRealtimeIndetwitter indetwitter = new InvertedRealtimeIndetwitter(
          fieldType,
          flushInfo.getIntProperty(NUM_TERMS_PROP_NAME),
          flushInfo.getIntProperty(NUM_DOCS_PROP_NAME),
          flushInfo.getIntProperty(SUM_TERM_DOC_FREQ_PROP_NAME),
          flushInfo.getIntProperty(SUM_TOTAL_TERM_FREQ_PROP_NAME),
          flushInfo.getIntProperty(MAtwitter_POSITION),
          termsHash,
          termsArray,
          termPool,
          termPointerEncoding,
          postingList);

      if (fieldType.isSupportOrderedTerms()) {
        SkipListComparator<BytesRef> comparator = new TermsSkipListComparator(indetwitter);
        indetwitter.setTermsSkipList((new SkipListContainer.FlushHandler<>(comparator))
            .load(flushInfo.getSubProperties(TERMS_SKIP_LIST_PROP_NAME), in));
      }

      return indetwitter;
    }
  }
}
