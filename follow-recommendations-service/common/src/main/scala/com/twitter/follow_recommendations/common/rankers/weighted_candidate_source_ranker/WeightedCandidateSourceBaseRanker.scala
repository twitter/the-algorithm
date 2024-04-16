package com.tw ter.follow_recom ndat ons.common.rankers.  ghted_cand date_s ce_ranker

 mport com.tw ter.follow_recom ndat ons.common.ut ls.RandomUt l
 mport com.tw ter.follow_recom ndat ons.common.ut ls. rgeUt l
 mport com.tw ter.follow_recom ndat ons.common.ut ls.  ghted
 mport com.tw ter.follow_recom ndat ons.common.rankers.  ghted_cand date_s ce_ranker.  ght thod._
 mport scala.ut l.Random

/**
 * T  ranker selects t  next cand date s ce to select a cand date from.   supports
 * two k nds of algor hm,   ghtedRandomSampl ng or   ghtedRoundRob n.   ghtedRandomSampl ng
 * p ck t  next cand date s ce randomly,   ghtedRoundRob n p cked t  next cand date s ce
 * sequent ally based on t    ght of t  cand date s ce.    s default to   ghtedRandomSampl ng
 *  f no   ght  thod  s prov ded.
 *
 * Example usage of t  class:
 *
 * W n use   ghtedRandomSampl ng:
 *  nput cand date s ces and t  r   ghts are: {{CS1: 3}, {CS2: 2}, {CS3: 5}}
 * Ranked cand dates sequence  s not determ ned because of random sampl ng.
 * One poss ble output cand date sequence  s: (CS1_cand date1, CS2_cand date1, CS2_cand date2,
 * CS3_cand date1, CS3_cand dates2, CS3_cand date3, CS1_cand date2, CS1_cand date3,
 * CS3_cand date4, CS3_cand date5, CS1_cand date4, CS1_cand date5, CS2_cand date6, CS2_cand date3,...)
 *
 * W n use   ghtedRoundRob n:
 *  nput cand date s ces and t  r   ghts are: {{CS1: 3}, {CS2: 2}, {CS3: 5}}
 * Output cand date sequence  s: (CS1_cand date1, CS1_cand date2, CS1_cand date3,
 * CS2_cand date1, CS2_cand dates2, CS3_cand date1, CS3_cand date2, CS3_cand date3,
 * CS3_cand date4, CS3_cand date5, CS1_cand date4, CS1_cand date5, CS1_cand date6, CS2_cand date3,...)
 *
 * Note: CS1_cand date1  ans t  f rst cand date  n CS1 cand date s ce.
 *
 * @tparam A cand date s ce type
 * @tparam Rec Recom ndat on type
 * @param cand dateS ce  ghts relat ve   ghts for d fferent cand date s ces
 */
class   ghtedCand dateS ceBaseRanker[A, Rec](
  cand dateS ce  ghts: Map[A, Double],
    ght thod:   ght thod =   ghtedRandomSampl ng,
  randomSeed: Opt on[Long]) {

  /**
   * Creates a  erator over algor hms and calls next to return a Stream of cand dates
   *
   *
   * @param cand dateS ces t  set of cand date s ces that are be ng sampled
   * @param cand dateS ce  ghts map of cand date s ce to   ght
   * @param cand dates t  map of cand date s ce to t   erator of  s results
   * @param   ght thod a enum to  nd ct wh ch   ght  thod to use. Two values are supported
   * currently. W n   ghtedRandomSampl ng  s set, t  next cand date  s p cked from a cand date
   * s ce that  s randomly chosen. W n   ghtedRoundRob n  s set, t  next cand date  s p cked
   * from current cand date s ce unt l t  number of cand dates reac s to t  ass gned   ght of
   * t  cand date s ce. T  next call of t  funct on w ll return a cand date from t  next
   * cand date s ce wh ch  s after prev ous cand date s ce based on t  order  nput
   * cand date s ce sequence.

   * @return stream of cand dates
   */
  def stream(
    cand dateS ces: Set[A],
    cand dateS ce  ghts: Map[A, Double],
    cand dates: Map[A,  erator[Rec]],
      ght thod:   ght thod =   ghtedRandomSampl ng,
    random: Opt on[Random] = None
  ): Stream[Rec] = {
    val   ghtedCand dateS ce:   ghted[A] = new   ghted[A] {
      overr de def apply(a: A): Double = cand dateS ce  ghts.getOrElse(a, 0)
    }

    /**
     * Generates a stream of cand dates.
     *
     * @param cand dateS ce er an  erator over cand date s ces returned by t  sampl ng procedure
     * @return stream of cand dates
     */
    def next(cand dateS ce er:  erator[A]): Stream[Rec] = {
      val s ce = cand dateS ce er.next()
      val   = cand dates(s ce)
       f ( .hasNext) {
        val currCand =  .next()
        currCand #:: next(cand dateS ce er)
      } else {
        assert(cand dateS ces.conta ns(s ce), "Selected s ce  s not  n cand date s ces")
        // Remove t  depleted cand date s ce and re-sample
        stream(cand dateS ces - s ce, cand dateS ce  ghts, cand dates,   ght thod, random)
      }
    }
     f (cand dateS ces. sEmpty)
      Stream.empty
    else {
      val cand dateS ceSeq = cand dateS ces.toSeq
      val cand dateS ce er =
         f (  ght thod ==   ght thod.  ghtedRoundRob n) {
           rgeUt l.  ghtedRoundRob n(cand dateS ceSeq)(  ghtedCand dateS ce). erator
        } else {
          //default to   ghted random sampl ng  f no ot r   ght  thod  s prov ded
          RandomUt l
            .  ghtedRandomSampl ngW hReplace nt(
              cand dateS ceSeq,
              random
            )(  ghtedCand dateS ce). erator
        }
      next(cand dateS ce er)
    }
  }

  def apply( nput: Map[A, TraversableOnce[Rec]]): Stream[Rec] = {
    stream(
       nput.keySet,
      cand dateS ce  ghts,
       nput.map {
        case (k, v) => k -> v.to erator
      }, // cannot do mapValues  re, as that only returns a v ew
        ght thod,
      randomSeed.map(new Random(_))
    )
  }
}
