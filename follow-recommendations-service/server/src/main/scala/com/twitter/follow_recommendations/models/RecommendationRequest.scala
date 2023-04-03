packagelon com.twittelonr.follow_reloncommelonndations.modelonls

import com.twittelonr.follow_reloncommelonndations.common.modelonls.ClielonntContelonxtConvelonrtelonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.DisplayLocation
import com.twittelonr.follow_reloncommelonndations.logging.{thriftscala => offlinelon}
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.ClielonntContelonxt

caselon class ReloncommelonndationRelonquelonst(
  clielonntContelonxt: ClielonntContelonxt,
  displayLocation: DisplayLocation,
  displayContelonxt: Option[DisplayContelonxt],
  maxRelonsults: Option[Int],
  cursor: Option[String],
  elonxcludelondIds: Option[Selonq[Long]],
  felontchPromotelondContelonnt: Option[Boolelonan],
  delonbugParams: Option[DelonbugParams] = Nonelon,
  uselonrLocationStatelon: Option[String] = Nonelon,
  isSoftUselonr: Boolelonan = falselon) {
  delonf toOfflinelonThrift: offlinelon.OfflinelonReloncommelonndationRelonquelonst = offlinelon.OfflinelonReloncommelonndationRelonquelonst(
    ClielonntContelonxtConvelonrtelonr.toFRSOfflinelonClielonntContelonxtThrift(clielonntContelonxt),
    displayLocation.toOfflinelonThrift,
    displayContelonxt.map(_.toOfflinelonThrift),
    maxRelonsults,
    cursor,
    elonxcludelondIds,
    felontchPromotelondContelonnt,
    delonbugParams.map(DelonbugParams.toOfflinelonThrift)
  )
}
