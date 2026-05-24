package com.twitter.tweet_mixer.candidate_source.text_embedding_ann

import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.stitch.Stitch
import com.twitter.strato.catalog.Scan.Slice
import com.twitter.strato.generated.client.searchai.vectordb.RealtimeTopV8ClientColumn
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TextEmbeddingCandidateSource @Inject() (
  column: RealtimeTopV8ClientColumn)
    extends CandidateSource[Seq[TextEmbeddingQuery], TweetMixerCandidate] {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier("TextEmbedding")

  private val scanner = column.scanner

  override def apply(request: Seq[TextEmbeddingQuery]): Stitch[Seq[TweetMixerCandidate]] = {

    val stitchResponse = request.map { query =>
      val scan = Slice[Long](from = None, to = None, limit = Some(query.limit))
      val view = RealtimeTopV8ClientColumn.View(
        vector = query.vector,
        filter = None,
        params = None
      )
      scanner.scan(scan, view).map {
        _.map { scoredPoint =>
          val value = scoredPoint._2
          TweetMixerCandidate(tweetId = value.id, score = value.score, seedId = -1L)
        }
      }
    }

    Stitch.collect(stitchResponse).map(_.flatten)
  }
}
