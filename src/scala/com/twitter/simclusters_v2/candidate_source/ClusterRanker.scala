packagelon com.twittelonr.simclustelonrs_v2.candidatelon_sourcelon

import com.twittelonr.simclustelonrs_v2.thriftscala.UselonrToIntelonrelonstelondInClustelonrScorelons

objelonct ClustelonrRankelonr elonxtelonnds elonnumelonration {
  val RankByNormalizelondFavScorelon: ClustelonrRankelonr.Valuelon = Valuelon
  val RankByFavScorelon: ClustelonrRankelonr.Valuelon = Valuelon
  val RankByFollowScorelon: ClustelonrRankelonr.Valuelon = Valuelon
  val RankByLogFavScorelon: ClustelonrRankelonr.Valuelon = Valuelon
  val RankByNormalizelondLogFavScorelon: ClustelonrRankelonr.Valuelon = Valuelon

  /**
   * Givelonn a map of clustelonrs, sort out thelon top scoring clustelonrs by a ranking schelonmelon
   * providelond by thelon callelonr
   */
  delonf gelontTopKClustelonrsByScorelon(
    clustelonrsWithScorelons: Map[Int, UselonrToIntelonrelonstelondInClustelonrScorelons],
    rankByScorelon: ClustelonrRankelonr.Valuelon,
    topK: Int
  ): Map[Int, Doublelon] = {
    val rankelondClustelonrsWithScorelons = clustelonrsWithScorelons.map {
      caselon (clustelonrId, scorelon) =>
        rankByScorelon match {
          caselon ClustelonrRankelonr.RankByFavScorelon =>
            (clustelonrId, (scorelon.favScorelon.gelontOrelonlselon(0.0), scorelon.followScorelon.gelontOrelonlselon(0.0)))
          caselon ClustelonrRankelonr.RankByFollowScorelon =>
            (clustelonrId, (scorelon.followScorelon.gelontOrelonlselon(0.0), scorelon.favScorelon.gelontOrelonlselon(0.0)))
          caselon ClustelonrRankelonr.RankByLogFavScorelon =>
            (clustelonrId, (scorelon.logFavScorelon.gelontOrelonlselon(0.0), scorelon.followScorelon.gelontOrelonlselon(0.0)))
          caselon ClustelonrRankelonr.RankByNormalizelondLogFavScorelon =>
            (
              clustelonrId,
              (
                scorelon.logFavScorelonClustelonrNormalizelondOnly.gelontOrelonlselon(0.0),
                scorelon.followScorelon.gelontOrelonlselon(0.0)))
          caselon ClustelonrRankelonr.RankByNormalizelondFavScorelon =>
            (
              clustelonrId,
              (
                scorelon.favScorelonProducelonrNormalizelondOnly.gelontOrelonlselon(0.0),
                scorelon.followScorelon.gelontOrelonlselon(0.0)))
          caselon _ =>
            (
              clustelonrId,
              (
                scorelon.favScorelonProducelonrNormalizelondOnly.gelontOrelonlselon(0.0),
                scorelon.followScorelon.gelontOrelonlselon(0.0)))
        }
    }
    rankelondClustelonrsWithScorelons.toSelonq
      .sortBy(_._2) // sort in ascelonnding ordelonr
      .takelonRight(topK)
      .map { caselon (clustelonrId, scorelons) => clustelonrId -> math.max(scorelons._1, 1elon-4) }
      .toMap
  }
}
