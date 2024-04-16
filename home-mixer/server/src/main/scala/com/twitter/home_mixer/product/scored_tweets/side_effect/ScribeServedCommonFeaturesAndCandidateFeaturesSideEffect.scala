package com.tw ter.ho _m xer.product.scored_t ets.s de_effect

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle. sql.Cl ent
 mport com.tw ter.f nagle. sql.Transact ons
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.ut l.DefaultT  r
 mport com.tw ter.ho _m xer.model.Ho Features.ServedRequest dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.S ceT et dFeature
 mport com.tw ter.ho _m xer.param.Ho M xerFlagNa .DataRecord tadataStoreConf gsYmlFlag
 mport com.tw ter.ho _m xer.param.Ho M xerFlagNa .Scr beServedCommonFeaturesAndCand dateFeaturesFlag
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.Cand dateFeaturesScr beEventPubl s r
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.CommonFeaturesScr beEventPubl s r
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.M n mumFeaturesScr beEventPubl s r
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.adapters.non_ml_features.NonMLCand dateFeatures
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.adapters.non_ml_features.NonMLCand dateFeaturesAdapter
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.adapters.non_ml_features.NonMLCommonFeatures
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.adapters.non_ml_features.NonMLCommonFeaturesAdapter
 mport com.tw ter.ho _m xer.product.scored_t ets.model.ScoredT etsQuery
 mport com.tw ter.ho _m xer.product.scored_t ets.model.ScoredT etsResponse
 mport com.tw ter.ho _m xer.product.scored_t ets.scorer.Cand dateFeaturesDataRecordFeature
 mport com.tw ter.ho _m xer.product.scored_t ets.scorer.CommonFeaturesDataRecordFeature
 mport com.tw ter.ho _m xer.product.scored_t ets.scorer.Pred ctedScoreFeature.Pred ctedScoreFeatures
 mport com.tw ter.ho _m xer.ut l.Cand datesUt l.getOr g nalAuthor d
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.logp pel ne.cl ent.common.EventPubl s r
 mport com.tw ter.ml.ap .DataRecord rger
 mport com.tw ter.product_m xer.core.feature.featuremap.datarecord.DataRecordConverter
 mport com.tw ter.product_m xer.core.feature.featuremap.datarecord.Spec f cFeatures
 mport com.tw ter.product_m xer.core.funct onal_component.s de_effect.P pel neResultS deEffect
 mport com.tw ter.product_m xer.core.model.common. dent f er.S deEffect dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.ml.cont_tra n.common.doma n.non_scald ng.Cand dateAndCommonFeaturesStream ngUt ls
 mport com.tw ter.t  l nes.ml.pldr.cl ent. sqlCl entUt ls
 mport com.tw ter.t  l nes.ml.pldr.cl ent.Vers oned tadataCac Cl ent
 mport com.tw ter.t  l nes.ml.pldr.convers on.Vers on dAndFeatures
 mport com.tw ter.t  l nes.suggests.common.data_record_ tadata.{thr ftscala => drmd}
 mport com.tw ter.t  l nes.suggests.common.poly_data_record.{thr ftjava => pldr}
 mport com.tw ter.t  l nes.ut l.stats.Opt onObserver
 mport com.tw ter.ut l.T  
 mport com.tw ter.ut l.Try
 mport com.tw ter.ut l.logg ng.Logg ng
 mport javax. nject. nject
 mport javax. nject.Na d
 mport javax. nject.S ngleton
 mport scala.collect on.JavaConverters._

/**
 * (1) Scr be common features sent to pred ct on serv ce + so  ot r features as PLDR format  nto logs
 * (2) Scr be cand date features sent to pred ct on serv ce + so  ot r features as PLDR format  nto anot r logs
 */
