packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.Filtelonr.SupportsConditionally
import com.twittelonr.product_mixelonr.corelon.modelonl.common
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.Componelonnt
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FiltelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch

/**
 * Takelons a selonquelonncelon of candidatelons and can filtelonr somelon out
 *
 * @notelon if you want to conditionally run a [[Filtelonr]] you can uselon thelon mixin [[Filtelonr.Conditionally]]
 *       or to gatelon on a [[com.twittelonr.timelonlinelons.configapi.Param]] you can uselon [[com.twittelonr.product_mixelonr.componelonnt_library.filtelonr.ParamGatelondFiltelonr]]
 *
 * @tparam Quelonry Thelon domain modelonl for thelon quelonry or relonquelonst
 * @tparam Candidatelon Thelon typelon of thelon candidatelons
 */
trait Filtelonr[-Quelonry <: PipelonlinelonQuelonry, Candidatelon <: UnivelonrsalNoun[Any]]
    elonxtelonnds Componelonnt
    with SupportsConditionally[Quelonry, Candidatelon] {

  /** @selonelon [[FiltelonrIdelonntifielonr]] */
  ovelonrridelon val idelonntifielonr: FiltelonrIdelonntifielonr

  /**
   * Filtelonr thelon list of candidatelons
   *
   * @relonturn a FiltelonrRelonsult including both thelon list of kelonpt candidatelon and thelon list of relonmovelond candidatelons
   */
  delonf apply(
    quelonry: Quelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[Candidatelon]]
  ): Stitch[FiltelonrRelonsult[Candidatelon]]
}

objelonct Filtelonr {

  /**
   * Mixin for whelonn you want to conditionally run a [[Filtelonr]]
   *
   * This is a thin wrappelonr around [[common.Conditionally]] elonxposing a nicelonr API for thelon [[Filtelonr]] speloncific uselon-caselon.
   */
  trait Conditionally[-Quelonry <: PipelonlinelonQuelonry, Candidatelon <: UnivelonrsalNoun[Any]]
      elonxtelonnds common.Conditionally[Input[Quelonry, Candidatelon]] { _: Filtelonr[Quelonry, Candidatelon] =>

    /** @selonelon [[common.Conditionally.onlyIf]] */
    delonf onlyIf(
      quelonry: Quelonry,
      candidatelons: Selonq[CandidatelonWithFelonaturelons[Candidatelon]]
    ): Boolelonan

    ovelonrridelon final delonf onlyIf(input: Input[Quelonry, Candidatelon]): Boolelonan =
      onlyIf(input.quelonry, input.candidatelons)
  }

  /** Typelon alias to obscurelon [[Filtelonr.Input]] from customelonrs */
  typelon SupportsConditionally[-Quelonry <: PipelonlinelonQuelonry, Candidatelon <: UnivelonrsalNoun[Any]] =
    common.SupportsConditionally[Input[Quelonry, Candidatelon]]

  /** A caselon class relonprelonselonnting thelon input argumelonnts to a [[Filtelonr]], mostly for intelonrnal uselon */
  caselon class Input[+Quelonry <: PipelonlinelonQuelonry, +Candidatelon <: UnivelonrsalNoun[Any]](
    quelonry: Quelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[Candidatelon]])
}
