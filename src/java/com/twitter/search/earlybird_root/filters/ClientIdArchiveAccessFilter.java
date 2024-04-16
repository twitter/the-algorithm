package com.tw ter.search.earlyb rd_root.f lters;

 mport java.ut l.Opt onal;

 mport javax. nject. nject;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.earlyb rd.common.Cl ent dUt l;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.search.earlyb rd_root.quota.Cl ent dQuotaManager;
 mport com.tw ter.search.earlyb rd_root.quota.Quota nfo;
 mport com.tw ter.ut l.Future;

publ c class Cl ent dArch veAccessF lter extends S mpleF lter<Earlyb rdRequest, Earlyb rdResponse> {
  pr vate stat c f nal Str ng UNAUTHOR ZED_ARCH VE_ACCESS_COUNTER_PATTERN =
      "unauthor zed_access_to_full_arch ve_by_cl ent_%s";

  pr vate f nal Cl ent dQuotaManager quotaManager;

  /**
   * Construct t  f lter by us ng Cl ent dQuotaManager
   */
  @ nject
  publ c Cl ent dArch veAccessF lter(Cl ent dQuotaManager quotaManager) {
    t .quotaManager = Precond  ons.c ckNotNull(quotaManager);
  }

  @Overr de
  publ c Future<Earlyb rdResponse> apply(Earlyb rdRequest request,
                                         Serv ce<Earlyb rdRequest, Earlyb rdResponse> serv ce) {
    Str ng cl ent d = Cl ent dUt l.getCl ent dFromRequest(request);

    Opt onal<Quota nfo> quota nfoOpt onal = quotaManager.getQuotaForCl ent(cl ent d);
    Quota nfo quota nfo = quota nfoOpt onal.orElseGet(quotaManager::getCommonPoolQuota);
     f (!quota nfo.hasArch veAccess() && request. sGetOlderResults()) {
      SearchCounter unauthor zedArch veAccessCounter = SearchCounter.export(
          Str ng.format(UNAUTHOR ZED_ARCH VE_ACCESS_COUNTER_PATTERN, cl ent d));
      unauthor zedArch veAccessCounter. ncre nt();

      Str ng  ssage = Str ng.format(
          "Cl ent %s  s not wh el sted for arch ve access. Request access at go/searchquota.",
          cl ent d);
      Earlyb rdResponse response = new Earlyb rdResponse(
          Earlyb rdResponseCode.QUOTA_EXCEEDED_ERROR, 0)
          .setDebugStr ng( ssage);
      return Future.value(response);
    }
    return serv ce.apply(request);
  }
}
