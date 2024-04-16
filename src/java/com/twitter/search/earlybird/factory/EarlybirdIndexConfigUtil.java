package com.tw ter.search.earlyb rd.factory;

 mport com.tw ter.dec der.Dec der;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.earlyb rd.Earlyb rd ndexConf g;
 mport com.tw ter.search.earlyb rd.Realt  Earlyb rd ndexConf g;
 mport com.tw ter.search.earlyb rd.arch ve.Arch veOnD skEarlyb rd ndexConf g;
 mport com.tw ter.search.earlyb rd.arch ve.Arch veSearchPart  onManager;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.except on.Cr  calExcept onHandler;
 mport com.tw ter.search.earlyb rd.part  on.Search ndex ng tr cSet;

publ c f nal class Earlyb rd ndexConf gUt l {
  pr vate Earlyb rd ndexConf gUt l() {
  }

  /**
   * Creates t   ndex conf g for t  earlyb rd.
   */
  publ c stat c Earlyb rd ndexConf g createEarlyb rd ndexConf g(
      Dec der dec der, Search ndex ng tr cSet search ndex ng tr cSet,
      Cr  calExcept onHandler cr  calExcept onHandler) {
     f ( sArch veSearch()) {
      return new Arch veOnD skEarlyb rd ndexConf g(dec der, search ndex ng tr cSet,
          cr  calExcept onHandler);
    } else  f ( sProtectedSearch()) {
      return new Realt  Earlyb rd ndexConf g(
          Earlyb rdCluster.PROTECTED, dec der, search ndex ng tr cSet, cr  calExcept onHandler);
    } else  f ( sRealt  CG()) {
      return new Realt  Earlyb rd ndexConf g(
          Earlyb rdCluster.REALT ME_CG, dec der, search ndex ng tr cSet, cr  calExcept onHandler);
    } else {
      return new Realt  Earlyb rd ndexConf g(
          Earlyb rdCluster.REALT ME, dec der, search ndex ng tr cSet, cr  calExcept onHandler);
    }
  }

  publ c stat c boolean  sArch veSearch() {
    // Re-read ng conf g on each call so that tests can rel ably overwr e t 
    return Earlyb rdConf g.getStr ng("part  on_manager", "realt  ")
        .equals(Arch veSearchPart  onManager.CONF G_NAME);
  }

  pr vate stat c boolean  sProtectedSearch() {
    // Re-read ng conf g on each call so that tests can rel ably overwr e t 
    return Earlyb rdConf g.getBool("protected_ ndex", false);
  }

  pr vate stat c boolean  sRealt  CG() {
    // Re-read ng conf g on each call so that tests can rel ably overwr e t 
    return Earlyb rdConf g.getBool("realt  _cg_ ndex", false);
  }
}
