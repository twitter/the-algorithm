package com.tw ter.search.earlyb rd_root.f lters;

 mport java.ut l.concurrent.atom c.Atom cReference;

 mport scala.Opt on;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.f nagle.context.Contexts;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.root.SearchPayloadS zeF lter;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.ut l.Future;

/**
 * A f lter that sets t  cl ent d  n t  local context, to be usd later by SearchPayloadS zeF lter.
 */
publ c class SearchPayloadS zeLocalContextF lter
    extends S mpleF lter<Earlyb rdRequest, Earlyb rdResponse> {
  pr vate stat c f nal SearchCounter CL ENT_ D_CONTEXT_KEY_NOT_SET_COUNTER = SearchCounter.export(
      "search_payload_s ze_local_context_f lter_cl ent_ d_context_key_not_set");

  @Overr de
  publ c Future<Earlyb rdResponse> apply(Earlyb rdRequest request,
                                         Serv ce<Earlyb rdRequest, Earlyb rdResponse> serv ce) {
    //  n product on, t  SearchPayloadS zeF lter.CL ENT_ D_CONTEXT_KEY should always be set
    // (by Thr ftServer). Ho ver,  's not set  n tests, because tests do not start a Thr ftServer.
    Opt on<Atom cReference<Str ng>> cl ent dOpt on =
        Contexts.local().get(SearchPayloadS zeF lter.CL ENT_ D_CONTEXT_KEY);
     f (cl ent dOpt on. sDef ned()) {
      Atom cReference<Str ng> cl ent dReference = cl ent dOpt on.get();
      Precond  ons.c ckArgu nt(cl ent dReference.get() == null);
      cl ent dReference.set(request.getCl ent d());
    } else {
      CL ENT_ D_CONTEXT_KEY_NOT_SET_COUNTER. ncre nt();
    }

    return serv ce.apply(request);
  }
}
