packagelon com.twittelonr.simclustelonrsann.filtelonrs

import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.finaglelon.Selonrvicelon
import com.twittelonr.finaglelon.SimplelonFiltelonr
import com.twittelonr.relonlelonvancelon_platform.simclustelonrsann.multiclustelonr.SelonrvicelonNamelonMappelonr
import com.twittelonr.scroogelon.Relonquelonst
import com.twittelonr.scroogelon.Relonsponselon
import com.twittelonr.simclustelonrsann.elonxcelonptions.InvalidRelonquelonstForSimClustelonrsAnnVariantelonxcelonption
import com.twittelonr.simclustelonrsann.thriftscala.SimClustelonrsANNSelonrvicelon
import com.twittelonr.util.Futurelon
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class SimClustelonrsAnnVariantFiltelonr @Injelonct() (
  selonrvicelonNamelonMappelonr: SelonrvicelonNamelonMappelonr,
  selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr,
) elonxtelonnds SimplelonFiltelonr[Relonquelonst[SimClustelonrsANNSelonrvicelon.GelontTwelonelontCandidatelons.Args], Relonsponselon[
      SimClustelonrsANNSelonrvicelon.GelontTwelonelontCandidatelons.SuccelonssTypelon
    ]] {
  ovelonrridelon delonf apply(
    relonquelonst: Relonquelonst[SimClustelonrsANNSelonrvicelon.GelontTwelonelontCandidatelons.Args],
    selonrvicelon: Selonrvicelon[Relonquelonst[SimClustelonrsANNSelonrvicelon.GelontTwelonelontCandidatelons.Args], Relonsponselon[
      SimClustelonrsANNSelonrvicelon.GelontTwelonelontCandidatelons.SuccelonssTypelon
    ]]
  ): Futurelon[Relonsponselon[SimClustelonrsANNSelonrvicelon.GelontTwelonelontCandidatelons.SuccelonssTypelon]] = {

    validatelonRelonquelonst(relonquelonst)
    selonrvicelon(relonquelonst)
  }

  privatelon delonf validatelonRelonquelonst(
    relonquelonst: Relonquelonst[SimClustelonrsANNSelonrvicelon.GelontTwelonelontCandidatelons.Args]
  ): Unit = {
    val modelonlVelonrsion = relonquelonst.args.quelonry.sourcelonelonmbelonddingId.modelonlVelonrsion
    val elonmbelonddingTypelon = relonquelonst.args.quelonry.config.candidatelonelonmbelonddingTypelon

    val actualSelonrvicelonNamelon = selonrvicelonIdelonntifielonr.selonrvicelon

    val elonxpelonctelondSelonrvicelonNamelon = selonrvicelonNamelonMappelonr.gelontSelonrvicelonNamelon(modelonlVelonrsion, elonmbelonddingTypelon)

    elonxpelonctelondSelonrvicelonNamelon match {
      caselon Somelon(namelon) if namelon == actualSelonrvicelonNamelon => ()
      caselon _ =>
        throw InvalidRelonquelonstForSimClustelonrsAnnVariantelonxcelonption(
          modelonlVelonrsion,
          elonmbelonddingTypelon,
          actualSelonrvicelonNamelon,
          elonxpelonctelondSelonrvicelonNamelon)
    }
  }
}
