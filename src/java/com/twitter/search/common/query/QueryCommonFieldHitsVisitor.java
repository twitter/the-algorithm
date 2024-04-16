package com.tw ter.search.common.query;

 mport java.ut l.Collect ons;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.Set;
 mport java.ut l.logg ng.Level;
 mport java.ut l.logg ng.Logger;

 mport com.google.common.collect.Sets;

 mport com.tw ter.search.queryparser.query.Conjunct on;
 mport com.tw ter.search.queryparser.query.D sjunct on;
 mport com.tw ter.search.queryparser.query.Phrase;
 mport com.tw ter.search.queryparser.query.Query;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;
 mport com.tw ter.search.queryparser.query.Spec alTerm;
 mport com.tw ter.search.queryparser.query.Term;
 mport com.tw ter.search.queryparser.query.search.L nk;
 mport com.tw ter.search.queryparser.query.search.SearchOperator;
 mport com.tw ter.search.queryparser.query.search.SearchQueryV s or;

/**
 * V s or to track t  f elds h s of each node
 * Returns t  common f elds among conjunct ons and t  un on of t  f elds amongst d sjunct ons
 */
publ c f nal class QueryCommonF eldH sV s or extends SearchQueryV s or<Set<Str ng>> {

  pr vate stat c f nal Logger LOG = Logger.getLogger(QueryCommonF eldH sV s or.class.getNa ());

  pr vate Map<Query,  nteger> nodeToRankMap;
  pr vate Map< nteger, L st<Str ng>> h F eldsByRank;

  /**
   * F nd query term h   ntersect ons based on h map g ven by H Attr bute lper
   *
   * @param h Attr bute lper t  H Attr bute lper
   * @param doc D docu nt D
   * @param query t  query searc d
   * @return a set of h  f elds  n Str ng representat on
   */
  publ c stat c Set<Str ng> f nd ntersect on(
      H Attr bute lper h Attr bute lper,
       nt doc D,
      Query query) {
    return f nd ntersect on(h Attr bute lper.getNodeToRankMap(),
                            h Attr bute lper.getH Attr but on(doc D),
                            query);
  }

  /**
   * F nd query term h   ntersect ons based on h map g ven by H Attr bute lper
   *
   * @param nodeToRankMap t  map of query node to  s  nteger rank value
   * @param h F eldsByRank map of rank to l st of h  f elds  n Str ng representat on
   * @param query t  query searc d
   * @return a set of h  f elds  n Str ng representat on
   */
  publ c stat c Set<Str ng> f nd ntersect on(
      Map<Query,  nteger> nodeToRankMap,
      Map< nteger, L st<Str ng>> h F eldsByRank,
      Query query) {
    QueryCommonF eldH sV s or v s or =
        new QueryCommonF eldH sV s or(nodeToRankMap, h F eldsByRank);
    try {
      Set<Str ng> returnSet = query.accept(v s or);
      return returnSet;
    } catch (QueryParserExcept on e) {
      LOG.log(Level.SEVERE, "Could not f nd  ntersect on for query [" + query + "]: ", e);
      return Collect ons.emptySet();
    }
  }

  pr vate QueryCommonF eldH sV s or(Map<Query,  nteger> nodeToRankMap,
                                      Map< nteger, L st<Str ng>> h F eldsByRank) {
    t .nodeToRankMap = nodeToRankMap;
    t .h F eldsByRank = h F eldsByRank;
  }

  @Overr de
  publ c Set<Str ng> v s (D sjunct on d sjunct on) throws QueryParserExcept on {
    Set<Str ng> f eldH  ntersect ons = Sets.newHashSet();
    for (Query ch ld : d sjunct on.getCh ldren()) {
      f eldH  ntersect ons.addAll(ch ld.accept(t ));
    }
    return f eldH  ntersect ons;
  }

  @Overr de
  publ c Set<Str ng> v s (Conjunct on conjunct on) throws QueryParserExcept on {
    L st<Query> ch ldren = conjunct on.getCh ldren();
     f (!ch ldren. sEmpty()) {
      boolean  n  al zed ntersect ons = false;
      Set<Str ng> f eldH  ntersect ons = Sets.newHashSet();
      for (Query ch ld : ch ldren) {
        Set<Str ng> h s = ch ld.accept(t );
         f (h s. sEmpty()) {
          //  f    s empty,    ans t  query node  s not of term type
          // and   do not  nclude t se  n t  f eld  ntersect on
          // eg. cac  f lters, prox m y groups
          cont nue;
        }
         f (! n  al zed ntersect ons) {
          f eldH  ntersect ons.addAll(h s);
           n  al zed ntersect ons = true;
        } else {
          f eldH  ntersect ons.reta nAll(h s);
        }
      }
      return f eldH  ntersect ons;
    }
    return Collect ons.emptySet();
  }

  @Overr de
  publ c Set<Str ng> v s (Term term) throws QueryParserExcept on {
    Set<Str ng> f eldH  ntersect ons = Sets.newHashSet();
     nteger rank = nodeToRankMap.get(term);
     f (rank != null) {
      L st<Str ng> f elds = h F eldsByRank.get(rank);
      // for d sjunct on cases w re a term may not have any h s
       f (f elds != null) {
        f eldH  ntersect ons.addAll(f elds);
      }
    }
    return f eldH  ntersect ons;
  }

  @Overr de
  publ c Set<Str ng> v s (Spec alTerm spec alTerm) throws QueryParserExcept on {
    // T   s way of spl t ng @ nt ons ensures cons stency w h way t  lucene query  s bu lt  n
    // expertsearch
     f (spec alTerm.getType() == Spec alTerm.Type.MENT ON && spec alTerm.getValue().conta ns("_")) {
      Phrase phrase = new Phrase(spec alTerm.getValue().spl ("_"));
      return phrase.accept(t );
    }
    return spec alTerm.toTermOrPhrase().accept(t );
  }

  @Overr de
  publ c Set<Str ng> v s (SearchOperator operator) throws QueryParserExcept on {
    return Collect ons.emptySet();
  }

  @Overr de
  publ c Set<Str ng> v s (L nk l nk) throws QueryParserExcept on {
    return l nk.toPhrase().accept(t );
  }

  @Overr de
  publ c Set<Str ng> v s (Phrase phrase) throws QueryParserExcept on {
    // All terms  n t  phrase should return t  sa  h s f elds, just c ck t  f rst one
    L st<Str ng> terms = phrase.getTerms();
     f (!terms. sEmpty()) {
      Term term = new Term(phrase.getTerms().get(0));
      return term.accept(t );
    }
    return Collect ons.emptySet();
  }
}
