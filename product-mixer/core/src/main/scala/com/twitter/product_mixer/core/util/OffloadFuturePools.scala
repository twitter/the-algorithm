package com.tw ter.product_m xer.core.ut l

 mport com.tw ter.f nagle.offload.OffloadFuturePool
 mport com.tw ter.ut l.Future

object OffloadFuturePools {

  def parallel ze[ n, Out](
     nputSeq: Seq[ n],
    transfor r:  n => Out,
    parallel sm:  nt
  ): Future[Seq[Out]] = {
    parallel ze( nputSeq, transfor r.andT n(So (_)), parallel sm, None).map(_.flatten)
  }

  def parallel ze[ n, Out](
     nputSeq: Seq[ n],
    transfor r:  n => Out,
    parallel sm:  nt,
    default: Out
  ): Future[Seq[Out]] = {
    val threadProcessFutures = (0 unt l parallel sm).map {   =>
      OffloadFuturePool.getPool(part  onAndProcess nput( nputSeq, transfor r,  , parallel sm))
    }

    val resultMap = Future.collect(threadProcessFutures).map(_.flatten.toMap)

    Future.collect {
       nputSeq. nd ces.map {  dx =>
        resultMap.map(_.getOrElse( dx, default))
      }
    }
  }

  pr vate def part  onAndProcess nput[ n, Out](
     nputSeq: Seq[ n],
    transfor r:  n => Out,
    thread d:  nt,
    parallel sm:  nt
  ): Seq[( nt, Out)] = {
    part  on nputForThread( nputSeq, thread d, parallel sm)
      .map {
        case ( nputRecord,  dx) =>
          ( dx, transfor r( nputRecord))
      }
  }

  pr vate def part  on nputForThread[ n](
     nputSeq: Seq[ n],
    thread d:  nt,
    parallel sm:  nt
  ): Seq[( n,  nt)] = {
     nputSeq.z pW h ndex
      .f lter {
        case (_,  dx) =>  dx % parallel sm == thread d
        case _ => false
      }
  }
}
