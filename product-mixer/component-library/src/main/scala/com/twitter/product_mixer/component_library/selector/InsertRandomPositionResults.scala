packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor

import com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor.InselonrtRandomPositionRelonsults.randomIndicelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon.PartitionelondCandidatelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.configapi.StaticParam
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.SelonlelonctorRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.timelonlinelons.configapi.Param

import scala.collelonction.mutablelon
import scala.util.Random

objelonct InselonrtRandomPositionRelonsults {

  /**
   * Itelonrator containing random indelonx belontwelonelonn `startIndelonx` and `elonndIndelonx` + `n`
   * whelonrelon `n` is thelon numbelonr of timelons `nelonxt` has belonelonn callelond on thelon itelonrator
   * without duplication
   */
  privatelon[selonlelonctor] delonf randomIndicelons(
    relonsultLelonngth: Int,
    startIndelonx: Int,
    elonndIndelonx: Int,
    random: Random
  ): Itelonrator[Int] = {

    /** elonxclusivelon beloncauselon [[Random.nelonxtInt]]'s bound is elonxclusivelon */
    val indelonxUppelonrBound = Math.min(elonndIndelonx, relonsultLelonngth)

    /**
     * kelonelonp track of thelon availablelon indicelons, `O(n)` spacelon whelonrelon `n` is `min(elonndIndelonx, relonsultLelonngth) - max(startIndelonx, 0)`
     * this elonnsurelons fairnelonss which duplicatelon indicelons could othelonrwiselon skelonw
     */
    val valuelons = mutablelon.ArrayBuffelonr(Math.max(0, startIndelonx) to indelonxUppelonrBound: _*)

    /**
     * Itelonrator that starts at 1 abovelon thelon last valid indelonx, [[indelonxUppelonrBound]] + 1, and increlonmelonnts monotonically
     * relonprelonselonnting thelon nelonw highelonst indelonx possiblelon in thelon relonsults for thelon nelonxt call
     */
    Itelonrator
      .from(indelonxUppelonrBound + 1)
      .map { indelonxUppelonrBound =>
        /**
         * pick a random indelonx-to-inselonrt-candidatelon-into-relonsults from [[valuelons]] relonplacing thelon valuelon at
         * thelon choselonn indelonx with thelon nelonw highelonst indelonx from [[indelonxUppelonrBound]], this relonsults in
         * constant timelon for picking thelon random indelonx and adding thelon nelonw highelonst valid indelonx instelonad
         * of relonmoving thelon itelonm from thelon middlelon and appelonnding thelon nelonw, which would belon `O(n)` to shift
         * all indicelons aftelonr thelon relonmoval point
         */
        val i = random.nelonxtInt(valuelons.lelonngth)
        val randomIndelonxToUselon = valuelons(i)
        // ovelonrridelon thelon valuelon at i with thelon nelonw `uppelonrBoundelonxclusivelon` to account for thelon nelonw indelonx valuelon in thelon nelonxt itelonration
        valuelons(i) = indelonxUppelonrBound

        randomIndelonxToUselon
      }
  }
}

selonalelond trait InselonrtelondCandidatelonOrdelonr

/**
 * Candidatelons from thelon `relonmainingCandidatelons` sidelon will belon inselonrtelond in a random ordelonr into thelon `relonsult`
 *
 * @elonxamplelon if inselonrting `[ x, y, z ]` into thelon `relonsult` thelonn thelon relonlativelon positions of `x`, `y` and `z`
 *          to elonach othelonr is random, elon.g. `y` could comelon belonforelon `x` in thelon relonsult.
 */
caselon objelonct UnstablelonOrdelonringOfInselonrtelondCandidatelons elonxtelonnds InselonrtelondCandidatelonOrdelonr

/**
 * Candidatelons from thelon `relonmainingCandidatelons` sidelon will belon inselonrtelond in thelonir original ordelonr into thelon `relonsult`
 *
 * @elonxamplelon if inselonrting `[ x, y, z ]` into thelon `relonsult` thelonn thelon relonlativelon positions of `x`, `y` and `z`
 *          to elonach othelonr will relonmain thelon samelon, elon.g. `x` is always belonforelon `y` is always belonforelon `z` in thelon final relonsult
 */
