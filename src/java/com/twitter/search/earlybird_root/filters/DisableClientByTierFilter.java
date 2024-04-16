package com.tw ter.search.earlyb rd_root.f lters;

 mport java.ut l.Opt onal;

 mport javax. nject. nject;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.L sts;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.earlyb rd.common.Cl ent dUt l;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResults;
 mport com.tw ter.search.earlyb rd_root.quota.Cl ent dQuotaManager;
 mport com.tw ter.search.earlyb rd_root.quota.Quota nfo;
 mport com.tw ter.ut l.Future;

publ c class D sableCl entByT erF lter extends S mpleF lter<Earlyb rdRequest, Earlyb rdResponse> {
  pr vate stat c f nal Str ng CL ENT_BLOCKED_RESPONSE_PATTERN =
      "Requests of cl ent %s are blocked due to %s d sable";

  pr vate f nal SearchDec der dec der;
  pr vate f nal Cl ent dQuotaManager quotaManager;

  /**
   * Construct t  f lter by us ng Cl ent dQuotaManager
   */
  @ nject
  publ c D sableCl entByT erF lter(Cl ent dQuotaManager quotaManager, SearchDec der dec der) {
    t .quotaManager = Precond  ons.c ckNotNull(quotaManager);
    t .dec der = dec der;
  }

  @Overr de
  publ c Future<Earlyb rdResponse> apply(Earlyb rdRequest request,
                                         Serv ce<Earlyb rdRequest, Earlyb rdResponse> serv ce) {
    Str ng cl ent d = Cl ent dUt l.getCl ent dFromRequest(request);
    Opt onal<Quota nfo> quota nfoOpt onal = quotaManager.getQuotaForCl ent(cl ent d);
    Quota nfo quota nfo = quota nfoOpt onal.orElseGet(quotaManager::getCommonPoolQuota);
    // T er value should ex st:  f cl ent's t er value not  n conf g f le,   w ll be
    // set to "no_t er" by default  n Conf gBasedQuotaConf g
    Str ng t er = quota nfo.getCl entT er();

    Precond  ons.c ckNotNull(t er);

     f (dec der. sAva lable("superroot_unava lable_for_" + t er + "_cl ents")) {
      return Future.value(getCl entBlockedResponse(cl ent d, t er));
    } else {
      return serv ce.apply(request);
    }
  }

  pr vate stat c Earlyb rdResponse getCl entBlockedResponse(Str ng cl ent d, Str ng t er) {
    return new Earlyb rdResponse(Earlyb rdResponseCode.CL ENT_BLOCKED_BY_T ER_ERROR, 0)
        .setSearchResults(new Thr ftSearchResults()
            .setResults(L sts.<Thr ftSearchResult>newArrayL st()))
        .setDebugStr ng(Str ng.format(CL ENT_BLOCKED_RESPONSE_PATTERN, cl ent d, t er));
  }
}
