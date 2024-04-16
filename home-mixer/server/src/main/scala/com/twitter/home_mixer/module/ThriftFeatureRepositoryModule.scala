package com.tw ter.ho _m xer.module

 mport com.google. nject.Prov des
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.convers ons.PercentOps._
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter.graph_feature_serv ce.{thr ftscala => gfs}
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.Earlyb rdRepos ory
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.GraphTwoHopRepos ory
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s. nterestsThr ftServ ceCl ent
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.T etyp eContentRepos ory
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.UserFollo dTop c dsRepos ory
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.UtegSoc alProofRepos ory
 mport com.tw ter.ho _m xer.ut l.earlyb rd.Earlyb rdRequestUt l
 mport com.tw ter.ho _m xer.ut l.t etyp e.RequestF elds
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter. nterests.{thr ftscala =>  nt}
 mport com.tw ter.product_m xer.shared_l brary. mcac d_cl ent. mcac dCl entBu lder
 mport com.tw ter.product_m xer.shared_l brary.thr ft_cl ent.F nagleThr ftCl entBu lder
 mport com.tw ter.product_m xer.shared_l brary.thr ft_cl ent. dempotent
 mport com.tw ter.recos.recos_common.{thr ftscala => rc}
 mport com.tw ter.recos.user_t et_ent y_graph.{thr ftscala => uteg}
 mport com.tw ter.search.earlyb rd.{thr ftscala => eb}
 mport com.tw ter.servo.cac .Cac d
 mport com.tw ter.servo.cac .Cac dSer al zer
 mport com.tw ter.servo.cac .F nagle mcac Factory
 mport com.tw ter.servo.cac . mcac Cac Factory
 mport com.tw ter.servo.cac .NonLock ngCac 
 mport com.tw ter.servo.cac .Thr ftSer al zer
 mport com.tw ter.servo.keyvalue.KeyValueResultBu lder
 mport com.tw ter.servo.repos ory.Cach ngKeyValueRepos ory
 mport com.tw ter.servo.repos ory.Chunk ngStrategy
 mport com.tw ter.servo.repos ory.KeyValueRepos ory
 mport com.tw ter.servo.repos ory.KeyValueResult
 mport com.tw ter.servo.repos ory.keysAsQuery
 mport com.tw ter.spam.rtf.{thr ftscala => sp}
 mport com.tw ter.t etyp e.{thr ftscala => tp}
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.Return
 mport javax. nject.Na d
 mport javax. nject.S ngleton
 mport org.apac .thr ft.protocol.TCompactProtocol

object Thr ftFeatureRepos oryModule extends Tw terModule {

  pr vate val DefaultRPCChunkS ze = 50
  pr vate val GFS nteract on dsL m  = 10

  type Earlyb rdQuery = (Seq[Long], Long)
  type UtegQuery = (Seq[Long], (Long, Map[Long, Double]))

  @Prov des
  @S ngleton
  @Na d( nterestsThr ftServ ceCl ent)
  def prov des nterestsThr ftServ ceCl ent(
    cl ent d: Cl ent d,
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ):  nt. nterestsThr ftServ ce. thodPerEndpo nt = {
    F nagleThr ftCl entBu lder
      .bu ldF nagle thodPerEndpo nt[
         nt. nterestsThr ftServ ce.Serv cePerEndpo nt,
         nt. nterestsThr ftServ ce. thodPerEndpo nt](
        serv ce dent f er = serv ce dent f er,
        cl ent d = cl ent d,
        dest = "/s/ nterests-thr ft-serv ce/ nterests-thr ft-serv ce",
        label = " nterests",
        statsRece ver = statsRece ver,
         dempotency =  dempotent(1.percent),
        t  outPerRequest = 350.m ll seconds,
        t  outTotal = 350.m ll seconds
      )
  }

