package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.featuresw c s.v2.FeatureSw chResults
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.thr ftscala._
 mport scala.collect on.mutable

object T etCountsHydrator {
  type Type = ValueHydrator[Opt on[StatusCounts], Ctx]

  case class Ctx(featureSw chResults: Opt on[FeatureSw chResults], underly ngT etCtx: T etCtx)
      extends T etCtx.Proxy

  val ret etCountF eld: F eldByPath =
    f eldByPath(T et.CountsF eld, StatusCounts.Ret etCountF eld)
  val replyCountF eld: F eldByPath = f eldByPath(T et.CountsF eld, StatusCounts.ReplyCountF eld)
  val favor eCountF eld: F eldByPath =
    f eldByPath(T et.CountsF eld, StatusCounts.Favor eCountF eld)
  val quoteCountF eld: F eldByPath = f eldByPath(T et.CountsF eld, StatusCounts.QuoteCountF eld)
  val bookmarkCountF eld: F eldByPath =
    f eldByPath(T et.CountsF eld, StatusCounts.BookmarkCountF eld)

  val emptyCounts = StatusCounts()

  val ret etCountPart al = ValueState.part al(emptyCounts, ret etCountF eld)
  val replyCountPart al = ValueState.part al(emptyCounts, replyCountF eld)
  val favor eCountPart al = ValueState.part al(emptyCounts, favor eCountF eld)
  val quoteCountPart al = ValueState.part al(emptyCounts, quoteCountF eld)
  val bookmarkCountPart al = ValueState.part al(emptyCounts, bookmarkCountF eld)

  val bookmarksCountHydrat onEnabledKey = "bookmarks_count_hydrat on_enabled"

  /**
   * Take a Seq of StatusCounts and reduce down to a s ngle StatusCounts.
   * Note: `reduce`  re  s safe because   are guaranteed to always have at least
   * one value.
   */
  def reduceStatusCounts(counts: Seq[StatusCounts]): StatusCounts =
    counts.reduce { (a, b) =>
      StatusCounts(
        ret etCount = b.ret etCount.orElse(a.ret etCount),
        replyCount = b.replyCount.orElse(a.replyCount),
        favor eCount = b.favor eCount.orElse(a.favor eCount),
        quoteCount = b.quoteCount.orElse(a.quoteCount),
        bookmarkCount = b.bookmarkCount.orElse(a.bookmarkCount)
      )
    }

  def toKeys(
    t et d: T et d,
    countsF elds: Set[F eld d],
    curr: Opt on[StatusCounts]
  ): Seq[T etCountKey] = {
    val keys = new mutable.ArrayBuffer[T etCountKey](4)

    countsF elds.foreach {
      case StatusCounts.Ret etCountF eld. d =>
         f (curr.flatMap(_.ret etCount). sEmpty)
          keys += Ret etsKey(t et d)

      case StatusCounts.ReplyCountF eld. d =>
         f (curr.flatMap(_.replyCount). sEmpty)
          keys += Repl esKey(t et d)

      case StatusCounts.Favor eCountF eld. d =>
         f (curr.flatMap(_.favor eCount). sEmpty)
          keys += FavsKey(t et d)

      case StatusCounts.QuoteCountF eld. d =>
         f (curr.flatMap(_.quoteCount). sEmpty)
          keys += QuotesKey(t et d)

      case StatusCounts.BookmarkCountF eld. d =>
         f (curr.flatMap(_.bookmarkCount). sEmpty)
          keys += BookmarksKey(t et d)

      case _ =>
    }

    keys
  }

