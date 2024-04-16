package com.tw ter.v s b l y.bu lder.t ets

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.thr ftscala.CollabControl
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.T  
 mport com.tw ter.v s b l y.bu lder.FeatureMapBu lder
 mport com.tw ter.v s b l y.common.SafetyLabelMapS ce
 mport com.tw ter.v s b l y.common.T et d
 mport com.tw ter.v s b l y.common.User d
 mport com.tw ter.v s b l y.features._
 mport com.tw ter.v s b l y.models.Semant cCoreAnnotat on
 mport com.tw ter.v s b l y.models.T etSafetyLabel

object T etFeatures {

  def FALLBACK_T MESTAMP: T   = T  .epoch

  def t et sSelfReply(t et: T et): Boolean = {
    t et.coreData match {
      case So (coreData) =>
        coreData.reply match {
          case So (reply) =>
            reply. nReplyToUser d == coreData.user d

          case None =>
            false
        }

      case None =>
        false
    }
  }

  def t etReplyToParentT etDurat on(t et: T et): Opt on[Durat on] = for {
    coreData <- t et.coreData
    reply <- coreData.reply
     nReplyToStatus d <- reply. nReplyToStatus d
    replyT   <- Snowflake d.t  From dOpt(t et. d)
    repl edToT   <- Snowflake d.t  From dOpt( nReplyToStatus d)
  } y eld {
    replyT  .d ff(repl edToT  )
  }

  def t etReplyToRootT etDurat on(t et: T et): Opt on[Durat on] = for {
    coreData <- t et.coreData
     f coreData.reply. sDef ned
    conversat on d <- coreData.conversat on d
    replyT   <- Snowflake d.t  From dOpt(t et. d)
    rootT   <- Snowflake d.t  From dOpt(conversat on d)
  } y eld {
    replyT  .d ff(rootT  )
  }

  def t etT  stamp(t et d: Long): T   =
    Snowflake d.t  From dOpt(t et d).getOrElse(FALLBACK_T MESTAMP)

  def t etSemant cCoreAnnotat ons(t et: T et): Seq[Semant cCoreAnnotat on] = {
    t et.esc rb rdEnt yAnnotat ons
      .map(a =>
        a.ent yAnnotat ons.map { annotat on =>
          Semant cCoreAnnotat on(
            annotat on.group d,
            annotat on.doma n d,
            annotat on.ent y d
          )
        }).toSeq.flatten
  }

  def t et sNullcast(t et: T et): Boolean = {
    t et.coreData match {
      case So (coreData) =>
        coreData.nullcast
      case None =>
        false
    }
  }

  def t etAuthorUser d(t et: T et): Opt on[User d] = {
    t et.coreData.map(_.user d)
  }
}

sealed tra  T etLabels {
  def forT et(t et: T et): St ch[Seq[T etSafetyLabel]]
  def forT et d(t et d: T et d): St ch[Seq[T etSafetyLabel]]
}

class StratoT etLabelMaps(safetyLabelS ce: SafetyLabelMapS ce) extends T etLabels {

  overr de def forT et(t et: T et): St ch[Seq[T etSafetyLabel]] = {
    forT et d(t et. d)
  }

  def forT et d(t et d: T et d): St ch[Seq[T etSafetyLabel]] = {
    safetyLabelS ce
      .fetch(t et d).map(
        _.map(
          _.labels
            .map(
              _.map(sl => T etSafetyLabel.fromTuple(sl._1, sl._2)).toSeq
            ).getOrElse(Seq())
        ).getOrElse(Seq()))
  }
}

object N lT etLabelMaps extends T etLabels {
  overr de def forT et(t et: T et): St ch[Seq[T etSafetyLabel]] = St ch.N l
  overr de def forT et d(t et d: T et d): St ch[Seq[T etSafetyLabel]] = St ch.N l
}

class T etFeatures(t etLabels: T etLabels, statsRece ver: StatsRece ver) {
  pr vate[t ] val scopedStatsRece ver = statsRece ver.scope("t et_features")

