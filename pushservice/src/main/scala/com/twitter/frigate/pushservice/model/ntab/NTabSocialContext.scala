package com.tw ter.fr gate.pushserv ce.model.ntab

 mport com.tw ter.fr gate.common.base.Soc alContextAct ons
 mport com.tw ter.fr gate.common.base.Soc alContextUserDeta ls
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.ut l.Cand dateUt l
 mport com.tw ter.ut l.Future

tra  NTabSoc alContext {
  self: PushCand date w h Soc alContextAct ons w h Soc alContextUserDeta ls =>

  pr vate def ntabD splayUser ds: Seq[Long] =
    soc alContextUser ds.take(ntabD splayUser dsLength)

  def ntabD splayUser dsLength:  nt =
     f (soc alContextUser ds.s ze == 2) 2 else 1

  def ntabD splayNa sAnd ds: Future[Seq[(Str ng, Long)]] =
    scUserMap.map { userObjMap =>
      ntabD splayUser ds.flatMap {  d =>
        userObjMap( d).flatMap(_.prof le.map(_.na )).map { na  => (na ,  d) }
      }
    }

  def rankedNtabD splayNa sAnd ds(defaultToRecency: Boolean): Future[Seq[(Str ng, Long)]] =
    scUserMap.flatMap { userObjMap =>
      val rankedSoc alContextAct v yFut =
        Cand dateUt l.getRankedSoc alContext(
          soc alContextAct ons,
          target.seedsW h  ght,
          defaultToRecency)
      rankedSoc alContextAct v yFut.map { rankedSoc alContextAct v y =>
        val ntabD splayUser ds =
          rankedSoc alContextAct v y.map(_.user d).take(ntabD splayUser dsLength)
        ntabD splayUser ds.flatMap {  d =>
          userObjMap( d).flatMap(_.prof le.map(_.na )).map { na  => (na ,  d) }
        }
      }
    }

  def ot rCount: Future[ nt] =
    ntabD splayNa sAnd ds.map {
      case na sW h dSeq =>
        Math.max(0, soc alContextUser ds.length - na sW h dSeq.s ze)
    }
}
