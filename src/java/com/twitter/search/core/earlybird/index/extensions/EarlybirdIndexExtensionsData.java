package com.tw ter.search.core.earlyb rd. ndex.extens ons;

 mport java. o. OExcept on;

 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;

/**
 * Base  ndex extens ons class.
 */
publ c  nterface Earlyb rd ndexExtens onsData {
  /**
   * Sets up t  extens ons for t  g ven reader.
   */
  vo d setupExtens ons(Earlyb rd ndexSeg ntAtom cReader atom cReader) throws  OExcept on;
}
