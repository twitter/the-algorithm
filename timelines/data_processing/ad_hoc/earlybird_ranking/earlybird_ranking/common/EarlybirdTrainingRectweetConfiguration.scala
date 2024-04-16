package com.tw ter.t  l nes.data_process ng.ad_hoc.earlyb rd_rank ng.common

 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ml.ap . Transform
 mport com.tw ter.ml.ap .transform.CascadeTransform
 mport com.tw ter.ml.ap .ut l.SR chDataRecord
 mport com.tw ter.search.common.features.SearchResultFeature
 mport com.tw ter.search.common.features.T etFeature
 mport com.tw ter.t  l nes.pred ct on.features. l. TLFeatures._
 mport scala.collect on.JavaConverters._

class Earlyb rdTra n ngRect etConf gurat on extends Earlyb rdTra n ngConf gurat on {

  overr de val labels: Map[Str ng, Feature.B nary] = Map(
    "deta l_expanded" ->  S_CL CKED,
    "favor ed" ->  S_FAVOR TED,
    "open_l nked" ->  S_OPEN_L NKED,
    "photo_expanded" ->  S_PHOTO_EXPANDED,
    "prof le_cl cked" ->  S_PROF LE_CL CKED,
    "repl ed" ->  S_REPL ED,
    "ret eted" ->  S_RETWEETED,
    "v deo_playback50" ->  S_V DEO_PLAYBACK_50
  )

  overr de val Pos  veSampl ngRate: Double = 0.5

  overr de def featureToSearchResultFeatureMap: Map[Feature[_], SearchResultFeature] =
    super.featureToSearchResultFeatureMap ++ Map(
      TEXT_SCORE -> T etFeature.TEXT_SCORE,
      REPLY_COUNT -> T etFeature.REPLY_COUNT,
      RETWEET_COUNT -> T etFeature.RETWEET_COUNT,
      FAV_COUNT -> T etFeature.FAVOR TE_COUNT,
      HAS_CARD -> T etFeature.HAS_CARD_FLAG,
      HAS_CONSUMER_V DEO -> T etFeature.HAS_CONSUMER_V DEO_FLAG,
      HAS_PRO_V DEO -> T etFeature.HAS_PRO_V DEO_FLAG,
      HAS_V NE -> T etFeature.HAS_V NE_FLAG,
      HAS_PER SCOPE -> T etFeature.HAS_PER SCOPE_FLAG,
      HAS_NAT VE_ MAGE -> T etFeature.HAS_NAT VE_ MAGE_FLAG,
      HAS_ MAGE -> T etFeature.HAS_ MAGE_URL_FLAG,
      HAS_NEWS -> T etFeature.HAS_NEWS_URL_FLAG,
      HAS_V DEO -> T etFeature.HAS_V DEO_URL_FLAG,
      // so  features that ex st for recap are not ava lable  n rect et
      //    HAS_TREND
      //    HAS_MULT PLE_HASHTAGS_OR_TRENDS
      //     S_OFFENS VE
      //     S_REPLY
      //     S_RETWEET
       S_AUTHOR_BOT -> T etFeature. S_USER_BOT_FLAG,
       S_AUTHOR_SPAM -> T etFeature. S_USER_SPAM_FLAG,
       S_AUTHOR_NSFW -> T etFeature. S_USER_NSFW_FLAG,
      //    FROM_VER F ED_ACCOUNT
      USER_REP -> T etFeature.USER_REPUTAT ON,
      //    EMBEDS_ MPRESS ON_COUNT
      //    EMBEDS_URL_COUNT
      //    V DEO_V EW_COUNT
      FAV_COUNT_V2 -> T etFeature.FAVOR TE_COUNT_V2,
      RETWEET_COUNT_V2 -> T etFeature.RETWEET_COUNT_V2,
      REPLY_COUNT_V2 -> T etFeature.REPLY_COUNT_V2,
       S_SENS T VE -> T etFeature. S_SENS T VE_CONTENT,
      HAS_MULT PLE_MED A -> T etFeature.HAS_MULT PLE_MED A_FLAG,
       S_AUTHOR_PROF LE_EGG -> T etFeature.PROF LE_ S_EGG_FLAG,
       S_AUTHOR_NEW -> T etFeature. S_USER_NEW_FLAG,
      NUM_MENT ONS -> T etFeature.NUM_MENT ONS,
      NUM_HASHTAGS -> T etFeature.NUM_HASHTAGS,
      HAS_V S BLE_L NK -> T etFeature.HAS_V S BLE_L NK_FLAG,
      HAS_L NK -> T etFeature.HAS_L NK_FLAG
    )

  overr de def der vedFeaturesAdder: CascadeTransform = {
    // only L NK_LANGUAGE ava labe  n rect et. no LANGUAGE feature
    val l nkLanguageTransform = new  Transform {
      pr vate val l nkLanguageFeature = new Feature.Cont nuous(T etFeature.L NK_LANGUAGE.getNa )

      overr de def transformContext(featureContext: FeatureContext): FeatureContext =
        featureContext.addFeatures(
          l nkLanguageFeature
        )

      overr de def transform(record: DataRecord): Un  = {
        val srecord = SR chDataRecord(record)

        srecord.getFeatureValueOpt(L NK_LANGUAGE).map { l nk_language =>
          srecord.setFeatureValue(
            l nkLanguageFeature,
            l nk_language.toDouble
          )
        }
      }
    }

    new CascadeTransform(
      L st(
        super.der vedFeaturesAdder,
        l nkLanguageTransform
      ).asJava
    )
  }
}
