package com.tw ter.search.earlyb rd.search;

 mport java.ut l.Map;

 mport com.google.common.collect.Maps;

 mport com.tw ter.search.earlyb rd.search.quer es.S nceMax DF lter;

publ c class SearchResults nfo {
  publ c stat c f nal long NO_ D = S nceMax DF lter.NO_F LTER;
  publ c stat c f nal  nt NO_T ME = -1;

  pr vate  nt numH sProcessed = 0;
  pr vate  nt numSearc dSeg nts = 0;

  pr vate boolean earlyTerm nated = false;
  pr vate Str ng earlyTerm nat onReason = null;

  pr vate long maxSearc dStatus D = NO_ D;
  pr vate long m nSearc dStatus D = NO_ D;

  pr vate  nt maxSearc dT   = NO_T ME;
  pr vate  nt m nSearc dT   = NO_T ME;

  // Map from t   thresholds ( n m ll seconds) to number of results more recent than t  per od.
  protected f nal Map<Long,  nteger> h Counts = Maps.newHashMap();

  publ c f nal  nt getNumH sProcessed() {
    return numH sProcessed;
  }

  publ c f nal vo d setNumH sProcessed( nt numH sProcessed) {
    t .numH sProcessed = numH sProcessed;
  }

  publ c f nal  nt getNumSearc dSeg nts() {
    return numSearc dSeg nts;
  }

  publ c f nal vo d setNumSearc dSeg nts( nt numSearc dSeg nts) {
    t .numSearc dSeg nts = numSearc dSeg nts;
  }

  publ c f nal long getMaxSearc dStatus D() {
    return maxSearc dStatus D;
  }

  publ c f nal long getM nSearc dStatus D() {
    return m nSearc dStatus D;
  }

  publ c f nal  nt getMaxSearc dT  () {
    return maxSearc dT  ;
  }

  publ c f nal  nt getM nSearc dT  () {
    return m nSearc dT  ;
  }

  publ c boolean  sSetSearc dStatus Ds() {
    return maxSearc dStatus D != NO_ D && m nSearc dStatus D != NO_ D;
  }

  publ c boolean  sSetSearc dT  s() {
    return maxSearc dT   != NO_T ME && m nSearc dT   != NO_T ME;
  }

  publ c vo d setMaxSearc dStatus D(long maxSearc dStatus D) {
    t .maxSearc dStatus D = maxSearc dStatus D;
  }

  publ c vo d setM nSearc dStatus D(long m nSearc dStatus D) {
    t .m nSearc dStatus D = m nSearc dStatus D;
  }

  publ c vo d setMaxSearc dT  ( nt maxSearc dT  ) {
    t .maxSearc dT   = maxSearc dT  ;
  }

  publ c vo d setM nSearc dT  ( nt m nSearc dT  ) {
    t .m nSearc dT   = m nSearc dT  ;
  }

  publ c vo d setEarlyTerm nated(boolean earlyTerm nated) {
    t .earlyTerm nated = earlyTerm nated;
  }

  publ c boolean  sEarlyTerm nated() {
    return earlyTerm nated;
  }

  publ c Str ng getEarlyTerm nat onReason() {
    return earlyTerm nat onReason;
  }

  publ c vo d setEarlyTerm nat onReason(Str ng earlyTerm nat onReason) {
    t .earlyTerm nat onReason = earlyTerm nat onReason;
  }
}