  pr vate[t ] val requests = scopedStatsRece ver.counter("requests")
  pr vate[t ] val t etSafetyLabels =
    scopedStatsRece ver.scope(T etSafetyLabels.na ).counter("requests")
  pr vate[t ] val t etTakedownReasons =
    scopedStatsRece ver.scope(T etTakedownReasons.na ).counter("requests")
  pr vate[t ] val t et sSelfReply =
    scopedStatsRece ver.scope(T et sSelfReply.na ).counter("requests")
  pr vate[t ] val t etT  stamp =
    scopedStatsRece ver.scope(T etT  stamp.na ).counter("requests")
  pr vate[t ] val t etReplyToParentT etDurat on =
    scopedStatsRece ver.scope(T etReplyToParentT etDurat on.na ).counter("requests")
  pr vate[t ] val t etReplyToRootT etDurat on =
    scopedStatsRece ver.scope(T etReplyToRootT etDurat on.na ).counter("requests")
  pr vate[t ] val t etSemant cCoreAnnotat ons =
    scopedStatsRece ver.scope(T etSemant cCoreAnnotat ons.na ).counter("requests")
  pr vate[t ] val t et d =
    scopedStatsRece ver.scope(T et d.na ).counter("requests")
  pr vate[t ] val t etHasNsfwUser =
    scopedStatsRece ver.scope(T etHasNsfwUser.na ).counter("requests")
  pr vate[t ] val t etHasNsfwAdm n =
    scopedStatsRece ver.scope(T etHasNsfwAdm n.na ).counter("requests")
  pr vate[t ] val t et sNullcast =
    scopedStatsRece ver.scope(T et sNullcast.na ).counter("requests")
  pr vate[t ] val t etHas d a =
    scopedStatsRece ver.scope(T etHas d a.na ).counter("requests")
  pr vate[t ] val t et sCommun y =
    scopedStatsRece ver.scope(T et sCommun yT et.na ).counter("requests")
  pr vate[t ] val t et sCollab nv at on =
    scopedStatsRece ver.scope(T et sCollab nv at onT et.na ).counter("requests")

  def forT et(t et: T et): FeatureMapBu lder => FeatureMapBu lder = {
    forT etW houtSafetyLabels(t et)
      .andT n(_.w hFeature(T etSafetyLabels, t etLabels.forT et(t et)))
  }

  def forT etW houtSafetyLabels(t et: T et): FeatureMapBu lder => FeatureMapBu lder = {
    requests. ncr()

    t etTakedownReasons. ncr()
    t et sSelfReply. ncr()
    t etT  stamp. ncr()
    t etReplyToParentT etDurat on. ncr()
    t etReplyToRootT etDurat on. ncr()
    t etSemant cCoreAnnotat ons. ncr()
    t et d. ncr()
    t etHasNsfwUser. ncr()
    t etHasNsfwAdm n. ncr()
    t et sNullcast. ncr()
    t etHas d a. ncr()
    t et sCommun y. ncr()
    t et sCollab nv at on. ncr()

    _.w hConstantFeature(T etTakedownReasons, t et.takedownReasons.getOrElse(Seq.empty))
      .w hConstantFeature(T et sSelfReply, T etFeatures.t et sSelfReply(t et))
      .w hConstantFeature(T etT  stamp, T etFeatures.t etT  stamp(t et. d))
      .w hConstantFeature(
        T etReplyToParentT etDurat on,
        T etFeatures.t etReplyToParentT etDurat on(t et))
      .w hConstantFeature(
        T etReplyToRootT etDurat on,
        T etFeatures.t etReplyToRootT etDurat on(t et))
      .w hConstantFeature(
        T etSemant cCoreAnnotat ons,
        T etFeatures.t etSemant cCoreAnnotat ons(t et))
      .w hConstantFeature(T et d, t et. d)
      .w hConstantFeature(T etHasNsfwUser, t etHasNsfwUser(t et))
      .w hConstantFeature(T etHasNsfwAdm n, t etHasNsfwAdm n(t et))
      .w hConstantFeature(T et sNullcast, T etFeatures.t et sNullcast(t et))
      .w hConstantFeature(T etHas d a, t etHas d a(t et))
      .w hConstantFeature(T et sCommun yT et, t etHasCommun y(t et))
      .w hConstantFeature(T et sCollab nv at onT et, t et sCollab nv at on(t et))
  }

  def t etHasNsfwUser(t et: T et): Boolean =
    t et.coreData.ex sts(_.nsfwUser)

  def t etHasNsfwAdm n(t et: T et): Boolean =
    t et.coreData.ex sts(_.nsfwAdm n)

  def t etHas d a(t et: T et): Boolean =
    t et.coreData.ex sts(_.has d a.getOrElse(false))

  def t etHasCommun y(t et: T et): Boolean = {
    t et.commun  es.ex sts(_.commun y ds.nonEmpty)
  }

  def t et sCollab nv at on(t et: T et): Boolean = {
    t et.collabControl.ex sts(_ match {
      case CollabControl.Collab nv at on(_) => true
      case _ => false
    })
  }
}
