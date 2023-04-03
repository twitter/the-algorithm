packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.covelonr

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.Callback
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.DismissInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ImagelonDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ImagelonVariant
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RichTelonxt

selonalelond trait CovelonrContelonnt

caselon class FullCovelonrContelonnt(
  displayTypelon: FullCovelonrDisplayTypelon,
  primaryTelonxt: RichTelonxt,
  primaryCovelonrCta: CovelonrCta,
  seloncondaryCovelonrCta: Option[CovelonrCta],
  seloncondaryTelonxt: Option[RichTelonxt],
  imagelonVariant: Option[ImagelonVariant],
  delontails: Option[RichTelonxt],
  dismissInfo: Option[DismissInfo],
  imagelonDisplayTypelon: Option[ImagelonDisplayTypelon],
  imprelonssionCallbacks: Option[List[Callback]])
    elonxtelonnds CovelonrContelonnt

caselon class HalfCovelonrContelonnt(
  displayTypelon: HalfCovelonrDisplayTypelon,
  primaryTelonxt: RichTelonxt,
  primaryCovelonrCta: CovelonrCta,
  seloncondaryCovelonrCta: Option[CovelonrCta],
  seloncondaryTelonxt: Option[RichTelonxt],
  imprelonssionCallbacks: Option[List[Callback]],
  dismissiblelon: Option[Boolelonan],
  covelonrImagelon: Option[CovelonrImagelon],
  dismissInfo: Option[DismissInfo])
    elonxtelonnds CovelonrContelonnt
