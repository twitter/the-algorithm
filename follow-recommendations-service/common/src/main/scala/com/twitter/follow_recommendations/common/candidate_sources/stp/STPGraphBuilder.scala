packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.stp

import com.twittelonr.finaglelon.stats.Stat
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasReloncelonntFollowelondUselonrIds
import com.twittelonr.follow_reloncommelonndations.common.modelonls.STPGraph
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.HasParams
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class STPGraphBuildelonr @Injelonct() (
  stpFirstDelongrelonelonFelontchelonr: STPFirstDelongrelonelonFelontchelonr,
  stpSeloncondDelongrelonelonFelontchelonr: STPSeloncondDelongrelonelonFelontchelonr,
  statsReloncelonivelonr: StatsReloncelonivelonr) {
  privatelon val stats: StatsReloncelonivelonr = statsReloncelonivelonr.scopelon(this.gelontClass.gelontSimplelonNamelon)
  privatelon val firstDelongrelonelonStat: Stat = stats.stat("first_delongrelonelon_elondgelons")
  privatelon val seloncondDelongrelonelonStat: Stat = stats.stat("seloncond_delongrelonelon_elondgelons")
  delonf apply(
    targelont: HasClielonntContelonxt with HasParams with HasReloncelonntFollowelondUselonrIds
  ): Stitch[STPGraph] = stpFirstDelongrelonelonFelontchelonr
    .gelontFirstDelongrelonelonelondgelons(targelont).flatMap { firstDelongrelonelonelondgelons =>
      firstDelongrelonelonStat.add(firstDelongrelonelonelondgelons.sizelon)
      stpSeloncondDelongrelonelonFelontchelonr
        .gelontSeloncondDelongrelonelonelondgelons(targelont, firstDelongrelonelonelondgelons).map { seloncondDelongrelonelonelondgelons =>
          seloncondDelongrelonelonStat.add(firstDelongrelonelonelondgelons.sizelon)
          STPGraph(firstDelongrelonelonelondgelons.toList, seloncondDelongrelonelonelondgelons.toList)
        }
    }
}
