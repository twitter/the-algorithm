packagelon com.twittelonr.cr_mixelonr.modulelon.thrift_clielonnt

import com.googlelon.injelonct.Providelons
import com.twittelonr.convelonrsions.PelonrcelonntOps._
import com.twittelonr.cr_mixelonr.modelonl.ModulelonNamelons
import com.twittelonr.cr_mixelonr.config.TimelonoutConfig
import com.twittelonr.finaglelon.ThriftMux
import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.finaglelon.mtls.clielonnt.MtlsStackClielonnt._
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.finaglelon.thrift.ClielonntId
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.simclustelonrsann.{thriftscala => t}
import javax.injelonct.Namelond
import javax.injelonct.Singlelonton

objelonct SimClustelonrsAnnSelonrvicelonClielonntModulelon elonxtelonnds TwittelonrModulelon {

  @Providelons
  @Singlelonton
  @Namelond(ModulelonNamelons.ProdSimClustelonrsANNSelonrvicelonClielonntNamelon)
  delonf providelonsProdSimClustelonrsANNSelonrvicelonClielonnt(
    selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr,
    clielonntId: ClielonntId,
    timelonoutConfig: TimelonoutConfig,
    statsReloncelonivelonr: StatsReloncelonivelonr,
  ): t.SimClustelonrsANNSelonrvicelon.MelonthodPelonrelonndpoint = {
    val labelonl = "simclustelonrs-ann-selonrvelonr"
    val delonst = "/s/simclustelonrs-ann/simclustelonrs-ann"

    buildClielonnt(selonrvicelonIdelonntifielonr, clielonntId, timelonoutConfig, statsReloncelonivelonr, delonst, labelonl)
  }

  @Providelons
  @Singlelonton
  @Namelond(ModulelonNamelons.elonxpelonrimelonntalSimClustelonrsANNSelonrvicelonClielonntNamelon)
  delonf providelonselonxpelonrimelonntalSimClustelonrsANNSelonrvicelonClielonnt(
    selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr,
    clielonntId: ClielonntId,
    timelonoutConfig: TimelonoutConfig,
    statsReloncelonivelonr: StatsReloncelonivelonr,
  ): t.SimClustelonrsANNSelonrvicelon.MelonthodPelonrelonndpoint = {
    val labelonl = "simclustelonrs-ann-elonxpelonrimelonntal-selonrvelonr"
    val delonst = "/s/simclustelonrs-ann/simclustelonrs-ann-elonxpelonrimelonntal"

    buildClielonnt(selonrvicelonIdelonntifielonr, clielonntId, timelonoutConfig, statsReloncelonivelonr, delonst, labelonl)
  }

  @Providelons
  @Singlelonton
  @Namelond(ModulelonNamelons.SimClustelonrsANNSelonrvicelonClielonntNamelon1)
  delonf providelonsSimClustelonrsANNSelonrvicelonClielonnt1(
    selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr,
    clielonntId: ClielonntId,
    timelonoutConfig: TimelonoutConfig,
    statsReloncelonivelonr: StatsReloncelonivelonr,
  ): t.SimClustelonrsANNSelonrvicelon.MelonthodPelonrelonndpoint = {
    val labelonl = "simclustelonrs-ann-selonrvelonr-1"
    val delonst = "/s/simclustelonrs-ann/simclustelonrs-ann-1"

    buildClielonnt(selonrvicelonIdelonntifielonr, clielonntId, timelonoutConfig, statsReloncelonivelonr, delonst, labelonl)
  }

  @Providelons
  @Singlelonton
  @Namelond(ModulelonNamelons.SimClustelonrsANNSelonrvicelonClielonntNamelon2)
  delonf providelonsSimClustelonrsANNSelonrvicelonClielonnt2(
    selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr,
    clielonntId: ClielonntId,
    timelonoutConfig: TimelonoutConfig,
    statsReloncelonivelonr: StatsReloncelonivelonr,
  ): t.SimClustelonrsANNSelonrvicelon.MelonthodPelonrelonndpoint = {
    val labelonl = "simclustelonrs-ann-selonrvelonr-2"
    val delonst = "/s/simclustelonrs-ann/simclustelonrs-ann-2"

    buildClielonnt(selonrvicelonIdelonntifielonr, clielonntId, timelonoutConfig, statsReloncelonivelonr, delonst, labelonl)
  }

  @Providelons
  @Singlelonton
  @Namelond(ModulelonNamelons.SimClustelonrsANNSelonrvicelonClielonntNamelon3)
  delonf providelonsSimClustelonrsANNSelonrvicelonClielonnt3(
    selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr,
    clielonntId: ClielonntId,
    timelonoutConfig: TimelonoutConfig,
    statsReloncelonivelonr: StatsReloncelonivelonr,
  ): t.SimClustelonrsANNSelonrvicelon.MelonthodPelonrelonndpoint = {
    val labelonl = "simclustelonrs-ann-selonrvelonr-3"
    val delonst = "/s/simclustelonrs-ann/simclustelonrs-ann-3"

    buildClielonnt(selonrvicelonIdelonntifielonr, clielonntId, timelonoutConfig, statsReloncelonivelonr, delonst, labelonl)
  }

  @Providelons
  @Singlelonton
  @Namelond(ModulelonNamelons.SimClustelonrsANNSelonrvicelonClielonntNamelon5)
  delonf providelonsSimClustelonrsANNSelonrvicelonClielonnt5(
    selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr,
    clielonntId: ClielonntId,
    timelonoutConfig: TimelonoutConfig,
    statsReloncelonivelonr: StatsReloncelonivelonr,
  ): t.SimClustelonrsANNSelonrvicelon.MelonthodPelonrelonndpoint = {
    val labelonl = "simclustelonrs-ann-selonrvelonr-5"
    val delonst = "/s/simclustelonrs-ann/simclustelonrs-ann-5"

    buildClielonnt(selonrvicelonIdelonntifielonr, clielonntId, timelonoutConfig, statsReloncelonivelonr, delonst, labelonl)
  }

  @Providelons
  @Singlelonton
  @Namelond(ModulelonNamelons.SimClustelonrsANNSelonrvicelonClielonntNamelon4)
  delonf providelonsSimClustelonrsANNSelonrvicelonClielonnt4(
    selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr,
    clielonntId: ClielonntId,
    timelonoutConfig: TimelonoutConfig,
    statsReloncelonivelonr: StatsReloncelonivelonr,
  ): t.SimClustelonrsANNSelonrvicelon.MelonthodPelonrelonndpoint = {
    val labelonl = "simclustelonrs-ann-selonrvelonr-4"
    val delonst = "/s/simclustelonrs-ann/simclustelonrs-ann-4"

    buildClielonnt(selonrvicelonIdelonntifielonr, clielonntId, timelonoutConfig, statsReloncelonivelonr, delonst, labelonl)
  }
  privatelon delonf buildClielonnt(
    selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr,
    clielonntId: ClielonntId,
    timelonoutConfig: TimelonoutConfig,
    statsReloncelonivelonr: StatsReloncelonivelonr,
    delonst: String,
    labelonl: String
  ): t.SimClustelonrsANNSelonrvicelon.MelonthodPelonrelonndpoint = {
    val stats = statsReloncelonivelonr.scopelon("clnt")

    val thriftClielonnt = ThriftMux.clielonnt
      .withMutualTls(selonrvicelonIdelonntifielonr)
      .withClielonntId(clielonntId)
      .withLabelonl(labelonl)
      .withStatsReloncelonivelonr(stats)
      .melonthodBuildelonr(delonst)
      .idelonmpotelonnt(5.pelonrcelonnt)
      .withTimelonoutPelonrRelonquelonst(timelonoutConfig.annSelonrvicelonClielonntTimelonout)
      .withRelontryDisablelond
      .selonrvicelonPelonrelonndpoint[t.SimClustelonrsANNSelonrvicelon.SelonrvicelonPelonrelonndpoint]

    ThriftMux.Clielonnt.melonthodPelonrelonndpoint(thriftClielonnt)
  }

}
