package com.tw ter.representat on_manager.m grat on

 mport com.tw ter.b ject on. nject on
 mport com.tw ter.b ject on.scrooge.B naryScalaCodec
 mport com.tw ter.contentrecom nder.store.ApeEnt yEmbedd ngStore
 mport com.tw ter.contentrecom nder.store. nterestsOptOutStore
 mport com.tw ter.contentrecom nder.store.Semant cCoreTop cSeedStore
 mport com.tw ter.contentrecom nder.tw stly
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.dec der.Dec der
 mport com.tw ter.esc rb rd.ut l.uttcl ent.Cac Conf gV2
 mport com.tw ter.esc rb rd.ut l.uttcl ent.Cac dUttCl entV2
 mport com.tw ter.esc rb rd.ut l.uttcl ent.UttCl entCac Conf gsV2
 mport com.tw ter.esc rb rd.utt.strato.thr ftscala.Env ron nt
 mport com.tw ter.f nagle.Thr ftMux
 mport com.tw ter.f nagle. mcac d.Cl ent
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.mtls.cl ent.MtlsStackCl ent.MtlsThr ftMuxCl entSyntax
 mport com.tw ter.f nagle.mux.Cl entD scardedRequestExcept on
 mport com.tw ter.f nagle.serv ce.ReqRep
 mport com.tw ter.f nagle.serv ce.ResponseClass
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter.fr gate.common.store.strato.StratoFetchableStore
 mport com.tw ter.fr gate.common.ut l.SeqLong nject on
 mport com.tw ter.hash ng.KeyHas r
 mport com.tw ter. rm .store.common.Dec derableReadableStore
 mport com.tw ter. rm .store.common.ObservedCac dReadableStore
 mport com.tw ter. rm .store.common.Observed mcac dReadableStore
 mport com.tw ter. rm .store.common.ObservedReadableStore
 mport com.tw ter. nterests.thr ftscala. nterestsThr ftServ ce
 mport com.tw ter.relevance_platform.common. nject on.LZ4 nject on
 mport com.tw ter.relevance_platform.common.readablestore.ReadableStoreW hT  out
 mport com.tw ter.representat on_manager.common.Representat onManagerDec der
 mport com.tw ter.representat on_manager.store.Dec derConstants
 mport com.tw ter.representat on_manager.store.Dec derKey
 mport com.tw ter.s mclusters_v2.common.ModelVers ons
 mport com.tw ter.s mclusters_v2.common.S mClustersEmbedd ng
 mport com.tw ter.s mclusters_v2.common.S mClustersEmbedd ng dCac KeyBu lder
 mport com.tw ter.s mclusters_v2.stores.S mClustersEmbedd ngStore
 mport com.tw ter.s mclusters_v2.summ ngb rd.stores.Pers stentT etEmbedd ngStore
 mport com.tw ter.s mclusters_v2.summ ngb rd.stores.ProducerClusterEmbedd ngReadableStores
 mport com.tw ter.s mclusters_v2.summ ngb rd.stores.User nterested nReadableStore
 mport com.tw ter.s mclusters_v2.thr ftscala.ClustersUser s nterested n
 mport com.tw ter.s mclusters_v2.thr ftscala.Embedd ngType
 mport com.tw ter.s mclusters_v2.thr ftscala.Embedd ngType._
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on
 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on.Model20m145k2020
 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on.Model20m145kUpdated
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersEmbedd ng d
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersMult Embedd ng
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersMult Embedd ng d
 mport com.tw ter.s mclusters_v2.thr ftscala.{S mClustersEmbedd ng => Thr ftS mClustersEmbedd ng}
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVCl entMtlsParams
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.storehaus_ nternal.manhattan.At na
 mport com.tw ter.storehaus_ nternal.manhattan.ManhattanRO
 mport com.tw ter.storehaus_ nternal.manhattan.ManhattanROConf g
 mport com.tw ter.storehaus_ nternal.ut l.Appl cat on D
 mport com.tw ter.storehaus_ nternal.ut l.DatasetNa 
 mport com.tw ter.storehaus_ nternal.ut l.HDFSPath
 mport com.tw ter.strato.cl ent.Strato
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.strato.thr ft.ScroogeConv mpl c s._
 mport com.tw ter.t etyp e.ut l.User d
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.T  r
 mport javax. nject. nject
 mport javax. nject.Na d
 mport scala.reflect.ClassTag

