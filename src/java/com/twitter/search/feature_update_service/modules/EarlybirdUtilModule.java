package com.tw ter.search.feature_update_serv ce.modules;

 mport com.tw ter.app.Flaggable;
 mport com.tw ter. nject.Tw terModule;

publ c class Earlyb rdUt lModule extends Tw terModule {
  publ c stat c f nal Str ng PENGU N_VERS ONS_FLAG = "pengu n.vers ons";

  publ c Earlyb rdUt lModule() {
    flag(PENGU N_VERS ONS_FLAG, "pengu n_6",
        "Comma-separated l st of supported Pengu n vers ons.", Flaggable.ofStr ng());
  }
}
