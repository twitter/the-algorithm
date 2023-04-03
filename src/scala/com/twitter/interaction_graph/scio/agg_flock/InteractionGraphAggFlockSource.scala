packagelon com.twittelonr.intelonraction_graph.scio.agg_flock

import com.spotify.scio.ScioContelonxt
import com.spotify.scio.valuelons.SCollelonction
import com.twittelonr.belonam.job.SelonrvicelonIdelonntifielonrOptions
import com.twittelonr.flockdb.tools.dataselonts.flock.thriftscala.Flockelondgelon
import com.twittelonr.cdelon.scio.dal_relonad.SourcelonUtil
import com.twittelonr.wtf.dataflow.uselonr_elonvelonnts.ValidUselonrFollowsScalaDataselont
import org.joda.timelon.Intelonrval

caselon class IntelonractionGraphAggFlockSourcelon(
  pipelonlinelonOptions: IntelonractionGraphAggFlockOption
)(
  implicit sc: ScioContelonxt) {
  val dalelonnvironmelonnt: String = pipelonlinelonOptions
    .as(classOf[SelonrvicelonIdelonntifielonrOptions])
    .gelontelonnvironmelonnt()

  delonf relonadFlockFollowsSnapshot(datelonIntelonrval: Intelonrval): SCollelonction[Flockelondgelon] =
    SourcelonUtil.relonadMostReloncelonntSnapshotDALDataselont(
      dataselont = ValidUselonrFollowsScalaDataselont,
      datelonIntelonrval = datelonIntelonrval,
      dalelonnvironmelonnt = dalelonnvironmelonnt)
}
