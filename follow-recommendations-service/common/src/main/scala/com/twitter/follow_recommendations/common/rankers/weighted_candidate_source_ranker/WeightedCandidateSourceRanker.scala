package com.twitter.follow_recommendations.common.rankers.weighted_candidate_source_ranker

import com.twitter.follow_recommendations.common.base.Ranker
import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.follow_recommendations.common.rankers.common.DedupCandidates
import com.twitter.follow_recommendations.common.rankers.utils.Utils
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.HasParams

/**
 * Candidate Ranker that mixes and ranks multiple candidate lists from different candidate sources with the
 * following steps:
 *  1) generate a ranked candidate list of each candidate source by sorting and shuffling the candidate list
 *     of the algorithm.
 *  2) merge the ranked lists generated in 1) into a single list using weighted randomly sampling.
 *  3) If dedup is required, dedup the output from 2) by candidate id.
 *
 * @param basedRanker base ranker
 * @param shuffleFn the shuffle function that will be used to shuffle each algorithm's sorted candidate list.
 * @param dedup whether to remove duplicated candidates from the final output.
 */


class WeightedCandidateSourceRanker(
  basedRanker: WeightedCandidateSourceBaseRanker[CandidateSourceIdentifier, CandidateUser],
  shuffleFn: Seq[CandidateUser] => Seq[CandidateUser],
  dedup: Boolean
) extends Ranker[Target, CandidateUser] {

  override def rank(target: Target, candidates: Seq[CandidateUser]): Stitch[Seq[CandidateUser]] = {
    val scribeRankingInfo = target.params(WeightedCandidateSourceRankerParams.ScribeRankingInfoInWeightedRanker)
    val rankedCandidates = rankCandidates(group(candidates))
    Stitch.value(if (scribeRankingInfo) Utils.addRankingInfo(rankedCandidates, name) else rankedCandidates)
  }

  private def group(candidates: Seq[CandidateUser]): Map[CandidateSourceIdentifier, Seq[CandidateUser]] = {
    candidates.flatMap(_.getPrimaryCandidateSource.map(identifier => (identifier, Seq(_)))).toMap
  }

  private def rankCandidates(input: Map[CandidateSourceIdentifier, Seq[CandidateUser]]): Seq[CandidateUser] = {
    val sortAndShuffledCandidates = input.mapValues(shuffleFn compose (_.toList)).toSeq
    val rankedCandidates = basedRanker(sortAndShuffledCandidates)
    if (dedup) DedupCandidates(rankedCandidates) else rankedCandidates
  }

  val name: String = getClass.getSimpleName
}

object WeightedCandidateSourceRanker {
  def build(
    candidateSourceWeight: Map[CandidateSourceIdentifier, Double],
    shuffleFn: Seq[CandidateUser] => Seq[CandidateUser] = identity,
    dedup: Boolean = false,
    randomSeed: Option[Long] = None
  ): WeightedCandidateSourceRanker = {
    new WeightedCandidateSourceRanker(
      new WeightedCandidateSourceBaseRanker(candidateSourceWeight, WeightMethod.WeightedRandomSampling, randomSeed),
      shuffleFn,
      dedup
    )
  }
}

object WeightedCandidateSourceRankerWithoutRandomSampling {
  def build(candidateSourceWeight: Map[CandidateSourceIdentifier, Double]): WeightedCandidateSourceRanker = {
    new WeightedCandidateSourceRanker(
      new WeightedCandidateSourceBaseRanker(candidateSourceWeight, WeightMethod.WeightedRoundRobin, None),
      identity,
      false
    )
  }
}
