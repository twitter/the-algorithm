package com.tw ter.t  l nes.data_process ng.ad_hoc.earlyb rd_rank ng.common

 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ml.ap . Transform
 mport com.tw ter.ml.ap .transform.CascadeTransform
 mport com.tw ter.ml.ap .transform.TransformFactory
 mport com.tw ter.ml.ap .ut l.SR chDataRecord
 mport com.tw ter.ml.ap .constant.SharedFeatures
 mport com.tw ter.search.common.features.SearchResultFeature
 mport com.tw ter.search.common.features.ExternalT etFeature
 mport com.tw ter.search.common.features.T etFeature
 mport com.tw ter.t  l nes.pred ct on.features.recap.RecapFeatures
 mport com.tw ter.t  l nes.pred ct on.features.request_context.RequestContextFeatures
 mport com.tw ter.t  l nes.pred ct on.features.t  _features.T  DataRecordFeatures
 mport com.tw ter.t  l nes.pred ct on.features.common.T  l nesSharedFeatures
 mport com.tw ter.t  l nes.pred ct on.features.real_graph.RealGraphDataRecordFeatures
 mport scala.collect on.JavaConverters._
 mport java.lang.{Boolean => JBoolean}

case class Label nfo(na : Str ng, downsampleFract on: Double,  mportance: Double)

case class Label nfoW hFeature( nfo: Label nfo, feature: Feature[JBoolean])

