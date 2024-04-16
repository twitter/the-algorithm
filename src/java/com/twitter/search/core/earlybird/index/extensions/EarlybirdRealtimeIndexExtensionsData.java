package com.tw ter.search.core.earlyb rd. ndex.extens ons;

 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rdRealt   ndexSeg ntWr er;

/**
 * An  ndex extens ons  mple ntat on for real-t   Earlyb rd  ndexes.
 */
publ c  nterface Earlyb rdRealt   ndexExtens onsData extends Earlyb rd ndexExtens onsData {
  /**
   * Opt onally, an  mple nt ng class can prov de a custom consu r for  nverted f elds ( .e. streams of tokens).
   */
  vo d create nvertedDocConsu r(
      Earlyb rdRealt   ndexSeg ntWr er. nvertedDocConsu rBu lder bu lder);

  /**
   * Opt onally, an  mple nt ng class can prov de a custom consu r for stored f elds (e.g. doc values f elds).
   */
  vo d createStoredF eldsConsu r(
      Earlyb rdRealt   ndexSeg ntWr er.StoredF eldsConsu rBu lder bu lder);
}
