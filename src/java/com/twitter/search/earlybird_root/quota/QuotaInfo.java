package com.tw ter.search.earlyb rd_root.quota;

 mport com.google.common.base.Precond  ons;

/**
 * S mple conta ner of quota related  nformat on.
 */
publ c class Quota nfo {
  publ c stat c f nal Str ng DEFAULT_T ER_VALUE = "no_t er";
  publ c stat c f nal boolean DEFAULT_ARCH VE_ACCESS_VALUE = false;

  pr vate f nal Str ng quotaCl ent d;
  pr vate f nal Str ng quotaEma l;
  pr vate f nal  nt quota;
  pr vate f nal boolean shouldEnforceQuota;
  pr vate f nal Str ng cl entT er;
  pr vate f nal boolean arch veAccess;

  /**
   * Creates a new Quota nfo object w h t  g ven cl ent d, quota and shouldEnforceQuota.
   */
  publ c Quota nfo(
      Str ng quotaCl ent d,
      Str ng quotaEma l,
       nt quota,
      boolean shouldEnforceQuota,
      Str ng cl entT er,
      boolean arch veAccess) {
    t .quotaCl ent d = Precond  ons.c ckNotNull(quotaCl ent d);
    t .quotaEma l = Precond  ons.c ckNotNull(quotaEma l);
    t .quota = quota;
    t .shouldEnforceQuota = shouldEnforceQuota;
    t .cl entT er = Precond  ons.c ckNotNull(cl entT er);
    t .arch veAccess = arch veAccess;
  }

  /**
   * Returns t  cl ent d for wh ch   have t  Quota nfo.
   */
  publ c Str ng getQuotaCl ent d() {
    return quotaCl ent d;
  }

  /**
   * Returns t  ema l assoc ated w h t  cl ent d.
   */
  publ c Str ng getQuotaEma l() {
    return quotaEma l;
  }

  /**
   * Returns t   nteger based quota for t  stored cl ent  d.
   */
  publ c  nt getQuota() {
    return quota;
  }

  /**
   * Returns w t r t  quota should be enforced or not.
   */
  publ c boolean shouldEnforceQuota() {
    return shouldEnforceQuota;
  }

  /**
   * Return t er  nfo about t  cl ent.
   */
  publ c Str ng getCl entT er() {
    return cl entT er;
  }

  /**
   * Returns w t r t  cl ent has access to t  full arch ve.
   */
  publ c boolean hasArch veAccess() {
    return arch veAccess;
  }
}
