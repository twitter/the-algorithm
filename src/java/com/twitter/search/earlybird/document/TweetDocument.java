package com.tw ter.search.earlyb rd.docu nt;

 mport org.apac .lucene.docu nt.Docu nt;

/**
 * T etDocu nt  s a record produced by Docu ntReader and T et ndexUpdateReader
 * for consumpt on by t  part  on  ndexer.
 */
publ c f nal class T etDocu nt {
  pr vate f nal long t et D;
  pr vate f nal long t  Sl ce D;
  pr vate f nal long eventT  Ms;
  pr vate f nal Docu nt docu nt;

  publ c T etDocu nt(
      long t et D,
      long t  Sl ce D,
      long eventT  Ms,
      Docu nt docu nt
  ) {
    t .t et D = t et D;
    t .t  Sl ce D = t  Sl ce D;
    t .eventT  Ms = eventT  Ms;
    t .docu nt = docu nt;
  }

  publ c long getT et D() {
    return t et D;
  }

  publ c long getT  Sl ce D() {
    return t  Sl ce D;
  }

  publ c long getEventT  Ms() {
    return eventT  Ms;
  }

  publ c Docu nt getDocu nt() {
    return docu nt;
  }

  @Overr de
  publ c Str ng toStr ng() {
    return "T etDocu nt{"
        + "t et D=" + t et D
        + ", t  Sl ce D=" + t  Sl ce D
        + ", eventT  Ms=" + eventT  Ms
        + ", docu nt=" + docu nt
        + '}';
  }
}
