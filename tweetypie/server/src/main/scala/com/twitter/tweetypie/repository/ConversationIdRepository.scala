package com.tw ter.t etyp e
package repos ory

 mport com.tw ter.flockdb.cl ent._
 mport com.tw ter.st ch.SeqGroup
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.compat.LegacySeqGroup

case class Conversat on dKey(t et d: T et d, parent d: T et d)

object Conversat on dRepos ory {
  type Type = Conversat on dKey => St ch[T et d]

  def apply(mult SelectOne:  erable[Select[StatusGraph]] => Future[Seq[Opt on[Long]]]): Type =
    key => St ch.call(key, Group(mult SelectOne))

  pr vate case class Group(
    mult SelectOne:  erable[Select[StatusGraph]] => Future[Seq[Opt on[Long]]])
      extends SeqGroup[Conversat on dKey, T et d] {

    pr vate[t ] def getConversat on ds(
      keys: Seq[Conversat on dKey],
      getLookup d: Conversat on dKey => T et d
    ): Future[Map[Conversat on dKey, T et d]] = {
      val d st nct ds = keys.map(getLookup d).d st nct
      val tflockQuer es = d st nct ds.map(Conversat onGraph.to)
       f (tflockQuer es. sEmpty) {
        Future.value(Map[Conversat on dKey, T et d]())
      } else {
        mult SelectOne(tflockQuer es).map { results =>
          // f rst,   need to match up t  d st nct  ds requested w h t  correspond ng result
          val resultMap =
            d st nct ds
              .z p(results)
              .collect {
                case ( d, So (conversat on d)) =>  d -> conversat on d
              }
              .toMap

          // t n   need to map keys  nto t  above map
          keys.flatMap { key => resultMap.get(getLookup d(key)).map(key -> _) }.toMap
        }
      }
    }

    /**
     * Returns a key-value result that maps keys to t  t et's conversat on  Ds.
     *
     * Example:
     * T et B  s a reply to t et A w h conversat on  D c.
     *   want to get B's conversat on  D. T n, for t  request
     *
     *   Conversat on dRequest(B. d, A. d)
     *
     *   key-value result's "found" map w ll conta n a pa r (B. d -> c).
     */
    protected overr de def run(keys: Seq[Conversat on dKey]): Future[Seq[Try[T et d]]] =
      LegacySeqGroup.l ftToSeqTry(
        for {
          // Try to get t  conversat on  Ds for t  parent t ets
          conv dsFromParent <- getConversat on ds(keys, _.parent d)

          // Collect t  t et  Ds whose parents' conversat on  Ds couldn't be found.
          //   assu  that happened  n one of two cases:
          //  * for a t et whose parent has been deleted
          //  * for a t et whose parent  s t  root of a conversat on
          // Note:  n e  r case,   w ll try to look up t  conversat on  D of t  t et whose parent
          // couldn't be found.  f that can't be found e  r,   w ll eventually return t  parent  D.
          t etsWhoseParentsDontHaveConvo ds = keys.toSet -- conv dsFromParent.keys

          // Collect t  conversat on  Ds for t  t ets whose parents have not been found, now us ng t 
          // t ets' own  Ds.
          conv dsFromT et <-
            getConversat on ds(t etsWhoseParentsDontHaveConvo ds.toSeq, _.t et d)

          // Comb ne t  by-parent- D and by-t et- D results.
          conv dMap = conv dsFromParent ++ conv dsFromT et

          // Ass gn conversat on  Ds to all not-found t et  Ds.
          // A t et m ght not have rece ved a conversat on  D  f
          //  * t  parent of t  t et  s t  root of t  conversat on, and   are  n t  wr e path
          //    for creat ng t  t et.  n that case, t  conversat on  D should be t  t et's parent
          //     D.
          //  *   had been created before TFlock started handl ng conversat on  Ds.  n that case, t 
          //    conversat on  D w ll just po nt to t  parent t et so that   can have a conversat on of
          //    at least two t ets.
          // So  n both cases,   want to return t  t et's parent  D.
        } y eld {
          keys.map {
            case k @ Conversat on dKey(t, p) => conv dMap.getOrElse(k, p)
          }
        }
      )
  }
}
