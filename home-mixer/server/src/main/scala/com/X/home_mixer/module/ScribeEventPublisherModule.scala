package com.X.home_mixer.module

import com.google.inject.Provides
import com.X.clientapp.{thriftscala => ca}
import com.X.home_mixer.param.HomeMixerInjectionNames.CandidateFeaturesScribeEventPublisher
import com.X.home_mixer.param.HomeMixerInjectionNames.CommonFeaturesScribeEventPublisher
import com.X.home_mixer.param.HomeMixerInjectionNames.MinimumFeaturesScribeEventPublisher
import com.X.inject.XModule
import com.X.logpipeline.client.EventPublisherManager
import com.X.logpipeline.client.common.EventPublisher
import com.X.logpipeline.client.serializers.EventLogMsgTBinarySerializer
import com.X.logpipeline.client.serializers.EventLogMsgThriftStructSerializer
import com.X.timelines.suggests.common.poly_data_record.{thriftjava => pldr}
import com.X.timelines.timeline_logging.{thriftscala => tl}
import javax.inject.Named
import javax.inject.Singleton

object ScribeEventPublisherModule extends XModule {

  val ClientEventLogCategory = "client_event"
  val ServedCandidatesLogCategory = "home_timeline_served_candidates_flattened"
  val ScoredCandidatesLogCategory = "home_timeline_scored_candidates"
  val ServedCommonFeaturesLogCategory = "tq_served_common_features_offline"
  val ServedCandidateFeaturesLogCategory = "tq_served_candidate_features_offline"
  val ServedMinimumFeaturesLogCategory = "tq_served_minimum_features_offline"

  @Provides
  @Singleton
  def providesClientEventsScribeEventPublisher: EventPublisher[ca.LogEvent] = {
    val serializer = EventLogMsgThriftStructSerializer.getNewSerializer[ca.LogEvent]()
    EventPublisherManager.buildScribeLogPipelinePublisher(ClientEventLogCategory, serializer)
  }

  @Provides
  @Singleton
  @Named(CommonFeaturesScribeEventPublisher)
  def providesCommonFeaturesScribeEventPublisher: EventPublisher[pldr.PolyDataRecord] = {
    val serializer = EventLogMsgTBinarySerializer.getNewSerializer
    EventPublisherManager.buildScribeLogPipelinePublisher(
      ServedCommonFeaturesLogCategory,
      serializer)
  }

  @Provides
  @Singleton
  @Named(CandidateFeaturesScribeEventPublisher)
  def providesCandidateFeaturesScribeEventPublisher: EventPublisher[pldr.PolyDataRecord] = {
    val serializer = EventLogMsgTBinarySerializer.getNewSerializer
    EventPublisherManager.buildScribeLogPipelinePublisher(
      ServedCandidateFeaturesLogCategory,
      serializer)
  }

  @Provides
  @Singleton
  @Named(MinimumFeaturesScribeEventPublisher)
  def providesMinimumFeaturesScribeEventPublisher: EventPublisher[pldr.PolyDataRecord] = {
    val serializer = EventLogMsgTBinarySerializer.getNewSerializer
    EventPublisherManager.buildScribeLogPipelinePublisher(
      ServedMinimumFeaturesLogCategory,
      serializer)
  }

  @Provides
  @Singleton
  def providesServedCandidatesScribeEventPublisher: EventPublisher[tl.ServedEntry] = {
    val serializer = EventLogMsgThriftStructSerializer.getNewSerializer[tl.ServedEntry]()
    EventPublisherManager.buildScribeLogPipelinePublisher(ServedCandidatesLogCategory, serializer)
  }

  @Provides
  @Singleton
  def provideScoredCandidatesScribeEventPublisher: EventPublisher[tl.ScoredCandidate] = {
    val serializer = EventLogMsgThriftStructSerializer.getNewSerializer[tl.ScoredCandidate]()
    EventPublisherManager.buildScribeLogPipelinePublisher(ScoredCandidatesLogCategory, serializer)
  }
}
