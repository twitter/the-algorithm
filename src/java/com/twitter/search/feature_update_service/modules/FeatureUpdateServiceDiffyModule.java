package com.tw ter.search.feature_update_serv ce.modules;

 mport com.tw ter.dec der.Dec der;
 mport com.tw ter. nject. njector;
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsJavaDarkTraff cF lterModule;
 mport com.tw ter.search.common.dec der.Dec derUt l;
 mport com.tw ter.ut l.Funct on;


/**
 * Prov de a f lter that sends dark traff c to d ffy,  f t  d ffy.dest command-l ne para ter
 *  s non-empty.  f d ffy.dest  s empty, just prov de a no-op f lter.
 */
publ c class FeatureUpdateServ ceD ffyModule extends MtlsJavaDarkTraff cF lterModule {
  @Overr de
  publ c Str ng destFlagNa () {
    return "d ffy.dest";
  }

  @Overr de
  publ c Str ng defaultCl ent d() {
    return "feature_update_serv ce.or g n";
  }

  @Overr de
  publ c Funct on<byte[], Object> enableSampl ng( njector  njector) {
    Dec der dec der =  njector. nstance(Dec der.class);
    return new Funct on<byte[], Object>() {
      @Overr de
      publ c Object apply(byte[] v1) {
        return Dec derUt l. sAva lableForRandomRec p ent(dec der, "dark_traff c_f lter");
      }
    };
  }
}
