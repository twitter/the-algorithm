package com.twitter.home_mixer.functional_component.feature_hydrator.user_history

import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.transformer_embeddings.BaseUserHistoryEventsAdapter
import com.twitter.ml.api.DataRecord
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.client.Fetcher
import com.twitter.user_history_transformer.thriftscala.UnhydratedUserHistory
import com.twitter.user_history_transformer.thriftscala.UserHistory
import com.twitter.user_history_transformer.util.UserHistoryCompressionUtils
import scala.collection.JavaConverters._

object UserHistoryEventsFeature
    extends DataRecordInAFeature[PipelineQuery]
    with FeatureWithDefaultOnFailure[PipelineQuery, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

trait BaseUserHistoryEventsQueryFeatureHydrator extends QueryFeatureHydrator[PipelineQuery] {
  private val DefaultDataRecord = new DataRecord()

  override def features: Set[Feature[_, _]] = Set(UserHistoryEventsFeature)

  def historyFetcher: Fetcher[Long, Unit, UnhydratedUserHistory]
  def filterEvents(query: PipelineQuery, historyEvents: Seq[UserHistory]): Stitch[Seq[UserHistory]]
  def adapter: BaseUserHistoryEventsAdapter

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    historyFetcher.fetch(query.getRequiredUserId).flatMap { result =>
      result.v match {
        case Some(unhydratedUserHistory: UnhydratedUserHistory) =>
          val expandedValue: UnhydratedUserHistory =
            UserHistoryCompressionUtils.expand(value = unhydratedUserHistory)
          filterEvents(query, expandedValue.events)
            .map { filteredEvents: Seq[UserHistory] =>
              val record =
                if (filteredEvents.nonEmpty)
                  adapter
                    .adaptToDataRecords(filteredEvents)
                    .asScala
                    .headOption
                    .getOrElse(DefaultDataRecord)
                else
                  DefaultDataRecord
              FeatureMap(UserHistoryEventsFeature, record)
            }
        case None =>
          Stitch.value(FeatureMap(UserHistoryEventsFeature, DefaultDataRecord))
      }
    }
  }
}
