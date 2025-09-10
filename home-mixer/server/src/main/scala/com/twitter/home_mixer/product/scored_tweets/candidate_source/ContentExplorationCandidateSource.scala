package com.twitter.home_mixer.product.scored_tweets.candidate_source

import com.twitter.home_mixer.product.scored_tweets.query_transformer.ContentExplorationQueryRequest
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.snowflake.id.SnowflakeId
import com.twitter.stitch.Stitch
import com.twitter.strato.catalog.Scan.Slice
import com.twitter.strato.generated.client.content_understanding.CategoryColdStartPostsMhClientColumn
import javax.inject.Inject
import javax.inject.Singleton
import scala.util.Random

case class ContentExplorationCandidateResponse(
  tweetId: Long,
  tier: String)

@Singleton
class ContentExplorationCandidateSource @Inject() (
  coldStartPostsColumn: CategoryColdStartPostsMhClientColumn)
    extends CandidateSource[ContentExplorationQueryRequest, ContentExplorationCandidateResponse] {

  private val MaxUserCategory = 5
  private val MaxResultPerCategory = 100
  private val MaxResult = 500

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier("ContentExploration")

  private val scanner = coldStartPostsColumn.scanner

  private def interweave(
    candidates: Seq[Seq[ContentExplorationCandidateResponse]]
  ): Seq[ContentExplorationCandidateResponse] = {
    if (candidates.isEmpty) return Seq.empty
    val maxLength = candidates.map(_.length).max
    (0 until maxLength)
      .flatMap { i =>
        candidates.flatMap(seq => seq.lift(i))
      }.take(MaxResult)
  }

  private def getSlice(): Slice[Long] = {
    val now = System.currentTimeMillis()
    val startId = SnowflakeId.firstIdFor(now - 6 * 60 * 60 * 1000L)
    val endId = SnowflakeId.firstIdFor(now)

    Slice[Long](
      from = Some(startId),
      to = Some(endId),
      limit = Some(MaxResultPerCategory)
    )
  }

  override def apply(
    request: ContentExplorationQueryRequest
  ): Stitch[Seq[ContentExplorationCandidateResponse]] = {
    val tags = request.userCategories
    val scan = getSlice()
    val version = request.version

    val scans: Seq[Stitch[Seq[ContentExplorationCandidateResponse]]] = tags.flatMap { tag =>
      Seq(
        scanner.scan((version + "tier1_" + tag._1, scan)).map { results =>
          Random
            .shuffle(results.map(_._1._2))
            .map(id => ContentExplorationCandidateResponse(id, "tier1"))
        },
        scanner.scan((version + "tier2_" + tag._1, scan)).map { results =>
          Random
            .shuffle(results.map(_._1._2))
            .map(id => ContentExplorationCandidateResponse(id, "tier2"))
        }
      )
    }

    Stitch.collect(scans).map(interweave)
  }
}
