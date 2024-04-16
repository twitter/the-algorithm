package com.tw ter.search.common.query;

 mport java. o. OExcept on;
 mport java.ut l. erator;
 mport java.ut l.Set;

 mport org.apac .lucene. ndex.F lteredTermsEnum;
 mport org.apac .lucene. ndex.Terms;
 mport org.apac .lucene. ndex.TermsEnum;
 mport org.apac .lucene.search.Mult TermQuery;
 mport org.apac .lucene.ut l.Attr buteS ce;
 mport org.apac .lucene.ut l.BytesRef;


publ c class Mult TermD sjunct onQuery extends Mult TermQuery {

  pr vate f nal Set<BytesRef> values;

  /** Creates a new Mult TermD sjunct onQuery  nstance. */
  publ c Mult TermD sjunct onQuery(Str ng f eld, Set<BytesRef> values) {
    super(f eld);
    t .values = values;
  }

  @Overr de
  protected TermsEnum getTermsEnum(Terms terms, Attr buteS ce atts)
      throws  OExcept on {
    f nal TermsEnum termsEnum = terms. erator();
    f nal  erator<BytesRef>   = values. erator();

    return new F lteredTermsEnum(termsEnum) {
      @Overr de protected AcceptStatus accept(BytesRef term) throws  OExcept on {
        return AcceptStatus.YES;
      }

      @Overr de publ c BytesRef next() throws  OExcept on {
        wh le ( .hasNext()) {
          BytesRef termRef =  .next();
           f (termsEnum.seekExact(termRef)) {
            return termRef;
          }
        }

        return null;
      }
    };
  }

  @Overr de
  publ c Str ng toStr ng(Str ng f eld) {
    Str ngBu lder bu lder = new Str ngBu lder();
    bu lder.append("Mult TermD sjunct onQuery[");
    for (BytesRef termVal : t .values) {
      bu lder.append(termVal);
      bu lder.append(",");
    }
    bu lder.setLength(bu lder.length() - 1);
    bu lder.append("]");
    return bu lder.toStr ng();
  }
}
