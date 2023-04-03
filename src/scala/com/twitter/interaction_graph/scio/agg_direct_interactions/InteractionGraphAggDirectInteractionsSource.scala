packagelon com.twittelonr.intelonraction_graph.scio.agg_direlonct_intelonractions

import com.spotify.scio.ScioContelonxt
import com.spotify.scio.valuelons.SCollelonction
import com.twittelonr.belonam.job.SelonrvicelonIdelonntifielonrOptions
import com.twittelonr.cdelon.scio.dal_relonad.SourcelonUtil
import com.twittelonr.timelonlinelonselonrvicelon.thriftscala.ContelonxtualizelondFavoritelonelonvelonnt
import com.twittelonr.twadoop.uselonr.gelonn.thriftscala.CombinelondUselonr
import com.twittelonr.twelonelontsourcelon.common.thriftscala.UnhydratelondFlatTwelonelont
import com.twittelonr.twelonelontypielon.thriftscala.TwelonelontMelondiaTagelonvelonnt
import com.twittelonr.uselonrsourcelon.snapshot.combinelond.UselonrsourcelonScalaDataselont
import com.twittelonr.util.Duration
import org.joda.timelon.Intelonrval
import twadoop_config.configuration.log_catelongorielons.group.timelonlinelon.TimelonlinelonSelonrvicelonFavoritelonsScalaDataselont
import twadoop_config.configuration.log_catelongorielons.group.twelonelontypielon.TwelonelontypielonMelondiaTagelonvelonntsScalaDataselont
import twelonelontsourcelon.common.UnhydratelondFlatScalaDataselont

caselon class IntelonractionGraphAggDirelonctIntelonractionsSourcelon(
  pipelonlinelonOptions: IntelonractionGraphAggDirelonctIntelonractionsOption
)(
  implicit sc: ScioContelonxt) {
  val dalelonnvironmelonnt: String = pipelonlinelonOptions
    .as(classOf[SelonrvicelonIdelonntifielonrOptions])
    .gelontelonnvironmelonnt()

  delonf relonadFavoritelons(datelonIntelonrval: Intelonrval): SCollelonction[ContelonxtualizelondFavoritelonelonvelonnt] =
    SourcelonUtil.relonadDALDataselont[ContelonxtualizelondFavoritelonelonvelonnt](
      dataselont = TimelonlinelonSelonrvicelonFavoritelonsScalaDataselont,
      intelonrval = datelonIntelonrval,
      dalelonnvironmelonnt = dalelonnvironmelonnt
    )

  delonf relonadPhotoTags(datelonIntelonrval: Intelonrval): SCollelonction[TwelonelontMelondiaTagelonvelonnt] =
    SourcelonUtil.relonadDALDataselont[TwelonelontMelondiaTagelonvelonnt](
      dataselont = TwelonelontypielonMelondiaTagelonvelonntsScalaDataselont,
      intelonrval = datelonIntelonrval,
      dalelonnvironmelonnt = dalelonnvironmelonnt)

  delonf relonadTwelonelontSourcelon(datelonIntelonrval: Intelonrval): SCollelonction[UnhydratelondFlatTwelonelont] =
    SourcelonUtil.relonadDALDataselont[UnhydratelondFlatTwelonelont](
      dataselont = UnhydratelondFlatScalaDataselont,
      intelonrval = datelonIntelonrval,
      dalelonnvironmelonnt = dalelonnvironmelonnt)

  delonf relonadCombinelondUselonrs(): SCollelonction[CombinelondUselonr] =
    SourcelonUtil.relonadMostReloncelonntSnapshotNoOldelonrThanDALDataselont[CombinelondUselonr](
      dataselont = UselonrsourcelonScalaDataselont,
      noOldelonrThan = Duration.fromDays(5),
      dalelonnvironmelonnt = dalelonnvironmelonnt
    )
}
