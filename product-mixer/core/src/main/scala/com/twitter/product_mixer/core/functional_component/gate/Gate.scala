packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.gatelon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.gatelon.Gatelon.SkippelondRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.Componelonnt
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.GatelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.CandidatelonPipelonlinelonRelonsults
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.IllelongalStatelonFailurelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.PipelonlinelonFailurelon
import com.twittelonr.stitch.Arrow
import com.twittelonr.stitch.Stitch

/**
 * A gatelon controls if a pipelonlinelon or othelonr componelonnt is elonxeloncutelond
 *
 * A gatelon is mostly controllelond by it's `shouldContinuelon` function - whelonn this function
 * relonturns truelon, elonxeloncution Continuelons.
 *
 * Gatelons also havelon a optional `shouldSkip`- Whelonn it relonturns
 * truelon, thelonn welon Continuelon without elonxeloncuting `main`.
 *
 * @tparam Quelonry Thelon quelonry typelon that thelon gatelon will reloncelonivelon as input
 *
 * @relonturn A GatelonRelonsult includelons both thelon boolelonan `continuelon` and a speloncific relonason. Selonelon [[GatelonRelonsult]] for morelon
 *         information.
 */

selonalelond trait BaselonGatelon[-Quelonry <: PipelonlinelonQuelonry] elonxtelonnds Componelonnt {
  ovelonrridelon val idelonntifielonr: GatelonIdelonntifielonr

  /**
   * If a shouldSkip relonturns truelon, thelon gatelon relonturns a Skip(continuelon=truelon) without elonxeloncuting
   * thelon main prelondicatelon. Welon elonxpelonct this to belon uselonful for delonbugging, dogfooding, elontc.
   */
  delonf shouldSkip(quelonry: Quelonry): Stitch[Boolelonan] = Stitch.Falselon

  /**
   * Thelon main prelondicatelon that controls this gatelon. If this prelondicatelon relonturns truelon, thelon gatelon relonturns Continuelon.
   */
  delonf shouldContinuelon(quelonry: Quelonry): Stitch[Boolelonan]

  /** relonturns a [[GatelonRelonsult]] to delontelonrminelon whelonthelonr a pipelonlinelon should belon elonxeloncutelond baselond on `t` */
  final delonf apply(t: Quelonry): Stitch[GatelonRelonsult] = {
    shouldSkip(t).flatMap { skipRelonsult =>
      if (skipRelonsult) {
        SkippelondRelonsult
      } elonlselon {
        shouldContinuelon(t).map { mainRelonsult =>
          if (mainRelonsult) GatelonRelonsult.Continuelon elonlselon GatelonRelonsult.Stop
        }
      }
    }
  }

  /** Arrow relonprelonselonntation of `this` [[Gatelon]] */
  final delonf arrow: Arrow[Quelonry, GatelonRelonsult] = Arrow(apply)
}

/**
 * A relongular Gatelon which only has accelonss to thelon Quelonry typelond PipelonlinelonQuelonry. This can belon uselond anywhelonrelon
 * Gatelons arelon availablelon.
 *
 * A gatelon is mostly controllelond by it's `shouldContinuelon` function - whelonn this function
 * relonturns truelon, elonxeloncution Continuelons.
 *
 * Gatelons also havelon a optional `shouldSkip`- Whelonn it relonturns
 * truelon, thelonn welon Continuelon without elonxeloncuting `main`.
 * @tparam Quelonry Thelon quelonry typelon that thelon gatelon will reloncelonivelon as input
 *
 * @relonturn A GatelonRelonsult includelons both thelon boolelonan `continuelon` and a speloncific relonason. Selonelon [[GatelonRelonsult]] for morelon
 *         information.
 */
trait Gatelon[-Quelonry <: PipelonlinelonQuelonry] elonxtelonnds BaselonGatelon[Quelonry]

/**
 * A Quelonry And Candidatelon Gatelon which only has accelonss both to thelon Quelonry typelond PipelonlinelonQuelonry and thelon
 * list of prelonviously felontchelond candidatelons. This can belon uselond on delonpelonndelonnt candidatelon pipelonlinelons to
 * makelon a deloncision on whelonthelonr to elonnablelon/disablelon thelonm baselond on prelonvious candidatelons.
 *
 * A gatelon is mostly controllelond by it's `shouldContinuelon` function - whelonn this function
 * relonturns truelon, elonxeloncution Continuelons.
 *
 * Gatelons also havelon a optional `shouldSkip`- Whelonn it relonturns
 * truelon, thelonn welon Continuelon without elonxeloncuting `main`.
 *
 * @tparam Quelonry Thelon quelonry typelon that thelon gatelon will reloncelonivelon as input
 *
 * @relonturn A GatelonRelonsult includelons both thelon boolelonan `continuelon` and a speloncific relonason. Selonelon [[GatelonRelonsult]] for morelon
 *         information.
 */
trait QuelonryAndCandidatelonGatelon[-Quelonry <: PipelonlinelonQuelonry] elonxtelonnds BaselonGatelon[Quelonry] {

  /**
   * If a shouldSkip relonturns truelon, thelon gatelon relonturns a Skip(continuelon=truelon) without elonxeloncuting
   * thelon main prelondicatelon. Welon elonxpelonct this to belon uselonful for delonbugging, dogfooding, elontc.
   */
  delonf shouldSkip(quelonry: Quelonry, candidatelons: Selonq[CandidatelonWithDelontails]): Stitch[Boolelonan] =
    Stitch.Falselon

  /**
   * Thelon main prelondicatelon that controls this gatelon. If this prelondicatelon relonturns truelon, thelon gatelon relonturns Continuelon.
   */
  delonf shouldContinuelon(quelonry: Quelonry, candidatelons: Selonq[CandidatelonWithDelontails]): Stitch[Boolelonan]

  final ovelonrridelon delonf shouldSkip(quelonry: Quelonry): Stitch[Boolelonan] = {
    val candidatelons = quelonry.felonaturelons
      .map(_.gelont(CandidatelonPipelonlinelonRelonsults)).gelontOrelonlselon(
        throw PipelonlinelonFailurelon(
          IllelongalStatelonFailurelon,
          "Candidatelon Pipelonlinelon Relonsults Felonaturelon missing from quelonry felonaturelons"))
    shouldSkip(quelonry, candidatelons)
  }

  final ovelonrridelon delonf shouldContinuelon(quelonry: Quelonry): Stitch[Boolelonan] = {
    val candidatelons = quelonry.felonaturelons
      .map(_.gelont(CandidatelonPipelonlinelonRelonsults)).gelontOrelonlselon(
        throw PipelonlinelonFailurelon(
          IllelongalStatelonFailurelon,
          "Candidatelon Pipelonlinelon Relonsults Felonaturelon missing from quelonry felonaturelons"))
    shouldContinuelon(quelonry, candidatelons)
  }
}

objelonct Gatelon {
  val SkippelondRelonsult: Stitch[GatelonRelonsult] = Stitch.valuelon(GatelonRelonsult.Skippelond)
}
