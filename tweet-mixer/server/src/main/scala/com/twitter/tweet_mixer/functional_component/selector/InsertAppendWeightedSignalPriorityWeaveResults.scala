package com.twitter.product_mixer.component_library.selector

import com.twitter.product_mixer.core.functional_component.common.CandidateScope
import com.twitter.product_mixer.core.functional_component.common.SpecificPipeline
import com.twitter.product_mixer.core.functional_component.common.SpecificPipelines
import com.twitter.product_mixer.core.functional_component.selector.Selector
import com.twitter.product_mixer.core.functional_component.selector.SelectorResult
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.usersignalservice.thriftscala.SignalType
import scala.annotation.tailrec
import scala.collection.mutable
import scala.util.Random

object InsertAppendWeightedSignalPriorityWeaveResults {
  def apply[Query <: PipelineQuery, Bucket](
    candidatePipelines: Set[CandidatePipelineIdentifier],
    bucketer: Bucketer[Bucket],
    weights: Bucket => (Option[SignalType], Double),
    random: Random
  ): InsertAppendWeightedSignalPriorityWeaveResults[Query, Bucket] =
    new InsertAppendWeightedSignalPriorityWeaveResults(
      SpecificPipelines(candidatePipelines),
      bucketer,
      weights,
      random
    )

  def apply[Query <: PipelineQuery, Bucket](
    candidatePipeline: CandidatePipelineIdentifier,
    bucketer: Bucketer[Bucket],
    weights: Bucket => (Option[SignalType], Double),
    random: Random
  ): InsertAppendWeightedSignalPriorityWeaveResults[Query, Bucket] =
    new InsertAppendWeightedSignalPriorityWeaveResults(
      SpecificPipeline(candidatePipeline),
      bucketer,
      weights,
      random
    )
}

case class InsertAppendWeightedSignalPriorityWeaveResults[-Query <: PipelineQuery, Bucket](
  override val pipelineScope: CandidateScope,
  bucketer: Bucketer[Bucket],
  weights: Bucket => (Option[SignalType], Double),
  random: Random)
    extends Selector[Query] {
  private sealed trait PatternResult
  private case object NotASelectedCandidatePipeline extends PatternResult
  private case class Bucketed(bucket: Bucket) extends PatternResult

  override def apply(
    query: Query,
    remainingCandidates: Seq[CandidateWithDetails],
    result: Seq[CandidateWithDetails]
  ): SelectorResult = {

    val groupedCandidates: Map[PatternResult, Seq[CandidateWithDetails]] =
      remainingCandidates.groupBy { candidateWithDetails =>
        if (pipelineScope.contains(candidateWithDetails)) {
          val bucket = bucketer(candidateWithDetails)
          Bucketed(bucket)
        } else {
          NotASelectedCandidatePipeline
        }
      }

    val otherCandidates =
      groupedCandidates.getOrElse(NotASelectedCandidatePipeline, Seq.empty)

    val groupedCandidatesIterators = groupedCandidates.collect {
      case (Bucketed(bucket), candidatesWithDetails) => (bucket, candidatesWithDetails.iterator)
    }

    // Group buckets by signal type to calculate normalized weights
    val bucketsBySignalType = groupedCandidatesIterators.keys
      .map { bucket =>
        val (signalTypeOpt, weight) = weights(bucket)
        (bucket, signalTypeOpt, weight)
      }.groupBy(_._2)

    val normalizedBucketWeights = bucketsBySignalType.flatMap {
      case (signalTypeOpt, bucketsWithSignal) =>
        val buckets = bucketsWithSignal.map(_._1)
        val normalizedWeight = if (buckets.nonEmpty) {
          val weight = bucketsWithSignal.head._3
          weight / buckets.size
        } else 0.0
        buckets.map(bucket => (bucket, normalizedWeight))
    }

    val currentBucketWeights: mutable.Map[Bucket, Double] = {
      val bucketsAndWeightsSortedByWeight =
        normalizedBucketWeights.toSeq.sortBy(_._2)(Ordering.Double.reverse)
      mutable.LinkedHashMap(bucketsAndWeightsSortedByWeight: _*)
    }

    var weightSum = currentBucketWeights.valuesIterator.sum

    // Add candidates to `newResults` until all remaining candidates are for a single bucket
    val newResult = new mutable.ArrayBuffer[CandidateWithDetails]()
    while (currentBucketWeights.size > 1) {
      // Random number between 0 and the sum of the ratios of all params
      val randomValue = random.nextDouble() * weightSum

      val currentBucketWeightsIterator: Iterator[(Bucket, Double)] =
        currentBucketWeights.iterator
      val (currentBucket, weight) = currentBucketWeightsIterator.next()

      val componentToTakeFrom = findBucketToTakeFrom(
        randomValue = randomValue,
        cumulativeSumOfWeights = weight,
        bucket = currentBucket,
        bucketWeightsIterator = currentBucketWeightsIterator
      )

      groupedCandidatesIterators.get(componentToTakeFrom) match {
        case Some(iteratorForBucket) if iteratorForBucket.nonEmpty =>
          newResult += iteratorForBucket.next()
        case _ =>
          weightSum -= currentBucketWeights(componentToTakeFrom)
          currentBucketWeights.remove(componentToTakeFrom)
      }
    }

    val remainingBucketInRatio =
      currentBucketWeights.keysIterator.flatMap(groupedCandidatesIterators.get).flatten

    SelectorResult(
      remainingCandidates = otherCandidates,
      result = result ++ newResult ++ remainingBucketInRatio)
  }

  @tailrec private def findBucketToTakeFrom(
    randomValue: Double,
    cumulativeSumOfWeights: Double,
    bucket: Bucket,
    bucketWeightsIterator: Iterator[(Bucket, Double)]
  ): Bucket = {
    if (randomValue < cumulativeSumOfWeights || bucketWeightsIterator.isEmpty) {
      bucket
    } else {
      val (nextBucket, weight) = bucketWeightsIterator.next()
      findBucketToTakeFrom(
        randomValue,
        cumulativeSumOfWeights + weight,
        nextBucket,
        bucketWeightsIterator)
    }
  }
}
