package com.twitter.search.core.earlybird.indetwitter;

import java.io.Closeable;
import java.io.IOEtwitterception;

import org.apache.lucene.document.Document;
import org.apache.lucene.indetwitter.IndetwitterReader;
import org.apache.lucene.indetwitter.LeafReaderContetwittert;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.IndetwitterSearcher;
import org.apache.lucene.search.LeafCollector;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorable;
import org.apache.lucene.search.ScoreMode;
import org.apache.lucene.store.Directory;

import com.twitter.search.core.earlybird.indetwitter.column.ColumnStrideFieldIndetwitter;
import com.twitter.search.core.earlybird.indetwitter.column.DocValuesUpdate;

/**
 * IndetwitterSegmentWriter combines some common functionality between the Lucene and Realtime indetwitter
 * segment writers.
 */
public abstract class EarlybirdIndetwitterSegmentWriter implements Closeable {

  public EarlybirdIndetwitterSegmentWriter() {
  }

  /**
   * Gets the segment data this segment write is associated with.
   * @return
   */
  public abstract EarlybirdIndetwitterSegmentData getSegmentData();

  /**
   * Appends terms from the document to the document matching the query. Does not replace a field or
   * document, actually adds to the the field in the segment.
   */
  public final void appendOutOfOrder(Query query, Document doc) throws IOEtwitterception {
    runQuery(query, docID -> appendOutOfOrder(doc, docID));
  }

  protected abstract void appendOutOfOrder(Document doc, int docId) throws IOEtwitterception;

  /**
   * Deletes a document in this segment that matches this query.
   */
  public void deleteDocuments(Query query) throws IOEtwitterception {
    runQuery(query, docID -> getSegmentData().getDeletedDocs().deleteDoc(docID));
  }

  /**
   * Updates the docvalues of a document in this segment that matches this query.
   */
  public void updateDocValues(Query query, String field, DocValuesUpdate update)
      throws IOEtwitterception {
    runQuery(query, docID -> {
        ColumnStrideFieldIndetwitter docValues =
            getSegmentData().getDocValuesManager().getColumnStrideFieldIndetwitter(field);
        if (docValues == null) {
          return;
        }

        update.update(docValues, docID);
      });
  }

  private void runQuery(final Query query, final OnHit onHit) throws IOEtwitterception {
    try (IndetwitterReader reader = getSegmentData().createAtomicReader()) {
      new IndetwitterSearcher(reader).search(query, new Collector() {
        @Override
        public LeafCollector getLeafCollector(LeafReaderContetwittert contetwittert) throws IOEtwitterception {
          return new LeafCollector() {
            @Override
            public void setScorer(Scorable scorer) {
            }

            @Override
            public void collect(int docID) throws IOEtwitterception {
              onHit.hit(docID);
            }
          };
        }

        @Override
        public ScoreMode scoreMode() {
          return ScoreMode.COMPLETE_NO_SCORES;
        }
      });
    }
  }

  private interface OnHit {
    void hit(int docID) throws IOEtwitterception;
  }

  /**
   * Adds a new document to this segment. In production, this method should be called only by
   * Etwitterpertsearch.
   */
  public abstract void addDocument(Document doc) throws IOEtwitterception;

  /**
   * Adds a new tweet to this segment. This method should be called only by Earlybird.
   */
  public abstract void addTweet(Document doc, long tweetId, boolean docIsOffensive)
      throws IOEtwitterception;

  /**
   * Returns the total number of documents in the segment.
   */
  public abstract int numDocs() throws IOEtwitterception;

  /**
   * Returns the number of documents in this segment without taking deleted docs into account.
   * E.g. if 10 documents were added to this segments, and 5 were deleted,
   * this method still returns 10.
   */
  public abstract int numDocsNoDelete() throws IOEtwitterception;

  /**
   * Forces the underlying indetwitter to be merged down to a single segment.
   */
  public abstract void forceMerge() throws IOEtwitterception;

  /**
   * Appends the provides Lucene indetwitteres to this segment.
   */
  public abstract void addIndetwitteres(Directory... dirs) throws IOEtwitterception;
}
