package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.UserActionsContainsExplicitSignalsFeature
import com.twitter.home_mixer.model.HomeFeatures.UserActionsFeature
import com.twitter.home_mixer.model.HomeFeatures.UserActionsSizeFeature
import com.twitter.home_mixer.param.HomeGlobalParams.UserActionsMaxCount
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.FeatureHydration.EnableDenseUserActionsHydrationParam
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.user_history_transformer.user_actions.UserActionSequenceMhClientColumn
import com.twitter.user_history_transformer.domain.AggregationAlgorithmV1
import com.twitter.user_history_transformer.domain.AggregationAlgorithmWithoutHomeFilter
import com.twitter.user_history_transformer.domain.AggregationConfig
import com.twitter.user_history_transformer.domain.AggregationProcessor
import com.twitter.user_history_transformer.domain.UserActionSequenceUtils
import com.twitter.user_history_transformer.util.SchemaUtils
import com.x.user_action_sequence.thriftscala.ActionName.ClientTweetRecapDwelled
import com.x.user_action_sequence.thriftscala.ActionName.ClientTweetRecapNotDwelled
import com.x.user_action_sequence.thriftscala.ActionName
import com.x.user_action_sequence.thriftscala.AggregatedUserAction
import com.x.user_action_sequence.thriftscala.UserActionSequenceDataContainer.OrderedAggregatedUserActionList
import com.x.user_action_sequence.{thriftscala => t}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserActionsQueryFeatureHydrator @Inject() (
  userActionSequenceMhClientColumn: UserActionSequenceMhClientColumn,
  statsReceiver: StatsReceiver)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("UserActions")

  override val features: Set[Feature[_, _]] = Set(
    UserActionsFeature,
    UserActionsSizeFeature,
    UserActionsContainsExplicitSignalsFeature
  )

  private val userAggregatedActionSeqlengthStat =
    statsReceiver.stat("UserActionsQueryFeatureHydrator", "length")

  private val DefaultFeatureMap = FeatureMapBuilder()
    .add(UserActionsFeature, None)
    .add(UserActionsSizeFeature, None)
    .add(UserActionsContainsExplicitSignalsFeature, false)
    .build()

  private val windowTimeMs = 5 * 60 * 1000

  private val ExcludedDwellActions: Set[ActionName] =
    Set(ClientTweetRecapDwelled, ClientTweetRecapNotDwelled)

  private def filterDwells(
    aggAction: AggregatedUserAction
  ): Boolean = {
    aggAction.actions
      .getOrElse(Seq.empty)
      .exists {
        _.actionName.exists { name =>
          !ExcludedDwellActions.contains(name)
        }
      }
  }

  private val aggregationProcessor = new AggregationProcessor(
    AggregationConfig(
      postProcessorSeq = Seq.empty,
      windowTimeMs = windowTimeMs,
      maxLength = 1024,
      aggregationAlgorithm = AggregationAlgorithmV1,
    )
  )

  private val denseAggregationProcessor = new AggregationProcessor(
    AggregationConfig(
      postProcessorSeq = Seq.empty,
      windowTimeMs = windowTimeMs,
      maxLength = 1024,
      aggregationAlgorithm = AggregationAlgorithmWithoutHomeFilter,
      aggActionFilterFuncGenerator = (_, _) => {
        aggAction => filterDwells(aggAction)
      }
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

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val processor =
      if (query.params(EnableDenseUserActionsHydrationParam)) denseAggregationProcessor
      else aggregationProcessor
    OffloadFuturePools.offloadStitch {
      userActionSequenceMhClientColumn.fetcher.fetch(query.getRequiredUserId).map { response =>
        val featureMap = response.v.map { userActionsSeq =>
          val decompressedUserActionSeq =
            UserActionSequenceUtils.expand(userActionsSeq, statsReceiver)
          val aggregatedUserActions = processor
            .process(decompressedUserActionSeq)
            .filterNot(hasNegativeValues)
            .takeRight(query.params(UserActionsMaxCount))

          val size = aggregatedUserActions.length
          userAggregatedActionSeqlengthStat.add(size)

          val hasExplicitSignals =
            UserActionSequenceUtils.hasExplicitSignals(decompressedUserActionSeq)

          val filteredUserActionSeq = userActionsSeq.copy(
            userActionsData = Some(
              OrderedAggregatedUserActionList(
                t.AggregatedUserActionList(aggregatedUserActions = Some(aggregatedUserActions))
              )
            )
          )

          val actions = SchemaUtils.convertUserActionSequenceThriftToProtobuf(filteredUserActionSeq)

          FeatureMapBuilder()
            .add(UserActionsFeature, Some(actions))
            .add(UserActionsSizeFeature, Some(size))
            .add(UserActionsContainsExplicitSignalsFeature, hasExplicitSignals)
            .build()
        }

        featureMap.getOrElse(DefaultFeatureMap)
      }
    }
  }
}
