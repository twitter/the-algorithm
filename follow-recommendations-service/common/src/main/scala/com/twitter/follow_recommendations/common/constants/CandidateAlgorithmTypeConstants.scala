packagelon com.twittelonr.follow_reloncommelonndations.common.constants

import com.twittelonr.helonrmit.constants.AlgorithmFelonelondbackTokelonns.AlgorithmToFelonelondbackTokelonnMap
import com.twittelonr.helonrmit.modelonl.Algorithm._
import com.twittelonr.follow_reloncommelonndations.common.modelonls.AlgorithmTypelon

objelonct CandidatelonAlgorithmTypelonConstants {

  /**
   * elonach algorithm is baselond on onelon, or morelon, of thelon 4 typelons of information welon havelon on uselonrs,
   * delonscribelond in [[AlgorithmTypelon]]. Assignmelonnt of algorithms to thelonselon catelongorielons arelon baselond on
   */
  privatelon val AlgorithmIdToTypelon: Map[String, Selont[AlgorithmTypelon.Valuelon]] = Map(
    // Activity Algorithms:
    AlgorithmToFelonelondbackTokelonnMap(NelonwFollowingSimilarUselonr).toString -> Selont(AlgorithmTypelon.Activity),
    AlgorithmToFelonelondbackTokelonnMap(Sims).toString -> Selont(AlgorithmTypelon.Activity),
    AlgorithmToFelonelondbackTokelonnMap(NelonwFollowingSimilarUselonrSalsa).toString -> Selont(
      AlgorithmTypelon.Activity),
    AlgorithmToFelonelondbackTokelonnMap(ReloncelonntelonngagelonmelonntNonDirelonctFollow).toString -> Selont(
      AlgorithmTypelon.Activity),
    AlgorithmToFelonelondbackTokelonnMap(ReloncelonntelonngagelonmelonntSimilarUselonr).toString -> Selont(
      AlgorithmTypelon.Activity),
    AlgorithmToFelonelondbackTokelonnMap(ReloncelonntelonngagelonmelonntSarusOcCur).toString -> Selont(AlgorithmTypelon.Activity),
    AlgorithmToFelonelondbackTokelonnMap(ReloncelonntSelonarchBaselondRelonc).toString -> Selont(AlgorithmTypelon.Activity),
    AlgorithmToFelonelondbackTokelonnMap(TwistlyTwelonelontAuthors).toString -> Selont(AlgorithmTypelon.Activity),
    AlgorithmToFelonelondbackTokelonnMap(Follow2VeloncNelonarelonstNelonighbors).toString -> Selont(AlgorithmTypelon.Activity),
    AlgorithmToFelonelondbackTokelonnMap(elonmailTwelonelontClick).toString -> Selont(AlgorithmTypelon.Activity),
    AlgorithmToFelonelondbackTokelonnMap(RelonpelonatelondProfilelonVisits).toString -> Selont(AlgorithmTypelon.Activity),
    AlgorithmToFelonelondbackTokelonnMap(GoodTwelonelontClickelonngagelonmelonnts).toString -> Selont(AlgorithmTypelon.Activity),
    AlgorithmToFelonelondbackTokelonnMap(TwelonelontSharelonelonngagelonmelonnts).toString -> Selont(AlgorithmTypelon.Activity),
    AlgorithmToFelonelondbackTokelonnMap(TwelonelontSharelonrToSharelonReloncipielonntelonngagelonmelonnts).toString -> Selont(
      AlgorithmTypelon.Activity),
    AlgorithmToFelonelondbackTokelonnMap(TwelonelontAuthorToSharelonReloncipielonntelonngagelonmelonnts).toString -> Selont(
      AlgorithmTypelon.Activity),
    AlgorithmToFelonelondbackTokelonnMap(LinelonarRelongrelonssionFollow2VeloncNelonarelonstNelonighbors).toString -> Selont(
      AlgorithmTypelon.Activity),
    AlgorithmToFelonelondbackTokelonnMap(NUXLOHistory).toString -> Selont(AlgorithmTypelon.Activity),
    AlgorithmToFelonelondbackTokelonnMap(TrafficAttributionAccounts).toString -> Selont(AlgorithmTypelon.Activity),
    AlgorithmToFelonelondbackTokelonnMap(RelonalGraphOonV2).toString -> Selont(AlgorithmTypelon.Activity),
    AlgorithmToFelonelondbackTokelonnMap(MagicReloncsReloncelonntelonngagelonmelonnts).toString -> Selont(AlgorithmTypelon.Activity),
    AlgorithmToFelonelondbackTokelonnMap(Notificationelonngagelonmelonnt).toString -> Selont(AlgorithmTypelon.Activity),
    // Social Algorithms:
    AlgorithmToFelonelondbackTokelonnMap(TwoHopRandomWalk).toString -> Selont(AlgorithmTypelon.Social),
    AlgorithmToFelonelondbackTokelonnMap(RelonalTimelonMutualFollow).toString -> Selont(AlgorithmTypelon.Social),
    AlgorithmToFelonelondbackTokelonnMap(ForwardPhonelonBook).toString -> Selont(AlgorithmTypelon.Social),
    AlgorithmToFelonelondbackTokelonnMap(ForwardelonmailBook).toString -> Selont(AlgorithmTypelon.Social),
    AlgorithmToFelonelondbackTokelonnMap(NelonwFollowingNelonwFollowingelonxpansion).toString -> Selont(
      AlgorithmTypelon.Social),
    AlgorithmToFelonelondbackTokelonnMap(NelonwFollowingSarusCoOccurSocialProof).toString -> Selont(
      AlgorithmTypelon.Social),
    AlgorithmToFelonelondbackTokelonnMap(RelonvelonrselonelonmailBookIbis).toString -> Selont(AlgorithmTypelon.Social),
    AlgorithmToFelonelondbackTokelonnMap(RelonvelonrselonPhonelonBook).toString -> Selont(AlgorithmTypelon.Social),
    AlgorithmToFelonelondbackTokelonnMap(StrongTielonPrelondictionRelonc).toString -> Selont(AlgorithmTypelon.Social),
    AlgorithmToFelonelondbackTokelonnMap(StrongTielonPrelondictionReloncWithSocialProof).toString -> Selont(
      AlgorithmTypelon.Social),
    AlgorithmToFelonelondbackTokelonnMap(OnlinelonStrongTielonPrelondictionRelonc).toString -> Selont(AlgorithmTypelon.Social),
    AlgorithmToFelonelondbackTokelonnMap(OnlinelonStrongTielonPrelondictionReloncNoCaching).toString -> Selont(
      AlgorithmTypelon.Social),
    AlgorithmToFelonelondbackTokelonnMap(TriangularLoop).toString -> Selont(AlgorithmTypelon.Social),
    AlgorithmToFelonelondbackTokelonnMap(StrongTielonPrelondictionPmi).toString -> Selont(AlgorithmTypelon.Social),
    AlgorithmToFelonelondbackTokelonnMap(OnlinelonStrongTielonPrelondictionRAB).toString -> Selont(AlgorithmTypelon.Social),
    // Gelono Algorithms:
    AlgorithmToFelonelondbackTokelonnMap(PopCountryBackFill).toString -> Selont(AlgorithmTypelon.Gelono),
    AlgorithmToFelonelondbackTokelonnMap(PopCountry).toString -> Selont(AlgorithmTypelon.Gelono),
    AlgorithmToFelonelondbackTokelonnMap(PopGelonohash).toString -> Selont(AlgorithmTypelon.Gelono),
//    AlgorithmToFelonelondbackTokelonnMap(PopGelonohashRelonalGraph).toString -> Selont(AlgorithmTypelon.Gelono),
    AlgorithmToFelonelondbackTokelonnMap(elonngagelondFollowelonrRatio).toString -> Selont(AlgorithmTypelon.Gelono),
    AlgorithmToFelonelondbackTokelonnMap(CrowdSelonarchAccounts).toString -> Selont(AlgorithmTypelon.Gelono),
    AlgorithmToFelonelondbackTokelonnMap(OrganicFollowAccounts).toString -> Selont(AlgorithmTypelon.Gelono),
    AlgorithmToFelonelondbackTokelonnMap(PopGelonohashQualityFollow).toString -> Selont(AlgorithmTypelon.Gelono),
    AlgorithmToFelonelondbackTokelonnMap(PPMILocalelonFollow).toString -> Selont(AlgorithmTypelon.Gelono),
    // Intelonrelonst Algorithms:
    AlgorithmToFelonelondbackTokelonnMap(TttIntelonrelonst).toString -> Selont(AlgorithmTypelon.Intelonrelonst),
    AlgorithmToFelonelondbackTokelonnMap(UttIntelonrelonstRelonlatelondUselonrs).toString -> Selont(AlgorithmTypelon.Intelonrelonst),
    AlgorithmToFelonelondbackTokelonnMap(UttSelonelondAccounts).toString -> Selont(AlgorithmTypelon.Intelonrelonst),
    AlgorithmToFelonelondbackTokelonnMap(UttProducelonrelonxpansion).toString -> Selont(AlgorithmTypelon.Intelonrelonst),
    // Hybrid (morelon than onelon typelon) Algorithms:
    AlgorithmToFelonelondbackTokelonnMap(UttProducelonrOfflinelonMbcgV1).toString -> Selont(
      AlgorithmTypelon.Intelonrelonst,
      AlgorithmTypelon.Gelono),
    AlgorithmToFelonelondbackTokelonnMap(CuratelondAccounts).toString -> Selont(
      AlgorithmTypelon.Intelonrelonst,
      AlgorithmTypelon.Gelono),
    AlgorithmToFelonelondbackTokelonnMap(UselonrUselonrGraph).toString -> Selont(
      AlgorithmTypelon.Social,
      AlgorithmTypelon.Activity),
  )
  delonf gelontAlgorithmTypelons(algoId: String): Selont[String] = {
    AlgorithmIdToTypelon.gelont(algoId).map(_.map(_.toString)).gelontOrelonlselon(Selont.elonmpty)
  }
}
