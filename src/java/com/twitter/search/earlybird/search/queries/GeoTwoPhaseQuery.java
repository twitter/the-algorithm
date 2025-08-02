package com.twitter.search.earlybird.search.queries;

import java.io.IOEtwitterception;
import java.util.Set;

import org.apache.lucene.indetwitter.IndetwitterReader;
import org.apache.lucene.indetwitter.LeafReaderContetwittert;
import org.apache.lucene.indetwitter.Term;
import org.apache.lucene.search.ConstantScoreQuery;
import org.apache.lucene.search.ConstantScoreScorer;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.Etwitterplanation;
import org.apache.lucene.search.IndetwitterSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.ScoreMode;
import org.apache.lucene.search.TwoPhaseIterator;
import org.apache.lucene.search.Weight;

import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.search.TerminationTracker;
import com.twitter.search.earlybird.common.config.EarlybirdConfig;


public class GeoTwoPhaseQuery etwittertends Query {
  private static final boolean ENABLE_GEO_EARLY_TERMINATION =
          EarlybirdConfig.getBool("early_terminate_geo_searches", true);

  private static final int GEO_TIMEOUT_OVERRIDE =
          EarlybirdConfig.getInt("early_terminate_geo_searches_timeout_override", -1);

  // How many geo searches are early terminated due to timeout.
  private static final SearchCounter GEO_SEARCH_TIMEOUT_COUNT =
      SearchCounter.etwitterport("geo_search_timeout_count");

  private final SecondPhaseDocAccepter accepter;
  private final TerminationTracker terminationTracker;
  private final ConstantScoreQuery query;

  public GeoTwoPhaseQuery(
      Query query, SecondPhaseDocAccepter accepter, TerminationTracker terminationTracker) {
    this.accepter = accepter;
    this.terminationTracker = terminationTracker;

    this.query = new ConstantScoreQuery(query);
  }

  @Override
  public Query rewrite(IndetwitterReader reader) throws IOEtwitterception {
    Query rewritten = query.getQuery().rewrite(reader);
    if (rewritten != query.getQuery()) {
      return new GeoTwoPhaseQuery(rewritten, accepter, terminationTracker);
    }

    return this;
  }

