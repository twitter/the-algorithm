package com.twitter.timelines.prediction.features.request_contetwittert

import com.twitter.ml.api.FeatureContetwittert
import com.twitter.ml.api.Feature._
import com.twitter.dal.personal_data.thriftjava.PersonalDataType._
import scala.collection.JavaConverters._

object RequestContetwittertFeatures {
  val COUNTRY_CODE =
    new Tetwittert("request_contetwittert.country_code", Set(PrivateCountryOrRegion, InferredCountry).asJava)
  val LANGUAGE_CODE = new Tetwittert(
    "request_contetwittert.language_code",
    Set(GeneralSettings, ProvidedLanguage, InferredLanguage).asJava)
  val REQUEST_PROVENANCE = new Tetwittert("request_contetwittert.request_provenance", Set(AppUsage).asJava)
  val DISPLAY_WIDTH = new Continuous("request_contetwittert.display_width", Set(OtherDeviceInfo).asJava)
  val DISPLAY_HEIGHT = new Continuous("request_contetwittert.display_height", Set(OtherDeviceInfo).asJava)
  val DISPLAY_DPI = new Continuous("request_contetwittert.display_dpi", Set(OtherDeviceInfo).asJava)

  // the following features are not Continuous Features because for e.g. continuity between
  // 23 and 0 hours cannot be handled that way. instead, we will treat each slice of hours/days
  // independently, like a set of sparse binary features.
  val TIMESTAMP_GMT_HOUR =
    new Discrete("request_contetwittert.timestamp_gmt_hour", Set(PrivateTimestamp).asJava)
  val TIMESTAMP_GMT_DOW =
    new Discrete("request_contetwittert.timestamp_gmt_dow", Set(PrivateTimestamp).asJava)

  val IS_GET_INITIAL = new Binary("request_contetwittert.is_get_initial")
  val IS_GET_MIDDLE = new Binary("request_contetwittert.is_get_middle")
  val IS_GET_NEWER = new Binary("request_contetwittert.is_get_newer")
  val IS_GET_OLDER = new Binary("request_contetwittert.is_get_older")

  // the following features are not Binary Features because the source field is Option[Boolean],
  // and we want to distinguish Some(false) from None. None will be converted to -1.
  val IS_POLLING = new Discrete("request_contetwittert.is_polling")
  val IS_SESSION_START = new Discrete("request_contetwittert.is_session_start")

  // Helps distinguish requests from "home" vs "home_latest" (reverse chron home view).
  val TIMELINE_KIND = new Tetwittert("request_contetwittert.timeline_kind")

  val featureContetwittert = new FeatureContetwittert(
    COUNTRY_CODE,
    LANGUAGE_CODE,
    REQUEST_PROVENANCE,
    DISPLAY_WIDTH,
    DISPLAY_HEIGHT,
    DISPLAY_DPI,
    TIMESTAMP_GMT_HOUR,
    TIMESTAMP_GMT_DOW,
    IS_GET_INITIAL,
    IS_GET_MIDDLE,
    IS_GET_NEWER,
    IS_GET_OLDER,
    IS_POLLING,
    IS_SESSION_START,
    TIMELINE_KIND
  )
}
