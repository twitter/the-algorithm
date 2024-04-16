package com.tw ter.search.earlyb rd_root.f lters;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.search.earlyb rd.common.Cl ent dUt l;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.ut l.Future;

/**
 * A f lter that w ll set t  cl ent d of t  request to t  strato HttpEndpo nt Attr but on.
 * <p>
 *  f t  cl ent d  s already set to so th ng non-null t n that value  s used.
 *  f t  cl ent d  s null but Attr but on.httpEndpo nt() conta ns a value   w ll be set as
 * t  cl ent d.
 */
publ c class StratoAttr but onCl ent dF lter extends
    S mpleF lter<Earlyb rdRequest, Earlyb rdResponse> {
  @Overr de
  publ c Future<Earlyb rdResponse> apply(
      Earlyb rdRequest request, Serv ce<Earlyb rdRequest, Earlyb rdResponse> serv ce
  ) {
     f (request.getCl ent d() == null) {
      Cl ent dUt l.getCl ent dFromHttpEndpo ntAttr but on(). fPresent(request::setCl ent d);
    }

    return serv ce.apply(request);
  }
}

