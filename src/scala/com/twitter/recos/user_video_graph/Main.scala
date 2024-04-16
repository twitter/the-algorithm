package com.tw ter.recos.user_v deo_graph

 mport com.tw ter.abdec der.ABDec derFactory
 mport com.tw ter.abdec der.Logg ngABDec der
 mport com.tw ter.app.Flag
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.Thr ftMux
 mport com.tw ter.f nagle.http.HttpMuxer
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.mtls.cl ent.MtlsStackCl ent.MtlsThr ftMuxCl entSyntax
 mport com.tw ter.f nagle.mtls.server.MtlsStackServer._
 mport com.tw ter.f nagle.mux.Cl entD scardedRequestExcept on
 mport com.tw ter.f nagle.mux.transport.Opportun st cTls
 mport com.tw ter.f nagle.serv ce.ReqRep
 mport com.tw ter.f nagle.serv ce.ResponseClass
 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter.f natra.kafka.consu rs.F nagleKafkaConsu rBu lder
 mport com.tw ter.f natra.kafka.doma n.KafkaGroup d
 mport com.tw ter.f natra.kafka.doma n.SeekStrategy
 mport com.tw ter.f natra.kafka.serde.ScalaSerdes
 mport com.tw ter.fr gate.common.ut l.ElfOwlF lter
 mport com.tw ter.fr gate.common.ut l.ElfOwlF lter.ByLdapGroup
 mport com.tw ter.graphjet.b part e.Mult Seg ntPo rLawB part eGraph
 mport com.tw ter.logg ng._
 mport com.tw ter.recos.dec der.Endpo ntLoadS dder
 mport com.tw ter.recos.dec der.UserT etGraphDec der
 mport com.tw ter.recos.graph_common.F nagleStatsRece verWrapper
 mport com.tw ter.recos.graph_common.Mult Seg ntPo rLawB part eGraphBu lder
 mport com.tw ter.recos. nternal.thr ftscala.RecosHose ssage
 mport com.tw ter.recos.user_v deo_graph.RecosConf g._
 mport com.tw ter.recos.user_t et_graph.relatedT etHandlers.Consu rsBasedRelatedT etsHandler
 mport com.tw ter.recos.user_v deo_graph.relatedT etHandlers.T etBasedRelatedT etsHandler
 mport com.tw ter.recos.user_v deo_graph.relatedT etHandlers.ProducerBasedRelatedT etsHandler
 mport com.tw ter.recos.user_v deo_graph.store.UserRecentFollo rsStore
 mport com.tw ter.server.Dec derable
 mport com.tw ter.server.Tw terServer
 mport com.tw ter.server.logg ng.{Logg ng => JDK14Logg ng}
 mport com.tw ter.servo.request._
 mport com.tw ter.servo.ut l.Except onCounter
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.soc algraph.thr ftscala.Soc alGraphServ ce
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Awa 
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.JavaT  r
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.T  r
 mport java.net. netSocketAddress
 mport java.ut l.concurrent.T  Un 
 mport org.apac .kafka.cl ents.CommonCl entConf gs
 mport org.apac .kafka.common.conf g.SaslConf gs
 mport org.apac .kafka.common.conf g.SslConf gs
 mport org.apac .kafka.common.secur y.auth.Secur yProtocol
 mport org.apac .kafka.common.ser al zat on.Str ngDeser al zer
 mport scala.reflect.ClassTag

object Ma n extends Tw terServer w h JDK14Logg ng w h Dec derable {
  prof le =>

  val shard d: Flag[ nt] = flag("shard d", 0, "Shard  D")
  val serv cePort: Flag[ netSocketAddress] =
    flag("serv ce.port", new  netSocketAddress(10143), "Thr ft serv ce port")
  val logD r: Flag[Str ng] = flag("logd r", "recos", "Logg ng d rectory")
  val numShards: Flag[ nt] = flag("numShards", 1, "Number of shards for t  serv ce")
  val truststoreLocat on: Flag[Str ng] =
    flag[Str ng]("truststore_locat on", "", "Truststore f le locat on")
  val hoseNa : Flag[Str ng] =
    flag("hosena ", "recos_ njector_user_user", "t  kafka stream used for  ncom ng edges")

  val dataCenter: Flag[Str ng] = flag("serv ce.cluster", "atla", "Data Center")
  val serv ceRole: Flag[Str ng] = flag("serv ce.role", "Serv ce Role")
  val serv ceEnv: Flag[Str ng] = flag("serv ce.env", "Serv ce Env")
  val serv ceNa : Flag[Str ng] = flag("serv ce.na ", "Serv ce Na ")

