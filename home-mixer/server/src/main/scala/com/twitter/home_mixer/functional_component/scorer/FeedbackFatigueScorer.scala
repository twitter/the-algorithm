package com.tw ter.ho _m xer.funct onal_component.scorer

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Feedback toryFeature
 mport com.tw ter.ho _m xer.model.Ho Features. sRet etFeature
 mport com.tw ter.ho _m xer.model.Ho Features.SGSVal dFollo dByUser dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.SGSVal dL kedByUser dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.ScoreFeature
 mport com.tw ter.ho _m xer.ut l.Cand datesUt l
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.scorer.Scorer
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Cond  onally
 mport com.tw ter.product_m xer.core.model.common. dent f er.Scorer dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.common.{thr ftscala => tl}
 mport com.tw ter.t  l neserv ce.model.FeedbackEntry
 mport com.tw ter.t  l neserv ce.{thr ftscala => tls}
 mport com.tw ter.ut l.T  
 mport scala.collect on.mutable

object FeedbackFat gueScorer
    extends Scorer[P pel neQuery, T etCand date]
    w h Cond  onally[P pel neQuery] {

  overr de val  dent f er: Scorer dent f er = Scorer dent f er("FeedbackFat gue")

  overr de def features: Set[Feature[_, _]] = Set(ScoreFeature)

  overr de def only f(query: P pel neQuery): Boolean =
    query.features.ex sts(_.getOrElse(Feedback toryFeature, Seq.empty).nonEmpty)

  val Durat onForF lter ng = 14.days
  val Durat onForD scount ng = 140.days
  pr vate val ScoreMult pl erLo rBound = 0.2
  pr vate val ScoreMult pl erUpperBound = 1.0
  pr vate val ScoreMult pl er ncre ntsCount = 4
  pr vate val ScoreMult pl er ncre nt =
    (ScoreMult pl erUpperBound - ScoreMult pl erLo rBound) / ScoreMult pl er ncre ntsCount
  pr vate val ScoreMult pl er ncre ntDurat on nDays =
    Durat onForD scount ng. nDays / ScoreMult pl er ncre ntsCount.toDouble

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[Seq[FeatureMap]] = {
    val feedbackEntr esByEngage ntType =
      query.features
        .getOrElse(FeatureMap.empty).getOrElse(Feedback toryFeature, Seq.empty)
        .f lter { entry =>
          val t  S nceFeedback = query.queryT  .m nus(entry.t  stamp)
          t  S nceFeedback < Durat onForF lter ng + Durat onForD scount ng &&
          entry.feedbackType == tls.FeedbackType.SeeFe r
        }.groupBy(_.engage ntType)

    val authorsToD scount =
      getUserD scounts(
        query.queryT  ,
        feedbackEntr esByEngage ntType.getOrElse(tls.FeedbackEngage ntType.T et, Seq.empty))
    val l kersToD scount =
      getUserD scounts(
        query.queryT  ,
        feedbackEntr esByEngage ntType.getOrElse(tls.FeedbackEngage ntType.L ke, Seq.empty))
    val follo rsToD scount =
      getUserD scounts(
        query.queryT  ,
        feedbackEntr esByEngage ntType.getOrElse(tls.FeedbackEngage ntType.Follow, Seq.empty))
    val ret etersToD scount =
      getUserD scounts(
        query.queryT  ,
        feedbackEntr esByEngage ntType.getOrElse(tls.FeedbackEngage ntType.Ret et, Seq.empty))

    val featureMaps = cand dates.map { cand date =>
      val mult pl er = getScoreMult pl er(
        cand date,
        authorsToD scount,
        l kersToD scount,
        follo rsToD scount,
        ret etersToD scount
      )
      val score = cand date.features.getOrElse(ScoreFeature, None)
      FeatureMapBu lder().add(ScoreFeature, score.map(_ * mult pl er)).bu ld()
    }

    St ch.value(featureMaps)
  }

  def getScoreMult pl er(
    cand date: Cand dateW hFeatures[T etCand date],
    authorsToD scount: Map[Long, Double],
    l kersToD scount: Map[Long, Double],
    follo rsToD scount: Map[Long, Double],
    ret etersToD scount: Map[Long, Double],
  ): Double = {
    val or g nalAuthor d =
      Cand datesUt l.getOr g nalAuthor d(cand date.features).getOrElse(0L)
    val or g nalAuthorMult pl er = authorsToD scount.getOrElse(or g nalAuthor d, 1.0)

    val l kers = cand date.features.getOrElse(SGSVal dL kedByUser dsFeature, Seq.empty)
    val l kerMult pl ers = l kers.flatMap(l kersToD scount.get)
    val l kerMult pl er =
       f (l kerMult pl ers.nonEmpty && l kers.s ze == l kerMult pl ers.s ze)
        l kerMult pl ers.max
      else 1.0

    val follo rs = cand date.features.getOrElse(SGSVal dFollo dByUser dsFeature, Seq.empty)
    val follo rMult pl ers = follo rs.flatMap(follo rsToD scount.get)
    val follo rMult pl er =
       f (follo rMult pl ers.nonEmpty && follo rs.s ze == follo rMult pl ers.s ze &&
        l kers. sEmpty)
        follo rMult pl ers.max
      else 1.0

    val author d = cand date.features.getOrElse(Author dFeature, None).getOrElse(0L)
    val ret eterMult pl er =
       f (cand date.features.getOrElse( sRet etFeature, false))
        ret etersToD scount.getOrElse(author d, 1.0)
      else 1.0

    or g nalAuthorMult pl er * l kerMult pl er * follo rMult pl er * ret eterMult pl er
  }

  def getUserD scounts(
    queryT  : T  ,
    feedbackEntr es: Seq[FeedbackEntry],
  ): Map[Long, Double] = {
    val userD scounts = mutable.Map[Long, Double]()
    feedbackEntr es
      .collect {
        case FeedbackEntry(_, _, tl.FeedbackEnt y.User d(user d), t  stamp, _) =>
          val t  S nceFeedback = queryT  .m nus(t  stamp)
          val t  S nceD scount ng = t  S nceFeedback - Durat onForF lter ng
          val mult pl er = ((t  S nceD scount ng. nDays / ScoreMult pl er ncre ntDurat on nDays)
            * ScoreMult pl er ncre nt + ScoreMult pl erLo rBound)
          userD scounts.update(user d, mult pl er)
      }
    userD scounts.toMap
  }
}