class LegacyRMS @ nject() (
  serv ce dent f er: Serv ce dent f er,
  cac Cl ent: Cl ent,
  stats: StatsRece ver,
  dec der: Dec der,
  cl ent d: Cl ent d,
  t  r: T  r,
  @Na d("cac HashKeyPref x") val cac HashKeyPref x: Str ng = "RMS",
  @Na d("useContentRecom nderConf gurat on") val useContentRecom nderConf gurat on: Boolean =
    false) {

  pr vate val mhMtlsParams: ManhattanKVCl entMtlsParams = ManhattanKVCl entMtlsParams(
    serv ce dent f er)
  pr vate val rmsDec der = Representat onManagerDec der(dec der)
  val keyHas r: KeyHas r = KeyHas r.FNV1A_64

  pr vate val embedd ngCac KeyBu lder =
    S mClustersEmbedd ng dCac KeyBu lder(keyHas r.hashKey, cac HashKeyPref x)
  pr vate val statsRece ver = stats.scope("representat on_manage nt")

  // Strato cl ent, default t  out = 280ms
  val stratoCl ent: StratoCl ent =
    Strato.cl ent
      .w hMutualTls(serv ce dent f er)
      .bu ld()

  // Bu lds Thr ftMux cl ent bu lder for Content-Recom nder serv ce
  pr vate def makeThr ftCl entBu lder(
    requestT  out: Durat on
  ): Thr ftMux.Cl ent = {
    Thr ftMux.cl ent
      .w hCl ent d(cl ent d)
      .w hMutualTls(serv ce dent f er)
      .w hRequestT  out(requestT  out)
      .w hStatsRece ver(statsRece ver.scope("clnt"))
      .w hResponseClass f er {
        case ReqRep(_, Throw(_: Cl entD scardedRequestExcept on)) => ResponseClass. gnorable
      }
  }

  pr vate def makeThr ftCl ent[Thr ftServ ceType: ClassTag](
    dest: Str ng,
    label: Str ng,
    requestT  out: Durat on = 450.m ll seconds
  ): Thr ftServ ceType = {
    makeThr ftCl entBu lder(requestT  out)
      .bu ld[Thr ftServ ceType](dest, label)
  }

  /** *** S mCluster Embedd ng Stores ******/
   mpl c  val s mClustersEmbedd ng d nject on:  nject on[S mClustersEmbedd ng d, Array[Byte]] =
    B naryScalaCodec(S mClustersEmbedd ng d)
   mpl c  val s mClustersEmbedd ng nject on:  nject on[Thr ftS mClustersEmbedd ng, Array[Byte]] =
    B naryScalaCodec(Thr ftS mClustersEmbedd ng)
   mpl c  val s mClustersMult Embedd ng nject on:  nject on[S mClustersMult Embedd ng, Array[
    Byte
  ]] =
    B naryScalaCodec(S mClustersMult Embedd ng)
   mpl c  val s mClustersMult Embedd ng d nject on:  nject on[S mClustersMult Embedd ng d, Array[
    Byte
  ]] =
    B naryScalaCodec(S mClustersMult Embedd ng d)

  def getEmbedd ngsDataset(
    mhMtlsParams: ManhattanKVCl entMtlsParams,
    datasetNa : Str ng
  ): ReadableStore[S mClustersEmbedd ng d, Thr ftS mClustersEmbedd ng] = {
    ManhattanRO.getReadableStoreW hMtls[S mClustersEmbedd ng d, Thr ftS mClustersEmbedd ng](
      ManhattanROConf g(
        HDFSPath(""), // not needed
        Appl cat on D("content_recom nder_at na"),
        DatasetNa (datasetNa ), // t  should be correct
        At na
      ),
      mhMtlsParams
    )
  }

  lazy val logFavBasedLongestL2T et20M145K2020Embedd ngStore: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    val rawStore =
      Pers stentT etEmbedd ngStore
        .longestL2NormT etEmbedd ngStoreManhattan(
          mhMtlsParams,
          Pers stentT etEmbedd ngStore.LogFavBased20m145k2020Dataset,
          statsRece ver,
          maxLength = 10,
        ).mapValues(_.toThr ft)

    val  mcac dStore = Observed mcac dReadableStore.fromCac Cl ent(
      back ngStore = rawStore,
      cac Cl ent = cac Cl ent,
      ttl = 15.m nutes
    )(
      value nject on = LZ4 nject on.compose(B naryScalaCodec(Thr ftS mClustersEmbedd ng)),
      statsRece ver =
        statsRece ver.scope("log_fav_based_longest_l2_t et_embedd ng_20m145k2020_ m_cac "),
      keyToStr ng = { k =>
        s"scez_l2:${LogFavBasedT et}_${ModelVers ons.Model20M145K2020}_$k"
      }
    )

    val  n moryCac Store: ReadableStore[S mClustersEmbedd ng d, S mClustersEmbedd ng] =
       mcac dStore
        .composeKeyMapp ng[S mClustersEmbedd ng d] {
          case S mClustersEmbedd ng d(
                LogFavLongestL2Embedd ngT et,
                Model20m145k2020,
                 nternal d.T et d(t et d)) =>
            t et d
        }
        .mapValues(S mClustersEmbedd ng(_))

    ObservedCac dReadableStore.from[S mClustersEmbedd ng d, S mClustersEmbedd ng](
       n moryCac Store,
      ttl = 12.m nute,
      maxKeys = 1048575,
      cac Na  = "log_fav_based_longest_l2_t et_embedd ng_20m145k2020_cac ",
      w ndowS ze = 10000L
    )(statsRece ver.scope("log_fav_based_longest_l2_t et_embedd ng_20m145k2020_store"))
  }

  lazy val logFavBased20M145KUpdatedT etEmbedd ngStore: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    val rawStore =
      Pers stentT etEmbedd ngStore
        .mostRecentT etEmbedd ngStoreManhattan(
          mhMtlsParams,
          Pers stentT etEmbedd ngStore.LogFavBased20m145kUpdatedDataset,
          statsRece ver
        ).mapValues(_.toThr ft)

    val  mcac dStore = Observed mcac dReadableStore.fromCac Cl ent(
      back ngStore = rawStore,
      cac Cl ent = cac Cl ent,
      ttl = 10.m nutes
    )(
      value nject on = LZ4 nject on.compose(B naryScalaCodec(Thr ftS mClustersEmbedd ng)),
      statsRece ver = statsRece ver.scope("log_fav_based_t et_embedd ng_ m_cac "),
      keyToStr ng = { k =>
        // S mClusters_embedd ng_LZ4/embedd ngType_modelVers on_t et d
        s"scez:${LogFavBasedT et}_${ModelVers ons.Model20M145KUpdated}_$k"
      }
    )

    val  n moryCac Store: ReadableStore[S mClustersEmbedd ng d, S mClustersEmbedd ng] = {
       mcac dStore
        .composeKeyMapp ng[S mClustersEmbedd ng d] {
          case S mClustersEmbedd ng d(
                LogFavBasedT et,
                Model20m145kUpdated,
                 nternal d.T et d(t et d)) =>
            t et d
        }
        .mapValues(S mClustersEmbedd ng(_))
    }

    ObservedCac dReadableStore.from[S mClustersEmbedd ng d, S mClustersEmbedd ng](
       n moryCac Store,
      ttl = 5.m nute,
      maxKeys = 1048575, // 200MB
      cac Na  = "log_fav_based_t et_embedd ng_cac ",
      w ndowS ze = 10000L
    )(statsRece ver.scope("log_fav_based_t et_embedd ng_store"))
  }

  lazy val logFavBased20M145K2020T etEmbedd ngStore: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    val rawStore =
      Pers stentT etEmbedd ngStore
        .mostRecentT etEmbedd ngStoreManhattan(
          mhMtlsParams,
          Pers stentT etEmbedd ngStore.LogFavBased20m145k2020Dataset,
          statsRece ver,
          maxLength = 10,
        ).mapValues(_.toThr ft)

    val  mcac dStore = Observed mcac dReadableStore.fromCac Cl ent(
      back ngStore = rawStore,
      cac Cl ent = cac Cl ent,
      ttl = 15.m nutes
    )(
      value nject on = LZ4 nject on.compose(B naryScalaCodec(Thr ftS mClustersEmbedd ng)),
      statsRece ver = statsRece ver.scope("log_fav_based_t et_embedd ng_20m145k2020_ m_cac "),
      keyToStr ng = { k =>
        // S mClusters_embedd ng_LZ4/embedd ngType_modelVers on_t et d
        s"scez:${LogFavBasedT et}_${ModelVers ons.Model20M145K2020}_$k"
      }
    )

    val  n moryCac Store: ReadableStore[S mClustersEmbedd ng d, S mClustersEmbedd ng] =
       mcac dStore
        .composeKeyMapp ng[S mClustersEmbedd ng d] {
          case S mClustersEmbedd ng d(
                LogFavBasedT et,
                Model20m145k2020,
                 nternal d.T et d(t et d)) =>
            t et d
        }
        .mapValues(S mClustersEmbedd ng(_))

    ObservedCac dReadableStore.from[S mClustersEmbedd ng d, S mClustersEmbedd ng](
       n moryCac Store,
      ttl = 12.m nute,
      maxKeys = 16777215,
      cac Na  = "log_fav_based_t et_embedd ng_20m145k2020_cac ",
      w ndowS ze = 10000L
    )(statsRece ver.scope("log_fav_based_t et_embedd ng_20m145k2020_store"))
  }

  lazy val favBasedTfgTop cEmbedd ng2020Store: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    val stratoStore =
      StratoFetchableStore
        .w hUn V ew[S mClustersEmbedd ng d, Thr ftS mClustersEmbedd ng](
          stratoCl ent,
          "recom ndat ons/s mclusters_v2/embedd ngs/favBasedTFGTop c20M145K2020")

    val truncatedStore = stratoStore.mapValues { embedd ng =>
      S mClustersEmbedd ng(embedd ng, truncate = 50)
    }

    ObservedCac dReadableStore.from(
      ObservedReadableStore(truncatedStore)(
        statsRece ver.scope("fav_tfg_top c_embedd ng_2020_cac _back ng_store")),
      ttl = 12.h s,
      maxKeys = 262143, // 200MB
      cac Na  = "fav_tfg_top c_embedd ng_2020_cac ",
      w ndowS ze = 10000L
    )(statsRece ver.scope("fav_tfg_top c_embedd ng_2020_cac "))
  }

  lazy val logFavBasedApe20M145K2020Embedd ngStore: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    ObservedReadableStore(
      StratoFetchableStore
        .w hUn V ew[S mClustersEmbedd ng d, Thr ftS mClustersEmbedd ng](
          stratoCl ent,
          "recom ndat ons/s mclusters_v2/embedd ngs/logFavBasedAPE20M145K2020")
        .composeKeyMapp ng[S mClustersEmbedd ng d] {
          case S mClustersEmbedd ng d(
                AggregatableLogFavBasedProducer,
                Model20m145k2020,
                 nternal d) =>
            S mClustersEmbedd ng d(AggregatableLogFavBasedProducer, Model20m145k2020,  nternal d)
        }
        .mapValues(embedd ng => S mClustersEmbedd ng(embedd ng, 50))
    )(statsRece ver.scope("aggregatable_producer_embedd ngs_by_logfav_score_2020"))
  }

  val  nterestServ ce:  nterestsThr ftServ ce. thodPerEndpo nt =
    makeThr ftCl ent[ nterestsThr ftServ ce. thodPerEndpo nt](
      "/s/ nterests-thr ft-serv ce/ nterests-thr ft-serv ce",
      " nterests_thr ft_serv ce"
    )

  val  nterestsOptOutStore:  nterestsOptOutStore =  nterestsOptOutStore( nterestServ ce)

  // Save 2 ^ 18 UTTs. Prom s ng 100% cac  rate
  lazy val defaultCac Conf gV2: Cac Conf gV2 = Cac Conf gV2(262143)
  lazy val uttCl entCac Conf gsV2: UttCl entCac Conf gsV2 = UttCl entCac Conf gsV2(
    getTaxono Conf g = defaultCac Conf gV2,
    getUttTaxono Conf g = defaultCac Conf gV2,
    getLeaf ds = defaultCac Conf gV2,
    getLeafUttEnt  es = defaultCac Conf gV2
  )

  // Cac dUttCl ent to use StratoCl ent
  lazy val cac dUttCl entV2: Cac dUttCl entV2 = new Cac dUttCl entV2(
    stratoCl ent = stratoCl ent,
    env = Env ron nt.Prod,
    cac Conf gs = uttCl entCac Conf gsV2,
    statsRece ver = statsRece ver.scope("cac d_utt_cl ent")
  )

  lazy val semant cCoreTop cSeedStore: ReadableStore[
    Semant cCoreTop cSeedStore.Key,
    Seq[User d]
  ] = {
    /*
      Up to 1000 Long seeds per top c/language = 62.5kb per top c/language (worst case)
      Assu  ~10k act ve top c/languages ~= 650MB (worst case)
     */
    val underly ng = new Semant cCoreTop cSeedStore(cac dUttCl entV2,  nterestsOptOutStore)(
      statsRece ver.scope("semant c_core_top c_seed_store"))

    val  mcac Store = Observed mcac dReadableStore.fromCac Cl ent(
      back ngStore = underly ng,
      cac Cl ent = cac Cl ent,
      ttl = 12.h s
    )(
      value nject on = SeqLong nject on,
      statsRece ver = statsRece ver.scope("top c_producer_seed_store_ m_cac "),
      keyToStr ng = { k => s"tpss:${k.ent y d}_${k.languageCode}" }
    )

    ObservedCac dReadableStore.from[Semant cCoreTop cSeedStore.Key, Seq[User d]](
      store =  mcac Store,
      ttl = 6.h s,
      maxKeys = 20e3.to nt,
      cac Na  = "top c_producer_seed_store_cac ",
      w ndowS ze = 5000
    )(statsRece ver.scope("top c_producer_seed_store_cac "))
  }

  lazy val logFavBasedApeEnt y20M145K2020Embedd ngStore: ApeEnt yEmbedd ngStore = {
    val apeStore = logFavBasedApe20M145K2020Embedd ngStore.composeKeyMapp ng[User d]({  d =>
      S mClustersEmbedd ng d(
        AggregatableLogFavBasedProducer,
        Model20m145k2020,
         nternal d.User d( d))
    })

    new ApeEnt yEmbedd ngStore(
      semant cCoreSeedStore = semant cCoreTop cSeedStore,
      aggregatableProducerEmbedd ngStore = apeStore,
      statsRece ver = statsRece ver.scope("log_fav_based_ape_ent y_2020_embedd ng_store"))
  }

  lazy val logFavBasedApeEnt y20M145K2020Embedd ngCac dStore: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    val truncatedStore =
      logFavBasedApeEnt y20M145K2020Embedd ngStore.mapValues(_.truncate(50).toThr ft)

    val  mcac dStore = Observed mcac dReadableStore
      .fromCac Cl ent(
        back ngStore = truncatedStore,
        cac Cl ent = cac Cl ent,
        ttl = 12.h s
      )(
        value nject on = LZ4 nject on.compose(B naryScalaCodec(Thr ftS mClustersEmbedd ng)),
        statsRece ver = statsRece ver.scope("log_fav_based_ape_ent y_2020_embedd ng_ m_cac "),
        keyToStr ng = { k => embedd ngCac KeyBu lder.apply(k) }
      ).mapValues(S mClustersEmbedd ng(_))

    val  n moryCac dStore =
      ObservedCac dReadableStore.from[S mClustersEmbedd ng d, S mClustersEmbedd ng](
         mcac dStore,
        ttl = 6.h s,
        maxKeys = 262143,
        cac Na  = "log_fav_based_ape_ent y_2020_embedd ng_cac ",
        w ndowS ze = 10000L
      )(statsRece ver.scope("log_fav_based_ape_ent y_2020_embedd ng_cac d_store"))

    Dec derableReadableStore(
       n moryCac dStore,
      rmsDec der.dec derGateBu lder. dGateW hHash ng[S mClustersEmbedd ng d](
        Dec derKey.enableLogFavBasedApeEnt y20M145K2020Embedd ngCac dStore),
      statsRece ver.scope("log_fav_based_ape_ent y_2020_embedd ng_dec derable_store")
    )
  }

  lazy val relaxedLogFavBasedApe20M145K2020Embedd ngStore: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    ObservedReadableStore(
      StratoFetchableStore
        .w hUn V ew[S mClustersEmbedd ng d, Thr ftS mClustersEmbedd ng](
          stratoCl ent,
          "recom ndat ons/s mclusters_v2/embedd ngs/logFavBasedAPERelaxedFavEngage ntThreshold20M145K2020")
        .composeKeyMapp ng[S mClustersEmbedd ng d] {
          case S mClustersEmbedd ng d(
                RelaxedAggregatableLogFavBasedProducer,
                Model20m145k2020,
                 nternal d) =>
            S mClustersEmbedd ng d(
              RelaxedAggregatableLogFavBasedProducer,
              Model20m145k2020,
               nternal d)
        }
        .mapValues(embedd ng => S mClustersEmbedd ng(embedd ng).truncate(50))
    )(statsRece ver.scope(
      "aggregatable_producer_embedd ngs_by_logfav_score_relaxed_fav_engage nt_threshold_2020"))
  }

  lazy val relaxedLogFavBasedApe20M145K2020Embedd ngCac dStore: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    val truncatedStore =
      relaxedLogFavBasedApe20M145K2020Embedd ngStore.mapValues(_.truncate(50).toThr ft)

    val  mcac dStore = Observed mcac dReadableStore
      .fromCac Cl ent(
        back ngStore = truncatedStore,
        cac Cl ent = cac Cl ent,
        ttl = 12.h s
      )(
        value nject on = LZ4 nject on.compose(B naryScalaCodec(Thr ftS mClustersEmbedd ng)),
        statsRece ver =
          statsRece ver.scope("relaxed_log_fav_based_ape_ent y_2020_embedd ng_ m_cac "),
        keyToStr ng = { k: S mClustersEmbedd ng d => embedd ngCac KeyBu lder.apply(k) }
      ).mapValues(S mClustersEmbedd ng(_))

    ObservedCac dReadableStore.from[S mClustersEmbedd ng d, S mClustersEmbedd ng](
       mcac dStore,
      ttl = 6.h s,
      maxKeys = 262143,
      cac Na  = "relaxed_log_fav_based_ape_ent y_2020_embedd ng_cac ",
      w ndowS ze = 10000L
    )(statsRece ver.scope("relaxed_log_fav_based_ape_ent y_2020_embedd ng_cac _store"))
  }

  lazy val favBasedProducer20M145K2020Embedd ngStore: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    val underly ngStore = ProducerClusterEmbedd ngReadableStores
      .getProducerTopKS mClusters2020Embedd ngsStore(
        mhMtlsParams
      ).composeKeyMapp ng[S mClustersEmbedd ng d] {
        case S mClustersEmbedd ng d(
              FavBasedProducer,
              Model20m145k2020,
               nternal d.User d(user d)) =>
          user d
      }.mapValues { topS mClustersW hScore =>
        Thr ftS mClustersEmbedd ng(topS mClustersW hScore.topClusters.take(10))
      }

    // sa   mcac  conf g as for favBasedUser nterested n20M145K2020Store
    val  mcac dStore = Observed mcac dReadableStore
      .fromCac Cl ent(
        back ngStore = underly ngStore,
        cac Cl ent = cac Cl ent,
        ttl = 24.h s
      )(
        value nject on = LZ4 nject on.compose(B naryScalaCodec(Thr ftS mClustersEmbedd ng)),
        statsRece ver = statsRece ver.scope("fav_based_producer_embedd ng_20M_145K_2020_ m_cac "),
        keyToStr ng = { k => embedd ngCac KeyBu lder.apply(k) }
      ).mapValues(S mClustersEmbedd ng(_))

    ObservedCac dReadableStore.from[S mClustersEmbedd ng d, S mClustersEmbedd ng](
       mcac dStore,
      ttl = 12.h s,
      maxKeys = 16777215,
      cac Na  = "fav_based_producer_embedd ng_20M_145K_2020_embedd ng_cac ",
      w ndowS ze = 10000L
    )(statsRece ver.scope("fav_based_producer_embedd ng_20M_145K_2020_embedd ng_store"))
  }

  // Product on
  lazy val  nterested n20M145KUpdatedStore: ReadableStore[User d, ClustersUser s nterested n] = {
    User nterested nReadableStore.defaultStoreW hMtls(
      mhMtlsParams,
      modelVers on = ModelVers ons.Model20M145KUpdated
    )
  }

  // Product on
  lazy val  nterested n20M145K2020Store: ReadableStore[User d, ClustersUser s nterested n] = {
    User nterested nReadableStore.defaultStoreW hMtls(
      mhMtlsParams,
      modelVers on = ModelVers ons.Model20M145K2020
    )
  }

  // Product on
  lazy val  nterested nFromPE20M145KUpdatedStore: ReadableStore[
    User d,
    ClustersUser s nterested n
  ] = {
    User nterested nReadableStore.default  PEStoreW hMtls(
      mhMtlsParams,
      modelVers on = ModelVers ons.Model20M145KUpdated)
  }

  lazy val s mClusters nterested nStore: ReadableStore[
    (User d, ModelVers on),
    ClustersUser s nterested n
  ] = {
    new ReadableStore[(User d, ModelVers on), ClustersUser s nterested n] {
      overr de def get(k: (User d, ModelVers on)): Future[Opt on[ClustersUser s nterested n]] = {
        k match {
          case (user d, Model20m145kUpdated) =>
             nterested n20M145KUpdatedStore.get(user d)
          case (user d, Model20m145k2020) =>
             nterested n20M145K2020Store.get(user d)
          case _ =>
            Future.None
        }
      }
    }
  }

  lazy val s mClusters nterested nFromProducerEmbedd ngsStore: ReadableStore[
    (User d, ModelVers on),
    ClustersUser s nterested n
  ] = {
    new ReadableStore[(User d, ModelVers on), ClustersUser s nterested n] {
      overr de def get(k: (User d, ModelVers on)): Future[Opt on[ClustersUser s nterested n]] = {
        k match {
          case (user d, ModelVers on.Model20m145kUpdated) =>
             nterested nFromPE20M145KUpdatedStore.get(user d)
          case _ =>
            Future.None
        }
      }
    }
  }

  lazy val user nterested nStore =
    new tw stly. nterested n.Embedd ngStore(
       nterested nStore = s mClusters nterested nStore,
       nterested nFromProducerEmbedd ngStore = s mClusters nterested nFromProducerEmbedd ngsStore,
      statsRece ver = statsRece ver
    )

  // Product on
  lazy val favBasedUser nterested n20M145KUpdatedStore: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    val underly ngStore =
      User nterested nReadableStore
        .defaultS mClustersEmbedd ngStoreW hMtls(
          mhMtlsParams,
          Embedd ngType.FavBasedUser nterested n,
          ModelVers on.Model20m145kUpdated)
        .mapValues(_.toThr ft)

    val  mcac dStore = Observed mcac dReadableStore
      .fromCac Cl ent(
        back ngStore = underly ngStore,
        cac Cl ent = cac Cl ent,
        ttl = 12.h s
      )(
        value nject on = LZ4 nject on.compose(B naryScalaCodec(Thr ftS mClustersEmbedd ng)),
        statsRece ver = statsRece ver.scope("fav_based_user_ nterested_ n_ m_cac "),
        keyToStr ng = { k => embedd ngCac KeyBu lder.apply(k) }
      ).mapValues(S mClustersEmbedd ng(_))

    ObservedCac dReadableStore.from[S mClustersEmbedd ng d, S mClustersEmbedd ng](
       mcac dStore,
      ttl = 6.h s,
      maxKeys = 262143,
      cac Na  = "fav_based_user_ nterested_ n_cac ",
      w ndowS ze = 10000L
    )(statsRece ver.scope("fav_based_user_ nterested_ n_store"))
  }

  // Product on
  lazy val LogFavBased nterested nFromAPE20M145K2020Store: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    val underly ngStore =
      User nterested nReadableStore
        .default  APES mClustersEmbedd ngStoreW hMtls(
          mhMtlsParams,
          Embedd ngType.LogFavBasedUser nterested nFromAPE,
          ModelVers on.Model20m145k2020)
        .mapValues(_.toThr ft)

    val  mcac dStore = Observed mcac dReadableStore
      .fromCac Cl ent(
        back ngStore = underly ngStore,
        cac Cl ent = cac Cl ent,
        ttl = 12.h s
      )(
        value nject on = LZ4 nject on.compose(B naryScalaCodec(Thr ftS mClustersEmbedd ng)),
        statsRece ver = statsRece ver.scope("log_fav_based_user_ nterested_ n_from_ape_ m_cac "),
        keyToStr ng = { k => embedd ngCac KeyBu lder.apply(k) }
      ).mapValues(S mClustersEmbedd ng(_))

    ObservedCac dReadableStore.from[S mClustersEmbedd ng d, S mClustersEmbedd ng](
       mcac dStore,
      ttl = 6.h s,
      maxKeys = 262143,
      cac Na  = "log_fav_based_user_ nterested_ n_from_ape_cac ",
      w ndowS ze = 10000L
    )(statsRece ver.scope("log_fav_based_user_ nterested_ n_from_ape_store"))
  }

  // Product on
  lazy val FollowBased nterested nFromAPE20M145K2020Store: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    val underly ngStore =
      User nterested nReadableStore
        .default  APES mClustersEmbedd ngStoreW hMtls(
          mhMtlsParams,
          Embedd ngType.FollowBasedUser nterested nFromAPE,
          ModelVers on.Model20m145k2020)
        .mapValues(_.toThr ft)

    val  mcac dStore = Observed mcac dReadableStore
      .fromCac Cl ent(
        back ngStore = underly ngStore,
        cac Cl ent = cac Cl ent,
        ttl = 12.h s
      )(
        value nject on = LZ4 nject on.compose(B naryScalaCodec(Thr ftS mClustersEmbedd ng)),
        statsRece ver = statsRece ver.scope("follow_based_user_ nterested_ n_from_ape_ m_cac "),
        keyToStr ng = { k => embedd ngCac KeyBu lder.apply(k) }
      ).mapValues(S mClustersEmbedd ng(_))

    ObservedCac dReadableStore.from[S mClustersEmbedd ng d, S mClustersEmbedd ng](
       mcac dStore,
      ttl = 6.h s,
      maxKeys = 262143,
      cac Na  = "follow_based_user_ nterested_ n_from_ape_cac ",
      w ndowS ze = 10000L
    )(statsRece ver.scope("follow_based_user_ nterested_ n_from_ape_store"))
  }

  // product on
  lazy val favBasedUser nterested n20M145K2020Store: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    val underly ngStore: ReadableStore[S mClustersEmbedd ng d, Thr ftS mClustersEmbedd ng] =
      User nterested nReadableStore
        .defaultS mClustersEmbedd ngStoreW hMtls(
          mhMtlsParams,
          Embedd ngType.FavBasedUser nterested n,
          ModelVers on.Model20m145k2020).mapValues(_.toThr ft)

    Observed mcac dReadableStore
      .fromCac Cl ent(
        back ngStore = underly ngStore,
        cac Cl ent = cac Cl ent,
        ttl = 12.h s
      )(
        value nject on = LZ4 nject on.compose(B naryScalaCodec(Thr ftS mClustersEmbedd ng)),
        statsRece ver = statsRece ver.scope("fav_based_user_ nterested_ n_2020_ m_cac "),
        keyToStr ng = { k => embedd ngCac KeyBu lder.apply(k) }
      ).mapValues(S mClustersEmbedd ng(_))
  }

  // Product on
  lazy val logFavBasedUser nterested n20M145K2020Store: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    val underly ngStore =
      User nterested nReadableStore
        .defaultS mClustersEmbedd ngStoreW hMtls(
          mhMtlsParams,
          Embedd ngType.LogFavBasedUser nterested n,
          ModelVers on.Model20m145k2020)

    val  mcac dStore = Observed mcac dReadableStore
      .fromCac Cl ent(
        back ngStore = underly ngStore.mapValues(_.toThr ft),
        cac Cl ent = cac Cl ent,
        ttl = 12.h s
      )(
        value nject on = LZ4 nject on.compose(B naryScalaCodec(Thr ftS mClustersEmbedd ng)),
        statsRece ver = statsRece ver.scope("log_fav_based_user_ nterested_ n_2020_store"),
        keyToStr ng = { k => embedd ngCac KeyBu lder.apply(k) }
      ).mapValues(S mClustersEmbedd ng(_))

    ObservedCac dReadableStore.from[S mClustersEmbedd ng d, S mClustersEmbedd ng](
       mcac dStore,
      ttl = 6.h s,
      maxKeys = 262143,
      cac Na  = "log_fav_based_user_ nterested_ n_2020_cac ",
      w ndowS ze = 10000L
    )(statsRece ver.scope("log_fav_based_user_ nterested_ n_2020_store"))
  }

  // Product on
  lazy val favBasedUser nterested nFromPE20M145KUpdatedStore: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    val underly ngStore =
      User nterested nReadableStore
        .default  PES mClustersEmbedd ngStoreW hMtls(
          mhMtlsParams,
          Embedd ngType.FavBasedUser nterested nFromPE,
          ModelVers on.Model20m145kUpdated)
        .mapValues(_.toThr ft)

    val  mcac dStore = Observed mcac dReadableStore
      .fromCac Cl ent(
        back ngStore = underly ngStore,
        cac Cl ent = cac Cl ent,
        ttl = 12.h s
      )(
        value nject on = LZ4 nject on.compose(B naryScalaCodec(Thr ftS mClustersEmbedd ng)),
        statsRece ver = statsRece ver.scope("fav_based_user_ nterested_ n_from_pe_ m_cac "),
        keyToStr ng = { k => embedd ngCac KeyBu lder.apply(k) }
      ).mapValues(S mClustersEmbedd ng(_))

    ObservedCac dReadableStore.from[S mClustersEmbedd ng d, S mClustersEmbedd ng](
       mcac dStore,
      ttl = 6.h s,
      maxKeys = 262143,
      cac Na  = "fav_based_user_ nterested_ n_from_pe_cac ",
      w ndowS ze = 10000L
    )(statsRece ver.scope("fav_based_user_ nterested_ n_from_pe_cac "))
  }

  pr vate val underly ngStores: Map[
    (Embedd ngType, ModelVers on),
    ReadableStore[S mClustersEmbedd ng d, S mClustersEmbedd ng]
  ] = Map(
    // T et Embedd ngs
    (LogFavBasedT et, Model20m145kUpdated) -> logFavBased20M145KUpdatedT etEmbedd ngStore,
    (LogFavBasedT et, Model20m145k2020) -> logFavBased20M145K2020T etEmbedd ngStore,
    (
      LogFavLongestL2Embedd ngT et,
      Model20m145k2020) -> logFavBasedLongestL2T et20M145K2020Embedd ngStore,
    // Ent y Embedd ngs
    (FavTfgTop c, Model20m145k2020) -> favBasedTfgTop cEmbedd ng2020Store,
    (
      LogFavBasedKgoApeTop c,
      Model20m145k2020) -> logFavBasedApeEnt y20M145K2020Embedd ngCac dStore,
    // KnownFor Embedd ngs
    (FavBasedProducer, Model20m145k2020) -> favBasedProducer20M145K2020Embedd ngStore,
    (
      RelaxedAggregatableLogFavBasedProducer,
      Model20m145k2020) -> relaxedLogFavBasedApe20M145K2020Embedd ngCac dStore,
    //  nterested n Embedd ngs
    (
      LogFavBasedUser nterested nFromAPE,
      Model20m145k2020) -> LogFavBased nterested nFromAPE20M145K2020Store,
    (
      FollowBasedUser nterested nFromAPE,
      Model20m145k2020) -> FollowBased nterested nFromAPE20M145K2020Store,
    (FavBasedUser nterested n, Model20m145kUpdated) -> favBasedUser nterested n20M145KUpdatedStore,
    (FavBasedUser nterested n, Model20m145k2020) -> favBasedUser nterested n20M145K2020Store,
    (LogFavBasedUser nterested n, Model20m145k2020) -> logFavBasedUser nterested n20M145K2020Store,
    (
      FavBasedUser nterested nFromPE,
      Model20m145kUpdated) -> favBasedUser nterested nFromPE20M145KUpdatedStore,
    (F lteredUser nterested n, Model20m145kUpdated) -> user nterested nStore,
    (F lteredUser nterested n, Model20m145k2020) -> user nterested nStore,
    (F lteredUser nterested nFromPE, Model20m145kUpdated) -> user nterested nStore,
    (Unf lteredUser nterested n, Model20m145kUpdated) -> user nterested nStore,
    (Unf lteredUser nterested n, Model20m145k2020) -> user nterested nStore,
  )

  val s mClustersEmbedd ngStore: ReadableStore[S mClustersEmbedd ng d, S mClustersEmbedd ng] = {
    val underly ng: ReadableStore[S mClustersEmbedd ng d, S mClustersEmbedd ng] =
      S mClustersEmbedd ngStore.bu ldW hDec der(
        underly ngStores = underly ngStores,
        dec der = rmsDec der.dec der,
        statsRece ver = statsRece ver.scope("s mClusters_embedd ngs_store_dec derable")
      )

    val underly ngW hT  out: ReadableStore[S mClustersEmbedd ng d, S mClustersEmbedd ng] =
      new ReadableStoreW hT  out(
        rs = underly ng,
        dec der = rmsDec der.dec der,
        enableT  outDec derKey = Dec derConstants.enableS mClustersEmbedd ngStoreT  outs,
        t  outValueKey = Dec derConstants.s mClustersEmbedd ngStoreT  outValueM ll s,
        t  r = t  r,
        statsRece ver = statsRece ver.scope("s mClusters_embedd ng_store_t  outs")
      )

    ObservedReadableStore(
      store = underly ngW hT  out
    )(statsRece ver.scope("s mClusters_embedd ngs_store"))
  }
}
