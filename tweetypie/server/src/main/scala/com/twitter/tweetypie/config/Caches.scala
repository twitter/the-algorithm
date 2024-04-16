package com.tw ter.t etyp e
package conf g

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.Backoff
 mport com.tw ter.f nagle. mcac d
 mport com.tw ter.f nagle.stats.Stat
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.servo.cac .{Ser al zer => Cac Ser al zer, _}
 mport com.tw ter.t etyp e.cl ent_ d.Cl ent d lper
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.handler.Cac BasedT etCreat onLock
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.serverut l._
 mport com.tw ter.t etyp e.thr ftscala._
 mport com.tw ter.t etyp e.ut l._
 mport com.tw ter.ut l.T  r

/**
 * Prov des conf gured cac s (most backed by  mcac d) wrapped w h appropr ate  tr cs and locks.
 *
 * All  mcac d-backed cac s share:
 *     - one F nagle  mcac d cl ent from backends. mcac Cl ent
 *     - one  n  mory caffe ne cac 
 *     - one T mcac  pool
 *
 * Each  mcac d-backed cac  spec al zat on prov des  s own:
 *     - key pref x or "na space"
 *     - value ser al zer/deser al zer
 *     - stats scope
 *     - log na 
 */
tra  Cac s {
  val  mcac dCl entW h nProcessCach ng:  mcac d.Cl ent
  val t etCac : Lock ngCac [T etKey, Cac d[Cac dT et]]
  val t etResultCac : Lock ngCac [T et d, Cac d[T etResult]]
  val t etDataCac : Lock ngCac [T et d, Cac d[T etData]]
  val t etCreateLockerCac : Cac [T etCreat onLock.Key, T etCreat onLock.State]
  val t etCountsCac : Lock ngCac [T etCountKey, Cac d[Count]]
  val dev ceS ce nProcessCac : Lock ngCac [Str ng, Cac d[Dev ceS ce]]
  val geoScrubCac : Lock ngCac [User d, Cac d[T  ]]
}

object Cac s {
  object NoCac  extends Cac s {
    overr de val  mcac dCl entW h nProcessCach ng:  mcac d.Cl ent = new Null mcac Cl ent()
    pr vate val toLock ngCac : Lock ngCac Factory = NonLock ngCac Factory
    val t etCac : Lock ngCac [T etKey, Cac d[Cac dT et]] =
      toLock ngCac (new NullCac )
    val t etResultCac : Lock ngCac [T et d, Cac d[T etResult]] =
      toLock ngCac (new NullCac )
    val t etDataCac : Lock ngCac [T et d, Cac d[T etData]] =
      toLock ngCac (new NullCac )
    val t etCreateLockerCac : Cac [T etCreat onLock.Key, T etCreat onLock.State] =
      new NullCac 
    val t etCountsCac : Lock ngCac [T etCountKey, Cac d[Count]] =
      toLock ngCac (new NullCac )
    val dev ceS ce nProcessCac : Lock ngCac [Str ng, Cac d[Dev ceS ce]] =
      toLock ngCac (new NullCac )
    val geoScrubCac : Lock ngCac [User d, Cac d[T  ]] =
      toLock ngCac (new NullCac )
  }

