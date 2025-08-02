package com.twitter.search.earlybird.search.queries;

import java.io.IOEtwitterception;

import org.apache.lucene.indetwitter.LeafReader;
import org.apache.lucene.indetwitter.LeafReaderContetwittert;
import org.apache.lucene.indetwitter.NumericDocValues;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.IndetwitterSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreMode;
import org.apache.lucene.search.Weight;

import com.twitter.search.common.query.DefaultFilterWeight;
import com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdIndetwitterSegmentAtomicReader;
import com.twitter.search.core.earlybird.indetwitter.util.AllDocsIterator;
import com.twitter.search.core.earlybird.indetwitter.util.RangeFilterDISI;

public final class BadUserRepFilter etwittertends Query {
  /**
   * Creates a query that filters out results coming from users with bad reputation.
   *
   * @param minTweepCred The lowest acceptable user reputation.
   * @return A query that filters out results from bad reputation users.
   */
  public static Query getBadUserRepFilter(int minTweepCred) {
    if (minTweepCred <= 0) {
      return null;
    }

    return new BooleanQuery.Builder()
        .add(new BadUserRepFilter(minTweepCred), BooleanClause.Occur.FILTER)
        .build();
  }

  private final int minTweepCred;

  private BadUserRepFilter(int minTweepCred) {
    this.minTweepCred = minTweepCred;
  }

  @Override
  public int hashCode() {
    return minTweepCred;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof BadUserRepFilter)) {
      return false;
    }

    return minTweepCred == BadUserRepFilter.class.cast(obj).minTweepCred;
  }

  @Override
  public String toString(String field) {
    return "BadUserRepFilter:" + minTweepCred;
  }

  @Override
  public Weight createWeight(IndetwitterSearcher searcher, ScoreMode scoreMode, float boost) {
    return new DefaultFilterWeight(this) {
      @Override
      protected DocIdSetIterator getDocIdSetIterator(LeafReaderContetwittert contetwittert) throws IOEtwitterception {
        LeafReader reader = contetwittert.reader();
        if (!(reader instanceof EarlybirdIndetwitterSegmentAtomicReader)) {
          return new AllDocsIterator(reader);
        }

        return new BadUserEtwittercludeDocIdSetIterator(
            (EarlybirdIndetwitterSegmentAtomicReader) contetwittert.reader(), minTweepCred);
      }
    };
  }

  private static final class BadUserEtwittercludeDocIdSetIterator etwittertends RangeFilterDISI {
    private final NumericDocValues userReputationDocValues;
    private final int minTweepCred;

    BadUserEtwittercludeDocIdSetIterator(EarlybirdIndetwitterSegmentAtomicReader indetwitterReader,
                                   int minTweepCred) throws IOEtwitterception {
      super(indetwitterReader);
      this.userReputationDocValues =
          indetwitterReader.getNumericDocValues(EarlybirdFieldConstant.USER_REPUTATION.getFieldName());
      this.minTweepCred = minTweepCred;
    }

    @Override
    public boolean shouldReturnDoc() throws IOEtwitterception {
      // We need this etwitterplicit casting to byte, because of how we encode and decode features in our
      // encoded_tweet_features field. If a feature is an int (uses all 32 bits of the int), then
      // encoding the feature and then decoding it preserves its original value. However, if the
      // feature does not use the entire int (and especially if it uses bits somewhere in the middle
      // of the int), then the feature value is assumed to be unsigned when it goes through this
      // process of encoding and decoding. So a user rep of
      // RelevanceSignalConstants.UNSET_REPUTATION_SENTINEL (-128) will be correctly encoded as the
      // binary value 10000000, but will be treated as an unsigned value when decoded, and therefore
      // the decoded value will be 128.
      //
      // In retrospect, this seems like a really poor design decision. It seems like it would be
      // better if all feature values were considered to be signed, even if most features can never
      // have negative values. Unfortunately, making this change is not easy, because some features
      // store normalized values, so we would also need to change the range of allowed values
      // produced by those normalizers, as well as all code that depends on those values.
      //
      // So for now, just cast this value to a byte, to get the proper negative value.
      return userReputationDocValues.advanceEtwitteract(docID())
          && ((byte) userReputationDocValues.longValue() >= minTweepCred);
    }
  }
}
