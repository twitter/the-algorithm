package com.tw ter.t etyp e
package conf g

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.Backoff
 mport com.tw ter.f nagle. mcac d.exp.local mcac dPort
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.ssl.Opportun st cTls
 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter.flockdb.cl ent.thr ftscala.Pr or y
 mport com.tw ter.servo.repos ory.Cac dResult
 mport com.tw ter.servo.ut l.Ava lab l y
 mport com.tw ter.t etyp e.backends._
 mport com.tw ter.t etyp e.cach ng.SoftTtl
 mport com.tw ter.t etyp e.handler.Dupl cateT etF nder
 mport com.tw ter.t etyp e.repos ory.TombstoneTtl
 mport com.tw ter.t etyp e.serv ce._
 mport com.tw ter.t etyp e.storage.ManhattanT etStorageCl ent
 mport com.tw ter.ut l.Durat on

case class  nProcessCac Conf g(ttl: Durat on, max mumS ze:  nt)

class T etServ ceSett ngs(val flags: T etServ ceFlags) {

  /**
   * Convert a Boolean to an Opt on
   * > opt onal(true, "  value")
   * res: So (  value)
   *
   * > opt onal(false, "  value")
   * res: None
   */
  def opt onal[T](b: Boolean, a: => T): Opt on[T] =  f (b) So (a) else None

  /** atla, localhost, etc. */
  val zone: Str ng = flags.zone()

  /** dc  s less spec f c than zone, zone=atla, dc=atl */
  val dc: Str ng = zone.dropR ght(1)

  /** one of: prod, stag ng, dev, testbox */
  val env: Env.Value = flags.env()

  /**  nstance d of t  aurora  nstance */
  lazy val  nstance d:  nt = flags. nstance d()

  /** total number of t etyp e aurora  nstances */
  val  nstanceCount:  nt = flags. nstanceCount()

  /** T  Na  to resolve to f nd t   mcac d cluster */
  val t mcac Dest: Str ng =
    //  f t mcac Dest  s expl c ly set, always prefer that to
    // local mcac dPort.
    flags.t mcac Dest.get
    // Testbox uses t  global flag to spec fy t  locat on of t 
    // local  mcac d  nstance.
      .orElse(local mcac dPort().map("/$/ net/localhost/" + _))
      //  f no expl c  Na   s spec f ed, use t  default.
      .getOrElse(flags.t mcac Dest())

  /** Read/wr e data through Cac  */
  val w hCac : Boolean = flags.w hCac ()

  /**
   * T  TFlock queue to use for background  ndex ng operat ons. For
   * product on, t  should always be t  low pr or y queue, to
   * allow foreground operat ons to be processed f rst.
   */
  val background ndex ngPr or y: Pr or y = flags.background ndex ngPr or y()

  /** Set certa n dec der gates to t  overr dden value */
  val dec derOverr des: Map[Str ng, Boolean] =
    flags.dec derOverr des()

  /** use per host stats? */
  val cl entHostStats: Boolean =
    flags.cl entHostStats()

  val warmupRequestsSett ngs: Opt on[WarmupQuer esSett ngs] =
    opt onal(flags.enableWarmupRequests(), WarmupQuer esSett ngs())

  /** enables request author zat on v a a allowl st */
  val allowl st ngRequ red: Boolean =
    flags.allowl st.get.getOrElse(env == Env.prod)

  /** read rate l m  for unknown cl ents (w n allowl st ngRequ red  s enabled) */
  val nonAllowL stedCl entRateL m PerSec: Double =
    flags.grayL stRateL m ()

  /** enables requests from product on cl ents */
  val allowProduct onCl ents: Boolean =
    env == Env.prod

  /** enables repl cat on v a DRPC */
  val enableRepl cat on: Boolean = flags.enableRepl cat on()

  /** enables fork ng of so  traff c to conf gured target */
  val traff cFork ngEnabled: Boolean =
    env == Env.prod

  val scr beUn queness ds: Boolean =
    env == Env.prod

  /** Cl ent d to send to backend serv ces */
  val thr ftCl ent d: Cl ent d =
    flags.cl ent d.get.map(Cl ent d(_)).getOrElse {
      env match {
        case Env.dev | Env.stag ng => Cl ent d("t etyp e.stag ng")
        case Env.prod => Cl ent d("t etyp e.prod")
      }
    }

