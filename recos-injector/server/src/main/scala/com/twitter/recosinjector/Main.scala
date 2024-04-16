package com.tw ter.recos njector

 mport com.tw ter.app.Flag
 mport com.tw ter.f nagle.http.HttpMuxer
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.ut l.ElfOwlF lter
 mport com.tw ter.recos njector.cl ents.G zmoduck
 mport com.tw ter.recos njector.cl ents.RecosHoseEnt  esCac 
 mport com.tw ter.recos njector.cl ents.Soc alGraph
 mport com.tw ter.recos njector.cl ents.T etyp e
 mport com.tw ter.recos njector.cl ents.UrlResolver
 mport com.tw ter.recos njector.conf g._
 mport com.tw ter.recos njector.edges.Soc alWr eEventToUserUserGraphBu lder
 mport com.tw ter.recos njector.edges.T  l neEventToUserT etEnt yGraphBu lder
 mport com.tw ter.recos njector.edges.T etEventToUserT etEnt yGraphBu lder
 mport com.tw ter.recos njector.edges.T etEventToUserUserGraphBu lder
 mport com.tw ter.recos njector.edges.Un f edUserAct onToUserV deoGraphBu lder
 mport com.tw ter.recos njector.edges.Un f edUserAct onToUserAdGraphBu lder
 mport com.tw ter.recos njector.edges.Un f edUserAct onToUserT etGraphPlusBu lder
 mport com.tw ter.recos njector.edges.UserT etEnt yEdgeBu lder
 mport com.tw ter.recos njector.event_processors.Soc alWr eEventProcessor
 mport com.tw ter.recos njector.event_processors.T  l neEventProcessor
 mport com.tw ter.recos njector.event_processors.T etEventProcessor
 mport com.tw ter.recos njector.publ s rs.KafkaEventPubl s r
 mport com.tw ter.recos njector.uua_processors.Un f edUserAct onProcessor
 mport com.tw ter.recos njector.uua_processors.Un f edUserAct onsConsu r
 mport com.tw ter.server.logg ng.{Logg ng => JDK14Logg ng}
 mport com.tw ter.server.Dec derable
 mport com.tw ter.server.Tw terServer
 mport com.tw ter.soc algraph.thr ftscala.Wr eEvent
 mport com.tw ter.t  l neserv ce.thr ftscala.{Event => T  l neEvent}
 mport com.tw ter.t etyp e.thr ftscala.T etEvent
 mport com.tw ter.ut l.Awa 
 mport com.tw ter.ut l.Durat on
 mport java.ut l.concurrent.T  Un 

object Ma n extends Tw terServer w h JDK14Logg ng w h Dec derable { self =>

   mpl c  val stats: StatsRece ver = statsRece ver

  pr vate val dataCenter: Flag[Str ng] = flag("serv ce.cluster", "atla", "Data Center")
  pr vate val serv ceRole: Flag[Str ng] = flag("serv ce.role", "Serv ce Role")
  pr vate val serv ceEnv: Flag[Str ng] = flag("serv ce.env", "Serv ce Env")
  pr vate val serv ceNa : Flag[Str ng] = flag("serv ce.na ", "Serv ce Na ")
  pr vate val shard d = flag("shard d", 0, "Shard  D")
  pr vate val numShards = flag("numShards", 1, "Number of shards for t  serv ce")
  pr vate val truststoreLocat on =
    flag[Str ng]("truststore_locat on", "", "Truststore f le locat on")

