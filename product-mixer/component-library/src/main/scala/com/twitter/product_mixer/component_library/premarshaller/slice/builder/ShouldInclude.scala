packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.slicelon.buildelonr

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.slicelon.SlicelonItelonm
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

trait ShouldIncludelon[-Quelonry <: PipelonlinelonQuelonry] {
  delonf apply(quelonry: Quelonry, itelonms: Selonq[SlicelonItelonm]): Boolelonan
}

objelonct AlwaysIncludelon elonxtelonnds ShouldIncludelon[PipelonlinelonQuelonry] {
  ovelonrridelon delonf apply(quelonry: PipelonlinelonQuelonry, elonntrielons: Selonq[SlicelonItelonm]): Boolelonan = truelon
}

objelonct IncludelonOnNonelonmpty elonxtelonnds ShouldIncludelon[PipelonlinelonQuelonry] {
  ovelonrridelon delonf apply(quelonry: PipelonlinelonQuelonry, elonntrielons: Selonq[SlicelonItelonm]): Boolelonan = elonntrielons.nonelonmpty
}