  @Prov des
  @S ngleton
  @Na d(UserFollo dTop c dsRepos ory)
  def prov desUserFollo dTop c dsRepos ory(
    @Na d( nterestsThr ftServ ceCl ent) cl ent:  nt. nterestsThr ftServ ce. thodPerEndpo nt
  ): KeyValueRepos ory[Seq[Long], Long, Seq[Long]] = {

    val lookupContext = So (
       nt.Expl c  nterestLookupContext(So (Seq( nt. nterestRelat onType.Follo d)))
    )

    def lookup(user d: Long): Future[Seq[Long]] = {
      cl ent.getUserExpl c  nterests(user d, lookupContext).map {  nterests =>
         nterests.flatMap {
          _. nterest d match {
            case  nt. nterest d.Semant cCore(semant cCore nterest) => So (semant cCore nterest. d)
            case _ => None
          }
        }
      }
    }

    val keyValueRepos ory = toRepos ory(lookup)

    keyValueRepos ory
  }

  @Prov des
  @S ngleton
  @Na d(UtegSoc alProofRepos ory)
  def prov desUtegSoc alProofRepos ory(
    cl ent d: Cl ent d,
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ): KeyValueRepos ory[UtegQuery, Long, uteg.T etRecom ndat on] = {
    val cl ent = F nagleThr ftCl entBu lder.bu ldF nagle thodPerEndpo nt[
      uteg.UserT etEnt yGraph.Serv cePerEndpo nt,
      uteg.UserT etEnt yGraph. thodPerEndpo nt](
      serv ce dent f er = serv ce dent f er,
      cl ent d = cl ent d,
      dest = "/s/cassowary/user_t et_ent y_graph",
      label = "uteg-soc al-proof-repo",
      statsRece ver = statsRece ver,
       dempotency =  dempotent(1.percent),
      t  outPerRequest = 150.m ll seconds,
      t  outTotal = 250.m ll seconds
    )

    val utegSoc alProofTypes = Seq(
      rc.Soc alProofType.Favor e,
      rc.Soc alProofType.Ret et,
      rc.Soc alProofType.Reply
    )

    def lookup(
      t et ds: Seq[Long],
      v ew: (Long, Map[Long, Double])
    ): Future[Seq[Opt on[uteg.T etRecom ndat on]]] = {
      val (user d, seedsW h  ghts) = v ew
      val soc alProofRequest = uteg.Soc alProofRequest(
        requester d = So (user d),
        seedsW h  ghts = seedsW h  ghts,
         nputT ets = t et ds,
        soc alProofTypes = So (utegSoc alProofTypes)
      )
      cl ent.f ndT etSoc alProofs(soc alProofRequest).map { result =>
        val resultMap = result.soc alProofResults.map(t => t.t et d -> t).toMap
        t et ds.map(resultMap.get)
      }
    }

    toRepos oryBatchW hV ew(lookup, chunkS ze = 200)
  }