  /**
   *  nstead of us ng DRPC for call ng  nto t  async code path, call back  nto t 
   * current  nstance. Used for develop nt and test to ensure log c  n t  current
   *  nstance  s be ng tested.
   */
  val s mulateDeferredrpcCallbacks: Boolean = flags.s mulateDeferredrpcCallbacks()

  /**
   * Cl ent d to set  n 'asynchronous' requests w n s mulateDeferredrpcCallbacks  s
   * true and T etyp e ends up just call ng  self synchronously.
   */
  val deferredrpcCl ent d: Cl ent d = Cl ent d("deferredrpc.prod")

  /**
   * Serv ce dent f er used to enable mTLS
   */
  val serv ce dent f er: Serv ce dent f er = flags.serv ce dent f er()

  /**
   * Dec der sett ngs
   */
  val dec derBaseF lena : Opt on[Str ng] = Opt on(flags.dec derBase())
  val dec derOverlayF lena : Opt on[Str ng] = Opt on(flags.dec derOverlay())
  val vfDec derOverlayF lena : Opt on[Str ng] = flags.vfDec derOverlay.get

  /**
   * Used to determ ne w t r   should fa l requests for T ets that are l kely too  ng
   * to return a non-part al response.   return NotFound for T ets that are dee d too  ng.
   * Used by [[com.tw ter.t etyp e.repos ory.ManhattanT etRepos ory]].
   */
  val shortC rcu L kelyPart alT etReads: Gate[Durat on] = {
    //  nterpret t  flag as a durat on  n m ll seconds
    val ageCe l ng: Durat on = flags.shortC rcu L kelyPart alT etReadsMs().m ll seconds
    Gate(t etAge => t etAge < ageCe l ng)
  }

  // t et-serv ce  nternal sett ngs

  val t etKeyCac Vers on = 1

  /** how often to flush aggregated count updates for t et counts */
  val aggregatedT etCountsFlush nterval: Durat on = 5.seconds

  /** max mum number of keys for wh ch aggregated cac d count updates may be cac d */
  val maxAggregatedCountsS ze = 1000

  /** ramp up per od for dec der ng up forked traff c ( f enabled) to t  full dec dered value */
  val fork ngRampUp: Durat on = 3.m nutes

  /** how long to wa  after startup for serversets to resolve before g v ng up and mov ng on */
  val wa ForServerSetsT  out: Durat on = 120.seconds

  /** number of threads to use  n thread pool for language  dent f cat on */
  val numPengu nThreads = 4

  /** max mum number of t ets that cl ents can request per getT ets RPC call */
  val maxGetT etsRequestS ze = 200

  /** max mum batch s ze for any batc d request (getT ets  s exempt,   has  s own l m  ng) */
  val maxRequestS ze = 200

  /**
   * max mum s ze to allow t  thr ft response buffer to grow before resett ng  .  t   s set to
   * approx mately t  current value of `srv/thr ft/response_payload_bytes.p999`,  an ng roughly
   * 1 out of 1000 requests w ll cause t  buffer to be reset.
   */
  val maxThr ftBufferS ze:  nt = 200 * 1024

  // ********* t  outs and backoffs **********

  /** backoffs for Opt m st cLock ngCac  lockAndSet operat ons */
  val lock ngCac Backoffs: Stream[Durat on] =
    Backoff.exponent alJ tered(10.m ll second, 50.m ll seconds).take(3).toStream

  /** retry once on t  out w h no backoff */
  val defaultT  outBackoffs: Stream[Durat on] = Stream(0.m ll seconds).toStream

  /** backoffs w n user v ew  s m ss ng */
  val g zmoduckM ss ngUserV ewBackoffs: Stream[Durat on] = Backoff.const(10.m ll s).take(3).toStream

  /** backoffs for retry ng fa led async-wr e act ons after f rst retry fa lure */
  val asyncWr eRetryBackoffs: Stream[Durat on] =
    Backoff.exponent al(10.m ll seconds, 2).take(9).toStream.map(_ m n 1.second)

  /** backoffs for retry ng fa led deferredrpc enqueues */
  val deferredrpcBackoffs: Stream[Durat on] =
    Backoff.exponent al(10.m ll seconds, 2).take(3).toStream

