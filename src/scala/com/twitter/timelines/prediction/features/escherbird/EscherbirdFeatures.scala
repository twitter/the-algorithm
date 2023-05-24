package com.twitter.timelines.prediction.features.escherbird

import com.twitter.dal.personal_data.thriftjava.PersonalDataType._
import com.twitter.ml.api.Feature
import java.util.{Set => JSet}
import scala.collection.JavaConverters._

object EscherbirdFeatures {
  val TweetGroupIds = new Feature.SparseBinary("escherbird.tweet_group_ids")
  val TweetDomainIds = new Feature.SparseBinary("escherbird.tweet_domain_ids", Set(DomainId).asJava)
  val TweetEntityIds =
    new Feature.SparseBinary("escherbird.tweet_entity_ids", Set(SemanticcoreClassification).asJava)
}

case class EscherbirdFeatures(
  tweetId: Long,
  tweetGroupIds: JSet[String],
  tweetDomainIds: JSet[String],
  tweetEntityIds: JSet[String])
