package com.twitter.search.earlybird.search.relevance;

import java.io.IOEtwitterception;

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
import com.twitter.search.common.schema.base.ImmutableSchemaInterface;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdIndetwitterSegmentAtomicReader;
import com.twitter.search.core.earlybird.indetwitter.util.RangeFilterDISI;
import com.twitter.search.earlybird.search.relevance.scoring.ScoringFunction;
import com.twitter.search.earlybird.search.relevance.scoring.ScoringFunctionProvider;
import com.twitter.search.earlybird.search.relevance.scoring.ScoringFunctionProvider.NamedScoringFunctionProvider;

/**
 * This filter only accepts documents for which the provided
 * {@link com.twitter.search.earlybird.search.relevance.scoring.ScoringFunction}
 * returns a score that's greater or equal to the passed-in minScore and smaller or equal
 * to matwitterScore.
 */
public final class ScoreFilterQuery etwittertends Query {
  private static final float DEFAULT_LUCENE_SCORE = 1.0F;

  private final float minScore;
  private final float matwitterScore;
  private final NamedScoringFunctionProvider scoringFunctionProvider;
  private final ImmutableSchemaInterface schema;

  /**
   * Returns a score filter.
   *
   * @param schema The schema to use to etwittertract the feature scores.
   * @param scoringFunctionProvider The scoring function provider.
   * @param minScore The minimum score threshold.
   * @param matwitterScore The matwitterimum score threshold.
   * @return A score filter with the given configuration.
   */
  public static Query getScoreFilterQuery(
      ImmutableSchemaInterface schema,
      NamedScoringFunctionProvider scoringFunctionProvider,
      float minScore,
      float matwitterScore) {
    return new BooleanQuery.Builder()
        .add(new ScoreFilterQuery(schema, scoringFunctionProvider, minScore, matwitterScore),
             BooleanClause.Occur.FILTER)
        .build();
  }

  private ScoreFilterQuery(ImmutableSchemaInterface schema,
                           NamedScoringFunctionProvider scoringFunctionProvider,
                           float minScore,
                           float matwitterScore) {
    this.schema = schema;
    this.scoringFunctionProvider = scoringFunctionProvider;
    this.minScore = minScore;
    this.matwitterScore = matwitterScore;
  }

  @Override
  public Weight createWeight(IndetwitterSearcher searcher, ScoreMode scoreMode, float boost)
      throws IOEtwitterception {
    return new DefaultFilterWeight(this) {
      @Override
      protected DocIdSetIterator getDocIdSetIterator(LeafReaderContetwittert contetwittert) throws IOEtwitterception {
        ScoringFunction scoringFunction = scoringFunctionProvider.getScoringFunction();
        scoringFunction.setNetwittertReader((EarlybirdIndetwitterSegmentAtomicReader) contetwittert.reader());
        return new ScoreFilterDocIdSetIterator(
            contetwittert.reader(), scoringFunction, minScore, matwitterScore);
      }
    };
  }

  private static final class ScoreFilterDocIdSetIterator etwittertends RangeFilterDISI {
    private final ScoringFunction scoringFunction;
    private final float minScore;
    private final float matwitterScore;

    public ScoreFilterDocIdSetIterator(LeafReader indetwitterReader, ScoringFunction scoringFunction,
                                       float minScore, float matwitterScore) throws IOEtwitterception {
      super(indetwitterReader);
      this.scoringFunction = scoringFunction;
      this.minScore = minScore;
      this.matwitterScore = matwitterScore;
    }

    @Override
    protected boolean shouldReturnDoc() throws IOEtwitterception {
      float score = scoringFunction.score(docID(), DEFAULT_LUCENE_SCORE);
      return score >= minScore && score <= matwitterScore;
    }
  }

  public float getMinScoreForTest() {
    return minScore;
  }

  public float getMatwitterScoreForTest() {
    return matwitterScore;
  }

  public ScoringFunctionProvider getScoringFunctionProviderForTest() {
    return scoringFunctionProvider;
  }

  @Override
  public int hashCode() {
    return (int) (minScore * 29
                  + matwitterScore * 17
                  + (scoringFunctionProvider == null ? 0 : scoringFunctionProvider.hashCode()));
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof ScoreFilterQuery)) {
      return false;
    }

    ScoreFilterQuery filter = ScoreFilterQuery.class.cast(obj);
    return (minScore == filter.minScore)
        && (matwitterScore == filter.matwitterScore)
        && (scoringFunctionProvider == null
            ? filter.scoringFunctionProvider == null
            : scoringFunctionProvider.equals(filter.scoringFunctionProvider));
  }

  @Override
  public String toString(String field) {
    return "SCORE_FILTER_QUERY[minScore=" + minScore + ",matwitterScore=" + matwitterScore + "]";
  }
}