  @Prov des
  @S ngleton
  @Na d(T etyp eContentRepos ory)
  def prov desT etyp eContentRepos ory(
    cl ent d: Cl ent d,
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ): KeyValueRepos ory[Seq[Long], Long, tp.T et] = {
    val cl ent = F nagleThr ftCl entBu lder
      .bu ldF nagle thodPerEndpo nt[
        tp.T etServ ce.Serv cePerEndpo nt,
        tp.T etServ ce. thodPerEndpo nt](
        serv ce dent f er = serv ce dent f er,
        cl ent d = cl ent d,
        dest = "/s/t etyp e/t etyp e",
        label = "t etyp e-content-repo",
        statsRece ver = statsRece ver,
         dempotency =  dempotent(1.percent),
        t  outPerRequest = 300.m ll seconds,
        t  outTotal = 500.m ll seconds
      )

    def lookup(t et ds: Seq[Long]): Future[Seq[Opt on[tp.T et]]] = {
      val getT etF eldsOpt ons = tp.GetT etF eldsOpt ons(
        t et ncludes = RequestF elds.ContentF elds,
         ncludeRet etedT et = false,
         ncludeQuotedT et = false,
        forUser d = None,
        safetyLevel = So (sp.SafetyLevel.F lterNone),
        v s b l yPol cy = tp.T etV s b l yPol cy.NoF lter ng
      )

      val request = tp.GetT etF eldsRequest(t et ds = t et ds, opt ons = getT etF eldsOpt ons)

      cl ent.getT etF elds(request).map { results =>
        results.map {
          case tp.GetT etF eldsResult(_, tp.T etF eldsResultState.Found(found), _, _) =>
            So (found.t et)
          case _ => None
        }
      }
    }

    val keyValueRepos ory = toRepos oryBatch(lookup, chunkS ze = 20)

    val cac Cl ent =  mcac dCl entBu lder.bu ldRaw mcac dCl ent(
      numTr es = 1,
      numConnect ons = 1,
      requestT  out = 200.m ll seconds,
      globalT  out = 200.m ll seconds,
      connectT  out = 200.m ll seconds,
      acqu s  onT  out = 200.m ll seconds,
      serv ce dent f er = serv ce dent f er,
      statsRece ver = statsRece ver
    )

    val f nagle mcac Factory =
      F nagle mcac Factory(cac Cl ent, "/s/cac /ho _content_features:t mcac s")
    val cac ValueTransfor r =
      new Thr ftSer al zer[tp.T et](tp.T et, new TCompactProtocol.Factory())
    val cac dSer al zer = Cac dSer al zer.b nary(cac ValueTransfor r)

    val cac  =  mcac Cac Factory(
       mcac  = f nagle mcac Factory(),
      ttl = 48.h s
    )[Long, Cac d[tp.T et]](cac dSer al zer)

    val lock ngCac  = new NonLock ngCac (cac )
    val cac dKeyValueRepos ory = new Cach ngKeyValueRepos ory(
      keyValueRepos ory,
      lock ngCac ,
      keysAsQuery[Long]
    )
    cac dKeyValueRepos ory
  }

  @Prov des
  @S ngleton
  @Na d(GraphTwoHopRepos ory)
  def prov desGraphTwoHopRepos ory(
    cl ent d: Cl ent d,
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ): KeyValueRepos ory[(Seq[Long], Long), Long, Seq[gfs. ntersect onValue]] = {
    val cl ent = F nagleThr ftCl entBu lder
      .bu ldF nagle thodPerEndpo nt[gfs.Server.Serv cePerEndpo nt, gfs.Server. thodPerEndpo nt](
        serv ce dent f er = serv ce dent f er,
        cl ent d = cl ent d,
        dest = "/s/cassowary/graph_feature_serv ce-server",
        label = "gfs-repo",
        statsRece ver = statsRece ver,
         dempotency =  dempotent(1.percent),
        t  outPerRequest = 350.m ll seconds,
        t  outTotal = 500.m ll seconds
      )

    def lookup(
      user ds: Seq[Long],
      v e r d: Long
    ): Future[Seq[Opt on[Seq[gfs. ntersect onValue]]]] = {
      val gfs ntersect onRequest = gfs.GfsPreset ntersect onRequest(
        user d = v e r d,
        cand dateUser ds = user ds,
        presetFeatureTypes = gfs.PresetFeatureTypes.HtlTwoHop,
         ntersect on dL m  = So (GFS nteract on dsL m )
      )

      cl ent
        .getPreset ntersect on(gfs ntersect onRequest)
        .map { graphFeatureServ ceResponse =>
          val resultMap = graphFeatureServ ceResponse.results
            .map(result => result.cand dateUser d -> result. ntersect onValues).toMap
          user ds.map(resultMap.get(_))
        }
    }

    toRepos oryBatchW hV ew(lookup, chunkS ze = 200)
  }

