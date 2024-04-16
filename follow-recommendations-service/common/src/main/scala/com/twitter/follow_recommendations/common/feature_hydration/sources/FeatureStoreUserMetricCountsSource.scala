package com.tw ter.follow_recom ndat ons.common.feature_hydrat on.s ces

 mport com.g hub.benmanes.caffe ne.cac .Caffe ne
 mport com.google. nject. nject
 mport com.tw ter.f nagle.T  outExcept on
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.feature_hydrat on.common.FeatureS ce
 mport com.tw ter.follow_recom ndat ons.common.feature_hydrat on.common.FeatureS ce d
 mport com.tw ter.follow_recom ndat ons.common.feature_hydrat on.common.HasPreFetc dFeature
 mport com.tw ter.follow_recom ndat ons.common.feature_hydrat on.s ces.Ut ls.adaptAdd  onalFeaturesToDataRecord
 mport com.tw ter.follow_recom ndat ons.common.feature_hydrat on.s ces.Ut ls.random zedTTL
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.HasS m larToContext
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ml.ap . RecordOneToOneAdapter
 mport com.tw ter.ml.featurestore.catalog.datasets.onboard ng. tr cCenterUserCount ngFeaturesDataset
 mport com.tw ter.ml.featurestore.catalog.ent  es.core.{Author => AuthorEnt y}
 mport com.tw ter.ml.featurestore.catalog.ent  es.core.{AuthorTop c => AuthorTop cEnt y}
 mport com.tw ter.ml.featurestore.catalog.ent  es.core.{Cand dateUser => Cand dateUserEnt y}
 mport com.tw ter.ml.featurestore.catalog.ent  es.core.{User => UserEnt y}
 mport com.tw ter.ml.featurestore.l b.EdgeEnt y d
 mport com.tw ter.ml.featurestore.l b.Ent y d
 mport com.tw ter.ml.featurestore.l b.Top c d
 mport com.tw ter.ml.featurestore.l b.User d
 mport com.tw ter.ml.featurestore.l b.data.Pred ct onRecord
 mport com.tw ter.ml.featurestore.l b.data.Pred ct onRecordAdapter
 mport com.tw ter.ml.featurestore.l b.dataset.Dataset d
 mport com.tw ter.ml.featurestore.l b.dataset.onl ne.Hydrator.Hydrat onResponse
 mport com.tw ter.ml.featurestore.l b.dataset.onl ne.Onl neAccessDataset
 mport com.tw ter.ml.featurestore.l b.dynam c.Cl entConf g
 mport com.tw ter.ml.featurestore.l b.dynam c.Dynam cFeatureStoreCl ent
 mport com.tw ter.ml.featurestore.l b.dynam c.Dynam cHydrat onConf g
 mport com.tw ter.ml.featurestore.l b.dynam c.FeatureStoreParamsConf g
 mport com.tw ter.ml.featurestore.l b.dynam c.GatedFeatures
 mport com.tw ter.ml.featurestore.l b.feature.BoundFeature
 mport com.tw ter.ml.featurestore.l b.feature.BoundFeatureSet
 mport com.tw ter.ml.featurestore.l b.onl ne.DatasetValuesCac 
 mport com.tw ter.ml.featurestore.l b.onl ne.FeatureStoreRequest
 mport com.tw ter.ml.featurestore.l b.onl ne.Onl neFeatureGenerat onStats
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport java.ut l.concurrent.T  Un 
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.follow_recom ndat ons.common.models.HasD splayLocat on
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext

