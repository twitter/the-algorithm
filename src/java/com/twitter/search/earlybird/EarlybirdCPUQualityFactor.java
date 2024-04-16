package com.tw ter.search.earlyb rd;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.sun.manage nt.Operat ngSystemMXBean;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.dec der.Dec der;
 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.common. tr cs.SearchStatsRece ver;

/**
 * Manages t  qual y factor for an Earlyb rd based on CPU usage.
 */
publ c class Earlyb rdCPUQual yFactor  mple nts Qual yFactor {
  publ c stat c f nal Str ng ENABLE_QUAL TY_FACTOR_DEC DER = "enable_qual y_factor";
  publ c stat c f nal Str ng OVERR DE_QUAL TY_FACTOR_DEC DER = "overr de_qual y_factor";

  @V s bleForTest ng
  protected stat c f nal double CPU_USAGE_THRESHOLD = 0.8;
  @V s bleForTest ng
  protected stat c f nal double MAX_QF_ NCREMENT = 0.5;
  @V s bleForTest ng
  protected stat c f nal double MAX_QF_DECREMENT = 0.1;
  @V s bleForTest ng
  protected stat c f nal double MAX_CPU_USAGE = 1.0;

  pr vate stat c f nal Logger QUAL TY_FACTOR_LOG =
      LoggerFactory.getLogger(Earlyb rdCPUQual yFactor.class);
  pr vate stat c f nal Logger EARLYB RD_LOG = LoggerFactory.getLogger(Earlyb rd.class);

  /**
   * Tracks t  real, underly ng CPU QF value, regardless of t  dec der enabl ng
   *  .
   */
  @V s bleForTest ng
  protected stat c f nal Str ng UNDERLY NG_CPU_QF_GUAGE = "underly ng_cpu_qual y_factor";

  /**
   * Reports t  QF actually used to degrade Earlyb rds.
   */
  @V s bleForTest ng
  protected stat c f nal Str ng CPU_QF_GUAGE = "cpu_qual y_factor";

  pr vate stat c f nal  nt SAMPL NG_W NDOW_M LL S = 60 * 1000;   // one m nute


  pr vate double qual yFactor = 1;
  pr vate double prev ousQual yFactor = 1;

  pr vate f nal SearchDec der dec der;
  pr vate f nal Operat ngSystemMXBean operat ngSystemMXBean;

  publ c Earlyb rdCPUQual yFactor(
      Dec der dec der,
      Operat ngSystemMXBean operat ngSystemMXBean,
      SearchStatsRece ver searchStatsRece ver) {
    t .dec der = new SearchDec der(dec der);
    t .operat ngSystemMXBean = operat ngSystemMXBean;

    searchStatsRece ver.getCustomGauge(UNDERLY NG_CPU_QF_GUAGE, () -> qual yFactor);
    searchStatsRece ver.getCustomGauge(CPU_QF_GUAGE, t ::get);
  }

  /**
   * Updates t  current qual y factor based on CPU usage.
   */
  @V s bleForTest ng
  protected vo d update() {
    prev ousQual yFactor = qual yFactor;

    double cpuUsage = operat ngSystemMXBean.getSystemCpuLoad();

     f (cpuUsage < CPU_USAGE_THRESHOLD) {
      double  ncre nt =
          ((CPU_USAGE_THRESHOLD - cpuUsage) / CPU_USAGE_THRESHOLD) * MAX_QF_ NCREMENT;
      qual yFactor = Math.m n(1, qual yFactor +  ncre nt);
    } else {
      double decre nt =
          ((cpuUsage - CPU_USAGE_THRESHOLD) / (MAX_CPU_USAGE - CPU_USAGE_THRESHOLD))
              * MAX_QF_DECREMENT;
      qual yFactor = Math.max(0, qual yFactor - decre nt);
    }

     f (!qual yFactorChanged()) {
      return;
    }

    QUAL TY_FACTOR_LOG. nfo(
        Str ng.format("CPU: %.2f Qual y Factor: %.2f", cpuUsage, qual yFactor));

     f (!enabled()) {
      return;
    }

     f (degradat onBegan()) {
      EARLYB RD_LOG. nfo("Serv ce degradat on began.");
    }

     f (degradat onEnded()) {
      EARLYB RD_LOG. nfo("Serv ce degradat on ended.");
    }
  }

  @Overr de
  publ c double get() {
     f (!enabled()) {
      return 1;
    }

     f ( sOverr dden()) {
      return overr de();
    }

    return qual yFactor;
  }

  @Overr de
  publ c vo d startUpdates() {
    new Thread(() -> {
      wh le (true) {
        update();
        try {
          Thread.sleep(SAMPL NG_W NDOW_M LL S);
        } catch ( nterruptedExcept on e) {
          QUAL TY_FACTOR_LOG.warn(
              "Qual y factor ng thread  nterrupted dur ng sleep bet en updates", e);
        }
      }
    }).start();
  }

  /**
   * Returns true  f qual y factor ng  s enabled by t  dec der.
   * @return
   */
  pr vate boolean enabled() {
    return dec der != null && dec der. sAva lable(ENABLE_QUAL TY_FACTOR_DEC DER);
  }

  /**
   * Returns true  f a dec der has overr dden t  qual y factor.
   * @return
   */
  pr vate boolean  sOverr dden() {
    return dec der != null && dec der.getAva lab l y(OVERR DE_QUAL TY_FACTOR_DEC DER) < 10000.0;
  }

  /**
   * Returns t  overr de dec der value.
   * @return
   */
  pr vate double overr de() {
    return dec der == null ? 1 : dec der.getAva lab l y(OVERR DE_QUAL TY_FACTOR_DEC DER) / 10000.0;
  }

  /**
   * Returns true  f t  qual y factor has changed s nce t  last update.
   * @return
   */
  pr vate boolean qual yFactorChanged() {
    return Math.abs(qual yFactor - prev ousQual yFactor) > 0.01;
  }

  /**
   * Returns true  f  've entered a degraded state.
   * @return
   */
  pr vate boolean degradat onBegan() {
    return Math.abs(prev ousQual yFactor - 1.0) < 0.01 && qual yFactor < prev ousQual yFactor;
  }

  /**
   * Returns true  f  've left t  degraded state.
   * @return
   */
  pr vate boolean degradat onEnded() {
    return Math.abs(qual yFactor - 1.0) < 0.01 && prev ousQual yFactor < qual yFactor;
  }
}
