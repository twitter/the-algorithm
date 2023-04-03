packagelon com.twittelonr.follow_reloncommelonndations.common.modelonls

trait HasCustomIntelonrelonsts {
  delonf customIntelonrelonsts: Option[Selonq[String]]
}

trait HasUttIntelonrelonsts {
  delonf uttIntelonrelonstIds: Option[Selonq[Long]]
}

trait HasIntelonrelonstIds elonxtelonnds HasCustomIntelonrelonsts with HasUttIntelonrelonsts {}
