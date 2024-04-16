package com.tw ter.follow_recom ndat ons.common.base

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.st ch.St ch
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.T  outExcept on

/**
 * Ranker  s a spec al k nd of transform that would only change t  order of a l st of  ems.
 *  f a s ngle  em  s g ven,   "may" attach add  onal scor ng  nformat on to t   em.
 *
 * @tparam Target target to recom nd t  cand dates
 * @tparam Cand date cand date type to rank
 */
tra  Ranker[Target, Cand date] extends Transform[Target, Cand date] { ranker =>

  def rank(target: Target, cand dates: Seq[Cand date]): St ch[Seq[Cand date]]

  overr de def transform(target: Target, cand dates: Seq[Cand date]): St ch[Seq[Cand date]] = {
    rank(target, cand dates)
  }

  overr de def observe(statsRece ver: StatsRece ver): Ranker[Target, Cand date] = {
    val or g nalRanker = t 
    new Ranker[Target, Cand date] {
      overr de def rank(target: Target,  ems: Seq[Cand date]): St ch[Seq[Cand date]] = {
        statsRece ver.counter(Transform. nputCand datesCount). ncr( ems.s ze)
        statsRece ver.stat(Transform. nputCand datesStat).add( ems.s ze)
        StatsUt l.prof leSt chSeqResults(or g nalRanker.rank(target,  ems), statsRece ver)
      }
    }
  }

  def reverse: Ranker[Target, Cand date] = new Ranker[Target, Cand date] {
    def rank(target: Target, cand dates: Seq[Cand date]): St ch[Seq[Cand date]] =
      ranker.rank(target, cand dates).map(_.reverse)
  }

  def andT n(ot r: Ranker[Target, Cand date]): Ranker[Target, Cand date] = {
    val or g nal = t 
    new Ranker[Target, Cand date] {
      def rank(target: Target, cand dates: Seq[Cand date]): St ch[Seq[Cand date]] = {
        or g nal.rank(target, cand dates).flatMap { results => ot r.rank(target, results) }
      }
    }
  }

  /**
   * T   thod wraps t  Ranker  n a des gnated t  out.
   *  f t  ranker t  outs,   would return t  or g nal cand dates d rectly,
   *  nstead of fa l ng t  whole recom ndat on flow
   */
  def w h n(t  out: Durat on, statsRece ver: StatsRece ver): Ranker[Target, Cand date] = {
    val t  outCounter = statsRece ver.counter("t  out")
    val or g nal = t 
    new Ranker[Target, Cand date] {
      overr de def rank(target: Target, cand dates: Seq[Cand date]): St ch[Seq[Cand date]] = {
        or g nal
          .rank(target, cand dates)
          .w h n(t  out)(com.tw ter.f nagle.ut l.DefaultT  r)
          .rescue {
            case _: T  outExcept on =>
              t  outCounter. ncr()
              St ch.value(cand dates)
          }
      }
    }
  }
}

object Ranker {

  def cha n[Target, Cand date](
    transfor r: Transform[Target, Cand date],
    ranker: Ranker[Target, Cand date]
  ): Ranker[Target, Cand date] = {
    new Ranker[Target, Cand date] {
      def rank(target: Target, cand dates: Seq[Cand date]): St ch[Seq[Cand date]] = {
        transfor r
          .transform(target, cand dates)
          .flatMap { results => ranker.rank(target, results) }
      }
    }
  }
}

class  dent yRanker[Target, Cand date] extends Ranker[Target, Cand date] {
  def rank(target: Target, cand dates: Seq[Cand date]): St ch[Seq[Cand date]] =
    St ch.value(cand dates)
}
