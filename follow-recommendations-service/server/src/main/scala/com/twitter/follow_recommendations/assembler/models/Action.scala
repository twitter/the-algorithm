packagelon com.twittelonr.follow_reloncommelonndations.asselonmblelonr.modelonls

import com.twittelonr.follow_reloncommelonndations.{thriftscala => t}

caselon class Action(telonxt: String, actionURL: String) {
  lazy val toThrift: t.Action = {
    t.Action(telonxt, actionURL)
  }
}
