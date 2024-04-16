package com.tw ter.fr gate.pushserv ce.pred cate

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.params.PushParams
 mport com.tw ter. rm .pred cate.Na dPred cate
 mport com.tw ter. rm .pred cate.Pred cate
 mport com.tw ter.ut l.Future

object MlModelsHoldbackExper  ntPred cate {

  val na  = "MlModelsHoldbackExper  ntPred cate"

  pr vate val alwaysTruePred = Pred catesForCand date.alwaysTruePushCand datePred cate

  def getPred cateBasedOnCand date(
    pc: PushCand date,
    treat ntPred: Pred cate[PushCand date]
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Future[Pred cate[PushCand date]] = {

    Future
      .jo n(Future.value(pc.target.sk pF lters), pc.target. s nModelExclus onL st)
      .map {
        case (sk pF lters,  s nModelExclus onL st) =>
           f (sk pF lters ||
             s nModelExclus onL st ||
            pc.target.params(PushParams.D sableMl nF lter ngParam) ||
            pc.target.params(PushFeatureSw chParams.D sableMl nF lter ngFeatureSw chParam) ||
            pc.target.params(PushParams.D sableAllRelevanceParam) ||
            pc.target.params(PushParams.D sable avyRank ngParam)) {
            alwaysTruePred
          } else {
            treat ntPred
          }
      }
  }

  def apply()( mpl c  statsRece ver: StatsRece ver): Na dPred cate[PushCand date] = {
    val stats = statsRece ver.scope(s"pred cate_$na ")
    val statsProd = stats.scope("prod")
    val counterAcceptedByModel = statsProd.counter("accepted")
    val counterRejectedByModel = statsProd.counter("rejected")
    val counterHoldback = stats.scope("holdback").counter("all")
    val jo ntDauQual yPred cate = Jo ntDauAndQual yModelPred cate()

    new Pred cate[PushCand date] {
      def apply( ems: Seq[PushCand date]): Future[Seq[Boolean]] = {
        val boolFuts =  ems.map {  em =>
          getPred cateBasedOnCand date( em, jo ntDauQual yPred cate)(statsRece ver)
            .flatMap { pred cate =>
              val pred ct onFut = pred cate.apply(Seq( em)).map(_. adOpt on.getOrElse(false))
              pred ct onFut.foreach { pred ct on =>
                 f ( em.target.params(PushParams.D sableMl nF lter ngParam) ||  em.target.params(
                    PushFeatureSw chParams.D sableMl nF lter ngFeatureSw chParam)) {
                  counterHoldback. ncr()
                } else {
                   f (pred ct on) counterAcceptedByModel. ncr() else counterRejectedByModel. ncr()
                }
              }
              pred ct onFut
            }
        }
        Future.collect(boolFuts)
      }
    }.w hStats(stats)
      .w hNa (na )
  }
}
