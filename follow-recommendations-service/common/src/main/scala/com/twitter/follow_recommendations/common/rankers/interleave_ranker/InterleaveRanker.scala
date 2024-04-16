package com.tw ter.follow_recom ndat ons.common.rankers. nterleave_ranker

 mport com.google.common.annotat ons.V s bleForTest ng
 mport com.google. nject. nject
 mport com.google. nject.S ngleton
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.base.Ranker
 mport com.tw ter.follow_recom ndat ons.common.base.StatsUt l
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.rankers.common.Ranker d
 mport com.tw ter.follow_recom ndat ons.common.rankers.ut ls.Ut ls
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams

@S ngleton
class  nterleaveRanker[Target <: HasParams] @ nject() (
  statsRece ver: StatsRece ver)
    extends Ranker[Target, Cand dateUser] {

  val na : Str ng = t .getClass.getS mpleNa 
  pr vate val stats = statsRece ver.scope(" nterleave_ranker")
  pr vate val  nputStats = stats.scope(" nput")
  pr vate val  nterleav ngStats = stats.scope(" nterleave")

  overr de def rank(
    target: Target, 
    cand dates: Seq[Cand dateUser]
  ): St ch[Seq[Cand dateUser]] = {
    StatsUt l.prof leSt ch(
      St ch.value(rankCand dates(target, cand dates)),
      stats.scope("rank")
    )
  }

  pr vate def rankCand dates(
    target: Target,
    cand dates: Seq[Cand dateUser]
  ): Seq[Cand dateUser] = {

    /**
     * By t  stage, all val d cand dates should have:
     *   1. T  r Scores f eld populated.
     *   2. T  r selectedRanker d set.
     *   3. Have a score assoc ated to t  r selectedRanker d.
     *  f t re  s any cand date that doesn't  et t  cond  ons above, t re  s a problem  n one
     * of t  prev ous rankers. S nce no new scor ng  s done  n t  ranker,   s mply remove t m.
     */
    val val dCand dates =
      cand dates.f lter { c =>
        c.scores. sDef ned &&
        c.scores.ex sts(_.selectedRanker d. sDef ned) &&
        getCand dateScoreByRanker d(c, c.scores.flatMap(_.selectedRanker d)). sDef ned
      }

    // To mon or t  percentage of val d cand dates, as def ned above,   track t  follow ng:
     nputStats.counter("cand dates_w h_no_scores"). ncr(cand dates.count(_.scores. sEmpty))
     nputStats
      .counter("cand dates_w h_no_selected_ranker"). ncr(cand dates.count { c =>
        c.scores. sEmpty || c.scores.ex sts(_.selectedRanker d. sEmpty)
      })
     nputStats
      .counter("cand dates_w h_no_score_for_selected_ranker"). ncr(cand dates.count { c =>
        c.scores. sEmpty ||
        c.scores.ex sts(_.selectedRanker d. sEmpty) ||
        getCand dateScoreByRanker d(c, c.scores.flatMap(_.selectedRanker d)). sEmpty
      })
     nputStats.counter("total_num_cand dates"). ncr(cand dates.length)
     nputStats.counter("total_val d_cand dates"). ncr(val dCand dates.length)

    //   only count ranker ds from those cand dates who are val d to exclude those cand dates w h
    // a val d selectedRanker d that don't have an assoc ated score for  .
    val ranker ds = val dCand dates.flatMap(_.scores.flatMap(_.selectedRanker d)).sorted.d st nct
    ranker ds.foreach { ranker d =>
       nputStats
        .counter(s"val d_scores_for_${ranker d.toStr ng}"). ncr(
          cand dates.count(getCand dateScoreByRanker d(_, So (ranker d)). sDef ned))
       nputStats.counter(s"total_cand dates_for_${ranker d.toStr ng}"). ncr(cand dates.length)
    }
     nputStats.counter(s"num_ranker_ ds=${ranker ds.length}"). ncr()
    val scr beRank ng nfo: Boolean =
      target.params( nterleaveRankerParams.Scr beRank ng nfo n nterleaveRanker)
     f (ranker ds.length <= 1)
      //  n t  case of "Number of Ranker ds = 0",   pass on t  cand dates even though t re  s
      // a problem  n a prev ous ranker that prov ded t  scores.
       f (scr beRank ng nfo) Ut ls.addRank ng nfo(cand dates, na ) else cand dates
    else      
       f (scr beRank ng nfo)
        Ut ls.addRank ng nfo( nterleaveCand dates(val dCand dates, ranker ds), na )
      else  nterleaveCand dates(val dCand dates, ranker ds)
  }

  @V s bleForTest ng
  pr vate[ nterleave_ranker] def  nterleaveCand dates(
    cand dates: Seq[Cand dateUser],
    ranker ds: Seq[Ranker d.Ranker d]
  ): Seq[Cand dateUser] = {
    val cand datesW hRank = ranker ds
      .flatMap { ranker =>
        cand dates
        //   f rst sort all cand dates us ng t  ranker.
          .sortBy(-getCand dateScoreByRanker d(_, So (ranker)).getOrElse(Double.M nValue))
          .z pW h ndex.f lter(
            // but only hold those cand dates whose selected ranker  s t  ranker.
            // T se ranks w ll be forced  n t  f nal order ng.
            _._1.scores.flatMap(_.selectedRanker d).conta ns(ranker))
      }

    // Only cand dates who have  s nProducerScor ngExper  nt set to true w ll have t  r pos  on enforced.  
    // separate cand dates  nto two groups: (1) Product on and (2) Exper  nt.
    val (expCand dates, prodCand dates) =
      cand datesW hRank.part  on(_._1.scores.ex sts(_. s nProducerScor ngExper  nt))

    //   resolve (potent al) confl cts bet en t  enforced ranks of exper  ntal models.
    val expCand datesF nalPos = resolveConfl cts(expCand dates)

    // Retr eve non-occup ed pos  ons and ass gn t m to cand dates who use product on ranker.
    val occup edPos = expCand datesF nalPos.map(_._2).toSet
    val prodCand datesF nalPos =
      prodCand dates
        .map(_._1).z p(
          cand dates. nd ces.f lterNot(occup edPos.conta ns).sorted.take(prodCand dates.length))

    //  rge t  two groups and sort t m by t  r correspond ng pos  ons.
    val f nalCand dates = (prodCand datesF nalPos ++ expCand datesF nalPos).sortBy(_._2).map(_._1)

    //   count t  presence of each ranker  n t  top-3 f nal pos  ons.
    f nalCand dates.z p(0 unt l 3).foreach {
      case (c, r) =>
        //   only do so for cand dates that are  n a producer-s de exper  nt.
         f (c.scores.ex sts(_. s nProducerScor ngExper  nt))
          c.scores.flatMap(_.selectedRanker d).map(_.toStr ng).foreach { rankerNa  =>
             nterleav ngStats
              .counter(s"num_f nal_pos  on_${r}_$rankerNa ")
              . ncr()
          }
    }

    f nalCand dates
  }

  @V s bleForTest ng
  pr vate[ nterleave_ranker] def resolveConfl cts(
    cand datesW hRank: Seq[(Cand dateUser,  nt)]
  ): Seq[(Cand dateUser,  nt)] = {
    // T  follow ng two  tr cs w ll allow us to calculate t  rate of confl cts occurr ng.
    // Example:  f overall t re are 10 producers  n d fferent bucket ng exper  nts, and 3 of t m
    // are ass gned to t  sa  pos  on. T  rate would be 3/10, 30%.
    val numCand datesW hConfl cts =  nterleav ngStats.counter("cand dates_w h_confl ct")
    val numCand datesNoConfl cts =  nterleav ngStats.counter("cand dates_w hout_confl ct")
    val cand datesGroupedByRank = cand datesW hRank.groupBy(_._2).toSeq.sortBy(_._1).map {
      case (rank, cand datesW hRank) => (rank, cand datesW hRank.map(_._1))
    }

    cand datesGroupedByRank.foldLeft(Seq[(Cand dateUser,  nt)]()) { (upTo re, nextGroup) =>
      val (rank, cand dates) = nextGroup
       f (cand dates.length > 1)
        numCand datesW hConfl cts. ncr(cand dates.length)
      else
        numCand datesNoConfl cts. ncr()

      //   use t  pos  on after t  last-ass gned cand date as a start ng po nt, or 0 ot rw se.
      //  f cand dates' pos  on  s after t  "start ng po nt",   enforce that pos  on  nstead.
      val m nAva lable ndex = scala.math.max(upTo re.lastOpt on.map(_._2).getOrElse(-1) + 1, rank)
      val enforcedPos =
        (m nAva lable ndex unt l m nAva lable ndex + cand dates.length).toL st
      val shuffledEnforcedPos =
         f (cand dates.length > 1) scala.ut l.Random.shuffle(enforcedPos) else enforcedPos
       f (shuffledEnforcedPos.length > 1) {
        cand dates.z p(shuffledEnforcedPos).sortBy(_._2).map(_._1).z pW h ndex.foreach {
          case (c, r) =>
            c.scores.flatMap(_.selectedRanker d).map(_.toStr ng).foreach { rankerNa  =>
              // For each ranker,   count t  total number of t  s   has been  n a confl ct.
               nterleav ngStats
                .counter(s"num_${shuffledEnforcedPos.length}-way_confl cts_$rankerNa ")
                . ncr()
              //   also count t  pos  ons each of t  rankers have fallen randomly  nto.  n any
              // exper  nt t  should converge to un form d str but on g ven enough occurrences.
              // Note that t  pos  on  re  s relat ve to t  ot r cand dates  n t  confl ct and
              // not t  overall pos  on of each cand date.
               nterleav ngStats
                .counter(
                  s"num_pos  on_${r}_after_${shuffledEnforcedPos.length}-way_confl ct_$rankerNa ")
                . ncr()
            }
        }
      }
      upTo re ++ cand dates.z p(shuffledEnforcedPos).sortBy(_._2)
    }
  }

  @V s bleForTest ng
  pr vate[ nterleave_ranker] def getCand dateScoreByRanker d(
    cand date: Cand dateUser,
    ranker dOpt: Opt on[Ranker d.Ranker d]
  ): Opt on[Double] = {
    ranker dOpt match {
      case None => None
      case So (ranker d) =>
        cand date.scores.flatMap {
          _.scores.f nd(_.ranker d.conta ns(ranker d)).map(_.value)
        }
    }
  }
}
