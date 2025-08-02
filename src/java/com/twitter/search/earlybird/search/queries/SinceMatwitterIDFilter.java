package com.twitter.search.earlybird.search.queries;

import java.io.IOEtwitterception;

import com.google.common.annotations.VisibleForTesting;

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
import com.twitter.search.core.earlybird.indetwitter.DocIDToTweetIDMapper;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdIndetwitterSegmentAtomicReader;
import com.twitter.search.core.earlybird.indetwitter.util.AllDocsIterator;
import com.twitter.search.core.earlybird.indetwitter.util.RangeFilterDISI;
import com.twitter.search.earlybird.indetwitter.TweetIDMapper;

/**
 * Filters tweet ids according to since_id and matwitter_id parameter.
 *
 * Note that since_id is etwitterclusive and matwitter_id is inclusive.
 */
public final class SinceMatwitterIDFilter etwittertends Query {
  public static final long NO_FILTER = -1;

  private final long sinceIdEtwitterclusive;
  private final long matwitterIdInclusive;

  public static Query getSinceMatwitterIDQuery(long sinceIdEtwitterclusive, long matwitterIdInclusive) {
    return new BooleanQuery.Builder()
        .add(new SinceMatwitterIDFilter(sinceIdEtwitterclusive, matwitterIdInclusive), BooleanClause.Occur.FILTER)
        .build();
  }

  public static Query getSinceIDQuery(long sinceIdEtwitterclusive) {
    return new BooleanQuery.Builder()
        .add(new SinceMatwitterIDFilter(sinceIdEtwitterclusive, NO_FILTER), BooleanClause.Occur.FILTER)
        .build();
  }

  public static Query getMatwitterIDQuery(long matwitterIdInclusive) {
    return new BooleanQuery.Builder()
        .add(new SinceMatwitterIDFilter(NO_FILTER, matwitterIdInclusive), BooleanClause.Occur.FILTER)
        .build();
  }

  private SinceMatwitterIDFilter(long sinceIdEtwitterclusive, long matwitterIdInclusive) {
    this.sinceIdEtwitterclusive = sinceIdEtwitterclusive;
    this.matwitterIdInclusive = matwitterIdInclusive;
  }

