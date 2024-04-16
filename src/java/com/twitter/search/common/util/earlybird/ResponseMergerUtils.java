package com.tw ter.search.common.ut l.earlyb rd;

 mport java.ut l.L st;
 mport java.ut l.Set;

 mport com.google.common.collect.L sts;
 mport com.google.common.collect.Sets;

 mport com.tw ter.search.common.query.thr ftjava.EarlyTerm nat on nfo;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;

publ c f nal class Response rgerUt ls {

  // Ut l y class, d sallow  nstant at on.
  pr vate Response rgerUt ls() {
  }

  /**
   *  rges early term nat on  nfos from several earlyb rd responses.
   *
   * @param responses earlyb rd responses to  rge t  early term nat on  nfos from
   * @return  rged early term nat on  nfo
   */
  publ c stat c EarlyTerm nat on nfo  rgeEarlyTerm nat on nfo(L st<Earlyb rdResponse> responses) {
    EarlyTerm nat on nfo et nfo = new EarlyTerm nat on nfo(false);
    Set<Str ng> etReasonSet = Sets.newHashSet();
    // F ll  n EarlyTerm nat onStatus
    for (Earlyb rdResponse ebResp : responses) {
       f (ebResp. sSetEarlyTerm nat on nfo()
          && ebResp.getEarlyTerm nat on nfo(). sEarlyTerm nated()) {
        et nfo.setEarlyTerm nated(true);
         f (ebResp.getEarlyTerm nat on nfo(). sSetEarlyTerm nat onReason()) {
          etReasonSet.add(ebResp.getEarlyTerm nat on nfo().getEarlyTerm nat onReason());
        }
         f (ebResp.getEarlyTerm nat on nfo(). sSet rgedEarlyTerm nat onReasons()) {
          etReasonSet.addAll(ebResp.getEarlyTerm nat on nfo().get rgedEarlyTerm nat onReasons());
        }
      }
    }
     f (et nfo. sEarlyTerm nated()) {
      et nfo.set rgedEarlyTerm nat onReasons(L sts.newArrayL st(etReasonSet));
    }
    return et nfo;
  }
}
