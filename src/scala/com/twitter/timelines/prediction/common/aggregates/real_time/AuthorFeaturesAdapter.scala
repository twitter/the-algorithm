package com.twitter.timelines.prediction.common.aggregates.real_time

import com.twitter.dal.personal_data.thriftjava.PersonalDataType.UserState
import com.twitter.ml.api.Feature.Binary
import com.twitter.ml.api.{DataRecord, Feature, FeatureContext, RichDataRecord}
import com.twitter.ml.featurestore.catalog.entities.core.Author
import com.twitter.ml.featurestore.catalog.features.magicrecs.UserActivity
import com.twitter.ml.featurestore.lib.data.PredictionRecord
import com.twitter.ml.featurestore.lib.feature.{BoundFeature, BoundFeatureSet}
import com.twitter.ml.featurestore.lib.{UserId, Discrete => FSDiscrete}
import com.twitter.timelines.prediction.common.adapters.TimelinesAdapterBase
import java.lang.{Boolean => JBoolean}
import java.util
import scala.collection.JavaConverters._

object AuthorFeaturesAdapter extends TimelinesAdapterBase[PredictionRecord] {
  val UserStateBoundFeature: BoundFeature[UserId, FSDiscrete] = UserActivity.UserState.bind(Author)
  val UserFeaturesSet: BoundFeatureSet = BoundFeatureSet(UserStateBoundFeature)

  /**
   * Boolean features about viewer's user state. 
   * enum UserState {
   *   NEW = 0,
   *   NEAR_ZERO = 1,
   *   VERY_LIGHT = 2,
   *   LIGHT = 3,
   *   MEDIUM_TWEETER = 4,
   *   MEDIUM_NON_TWEETER = 5,
   *   HEAVY_NON_TWEETER = 6,
   *   HEAVY_TWEETER = 7
   * }(persisted='true')
   */
  val IS_USER_NEW = new Binary("timelines.author.user_state.is_user_new", Set(UserState).asJava)
  val IS_USER_LIGHT = new Binary("timelines.author.user_state.is_user_light", Set(UserState).asJava)
  val IS_USER_MEDIUM_TWEETER =
    new Binary("timelines.author.user_state.is_user_medium_tweeter", Set(UserState).asJava)
  val IS_USER_MEDIUM_NON_TWEETER =
    new Binary("timelines.author.user_state.is_user_medium_non_tweeter", Set(UserState).asJava)
  val IS_USER_HEAVY_NON_TWEETER =
    new Binary("timelines.author.user_state.is_user_heavy_non_tweeter", Set(UserState).asJava)
  val IS_USER_HEAVY_TWEETER =
    new Binary("timelines.author.user_state.is_user_heavy_tweeter", Set(UserState).asJava)
  val userStateToFeatureMap: Map[Long, Binary] = Map(
    0L -> IS_USER_NEW,
    1L -> IS_USER_LIGHT,
    2L -> IS_USER_LIGHT,
    3L -> IS_USER_LIGHT,
    4L -> IS_USER_MEDIUM_TWEETER,
    5L -> IS_USER_MEDIUM_NON_TWEETER,
    6L -> IS_USER_HEAVY_NON_TWEETER,
    7L -> IS_USER_HEAVY_TWEETER
  )

  val UserStateBooleanFeatures: Set[Feature[_]] = userStateToFeatureMap.values.toSet

  private val allFeatures: Seq[Feature[_]] = UserStateBooleanFeatures.toSeq
  override def getFeatureContext: FeatureContext = new FeatureContext(allFeatures: _*)
  override def commonFeatures: Set[Feature[_]] = Set.empty

  override def adaptToDataRecords(record: PredictionRecord): util.List[DataRecord] = {
    val newRecord = new RichDataRecord(new DataRecord)
    record
      .getFeatureValue(UserStateBoundFeature)
      .flatMap { userState => userStateToFeatureMap.get(userState.value) }.foreach {
        booleanFeature => newRecord.setFeatureValue[JBoolean](booleanFeature, true)
      }

    List(newRecord.getRecord).asJava
  }
}