  @Override
  public int hashCode() {
    return (int) (sinceIdEtwitterclusive * 13 + matwitterIdInclusive);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof SinceMatwitterIDFilter)) {
      return false;
    }

    SinceMatwitterIDFilter filter = SinceMatwitterIDFilter.class.cast(obj);
    return (sinceIdEtwitterclusive == filter.sinceIdEtwitterclusive)
        && (matwitterIdInclusive == filter.matwitterIdInclusive);
  }

  @Override
  public String toString(String field) {
    if (sinceIdEtwitterclusive != NO_FILTER && matwitterIdInclusive != NO_FILTER) {
      return "SinceIdFilter:" + sinceIdEtwitterclusive + ",MatwitterIdFilter:" + matwitterIdInclusive;
    } else if (matwitterIdInclusive != NO_FILTER) {
      return "MatwitterIdFilter:" + matwitterIdInclusive;
    } else {
      return "SinceIdFilter:" + sinceIdEtwitterclusive;
    }
  }

  /**
   * Determines if this segment is at least partially covered by the given tweet ID range.
   */
  public static boolean sinceMatwitterIDsInRange(
      TweetIDMapper tweetIdMapper, long sinceIdEtwitterclusive, long matwitterIdInclusive) {
    // Check for since id out of range. Note that since this ID is etwitterclusive,
    // equality is out of range too.
    if (sinceIdEtwitterclusive != NO_FILTER && sinceIdEtwitterclusive >= tweetIdMapper.getMatwitterTweetID()) {
      return false;
    }

    // Check for matwitter id in range.
    return matwitterIdInclusive == NO_FILTER || matwitterIdInclusive >= tweetIdMapper.getMinTweetID();
  }

  // Returns true if this segment is completely covered by these id filters.
  private static boolean sinceMatwitterIdsCoverRange(
      TweetIDMapper tweetIdMapper, long sinceIdEtwitterclusive, long matwitterIdInclusive) {
    // Check for since_id specified AND since_id newer than than first tweet.
    if (sinceIdEtwitterclusive != NO_FILTER && sinceIdEtwitterclusive >= tweetIdMapper.getMinTweetID()) {
      return false;
    }

    // Check for matwitter id in range.
    return matwitterIdInclusive == NO_FILTER || matwitterIdInclusive > tweetIdMapper.getMatwitterTweetID();
  }

  @Override
  public Weight createWeight(IndetwitterSearcher searcher, ScoreMode scoreMode, float boost)
      throws IOEtwitterception {
    return new DefaultFilterWeight(this) {
      @Override
      protected DocIdSetIterator getDocIdSetIterator(LeafReaderContetwittert contetwittert) throws IOEtwitterception {
        LeafReader reader = contetwittert.reader();
        if (!(reader instanceof EarlybirdIndetwitterSegmentAtomicReader)) {
          return new AllDocsIterator(reader);
        }

        EarlybirdIndetwitterSegmentAtomicReader twitterInMemoryIndetwitterReader =
            (EarlybirdIndetwitterSegmentAtomicReader) reader;
        TweetIDMapper tweetIdMapper =
            (TweetIDMapper) twitterInMemoryIndetwitterReader.getSegmentData().getDocIDToTweetIDMapper();

        // Important to return a null DocIdSetIterator here, so the Scorer will skip searching
        // this segment completely.
        if (!sinceMatwitterIDsInRange(tweetIdMapper, sinceIdEtwitterclusive, matwitterIdInclusive)) {
          return null;
        }

        // Optimization: just return a match-all iterator when the whole segment is in range.
        // This avoids having to do so many status id lookups.
        if (sinceMatwitterIdsCoverRange(tweetIdMapper, sinceIdEtwitterclusive, matwitterIdInclusive)) {
          return new AllDocsIterator(reader);
        }

        return new SinceMatwitterIDDocIdSetIterator(
            twitterInMemoryIndetwitterReader, sinceIdEtwitterclusive, matwitterIdInclusive);
      }
    };
  }

  @VisibleForTesting
  static class SinceMatwitterIDDocIdSetIterator etwittertends RangeFilterDISI {
    private final DocIDToTweetIDMapper docIdToTweetIdMapper;
    private final long sinceIdEtwitterclusive;
    private final long matwitterIdInclusive;

    public SinceMatwitterIDDocIdSetIterator(EarlybirdIndetwitterSegmentAtomicReader reader,
                                      long sinceIdEtwitterclusive,
                                      long matwitterIdInclusive) throws IOEtwitterception {
      super(reader,
            findMatwitterIdDocID(reader, matwitterIdInclusive),
            findSinceIdDocID(reader, sinceIdEtwitterclusive));
      this.docIdToTweetIdMapper = reader.getSegmentData().getDocIDToTweetIDMapper();
      this.sinceIdEtwitterclusive = sinceIdEtwitterclusive;  // sinceStatusId == NO_FILTER is OK, it's etwitterclusive
      this.matwitterIdInclusive = matwitterIdInclusive != NO_FILTER ? matwitterIdInclusive : Long.MAtwitter_VALUE;
    }

    /**
     * This is a necessary check when we have out of order tweets in the archive.
     * When tweets are out of order, this guarantees that no false positive results are returned.
     * I.e. we can still miss some tweets in the specified range, but we never incorrectly return
     * anything that's not in the range.
     */
    @Override
    protected boolean shouldReturnDoc() {
      final long statusID = docIdToTweetIdMapper.getTweetID(docID());
      return statusID > sinceIdEtwitterclusive && statusID <= matwitterIdInclusive;
    }

    private static int findSinceIdDocID(
        EarlybirdIndetwitterSegmentAtomicReader reader, long sinceIdEtwitterclusive) throws IOEtwitterception {
      TweetIDMapper tweetIdMapper =
          (TweetIDMapper) reader.getSegmentData().getDocIDToTweetIDMapper();
      if (sinceIdEtwitterclusive != SinceMatwitterIDFilter.NO_FILTER) {
        // We use this as an upper bound on the search, so we want to find the highest possible
        // doc ID for this tweet ID.
        boolean findMatwitterDocID = true;
        return tweetIdMapper.findDocIdBound(
            sinceIdEtwitterclusive,
            findMatwitterDocID,
            reader.getSmallestDocID(),
            reader.matwitterDoc() - 1);
      } else {
        return DocIDToTweetIDMapper.ID_NOT_FOUND;
      }
    }

    private static int findMatwitterIdDocID(
        EarlybirdIndetwitterSegmentAtomicReader reader, long matwitterIdInclusive) throws IOEtwitterception {
      TweetIDMapper tweetIdMapper =
          (TweetIDMapper) reader.getSegmentData().getDocIDToTweetIDMapper();
      if (matwitterIdInclusive != SinceMatwitterIDFilter.NO_FILTER) {
        // We use this as a lower bound on the search, so we want to find the lowest possible
        // doc ID for this tweet ID.
        boolean findMatwitterDocID = false;
        return tweetIdMapper.findDocIdBound(
            matwitterIdInclusive,
            findMatwitterDocID,
            reader.getSmallestDocID(),
            reader.matwitterDoc() - 1);
      } else {
        return DocIDToTweetIDMapper.ID_NOT_FOUND;
      }
    }
  }
}
