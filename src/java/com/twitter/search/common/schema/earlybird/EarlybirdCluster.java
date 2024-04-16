package com.tw ter.search.common.sc ma.earlyb rd;

 mport java.ut l.Set;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.collect. mmutableSet;

/**
 * A l st of ex st ng Earlyb rd clusters.
 */
publ c enum Earlyb rdCluster {
  /**
   * Realt   earlyb rd cluster. Has 100% of t et for about 7 days.
   */
  REALT ME,
  /**
   * Protected earlyb rd cluster. Has only t ets from protected accounts.
   */
  PROTECTED,
  /**
   * Full arch ve cluster. Has all t ets unt l about 2 days ago.
   */
  FULL_ARCH VE,
  /**
   * SuperRoot cluster. Talks to t  ot r clusters  nstead of talk ng d rectly to earlyb rds.
   */
  SUPERROOT,

  /**
   * A ded cated cluster for Cand date Generat on use cases based on Earlyb rd  n Ho /PushServ ce
   */
  REALT ME_CG;

  publ c Str ng getNa ForStats() {
    return na ().toLo rCase();
  }

  publ c stat c boolean  sArch ve(Earlyb rdCluster cluster) {
    return  sCluster nSet(cluster, ARCH VE_CLUSTERS);
  }

  publ c stat c boolean  sTw ter moryFormatCluster(Earlyb rdCluster cluster) {
    return  sCluster nSet(cluster, TW TTER_ N_MEMORY_ NDEX_FORMAT_GENERAL_PURPOSE_CLUSTERS);
  }

  publ c stat c boolean hasEarlyb rds(Earlyb rdCluster cluster) {
    return cluster != SUPERROOT;
  }

  pr vate stat c boolean  sCluster nSet(Earlyb rdCluster cluster, Set<Earlyb rdCluster> set) {
    return set.conta ns(cluster);
  }

  protected stat c f nal  mmutableSet<Earlyb rdCluster> ARCH VE_CLUSTERS =
       mmutableSet.of(FULL_ARCH VE);

  @V s bleForTest ng
  publ c stat c f nal  mmutableSet<Earlyb rdCluster>
          TW TTER_ N_MEMORY_ NDEX_FORMAT_GENERAL_PURPOSE_CLUSTERS =
       mmutableSet.of(
          REALT ME,
          PROTECTED);

  @V s bleForTest ng
  publ c stat c f nal  mmutableSet<Earlyb rdCluster> TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS =
       mmutableSet.of(
          REALT ME,
          PROTECTED,
          REALT ME_CG);

  /**
   * Constant for f eld used  n general purpose clusters,
   * Note that GENERAL_PURPOSE_CLUSTERS does not  nclude REALT ME_CG.  f   w sh to  nclude REALT ME_CG,
   * please use ALL_CLUSTERS
   */
  protected stat c f nal  mmutableSet<Earlyb rdCluster> GENERAL_PURPOSE_CLUSTERS =
       mmutableSet.of(
          REALT ME,
          PROTECTED,
          FULL_ARCH VE,
          SUPERROOT);

  protected stat c f nal  mmutableSet<Earlyb rdCluster> ALL_CLUSTERS =
       mmutableSet.of(
          REALT ME,
          PROTECTED,
          FULL_ARCH VE,
          SUPERROOT,
          REALT ME_CG);
}
