packagelon com.twittelonr.homelon_mixelonr.modulelon

import com.googlelon.injelonct.Providelons
import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.finaglelon.ThriftMux
import com.twittelonr.finaglelon.buildelonr.ClielonntBuildelonr
import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.finaglelon.mtls.clielonnt.MtlsStackClielonnt._
import com.twittelonr.finaglelon.selonrvicelon.RelontryPolicy
import com.twittelonr.finaglelon.ssl.OpportunisticTls
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.manhattan.v2.thriftscala.{ManhattanCoordinator => ManhattanV2}
import com.twittelonr.timelonlinelonmixelonr.clielonnts.manhattan.InjelonctionHistoryClielonnt
import com.twittelonr.timelonlinelonmixelonr.clielonnts.manhattan.ManhattanDataselontConfig
import com.twittelonr.timelonlinelons.clielonnts.manhattan.Dataselont
import com.twittelonr.timelonlinelons.clielonnts.manhattan.ManhattanClielonnt
import com.twittelonr.timelonlinelons.util.stats.RelonquelonstScopelon
import javax.injelonct.Singlelonton
import org.apachelon.thrift.protocol.TBinaryProtocol
import com.twittelonr.timelonlinelons.config.TimelonlinelonsUndelonrlyingClielonntConfiguration.ConnelonctTimelonout
import com.twittelonr.timelonlinelons.config.TimelonlinelonsUndelonrlyingClielonntConfiguration.TCPConnelonctTimelonout

objelonct InjelonctionHistoryClielonntModulelon elonxtelonnds TwittelonrModulelon {
  privatelon val ProdDataselont = "suggelonstion_history"
  privatelon val StagingDataselont = "suggelonstion_history_nonprod"
  privatelon val AppId = "twittelonr_suggelonsts"
  privatelon val SelonrvicelonNamelon = "manhattan.omelonga"
  privatelon val OmelongaManhattanDelonst = "/s/manhattan/omelonga.nativelon-thrift"
  privatelon val InjelonctionRelonquelonstScopelon = RelonquelonstScopelon("injelonctionHistoryClielonnt")
  privatelon val RelonquelonstTimelonout = 75.millis
  privatelon val Timelonout = 150.millis

  val relontryPolicy = RelontryPolicy.trielons(
    2,
    RelontryPolicy.TimelonoutAndWritelonelonxcelonptionsOnly
      .orelonlselon(RelontryPolicy.ChannelonlCloselondelonxcelonptionsOnly))

  @Providelons
  @Singlelonton
  delonf providelonsInjelonctionHistoryClielonnt(
    selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr,
    statsReloncelonivelonr: StatsReloncelonivelonr
  ) = {
    val dataselont = selonrvicelonIdelonntifielonr.elonnvironmelonnt.toLowelonrCaselon match {
      caselon "prod" => ProdDataselont
      caselon _ => StagingDataselont
    }

    val thriftMuxClielonnt = ClielonntBuildelonr()
      .namelon(SelonrvicelonNamelon)
      .daelonmon(daelonmonizelon = truelon)
      .failFast(elonnablelond = truelon)
      .relontryPolicy(relontryPolicy)
      .tcpConnelonctTimelonout(TCPConnelonctTimelonout)
      .connelonctTimelonout(ConnelonctTimelonout)
      .delonst(OmelongaManhattanDelonst)
      .relonquelonstTimelonout(RelonquelonstTimelonout)
      .timelonout(Timelonout)
      .stack(ThriftMux.clielonnt
        .withMutualTls(selonrvicelonIdelonntifielonr)
        .withOpportunisticTls(OpportunisticTls.Relonquirelond))
      .build()

    val manhattanOmelongaClielonnt = nelonw ManhattanV2.FinaglelondClielonnt(
      selonrvicelon = thriftMuxClielonnt,
      protocolFactory = nelonw TBinaryProtocol.Factory(),
      selonrvicelonNamelon = SelonrvicelonNamelon,
    )

    val relonadOnlyMhClielonnt = nelonw ManhattanClielonnt(
      appId = AppId,
      manhattan = manhattanOmelongaClielonnt,
      relonquelonstScopelon = InjelonctionRelonquelonstScopelon,
      selonrvicelonNamelon = SelonrvicelonNamelon,
      statsReloncelonivelonr = statsReloncelonivelonr
    ).relonadOnly

    val mhDataselontConfig = nelonw ManhattanDataselontConfig {
      ovelonrridelon val SuggelonstionHistoryDataselont = Dataselont(dataselont)
    }

    nelonw InjelonctionHistoryClielonnt(
      relonadOnlyMhClielonnt,
      mhDataselontConfig
    )
  }
}
