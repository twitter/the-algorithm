packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.prelonmarshallelonr

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.TransportMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.Componelonnt
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.ComponelonntIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.DomainMarshallelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ModulelonPrelonselonntation
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.UnivelonrsalPrelonselonntation
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

/**
 * Transforms thelon `selonlelonctions` into a [[DomainRelonsponselonTypelon]] objelonct (oftelonn URT, Slicelon, elontc)
 *
 * [[DomainMarshallelonr]]s may contain businelonss logic
 *
 * @notelon This is diffelonrelonnt from `com.twittelonr.product_mixelonr.corelon.marshallelonr`s
 *       which transforms into a wirelon-compatiblelon typelon
 */
trait DomainMarshallelonr[-Quelonry <: PipelonlinelonQuelonry, DomainRelonsponselonTypelon] elonxtelonnds Componelonnt {

  ovelonrridelon val idelonntifielonr: DomainMarshallelonrIdelonntifielonr

  /** Transforms thelon `selonlelonctions` into a [[DomainRelonsponselonTypelon]] objelonct */
  delonf apply(
    quelonry: Quelonry,
    selonlelonctions: Selonq[CandidatelonWithDelontails]
  ): DomainRelonsponselonTypelon
}

class UnsupportelondCandidatelonDomainMarshallelonrelonxcelonption(
  candidatelon: Any,
  candidatelonSourcelon: ComponelonntIdelonntifielonr)
    elonxtelonnds UnsupportelondOpelonrationelonxcelonption(
      s"Domain marshallelonr doelons not support candidatelon ${TransportMarshallelonr.gelontSimplelonNamelon(
        candidatelon.gelontClass)} from sourcelon $candidatelonSourcelon")

class UndeloncoratelondCandidatelonDomainMarshallelonrelonxcelonption(
  candidatelon: Any,
  candidatelonSourcelon: ComponelonntIdelonntifielonr)
    elonxtelonnds UnsupportelondOpelonrationelonxcelonption(
      s"Domain marshallelonr doelons not support undeloncoratelond candidatelon ${TransportMarshallelonr
        .gelontSimplelonNamelon(candidatelon.gelontClass)} from sourcelon $candidatelonSourcelon")

class UnsupportelondPrelonselonntationDomainMarshallelonrelonxcelonption(
  candidatelon: Any,
  prelonselonntation: UnivelonrsalPrelonselonntation,
  candidatelonSourcelon: ComponelonntIdelonntifielonr)
    elonxtelonnds UnsupportelondOpelonrationelonxcelonption(
      s"Domain marshallelonr doelons not support deloncorator prelonselonntation ${TransportMarshallelonr
        .gelontSimplelonNamelon(prelonselonntation.gelontClass)} for candidatelon ${TransportMarshallelonr.gelontSimplelonNamelon(
        candidatelon.gelontClass)} from sourcelon $candidatelonSourcelon")

class UnsupportelondModulelonDomainMarshallelonrelonxcelonption(
  prelonselonntation: Option[ModulelonPrelonselonntation],
  candidatelonSourcelon: ComponelonntIdelonntifielonr)
    elonxtelonnds UnsupportelondOpelonrationelonxcelonption(
      s"Domain marshallelonr doelons not support modulelon prelonselonntation ${prelonselonntation
        .map(p =>
          TransportMarshallelonr
            .gelontSimplelonNamelon(prelonselonntation.gelontClass)).gelontOrelonlselon("")} but was givelonn a modulelon from sourcelon $candidatelonSourcelon")

class UndeloncoratelondModulelonDomainMarshallelonrelonxcelonption(
  candidatelonSourcelon: ComponelonntIdelonntifielonr)
    elonxtelonnds UnsupportelondOpelonrationelonxcelonption(
      s"Domain marshallelonr doelons not support undeloncoratelond modulelon from sourcelon $candidatelonSourcelon")