  pr vate val maxNumSeg nts =
    flag("maxNumSeg nts", graphBu lderConf g.maxNumSeg nts, "t  number of seg nts  n t  graph")

  pr vate val statsRece verWrapper = F nagleStatsRece verWrapper(statsRece ver)

  /**
   * A Cl entRequestAuthor zer to be used  n a request-author zat on RequestF lter.
   */
  lazy val cl entAuthor zer: Cl entRequestAuthor zer =
    Cl entRequestAuthor zer.observed(
      Cl entRequestAuthor zer.perm ss ve,
      new Cl entRequestObserver(statsRece ver)
    )

  lazy val cl ent d = Cl ent d(s"usert etgraph.${serv ceEnv()}")

  pr vate def makeThr ftCl ent[Thr ftServ ceType: ClassTag](
    dest: Str ng,
    label: Str ng,
    serv ce dent f er: Serv ce dent f er,
    requestT  out: Durat on = 100.m ll seconds
  ): Thr ftServ ceType = {
    Thr ftMux.cl ent
      .w hCl ent d(Cl ent d("usert etgraph.prod"))
      .w hOpportun st cTls(Opportun st cTls.Requ red)
      .w hMutualTls(serv ce dent f er)
      .w hRequestT  out(requestT  out)
      .w hStatsRece ver(statsRece ver.scope("clnt"))
      .w hResponseClass f er {
        case ReqRep(_, Throw(_: Cl entD scardedRequestExcept on)) => ResponseClass. gnorable
      }.bu ld[Thr ftServ ceType](dest, label)
  }

  pr vate val shutdownT  out = flag(
    "serv ce.shutdownT  out",
    5.seconds,
    "Max mum amount of t   to wa  for pend ng requests to complete on shutdown"
  )

  /**
   * Except onCounter for track ng fa lures from RequestHandler(s).
   */
  lazy val except onCounter = new Except onCounter(statsRece ver)

  /**
   * Funct on for translat ng except ons returned by a RequestHandler. Useful
   * for cases w re underly ng except on types should be wrapped  n those
   * def ned  n t  project's Thr ft  DL.
   */
  lazy val translateExcept ons: Part alFunct on[Throwable, Throwable] = {
    case t => t
  }

  val DefaultLdapAccessGroup: Seq[Str ng] = Seq("eng", "cassowary-group", "t  l ne-team")

  // ********* logg ng **********

  lazy val logg ngLevel: Level = Level. NFO
  lazy val recosLogPath: Str ng = logD r() + "/recos.log"
  lazy val graphLogPath: Str ng = logD r() + "/graph.log"
  lazy val accessLogPath: Str ng = logD r() + "/access.log"

  overr de def loggerFactor es: L st[LoggerFactory] =
    L st(
      LoggerFactory(
        level = So (logg ngLevel),
        handlers = Queue ngHandler(
          handler = F leHandler(
            f lena  = recosLogPath,
            level = So (logg ngLevel),
            rollPol cy = Pol cy.H ly,
            rotateCount = 6,
            formatter = new Formatter
          )
        ) :: N l
      ),
      LoggerFactory(
        node = "graph",
        useParents = false,
        level = So (logg ngLevel),
        handlers = Queue ngHandler(
          handler = F leHandler(
            f lena  = graphLogPath,
            level = So (logg ngLevel),
            rollPol cy = Pol cy.H ly,
            rotateCount = 6,
            formatter = new Formatter
          )
        ) :: N l
      ),
      LoggerFactory(
        node = "access",
        useParents = false,
        level = So (logg ngLevel),
        handlers = Queue ngHandler(
          handler = F leHandler(
            f lena  = accessLogPath,
            level = So (logg ngLevel),
            rollPol cy = Pol cy.H ly,
            rotateCount = 6,
            formatter = new Formatter
          )
        ) :: N l
      ),
      LoggerFactory(
        node = "cl ent_event",
        level = So (logg ngLevel),
        useParents = false,
        handlers = Queue ngHandler(
          maxQueueS ze = 10000,
          handler = Scr beHandler(
            category = "cl ent_event",
            formatter = BareFormatter
          )
        ) :: N l
      )
    )
  // ******** Dec der *************

  // ********* ABdec der **********

  val abDec derYmlPath: Str ng = "/usr/local/conf g/abdec der/abdec der.yml"

  val scr beLogger: Opt on[Logger] = So (Logger.get("cl ent_event"))

