package com.tw ter.search.earlyb rd.search.relevance.scor ng;

 mport com.tw ter.search.common.features.thr ft.Thr ftSearchResultFeatures;
 mport com.tw ter.search.earlyb rd.search.relevance.L nearScor ngData;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult tadata;

publ c class BatchH  {
  pr vate f nal L nearScor ngData scor ngData;
  pr vate f nal Thr ftSearchResultFeatures features;
  pr vate f nal Thr ftSearchResult tadata  tadata;
  pr vate f nal long t et D;
  pr vate f nal long t  Sl ce D;

  publ c BatchH (
      L nearScor ngData scor ngData,
      Thr ftSearchResultFeatures features,
      Thr ftSearchResult tadata  tadata,
      long t et D,
      long t  Sl ce D
  ) {
    t .scor ngData = scor ngData;
    t .features = features;
    t . tadata =  tadata;
    t .t et D = t et D;
    t .t  Sl ce D = t  Sl ce D;
  }

  publ c L nearScor ngData getScor ngData() {
    return scor ngData;
  }

  publ c Thr ftSearchResultFeatures getFeatures() {
    return features;
  }

  publ c Thr ftSearchResult tadata get tadata() {
    return  tadata;
  }

  publ c long getT et D() {
    return t et D;
  }

  publ c long getT  Sl ce D() {
    return t  Sl ce D;
  }
}
