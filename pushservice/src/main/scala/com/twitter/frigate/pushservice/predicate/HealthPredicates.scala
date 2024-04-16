package com.tw ter.fr gate.pushserv ce.pred cate

 mport com.tw ter.abuse.detect on.scor ng.thr ftscala.T etScor ngRequest
 mport com.tw ter.abuse.detect on.scor ng.thr ftscala.T etScor ngResponse
 mport com.tw ter.abuse.detect on.scor ng.thr ftscala.{Model => T et althModel}
 mport com.tw ter.f nagle.stats.Counter
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base._
 mport com.tw ter.fr gate.common.rec_types.RecTypes
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.params.NsfwTextDetect onModel
 mport com.tw ter.fr gate.pushserv ce.params.PushConstants
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.ut l.Cand dateHydrat onUt l
 mport com.tw ter.fr gate.pushserv ce.ut l.Cand dateUt l
 mport com.tw ter.fr gate.pushserv ce.ut l. d aAnnotat onsUt l
 mport com.tw ter.fr gate.thr ftscala.User d aRepresentat on
 mport com.tw ter. rm .pred cate.Na dPred cate
 mport com.tw ter. rm .pred cate.Pred cate
 mport com.tw ter.hss.ap .thr ftscala.User althS gnal._
 mport com.tw ter.hss.ap .thr ftscala.S gnalValue
 mport com.tw ter.hss.ap .thr ftscala.User althS gnalResponse
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  

object  althPred cates {

  pr vate val NsfwTextDetect onModelMap: Map[NsfwTextDetect onModel.Value, T et althModel] =
    Map(
      NsfwTextDetect onModel.ProdModel -> T et althModel.PnsfwT etText,
      NsfwTextDetect onModel.Retra nedModel -> T et althModel.Exper  ntal althModelScore1,
    )

  pr vate def t et sSupportedLanguage(
    cand date: PushCand date,
    supportedLanguages: Set[Str ng]
  ): Boolean = {
    val t etLanguage =
      cand date.categor calFeatures.getOrElse("RecT et.T etyP eResult.Language", "")
    supportedLanguages.conta ns(t etLanguage)
  }

