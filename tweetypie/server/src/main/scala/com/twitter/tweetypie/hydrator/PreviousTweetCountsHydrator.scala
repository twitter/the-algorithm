package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.featuresw c s.v2.FeatureSw chResults
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.F eld d
 mport com.tw ter.t etyp e.T et d
 mport com.tw ter.t etyp e.core.ValueState
 mport com.tw ter.t etyp e.repos ory.T etCountKey
 mport com.tw ter.t etyp e.repos ory.T etCountsRepos ory
 mport com.tw ter.t etyp e.thr ftscala.Ed Control
 mport com.tw ter.t etyp e.thr ftscala.StatusCounts
 mport com.tw ter.t etyp e.thr ftscala._

/*
 * A constructor for a ValueHydrator that hydrates `prev ous_counts`
 *  nformat on. Prev ous counts are appl ed to ed  t ets, t y
 * are t  summat on of all t  status_counts  n an ed  cha n up to
 * but not  nclud ng t  t et be ng hydrated.
 *
 */
object Prev ousT etCountsHydrator {

  case class Ctx(
    ed Control: Opt on[Ed Control],
    featureSw chResults: Opt on[FeatureSw chResults],
    underly ngT etCtx: T etCtx)
      extends T etCtx.Proxy

  type Type = ValueHydrator[Opt on[StatusCounts], Ctx]

  val hydratedF eld: F eldByPath = f eldByPath(T et.Prev ousCountsF eld)

  /*
   * Params:
   *  t et d: T  t et be ng hydrated.
   *  ed T et ds: T  sorted l st of all ed s  n an ed  cha n.
   *
   * Returns: t et ds  n an ed  cha n from t   n  al t et up to but not  nclud ng
   *  t  t et be ng hydrated (`t et d`)
   */
  def prev ousT et ds(t et d: T et d, ed T et ds: Seq[T et d]): Seq[T et d] = {
    ed T et ds.takeWh le(_ < t et d)
  }

  /* An add  on operat on for Opt on[Long] */
  def sumOpt ons(A: Opt on[Long], B: Opt on[Long]): Opt on[Long] =
    (A, B) match {
      case (None, None) => None
      case (So (a), None) => So (a)
      case (None, So (b)) => So (b)
      case (So (a), So (b)) => So (a + b)
    }

  /* An add  on operat on for StatusCounts */
  def sumStatusCounts(A: StatusCounts, B: StatusCounts): StatusCounts =
    StatusCounts(
      ret etCount = sumOpt ons(A.ret etCount, B.ret etCount),
      replyCount = sumOpt ons(A.replyCount, B.replyCount),
      favor eCount = sumOpt ons(A.favor eCount, B.favor eCount),
      quoteCount = sumOpt ons(A.quoteCount, B.quoteCount),
      bookmarkCount = sumOpt ons(A.bookmarkCount, B.bookmarkCount)
    )

  def apply(repo: T etCountsRepos ory.Type, shouldHydrateBookmarksCount: Gate[Long]): Type = {

    /*
     * Get a StatusCount represent ng t  sum d engage nts of all prev ous
     * StatusCounts  n an ed  cha n. Only `countsF elds` that are spec f cally requested
     * are  ncluded  n t  aggregate StatusCount, ot rw se those f elds are None.
     */
    def getPrev ousEngage ntCounts(
      t et d: T et d,
      ed T et ds: Seq[T et d],
      countsF elds: Set[F eld d]
    ): St ch[ValueState[StatusCounts]] = {
      val ed T et dL st = prev ousT et ds(t et d, ed T et ds)

      // StatusCounts for each ed  t et rev s on
      val statusCountsPerEd Vers on: St ch[Seq[ValueState[StatusCounts]]] =
        St ch.collect(ed T et dL st.map { t et d =>
          // Wh ch t et count keys to request, as  nd cated by t  t et opt ons.
          val keys: Seq[T etCountKey] =
            T etCountsHydrator.toKeys(t et d, countsF elds, None)

          // A separate StatusCounts for each count f eld, for `t et d`
          // e.g. Seq(StatusCounts(ret etCounts=5L), StatusCounts(favCounts=6L))
          val statusCountsPerCountF eld: St ch[Seq[ValueState[StatusCounts]]] =
            St ch.collect(keys.map(key => T etCountsHydrator.statusCountsRepo(key, repo)))

          // Reduce t  per-f eld counts  nto a s ngle StatusCounts for `t et d`
          statusCountsPerCountF eld.map { vs =>
            // NOTE: T  StatusCounts reduct on uses d fferent log c than
            // `sumStatusCounts`. T  reduct on takes t  latest value for a f eld.
            //  nstead of summ ng t  f elds.
            ValueState.sequence(vs).map(T etCountsHydrator.reduceStatusCounts)
          }
        })

      // Sum toget r t  StatusCounts for each ed  t et rev s on  nto a s ngle Status Count
      statusCountsPerEd Vers on.map { vs =>
        ValueState.sequence(vs).map { statusCounts =>
          // Reduce a l st of StatusCounts  nto a s ngle StatusCount by summ ng t  r f elds.
          statusCounts.reduce { (a, b) => sumStatusCounts(a, b) }
        }
      }
    }

    ValueHydrator[Opt on[StatusCounts], Ctx] { ( nputStatusCounts, ctx) =>
      val countsF elds: Set[F eld d] = T etCountsHydrator.f lterRequestedCounts(
        ctx.opts.forUser d.getOrElse(ctx.user d),
        ctx.opts. nclude.countsF elds,
        shouldHydrateBookmarksCount,
        ctx.featureSw chResults
      )

      ctx.ed Control match {
        case So (Ed Control.Ed (ed )) =>
          ed .ed Control n  al match {
            case So ( n  al) =>
              val prev ousStatusCounts: St ch[ValueState[StatusCounts]] =
                getPrev ousEngage ntCounts(ctx.t et d,  n  al.ed T et ds, countsF elds)

              // Add t  new aggregated StatusCount to t  T etData and return  
              prev ousStatusCounts.map { valueState =>
                valueState.map { statusCounts => So (statusCounts) }
              }
            case None =>
              // Ed Control n  al  s not hydrated w h n Ed ControlEd 
              // T   ans   cannot prov de aggregated prev ous counts,   w ll
              // fa l open and return t   nput data unchanged.
              St ch.value(ValueState.part al( nputStatusCounts, hydratedF eld))
          }

        case _ =>
          //  f t  t et has an Ed Control n  al -  's t  f rst T et  n t  Ed  Cha n
          // or has no Ed Control -   could be an old T et from w n no Ed  Controls ex sted
          // t n t  prev ous counts are set to be equal to None.
          St ch.value(ValueState.un (None))
      }
    }.only f { (_, ctx: Ctx) =>
      // only run  f t  CountsF eld was requested; note t   s ran both on read and wr e path
      T etCountsHydrator
        .f lterRequestedCounts(
          ctx.opts.forUser d.getOrElse(ctx.user d),
          ctx.opts. nclude.countsF elds,
          shouldHydrateBookmarksCount,
          ctx.featureSw chResults
        ).nonEmpty
    }
  }
}
