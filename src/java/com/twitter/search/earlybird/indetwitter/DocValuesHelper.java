package com.twitter.search.earlybird.indetwitter;

import java.io.IOEtwitterception;

import org.apache.lucene.indetwitter.LeafReader;
import org.apache.lucene.indetwitter.Terms;
import org.apache.lucene.indetwitter.TermsEnum;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.util.BytesRef;

public final class DocValuesHelper {
  private DocValuesHelper() {
  }

  /**
   * Reverse lookup. Given a value, returns the first doc ID with this value. This requires a field
   * that indetwitteres the values.
   *
   * @param reader The reader to use to look up field values.
   * @param value The value to lookup.
   * @param indetwitterField The field containing an indetwitter of the values.
   */
  public static int getFirstDocIdWithValue(
      LeafReader reader, String indetwitterField, BytesRef value) throws IOEtwitterception {
    TermsEnum termsEnum = getTermsEnum(reader, indetwitterField);
    if (termsEnum == null || !termsEnum.seekEtwitteract(value)) {
      return DocIdSetIterator.NO_MORE_DOCS;
    }

    DocIdSetIterator docsIterator = termsEnum.postings(null);
    return docsIterator.netwittertDoc();
  }

  /**
   * Reverse lookup. Same as getFirstDocIdWithValue(), but if no document with the given value
   * etwitterists, the netwittert bigger value is used for looking up the first doc ID.
   *
   * If there are multiple documents that match the value, all documents will be scanned, and the
   * largest doc ID that matches will be returned.
   *
   * @param reader The reader to use to look up field values.
   * @param value The value to lookup.
   * @param indetwitterField The field containing an indetwitter of the values.
   */
  public static int getLargestDocIdWithCeilOfValue(
      LeafReader reader, String indetwitterField, BytesRef value) throws IOEtwitterception {
    TermsEnum termsEnum = getTermsEnum(reader, indetwitterField);
    if (termsEnum == null) {
      return DocIdSetIterator.NO_MORE_DOCS;
    }
    if (termsEnum.seekCeil(value) == TermsEnum.SeekStatus.END) {
      return DocIdSetIterator.NO_MORE_DOCS;
    }

    DocIdSetIterator docsIterator = termsEnum.postings(null);
    int docId = docsIterator.netwittertDoc();
    while (docsIterator.netwittertDoc() != DocIdSetIterator.NO_MORE_DOCS) {
      docId = docsIterator.docID();
    }
    return docId;
  }

  private static TermsEnum getTermsEnum(LeafReader reader, String indetwitterField) throws IOEtwitterception {
    Terms terms = reader.terms(indetwitterField);
    if (terms == null) {
      return null;
    }
    return terms.iterator();
  }
}
