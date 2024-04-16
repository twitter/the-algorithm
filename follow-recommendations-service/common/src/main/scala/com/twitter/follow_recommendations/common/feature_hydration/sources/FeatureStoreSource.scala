package com.tw ter.follow_recom ndat ons.common.feature_hydrat on.s ces

 mport com.g hub.benmanes.caffe ne.cac .Caffe ne
 mport com.google. nject. nject
 mport com.google. nject.S ngleton
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.T  outExcept on
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.feature_hydrat on.adapters.Cand dateAlgor hmAdapter.remapCand dateS ce
 mport com.tw ter.follow_recom ndat ons.common.feature_hydrat on.common.FeatureS ce
 mport com.tw ter.follow_recom ndat ons.common.feature_hydrat on.common.FeatureS ce d
 mport com.tw ter.follow_recom ndat ons.common.feature_hydrat on.common.HasPreFetc dFeature
 mport com.tw ter.follow_recom ndat ons.common.feature_hydrat on.s ces.Ut ls.adaptAdd  onalFeaturesToDataRecord
 mport com.tw ter.follow_recom ndat ons.common.feature_hydrat on.s ces.Ut ls.random zedTTL
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.HasD splayLocat on
 mport com.tw ter.follow_recom ndat ons.common.models.HasS m larToContext
 mport com.tw ter. rm .constants.Algor hmFeedbackTokens.Algor hmToFeedbackTokenMap
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ml.ap . RecordOneToOneAdapter
 mport com.tw ter.ml.featurestore.catalog.datasets.core.Users ceEnt yDataset
 mport com.tw ter.ml.featurestore.catalog.datasets.mag crecs.Not f cat onSummar esEnt yDataset
 mport com.tw ter.ml.featurestore.catalog.datasets.onboard ng. tr cCenterUserCount ngFeaturesDataset
 mport com.tw ter.ml.featurestore.catalog.datasets.t  l nes.AuthorFeaturesEnt yDataset
 mport com.tw ter.ml.featurestore.catalog.ent  es.core.{Author => AuthorEnt y}
 mport com.tw ter.ml.featurestore.catalog.ent  es.core.{AuthorTop c => AuthorTop cEnt y}
 mport com.tw ter.ml.featurestore.catalog.ent  es.core.{Cand dateUser => Cand dateUserEnt y}
 mport com.tw ter.ml.featurestore.catalog.ent  es.core.{Top c => Top cEnt y}
 mport com.tw ter.ml.featurestore.catalog.ent  es.core.{User => UserEnt y}
 mport com.tw ter.ml.featurestore.catalog.ent  es.core.{UserCand date => UserCand dateEnt y}
 mport com.tw ter.ml.featurestore.catalog.ent  es.onboard ng.UserWtfAlgor hmEnt y
 mport com.tw ter.ml.featurestore.l b.data.Pred ct onRecord
 mport com.tw ter.ml.featurestore.l b.data.Pred ct onRecordAdapter
 mport com.tw ter.ml.featurestore.l b.dataset.onl ne.Hydrator.Hydrat onResponse
 mport com.tw ter.ml.featurestore.l b.dataset.onl ne.Onl neAccessDataset
 mport com.tw ter.ml.featurestore.l b.dataset.Dataset d
 mport com.tw ter.ml.featurestore.l b.dynam c._
 mport com.tw ter.ml.featurestore.l b.feature._
 mport com.tw ter.ml.featurestore.l b.onl ne.DatasetValuesCac 
 mport com.tw ter.ml.featurestore.l b.onl ne.FeatureStoreRequest
 mport com.tw ter.ml.featurestore.l b.onl ne.Onl neFeatureGenerat onStats
 mport com.tw ter.ml.featurestore.l b.EdgeEnt y d
 mport com.tw ter.ml.featurestore.l b.Ent y d
 mport com.tw ter.ml.featurestore.l b.Top c d
 mport com.tw ter.ml.featurestore.l b.User d
 mport com.tw ter.ml.featurestore.l b.WtfAlgor hm d
 mport com.tw ter.onboard ng.relevance.adapters.features.featurestore.Cand dateAuthorTop cAggregatesAdapter
 mport com.tw ter.onboard ng.relevance.adapters.features.featurestore.Cand dateTop cEngage ntRealT  AggregatesAdapter
 mport com.tw ter.onboard ng.relevance.adapters.features.featurestore.Cand dateTop cEngage ntUserStateRealT  AggregatesAdapter
 mport com.tw ter.onboard ng.relevance.adapters.features.featurestore.Cand dateTop cNegat veEngage ntUserStateRealT  AggregatesAdapter
 mport com.tw ter.onboard ng.relevance.adapters.features.featurestore.FeatureStoreAdapter
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams

 mport java.ut l.concurrent.T  Un 

