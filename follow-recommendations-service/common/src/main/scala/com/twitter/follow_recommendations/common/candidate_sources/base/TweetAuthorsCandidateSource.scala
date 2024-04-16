package com.tw ter.follow_recom ndat ons.common.cand date_s ces.base

 mport com.tw ter.follow_recom ndat ons.common.models.T etCand date
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.st ch.St ch

/**
 * base tra  for t et authors based algor hms, e.g. top cal t et authors, tw stly, ...
 *
 * @tparam Target target type
 * @tparam Cand date output cand date types
 */
tra  T etAuthorsCand dateS ce[-Target, +Cand date] extends Cand dateS ce[Target, Cand date] {

  /**
   * fetch T et cand dates
   */
  def getT etCand dates(target: Target): St ch[Seq[T etCand date]]

  /**
   * fetch author d
   */
  def getT etAuthor d(t etCand date: T etCand date): St ch[Opt on[Long]]

  /**
   * wrap cand date  D and T etAuthorProof  n Cand date
   */
  def toCand date(author d: Long, t et ds: Seq[Long], score: Opt on[Double]): Cand date

  /**
   * aggregate scores, default to t  f rst score
   */
  def aggregator(scores: Seq[Double]): Double =
    scores. adOpt on.getOrElse(T etAuthorsCand dateS ce.DefaultScore)

  /**
   * aggregat on  thod for a group of t et cand dates
   */
  def aggregateAndScore(
    target: Target,
    t etCand dates: Seq[T etCand date]
  ): Seq[Cand date]

  /**
   * generate a l st of cand dates for t  target
   */
  def bu ld(
    target: Target
  ): St ch[Seq[Cand date]] = {
    // Fetch T et cand dates and hydrate author  Ds
    val t etCand datesSt ch = for {
      t etCand dates <- getT etCand dates(target)
      author ds <- St ch.collect(t etCand dates.map(getT etAuthor d(_)))
    } y eld {
      for {
        (author dOpt, t etCand date) <- author ds.z p(t etCand dates)
        author d <- author dOpt
      } y eld t etCand date.copy(author d = author d)
    }

    // Aggregate and score, convert to cand date
    t etCand datesSt ch.map(aggregateAndScore(target, _))
  }

  def apply(target: Target): St ch[Seq[Cand date]] =
    bu ld(target)
}

object T etAuthorsCand dateS ce {
  f nal val DefaultScore: Double = 0.0
}