class FeatureStoreUser tr cCountsS ce @ nject() (
  serv ce dent f er: Serv ce dent f er,
  stats: StatsRece ver)
    extends FeatureS ce {
   mport FeatureStoreUser tr cCountsS ce._

  val backupS ceStats = stats.scope("feature_store_hydrat on_mc_count ng")
  val adapterStats = backupS ceStats.scope("adapters")
  overr de def  d: FeatureS ce d = FeatureS ce d.FeatureStoreUser tr cCountsS ce d
  overr de def featureContext: FeatureContext = getFeatureContext

  val cl entConf g: Cl entConf g[HasParams] = Cl entConf g(
    dynam cHydrat onConf g = dynam cHydrat onConf g,
    featureStoreParamsConf g =
      FeatureStoreParamsConf g(FeatureStorePara ters.featureStoreParams, Map.empty),
    /**
     * T  smaller one bet en `t  outProv der` and `FeatureStoreS ceParams.GlobalFetchT  out`
     * used below takes effect.
     */
    t  outProv der = Funct on.const(800.m ll s),
    serv ce dent f er = serv ce dent f er
  )

  pr vate val datasetsToCac  = Set(
     tr cCenterUserCount ngFeaturesDataset
  ).as nstanceOf[Set[Onl neAccessDataset[_ <: Ent y d, _]]]

  pr vate val datasetValuesCac : DatasetValuesCac  =
    DatasetValuesCac (
      Caffe ne
        .newBu lder()
        .exp reAfterWr e(random zedTTL(12.h s. nSeconds), T  Un .SECONDS)
        .max mumS ze(DefaultCac MaxKeys)
        .bu ld[(_ <: Ent y d, Dataset d), St ch[Hydrat onResponse[_]]]
        .asMap,
      datasetsToCac ,
      DatasetCac Scope
    )

  pr vate val dynam cFeatureStoreCl ent = Dynam cFeatureStoreCl ent(
    cl entConf g,
    backupS ceStats,
    Set(datasetValuesCac )
  )

  pr vate val adapter:  RecordOneToOneAdapter[Pred ct onRecord] =
    Pred ct onRecordAdapter.oneToOne(
      BoundFeatureSet(allFeatures),
      Onl neFeatureGenerat onStats(backupS ceStats)
    )

  overr de def hydrateFeatures(
    target: HasCl entContext
      w h HasPreFetc dFeature
      w h HasParams
      w h HasS m larToContext
      w h HasD splayLocat on,
    cand dates: Seq[Cand dateUser]
  ): St ch[Map[Cand dateUser, DataRecord]] = {
    target.getOpt onalUser d
      .map { targetUser d =>
        val featureRequests = cand dates.map { cand date =>
          val userEnt y d = UserEnt y.w h d(User d(targetUser d))
          val cand dateEnt y d = Cand dateUserEnt y.w h d(User d(cand date. d))
          val s m larToUser d = target.s m larToUser ds.map( d => AuthorEnt y.w h d(User d( d)))
          val top cProof = cand date.reason.flatMap(_.accountProof.flatMap(_.top cProof))
          val authorTop cEnt y =  f (top cProof. sDef ned) {
            backupS ceStats.counter("cand dates_w h_top c_proof"). ncr()
            Set(
              AuthorTop cEnt y.w h d(
                EdgeEnt y d(User d(cand date. d), Top c d(top cProof.get.top c d))))
          } else N l

          val ent  es =
            Seq(userEnt y d, cand dateEnt y d) ++ s m larToUser d ++ authorTop cEnt y
          FeatureStoreRequest(ent  es)
        }

        val pred ct onRecordsFut = dynam cFeatureStoreCl ent(featureRequests, target)
        val cand dateFeatureMap = pred ct onRecordsFut.map { pred ct onRecords =>
          //   can z p pred ct onRecords w h cand dates as t  order  s preserved  n t  cl ent
          cand dates
            .z p(pred ct onRecords).map {
              case (cand date, pred ct onRecord) =>
                cand date -> adaptAdd  onalFeaturesToDataRecord(
                  adapter.adaptToDataRecord(pred ct onRecord),
                  adapterStats,
                  FeatureStoreS ce.featureAdapters)
            }.toMap
        }
        St ch
          .callFuture(cand dateFeatureMap)
          .w h n(target.params(FeatureStoreS ceParams.GlobalFetchT  out))(
            com.tw ter.f nagle.ut l.DefaultT  r)
          .rescue {
            case _: T  outExcept on =>
              St ch.value(Map.empty[Cand dateUser, DataRecord])
          }
      }.getOrElse(St ch.value(Map.empty[Cand dateUser, DataRecord]))
  }
}

object FeatureStoreUser tr cCountsS ce {
  pr vate val DatasetCac Scope = "feature_store_local_cac _mc_user_count ng"
  pr vate val DefaultCac MaxKeys = 20000

  val allFeatures: Set[BoundFeature[_ <: Ent y d, _]] =
    FeatureStoreFeatures.cand dateUser tr cCountFeatures ++
      FeatureStoreFeatures.s m larToUser tr cCountFeatures ++
      FeatureStoreFeatures.targetUser tr cCountFeatures

  val getFeatureContext: FeatureContext =
    BoundFeatureSet(allFeatures).toFeatureContext

  val dynam cHydrat onConf g: Dynam cHydrat onConf g[HasParams] =
    Dynam cHydrat onConf g(
      Set(
        GatedFeatures(
          boundFeatureSet = BoundFeatureSet(FeatureStoreFeatures.targetUser tr cCountFeatures),
          gate = HasParams
            .paramGate(FeatureStoreS ceParams.EnableSeparateCl entFor tr cCenterUserCount ng) &
            HasParams.paramGate(FeatureStoreS ceParams.EnableTargetUserFeatures)
        ),
        GatedFeatures(
          boundFeatureSet = BoundFeatureSet(FeatureStoreFeatures.cand dateUser tr cCountFeatures),
          gate =
            HasParams
              .paramGate(FeatureStoreS ceParams.EnableSeparateCl entFor tr cCenterUserCount ng) &
              HasParams.paramGate(FeatureStoreS ceParams.EnableCand dateUserFeatures)
        ),
        GatedFeatures(
          boundFeatureSet = BoundFeatureSet(FeatureStoreFeatures.s m larToUser tr cCountFeatures),
          gate =
            HasParams
              .paramGate(FeatureStoreS ceParams.EnableSeparateCl entFor tr cCenterUserCount ng) &
              HasParams.paramGate(FeatureStoreS ceParams.EnableS m larToUserFeatures)
        ),
      ))
}