tra  Earlyb rdTra n ngConf gurat on {

  protected def labels: Map[Str ng, Feature.B nary]

  protected def   ghts: Map[Str ng, Double] = Map(
    "deta l_expanded" -> 0.3,
    "favor ed" -> 1.0,
    "open_l nked" -> 0.1,
    "photo_expanded" -> 0.03,
    "prof le_cl cked" -> 1.0,
    "repl ed" -> 9.0,
    "ret eted" -> 1.0,
    "v deo_playback50" -> 0.01
  )

  //   bas cally should not downsample any of t  prec ous pos  ve data.
  //  mportance are currently set to match t  full model's   ghts.
  protected def Pos  veSampl ngRate: Double = 1.0
  pr vate def Negat veSampl ngRate: Double = Pos  veSampl ngRate * 0.08

  //   bas cally should not downsample any of t  prec ous pos  ve data.
  //  mportance are currently set to match t  full model's   ghts.
  f nal lazy val Label nfos: L st[Label nfoW hFeature] = {
    assert(labels.keySet ==   ghts.keySet)
    labels.keySet.map(makeLabel nfoW hFeature).toL st
  }

  def makeLabel nfoW hFeature(labelNa : Str ng): Label nfoW hFeature = {
    Label nfoW hFeature(
      Label nfo(labelNa , Pos  veSampl ngRate,   ghts(labelNa )),
      labels(labelNa ))
  }

  f nal lazy val Negat ve nfo: Label nfo = Label nfo("negat ve", Negat veSampl ngRate, 1.0)

  // example of features ava lable  n sc ma based na space:
  protected def featureToSearchResultFeatureMap: Map[Feature[_], SearchResultFeature] = Map(
    RecapFeatures.TEXT_SCORE -> T etFeature.TEXT_SCORE,
    RecapFeatures.REPLY_COUNT -> T etFeature.REPLY_COUNT,
    RecapFeatures.RETWEET_COUNT -> T etFeature.RETWEET_COUNT,
    RecapFeatures.FAV_COUNT -> T etFeature.FAVOR TE_COUNT,
    RecapFeatures.HAS_CARD -> T etFeature.HAS_CARD_FLAG,
    RecapFeatures.HAS_CONSUMER_V DEO -> T etFeature.HAS_CONSUMER_V DEO_FLAG,
    RecapFeatures.HAS_PRO_V DEO -> T etFeature.HAS_PRO_V DEO_FLAG,
    // no correspond ng HAS_NAT VE_V DEO feature  n T etFeature
    RecapFeatures.HAS_V NE -> T etFeature.HAS_V NE_FLAG,
    RecapFeatures.HAS_PER SCOPE -> T etFeature.HAS_PER SCOPE_FLAG,
    RecapFeatures.HAS_NAT VE_ MAGE -> T etFeature.HAS_NAT VE_ MAGE_FLAG,
    RecapFeatures.HAS_ MAGE -> T etFeature.HAS_ MAGE_URL_FLAG,
    RecapFeatures.HAS_NEWS -> T etFeature.HAS_NEWS_URL_FLAG,
    RecapFeatures.HAS_V DEO -> T etFeature.HAS_V DEO_URL_FLAG,
    RecapFeatures.HAS_TREND -> T etFeature.HAS_TREND_FLAG,
    RecapFeatures.HAS_MULT PLE_HASHTAGS_OR_TRENDS -> T etFeature.HAS_MULT PLE_HASHTAGS_OR_TRENDS_FLAG,
    RecapFeatures. S_OFFENS VE -> T etFeature. S_OFFENS VE_FLAG,
    RecapFeatures. S_REPLY -> T etFeature. S_REPLY_FLAG,
    RecapFeatures. S_RETWEET -> T etFeature. S_RETWEET_FLAG,
    RecapFeatures. S_AUTHOR_BOT -> T etFeature. S_USER_BOT_FLAG,
    RecapFeatures.FROM_VER F ED_ACCOUNT -> T etFeature.FROM_VER F ED_ACCOUNT_FLAG,
    RecapFeatures.USER_REP -> T etFeature.USER_REPUTAT ON,
    RecapFeatures.EMBEDS_ MPRESS ON_COUNT -> T etFeature.EMBEDS_ MPRESS ON_COUNT,
    RecapFeatures.EMBEDS_URL_COUNT -> T etFeature.EMBEDS_URL_COUNT,
    // RecapFeatures.V DEO_V EW_COUNT deprecated
    RecapFeatures.FAV_COUNT_V2 -> T etFeature.FAVOR TE_COUNT_V2,
    RecapFeatures.RETWEET_COUNT_V2 -> T etFeature.RETWEET_COUNT_V2,
    RecapFeatures.REPLY_COUNT_V2 -> T etFeature.REPLY_COUNT_V2,
    RecapFeatures. S_SENS T VE -> T etFeature. S_SENS T VE_CONTENT,
    RecapFeatures.HAS_MULT PLE_MED A -> T etFeature.HAS_MULT PLE_MED A_FLAG,
    RecapFeatures. S_AUTHOR_PROF LE_EGG -> T etFeature.PROF LE_ S_EGG_FLAG,
    RecapFeatures. S_AUTHOR_NEW -> T etFeature. S_USER_NEW_FLAG,
    RecapFeatures.NUM_MENT ONS -> T etFeature.NUM_MENT ONS,
    RecapFeatures.NUM_HASHTAGS -> T etFeature.NUM_HASHTAGS,
    RecapFeatures.HAS_V S BLE_L NK -> T etFeature.HAS_V S BLE_L NK_FLAG,
    RecapFeatures.HAS_L NK -> T etFeature.HAS_L NK_FLAG,
    //note: D SCRETE features are not supported by t  model nterpreter tool.
    // for t  follow ng features,   w ll create separate CONT NUOUS features  nstead of renam ng
    //RecapFeatures.L NK_LANGUAGE
    //RecapFeatures.LANGUAGE
    T  l nesSharedFeatures.HAS_QUOTE -> T etFeature.HAS_QUOTE_FLAG,
    T  l nesSharedFeatures.QUOTE_COUNT -> T etFeature.QUOTE_COUNT,
    T  l nesSharedFeatures.WE GHTED_FAV_COUNT -> T etFeature.WE GHTED_FAVOR TE_COUNT,
    T  l nesSharedFeatures.WE GHTED_QUOTE_COUNT -> T etFeature.WE GHTED_QUOTE_COUNT,
    T  l nesSharedFeatures.WE GHTED_REPLY_COUNT -> T etFeature.WE GHTED_REPLY_COUNT,
    T  l nesSharedFeatures.WE GHTED_RETWEET_COUNT -> T etFeature.WE GHTED_RETWEET_COUNT,
    T  l nesSharedFeatures.DECAYED_FAVOR TE_COUNT -> T etFeature.DECAYED_FAVOR TE_COUNT,
    T  l nesSharedFeatures.DECAYED_RETWEET_COUNT -> T etFeature.DECAYED_RETWEET_COUNT,
    T  l nesSharedFeatures.DECAYED_REPLY_COUNT -> T etFeature.DECAYED_RETWEET_COUNT,
    T  l nesSharedFeatures.DECAYED_QUOTE_COUNT -> T etFeature.DECAYED_QUOTE_COUNT,
    T  l nesSharedFeatures.FAKE_FAVOR TE_COUNT -> T etFeature.FAKE_FAVOR TE_COUNT,
    T  l nesSharedFeatures.FAKE_RETWEET_COUNT -> T etFeature.FAKE_RETWEET_COUNT,
    T  l nesSharedFeatures.FAKE_REPLY_COUNT -> T etFeature.FAKE_REPLY_COUNT,
    T  l nesSharedFeatures.FAKE_QUOTE_COUNT -> T etFeature.FAKE_QUOTE_COUNT,
    T  l nesSharedFeatures.EMBEDS_ MPRESS ON_COUNT_V2 -> T etFeature.EMBEDS_ MPRESS ON_COUNT_V2,
    T  l nesSharedFeatures.EMBEDS_URL_COUNT_V2 -> T etFeature.EMBEDS_URL_COUNT_V2,
    T  l nesSharedFeatures.LABEL_ABUS VE_FLAG -> T etFeature.LABEL_ABUS VE_FLAG,
    T  l nesSharedFeatures.LABEL_ABUS VE_H _RCL_FLAG -> T etFeature.LABEL_ABUS VE_H _RCL_FLAG,
    T  l nesSharedFeatures.LABEL_DUP_CONTENT_FLAG -> T etFeature.LABEL_DUP_CONTENT_FLAG,
    T  l nesSharedFeatures.LABEL_NSFW_H _PRC_FLAG -> T etFeature.LABEL_NSFW_H _PRC_FLAG,
    T  l nesSharedFeatures.LABEL_NSFW_H _RCL_FLAG -> T etFeature.LABEL_NSFW_H _RCL_FLAG,
    T  l nesSharedFeatures.LABEL_SPAM_FLAG -> T etFeature.LABEL_SPAM_FLAG,
    T  l nesSharedFeatures.LABEL_SPAM_H _RCL_FLAG -> T etFeature.LABEL_SPAM_H _RCL_FLAG
  )

  protected def der vedFeaturesAdder:  Transform =
    new  Transform {
      pr vate val hasEngl shT etD ffU LangFeature =
        feature nstanceFromSearchResultFeature(ExternalT etFeature.HAS_ENGL SH_TWEET_D FF_U _LANG)
          .as nstanceOf[Feature.B nary]
      pr vate val hasEngl shU D ffT etLangFeature =
        feature nstanceFromSearchResultFeature(ExternalT etFeature.HAS_ENGL SH_U _D FF_TWEET_LANG)
          .as nstanceOf[Feature.B nary]
      pr vate val hasD ffLangFeature =
        feature nstanceFromSearchResultFeature(ExternalT etFeature.HAS_D FF_LANG)
          .as nstanceOf[Feature.B nary]
      pr vate val  sSelfT etFeature =
        feature nstanceFromSearchResultFeature(ExternalT etFeature. S_SELF_TWEET)
          .as nstanceOf[Feature.B nary]
      pr vate val t etAge nSecsFeature =
        feature nstanceFromSearchResultFeature(ExternalT etFeature.TWEET_AGE_ N_SECS)
          .as nstanceOf[Feature.Cont nuous]
      pr vate val authorSpec f cScoreFeature =
        feature nstanceFromSearchResultFeature(ExternalT etFeature.AUTHOR_SPEC F C_SCORE)
          .as nstanceOf[Feature.Cont nuous]

      // see com nts above
      pr vate val l nkLanguageFeature = new Feature.Cont nuous(T etFeature.L NK_LANGUAGE.getNa )
      pr vate val languageFeature = new Feature.Cont nuous(T etFeature.LANGUAGE.getNa )

      overr de def transformContext(featureContext: FeatureContext): FeatureContext =
        featureContext.addFeatures(
          authorSpec f cScoreFeature,
          // used w n tra n ng aga nst t  full scoreEarlyb rdModelEvaluat onJob.scala
          // T  l nesSharedFeatures.PRED CTED_SCORE_LOG,
          hasEngl shT etD ffU LangFeature,
          hasEngl shU D ffT etLangFeature,
          hasD ffLangFeature,
           sSelfT etFeature,
          t etAge nSecsFeature,
          l nkLanguageFeature,
          languageFeature
        )

      overr de def transform(record: DataRecord): Un  = {
        val srecord = SR chDataRecord(record)

        srecord.getFeatureValueOpt(RealGraphDataRecordFeatures.WE GHT).map { realgraph  ght =>
          srecord.setFeatureValue(authorSpec f cScoreFeature, realgraph  ght)
        }

        // use t  w n tra n ng aga nst t  log of t  full score
        // srecord.getFeatureValueOpt(T  l nesSharedFeatures.PRED CTED_SCORE).map { score =>
        //    f (score > 0.0) {
        //     srecord.setFeatureValue(T  l nesSharedFeatures.PRED CTED_SCORE_LOG, Math.log(score))
        //   }
        // }

         f (srecord.hasFeature(RequestContextFeatures.LANGUAGE_CODE) && srecord.hasFeature(
            RecapFeatures.LANGUAGE)) {
          val u lang sEngl sh = srecord
            .getFeatureValue(RequestContextFeatures.LANGUAGE_CODE).toStr ng == "en"
          val t et sEngl sh = srecord.getFeatureValue(RecapFeatures.LANGUAGE) == 5
          srecord.setFeatureValue(
            hasEngl shT etD ffU LangFeature,
            t et sEngl sh && !u lang sEngl sh
          )
          srecord.setFeatureValue(
            hasEngl shU D ffT etLangFeature,
            u lang sEngl sh && !t et sEngl sh
          )
        }
        srecord.getFeatureValueOpt(RecapFeatures.MATCH_U _LANG).map { match_u _lang =>
          srecord.setFeatureValue(
            hasD ffLangFeature,
            !match_u _lang
          )
        }

        for {
          author_ d <- srecord.getFeatureValueOpt(SharedFeatures.AUTHOR_ D)
          user_ d <- srecord.getFeatureValueOpt(SharedFeatures.USER_ D)
        } srecord.setFeatureValue(
           sSelfT etFeature,
          author_ d == user_ d
        )

        srecord.getFeatureValueOpt(T  DataRecordFeatures.T ME_S NCE_TWEET_CREAT ON).map {
          t  _s nce_t et_creat on =>
            srecord.setFeatureValue(
              t etAge nSecsFeature,
              t  _s nce_t et_creat on / 1000.0
            )
        }

        srecord.getFeatureValueOpt(RecapFeatures.L NK_LANGUAGE).map { l nk_language =>
          srecord.setFeatureValue(
            l nkLanguageFeature,
            l nk_language.toDouble
          )
        }
        srecord.getFeatureValueOpt(RecapFeatures.LANGUAGE).map { language =>
          srecord.setFeatureValue(
            languageFeature,
            language.toDouble
          )
        }
      }
    }

  protected def feature nstanceFromSearchResultFeature(
    t etFeature: SearchResultFeature
  ): Feature[_] = {
    val featureType = t etFeature.getType
    val featureNa  = t etFeature.getNa 

    requ re(
      !t etFeature. sD screte && (
        featureType == com.tw ter.search.common.features.thr ft.Thr ftSearchFeatureType.BOOLEAN_VALUE ||
          featureType == com.tw ter.search.common.features.thr ft.Thr ftSearchFeatureType.DOUBLE_VALUE ||
          featureType == com.tw ter.search.common.features.thr ft.Thr ftSearchFeatureType. NT32_VALUE
      )
    )

     f (featureType == com.tw ter.search.common.features.thr ft.Thr ftSearchFeatureType.BOOLEAN_VALUE)
      new Feature.B nary(featureNa )
    else
      new Feature.Cont nuous(featureNa )
  }

  lazy val Earlyb rdFeatureRena r:  Transform = {
    val earlyb rdFeatureRena Map: Map[Feature[_], Feature[_]] =
      featureToSearchResultFeatureMap.map {
        case (or g nalFeature, t etFeature) =>
          or g nalFeature -> feature nstanceFromSearchResultFeature(t etFeature)
      }.toMap

    new CascadeTransform(
      L st(
        der vedFeaturesAdder,
        TransformFactory.produceTransform(
          TransformFactory.produceFeatureRena TransformSpec(
            earlyb rdFeatureRena Map.asJava
          )
        )
      ).asJava
    )
  }
}
