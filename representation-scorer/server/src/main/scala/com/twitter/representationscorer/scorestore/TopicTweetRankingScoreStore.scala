package com.tw ter.representat onscorer.scorestore

 mport com.tw ter.s mclusters_v2.score.  ghtedSumAggregatedScoreStore
 mport com.tw ter.s mclusters_v2.score.  ghtedSumAggregatedScoreStore.  ghtedSumAggregatedScorePara ter
 mport com.tw ter.s mclusters_v2.thr ftscala.{Embedd ngType, ModelVers on, Scor ngAlgor hm}

object Top cT etRank ngScoreStore {
  val producerEmbedd ngScoreMult pl er = 1.0
  val consu rEmbedd ngScoreMult pl er = 1.0

  /**
   * Bu ld t  scor ng store for Top cT et Rank ng based on Default Mult pl ers.
   *  f   want to compare t  rank ng bet en d fferent mult pl ers, reg ster a new
   * Scor ngAlgor hm and let t  upstream uses d fferent scor ngAlgor hm by params.
   */
  def bu ldTop cT etRank ngStore(
    consu rEmbedd ngType: Embedd ngType,
    producerEmbedd ngType: Embedd ngType,
    t etEmbedd ngType: Embedd ngType,
    modelVers on: ModelVers on,
    consu rEmbedd ngMult pl er: Double = consu rEmbedd ngScoreMult pl er,
    producerEmbedd ngMult pl er: Double = producerEmbedd ngScoreMult pl er
  ):   ghtedSumAggregatedScoreStore = {
      ghtedSumAggregatedScoreStore(
      L st(
          ghtedSumAggregatedScorePara ter(
          Scor ngAlgor hm.Pa rEmbedd ngCos neS m lar y,
          consu rEmbedd ngMult pl er,
            ghtedSumAggregatedScoreStore.gener cPa rScore dToS mClustersEmbedd ngPa rScore d(
            consu rEmbedd ngType,
            t etEmbedd ngType,
            modelVers on
          )
        ),
          ghtedSumAggregatedScorePara ter(
          Scor ngAlgor hm.Pa rEmbedd ngCos neS m lar y,
          producerEmbedd ngMult pl er,
            ghtedSumAggregatedScoreStore.gener cPa rScore dToS mClustersEmbedd ngPa rScore d(
            producerEmbedd ngType,
            t etEmbedd ngType,
            modelVers on
          )
        )
      )
    )
  }

}
