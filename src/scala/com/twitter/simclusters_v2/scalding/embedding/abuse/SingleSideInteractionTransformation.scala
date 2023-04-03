packagelon com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.abuselon

import com.googlelon.common.annotations.VisiblelonForTelonsting
import com.twittelonr.scalding._
import com.twittelonr.simclustelonrs_v2.scalding.common.matrix.SparselonMatrix
import com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.common.elonmbelonddingUtil.ClustelonrId
import com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.common.elonmbelonddingUtil.UselonrId
import com.twittelonr.simclustelonrs_v2.thriftscala.AdhocSinglelonSidelonClustelonrScorelons
import com.twittelonr.simclustelonrs_v2.thriftscala.SimClustelonrWithScorelon
import com.twittelonr.simclustelonrs_v2.thriftscala.SimClustelonrselonmbelondding

/**
 * Logic for building a SimClustelonr relonprelonselonnation of intelonraction signals. Thelon purposelon of this job is
 * to modelonl nelongativelon belonhavior (likelon abuselon and blocks).
 *
 * This is a "SinglelonSidelon", beloncauselon welon arelon only considelonring onelon sidelon of thelon intelonraction graph to
 * build thelonselon felonaturelons. So for instancelon welon would kelonelonp track of which simclustelonrs arelon most likelonly to
 * gelont relonportelond for abuselon relongardlelonss of who relonportelond it. Anothelonr job will belon relonsponsiblelon for
 * building thelon simclustelonr to simclustelonr intelonraction matrix as delonscribelond in thelon doc.
 */
