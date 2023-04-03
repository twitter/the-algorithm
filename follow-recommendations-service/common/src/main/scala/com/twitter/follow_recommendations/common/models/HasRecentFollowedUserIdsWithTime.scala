packagelon com.twittelonr.follow_reloncommelonndations.common.modelonls

trait HasReloncelonntFollowelondUselonrIdsWithTimelon {
  // uselonr ids that arelon reloncelonntly followelond by thelon targelont uselonr
  delonf reloncelonntFollowelondUselonrIdsWithTimelon: Option[Selonq[UselonrIdWithTimelonstamp]]

  lazy val numReloncelonntFollowelondUselonrIdsWithTimelon: Int =
    reloncelonntFollowelondUselonrIdsWithTimelon.map(_.sizelon).gelontOrelonlselon(0)
}