  def t et althS gnalScorePred cate(
    t et althScoreStore: ReadableStore[T etScor ngRequest, T etScor ngResponse],
    applyToQuoteT et: Boolean = false
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[PushCand date w h T etCand date w h T etDeta ls] = {
    val na  = "t et_ alth_s gnal_store_applyToQuoteT et_" + applyToQuoteT et.toStr ng
    val scopedStatsRece ver = stats.scope(na )
    val numCand datesStats = scopedStatsRece ver.scope("num_cand dates")
    val numCand dates d aNsfwScoreStats = numCand datesStats.scope(" d a_nsfw_score")

    Pred cate
      .fromAsync { cand date: PushCand date w h T etCand date w h T etDeta ls =>
        numCand datesStats.counter("all"). ncr()
        val target = cand date.target
        val t et dOpt =  f (!applyToQuoteT et) {
          So (cand date.t et d)
        } else cand date.t etyP eResult.flatMap(_.quotedT et.map(_. d))

        t et dOpt match {
          case So (t et d) =>
            val p d aNsfwRequest =
              T etScor ngRequest(t et d, T et althModel.Exper  ntal althModelScore4)
            t et althScoreStore.get(p d aNsfwRequest).map {
              case So (t etScor ngResponse) =>
                numCand dates d aNsfwScoreStats.counter("non_empty"). ncr()
                val p d aNsfwScore = t etScor ngResponse.score

                 f (!applyToQuoteT et) {
                  cand date
                    .cac ExternalScore("Nsfw d aProbab l y", Future.value(So (p d aNsfwScore)))
                }

                val p d aNsfwShouldBucket =
                  p d aNsfwScore > target.params(
                    PushFeatureSw chParams.PnsfwT et d aBucket ngThreshold)
                 f (Cand dateUt l.shouldApply althQual yF lters(
                    cand date) && p d aNsfwShouldBucket) {
                  numCand dates d aNsfwScoreStats.counter("bucketed"). ncr()
                   f (target.params(PushFeatureSw chParams.PnsfwT et d aF lterOonOnly)
                    && !RecTypes. sOutOfNetworkT etRecType(cand date.commonRecType)) {
                    true
                  } else {
                    val p d aNsfwScoreThreshold =
                       f (applyToQuoteT et)
                        target.params(PushFeatureSw chParams.PnsfwQuoteT etThreshold)
                      else  f (cand date.hasPhoto)
                        target.params(PushFeatureSw chParams.PnsfwT et mageThreshold)
                      else target.params(PushFeatureSw chParams.PnsfwT et d aThreshold)
                    cand date.cac Pred cate nfo(
                      na  + "_nsfw d a",
                      p d aNsfwScore,
                      p d aNsfwScoreThreshold,
                      p d aNsfwScore > p d aNsfwScoreThreshold)
                     f (p d aNsfwScore > p d aNsfwScoreThreshold) {
                      numCand dates d aNsfwScoreStats.counter("f ltered"). ncr()
                      false
                    } else true
                  }
                } else true
              case _ =>
                numCand dates d aNsfwScoreStats.counter("empty"). ncr()
                 f (cand date.hasPhoto || cand date.hasV deo) {
                  numCand dates d aNsfwScoreStats.counter(" d a_t et_w h_empty_score"). ncr()
                }
                true
            }
          case _ => Future.True
        }
      }
      .w hStats(stats.scope(s"pred cate_$na "))
      .w hNa (na )
  }

  def  althS gnalScoreSpam T etPred cate(
    t et althScoreStore: ReadableStore[T etScor ngRequest, T etScor ngResponse]
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[PushCand date w h T etCand date w h T etDeta ls] = {
    val na  = " alth_s gnal_store_spam _t et"
    val statsScope = stats.scope(na )
    val allCand datesCounter = statsScope.counter("all_cand dates")
    val el g bleCand datesCounter = statsScope.counter("el g ble_cand dates")
    val oonCand datesCounter = statsScope.counter("oon_cand dates")
    val  nCand datesCounter = statsScope.counter(" n_cand dates")
    val bucketedCand datesCounter = statsScope.counter("num_bucketed")
    val nonEmptySpamScoreCounter = statsScope.counter("non_empty_spam_score")
    val f lteredOonCand datesCounter = statsScope.counter("num_f ltered_oon")
    val f ltered nCand datesCounter = statsScope.counter("num_f ltered_ n")

    Pred cate
      .fromAsync { cand date: PushCand date w h T etCand date w h T etDeta ls =>
        allCand datesCounter. ncr()
        val crt = cand date.commonRecType
        val  sOonCand date = RecTypes. sOutOfNetworkT etRecType(crt) ||
          RecTypes.outOfNetworkTop cT etTypes.conta ns(crt)
         f ( sOonCand date) {
          oonCand datesCounter. ncr()
        }
        val target = cand date.target
         f (target.params(PushFeatureSw chParams.EnableSpam T etF lter)) {
          el g bleCand datesCounter. ncr()
          val t etSpamScore =
            T etScor ngRequest(cand date.t et d, T et althModel.Spam T etContent)
          t et althScoreStore.get(t etSpamScore).map {
            case (So (t etScor ngResponse)) =>
              nonEmptySpamScoreCounter. ncr()
              val cand dateSpamScore = t etScor ngResponse.score

              cand date
                .cac ExternalScore("Spam T etScore", Future.value(So (cand dateSpamScore)))

              val t etSpamShouldBucket =
                cand dateSpamScore > target.params(
                  PushFeatureSw chParams.Spam T etBucket ngThreshold)
               f (Cand dateUt l.shouldApply althQual yF lters(
                  cand date) && t etSpamShouldBucket) {
                bucketedCand datesCounter. ncr()
                 f ( sOonCand date) {
                  val spamScoreThreshold =
                    target.params(PushFeatureSw chParams.Spam T etOonThreshold)
                   f (cand dateSpamScore > spamScoreThreshold) {
                    f lteredOonCand datesCounter. ncr()
                    false
                  } else true
                } else {
                   nCand datesCounter. ncr()
                  val spamScoreThreshold =
                    target.params(PushFeatureSw chParams.Spam T et nThreshold)
                   f (cand dateSpamScore > spamScoreThreshold) {
                    f ltered nCand datesCounter. ncr()
                    false
                  } else true
                }
              } else true
            case _ => true
          }
        } else Future.True
      }
      .w hStats(stats.scope(s"pred cate_$na "))
      .w hNa (na )
  }

  def  althS gnalScorePnsfwT etTextPred cate(
    t et althScoreStore: ReadableStore[T etScor ngRequest, T etScor ngResponse]
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[PushCand date w h T etCand date] = {
    val na  = " alth_s gnal_store_pnsfw_t et_text"
    val statsScope = stats.scope(na )
    val allCand datesCounter = statsScope.counter("all_cand dates")
    val nonEmptyNsfwTextScoreNum = statsScope.counter("non_empty_nsfw_text_score")
    val f lteredCounter = statsScope.counter("num_f ltered")
    val lowScoreCounter = statsScope.counter("low_score_count")

    Pred cate
      .fromAsync { cand date: PushCand date w h T etCand date =>
        val target = cand date.target
        val predEnabled =
          target.params(PushFeatureSw chParams.Enable althS gnalStorePnsfwT etTextPred cate)
         f (Cand dateUt l.shouldApply althQual yF lters(
            cand date) && predEnabled && t et sSupportedLanguage(cand date, Set(""))) {
          allCand datesCounter. ncr()
          val pnsfwTextRequest =
            T etScor ngRequest(cand date.t et d, T et althModel.PnsfwT etText)
          t et althScoreStore.get(pnsfwTextRequest).flatMap {
            case So (t etScor ngResponse) => {
              nonEmptyNsfwTextScoreNum. ncr()
               f (t etScor ngResponse.score < 1e-8) {
                lowScoreCounter. ncr()
              }

              cand date
                .cac ExternalScore(
                  "NsfwTextProbab l y-en",
                  Future.value(So (t etScor ngResponse.score)))
              val threshold = target.params(PushFeatureSw chParams.PnsfwT etTextThreshold)
              cand date.cac Pred cate nfo(
                na ,
                t etScor ngResponse.score,
                threshold,
                t etScor ngResponse.score > threshold)
               f (t etScor ngResponse.score > threshold) {
                f lteredCounter. ncr()
                Future.False
              } else Future.True
            }
            case _ => Future.True
          }
        } else Future.True
      }
      .w hStats(stats.scope(s"pred cate_$na "))
      .w hNa (na )
  }

  def  althS gnalScoreMult l ngualPnsfwT etTextPred cate(
    t et althScoreStore: ReadableStore[T etScor ngRequest, T etScor ngResponse]
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[PushCand date w h T etCand date] = {
    val na  = " alth_s gnal_store_mult l ngual_pnsfw_t et_text"
    val statsScope = stats.scope(na )

    val allLanguages dent f er = "all"
    val languagesSelectedForStats =
      Set("") + allLanguages dent f er

    val cand datesCounterMap: Map[Str ng, Counter] = languagesSelectedForStats.map { lang =>
      lang -> statsScope.counter(f"cand dates_$lang")
    }.toMap
    val nonEmpty althScoreMap: Map[Str ng, Counter] = languagesSelectedForStats.map { lang =>
      lang -> statsScope.counter(f"non_empty_ alth_score_$lang")
    }.toMap
    val empty althScoreMap: Map[Str ng, Counter] = languagesSelectedForStats.map { lang =>
      lang -> statsScope.counter(f"empty_ alth_score_$lang")
    }.toMap
    val bucketedCounterMap: Map[Str ng, Counter] = languagesSelectedForStats.map { lang =>
      lang -> statsScope.counter(f"num_cand dates_bucketed_$lang")
    }.toMap
    val f lteredCounterMap: Map[Str ng, Counter] = languagesSelectedForStats.map { lang =>
      lang -> statsScope.counter(f"num_f ltered_$lang")
    }.toMap
    val lowScoreCounterMap: Map[Str ng, Counter] = languagesSelectedForStats.map { lang =>
      lang -> statsScope.counter(f"low_score_count_$lang")
    }.toMap

    val wrongBucket ngModelCounter = statsScope.counter("wrong_bucket ng_model_count")
    val wrongDetect onModelCounter = statsScope.counter("wrong_detect on_model_count")

    def  ncreaseCounterForLanguage(counterMap: Map[Str ng, Counter], language: Str ng): Un  = {
      counterMap.get(allLanguages dent f er) match {
        case So (counter) => counter. ncr()
        case _ =>
      }
      counterMap.get(language) match {
        case So (counter) => counter. ncr()
        case _ =>
      }
    }

    Pred cate
      .fromAsync { cand date: PushCand date w h T etCand date =>
        val target = cand date.target

        val languageFeatureNa  = "RecT et.T etyP eResult.Language"

        lazy val  sPred cateEnabledForTarget = target.params(
          PushFeatureSw chParams.Enable althS gnalStoreMult l ngualPnsfwT etTextPred cate)

        lazy val targetNsfwTextDetect onModel: NsfwTextDetect onModel.Value =
          target.params(PushFeatureSw chParams.Mult l ngualPnsfwT etTextModel)

        lazy val targetPred cateSupportedLanguageSeq: Seq[Str ng] =
          target.params(PushFeatureSw chParams.Mult l ngualPnsfwT etTextSupportedLanguages)

        lazy val bucket ngModelSeq: Seq[NsfwTextDetect onModel.Value] =
          target.params(PushFeatureSw chParams.Mult l ngualPnsfwT etTextBucket ngModelL st)

        lazy val bucket ngThresholdPerLanguageSeq: Seq[Double] =
          target.params(PushFeatureSw chParams.Mult l ngualPnsfwT etTextBucket ngThreshold)

        lazy val f lter ngThresholdPerLanguageSeq: Seq[Double] =
          target.params(PushFeatureSw chParams.Mult l ngualPnsfwT etTextF lter ngThreshold)

         f (Cand dateUt l.shouldApply althQual yF lters(
            cand date) &&  sPred cateEnabledForTarget) {
          val cand dateLanguage =
            cand date.categor calFeatures.getOrElse(languageFeatureNa , "")

          val  ndexOfCand dateLanguage =
            targetPred cateSupportedLanguageSeq. ndexOf(cand dateLanguage)

          val  sCand dateLanguageSupported =  ndexOfCand dateLanguage >= 0

           f ( sCand dateLanguageSupported) {
             ncreaseCounterForLanguage(cand datesCounterMap, cand dateLanguage)

            val bucket ngModelScoreMap: Map[NsfwTextDetect onModel.Value, Future[Opt on[Double]]] =
              bucket ngModelSeq.map { modelNa  =>
                NsfwTextDetect onModelMap.get(modelNa ) match {
                  case So (targetNsfwTextDetect onModel) =>
                    val pnsfwT etTextRequest: T etScor ngRequest =
                      T etScor ngRequest(cand date.t et d, targetNsfwTextDetect onModel)

                    val scoreOptFut: Future[Opt on[Double]] =
                      t et althScoreStore.get(pnsfwT etTextRequest).map(_.map(_.score))

                    cand date
                      .cac ExternalScore("NsfwTextProbab l y", scoreOptFut)

                    modelNa  -> scoreOptFut
                  case _ =>
                    wrongBucket ngModelCounter. ncr()
                    modelNa  -> Future.None
                }
              }.toMap

            val cand dateLanguageBucket ngThreshold =
              bucket ngThresholdPerLanguageSeq( ndexOfCand dateLanguage)

            val userShouldBeBucketedFut: Future[Boolean] =
              Future
                .collect(bucket ngModelScoreMap.map {
                  case (_, modelScoreOptFut) =>
                    modelScoreOptFut.map {
                      case So (score) =>
                         ncreaseCounterForLanguage(nonEmpty althScoreMap, cand dateLanguage)
                        score > cand dateLanguageBucket ngThreshold
                      case _ =>
                         ncreaseCounterForLanguage(empty althScoreMap, cand dateLanguage)
                        false
                    }
                }.toSeq).map(_.conta ns(true))

            val cand dateShouldBeF lteredFut: Future[Boolean] = userShouldBeBucketedFut.flatMap {
              userShouldBeBucketed =>
                 f (userShouldBeBucketed) {
                   ncreaseCounterForLanguage(bucketedCounterMap, cand dateLanguage)

                  val cand dateLanguageF lter ngThreshold =
                    f lter ngThresholdPerLanguageSeq( ndexOfCand dateLanguage)

                  bucket ngModelScoreMap.get(targetNsfwTextDetect onModel) match {
                    case So (scoreOptFut) =>
                      scoreOptFut.map {
                        case So (score) =>
                          val cand dateShouldBeF ltered =
                            score > cand dateLanguageF lter ngThreshold
                           f (cand dateShouldBeF ltered) {
                             ncreaseCounterForLanguage(f lteredCounterMap, cand dateLanguage)
                          }
                          cand dateShouldBeF ltered
                        case _ => false
                      }
                    case _ =>
                      wrongDetect onModelCounter. ncr()
                      Future.False
                  }
                } else {
                   ncreaseCounterForLanguage(lowScoreCounterMap, cand dateLanguage)
                  Future.False
                }
            }
            cand dateShouldBeF lteredFut.map(result => !result)
          } else Future.True
        } else Future.True
      }
      .w hStats(stats.scope(s"pred cate_$na "))
      .w hNa (na )
  }

  def authorProf leBasedPred cate(
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[PushCand date w h T etCand date] = {
    val na  = "author_prof le"
    val statsScope = stats.scope(na )
    val f lterByNsfwToken = statsScope.counter("f lter_by_nsfw_token")
    val f lterByAccountAge = statsScope.counter("f lter_by_account_age")

    Pred cate
      .fromAsync { cand date: PushCand date w h T etCand date =>
        val target = cand date.target
        cand date match {
          case cand: PushCand date w h T etAuthorDeta ls =>
            cand.t etAuthor.map {
              case So (author) =>
                val nsfwTokens = target.params(PushFeatureSw chParams.NsfwTokensParam)
                val accountAge nH s =
                  (T  .now - T  .fromM ll seconds(author.createdAtMsec)). nH s
                val  sNsfwAccount = Cand dateHydrat onUt l. sNsfwAccount(author, nsfwTokens)
                val  sVer f ed = author.safety.map(_.ver f ed).getOrElse(false)

                 f (Cand dateUt l.shouldApply althQual yF lters(cand date) && ! sVer f ed) {
                  val enableNsfwTokenC ck =
                    target.params(PushFeatureSw chParams.EnableNsfwTokenBasedF lter ng)
                  val m n mumAllo dAge =
                    target.params(PushFeatureSw chParams.M n mumAllo dAuthorAccountAge nH s)
                  cand.cac Pred cate nfo(
                    na  + "_nsfwToken",
                     f ( sNsfwAccount) 1.0 else 0.0,
                    0.0,
                    enableNsfwTokenC ck &&  sNsfwAccount)
                  cand.cac Pred cate nfo(
                    na  + "_authorAge",
                    accountAge nH s,
                    m n mumAllo dAge,
                    accountAge nH s < m n mumAllo dAge)

                   f (enableNsfwTokenC ck &&  sNsfwAccount) {
                    f lterByNsfwToken. ncr()
                    false
                  } else  f (accountAge nH s < m n mumAllo dAge) {
                    f lterByAccountAge. ncr()
                    false
                  } else true
                } else true
              case _ => true
            }
          case _ => Future.value(true)
        }
      }
      .w hStats(stats.scope(s"pred cate_$na "))
      .w hNa (na )
  }

  def authorSens  ve d aPred cate(
    producer d aRepresentat onStore: ReadableStore[Long, User d aRepresentat on]
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[PushCand date w h T etAuthor] = {
    val na  = "author_sens  ve_ d a_mrtw stly"
    val statsScope = stats.scope(na )
    val enableQueryNum = statsScope.counter("enable_query")
    val nonEmpty d aRepresentat onNum = statsScope.counter("non_empty_ d a_representat on")
    val f lteredOON = statsScope.counter("f ltered_oon")

    Pred cate
      .fromAsync { cand date: PushCand date w h T etAuthor =>
        val target = cand date.target
        val useAggress veThresholds = Cand dateUt l.useAggress ve althThresholds(cand date)

         f (Cand dateUt l.shouldApply althQual yF lters(cand date) &&
          RecTypes. sOutOfNetworkT etRecType(cand date.commonRecType) &&
          target.params(PushFeatureSw chParams.EnableQueryAuthor d aRepresentat onStore)) {
          enableQueryNum. ncr()

          cand date.author d match {
            case So (author d) =>
              producer d aRepresentat onStore.get(author d).map {
                case So ( d aRepresentat on) =>
                  nonEmpty d aRepresentat onNum. ncr()
                  val sumScore: Double =  d aRepresentat on. d aRepresentat on.values.sum
                  val nud yScore: Double =  d aRepresentat on. d aRepresentat on
                    .getOrElse( d aAnnotat onsUt l.nud yCategory d, 0.0)
                  val nud yRate =  f (sumScore > 0) nud yScore / sumScore else 0.0

                  cand date
                    .cac ExternalScore("AuthorNud yScore", Future.value(So (nud yScore)))
                  cand date.cac ExternalScore("AuthorNud yRate", Future.value(So (nud yRate)))

                  val threshold =  f (useAggress veThresholds) {
                    target.params(
                      PushFeatureSw chParams.AuthorSens  ve d aF lter ngThresholdForMrTw stly)
                  } else {
                    target.params(PushFeatureSw chParams.AuthorSens  ve d aF lter ngThreshold)
                  }
                  cand date.cac Pred cate nfo(
                    na ,
                    nud yRate,
                    threshold,
                    nud yRate > threshold,
                    So (Map[Str ng, Double]("sumScore" -> sumScore, "nud yScore" -> nud yScore)))

                   f (nud yRate > threshold) {
                    f lteredOON. ncr()
                    false
                  } else true
                case _ => true
              }
            case _ => Future.True
          }
        } else {
          Future.True
        }
      }
      .w hStats(stats.scope(s"pred cate_$na "))
      .w hNa (na )
  }

  def sens  ve d aCategoryPred cate(
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[PushCand date w h T etCand date] = {
    val na  = "sens  ve_ d a_category"
    val t et d aAnnotat onFeature =
      "t et. d aunderstand ng.t et_annotat ons.sens  ve_category_probab l  es"
    val scopedStatsRece ver = stats.scope(na )
    val allCand datesCounter = scopedStatsRece ver.counter("all_cand dates")
    val nonZeroNud yCand datesCounter = scopedStatsRece ver.counter("non_zero_nud y_cand dates")
    val nud yScoreStats = scopedStatsRece ver.stat("nud y_scores")

    Pred cate
      .fromAsync { cand date: PushCand date =>
        allCand datesCounter. ncr()
        val target = cand date.target
        val nud yScore = cand date.sparseCont nuousFeatures
          .getOrElse(t et d aAnnotat onFeature, Map.empty[Str ng, Double]).getOrElse(
             d aAnnotat onsUt l.nud yCategory d,
            0.0)
         f (nud yScore > 0) nonZeroNud yCand datesCounter. ncr()
        nud yScoreStats.add(nud yScore.toFloat)
        val threshold =
          target.params(PushFeatureSw chParams.T et d aSens  veCategoryThresholdParam)
        cand date.cac Pred cate nfo(na , nud yScore, threshold, nud yScore > threshold)
         f (Cand dateUt l.shouldApply althQual yF lters(cand date) && nud yScore > threshold) {
          Future.False
        } else {
          Future.True
        }
      }
      .w hStats(stats.scope(s"pred cate_$na "))
      .w hNa (na )
  }

  def profan yPred cate(
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[PushCand date w h T etCand date] = {
    val na  = "profan y_f lter"
    val scopedStatsRece ver = stats.scope(na )
    val allCand datesCounter = scopedStatsRece ver.counter("all_cand dates")

    Pred cate
      .fromAsync { cand date: PushCand date =>
        allCand datesCounter. ncr()
        val target = cand date.target

        lazy val enableF lter =
          target.params(PushFeatureSw chParams.EnableProfan yF lterParam)
        val t etSemant cCore ds = cand date.sparseB naryFeatures
          .getOrElse(PushConstants.T etSemant cCore dFeature, Set.empty[Str ng])

         f (Cand dateUt l.shouldApply althQual yF lters(cand date) &&
          t etSemant cCore ds.conta ns(PushConstants.Profan yF lter_ d) && enableF lter) {
          Future.False
        } else {
          Future.True
        }
      }
      .w hStats(stats.scope(s"pred cate_$na "))
      .w hNa (na )
  }

  def agathaAbus veT etAuthorPred cateMrTw stly(
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[PushCand date w h OutOfNetworkT etCand date] = {
    val na  = "agatha_abus ve_t et_author_mr_tw stly"
    val scopedStatsRece ver = stats.scope(na )
    val allCand datesCounter = scopedStatsRece ver.counter("all_cand dates")
    val  sMrBackf llCRCand dateCounter = scopedStatsRece ver.counter(" sMrBackf llCR_cand dates")
    Pred cate
      .fromAsync { cand: PushCand date w h OutOfNetworkT etCand date =>
        allCand datesCounter. ncr()
        val target = cand.target
        val t etSemant cCore ds = cand.sparseB naryFeatures
          .getOrElse(PushConstants.T etSemant cCore dFeature, Set.empty[Str ng])

        val hasAbuseStr keTop2Percent =
          t etSemant cCore ds.conta ns(PushConstants.AbuseStr ke_Top2Percent_ d)
        val hasAbuseStr keTop1Percent =
          t etSemant cCore ds.conta ns(PushConstants.AbuseStr ke_Top1Percent_ d)
        val hasAbuseStr keTop05Percent =
          t etSemant cCore ds.conta ns(PushConstants.AbuseStr ke_Top05Percent_ d)

         f (hasAbuseStr keTop2Percent) {
          scopedStatsRece ver.counter("abuse_str ke_top_2_percent_cand dates"). ncr()
        }
         f (hasAbuseStr keTop1Percent) {
          scopedStatsRece ver.counter("abuse_str ke_top_1_percent_cand dates"). ncr()
        }
         f (hasAbuseStr keTop05Percent) {
          scopedStatsRece ver.counter("abuse_str ke_top_05_percent_cand dates"). ncr()
        }

         f (Cand dateUt l.shouldApply althQual yF lters(cand) && cand. sMrBackf llCR.getOrElse(
            false)) {
           sMrBackf llCRCand dateCounter. ncr()
           f (hasAbuseStr keTop2Percent) {
             f (target.params(
                PushFeatureSw chParams.EnableAbuseStr keTop2PercentF lterS mCluster) && hasAbuseStr keTop2Percent ||
              target.params(
                PushFeatureSw chParams.EnableAbuseStr keTop1PercentF lterS mCluster) && hasAbuseStr keTop1Percent ||
              target.params(
                PushFeatureSw chParams.EnableAbuseStr keTop05PercentF lterS mCluster) && hasAbuseStr keTop05Percent) {
              Future.False
            } else {
              Future.True
            }
          } else {
            Future.True
          }
        } else Future.True
      }
      .w hStats(stats.scope(s"pred cate_$na "))
      .w hNa (na )
  }

  def user althS gnalsPred cate(
    user althS gnalStore: ReadableStore[Long, User althS gnalResponse]
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[PushCand date w h T etDeta ls] = {
    val na  = "agatha_user_ alth_model_score"
    val scopedStatsRece ver = stats.scope(na )
    val allCand datesCounter = scopedStatsRece ver.counter("all_cand dates")
    val bucketedUserCand datesCounter =
      scopedStatsRece ver.counter("bucketed_user_cand dates")
    val f lteredOON = scopedStatsRece ver.counter("f ltered_oon")

    Pred cate
      .fromAsync { cand date: PushCand date w h T etDeta ls =>
        allCand datesCounter. ncr()
        val target = cand date.target
        val useAggress veThresholds = Cand dateUt l.useAggress ve althThresholds(cand date)

         f (Cand dateUt l.shouldApply althQual yF lters(cand date) && target.params(
            PushFeatureSw chParams.EnableAgathaUser althModelPred cate)) {
          val  althS gnalsResponseFutOpt: Future[Opt on[User althS gnalResponse]] =
            cand date.author d match {
              case So (author d) => user althS gnalStore.get(author d)
              case _ => Future.None
            }
           althS gnalsResponseFutOpt.map {
            case So (response) =>
              val agathaRecentAbuseStr keScore: Double = user althS gnalValueToDouble(
                response.s gnalValues
                  .getOrElse(AgathaRecentAbuseStr keDouble, S gnalValue.DoubleValue(0.0)))
              val agathaCal bratedNSFWScore: Double = user althS gnalValueToDouble(
                response.s gnalValues
                  .getOrElse(AgathaCal bratedNsfwDouble, S gnalValue.DoubleValue(0.0)))
              val agathaTextNSFWScore: Double = user althS gnalValueToDouble(response.s gnalValues
                .getOrElse(NsfwTextUserScoreDouble, S gnalValue.DoubleValue(0.0)))

              cand date
                .cac ExternalScore(
                  "agathaRecentAbuseStr keScore",
                  Future.value(So (agathaRecentAbuseStr keScore)))
              cand date
                .cac ExternalScore(
                  "agathaCal bratedNSFWScore",
                  Future.value(So (agathaCal bratedNSFWScore)))
              cand date
                .cac ExternalScore("agathaTextNSFWScore", Future.value(So (agathaTextNSFWScore)))

              val NSFWShouldBucket = agathaCal bratedNSFWScore > target.params(
                PushFeatureSw chParams.AgathaCal bratedNSFWBucketThreshold)
              val textNSFWShouldBucket = agathaTextNSFWScore > target.params(
                PushFeatureSw chParams.AgathaTextNSFWBucketThreshold)

               f (NSFWShouldBucket || textNSFWShouldBucket) {
                bucketedUserCand datesCounter. ncr()
                 f (NSFWShouldBucket) {
                  scopedStatsRece ver.counter("cal brated_nsfw_bucketed_user_cand dates"). ncr()
                }
                 f (textNSFWShouldBucket) {
                  scopedStatsRece ver.counter("text_nsfw_bucketed_user_cand dates"). ncr()
                }

                val (thresholdAgathaNsfw, thresholdTextNsfw) =  f (useAggress veThresholds) {
                  (
                    target.params(
                      PushFeatureSw chParams.AgathaCal bratedNSFWThresholdForMrTw stly),
                    target
                      .params(PushFeatureSw chParams.AgathaTextNSFWThresholdForMrTw stly))
                } else {
                  (
                    target.params(PushFeatureSw chParams.AgathaCal bratedNSFWThreshold),
                    target.params(PushFeatureSw chParams.AgathaTextNSFWThreshold))
                }
                cand date.cac Pred cate nfo(
                  na  + "_agathaNsfw",
                  agathaCal bratedNSFWScore,
                  thresholdAgathaNsfw,
                  agathaCal bratedNSFWScore > thresholdAgathaNsfw)
                cand date.cac Pred cate nfo(
                  na  + "_authorTextNsfw",
                  agathaTextNSFWScore,
                  thresholdTextNsfw,
                  agathaTextNSFWScore > thresholdTextNsfw)

                 f ((agathaCal bratedNSFWScore > thresholdAgathaNsfw) ||
                  (agathaTextNSFWScore > thresholdTextNsfw)) {
                  f lteredOON. ncr()
                  false
                } else true
              } else {
                true
              }
            case _ => true
          }
        } else {
          Future.True
        }
      }
      .w hStats(stats.scope(s"pred cate_$na "))
      .w hNa (na )
  }

  def user althS gnalValueToDouble(s gnalValue: S gnalValue): Double = {
    s gnalValue match {
      case S gnalValue.DoubleValue(value) => value
      case _ => throw new Except on(f"Could not convert s gnal value to double")
    }
  }
}
