package com.tw ter.fr gate.pushserv ce.ut l

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.sc o.nsfw_user_seg ntat on.thr ftscala.NSFWUserSeg ntat on

object NsfwPersonal zat onUt l {
  def computeNsfwUserStats(
    targetNsfw nfo: Opt on[Nsfw nfo]
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Un  = {

    def computeNsfwProf leV s Stats(sRece ver: StatsRece ver, nsfwProf leV s s: Long): Un  = {
       f (nsfwProf leV s s >= 1)
        sRece ver.counter("nsfwProf leV s s_gt_1"). ncr()
       f (nsfwProf leV s s >= 2)
        sRece ver.counter("nsfwProf leV s s_gt_2"). ncr()
       f (nsfwProf leV s s >= 3)
        sRece ver.counter("nsfwProf leV s s_gt_3"). ncr()
       f (nsfwProf leV s s >= 5)
        sRece ver.counter("nsfwProf leV s s_gt_5"). ncr()
       f (nsfwProf leV s s >= 8)
        sRece ver.counter("nsfwProf leV s s_gt_8"). ncr()
    }

    def computeRat oStats(
      sRece ver: StatsRece ver,
      rat o:  nt,
      statNa : Str ng,
       ntervals: L st[Double] = L st(0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9)
    ): Un  = {
       ntervals.foreach {   =>
         f (rat o >   * 10000)
          sRece ver.counter(f"${statNa }_greater_than_${ }"). ncr()
      }
    }
    val sRece ver = statsRece ver.scope("nsfw_personal zat on")
    sRece ver.counter("AllUsers"). ncr()

    (targetNsfw nfo) match {
      case (So (nsfw nfo)) =>
        val sens  ve = nsfw nfo.senst veOpt n.getOrElse(false)
        val nsfwFollowRat o =
          nsfw nfo.nsfwFollowRat o
        val totalFollows = nsfw nfo.totalFollowCount
        val numNsfwProf leV s s = nsfw nfo.nsfwProf leV s s
        val nsfwRealGraphScore = nsfw nfo.realGraphScore
        val nsfwSearchScore = nsfw nfo.searchNsfwScore
        val totalSearc s = nsfw nfo.totalSearc s
        val realGraphScore = nsfw nfo.realGraphScore
        val searchScore = nsfw nfo.searchNsfwScore

         f (sens  ve)
          sRece ver.counter("sens  veOpt nEnabled"). ncr()
        else
          sRece ver.counter("sens  veOpt nD sabled"). ncr()

        computeRat oStats(sRece ver, nsfwFollowRat o, "nsfwRat o")
        computeNsfwProf leV s Stats(sRece ver, numNsfwProf leV s s)
        computeRat oStats(sRece ver, nsfwRealGraphScore.to nt, "nsfwRealGraphScore")

         f (totalSearc s >= 10)
          computeRat oStats(sRece ver, nsfwSearchScore.to nt, "nsfwSearchScore")
         f (searchScore == 0)
          sRece ver.counter("lowSearchScore"). ncr()
         f (realGraphScore < 500)
          sRece ver.counter("lowRealScore"). ncr()
         f (numNsfwProf leV s s == 0)
          sRece ver.counter("lowProf leV s "). ncr()
         f (nsfwFollowRat o == 0)
          sRece ver.counter("lowFollowScore"). ncr()

         f (totalSearc s > 10 && searchScore > 5000)
          sRece ver.counter("h ghSearchScore"). ncr()
         f (realGraphScore > 7000)
          sRece ver.counter("h ghRealScore"). ncr()
         f (numNsfwProf leV s s > 5)
          sRece ver.counter("h ghProf leV s "). ncr()
         f (totalFollows > 10 && nsfwFollowRat o > 7000)
          sRece ver.counter("h ghFollowScore"). ncr()

         f (searchScore == 0 && realGraphScore <= 500 && numNsfwProf leV s s == 0 && nsfwFollowRat o == 0)
          sRece ver.counter("low ntent"). ncr()
         f ((totalSearc s > 10 && searchScore > 5000) || realGraphScore > 7000 || numNsfwProf leV s s > 5 || (totalFollows > 10 && nsfwFollowRat o > 7000))
          sRece ver.counter("h gh ntent"). ncr()
      case _ =>
    }
  }
}

case class Nsfw nfo(nsfwUserSeg ntat on: NSFWUserSeg ntat on) {

  val scal ngFactor = 10000 // to convert float to  nt as custom f elds cannot be float
  val senst veOpt n: Opt on[Boolean] = nsfwUserSeg ntat on.nsfwV ew
  val totalFollowCount: Long = nsfwUserSeg ntat on.totalFollowCnt.getOrElse(0L)
  val nsfwFollowCnt: Long =
    nsfwUserSeg ntat on.nsfwAdm nOrH ghprecOrAgathaGtP98FollowsCnt.getOrElse(0L)
  val nsfwFollowRat o:  nt = {
     f (totalFollowCount != 0) {
      (nsfwFollowCnt * scal ngFactor / totalFollowCount).to nt
    } else 0
  }
  val nsfwProf leV s s: Long =
    nsfwUserSeg ntat on.nsfwAdm nOrH ghPrecOrAgathaGtP98V s s
      .map(_.numProf les nLast14Days).getOrElse(0L)
  val realGraphScore:  nt =
    nsfwUserSeg ntat on.realGraph tr cs
      .map { rm =>
         f (rm.totalOutboundRGScore != 0)
          rm.totalNsfwAdmHPAgthGtP98OutboundRGScore * scal ngFactor / rm.totalOutboundRGScore
        else 0d
      }.getOrElse(0d).to nt
  val totalSearc s: Long =
    nsfwUserSeg ntat on.search tr cs.map(_.numNonTrndSrch nLast14Days).getOrElse(0L)
  val searchNsfwScore:  nt = nsfwUserSeg ntat on.search tr cs
    .map { sm =>
       f (sm.numNonTrndNonHshtgSrch nLast14Days != 0)
        sm.numNonTrndNonHshtgGlobalNsfwSrch nLast14Days.toDouble * scal ngFactor / sm.numNonTrndNonHshtgSrch nLast14Days
      else 0
    }.getOrElse(0d).to nt
  val hasReported: Boolean =
    nsfwUserSeg ntat on.not fFeedback tr cs.ex sts(_.not fReport tr cs.ex sts(_.countTotal != 0))
  val hasD sl ked: Boolean =
    nsfwUserSeg ntat on.not fFeedback tr cs
      .ex sts(_.not fD sl ke tr cs.ex sts(_.countTotal != 0))
}
