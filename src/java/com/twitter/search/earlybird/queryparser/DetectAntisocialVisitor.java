package com.tw ter.search.earlyb rd.queryparser;

 mport com.tw ter.search.common.constants.QueryCac Constants;
 mport com.tw ter.search.queryparser.query.Conjunct on;
 mport com.tw ter.search.queryparser.query.D sjunct on;
 mport com.tw ter.search.queryparser.query.Phrase;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;
 mport com.tw ter.search.queryparser.query.Spec alTerm;
 mport com.tw ter.search.queryparser.query.Term;
 mport com.tw ter.search.queryparser.query.search.SearchOperator;
 mport com.tw ter.search.queryparser.query.search.SearchOperatorConstants;
 mport com.tw ter.search.queryparser.query.search.SearchQueryV s or;

/**
 * V s or to detect presence of any ant soc al / spam operator  n a Query.
 * V s or returns true  f any operators   detects  re found.
 */
publ c class DetectAnt soc alV s or extends SearchQueryV s or<Boolean> {
  // True  f t  query conta ns any operator to  nclude ant soc al t ets.
  pr vate boolean  ncludeAnt soc al = false;

  // True  f t  query conta ns any operator to exclude ant soc al/spam t ets.
  pr vate boolean excludeAnt soc al = false;

  // True  f t  query conta ns an ant soc al t ets f lter.
  pr vate boolean f lterAnt soc al = false;

  publ c boolean has ncludeAnt soc al() {
    return  ncludeAnt soc al;
  }

  publ c boolean hasExcludeAnt soc al() {
    return excludeAnt soc al;
  }

  publ c boolean hasF lterAnt soc al() {
    return f lterAnt soc al;
  }

  publ c boolean hasAnyAnt soc alOperator() {
    // Top t ets  s cons dered an ant soc al operator due to scor ng also exclud ng
    // spam t ets.
    return has ncludeAnt soc al() || hasExcludeAnt soc al() || hasF lterAnt soc al();
  }

  @Overr de publ c Boolean v s (D sjunct on d sjunct on) throws QueryParserExcept on {
    boolean found = false;
    for (com.tw ter.search.queryparser.query.Query node : d sjunct on.getCh ldren()) {
       f (node.accept(t )) {
        found = true;
      }
    }
    return found;
  }

  @Overr de publ c Boolean v s (Conjunct on conjunct on) throws QueryParserExcept on {
    boolean found = false;
    for (com.tw ter.search.queryparser.query.Query node : conjunct on.getCh ldren()) {
       f (node.accept(t )) {
        found = true;
      }
    }
    return found;
  }

  @Overr de publ c Boolean v s (SearchOperator operator) throws QueryParserExcept on {
    boolean found = false;
    sw ch (operator.getOperatorType()) {
      case  NCLUDE:
         f (SearchOperatorConstants.ANT SOC AL.equals(operator.getOperand())) {
           f (operator.mustNotOccur()) {
            excludeAnt soc al = true;
          } else {
             ncludeAnt soc al = true;
          }
          found = true;
        }
        break;
      case EXCLUDE:
         f (SearchOperatorConstants.ANT SOC AL.equals(operator.getOperand())) {
           f (operator.mustNotOccur()) {
             ncludeAnt soc al = true;
          } else {
            excludeAnt soc al = true;
          }
          found = true;
        }
        break;
      case F LTER:
         f (SearchOperatorConstants.ANT SOC AL.equals(operator.getOperand())) {
           f (operator.mustNotOccur()) {
            excludeAnt soc al = true;
          } else {
            f lterAnt soc al = true;
          }
          found = true;
        }
        break;
      case CACHED_F LTER:
         f (QueryCac Constants.EXCLUDE_SPAM.equals(operator.getOperand())
            || QueryCac Constants.EXCLUDE_SPAM_AND_NAT VERETWEETS.equals(operator.getOperand())
            || QueryCac Constants.EXCLUDE_ANT SOC AL.equals(operator.getOperand())
            || QueryCac Constants.EXCLUDE_ANT SOC AL_AND_NAT VERETWEETS
                .equals(operator.getOperand())) {

          excludeAnt soc al = true;
          found = true;
        }
        break;
      default:
        break;
    }

    return found;
  }

  @Overr de
  publ c Boolean v s (Spec alTerm spec al) throws QueryParserExcept on {
    return false;
  }

  @Overr de
  publ c Boolean v s (Phrase phrase) throws QueryParserExcept on {
    return false;
  }

  @Overr de
  publ c Boolean v s (Term term) throws QueryParserExcept on {
    return false;
  }
}
