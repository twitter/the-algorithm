package com.tw ter.t  l neranker.para ters.ut l

 mport com.tw ter.servo.ut l.Funct onArrow
 mport com.tw ter.t  l neranker.conf g.Runt  Conf gurat on
 mport com.tw ter.t  l neranker.model.RecapQuery
 mport com.tw ter.t  l nes.conf gap .Conf g
 mport com.tw ter.ut l.Future

class RecapQueryParam n  al zer(conf g: Conf g, runt  Conf g: Runt  Conf gurat on)
    extends Funct onArrow[RecapQuery, Future[RecapQuery]] {
  pr vate[t ] val requestContextBu lder =
    new RequestContextBu lder mpl(runt  Conf g.conf gAp Conf gurat on.requestContextFactory)

  def apply(query: RecapQuery): Future[RecapQuery] = {
    requestContextBu lder(So (query.user d), query.dev ceContext).map { baseContext =>
      val params = conf g(baseContext, runt  Conf g.statsRece ver)
      query.copy(params = params)
    }
  }
}
