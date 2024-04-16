package com.tw ter.t  l neranker.server

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.mtls.server.MtlsStackServer._
 mport com.tw ter.f nagle.mux
 mport com.tw ter.f nagle.param.Reporter
 mport com.tw ter.f nagle.stats.DefaultStatsRece ver
 mport com.tw ter.f nagle.ut l.NullReporterFactory
 mport com.tw ter.f nagle.L sten ngServer
 mport com.tw ter.f nagle.Serv ceFactory
 mport com.tw ter.f nagle.Thr ftMux
 mport com.tw ter.f nagle.mtls.author zat on.server.MtlsServerSess onTrackerF lter
 mport com.tw ter.f nagle.ssl.Opportun st cTls
 mport com.tw ter.f natra.thr ft.f lters.Logg ngMDCF lter
 mport com.tw ter.f natra.thr ft.f lters.Thr ftMDCF lter
 mport com.tw ter.f natra.thr ft.f lters.Trace dMDCF lter
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.server.Tw terServer
 mport com.tw ter.servo.ut l. mo z ngStatsRece ver
 mport com.tw ter.thr ft bforms. thodOpt ons
 mport com.tw ter.thr ft bforms.Tw terServerThr ft bForms
 mport com.tw ter.t  l neranker.conf g.Runt  Conf gurat on mpl
 mport com.tw ter.t  l neranker.conf g.T  l neRankerFlags
 mport com.tw ter.t  l neranker.thr ftscala
 mport com.tw ter.t  l nes.conf g.DefaultDynam cConf gStoreFactory
 mport com.tw ter.t  l nes.conf g.EmptyDynam cConf gStoreFactory
 mport com.tw ter.t  l nes.conf g.Env
 mport com.tw ter.t  l nes.features.app.Forc bleFeatureValues
 mport com.tw ter.t  l nes.server.Adm nMutableDec ders
 mport com.tw ter.t  l nes.warmup.NoWarmup
 mport com.tw ter.t  l nes.warmup.WarmupFlag
 mport com.tw ter.ut l.Awa 
 mport java.net.SocketAddress
 mport org.apac .thr ft.protocol.TB naryProtocol
 mport org.apac .thr ft.protocol.TCompactProtocol
 mport org.apac .thr ft.protocol.TProtocolFactory

object Ma n extends T  l neRankerServer

