package com.X.product_mixer.component_library.filter

import com.X.util.logging.Logging
import com.X.product_mixer.component_library.filter.TweetVisibilityFilter._
import com.X.product_mixer.component_library.model.candidate.BaseTweetCandidate
import com.X.product_mixer.core.functional_component.filter.Filter
import com.X.product_mixer.core.functional_component.filter.FilterResult
import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.product_mixer.core.model.common.identifier.FilterIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.spam.rtf.thriftscala.SafetyLevel
import com.X.stitch.Stitch
import com.X.stitch.tweetypie.{TweetyPie => TweetypieStitchClient}
import com.X.tweetypie.{thriftscala => TP}
import com.X.util.Return
import com.X.util.Try

object TweetVisibilityFilter {
  val DefaultTweetIncludes = Set(TP.TweetInclude.TweetFieldId(TP.Tweet.IdField.id))
  private final val getTweetFieldsFailureMessage = "TweetyPie.getTweetFields failed: "
}

case class TweetVisibilityFilter[Candidate <: BaseTweetCandidate](
  tweetypieStitchClient: TweetypieStitchClient,
  tweetVisibilityPolicy: TP.TweetVisibilityPolicy,
  safetyLevel: SafetyLevel,
  tweetIncludes: Set[TP.TweetInclude.TweetFieldId] = DefaultTweetIncludes)
    extends Filter[PipelineQuery, Candidate]
    with Logging {

  override val identifier: FilterIdentifier = FilterIdentifier("TweetVisibility")

  def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): Stitch[FilterResult[Candidate]] = {
    Stitch
      .traverse(candidates.map(_.candidate.id)) { tweetId =>
        tweetypieStitchClient
          .getTweetFields(tweetId, getTweetFieldsOptions(query.getOptionalUserId))
          .liftToTry
      }
      .map { getTweetFieldsResults: Seq[Try[TP.GetTweetFieldsResult]] =>
        val (checkedSucceeded, checkFailed) = getTweetFieldsResults.partition(_.isReturn)
        checkFailed.foreach(e => warn(() => getTweetFieldsFailureMessage, e.throwable))
        if (checkFailed.nonEmpty) {
          warn(() =>
            s"TweetVisibilityFilter dropped ${checkFailed.size} candidates due to tweetypie failure.")
        }

        val allowedTweets = checkedSucceeded.collect {
          case Return(TP.GetTweetFieldsResult(_, TP.TweetFieldsResultState.Found(found), _, _)) =>
            found.tweet.id
        }.toSet

        val (kept, removed) =
          candidates.map(_.candidate).partition(candidate => allowedTweets.contains(candidate.id))

        FilterResult(kept = kept, removed = removed)
      }
  }

  private def getTweetFieldsOptions(userId: Option[Long]) =
    TP.GetTweetFieldsOptions(
      forUserId = userId,
      tweetIncludes = tweetIncludes.toSet,
      doNotCache = true,
      visibilityPolicy = tweetVisibilityPolicy,
      safetyLevel = Some(safetyLevel)
    )
}