@S ngleton
class Scr beServedCommonFeaturesAndCand dateFeaturesS deEffect @ nject() (
  @Flag(DataRecord tadataStoreConf gsYmlFlag) dataRecord tadataStoreConf gsYml: Str ng,
  @Flag(Scr beServedCommonFeaturesAndCand dateFeaturesFlag) enableScr beServedCommonFeaturesAndCand dateFeatures: Boolean,
  @Na d(CommonFeaturesScr beEventPubl s r) commonFeaturesScr beEventPubl s r: EventPubl s r[
    pldr.PolyDataRecord
  ],
  @Na d(Cand dateFeaturesScr beEventPubl s r) cand dateFeaturesScr beEventPubl s r: EventPubl s r[
    pldr.PolyDataRecord
  ],
  @Na d(M n mumFeaturesScr beEventPubl s r) m n mumFeaturesScr beEventPubl s r: EventPubl s r[
    pldr.PolyDataRecord
  ],
  statsRece ver: StatsRece ver,
) extends P pel neResultS deEffect[ScoredT etsQuery, ScoredT etsResponse]
    w h P pel neResultS deEffect.Cond  onally[ScoredT etsQuery, ScoredT etsResponse]
    w h Logg ng {

  overr de val  dent f er: S deEffect dent f er =
    S deEffect dent f er("Scr beServedCommonFeaturesAndCand dateFeatures")

  pr vate val dr rger = new DataRecord rger
  pr vate val postScor ngCand dateFeatures = Spec f cFeatures(Pred ctedScoreFeatures)
  pr vate val postScor ngCand dateFeaturesDataRecordAdapter =
    new DataRecordConverter(postScor ngCand dateFeatures)

  pr vate val scopedStatsRece ver = statsRece ver.scope(getClass.getS mpleNa )
  pr vate val  tadataFetchFa ledCounter = scopedStatsRece ver.counter(" tadataFetchFa led")
  pr vate val commonFeaturesScr beCounter = scopedStatsRece ver.counter("commonFeaturesScr be")
  pr vate val commonFeaturesPLDROpt onObserver =
    Opt onObserver(scopedStatsRece ver.scope("commonFeaturesPLDR"))
  pr vate val cand dateFeaturesScr beCounter =
    scopedStatsRece ver.counter("cand dateFeaturesScr be")
  pr vate val cand dateFeaturesPLDROpt onObserver =
    Opt onObserver(scopedStatsRece ver.scope("cand dateFeaturesPLDR"))
  pr vate val m n mumFeaturesPLDROpt onObserver =
    Opt onObserver(scopedStatsRece ver.scope("m n mumFeaturesPLDR"))
  pr vate val m n mumFeaturesScr beCounter =
    scopedStatsRece ver.counter("m n mumFeaturesScr be")

  lazy pr vate val dataRecord tadataStoreCl ent: Opt on[Cl ent w h Transact ons] =
    Try {
       sqlCl entUt ls. sqlCl entProv der(
         sqlCl entUt ls.parseConf gFromYaml(dataRecord tadataStoreConf gsYml))
    }.onFa lure { e =>  nfo(s"Error bu ld ng  SQL cl ent: $e") }.toOpt on

  lazy pr vate val vers oned tadataCac Cl entOpt: Opt on[
    Vers oned tadataCac Cl ent[Map[drmd.FeaturesCategory, Opt on[Vers on dAndFeatures]]]
  ] =
    dataRecord tadataStoreCl ent.map {  sqlCl ent =>
      new Vers oned tadataCac Cl ent[Map[drmd.FeaturesCategory, Opt on[Vers on dAndFeatures]]](
        max mumS ze = 1,
        exp reDurat onOpt = None,
         sqlCl ent =  sqlCl ent,
        transform = Cand dateAndCommonFeaturesStream ngUt ls. tadataTransfor r,
        statsRece ver = statsRece ver
      )
    }

  vers oned tadataCac Cl entOpt.foreach { vers oned tadataCac Cl ent =>
    vers oned tadataCac Cl ent
      . tadataFetchT  rTask(
        Cand dateAndCommonFeaturesStream ngUt ls. tadataFetchKey,
         tadataFetchT  r = DefaultT  r,
         tadataFetch nterval = 90.seconds,
         tadataFetchFa ledCounter =  tadataFetchFa ledCounter
      )
  }

  overr de def only f(
    query: ScoredT etsQuery,
    selectedCand dates: Seq[Cand dateW hDeta ls],
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    droppedCand dates: Seq[Cand dateW hDeta ls],
    response: ScoredT etsResponse
  ): Boolean = enableScr beServedCommonFeaturesAndCand dateFeatures

  overr de def apply(
     nputs: P pel neResultS deEffect. nputs[ScoredT etsQuery, ScoredT etsResponse]
  ): St ch[Un ] = {
    St ch.value {
      val servedT  stamp: Long = T  .now. nM ll seconds
      val nonMLCommonFeatures = NonMLCommonFeatures(
        user d =  nputs.query.getRequ redUser d,
        pred ct onRequest d =
           nputs.query.features.flatMap(_.getOrElse(ServedRequest dFeature, None)),
        servedT  stamp = servedT  stamp
      )
      val nonMLCommonFeaturesDataRecord =
        NonMLCommonFeaturesAdapter.adaptToDataRecords(nonMLCommonFeatures).asScala. ad

      /**
       * Steps of scr b ng common features
       * (1) fetch common features as data record
       * (2) extract add  onal feature as data record, e.g. pred ct onRequest d wh ch  s used as jo n key  n downstream jobs
       * (3)  rge two data records above and convert t   rged data record to pldr
       * (4) publ sh pldr
       */
      val commonFeaturesDataRecordOpt =
         nputs.selectedCand dates. adOpt on.map(_.features.get(CommonFeaturesDataRecordFeature))
      val commonFeaturesPLDROpt = commonFeaturesDataRecordOpt.flatMap { commonFeaturesDataRecord =>
        dr rger. rge(commonFeaturesDataRecord, nonMLCommonFeaturesDataRecord)

        Cand dateAndCommonFeaturesStream ngUt ls.commonFeaturesToPolyDataRecord(
          vers oned tadataCac Cl entOpt = vers oned tadataCac Cl entOpt,
          commonFeatures = commonFeaturesDataRecord,
          valueFormat = pldr.PolyDataRecord._F elds.L TE_COMPACT_DATA_RECORD
        )
      }

      commonFeaturesPLDROpt onObserver(commonFeaturesPLDROpt).foreach { pldr =>
        commonFeaturesScr beEventPubl s r.publ sh(pldr)
        commonFeaturesScr beCounter. ncr()
      }

      /**
       * steps of scr b ng cand date features
       * (1) fetch cand date features as data record
       * (2) extract add  onal features (mostly non ML features  nclud ng pred cted scores, pred ct onRequest d, user d, t et d)
       * (3)  rge data records and convert t   rged data record  nto pldr
       * (4) publ sh pldr
       */
       nputs.selectedCand dates.foreach { cand date =>
        val cand dateFeaturesDataRecord = cand date.features.get(Cand dateFeaturesDataRecordFeature)

        /**
         * extract pred cted scores as data record and  rge    nto or g nal data record
         */
        val postScor ngCand dateFeaturesDataRecord =
          postScor ngCand dateFeaturesDataRecordAdapter.toDataRecord(cand date.features)
        dr rger. rge(cand dateFeaturesDataRecord, postScor ngCand dateFeaturesDataRecord)

        /**
         * extract non ML common features as data record and  rge    nto or g nal data record
         */
        dr rger. rge(cand dateFeaturesDataRecord, nonMLCommonFeaturesDataRecord)

        /**
         * extract non ML cand date features as data record and  rge    nto or g nal data record
         */
        val nonMLCand dateFeatures = NonMLCand dateFeatures(
          t et d = cand date.cand date dLong,
          s ceT et d = cand date.features.getOrElse(S ceT et dFeature, None),
          or g nalAuthor d = getOr g nalAuthor d(cand date.features)
        )
        val nonMLCand dateFeaturesDataRecord =
          NonMLCand dateFeaturesAdapter.adaptToDataRecords(nonMLCand dateFeatures).asScala. ad
        dr rger. rge(cand dateFeaturesDataRecord, nonMLCand dateFeaturesDataRecord)

        val cand dateFeaturesPLDROpt =
          Cand dateAndCommonFeaturesStream ngUt ls.cand dateFeaturesToPolyDataRecord(
            vers oned tadataCac Cl entOpt = vers oned tadataCac Cl entOpt,
            cand dateFeatures = cand dateFeaturesDataRecord,
            valueFormat = pldr.PolyDataRecord._F elds.L TE_COMPACT_DATA_RECORD
          )

        cand dateFeaturesPLDROpt onObserver(cand dateFeaturesPLDROpt).foreach { pldr =>
          cand dateFeaturesScr beEventPubl s r.publ sh(pldr)
          cand dateFeaturesScr beCounter. ncr()
        }

        // scr be m n mum features wh ch are used to jo n labels from cl ent events.
        val m n mumFeaturesPLDROpt = cand dateFeaturesPLDROpt
          .map(Cand dateAndCommonFeaturesStream ngUt ls.extractM n mumFeaturesFromPldr)
          .map(pldr.PolyDataRecord.dataRecord)
        m n mumFeaturesPLDROpt onObserver(m n mumFeaturesPLDROpt).foreach { pldr =>
          m n mumFeaturesScr beEventPubl s r.publ sh(pldr)
          m n mumFeaturesScr beCounter. ncr()
        }
      }
    }
  }
}
