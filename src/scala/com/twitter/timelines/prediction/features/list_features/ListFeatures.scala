package com.twitter.timelines.prediction.features.list_features

import com.twitter.ml.api.Feature.{Binary, Discrete}
import com.twitter.ml.api.FeatureContext
import com.twitter.dal.personal_data.thriftjava.PersonalDataType._
import scala.collection.JavaConverters._

object ListFeatures {

  // list.id is used for list tweet injections in home. timelines.meta.list_id is used for list tweets in list timeline.
  val LIST_ID = new Discrete("list.id")

  val VIEWER_IS_OWNER =
    new Binary("list.viewer.is_owner", Set(ListsNonpublicList, ListsPublicList).asJava)
  val VIEWER_IS_SUBSCRIBER = new Binary("list.viewer.is_subscriber")
  val IS_PINNED_LIST = new Binary("list.is_pinned")

  val featureContext = new FeatureContext(
    LIST_ID,
    VIEWER_IS_OWNER,
    VIEWER_IS_SUBSCRIBER,
    IS_PINNED_LIST
  )
}
