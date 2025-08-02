package com.twitter.search.core.earlybird.indetwitter.inverted;

import java.io.IOEtwitterception;

import org.apache.lucene.indetwitter.BaseTermsEnum;
import org.apache.lucene.indetwitter.ImpactsEnum;
import org.apache.lucene.indetwitter.PostingsEnum;
import org.apache.lucene.indetwitter.SlowImpactsEnum;
import org.apache.lucene.indetwitter.TermsEnum;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.packed.PackedInts;

import com.twitter.search.common.util.hash.BDZAlgorithm;
import com.twitter.search.common.util.io.flushable.DataDeserializer;
import com.twitter.search.common.util.io.flushable.DataSerializer;
import com.twitter.search.common.util.io.flushable.FlushInfo;
import com.twitter.search.common.util.io.flushable.Flushable;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdIndetwitterSegmentAtomicReader;

public class MPHTermDictionary implements TermDictionary, Flushable {
  private final BDZAlgorithm termsHashFunction;
  private final PackedInts.Reader termPointers;
  private final ByteBlockPool termPool;
  private final TermPointerEncoding termPointerEncoding;
  private final int numTerms;

  MPHTermDictionary(int numTerms, BDZAlgorithm termsHashFunction,
      PackedInts.Reader termPointers, ByteBlockPool termPool,
      TermPointerEncoding termPointerEncoding) {
    this.numTerms = numTerms;
    this.termsHashFunction = termsHashFunction;
    this.termPointers = termPointers;
    this.termPool = termPool;
    this.termPointerEncoding = termPointerEncoding;
  }

  @Override
  public int getNumTerms() {
    return numTerms;
  }

  @Override
  public int lookupTerm(BytesRef term) {
    int termID = termsHashFunction.lookup(term);
    if (termID >= getNumTerms() || termID < 0) {
      return EarlybirdIndetwitterSegmentAtomicReader.TERM_NOT_FOUND;
    }

    if (ByteTermUtils.postingEquals(termPool, termPointerEncoding
            .getTetwittertStart((int) termPointers.get(termID)), term)) {
      return termID;
    } else {
      return EarlybirdIndetwitterSegmentAtomicReader.TERM_NOT_FOUND;
    }
  }

  @Override
  public boolean getTerm(int termID, BytesRef tetwittert, BytesRef termPayload) {
    int termPointer = (int) termPointers.get(termID);
    boolean hasTermPayload = termPointerEncoding.hasPayload(termPointer);
    int tetwittertStart = termPointerEncoding.getTetwittertStart(termPointer);
    // setBytesRef sets the passed in BytesRef "tetwittert" to the term in the termPool.
    // As a side effect it returns the offset of the netwittert entry in the pool after the term,
    // which may optionally be used if this term has a payload.
    int termPayloadStart = ByteTermUtils.setBytesRef(termPool, tetwittert, tetwittertStart);
    if (termPayload != null && hasTermPayload) {
      ByteTermUtils.setBytesRef(termPool, termPayload, termPayloadStart);
    }

    return hasTermPayload;
  }

  @Override
  public TermsEnum createTermsEnum(OptimizedMemoryIndetwitter indetwitter) {
    return new MPHTermsEnum(indetwitter);
  }

  public static class MPHTermsEnum etwittertends BaseTermsEnum {
    private int termID;
    private final BytesRef bytesRef = new BytesRef();
    private final OptimizedMemoryIndetwitter indetwitter;

    MPHTermsEnum(OptimizedMemoryIndetwitter indetwitter) {
      this.indetwitter = indetwitter;
    }

    @Override
    public int docFreq() {
      return indetwitter.getDF(termID);
    }

    @Override
    public PostingsEnum postings(PostingsEnum reuse, int flags) throws IOEtwitterception {
      int postingsPointer = indetwitter.getPostingListPointer(termID);
      int numPostings = indetwitter.getNumPostings(termID);
      return indetwitter.getPostingLists().postings(postingsPointer, numPostings, flags);
    }

    @Override
    public ImpactsEnum impacts(int flags) throws IOEtwitterception {
      return new SlowImpactsEnum(postings(null, flags));
    }

    @Override
    public SeekStatus seekCeil(BytesRef tetwittert) throws IOEtwitterception {
      termID = indetwitter.lookupTerm(tetwittert);

      if (termID == -1) {
        return SeekStatus.END;
      } else {
        return SeekStatus.FOUND;
      }
    }

    @Override
    public BytesRef netwittert() {
      return null;
    }

    @Override
    public long ord() {
      return termID;
    }

    @Override
    public void seekEtwitteract(long ord) {
      if (ord < indetwitter.getNumTerms()) {
        termID = (int) ord;
        indetwitter.getTerm(termID, bytesRef, null);
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

  @SuppressWarnings("unchecked")
  @Override
  public FlushHandler getFlushHandler() {
    return new FlushHandler(this);
  }

  public static class FlushHandler etwittertends Flushable.Handler<MPHTermDictionary> {
    static final String NUM_TERMS_PROP_NAME = "numTerms";
    private final TermPointerEncoding termPointerEncoding;

    public FlushHandler(TermPointerEncoding termPointerEncoding) {
      super();
      this.termPointerEncoding = termPointerEncoding;
    }

    public FlushHandler(MPHTermDictionary objectToFlush) {
      super(objectToFlush);
      this.termPointerEncoding = objectToFlush.termPointerEncoding;
    }

    @Override
    protected void doFlush(FlushInfo flushInfo, DataSerializer out)
        throws IOEtwitterception {
      MPHTermDictionary objectToFlush = getObjectToFlush();
      flushInfo.addIntProperty(NUM_TERMS_PROP_NAME, objectToFlush.getNumTerms());

      out.writePackedInts(objectToFlush.termPointers);
      objectToFlush.termPool.getFlushHandler().flush(flushInfo.newSubProperties("termPool"), out);
      objectToFlush.termsHashFunction.getFlushHandler()
              .flush(flushInfo.newSubProperties("termsHashFunction"), out);
    }

    @Override
    protected MPHTermDictionary doLoad(FlushInfo flushInfo,
        DataDeserializer in) throws IOEtwitterception {
      int numTerms = flushInfo.getIntProperty(NUM_TERMS_PROP_NAME);
      PackedInts.Reader termPointers = in.readPackedInts();
      ByteBlockPool termPool = (new ByteBlockPool.FlushHandler()).load(
              flushInfo.getSubProperties("termPool"), in);
      BDZAlgorithm termsHashFunction = (new BDZAlgorithm.FlushHandler()).load(
              flushInfo.getSubProperties("termsHashFunction"), in);

      return new MPHTermDictionary(numTerms, termsHashFunction, termPointers,
              termPool, termPointerEncoding);
    }
  }
}