class T  l neRankerServer extends {
  overr de val statsRece ver:  mo z ngStatsRece ver = new  mo z ngStatsRece ver(
    DefaultStatsRece ver)
} w h Tw terServer w h Adm nMutableDec ders w h Forc bleFeatureValues w h WarmupFlag {

  val t  l neRankerFlags: T  l neRankerFlags = new T  l neRankerFlags(flag)
  lazy val ma nLogger: Logger = Logger.get("Ma n")

  pr vate[t ] lazy val thr ft bFormsAccess =  f (t  l neRankerFlags.getEnv == Env.local) {
     thodOpt ons.Access.Default
  } else {
     thodOpt ons.Access.ByLdapGroup(Seq("t  l ne-team", "t  l neranker-twf-read"))
  }

  pr vate[t ] def mkThr ft bFormsRoutes() =
    Tw terServerThr ft bForms[thr ftscala.T  l neRanker. thodPerEndpo nt](
      thr ftServ cePort = t  l neRankerFlags.serv cePort().getPort,
      default thodAccess = thr ft bFormsAccess,
       thodOpt ons = T  l neRankerThr ft bForms. thodOpt ons,
      serv ce dent f er = t  l neRankerFlags.serv ce dent f er(),
      opportun st cTlsLevel = Opportun st cTls.Requ red,
    )

  overr de protected def fa lfastOnFlagsNotParsed = true
  overr de val defaultCloseGracePer od = 10.seconds

  pr vate[t ] def mkServer(
    labelSuff x: Str ng,
    socketAddress: SocketAddress,
    protocolFactory: TProtocolFactory,
    serv ceFactory: Serv ceFactory[Array[Byte], Array[Byte]],
    opportun st cTlsLevel: Opportun st cTls.Level,
  ): L sten ngServer = {
    val compressor = Seq(mux.transport.Compress on.lz4Compressor(h ghCompress on = false))
    val decompressor = Seq(mux.transport.Compress on.lz4Decompressor())
    val compress onLevel =
       f (t  l neRankerFlags.enableThr ftmuxCompress on()) {
        mux.transport.Compress onLevel.Des red
      } else {
        mux.transport.Compress onLevel.Off
      }

    val mtlsSess onTrackerF lter =
      new MtlsServerSess onTrackerF lter[mux.Request, mux.Response](statsRece ver)
    val logg ngMDCF lter = { new Logg ngMDCF lter }.toF lter[mux.Request, mux.Response]
    val trace dMDCF lter = { new Trace dMDCF lter }.toF lter[mux.Request, mux.Response]
    val thr ftMDCF lter = { new Thr ftMDCF lter }.toF lter[mux.Request, mux.Response]
    val f lters = mtlsSess onTrackerF lter
      .andT n(logg ngMDCF lter)
      .andT n(trace dMDCF lter)
      .andT n(thr ftMDCF lter)

    Thr ftMux.server
    // By default, f nagle logs except ons to ch ckadee, wh ch  s deprecated and
    // bas cally unused. To avo d wasted over ad,   expl c ly d sable t  reporter.
      .conf gured(Reporter(NullReporterFactory))
      .w hLabel("t  l neranker." + labelSuff x)
      .w hMutualTls(t  l neRankerFlags.getServ ce dent f er)
      .w hOpportun st cTls(opportun st cTlsLevel)
      .w hProtocolFactory(protocolFactory)
      .w hCompress onPreferences.compress on(compress onLevel, compressor)
      .w hCompress onPreferences.decompress on(compress onLevel, decompressor)
      .f ltered(f lters)
      .serve(socketAddress, serv ceFactory)
  }

  def ma n(): Un  = {
    try {
      val parsedOpportun st cTlsLevel = Opportun st cTls.Values
        .f nd(
          _.value.toLo rCase == t  l neRankerFlags.opportun st cTlsLevel().toLo rCase).getOrElse(
          Opportun st cTls.Des red)

      Tw terServerThr ft bForms.addAdm nRoutes(t , mkThr ft bFormsRoutes())
      addAdm nMutableDec derRoutes(t  l neRankerFlags.getEnv)

      val conf gStoreFactory =  f (t  l neRankerFlags.getEnv == Env.local) {
        EmptyDynam cConf gStoreFactory
      } else {
        new DefaultDynam cConf gStoreFactory
      }

      val runt  Conf gurat on = new Runt  Conf gurat on mpl(
        t  l neRankerFlags,
        conf gStoreFactory,
        dec der,
        forcedFeatureValues = getFeatureSw chOverr des,
        statsRece ver
      )

      val t  l neRankerBu lder = new T  l neRankerBu lder(runt  Conf gurat on)

      val warmup =  f (shouldWarmup) {
        new Warmup(
          t  l neRankerBu lder.t  l neRanker,
          runt  Conf gurat on.underly ngCl ents.t  l neRankerForward ngCl ent,
          ma nLogger
        )
      } else {
        new NoWarmup()
      }

      warmup.preb ndWarmup()

      // Create Thr ft serv ces that use t  b nary Thr ft protocol, and t  compact one.
      val server =
        mkServer(
          "b nary",
          t  l neRankerFlags.serv cePort(),
          new TB naryProtocol.Factory(),
          t  l neRankerBu lder.serv ceFactory,
          parsedOpportun st cTlsLevel,
        )

      val compactServer =
        mkServer(
          "compact",
          t  l neRankerFlags.serv ceCompactPort(),
          new TCompactProtocol.Factory(),
          t  l neRankerBu lder.compactProtocolServ ceFactory,
          parsedOpportun st cTlsLevel,
        )

      ma nLogger. nfo(
        s"Thr ft b nary server bound to serv ce port [${t  l neRankerFlags.serv cePort()}]")
      closeOnEx (server)
      ma nLogger. nfo(
        s"Thr ft compact server bound to serv ce port [${t  l neRankerFlags.serv ceCompactPort()}]")
      closeOnEx (compactServer)

      warmup.warmupComplete()

      ma nLogger. nfo("ready: server")
      Awa .ready(server)
      Awa .ready(compactServer)
    } catch {
      case e: Throwable =>
        e.pr ntStackTrace()
        ma nLogger.error(e, s"fa lure  n ma n")
        System.ex (1)
    }
  }
}
