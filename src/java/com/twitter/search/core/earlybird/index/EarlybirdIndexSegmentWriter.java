package com.tw ter.search.core.earlyb rd. ndex;

 mport java. o.Closeable;
 mport java. o. OExcept on;

 mport org.apac .lucene.docu nt.Docu nt;
 mport org.apac .lucene. ndex. ndexReader;
 mport org.apac .lucene. ndex.LeafReaderContext;
 mport org.apac .lucene.search.Collector;
 mport org.apac .lucene.search. ndexSearc r;
 mport org.apac .lucene.search.LeafCollector;
 mport org.apac .lucene.search.Query;
 mport org.apac .lucene.search.Scorable;
 mport org.apac .lucene.search.ScoreMode;
 mport org.apac .lucene.store.D rectory;

 mport com.tw ter.search.core.earlyb rd. ndex.column.ColumnStr deF eld ndex;
 mport com.tw ter.search.core.earlyb rd. ndex.column.DocValuesUpdate;

/**
 *  ndexSeg ntWr er comb nes so  common funct onal y bet en t  Lucene and Realt    ndex
 * seg nt wr ers.
 */
publ c abstract class Earlyb rd ndexSeg ntWr er  mple nts Closeable {

  publ c Earlyb rd ndexSeg ntWr er() {
  }

  /**
   * Gets t  seg nt data t  seg nt wr e  s assoc ated w h.
   * @return
   */
  publ c abstract Earlyb rd ndexSeg ntData getSeg ntData();

  /**
   * Appends terms from t  docu nt to t  docu nt match ng t  query. Does not replace a f eld or
   * docu nt, actually adds to t  t  f eld  n t  seg nt.
   */
  publ c f nal vo d appendOutOfOrder(Query query, Docu nt doc) throws  OExcept on {
    runQuery(query, doc D -> appendOutOfOrder(doc, doc D));
  }

  protected abstract vo d appendOutOfOrder(Docu nt doc,  nt doc d) throws  OExcept on;

  /**
   * Deletes a docu nt  n t  seg nt that matc s t  query.
   */
  publ c vo d deleteDocu nts(Query query) throws  OExcept on {
    runQuery(query, doc D -> getSeg ntData().getDeletedDocs().deleteDoc(doc D));
  }

  /**
   * Updates t  docvalues of a docu nt  n t  seg nt that matc s t  query.
   */
  publ c vo d updateDocValues(Query query, Str ng f eld, DocValuesUpdate update)
      throws  OExcept on {
    runQuery(query, doc D -> {
        ColumnStr deF eld ndex docValues =
            getSeg ntData().getDocValuesManager().getColumnStr deF eld ndex(f eld);
         f (docValues == null) {
          return;
        }

        update.update(docValues, doc D);
      });
  }

  pr vate vo d runQuery(f nal Query query, f nal OnH  onH ) throws  OExcept on {
    try ( ndexReader reader = getSeg ntData().createAtom cReader()) {
      new  ndexSearc r(reader).search(query, new Collector() {
        @Overr de
        publ c LeafCollector getLeafCollector(LeafReaderContext context) throws  OExcept on {
          return new LeafCollector() {
            @Overr de
            publ c vo d setScorer(Scorable scorer) {
            }

            @Overr de
            publ c vo d collect( nt doc D) throws  OExcept on {
              onH .h (doc D);
            }
          };
        }

        @Overr de
        publ c ScoreMode scoreMode() {
          return ScoreMode.COMPLETE_NO_SCORES;
        }
      });
    }
  }

  pr vate  nterface OnH  {
    vo d h ( nt doc D) throws  OExcept on;
  }

  /**
   * Adds a new docu nt to t  seg nt.  n product on, t   thod should be called only by
   * Expertsearch.
   */
  publ c abstract vo d addDocu nt(Docu nt doc) throws  OExcept on;

  /**
   * Adds a new t et to t  seg nt. T   thod should be called only by Earlyb rd.
   */
  publ c abstract vo d addT et(Docu nt doc, long t et d, boolean doc sOffens ve)
      throws  OExcept on;

  /**
   * Returns t  total number of docu nts  n t  seg nt.
   */
  publ c abstract  nt numDocs() throws  OExcept on;

  /**
   * Returns t  number of docu nts  n t  seg nt w hout tak ng deleted docs  nto account.
   * E.g.  f 10 docu nts  re added to t  seg nts, and 5  re deleted,
   * t   thod st ll returns 10.
   */
  publ c abstract  nt numDocsNoDelete() throws  OExcept on;

  /**
   * Forces t  underly ng  ndex to be  rged down to a s ngle seg nt.
   */
  publ c abstract vo d force rge() throws  OExcept on;

  /**
   * Appends t  prov des Lucene  ndexes to t  seg nt.
   */
  publ c abstract vo d add ndexes(D rectory... d rs) throws  OExcept on;
}
