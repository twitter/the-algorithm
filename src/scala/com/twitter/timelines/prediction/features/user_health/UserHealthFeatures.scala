package com.twitter.timelines.prediction.features.user_health

import com.twitter.ml.api.Feature
import com.twitter.timelines.author_features.user_health.thriftscala.UserState
import com.twitter.dal.personal_data.thriftjava.PersonalDataType.{UserState => UserStatePDT}
import com.twitter.dal.personal_data.thriftjava.PersonalDataType._
import scala.collection.JavaConverters._

object UserHealthFeatures {
  val UserState = new Feature.Discrete("user_health.user_state", Set(UserStatePDT, UserType).asJava)
  val IsLightMinusUser =
    new Feature.Binary("user_health.is_light_minus_user", Set(UserStatePDT, UserType).asJava)
  val AuthorState =
    new Feature.Discrete("user_health.author_state", Set(UserStatePDT, UserType).asJava)
  val NumAuthorFollowers =
    new Feature.Continuous("author_health.num_followers", Set(CountOfFollowersAndFollowees).asJava)
  val NumAuthorConnectDays = new Feature.Continuous("author_health.num_connect_days")
  val NumAuthorConnect = new Feature.Continuous("author_health.num_connect")

  val IsUserVerifiedUnion = new Feature.Binary("user_account.is_user_verified_union")
}

case class UserHealthFeatures(id: Long, userStateOpt: Option[UserState])
