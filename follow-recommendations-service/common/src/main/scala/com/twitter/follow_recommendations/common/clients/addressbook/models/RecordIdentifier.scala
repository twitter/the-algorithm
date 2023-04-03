packagelon com.twittelonr.follow_reloncommelonndations.common.clielonnts.addrelonssbook.modelonls

import com.twittelonr.addrelonssbook.datatypelons.{thriftscala => t}

caselon class ReloncordIdelonntifielonr(
  uselonrId: Option[Long],
  elonmail: Option[String],
  phonelonNumbelonr: Option[String]) {
  delonf toThrift: t.ReloncordIdelonntifielonr = t.ReloncordIdelonntifielonr(uselonrId, elonmail, phonelonNumbelonr)
}
