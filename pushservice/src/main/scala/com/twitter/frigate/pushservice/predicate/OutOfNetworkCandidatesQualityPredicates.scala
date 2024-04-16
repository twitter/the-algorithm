package com.tw ter.fr gate.pushserv ce.pred cate

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base._
 mport com.tw ter.fr gate.common.cand date.TargetABDec der
 mport com.tw ter.fr gate.common.rec_types.RecTypes
 mport com.tw ter.fr gate.data_p pel ne.features_common.MrRequestContextForFeatureStore
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter. rm .pred cate.Na dPred cate
 mport com.tw ter. rm .pred cate.Pred cate
 mport com.tw ter.ml.featurestore.l b.dynam c.Dynam cFeatureStoreCl ent
 mport com.tw ter.ut l.Future
 mport com.tw ter.fr gate.pushserv ce.pred cate.PostRank ngPred cate lper._
 mport com.tw ter.fr gate.pushserv ce.ut l.Cand dateUt l

object OutOfNetworkCand datesQual yPred cates {

  def getT etCharLengthThreshold(
    target: TargetUser w h TargetABDec der,
    language: Str ng,
    use d aThresholds: Boolean
  ): Double = {
    lazy val sautOonW h d aT etLengthThreshold =
      target.params(PushFeatureSw chParams.SautOonW h d aT etLengthThresholdParam)
    lazy val nonSautOonW h d aT etLengthThreshold =
      target.params(PushFeatureSw chParams.NonSautOonW h d aT etLengthThresholdParam)
    lazy val sautOonW hout d aT etLengthThreshold =
      target.params(PushFeatureSw chParams.SautOonW hout d aT etLengthThresholdParam)
    lazy val nonSautOonW hout d aT etLengthThreshold =
      target.params(PushFeatureSw chParams.NonSautOonW hout d aT etLengthThresholdParam)
    val moreStr ctForUndef nedLanguages =
      target.params(PushFeatureSw chParams.OonT etLengthPred cateMoreStr ctForUndef nedLanguages)
    val  sSautLanguage =  f (moreStr ctForUndef nedLanguages) {
       sT etLanguage nSautOrUndef ned(language)
    } else  sT etLanguage nSaut(language)

    (use d aThresholds,  sSautLanguage) match {
      case (true, true) =>
        sautOonW h d aT etLengthThreshold
      case (true, false) =>
        nonSautOonW h d aT etLengthThreshold
      case (false, true) =>
        sautOonW hout d aT etLengthThreshold
      case (false, false) =>
        nonSautOonW hout d aT etLengthThreshold
      case _ => -1
    }
  }

  def getT etWordLengthThreshold(
    target: TargetUser w h TargetABDec der,
    language: Str ng,
    use d aThresholds: Boolean
  ): Double = {
    lazy val argfOonW h d aT etWordLengthThresholdParam =
      target.params(PushFeatureSw chParams.ArgfOonW h d aT etWordLengthThresholdParam)
    lazy val esfthOonW h d aT etWordLengthThresholdParam =
      target.params(PushFeatureSw chParams.EsfthOonW h d aT etWordLengthThresholdParam)

    lazy val argfOonCand datesW h d aCond  on =
       sT etLanguage nArgf(language) && use d aThresholds
    lazy val esfthOonCand datesW h d aCond  on =
       sT etLanguage nEsfth(language) && use d aThresholds
    lazy val af rfOonCand datesW hout d aCond  on =
       sT etLanguage nAf rf(language) && !use d aThresholds

    val af rfOonCand datesW hout d aT etWordLengthThreshold = 5
     f (argfOonCand datesW h d aCond  on) {
      argfOonW h d aT etWordLengthThresholdParam
    } else  f (esfthOonCand datesW h d aCond  on) {
      esfthOonW h d aT etWordLengthThresholdParam
    } else  f (af rfOonCand datesW hout d aCond  on) {
      af rfOonCand datesW hout d aT etWordLengthThreshold
    } else -1
  }

