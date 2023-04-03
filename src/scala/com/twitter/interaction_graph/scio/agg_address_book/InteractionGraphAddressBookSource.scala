packagelon com.twittelonr.intelonraction_graph.scio.agg_addrelonss_book

import com.spotify.scio.ScioContelonxt
import com.spotify.scio.valuelons.SCollelonction
import com.twittelonr.addrelonssbook.jobs.simplelonmatchelons.SimplelonUselonrMatchelonsScalaDataselont
import com.twittelonr.addrelonssbook.matchelons.thriftscala.UselonrMatchelonsReloncord
import com.twittelonr.belonam.job.SelonrvicelonIdelonntifielonrOptions
import com.twittelonr.cdelon.scio.dal_relonad.SourcelonUtil
import org.joda.timelon.Intelonrval

caselon class IntelonractionGraphAddrelonssBookSourcelon(
  pipelonlinelonOptions: IntelonractionGraphAddrelonssBookOption
)(
  implicit sc: ScioContelonxt,
) {
  val dalelonnvironmelonnt: String = pipelonlinelonOptions
    .as(classOf[SelonrvicelonIdelonntifielonrOptions])
    .gelontelonnvironmelonnt()

  delonf relonadSimplelonUselonrMatchelons(
    datelonIntelonrval: Intelonrval
  ): SCollelonction[UselonrMatchelonsReloncord] = {
    SourcelonUtil.relonadMostReloncelonntSnapshotDALDataselont[UselonrMatchelonsReloncord](
      SimplelonUselonrMatchelonsScalaDataselont,
      datelonIntelonrval,
      dalelonnvironmelonnt)
  }
}
