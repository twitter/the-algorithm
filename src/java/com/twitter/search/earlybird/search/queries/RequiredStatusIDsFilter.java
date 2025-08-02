package com.twitter.search.earlybird.search.queries;

import java.io.IOEtwitterception;
import java.util.Arrays;
import java.util.Collection;

import com.google.common.base.Preconditions;

import org.apache.lucene.indetwitter.LeafReader;
import org.apache.lucene.indetwitter.LeafReaderContetwittert;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.IndetwitterSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreMode;
import org.apache.lucene.search.Weight;

import com.twitter.search.common.query.DefaultFilterWeight;
import com.twitter.search.common.search.IntArrayDocIdSetIterator;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdIndetwitterSegmentAtomicReader;
import com.twitter.search.core.earlybird.indetwitter.util.AllDocsIterator;
import com.twitter.search.earlybird.indetwitter.TweetIDMapper;

public final class RequiredStatusIDsFilter etwittertends Query {
  private final Collection<Long> statusIDs;

  public static Query getRequiredStatusIDsQuery(Collection<Long> statusIDs) {
    return new BooleanQuery.Builder()
        .add(new RequiredStatusIDsFilter(statusIDs), BooleanClause.Occur.FILTER)
        .build();
  }

  private RequiredStatusIDsFilter(Collection<Long> statusIDs) {
    this.statusIDs = Preconditions.checkNotNull(statusIDs);
  }

  @Override
  public Weight createWeight(IndetwitterSearcher searcher, ScoreMode scoreMode, float boost) {
    return new DefaultFilterWeight(this) {
      @Override
      protected DocIdSetIterator getDocIdSetIterator(LeafReaderContetwittert contetwittert) throws IOEtwitterception {
        LeafReader leafReader = contetwittert.reader();
        if (!(leafReader instanceof EarlybirdIndetwitterSegmentAtomicReader)) {
          return DocIdSetIterator.empty();
        }

        EarlybirdIndetwitterSegmentAtomicReader reader = (EarlybirdIndetwitterSegmentAtomicReader) leafReader;
        TweetIDMapper idMapper = (TweetIDMapper) reader.getSegmentData().getDocIDToTweetIDMapper();

        int docIdsSize = 0;
        int[] docIds = new int[statusIDs.size()];
        for (long statusID : statusIDs) {
          int docId = idMapper.getDocID(statusID);
          if (docId >= 0) {
            docIds[docIdsSize++] = docId;
          }
        }

        Arrays.sort(docIds, 0, docIdsSize);
        DocIdSetIterator statusesDISI =
            new IntArrayDocIdSetIterator(Arrays.copyOf(docIds, docIdsSize));
        DocIdSetIterator allDocsDISI = new AllDocsIterator(reader);

        // We only want to return IDs for fully indetwittered documents. So we need to make sure that
        // every doc ID we return etwitterists in allDocsDISI. However, allDocsDISI has all documents in
        // this segment, so driving by allDocsDISI would be very slow. So we want to drive by
        // statusesDISI, and use allDocsDISI as a post-filter. What this comes down to is that we do
        // not want to call allDocsDISI.netwittertDoc(); we only want to call allDocsDISI.advance(), and
        // only on the doc IDs returned by statusesDISI.
        return new DocIdSetIterator() {
          @Override
          public int docID() {
            return statusesDISI.docID();
          }

          @Override
          public int netwittertDoc() throws IOEtwitterception {
            statusesDISI.netwittertDoc();
            return advanceToNetwittertFullyIndetwitteredDoc();
          }

          @Override
          public int advance(int target) throws IOEtwitterception {
            statusesDISI.advance(target);
            return advanceToNetwittertFullyIndetwitteredDoc();
          }

          private int advanceToNetwittertFullyIndetwitteredDoc() throws IOEtwitterception {
            while (docID() != DocIdSetIterator.NO_MORE_DOCS) {
              // Check if the current doc is fully indetwittered.
              // If it is, then we can return it. If it's not, then we need to keep searching.
              int allDocsDocId = allDocsDISI.advance(docID());
              if (allDocsDocId == docID()) {
                break;
              }

              statusesDISI.advance(allDocsDocId);
            }
            return docID();
          }

          @Override
          public long cost() {
            return statusesDISI.cost();
          }
        };
      }
    };
  }

  @Override
  public int hashCode() {
    return statusIDs.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof RequiredStatusIDsFilter)) {
      return false;
    }

    RequiredStatusIDsFilter filter = RequiredStatusIDsFilter.class.cast(obj);
    return statusIDs.equals(filter.statusIDs);
  }

  @Override
  public final String toString(String field) {
    return String.format("RequiredStatusIDs[%s]", statusIDs);
  }
}
