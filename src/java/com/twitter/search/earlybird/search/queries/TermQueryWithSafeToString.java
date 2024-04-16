package com.tw ter.search.earlyb rd.search.quer es;

 mport org.apac .lucene. ndex.Term;
 mport org.apac .lucene.search.TermQuery;

/**
 * Work around an  ssue w re  ntTerms and LongTerms are not val d utf8,
 * so call ng toStr ng on any TermQuery conta n ng an  ntTerm or a LongTerm may cause except ons.
 * T  code should produce t  sa  output as TermQuery.toStr ng
 */
publ c f nal class TermQueryW hSafeToStr ng extends TermQuery {
  pr vate f nal Str ng termValueForToStr ng;

  publ c TermQueryW hSafeToStr ng(Term term, Str ng termValueForToStr ng) {
    super(term);
    t .termValueForToStr ng = termValueForToStr ng;
  }

  @Overr de
  publ c Str ng toStr ng(Str ng f eld) {
    Str ngBu lder buffer = new Str ngBu lder();
     f (!getTerm().f eld().equals(f eld)) {
      buffer.append(getTerm().f eld());
      buffer.append(":");
    }
    buffer.append(termValueForToStr ng);
    return buffer.toStr ng();
  }
}
