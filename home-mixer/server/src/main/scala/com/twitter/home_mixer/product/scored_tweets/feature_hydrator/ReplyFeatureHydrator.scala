package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ho _m xer.model.ContentFeatures
 mport com.tw ter.ho _m xer.model.Ho Features._
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.adapters.content. nReplyToContentFeatureAdapter
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.adapters.earlyb rd. nReplyToEarlyb rdAdapter
 mport com.tw ter.ho _m xer.ut l.ReplyRet etUt l
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.FeatureW hDefaultOnFa lure
 mport com.tw ter.product_m xer.core.feature.datarecord.DataRecord nAFeature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BulkCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.ut l.OffloadFuturePools
 mport com.tw ter.search.common.features.thr ftscala.Thr ftT etFeatures
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conversat on_features.v1.thr ftscala.Conversat onFeatures
 mport com.tw ter.t  l nes.conversat on_features.{thr ftscala => cf}
 mport com.tw ter.t  l nes.pred ct on.adapters.conversat on_features.Conversat onFeaturesAdapter
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.T  
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport scala.collect on.JavaConverters._

object  nReplyToT etHydratedEarlyb rdFeature
    extends Feature[T etCand date, Opt on[Thr ftT etFeatures]]

object Conversat onDataRecordFeature
    extends DataRecord nAFeature[T etCand date]
    w h FeatureW hDefaultOnFa lure[T etCand date, DataRecord] {
  overr de def defaultValue: DataRecord = new DataRecord()
}

object  nReplyToEarlyb rdDataRecordFeature
    extends DataRecord nAFeature[T etCand date]
    w h FeatureW hDefaultOnFa lure[T etCand date, DataRecord] {
  overr de def defaultValue: DataRecord = new DataRecord()
}

object  nReplyToT etyp eContentDataRecordFeature
    extends DataRecord nAFeature[T etCand date]
    w h FeatureW hDefaultOnFa lure[T etCand date, DataRecord] {
  overr de def defaultValue: DataRecord = new DataRecord()
}

/**
 * T  purpose of t  hydrator  s to
 * 1) hydrate s mple features  nto repl es and t  r ancestor t ets
 * 2) keep both t  normal repl es and ancestor s ce cand dates, but hydrate  nto t  cand dates
 * features useful for pred ct ng t  qual y of t  repl es and s ce ancestor t ets.
 */
