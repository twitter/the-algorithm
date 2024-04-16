package com.tw ter.search.earlyb rd.search.quer es;

 mport java. o. OExcept on;
 mport java.ut l.Objects;

 mport com.google.common.annotat ons.V s bleForTest ng;

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
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftCSFType;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;
 mport com.tw ter.search.core.earlyb rd. ndex.ut l.AllDocs erator;
 mport com.tw ter.search.core.earlyb rd. ndex.ut l.RangeF lterD S ;

/**
 * F lters t ets accord ng to t  spec f ed CSF f eld value.
 * Note that m n value  s  nclus ve, and max value  s exclus ve.
 */
publ c f nal class DocValRangeF lter extends Query {
  pr vate f nal Str ng csfF eld;
  pr vate f nal Thr ftCSFType csfF eldType;
  pr vate f nal Number m nVal nclus ve;
  pr vate f nal Number maxValExclus ve;

  /**
   * Returns a query that f lters h s based on t  value of a CSF.
   *
   * @param csfF eld T  CSF na .
   * @param csfF eldType T  CSF type.
   * @param m nVal T  m n mum acceptable value ( nclus ve).
   * @param maxVal T  max mum acceptable value (exclus ve).
   * @return A query that f lters h s based on t  value of a CSF.
   */
  publ c stat c Query getDocValRangeQuery(Str ng csfF eld, Thr ftCSFType csfF eldType,
                                          double m nVal, double maxVal) {
    return new BooleanQuery.Bu lder()
        .add(new DocValRangeF lter(csfF eld, csfF eldType, m nVal, maxVal),
             BooleanClause.Occur.F LTER)
        .bu ld();
  }

  /**
   * Returns a query that f lters h s based on t  value of a CSF.
   *
   * @param csfF eld T  CSF na .
   * @param csfF eldType T  CSF type.
   * @param m nVal T  m n mum acceptable value ( nclus ve).
   * @param maxVal T  max mum acceptable value (exclus ve).
   * @return A query that f lters h s based on t  value of a CSF.
   */
  publ c stat c Query getDocValRangeQuery(Str ng csfF eld, Thr ftCSFType csfF eldType,
                                          long m nVal, long maxVal) {
    return new BooleanQuery.Bu lder()
        .add(new DocValRangeF lter(csfF eld, csfF eldType, m nVal, maxVal),
             BooleanClause.Occur.F LTER)
        .bu ld();
  }

  pr vate DocValRangeF lter(Str ng csfF eld, Thr ftCSFType csfF eldType,
                            double m nVal, double maxVal) {
    t .csfF eld = csfF eld;
    t .csfF eldType = csfF eldType;
    t .m nVal nclus ve = new Float(m nVal);
    t .maxValExclus ve = new Float(maxVal);
  }

  pr vate DocValRangeF lter(Str ng csfF eld, Thr ftCSFType csfF eldType,
                            long m nVal, long maxVal) {
    t .csfF eld = csfF eld;
    t .csfF eldType = csfF eldType;
    t .m nVal nclus ve = new Long(m nVal);
    t .maxValExclus ve = new Long(maxVal);
  }

  @Overr de
  publ c  nt hashCode() {
    return (csfF eld == null ? 0 : csfF eld.hashCode()) * 29
        + (csfF eldType == null ? 0 : csfF eldType.hashCode()) * 17
        + m nVal nclus ve.hashCode() * 7
        + maxValExclus ve.hashCode();
  }

  @Overr de
  publ c boolean equals(Object obj) {
     f (!(obj  nstanceof DocValRangeF lter)) {
      return false;
    }

    DocValRangeF lter f lter = DocValRangeF lter.class.cast(obj);
    return Objects.equals(csfF eld, f lter.csfF eld)
        && (csfF eldType == f lter.csfF eldType)
        && m nVal nclus ve.equals(f lter.m nVal nclus ve)
        && maxValExclus ve.equals(f lter.maxValExclus ve);
  }

  @Overr de
  publ c Str ng toStr ng(Str ng f eld) {
    return "DocValRangeF lter:" + csfF eld
        + ",type:" + csfF eldType.toStr ng()
        + ",m n:" + t .m nVal nclus ve.toStr ng()
        + ",max:" + t .maxValExclus ve.toStr ng();
  }

  @Overr de
  publ c   ght create  ght( ndexSearc r searc r, ScoreMode scoreMode, float boost) {
    return new DefaultF lter  ght(t ) {
      @Overr de
      protected Doc dSet erator getDoc dSet erator(LeafReaderContext context) throws  OExcept on {
        LeafReader reader = context.reader();
         f (csfF eldType == null) {
          return new AllDocs erator(reader);
        }

         nt smallestDoc = (reader  nstanceof Earlyb rd ndexSeg ntAtom cReader)
            ? ((Earlyb rd ndexSeg ntAtom cReader) reader).getSmallestDoc D() : 0;
         nt largestDoc = reader.maxDoc() - 1;
        return new CSFRangeDoc dSet erator(reader, csfF eld, csfF eldType,
                                            smallestDoc, largestDoc,
                                            m nVal nclus ve, maxValExclus ve);
      }
    };
  }

  pr vate stat c f nal class CSFRangeDoc dSet erator extends RangeF lterD S  {
    pr vate f nal Nu r cDocValues nu r cDocValues;
    pr vate f nal Thr ftCSFType csfType;
    pr vate f nal Number m nVal nclus ve;
    pr vate f nal Number maxValExclus ve;

    publ c CSFRangeDoc dSet erator(LeafReader reader,
                                    Str ng csfF eld,
                                    Thr ftCSFType csfType,
                                     nt smallestDoc D,
                                     nt largestDoc D,
                                    Number m nVal nclus ve,
                                    Number maxValExclus ve) throws  OExcept on {
      super(reader, smallestDoc D, largestDoc D);
      t .nu r cDocValues = reader.getNu r cDocValues(csfF eld);
      t .csfType = csfType;
      t .m nVal nclus ve = m nVal nclus ve;
      t .maxValExclus ve = maxValExclus ve;
    }

    @Overr de
    protected boolean shouldReturnDoc() throws  OExcept on {
       f (!nu r cDocValues.advanceExact(doc D())) {
        return false;
      }

      long val = nu r cDocValues.longValue();
      sw ch (csfType) {
        case DOUBLE:
          double doubleVal = Double.longB sToDouble(val);
          return doubleVal >= m nVal nclus ve.doubleValue()
              && doubleVal < maxValExclus ve.doubleValue();
        case FLOAT:
          float floatVal = Float. ntB sToFloat(( nt) val);
          return floatVal >= m nVal nclus ve.doubleValue()
              && floatVal < maxValExclus ve.doubleValue();
        case LONG:
          return val >= m nVal nclus ve.longValue() && val < maxValExclus ve.longValue();
        case  NT:
          return val >= m nVal nclus ve.longValue() && ( nt) val < maxValExclus ve.longValue();
        case BYTE:
          return (byte) val >= m nVal nclus ve.longValue()
              && (byte) val < maxValExclus ve.longValue();
        default:
          return false;
      }
    }
  }

  //////////////////////////
  // for un  tests only
  //////////////////////////
  @V s bleForTest ng
  publ c Number getM nValForTest() {
    return m nVal nclus ve;
  }

  @V s bleForTest ng
  publ c Number getMaxValForTest() {
    return maxValExclus ve;
  }
}
