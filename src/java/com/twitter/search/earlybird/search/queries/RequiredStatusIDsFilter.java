package com.tw ter.search.earlyb rd.search.quer es;

 mport java. o. OExcept on;
 mport java.ut l.Arrays;
 mport java.ut l.Collect on;

 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene. ndex.LeafReader;
 mport org.apac .lucene. ndex.LeafReaderContext;
 mport org.apac .lucene.search.BooleanClause;
 mport org.apac .lucene.search.BooleanQuery;
 mport org.apac .lucene.search.Doc dSet erator;
 mport org.apac .lucene.search. ndexSearc r;
 mport org.apac .lucene.search.Query;
 mport org.apac .lucene.search.ScoreMode;
 mport org.apac .lucene.search.  ght;

 mport com.tw ter.search.common.query.DefaultF lter  ght;
 mport com.tw ter.search.common.search. ntArrayDoc dSet erator;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;
 mport com.tw ter.search.core.earlyb rd. ndex.ut l.AllDocs erator;
 mport com.tw ter.search.earlyb rd. ndex.T et DMapper;

publ c f nal class Requ redStatus DsF lter extends Query {
  pr vate f nal Collect on<Long> status Ds;

  publ c stat c Query getRequ redStatus DsQuery(Collect on<Long> status Ds) {
    return new BooleanQuery.Bu lder()
        .add(new Requ redStatus DsF lter(status Ds), BooleanClause.Occur.F LTER)
        .bu ld();
  }

  pr vate Requ redStatus DsF lter(Collect on<Long> status Ds) {
    t .status Ds = Precond  ons.c ckNotNull(status Ds);
  }

  @Overr de
  publ c   ght create  ght( ndexSearc r searc r, ScoreMode scoreMode, float boost) {
    return new DefaultF lter  ght(t ) {
      @Overr de
      protected Doc dSet erator getDoc dSet erator(LeafReaderContext context) throws  OExcept on {
        LeafReader leafReader = context.reader();
         f (!(leafReader  nstanceof Earlyb rd ndexSeg ntAtom cReader)) {
          return Doc dSet erator.empty();
        }

        Earlyb rd ndexSeg ntAtom cReader reader = (Earlyb rd ndexSeg ntAtom cReader) leafReader;
        T et DMapper  dMapper = (T et DMapper) reader.getSeg ntData().getDoc DToT et DMapper();

         nt doc dsS ze = 0;
         nt[] doc ds = new  nt[status Ds.s ze()];
        for (long status D : status Ds) {
           nt doc d =  dMapper.getDoc D(status D);
           f (doc d >= 0) {
            doc ds[doc dsS ze++] = doc d;
          }
        }

        Arrays.sort(doc ds, 0, doc dsS ze);
        Doc dSet erator statusesD S  =
            new  ntArrayDoc dSet erator(Arrays.copyOf(doc ds, doc dsS ze));
        Doc dSet erator allDocsD S  = new AllDocs erator(reader);

        //   only want to return  Ds for fully  ndexed docu nts. So   need to make sure that
        // every doc  D   return ex sts  n allDocsD S . Ho ver, allDocsD S  has all docu nts  n
        // t  seg nt, so dr v ng by allDocsD S  would be very slow. So   want to dr ve by
        // statusesD S , and use allDocsD S  as a post-f lter. What t  co s down to  s that   do
        // not want to call allDocsD S .nextDoc();   only want to call allDocsD S .advance(), and
        // only on t  doc  Ds returned by statusesD S .
        return new Doc dSet erator() {
          @Overr de
          publ c  nt doc D() {
            return statusesD S .doc D();
          }

          @Overr de
          publ c  nt nextDoc() throws  OExcept on {
            statusesD S .nextDoc();
            return advanceToNextFully ndexedDoc();
          }

          @Overr de
          publ c  nt advance( nt target) throws  OExcept on {
            statusesD S .advance(target);
            return advanceToNextFully ndexedDoc();
          }

          pr vate  nt advanceToNextFully ndexedDoc() throws  OExcept on {
            wh le (doc D() != Doc dSet erator.NO_MORE_DOCS) {
              // C ck  f t  current doc  s fully  ndexed.
              //  f    s, t n   can return  .  f  's not, t n   need to keep search ng.
               nt allDocsDoc d = allDocsD S .advance(doc D());
               f (allDocsDoc d == doc D()) {
                break;
              }

              statusesD S .advance(allDocsDoc d);
            }
            return doc D();
          }

          @Overr de
          publ c long cost() {
            return statusesD S .cost();
          }
        };
      }
    };
  }

  @Overr de
  publ c  nt hashCode() {
    return status Ds.hashCode();
  }

  @Overr de
  publ c boolean equals(Object obj) {
     f (!(obj  nstanceof Requ redStatus DsF lter)) {
      return false;
    }

    Requ redStatus DsF lter f lter = Requ redStatus DsF lter.class.cast(obj);
    return status Ds.equals(f lter.status Ds);
  }

  @Overr de
  publ c f nal Str ng toStr ng(Str ng f eld) {
    return Str ng.format("Requ redStatus Ds[%s]", status Ds);
  }
}
