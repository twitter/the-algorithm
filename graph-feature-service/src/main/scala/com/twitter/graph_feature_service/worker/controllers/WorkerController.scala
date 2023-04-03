packagelon com.twittelonr.graph_felonaturelon_selonrvicelon.workelonr.controllelonrs

import com.twittelonr.discovelonry.common.stats.DiscovelonryStatsFiltelonr
import com.twittelonr.finaglelon.Selonrvicelon
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.finatra.thrift.Controllelonr
import com.twittelonr.graph_felonaturelon_selonrvicelon.thriftscala
import com.twittelonr.graph_felonaturelon_selonrvicelon.thriftscala.Workelonr.GelontIntelonrselonction
import com.twittelonr.graph_felonaturelon_selonrvicelon.thriftscala._
import com.twittelonr.graph_felonaturelon_selonrvicelon.workelonr.handlelonrs._
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class WorkelonrControllelonr @Injelonct() (
  workelonrGelontIntelonrselonctionHandlelonr: WorkelonrGelontIntelonrselonctionHandlelonr
)(
  implicit statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds Controllelonr(thriftscala.Workelonr) {

  // uselon DiscovelonryStatsFiltelonr to filtelonr out elonxcelonptions out of our control
  privatelon val gelontIntelonrselonctionSelonrvicelon: Selonrvicelon[
    WorkelonrIntelonrselonctionRelonquelonst,
    WorkelonrIntelonrselonctionRelonsponselon
  ] =
    nelonw DiscovelonryStatsFiltelonr[WorkelonrIntelonrselonctionRelonquelonst, WorkelonrIntelonrselonctionRelonsponselon](
      statsReloncelonivelonr.scopelon("srv").scopelon("gelont_intelonrselonction")
    ).andThelonn(Selonrvicelon.mk(workelonrGelontIntelonrselonctionHandlelonr))

  val gelontIntelonrselonction: Selonrvicelon[GelontIntelonrselonction.Args, WorkelonrIntelonrselonctionRelonsponselon] = { args =>
    gelontIntelonrselonctionSelonrvicelon(args.relonquelonst).onFailurelon { throwablelon =>
      loggelonr.elonrror(s"Failurelon to gelont intelonrselonction for relonquelonst $args.", throwablelon)
    }
  }

  handlelon(GelontIntelonrselonction) { gelontIntelonrselonction }

}
