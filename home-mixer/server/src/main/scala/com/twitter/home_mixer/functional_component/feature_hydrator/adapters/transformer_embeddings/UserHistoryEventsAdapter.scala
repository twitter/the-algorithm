package com.twitter.home_mixer.functional_component.feature_hydrator.adapters.transformer_embeddings

import com.twitter.ml.api.DataType
import com.twitter.ml.api.Feature
import com.twitter.ml.api.FeatureContext
import com.twitter.ml.api.GeneralTensor
import com.twitter.ml.api.Int32Tensor
import com.twitter.ml.api.Int64Tensor
import com.twitter.ml.api.RawTypedTensor
import com.twitter.ml.api.RichDataRecord
import com.twitter.timelines.prediction.common.adapters.TimelinesMutatingAdapterBase
import com.twitter.user_history_transformer.thriftscala.UserHistory
import scala.collection.JavaConverters._
import java.nio.ByteBuffer

object UserHistoryEventsFeatures {
  val TweetIdsFeature: Feature.Tensor = new Feature.Tensor(
    "user.history.user_history_events_tweet_ids",
    DataType.INT64,
    List(100L).map(long2Long).asJava,
  )

  val AuthorIdsFeature: Feature.Tensor = new Feature.Tensor(
    "user.history.user_history_events_author_ids",
    DataType.INT64,
    List(100L).map(long2Long).asJava,
  )

  val ActionTypesFeature: Feature.Tensor = new Feature.Tensor(
    "user.history.user_history_events_action_types",
    DataType.INT32,
    List(100L).map(long2Long).asJava,
  )

  val ActionTimestampsFeature: Feature.Tensor = new Feature.Tensor(
    "user.history.user_history_events_action_timestamps",
    DataType.INT64,
    List(100L).map(long2Long).asJava,
  )

  val SemanticIdsFeature: Feature.Tensor = new Feature.Tensor(
    "user.history.user_history_events_semantic_ids",
    DataType.UINT8,
    List(500L).map(long2Long).asJava,
  )
}

trait BaseUserHistoryEventsAdapter extends TimelinesMutatingAdapterBase[Seq[UserHistory]] {
  override val commonFeatures: Set[Feature[_]] = Set.empty

  private val DefaultSemanticId: Seq[Short] = Seq(0, 0, 0, 0, 0)

  def setAdditionalFeatures(
    record: Seq[UserHistory],
    mutableDataRecord: RichDataRecord
  ): Unit = {}

  override def setFeatures(
    record: Seq[UserHistory],
    mutableDataRecord: RichDataRecord
  ): Unit = {
    val tweetIdsFeature = GeneralTensor.int64Tensor(
      new Int64Tensor(
        record
          .map(history => history.sourceTweetId.getOrElse(history.tweetId)).map(long2Long).asJava))

    val authorIdsFeature = GeneralTensor.int64Tensor(
      new Int64Tensor(record.map(_.authorId.getOrElse(-1L)).map(long2Long).asJava))

    val actionTypesFeature = GeneralTensor.int32Tensor(
      new Int32Tensor(record.map(_.actionType.getValue()).map(int2Integer).asJava))

    val actionTimestampsFeature = GeneralTensor.int64Tensor(
      new Int64Tensor(record.map(_.engagedTimestampMs).map(long2Long).asJava))

    val semanticIdsFeature = GeneralTensor.rawTypedTensor(
      new RawTypedTensor(
        DataType.UINT8,
        ByteBuffer.wrap(
          record
            .flatMap(_.metadata.flatMap(_.semanticId).getOrElse(DefaultSemanticId))
            .map(_.toByte).toArray)
      )
    )

    mutableDataRecord
      .setFeatureValue(UserHistoryEventsFeatures.TweetIdsFeature, tweetIdsFeature)
    mutableDataRecord
      .setFeatureValue(UserHistoryEventsFeatures.AuthorIdsFeature, authorIdsFeature)
    mutableDataRecord
      .setFeatureValue(UserHistoryEventsFeatures.ActionTypesFeature, actionTypesFeature)
    mutableDataRecord
      .setFeatureValue(UserHistoryEventsFeatures.ActionTimestampsFeature, actionTimestampsFeature)
    mutableDataRecord
      .setFeatureValue(UserHistoryEventsFeatures.SemanticIdsFeature, semanticIdsFeature)

    setAdditionalFeatures(record, mutableDataRecord)

  }

  override def getFeatureContext: FeatureContext = new FeatureContext(
    UserHistoryEventsFeatures.TweetIdsFeature,
    UserHistoryEventsFeatures.AuthorIdsFeature,
    UserHistoryEventsFeatures.ActionTypesFeature,
    UserHistoryEventsFeatures.ActionTimestampsFeature,
    UserHistoryEventsFeatures.SemanticIdsFeature,
  )
}
object UserHistoryEventsAdapter extends BaseUserHistoryEventsAdapter {}
