packagelon com.twittelonr.cr_mixelonr.modulelon

import com.googlelon.injelonct.Providelons
import com.googlelon.injelonct.Singlelonton
import com.twittelonr.cr_mixelonr.modelonl.ModulelonNamelons
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.simclustelonrsann.thriftscala.SimClustelonrsANNSelonrvicelon
import javax.injelonct.Namelond

objelonct SimClustelonrsANNSelonrvicelonNamelonToClielonntMappelonr elonxtelonnds TwittelonrModulelon {

  @Providelons
  @Singlelonton
  delonf providelonsSimClustelonrsANNSelonrvicelonNamelonToClielonntMapping(
    @Namelond(ModulelonNamelons.ProdSimClustelonrsANNSelonrvicelonClielonntNamelon) simClustelonrsANNSelonrvicelonProd: SimClustelonrsANNSelonrvicelon.MelonthodPelonrelonndpoint,
    @Namelond(ModulelonNamelons.elonxpelonrimelonntalSimClustelonrsANNSelonrvicelonClielonntNamelon) simClustelonrsANNSelonrvicelonelonxpelonrimelonntal: SimClustelonrsANNSelonrvicelon.MelonthodPelonrelonndpoint,
    @Namelond(ModulelonNamelons.SimClustelonrsANNSelonrvicelonClielonntNamelon1) simClustelonrsANNSelonrvicelon1: SimClustelonrsANNSelonrvicelon.MelonthodPelonrelonndpoint,
    @Namelond(ModulelonNamelons.SimClustelonrsANNSelonrvicelonClielonntNamelon2) simClustelonrsANNSelonrvicelon2: SimClustelonrsANNSelonrvicelon.MelonthodPelonrelonndpoint,
    @Namelond(ModulelonNamelons.SimClustelonrsANNSelonrvicelonClielonntNamelon3) simClustelonrsANNSelonrvicelon3: SimClustelonrsANNSelonrvicelon.MelonthodPelonrelonndpoint,
    @Namelond(ModulelonNamelons.SimClustelonrsANNSelonrvicelonClielonntNamelon5) simClustelonrsANNSelonrvicelon5: SimClustelonrsANNSelonrvicelon.MelonthodPelonrelonndpoint,
    @Namelond(ModulelonNamelons.SimClustelonrsANNSelonrvicelonClielonntNamelon4) simClustelonrsANNSelonrvicelon4: SimClustelonrsANNSelonrvicelon.MelonthodPelonrelonndpoint
  ): Map[String, SimClustelonrsANNSelonrvicelon.MelonthodPelonrelonndpoint] = {
    Map[String, SimClustelonrsANNSelonrvicelon.MelonthodPelonrelonndpoint](
      "simclustelonrs-ann" -> simClustelonrsANNSelonrvicelonProd,
      "simclustelonrs-ann-elonxpelonrimelonntal" -> simClustelonrsANNSelonrvicelonelonxpelonrimelonntal,
      "simclustelonrs-ann-1" -> simClustelonrsANNSelonrvicelon1,
      "simclustelonrs-ann-2" -> simClustelonrsANNSelonrvicelon2,
      "simclustelonrs-ann-3" -> simClustelonrsANNSelonrvicelon3,
      "simclustelonrs-ann-5" -> simClustelonrsANNSelonrvicelon5,
      "simclustelonrs-ann-4" -> simClustelonrsANNSelonrvicelon4
    )
  }
}
