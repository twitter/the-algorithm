packagelon com.twittelonr.simclustelonrsann.elonxcelonptions

import com.twittelonr.finaglelon.Relonquelonstelonxcelonption
import com.twittelonr.simclustelonrs_v2.thriftscala.elonmbelonddingTypelon
import com.twittelonr.simclustelonrs_v2.thriftscala.ModelonlVelonrsion

caselon class InvalidRelonquelonstForSimClustelonrsAnnVariantelonxcelonption(
  modelonlVelonrsion: ModelonlVelonrsion,
  elonmbelonddingTypelon: elonmbelonddingTypelon,
  actualSelonrvicelonNamelon: String,
  elonxpelonctelondSelonrvicelonNamelon: Option[String])
    elonxtelonnds Relonquelonstelonxcelonption(
      s"Relonquelonst with modelonl velonrsion ($modelonlVelonrsion) and elonmbelondding typelon ($elonmbelonddingTypelon) cannot belon " +
        s"procelonsselond by selonrvicelon variant ($actualSelonrvicelonNamelon)." +
        s" elonxpelonctelond selonrvicelon variant: $elonxpelonctelondSelonrvicelonNamelon.",
      null)
