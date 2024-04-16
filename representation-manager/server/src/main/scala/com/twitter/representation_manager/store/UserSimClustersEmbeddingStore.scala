package com.tw ter.representat on_manager.store

 mport com.tw ter.contentrecom nder.tw stly
 mport com.tw ter.f nagle. mcac d.Cl ent
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.store.strato.StratoFetchableStore
 mport com.tw ter. rm .store.common.ObservedReadableStore
 mport com.tw ter.representat on_manager.common. mCac Conf g
 mport com.tw ter.representat on_manager.common.Representat onManagerDec der
 mport com.tw ter.s mclusters_v2.common.ModelVers ons
 mport com.tw ter.s mclusters_v2.common.S mClustersEmbedd ng
 mport com.tw ter.s mclusters_v2.stores.S mClustersEmbedd ngStore
 mport com.tw ter.s mclusters_v2.summ ngb rd.stores.ProducerClusterEmbedd ngReadableStores
 mport com.tw ter.s mclusters_v2.summ ngb rd.stores.User nterested nReadableStore
 mport com.tw ter.s mclusters_v2.summ ngb rd.stores.User nterested nReadableStore.getStore
 mport com.tw ter.s mclusters_v2.summ ngb rd.stores.User nterested nReadableStore.modelVers onToDatasetMap
 mport com.tw ter.s mclusters_v2.summ ngb rd.stores.User nterested nReadableStore.knownModelVers ons
 mport com.tw ter.s mclusters_v2.summ ngb rd.stores.User nterested nReadableStore.toS mClustersEmbedd ng
 mport com.tw ter.s mclusters_v2.thr ftscala.ClustersUser s nterested n
 mport com.tw ter.s mclusters_v2.thr ftscala.Embedd ngType
 mport com.tw ter.s mclusters_v2.thr ftscala.Embedd ngType._
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on
 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on._
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersEmbedd ng d
 mport com.tw ter.s mclusters_v2.thr ftscala.{S mClustersEmbedd ng => Thr ftS mClustersEmbedd ng}
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVCl entMtlsParams
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.storehaus_ nternal.manhattan.Apollo
 mport com.tw ter.storehaus_ nternal.manhattan.ManhattanCluster
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.strato.thr ft.ScroogeConv mpl c s._
 mport com.tw ter.t etyp e.ut l.User d
 mport com.tw ter.ut l.Future
 mport javax. nject. nject