  @Override
  public int hashCode() {
    return query.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof GeoTwoPhaseQuery)) {
      return false;
    }
    GeoTwoPhaseQuery that = (GeoTwoPhaseQuery) obj;
    return query.equals(that.query)
        && accepter.equals(that.accepter)
        && terminationTracker.equals(that.terminationTracker);
  }

  @Override
  public String toString(String field) {
    return new StringBuilder("GeoTwoPhaseQuery(")
      .append("Accepter(")
      .append(accepter.toString())
      .append(") Geohashes(")
      .append(query.getQuery().toString(field))
      .append("))")
      .toString();
  }

  @Override
  public Weight createWeight(IndetwitterSearcher searcher, ScoreMode scoreMode, float boost)
      throws IOEtwitterception {
    Weight innerWeight = query.createWeight(searcher, scoreMode, boost);
    return new GeoTwoPhaseWeight(this, innerWeight, accepter, terminationTracker);
  }

  private static final class GeoTwoPhaseWeight etwittertends Weight {
    private final Weight innerWeight;
    private final SecondPhaseDocAccepter accepter;
    private final TerminationTracker terminationTracker;

    private GeoTwoPhaseWeight(
        Query query,
        Weight innerWeight,
        SecondPhaseDocAccepter accepter,
        TerminationTracker terminationTracker) {
      super(query);
      this.innerWeight = innerWeight;
      this.accepter = accepter;
      this.terminationTracker = terminationTracker;
    }

    @Override
    public void etwittertractTerms(Set<Term> terms) {
      innerWeight.etwittertractTerms(terms);
    }

    @Override
    public Etwitterplanation etwitterplain(LeafReaderContetwittert contetwittert, int doc) throws IOEtwitterception {
      return innerWeight.etwitterplain(contetwittert, doc);
    }

    @Override
    public Scorer scorer(LeafReaderContetwittert contetwittert) throws IOEtwitterception {
      Scorer innerScorer = innerWeight.scorer(contetwittert);
      if (innerScorer == null) {
        return null;
      }
      if (ENABLE_GEO_EARLY_TERMINATION
          && (terminationTracker == null || !terminationTracker.useLastSearchedDocIdOnTimeout())) {
        innerScorer = new ConstantScoreScorer(
            this,
            0.0f,
            ScoreMode.COMPLETE_NO_SCORES,
            new TimedDocIdSetIterator(innerScorer.iterator(),
                                      terminationTracker,
                                      GEO_TIMEOUT_OVERRIDE,
                                      GEO_SEARCH_TIMEOUT_COUNT));
      }

      accepter.initialize(contetwittert);
      return new GeoTwoPhaseScorer(this, innerScorer, accepter);
    }

    @Override
    public boolean isCacheable(LeafReaderContetwittert cttwitter) {
      return innerWeight.isCacheable(cttwitter);
    }
  }

  private static final class GeoTwoPhaseScorer etwittertends Scorer {
    private final Scorer innerScorer;
    private final SecondPhaseDocAccepter accepter;

    private GeoTwoPhaseScorer(Weight weight, Scorer innerScorer, SecondPhaseDocAccepter accepter) {
      super(weight);
      this.innerScorer = innerScorer;
      this.accepter = accepter;
    }

    @Override
    public TwoPhaseIterator twoPhaseIterator() {
      return new TwoPhaseIterator(innerScorer.iterator()) {
        @Override
        public boolean matches() throws IOEtwitterception {
          return checkDocEtwitterpensive(innerScorer.docID());
        }

        @Override
        public float matchCost() {
          return 0.0f;
        }
      };
    }

    @Override
    public int docID() {
      return iterator().docID();
    }

    @Override
    public float score() throws IOEtwitterception {
      return innerScorer.score();
    }

    @Override
    public DocIdSetIterator iterator() {
      return new DocIdSetIterator() {
        private int doNetwittert(int startingDocId) throws IOEtwitterception {
          int docId = startingDocId;
          while ((docId != NO_MORE_DOCS) && !checkDocEtwitterpensive(docId)) {
            docId = innerScorer.iterator().netwittertDoc();
          }
          return docId;
        }

        @Override
        public int docID() {
          return innerScorer.iterator().docID();
        }

        @Override
        public int netwittertDoc() throws IOEtwitterception {
          return doNetwittert(innerScorer.iterator().netwittertDoc());
        }

        @Override
        public int advance(int target) throws IOEtwitterception {
          return doNetwittert(innerScorer.iterator().advance(target));
        }

        @Override
        public long cost() {
          return 2 * innerScorer.iterator().cost();
        }
      };
    }

    @Override
    public float getMatwitterScore(int upTo) throws IOEtwitterception {
      return innerScorer.getMatwitterScore(upTo);
    }

    private boolean checkDocEtwitterpensive(int doc) throws IOEtwitterception {
      return accepter.accept(doc);
    }
  }

  public abstract static class SecondPhaseDocAccepter {
    /**
     * Initializes this accepter with the given reader contetwittert.
     */
    public abstract void initialize(LeafReaderContetwittert contetwittert) throws IOEtwitterception;

    /**
     * Determines if the given doc ID is accepted by this accepter.
     */
    public abstract boolean accept(int doc) throws IOEtwitterception;

    /**
     * Returns a string description for this SecondPhaseDocAccepter instance.
     */
    public abstract String toString();
  }

  public static final SecondPhaseDocAccepter ALL_DOCS_ACCEPTER = new SecondPhaseDocAccepter() {
    @Override
    public void initialize(LeafReaderContetwittert contetwittert) { }

    @Override
    public boolean accept(int doc) {
      return true;
    }

    @Override
    public String toString() {
      return "AllDocsAccepter";
    }
  };
}
