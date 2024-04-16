package com.tw ter.search.feature_update_serv ce.modules;

 mport com.google. nject.Prov des;
 mport com.google. nject.S ngleton;

 mport com.tw ter.app.Flaggable;
 mport com.tw ter. nject.Tw terModule;
 mport com.tw ter. nject.annotat ons.Flag;

 mport com.tw ter.search.feature_update_serv ce.wh el st.Cl ent dWh el st;

/**
 * Prov des a Cl ent dWh el st, wh ch per od cally loads t 
 * Feature Update Serv ce cl ent wh el st from Conf gBus
 */
publ c class Cl ent dWh el stModule extends Tw terModule {
  publ c Cl ent dWh el stModule() {
    flag("cl ent.wh el st.path", "",
        "Path to cl ent  d wh e l st.", Flaggable.ofStr ng());
    flag("cl ent.wh el st.enable", true,
        "Enable cl ent wh el st for product on.", Flaggable.ofBoolean());
  }

    @Prov des
    @S ngleton
    publ c Cl ent dWh el st prov deCl entWh el st(
        @Flag("cl ent.wh el st.path") Str ng cl ent dWh eL stPath) throws Except on {
        return Cl ent dWh el st. n Wh el st(cl ent dWh eL stPath);
    }
  }