  def oonT etLengthBasedPrerank ngPred cate(
    characterBased: Boolean
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[OutOfNetworkT etCand date w h Target nfo[
    TargetUser w h TargetABDec der
  ]] = {
    val na  = "oon_t et_length_based_prerank ng_pred cate"
    val scopedStats = stats.scope(s"${na }_charBased_$characterBased")

    Pred cate
      .fromAsync {
        cand: OutOfNetworkT etCand date w h Target nfo[TargetUser w h TargetABDec der] =>
          cand match {
            case cand date: T etAuthorDeta ls =>
              val target = cand date.target
              val crt = cand date.commonRecType

              val updated d aLog c =
                target.params(PushFeatureSw chParams.OonT etLengthPred cateUpdated d aLog c)
              val updatedQuoteT etLog c =
                target.params(PushFeatureSw chParams.OonT etLengthPred cateUpdatedQuoteT etLog c)
              val use d aThresholds =  f (updated d aLog c || updatedQuoteT etLog c) {
                val has d a = updated d aLog c && (cand date.hasPhoto || cand date.hasV deo)
                val hasQuoteT et = updatedQuoteT etLog c && cand date.quotedT et.nonEmpty
                has d a || hasQuoteT et
              } else RecTypes. s d aType(crt)
              val enableF lter =
                target.params(PushFeatureSw chParams.EnablePrerank ngT etLengthPred cate)

              val language = cand date.t et.flatMap(_.language.map(_.language)).getOrElse("")
              val t etTextOpt = cand date.t et.flatMap(_.coreData.map(_.text))

              val (length: Double, threshold: Double) =  f (characterBased) {
                (
                  t etTextOpt.map(_.s ze.toDouble).getOrElse(9999.0),
                  getT etCharLengthThreshold(target, language, use d aThresholds))
              } else {
                (
                  t etTextOpt.map(getT etWordLength).getOrElse(999.0),
                  getT etWordLengthThreshold(target, language, use d aThresholds))
              }
              scopedStats.counter("threshold_" + threshold.toStr ng). ncr()

              Cand dateUt l.shouldApply althQual yF ltersForPrerank ngPred cates(cand date).map {
                case true  f enableF lter =>
                  length > threshold
                case _ => true
              }
            case _ =>
              scopedStats.counter("author_ s_not_hydrated"). ncr()
              Future.True
          }
      }.w hStats(scopedStats)
      .w hNa (na )
  }

  pr vate def  sT etLanguage nAf rf(cand dateLanguage: Str ng): Boolean = {
    val setAF RF: Set[Str ng] = Set("")
    setAF RF.conta ns(cand dateLanguage)
  }
  pr vate def  sT etLanguage nEsfth(cand dateLanguage: Str ng): Boolean = {
    val setESFTH: Set[Str ng] = Set("")
    setESFTH.conta ns(cand dateLanguage)
  }
  pr vate def  sT etLanguage nArgf(cand dateLanguage: Str ng): Boolean = {
    val setARGF: Set[Str ng] = Set("")
    setARGF.conta ns(cand dateLanguage)
  }

  pr vate def  sT etLanguage nSaut(cand dateLanguage: Str ng): Boolean = {
    val setSAUT = Set("")
    setSAUT.conta ns(cand dateLanguage)
  }

  pr vate def  sT etLanguage nSautOrUndef ned(cand dateLanguage: Str ng): Boolean = {
    val setSautOrUndef ned = Set("")
    setSautOrUndef ned.conta ns(cand dateLanguage)
  }

  def conta nTargetNegat veKeywords(text: Str ng, denyl st: Seq[Str ng]): Boolean = {
     f (denyl st. sEmpty)
      false
    else {
      denyl st
        .map { negat veKeyword =>
          text.toLo rCase().conta ns(negat veKeyword)
        }.reduce(_ || _)
    }
  }

  def Negat veKeywordsPred cate(
    postRank ngFeatureStoreCl ent: Dynam cFeatureStoreCl ent[MrRequestContextForFeatureStore]
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[
    PushCand date w h T etCand date w h Recom ndat onType
  ] = {

    val na  = "negat ve_keywords_pred cate"
    val scopedStatsRece ver = stats.scope(na )
    val allOonCand datesCounter = scopedStatsRece ver.counter("all_oon_cand dates")
    val f lteredOonCand datesCounter = scopedStatsRece ver.counter("f ltered_oon_cand dates")
    val t etLanguageFeature = "RecT et.T etyP eResult.Language"

    Pred cate
      .fromAsync { cand date: PushCand date w h T etCand date w h Recom ndat onType =>
        val target = cand date.target
        val crt = cand date.commonRecType
        val  sTw stlyCand date = RecTypes.tw stlyT ets.conta ns(crt)

        lazy val enableNegat veKeywordsPred cateParam =
          target.params(PushFeatureSw chParams.EnableNegat veKeywordsPred cateParam)
        lazy val negat veKeywordsPred cateDenyl st =
          target.params(PushFeatureSw chParams.Negat veKeywordsPred cateDenyl st)
        lazy val cand dateLanguage =
          cand date.categor calFeatures.getOrElse(t etLanguageFeature, "")

         f (Cand dateUt l.shouldApply althQual yF lters(cand date) && cand dateLanguage.equals(
            "en") &&  sTw stlyCand date && enableNegat veKeywordsPred cateParam) {
          allOonCand datesCounter. ncr()

          val t etTextFuture: Future[Str ng] =
            getT etText(cand date, postRank ngFeatureStoreCl ent)

          t etTextFuture.map { t etText =>
            val conta nsNegat veWords =
              conta nTargetNegat veKeywords(t etText, negat veKeywordsPred cateDenyl st)
            cand date.cac Pred cate nfo(
              na ,
               f (conta nsNegat veWords) 1.0 else 0.0,
              0.0,
              conta nsNegat veWords)
             f (conta nsNegat veWords) {
              f lteredOonCand datesCounter. ncr()
              false
            } else true
          }
        } else Future.True
      }
      .w hStats(stats.scope(na ))
      .w hNa (na )
  }
}
