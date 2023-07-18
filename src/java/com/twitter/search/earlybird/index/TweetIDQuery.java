package com.twitter.search.earlybird.index;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;

import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreMode;
import org.apache.lucene.search.Weight;

import com.twitter.search.common.query.DefaultFilterWeight;
import com.twitter.search.common.search.IntArrayDocIdSetIterator;
import com.twitter.search.core.earlybird.index.DocIDToTweetIDMapper;
import com.twitter.search.core.earlybird.index.EarlybirdIndexSegmentAtomicReader;
import com.twitter.search.core.earlybird.index.EarlybirdIndexSegmentData;

public class TweetIDQuery extends Query {
  private final Set<Long> distinctTweetIDs = Sets.newHashSet();

  public TweetIDQuery(long... tweetIDs) {
    for (long tweetID : tweetIDs) {
      this.distinctTweetIDs.add(tweetID);
    }
  }

  @Override
  public Weight createWeight(IndexSearcher searcher, ScoreMode scoreMode, float boost) {
    return new DefaultFilterWeight(this) {
      @Override
      protected DocIdSetIterator getDocIdSetIterator(LeafReaderContext context) throws IOException {
        EarlybirdIndexSegmentData segmentData =
            ((EarlybirdIndexSegmentAtomicReader) context.reader()).getSegmentData();
        DocIDToTweetIDMapper docIdToTweetIdMapper = segmentData.getDocIDToTweetIDMapper();

        int[] distinctSortedDocIDs = distinctTweetIDs.stream()
          .map(tweetID -> docIdToTweetIdMapper.getDocID(tweetID))
          .filter(docID -> docID != DocIDToTweetIDMapper.ID_NOT_FOUND)
          .sorted()
          .mapToInt(Integer::intValue)
          .toArray();

        return distinctSortedDocIDs.length == 0 ?
          DocIdSetIterator.empty() : 
          new IntArrayDocIdSetIterator(distinctSortedDocIDs);
      }
    };
  }

  @Override
  public int hashCode() {
    return distinctTweetIDs.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof TweetIDQuery)) {
      return false;
    }

    return distinctTweetIDs.equals(TweetIDQuery.class.cast(obj).distinctTweetIDs);
  }

  @Override
  public String toString(String field) {
    return "TWEET_ID_QUERY: " + distinctTweetIDs;
  }
}
