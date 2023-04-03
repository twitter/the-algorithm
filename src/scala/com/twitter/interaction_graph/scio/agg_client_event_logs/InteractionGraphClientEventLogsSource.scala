packagelon com.twittelonr.intelonraction_graph.scio.agg_clielonnt_elonvelonnt_logs

import com.spotify.scio.ScioContelonxt
import com.spotify.scio.valuelons.SCollelonction
import com.twittelonr.belonam.job.SelonrvicelonIdelonntifielonrOptions
import com.twittelonr.twadoop.uselonr.gelonn.thriftscala.CombinelondUselonr
import com.twittelonr.uselonrsourcelon.snapshot.combinelond.UselonrsourcelonScalaDataselont
import com.twittelonr.util.Duration
import com.twittelonr.cdelon.scio.dal_relonad.SourcelonUtil
import com.twittelonr.wtf.scalding.clielonnt_elonvelonnt_procelonssing.thriftscala.UselonrIntelonraction
import com.twittelonr.wtf.scalding.jobs.clielonnt_elonvelonnt_procelonssing.UselonrIntelonractionScalaDataselont
import org.joda.timelon.Intelonrval

caselon class IntelonractionGraphClielonntelonvelonntLogsSourcelon(
  pipelonlinelonOptions: IntelonractionGraphClielonntelonvelonntLogsOption
)(
  implicit sc: ScioContelonxt) {

  val dalelonnvironmelonnt: String = pipelonlinelonOptions
    .as(classOf[SelonrvicelonIdelonntifielonrOptions])
    .gelontelonnvironmelonnt()

  delonf relonadUselonrIntelonractions(datelonIntelonrval: Intelonrval): SCollelonction[UselonrIntelonraction] = {

    SourcelonUtil.relonadDALDataselont[UselonrIntelonraction](
      dataselont = UselonrIntelonractionScalaDataselont,
      intelonrval = datelonIntelonrval,
      dalelonnvironmelonnt = dalelonnvironmelonnt)

  }

  delonf relonadCombinelondUselonrs(): SCollelonction[CombinelondUselonr] = {

    SourcelonUtil.relonadMostReloncelonntSnapshotNoOldelonrThanDALDataselont[CombinelondUselonr](
      dataselont = UselonrsourcelonScalaDataselont,
      noOldelonrThan = Duration.fromDays(5),
      dalelonnvironmelonnt = dalelonnvironmelonnt
    )
  }
}