  def ma n(): Un  = {
    val serv ce dent f er = Serv ce dent f er(
      role = serv ceRole(),
      serv ce = serv ceNa (),
      env ron nt = serv ceEnv(),
      zone = dataCenter()
    )
    pr ntln("Serv ce dent f er = " + serv ce dent f er.toStr ng)
    log. nfo("Serv ce dent f er = " + serv ce dent f er.toStr ng)

    val shard = shard d()
    val numOfShards = numShards()
    val env ron nt = serv ceEnv()

     mpl c  val conf g: DeployConf g = {
      env ron nt match {
        case "prod" => ProdConf g(serv ce dent f er)(stats)
        case "stag ng" | "devel" => Stag ngConf g(serv ce dent f er)
        case env => throw new Except on(s"Unknown env ron nt $env")
      }
    }

    //  n  al ze t  conf g and wa  for  n  al zat on to f n sh
    Awa .ready(conf g. n ())

    log. nfo(
      "Start ng Recos  njector: env ron nt %s, cl ent d %s",
      env ron nt,
      conf g.recos njectorThr ftCl ent d
    )
    log. nfo("Start ng shard  d: %d of %d shards...".format(shard, numOfShards))

    // Cl ent wrappers
    val cac  = new RecosHoseEnt  esCac (conf g.recos njectorCoreSvcsCac Cl ent)
    val g zmoduck = new G zmoduck(conf g.userStore)
    val soc alGraph = new Soc alGraph(conf g.soc alGraph dStore)
    val t etyp e = new T etyp e(conf g.t etyP eStore)
    val urlResolver = new UrlResolver(conf g.url nfoStore)

    // Edge bu lders
    val userT etEnt yEdgeBu lder = new UserT etEnt yEdgeBu lder(cac , urlResolver)

    // Publ s rs
    val kafkaEventPubl s r = KafkaEventPubl s r(
      "/s/kafka/recom ndat ons:kafka-tls",
      conf g.outputKafkaTop cPref x,
      conf g.recos njectorThr ftCl ent d,
      truststoreLocat on())

    //  ssage Bu lders
    val soc alWr eToUserUser ssageBu lder =
      new Soc alWr eEventToUserUserGraphBu lder()(
        statsRece ver.scope("Soc alWr eEventToUserUserGraphBu lder")
      )

    val t  l neToUserT etEnt y ssageBu lder = new T  l neEventToUserT etEnt yGraphBu lder(
      userT etEnt yEdgeBu lder = userT etEnt yEdgeBu lder
    )(statsRece ver.scope("T  l neEventToUserT etEnt yGraphBu lder"))

    val t etEventToUserT etEnt yGraphBu lder = new T etEventToUserT etEnt yGraphBu lder(
      userT etEnt yEdgeBu lder = userT etEnt yEdgeBu lder,
      t etCreat onStore = conf g.t etCreat onStore,
      dec der = conf g.recos njectorDec der
    )(statsRece ver.scope("T etEventToUserT etEnt yGraphBu lder"))

    val soc alWr eEventProcessor = new Soc alWr eEventProcessor(
      eventBusStreamNa  = s"recos_ njector_soc al_wr e_event_$env ron nt",
      thr ftStruct = Wr eEvent,
      serv ce dent f er = serv ce dent f er,
      kafkaEventPubl s r = kafkaEventPubl s r,
      userUserGraphTop c = KafkaEventPubl s r.UserUserTop c,
      userUserGraph ssageBu lder = soc alWr eToUserUser ssageBu lder
    )(statsRece ver.scope("Soc alWr eEventProcessor"))

    val t etToUserUser ssageBu lder = new T etEventToUserUserGraphBu lder()(
      statsRece ver.scope("T etEventToUserUserGraphBu lder")
    )

    val un f edUserAct onToUserV deoGraphBu lder = new Un f edUserAct onToUserV deoGraphBu lder(
      userT etEnt yEdgeBu lder = userT etEnt yEdgeBu lder
    )(statsRece ver.scope("Un f edUserAct onToUserV deoGraphBu lder"))

    val un f edUserAct onToUserAdGraphBu lder = new Un f edUserAct onToUserAdGraphBu lder(
      userT etEnt yEdgeBu lder = userT etEnt yEdgeBu lder
    )(statsRece ver.scope("Un f edUserAct onToUserAdGraphBu lder"))

    val un f edUserAct onToUserT etGraphPlusBu lder =
      new Un f edUserAct onToUserT etGraphPlusBu lder(
        userT etEnt yEdgeBu lder = userT etEnt yEdgeBu lder
      )(statsRece ver.scope("Un f edUserAct onToUserT etGraphPlusBu lder"))

    // Processors
    val t etEventProcessor = new T etEventProcessor(
      eventBusStreamNa  = s"recos_ njector_t et_events_$env ron nt",
      thr ftStruct = T etEvent,
      serv ce dent f er = serv ce dent f er,
      userUserGraph ssageBu lder = t etToUserUser ssageBu lder,
      userUserGraphTop c = KafkaEventPubl s r.UserUserTop c,
      userT etEnt yGraph ssageBu lder = t etEventToUserT etEnt yGraphBu lder,
      userT etEnt yGraphTop c = KafkaEventPubl s r.UserT etEnt yTop c,
      kafkaEventPubl s r = kafkaEventPubl s r,
      soc alGraph = soc alGraph,
      t etyp e = t etyp e,
      g zmoduck = g zmoduck
    )(statsRece ver.scope("T etEventProcessor"))

    val t  l neEventProcessor = new T  l neEventProcessor(
      eventBusStreamNa  = s"recos_ njector_t  l ne_events_prototype_$env ron nt",
      thr ftStruct = T  l neEvent,
      serv ce dent f er = serv ce dent f er,
      kafkaEventPubl s r = kafkaEventPubl s r,
      userT etEnt yGraphTop c = KafkaEventPubl s r.UserT etEnt yTop c,
      userT etEnt yGraph ssageBu lder = t  l neToUserT etEnt y ssageBu lder,
      dec der = conf g.recos njectorDec der,
      g zmoduck = g zmoduck,
      t etyp e = t etyp e
    )(statsRece ver.scope("T  l neEventProcessor"))

    val eventBusProcessors = Seq(
      t  l neEventProcessor,
      soc alWr eEventProcessor,
      t etEventProcessor
    )

    val uuaProcessor = new Un f edUserAct onProcessor(
      g zmoduck = g zmoduck,
      t etyp e = t etyp e,
      kafkaEventPubl s r = kafkaEventPubl s r,
      userV deoGraphTop c = KafkaEventPubl s r.UserV deoTop c,
      userV deoGraphBu lder = un f edUserAct onToUserV deoGraphBu lder,
      userAdGraphTop c = KafkaEventPubl s r.UserAdTop c,
      userAdGraphBu lder = un f edUserAct onToUserAdGraphBu lder,
      userT etGraphPlusTop c = KafkaEventPubl s r.UserT etPlusTop c,
      userT etGraphPlusBu lder = un f edUserAct onToUserT etGraphPlusBu lder)(
      statsRece ver.scope("Un f edUserAct onProcessor"))

    val uuaConsu r = new Un f edUserAct onsConsu r(uuaProcessor, truststoreLocat on())

    // Start-up  n  and graceful shutdown setup

    // wa  a b  for serv ces to be ready
    Thread.sleep(5000L)

    log. nfo("Start ng t  event processors")
    eventBusProcessors.foreach(_.start())

    log. nfo("Start ng t  uua processors")
    uuaConsu r.atLeastOnceProcessor.start()

    t .addAdm nRoute(ElfOwlF lter.getPostbackRoute())

    onEx  {
      log. nfo("Shutt ng down t  event processors")
      eventBusProcessors.foreach(_.stop())
      log. nfo("Shutt ng down t  uua processors")
      uuaConsu r.atLeastOnceProcessor.close()
      log. nfo("done ex ")
    }

    // Wa  on t  thr ftServer so that shutdownT  out  s respected.
    Awa .result(adm nHttpServer)
  }
}
