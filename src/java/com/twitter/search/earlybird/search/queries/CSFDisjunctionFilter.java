package com.tw ter.search.earlyb rd.search.quer es;

 mport java. o. OExcept on;
 mport java.ut l.Objects;
 mport java.ut l.Set;

 mport org.apac .lucene. ndex.LeafReader;
 mport org.apac .lucene. ndex.LeafReaderContext;
 mport org.apac .lucene. ndex.Nu r cDocValues;
 mport org.apac .lucene.search.BooleanClause;
 mport org.apac .lucene.search.BooleanQuery;
 mport org.apac .lucene.search.Doc dSet erator;
 mport org.apac .lucene.search. ndexSearc r;
 mport org.apac .lucene.search.Query;
 mport org.apac .lucene.search.ScoreMode;
 mport org.apac .lucene.search.  ght;

 mport com.tw ter.search.common.query.DefaultF lter  ght;
 mport com.tw ter.search.core.earlyb rd. ndex.ut l.RangeF lterD S ;

/**
 * CSFD sjunct onF lter prov des an eff c ent  chan sm to query for docu nts that have a
 * long CSF equal to one of t  prov ded values.
 */
publ c f nal class CSFD sjunct onF lter extends Query {
  pr vate f nal Str ng csfF eld;
  pr vate f nal Set<Long> values;

  publ c stat c Query getCSFD sjunct onF lter(Str ng csfF eld, Set<Long> values) {
    return new BooleanQuery.Bu lder()
        .add(new CSFD sjunct onF lter(csfF eld, values), BooleanClause.Occur.F LTER)
        .bu ld();
  }

  pr vate CSFD sjunct onF lter(Str ng csfF eld, Set<Long> values) {
    t .csfF eld = csfF eld;
    t .values = values;
  }

  @Overr de
  publ c   ght create  ght( ndexSearc r searc r, ScoreMode scoreMode, float boost) {
    return new DefaultF lter  ght(t ) {
      @Overr de
      protected Doc dSet erator getDoc dSet erator(LeafReaderContext context) throws  OExcept on {
        return new CSFD sjunct onF lterD S (context.reader(), csfF eld, values);
      }
    };
  }

  @Overr de
  publ c  nt hashCode() {
    return (csfF eld == null ? 0 : csfF eld.hashCode()) * 17
        + (values == null ? 0 : values.hashCode());
  }

  @Overr de
  publ c boolean equals(Object obj) {
     f (!(obj  nstanceof CSFD sjunct onF lter)) {
      return false;
    }

    CSFD sjunct onF lter f lter = CSFD sjunct onF lter.class.cast(obj);
    return Objects.equals(csfF eld, f lter.csfF eld) && Objects.equals(values, f lter.values);
  }

  @Overr de
  publ c Str ng toStr ng(Str ng f eld) {
    return "CSFD sjunct onF lter:" + csfF eld + ",count:" + values.s ze();
  }

  pr vate stat c f nal class CSFD sjunct onF lterD S  extends RangeF lterD S  {
    pr vate f nal Nu r cDocValues docValues;
    pr vate f nal Set<Long> values;

    pr vate CSFD sjunct onF lterD S (LeafReader reader, Str ng csfF eld, Set<Long> values)
        throws  OExcept on {
      super(reader);
      t .values = values;
      t .docValues = reader.getNu r cDocValues(csfF eld);
    }

    @Overr de
    protected boolean shouldReturnDoc() throws  OExcept on {
      return docValues.advanceExact(doc D()) && values.conta ns(docValues.longValue());
    }
  }
}
