package com.X.product_mixer.component_library.feature_hydrator.candidate.tweet_visibility_reason

import com.X.product_mixer.component_library.model.candidate.BaseTweetCandidate
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.X.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.spam.rtf.{thriftscala => SPAM}
import com.X.stitch.Stitch
import com.X.stitch.tweetypie.{TweetyPie => TweetypieStitchClient}
import com.X.tweetypie.{thriftscala => TP}
import com.X.util.Return
import com.X.util.Throw
import com.X.util.Try
import com.X.util.logging.Logging
import javax.inject.Inject
import javax.inject.Singleton

object VisibilityReason
    extends FeatureWithDefaultOnFailure[TweetCandidate, Option[SPAM.FilteredReason]] {
  override val defaultValue = None
}

/**
 * A [[BulkCandidateFeatureHydrator]] that hydrates TweetCandidates with VisibilityReason features
 * by [[SPAM.SafetyLevel]] when present. The [[VisibilityReason]] feature represents a VisibilityFiltering
 * [[SPAM.FilteredReason]], which contains safety filtering verdict information including action (e.g.
 * Drop, Avoid) and reason (e.g. Misinformation, Abuse). This feature can inform downstream services'
 * handling and presentation of Tweets (e.g. ad avoidance).
 *
 * @param tweetypieStitchClient used to retrieve Tweet fields for BaseTweetCandidates
 * @param safetyLevel specifies VisibilityFiltering SafetyLabel
 */

@Singleton
case class TweetVisibilityReasonBulkCandidateFeatureHydrator @Inject() (
  tweetypieStitchClient: TweetypieStitchClient,
  safetyLevel: SPAM.SafetyLevel)
    extends BulkCandidateFeatureHydrator[PipelineQuery, BaseTweetCandidate]
    with Logging {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier(
    "TweetVisibilityReason")

  override def features: Set[Feature[_, _]] = Set(VisibilityReason)

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[BaseTweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = {
    Stitch
      .traverse(candidates.map(_.candidate.id)) { tweetId =>
        tweetypieStitchClient
          .getTweetFields(
            tweetId = tweetId,
            options = TP.GetTweetFieldsOptions(
              forUserId = query.getOptionalUserId,
              tweetIncludes = Set.empty,
              doNotCache = true,
              visibilityPolicy = TP.TweetVisibilityPolicy.UserVisible,
              safetyLevel = Some(safetyLevel)
            )
          ).liftToTry
      }.map { getTweetFieldsResults: Seq[Try[TP.GetTweetFieldsResult]] =>
        val tweetFields: Seq[Try[TP.TweetFieldsResultFound]] = getTweetFieldsResults.map {
          case Return(TP.GetTweetFieldsResult(_, TP.TweetFieldsResultState.Found(found), _, _)) =>
            Return(found)
          case Return(TP.GetTweetFieldsResult(_, resultState, _, _)) =>
            Throw(
              VisibilityReasonFeatureHydrationFailure(
                s"Unexpected tweet result state: ${resultState}"))
          case Throw(e) =>
            Throw(e)
        }

        tweetFields.map { tweetFieldTry =>
          val tweetFilteredReason = tweetFieldTry.map { tweetField =>
            tweetField.suppressReason match {
              case Some(suppressReason) => Some(suppressReason)
              case _ => None
            }
          }

          FeatureMapBuilder()
            .add(VisibilityReason, tweetFilteredReason)
            .build()
        }
      }
  }
}

case class VisibilityReasonFeatureHydrationFailure(message: String)
    extends Exception(s"VisibilityReasonFeatureHydrationFailure($message)")
