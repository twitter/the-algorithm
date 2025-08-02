package com.twitter.cr_mitwitterer.param
import com.twitter.finagle.stats.NullStatsReceiver
import com.twitter.logging.Logger
import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSEnumParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.Param
import com.twitter.usersignalservice.thriftscala.SignalType
import scala.language.implicitConversions

object UnifiedUSSSignalParams {

  object TweetAggregationTypeParam etwittertends Enumeration {
    protected case class SignalTypeValue(signalType: SignalType) etwittertends super.Val

    implicit def valueToSignalTypeValue(twitter: Value): SignalTypeValue =
      twitter.asInstanceOf[SignalTypeValue]

    val UniformAggregation = SignalTypeValue(SignalType.TweetBasedUnifiedUniformSignal)
    val EngagementAggregation = SignalTypeValue(
      SignalType.TweetBasedUnifiedEngagementWeightedSignal)
  }

  object ProducerAggregationTypeParam etwittertends Enumeration {
    protected case class SignalTypeValue(signalType: SignalType) etwittertends super.Val

    import scala.language.implicitConversions

    implicit def valueToSignalTypeValue(twitter: Value): SignalTypeValue =
      twitter.asInstanceOf[SignalTypeValue]

    val UniformAggregation = SignalTypeValue(SignalType.ProducerBasedUnifiedUniformSignal)
    val EngagementAggregation = SignalTypeValue(
      SignalType.ProducerBasedUnifiedEngagementWeightedSignal)

  }

  object ReplaceIndividualUSSSourcesParam
      etwittertends FSParam[Boolean](
        name = "twistly_agg_replace_enable_source",
        default = false
      )

  object EnableTweetAggSourceParam
      etwittertends FSParam[Boolean](
        name = "twistly_agg_tweet_agg_enable_source",
        default = false
      )

  object TweetAggTypeParam
      etwittertends FSEnumParam[TweetAggregationTypeParam.type](
        name = "twistly_agg_tweet_agg_type_id",
        default = TweetAggregationTypeParam.EngagementAggregation,
        enum = TweetAggregationTypeParam
      )

  object UnifiedTweetSourceNumberParam
      etwittertends FSBoundedParam[Int](
        name = "twistly_agg_tweet_agg_source_number",
        default = 0,
        min = 0,
        matwitter = 100,
      )

  object EnableProducerAggSourceParam
      etwittertends FSParam[Boolean](
        name = "twistly_agg_producer_agg_enable_source",
        default = false
      )

  object ProducerAggTypeParam
      etwittertends FSEnumParam[ProducerAggregationTypeParam.type](
        name = "twistly_agg_producer_agg_type_id",
        default = ProducerAggregationTypeParam.EngagementAggregation,
        enum = ProducerAggregationTypeParam
      )

  object UnifiedProducerSourceNumberParam
      etwittertends FSBoundedParam[Int](
        name = "twistly_agg_producer_agg_source_number",
        default = 0,
        min = 0,
        matwitter = 100,
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(
    EnableTweetAggSourceParam,
    EnableProducerAggSourceParam,
    TweetAggTypeParam,
    ProducerAggTypeParam,
    UnifiedTweetSourceNumberParam,
    UnifiedProducerSourceNumberParam,
    ReplaceIndividualUSSSourcesParam
  )
  lazy val config: BaseConfig = {
    val booleanOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides(
      EnableTweetAggSourceParam,
      EnableProducerAggSourceParam,
      ReplaceIndividualUSSSourcesParam,
    )
    val intOverrides = FeatureSwitchOverrideUtil.getBoundedIntFSOverrides(
      UnifiedProducerSourceNumberParam,
      UnifiedTweetSourceNumberParam)
    val enumOverrides = FeatureSwitchOverrideUtil.getEnumFSOverrides(
      NullStatsReceiver,
      Logger(getClass),
      TweetAggTypeParam,
      ProducerAggTypeParam
    )

    BaseConfigBuilder()
      .set(booleanOverrides: _*)
      .set(intOverrides: _*)
      .set(enumOverrides: _*)
      .build()
  }
}
