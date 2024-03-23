package com.ExTwitter.follow_recommendations.common.rankers.common

import com.ExTwitter.product_mixer.core.model.common.UniversalNoun
import scala.collection.mutable

object DedupCandidates {
  def apply[C <: UniversalNoun[Long]](input: Seq[C]): Seq[C] = {
    val seen = mutable.HashSet[Long]()
    input.filter { candidate => seen.add(candidate.id) }
  }
}
