package com.twitter.home_mixer.functional_component.selector

import com.twitter.home_mixer.model.HomeFeatures.IsBoostedCandidateFeature
import com.twitter.home_mixer.model.HomeFeatures.ScoreFeature
import com.twitter.home_mixer.model.HomeFeatures.ServedTypeFeature
import com.twitter.home_mixer.model.HomeFeatures.UserFollowersCountFeature
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.CategoryColdStartProbabilisticReturnParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.CategoryColdStartTierOneProbabilityParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.ContentExplorationBoostPosParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.ContentExplorationViewerMaxFollowersParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.DeepRetrievalBoostPosParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.DeepRetrievalI2iProbabilityParam
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.common.AllPipelines
import com.twitter.product_mixer.core.functional_component.common.CandidateScope
import com.twitter.product_mixer.core.functional_component.selector.Selector
import com.twitter.product_mixer.core.functional_component.selector.SelectorResult
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.model.common.presentation.ItemCandidateWithDetails
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import scala.util.Random

/**
 * Abstract base class specifically for fixed position selectors that need score averaging
 * Contains complete selector logic, supports removing target candidates from remaining candidates
 * and assigning them the average score of adjacent candidates
 */
abstract class ScoreAveragingPositionSelector extends Selector[PipelineQuery] {

  // Abstract methods that subclasses need to implement
  def getOffset(query: PipelineQuery): Int
  def getNumCandidatesToBoost(query: PipelineQuery): Int
  def selectEligibleCandidates(query: PipelineQuery, candidate: CandidateWithDetails): Boolean

  override def apply(
    query: PipelineQuery,
    remainingCandidates: Seq[CandidateWithDetails],
    result: Seq[CandidateWithDetails]
  ): SelectorResult = {
    val eligibleCandidates = remainingCandidates.filter(c => selectEligibleCandidates(query, c))
    val targetCandidates = eligibleCandidates.take(getNumCandidatesToBoost(query))

    if (targetCandidates.nonEmpty) {
      val filteredRemainingCandidates = remainingCandidates.diff(targetCandidates)
      val offset = getOffset(query)

      // Get scores from adjacent positions for average calculation
      val adjacentScores = getAdjacentScores(filteredRemainingCandidates, offset)

      // Assign target candidates scores based on the average of adjacent candidates
      val scoredTargetCandidates =
        targetCandidates.map(candidate => assignAverageScore(candidate, adjacentScores))

      val updatedRemainingCandidates =
        if (offset >= 0 && offset <= filteredRemainingCandidates.length) {
          filteredRemainingCandidates.slice(0, offset) ++ scoredTargetCandidates ++
            filteredRemainingCandidates.slice(offset, filteredRemainingCandidates.length)
        } else {
          filteredRemainingCandidates ++ scoredTargetCandidates
        }

      SelectorResult(remainingCandidates = updatedRemainingCandidates, result = result)
    } else {
      SelectorResult(remainingCandidates = remainingCandidates, result = result)
    }
  }

  // Get adjacent position scores
  def getAdjacentScores(
    filteredRemainingCandidates: Seq[CandidateWithDetails],
    offset: Int
  ): Seq[Option[Double]] = {
    if (offset >= 1 && offset < filteredRemainingCandidates.length) {
      Seq(
        filteredRemainingCandidates(offset - 1).features.getOrElse(ScoreFeature, None),
        filteredRemainingCandidates(offset).features.getOrElse(ScoreFeature, None)
      )
    } else if (offset == 0 && filteredRemainingCandidates.nonEmpty) {
      Seq(filteredRemainingCandidates.head.features.getOrElse(ScoreFeature, None))
    } else if (offset >= filteredRemainingCandidates.length && filteredRemainingCandidates.nonEmpty) {
      Seq(filteredRemainingCandidates.last.features.getOrElse(ScoreFeature, None))
    } else {
      Seq.empty
    }
  }

  // Assign scores to candidates based on the average score of adjacent candidates
  def assignAverageScore(
    candidate: CandidateWithDetails,
    adjacentScores: Seq[Option[Double]]
  ): CandidateWithDetails = {
    val validScores = adjacentScores.flatten
    val avgScore = if (validScores.nonEmpty) {
      Some(validScores.sum / validScores.size)
    } else None

    candidate match {
      case item: ItemCandidateWithDetails if avgScore.isDefined =>
        val updatedFeatures = FeatureMapBuilder()
          .add(ScoreFeature, avgScore)
          .add(IsBoostedCandidateFeature, true)
          .build()
        item.copy(features = item.features ++ updatedFeatures)
      case _ => candidate
    }
  }

  def pipelineScope: CandidateScope = AllPipelines
}

/**
 * Concrete implementations of score averaging position selectors
 */
object SortFixedPositionContentExplorationSimclusterColdPostsCandidates
    extends ScoreAveragingPositionSelector {

  override def getOffset(query: PipelineQuery): Int =
    query.params.getInt(ContentExplorationBoostPosParam)

  override def getNumCandidatesToBoost(query: PipelineQuery): Int = 1

  override def selectEligibleCandidates(
    query: PipelineQuery,
    candidate: CandidateWithDetails
  ): Boolean = {
    val servedType = candidate.features.getOrElse(ServedTypeFeature, hmt.ServedType.Undefined)
    servedType == hmt.ServedType.ForYouContentExplorationSimclusterColdPosts
  }
}

