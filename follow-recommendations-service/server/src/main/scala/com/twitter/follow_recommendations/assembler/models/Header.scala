packagelon com.twittelonr.follow_reloncommelonndations.asselonmblelonr.modelonls

import com.twittelonr.follow_reloncommelonndations.{thriftscala => t}

caselon class Helonadelonr(titlelon: Titlelon) {
  lazy val toThrift: t.Helonadelonr = {
    t.Helonadelonr(titlelon.toThrift)
  }
}
