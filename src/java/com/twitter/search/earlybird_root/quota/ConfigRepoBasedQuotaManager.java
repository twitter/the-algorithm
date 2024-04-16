package com.tw ter.search.earlyb rd_root.quota;

 mport java.ut l.Opt onal;

 mport javax. nject. nject;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.search.common.dark.ServerSetResolver.SelfServerSetResolver;

/**
 * A conf g based  mple ntat on of t  {@code Cl ent dQuotaManager}  nterface.
 *   uses a Conf gBasedQuotaConf g object to load t  contents of t  conf g.
 */
publ c class Conf gRepoBasedQuotaManager  mple nts Cl ent dQuotaManager {

  publ c stat c f nal Str ng COMMON_POOL_CL ENT_ D = "common_pool";

  pr vate f nal Conf gBasedQuotaConf g quotaConf g;
  pr vate f nal SelfServerSetResolver serverSetResolver;

  /** Creates a new Conf gRepoBasedQuotaManager  nstance. */
  @ nject
  publ c Conf gRepoBasedQuotaManager(
      SelfServerSetResolver serverSetResolver,
      Conf gBasedQuotaConf g quotaConf g) {
    Precond  ons.c ckNotNull(quotaConf g);

    t .quotaConf g = quotaConf g;
    t .serverSetResolver = serverSetResolver;
  }

  @Overr de
  publ c Opt onal<Quota nfo> getQuotaForCl ent(Str ng cl ent d) {
    Opt onal<Quota nfo> quotaForCl ent = quotaConf g.getQuotaForCl ent(cl ent d);

     f (!quotaForCl ent. sPresent()) {
      return Opt onal.empty();
    }

    Quota nfo quota = quotaForCl ent.get();

     nt quotaValue = quota.getQuota();
     nt root nstanceCount = serverSetResolver.getServerSetS ze();
     f (root nstanceCount > 0) {
      quotaValue = ( nt) Math.ce l((double) quotaValue / root nstanceCount);
    }

    return Opt onal.of(
        new Quota nfo(
            quota.getQuotaCl ent d(),
            quota.getQuotaEma l(),
            quotaValue,
            quota.shouldEnforceQuota(),
            quota.getCl entT er(),
            quota.hasArch veAccess()));
  }

  @Overr de
  publ c Quota nfo getCommonPoolQuota() {
    Opt onal<Quota nfo> commonPoolQuota = getQuotaForCl ent(COMMON_POOL_CL ENT_ D);
    Precond  ons.c ckState(commonPoolQuota. sPresent());
    return commonPoolQuota.get();
  }
}
