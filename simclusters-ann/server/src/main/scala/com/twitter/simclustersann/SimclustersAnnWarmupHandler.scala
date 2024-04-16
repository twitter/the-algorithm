package com.tw ter.s mclustersann

 mport com.tw ter. nject.Logg ng
 mport com.tw ter. nject.ut ls.Handler
 mport javax. nject. nject
 mport scala.ut l.control.NonFatal
 mport com.google.common.ut l.concurrent.RateL m er
 mport com.tw ter.convers ons.Durat onOps.r chDurat onFrom nt
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.s mclusters_v2.common.Cluster d
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Awa 
 mport com.tw ter.ut l.ExecutorServ ceFuturePool
 mport com.tw ter.ut l.Future

class S mclustersAnnWarmupHandler @ nject() (
  clusterT etCand datesStore: ReadableStore[Cluster d, Seq[(T et d, Double)]],
  futurePool: ExecutorServ ceFuturePool,
  rateL m er: RateL m er,
  statsRece ver: StatsRece ver)
    extends Handler
    w h Logg ng {

  pr vate val stats = statsRece ver.scope(t .getClass.getNa )

  pr vate val scopedStats = stats.scope("fetchFromCac ")
  pr vate val clusters = scopedStats.counter("clusters")
  pr vate val fetc dKeys = scopedStats.counter("keys")
  pr vate val fa lures = scopedStats.counter("fa lures")
  pr vate val success = scopedStats.counter("success")

  pr vate val S mclustersNumber = 144428

  overr de def handle(): Un  = {
    try {
      val cluster ds = L st.range(1, S mclustersNumber)
      val futures: Seq[Future[Un ]] = cluster ds
        .map { cluster d =>
          clusters. ncr()
          futurePool {
            rateL m er.acqu re()

            Awa .result(
              clusterT etCand datesStore
                .get(cluster d)
                .onSuccess { _ =>
                  success. ncr()
                }
                .handle {
                  case NonFatal(e) =>
                    fa lures. ncr()
                },
              t  out = 10.seconds
            )
            fetc dKeys. ncr()
          }
        }

      Awa .result(Future.collect(futures), t  out = 10.m nutes)

    } catch {
      case NonFatal(e) => error(e.get ssage, e)
    } f nally {
      try {
        futurePool.executor.shutdown()
      } catch {
        case NonFatal(_) =>
      }
       nfo("Warmup done.")
    }
  }
}
