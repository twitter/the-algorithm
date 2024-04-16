package com.tw ter.follow_recom ndat ons.common.rankers.f rst_n_ranker

 mport com.google. nject. nject
 mport com.google. nject.S ngleton
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.base.Ranker
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.HasQual yFactor
 mport com.tw ter.follow_recom ndat ons.common.rankers.ut ls.Ut ls
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams

/**
 * T  class  s  ant to f lter cand dates bet en stages of   ranker by tak ng t  f rst N
 * cand dates,  rg ng any cand date s ce  nformat on for cand dates w h mult ple entr es.
 * To allow us to cha n t  truncat on operat on any number of t  s sequent ally w h n t  ma n
 * rank ng bu lder,   abstract t  truncat on as a separate Ranker
 */
@S ngleton
class F rstNRanker[Target <: HasCl entContext w h HasParams w h HasQual yFactor] @ nject() (
  stats: StatsRece ver)
    extends Ranker[Target, Cand dateUser] {

  val na : Str ng = t .getClass.getS mpleNa 
  pr vate val baseStats = stats.scope("f rst_n_ranker")
  val scaledDownByQual yFactorCounter =
    baseStats.counter("scaled_down_by_qual y_factor")
  pr vate val  rgeStat = baseStats.scope(" rged_cand dates")
  pr vate val  rgeStat2 =  rgeStat.counter("2")
  pr vate val  rgeStat3 =  rgeStat.counter("3")
  pr vate val  rgeStat4 =  rgeStat.counter("4+")
  pr vate val cand dateS zeStats = baseStats.scope("cand date_s ze")

  pr vate case class Cand dateS ceScore(
    cand date d: Long,
    s ce d: Cand dateS ce dent f er,
    score: Opt on[Double])

  /**
   * Adds t  rank of each cand date based on t  pr mary cand date s ce's score.
   *  n t  event w re t  prov ded order ng of cand dates do not al gn w h t  score,
   *   w ll respect t  score, s nce t  order ng m ght have been m xed up due to ot r prev ous
   * steps l ke t  shuffleFn  n t  `  ghtedCand dateS ceRanker`.
   * @param cand dates  ordered l st of cand dates
   * @return            sa  ordered l st of cand dates, but w h t  rank  nformat on appended
   */
  def addRank(cand dates: Seq[Cand dateUser]): Seq[Cand dateUser] = {
    val cand dateS ceRanks = for {
      (s ce dOpt, s ceCand dates) <- cand dates.groupBy(_.getPr maryCand dateS ce)
      (cand date, rank) <- s ceCand dates.sortBy(-_.score.getOrElse(0.0)).z pW h ndex
    } y eld {
      (cand date, s ce dOpt) -> rank
    }
    cand dates.map { c =>
      c.getPr maryCand dateS ce
        .map { s ce d =>
          val s ceRank = cand dateS ceRanks((c, c.getPr maryCand dateS ce))
          c.addCand dateS ceRanksMap(Map(s ce d -> s ceRank))
        }.getOrElse(c)
    }
  }

  overr de def rank(target: Target, cand dates: Seq[Cand dateUser]): St ch[Seq[Cand dateUser]] = {

    val scaleDownFactor = Math.max(
      target.qual yFactor.getOrElse(1.0d),
      target.params(F rstNRankerParams.M nNumCand datesScoredScaleDownFactor)
    )

     f (scaleDownFactor < 1.0d)
      scaledDownByQual yFactorCounter. ncr()

    val n = (target.params(F rstNRankerParams.Cand datesToRank) * scaleDownFactor).to nt
    val scr beRank ng nfo: Boolean =
      target.params(F rstNRankerParams.Scr beRank ng nfo nF rstNRanker)
    cand dateS zeStats.counter(s"n$n"). ncr()
    val cand datesW hRank = addRank(cand dates)
     f (target.params(F rstNRankerParams.GroupDupl cateCand dates)) {
      val groupedCand dates: Map[Long, Seq[Cand dateUser]] = cand datesW hRank.groupBy(_. d)
      val topN = cand dates
        .map { c =>
           rge(groupedCand dates(c. d))
        }.d st nct.take(n)
      St ch.value( f (scr beRank ng nfo) Ut ls.addRank ng nfo(topN, na ) else topN)
    } else {
      St ch.value(
         f (scr beRank ng nfo) Ut ls.addRank ng nfo(cand datesW hRank, na ).take(n)
        else cand datesW hRank.take(n))
    } // for eff c ency,  f don't need to dedupl cate
  }

  /**
   *   use t  pr mary cand date s ce of t  f rst entry, and aggregate all of t  ot r entr es'
   * cand date s ce scores  nto t  f rst entry's cand dateS ceScores
   * @param cand dates l st of cand dates w h t  sa   d
   * @return           a s ngle  rged cand date
   */
  pr vate[f rst_n_ranker] def  rge(cand dates: Seq[Cand dateUser]): Cand dateUser = {
     f (cand dates.s ze == 1) {
      cand dates. ad
    } else {
      cand dates.s ze match {
        case 2 =>  rgeStat2. ncr()
        case 3 =>  rgeStat3. ncr()
        case    f   >= 4 =>  rgeStat4. ncr()
        case _ =>
      }
      val allS ces = cand dates.flatMap(_.getCand dateS ces).toMap
      val allRanks = cand dates.flatMap(_.getCand dateRanks).toMap
      cand dates. ad.addCand dateS ceScoresMap(allS ces).addCand dateS ceRanksMap(allRanks)
    }
  }
}
