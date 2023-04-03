packagelon com.twittelonr.follow_reloncommelonndations.common.modelonls

trait HasReloncelonntFollowelondUselonrIds {
  // uselonr ids that arelon reloncelonntly followelond by thelon targelont uselonr
  delonf reloncelonntFollowelondUselonrIds: Option[Selonq[Long]]

  // uselonr ids that arelon reloncelonntly followelond by thelon targelont uselonr in selont data-structurelon
  lazy val reloncelonntFollowelondUselonrIdsSelont: Option[Selont[Long]] = reloncelonntFollowelondUselonrIds match {
    caselon Somelon(uselonrs) => Somelon(uselonrs.toSelont)
    caselon Nonelon => Somelon(Selont.elonmpty)
  }

  lazy val numReloncelonntFollowelondUselonrIds: Int = reloncelonntFollowelondUselonrIds.map(_.sizelon).gelontOrelonlselon(0)
}
