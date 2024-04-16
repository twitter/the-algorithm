package com.tw ter.search.earlyb rd.part  on;

 mport java. o. OExcept on;
 mport java.ut l.Collect ons;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.concurrent.T  Un ;
 mport javax.annotat on.Nullable;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect. mmutableL st;
 mport com.google.common.collect. mmutableMap;
 mport com.google.common.collect.L sts;
 mport com.google.common.collect.Maps;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.dec der.Dec der;
 mport com.tw ter.search.common.dec der.Dec derUt l;
 mport com.tw ter.search.common. tr cs.SearchLongGauge;
 mport com.tw ter.search.common. tr cs.SearchStatsRece ver;
 mport com.tw ter.search.common. tr cs.SearchT  rStats;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted. nverted ndex;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted.Mult Seg ntTermD ct onary;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted.Mult Seg ntTermD ct onaryW hFastut l;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted.Opt m zed mory ndex;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd. ndex.Earlyb rdSeg nt;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntManager.F lter;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntManager.Order;

/**
 * Manages Mult Seg ntTermD ct onary's for spec f c f elds on t  earlyb rd. Only manages t m
 * for opt m zed seg nts, and should only regenerate new d ct onar es w n t  l st of opt m zed
 * seg nts changes. See SEARCH-10836
 */
