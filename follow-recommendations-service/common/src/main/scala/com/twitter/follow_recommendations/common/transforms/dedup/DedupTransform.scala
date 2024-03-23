package com.ExTwitter.follow_recommendations.common.transforms.dedup

import com.ExTwitter.follow_recommendations.common.base.Transform
import com.ExTwitter.product_mixer.core.model.common.UniversalNoun
import com.ExTwitter.stitch.Stitch
import scala.collection.mutable

class DedupTransform[Request, Candidate <: UniversalNoun[Long]]()
    extends Transform[Request, Candidate] {
  override def transform(target: Request, candidates: Seq[Candidate]): Stitch[Seq[Candidate]] = {
    val seen = mutable.HashSet[Long]()
    Stitch.value(candidates.filter(candidate => seen.add(candidate.id)))
  }
}
