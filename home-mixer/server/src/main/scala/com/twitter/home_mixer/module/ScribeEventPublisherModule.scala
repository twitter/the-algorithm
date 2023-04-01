package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.twitter.clientapp.{thriftscala => ca}
import com.twitter.home_mixer.param.HomeMixerFlagName.ScribeClientEventsFlag
import com.twitter.home_mixer.param.HomeMixerFlagName.ScribeServedCommonFeaturesAndCandidateFeaturesFlag
import com.twitter.home_mixer.param.HomeMixerFlagName.ScribeServedEntriesFlag
import com.twitter.home_mixer.param.HomeMixerInjectionNames.CandidateFeaturesScribeEventPublisher
import com.twitter.home_mixer.param.HomeMixerInjectionNames.CommonFeaturesScribeEventPublisher
import com.twitter.home_mixer.param.HomeMixerInjectionNames.MinimumFeaturesScribeEventPublisher
import com.twitter.inject.TwitterModule
import com.twitter.inject.annotations.Flag
import com.twitter.logpipeline.client.EventPublisherManager
import com.twitter.logpipeline.client.common.EventPublisher
import com.twitter.logpipeline.client.serializers.EventLogMsgTBinarySerializer
import com.twitter.logpipeline.client.serializers.EventLogMsgThriftStructSerializer
import com.twitter.timelines.suggests.common.poly_data_record.{thriftjava => pldr}
import com.twitter.timelines.timeline_logging.{thriftscala => tl}

import javax.inject.Named
import javax.inject.Singleton

object ScribeEventPublisherModule extends TwitterModule {

  val InMemoryBufferSize = 10000
  val ClientEventLogCategory = "client_event"
  val ServedEntriesLogCategory = "home_timeline_served_entries"
  val ServedCommonFeaturesLogCategory = "tq_served_common_features_offline"
  val ServedCandidateFeaturesLogCategory = "tq_served_candidate_features_offline"
  val ServedMinimumFeaturesLogCategory = "tq_served_minimum_features_offline"

  @Provides
  @Singleton
  def providesClientEventsScribeEventPublisher(
    @Flag(ScribeClientEventsFlag) sendToScribe: Boolean
  ): EventPublisher[ca.LogEvent] = {
    val serializer = EventLogMsgThriftStructSerializer.getNewSerializer[ca.LogEvent]()

    if (sendToScribe)
      EventPublisherManager.buildScribeLogPipelinePublisher(ClientEventLogCategory, serializer)
    else
      EventPublisherManager.buildInMemoryPublisher(
        ClientEventLogCategory,
        serializer,
        InMemoryBufferSize
      )
  }

  @Provides
  @Singleton
  @Named(CommonFeaturesScribeEventPublisher)
  def providesCommonFeaturesScribeEventPublisher(
    @Flag(ScribeServedCommonFeaturesAndCandidateFeaturesFlag) sendToScribe: Boolean
  ): EventPublisher[pldr.PolyDataRecord] = {
    val serializer = EventLogMsgTBinarySerializer.getNewSerializer

    if (sendToScribe)
      EventPublisherManager.buildScribeLogPipelinePublisher(
        ServedCommonFeaturesLogCategory,
        serializer)
    else
      EventPublisherManager.buildInMemoryPublisher(
        ServedCommonFeaturesLogCategory,
        serializer,
        InMemoryBufferSize
      )
  }

  @Provides
  @Singleton
  @Named(CandidateFeaturesScribeEventPublisher)
  def providesCandidateFeaturesScribeEventPublisher(
    @Flag(ScribeServedCommonFeaturesAndCandidateFeaturesFlag) sendToScribe: Boolean
  ): EventPublisher[pldr.PolyDataRecord] = {
    val serializer = EventLogMsgTBinarySerializer.getNewSerializer

    if (sendToScribe)
      EventPublisherManager.buildScribeLogPipelinePublisher(
        ServedCandidateFeaturesLogCategory,
        serializer)
    else
      EventPublisherManager.buildInMemoryPublisher(
        ServedCandidateFeaturesLogCategory,
        serializer,
        InMemoryBufferSize
      )
  }

  @Provides
  @Singleton
  @Named(MinimumFeaturesScribeEventPublisher)
  def providesMinimumFeaturesScribeEventPublisher(
    @Flag(ScribeServedCommonFeaturesAndCandidateFeaturesFlag) sendToScribe: Boolean
  ): EventPublisher[pldr.PolyDataRecord] = {
    val serializer = EventLogMsgTBinarySerializer.getNewSerializer

    if (sendToScribe)
      EventPublisherManager.buildScribeLogPipelinePublisher(
        ServedMinimumFeaturesLogCategory,
        serializer)
    else
      EventPublisherManager.buildInMemoryPublisher(
        ServedMinimumFeaturesLogCategory,
        serializer,
        InMemoryBufferSize
      )
  }

  @Provides
  @Singleton
  def providesServedEntriesScribeEventPublisher(
    @Flag(ScribeServedEntriesFlag) sendToScribe: Boolean
  ): EventPublisher[tl.Timeline] = {
    val serializer = EventLogMsgThriftStructSerializer.getNewSerializer[tl.Timeline]()

    if (sendToScribe)
      EventPublisherManager.buildScribeLogPipelinePublisher(ServedEntriesLogCategory, serializer)
    else
      EventPublisherManager.buildInMemoryPublisher(
        ServedEntriesLogCategory,
        serializer,
        InMemoryBufferSize
      )
  }
}
