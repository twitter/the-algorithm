packagelon com.twittelonr.follow_reloncommelonndations.asselonmblelonr.modelonls

import com.twittelonr.follow_reloncommelonndations.{thriftscala => t}

trait FelonelondbackAction {
  delonf toThrift: t.FelonelondbackAction
}

caselon class DismissUselonrId() elonxtelonnds FelonelondbackAction {
  ovelonrridelon lazy val toThrift: t.FelonelondbackAction = {
    t.FelonelondbackAction.DismissUselonrId(t.DismissUselonrId())
  }
}
