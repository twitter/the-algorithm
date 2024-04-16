package com.tw ter.search.earlyb rd_root.quota;

 mport java.ut l.Opt onal;

/** A manager that determ nes how quota restr ct ons should be appl ed for each cl ent. */
publ c  nterface Cl ent dQuotaManager {
  /**
   * Returns t  quota for t  g ven cl ent,  f one  s set.
   *
   * @param cl ent d T   D of t  cl ent.
   * @return T  quota for t  g ven cl ent ( n requests per second),  f one  s set.
   */
  Opt onal<Quota nfo> getQuotaForCl ent(Str ng cl ent d);

  /**
   * Returns t  common pool quota. A common pool quota must always be set.
   *
   * @return T  common pool quota ( n requests per second).
   */
  Quota nfo getCommonPoolQuota();

}
