package com.twitter.follow_recommendations.common.feature_hydration.adapters

import com.twitter.follow_recommendations.common.models.DisplayLocation
import com.twitter.ml.api.Feature.Binary
import com.twitter.ml.api.Feature.Continuous
import com.twitter.ml.api.Feature.Discrete
import com.twitter.ml.api.Feature.Tetwittert
import com.twitter.ml.api.util.FDsl._
import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.FeatureContetwittert
import com.twitter.ml.api.IRecordOneToOneAdapter
import com.twitter.onboarding.relevance.util.metadata.LanguageUtil
import com.twitter.product_mitwitterer.core.model.marshalling.request.ClientContetwittert
import com.twitter.snowflake.id.SnowflakeId

object ClientContetwittertAdapter etwittertends IRecordOneToOneAdapter[(ClientContetwittert, DisplayLocation)] {

  // we name features with `user.account` for relatively static user-related features
  val USER_COUNTRY: Tetwittert = new Tetwittert("user.account.country")
  val USER_LANGUAGE: Tetwittert = new Tetwittert("user.account.language")
  // we name features with `user.contetwittert` for more dynamic user-related features
  val USER_LANGUAGE_PREFItwitter: Tetwittert = new Tetwittert("user.contetwittert.language_prefitwitter")
  val USER_CLIENT: Discrete = new Discrete("user.contetwittert.client")
  val USER_AGE: Continuous = new Continuous("user.contetwittert.age")
  val USER_IS_RECENT: Binary = new Binary("user.is.recent")
  // we name features with `meta` for meta info about the WTF recommendation request
  val META_DISPLAY_LOCATION: Tetwittert = new Tetwittert("meta.display_location")
  val META_POSITION: Discrete = new Discrete("meta.position")
  // This indicates whether a data point is from a random serving policy
  val META_IS_RANDOM: Binary = new Binary("prediction.engine.is_random")

  val RECENT_WIN_IN_DAYS: Int = 30
  val GOAL_META_POSITION: Long = 1L
  val GOAL_META_IS_RANDOM: Boolean = true

  override val getFeatureContetwittert: FeatureContetwittert = new FeatureContetwittert(
    USER_COUNTRY,
    USER_LANGUAGE,
    USER_AGE,
    USER_LANGUAGE_PREFItwitter,
    USER_CLIENT,
    USER_IS_RECENT,
    META_DISPLAY_LOCATION,
    META_POSITION,
    META_IS_RANDOM
  )

  /**
   * we only want to set the relevant fields iff they etwitterist to eliminate redundant information
   * we do some simple normalization on the language code
   * we set META_POSITION to 1 always
   * we set META_IS_RANDOM to true always to simulate a random serving distribution
   * @param record ClientContetwittert and DisplayLocation from the request
   */
  override def adaptToDataRecord(target: (ClientContetwittert, DisplayLocation)): DataRecord = {
    val dr = new DataRecord()
    val cc = target._1
    val dl = target._2
    cc.countryCode.foreach(countryCode => dr.setFeatureValue(USER_COUNTRY, countryCode))
    cc.languageCode.foreach(rawLanguageCode => {
      val userLanguage = LanguageUtil.simplifyLanguage(rawLanguageCode)
      val userLanguagePrefitwitter = userLanguage.take(2)
      dr.setFeatureValue(USER_LANGUAGE, userLanguage)
      dr.setFeatureValue(USER_LANGUAGE_PREFItwitter, userLanguagePrefitwitter)
    })
    cc.appId.foreach(appId => dr.setFeatureValue(USER_CLIENT, appId))
    cc.userId.foreach(id =>
      SnowflakeId.timeFromIdOpt(id).map { signupTime =>
        val userAge = signupTime.untilNow.inMillis.toDouble
        dr.setFeatureValue(USER_AGE, userAge)
        dr.setFeatureValue(USER_IS_RECENT, signupTime.untilNow.inDays <= RECENT_WIN_IN_DAYS)
        signupTime.untilNow.inDays
      })
    dr.setFeatureValue(META_DISPLAY_LOCATION, dl.toFsName)
    dr.setFeatureValue(META_POSITION, GOAL_META_POSITION)
    dr.setFeatureValue(META_IS_RANDOM, GOAL_META_IS_RANDOM)
    dr
  }
}
