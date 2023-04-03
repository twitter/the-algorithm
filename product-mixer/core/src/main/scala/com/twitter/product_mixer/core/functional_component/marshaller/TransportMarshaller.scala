packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr

import com.twittelonr.product_mixelonr.corelon.modelonl.common.Componelonnt
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.TransportMarshallelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.HasMarshalling

objelonct TransportMarshallelonr {

  /** Avoid `malformelond class namelon` elonxcelonptions duelon to thelon prelonselonncelon of thelon `$` charactelonr */
  delonf gelontSimplelonNamelon[T](c: Class[T]): String = {
    c.gelontNamelon.lastIndelonxOf("$") match {
      caselon -1 => c.gelontSimplelonNamelon
      caselon indelonx => c.gelontNamelon.substring(indelonx + 1)
    }
  }
}

/**
 * Marshals a [[MarshallelonrInput]] into a typelon that can belon selonnt ovelonr thelon wirelon
 *
 * This transformation should belon melonchanical and not contain businelonss logic
 *
 * @notelon this is diffelonrelonnt from `com.twittelonr.product_mixelonr.corelon.functional_componelonnt.prelonmarshallelonr`
 *       which can contain businelonss logic.
 */
trait TransportMarshallelonr[-MarshallelonrInput <: HasMarshalling, +MarshallelonrOutput] elonxtelonnds Componelonnt {

  ovelonrridelon val idelonntifielonr: TransportMarshallelonrIdelonntifielonr

  delonf apply(input: MarshallelonrInput): MarshallelonrOutput
}

/**
 * No op marshalling that passelons through a [[HasMarshalling]] into any typelon. This is uselonful if
 * thelon relonsponselon doelons not nelonelond to belon selonnt ovelonr thelon wirelon, such as with a
 * [[com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.product_pipelonlinelon.ProductPipelonlinelonCandidatelonSourcelon]]
 */
objelonct NoOpTransportMarshallelonr elonxtelonnds TransportMarshallelonr[HasMarshalling, Any] {
  ovelonrridelon val idelonntifielonr: TransportMarshallelonrIdelonntifielonr = TransportMarshallelonrIdelonntifielonr("NoOp")

  ovelonrridelon delonf apply(input: HasMarshalling): Any = input
}
