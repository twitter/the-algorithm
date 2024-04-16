package com.tw ter.v s b l y.bu lder.t ets

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.t etyp e.thr ftscala.Conversat onControl
 mport com.tw ter.v s b l y.bu lder.FeatureMapBu lder
 mport com.tw ter.v s b l y.bu lder.users.Relat onsh pFeatures
 mport com.tw ter.v s b l y.common. nv edToConversat onRepo
 mport com.tw ter.v s b l y.features.Conversat onRootAuthorFollowsV e r
 mport com.tw ter.v s b l y.features.T etConversat onV e r s nv ed
 mport com.tw ter.v s b l y.features.T etConversat onV e r s nv edV aReply nt on
 mport com.tw ter.v s b l y.features.T etConversat onV e r sRootAuthor
 mport com.tw ter.v s b l y.features.T etHasBy nv at onConversat onControl
 mport com.tw ter.v s b l y.features.T etHasCommun yConversat onControl
 mport com.tw ter.v s b l y.features.T etHasFollo rsConversat onControl
 mport com.tw ter.v s b l y.features.V e rFollowsConversat onRootAuthor

class Conversat onControlFeatures(
  relat onsh pFeatures: Relat onsh pFeatures,
   s nv edToConversat onRepos ory:  nv edToConversat onRepo,
  statsRece ver: StatsRece ver) {

  pr vate[t ] val scopedStatsRece ver = statsRece ver.scope("conversat on_control_features")

  pr vate[t ] val requests = scopedStatsRece ver.counter("requests")

  pr vate[t ] val t etCommun yConversat onRequest =
    scopedStatsRece ver.scope(T etHasCommun yConversat onControl.na ).counter("requests")
  pr vate[t ] val t etBy nv at onConversat onRequest =
    scopedStatsRece ver.scope(T etHasBy nv at onConversat onControl.na ).counter("requests")
  pr vate[t ] val t etFollo rsConversat onRequest =
    scopedStatsRece ver.scope(T etHasFollo rsConversat onControl.na ).counter("requests")
  pr vate[t ] val rootAuthorFollowsV e r =
    scopedStatsRece ver.scope(Conversat onRootAuthorFollowsV e r.na ).counter("requests")
  pr vate[t ] val v e rFollowsRootAuthor =
    scopedStatsRece ver.scope(V e rFollowsConversat onRootAuthor.na ).counter("requests")

  def  sCommun yConversat on(conversat onControl: Opt on[Conversat onControl]): Boolean =
    conversat onControl
      .collect {
        case _: Conversat onControl.Commun y =>
          t etCommun yConversat onRequest. ncr()
          true
      }.getOrElse(false)

  def  sBy nv at onConversat on(conversat onControl: Opt on[Conversat onControl]): Boolean =
    conversat onControl
      .collect {
        case _: Conversat onControl.By nv at on =>
          t etBy nv at onConversat onRequest. ncr()
          true
      }.getOrElse(false)

  def  sFollo rsConversat on(conversat onControl: Opt on[Conversat onControl]): Boolean =
    conversat onControl
      .collect {
        case _: Conversat onControl.Follo rs =>
          t etFollo rsConversat onRequest. ncr()
          true
      }.getOrElse(false)

  def conversat onRootAuthor d(
    conversat onControl: Opt on[Conversat onControl]
  ): Opt on[Long] =
    conversat onControl match {
      case So (Conversat onControl.Commun y(commun y)) =>
        So (commun y.conversat onT etAuthor d)
      case So (Conversat onControl.By nv at on(by nv at on)) =>
        So (by nv at on.conversat onT etAuthor d)
      case So (Conversat onControl.Follo rs(follo rs)) =>
        So (follo rs.conversat onT etAuthor d)
      case _ => None
    }

  def v e r sRootAuthor(
    conversat onControl: Opt on[Conversat onControl],
    v e r dOpt: Opt on[Long]
  ): Boolean =
    (conversat onRootAuthor d(conversat onControl), v e r dOpt) match {
      case (So (rootAuthor d), So (v e r d))  f rootAuthor d == v e r d => true
      case _ => false
    }

  def v e r s nv ed(
    conversat onControl: Opt on[Conversat onControl],
    v e r d: Opt on[Long]
  ): Boolean = {
    val  nv edUser ds = conversat onControl match {
      case So (Conversat onControl.Commun y(commun y)) =>
        commun y. nv edUser ds
      case So (Conversat onControl.By nv at on(by nv at on)) =>
        by nv at on. nv edUser ds
      case So (Conversat onControl.Follo rs(follo rs)) =>
        follo rs. nv edUser ds
      case _ => Seq()
    }

    v e r d.ex sts( nv edUser ds.conta ns(_))
  }

  def conversat onAuthorFollows(
    conversat onControl: Opt on[Conversat onControl],
    v e r d: Opt on[Long]
  ): St ch[Boolean] = {
    val conversat onAuthor d = conversat onControl.collect {
      case Conversat onControl.Commun y(commun y) =>
        commun y.conversat onT etAuthor d
    }

    conversat onAuthor d match {
      case So (author d) =>
        rootAuthorFollowsV e r. ncr()
        relat onsh pFeatures.authorFollowsV e r(author d, v e r d)
      case None =>
        St ch.False
    }
  }

  def followsConversat onAuthor(
    conversat onControl: Opt on[Conversat onControl],
    v e r d: Opt on[Long]
  ): St ch[Boolean] = {
    val conversat onAuthor d = conversat onControl.collect {
      case Conversat onControl.Follo rs(follo rs) =>
        follo rs.conversat onT etAuthor d
    }

    conversat onAuthor d match {
      case So (author d) =>
        v e rFollowsRootAuthor. ncr()
        relat onsh pFeatures.v e rFollowsAuthor(author d, v e r d)
      case None =>
        St ch.False
    }
  }

  def v e r s nv edV aReply nt on(
    t et: T et,
    v e r dOpt: Opt on[Long]
  ): St ch[Boolean] = {
    val conversat on dOpt: Opt on[Long] = t et.conversat onControl match {
      case So (Conversat onControl.Commun y(commun y))
           f commun y. nv eV a nt on.conta ns(true) =>
        t et.coreData.flatMap(_.conversat on d)
      case So (Conversat onControl.By nv at on( nv at on))
           f  nv at on. nv eV a nt on.conta ns(true) =>
        t et.coreData.flatMap(_.conversat on d)
      case So (Conversat onControl.Follo rs(follo rs))
           f follo rs. nv eV a nt on.conta ns(true) =>
        t et.coreData.flatMap(_.conversat on d)
      case _ => None
    }

    (conversat on dOpt, v e r dOpt) match {
      case (So (conversat on d), So (v e r d)) =>
         s nv edToConversat onRepos ory(conversat on d, v e r d)
      case _ => St ch.False
    }
  }

  def forT et(t et: T et, v e r d: Opt on[Long]): FeatureMapBu lder => FeatureMapBu lder = {
    requests. ncr()
    val cc = t et.conversat onControl

    _.w hConstantFeature(T etHasCommun yConversat onControl,  sCommun yConversat on(cc))
      .w hConstantFeature(T etHasBy nv at onConversat onControl,  sBy nv at onConversat on(cc))
      .w hConstantFeature(T etHasFollo rsConversat onControl,  sFollo rsConversat on(cc))
      .w hConstantFeature(T etConversat onV e r sRootAuthor, v e r sRootAuthor(cc, v e r d))
      .w hConstantFeature(T etConversat onV e r s nv ed, v e r s nv ed(cc, v e r d))
      .w hFeature(Conversat onRootAuthorFollowsV e r, conversat onAuthorFollows(cc, v e r d))
      .w hFeature(V e rFollowsConversat onRootAuthor, followsConversat onAuthor(cc, v e r d))
      .w hFeature(
        T etConversat onV e r s nv edV aReply nt on,
        v e r s nv edV aReply nt on(t et, v e r d))

  }
}
