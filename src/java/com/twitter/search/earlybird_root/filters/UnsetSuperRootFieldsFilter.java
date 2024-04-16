package com.tw ter.search.earlyb rd_root.f lters;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestUt l;
 mport com.tw ter.ut l.Future;

/**
 * A f lter that unsets so  request f elds that make sense only on t  SuperRoot, before send ng
 * t m to t   nd v dual roots.
 */
publ c class UnsetSuperRootF eldsF lter extends S mpleF lter<Earlyb rdRequest, Earlyb rdResponse> {
  pr vate f nal boolean unsetFollo dUser ds;

  publ c UnsetSuperRootF eldsF lter() {
    t (true);
  }

  publ c UnsetSuperRootF eldsF lter(boolean unsetFollo dUser ds) {
    t .unsetFollo dUser ds = unsetFollo dUser ds;
  }

  @Overr de
  publ c Future<Earlyb rdResponse> apply(Earlyb rdRequest request,
                                         Serv ce<Earlyb rdRequest, Earlyb rdResponse> serv ce) {
    return serv ce.apply(Earlyb rdRequestUt l.unsetSuperRootF elds(request, unsetFollo dUser ds));
  }
}
