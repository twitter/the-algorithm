package com.tw ter.follow_recom ndat ons.common.pred cates.hss

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.ut l.DefaultT  r
 mport com.tw ter.follow_recom ndat ons.common.base.Pred cate
 mport com.tw ter.follow_recom ndat ons.common.base.Pred cateResult
 mport com.tw ter.follow_recom ndat ons.common.base.StatsUt l
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.F lterReason
 mport com.tw ter.follow_recom ndat ons.common.models.F lterReason.Fa lOpen
 mport com.tw ter.hss.ap .thr ftscala.S gnalValue
 mport com.tw ter.hss.ap .thr ftscala.User althS gnal.AgathaCseDouble
 mport com.tw ter.hss.ap .thr ftscala.User althS gnal.NsfwAgathaUserScoreDouble
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.generated.cl ent.hss.user_s gnals.ap . althS gnalsOnUserCl entColumn
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.ut l.logg ng.Logg ng
 mport com.tw ter.ut l.Durat on

 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * F lter out cand dates based on  alth S gnal Store (HSS)  alth s gnals
 */
@S ngleton
case class HssPred cate @ nject() (
   althS gnalsOnUserCl entColumn:  althS gnalsOnUserCl entColumn,
  statsRece ver: StatsRece ver)
    extends Pred cate[(HasCl entContext w h HasParams, Cand dateUser)]
    w h Logg ng {

  pr vate val stats: StatsRece ver = statsRece ver.scope(t .getClass.getNa )

  overr de def apply(
    pa r: (HasCl entContext w h HasParams, Cand dateUser)
  ): St ch[Pred cateResult] = {
    val (request, cand date) = pa r
    StatsUt l.prof leSt ch(
      getHssPred cateResult(request, cand date),
      stats.scope("getHssPred cateResult")
    )
  }

  pr vate def getHssPred cateResult(
    request: HasCl entContext w h HasParams,
    cand date: Cand dateUser
  ): St ch[Pred cateResult] = {

    val hssCseScoreThreshold: Double = request.params(HssPred cateParams.HssCseScoreThreshold)
    val hssNsfwScoreThreshold: Double = request.params(HssPred cateParams.HssNsfwScoreThreshold)
    val t  out: Durat on = request.params(HssPred cateParams.HssAp T  out)

     althS gnalsOnUserCl entColumn.fetc r
      .fetch(cand date. d, Seq(AgathaCseDouble, NsfwAgathaUserScoreDouble))
      .map { fetchResult =>
        fetchResult.v match {
          case So (response) =>
            val agathaCseScoreDouble: Double = user althS gnalValueToDoubleOpt(
              response.s gnalValues.get(AgathaCseDouble)).getOrElse(0d)
            val agathaNsfwScoreDouble: Double = user althS gnalValueToDoubleOpt(
              response.s gnalValues.get(NsfwAgathaUserScoreDouble)).getOrElse(0d)

            stats.stat("agathaCseScoreD str but on").add(agathaCseScoreDouble.toFloat)
            stats.stat("agathaNsfwScoreD str but on").add(agathaNsfwScoreDouble.toFloat)

            /**
             * Only f lter out t  cand date w n   has both h gh Agatha CSE score and NSFW score, as t  Agatha CSE
             * model  s an old one that may not be prec se or have h gh recall.
             */
             f (agathaCseScoreDouble >= hssCseScoreThreshold && agathaNsfwScoreDouble >= hssNsfwScoreThreshold) {
              Pred cateResult. nval d(Set(F lterReason.HssS gnal))
            } else {
              Pred cateResult.Val d
            }
          case None =>
            Pred cateResult.Val d
        }
      }
      .w h n(t  out)(DefaultT  r)
      .rescue {
        case e: Except on =>
          stats.scope("rescued").counter(e.getClass.getS mpleNa ). ncr()
          St ch(Pred cateResult. nval d(Set(Fa lOpen)))
      }
  }

  pr vate def user althS gnalValueToDoubleOpt(s gnalValue: Opt on[S gnalValue]): Opt on[Double] = {
    s gnalValue match {
      case So (S gnalValue.DoubleValue(value)) => So (value)
      case _ => None
    }
  }
}
