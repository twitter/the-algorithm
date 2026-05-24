package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.UserActionsByteArrayFeature
import com.twitter.home_mixer.param.HomeGlobalParams.UserActionsMaxCount
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.user_history_transformer.user_actions.UserActionSequenceMhClientColumn
import com.twitter.user_history_transformer.domain.AggregationAlgorithmV1
import com.twitter.user_history_transformer.domain.AggregationConfig
import com.twitter.user_history_transformer.domain.AggregationProcessor
import com.twitter.user_history_transformer.domain.UserActionSequenceUtils
import com.twitter.user_history_transformer.util.SchemaUtils
import com.x.user_action_sequence.thriftscala.UserActionSequenceDataContainer.OrderedAggregatedUserActionList
import com.x.user_action_sequence.{thriftscala => t}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserActionsArrayByteQueryFeatureHydrator @Inject() (
  userActionSequenceMhClientColumn: UserActionSequenceMhClientColumn,
  statsReceiver: StatsReceiver)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("UserActionsByteArray")

  override val features: Set[Feature[_, _]] = Set(UserActionsByteArrayFeature)

  private val DefaultFeatureMap = FeatureMap(UserActionsByteArrayFeature, None)

  private val windowTimeMs = 5 * 60 * 1000

  private val aggregationProcessor = new AggregationProcessor(
    AggregationConfig(
      postProcessorSeq = Seq.empty,
      windowTimeMs = windowTimeMs,
      maxLength = 1024,
      aggregationAlgorithm = AggregationAlgorithmV1
    )
  )

  private def hasNegativeValue(value: Option[Long]): Boolean = value.exists(_ < 0)

  private def hasNegativeValues(aggregatedUserAction: t.AggregatedUserAction): Boolean = {
    if (hasNegativeValue(aggregatedUserAction.userId)) return true

    aggregatedUserAction.tweetInfo.exists { tweetInfo =>
      val fieldsToCheck = List(
        tweetInfo.tweetId,
        tweetInfo.authorId,
        tweetInfo.retweetingTweetId,
        tweetInfo.quotingTweetId,
        tweetInfo.replyingTweetId,
        tweetInfo.quotedTweetId,
        tweetInfo.inReplyToTweetId,
        tweetInfo.retweetedTweetId,
        tweetInfo.editedTweetId
      )

      fieldsToCheck.exists(hasNegativeValue)
    }
  }

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] =
    OffloadFuturePools.offloadStitch {
      userActionSequenceMhClientColumn.fetcher.fetch(query.getRequiredUserId).map { response =>
        val featureMap = response.v.map { userActionsSeq =>
          val decompressedUserActionSeq =
            UserActionSequenceUtils.expand(userActionsSeq, statsReceiver)
          val downsampledUserActionSeq =
            UserActionSequenceUtils.mhToKafka(decompressedUserActionSeq)
          val aggregatedUserActions = aggregationProcessor
            .process(downsampledUserActionSeq)
            .filterNot(hasNegativeValues)
            .takeRight(query.params(UserActionsMaxCount))

          val filteredUserActionSeq = userActionsSeq.copy(
            userActionsData = Some(
              OrderedAggregatedUserActionList(
                t.AggregatedUserActionList(aggregatedUserActions = Some(aggregatedUserActions))
              )
            )
          )

          val actions = SchemaUtils.convertUserActionSequenceThriftToProtobuf(filteredUserActionSeq)

          FeatureMap(UserActionsByteArrayFeature, Some(actions.toByteArray))
        }

        featureMap.getOrElse(DefaultFeatureMap)
      }
    }
}
