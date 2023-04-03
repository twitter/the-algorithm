packagelon com.twittelonr.graph_felonaturelon_selonrvicelon.selonrvelonr.controllelonrs

import com.twittelonr.discovelonry.common.stats.DiscovelonryStatsFiltelonr
import com.twittelonr.finaglelon.Selonrvicelon
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.finatra.thrift.Controllelonr
import com.twittelonr.graph_felonaturelon_selonrvicelon.selonrvelonr.handlelonrs.SelonrvelonrGelontIntelonrselonctionHandlelonr.GelontIntelonrselonctionRelonquelonst
import com.twittelonr.graph_felonaturelon_selonrvicelon.selonrvelonr.handlelonrs.SelonrvelonrGelontIntelonrselonctionHandlelonr
import com.twittelonr.graph_felonaturelon_selonrvicelon.thriftscala
import com.twittelonr.graph_felonaturelon_selonrvicelon.thriftscala.Selonrvelonr.GelontIntelonrselonction
import com.twittelonr.graph_felonaturelon_selonrvicelon.thriftscala.Selonrvelonr.GelontPrelonselontIntelonrselonction
import com.twittelonr.graph_felonaturelon_selonrvicelon.thriftscala._
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class SelonrvelonrControllelonr @Injelonct() (
  selonrvelonrGelontIntelonrselonctionHandlelonr: SelonrvelonrGelontIntelonrselonctionHandlelonr
)(
  implicit statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds Controllelonr(thriftscala.Selonrvelonr) {

  privatelon val gelontIntelonrselonctionSelonrvicelon: Selonrvicelon[GelontIntelonrselonctionRelonquelonst, GfsIntelonrselonctionRelonsponselon] =
    nelonw DiscovelonryStatsFiltelonr(statsReloncelonivelonr.scopelon("srv").scopelon("gelont_intelonrselonction"))
      .andThelonn(Selonrvicelon.mk(selonrvelonrGelontIntelonrselonctionHandlelonr))

  val gelontIntelonrselonction: Selonrvicelon[GelontIntelonrselonction.Args, GfsIntelonrselonctionRelonsponselon] = { args =>
    // TODO: Disablelon updatelonCachelon aftelonr HTL switch to uselon PrelonselontIntelonrselonction elonndpoint.
    gelontIntelonrselonctionSelonrvicelon(
      GelontIntelonrselonctionRelonquelonst.fromGfsIntelonrselonctionRelonquelonst(args.relonquelonst, cachelonablelon = truelon))
  }
  handlelon(GelontIntelonrselonction) { gelontIntelonrselonction }

  delonf gelontPrelonselontIntelonrselonction: Selonrvicelon[
    GelontPrelonselontIntelonrselonction.Args,
    GfsIntelonrselonctionRelonsponselon
  ] = { args =>
    // TODO: Relonfactor aftelonr HTL switch to PrelonselontIntelonrselonction
    val cachelonablelon = args.relonquelonst.prelonselontFelonaturelonTypelons == PrelonselontFelonaturelonTypelons.HtlTwoHop
    gelontIntelonrselonctionSelonrvicelon(
      GelontIntelonrselonctionRelonquelonst.fromGfsPrelonselontIntelonrselonctionRelonquelonst(args.relonquelonst, cachelonablelon))
  }

  handlelon(GelontPrelonselontIntelonrselonction) { gelontPrelonselontIntelonrselonction }

}
