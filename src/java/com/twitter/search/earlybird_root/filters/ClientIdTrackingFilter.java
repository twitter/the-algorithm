package com.tw ter.search.earlyb rd_root.f lters;

 mport java.ut l.concurrent.ConcurrentHashMap;
 mport java.ut l.concurrent.ConcurrentMap;

 mport javax. nject. nject;

 mport com.google.common.annotat ons.V s bleForTest ng;

 mport com.tw ter.common.collect ons.Pa r;
 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.search.common.cl entstats.RequestCounters;
 mport com.tw ter.search.common.cl entstats.RequestCountersEventL stener;
 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.ut l.F nagleUt l;
 mport com.tw ter.search.common.ut l.earlyb rd.Thr ftSearchQueryUt l;
 mport com.tw ter.search.earlyb rd.common.Cl ent dUt l;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.ut l.Future;

/** Tracks t  number of quer es   get from each cl ent. */
publ c class Cl ent dTrack ngF lter extends S mpleF lter<Earlyb rdRequest, Earlyb rdResponse> {
  // Be careful w n chang ng t  na s of t se stats or add ng new ones: make sure that t y have
  // pref xes/suff xes that w ll allow us to group t m  n V z, w hout pull ng  n ot r stats.
  // For example,  'll probably have a V z graph for cl ent_ d_tracker_qps_for_cl ent_ d_*_all.
  // So  f   add a new stat na d cl ent_ d_tracker_qps_for_cl ent_ d_%s_and_new_f eld_%s_all,
  // t n t  graph w ll be group ng up t  values from both stats,  nstead of group ng up t 
  // values only for cl ent_ d_tracker_qps_for_cl ent_ d_%s_all.
  @V s bleForTest ng
  stat c f nal Str ng QPS_ALL_STAT_PATTERN = "cl ent_ d_tracker_qps_for_%s_all";

  @V s bleForTest ng
  stat c f nal Str ng QPS_LOGGED_ N_STAT_PATTERN = "cl ent_ d_tracker_qps_for_%s_logged_ n";

  @V s bleForTest ng
  stat c f nal Str ng QPS_LOGGED_OUT_STAT_PATTERN = "cl ent_ d_tracker_qps_for_%s_logged_out";

  stat c f nal Str ng SUPERROOT_REJECT_REQUESTS_W TH_UNKNOWN_F NAGLE_ D =
      "superroot_reject_requests_w h_unknown_f nagle_ d";

  stat c f nal Str ng UNKNOWN_F NAGLE_ D_DEBUG_STR NG = "Please spec fy a f nagle cl ent  d.";

  pr vate f nal ConcurrentMap<Str ng, RequestCounters> requestCountersByCl ent d =
    new ConcurrentHashMap<>();
  pr vate f nal ConcurrentMap<Pa r<Str ng, Str ng>, RequestCounters>
      requestCountersByF nagle dAndCl ent d = new ConcurrentHashMap<>();
  pr vate f nal Clock clock;
  pr vate f nal SearchDec der dec der;

  @ nject
  publ c Cl ent dTrack ngF lter(SearchDec der dec der) {
    t (dec der, Clock.SYSTEM_CLOCK);
  }

  @V s bleForTest ng
  Cl ent dTrack ngF lter(SearchDec der dec der, Clock clock) {
    t .dec der = dec der;
    t .clock = clock;
  }

  @Overr de
  publ c Future<Earlyb rdResponse> apply(Earlyb rdRequest request,
                                         Serv ce<Earlyb rdRequest, Earlyb rdResponse> serv ce) {
    Str ng cl ent d = Cl ent dUt l.getCl ent dFromRequest(request);
    Str ng f nagle d = F nagleUt l.getF nagleCl entNa ();
    boolean  sLogged n = Thr ftSearchQueryUt l.request n  atedByLogged nUser(request);
     ncre ntCounters(cl ent d, f nagle d,  sLogged n);

     f (dec der. sAva lable(SUPERROOT_REJECT_REQUESTS_W TH_UNKNOWN_F NAGLE_ D)
        && f nagle d.equals(F nagleUt l.UNKNOWN_CL ENT_NAME)) {
      Earlyb rdResponse response = new Earlyb rdResponse(
          Earlyb rdResponseCode.QUOTA_EXCEEDED_ERROR, 0)
          .setDebugStr ng(UNKNOWN_F NAGLE_ D_DEBUG_STR NG);
      return Future.value(response);
    }

    RequestCounters cl entCounters = getCl entCounters(cl ent d);
    RequestCountersEventL stener<Earlyb rdResponse> cl entCountersEventL stener =
        new RequestCountersEventL stener<>(
            cl entCounters, clock, Earlyb rdSuccessfulResponseHandler. NSTANCE);
    RequestCounters f nagle dAndCl entCounters = getF nagle dCl entCounters(cl ent d, f nagle d);
    RequestCountersEventL stener<Earlyb rdResponse> f nagle dAndCl entCountersEventL stener =
        new RequestCountersEventL stener<>(
            f nagle dAndCl entCounters, clock, Earlyb rdSuccessfulResponseHandler. NSTANCE);

    return serv ce.apply(request)
        .addEventL stener(cl entCountersEventL stener)
        .addEventL stener(f nagle dAndCl entCountersEventL stener);
  }

  // Returns t  RequestCounters  nstance track ng t  requests from t  g ven cl ent  D.
  pr vate RequestCounters getCl entCounters(Str ng cl ent d) {
    RequestCounters cl entCounters = requestCountersByCl ent d.get(cl ent d);
     f (cl entCounters == null) {
      cl entCounters = new RequestCounters(Cl ent dUt l.formatCl ent d(cl ent d));
      RequestCounters ex st ngCounters =
        requestCountersByCl ent d.put fAbsent(cl ent d, cl entCounters);
       f (ex st ngCounters != null) {
        cl entCounters = ex st ngCounters;
      }
    }
    return cl entCounters;
  }

  // Returns t  RequestCounters  nstance track ng t  requests from t  g ven cl ent  D.
  pr vate RequestCounters getF nagle dCl entCounters(Str ng cl ent d, Str ng f nagle d) {
    Pa r<Str ng, Str ng> cl entKey = Pa r.of(cl ent d, f nagle d);
    RequestCounters counters = requestCountersByF nagle dAndCl ent d.get(cl entKey);
     f (counters == null) {
      counters = new RequestCounters(Cl ent dUt l.formatF nagleCl ent dAndCl ent d(
          f nagle d, cl ent d));
      RequestCounters ex st ngCounters = requestCountersByF nagle dAndCl ent d.put fAbsent(
          cl entKey, counters);
       f (ex st ngCounters != null) {
        counters = ex st ngCounters;
      }
    }
    return counters;
  }

  //  ncre nts t  correct counters, based on t  g ven cl ent d, f nagle d, and w t r or not t 
  // request ca  from a logged  n user.
  pr vate stat c vo d  ncre ntCounters(Str ng cl ent d, Str ng f nagle d, boolean  sLogged n) {
    Str ng cl ent dForStats = Cl ent dUt l.formatCl ent d(cl ent d);
    Str ng f nagleCl ent dAndCl ent dForStats =
      Cl ent dUt l.formatF nagleCl ent dAndCl ent d(f nagle d, cl ent d);
    SearchCounter.export(Str ng.format(QPS_ALL_STAT_PATTERN, cl ent dForStats)). ncre nt();
    SearchCounter.export(Str ng.format(QPS_ALL_STAT_PATTERN, f nagleCl ent dAndCl ent dForStats))
      . ncre nt();
     f ( sLogged n) {
      SearchCounter.export(Str ng.format(QPS_LOGGED_ N_STAT_PATTERN, cl ent dForStats)). ncre nt();
      SearchCounter.export(
          Str ng.format(QPS_LOGGED_ N_STAT_PATTERN, f nagleCl ent dAndCl ent dForStats))
        . ncre nt();
    } else {
      SearchCounter.export(Str ng.format(QPS_LOGGED_OUT_STAT_PATTERN, cl ent dForStats))
        . ncre nt();
      SearchCounter.export(
          Str ng.format(QPS_LOGGED_OUT_STAT_PATTERN, f nagleCl ent dAndCl ent dForStats))
        . ncre nt();
    }
  }
}
