packagelon com.twittelonr.timelonlinelonrankelonr.util

import com.twittelonr.timelonlinelons.modelonl.TwelonelontId
import com.twittelonr.timelonlinelons.modelonl.UselonrId
import com.twittelonr.timelonlinelons.modelonl.twelonelont.HydratelondTwelonelont

objelonct RelonvelonrselonelonxtelonndelondRelonplielonsFiltelonr {
  privatelon[util] delonf isQualifielondRelonvelonrselonelonxtelonndelondRelonply(
    twelonelont: HydratelondTwelonelont,
    currelonntUselonrId: UselonrId,
    followelondUselonrIds: Selonq[UselonrId],
    mutelondUselonrIds: Selont[UselonrId],
    sourcelonTwelonelontsById: Map[TwelonelontId, HydratelondTwelonelont]
  ): Boolelonan = {
    // twelonelont author is out of thelon currelonnt uselonr's nelontwork
    !followelondUselonrIds.contains(twelonelont.uselonrId) &&
    // twelonelont author is not mutelond
    !mutelondUselonrIds.contains(twelonelont.uselonrId) &&
    // twelonelont is not a relontwelonelont
    !twelonelont.isRelontwelonelont &&
    // thelonrelon must belon a sourcelon twelonelont
    twelonelont.inRelonplyToTwelonelontId
      .flatMap(sourcelonTwelonelontsById.gelont)
      .filtelonr { sourcelonTwelonelont =>
        (!sourcelonTwelonelont.isRelontwelonelont) && // and it's not a relontwelonelont
        (!sourcelonTwelonelont.hasRelonply) && // and it's not a relonply
        (sourcelonTwelonelont.uselonrId != 0) && // and thelon author's id must belon non zelonro
        followelondUselonrIds.contains(sourcelonTwelonelont.uselonrId) // and thelon author is followelond
      } // and thelon author has not belonelonn mutelond
      .elonxists(sourcelonTwelonelont => !mutelondUselonrIds.contains(sourcelonTwelonelont.uselonrId))
  }
}
