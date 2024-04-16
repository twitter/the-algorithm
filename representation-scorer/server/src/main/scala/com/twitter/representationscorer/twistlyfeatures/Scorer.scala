package com.tw ter.representat onscorer.tw stlyfeatures

 mport com.tw ter.f nagle.stats.Counter
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.representat onscorer.common.T et d
 mport com.tw ter.representat onscorer.common.User d
 mport com.tw ter.representat onscorer.scorestore.ScoreStore
 mport com.tw ter.representat onscorer.thr ftscala.S mClustersRecentEngage ntS m lar  es
 mport com.tw ter.s mclusters_v2.thr ftscala.Embedd ngType
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on
 mport com.tw ter.s mclusters_v2.thr ftscala.Score d
 mport com.tw ter.s mclusters_v2.thr ftscala.Score nternal d
 mport com.tw ter.s mclusters_v2.thr ftscala.Scor ngAlgor hm
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersEmbedd ng d
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersEmbedd ngPa rScore d
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject

class Scorer @ nject() (
  fetchEngage ntsFromUSS: Long => St ch[Engage nts],
  scoreStore: ScoreStore,
  stats: StatsRece ver) {

   mport Scorer._

  pr vate val scoreStats = stats.scope("score")
  pr vate val scoreCalculat onStats = scoreStats.scope("calculat on")
  pr vate val scoreResultStats = scoreStats.scope("result")

  pr vate val scoresNonEmptyCounter = scoreResultStats.scope("all").counter("nonEmpty")
  pr vate val scoresNonZeroCounter = scoreResultStats.scope("all").counter("nonZero")

  pr vate val t etScoreStats = scoreCalculat onStats.scope("t etScore").stat("latency")
  pr vate val userScoreStats = scoreCalculat onStats.scope("userScore").stat("latency")

  pr vate val favNonZero = scoreResultStats.scope("favs").counter("nonZero")
  pr vate val favNonEmpty = scoreResultStats.scope("favs").counter("nonEmpty")

  pr vate val ret etsNonZero = scoreResultStats.scope("ret ets").counter("nonZero")
  pr vate val ret etsNonEmpty = scoreResultStats.scope("ret ets").counter("nonEmpty")

  pr vate val followsNonZero = scoreResultStats.scope("follows").counter("nonZero")
  pr vate val followsNonEmpty = scoreResultStats.scope("follows").counter("nonEmpty")

  pr vate val sharesNonZero = scoreResultStats.scope("shares").counter("nonZero")
  pr vate val sharesNonEmpty = scoreResultStats.scope("shares").counter("nonEmpty")

  pr vate val repl esNonZero = scoreResultStats.scope("repl es").counter("nonZero")
  pr vate val repl esNonEmpty = scoreResultStats.scope("repl es").counter("nonEmpty")

  pr vate val or g nalT etsNonZero = scoreResultStats.scope("or g nalT ets").counter("nonZero")
  pr vate val or g nalT etsNonEmpty = scoreResultStats.scope("or g nalT ets").counter("nonEmpty")

  pr vate val v deoV ewsNonZero = scoreResultStats.scope("v deoV ews").counter("nonZero")
  pr vate val v deoV ewsNonEmpty = scoreResultStats.scope("v deoV ews").counter("nonEmpty")

  pr vate val blockNonZero = scoreResultStats.scope("block").counter("nonZero")
  pr vate val blockNonEmpty = scoreResultStats.scope("block").counter("nonEmpty")

  pr vate val muteNonZero = scoreResultStats.scope("mute").counter("nonZero")
  pr vate val muteNonEmpty = scoreResultStats.scope("mute").counter("nonEmpty")

  pr vate val reportNonZero = scoreResultStats.scope("report").counter("nonZero")
  pr vate val reportNonEmpty = scoreResultStats.scope("report").counter("nonEmpty")

  pr vate val dontl keNonZero = scoreResultStats.scope("dontl ke").counter("nonZero")
  pr vate val dontl keNonEmpty = scoreResultStats.scope("dontl ke").counter("nonEmpty")

  pr vate val seeFe rNonZero = scoreResultStats.scope("seeFe r").counter("nonZero")
  pr vate val seeFe rNonEmpty = scoreResultStats.scope("seeFe r").counter("nonEmpty")

  pr vate def getT etScores(
    cand dateT et d: T et d,
    s ceT et ds: Seq[T et d]
  ): St ch[Seq[ScoreResult]] = {
    val getScoresSt ch = St ch.traverse(s ceT et ds) { s ceT et d =>
      scoreStore
        .un formScor ngStoreSt ch(getT etScore d(s ceT et d, cand dateT et d))
        .l ftNotFoundToOpt on
        .map(score => ScoreResult(s ceT et d, score.map(_.score)))
    }

    St ch.t  (getScoresSt ch).flatMap {
      case (tryResult, durat on) =>
        t etScoreStats.add(durat on. nM ll s)
        St ch.const(tryResult)
    }
  }

  pr vate def getUserScores(
    t et d: T et d,
    author ds: Seq[User d]
  ): St ch[Seq[ScoreResult]] = {
    val getScoresSt ch = St ch.traverse(author ds) { author d =>
      scoreStore
        .un formScor ngStoreSt ch(getAuthorScore d(author d, t et d))
        .l ftNotFoundToOpt on
        .map(score => ScoreResult(author d, score.map(_.score)))
    }

    St ch.t  (getScoresSt ch).flatMap {
      case (tryResult, durat on) =>
        userScoreStats.add(durat on. nM ll s)
        St ch.const(tryResult)
    }
  }

  /**
   * Get t  [[S mClustersRecentEngage ntS m lar  es]] result conta n ng t  s m lar y
   * features for t  g ven user d-T et d.
   */
  def get(
    user d: User d,
    t et d: T et d
  ): St ch[S mClustersRecentEngage ntS m lar  es] = {
    get(user d, Seq(t et d)).map(x => x. ad)
  }

  /**
   * Get a l st of [[S mClustersRecentEngage ntS m lar  es]] results conta n ng t  s m lar y
   * features for t  g ven t ets of t  user  d.
   * Guaranteed to be t  sa  number/order as requested.
   */
  def get(
    user d: User d,
    t et ds: Seq[T et d]
  ): St ch[Seq[S mClustersRecentEngage ntS m lar  es]] = {
    fetchEngage ntsFromUSS(user d)
      .flatMap(engage nts => {
        // For each t et rece ved  n t  request, compute t  s m lar y scores bet en t m
        // and t  user s gnals fetc d from USS.
        St ch
          .jo n(
            St ch.traverse(t et ds)( d => getT etScores( d, engage nts.t et ds)),
            St ch.traverse(t et ds)( d => getUserScores( d, engage nts.author ds)),
          )
          .map {
            case (t etScoresSeq, userScoreSeq) =>
              // All seq have = s ze because w n scores don't ex st, t y are returned as Opt on
              (t etScoresSeq, userScoreSeq).z pped.map { (t etScores, userScores) =>
                computeS m lar yScoresPerT et(
                  engage nts,
                  t etScores.groupBy(_. d),
                  userScores.groupBy(_. d))
              }
          }
      })
  }

  /**
   *
   * Computes t  [[S mClustersRecentEngage ntS m lar  es]]
   * us ng t  g ven t et-t et and user-t et scores  n T etScoresMap
   * and t  user s gnals  n [[Engage nts]].
   */
  pr vate def computeS m lar yScoresPerT et(
    engage nts: Engage nts,
    t etScores: Map[T et d, Seq[ScoreResult]],
    authorScores: Map[User d, Seq[ScoreResult]]
  ): S mClustersRecentEngage ntS m lar  es = {
    val favs7d = engage nts.favs7d.v ew
      .flatMap(s => t etScores.get(s.target d))
      .flatten.flatMap(_.score)
      .force

    val favs1d = engage nts.favs1d.v ew
      .flatMap(s => t etScores.get(s.target d))
      .flatten.flatMap(_.score)
      .force

    val ret ets7d = engage nts.ret ets7d.v ew
      .flatMap(s => t etScores.get(s.target d))
      .flatten.flatMap(_.score)
      .force

    val ret ets1d = engage nts.ret ets1d.v ew
      .flatMap(s => t etScores.get(s.target d))
      .flatten.flatMap(_.score)
      .force

    val follows30d = engage nts.follows30d.v ew
      .flatMap(s => authorScores.get(s.target d))
      .flatten.flatMap(_.score)
      .force

    val follows7d = engage nts.follows7d.v ew
      .flatMap(s => authorScores.get(s.target d))
      .flatten.flatMap(_.score)
      .force

    val shares7d = engage nts.shares7d.v ew
      .flatMap(s => t etScores.get(s.target d))
      .flatten.flatMap(_.score)
      .force

    val shares1d = engage nts.shares1d.v ew
      .flatMap(s => t etScores.get(s.target d))
      .flatten.flatMap(_.score)
      .force

    val repl es7d = engage nts.repl es7d.v ew
      .flatMap(s => t etScores.get(s.target d))
      .flatten.flatMap(_.score)
      .force

    val repl es1d = engage nts.repl es1d.v ew
      .flatMap(s => t etScores.get(s.target d))
      .flatten.flatMap(_.score)
      .force

    val or g nalT ets7d = engage nts.or g nalT ets7d.v ew
      .flatMap(s => t etScores.get(s.target d))
      .flatten.flatMap(_.score)
      .force

    val or g nalT ets1d = engage nts.or g nalT ets1d.v ew
      .flatMap(s => t etScores.get(s.target d))
      .flatten.flatMap(_.score)
      .force

    val v deoV ews7d = engage nts.v deoPlaybacks7d.v ew
      .flatMap(s => t etScores.get(s.target d))
      .flatten.flatMap(_.score)
      .force

    val v deoV ews1d = engage nts.v deoPlaybacks1d.v ew
      .flatMap(s => t etScores.get(s.target d))
      .flatten.flatMap(_.score)
      .force

    val block30d = engage nts.block30d.v ew
      .flatMap(s => t etScores.get(s.target d))
      .flatten.flatMap(_.score)
      .force

    val block7d = engage nts.block7d.v ew
      .flatMap(s => t etScores.get(s.target d))
      .flatten.flatMap(_.score)
      .force

    val block1d = engage nts.block1d.v ew
      .flatMap(s => t etScores.get(s.target d))
      .flatten.flatMap(_.score)
      .force

    val mute30d = engage nts.mute30d.v ew
      .flatMap(s => t etScores.get(s.target d))
      .flatten.flatMap(_.score)
      .force

    val mute7d = engage nts.mute7d.v ew
      .flatMap(s => t etScores.get(s.target d))
      .flatten.flatMap(_.score)
      .force

    val mute1d = engage nts.mute1d.v ew
      .flatMap(s => t etScores.get(s.target d))
      .flatten.flatMap(_.score)
      .force

    val report30d = engage nts.report30d.v ew
      .flatMap(s => t etScores.get(s.target d))
      .flatten.flatMap(_.score)
      .force

    val report7d = engage nts.report7d.v ew
      .flatMap(s => t etScores.get(s.target d))
      .flatten.flatMap(_.score)
      .force

    val report1d = engage nts.report1d.v ew
      .flatMap(s => t etScores.get(s.target d))
      .flatten.flatMap(_.score)
      .force

    val dontl ke30d = engage nts.dontl ke30d.v ew
      .flatMap(s => t etScores.get(s.target d))
      .flatten.flatMap(_.score)
      .force

    val dontl ke7d = engage nts.dontl ke7d.v ew
      .flatMap(s => t etScores.get(s.target d))
      .flatten.flatMap(_.score)
      .force

    val dontl ke1d = engage nts.dontl ke1d.v ew
      .flatMap(s => t etScores.get(s.target d))
      .flatten.flatMap(_.score)
      .force

    val seeFe r30d = engage nts.seeFe r30d.v ew
      .flatMap(s => t etScores.get(s.target d))
      .flatten.flatMap(_.score)
      .force

    val seeFe r7d = engage nts.seeFe r7d.v ew
      .flatMap(s => t etScores.get(s.target d))
      .flatten.flatMap(_.score)
      .force

    val seeFe r1d = engage nts.seeFe r1d.v ew
      .flatMap(s => t etScores.get(s.target d))
      .flatten.flatMap(_.score)
      .force

    val result = S mClustersRecentEngage ntS m lar  es(
      fav1dLast10Max = max(favs1d),
      fav1dLast10Avg = avg(favs1d),
      fav7dLast10Max = max(favs7d),
      fav7dLast10Avg = avg(favs7d),
      ret et1dLast10Max = max(ret ets1d),
      ret et1dLast10Avg = avg(ret ets1d),
      ret et7dLast10Max = max(ret ets7d),
      ret et7dLast10Avg = avg(ret ets7d),
      follow7dLast10Max = max(follows7d),
      follow7dLast10Avg = avg(follows7d),
      follow30dLast10Max = max(follows30d),
      follow30dLast10Avg = avg(follows30d),
      share1dLast10Max = max(shares1d),
      share1dLast10Avg = avg(shares1d),
      share7dLast10Max = max(shares7d),
      share7dLast10Avg = avg(shares7d),
      reply1dLast10Max = max(repl es1d),
      reply1dLast10Avg = avg(repl es1d),
      reply7dLast10Max = max(repl es7d),
      reply7dLast10Avg = avg(repl es7d),
      or g nalT et1dLast10Max = max(or g nalT ets1d),
      or g nalT et1dLast10Avg = avg(or g nalT ets1d),
      or g nalT et7dLast10Max = max(or g nalT ets7d),
      or g nalT et7dLast10Avg = avg(or g nalT ets7d),
      v deoPlayback1dLast10Max = max(v deoV ews1d),
      v deoPlayback1dLast10Avg = avg(v deoV ews1d),
      v deoPlayback7dLast10Max = max(v deoV ews7d),
      v deoPlayback7dLast10Avg = avg(v deoV ews7d),
      block1dLast10Max = max(block1d),
      block1dLast10Avg = avg(block1d),
      block7dLast10Max = max(block7d),
      block7dLast10Avg = avg(block7d),
      block30dLast10Max = max(block30d),
      block30dLast10Avg = avg(block30d),
      mute1dLast10Max = max(mute1d),
      mute1dLast10Avg = avg(mute1d),
      mute7dLast10Max = max(mute7d),
      mute7dLast10Avg = avg(mute7d),
      mute30dLast10Max = max(mute30d),
      mute30dLast10Avg = avg(mute30d),
      report1dLast10Max = max(report1d),
      report1dLast10Avg = avg(report1d),
      report7dLast10Max = max(report7d),
      report7dLast10Avg = avg(report7d),
      report30dLast10Max = max(report30d),
      report30dLast10Avg = avg(report30d),
      dontl ke1dLast10Max = max(dontl ke1d),
      dontl ke1dLast10Avg = avg(dontl ke1d),
      dontl ke7dLast10Max = max(dontl ke7d),
      dontl ke7dLast10Avg = avg(dontl ke7d),
      dontl ke30dLast10Max = max(dontl ke30d),
      dontl ke30dLast10Avg = avg(dontl ke30d),
      seeFe r1dLast10Max = max(seeFe r1d),
      seeFe r1dLast10Avg = avg(seeFe r1d),
      seeFe r7dLast10Max = max(seeFe r7d),
      seeFe r7dLast10Avg = avg(seeFe r7d),
      seeFe r30dLast10Max = max(seeFe r30d),
      seeFe r30dLast10Avg = avg(seeFe r30d),
    )
    trackStats(result)
    result
  }

  pr vate def trackStats(result: S mClustersRecentEngage ntS m lar  es): Un  = {
    val scores = Seq(
      result.fav7dLast10Max,
      result.ret et7dLast10Max,
      result.follow30dLast10Max,
      result.share1dLast10Max,
      result.share7dLast10Max,
      result.reply7dLast10Max,
      result.or g nalT et7dLast10Max,
      result.v deoPlayback7dLast10Max,
      result.block30dLast10Max,
      result.mute30dLast10Max,
      result.report30dLast10Max,
      result.dontl ke30dLast10Max,
      result.seeFe r30dLast10Max
    )

    val nonEmpty = scores.ex sts(_. sDef ned)
    val nonZero = scores.ex sts { case So (score)  f score > 0 => true; case _ => false }

     f (nonEmpty) {
      scoresNonEmptyCounter. ncr()
    }

     f (nonZero) {
      scoresNonZeroCounter. ncr()
    }

    //   use t  largest w ndow of a g ven type of score,
    // because t  largest w ndow  s  nclus ve of smaller w ndows.
    trackS gnalStats(favNonEmpty, favNonZero, result.fav7dLast10Avg)
    trackS gnalStats(ret etsNonEmpty, ret etsNonZero, result.ret et7dLast10Avg)
    trackS gnalStats(followsNonEmpty, followsNonZero, result.follow30dLast10Avg)
    trackS gnalStats(sharesNonEmpty, sharesNonZero, result.share7dLast10Avg)
    trackS gnalStats(repl esNonEmpty, repl esNonZero, result.reply7dLast10Avg)
    trackS gnalStats(or g nalT etsNonEmpty, or g nalT etsNonZero, result.or g nalT et7dLast10Avg)
    trackS gnalStats(v deoV ewsNonEmpty, v deoV ewsNonZero, result.v deoPlayback7dLast10Avg)
    trackS gnalStats(blockNonEmpty, blockNonZero, result.block30dLast10Avg)
    trackS gnalStats(muteNonEmpty, muteNonZero, result.mute30dLast10Avg)
    trackS gnalStats(reportNonEmpty, reportNonZero, result.report30dLast10Avg)
    trackS gnalStats(dontl keNonEmpty, dontl keNonZero, result.dontl ke30dLast10Avg)
    trackS gnalStats(seeFe rNonEmpty, seeFe rNonZero, result.seeFe r30dLast10Avg)
  }

  pr vate def trackS gnalStats(nonEmpty: Counter, nonZero: Counter, score: Opt on[Double]): Un  = {
     f (score.nonEmpty) {
      nonEmpty. ncr()

       f (score.get > 0)
        nonZero. ncr()
    }
  }
}