  /** backoffs for retry ng fa led cac  updates for repl cated events */
  val repl catedEventCac Backoffs: Stream[Durat on] =
    Backoff.exponent al(100.m ll seconds, 2).take(10).toStream

  val esc rb rdConf g: Esc rb rd.Conf g =
    Esc rb rd.Conf g(
      requestT  out = 200.m ll seconds,
      t  outBackoffs = defaultT  outBackoffs
    )

  val expandodoConf g: Expandodo.Conf g =
    Expandodo.Conf g(
      requestT  out = 300.m ll seconds,
      t  outBackoffs = defaultT  outBackoffs,
      serverErrorBackoffs = Backoff.const(0.m ll s).take(3).toStream
    )

  val creat vesConta nerServ ceConf g: Creat vesConta nerServ ce.Conf g =
    Creat vesConta nerServ ce.Conf g(
      requestT  out = 300.m ll seconds,
      t  outBackoffs = defaultT  outBackoffs,
      serverErrorBackoffs = Backoff.const(0.m ll s).take(3).toStream
    )

  val geoScrubEventStoreConf g: GeoScrubEventStore.Conf g =
    GeoScrubEventStore.Conf g(
      read = GeoScrubEventStore.Endpo ntConf g(
        requestT  out = 200.m ll seconds,
        maxRetryCount = 1
      ),
      wr e = GeoScrubEventStore.Endpo ntConf g(
        requestT  out = 1.second,
        maxRetryCount = 1
      )
    )

  val g zmoduckConf g: G zmoduck.Conf g =
    G zmoduck.Conf g(
      readT  out = 300.m ll seconds,
      wr eT  out = 300.m ll seconds,
      //   bump t  t  out value to 800ms because mod fyAndGet  s called only  n async request path  n GeoScrub daemon
      // and   do not expect sync/realt   apps call ng t  thr ft  thod
      mod fyAndGetT  out = 800.m ll seconds,
      mod fyAndGetT  outBackoffs = Backoff.const(0.m ll s).take(3).toStream,
      defaultT  outBackoffs = defaultT  outBackoffs,
      g zmoduckExcept onBackoffs = Backoff.const(0.m ll s).take(3).toStream
    )

  val l m erBackendConf g: L m erBackend.Conf g =
    L m erBackend.Conf g(
      requestT  out = 300.m ll seconds,
      t  outBackoffs = defaultT  outBackoffs
    )

  val  d a nfoServ ceConf g:  d a nfoServ ce.Conf g =
     d a nfoServ ce.Conf g(
      requestT  out = 300.m ll seconds,
      totalT  out = 500.m ll seconds,
      t  outBackoffs = defaultT  outBackoffs
    )

  val scarecrowConf g: Scarecrow.Conf g =
    Scarecrow.Conf g(
      readT  out = 100.m ll seconds,
      wr eT  out = 400.m ll seconds,
      t  outBackoffs = defaultT  outBackoffs,
      scarecrowExcept onBackoffs = Backoff.const(0.m ll s).take(3).toStream
    )

  val soc alGraphSev ceConf g: Soc alGraphServ ce.Conf g =
    Soc alGraphServ ce.Conf g(
      soc alGraphT  out = 250.m ll seconds,
      t  outBackoffs = defaultT  outBackoffs
    )

  val talonConf g: Talon.Conf g =
    Talon.Conf g(
      shortenT  out = 500.m ll seconds,
      expandT  out = 150.m ll seconds,
      t  outBackoffs = defaultT  outBackoffs,
      trans entErrorBackoffs = Backoff.const(0.m ll s).take(3).toStream
    )

  /**
   * page s ze w n retr ev ng tflock pages for t et delet on and undelet on
   * t et erasures have t  r own page s ze eraseUserT etsPageS ze
   */
  val tflockPageS ze:  nt = flags.tflockPageS ze()

  val tflockReadConf g: TFlock.Conf g =
    TFlock.Conf g(
      requestT  out = 300.m ll seconds,
      t  outBackoffs = defaultT  outBackoffs,
      flockExcept onBackoffs = Backoff.const(0.m ll s).take(3).toStream,
      overCapac yBackoffs = Stream.empty,
      defaultPageS ze = tflockPageS ze
    )

