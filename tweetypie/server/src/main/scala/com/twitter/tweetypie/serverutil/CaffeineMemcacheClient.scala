package com.tw ter.t etyp e.serverut l

 mport com.g hub.benmanes.caffe ne.cac .stats.Cac Stats
 mport com.g hub.benmanes.caffe ne.cac .stats.StatsCounter
 mport com.g hub.benmanes.caffe ne.cac .AsyncCac Loader
 mport com.g hub.benmanes.caffe ne.cac .AsyncLoad ngCac 
 mport com.g hub.benmanes.caffe ne.cac .Caffe ne
 mport com.tw ter.f nagle. mcac d.protocol.Value
 mport com.tw ter.f nagle. mcac d.Cl ent
 mport com.tw ter.f nagle. mcac d.GetResult
 mport com.tw ter.f nagle. mcac d.ProxyCl ent
 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.{Prom se => Tw terProm se}
 mport com.tw ter.ut l.logg ng.Logger
 mport java.ut l.concurrent.T  Un .NANOSECONDS
 mport java.ut l.concurrent.CompletableFuture
 mport java.ut l.concurrent.Executor
 mport java.ut l.concurrent.T  Un 
 mport java.ut l.funct on.B Consu r
 mport java.ut l.funct on.Suppl er
 mport java.lang
 mport java.ut l
 mport scala.collect on.JavaConverters._

object Caffe ne mcac Cl ent {
  val logger: Logger = Logger(getClass)

  /**
   *  lper  thod to convert bet en Java 8's CompletableFuture and Tw ter's Future.
   */
  pr vate def toTw terFuture[T](cf: CompletableFuture[T]): Future[T] = {
     f (cf. sDone && !cf. sCompletedExcept onally && !cf. sCancelled) {
      Future.const(Return(cf.get()))
    } else {
      val p = new Tw terProm se[T] w h Tw terProm se. nterruptHandler {
        overr de protected def on nterrupt(t: Throwable): Un  = cf.cancel(true)
      }
      cf.w nComplete(new B Consu r[T, Throwable] {
        overr de def accept(result: T, except on: Throwable): Un  = {
           f (except on != null) {
            p.update fEmpty(Throw(except on))
          } else {
            p.update fEmpty(Return(result))
          }
        }
      })
      p
    }
  }
}

class Caffe ne mcac Cl ent(
  overr de val proxyCl ent: Cl ent,
  val max mumS ze:  nt = 1000,
  val ttl: Durat on = Durat on.fromSeconds(10),
  stats: StatsRece ver = NullStatsRece ver)
    extends ProxyCl ent {
   mport Caffe ne mcac Cl ent._

  pr vate[t ] object Stats extends StatsCounter {
    pr vate val h s = stats.counter("h s")
    pr vate val m ss = stats.counter("m sses")
    pr vate val totalLoadT   = stats.stat("loads")
    pr vate val loadSuccess = stats.counter("loads-success")
    pr vate val loadFa lure = stats.counter("loads-fa lure")
    pr vate val ev ct on = stats.counter("ev ct ons")
    pr vate val ev ct on  ght = stats.counter("ev ct ons-  ght")

    overr de def recordH s( :  nt): Un  = h s. ncr( )
    overr de def recordM sses( :  nt): Un  = m ss. ncr( )
    overr de def recordLoadSuccess(l: Long): Un  = {
      loadSuccess. ncr()
      totalLoadT  .add(NANOSECONDS.toM ll s(l))
    }

    overr de def recordLoadFa lure(l: Long): Un  = {
      loadFa lure. ncr()
      totalLoadT  .add(NANOSECONDS.toM ll s(l))
    }

    overr de def recordEv ct on(): Un  = recordEv ct on(1)
    overr de def recordEv ct on(  ght:  nt): Un  = {
      ev ct on. ncr()
      ev ct on  ght. ncr(  ght)
    }

    /**
     *   are currently not us ng t   thod.
     */
    overr de def snapshot(): Cac Stats = {
      new Cac Stats(0, 0, 0, 0, 0, 0, 0)
    }
  }

  pr vate[t ] object  mcac dAsyncCac Loader extends AsyncCac Loader[Str ng, GetResult] {
    pr vate[t ] val EmptyM sses: Set[Str ng] = Set.empty
    pr vate[t ] val EmptyFa lures: Map[Str ng, Throwable] = Map.empty
    pr vate[t ] val EmptyH s: Map[Str ng, Value] = Map.empty

    overr de def asyncLoad(key: Str ng, executor: Executor): CompletableFuture[GetResult] = {
      val f = new ut l.funct on.Funct on[ut l.Map[Str ng, GetResult], GetResult] {
        overr de def apply(r: ut l.Map[Str ng, GetResult]): GetResult = r.get(key)
      }
      asyncLoadAll(Seq(key).asJava, executor).t nApply(f)
    }

    /**
     * Converts response from mult -key to s ngle key.  mcac  returns t  result
     *  n one struct that conta ns all t  h s, m sses and except ons. Caffe ne
     * requ res a map from a key to t  result, so   do that convers on  re.
     */
    overr de def asyncLoadAll(
      keys: lang. erable[_ <: Str ng],
      executor: Executor
    ): CompletableFuture[ut l.Map[Str ng, GetResult]] = {
      val result = new CompletableFuture[ut l.Map[Str ng, GetResult]]()
      proxyCl ent.getResult(keys.asScala).respond {
        case Return(r) =>
          val map = new ut l.HashMap[Str ng, GetResult]()
          r.h s.foreach {
            case (key, value) =>
              map.put(
                key,
                r.copy(h s = Map(key -> value), m sses = EmptyM sses, fa lures = EmptyFa lures)
              )
          }
          r.m sses.foreach { key =>
            map.put(key, r.copy(h s = EmptyH s, m sses = Set(key), fa lures = EmptyFa lures))
          }
          //   are pass ng through fa lures so that   ma nta n t  contract expected by cl ents.
          // W hout pass ng through t  fa lures, several  tr cs get lost. So  of t se fa lures
          // m ght get cac d. T  cac   s short-l ved, so   are not worr ed w n   does
          // get cac d.
          r.fa lures.foreach {
            case (key, value) =>
              map.put(
                key,
                r.copy(h s = EmptyH s, m sses = EmptyM sses, fa lures = Map(key -> value))
              )
          }
          result.complete(map)
        case Throw(ex) =>
          logger.warn("Error load ng keys from  mcac d", ex)
          result.completeExcept onally(ex)
      }
      result
    }
  }

  pr vate[t ] val cac : AsyncLoad ngCac [Str ng, GetResult] =
    Caffe ne
      .newBu lder()
      .max mumS ze(max mumS ze)
      .refreshAfterWr e(ttl. nM ll seconds * 3 / 4, T  Un .M LL SECONDS)
      .exp reAfterWr e(ttl. nM ll seconds, T  Un .M LL SECONDS)
      .recordStats(new Suppl er[StatsCounter] {
        overr de def get(): StatsCounter = Stats
      })
      .bu ldAsync( mcac dAsyncCac Loader)

  overr de def getResult(keys:  erable[Str ng]): Future[GetResult] = {
    val tw terFuture = toTw terFuture(cac .getAll(keys.asJava))
    tw terFuture
      .map { result =>
        val values = result.values().asScala
        values.reduce(_ ++ _)
      }
  }
}
