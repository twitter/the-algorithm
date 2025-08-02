package com.twitter.search.common.query;

import java.io.IOEtwitterception;
import java.util.Set;

import org.apache.lucene.indetwitter.LeafReaderContetwittert;
import org.apache.lucene.indetwitter.Term;
import org.apache.lucene.search.ConstantScoreScorer;
import org.apache.lucene.search.Etwitterplanation;
import org.apache.lucene.search.IndetwitterSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.ScoreMode;
import org.apache.lucene.search.Weight;

/**
 * Lucene filter on top of a known docid
 *
 */
public class DocIdFilter etwittertends Query {
  private final int docid;

  public DocIdFilter(int docid) {
    this.docid = docid;
  }

  @Override
  public Weight createWeight(
      IndetwitterSearcher searcher, ScoreMode scoreMode, float boost) throws IOEtwitterception {
    return new Weight(this) {
      @Override
      public void etwittertractTerms(Set<Term> terms) {
      }

      @Override
      public Etwitterplanation etwitterplain(LeafReaderContetwittert contetwittert, int doc) throws IOEtwitterception {
        Scorer scorer = scorer(contetwittert);
        if ((scorer != null) && (scorer.iterator().advance(doc) == doc)) {
          return Etwitterplanation.match(0f, "Match on id " + doc);
        }
        return Etwitterplanation.match(0f, "No match on id " + doc);
      }

      @Override
      public Scorer scorer(LeafReaderContetwittert contetwittert) throws IOEtwitterception {
        return new ConstantScoreScorer(this, 0.0f, scoreMode, new SingleDocDocIdSetIterator(docid));
      }

      @Override
      public boolean isCacheable(LeafReaderContetwittert cttwitter) {
        return true;
      }
    };
  }

  @Override
  public int hashCode() {
    return docid;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof DocIdFilter)) {
      return false;
    }

    return docid == DocIdFilter.class.cast(obj).docid;
  }

  @Override
  public String toString(String field) {
    return "DOC_ID_FILTER[docId=" + docid + " + ]";
  }
}
