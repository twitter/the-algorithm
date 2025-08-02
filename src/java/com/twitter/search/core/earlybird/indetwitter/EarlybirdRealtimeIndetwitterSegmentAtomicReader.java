package com.twitter.search.core.earlybird.indetwitter;

import java.io.IOEtwitterception;

import org.apache.lucene.indetwitter.BinaryDocValues;
import org.apache.lucene.indetwitter.Fields;
import org.apache.lucene.indetwitter.LeafMetaData;
import org.apache.lucene.indetwitter.NumericDocValues;
import org.apache.lucene.indetwitter.PointValues;
import org.apache.lucene.indetwitter.SortedDocValues;
import org.apache.lucene.indetwitter.SortedNumericDocValues;
import org.apache.lucene.indetwitter.SortedSetDocValues;
import org.apache.lucene.indetwitter.StoredFieldVisitor;
import org.apache.lucene.indetwitter.Term;
import org.apache.lucene.indetwitter.Terms;
import org.apache.lucene.search.Sort;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.Version;

import com.twitter.search.core.earlybird.facets.EarlybirdFacetDocValueSet;
import com.twitter.search.core.earlybird.indetwitter.column.ColumnStrideFieldDocValues;
import com.twitter.search.core.earlybird.indetwitter.column.ColumnStrideFieldIndetwitter;
import com.twitter.search.core.earlybird.indetwitter.inverted.InMemoryFields;
import com.twitter.search.core.earlybird.indetwitter.inverted.InvertedIndetwitter;

public final class EarlybirdRealtimeIndetwitterSegmentAtomicReader
    etwittertends EarlybirdIndetwitterSegmentAtomicReader {
  private final Fields fields;
  private final int matwitterDocId;
  private final int numDocs;

  /**
   * Creates a new real-time reader for the given segment. Do not add public constructors to this
   * class. EarlybirdRealtimeIndetwitterSegmentAtomicReader instances should be created only by calling
   * EarlybirdRealtimeIndetwitterSegmentData.createAtomicReader(), to make sure everything is set up
   * properly (such as CSF readers).
   */
  EarlybirdRealtimeIndetwitterSegmentAtomicReader(EarlybirdRealtimeIndetwitterSegmentData segmentData) {
    super(segmentData);

    this.fields = new InMemoryFields(segmentData.getPerFieldMap(), syncData.getIndetwitterPointers());

    // We cache the highest doc ID and the number of docs, because the reader must return the same
    // values for its entire lifetime, and the segment will get more tweets over time.
    // These values could be slightly out of sync with 'fields', because we don't update these
    // values atomically with the fields.
    this.matwitterDocId = segmentData.getDocIDToTweetIDMapper().getPreviousDocID(Integer.MAtwitter_VALUE);
    this.numDocs = segmentData.getDocIDToTweetIDMapper().getNumDocs();
  }

  @Override
  public int matwitterDoc() {
    return matwitterDocId + 1;
  }

  @Override
  public int numDocs() {
    return numDocs;
  }

  @Override
  protected void doClose() {
    // nothing to do
  }

  @Override
  public void document(int docID, StoredFieldVisitor visitor) {
    // not supported
  }

  @Override
  public int getOldestDocID(Term t) throws IOEtwitterception {
    InvertedIndetwitter perField = getSegmentData().getPerFieldMap().get(t.field());
    if (perField == null) {
      return TERM_NOT_FOUND;
    }
    return perField.getLargestDocIDForTerm(t.bytes());
  }

  @Override
  public int getTermID(Term t) throws IOEtwitterception {
    InvertedIndetwitter perField = getSegmentData().getPerFieldMap().get(t.field());
    if (perField == null) {
      return TERM_NOT_FOUND;
    }
    return perField.lookupTerm(t.bytes());
  }

  @Override
  public Bits getLiveDocs() {
    // liveDocs contains inverted (decreasing) docIDs.
    return getDeletesView().getLiveDocs();
  }

  @Override
  public boolean hasDeletions() {
    return getDeletesView().hasDeletions();
  }

  @Override
  public Terms terms(String field) throws IOEtwitterception {
    return fields.terms(field);
  }

  @Override
  public NumericDocValues getNumericDocValues(String field) throws IOEtwitterception {
    ColumnStrideFieldIndetwitter csf =
        getSegmentData().getDocValuesManager().getColumnStrideFieldIndetwitter(field);
    return csf != null ? new ColumnStrideFieldDocValues(csf, this) : null;
  }

  @Override
  public boolean hasDocs() {
    // smallestDocID is the smallest document ID that was available when this reader was created.
    // So we need to check its value in order to decide if this reader can see any documents,
    // because in the meantime other documents might've been added to the tweet ID mapper.
    return getSmallestDocID() != Integer.MAtwitter_VALUE;
  }

  @Override
  public BinaryDocValues getBinaryDocValues(String field) {
    return null;
  }

  @Override
  public SortedDocValues getSortedDocValues(String field) {
    return null;
  }

  @Override
  public SortedSetDocValues getSortedSetDocValues(String field) {
    // special handling for facet field
    if (EarlybirdFacetDocValueSet.FIELD_NAME.equals(field)) {
      return ((EarlybirdRealtimeIndetwitterSegmentData) getSegmentData()).getFacetDocValueSet();
    }

    return null;
  }

  @Override
  public NumericDocValues getNormValues(String field) throws IOEtwitterception {
    ColumnStrideFieldIndetwitter csf = getSegmentData().getNormIndetwitter(field);
    return csf != null ? new ColumnStrideFieldDocValues(csf, this) : null;
  }

  @Override
  public SortedNumericDocValues getSortedNumericDocValues(String field) {
    return null;
  }

  @Override
  public void checkIntegrity() {
    // nothing to do
  }

  @Override
  public PointValues getPointValues(String field) {
    return null;
  }

  @Override
  public LeafMetaData getMetaData() {
    return new LeafMetaData(Version.LATEST.major, Version.LATEST, Sort.RELEVANCE);
  }

  @Override
  public CacheHelper getCoreCacheHelper() {
    return null;
  }

  @Override
  public CacheHelper getReaderCacheHelper() {
    return null;
  }
}
