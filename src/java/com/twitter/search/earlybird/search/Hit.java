package com.tw ter.search.earlyb rd.search;

 mport javax.annotat on.Nullable;

 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult tadata;

/**
 * Class that abstracts a docu nt that matc s a query  're process ng  n Earlyb rd.
 */
publ c class H   mple nts Comparable<H > {
  protected long t  Sl ce D;
  protected long status D;
  pr vate boolean hasExplanat on;

  @Nullable
  protected Thr ftSearchResult tadata  tadata;

  publ c H (long t  Sl ce D, long status D) {
    t .t  Sl ce D = t  Sl ce D;
    t .status D = status D;
    t . tadata = null;
  }

  publ c long getT  Sl ce D() {
    return t  Sl ce D;
  }

  publ c long getStatus D() {
    return status D;
  }

  @Nullable
  publ c Thr ftSearchResult tadata get tadata() {
    return  tadata;
  }

  publ c vo d set tadata(Thr ftSearchResult tadata  tadata) {
    t . tadata =  tadata;
  }

  @Overr de
  publ c  nt compareTo(H  ot r) {
    return -Long.compare(t .status D, ot r.status D);
  }

  @Overr de
  publ c Str ng toStr ng() {
    return "H [t et D=" + status D + ",t  Sl ce D=" + t  Sl ce D
        + ",score=" + ( tadata == null ? "null" :  tadata.getScore()) + "]";
  }

  publ c boolean  sHasExplanat on() {
    return hasExplanat on;
  }

  publ c vo d setHasExplanat on(boolean hasExplanat on) {
    t .hasExplanat on = hasExplanat on;
  }
}
