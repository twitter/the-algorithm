package com.tw ter.search.earlyb rd.part  on;

 mport java. o. OExcept on;

 mport com.google.common.base.Precond  ons;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.aurora.Aurora nstanceKey;
 mport com.tw ter.search.common.aurora.AuroraSc dulerCl ent;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.factory.Part  onConf gUt l;

publ c f nal class Part  onConf gLoader {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Part  onConf gLoader.class);

  pr vate Part  onConf gLoader() {
    // t  never gets called
  }

  /**
   * Load part  on  nformat on from t  command l ne argu nts and Aurora sc duler.
   *
   * @return T  new Part  onConf g object for t  host
   */
  publ c stat c Part  onConf g getPart  on nfoFor sosConf g(
      AuroraSc dulerCl ent sc dulerCl ent) throws Part  onConf gLoad ngExcept on {
    Aurora nstanceKey  nstanceKey =
        Precond  ons.c ckNotNull(Earlyb rdConf g.getAurora nstanceKey());
     nt numTasks;

    try {
      numTasks = sc dulerCl ent.getAct veTasks(
           nstanceKey.getRole(),  nstanceKey.getEnv(),  nstanceKey.getJobNa ()).s ze();
      LOG. nfo("Found {} act ve tasks", numTasks);
    } catch ( OExcept on e) {
      // T  can happen w n Aurora Sc duler  s hold ng a conclave to elect a new reader.
      LOG.warn("Fa led to get tasks from Aurora sc duler.", e);
      throw new Part  onConf gLoad ngExcept on("Fa led to get tasks from Aurora sc duler.");
    }

    return Part  onConf gUt l. n Part  onConf gForAurora(numTasks);
  }
}
