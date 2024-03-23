package com.ExTwitter.home_mixer.module

import com.google.inject.Provides
import com.ExTwitter.clientapp.{thriftscala => ca}
import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.CandidateFeaturesScribeEventPublisher
import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.CommonFeaturesScribeEventPublisher
import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.MinimumFeaturesScribeEventPublisher
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.logpipeline.client.EventPublisherManager
import com.ExTwitter.logpipeline.client.common.EventPublisher
import com.ExTwitter.logpipeline.client.serializers.EventLogMsgTBinarySerializer
import com.ExTwitter.logpipeline.client.serializers.EventLogMsgThriftStructSerializer
import com.ExTwitter.timelines.suggests.common.poly_data_record.{thriftjava => pldr}
import com.ExTwitter.timelines.timeline_logging.{thriftscala => tl}
import javax.inject.Named
import javax.inject.Singleton

object ScribeEventPublisherModule extends ExTwitterModule {

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