  def apply(
    sett ngs: T etServ ceSett ngs,
    stats: StatsRece ver,
    t  r: T  r,
    cl ents: BackendCl ents,
    t etKeyFactory: T etKeyFactory,
    dec derGates: T etyp eDec derGates,
    cl ent d lper: Cl ent d lper,
  ): Cac s = {
    val cac sStats = stats.scope("cac s")
    val cac s nprocessStats = cac sStats.scope(" nprocess")
    val cac s mcac Stats = cac sStats.scope(" mcac ")
    val cac s mcac Observer = new StatsRece verCac Observer(cac sStats, 10000, " mcac ")
    val cac s mcac T etStats = cac s mcac Stats.scope("t et")
    val cac s nprocessDev ceS ceStats = cac s nprocessStats.scope("dev ce_s ce")
    val cac s mcac CountStats = cac s mcac Stats.scope("count")
    val cac s mcac T etCreateStats = cac s mcac Stats.scope("t et_create")
    val cac s mcac GeoScrubStats = cac s mcac Stats.scope("geo_scrub")
    val  mcac Cl ent = cl ents. mcac Cl ent

    val caff ene mcac dCl ent = sett ngs. nProcessCac Conf gOpt match {
      case So ( nProcessCac Conf g) =>
        new Caffe ne mcac Cl ent(
          proxyCl ent =  mcac Cl ent,
           nProcessCac Conf g.max mumS ze,
           nProcessCac Conf g.ttl,
          cac s mcac Stats.scope("caffe ne")
        )
      case None =>
         mcac Cl ent
    }

    val observed mcac W hCaffe neCl ent =
      new Observable mcac (
        new F nagle mcac (
          caff ene mcac dCl ent
        ),
        cac s mcac Observer
      )

    def observeCac [K, V](
      cac : Cac [K, V],
      stats: StatsRece ver,
      logNa : Str ng,
      w ndowS ze:  nt = 10000
    ) =
      ObservableCac (
        cac ,
        stats,
        w ndowS ze,
        // Need to use an old-school c.t.logg ng.Logger because that's what servo needs
        com.tw ter.logg ng.Logger(s"com.tw ter.t etyp e.cac .$logNa ")
      )

    def mkCac [K, V](
      ttl: Durat on,
      ser al zer: Cac Ser al zer[V],
      perCac Stats: StatsRece ver,
      logNa : Str ng,
      w ndowS ze:  nt = 10000
    ): Cac [K, V] = {
      observeCac (
        new  mcac Cac [K, V](
          observed mcac W hCaffe neCl ent,
          ttl,
          ser al zer
        ),
        perCac Stats,
        logNa ,
        w ndowS ze
      )
    }

    def toLock ngCac [K, V](
      cac : Cac [K, V],
      stats: StatsRece ver,
      backoffs: Stream[Durat on] = sett ngs.lock ngCac Backoffs
    ): Lock ngCac [K, V] =
      new Opt m st cLock ngCac (
        underly ngCac  = cac ,
        backoffs = Backoff.fromStream(backoffs),
        observer = new Opt m st cLock ngCac Observer(stats),
        t  r = t  r
      )

    def mkLock ngCac [K, V](
      ttl: Durat on,
      ser al zer: Cac Ser al zer[V],
      stats: StatsRece ver,
      logNa : Str ng,
      w ndowS ze:  nt = 10000,
      backoffs: Stream[Durat on] = sett ngs.lock ngCac Backoffs
    ): Lock ngCac [K, V] =
      toLock ngCac (
        mkCac (ttl, ser al zer, stats, logNa , w ndowS ze),
        stats,
        backoffs
      )

    def trackT   nCac [K, V](
      cac : Cac [K, Cac d[V]],
      stats: StatsRece ver
    ): Cac [K, Cac d[V]] =
      new Cac Wrapper[K, Cac d[V]] {
        val ageStat: Stat = stats.stat("t  _ n_cac _ms")
        val underly ngCac : Cac [K, Cac d[V]] = cac 

        overr de def get(keys: Seq[K]): Future[KeyValueResult[K, Cac d[V]]] =
          underly ngCac .get(keys).onSuccess(record)

        pr vate def record(res: KeyValueResult[K, Cac d[V]]): Un  = {
          val now = T  .now
          for (c <- res.found.values) {
            ageStat.add(c.cac dAt.unt l(now). nM ll seconds)
          }
        }
      }

    new Cac s {
      overr de val  mcac dCl entW h nProcessCach ng:  mcac d.Cl ent = caff ene mcac dCl ent

      pr vate val observ ngT etCac : Cac [T etKey, Cac d[Cac dT et]] =
        trackT   nCac (
          mkCac (
            ttl = sett ngs.t et mcac Ttl,
            ser al zer = Ser al zer.Cac dT et.Cac dCompact,
            perCac Stats = cac s mcac T etStats,
            logNa  = " mcac T etCac "
          ),
          cac s mcac T etStats
        )

      // Wrap t  t et cac  w h a wrapper that w ll scr be t  cac  wr es
      // that happen to a fract on of t ets. T  was added as part of t 
      //  nvest gat on  nto m ss ng place  ds and cac   ncons stenc es that
      //  re d scovered by t  add  onal f elds hydrator.
      pr vate[t ] val wr eLogg ngT etCac  =
        new Scr beT etCac Wr es(
          underly ngCac  = observ ngT etCac ,
          log ngT etCac Wr es = dec derGates.log ngT etCac Wr es,
          logT etCac Wr es = dec derGates.logT etCac Wr es
        )

      val t etCac : Lock ngCac [T etKey, Cac d[Cac dT et]] =
        toLock ngCac (
          cac  = wr eLogg ngT etCac ,
          stats = cac s mcac T etStats
        )

      val t etDataCac : Lock ngCac [T et d, Cac d[T etData]] =
        toLock ngCac (
          cac  = T etDataCac (t etCac , t etKeyFactory.from d),
          stats = cac s mcac T etStats
        )

      val t etResultCac : Lock ngCac [T et d, Cac d[T etResult]] =
        toLock ngCac (
          cac  = T etResultCac (t etDataCac ),
          stats = cac s mcac T etStats
        )

      val t etCountsCac : Lock ngCac [T etCountKey, Cac d[Count]] =
        mkLock ngCac (
          ttl = sett ngs.t etCounts mcac Ttl,
          ser al zer = Ser al zers.Cac dLong.Compact,
          stats = cac s mcac CountStats,
          logNa  = " mcac T etCountCac ",
          w ndowS ze = 1000,
          backoffs = Backoff.l near(0.m ll s, 2.m ll s).take(2).toStream
        )

      val t etCreateLockerCac : Cac [T etCreat onLock.Key, T etCreat onLock.State] =
        observeCac (
          new TtlCac ToCac (
            underly ngCac  = new KeyValueTransform ngTtlCac (
              underly ngCac  = observed mcac W hCaffe neCl ent,
              transfor r = T etCreat onLock.State.Ser al zer,
              underly ngKey = (_: T etCreat onLock.Key).toStr ng
            ),
            ttl = Cac BasedT etCreat onLock.ttlChooser(
              shortTtl = sett ngs.t etCreateLock ng mcac Ttl,
              longTtl = sett ngs.t etCreateLock ng mcac LongTtl
            )
          ),
          stats = cac s mcac T etCreateStats,
          logNa  = " mcac T etCreateLock ngCac ",
          w ndowS ze = 1000
        )

      val dev ceS ce nProcessCac : Lock ngCac [Str ng, Cac d[Dev ceS ce]] =
        toLock ngCac (
          observeCac (
            new Exp r ngLruCac (
              ttl = sett ngs.dev ceS ce nProcessTtl,
              max mumS ze = sett ngs.dev ceS ce nProcessCac MaxS ze
            ),
            stats = cac s nprocessDev ceS ceStats,
            logNa  = " nprocessDev ceS ceCac "
          ),
          stats = cac s nprocessDev ceS ceStats
        )

      val geoScrubCac : Lock ngCac [User d, Cac d[T  ]] =
        toLock ngCac [User d, Cac d[T  ]](
          new KeyTransform ngCac (
            mkCac [GeoScrubT  stampKey, Cac d[T  ]](
              ttl = sett ngs.geoScrub mcac Ttl,
              ser al zer = Ser al zer.toCac d(Cac Ser al zer.T  ),
              perCac Stats = cac s mcac GeoScrubStats,
              logNa  = " mcac GeoScrubCac "
            ),
            (user d: User d) => GeoScrubT  stampKey(user d)
          ),
          cac s mcac GeoScrubStats
        )
    }
  }
}
