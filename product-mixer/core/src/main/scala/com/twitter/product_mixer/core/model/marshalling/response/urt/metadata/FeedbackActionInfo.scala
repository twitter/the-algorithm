packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata

trait HasFelonelondbackActionInfo {
  delonf felonelondbackActionInfo: Option[FelonelondbackActionInfo]
}

trait ContainsFelonelondbackActionInfos {
  delonf felonelondbackActionInfos: Selonq[Option[FelonelondbackActionInfo]]
}

caselon class FelonelondbackActionInfo(
  felonelondbackActions: Selonq[FelonelondbackAction],
  felonelondbackMelontadata: Option[String],
  displayContelonxt: Option[FelonelondbackDisplayContelonxt],
  clielonntelonvelonntInfo: Option[ClielonntelonvelonntInfo])
