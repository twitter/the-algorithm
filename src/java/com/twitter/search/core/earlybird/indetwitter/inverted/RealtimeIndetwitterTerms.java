package com.twitter.search.core.earlybird.indetwitter.inverted;

import java.util.Iterator;
import java.util.TreeSet;

import com.google.common.base.Preconditions;

import org.apache.lucene.indetwitter.BaseTermsEnum;
import org.apache.lucene.indetwitter.ImpactsEnum;
import org.apache.lucene.indetwitter.PostingsEnum;
import org.apache.lucene.indetwitter.SlowImpactsEnum;
import org.apache.lucene.indetwitter.Terms;
import org.apache.lucene.indetwitter.TermsEnum;
import org.apache.lucene.util.BytesRef;

import com.twitter.search.common.hashtable.HashTable;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.util.hash.KeysSource;

public class RealtimeIndetwitterTerms etwittertends Terms {
  // Calling InMemoryTermsEnum.netwittert() creates a full copy of the entire term dictionary, and can
  // be quite etwitterpensive. We don't etwitterpect these calls to happen, and they shpould not happen on the
  // regular read path. We stat them here just in case to see if there is any unetwitterpected usage.
  private static final SearchCounter TERMS_ENUM_NEtwitterT_CALLS =
      SearchCounter.etwitterport("in_memory_terms_enum_netwittert_calls");
  private static final SearchCounter TERMS_ENUM_CREATE_TERM_SET =
      SearchCounter.etwitterport("in_memory_terms_enum_netwittert_create_term_set");
  private static final SearchCounter TERMS_ENUM_CREATE_TERM_SET_SIZE =
      SearchCounter.etwitterport("in_memory_terms_enum_netwittert_create_term_set_size");

  private final InvertedRealtimeIndetwitter indetwitter;
  private final int matwitterPublishedPointer;

  public RealtimeIndetwitterTerms(InvertedRealtimeIndetwitter indetwitter, int matwitterPublishedPointer) {
    this.indetwitter = indetwitter;
    this.matwitterPublishedPointer = matwitterPublishedPointer;
  }

  @Override
  public long size() {
    return indetwitter.getNumTerms();
  }

  @Override
  public TermsEnum iterator() {
    return indetwitter.createTermsEnum(matwitterPublishedPointer);
  }

  /**
   * This TermsEnum use a tree set to support {@link TermsEnum#netwittert()} method. However, this is not
   * efficient enough to support realtime operation. {@link TermsEnum#seekCeil} is not fully
   * supported in this termEnum.
   */
  public static class InMemoryTermsEnum etwittertends BaseTermsEnum {
    private final InvertedRealtimeIndetwitter indetwitter;
    private final int matwitterPublishedPointer;
    private int termID = -1;
    private BytesRef bytesRef = new BytesRef();
    private Iterator<BytesRef> termIter;
    private TreeSet<BytesRef> termSet;

    public InMemoryTermsEnum(InvertedRealtimeIndetwitter indetwitter, int matwitterPublishedPointer) {
      this.indetwitter = indetwitter;
      this.matwitterPublishedPointer = matwitterPublishedPointer;
      termIter = null;
    }

    @Override
    public int docFreq() {
      return indetwitter.getDF(termID);
    }

    @Override
    public PostingsEnum postings(PostingsEnum reuse, int flags) {
      int postingsPointer = indetwitter.getPostingListPointer(termID);
      return indetwitter.getPostingList().postings(postingsPointer, docFreq(), matwitterPublishedPointer);
    }

    @Override
    public ImpactsEnum impacts(int flags) {
      return new SlowImpactsEnum(postings(null, flags));
    }

    @Override
    public SeekStatus seekCeil(BytesRef tetwittert) {
      // Nullify termIter.
      termIter = null;

      termID = indetwitter.lookupTerm(tetwittert);

      if (termID == -1) {
        return SeekStatus.END;
      } else {
        indetwitter.getTerm(termID, bytesRef);
        return SeekStatus.FOUND;
      }
    }

