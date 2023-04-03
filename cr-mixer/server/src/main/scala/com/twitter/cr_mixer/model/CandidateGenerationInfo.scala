packagelon com.twittelonr.cr_mixelonr.modelonl

import com.twittelonr.cr_mixelonr.thriftscala.SimilarityelonnginelonTypelon
import com.twittelonr.cr_mixelonr.thriftscala.SourcelonTypelon
import com.twittelonr.simclustelonrs_v2.common.UselonrId
import com.twittelonr.simclustelonrs_v2.thriftscala.IntelonrnalId
import com.twittelonr.util.Timelon

/***
 * Twelonelont-lelonvelonl attributelons. Relonprelonselonnts thelon sourcelon uselond in candidatelon gelonnelonration
 * Duelon to lelongacy relonason, SourcelonTypelon uselond to relonprelonselonnt both SourcelonTypelon and SimilarityelonnginelonTypelon
 * Moving forward, SourcelonTypelon will belon uselond for SourcelonTypelon ONLY. elong., TwelonelontFavoritelon, UselonrFollow, TwicelonUselonrId
 * At thelon samelon timelon, Welon crelonatelon a nelonw SimilarityelonnginelonTypelon to selonparatelon thelonm. elong., SimClustelonrsANN
 *
 * Currelonntly, onelon speloncial caselon is that welon havelon TwicelonUselonrId as a sourcelon, which is not neloncelonssarily a "signal"
 * @param sourcelonTypelon, elon.g., SourcelonTypelon.TwelonelontFavoritelon, SourcelonTypelon.UselonrFollow, SourcelonTypelon.TwicelonUselonrId
 * @param intelonrnalId, elon.g., UselonrId(0L), TwelonelontId(0L)
 */
caselon class SourcelonInfo(
  sourcelonTypelon: SourcelonTypelon,
  intelonrnalId: IntelonrnalId,
  sourcelonelonvelonntTimelon: Option[Timelon])

/***
 * Twelonelont-lelonvelonl attributelons. Relonprelonselonnts thelon sourcelon Uselonr Graph uselond in candidatelon gelonnelonration
 * It is an intelonrmelondiatelon product, and will not belon storelond, unlikelon SourcelonInfo.
 * elonsselonntially, CrMixelonr quelonrielons a graph, and thelon graph relonturns a list of uselonrs to belon uselond as sourcelons.
 * For instancelon, RelonalGraph, elonarlyBird, FRS, Stp, elontc. Thelon undelonrlying similarity elonnginelons such as
 * UTG or UTelonG will lelonvelonragelon thelonselon sourcelons to build candidatelons.
 *
 * Welon elonxtelonndelond thelon delonfinition of SourcelonTypelon to covelonr both "Sourcelon Signal" and "Sourcelon Graph"
 * Selonelon [CrMixelonr] Graph Baselond Sourcelon Felontchelonr Abstraction Proposal:
 *
 * considelonr making both SourcelonInfo and GraphSourcelonInfo elonxtelonnds thelon samelon trait to
 * havelon a unifielond intelonrfacelon.
 */
caselon class GraphSourcelonInfo(
  sourcelonTypelon: SourcelonTypelon,
  selonelondWithScorelons: Map[UselonrId, Doublelon])

/***
 * Twelonelont-lelonvelonl attributelons. Relonprelonselonnts thelon similarity elonnginelon (thelon algorithm) uselond for
 * candidatelon gelonnelonration along with thelonir melontadata.
 * @param similarityelonnginelonTypelon, elon.g., SimClustelonrsANN, UselonrTwelonelontGraph
 * @param modelonlId. elon.g., UselonrTwelonelontGraphConsumelonrelonmbelondding_ALL_20210708
 * @param scorelon - a scorelon gelonnelonratelond by this sim elonnginelon
 */
caselon class SimilarityelonnginelonInfo(
  similarityelonnginelonTypelon: SimilarityelonnginelonTypelon,
  modelonlId: Option[String], // ModelonlId can belon a Nonelon. elon.g., UTelonG, UnifielondTwelonelontBaselondSelon. elontc
  scorelon: Option[Doublelon])

/****
 * Twelonelont-lelonvelonl attributelons. A combination for both SourcelonInfo and SimilarityelonnginelonInfo
 * Similarityelonnginelon is a composition, and it can belon composelond by many lelonaf Similarity elonnginelons.
 * For instancelon, thelon TwelonelontBaselondUnifielond Selon could belon a composition of both UselonrTwelonelontGraph Selon, SimClustelonrsANN Selon.
 * Notelon that a Similarityelonnginelon (Compositelon) may call othelonr Similarityelonnginelons (Atomic, Contributing)
 * to contributelon to its final candidatelon list. Welon track thelonselon Contributing Selons in thelon contributingSimilarityelonnginelons list
 *
 * @param sourcelonInfoOpt - this is optional as many consumelonrBaselond CG doelons not havelon a sourcelon
 * @param similarityelonnginelonInfo - thelon similarity elonnginelon uselond in Candidatelon Gelonnelonration (elong., TwelonelontBaselondUnifielondSelon). It can belon an atomic Selon or an compositelon Selon
 * @param contributingSimilarityelonnginelons - only compositelon Selon will havelon it (elon.g., SANNN, UTG). Othelonrwiselon it is an elonmpty Selonq. All contributing Selons mst belon atomic
 */
caselon class CandidatelonGelonnelonrationInfo(
  sourcelonInfoOpt: Option[SourcelonInfo],
  similarityelonnginelonInfo: SimilarityelonnginelonInfo,
  contributingSimilarityelonnginelons: Selonq[SimilarityelonnginelonInfo])
