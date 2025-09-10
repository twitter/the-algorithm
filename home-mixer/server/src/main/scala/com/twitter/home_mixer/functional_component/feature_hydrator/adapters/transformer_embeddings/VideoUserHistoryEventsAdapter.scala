package com.twitter.home_mixer.functional_component.feature_hydrator.adapters.transformer_embeddings

import com.twitter.ml.api.DataType
import com.twitter.ml.api.Feature
import com.twitter.ml.api.FeatureContext
import com.twitter.ml.api.GeneralTensor
import com.twitter.ml.api.Int32Tensor
import com.twitter.ml.api.Int64Tensor
import com.twitter.ml.api.RichDataRecord
import com.twitter.ml.api.StringTensor
import com.twitter.user_history_transformer.thriftscala.UserHistory
import scala.collection.JavaConverters._

object VideoUserHistoryEventsFeatures {
  val VideoWatchTimesFeature: Feature.Tensor = new Feature.Tensor(
    "user.history.user_history_events_video_watch_times",
    DataType.INT64,
    List(100L).map(long2Long).asJava,
  )
  val VideoMediaIdsFeature: Feature.Tensor = new Feature.Tensor(
    "user.history.user_history_events_video_media_ids",
    DataType.INT64,
    List(100L).map(long2Long).asJava,
  )

  val VideoMediaCategoriesFeature: Feature.Tensor = new Feature.Tensor(
    "user.history.user_history_events_video_media_categories",
    DataType.INT32,
    List(100L).map(long2Long).asJava,
  )

  val VideoDurationsFeature: Feature.Tensor = new Feature.Tensor(
    "user.history.user_history_events_video_durations",
    DataType.INT32,
    List(100L).map(long2Long).asJava,
  )
  val VideoTopEntityFeature: Feature.Tensor = new Feature.Tensor(
    "user.history.user_history_events_video_top_entity_info",
    DataType.STRING,
    List(100L).map(long2Long).asJava,
  )
  val VideoGrokTagsInfoFeature: Feature.Tensor = new Feature.Tensor(
    "user.history.user_history_events_video_grok_tags_info",
    DataType.STRING,
    List(100L).map(long2Long).asJava,
  )
  val VideoCluster95IdsFeature: Feature.Tensor = new Feature.Tensor(
    "user.history.user_history_events_video_cluster95_ids_info",
    DataType.INT64,
    List(100L).map(long2Long).asJava,
  )
}

object VideoUserHistoryEventsAdapter extends BaseUserHistoryEventsAdapter {
  override def getFeatureContext: FeatureContext = {
    super.getFeatureContext
      .addFeatures(VideoUserHistoryEventsFeatures.VideoWatchTimesFeature)
      .addFeatures(VideoUserHistoryEventsFeatures.VideoMediaIdsFeature)
      .addFeatures(VideoUserHistoryEventsFeatures.VideoMediaCategoriesFeature)
      .addFeatures(VideoUserHistoryEventsFeatures.VideoDurationsFeature)
      .addFeatures(VideoUserHistoryEventsFeatures.VideoCluster95IdsFeature)
      .addFeatures(VideoUserHistoryEventsFeatures.VideoTopEntityFeature)
      .addFeatures(VideoUserHistoryEventsFeatures.VideoGrokTagsInfoFeature)
  }

  override def setAdditionalFeatures(
    record: Seq[UserHistory],
    mutableDataRecord: RichDataRecord
  ): Unit = {
    val videoWatchTimes = new GeneralTensor()
    videoWatchTimes.setInt64Tensor(
      new Int64Tensor(
        record.flatMap(_.metadata.flatMap(_.watchTime).map(_.toLong)).map(long2Long).asJava)
    )
    mutableDataRecord.setFeatureValue(
      VideoUserHistoryEventsFeatures.VideoWatchTimesFeature,
      videoWatchTimes
    )
    val videoMediaIds = new GeneralTensor()
    videoMediaIds.setInt64Tensor(
      new Int64Tensor(record.flatMap(_.metadata.flatMap(_.mediaId)).map(long2Long).asJava)
    )
    mutableDataRecord.setFeatureValue(
      VideoUserHistoryEventsFeatures.VideoMediaIdsFeature,
      videoMediaIds
    )

    val videoMediaCategories = new GeneralTensor()
    videoMediaCategories.setInt32Tensor(
      new Int32Tensor(
        record.flatMap(_.metadata).flatMap(_.mediaCategory).map(_.value).map(int2Integer).asJava))
    mutableDataRecord.setFeatureValue(
      VideoUserHistoryEventsFeatures.VideoMediaCategoriesFeature,
      videoMediaCategories
    )

    val videoDurations = new GeneralTensor()
    videoDurations.setInt32Tensor(
      new Int32Tensor(record.flatMap(_.metadata).flatMap(_.videoDuration).map(int2Integer).asJava))
    mutableDataRecord.setFeatureValue(
      VideoUserHistoryEventsFeatures.VideoDurationsFeature,
      videoDurations
    )

    val topEntitiesInfo = new GeneralTensor()
    val topEntitiesAsString: Seq[String] = record.map { userHistory =>
      userHistory.metadata
        .flatMap(_.entities)
        .map(_.filter(_.score.isDefined))
        .filter(_.nonEmpty)
        .map(_.maxBy(_.score.get))
        .headOption
        .map(entity => entity.qualifiedId._1.toString + ":" + entity.qualifiedId._2.toString)
        .getOrElse("")
    }

    topEntitiesInfo.setStringTensor(new StringTensor(topEntitiesAsString.asJava))
    mutableDataRecord.setFeatureValue(
      VideoUserHistoryEventsFeatures.VideoTopEntityFeature,
      topEntitiesInfo
    )

    val grokTagsInfo = new GeneralTensor()
    val grokTagsAsString: Seq[String] = record.map { userHistory =>
      userHistory.metadata
        .flatMap(_.tags)
        .map(_.map(_.tag))
        .getOrElse(Nil)
        .mkString(":")
    }
    grokTagsInfo.setStringTensor(new StringTensor(grokTagsAsString.asJava))

    mutableDataRecord.setFeatureValue(
      VideoUserHistoryEventsFeatures.VideoGrokTagsInfoFeature,
      grokTagsInfo
    )

    val clusterIdsFeature = new GeneralTensor()
    clusterIdsFeature.setInt64Tensor(
      new Int64Tensor(record.flatMap(_.metadata).flatMap(_.clusterId).map(long2Long).asJava))
    mutableDataRecord.setFeatureValue(
      VideoUserHistoryEventsFeatures.VideoCluster95IdsFeature,
      clusterIdsFeature
    )
  }
}
