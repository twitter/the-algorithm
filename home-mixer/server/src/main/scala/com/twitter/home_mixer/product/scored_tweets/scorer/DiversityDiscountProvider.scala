package com.tw ter.ho _m xer.product.scored_t ets.scorer

 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.ScoreFeature
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures

tra  D vers yD scountProv der {

  /**
   * Fetch t   D of t  ent y to d vers fy
   */
  def ent y d(cand date: Cand dateW hFeatures[T etCand date]): Opt on[Long]

  /**
   * Compute d scount factor for each cand date based on pos  on (zero-based)
   * relat ve to ot r cand dates assoc ated w h t  sa  ent y
   */
  def d scount(pos  on:  nt): Double

  /**
   * Return cand date  Ds sorted by score  n descend ng order
   */
  def sort(cand dates: Seq[Cand dateW hFeatures[T etCand date]]): Seq[Long] = cand dates
    .map { cand date =>
      (cand date.cand date. d, cand date.features.getOrElse(ScoreFeature, None).getOrElse(0.0))
    }
    .sortBy(_._2)(Order ng.Double.reverse)
    .map(_._1)

  /**
   * Group by t  spec f ed ent y  D (e.g. authors, l kers, follo rs)
   * Sort each group by score  n descend ng order
   * Determ ne t  d scount factor based on t  pos  on of each cand date
   */
  def apply(
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): Map[Long, Double] = cand dates
    .groupBy(ent y d)
    .flatMap {
      case (ent y dOpt, ent yCand dates) =>
        val sortedCand date ds = sort(ent yCand dates)

         f (ent y dOpt. sDef ned) {
          sortedCand date ds.z pW h ndex.map {
            case (cand date d,  ndex) =>
              cand date d -> d scount( ndex)
          }
        } else sortedCand date ds.map(_ -> 1.0)
    }
}

object AuthorD vers yD scountProv der extends D vers yD scountProv der {
  pr vate val Decay = 0.5
  pr vate val Floor = 0.25

  overr de def ent y d(cand date: Cand dateW hFeatures[T etCand date]): Opt on[Long] =
    cand date.features.getOrElse(Author dFeature, None)

  // Prov des an exponent al decay based d scount by pos  on (w h a floor)
  overr de def d scount(pos  on:  nt): Double =
    (1 - Floor) * Math.pow(Decay, pos  on) + Floor
}