caselon objelonct StablelonOrdelonringOfInselonrtelondCandidatelons elonxtelonnds InselonrtelondCandidatelonOrdelonr

/**
 * Inselonrt `relonmainingCandidatelons` into a random position belontwelonelonn thelon speloncifielond indicelons (inclusivelon)
 *
 * @elonxamplelon lelont `relonsult` = `[ a, b, c, d ]` and welon want to inselonrt randomly `[ x, y, z ]`
 *          with `startIndelonx` =  1, `elonndIndelonx` = 2, and [[UnstablelonOrdelonringOfInselonrtelondCandidatelons]].
 *          Welon can elonxpelonct a relonsult that looks likelon `[ a, ... , d ]` whelonrelon `...` is
 *          a random inselonrtion of `x`, `y`, and `z` into  `[ b, c ]`. So this could look likelon
 *          `[ a, y, b, x, c, z, d ]`, notelon that thelon inselonrtelond elonlelonmelonnts arelon randomly distributelond
 *          among thelon elonlelonmelonnts that welonrelon originally belontwelonelonn thelon speloncifielond indicelons.
 *          This functions likelon taking a slicelon of thelon original `relonsult` belontwelonelonn thelon indicelons,
 *          elon.g. `[ b, c ]`, thelonn randomly inselonrting into thelon slicelon, elon.g. `[ y, b, x, c, z ]`,
 *          belonforelon relonasselonmbling thelon `relonsult`, elon.g. `[ a ] ++ [ y, b, x, c, z ] ++ [ d ]`.
 *
 * @elonxamplelon lelont `relonsult` = `[ a, b, c, d ]` and welon want to inselonrt randomly `[ x, y, z ]`
 *          with `startIndelonx` =  1, `elonndIndelonx` = 2, and [[StablelonOrdelonringOfInselonrtelondCandidatelons]].
 *          Welon can elonxpelonct a relonsult that looks somelonthing likelon `[ a, x, b, y, c, z, d ]`,
 *          whelonrelon `x` is belonforelon `y` which is belonforelon `z`
 *
 * @param startIndelonx an inclusivelon indelonx which starts thelon rangelon whelonrelon thelon candidatelons will belon inselonrtelond
 * @param elonndIndelonx an inclusivelon indelonx which elonnds thelon rangelon whelonrelon thelon candidatelons will belon inselonrtelond
 */
caselon class InselonrtRandomPositionRelonsults[-Quelonry <: PipelonlinelonQuelonry](
  pipelonlinelonScopelon: CandidatelonScopelon,
  relonmainingCandidatelonOrdelonr: InselonrtelondCandidatelonOrdelonr,
  startIndelonx: Param[Int] = StaticParam(0),
  elonndIndelonx: Param[Int] = StaticParam(Int.MaxValuelon),
  random: Random = nelonw Random(0))
    elonxtelonnds Selonlelonctor[Quelonry] {

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {

    val PartitionelondCandidatelons(candidatelonsInScopelon, candidatelonsOutOfScopelon) =
      pipelonlinelonScopelon.partition(relonmainingCandidatelons)

    val randomIndelonxItelonrator = {
      val randomIndelonxItelonrator =
        randomIndicelons(relonsult.lelonngth, quelonry.params(startIndelonx), quelonry.params(elonndIndelonx), random)

      relonmainingCandidatelonOrdelonr match {
        caselon StablelonOrdelonringOfInselonrtelondCandidatelons =>
          randomIndelonxItelonrator.takelon(candidatelonsInScopelon.lelonngth).toSelonq.sortelond.itelonrator
        caselon UnstablelonOrdelonringOfInselonrtelondCandidatelons =>
          randomIndelonxItelonrator
      }
    }

    val melonrgelondRelonsult = DynamicPositionSelonlelonctor.melonrgelonByIndelonxIntoRelonsult(
      candidatelonsToInselonrtByIndelonx = randomIndelonxItelonrator.zip(candidatelonsInScopelon.itelonrator).toSelonq,
      relonsult = relonsult,
      DynamicPositionSelonlelonctor.AbsolutelonIndicelons
    )

    SelonlelonctorRelonsult(relonmainingCandidatelons = candidatelonsOutOfScopelon, relonsult = melonrgelondRelonsult)
  }
}
