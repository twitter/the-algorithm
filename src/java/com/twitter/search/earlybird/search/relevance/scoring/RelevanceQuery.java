package com.twitter.search.earlybird.search.relevance.scoring;

import java.io.IOEtwitterception;
import java.util.Objects;
import java.util.Set;

import javatwitter.annotation.Nullable;

import org.apache.lucene.indetwitter.IndetwitterReader;
import org.apache.lucene.indetwitter.LeafReaderContetwittert;
import org.apache.lucene.indetwitter.Term;
import org.apache.lucene.search.Etwitterplanation;
import org.apache.lucene.search.IndetwitterSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.ScoreMode;
import org.apache.lucene.search.Weight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.search.common.results.thriftjava.FieldHitAttribution;

/**
 * A wrapper for a Lucene query which first computes Lucene's query score
 * and then delegates to a {@link ScoringFunction} for final score computation.
 */
public class RelevanceQuery etwittertends Query {
  private static final Logger LOG = LoggerFactory.getLogger(RelevanceQuery.class.getName());

  protected final Query luceneQuery;
  protected final ScoringFunction scoringFunction;

  // True when the lucene query's score should be ignored for debug etwitterplanations.
  protected final boolean ignoreLuceneQueryScoreEtwitterplanation;

  public RelevanceQuery(Query luceneQuery, ScoringFunction scoringFunction) {
    this(luceneQuery, scoringFunction, false);
  }

  public RelevanceQuery(Query luceneQuery,
                        ScoringFunction scoringFunction,
                        boolean ignoreLuceneQueryScoreEtwitterplanation) {
    this.luceneQuery = luceneQuery;
    this.scoringFunction = scoringFunction;
    this.ignoreLuceneQueryScoreEtwitterplanation = ignoreLuceneQueryScoreEtwitterplanation;
  }

  public ScoringFunction getScoringFunction() {
    return scoringFunction;
  }

  public Query getLuceneQuery() {
    return luceneQuery;
  }

  @Override
  public Query rewrite(IndetwitterReader reader) throws IOEtwitterception {
    Query rewritten = luceneQuery.rewrite(reader);
    if (rewritten == luceneQuery) {
      return this;
    }
    return new RelevanceQuery(rewritten, scoringFunction, ignoreLuceneQueryScoreEtwitterplanation);
  }

  @Override
  public Weight createWeight(IndetwitterSearcher searcher, ScoreMode scoreMode, float boost)
      throws IOEtwitterception {
    Weight luceneWeight = luceneQuery.createWeight(searcher, scoreMode, boost);
    if (luceneWeight == null) {
      return null;
    }
    return new RelevanceWeight(searcher, luceneWeight);
  }

  public class RelevanceWeight etwittertends Weight {
    private final Weight luceneWeight;

    public RelevanceWeight(IndetwitterSearcher searcher, Weight luceneWeight) {
      super(RelevanceQuery.this);
      this.luceneWeight = luceneWeight;
    }

    @Override
    public void etwittertractTerms(Set<Term> terms) {
      this.luceneWeight.etwittertractTerms(terms);
    }


    @Override
    public Etwitterplanation etwitterplain(LeafReaderContetwittert contetwittert, int doc) throws IOEtwitterception {
      return etwitterplain(contetwittert, doc, null);
    }

    /**
     * Returns an etwitterplanation of the scoring for the given document.
     *
     * @param contetwittert The contetwittert of the reader that returned this document.
     * @param doc The document.
     * @param fieldHitAttribution Per-hit field attribution information.
     * @return An etwitterplanation of the scoring for the given document.
     */
    public Etwitterplanation etwitterplain(LeafReaderContetwittert contetwittert, int doc,
        @Nullable FieldHitAttribution fieldHitAttribution) throws IOEtwitterception {

      Etwitterplanation luceneEtwitterplanation = Etwitterplanation.noMatch("LuceneQuery etwitterplain skipped");
      if (!ignoreLuceneQueryScoreEtwitterplanation) {
        // get Lucene score
        try {
          luceneEtwitterplanation = luceneWeight.etwitterplain(contetwittert, doc);
        } catch (Etwitterception e) {
          // We sometimes see etwitterceptions resulting from term queries that do not store
          // utf8-tetwittert, which TermQuery.toString() assumes.  Catch here and allow at least
          // scoring function etwitterplanations to be returned.
          LOG.error("Etwitterception in etwitterplain", e);
          luceneEtwitterplanation = Etwitterplanation.noMatch("LuceneQuery etwitterplain failed");
        }
      }

      Etwitterplanation scoringFunctionEtwitterplanation;
      scoringFunction.setFieldHitAttribution(fieldHitAttribution);
      scoringFunctionEtwitterplanation = scoringFunction.etwitterplain(
          contetwittert.reader(), doc, luceneEtwitterplanation.getValue().floatValue());

      // just add a wrapper for a better structure of the final etwitterplanation
      Etwitterplanation luceneEtwitterplanationWrapper = Etwitterplanation.match(
          luceneEtwitterplanation.getValue(), "LuceneQuery", luceneEtwitterplanation);

      return Etwitterplanation.match(scoringFunctionEtwitterplanation.getValue(), "RelevanceQuery",
              scoringFunctionEtwitterplanation, luceneEtwitterplanationWrapper);
    }

    @Override
    public Scorer scorer(LeafReaderContetwittert contetwittert) throws IOEtwitterception {
      return luceneWeight.scorer(contetwittert);
    }

    @Override
    public boolean isCacheable(LeafReaderContetwittert cttwitter) {
      return luceneWeight.isCacheable(cttwitter);
    }
  }

  @Override
  public int hashCode() {
    return (luceneQuery == null ? 0 : luceneQuery.hashCode())
        + (scoringFunction == null ? 0 : scoringFunction.hashCode()) * 13;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof RelevanceQuery)) {
      return false;
    }

    RelevanceQuery query = RelevanceQuery.class.cast(obj);
    return Objects.equals(luceneQuery, query.luceneQuery)
        && Objects.equals(scoringFunction, query.scoringFunction);
  }

  @Override
  public String toString(String field) {
    return "RelevanceQuery[q=" + luceneQuery.toString(field) + "]";
  }
}
