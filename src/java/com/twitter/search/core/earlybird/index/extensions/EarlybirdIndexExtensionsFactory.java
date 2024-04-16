package com.tw ter.search.core.earlyb rd. ndex.extens ons;

/**
 * Base class to  mple nt factor es that create realt   and Lucene  ndex extens ons.
 *
 * T  factory needs to be able to create  nstances for new seg nts, as  ll as load
 *  ndex extens ons of ex st ng seg nts from d sk.
 */
publ c abstract class Earlyb rd ndexExtens onsFactory {
  /**
   * Returns t  {@l nk Earlyb rdRealt   ndexExtens onsData}  nstance to be used for a new seg nt.
   */
  publ c abstract Earlyb rdRealt   ndexExtens onsData newRealt   ndexExtens onsData();

  /**
   * Returns t  {@l nk Earlyb rd ndexExtens onsData}  nstance to be used for a new Lucene seg nt.
   */
  publ c abstract Earlyb rd ndexExtens onsData newLucene ndexExtens onsData();
}
