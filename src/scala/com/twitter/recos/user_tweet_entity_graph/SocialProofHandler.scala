package com.tw ter.recos.user_t et_ent y_graph

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.graphjet.algor hms.{
  Recom ndat on nfo,
  Recom ndat onType => JavaRecom ndat onType
}
 mport com.tw ter.graphjet.algor hms.soc alproof.{
  Node tadataSoc alProofResult => Ent ySoc alProofJavaResult,
  Soc alProofResult => Soc alProofJavaResult
}
 mport com.tw ter.recos.dec der.UserT etEnt yGraphDec der
 mport com.tw ter.recos.ut l.Stats
 mport com.tw ter.recos.ut l.Stats._
 mport com.tw ter.recos.recos_common.thr ftscala.{Soc alProofType => Soc alProofThr ftType}
 mport com.tw ter.recos.user_t et_ent y_graph.thr ftscala.{
  HashtagRecom ndat on,
  T etRecom ndat on,
  UrlRecom ndat on,
  UserT etEnt yRecom ndat onUn on,
  Recom ndat onSoc alProofRequest => Soc alProofThr ftRequest,
  Recom ndat onSoc alProofResponse => Soc alProofThr ftResponse,
  Recom ndat onType => Thr ftRecom ndat onType
}
 mport com.tw ter.servo.request.RequestHandler
 mport com.tw ter.ut l.{Future, Try}
 mport scala.collect on.JavaConverters._

class Soc alProofHandler(
  t etSoc alProofRunner: T etSoc alProofRunner,
  ent ySoc alProofRunner: Ent ySoc alProofRunner,
  dec der: UserT etEnt yGraphDec der,
  statsRece ver: StatsRece ver)
    extends RequestHandler[Soc alProofThr ftRequest, Soc alProofThr ftResponse] {
  pr vate val stats = statsRece ver.scope(t .getClass.getS mpleNa )

  pr vate def getThr ftSoc alProof(
    ent ySoc alProof: Ent ySoc alProofJavaResult
  ): Map[Soc alProofThr ftType, Map[Long, Seq[Long]]] = {
    val soc alProofAttempt = Try(ent ySoc alProof.getSoc alProof)
      .onFa lure { e =>
        stats.counter(e.getClass.getS mpleNa ). ncr()
      }

    soc alProofAttempt.toOpt on match {
      case So (soc alProof)  f soc alProof. sEmpty =>
        stats.counter(Stats.EmptyResult). ncr()
        Map.empty[Soc alProofThr ftType, Map[Long, Seq[Long]]]
      case So (soc alProof)  f !soc alProof. sEmpty =>
        soc alProof.asScala.map {
          case (soc alProofType, soc alProofUserToT etsMap) =>
            val userToT etsSoc alProof = soc alProofUserToT etsMap.asScala.map {
              case (soc alProofUser, connect ngT ets) =>
                (soc alProofUser.toLong, connect ngT ets.asScala.map(Long2long).toSeq)
            }.toMap
            (Soc alProofThr ftType(soc alProofType.to nt), userToT etsSoc alProof)
        }.toMap
      case _ =>
        Map.empty[Soc alProofThr ftType, Map[Long, Seq[Long]]]
    }
  }

  pr vate def getThr ftSoc alProof(
    t etSoc alProof: Soc alProofJavaResult
  ): Map[Soc alProofThr ftType, Seq[Long]] = {
    val soc alProofAttempt = Try(t etSoc alProof.getSoc alProof)
      .onFa lure { e =>
        stats.counter(e.getClass.getS mpleNa ). ncr()
      }

    soc alProofAttempt.toOpt on match {
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
        Map.empty[Soc alProofThr ftType, Seq[Long]]
    }
  }

  pr vate def getEnt ySoc alProof(
    request: Soc alProofThr ftRequest
  ): Future[Seq[UserT etEnt yRecom ndat onUn on]] = {
    val soc alProofsFuture = ent ySoc alProofRunner(request)

    soc alProofsFuture.map { soc alProofs: Seq[Recom ndat on nfo] =>
      stats.counter(Stats.Served). ncr(soc alProofs.s ze)
      soc alProofs.flatMap { ent ySoc alProof: Recom ndat on nfo =>
        val ent ySoc alProofJavaResult =
          ent ySoc alProof.as nstanceOf[Ent ySoc alProofJavaResult]
         f (ent ySoc alProofJavaResult.getRecom ndat onType == JavaRecom ndat onType.URL) {
          So (
            UserT etEnt yRecom ndat onUn on.UrlRec(
              UrlRecom ndat on(
                ent ySoc alProofJavaResult.getNode tadata d,
                ent ySoc alProofJavaResult.get  ght,
                getThr ftSoc alProof(ent ySoc alProofJavaResult)
              )
            )
          )
        } else  f (ent ySoc alProofJavaResult.getRecom ndat onType == JavaRecom ndat onType.HASHTAG) {
          So (
            UserT etEnt yRecom ndat onUn on.HashtagRec(
              HashtagRecom ndat on(
                ent ySoc alProofJavaResult.getNode tadata d,
                ent ySoc alProofJavaResult.get  ght,
                getThr ftSoc alProof(ent ySoc alProofJavaResult)
              )
            )
          )
        } else {
          None
        }
      }
    }
  }

  pr vate def getT etSoc alProof(
    request: Soc alProofThr ftRequest
  ): Future[Seq[UserT etEnt yRecom ndat onUn on]] = {
    val soc alProofsFuture = t etSoc alProofRunner(request)

    soc alProofsFuture.map { soc alProofs: Seq[Recom ndat on nfo] =>
      stats.counter(Stats.Served). ncr(soc alProofs.s ze)
      soc alProofs.flatMap { t etSoc alProof: Recom ndat on nfo =>
        val t etSoc alProofJavaResult = t etSoc alProof.as nstanceOf[Soc alProofJavaResult]
        So (
          UserT etEnt yRecom ndat onUn on.T etRec(
            T etRecom ndat on(
              t etSoc alProofJavaResult.getNode,
              t etSoc alProofJavaResult.get  ght,
              getThr ftSoc alProof(t etSoc alProofJavaResult)
            )
          )
        )
      }
    }
  }

  def apply(request: Soc alProofThr ftRequest): Future[Soc alProofThr ftResponse] = {
    trackFutureBlockStats(stats) {
      val recom ndat onsW hSoc alProofFut = Future
        .collect {
          request.recom ndat on dsForSoc alProof.keys.map {
            case Thr ftRecom ndat onType.T et  f dec der.t etSoc alProof =>
              getT etSoc alProof(request)
            case (Thr ftRecom ndat onType.Url | Thr ftRecom ndat onType.Hashtag)
                 f dec der.ent ySoc alProof =>
              getEnt ySoc alProof(request)
            case _ =>
              Future.N l
          }.toSeq
        }.map(_.flatten)
      recom ndat onsW hSoc alProofFut.map { recom ndat onsW hSoc alProof =>
        Soc alProofThr ftResponse(recom ndat onsW hSoc alProof)
      }
    }
  }
}
