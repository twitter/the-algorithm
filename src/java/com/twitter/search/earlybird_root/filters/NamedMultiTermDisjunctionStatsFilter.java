package com.tw ter.search.earlyb rd_root.f lters;

 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.concurrent.ConcurrentHashMap;
 mport java.ut l.concurrent.ConcurrentMap;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.search.common. tr cs.Percent le;
 mport com.tw ter.search.common. tr cs.Percent leUt l;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.ut l.Future;

publ c class Na dMult TermD sjunct onStatsF lter extends
    S mpleF lter<Earlyb rdRequest, Earlyb rdResponse> {

    pr vate stat c f nal Str ng STAT_FORMAT = "na d_d sjunct on_s ze_cl ent_%s_key_%s";
    // Cl ent D -> d sjunct on na  -> operand count
    pr vate stat c f nal ConcurrentMap<Str ng, ConcurrentMap<Str ng, Percent le< nteger>>>
        NAMED_MULT _TERM_D SJUNCT ON_ DS_COUNT = new ConcurrentHashMap<>();

    @Overr de
    publ c Future<Earlyb rdResponse> apply(Earlyb rdRequest request,
        Serv ce<Earlyb rdRequest, Earlyb rdResponse> serv ce) {

         f (request.getSearchQuery(). sSetNa dD sjunct onMap()) {
            for (Map.Entry<Str ng, L st<Long>> entry
                : request.getSearchQuery().getNa dD sjunct onMap().entrySet()) {

                Map<Str ng, Percent le< nteger>> statsForCl ent =
                    NAMED_MULT _TERM_D SJUNCT ON_ DS_COUNT.compute fAbsent(
                        request.getCl ent d(), cl ent d -> new ConcurrentHashMap<>());
                Percent le< nteger> stats = statsForCl ent.compute fAbsent(entry.getKey(),
                    keyNa  -> Percent leUt l.createPercent le(
                        Str ng.format(STAT_FORMAT, request.getCl ent d(), keyNa )));

                stats.record(entry.getValue().s ze());
            }
        }

        return serv ce.apply(request);
    }
}
