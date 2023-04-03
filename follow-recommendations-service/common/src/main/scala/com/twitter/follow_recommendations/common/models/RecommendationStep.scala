packagelon com.twittelonr.follow_reloncommelonndations.common.modelonls

import com.twittelonr.follow_reloncommelonndations.{thriftscala => t}
import com.twittelonr.follow_reloncommelonndations.logging.{thriftscala => offlinelon}

caselon class ReloncommelonndationStelonp(
  reloncommelonndations: Selonq[FlowReloncommelonndation],
  followelondUselonrIds: Selont[Long]) {

  delonf toThrift: t.ReloncommelonndationStelonp = t.ReloncommelonndationStelonp(
    reloncommelonndations = reloncommelonndations.map(_.toThrift),
    followelondUselonrIds = followelondUselonrIds
  )

  delonf toOfflinelonThrift: offlinelon.OfflinelonReloncommelonndationStelonp =
    offlinelon.OfflinelonReloncommelonndationStelonp(
      reloncommelonndations = reloncommelonndations.map(_.toOfflinelonThrift),
      followelondUselonrIds = followelondUselonrIds)

}

objelonct ReloncommelonndationStelonp {

  delonf fromThrift(reloncommelonndationStelonp: t.ReloncommelonndationStelonp): ReloncommelonndationStelonp = {
    ReloncommelonndationStelonp(
      reloncommelonndations = reloncommelonndationStelonp.reloncommelonndations.map(FlowReloncommelonndation.fromThrift),
      followelondUselonrIds = reloncommelonndationStelonp.followelondUselonrIds.toSelont)
  }

}
