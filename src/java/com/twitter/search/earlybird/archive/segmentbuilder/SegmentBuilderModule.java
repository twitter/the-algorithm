package com.tw ter.search.earlyb rd.arch ve.seg ntbu lder;

 mport java. o.F le;

 mport com.google. nject.Prov des;
 mport com.google. nject.S ngleton;

 mport com.tw ter.app.Flaggable;
 mport com.tw ter.dec der.Dec der;
 mport com.tw ter. nject.Tw terModule;
 mport com.tw ter. nject.annotat ons.Flag;
 mport com.tw ter.search.common.conf g.LoggerConf gurat on;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.ut l.Earlyb rdDec der;

publ c class Seg ntBu lderModule extends Tw terModule {

  pr vate stat c f nal Str ng CONF G_F LE_FLAG_NAME = "conf g_f le";
  pr vate stat c f nal Str ng SEGMENT_LOG_D R_FLAG_NAME = "seg nt_log_d r";

  publ c Seg ntBu lderModule() {
    createFlag(CONF G_F LE_FLAG_NAME,
            new F le("earlyb rd-search.yml"),
            "spec fy conf g f le",
            Flaggable.ofF le());

    createFlag(SEGMENT_LOG_D R_FLAG_NAME,
            "",
            "overr de log d r from conf g f le",
            Flaggable.ofStr ng());
  }

  /**
   *  n  al zes t  Earlyb rd conf g and t  log conf gurat on, and returns an Earlyb rdDec der
   * object, wh ch w ll be  njected  nto t  Seg ntBu lder  nstance.
   *
   * @param conf gF le T  conf g f le to use to  n  al ze Earlyb rdConf g
   * @param seg ntLogD r  f not empty, used to overr de t  log d rectory from t  conf g f le
   * @return An  n  al zed Earlyb rdDec der
   */
  @Prov des
  @S ngleton
  publ c Dec der prov deDec der(@Flag(CONF G_F LE_FLAG_NAME) F le conf gF le,
                                @Flag(SEGMENT_LOG_D R_FLAG_NAME) Str ng seg ntLogD r) {
    // By default Gu ce w ll bu ld s ngletons eagerly:
    //    https://g hub.com/google/gu ce/w k /Scopes#eager-s ngletons
    // So  n order to ensure that t  Earlyb rdConf g and LoggerConf gurat on  n  al zat ons occur
    // before t  Earlyb rdDec der  n  al zat on,   place t m  re.
    Earlyb rdConf g. n (conf gF le.getNa ());
     f (!seg ntLogD r. sEmpty()) {
      Earlyb rdConf g.overr deLogD r(seg ntLogD r);
    }
    new LoggerConf gurat on(Earlyb rdConf g.getLogPropert esF le(), Earlyb rdConf g.getLogD r())
            .conf gure();

    return Earlyb rdDec der. n  al ze();
  }
}
