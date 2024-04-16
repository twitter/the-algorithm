package com.tw ter.search.earlyb rd_root.f lters;

 mport javax. nject. nject;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.ut l.Future;

publ c class VeryRecentT etsF lter
    extends S mpleF lter<Earlyb rdRequest, Earlyb rdResponse> {
  pr vate stat c f nal Str ng DEC DER_KEY = "enable_very_recent_t ets";
  pr vate stat c f nal SearchRateCounter VERY_RECENT_TWEETS_NOT_MOD F ED =
      SearchRateCounter.export("very_recent_t ets_not_mod f ed");
  pr vate stat c f nal SearchRateCounter VERY_RECENT_TWEETS_ENABLED =
      SearchRateCounter.export("very_recent_t ets_enabled");

  pr vate f nal SearchDec der dec der;

  @ nject
  publ c VeryRecentT etsF lter(
      SearchDec der dec der
  ) {
    t .dec der = dec der;
  }

  @Overr de
  publ c Future<Earlyb rdResponse> apply(
      Earlyb rdRequest request,
      Serv ce<Earlyb rdRequest, Earlyb rdResponse> serv ce
  ) {
     f (dec der. sAva lable(DEC DER_KEY)) {
      VERY_RECENT_TWEETS_ENABLED. ncre nt();
      request.setSk pVeryRecentT ets(false);
    } else {
      VERY_RECENT_TWEETS_NOT_MOD F ED. ncre nt();
    }

    return serv ce.apply(request);
  }
}
