package com.ExTwitter.follow_recommendations.common.transforms.modify_social_proof

import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.follow_recommendations.common.base.GatedTransform
import com.ExTwitter.follow_recommendations.common.models.CandidateUser
import com.ExTwitter.product_mixer.core.model.marshalling.request.HasClientContext
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.timelines.configapi.HasParams
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoveAccountProofTransform @Inject() (statsReceiver: StatsReceiver)
    extends GatedTransform[HasClientContext with HasParams, CandidateUser] {

  private val stats = statsReceiver.scope(this.getClass.getSimpleName)
  private val removedProofsCounter = stats.counter("num_removed_proofs")

  override def transform(
    target: HasClientContext with HasParams,
    items: Seq[CandidateUser]
  ): Stitch[Seq[CandidateUser]] =
    Stitch.value(items.map { candidate =>
      removedProofsCounter.incr()
      candidate.copy(reason = None)
    })
}
