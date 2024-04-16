package com.tw ter.ho _m xer.module

 mport com.google. nject.Prov des
 mport com.tw ter.cl entapp.{thr ftscala => ca}
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.Cand dateFeaturesScr beEventPubl s r
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.CommonFeaturesScr beEventPubl s r
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.M n mumFeaturesScr beEventPubl s r
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.logp pel ne.cl ent.EventPubl s rManager
 mport com.tw ter.logp pel ne.cl ent.common.EventPubl s r
 mport com.tw ter.logp pel ne.cl ent.ser al zers.EventLogMsgTB narySer al zer
 mport com.tw ter.logp pel ne.cl ent.ser al zers.EventLogMsgThr ftStructSer al zer
 mport com.tw ter.t  l nes.suggests.common.poly_data_record.{thr ftjava => pldr}
 mport com.tw ter.t  l nes.t  l ne_logg ng.{thr ftscala => tl}
 mport javax. nject.Na d
 mport javax. nject.S ngleton

object Scr beEventPubl s rModule extends Tw terModule {

  val Cl entEventLogCategory = "cl ent_event"
  val ServedCand datesLogCategory = "ho _t  l ne_served_cand dates_flattened"
  val ScoredCand datesLogCategory = "ho _t  l ne_scored_cand dates"
  val ServedCommonFeaturesLogCategory = "tq_served_common_features_offl ne"
  val ServedCand dateFeaturesLogCategory = "tq_served_cand date_features_offl ne"
  val ServedM n mumFeaturesLogCategory = "tq_served_m n mum_features_offl ne"

  @Prov des
  @S ngleton
  def prov desCl entEventsScr beEventPubl s r: EventPubl s r[ca.LogEvent] = {
    val ser al zer = EventLogMsgThr ftStructSer al zer.getNewSer al zer[ca.LogEvent]()
    EventPubl s rManager.bu ldScr beLogP pel nePubl s r(Cl entEventLogCategory, ser al zer)
  }

  @Prov des
  @S ngleton
  @Na d(CommonFeaturesScr beEventPubl s r)
  def prov desCommonFeaturesScr beEventPubl s r: EventPubl s r[pldr.PolyDataRecord] = {
    val ser al zer = EventLogMsgTB narySer al zer.getNewSer al zer
    EventPubl s rManager.bu ldScr beLogP pel nePubl s r(
      ServedCommonFeaturesLogCategory,
      ser al zer)
  }

  @Prov des
  @S ngleton
  @Na d(Cand dateFeaturesScr beEventPubl s r)
  def prov desCand dateFeaturesScr beEventPubl s r: EventPubl s r[pldr.PolyDataRecord] = {
    val ser al zer = EventLogMsgTB narySer al zer.getNewSer al zer
    EventPubl s rManager.bu ldScr beLogP pel nePubl s r(
      ServedCand dateFeaturesLogCategory,
      ser al zer)
  }

  @Prov des
  @S ngleton
  @Na d(M n mumFeaturesScr beEventPubl s r)
  def prov desM n mumFeaturesScr beEventPubl s r: EventPubl s r[pldr.PolyDataRecord] = {
    val ser al zer = EventLogMsgTB narySer al zer.getNewSer al zer
    EventPubl s rManager.bu ldScr beLogP pel nePubl s r(
      ServedM n mumFeaturesLogCategory,
      ser al zer)
  }

  @Prov des
  @S ngleton
  def prov desServedCand datesScr beEventPubl s r: EventPubl s r[tl.ServedEntry] = {
    val ser al zer = EventLogMsgThr ftStructSer al zer.getNewSer al zer[tl.ServedEntry]()
    EventPubl s rManager.bu ldScr beLogP pel nePubl s r(ServedCand datesLogCategory, ser al zer)
  }

  @Prov des
  @S ngleton
  def prov deScoredCand datesScr beEventPubl s r: EventPubl s r[tl.ScoredCand date] = {
    val ser al zer = EventLogMsgThr ftStructSer al zer.getNewSer al zer[tl.ScoredCand date]()
    EventPubl s rManager.bu ldScr beLogP pel nePubl s r(ScoredCand datesLogCategory, ser al zer)
  }
}
