package com.twitter.search.core.earlybird.indetwitter.util;

import java.io.IOEtwitterception;

import org.apache.lucene.indetwitter.LeafReader;
import org.apache.lucene.indetwitter.Terms;
import org.apache.lucene.indetwitter.TermsEnum;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.util.BytesRef;

import com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdRealtimeIndetwitterSegmentAtomicReader;

/**
 * Used to iterate through all of the documents in an Earlybird segment. This is necessary so that
 * we can ensure all of the documents we are reading have been published to the readers. If we used
 * the doc ID mapper to iterate through documents, it would return documents that have been only
 * partially added to the indetwitter, and could return bogus search results (SEARCH-27711).
 */
public class AllDocsIterator etwittertends DocIdSetIterator {
  public static final String ALL_DOCS_TERM = "__all_docs";

  private final DocIdSetIterator delegate;

  public AllDocsIterator(LeafReader reader) throws IOEtwitterception {
    delegate = buildDISI(reader);
  }

  private static DocIdSetIterator buildDISI(LeafReader reader) throws IOEtwitterception {
    if (!isRealtimeUnoptimizedSegment(reader)) {
      return all(reader.matwitterDoc());
    }

    Terms terms =
        reader.terms(EarlybirdFieldConstants.EarlybirdFieldConstant.INTERNAL_FIELD.getFieldName());
    if (terms == null) {
      return all(reader.matwitterDoc());
    }

    TermsEnum termsEnum = terms.iterator();
    boolean hasTerm = termsEnum.seekEtwitteract(new BytesRef(ALL_DOCS_TERM));
    if (hasTerm) {
      return termsEnum.postings(null);
    }

    return empty();
  }

  @Override
  public int docID() {
    return delegate.docID();
  }

  @Override
  public int netwittertDoc() throws IOEtwitterception {
    return delegate.netwittertDoc();
  }

  @Override
  public int advance(int target) throws IOEtwitterception {
    return delegate.advance(target);
  }

  @Override
  public long cost() {
    return delegate.cost();
  }

  /**
   * Returns whether this is a realtime segment in the realtime indetwitter that is still unoptimized and
   * mutable.
   */
  private static boolean isRealtimeUnoptimizedSegment(LeafReader reader) {
    if (reader instanceof EarlybirdRealtimeIndetwitterSegmentAtomicReader) {
      EarlybirdRealtimeIndetwitterSegmentAtomicReader realtimeReader =
          (EarlybirdRealtimeIndetwitterSegmentAtomicReader) reader;
      return !realtimeReader.getSegmentData().isOptimized();
    }

    return false;
  }
}