@S ngleton
class FeatureStoreS ce @ nject() (
  serv ce dent f er: Serv ce dent f er,
  stats: StatsRece ver)
    extends FeatureS ce {
   mport FeatureStoreS ce._

  overr de val  d: FeatureS ce d = FeatureS ce d.FeatureStoreS ce d
  overr de val featureContext: FeatureContext = FeatureStoreS ce.getFeatureContext
  val hydrateFeaturesStats = stats.scope("hydrate_features")
  val adapterStats = stats.scope("adapters")
  val featureSet: BoundFeatureSet = BoundFeatureSet(FeatureStoreS ce.allFeatures)
  val cl entConf g: Cl entConf g[HasParams] = Cl entConf g(
    dynam cHydrat onConf g = FeatureStoreS ce.dynam cHydrat onConf g,
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
     tr cCenterUserCount ngFeaturesDataset,
    Users ceEnt yDataset,
    AuthorFeaturesEnt yDataset,
    Not f cat onSummar esEnt yDataset
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
    stats,
    Set(datasetValuesCac )
  )

  pr vate val adapter:  RecordOneToOneAdapter[Pred ct onRecord] =
    Pred ct onRecordAdapter.oneToOne(
      BoundFeatureSet(allFeatures),
      Onl neFeatureGenerat onStats(stats)
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
          val user d = User d(targetUser d)
          val userEnt y d = UserEnt y.w h d(user d)
          val cand dateEnt y d = Cand dateUserEnt y.w h d(User d(cand date. d))
          val userCand dateEdgeEnt y d =
            UserCand dateEnt y.w h d(EdgeEnt y d(user d, User d(cand date. d)))
          val s m larToUser d = target.s m larToUser ds.map( d => AuthorEnt y.w h d(User d( d)))
          val top cProof = cand date.reason.flatMap(_.accountProof.flatMap(_.top cProof))
          val top cEnt  es =  f (top cProof. sDef ned) {
            hydrateFeaturesStats.counter("cand dates_w h_top c_proof"). ncr()
            val top c d = top cProof.get.top c d
            val top cEnt y d = Top cEnt y.w h d(Top c d(top c d))
            val authorTop cEnt y d =
              AuthorTop cEnt y.w h d(EdgeEnt y d(User d(cand date. d), Top c d(top c d)))
            Seq(top cEnt y d, authorTop cEnt y d)
          } else N l

          val cand dateAlgor hmsW hScores = cand date.getAllAlgor hms
          val userWtfAlgEdgeEnt  es =
            cand dateAlgor hmsW hScores.flatMap(algo => {
              val algo d = Algor hmToFeedbackTokenMap.get(remapCand dateS ce(algo))
              algo d.map( d =>
                UserWtfAlgor hmEnt y.w h d(EdgeEnt y d(user d, WtfAlgor hm d( d))))
            })

          val ent  es = Seq(
            userEnt y d,
            cand dateEnt y d,
            userCand dateEdgeEnt y d) ++ s m larToUser d ++ top cEnt  es ++ userWtfAlgEdgeEnt  es
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

// l st of features that   w ll be fetch ng, even  f   are only scr b ng but not scor ng w h t m
object FeatureStoreS ce {

  pr vate val DatasetCac Scope = "feature_store_local_cac "
  pr vate val DefaultCac MaxKeys = 70000

   mport FeatureStoreFeatures._

  ///////////////////// ALL hydrated features /////////////////////
  val allFeatures: Set[BoundFeature[_ <: Ent y d, _]] =
    //target user
    targetUserFeatures ++
      targetUserUserAuthorUserStateRealT  AggregatesFeature ++
      targetUserResurrect onFeatures ++
      targetUserWtf mpress onFeatures ++
      targetUserStatusFeatures ++
      targetUser tr cCountFeatures ++
      //cand date user
      cand dateUserFeatures ++
      cand dateUserResurrect onFeatures ++
      cand dateUserAuthorRealT  AggregateFeatures ++
      cand dateUserStatusFeatures ++
      cand dateUser tr cCountFeatures ++
      cand dateUserT  l nesAuthorAggregateFeatures ++
      cand dateUserCl entFeatures ++
      //s m lar to user
      s m larToUserFeatures ++
      s m larToUserStatusFeatures ++
      s m larToUser tr cCountFeatures ++
      s m larToUserT  l nesAuthorAggregateFeatures ++
      //ot r
      userCand dateEdgeFeatures ++
      userCand dateWtf mpress onCand dateFeatures ++
      top cFeatures ++
      userWtfAlgor hmEdgeFeatures ++
      targetUserCl entFeatures

  val dynam cHydrat onConf g: Dynam cHydrat onConf g[HasParams] =
    Dynam cHydrat onConf g(
      Set(
        GatedFeatures(
          boundFeatureSet = BoundFeatureSet(top cAggregateFeatures),
          gate = HasParams.paramGate(FeatureStoreS ceParams.EnableTop cAggregateFeatures)
        ),
        GatedFeatures(
          boundFeatureSet = BoundFeatureSet(authorTop cFeatures),
          gate =
            HasParams
              .paramGate(FeatureStoreS ceParams.EnableSeparateCl entForT  l nesAuthors).unary_! &
              HasParams.paramGate(FeatureStoreS ceParams.EnableAuthorTop cAggregateFeatures)
        ),
        GatedFeatures(
          boundFeatureSet = BoundFeatureSet(userTop cFeatures),
          gate = HasParams.paramGate(FeatureStoreS ceParams.EnableUserTop cFeatures)
        ),
        GatedFeatures(
          boundFeatureSet = BoundFeatureSet(targetUserFeatures),
          gate = HasParams.paramGate(FeatureStoreS ceParams.EnableTargetUserFeatures)
        ),
        GatedFeatures(
          boundFeatureSet = BoundFeatureSet(targetUserUserAuthorUserStateRealT  AggregatesFeature),
          gate = HasParams.paramGate(
            FeatureStoreS ceParams.EnableTargetUserUserAuthorUserStateRealT  AggregatesFeature)
        ),
        GatedFeatures(
          boundFeatureSet = BoundFeatureSet(targetUserResurrect onFeatures),
          gate = HasParams.paramGate(FeatureStoreS ceParams.EnableTargetUserResurrect onFeatures)
        ),
        GatedFeatures(
          boundFeatureSet = BoundFeatureSet(targetUserWtf mpress onFeatures),
          gate = HasParams.paramGate(FeatureStoreS ceParams.EnableTargetUserWtf mpress onFeatures)
        ),
        GatedFeatures(
          boundFeatureSet = BoundFeatureSet(targetUserStatusFeatures),
          gate =
            HasParams.paramGate(FeatureStoreS ceParams.EnableSeparateCl entForG zmoduck).unary_! &
              HasParams.paramGate(FeatureStoreS ceParams.EnableTargetUserFeatures)
        ),
        GatedFeatures(
          boundFeatureSet = BoundFeatureSet(targetUser tr cCountFeatures),
          gate = HasParams
            .paramGate(
              FeatureStoreS ceParams.EnableSeparateCl entFor tr cCenterUserCount ng).unary_! &
            HasParams.paramGate(FeatureStoreS ceParams.EnableTargetUserFeatures)
        ),
        GatedFeatures(
          boundFeatureSet = BoundFeatureSet(cand dateUserFeatures),
          gate = HasParams.paramGate(FeatureStoreS ceParams.EnableCand dateUserFeatures)
        ),
        GatedFeatures(
          boundFeatureSet = BoundFeatureSet(cand dateUserAuthorRealT  AggregateFeatures),
          gate = HasParams.paramGate(
            FeatureStoreS ceParams.EnableCand dateUserAuthorRealT  AggregateFeatures)
        ),
        GatedFeatures(
          boundFeatureSet = BoundFeatureSet(cand dateUserResurrect onFeatures),
          gate =
            HasParams.paramGate(FeatureStoreS ceParams.EnableCand dateUserResurrect onFeatures)
        ),
        GatedFeatures(
          boundFeatureSet = BoundFeatureSet(cand dateUserStatusFeatures),
          gate =
            HasParams.paramGate(FeatureStoreS ceParams.EnableSeparateCl entForG zmoduck).unary_! &
              HasParams.paramGate(FeatureStoreS ceParams.EnableCand dateUserFeatures)
        ),
        GatedFeatures(
          boundFeatureSet = BoundFeatureSet(cand dateUserT  l nesAuthorAggregateFeatures),
          gate =
            HasParams
              .paramGate(FeatureStoreS ceParams.EnableSeparateCl entForT  l nesAuthors).unary_! &
              HasParams.paramGate(
                FeatureStoreS ceParams.EnableCand dateUserT  l nesAuthorAggregateFeatures)
        ),
        GatedFeatures(
          boundFeatureSet = BoundFeatureSet(cand dateUser tr cCountFeatures),
          gate =
            HasParams
              .paramGate(
                FeatureStoreS ceParams.EnableSeparateCl entFor tr cCenterUserCount ng).unary_! &
              HasParams.paramGate(FeatureStoreS ceParams.EnableCand dateUserFeatures)
        ),
        GatedFeatures(
          boundFeatureSet = BoundFeatureSet(userCand dateEdgeFeatures),
          gate = HasParams.paramGate(FeatureStoreS ceParams.EnableUserCand dateEdgeFeatures)
        ),
        GatedFeatures(
          boundFeatureSet = BoundFeatureSet(userCand dateWtf mpress onCand dateFeatures),
          gate = HasParams.paramGate(
            FeatureStoreS ceParams.EnableUserCand dateWtf mpress onCand dateFeatures)
        ),
        GatedFeatures(
          boundFeatureSet = BoundFeatureSet(userWtfAlgor hmEdgeFeatures),
          gate = HasParams.paramGate(FeatureStoreS ceParams.EnableUserWtfAlgEdgeFeatures)
        ),
        GatedFeatures(
          boundFeatureSet = BoundFeatureSet(s m larToUserFeatures),
          gate = HasParams.paramGate(FeatureStoreS ceParams.EnableS m larToUserFeatures)
        ),
        GatedFeatures(
          boundFeatureSet = BoundFeatureSet(s m larToUserStatusFeatures),
          gate =
            HasParams.paramGate(FeatureStoreS ceParams.EnableSeparateCl entForG zmoduck).unary_! &
              HasParams.paramGate(FeatureStoreS ceParams.EnableS m larToUserFeatures)
        ),
        GatedFeatures(
          boundFeatureSet = BoundFeatureSet(s m larToUserT  l nesAuthorAggregateFeatures),
          gate =
            HasParams
              .paramGate(FeatureStoreS ceParams.EnableSeparateCl entForT  l nesAuthors).unary_! &
              HasParams.paramGate(FeatureStoreS ceParams.EnableS m larToUserFeatures)
        ),
        GatedFeatures(
          boundFeatureSet = BoundFeatureSet(s m larToUser tr cCountFeatures),
          gate =
            HasParams
              .paramGate(
                FeatureStoreS ceParams.EnableSeparateCl entFor tr cCenterUserCount ng).unary_! &
              HasParams.paramGate(FeatureStoreS ceParams.EnableS m larToUserFeatures)
        ),
        GatedFeatures(
          boundFeatureSet = BoundFeatureSet(cand dateUserCl entFeatures),
          gate = HasParams.paramGate(FeatureStoreS ceParams.EnableCand dateCl entFeatures)
        ),
        GatedFeatures(
          boundFeatureSet = BoundFeatureSet(targetUserCl entFeatures),
          gate = HasParams.paramGate(FeatureStoreS ceParams.EnableUserCl entFeatures)
        ),
      )
    )
  // for cal brat ng features, e.g. add log transfor d top c features
  val featureAdapters: Seq[FeatureStoreAdapter] = Seq(
    Cand dateTop cEngage ntRealT  AggregatesAdapter,
    Cand dateTop cNegat veEngage ntUserStateRealT  AggregatesAdapter,
    Cand dateTop cEngage ntUserStateRealT  AggregatesAdapter,
    Cand dateAuthorTop cAggregatesAdapter
  )
  val add  onalFeatureContext: FeatureContext = FeatureContext. rge(
    featureAdapters
      .foldR ght(new FeatureContext())((adapter, context) =>
        context
          .addFeatures(adapter.getFeatureContext))
  )
  val getFeatureContext: FeatureContext =
    BoundFeatureSet(allFeatures).toFeatureContext
      .addFeatures(add  onalFeatureContext)
      // T  below are aggregated features that are aggregated for a second t   over mult ple keys.
      .addFeatures(maxSumAvgAggregatedFeatureContext)
}
