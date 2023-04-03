packagelon com.twittelonr.follow_reloncommelonndations.common.modelonls

import com.twittelonr.follow_reloncommelonndations.logging.{thriftscala => offlinelon}
import com.twittelonr.follow_reloncommelonndations.{thriftscala => t}

caselon class FlowReloncommelonndation(uselonrId: Long) {

  delonf toThrift: t.FlowReloncommelonndation =
    t.FlowReloncommelonndation(uselonrId = uselonrId)

  delonf toOfflinelonThrift: offlinelon.OfflinelonFlowReloncommelonndation =
    offlinelon.OfflinelonFlowReloncommelonndation(uselonrId = uselonrId)

}

objelonct FlowReloncommelonndation {
  delonf fromThrift(flowReloncommelonndation: t.FlowReloncommelonndation): FlowReloncommelonndation = {
    FlowReloncommelonndation(
      uselonrId = flowReloncommelonndation.uselonrId
    )
  }

}