    @Override
    public BytesRef netwittert() {
      TERMS_ENUM_NEtwitterT_CALLS.increment();
      if (termSet == null) {
        termSet = new TreeSet<>();
        KeysSource keysource = indetwitter.getKeysSource();
        keysource.rewind();
        int numTerms = keysource.getNumberOfKeys();
        for (int i = 0; i < numTerms; ++i) {
          BytesRef ref = keysource.netwittertKey();
          // we need to clone the ref since the keysource is reusing the returned BytesRef
          // instance and we are storing it
          termSet.add(ref.clone());
        }
        TERMS_ENUM_CREATE_TERM_SET.increment();
        TERMS_ENUM_CREATE_TERM_SET_SIZE.add(numTerms);
      }

      // Construct termIter from the subset.
      if (termIter == null) {
        termIter = termSet.tailSet(bytesRef, true).iterator();
      }

      if (termIter.hasNetwittert()) {
        bytesRef = termIter.netwittert();
        termID = indetwitter.lookupTerm(bytesRef);
      } else {
        termID = -1;
        bytesRef = null;
      }
      return bytesRef;
    }

    @Override
    public long ord() {
      return termID;
    }

    @Override
    public void seekEtwitteract(long ord) {
      // Nullify termIter.
      termIter = null;

      if (ord < indetwitter.getNumTerms()) {
        termID = (int) ord;
        indetwitter.getTerm(termID, bytesRef);
      }
    }

    @Override
    public BytesRef term() {
      return bytesRef;
    }

    @Override
    public long totalTermFreq() {
      return docFreq();
    }
  }

  /**
   * This TermsEnum use a {@link SkipListContainer} backed termsSkipList provided by
   * {@link InvertedRealtimeIndetwitter} to supported ordered terms operations like
   * {@link TermsEnum#netwittert()} and {@link TermsEnum#seekCeil}.
   */
  public static class SkipListInMemoryTermsEnum etwittertends BaseTermsEnum {
    private final InvertedRealtimeIndetwitter indetwitter;

    private int termID = -1;
    private BytesRef bytesRef = new BytesRef();
    private int netwittertTermIDPointer;

    /**
     * {@link #netwittertTermIDPointer} is used to record pointer to netwittert termsID to accelerate
     * {@link #netwittert}. However, {@link #seekCeil} and {@link #seekEtwitteract} may jump to an arbitrary
     * term so the {@link #netwittertTermIDPointer} may not be correct, and this flag is used to check if
     * this happens. If this flag is false, {@link #correctNetwittertTermIDPointer} should be called to
     * correct the value.
     */
    private boolean isNetwittertTermIDPointerCorrect;

    private final SkipListContainer<BytesRef> termsSkipList;
    private final InvertedRealtimeIndetwitter.TermsSkipListComparator termsSkipListComparator;
    private final int matwitterPublishedPointer;

    /**
     * Creates a new {@link TermsEnum} for a skip list-based sorted real-time term dictionary.
     */
    public SkipListInMemoryTermsEnum(InvertedRealtimeIndetwitter indetwitter, int matwitterPublishedPointer) {
      Preconditions.checkNotNull(indetwitter.getTermsSkipList());

      this.indetwitter = indetwitter;
      this.termsSkipList = indetwitter.getTermsSkipList();

      // Each Terms Enum shall have their own comparators to be thread safe.
      this.termsSkipListComparator =
          new InvertedRealtimeIndetwitter.TermsSkipListComparator(indetwitter);
      this.netwittertTermIDPointer =
          termsSkipList.getNetwittertPointer(SkipListContainer.FIRST_LIST_HEAD);
      this.isNetwittertTermIDPointerCorrect = true;
      this.matwitterPublishedPointer = matwitterPublishedPointer;
    }

    @Override
    public int docFreq() {
      return indetwitter.getDF(termID);
    }

    @Override
    public PostingsEnum postings(PostingsEnum reuse, int flags) {
      int postingsPointer = indetwitter.getPostingListPointer(termID);
      return indetwitter.getPostingList().postings(postingsPointer, docFreq(), matwitterPublishedPointer);
    }

    @Override
    public ImpactsEnum impacts(int flags) {
      return new SlowImpactsEnum(postings(null, flags));
    }

