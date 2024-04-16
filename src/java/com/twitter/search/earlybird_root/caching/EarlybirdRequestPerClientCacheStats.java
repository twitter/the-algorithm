package com.tw ter.search.earlyb rd_root.cach ng;

 mport java.ut l.Map;
 mport java.ut l.concurrent.ConcurrentHashMap;

 mport com.tw ter.search.common.cach ng.f lter.PerCl entCac Stats;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.earlyb rd.common.Earlyb rdRequestUt l;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;

publ c class Earlyb rdRequestPerCl entCac Stats
    extends PerCl entCac Stats<Earlyb rdRequestContext> {

  pr vate Str ng cac OffByCl entStatFormat;
  pr vate f nal Map<Str ng, SearchRateCounter> cac TurnedOffByCl ent;

  pr vate Str ng cac H sByCl entStatFormat;
  pr vate f nal Map<Str ng, SearchRateCounter> cac H sByCl ent;

  publ c Earlyb rdRequestPerCl entCac Stats(Str ng cac RequestType) {
    t .cac OffByCl entStatFormat =
        cac RequestType + "_cl ent_ d_%s_cac _turned_off_ n_request";
    t .cac TurnedOffByCl ent = new ConcurrentHashMap<>();

    t .cac H sByCl entStatFormat = cac RequestType + "_cl ent_ d_%s_cac _h _total";
    t .cac H sByCl ent = new ConcurrentHashMap<>();
  }

  @Overr de
  publ c vo d recordRequest(Earlyb rdRequestContext requestContext) {
     f (!Earlyb rdRequestUt l. sCach ngAllo d(requestContext.getRequest())) {
      Str ng cl ent = requestContext.getRequest().getCl ent d();
      SearchRateCounter counter = cac TurnedOffByCl ent.compute fAbsent(cl ent,
          cl -> SearchRateCounter.export(Str ng.format(cac OffByCl entStatFormat, cl)));
      counter. ncre nt();
    }
  }

  @Overr de
  publ c vo d recordCac H (Earlyb rdRequestContext requestContext) {
    Str ng cl ent = requestContext.getRequest().getCl ent d();
    SearchRateCounter counter = cac H sByCl ent.compute fAbsent(cl ent,
        cl -> SearchRateCounter.export(Str ng.format(cac H sByCl entStatFormat, cl)));
    counter. ncre nt();
  }
}