publ c class Mult Seg ntTermD ct onaryManager {
  pr vate stat c f nal Logger LOG =
      LoggerFactory.getLogger(Mult Seg ntTermD ct onaryManager.class);

  @V s bleForTest ng
  publ c stat c f nal SearchT  rStats TERM_D CT ONARY_CREAT ON_STATS =
      SearchT  rStats.export("mult _seg nt_term_d ct onary_manager_bu ld_d ct onary",
          T  Un .M LL SECONDS, false);

  publ c stat c f nal Mult Seg ntTermD ct onaryManager NOOP_ NSTANCE =
      new Mult Seg ntTermD ct onaryManager(
          new Conf g(Collect ons.emptyL st()), null, null, null, null) {
        @Overr de
        publ c boolean bu ldD ct onary() {
          return false;
        }
      };

  pr vate stat c f nal Str ng MANAGER_D SABLED_DEC DER_KEY_PREF X =
      "mult _seg nt_term_d ct onary_manager_d sabled_ n_";

  publ c stat c class Conf g {
    pr vate f nal  mmutableL st<Str ng> f eldNa s;

    publ c Conf g(L st<Str ng> f eldNa s) {
      Precond  ons.c ckNotNull(f eldNa s);
      t .f eldNa s =  mmutableL st.copyOf(f eldNa s);
    }

    publ c L st<Str ng> managedF eldNa s() {
      return f eldNa s;
    }

    publ c boolean  sEnabled() {
      return Earlyb rdConf g.getBool("mult _seg nt_term_d ct onary_enabled", false);
    }
  }

  @V s bleForTest ng
  publ c stat c Str ng getManagerD sabledDec derNa (Earlyb rdCluster earlyb rdCluster) {
    return MANAGER_D SABLED_DEC DER_KEY_PREF X + earlyb rdCluster.na ().toLo rCase();
  }

  pr vate stat c f nal class F eldStats {
    pr vate f nal SearchT  rStats bu ldT  ;
    pr vate f nal SearchLongGauge numTerms;
    pr vate f nal SearchLongGauge numTermEntr es;

    pr vate F eldStats(SearchStatsRece ver statsRece ver, Str ng f eldNa ) {
      Precond  ons.c ckNotNull(f eldNa );
      Precond  ons.c ckNotNull(statsRece ver);

      Str ng t  rNa  = Str ng.format(
          "mult _seg nt_term_d ct onary_manager_f eld_%s_bu ld_d ct onary", f eldNa );
      t .bu ldT   = statsRece ver.getT  rStats(
          t  rNa , T  Un .M LL SECONDS, false, false, false);

      Str ng numTermsNa  = Str ng.format(
          "mult _seg nt_term_d ct onary_manager_f eld_%s_num_terms", f eldNa );
      t .numTerms = statsRece ver.getLongGauge(numTermsNa );

      Str ng numTermEntr esNa  = Str ng.format(
          "mult _seg nt_term_d ct onary_manager_f eld_%s_num_term_entr es", f eldNa );
      t .numTermEntr es = statsRece ver.getLongGauge(numTermEntr esNa );
    }
  }

  pr vate f nal Conf g conf g;
  @Nullable pr vate f nal Seg ntManager seg ntManager;
  @Nullable pr vate f nal Dec der dec der;
  @Nullable pr vate f nal Earlyb rdCluster earlyb rdCluster;
  pr vate f nal  mmutableMap<Str ng, F eldStats> f eldT  rStats;
  // A per-f eld map of mult -seg nt term d ct onar es. Each key  s a f eld. T  values are t 
  // mult -seg nt term d ct onar es for that f eld.
  pr vate volat le  mmutableMap<Str ng, Mult Seg ntTermD ct onary> mult Seg ntTermD ct onaryMap;
  pr vate L st<Seg nt nfo> prev ousSeg ntsTo rge;

  publ c Mult Seg ntTermD ct onaryManager(
      Conf g conf g,
      Seg ntManager seg ntManager,
      SearchStatsRece ver statsRece ver,
      Dec der dec der,
      Earlyb rdCluster earlyb rdCluster) {
    t .conf g = conf g;
    t .seg ntManager = seg ntManager;
    t .dec der = dec der;
    t .earlyb rdCluster = earlyb rdCluster;

    t .mult Seg ntTermD ct onaryMap =  mmutableMap.of();
    t .prev ousSeg ntsTo rge = L sts.newArrayL st();

     mmutableMap.Bu lder<Str ng, F eldStats> bu lder =  mmutableMap.bu lder();
     f (statsRece ver != null) {
      for (Str ng f eldNa  : conf g.managedF eldNa s()) {
        bu lder.put(f eldNa , new F eldStats(statsRece ver, f eldNa ));
      }
    }
    t .f eldT  rStats = bu lder.bu ld();
  }

  /**
   * Return t  most recently bu lt Mult Seg ntTermD ct onary for t  g ven f eld.
   * W ll return null  f t  f eld  s not supported by t  manager.
   */
  @Nullable
  publ c Mult Seg ntTermD ct onary getMult Seg ntTermD ct onary(Str ng f eldNa ) {
    return t .mult Seg ntTermD ct onaryMap.get(f eldNa );
  }

  /**
   * Bu ld new vers ons of mult -seg nt term d ct onar es  f t  manager  s enabled, and new
   * seg nts are ava lable.
   * @return true  f t  manager actually ran, and generated new vers ons of mult -seg nt term
   * d ct onar es.
   *
   *   synchron ze t   thod because   would be a log c error to mod fy t  var ables from
   * mult ple threads s multaneously, and    s poss ble for two seg nts to f n sh opt m z ng at
   * t  sa  t   and try to run  .
   */
  publ c synchron zed boolean bu ldD ct onary() {
     f (!conf g. sEnabled()) {
      return false;
    }

    Precond  ons.c ckNotNull(dec der);
    Precond  ons.c ckNotNull(earlyb rdCluster);
     f (Dec derUt l. sAva lableForRandomRec p ent(dec der,
        getManagerD sabledDec derNa (earlyb rdCluster))) {
      LOG. nfo("Mult  seg nt term d ct onary manager  s d sabled v a dec der for cluster {}.",
          earlyb rdCluster);
      t .mult Seg ntTermD ct onaryMap =  mmutableMap.of();
      t .prev ousSeg ntsTo rge = L sts.newArrayL st();
      return false;
    }

    L st<Seg nt nfo> seg ntsTo rge = getSeg ntsTo rge();

     f (d fferentFromPrev ousL st(seg ntsTo rge)) {
       long start = System.currentT  M ll s();
       try {
         t .mult Seg ntTermD ct onaryMap = createNewD ct onar es(seg ntsTo rge);
         t .prev ousSeg ntsTo rge = seg ntsTo rge;
         return true;
       } catch ( OExcept on e) {
         LOG.error("Unable to bu ld mult  seg nt term d ct onar es", e);
         return false;
       } f nally {
         long elapsed = System.currentT  M ll s() - start;
         TERM_D CT ONARY_CREAT ON_STATS.t  r ncre nt(elapsed);
       }
    } else {
      LOG.warn("No-op for bu ldD ct onary()");
      return false;
    }
  }

  /**
   * Only  rge terms from enabled and opt m zed seg nts. No need to look at non-enabled seg nts,
   * and   also don't want to use un-opt m zed seg nts as t  r term d ct onar es are st ll
   * chang ng.
   */
  pr vate L st<Seg nt nfo> getSeg ntsTo rge() {
     erable<Seg nt nfo> seg nt nfos =
        seg ntManager.getSeg nt nfos(F lter.Enabled, Order.OLD_TO_NEW);

    L st<Seg nt nfo> seg ntsTo rge = L sts.newArrayL st();
    for (Seg nt nfo seg nt nfo : seg nt nfos) {
       f (seg nt nfo.get ndexSeg nt(). sOpt m zed()) {
        seg ntsTo rge.add(seg nt nfo);
      }
    }
    return seg ntsTo rge;
  }

  pr vate boolean d fferentFromPrev ousL st(L st<Seg nt nfo> seg ntsTo rge) {
    // t re  s a potent ally d fferent approach  re to only c ck  f t 
    // seg ntsTo rge  s subsu d by t  prev ousSeg ntsTo rge l st, and not recompute
    // t  mult  seg nt term d ct onary  f so.
    // T re  s a case w re a new seg nt  s added, t  prev ously current seg nt  s not yet
    // opt m zed, but t  oldest seg nt  s dropped. W h t   mpl,   w ll recompute to remove
    // t  dropped seg nt, ho ver,   w ll recompute soon aga n w n t 
    // "prev ously current seg nt"  s actually opt m zed.   can potent ally delay t  f rst
    //  rg ng before t  opt m zat on.
     f (t .prev ousSeg ntsTo rge.s ze() == seg ntsTo rge.s ze()) {
      for ( nt   = 0;   < t .prev ousSeg ntsTo rge.s ze();  ++) {
         f (prev ousSeg ntsTo rge.get( ).compareTo(seg ntsTo rge.get( )) != 0) {
          return true;
        }
      }
      return false;
    }
    return true;
  }

  /**
   * Rebu ld t  term d ct onar es from scratch for all t  managed f elds.
   * Return ng a brand new map  re w h all t  f elds' term d ct onar es so that   can  solate
   * fa lures to bu ld, and only replace t  ent re map of all t  f elds are bu lt successfully.
   */
  pr vate  mmutableMap<Str ng, Mult Seg ntTermD ct onary> createNewD ct onar es(
      L st<Seg nt nfo> seg nts) throws  OExcept on {

    Map<Str ng, Mult Seg ntTermD ct onary> map = Maps.newHashMap();

    for (Str ng f eld : conf g.managedF eldNa s()) {
      LOG. nfo(" rg ng term d ct onar es for f eld {}", f eld);

      L st<Opt m zed mory ndex>  ndexesTo rge = f ndF eld ndexesTo rge(seg nts, f eld);

       f ( ndexesTo rge. sEmpty()) {
        LOG. nfo("No  ndexes to  rge for f eld {}", f eld);
      } else {
        long start = System.currentT  M ll s();

        Mult Seg ntTermD ct onary mult Seg ntTermD ct onary =
             rgeD ct onar es(f eld,  ndexesTo rge);

        map.put(f eld, mult Seg ntTermD ct onary);

        long elapsed = System.currentT  M ll s() - start;
        LOG. nfo("Done  rg ng term d ct onary for f eld {}, for {} seg nts  n {}ms",
            f eld,  ndexesTo rge.s ze(), elapsed);

        F eldStats f eldStats = f eldT  rStats.get(f eld);
        f eldStats.bu ldT  .t  r ncre nt(elapsed);
        f eldStats.numTerms.set(mult Seg ntTermD ct onary.getNumTerms());
        f eldStats.numTermEntr es.set(mult Seg ntTermD ct onary.getNumTermEntr es());
      }
    }
    return  mmutableMap.copyOf(map);
  }

  pr vate L st<Opt m zed mory ndex> f ndF eld ndexesTo rge(
      L st<Seg nt nfo> seg nts, Str ng f eld) throws  OExcept on {

    L st<Opt m zed mory ndex>  ndexesTo rge = L sts.newArrayL st();

    for (Seg nt nfo seg nt : seg nts) {
      Earlyb rdSeg nt  ndexSeg nt = seg nt.get ndexSeg nt();
      Precond  ons.c ckState( ndexSeg nt. sOpt m zed(),
          "Expect seg nt to be opt m zed: %s", seg nt);

       nverted ndex f eld ndex = Precond  ons.c ckNotNull( ndexSeg nt.get ndexReader())
          .getSeg ntData().getF eld ndex(f eld);

      // See SEARCH-11952
      //   w ll only have a  nverted ndex/Opt m zed mory ndex  re
      //  n t   n- mory non-lucene-based  ndexes, and not  n t  arch ve.   can so what
      // reasonably extend t  to work w h t  arch ve by mak ng t  d ct onar es work w h
      // TermsEnum's d rectly  nstead of Opt m zed mory ndex's. Leav ng t  as a furt r
      // extens on for now.
       f (f eld ndex != null) {
         f (f eld ndex  nstanceof Opt m zed mory ndex) {
           ndexesTo rge.add((Opt m zed mory ndex) f eld ndex);
        } else {
          LOG. nfo("Found f eld  ndex for f eld {}  n seg nt {} of type {}",
              f eld, seg nt, f eld ndex.getClass());
        }
      } else {
        LOG. nfo("Found null f eld  ndex for f eld {}  n seg nt {}", f eld, seg nt);
      }
    }
    LOG. nfo("Found good f elds for {} out of {} seg nts",  ndexesTo rge.s ze(),
            seg nts.s ze());

    return  ndexesTo rge;
  }

  pr vate Mult Seg ntTermD ct onary  rgeD ct onar es(
      Str ng f eld,
      L st<Opt m zed mory ndex>  ndexes) {
    // May change t   f   get a better  mple ntat on  n t  future.
    return new Mult Seg ntTermD ct onaryW hFastut l(f eld,  ndexes);
  }
}