    @Override
    public SeekStatus seekCeil(BytesRef tetwittert) {
      // Netwittert term pointer is not correct anymore since seek ceil
      //   will jump to an arbitrary term.
      isNetwittertTermIDPointerCorrect = false;

      // Doing precise lookup first.
      termID = indetwitter.lookupTerm(tetwittert);

      // Doing ceil lookup if not found, otherwise we are good.
      if (termID == -1) {
        return seekCeilWithSkipList(tetwittert);
      } else {
        indetwitter.getTerm(termID, bytesRef);
        return SeekStatus.FOUND;
      }
    }

    /**
     * Doing ceil terms search with terms skip list.
     */
    private SeekStatus seekCeilWithSkipList(BytesRef tetwittert) {
      int termIDPointer = termsSkipList.searchCeil(tetwittert,
          SkipListContainer.FIRST_LIST_HEAD,
          termsSkipListComparator,
          null);

      // End reached but still cannot found a ceil term.
      if (termIDPointer == SkipListContainer.FIRST_LIST_HEAD) {
        termID = HashTable.EMPTY_SLOT;
        return SeekStatus.END;
      }

      termID = termsSkipList.getValue(termIDPointer);

      // Set netwittert termID pointer and is correct flag.
      netwittertTermIDPointer = termsSkipList.getNetwittertPointer(termIDPointer);
      isNetwittertTermIDPointerCorrect = true;

      // Found a ceil term but not the precise match.
      indetwitter.getTerm(termID, bytesRef);
      return SeekStatus.NOT_FOUND;
    }

    /**
     * {@link #netwittertTermIDPointer} is used to record the pointer to netwittert termID. This method is used
     * to correct {@link #netwittertTermIDPointer} to correct value after {@link #seekCeil} or
     * {@link #seekEtwitteract} dropped current term to arbitrary point.
     */
    private void correctNetwittertTermIDPointer() {
      final int curTermIDPointer = termsSkipList.search(
          bytesRef,
          SkipListContainer.FIRST_LIST_HEAD,
          termsSkipListComparator,
          null);
      // Must be able to find the etwitteract term.
      assert termID == HashTable.EMPTY_SLOT
          || termID == termsSkipList.getValue(curTermIDPointer);

      netwittertTermIDPointer = termsSkipList.getNetwittertPointer(curTermIDPointer);
      isNetwittertTermIDPointerCorrect = true;
    }

    @Override
    public BytesRef netwittert() {
      // Correct netwittertTermIDPointer first if not correct due to seekEtwitteract or seekCeil.
      if (!isNetwittertTermIDPointerCorrect) {
        correctNetwittertTermIDPointer();
      }

      // Skip list is etwitterhausted.
      if (netwittertTermIDPointer == SkipListContainer.FIRST_LIST_HEAD) {
        termID = HashTable.EMPTY_SLOT;
        return null;
      }

      termID = termsSkipList.getValue(netwittertTermIDPointer);

      indetwitter.getTerm(termID, bytesRef);

      // Set netwittert termID Pointer.
      netwittertTermIDPointer = termsSkipList.getNetwittertPointer(netwittertTermIDPointer);
      return bytesRef;
    }

    @Override
    public long ord() {
      return termID;
    }

    @Override
    public void seekEtwitteract(long ord) {
      if (ord < indetwitter.getNumTerms()) {
        termID = (int) ord;
        indetwitter.getTerm(termID, bytesRef);

        // Netwittert term pointer is not correct anymore since seek etwitteract
        //   just jump to an arbitrary term.
        isNetwittertTermIDPointerCorrect = false;
      }
    }

    @Override
    public BytesRef term() {
      return bytesRef;
    }

    @Override
    public long totalTermFreq() {
      return docFreq();
    }
  }

  @Override
  public long getSumTotalTermFreq() {
    return indetwitter.getSumTotalTermFreq();
  }

  @Override
  public long getSumDocFreq() {
    return indetwitter.getSumTermDocFreq();
  }

  @Override
  public int getDocCount() {
    return indetwitter.getNumDocs();
  }

  @Override
  public boolean hasFreqs() {
    return true;
  }

  @Override
  public boolean hasOffsets() {
    return false;
  }

  @Override
  public boolean hasPositions() {
    return true;
  }

  @Override
  public boolean hasPayloads() {
    return true;
  }
}
