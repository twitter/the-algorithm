package com.tw ter.search.earlyb rd_root.f lters;

 mport javax. nject. nject;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.ut l.Future;

/** A f lter that sets t  Earlyb rdRequest.cl entRequestT  Ms f eld  f  's not already set. */
publ c class Cl entRequestT  F lter extends S mpleF lter<Earlyb rdRequest, Earlyb rdResponse> {
  pr vate stat c f nal SearchCounter CL ENT_REQUEST_T ME_MS_UNSET_COUNTER =
      SearchCounter.export("cl ent_request_t  _ms_unset");

  pr vate f nal Clock clock;

  @ nject
  publ c Cl entRequestT  F lter(Clock clock) {
    t .clock = clock;
  }

  @Overr de
  publ c Future<Earlyb rdResponse> apply(Earlyb rdRequest request,
                                         Serv ce<Earlyb rdRequest, Earlyb rdResponse> serv ce) {
     f (!request. sSetCl entRequestT  Ms()) {
      CL ENT_REQUEST_T ME_MS_UNSET_COUNTER. ncre nt();
      request.setCl entRequestT  Ms(clock.nowM ll s());
    }
    return serv ce.apply(request);
  }
}
