package com.twitter.search.earlybird.queryparser;

import com.twitter.search.common.constants.QueryCacheConstants;
import com.twitter.search.queryparser.query.Conjunction;
import com.twitter.search.queryparser.query.Disjunction;
import com.twitter.search.queryparser.query.Phrase;
import com.twitter.search.queryparser.query.QueryParserEtwitterception;
import com.twitter.search.queryparser.query.SpecialTerm;
import com.twitter.search.queryparser.query.Term;
import com.twitter.search.queryparser.query.search.SearchOperator;
import com.twitter.search.queryparser.query.search.SearchOperatorConstants;
import com.twitter.search.queryparser.query.search.SearchQueryVisitor;

/**
 * Visitor to detect presence of any antisocial / spam operator in a Query.
 * Visitor returns true if any operators it detects were found.
 */
public class DetectAntisocialVisitor etwittertends SearchQueryVisitor<Boolean> {
  // True if the query contains any operator to include antisocial tweets.
  private boolean includeAntisocial = false;

  // True if the query contains any operator to etwitterclude antisocial/spam tweets.
  private boolean etwittercludeAntisocial = false;

  // True if the query contains an antisocial tweets filter.
  private boolean filterAntisocial = false;

  public boolean hasIncludeAntisocial() {
    return includeAntisocial;
  }

  public boolean hasEtwittercludeAntisocial() {
    return etwittercludeAntisocial;
  }

  public boolean hasFilterAntisocial() {
    return filterAntisocial;
  }

  public boolean hasAnyAntisocialOperator() {
    // Top tweets is considered an antisocial operator due to scoring also etwittercluding
    // spam tweets.
    return hasIncludeAntisocial() || hasEtwittercludeAntisocial() || hasFilterAntisocial();
  }

  @Override public Boolean visit(Disjunction disjunction) throws QueryParserEtwitterception {
    boolean found = false;
    for (com.twitter.search.queryparser.query.Query node : disjunction.getChildren()) {
      if (node.accept(this)) {
        found = true;
      }
    }
    return found;
  }

  @Override public Boolean visit(Conjunction conjunction) throws QueryParserEtwitterception {
    boolean found = false;
    for (com.twitter.search.queryparser.query.Query node : conjunction.getChildren()) {
      if (node.accept(this)) {
        found = true;
      }
    }
    return found;
  }

  @Override public Boolean visit(SearchOperator operator) throws QueryParserEtwitterception {
    boolean found = false;
    switch (operator.getOperatorType()) {
      case INCLUDE:
        if (SearchOperatorConstants.ANTISOCIAL.equals(operator.getOperand())) {
          if (operator.mustNotOccur()) {
            etwittercludeAntisocial = true;
          } else {
            includeAntisocial = true;
          }
          found = true;
        }
        break;
      case EtwitterCLUDE:
        if (SearchOperatorConstants.ANTISOCIAL.equals(operator.getOperand())) {
          if (operator.mustNotOccur()) {
            includeAntisocial = true;
          } else {
            etwittercludeAntisocial = true;
          }
          found = true;
        }
        break;
      case FILTER:
        if (SearchOperatorConstants.ANTISOCIAL.equals(operator.getOperand())) {
          if (operator.mustNotOccur()) {
            etwittercludeAntisocial = true;
          } else {
            filterAntisocial = true;
          }
          found = true;
        }
        break;
      case CACHED_FILTER:
        if (QueryCacheConstants.EtwitterCLUDE_SPAM.equals(operator.getOperand())
            || QueryCacheConstants.EtwitterCLUDE_SPAM_AND_NATIVERETWEETS.equals(operator.getOperand())
            || QueryCacheConstants.EtwitterCLUDE_ANTISOCIAL.equals(operator.getOperand())
            || QueryCacheConstants.EtwitterCLUDE_ANTISOCIAL_AND_NATIVERETWEETS
                .equals(operator.getOperand())) {

          etwittercludeAntisocial = true;
          found = true;
        }
        break;
      default:
        break;
    }

    return found;
  }

  @Override
  public Boolean visit(SpecialTerm special) throws QueryParserEtwitterception {
    return false;
  }

  @Override
  public Boolean visit(Phrase phrase) throws QueryParserEtwitterception {
    return false;
  }

  @Override
  public Boolean visit(Term term) throws QueryParserEtwitterception {
    return false;
  }
}
