package com.tw ter.t  l neranker.para ters.revchron

 mport com.tw ter.t  l neranker.conf g.Runt  Conf gurat on
 mport com.tw ter.t  l neranker.dec der.Dec derKey
 mport com.tw ter.t  l neranker.model._
 mport com.tw ter.t  l neranker.para ters.ut l.RequestContextBu lder
 mport com.tw ter.t  l nes.conf gap .Conf g
 mport com.tw ter.t  l nes.dec der.FeatureValue
 mport com.tw ter.ut l.Future

object ReverseChronT  l neQueryContextBu lder {
  val MaxCountL m Key: Seq[Str ng] = Seq("search_request_max_count_l m ")
}

class ReverseChronT  l neQueryContextBu lder(
  conf g: Conf g,
  runt  Conf g: Runt  Conf gurat on,
  requestContextBu lder: RequestContextBu lder) {

   mport ReverseChronT  l neQueryContext._
   mport ReverseChronT  l neQueryContextBu lder._

  pr vate val maxCountMult pl er = FeatureValue(
    runt  Conf g.dec derGateBu lder,
    Dec derKey.Mult pl erOfMater al zat onT etsFetc d,
    MaxCountMult pl er,
    value => (value / 100.0)
  )

  pr vate val backf llF lteredEntr esGate =
    runt  Conf g.dec derGateBu lder.l nearGate(Dec derKey.Backf llF lteredEntr es)

  pr vate val t etsF lter ngLossageThresholdPercent = FeatureValue(
    runt  Conf g.dec derGateBu lder,
    Dec derKey.T etsF lter ngLossageThreshold,
    T etsF lter ngLossageThresholdPercent,
    value => (value / 100)
  )

  pr vate val t etsF lter ngLossageL m Percent = FeatureValue(
    runt  Conf g.dec derGateBu lder,
    Dec derKey.T etsF lter ngLossageL m ,
    T etsF lter ngLossageL m Percent,
    value => (value / 100)
  )

  pr vate def getMaxCountFromConf gStore():  nt = {
    runt  Conf g.conf gStore.getAs nt(MaxCountL m Key).getOrElse(MaxCount.default)
  }

  def apply(query: ReverseChronT  l neQuery): Future[ReverseChronT  l neQueryContext] = {
    requestContextBu lder(So (query.user d), dev ceContext = None).map { baseContext =>
      val params = conf g(baseContext, runt  Conf g.statsRece ver)

      new ReverseChronT  l neQueryContext mpl(
        query,
        getMaxCount = () => getMaxCountFromConf gStore(),
        getMaxCountMult pl er = () => maxCountMult pl er(),
        getMaxFollo dUsers = () => params(ReverseChronParams.MaxFollo dUsersParam),
        getReturnEmptyW nOverMaxFollows =
          () => params(ReverseChronParams.ReturnEmptyW nOverMaxFollowsParam),
        getD rectedAtNarrowast ngV aSearch =
          () => params(ReverseChronParams.D rectedAtNarrowcast ngV aSearchParam),
        getPostF lter ngBasedOnSearch tadataEnabled =
          () => params(ReverseChronParams.PostF lter ngBasedOnSearch tadataEnabledParam),
        getBackf llF lteredEntr es = () => backf llF lteredEntr esGate(),
        getT etsF lter ngLossageThresholdPercent = () => t etsF lter ngLossageThresholdPercent(),
        getT etsF lter ngLossageL m Percent = () => t etsF lter ngLossageL m Percent()
      )
    }
  }
}
