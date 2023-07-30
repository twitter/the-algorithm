package com.X.follow_recommendations.common.transforms.dedup

import com.X.follow_recommendations.common.base.Transform
import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.stitch.Stitch
import scala.collection.mutable

class DedupTransform[Request, Candidate <: UniversalNoun[Long]]()
    extends Transform[Request, Candidate] {
  override def transform(target: Request, candidates: Seq[Candidate]): Stitch[Seq[Candidate]] = {
    val seen = mutable.HashSet[Long]()
    Stitch.value(candidates.filter(candidate => seen.add(candidate.id)))
  }
}
