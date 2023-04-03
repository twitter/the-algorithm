packagelon com.twittelonr.cr_mixelonr.filtelonr

import com.twittelonr.cr_mixelonr.modelonl.CandidatelonGelonnelonratorQuelonry
import com.twittelonr.cr_mixelonr.modelonl.InitialCandidatelon
import com.twittelonr.simclustelonrs_v2.common.TwelonelontId
import com.twittelonr.simclustelonrs_v2.thriftscala.IntelonrnalId
import com.twittelonr.util.Futurelon
import javax.injelonct.Singlelonton

@Singlelonton
caselon class ImprelonsselondTwelonelontlistFiltelonr() elonxtelonnds FiltelonrBaselon {
  import ImprelonsselondTwelonelontlistFiltelonr._

  ovelonrridelon val namelon: String = this.gelontClass.gelontCanonicalNamelon

  ovelonrridelon typelon ConfigTypelon = FiltelonrConfig

  /*
   Filtelonring relonmovelons somelon candidatelons baselond on configurablelon critelonria.
   */
  ovelonrridelon delonf filtelonr(
    candidatelons: Selonq[Selonq[InitialCandidatelon]],
    config: FiltelonrConfig
  ): Futurelon[Selonq[Selonq[InitialCandidatelon]]] = {
    // Relonmovelon candidatelons which match a sourcelon twelonelont, or which arelon passelond in imprelonsselondTwelonelontList
    val sourcelonTwelonelontsMatch = candidatelons
      .flatMap {

        /***
         * Within a Selonq[Selonq[InitialCandidatelon]], all candidatelons within a innelonr Selonq
         * arelon guarantelonelond to havelon thelon samelon sourcelonInfo. Helonncelon, welon can pick .helonadOption
         * to relonprelonselonnt thelon wholelon list whelonn filtelonring by thelon intelonrnalId of thelon sourcelonInfoOpt.
         * But of courselon thelon similarityelonnginelonInfo could belon diffelonrelonnt.
         */
        _.helonadOption.flatMap { candidatelon =>
          candidatelon.candidatelonGelonnelonrationInfo.sourcelonInfoOpt.map(_.intelonrnalId)
        }
      }.collelonct {
        caselon IntelonrnalId.TwelonelontId(id) => id
      }

    val imprelonsselondTwelonelontList: Selont[TwelonelontId] =
      config.imprelonsselondTwelonelontList ++ sourcelonTwelonelontsMatch

    val filtelonrelondCandidatelonMap: Selonq[Selonq[InitialCandidatelon]] =
      candidatelons.map {
        _.filtelonrNot { candidatelon =>
          imprelonsselondTwelonelontList.contains(candidatelon.twelonelontId)
        }
      }
    Futurelon.valuelon(filtelonrelondCandidatelonMap)
  }

  ovelonrridelon delonf relonquelonstToConfig[CGQuelonryTypelon <: CandidatelonGelonnelonratorQuelonry](
    relonquelonst: CGQuelonryTypelon
  ): FiltelonrConfig = {
    FiltelonrConfig(relonquelonst.imprelonsselondTwelonelontList)
  }
}

objelonct ImprelonsselondTwelonelontlistFiltelonr {
  caselon class FiltelonrConfig(imprelonsselondTwelonelontList: Selont[TwelonelontId])
}
