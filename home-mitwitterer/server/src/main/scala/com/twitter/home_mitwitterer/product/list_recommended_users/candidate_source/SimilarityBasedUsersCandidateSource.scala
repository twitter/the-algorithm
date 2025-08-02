package com.twitter.home_mitwitterer.product.list_recommended_users.candidate_source

import com.twitter.hermit.candidate.{thriftscala => t}
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.stitch.Stitch
import com.twitter.strato.client.Fetcher
import com.twitter.strato.generated.client.recommendations.similarity.SimilarUsersBySimsOnUserClientColumn

import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class SimilarityBasedUsersCandidateSource @Inject() (
  similarUsersBySimsOnUserClientColumn: SimilarUsersBySimsOnUserClientColumn)
    etwittertends CandidateSource[Seq[Long], t.Candidate] {

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier("SimilarityBasedUsers")

  private val fetcher: Fetcher[Long, Unit, t.Candidates] =
    similarUsersBySimsOnUserClientColumn.fetcher

  private val MatwitterCandidatesToKeep = 4000

  override def apply(request: Seq[Long]): Stitch[Seq[t.Candidate]] = {
    Stitch
      .collect {
        request.map { userId =>
          fetcher
            .fetch(userId, Unit).map { result =>
              result.v.map(_.candidates).getOrElse(Seq.empty)
            }.map { candidates =>
              val sortedCandidates = candidates.sortBy(-_.score)
              sortedCandidates.take(MatwitterCandidatesToKeep)
            }
        }
      }.map(_.flatten)
  }
}
