package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel nes
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport scala.collect on.mutable

/**
 * Select cand dates and add t m accord ng to t  `pattern`.
 * T  pattern  s repeated unt l all cand dates conta ned  n t  pattern are added to t  `result`.
 *  f t  cand dates for a spec f c [[Bucket]]  n t  pattern are exhausted, that [[Bucket]] w ll be
 * sk pped on subsequent  erat ons.
 *  f a cand date has a [[Bucket]] that  sn't  n t  pattern    s added to t  end of t  `result`.
 * T  end result  s all cand dates from all [[cand dateP pel nes]]s prov ded w ll end up  n t  result.
 *
 * @example  f t re are no more cand dates from a g ven `Cand dateP pel ne` t n    s sk pped, so
 *          w h t  pattern `Seq(A, A, B, C)`,  f t re are no more cand dates from `B` t n    s
 *          effect vely t  sa  as `Seq(A, A, C)`. T  `result` w ll conta n all cand dates from all
 *          `Cand dateP pel ne`s who's `Bucket`  s  n t  `pattern`.
 *
 * @example  f t  pattern  s `Seq(A, A, B, C)` and t  rema n ng cand dates
 *          from t  prov ded `cand dateP pel nes` are:
 *          - 5 `A`s
 *          - 2 `B`s
 *          - 1 `C`
 *          - 1 `D`s
 *
 *          t n t  result ng output for each  erat on over t  pattern  s
 *          - `Seq(A, A, B, C)`
 *          - `Seq(A, A, B)` s nce t re's no more `C`s
 *          - `Seq(A)` s nce t re are no more `B`s or `C`s
 *          - `Seq(D)` s nce   wasn't  n t  pattern but  s from one of t  prov ded
 *            `cand dateP pel nes`,  's appended at t  end
 *
 *          so t  `result` that's returned would be `Seq(A, A, B, C, A, A, B, A, D)`
 */
case class  nsertAppendPatternResults[-Query <: P pel neQuery, Bucket](
  cand dateP pel nes: Set[Cand dateP pel ne dent f er],
  bucketer: Bucketer[Bucket],
  pattern: Seq[Bucket])
    extends Selector[Query] {

  requ re(pattern.nonEmpty, "`pattern` must be non-empty")

  overr de val p pel neScope: Cand dateScope = Spec f cP pel nes(cand dateP pel nes)

  pr vate sealed tra  PatternResult
  pr vate case object NotASelectedCand dateP pel ne extends PatternResult
  pr vate case object NotABucket nT Pattern extends PatternResult
  pr vate case class Bucketed(bucket: Bucket) extends PatternResult

  pr vate val allBuckets nPattern = pattern.toSet

  overr de def apply(
    query: Query,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {
    val groupedCand dates: Map[PatternResult, Seq[Cand dateW hDeta ls]] =
      rema n ngCand dates.groupBy { cand dateW hDeta ls =>
         f (p pel neScope.conta ns(cand dateW hDeta ls)) {
          //  f a cand date's Bucket doesnt appear  n t  pattern  's backf lled at t  end
          val bucket = bucketer(cand dateW hDeta ls)
           f (allBuckets nPattern.conta ns(bucket)) {
            Bucketed(bucket)
          } else {
            NotABucket nT Pattern
          }
        } else {
          NotASelectedCand dateP pel ne
        }
      }

    val ot rCand dates =
      groupedCand dates.getOrElse(NotASelectedCand dateP pel ne, Seq.empty)

    val notABucket nT Pattern =
      groupedCand dates.getOrElse(NotABucket nT Pattern, Seq.empty)

    // mutable so   can remove f n s d  erators to opt m ze w n loop ng for large patterns
    val groupedBuckets erators = mutable.HashMap(groupedCand dates.collect {
      case (Bucketed(bucket), cand datesW hDeta ls) => (bucket, cand datesW hDeta ls. erator)
    }.toSeq: _*)

    val pattern erator =  erator.cont nually(pattern).flatten

    val newResult = new mutable.ArrayBuffer[Cand dateW hDeta ls]()
    wh le (groupedBuckets erators.nonEmpty) {
      val bucket = pattern erator.next()
      groupedBuckets erators.get(bucket) match {
        case So ( erator)  f  erator.nonEmpty => newResult +=  erator.next()
        case So (_) => groupedBuckets erators.remove(bucket)
        case None =>
      }
    }

    SelectorResult(
      rema n ngCand dates = ot rCand dates,
      result = result ++ newResult ++ notABucket nT Pattern)
  }
}
