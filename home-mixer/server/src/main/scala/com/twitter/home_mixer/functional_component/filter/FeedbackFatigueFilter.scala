package com.tw ter.ho _m xer.funct onal_component.f lter

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Feedback toryFeature
 mport com.tw ter.ho _m xer.model.Ho Features. sRet etFeature
 mport com.tw ter.ho _m xer.model.Ho Features.SGSVal dFollo dByUser dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.SGSVal dL kedByUser dsFeature
 mport com.tw ter.ho _m xer.ut l.Cand datesUt l
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lterResult
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.common.thr ftscala.FeedbackEnt y
 mport com.tw ter.t  l neserv ce.model.FeedbackEntry
 mport com.tw ter.t  l neserv ce.{thr ftscala => tls}

object FeedbackFat gueF lter
    extends F lter[P pel neQuery, T etCand date]
    w h F lter.Cond  onally[P pel neQuery, T etCand date] {

  overr de val  dent f er: F lter dent f er = F lter dent f er("FeedbackFat gue")

  overr de def only f(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): Boolean =
    query.features.ex sts(_.getOrElse(Feedback toryFeature, Seq.empty).nonEmpty)

  pr vate val Durat onForF lter ng = 14.days

  overr de def apply(
    query: p pel ne.P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[F lterResult[T etCand date]] = {
    val feedbackEntr esByEngage ntType =
      query.features
        .getOrElse(FeatureMap.empty).getOrElse(Feedback toryFeature, Seq.empty)
        .f lter { entry =>
          val t  S nceFeedback = query.queryT  .m nus(entry.t  stamp)
          t  S nceFeedback < Durat onForF lter ng &&
          entry.feedbackType == tls.FeedbackType.SeeFe r
        }.groupBy(_.engage ntType)

    val authorsToF lter =
      getUser ds(
        feedbackEntr esByEngage ntType.getOrElse(tls.FeedbackEngage ntType.T et, Seq.empty))
    val l kersToF lter =
      getUser ds(
        feedbackEntr esByEngage ntType.getOrElse(tls.FeedbackEngage ntType.L ke, Seq.empty))
    val follo rsToF lter =
      getUser ds(
        feedbackEntr esByEngage ntType.getOrElse(tls.FeedbackEngage ntType.Follow, Seq.empty))
    val ret etersToF lter =
      getUser ds(
        feedbackEntr esByEngage ntType.getOrElse(tls.FeedbackEngage ntType.Ret et, Seq.empty))

    val (removed, kept) = cand dates.part  on { cand date =>
      val or g nalAuthor d = Cand datesUt l.getOr g nalAuthor d(cand date.features)
      val author d = cand date.features.getOrElse(Author dFeature, None)

      val l kers = cand date.features.getOrElse(SGSVal dL kedByUser dsFeature, Seq.empty)
      val el g bleL kers = l kers.f lterNot(l kersToF lter.conta ns)

      val follo rs = cand date.features.getOrElse(SGSVal dFollo dByUser dsFeature, Seq.empty)
      val el g bleFollo rs = follo rs.f lterNot(follo rsToF lter.conta ns)

      or g nalAuthor d.ex sts(authorsToF lter.conta ns) ||
      (l kers.nonEmpty && el g bleL kers. sEmpty) ||
      (follo rs.nonEmpty && el g bleFollo rs. sEmpty && l kers. sEmpty) ||
      (cand date.features.getOrElse( sRet etFeature, false) &&
      author d.ex sts(ret etersToF lter.conta ns))
    }

    St ch.value(F lterResult(kept = kept.map(_.cand date), removed = removed.map(_.cand date)))
  }

  pr vate def getUser ds(
    feedbackEntr es: Seq[FeedbackEntry],
  ): Set[Long] =
    feedbackEntr es.collect {
      case FeedbackEntry(_, _, FeedbackEnt y.User d(user d), _, _) => user d
    }.toSet
}
