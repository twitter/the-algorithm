package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel ne
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel nes
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport scala.collect on.mutable

object  nsertAppend aveResults {
  def apply[Query <: P pel neQuery, Bucket](
    cand dateP pel nes: Set[Cand dateP pel ne dent f er],
    bucketer: Bucketer[Bucket],
  ):  nsertAppend aveResults[Query, Bucket] =
    new  nsertAppend aveResults(Spec f cP pel nes(cand dateP pel nes), bucketer)

  def apply[Query <: P pel neQuery, Bucket](
    cand dateP pel ne: Cand dateP pel ne dent f er,
    bucketer: Bucketer[Bucket],
  ):  nsertAppend aveResults[Query, Bucket] =
    new  nsertAppend aveResults(Spec f cP pel ne(cand dateP pel ne), bucketer)
}

/**
 * Select cand dates  ave t m toget r accord ng to t  r [[Bucket]].
 *
 * Cand dates are grouped accord ng to [[Bucket]] and one cand date  s added from each group unt l
 * no cand dates belong ng to any group are left.
 *
 * Funct onally s m lar to [[ nsertAppendPatternResults]]. [[ nsertAppendPatternResults]]  s useful
 *  f   have more complex order ng requ re nts but   requ res   to know all t  buckets  n
 * advance.
 *
 * @note T  order  n wh ch cand dates are  aved toget r depends on t  order  n wh ch t  buckets
 *        re f rst seen on cand dates.
 *
 * @example  f t  cand dates are Seq(T et(10), T et(8), T et(3), T et(13)) and t y are bucketed
 *          us ng an  sEven bucket ng funct on, t n t  result ng buckets would be:
 *
 *          - Seq(T et(10), T et(8))
 *          - Seq(T et(3), T et(13))
 *
 *          T  selector would t n loop through t se buckets and produce:
 *
 *          - T et(10)
 *          - T et(3)
 *          - T et(8)
 *          - T et(13)
 *
 *          Note that f rst bucket encountered was t  'even' bucket so  av ng proceeds f rst w h
 *          t  even bucket t n t  odd bucket. T et(3) had been f rst t n t  oppos e would be
 *          true.
 */
case class  nsertAppend aveResults[-Query <: P pel neQuery, Bucket](
  overr de val p pel neScope: Cand dateScope,
  bucketer: Bucketer[Bucket])
    extends Selector[Query] {

  overr de def apply(
    query: Query,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {
    val (bucketableCand dates, ot rCand dates) =
      rema n ngCand dates.part  on(p pel neScope.conta ns)

    val groupedCand dates = groupByBucket(bucketableCand dates)

    val cand dateBucketQueues: mutable.Queue[mutable.Queue[Cand dateW hDeta ls]] =
      mutable.Queue() ++= groupedCand dates
    val newResult = mutable.ArrayBuffer[Cand dateW hDeta ls]()

    // Take t  next group of cand dates from t  queue and attempt to add t  f rst cand date from
    // that group  nto t  result. T  loop w ll term nate w n every queue  s empty.
    wh le (cand dateBucketQueues.nonEmpty) {
      val nextCand dateQueue = cand dateBucketQueues.dequeue()

       f (nextCand dateQueue.nonEmpty) {
        newResult += nextCand dateQueue.dequeue()

        // Re-queue t  bucket of cand dates  f  's st ll non-empty
         f (nextCand dateQueue.nonEmpty) {
          cand dateBucketQueues.enqueue(nextCand dateQueue)
        }
      }
    }

    SelectorResult(rema n ngCand dates = ot rCand dates, result = result ++ newResult)
  }

  /**
   * S m lar to `groupBy` but respect t  order  n wh ch  nd v dual bucket values are f rst seen.
   * T   s useful w n t  cand dates have already been sorted pr or to t  selector runn ng.
   */
  pr vate def groupByBucket(
    cand dates: Seq[Cand dateW hDeta ls]
  ): mutable.ArrayBuffer[mutable.Queue[Cand dateW hDeta ls]] = {
    val bucketToCand dateGroup ndex = mutable.Map.empty[Bucket,  nt]
    val cand dateGroups = mutable.ArrayBuffer[mutable.Queue[Cand dateW hDeta ls]]()

    cand dates.foreach { cand date =>
      val bucket = bucketer(cand date)

      //  ndex po nts to t  spec f c sub-group  n cand dateGroups w re   want to  nsert t  next
      // cand date.  f a bucket has already been seen t n t  value  s known, ot rw se   need
      // to add a new entry for  .
       f (!bucketToCand dateGroup ndex.conta ns(bucket)) {
        cand dateGroups.append(mutable.Queue())
        bucketToCand dateGroup ndex.put(bucket, cand dateGroups.length - 1)
      }

      cand dateGroups(bucketToCand dateGroup ndex(bucket)).enqueue(cand date)
    }

    cand dateGroups
  }
}
