package com.tw ter.search.earlyb rd_root.f lters;

 mport java.ut l.concurrent.T  Un ;
 mport javax. nject. nject;

 mport scala.Opt on;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.cac .Cac Bu lder;
 mport com.google.common.cac .Cac Loader;
 mport com.google.common.cac .Load ngCac ;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.f nagle.context.Contexts$;
 mport com.tw ter.f nagle.context.Deadl ne;
 mport com.tw ter.f nagle.context.Deadl ne$;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchT  rStats;
 mport com.tw ter.search.earlyb rd.common.Cl ent dUt l;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.ut l.Future;

/**
 * A f lter for compar ng t  request deadl ne (set  n t  f nagle request context) w h t  request
 * t  out, as set  n t  Earlyb rdRequest.
 *
 * Tracks stats per cl ent, for (1) requests w re t  request deadl ne  s set to exp re before t 
 * Earlyb rdRequest t  out, and also (2) requests w re t  deadl ne allows enough t   for t 
 * Earlyb rdRequest t  out to k ck  n.
 */
publ c class Deadl neT  outStatsF lter
    extends S mpleF lter<Earlyb rdRequestContext, Earlyb rdResponse> {

  // All stats maps below are per cl ent  d, keyed by t  cl ent  d.
  pr vate f nal Load ngCac <Str ng, SearchCounter> requestT  outNotSetStats;
  pr vate f nal Load ngCac <Str ng, SearchCounter> f nagleDeadl neNotSetStats;
  pr vate f nal Load ngCac <Str ng, SearchCounter> f nagleDeadl neAndRequestT  outNotSetStats;
  pr vate f nal Load ngCac <Str ng, SearchT  rStats> requestT  outStats;
  pr vate f nal Load ngCac <Str ng, SearchT  rStats> f nagleDeadl neStats;
  pr vate f nal Load ngCac <Str ng, SearchT  rStats> deadl neLargerStats;
  pr vate f nal Load ngCac <Str ng, SearchT  rStats> deadl neSmallerStats;

  @ nject
  publ c Deadl neT  outStatsF lter(Clock clock) {
    t .requestT  outNotSetStats = Cac Bu lder.newBu lder().bu ld(
        new Cac Loader<Str ng, SearchCounter>() {
          publ c SearchCounter load(Str ng cl ent d) {
            return SearchCounter.export(
                "deadl ne_for_cl ent_ d_" + cl ent d + "_request_t  out_not_set");
          }
        });
    t .f nagleDeadl neNotSetStats = Cac Bu lder.newBu lder().bu ld(
        new Cac Loader<Str ng, SearchCounter>() {
          publ c SearchCounter load(Str ng cl ent d) {
            return SearchCounter.export(
                "deadl ne_for_cl ent_ d_" + cl ent d + "_f nagle_deadl ne_not_set");
          }
        });
    t .f nagleDeadl neAndRequestT  outNotSetStats = Cac Bu lder.newBu lder().bu ld(
        new Cac Loader<Str ng, SearchCounter>() {
          publ c SearchCounter load(Str ng cl ent d) {
            return SearchCounter.export(
                "deadl ne_for_cl ent_ d_" + cl ent d
                    + "_f nagle_deadl ne_and_request_t  out_not_set");
          }
        });
    t .requestT  outStats = Cac Bu lder.newBu lder().bu ld(
        new Cac Loader<Str ng, SearchT  rStats>() {
          publ c SearchT  rStats load(Str ng cl ent d) {
            return SearchT  rStats.export(
                "deadl ne_for_cl ent_ d_" + cl ent d + "_request_t  out",
                T  Un .M LL SECONDS,
                false,
                true,
                clock);
          }
        });
    t .f nagleDeadl neStats = Cac Bu lder.newBu lder().bu ld(
        new Cac Loader<Str ng, SearchT  rStats>() {
          publ c SearchT  rStats load(Str ng cl ent d) {
            return SearchT  rStats.export(
                "deadl ne_for_cl ent_ d_" + cl ent d + "_f nagle_deadl ne",
                T  Un .M LL SECONDS,
                false,
                true,
                clock);
          }
        });
    t .deadl neLargerStats = Cac Bu lder.newBu lder().bu ld(
        new Cac Loader<Str ng, SearchT  rStats>() {
          publ c SearchT  rStats load(Str ng cl ent d) {
            return SearchT  rStats.export(
                "deadl ne_for_cl ent_ d_" + cl ent d
                    + "_f nagle_deadl ne_larger_than_request_t  out",
                T  Un .M LL SECONDS,
                false,
                true,
                clock
            );
          }
        });
    t .deadl neSmallerStats = Cac Bu lder.newBu lder().bu ld(
        new Cac Loader<Str ng, SearchT  rStats>() {
          publ c SearchT  rStats load(Str ng cl ent d) {
            return SearchT  rStats.export(
                "deadl ne_for_cl ent_ d_" + cl ent d
                    + "_f nagle_deadl ne_smaller_than_request_t  out",
                T  Un .M LL SECONDS,
                false,
                true,
                clock
            );
          }
        });
  }

  @Overr de
  publ c Future<Earlyb rdResponse> apply(
      Earlyb rdRequestContext requestContext,
      Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> serv ce) {

    Earlyb rdRequest request = requestContext.getRequest();
    Str ng cl ent d = Cl ent dUt l.getCl ent dFromRequest(request);
    long requestT  outM ll s = getRequestT  out(request);
    Opt on<Deadl ne> deadl ne = Contexts$.MODULE$.broadcast().get(Deadl ne$.MODULE$);

    // Track ng per-cl ent t  outs spec f ed  n t  Earlyb rdRequest.
     f (requestT  outM ll s > 0) {
      requestT  outStats.getUnc cked(cl ent d).t  r ncre nt(requestT  outM ll s);
    } else {
      requestT  outNotSetStats.getUnc cked(cl ent d). ncre nt();
    }

    // How much t   does t  request have, from  s deadl ne start, to t  effect ve deadl ne.
     f (deadl ne. sDef ned()) {
      long deadl neEndT  M ll s = deadl ne.get().deadl ne(). nM ll s();
      long deadl neStartT  M ll s = deadl ne.get().t  stamp(). nM ll s();
      long f nagleDeadl neT  M ll s = deadl neEndT  M ll s - deadl neStartT  M ll s;
      f nagleDeadl neStats.getUnc cked(cl ent d).t  r ncre nt(f nagleDeadl neT  M ll s);
    } else {
      f nagleDeadl neNotSetStats.getUnc cked(cl ent d). ncre nt();
    }

    // Expl c ly track w n both are not set.
     f (requestT  outM ll s <= 0 && deadl ne. sEmpty()) {
      f nagleDeadl neAndRequestT  outNotSetStats.getUnc cked(cl ent d). ncre nt();
    }

    //  f both t  out and t  deadl ne are set, track how much over / under   are, w n
    // compar ng t  deadl ne, and t  Earlyb rdRequest t  out.
     f (requestT  outM ll s > 0 && deadl ne. sDef ned()) {
      long deadl neEndT  M ll s = deadl ne.get().deadl ne(). nM ll s();
      Precond  ons.c ckState(request. sSetCl entRequestT  Ms(),
          "Expect Cl entRequestT  F lter to always set t  cl entRequestT  Ms f eld. Request: %s",
          request);
      long requestStartT  M ll s = request.getCl entRequestT  Ms();
      long requestEndT  M ll s = requestStartT  M ll s + requestT  outM ll s;

      long deadl neD ffM ll s = deadl neEndT  M ll s - requestEndT  M ll s;
       f (deadl neD ffM ll s >= 0) {
        deadl neLargerStats.getUnc cked(cl ent d).t  r ncre nt(deadl neD ffM ll s);
      } else {
        // Track "deadl ne  s smaller" as pos  ve values.
        deadl neSmallerStats.getUnc cked(cl ent d).t  r ncre nt(-deadl neD ffM ll s);
      }
    }

    return serv ce.apply(requestContext);
  }

  pr vate long getRequestT  out(Earlyb rdRequest request) {
     f (request. sSetSearchQuery()
        && request.getSearchQuery(). sSetCollectorParams()
        && request.getSearchQuery().getCollectorParams(). sSetTerm nat onParams()
        && request.getSearchQuery().getCollectorParams().getTerm nat onParams(). sSetT  outMs()) {

      return request.getSearchQuery().getCollectorParams().getTerm nat onParams().getT  outMs();
    } else  f (request. sSetT  outMs()) {
      return request.getT  outMs();
    } else {
      return -1;
    }
  }
}
