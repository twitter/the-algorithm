packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.SelonlelonctorRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.timelonlinelons.configapi.Param

trait IncludelonSelonlelonctor[-Quelonry <: PipelonlinelonQuelonry] {
  delonf apply(
    quelonry: Quelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): Boolelonan
}

/**
 * Run [[selonlelonctor]] if [[includelonSelonlelonctor]] relonsolvelons to truelon, elonlselon no-op thelon selonlelonctor
 */
caselon class SelonlelonctConditionally[-Quelonry <: PipelonlinelonQuelonry](
  selonlelonctor: Selonlelonctor[Quelonry],
  includelonSelonlelonctor: IncludelonSelonlelonctor[Quelonry])
    elonxtelonnds Selonlelonctor[Quelonry] {

  ovelonrridelon val pipelonlinelonScopelon: CandidatelonScopelon = selonlelonctor.pipelonlinelonScopelon

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {
    if (includelonSelonlelonctor(quelonry, relonmainingCandidatelons, relonsult)) {
      selonlelonctor(quelonry, relonmainingCandidatelons, relonsult)
    } elonlselon SelonlelonctorRelonsult(relonmainingCandidatelons = relonmainingCandidatelons, relonsult = relonsult)
  }
}

objelonct SelonlelonctConditionally {

  /**
   * Wrap elonach [[Selonlelonctor]] in `selonlelonctors` in an [[IncludelonSelonlelonctor]] with `includelonSelonlelonctor` as thelon [[SelonlelonctConditionally.includelonSelonlelonctor]]
   */
  delonf apply[Quelonry <: PipelonlinelonQuelonry](
    selonlelonctors: Selonq[Selonlelonctor[Quelonry]],
    includelonSelonlelonctor: IncludelonSelonlelonctor[Quelonry]
  ): Selonq[Selonlelonctor[Quelonry]] =
    selonlelonctors.map(SelonlelonctConditionally(_, includelonSelonlelonctor))

  /**
   * A [[SelonlelonctConditionally]] baselond on a [[Param]]
   */
  delonf paramGatelond[Quelonry <: PipelonlinelonQuelonry](
    selonlelonctor: Selonlelonctor[Quelonry],
    elonnablelondParam: Param[Boolelonan],
  ): SelonlelonctConditionally[Quelonry] =
    SelonlelonctConditionally(selonlelonctor, (quelonry, _, _) => quelonry.params(elonnablelondParam))

  /**
   * Wrap elonach [[Selonlelonctor]] in `selonlelonctors` in a [[SelonlelonctConditionally]] baselond on a [[Param]]
   */
  delonf paramGatelond[Quelonry <: PipelonlinelonQuelonry](
    selonlelonctors: Selonq[Selonlelonctor[Quelonry]],
    elonnablelondParam: Param[Boolelonan],
  ): Selonq[Selonlelonctor[Quelonry]] =
    selonlelonctors.map(SelonlelonctConditionally.paramGatelond(_, elonnablelondParam))

  /**
   * A [[SelonlelonctConditionally]] baselond on an invelonrtelond [[Param]]
   */
  delonf paramNotGatelond[Quelonry <: PipelonlinelonQuelonry](
    selonlelonctor: Selonlelonctor[Quelonry],
    elonnablelondParamToInvelonrt: Param[Boolelonan],
  ): SelonlelonctConditionally[Quelonry] =
    SelonlelonctConditionally(selonlelonctor, (quelonry, _, _) => !quelonry.params(elonnablelondParamToInvelonrt))

  /**
   * Wrap elonach [[Selonlelonctor]] in `selonlelonctors` in a [[SelonlelonctConditionally]] baselond on an invelonrtelond [[Param]]
   */
  delonf paramNotGatelond[Quelonry <: PipelonlinelonQuelonry](
    selonlelonctors: Selonq[Selonlelonctor[Quelonry]],
    elonnablelondParamToInvelonrt: Param[Boolelonan],
  ): Selonq[Selonlelonctor[Quelonry]] =
    selonlelonctors.map(SelonlelonctConditionally.paramNotGatelond(_, elonnablelondParamToInvelonrt))
}
