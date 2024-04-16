package com.tw ter.recos.user_t et_ent y_graph

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.ut l.StatsUt l
 mport com.tw ter.graphjet.algor hms.Recom ndat on nfo
 mport com.tw ter.graphjet.algor hms.soc alproof.{Soc alProofResult => Soc alProofJavaResult}
 mport com.tw ter.recos.dec der.UserT etEnt yGraphDec der
 mport com.tw ter.recos.ut l.Stats
 mport com.tw ter.recos.ut l.Stats._
 mport com.tw ter.recos.recos_common.thr ftscala.{Soc alProofType => Soc alProofThr ftType}
 mport com.tw ter.recos.user_t et_ent y_graph.thr ftscala.T etRecom ndat on
 mport com.tw ter.recos.user_t et_ent y_graph.thr ftscala.{
  Soc alProofRequest => Soc alProofThr ftRequest
}
 mport com.tw ter.recos.user_t et_ent y_graph.thr ftscala.{
  Soc alProofResponse => Soc alProofThr ftResponse
}
 mport com.tw ter.servo.request.RequestHandler
 mport com.tw ter.ut l.Future
 mport scala.collect on.JavaConverters._

class T etSoc alProofHandler(
  t etSoc alProofRunner: T etSoc alProofRunner,
  dec der: UserT etEnt yGraphDec der,
  statsRece ver: StatsRece ver)
    extends RequestHandler[Soc alProofThr ftRequest, Soc alProofThr ftResponse] {
  pr vate val stats = statsRece ver.scope(t .getClass.getS mpleNa )

  def getThr ftSoc alProof(
    t etSoc alProof: Soc alProofJavaResult
  ): Map[Soc alProofThr ftType, Seq[Long]] = {
    Opt on(t etSoc alProof.getSoc alProof) match {
      case So (soc alProof)  f soc alProof. sEmpty =>
        stats.counter(Stats.EmptyResult). ncr()
        Map.empty[Soc alProofThr ftType, Seq[Long]]
      case So (soc alProof)  f !soc alProof. sEmpty =>
        soc alProof.asScala.map {
          case (soc alProofType, connect ngUsers) =>
            (
              Soc alProofThr ftType(soc alProofType.to nt),
              connect ngUsers.asScala.map { Long2long }.toSeq)
        }.toMap
      case _ =>
        throw new Except on("T etSoc alProofHandler gets wrong T etSoc alProof response")
    }
  }

  def apply(request: Soc alProofThr ftRequest): Future[Soc alProofThr ftResponse] = {
    StatsUt l.trackBlockStats(stats) {
       f (dec der.t etSoc alProof) {
        val soc alProofsFuture = t etSoc alProofRunner(request)

        soc alProofsFuture map { soc alProofs: Seq[Recom ndat on nfo] =>
          stats.counter(Stats.Served). ncr(soc alProofs.s ze)
          Soc alProofThr ftResponse(
            soc alProofs.flatMap { t etSoc alProof: Recom ndat on nfo =>
              val t etSoc alProofJavaResult = t etSoc alProof.as nstanceOf[Soc alProofJavaResult]
              So (
                T etRecom ndat on(
                  t etSoc alProofJavaResult.getNode,
                  t etSoc alProofJavaResult.get  ght,
                  getThr ftSoc alProof(t etSoc alProofJavaResult)
                )
              )
            }
          )
        }
      } else {
        Future.value(Soc alProofThr ftResponse())
      }
    }
  }
}
