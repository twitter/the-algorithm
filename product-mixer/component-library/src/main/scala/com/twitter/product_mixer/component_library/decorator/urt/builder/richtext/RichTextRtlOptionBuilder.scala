packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.richtelonxt

import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

trait RichTelonxtRtlOptionBuildelonr[-Quelonry <: PipelonlinelonQuelonry] {
  delonf apply(quelonry: Quelonry): Option[Boolelonan]
}

caselon class StaticRichTelonxtRtlOptionBuildelonr[-Quelonry <: PipelonlinelonQuelonry](rtlOption: Option[Boolelonan])
    elonxtelonnds RichTelonxtRtlOptionBuildelonr[Quelonry] {
  ovelonrridelon delonf apply(quelonry: Quelonry): Option[Boolelonan] = rtlOption
}