  val tflockWr eConf g: TFlock.Conf g =
    TFlock.Conf g(
      requestT  out = 400.m ll seconds,
      t  outBackoffs = defaultT  outBackoffs,
      flockExcept onBackoffs = Backoff.const(0.m ll s).take(3).toStream,
      overCapac yBackoffs = Backoff.exponent al(10.m ll s, 2).take(3).toStream
    )

  val t  l neServ ceConf g: T  l neServ ce.Conf g = {
    val tlsExcept onBackoffs = Backoff.const(0.m ll s).take(3).toStream
    T  l neServ ce.Conf g(
      wr eRequestPol cy =
        Backend.T  outPol cy(4.seconds) >>>
          T  l neServ ce.Fa lureBackoffsPol cy(
            t  outBackoffs = defaultT  outBackoffs,
            tlsExcept onBackoffs = tlsExcept onBackoffs
          ),
      readRequestPol cy =
        Backend.T  outPol cy(400.m ll seconds) >>>
          T  l neServ ce.Fa lureBackoffsPol cy(
            t  outBackoffs = defaultT  outBackoffs,
            tlsExcept onBackoffs = tlsExcept onBackoffs
          )
    )
  }

  val t etStorageConf g: ManhattanT etStorageCl ent.Conf g = {
    val remoteZone = zone match {
      case "atla" => "pdxa"
      case "pdxa" => "atla"
      case "atla" | "localhost" => "atla"
      case _ =>
        throw new  llegalArgu ntExcept on(s"Cannot conf gure remote DC for unknown zone '$zone'")
    }
    ManhattanT etStorageCl ent.Conf g(
      appl cat on d = "tb rd_mh",
      localDest nat on = "/s/manhattan/cylon.nat ve-thr ft",
      localT  out = 290.m ll seconds,
      remoteDest nat on = s"/srv#/prod/$remoteZone/manhattan/cylon.nat ve-thr ft",
      remoteT  out = 1.second,
      maxRequestsPerBatch = 25,
      serv ce dent f er = serv ce dent f er,
      opportun st cTlsLevel = Opportun st cTls.Requ red
    )
  }

  val user mageServ ceConf g: User mageServ ce.Conf g =
    User mageServ ce.Conf g(
      processT et d aT  out = 5.seconds,
      updateT et d aT  out = 2.seconds,
      t  outBackoffs = defaultT  outBackoffs
    )

  val adsLogg ngCl entTop cNa  = env match {
    case Env.prod => "ads_cl ent_callback_prod"
    case Env.dev | Env.stag ng => "ads_cl ent_callback_stag ng"
  }

  /** Delay bet en success ve cascadedDeleteT et calls w n delet ng ret ets.  Appl ed v a dec der. */
  val ret etDelet onDelay: Durat on = 20.m ll seconds

  /**
   * Delay to sleep before each t et delet on of an eraseUserT ets request.
   * T   s a s mple rate l m  ng  chan sm. T  long term solut on  s
   * to move async endpo nts l ke user erasures and ret et delet ons out
   * of t  t  ma n t etyp e cluster and  nto an async cluster w h f rst class
   * rate l m  ng support
   */
  val eraseUserT etsDelay: Durat on = 100.m ll seconds

  val eraseUserT etsPageS ze = 100

  val getStoredT etsByUserPageS ze = 20
  val getStoredT etsByUserMaxPages = 30

  // ********* ttls **********

  // Unfortunately, t  tombstone TTL appl es equally to t  case
  // w re t  t et was deleted and t  case that t  t et does not
  // ex st or  s unava lable.  f   could d fferent ate bet en those
  // cases,  'd cac  deleted for a long t   and not
  // found/unava lable for a short t  .   chose 100
  // m ll seconds for t  m n mum TTL because t re are known cases  n
  // wh ch a not found result can be erroneously wr ten to cac  on
  // t et creat on. T  m n mum TTL  s a trade-off bet en a
  // thunder ng  rd of database requests from cl ents that just got
  // t  fanned-out t et and t  w ndow for wh ch t se  ncons stent
  // results w ll be ava lable.
  val t etTombstoneTtl: Cac dResult.Cac dNotFound[T et d] => Durat on =
    TombstoneTtl.l near(m n = 100.m ll seconds, max = 1.day, from = 5.m nutes, to = 5.h s)

