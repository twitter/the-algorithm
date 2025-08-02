package com.twitter.follow_recommendations.common.transforms.modify_social_proof

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.follow_recommendations.common.base.GatedTransform
import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.product_mitwitterer.core.model.marshalling.request.HasClientContetwittert
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.HasParams
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class RemoveAccountProofTransform @Inject() (statsReceiver: StatsReceiver)
    etwittertends GatedTransform[HasClientContetwittert with HasParams, CandidateUser] {

  private val stats = statsReceiver.scope(this.getClass.getSimpleName)
  private val removedProofsCounter = stats.counter("num_removed_proofs")

  override def transform(
    target: HasClientContetwittert with HasParams,
    items: Seq[CandidateUser]
  ): Stitch[Seq[CandidateUser]] =
    Stitch.value(items.map { candidate =>
      removedProofsCounter.incr()
      candidate.copy(reason = None)
    })
}