class UserS mClustersEmbedd ngStore @ nject() (
  stratoCl ent: StratoCl ent,
  cac Cl ent: Cl ent,
  globalStats: StatsRece ver,
  mhMtlsParams: ManhattanKVCl entMtlsParams,
  rmsDec der: Representat onManagerDec der) {

  pr vate val stats = globalStats.scope(t .getClass.getS mpleNa )

  pr vate val favBasedProducer20M145KUpdatedEmbedd ngStore: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    val rawStore = ProducerClusterEmbedd ngReadableStores
      .getProducerTopKS mClustersEmbedd ngsStore(
        mhMtlsParams
      ).mapValues { topS mClustersW hScore =>
        Thr ftS mClustersEmbedd ng(topS mClustersW hScore.topClusters)
      }.composeKeyMapp ng[S mClustersEmbedd ng d] {
        case S mClustersEmbedd ng d(_, _,  nternal d.User d(user d)) =>
          user d
      }

    bu ld mCac Store(rawStore, FavBasedProducer, Model20m145kUpdated)
  }

  pr vate val favBasedProducer20M145K2020Embedd ngStore: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    val rawStore = ProducerClusterEmbedd ngReadableStores
      .getProducerTopKS mClusters2020Embedd ngsStore(
        mhMtlsParams
      ).mapValues { topS mClustersW hScore =>
        Thr ftS mClustersEmbedd ng(topS mClustersW hScore.topClusters)
      }.composeKeyMapp ng[S mClustersEmbedd ng d] {
        case S mClustersEmbedd ng d(_, _,  nternal d.User d(user d)) =>
          user d
      }

    bu ld mCac Store(rawStore, FavBasedProducer, Model20m145k2020)
  }

  pr vate val followBasedProducer20M145K2020Embedd ngStore: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    val rawStore = ProducerClusterEmbedd ngReadableStores
      .getProducerTopKS mClustersEmbedd ngsByFollowStore(
        mhMtlsParams
      ).mapValues { topS mClustersW hScore =>
        Thr ftS mClustersEmbedd ng(topS mClustersW hScore.topClusters)
      }.composeKeyMapp ng[S mClustersEmbedd ng d] {
        case S mClustersEmbedd ng d(_, _,  nternal d.User d(user d)) =>
          user d
      }

    bu ld mCac Store(rawStore, FollowBasedProducer, Model20m145k2020)
  }

  pr vate val logFavBasedApe20M145K2020Embedd ngStore: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    val rawStore = StratoFetchableStore
      .w hUn V ew[S mClustersEmbedd ng d, Thr ftS mClustersEmbedd ng](
        stratoCl ent,
        "recom ndat ons/s mclusters_v2/embedd ngs/logFavBasedAPE20M145K2020")
      .mapValues(embedd ng => S mClustersEmbedd ng(embedd ng, truncate = 50).toThr ft)

    bu ld mCac Store(rawStore, AggregatableLogFavBasedProducer, Model20m145k2020)
  }

  pr vate val rawRelaxedLogFavBasedApe20M145K2020Embedd ngStore: ReadableStore[
    S mClustersEmbedd ng d,
    Thr ftS mClustersEmbedd ng
  ] = {
    StratoFetchableStore
      .w hUn V ew[S mClustersEmbedd ng d, Thr ftS mClustersEmbedd ng](
        stratoCl ent,
        "recom ndat ons/s mclusters_v2/embedd ngs/logFavBasedAPERelaxedFavEngage ntThreshold20M145K2020")
      .mapValues(embedd ng => S mClustersEmbedd ng(embedd ng, truncate = 50).toThr ft)
  }

  pr vate val relaxedLogFavBasedApe20M145K2020Embedd ngStore: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    bu ld mCac Store(
      rawRelaxedLogFavBasedApe20M145K2020Embedd ngStore,
      RelaxedAggregatableLogFavBasedProducer,
      Model20m145k2020)
  }

  pr vate val relaxedLogFavBasedApe20m145kUpdatedEmbedd ngStore: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    val rawStore = rawRelaxedLogFavBasedApe20M145K2020Embedd ngStore
      .composeKeyMapp ng[S mClustersEmbedd ng d] {
        case S mClustersEmbedd ng d(
              RelaxedAggregatableLogFavBasedProducer,
              Model20m145kUpdated,
               nternal d) =>
          S mClustersEmbedd ng d(
            RelaxedAggregatableLogFavBasedProducer,
            Model20m145k2020,
             nternal d)
      }

    bu ld mCac Store(rawStore, RelaxedAggregatableLogFavBasedProducer, Model20m145kUpdated)
  }

  pr vate val logFavBased nterested nFromAPE20M145K2020Store: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    bu ldUser nterested nStore(
      User nterested nReadableStore.default  APES mClustersEmbedd ngStoreW hMtls,
      LogFavBasedUser nterested nFromAPE,
      Model20m145k2020)
  }

  pr vate val followBased nterested nFromAPE20M145K2020Store: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    bu ldUser nterested nStore(
      User nterested nReadableStore.default  APES mClustersEmbedd ngStoreW hMtls,
      FollowBasedUser nterested nFromAPE,
      Model20m145k2020)
  }

  pr vate val favBasedUser nterested n20M145KUpdatedStore: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    bu ldUser nterested nStore(
      User nterested nReadableStore.defaultS mClustersEmbedd ngStoreW hMtls,
      FavBasedUser nterested n,
      Model20m145kUpdated)
  }

  pr vate val favBasedUser nterested n20M145K2020Store: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    bu ldUser nterested nStore(
      User nterested nReadableStore.defaultS mClustersEmbedd ngStoreW hMtls,
      FavBasedUser nterested n,
      Model20m145k2020)
  }

  pr vate val followBasedUser nterested n20M145K2020Store: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    bu ldUser nterested nStore(
      User nterested nReadableStore.defaultS mClustersEmbedd ngStoreW hMtls,
      FollowBasedUser nterested n,
      Model20m145k2020)
  }

  pr vate val logFavBasedUser nterested n20M145K2020Store: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    bu ldUser nterested nStore(
      User nterested nReadableStore.defaultS mClustersEmbedd ngStoreW hMtls,
      LogFavBasedUser nterested n,
      Model20m145k2020)
  }

  pr vate val favBasedUser nterested nFromPE20M145KUpdatedStore: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    bu ldUser nterested nStore(
      User nterested nReadableStore.default  PES mClustersEmbedd ngStoreW hMtls,
      FavBasedUser nterested nFromPE,
      Model20m145kUpdated)
  }

  pr vate val tw stlyUser nterested nStore: ReadableStore[
    S mClustersEmbedd ng d,
    Thr ftS mClustersEmbedd ng
  ] = {
    val  nterested n20M145KUpdatedStore = {
      User nterested nReadableStore.defaultStoreW hMtls(
        mhMtlsParams,
        modelVers on = ModelVers ons.Model20M145KUpdated
      )
    }
    val  nterested n20M145K2020Store = {
      User nterested nReadableStore.defaultStoreW hMtls(
        mhMtlsParams,
        modelVers on = ModelVers ons.Model20M145K2020
      )
    }
    val  nterested nFromPE20M145KUpdatedStore = {
      User nterested nReadableStore.default  PEStoreW hMtls(
        mhMtlsParams,
        modelVers on = ModelVers ons.Model20M145KUpdated)
    }
    val s mClusters nterested nStore: ReadableStore[
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
    val s mClusters nterested nFromProducerEmbedd ngsStore: ReadableStore[
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
    new tw stly. nterested n.Embedd ngStore(
       nterested nStore = s mClusters nterested nStore,
       nterested nFromProducerEmbedd ngStore = s mClusters nterested nFromProducerEmbedd ngsStore,
      statsRece ver = stats
    ).mapValues(_.toThr ft)
  }

  pr vate val userNext nterested n20m145k2020Store: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    bu ldUser nterested nStore(
      User nterested nReadableStore.defaultNext nterested nStoreW hMtls,
      UserNext nterested n,
      Model20m145k2020)
  }

  pr vate val f lteredUser nterested n20m145kUpdatedStore: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    bu ld mCac Store(tw stlyUser nterested nStore, F lteredUser nterested n, Model20m145kUpdated)
  }

  pr vate val f lteredUser nterested n20m145k2020Store: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    bu ld mCac Store(tw stlyUser nterested nStore, F lteredUser nterested n, Model20m145k2020)
  }

  pr vate val f lteredUser nterested nFromPE20m145kUpdatedStore: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    bu ld mCac Store(
      tw stlyUser nterested nStore,
      F lteredUser nterested nFromPE,
      Model20m145kUpdated)
  }

  pr vate val unf lteredUser nterested n20m145kUpdatedStore: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    bu ld mCac Store(
      tw stlyUser nterested nStore,
      Unf lteredUser nterested n,
      Model20m145kUpdated)
  }

  pr vate val unf lteredUser nterested n20m145k2020Store: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    bu ld mCac Store(tw stlyUser nterested nStore, Unf lteredUser nterested n, Model20m145k2020)
  }

  // [Exper  ntal] User  nterested n, generated by aggregat ng   APE embedd ng from AddressBook

  pr vate val logFavBased nterestedMaxpool ngAddressBookFrom  APE20M145K2020Store: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    val datasetNa  = "addressbook_s ms_embedd ng_  ape_maxpool ng"
    val app d = "wtf_embedd ng_apollo"
    bu ldUser nterested nStoreGener c(
      s mClustersEmbedd ngStoreW hMtls,
      LogFavBasedUser nterestedMaxpool ngAddressBookFrom  APE,
      Model20m145k2020,
      datasetNa  = datasetNa ,
      app d = app d,
      manhattanCluster = Apollo
    )
  }

  pr vate val logFavBased nterestedAverageAddressBookFrom  APE20M145K2020Store: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    val datasetNa  = "addressbook_s ms_embedd ng_  ape_average"
    val app d = "wtf_embedd ng_apollo"
    bu ldUser nterested nStoreGener c(
      s mClustersEmbedd ngStoreW hMtls,
      LogFavBasedUser nterestedAverageAddressBookFrom  APE,
      Model20m145k2020,
      datasetNa  = datasetNa ,
      app d = app d,
      manhattanCluster = Apollo
    )
  }

  pr vate val logFavBasedUser nterestedBooktypeMaxpool ngAddressBookFrom  APE20M145K2020Store: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    val datasetNa  = "addressbook_s ms_embedd ng_  ape_booktype_maxpool ng"
    val app d = "wtf_embedd ng_apollo"
    bu ldUser nterested nStoreGener c(
      s mClustersEmbedd ngStoreW hMtls,
      LogFavBasedUser nterestedBooktypeMaxpool ngAddressBookFrom  APE,
      Model20m145k2020,
      datasetNa  = datasetNa ,
      app d = app d,
      manhattanCluster = Apollo
    )
  }

  pr vate val logFavBasedUser nterestedLargestD mMaxpool ngAddressBookFrom  APE20M145K2020Store: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    val datasetNa  = "addressbook_s ms_embedd ng_  ape_largestd m_maxpool ng"
    val app d = "wtf_embedd ng_apollo"
    bu ldUser nterested nStoreGener c(
      s mClustersEmbedd ngStoreW hMtls,
      LogFavBasedUser nterestedLargestD mMaxpool ngAddressBookFrom  APE,
      Model20m145k2020,
      datasetNa  = datasetNa ,
      app d = app d,
      manhattanCluster = Apollo
    )
  }

  pr vate val logFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE20M145K2020Store: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    val datasetNa  = "addressbook_s ms_embedd ng_  ape_louva n_maxpool ng"
    val app d = "wtf_embedd ng_apollo"
    bu ldUser nterested nStoreGener c(
      s mClustersEmbedd ngStoreW hMtls,
      LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE,
      Model20m145k2020,
      datasetNa  = datasetNa ,
      app d = app d,
      manhattanCluster = Apollo
    )
  }

  pr vate val logFavBasedUser nterestedConnectedMaxpool ngAddressBookFrom  APE20M145K2020Store: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    val datasetNa  = "addressbook_s ms_embedd ng_  ape_connected_maxpool ng"
    val app d = "wtf_embedd ng_apollo"
    bu ldUser nterested nStoreGener c(
      s mClustersEmbedd ngStoreW hMtls,
      LogFavBasedUser nterestedConnectedMaxpool ngAddressBookFrom  APE,
      Model20m145k2020,
      datasetNa  = datasetNa ,
      app d = app d,
      manhattanCluster = Apollo
    )
  }

  /**
   *  lper func to bu ld a readable store for so  User nterested n embedd ngs w h
   *    1. A storeFunc from User nterested nReadableStore
   *    2. Embedd ngType
   *    3. ModelVers on
   *    4.  mCac Conf g
   * */
  pr vate def bu ldUser nterested nStore(
    storeFunc: (ManhattanKVCl entMtlsParams, Embedd ngType, ModelVers on) => ReadableStore[
      S mClustersEmbedd ng d,
      S mClustersEmbedd ng
    ],
    embedd ngType: Embedd ngType,
    modelVers on: ModelVers on
  ): ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    val rawStore = storeFunc(mhMtlsParams, embedd ngType, modelVers on)
      .mapValues(_.toThr ft)
    val observedStore = ObservedReadableStore(
      store = rawStore
    )(stats.scope(embedd ngType.na ).scope(modelVers on.na ))

     mCac Conf g.bu ld mCac StoreForS mClustersEmbedd ng(
      observedStore,
      cac Cl ent,
      embedd ngType,
      modelVers on,
      stats
    )
  }

  pr vate def bu ldUser nterested nStoreGener c(
    storeFunc: (ManhattanKVCl entMtlsParams, Embedd ngType, ModelVers on, Str ng, Str ng,
      ManhattanCluster) => ReadableStore[
      S mClustersEmbedd ng d,
      S mClustersEmbedd ng
    ],
    embedd ngType: Embedd ngType,
    modelVers on: ModelVers on,
    datasetNa : Str ng,
    app d: Str ng,
    manhattanCluster: ManhattanCluster
  ): ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    val rawStore =
      storeFunc(mhMtlsParams, embedd ngType, modelVers on, datasetNa , app d, manhattanCluster)
        .mapValues(_.toThr ft)
    val observedStore = ObservedReadableStore(
      store = rawStore
    )(stats.scope(embedd ngType.na ).scope(modelVers on.na ))

     mCac Conf g.bu ld mCac StoreForS mClustersEmbedd ng(
      observedStore,
      cac Cl ent,
      embedd ngType,
      modelVers on,
      stats
    )
  }

  pr vate def s mClustersEmbedd ngStoreW hMtls(
    mhMtlsParams: ManhattanKVCl entMtlsParams,
    embedd ngType: Embedd ngType,
    modelVers on: ModelVers on,
    datasetNa : Str ng,
    app d: Str ng,
    manhattanCluster: ManhattanCluster
  ): ReadableStore[S mClustersEmbedd ng d, S mClustersEmbedd ng] = {

     f (!modelVers onToDatasetMap.conta ns(ModelVers ons.toKnownForModelVers on(modelVers on))) {
      throw new  llegalArgu ntExcept on(
        "Unknown model vers on: " + modelVers on + ". Known model vers ons: " + knownModelVers ons)
    }
    getStore(app d, mhMtlsParams, datasetNa , manhattanCluster)
      .composeKeyMapp ng[S mClustersEmbedd ng d] {
        case S mClustersEmbedd ng d(t Embedd ngType, t ModelVers on,  nternal d.User d(user d))
             f t Embedd ngType == embedd ngType && t ModelVers on == modelVers on =>
          user d
      }.mapValues(toS mClustersEmbedd ng(_, embedd ngType))
  }

  pr vate def bu ld mCac Store(
    rawStore: ReadableStore[S mClustersEmbedd ng d, Thr ftS mClustersEmbedd ng],
    embedd ngType: Embedd ngType,
    modelVers on: ModelVers on
  ): ReadableStore[S mClustersEmbedd ng d, S mClustersEmbedd ng] = {
    val observedStore = ObservedReadableStore(
      store = rawStore
    )(stats.scope(embedd ngType.na ).scope(modelVers on.na ))

     mCac Conf g.bu ld mCac StoreForS mClustersEmbedd ng(
      observedStore,
      cac Cl ent,
      embedd ngType,
      modelVers on,
      stats
    )
  }

  pr vate val underly ngStores: Map[
    (Embedd ngType, ModelVers on),
    ReadableStore[S mClustersEmbedd ng d, S mClustersEmbedd ng]
  ] = Map(
    // KnownFor Embedd ngs
    (FavBasedProducer, Model20m145kUpdated) -> favBasedProducer20M145KUpdatedEmbedd ngStore,
    (FavBasedProducer, Model20m145k2020) -> favBasedProducer20M145K2020Embedd ngStore,
    (FollowBasedProducer, Model20m145k2020) -> followBasedProducer20M145K2020Embedd ngStore,
    (AggregatableLogFavBasedProducer, Model20m145k2020) -> logFavBasedApe20M145K2020Embedd ngStore,
    (
      RelaxedAggregatableLogFavBasedProducer,
      Model20m145kUpdated) -> relaxedLogFavBasedApe20m145kUpdatedEmbedd ngStore,
    (
      RelaxedAggregatableLogFavBasedProducer,
      Model20m145k2020) -> relaxedLogFavBasedApe20M145K2020Embedd ngStore,
    //  nterested n Embedd ngs
    (
      LogFavBasedUser nterested nFromAPE,
      Model20m145k2020) -> logFavBased nterested nFromAPE20M145K2020Store,
    (
      FollowBasedUser nterested nFromAPE,
      Model20m145k2020) -> followBased nterested nFromAPE20M145K2020Store,
    (FavBasedUser nterested n, Model20m145kUpdated) -> favBasedUser nterested n20M145KUpdatedStore,
    (FavBasedUser nterested n, Model20m145k2020) -> favBasedUser nterested n20M145K2020Store,
    (FollowBasedUser nterested n, Model20m145k2020) -> followBasedUser nterested n20M145K2020Store,
    (LogFavBasedUser nterested n, Model20m145k2020) -> logFavBasedUser nterested n20M145K2020Store,
    (
      FavBasedUser nterested nFromPE,
      Model20m145kUpdated) -> favBasedUser nterested nFromPE20M145KUpdatedStore,
    (F lteredUser nterested n, Model20m145kUpdated) -> f lteredUser nterested n20m145kUpdatedStore,
    (F lteredUser nterested n, Model20m145k2020) -> f lteredUser nterested n20m145k2020Store,
    (
      F lteredUser nterested nFromPE,
      Model20m145kUpdated) -> f lteredUser nterested nFromPE20m145kUpdatedStore,
    (
      Unf lteredUser nterested n,
      Model20m145kUpdated) -> unf lteredUser nterested n20m145kUpdatedStore,
    (Unf lteredUser nterested n, Model20m145k2020) -> unf lteredUser nterested n20m145k2020Store,
    (UserNext nterested n, Model20m145k2020) -> userNext nterested n20m145k2020Store,
    (
      LogFavBasedUser nterestedMaxpool ngAddressBookFrom  APE,
      Model20m145k2020) -> logFavBased nterestedMaxpool ngAddressBookFrom  APE20M145K2020Store,
    (
      LogFavBasedUser nterestedAverageAddressBookFrom  APE,
      Model20m145k2020) -> logFavBased nterestedAverageAddressBookFrom  APE20M145K2020Store,
    (
      LogFavBasedUser nterestedBooktypeMaxpool ngAddressBookFrom  APE,
      Model20m145k2020) -> logFavBasedUser nterestedBooktypeMaxpool ngAddressBookFrom  APE20M145K2020Store,
    (
      LogFavBasedUser nterestedLargestD mMaxpool ngAddressBookFrom  APE,
      Model20m145k2020) -> logFavBasedUser nterestedLargestD mMaxpool ngAddressBookFrom  APE20M145K2020Store,
    (
      LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE,
      Model20m145k2020) -> logFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE20M145K2020Store,
    (
      LogFavBasedUser nterestedConnectedMaxpool ngAddressBookFrom  APE,
      Model20m145k2020) -> logFavBasedUser nterestedConnectedMaxpool ngAddressBookFrom  APE20M145K2020Store,
  )

  val userS mClustersEmbedd ngStore: ReadableStore[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] = {
    S mClustersEmbedd ngStore.bu ldW hDec der(
      underly ngStores = underly ngStores,
      dec der = rmsDec der.dec der,
      statsRece ver = stats
    )
  }

}