  @Prov des
  @S ngleton
  @Na d(Earlyb rdRepos ory)
  def prov desEarlyb rdSearchRepos ory(
    cl ent: eb.Earlyb rdServ ce. thodPerEndpo nt,
    cl ent d: Cl ent d
  ): KeyValueRepos ory[Earlyb rdQuery, Long, eb.Thr ftSearchResult] = {

    def lookup(
      t et ds: Seq[Long],
      v e r d: Long
    ): Future[Seq[Opt on[eb.Thr ftSearchResult]]] = {
      val request = Earlyb rdRequestUt l.getT etsFeaturesRequest(
        user d = So (v e r d),
        t et ds = So (t et ds),
        cl ent d = So (cl ent d.na ),
        authorScoreMap = None,
        tensorflowModel = So ("t  l nes_rect et_repl ca")
      )

      cl ent
        .search(request).map { response =>
          val resultMap = response.searchResults
            .map(_.results.map { result => result. d -> result }.toMap).getOrElse(Map.empty)
          t et ds.map(resultMap.get)
        }
    }
    toRepos oryBatchW hV ew(lookup)
  }

  protected def toRepos ory[K, V](
    hydrate: K => Future[V]
  ): KeyValueRepos ory[Seq[K], K, V] = {
    def asRepos ory(keys: Seq[K]): Future[KeyValueResult[K, V]] = {
      Future.collect(keys.map(hydrate(_).l ftToTry)).map { results =>
        keys
          .z p(results)
          .foldLeft(new KeyValueResultBu lder[K, V]()) {
            case (bldr, (k, result)) =>
              result match {
                case Return(v) => bldr.addFound(k, v)
                case _ => bldr.addNotFound(k)
              }
          }.result
      }
    }

    asRepos ory
  }

  protected def toRepos oryBatch[K, V](
    hydrate: Seq[K] => Future[Seq[Opt on[V]]],
    chunkS ze:  nt = DefaultRPCChunkS ze
  ): KeyValueRepos ory[Seq[K], K, V] = {
    def repos ory(keys: Seq[K]): Future[KeyValueResult[K, V]] =
      batchRepos oryProcess(keys, hydrate(keys))

    KeyValueRepos ory.chunked(repos ory, Chunk ngStrategy.equalS ze(chunkS ze))
  }

  protected def toRepos oryBatchW hV ew[K, T, V](
    hydrate: (Seq[K], T) => Future[Seq[Opt on[V]]],
    chunkS ze:  nt = DefaultRPCChunkS ze
  ): KeyValueRepos ory[(Seq[K], T), K, V] = {
    def repos ory( nput: (Seq[K], T)): Future[KeyValueResult[K, V]] = {
      val (keys, v ew) =  nput
      batchRepos oryProcess(keys, hydrate(keys, v ew))
    }

    KeyValueRepos ory.chunked(repos ory, CustomChunk ngStrategy.equalS zeW hV ew(chunkS ze))
  }

  pr vate def batchRepos oryProcess[K, V](
    keys: Seq[K],
    f: Future[Seq[Opt on[V]]]
  ): Future[KeyValueResult[K, V]] = {
    f.l ftToTry
      .map {
        case Return(values) =>
          keys
            .z p(values)
            .foldLeft(new KeyValueResultBu lder[K, V]()) {
              case (bldr, (k, value)) =>
                value match {
                  case So (v) => bldr.addFound(k, v)
                  case _ => bldr.addNotFound(k)
                }
            }.result
        case _ =>
          keys
            .foldLeft(new KeyValueResultBu lder[K, V]()) {
              case (bldr, k) => bldr.addNotFound(k)
            }.result
      }
  }

  // Use only for cases not already covered by Servo's [[Chunk ngStrategy]]
  object CustomChunk ngStrategy {
    def equalS zeW hV ew[K, T](maxS ze:  nt): ((Seq[K], T)) => Seq[(Seq[K], T)] = {
      case (keys, v ew) =>
        Chunk ngStrategy
          .equalS ze[K](maxS ze)(keys)
          .map { chunk: Seq[K] => (chunk, v ew) }
    }
  }
}
