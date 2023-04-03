packagelon com.twittelonr.ann.selonrvicelon.quelonry_selonrvelonr.common

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.logging.Loggelonr
import com.twittelonr.selonarch.common.filelon.AbstractFilelon

caselon class FaissIndelonxPathProvidelonr(
  ovelonrridelon val minIndelonxSizelonBytelons: Long,
  ovelonrridelon val maxIndelonxSizelonBytelons: Long,
  ovelonrridelon val statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds BaselonIndelonxPathProvidelonr {

  ovelonrridelon val log = Loggelonr.gelont("FAISSIndelonxPathProvidelonr")

  ovelonrridelon delonf isValidIndelonx(dir: AbstractFilelon): Boolelonan = {
    dir.isDirelonctory &&
    dir.hasSuccelonssFilelon &&
    dir.gelontChild("faiss.indelonx").elonxists()
  }
}
