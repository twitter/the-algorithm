packagelon com.twittelonr.follow_reloncommelonndations.common.modelonls

import com.twittelonr.follow_reloncommelonndations.logging.{thriftscala => offlinelon}
import com.twittelonr.follow_reloncommelonndations.{thriftscala => t}

caselon class ReloncelonntlyelonngagelondUselonrId(id: Long, elonngagelonmelonntTypelon: elonngagelonmelonntTypelon) {
  delonf toThrift: t.ReloncelonntlyelonngagelondUselonrId =
    t.ReloncelonntlyelonngagelondUselonrId(id = id, elonngagelonmelonntTypelon = elonngagelonmelonntTypelon.toThrift)

  delonf toOfflinelonThrift: offlinelon.ReloncelonntlyelonngagelondUselonrId =
    offlinelon.ReloncelonntlyelonngagelondUselonrId(id = id, elonngagelonmelonntTypelon = elonngagelonmelonntTypelon.toOfflinelonThrift)
}

objelonct ReloncelonntlyelonngagelondUselonrId {
  delonf fromThrift(reloncelonntlyelonngagelondUselonrId: t.ReloncelonntlyelonngagelondUselonrId): ReloncelonntlyelonngagelondUselonrId = {
    ReloncelonntlyelonngagelondUselonrId(
      id = reloncelonntlyelonngagelondUselonrId.id,
      elonngagelonmelonntTypelon = elonngagelonmelonntTypelon.fromThrift(reloncelonntlyelonngagelondUselonrId.elonngagelonmelonntTypelon)
    )
  }

  delonf fromOfflinelonThrift(
    reloncelonntlyelonngagelondUselonrId: offlinelon.ReloncelonntlyelonngagelondUselonrId
  ): ReloncelonntlyelonngagelondUselonrId = {
    ReloncelonntlyelonngagelondUselonrId(
      id = reloncelonntlyelonngagelondUselonrId.id,
      elonngagelonmelonntTypelon = elonngagelonmelonntTypelon.fromOfflinelonThrift(reloncelonntlyelonngagelondUselonrId.elonngagelonmelonntTypelon)
    )
  }

}