  val t et mcac Ttl: Durat on = 14.days
  val url mcac Ttl: Durat on = 1.h 
  val url mcac SoftTtl: Durat on = 1.h 
  val dev ceS ce mcac Ttl: Durat on = 12.h s
  val dev ceS ce mcac SoftTtl: SoftTtl.ByAge[Noth ng] =
    SoftTtl.ByAge(softTtl = 1.h , j ter = 1.m nute)
  val dev ceS ce nProcessTtl: Durat on = 8.h s
  val dev ceS ce nProcessSoftTtl: Durat on = 30.m nutes
  val place mcac Ttl: Durat on = 1.day
  val place mcac SoftTtl: SoftTtl.ByAge[Noth ng] =
    SoftTtl.ByAge(softTtl = 3.h s, j ter = 1.m nute)
  val card mcac Ttl: Durat on = 20.m nutes
  val card mcac SoftTtl: Durat on = 30.seconds
  val t etCreateLock ng mcac Ttl: Durat on = 10.seconds
  val t etCreateLock ng mcac LongTtl: Durat on = 12.h s
  val geoScrub mcac Ttl: Durat on = 30.m nutes

  val t etCounts mcac Ttl: Durat on = 24.h s
  val t etCounts mcac NonZeroSoftTtl: Durat on = 3.h s
  val t etCounts mcac ZeroSoftTtl: Durat on = 7.h s

  val cac Cl entPend ngRequestL m :  nt = flags. mcac Pend ngRequestL m ()

  val dev ceS ce nProcessCac MaxS ze = 10000

  val  nProcessCac Conf gOpt: Opt on[ nProcessCac Conf g] =
     f (flags.enable nProcessCac ()) {
      So (
         nProcessCac Conf g(
          ttl = flags. nProcessCac TtlMs().m ll seconds,
          max mumS ze = flags. nProcessCac S ze()
        )
      )
    } else {
      None
    }

  // Beg n return ng OverCapac y for t et repo w n cac  SR falls below 95%,
  // Scale to reject ng 95% of requests w n cac  SR <= 80%
  val t etCac Ava lab l yFromSuccessRate: Double => Double =
    Ava lab l y.l nearlyScaled(0.95, 0.80, 0.05)

  // ******* repos ory chunk ng s ze ********

  val t etCountsRepoChunkS ze = 6
  // n t  s `t etCountsRepoChunkS ze`, so chunk ng at h g r level does not
  // generate small batc s at lo r level.
  val t etCountsCac ChunkS ze = 18

  val dupl cateT etF nderSett ngs: Dupl cateT etF nder.Sett ngs =
    Dupl cateT etF nder.Sett ngs(numT etsToC ck = 10, maxDupl cateAge = 12.h s)

  val backendWarmupSett ngs: Warmup.Sett ngs =
    Warmup.Sett ngs(
      // Try for t nty seconds to warm up t  backends before g v ng
      // up.
      maxWarmupDurat on = 20.seconds,
      // Only allow up to 50 outstand ng warmup requests of any k nd
      // to be outstand ng at a t  .
      maxOutstand ngRequests = 50,
      // T se t  outs are just over t  p999 latency observed  n ATLA
      // for requests to t se backends.
      requestT  outs = Map(
        "expandodo" -> 120.m ll seconds,
        "geo_relevance" -> 50.m ll seconds,
        "g zmoduck" -> 200.m ll seconds,
        " mcac " -> 50.m ll seconds,
        "scarecrow" -> 120.m ll seconds,
        "soc algraphserv ce" -> 180.m ll seconds,
        "talon" -> 70.m ll seconds,
        "tflock" -> 320.m ll seconds,
        "t  l neserv ce" -> 200.m ll seconds,
        "t etstorage" -> 50.m ll seconds
      ),
      rel ab l y = Warmup.Rel ably(
        // Cons der a backend war d up  f 99% of requests are succeed ng.
        rel ab l yThreshold = 0.99,
        // W n perform ng warmup, use a max mum of 10 concurrent
        // requests to each backend.
        concurrency = 10,
        // Do not allow more than t  many attempts to perform t 
        // warmup act on before g v ng up.
        maxAttempts = 1000
      )
    )
}
