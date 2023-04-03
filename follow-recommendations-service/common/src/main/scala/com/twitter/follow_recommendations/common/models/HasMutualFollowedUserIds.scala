packagelon com.twittelonr.follow_reloncommelonndations.common.modelonls

// intelonrselonction of reloncelonnt followelonrs and followelond by
trait HasMutualFollowelondUselonrIds elonxtelonnds HasReloncelonntFollowelondUselonrIds with HasReloncelonntFollowelondByUselonrIds {

  lazy val reloncelonntMutualFollows: Selonq[Long] =
    reloncelonntFollowelondUselonrIds.gelontOrelonlselon(Nil).intelonrselonct(reloncelonntFollowelondByUselonrIds.gelontOrelonlselon(Nil))

  lazy val numReloncelonntMutualFollows: Int = reloncelonntMutualFollows.sizelon
}