object SortFixedPositionContentExplorationMixedCandidates extends ScoreAveragingPositionSelector {

  override def getOffset(query: PipelineQuery): Int =
    query.params.getInt(ContentExplorationBoostPosParam)

  override def getNumCandidatesToBoost(query: PipelineQuery): Int = 1

  override def selectEligibleCandidates(
    query: PipelineQuery,
    candidate: CandidateWithDetails
  ): Boolean = true

  override def apply(
    query: PipelineQuery,
    remainingCandidates: Seq[CandidateWithDetails],
    result: Seq[CandidateWithDetails]
  ): SelectorResult = {
    val chosenServedType =
      if (Random.nextDouble() < query.params.getDouble(CategoryColdStartTierOneProbabilityParam))
        hmt.ServedType.ForYouContentExploration
      else hmt.ServedType.ForYouContentExplorationTier2

    val eligibleCandidates = remainingCandidates
      .filter(_.features.getOrElse(ServedTypeFeature, hmt.ServedType.Undefined) == chosenServedType)

    val viewerMaxFollowers = query.params(ContentExplorationViewerMaxFollowersParam)
    val viewerFollowers = query.features.flatMap(_.getOrElse(UserFollowersCountFeature, None))

    val returnCandidatesRandomDraw =
      Random.nextDouble() < query.params.getDouble(CategoryColdStartProbabilisticReturnParam)

    if (viewerFollowers.forall(_ < viewerMaxFollowers) &&
      eligibleCandidates.nonEmpty && returnCandidatesRandomDraw) {
      val targetCandidates = eligibleCandidates.take(getNumCandidatesToBoost(query))

      val filteredRemainingCandidates = remainingCandidates.diff(targetCandidates)
      val offset = getOffset(query)

      // Get scores from adjacent positions for average calculation
      val adjacentScores = getAdjacentScores(filteredRemainingCandidates, offset)

      // Assign target candidates scores based on the average of adjacent candidates
      val scoredTargetCandidates =
        targetCandidates.map(candidate => assignAverageScore(candidate, adjacentScores))

      val updatedRemainingCandidates =
        if (offset >= 0 && offset <= filteredRemainingCandidates.length) {
          filteredRemainingCandidates.slice(0, offset) ++ scoredTargetCandidates ++
            filteredRemainingCandidates.slice(offset, filteredRemainingCandidates.length)
        } else filteredRemainingCandidates ++ scoredTargetCandidates

      SelectorResult(remainingCandidates = updatedRemainingCandidates, result = result)
    } else SelectorResult(remainingCandidates = remainingCandidates, result = result)
  }
}

object SortFixedPositionDeepRetrievalMixedCandidates extends ScoreAveragingPositionSelector {

  override def getOffset(query: PipelineQuery): Int =
    query.params.getInt(DeepRetrievalBoostPosParam)

  override def getNumCandidatesToBoost(query: PipelineQuery): Int = 1

  override def selectEligibleCandidates(
    query: PipelineQuery,
    candidate: CandidateWithDetails
  ): Boolean = true

  override def apply(
    query: PipelineQuery,
    remainingCandidates: Seq[CandidateWithDetails],
    result: Seq[CandidateWithDetails]
  ): SelectorResult = {
    val chosenServedType =
      if (Random.nextDouble() < query.params.getDouble(DeepRetrievalI2iProbabilityParam))
        hmt.ServedType.ForYouContentExplorationDeepRetrievalI2i
      else hmt.ServedType.ForYouContentExplorationTier2DeepRetrievalI2i

    val eligibleCandidates = remainingCandidates
      .filter(_.features.getOrElse(ServedTypeFeature, hmt.ServedType.Undefined) == chosenServedType)

    val viewerMaxFollowers = query.params(ContentExplorationViewerMaxFollowersParam)
    val viewerFollowers = query.features.flatMap(_.getOrElse(UserFollowersCountFeature, None))

    if (viewerFollowers.forall(_ < viewerMaxFollowers) && eligibleCandidates.nonEmpty) {
      val targetCandidates = Seq(eligibleCandidates(Random.nextInt(eligibleCandidates.size)))

      val filteredRemainingCandidates = remainingCandidates.diff(targetCandidates)
      val offset = getOffset(query)

      // Get scores from adjacent positions for average calculation
      val adjacentScores = getAdjacentScores(filteredRemainingCandidates, offset)

      // Assign target candidates scores based on the average of adjacent candidates
      val scoredTargetCandidates =
        targetCandidates.map(candidate => assignAverageScore(candidate, adjacentScores))

      val updatedRemainingCandidates =
        if (offset >= 0 && offset <= filteredRemainingCandidates.length) {
          filteredRemainingCandidates.slice(0, offset) ++ scoredTargetCandidates ++
            filteredRemainingCandidates.slice(offset, filteredRemainingCandidates.length)
        } else filteredRemainingCandidates ++ scoredTargetCandidates

      SelectorResult(remainingCandidates = updatedRemainingCandidates, result = result)
    } else SelectorResult(remainingCandidates = remainingCandidates, result = result)
  }
}
