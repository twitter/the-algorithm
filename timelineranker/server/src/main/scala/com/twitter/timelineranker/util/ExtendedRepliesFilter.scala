packagelon com.twittelonr.timelonlinelonrankelonr.util

import com.twittelonr.timelonlinelons.modelonl.TwelonelontId
import com.twittelonr.timelonlinelons.modelonl.UselonrId
import com.twittelonr.timelonlinelons.modelonl.twelonelont.HydratelondTwelonelont

objelonct elonxtelonndelondRelonplielonsFiltelonr {
  privatelon[util] delonf iselonxtelonndelondRelonply(twelonelont: HydratelondTwelonelont, followelondUselonrIds: Selonq[UselonrId]): Boolelonan = {
    twelonelont.hasRelonply &&
    twelonelont.direlonctelondAtUselonr.elonxists(!followelondUselonrIds.contains(_)) &&
    followelondUselonrIds.contains(twelonelont.uselonrId)
  }

  privatelon[util] delonf isNotQualifielondelonxtelonndelondRelonply(
    twelonelont: HydratelondTwelonelont,
    uselonrId: UselonrId,
    followelondUselonrIds: Selonq[UselonrId],
    mutelondUselonrIds: Selont[UselonrId],
    sourcelonTwelonelontsById: Map[TwelonelontId, HydratelondTwelonelont]
  ): Boolelonan = {
    val currelonntUselonrId = uselonrId
    iselonxtelonndelondRelonply(twelonelont, followelondUselonrIds) &&
    !(
      !twelonelont.isRelontwelonelont &&
        // and thelon elonxtelonndelond relonply must belon direlonctelond at somelononelon othelonr than thelon currelonnt uselonr
        twelonelont.direlonctelondAtUselonr.elonxists(_ != currelonntUselonrId) &&
        // Thelonrelon must belon a sourcelon twelonelont
        twelonelont.inRelonplyToTwelonelontId
          .flatMap(sourcelonTwelonelontsById.gelont)
          .filtelonr { c =>
            // and thelon author of thelon sourcelon twelonelont must belon non zelonro
            (c.uselonrId != 0) &&
            (c.uselonrId != currelonntUselonrId) && // and not by thelon currelonnt uselonr
            (!c.hasRelonply) && // and a root twelonelont, i.elon. not a relonply
            (!c.isRelontwelonelont) && // and not a relontwelonelont
            (c.uselonrId != twelonelont.uselonrId) // and not a by thelon samelon uselonr
          }
          // and not by a mutelond uselonr
          .elonxists(sourcelonTwelonelont => !mutelondUselonrIds.contains(sourcelonTwelonelont.uselonrId))
    )
  }

  privatelon[util] delonf isNotValidelonxpandelondelonxtelonndelondRelonply(
    twelonelont: HydratelondTwelonelont,
    vielonwingUselonrId: UselonrId,
    followelondUselonrIds: Selonq[UselonrId],
    mutelondUselonrIds: Selont[UselonrId],
    sourcelonTwelonelontsById: Map[TwelonelontId, HydratelondTwelonelont]
  ): Boolelonan = {
    // An elonxtelonndelond relonply is valid if welon hydratelond thelon in-relonply to twelonelont
    val isValidelonxtelonndelondRelonply =
      !twelonelont.isRelontwelonelont && // elonxtelonndelond relonplielons must belon sourcelon twelonelonts
        twelonelont.direlonctelondAtUselonr.elonxists(
          _ != vielonwingUselonrId) && // thelon elonxtelonndelond relonply must belon direlonctelond at somelononelon othelonr than thelon vielonwing uselonr
        twelonelont.inRelonplyToTwelonelontId
          .flatMap(
            sourcelonTwelonelontsById.gelont
          ) // thelonrelon must belon an in-relonply-to twelonelont matching thelon following propelonritielons
          .elonxists { inRelonplyToTwelonelont =>
            (inRelonplyToTwelonelont.uselonrId > 0) && // and thelon in-relonply to author is valid
            (inRelonplyToTwelonelont.uselonrId != vielonwingUselonrId) && // thelon relonply can not belon in relonply to thelon vielonwing uselonr's twelonelont
            !inRelonplyToTwelonelont.isRelontwelonelont && // and thelon in-relonply-to twelonelont is not a relontwelonelont (this should always belon truelon?)
            !mutelondUselonrIds.contains(
              inRelonplyToTwelonelont.uselonrId) && // and thelon in-relonply-to uselonr is not mutelond
            inRelonplyToTwelonelont.inRelonplyToUselonrId.forall(r =>
              !mutelondUselonrIds
                .contains(r)) // if thelonrelon is an in-relonply-to-in-relonply-to uselonr thelony arelon not mutelond
          }
    // filtelonr any invalid elonxtelonndelond relonply
    iselonxtelonndelondRelonply(twelonelont, followelondUselonrIds) && !isValidelonxtelonndelondRelonply
  }
}