  /*
   * Get a StatusCounts object for a spec f c t et and spec f c f eld (e.g. only fav, or reply etc).
   * StatusCounts returned from  re can be comb ned w h ot r StatusCounts us ng `sumStatusCount`
   */
  def statusCountsRepo(
    key: T etCountKey,
    repo: T etCountsRepos ory.Type
  ): St ch[ValueState[StatusCounts]] =
    repo(key).l ftToTry.map {
      case Return(count) =>
        ValueState.mod f ed(
          key match {
            case _: Ret etsKey => StatusCounts(ret etCount = So (count))
            case _: Repl esKey => StatusCounts(replyCount = So (count))
            case _: FavsKey => StatusCounts(favor eCount = So (count))
            case _: QuotesKey => StatusCounts(quoteCount = So (count))
            case _: BookmarksKey => StatusCounts(bookmarkCount = So (count))
          }
        )

      case Throw(_) =>
        key match {
          case _: Ret etsKey => ret etCountPart al
          case _: Repl esKey => replyCountPart al
          case _: FavsKey => favor eCountPart al
          case _: QuotesKey => quoteCountPart al
          case _: BookmarksKey => bookmarkCountPart al
        }
    }

  def f lterRequestedCounts(
    user d: User d,
    requestedCounts: Set[F eld d],
    bookmarkCountsDec der: Gate[Long],
    featureSw chResults: Opt on[FeatureSw chResults]
  ): Set[F eld d] = {
     f (requestedCounts.conta ns(StatusCounts.BookmarkCountF eld. d))
       f (bookmarkCountsDec der(user d) ||
        featureSw chResults
          .flatMap(_.getBoolean(bookmarksCountHydrat onEnabledKey, false))
          .getOrElse(false))
        requestedCounts
      else
        requestedCounts.f lter(_ != StatusCounts.BookmarkCountF eld. d)
    else
      requestedCounts
  }

  def apply(repo: T etCountsRepos ory.Type, shouldHydrateBookmarksCount: Gate[Long]): Type = {

    val all: Set[F eld d] = StatusCounts.f eld nfos.map(_.tf eld. d).toSet

    val mod f edZero: Map[Set[F eld d], ValueState[So [StatusCounts]]] = {
      for (set <- all.subsets) y eld {
        @ nl ne
        def zeroOrNone(f eld d: F eld d) =
           f (set.conta ns(f eld d)) So (0L) else None

        val statusCounts =
          StatusCounts(
            ret etCount = zeroOrNone(StatusCounts.Ret etCountF eld. d),
            replyCount = zeroOrNone(StatusCounts.ReplyCountF eld. d),
            favor eCount = zeroOrNone(StatusCounts.Favor eCountF eld. d),
            quoteCount = zeroOrNone(StatusCounts.QuoteCountF eld. d),
            bookmarkCount = zeroOrNone(StatusCounts.BookmarkCountF eld. d)
          )

        set -> ValueState.mod f ed(So (statusCounts))
      }
    }.toMap

    ValueHydrator[Opt on[StatusCounts], Ctx] { (curr, ctx) =>
      val countsF elds: Set[F eld d] = f lterRequestedCounts(
        ctx.opts.forUser d.getOrElse(ctx.user d),
        ctx.opts. nclude.countsF elds,
        shouldHydrateBookmarksCount,
        ctx.featureSw chResults
      )
       f (ctx. sRet et) {
        // To avo d a reflect on- nduced key error w re t  countsF elds can conta n a f eld d
        // that  s not  n t  thr ft sc ma loaded at start,   str p unknown f eld_ ds us ng
        // ` ntersect`
        St ch.value(mod f edZero(countsF elds. ntersect(all)))
      } else {
        val keys = toKeys(ctx.t et d, countsF elds, curr)

        St ch.traverse(keys)(key => statusCountsRepo(key, repo)).map { results =>
          // always flag mod f ed  f start ng from None
          val vs0 = ValueState.success(curr.getOrElse(emptyCounts), curr. sEmpty)
          val vs = vs0 +: results

          ValueState.sequence(vs).map(reduceStatusCounts).map(So (_))
        }
      }
    }.only f { (_, ctx) =>
      f lterRequestedCounts(
        ctx.opts.forUser d.getOrElse(ctx.user d),
        ctx.opts. nclude.countsF elds,
        shouldHydrateBookmarksCount,
        ctx.featureSw chResults
      ).nonEmpty
    }
  }
}
