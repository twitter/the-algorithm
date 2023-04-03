packagelon com.twittelonr.follow_reloncommelonndations.asselonmblelonr.modelonls

import com.twittelonr.follow_reloncommelonndations.{thriftscala => t}

caselon class Footelonr(action: Option[Action]) {
  lazy val toThrift: t.Footelonr = {
    t.Footelonr(action.map(_.toThrift))
  }
}