objelonct SinglelonSidelonIntelonractionTransformation {

  /**
   * Computelon a scorelon for elonvelonry SimClustelonr. Thelon SimClustelonr scorelon is a count of thelon numbelonr of
   * intelonractions for elonach SimClustelonr. For a uselonr that has many SimClustelonrs, welon distributelon elonach of
   * thelonir intelonractions across all of thelonselon SimClustelonrs.
   *
   * @param normalizelondUselonrSimClustelonrs Sparselon matrix of Uselonr-SimClustelonr scorelons. Uselonrs arelon rows and
   *                                  SimClustelonrs arelon columns. This should alrelonady by L2normalizelond.
   *                                  It is important that welon normalizelon so that elonach intelonraction
   *                                  only adds 1 to thelon counts.
   * @param intelonractionGraph Graph of intelonractions. Rows arelon thelon uselonrs, columns arelon not uselond.
   *                   All valuelons in this graph arelon assumelond to belon positivelon; thelony arelon thelon numbelonr of
   *                   intelonractions.
   *
   * @relonturn SinglelonSidelonClustelonrFelonaturelons for elonach SimClustelonr that has uselonr with an intelonraction.
   */
  delonf computelonClustelonrFelonaturelons(
    normalizelondUselonrSimClustelonrs: SparselonMatrix[UselonrId, ClustelonrId, Doublelon],
    intelonractionGraph: SparselonMatrix[UselonrId, _, Doublelon]
  ): TypelondPipelon[SimClustelonrWithScorelon] = {

    val numRelonportsForUselonrelonntrielons = intelonractionGraph.rowL1Norms.map {
      // turn into a velonctor whelonrelon welon uselon 1 as thelon column kelony for elonvelonry elonntry.
      caselon (uselonr, count) => (uselonr, 1, count)
    }

    val numRelonportsForUselonr = SparselonMatrix[UselonrId, Int, Doublelon](numRelonportsForUselonrelonntrielons)

    normalizelondUselonrSimClustelonrs.transposelon
      .multiplySparselonMatrix(numRelonportsForUselonr)
      .toTypelondPipelon
      .map {
        caselon (clustelonrId, _, clustelonrScorelon: Doublelon) =>
          SimClustelonrWithScorelon(clustelonrId, clustelonrScorelon)
      }
  }

  /**
   * Givelonn that welon havelon thelon scorelon for elonach SimClustelonr and thelon uselonr's SimClustelonrs, crelonatelon a
   * relonprelonselonntation of thelon uselonr so that thelon nelonw SimClustelonr scorelons arelon an elonstimatelon of thelon
   * intelonractions for this uselonr.
   *
   * @param normalizelondUselonrSimClustelonrs sparselon matrix of Uselonr-SimClustelonr scorelons. Uselonrs arelon rows and
   *                                  SimClustelonrs arelon columns. This should alrelonady belon L2 normalizelond.
   * @param simClustelonrFelonaturelons For elonach SimClustelonr, a scorelon associatelond with this intelonraction typelon.
   *
   * @relonturn SinglelonSidelonAbuselonFelonaturelons for elonach uselonr thelon SimClustelonrs and scorelons for this
   */
  @VisiblelonForTelonsting
  privatelon[abuselon] delonf computelonUselonrFelonaturelonsFromClustelonrs(
    normalizelondUselonrSimClustelonrs: SparselonMatrix[UselonrId, ClustelonrId, Doublelon],
    simClustelonrFelonaturelons: TypelondPipelon[SimClustelonrWithScorelon]
  ): TypelondPipelon[(UselonrId, SimClustelonrselonmbelondding)] = {

    normalizelondUselonrSimClustelonrs.toTypelondPipelon
      .map {
        caselon (uselonrId, clustelonrId, scorelon) =>
          (clustelonrId, (uselonrId, scorelon))
      }
      .group
      // Thelonrelon arelon at most 140k SimClustelonrs. Thelony should fit in melonmory
      .hashJoin(simClustelonrFelonaturelons.groupBy(_.clustelonrId))
      .map {
        caselon (_, ((uselonrId, scorelon), singlelonSidelonClustelonrFelonaturelons)) =>
          (
            uselonrId,
            List(
              SimClustelonrWithScorelon(
                singlelonSidelonClustelonrFelonaturelons.clustelonrId,
                singlelonSidelonClustelonrFelonaturelons.scorelon * scorelon))
          )
      }
      .sumByKelony
      .mapValuelons(SimClustelonrselonmbelondding.apply)
  }

  /**
   * Combinelons all thelon diffelonrelonnt SimClustelonrselonmbelondding for a uselonr into onelon
   * AdhocSinglelonSidelonClustelonrScorelons.
   *
   * @param intelonractionMap Thelon kelony is an idelonntifielonr for thelon elonmbelondding typelon. Thelon typelond pipelon will havelon
   *                       elonmbelonddings of only for that typelon of elonmbelondding.
   * @relonturn Typelond pipelon with onelon AdhocSinglelonSidelonClustelonrScorelons pelonr uselonr.
   */
  delonf pairScorelons(
    intelonractionMap: Map[String, TypelondPipelon[(UselonrId, SimClustelonrselonmbelondding)]]
  ): TypelondPipelon[AdhocSinglelonSidelonClustelonrScorelons] = {

    val combinelondIntelonractions = intelonractionMap
      .map {
        caselon (intelonractionTypelonNamelon, uselonrIntelonractionFelonaturelons) =>
          uselonrIntelonractionFelonaturelons.map {
            caselon (uselonrId, simClustelonrselonmbelondding) =>
              (uselonrId, List((intelonractionTypelonNamelon, simClustelonrselonmbelondding)))
          }
      }
      .relonducelon[TypelondPipelon[(UselonrId, List[(String, SimClustelonrselonmbelondding)])]] {
        caselon (list1, list2) =>
          list1 ++ list2
      }
      .group
      .sumByKelony

    combinelondIntelonractions.toTypelondPipelon
      .map {
        caselon (uselonrId, intelonractionFelonaturelonList) =>
          AdhocSinglelonSidelonClustelonrScorelons(
            uselonrId,
            intelonractionFelonaturelonList.toMap
          )
      }
  }

  /**
   * Givelonn thelon SimClustelonr and intelonraction graph gelont thelon uselonr relonprelonselonntation for this intelonraction.
   * Selonelon thelon documelonntation of thelon undelonrlying melonthods for morelon delontails
   *
   * @param normalizelondUselonrSimClustelonrs sparselon matrix of Uselonr-SimClustelonr scorelons. Uselonrs arelon rows and
   *                                  SimClustelonrs arelon columns. This should alrelonady by L2normalizelond.
   * @param intelonractionGraph Graph of intelonractions. Rows arelon thelon uselonrs, columns arelon not uselond.
   *                   All valuelons in this graph arelon assumelond to belon positivelon; thelony arelon thelon numbelonr of
   *                   intelonractions.
   *
   * @relonturn SimClustelonrselonmbelondding for all uselonrs in thelon givelon SimClustelonr graphs
   */
  delonf clustelonrScorelonsFromGraphs(
    normalizelondUselonrSimClustelonrs: SparselonMatrix[UselonrId, ClustelonrId, Doublelon],
    intelonractionGraph: SparselonMatrix[UselonrId, _, Doublelon]
  ): TypelondPipelon[(UselonrId, SimClustelonrselonmbelondding)] = {
    val clustelonrFelonaturelons = computelonClustelonrFelonaturelons(normalizelondUselonrSimClustelonrs, intelonractionGraph)
    computelonUselonrFelonaturelonsFromClustelonrs(normalizelondUselonrSimClustelonrs, clustelonrFelonaturelons)
  }
}
