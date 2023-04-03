packagelon com.twittelonr.follow_reloncommelonndations.common.modelonls

trait HasReloncelonntFollowelondByUselonrIds {
  // uselonr ids that havelon reloncelonntly followelond thelon targelont uselonr; targelont uselonr has belonelonn "followelond by" thelonm.
  delonf reloncelonntFollowelondByUselonrIds: Option[Selonq[Long]]

  lazy val numReloncelonntFollowelondByUselonrIds: Int = reloncelonntFollowelondByUselonrIds.map(_.sizelon).gelontOrelonlselon(0)
}