object Scorer {
  def avg(s: Traversable[Double]): Opt on[Double] =
     f (s. sEmpty) None else So (s.sum / s.s ze)
  def max(s: Traversable[Double]): Opt on[Double] =
     f (s. sEmpty) None else So (s.foldLeft(0.0D) { (curr, _max) => math.max(curr, _max) })

  pr vate def getAuthorScore d(
    user d: User d,
    t et d: T et d
  ) = {
    Score d(
      algor hm = Scor ngAlgor hm.Pa rEmbedd ngCos neS m lar y,
       nternal d = Score nternal d.S mClustersEmbedd ngPa rScore d(
        S mClustersEmbedd ngPa rScore d(
          S mClustersEmbedd ng d(
             nternal d =  nternal d.User d(user d),
            modelVers on = ModelVers on.Model20m145k2020,
            embedd ngType = Embedd ngType.FavBasedProducer
          ),
          S mClustersEmbedd ng d(
             nternal d =  nternal d.T et d(t et d),
            modelVers on = ModelVers on.Model20m145k2020,
            embedd ngType = Embedd ngType.LogFavBasedT et
          )
        ))
    )
  }

  pr vate def getT etScore d(
    s ceT et d: T et d,
    cand dateT et d: T et d
  ) = {
    Score d(
      algor hm = Scor ngAlgor hm.Pa rEmbedd ngCos neS m lar y,
       nternal d = Score nternal d.S mClustersEmbedd ngPa rScore d(
        S mClustersEmbedd ngPa rScore d(
          S mClustersEmbedd ng d(
             nternal d =  nternal d.T et d(s ceT et d),
            modelVers on = ModelVers on.Model20m145k2020,
            embedd ngType = Embedd ngType.LogFavLongestL2Embedd ngT et
          ),
          S mClustersEmbedd ng d(
             nternal d =  nternal d.T et d(cand dateT et d),
            modelVers on = ModelVers on.Model20m145k2020,
            embedd ngType = Embedd ngType.LogFavBasedT et
          )
        ))
    )
  }
}