  val abDec der: Logg ngABDec der =
    ABDec derFactory(
      abDec derYmlPath = abDec derYmlPath,
      scr beLogger = scr beLogger,
      env ron nt = So ("product on")
    ).bu ldW hLogg ng()

  // ********* Recos serv ce **********

  def ma n(): Un  = {
    log. nfo("bu ld ng graph w h maxNumSeg nts = " + prof le.maxNumSeg nts())

     mpl c  val t  r: T  r = new JavaT  r(true)

    val graph = Mult Seg ntPo rLawB part eGraphBu lder(
      graphBu lderConf g.copy(maxNumSeg nts = prof le.maxNumSeg nts()),
      statsRece verWrapper
    )

    val kafkaConf gBu lder = F nagleKafkaConsu rBu lder[Str ng, RecosHose ssage]()
      .dest("/s/kafka/recom ndat ons:kafka-tls")
      .group d(KafkaGroup d(f"user_v deo_graph-${shard d()}%06d"))
      .keyDeser al zer(new Str ngDeser al zer)
      .valueDeser al zer(ScalaSerdes.Thr ft[RecosHose ssage].deser al zer)
      .seekStrategy(SeekStrategy.REW ND)
      .rew ndDurat on(48.h s)
      .w hConf g(CommonCl entConf gs.SECUR TY_PROTOCOL_CONF G, Secur yProtocol.SASL_SSL.toStr ng)
      .w hConf g(SslConf gs.SSL_TRUSTSTORE_LOCAT ON_CONF G, truststoreLocat on())
      .w hConf g(SaslConf gs.SASL_MECHAN SM, SaslConf gs.GSSAP _MECHAN SM)
      .w hConf g(SaslConf gs.SASL_KERBEROS_SERV CE_NAME, "kafka")
      .w hConf g(SaslConf gs.SASL_KERBEROS_SERVER_NAME, "kafka")

    val graphWr er =
      UserV deoGraphWr er(
        shard d().toStr ng,
        serv ceEnv(),
        hoseNa (),
        128, // keep t  or g nal sett ng.
        kafkaConf gBu lder,
        cl ent d.na ,
        statsRece ver,
      )
    graphWr er. n Hose(graph)

    // For MutualTLS
    val serv ce dent f er = Serv ce dent f er(
      role = serv ceRole(),
      serv ce = serv ceNa (),
      env ron nt = serv ceEnv(),
      zone = dataCenter()
    )
    log. nfo(s"Serv ce dent f er = ${serv ce dent f er.toStr ng}")

    val soc alGraphCl ent: Soc alGraphServ ce. thodPerEndpo nt =
      makeThr ftCl ent[Soc alGraphServ ce. thodPerEndpo nt](
        "/s/soc algraph/soc algraph",
        "soc algraph",
        serv ce dent f er)
    val userRecentFollo rsStore: ReadableStore[UserRecentFollo rsStore.Query, Seq[User d]] =
      new UserRecentFollo rsStore(soc alGraphCl ent)

    val t etBasedRelatedT etsHandler = new T etBasedRelatedT etsHandler(graph, statsRece ver)
    val consu rsBasedRelatedT etsHandler =
      new Consu rsBasedRelatedT etsHandler(graph, statsRece ver)
    val producerBasedRelatedT etsHandler =
      new ProducerBasedRelatedT etsHandler(graph, userRecentFollo rsStore, statsRece ver)

    val dec der = UserT etGraphDec der(serv ceEnv(), dataCenter())
    val endpo ntLoadS dder = new Endpo ntLoadS dder(dec der)
    val userV deoGraph =
      new UserV deoGraph(
        t etBasedRelatedT etsHandler,
        producerBasedRelatedT etsHandler,
        consu rsBasedRelatedT etsHandler,
        endpo ntLoadS dder)(t  r) w h Logg ngUserV deoGraph

    val thr ftServer = Thr ftMux.server
      .w hOpportun st cTls(Opportun st cTls.Requ red)
      .w hMutualTls(serv ce dent f er)
      .serve face(serv cePort(), userV deoGraph)

    log. nfo("cl ent d: " + cl ent d.toStr ng)
    log. nfo("serv cePort: " + serv cePort().toStr ng)

    log. nfo("add ng shutdown hook")
    onEx  {
      graphWr er.shutdown()
      thr ftServer.close(shutdownT  out().fromNow)
    }
    log. nfo("added shutdown hook")

    // Wa  on t  thr ftServer so that shutdownT  out  s respected.
    Awa .result(thr ftServer)
  }
}
