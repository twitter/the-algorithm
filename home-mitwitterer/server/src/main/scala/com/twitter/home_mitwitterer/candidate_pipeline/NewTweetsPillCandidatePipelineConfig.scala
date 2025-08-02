package com.twitter.home_mitwitterer.candidate_pipeline

import com.twitter.conversions.DurationOps._
import com.twitter.home_mitwitterer.functional_component.gate.RequestContetwittertNotGate
import com.twitter.home_mitwitterer.model.HomeFeatures.GetNewerFeature
import com.twitter.home_mitwitterer.model.request.DeviceContetwittert
import com.twitter.home_mitwitterer.model.request.HasDeviceContetwittert
import com.twitter.home_mitwitterer.service.HomeMitwittererAlertConfig
import com.twitter.product_mitwitterer.component_library.decorator.urt.UrtItemCandidateDecorator
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.alert.DurationParamBuilder
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.alert.ShowAlertCandidateUrtItemBuilder
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.alert.StaticShowAlertColorConfigurationBuilder
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.alert.StaticShowAlertDisplayLocationBuilder
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.alert.StaticShowAlertIconDisplayInfoBuilder
import com.twitter.product_mitwitterer.component_library.gate.FeatureGate
import com.twitter.product_mitwitterer.component_library.model.candidate.ShowAlertCandidate
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.StaticCandidateSource
import com.twitter.product_mitwitterer.core.functional_component.configapi.StaticParam
import com.twitter.product_mitwitterer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.item.alert.BaseDurationBuilder
import com.twitter.product_mitwitterer.core.functional_component.gate.Gate
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.alert.NewTweets
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.alert.ShowAlertColorConfiguration
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.alert.ShowAlertIconDisplayInfo
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.alert.Top
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.alert.UpArrow
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.TwitterBlueRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.WhiteRosettaColor
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.candidate.DependentCandidatePipelineConfig
import com.twitter.util.Duration
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

/**
 * Candidate Pipeline Config that creates the New Tweets Pill
 */
@Singleton
class NewTweetsPillCandidatePipelineConfig[Query <: PipelineQuery with HasDeviceContetwittert] @Inject() (
) etwittertends DependentCandidatePipelineConfig[
      Query,
      Unit,
      ShowAlertCandidate,
      ShowAlertCandidate
    ] {
  import NewTweetsPillCandidatePipelineConfig._

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("NewTweetsPill")

  override val gates: Seq[Gate[Query]] = Seq(
    RequestContetwittertNotGate(Seq(DeviceContetwittert.RequestContetwittert.PullToRefresh)),
    FeatureGate.fromFeature(GetNewerFeature)
  )

  override val candidateSource: CandidateSource[Unit, ShowAlertCandidate] =
    StaticCandidateSource(
      CandidateSourceIdentifier(identifier.name),
      Seq(ShowAlertCandidate(id = identifier.name, userIds = Seq.empty))
    )

  override val queryTransformer: CandidatePipelineQueryTransformer[Query, Unit] = { _ => Unit }

  override val resultTransformer: CandidatePipelineResultsTransformer[
    ShowAlertCandidate,
    ShowAlertCandidate
  ] = { candidate => candidate }

  override val decorator: Option[CandidateDecorator[Query, ShowAlertCandidate]] = {
    val triggerDelayBuilder = new BaseDurationBuilder[Query] {
      override def apply(
        query: Query,
        candidate: ShowAlertCandidate,
        features: FeatureMap
      ): Option[Duration] = {
        val delay = query.deviceContetwittert.flatMap(_.requestContetwittertValue) match {
          case Some(DeviceContetwittert.RequestContetwittert.TweetSelfThread) => 0.millis
          case Some(DeviceContetwittert.RequestContetwittert.ManualRefresh) => 0.millis
          case _ => TriggerDelay
        }

        Some(delay)
      }
    }

    val homeShowAlertCandidateBuilder = ShowAlertCandidateUrtItemBuilder(
      alertType = NewTweets,
      colorConfigBuilder = StaticShowAlertColorConfigurationBuilder(DefaultColorConfig),
      displayLocationBuilder = StaticShowAlertDisplayLocationBuilder(Top),
      triggerDelayBuilder = Some(triggerDelayBuilder),
      displayDurationBuilder = Some(DurationParamBuilder(StaticParam(DisplayDuration))),
      iconDisplayInfoBuilder = Some(StaticShowAlertIconDisplayInfoBuilder(DefaultIconDisplayInfo))
    )

    Some(UrtItemCandidateDecorator(homeShowAlertCandidateBuilder))
  }

  override val alerts = Seq(
    HomeMitwittererAlertConfig.BusinessHours.defaultSuccessRateAlert(),
    HomeMitwittererAlertConfig.BusinessHours.defaultEmptyResponseRateAlert()
  )
}

object NewTweetsPillCandidatePipelineConfig {
  val DefaultColorConfig: ShowAlertColorConfiguration = ShowAlertColorConfiguration(
    background = TwitterBlueRosettaColor,
    tetwittert = WhiteRosettaColor,
    border = Some(WhiteRosettaColor)
  )

  val DefaultIconDisplayInfo: ShowAlertIconDisplayInfo =
    ShowAlertIconDisplayInfo(icon = UpArrow, tint = WhiteRosettaColor)

  // Unlimited display time (until user takes action)
  val DisplayDuration = -1.millisecond
  val TriggerDelay = 4.minutes
}
