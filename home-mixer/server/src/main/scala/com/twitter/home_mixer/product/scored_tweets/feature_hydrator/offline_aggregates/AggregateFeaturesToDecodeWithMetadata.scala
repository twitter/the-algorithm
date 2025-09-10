package com.twitter.home_mixer.product.scored_tweets.feature_hydrator.offline_aggregates

import com.twitter.finagle.stats.NullStatsReceiver
import com.twitter.timelinemixer.injection.repository.uss.VersionedAggregateFeaturesDecoder
import com.twitter.ml.api.DataRecord
import com.twitter.timelines.aggregate_interactions.thriftjava.UserAggregateInteractions
import com.twitter.timelines.aggregate_interactions.v17.thriftjava.{
  UserAggregateInteractions => V17UserAggregateInteractions
}
import com.twitter.timelines.aggregate_interactions.v1.thriftjava.{
  UserAggregateInteractions => V1UserAggregateInteractions
}
import com.twitter.timelines.suggests.common.dense_data_record.thriftjava.DenseCompactDataRecord
import com.twitter.timelines.suggests.common.dense_data_record.thriftscala.DenseFeatureMetadata
import java.lang.{Long => JLong}
import java.util.Collections
import java.util.{Map => JMap}

private[offline_aggregates] case class AggregateFeaturesToDecodeWithMetadata(
  metadataOpt: Option[DenseFeatureMetadata],
  aggregates: UserAggregateInteractions) {
  def toDataRecord(dr: DenseCompactDataRecord): DataRecord =
    VersionedAggregateFeaturesDecoder.fromJDenseCompact(
      metadataOpt,
      dr.versionId,
      NullStatsReceiver,
      s"V${dr.versionId}"
    )(dr)

  def userAggregatesOpt: Option[DenseCompactDataRecord] = {
    aggregates.getSetField match {
      case UserAggregateInteractions._Fields.V17 =>
        Option(aggregates.getV17.user_aggregates)
      case _ =>
        None
    }
  }

  def userAuthorAggregates = extract(_.user_author_aggregates)
  def userEngagerAggregates = extract(_.user_engager_aggregates)
  def userMentionAggregates = extract(_.user_mention_aggregates)
  def userOriginalAuthorAggregates = extract(_.user_original_author_aggregates)
  def userRequestDowAggregates = extract(_.user_request_dow_aggregates)
  def userRequestHourAggregates = extract(_.user_request_hour_aggregates)
  def rectweetUserSimclustersTweetAggregates = extract(_.rectweet_user_simclusters_tweet_aggregates)
  def userTwitterListAggregates = extract(_.user_list_aggregates)
  def userTopicAggregates = extract(_.user_topic_aggregates)
  def userInferredTopicAggregates = extract(_.user_inferred_topic_aggregates)
  def userMediaUnderstandingAnnotationAggregates = extract(
    _.user_media_understanding_annotation_aggregates)

  private def extract[T](
    v17Fn: V17UserAggregateInteractions => JMap[JLong, DenseCompactDataRecord]
  ): JMap[JLong, DenseCompactDataRecord] = {
    aggregates.getSetField match {
      case UserAggregateInteractions._Fields.V17 =>
        v17Fn(aggregates.getV17)
      case _ =>
        Collections.emptyMap()
    }
  }
}

object AggregateFeaturesToDecodeWithMetadata {
  val empty = new AggregateFeaturesToDecodeWithMetadata(
    None,
    UserAggregateInteractions.v1(new V1UserAggregateInteractions()))
}
