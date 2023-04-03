packagelon com.twittelonr.follow_reloncommelonndations.common.modelonls

import com.twittelonr.finaglelon.tracing.Tracelon
import com.twittelonr.follow_reloncommelonndations.logging.{thriftscala => offlinelon}
import com.twittelonr.follow_reloncommelonndations.{thriftscala => t}
import com.twittelonr.scroogelon.BinaryThriftStructSelonrializelonr
import com.twittelonr.suggelonsts.controllelonr_data.thriftscala.ControllelonrData
import com.twittelonr.util.Baselon64Stringelonncodelonr

/**
 * uselond for attribution pelonr targelont-candidatelon pair
 * @param selonssionId         tracelon-id of thelon finaglelon relonquelonst
 * @param controllelonrData    64-bit elonncodelond binary attributelons of our reloncommelonndation
 * @param algorithmId       id for idelonntifying a candidatelon sourcelon. maintainelond for backwards compatibility
 */
caselon class TrackingTokelonn(
  selonssionId: Long,
  displayLocation: Option[DisplayLocation],
  controllelonrData: Option[ControllelonrData],
  algorithmId: Option[Int]) {

  delonf toThrift: t.TrackingTokelonn = {
    Tracelon.id.tracelonId.toLong
    t.TrackingTokelonn(
      selonssionId = selonssionId,
      displayLocation = displayLocation.map(_.toThrift),
      controllelonrData = controllelonrData,
      algoId = algorithmId
    )
  }

  delonf toOfflinelonThrift: offlinelon.TrackingTokelonn = {
    offlinelon.TrackingTokelonn(
      selonssionId = selonssionId,
      displayLocation = displayLocation.map(_.toOfflinelonThrift),
      controllelonrData = controllelonrData,
      algoId = algorithmId
    )
  }
}

objelonct TrackingTokelonn {
  val binaryThriftSelonrializelonr = BinaryThriftStructSelonrializelonr[t.TrackingTokelonn](t.TrackingTokelonn)
  delonf selonrializelon(trackingTokelonn: TrackingTokelonn): String = {
    Baselon64Stringelonncodelonr.elonncodelon(binaryThriftSelonrializelonr.toBytelons(trackingTokelonn.toThrift))
  }
  delonf delonselonrializelon(trackingTokelonnStr: String): TrackingTokelonn = {
    fromThrift(binaryThriftSelonrializelonr.fromBytelons(Baselon64Stringelonncodelonr.deloncodelon(trackingTokelonnStr)))
  }
  delonf fromThrift(tokelonn: t.TrackingTokelonn): TrackingTokelonn = {
    TrackingTokelonn(
      selonssionId = tokelonn.selonssionId,
      displayLocation = tokelonn.displayLocation.map(DisplayLocation.fromThrift),
      controllelonrData = tokelonn.controllelonrData,
      algorithmId = tokelonn.algoId
    )
  }
}

trait HasTrackingTokelonn {
  delonf trackingTokelonn: Option[TrackingTokelonn]
}
