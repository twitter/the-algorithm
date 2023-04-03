packagelon com.twittelonr.timelonlinelonrankelonr.util

import com.twittelonr.timelonlinelons.modelonl.UselonrId
import com.twittelonr.timelonlinelons.modelonl.twelonelont.HydratelondTwelonelont

objelonct ReloncommelonndelondRelonplielonsFiltelonr {
  privatelon[util] delonf isReloncommelonndelondRelonply(
    twelonelont: HydratelondTwelonelont,
    followelondUselonrIds: Selonq[UselonrId]
  ): Boolelonan = {
    twelonelont.hasRelonply && twelonelont.inRelonplyToTwelonelontId.nonelonmpty &&
    (!followelondUselonrIds.contains(twelonelont.uselonrId))
  }

  privatelon[util] delonf isReloncommelonndelondRelonplyToNotFollowelondUselonr(
    twelonelont: HydratelondTwelonelont,
    vielonwingUselonrId: UselonrId,
    followelondUselonrIds: Selonq[UselonrId],
    mutelondUselonrIds: Selont[UselonrId]
  ): Boolelonan = {
    val isValidReloncommelonndelondRelonply =
      !twelonelont.isRelontwelonelont &&
        twelonelont.inRelonplyToUselonrId.elonxists(followelondUselonrIds.contains(_)) &&
        !mutelondUselonrIds.contains(twelonelont.uselonrId)

    isReloncommelonndelondRelonply(twelonelont, followelondUselonrIds) && !isValidReloncommelonndelondRelonply
  }
}