@S ngleton
class ReplyFeatureHydrator @ nject() (statsRece ver: StatsRece ver)
    extends BulkCand dateFeatureHydrator[P pel neQuery, T etCand date] {

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er("ReplyT et")

  overr de val features: Set[Feature[_, _]] = Set(
    Conversat onDataRecordFeature,
     nReplyToT etHydratedEarlyb rdFeature,
     nReplyToEarlyb rdDataRecordFeature,
     nReplyToT etyp eContentDataRecordFeature
  )

  pr vate val defaulDataRecord: DataRecord = new DataRecord()

  pr vate val DefaultFeatureMap = FeatureMapBu lder()
    .add(Conversat onDataRecordFeature, defaulDataRecord)
    .add( nReplyToT etHydratedEarlyb rdFeature, None)
    .add( nReplyToEarlyb rdDataRecordFeature, defaulDataRecord)
    .add( nReplyToT etyp eContentDataRecordFeature, defaulDataRecord)
    .bu ld()

  pr vate val scopedStatsRece ver = statsRece ver.scope(getClass.getS mpleNa )
  pr vate val hydratedReplyCounter = scopedStatsRece ver.counter("hydratedReply")
  pr vate val hydratedAncestorCounter = scopedStatsRece ver.counter("hydratedAncestor")

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[Seq[FeatureMap]] = OffloadFuturePools.offload {
    val replyTo nReplyToT etMap =
      ReplyRet etUt l.replyT et dTo nReplyToT etMap(cand dates)
    val cand datesW hRepl esHydrated = cand dates.map { cand date =>
      replyTo nReplyToT etMap
        .get(cand date.cand date. d).map {  nReplyToT et =>
          hydratedReplyCounter. ncr()
          hydratedReplyCand date(cand date,  nReplyToT et)
        }.getOrElse((cand date, None, None))
    }

    /**
     * Update ancestor t ets w h descendant repl es and hydrate s mple features from one of
     * t  descendants.
     */
    val ancestorT etToDescendantRepl esMap =
      ReplyRet etUt l.ancestorT et dToDescendantRepl esMap(cand dates)
    val cand datesW hRepl esAndAncestorT etsHydrated = cand datesW hRepl esHydrated.map {
      case (
            maybeAncestorT etCand date,
            updatedReplyConversat onFeatures,
             nReplyToT etEarlyB rdFeature) =>
        ancestorT etToDescendantRepl esMap
          .get(maybeAncestorT etCand date.cand date. d)
          .map { descendantRepl es =>
            hydratedAncestorCounter. ncr()
            val (ancestorT etCand date, updatedConversat onFeatures): (
              Cand dateW hFeatures[T etCand date],
              Opt on[Conversat onFeatures]
            ) =
              hydrateAncestorT etCand date(
                maybeAncestorT etCand date,
                descendantRepl es,
                updatedReplyConversat onFeatures)
            (ancestorT etCand date,  nReplyToT etEarlyB rdFeature, updatedConversat onFeatures)
          }
          .getOrElse(
            (
              maybeAncestorT etCand date,
               nReplyToT etEarlyB rdFeature,
              updatedReplyConversat onFeatures))
    }

    cand datesW hRepl esAndAncestorT etsHydrated.map {
      case (cand date,  nReplyToT etEarlyB rdFeature, updatedConversat onFeatures) =>
        val conversat onDataRecordFeature = updatedConversat onFeatures
          .map(f => Conversat onFeaturesAdapter.adaptToDataRecord(cf.Conversat onFeatures.V1(f)))
          .getOrElse(defaulDataRecord)

        val  nReplyToEarlyb rdDataRecord =
           nReplyToEarlyb rdAdapter
            .adaptToDataRecords( nReplyToT etEarlyB rdFeature).asScala. ad
        val  nReplyToContentDataRecord =  nReplyToContentFeatureAdapter
          .adaptToDataRecords(
             nReplyToT etEarlyB rdFeature.map(ContentFeatures.fromThr ft)).asScala. ad

        FeatureMapBu lder()
          .add(Conversat onDataRecordFeature, conversat onDataRecordFeature)
          .add( nReplyToT etHydratedEarlyb rdFeature,  nReplyToT etEarlyB rdFeature)
          .add( nReplyToEarlyb rdDataRecordFeature,  nReplyToEarlyb rdDataRecord)
          .add( nReplyToT etyp eContentDataRecordFeature,  nReplyToContentDataRecord)
          .bu ld()
      case _ => DefaultFeatureMap
    }
  }

  pr vate def hydratedReplyCand date(
    replyCand date: Cand dateW hFeatures[T etCand date],
     nReplyToT etCand date: Cand dateW hFeatures[T etCand date]
  ): (
    Cand dateW hFeatures[T etCand date],
    Opt on[Conversat onFeatures],
    Opt on[Thr ftT etFeatures]
  ) = {
    val t etedAfter nReplyToT et nSecs =
      (
        or g nalT etAgeFromSnowflake( nReplyToT etCand date),
        or g nalT etAgeFromSnowflake(replyCand date)) match {
        case (So ( nReplyToT etAge), So (replyT etAge)) =>
          So (( nReplyToT etAge - replyT etAge). nSeconds.toLong)
        case _ => None
      }

    val ex st ngConversat onFeatures = So (
      replyCand date.features
        .getOrElse(Conversat onFeature, None).getOrElse(Conversat onFeatures()))

    val updatedConversat onFeatures = ex st ngConversat onFeatures match {
      case So (v1) =>
        So (
          v1.copy(
            t etedAfter nReplyToT et nSecs = t etedAfter nReplyToT et nSecs,
             sSelfReply = So (
              replyCand date.features.getOrElse(
                Author dFeature,
                None) ==  nReplyToT etCand date.features.getOrElse(Author dFeature, None))
          )
        )
      case _ => None
    }

    // Note:  f  nReplyToT et  s a ret et,   need to read early b rd feature from t   rged
    // early b rd feature f eld from Ret etS ceT etFeatureHydrator class.
    // But  f  nReplyToT et  s a reply,   return  s early b rd feature d rectly
    val  nReplyToT etThr ftT etFeaturesOpt = {
       f ( nReplyToT etCand date.features.getOrElse( sRet etFeature, false)) {
         nReplyToT etCand date.features.getOrElse(S ceT etEarlyb rdFeature, None)
      } else {
         nReplyToT etCand date.features.getOrElse(Earlyb rdFeature, None)
      }
    }

    (replyCand date, updatedConversat onFeatures,  nReplyToT etThr ftT etFeaturesOpt)
  }

  pr vate def hydrateAncestorT etCand date(
    ancestorT etCand date: Cand dateW hFeatures[T etCand date],
    descendantRepl es: Seq[Cand dateW hFeatures[T etCand date]],
    updatedReplyConversat onFeatures: Opt on[Conversat onFeatures]
  ): (Cand dateW hFeatures[T etCand date], Opt on[Conversat onFeatures]) = {
    // Ancestor could be a reply. For example,  n thread: t etA -> t etB -> t etC,
    // t etB  s a reply and ancestor at t  sa  t  .  nce, t etB's conversat on feature
    // w ll be updated by hydratedReplyCand date and hydrateAncestorT etCand date funct ons.
    val ex st ngConversat onFeatures =
       f (updatedReplyConversat onFeatures.nonEmpty)
        updatedReplyConversat onFeatures
      else
        So (
          ancestorT etCand date.features
            .getOrElse(Conversat onFeature, None).getOrElse(Conversat onFeatures()))

    val updatedConversat onFeatures = ex st ngConversat onFeatures match {
      case So (v1) =>
        So (
          v1.copy(
            hasDescendantReplyCand date = So (true),
            has nNetworkDescendantReply =
              So (descendantRepl es.ex sts(_.features.getOrElse( nNetworkFeature, false)))
          ))
      case _ => None
    }
    (ancestorT etCand date, updatedConversat onFeatures)
  }

  pr vate def or g nalT etAgeFromSnowflake(
    cand date: Cand dateW hFeatures[T etCand date]
  ): Opt on[Durat on] = {
    Snowflake d
      .t  From dOpt(
        cand date.features
          .getOrElse(S ceT et dFeature, None).getOrElse(cand date.cand date. d))
      .map(T  .now - _)
  }
}
