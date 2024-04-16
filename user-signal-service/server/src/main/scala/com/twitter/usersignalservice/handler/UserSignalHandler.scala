package com.tw ter.users gnalserv ce.handler

 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.users gnalserv ce.thr ftscala.BatchS gnalRequest
 mport com.tw ter.users gnalserv ce.thr ftscala.BatchS gnalResponse
 mport com.tw ter.ut l.Future
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.ut l.StatsUt l
 mport com.tw ter.users gnalserv ce.conf g.S gnalFetc rConf g
 mport com.tw ter.users gnalserv ce.base.Query
 mport com.tw ter.users gnalserv ce.thr ftscala.Cl ent dent f er
 mport com.tw ter.users gnalserv ce.thr ftscala.S gnalType
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.T  r
 mport com.tw ter.ut l.T  outExcept on

class UserS gnalHandler(
  s gnalFetc rConf g: S gnalFetc rConf g,
  t  r: T  r,
  stats: StatsRece ver) {
   mport UserS gnalHandler._

  val statsRece ver: StatsRece ver = stats.scope("user-s gnal-serv ce/serv ce")

  def getBatchS gnalsResponse(request: BatchS gnalRequest): Future[Opt on[BatchS gnalResponse]] = {
    StatsUt l.trackOpt onStats(statsRece ver) {
      val allS gnals = request.s gnalRequest.map { s gnalRequest =>
        s gnalFetc rConf g
          .S gnalFetc rMapper(s gnalRequest.s gnalType)
          .get(Query(
            user d = request.user d,
            s gnalType = s gnalRequest.s gnalType,
            maxResults = s gnalRequest.maxResults.map(_.to nt),
            cl ent d = request.cl ent d.getOrElse(Cl ent dent f er.Unknown)
          ))
          .map(s gnalOpt => (s gnalRequest.s gnalType, s gnalOpt))
      }

      Future.collect(allS gnals).map { s gnalsSeq =>
        val s gnalsMap = s gnalsSeq.map {
          case (s gnalType: S gnalType, s gnalsOpt) =>
            (s gnalType, s gnalsOpt.getOrElse(EmptySeq))
        }.toMap
        So (BatchS gnalResponse(s gnalsMap))
      }
    }
  }

  def toReadableStore: ReadableStore[BatchS gnalRequest, BatchS gnalResponse] = {
    new ReadableStore[BatchS gnalRequest, BatchS gnalResponse] {
      overr de def get(request: BatchS gnalRequest): Future[Opt on[BatchS gnalResponse]] = {
        getBatchS gnalsResponse(request).ra seW h n(UserS gnalServ ceT  out)(t  r).rescue {
          case _: T  outExcept on =>
            statsRece ver.counter("endpo ntGetBatchS gnals/fa lure/t  out"). ncr()
            EmptyResponse
          case e =>
            statsRece ver.counter("endpo ntGetBatchS gnals/fa lure/" + e.getClass.getNa ). ncr()
            EmptyResponse
        }
      }
    }
  }
}

object UserS gnalHandler {
  val UserS gnalServ ceT  out: Durat on = 25.m ll seconds

  val EmptySeq: Seq[Noth ng] = Seq.empty
  val EmptyResponse: Future[Opt on[BatchS gnalResponse]] = Future.value(So (BatchS gnalResponse()))
}
