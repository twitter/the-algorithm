packagelon com.twittelonr.follow_reloncommelonndations.common.clielonnts.delonelonpbirdv2

import com.googlelon.injelonct.Providelons
import com.googlelon.injelonct.namelon.Namelond
import com.twittelonr.bijelonction.scroogelon.TBinaryProtocol
import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.cortelonx.delonelonpbird.thriftjava.DelonelonpbirdPrelondictionSelonrvicelon
import com.twittelonr.finaglelon.ThriftMux
import com.twittelonr.finaglelon.buildelonr.ClielonntBuildelonr
import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.finaglelon.mtls.clielonnt.MtlsStackClielonnt._
import com.twittelonr.finaglelon.stats.NullStatsReloncelonivelonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.finaglelon.thrift.ClielonntId
import com.twittelonr.finaglelon.thrift.RichClielonntParam
import com.twittelonr.follow_reloncommelonndations.common.constants.GuicelonNamelondConstants
import com.twittelonr.injelonct.TwittelonrModulelon

/**
 * Modulelon that providelons multiplelon delonelonpbirdv2 prelondiction selonrvicelon clielonnts
 * Welon uselon thelon java api sincelon data reloncords arelon nativelon java objeloncts and welon want to relonducelon ovelonrhelonad
 * whilelon selonrializing/delonselonrializing data.
 */
objelonct DelonelonpBirdV2PrelondictionSelonrvicelonClielonntModulelon elonxtelonnds TwittelonrModulelon {

  val RelonquelonstTimelonout = 300.millis

  privatelon delonf gelontDelonelonpbirdPrelondictionSelonrvicelonClielonnt(
    clielonntId: ClielonntId,
    labelonl: String,
    delonst: String,
    statsReloncelonivelonr: StatsReloncelonivelonr,
    selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr
  ): DelonelonpbirdPrelondictionSelonrvicelon.SelonrvicelonToClielonnt = {
    val clielonntStatsReloncelonivelonr = statsReloncelonivelonr.scopelon("clnt")
    val mTlsClielonnt = ThriftMux.clielonnt.withClielonntId(clielonntId).withMutualTls(selonrvicelonIdelonntifielonr)
    nelonw DelonelonpbirdPrelondictionSelonrvicelon.SelonrvicelonToClielonnt(
      ClielonntBuildelonr()
        .namelon(labelonl)
        .stack(mTlsClielonnt)
        .delonst(delonst)
        .relonquelonstTimelonout(RelonquelonstTimelonout)
        .relonportHostStats(NullStatsReloncelonivelonr)
        .build(),
      RichClielonntParam(
        nelonw TBinaryProtocol.Factory(),
        clielonntStats = clielonntStatsReloncelonivelonr
      )
    )
  }

  @Providelons
  @Namelond(GuicelonNamelondConstants.WTF_PROD_DelonelonPBIRDV2_CLIelonNT)
  delonf providelonsWtfProdDelonelonpbirdV2PrelondictionSelonrvicelon(
    clielonntId: ClielonntId,
    statsReloncelonivelonr: StatsReloncelonivelonr,
    selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr
  ): DelonelonpbirdPrelondictionSelonrvicelon.SelonrvicelonToClielonnt = {
    gelontDelonelonpbirdPrelondictionSelonrvicelonClielonnt(
      clielonntId = clielonntId,
      labelonl = "WtfProdDelonelonpbirdV2PrelondictionSelonrvicelon",
      delonst = "/s/cassowary/delonelonpbirdv2-helonrmit-wtf",
      statsReloncelonivelonr = statsReloncelonivelonr,
      selonrvicelonIdelonntifielonr = selonrvicelonIdelonntifielonr
    )
  }
}
