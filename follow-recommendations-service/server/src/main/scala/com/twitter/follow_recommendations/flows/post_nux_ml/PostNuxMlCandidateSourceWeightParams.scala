package com.tw ter.follow_recom ndat ons.flows.post_nux_ml

 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .Param

abstract class PostNuxMlCand dateS ce  ghtParams[A](default: A) extends Param[A](default) {
  overr de val statNa : Str ng = "post_nux_ml/" + t .getClass.getS mpleNa 
}

object PostNuxMlCand dateS ce  ghtParams {

  case object Cand date  ghtCrowdSearch
      extends FSBoundedParam[Double](
        PostNuxMlFlowCand dateS ce  ghtsFeatureSw chKeys.Cand date  ghtCrowdSearch,
        1.0,
        0.0,
        1000.0
      )

  case object Cand date  ghtTopOrgan cFollow
      extends FSBoundedParam[Double](
        PostNuxMlFlowCand dateS ce  ghtsFeatureSw chKeys.Cand date  ghtTopOrgan cFollow,
        1.0,
        0.0,
        1000.0
      )
  case object Cand date  ghtPPM LocaleFollow
      extends FSBoundedParam[Double](
        PostNuxMlFlowCand dateS ce  ghtsFeatureSw chKeys.Cand date  ghtPPM LocaleFollow,
        1.0,
        0.0,
        1000.0
      )

  case object Cand date  ghtForwardEma lBook
      extends FSBoundedParam[Double](
        PostNuxMlFlowCand dateS ce  ghtsFeatureSw chKeys.Cand date  ghtForwardEma lBook,
        1.0,
        0.0,
        1000.0
      )
  case object Cand date  ghtForwardPhoneBook
      extends FSBoundedParam[Double](
        PostNuxMlFlowCand dateS ce  ghtsFeatureSw chKeys.Cand date  ghtForwardPhoneBook,
        1.0,
        0.0,
        1000.0
      )

  case object Cand date  ghtOffl neStrongT ePred ct on
      extends FSBoundedParam[Double](
        PostNuxMlFlowCand dateS ce  ghtsFeatureSw chKeys.Cand date  ghtOffl neStrongT ePred ct on,
        1.0,
        0.0,
        1000.0
      )
  case object Cand date  ghtOnl neStp
      extends FSBoundedParam[Double](
        PostNuxMlFlowCand dateS ce  ghtsFeatureSw chKeys.Cand date  ghtOnl neStp,
        1.0,
        0.0,
        1000.0
      )
  case object Cand date  ghtPopCountry
      extends FSBoundedParam[Double](
        PostNuxMlFlowCand dateS ce  ghtsFeatureSw chKeys.Cand date  ghtPopCountry,
        1.0,
        0.0,
        1000.0
      )
  case object Cand date  ghtPopGeohash
      extends FSBoundedParam[Double](
        PostNuxMlFlowCand dateS ce  ghtsFeatureSw chKeys.Cand date  ghtPopGeohash,
        1.0,
        0.0,
        1000.0
      )
  case object Cand date  ghtPopGeohashQual yFollow
      extends FSBoundedParam[Double](
        PostNuxMlFlowCand dateS ce  ghtsFeatureSw chKeys.Cand date  ghtPopGeohashQual yFollow,
        1.0,
        0.0,
        1000.0
      )
  case object Cand date  ghtPopGeoBackf ll
      extends FSBoundedParam[Double](
        PostNuxMlFlowCand dateS ce  ghtsFeatureSw chKeys.Cand date  ghtPopGeoBackf ll,
        1,
        0.0,
        1000.0
      )
  case object Cand date  ghtRecentFollow ngS m larUsers
      extends FSBoundedParam[Double](
        PostNuxMlFlowCand dateS ce  ghtsFeatureSw chKeys.Cand date  ghtRecentFollow ngS m larUsers,
        1.0,
        0.0,
        1000.0
      )
  case object Cand date  ghtRecentEngage ntD rectFollowSalsaExpans on
      extends FSBoundedParam[Double](
        PostNuxMlFlowCand dateS ce  ghtsFeatureSw chKeys.Cand date  ghtRecentEngage ntD rectFollowSalsaExpans on,
        1.0,
        0.0,
        1000.0
      )
  case object Cand date  ghtRecentEngage ntNonD rectFollow
      extends FSBoundedParam[Double](
        PostNuxMlFlowCand dateS ce  ghtsFeatureSw chKeys.Cand date  ghtRecentEngage ntNonD rectFollow,
        1.0,
        0.0,
        1000.0
      )
  case object Cand date  ghtRecentEngage ntS m larUsers
      extends FSBoundedParam[Double](
        PostNuxMlFlowCand dateS ce  ghtsFeatureSw chKeys.Cand date  ghtRecentEngage ntS m larUsers,
        1.0,
        0.0,
        1000.0
      )
  case object Cand date  ghtRepeatedProf leV s s
      extends FSBoundedParam[Double](
        PostNuxMlFlowCand dateS ce  ghtsFeatureSw chKeys.Cand date  ghtRepeatedProf leV s s,
        1.0,
        0.0,
        1000.0
      )
  case object Cand date  ghtFollow2vecNearestNe ghbors
      extends FSBoundedParam[Double](
        PostNuxMlFlowCand dateS ce  ghtsFeatureSw chKeys.Cand date  ghtFollow2vecNearestNe ghbors,
        1.0,
        0.0,
        1000.0
      )
  case object Cand date  ghtReverseEma lBook
      extends FSBoundedParam[Double](
        PostNuxMlFlowCand dateS ce  ghtsFeatureSw chKeys.Cand date  ghtReverseEma lBook,
        1.0,
        0.0,
        1000.0
      )
  case object Cand date  ghtReversePhoneBook
      extends FSBoundedParam[Double](
        PostNuxMlFlowCand dateS ce  ghtsFeatureSw chKeys.Cand date  ghtReversePhoneBook,
        1.0,
        0.0,
        1000.0
      )
  case object Cand date  ghtTr angularLoops
      extends FSBoundedParam[Double](
        PostNuxMlFlowCand dateS ce  ghtsFeatureSw chKeys.Cand date  ghtTr angularLoops,
        1.0,
        0.0,
        1000.0
      )
  case object Cand date  ghtTwoHopRandomWalk
      extends FSBoundedParam[Double](
        PostNuxMlFlowCand dateS ce  ghtsFeatureSw chKeys.Cand date  ghtTwoHopRandomWalk,
        1.0,
        0.0,
        1000.0
      )
  case object Cand date  ghtUserUserGraph
      extends FSBoundedParam[Double](
        PostNuxMlFlowCand dateS ce  ghtsFeatureSw chKeys.Cand date  ghtUserUserGraph,
        1.0,
        0.0,
        1000.0
      )

  case object Cand date  ghtRealGraphOonV2
      extends FSBoundedParam[Double](
        PostNuxMlFlowCand dateS ce  ghtsFeatureSw chKeys.Cand date  ghtRealGraphOonV2,
        1.0,
        0.0,
        2000.0
      )
}
