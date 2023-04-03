packagelon com.twittelonr.follow_reloncommelonndations.common.modelonls

import com.twittelonr.follow_reloncommelonndations.logging.{thriftscala => offlinelon}
import com.twittelonr.follow_reloncommelonndations.{thriftscala => t}

caselon class FlowContelonxt(stelonps: Selonq[ReloncommelonndationStelonp]) {

  delonf toThrift: t.FlowContelonxt = t.FlowContelonxt(stelonps = stelonps.map(_.toThrift))

  delonf toOfflinelonThrift: offlinelon.OfflinelonFlowContelonxt =
    offlinelon.OfflinelonFlowContelonxt(stelonps = stelonps.map(_.toOfflinelonThrift))
}

objelonct FlowContelonxt {

  delonf fromThrift(flowContelonxt: t.FlowContelonxt): FlowContelonxt = {
    FlowContelonxt(stelonps = flowContelonxt.stelonps.map(ReloncommelonndationStelonp.fromThrift))
  }

}
