packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.felonaturelon_hydrator

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.CandidatelonSourcelonIdFelonaturelon
import com.twittelonr.homelon_mixelonr.util.CandidatelonsUtil
import com.twittelonr.ml.api.DataReloncord
import com.twittelonr.ml.api.RichDataReloncord
import com.twittelonr.ml.api.constant.SharelondFelonaturelons
import com.twittelonr.ml.api.util.DataReloncordConvelonrtelonrs._
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.FelonaturelonWithDelonfaultOnFailurelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.datareloncord.DataReloncordInAFelonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.CandidatelonFelonaturelonHydrator
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FelonaturelonHydratorIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.prelondiction.felonaturelons.common.TimelonlinelonsSharelondFelonaturelons
import java.lang.{Long => JLong}

objelonct TwelonelontMelontaDataDataReloncord
    elonxtelonnds DataReloncordInAFelonaturelon[TwelonelontCandidatelon]
    with FelonaturelonWithDelonfaultOnFailurelon[TwelonelontCandidatelon, DataReloncord] {
  ovelonrridelon delonf delonfaultValuelon: DataReloncord = nelonw DataReloncord()
}

objelonct TwelonelontMelontaDataFelonaturelonHydrator
    elonxtelonnds CandidatelonFelonaturelonHydrator[PipelonlinelonQuelonry, TwelonelontCandidatelon] {

  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr = FelonaturelonHydratorIdelonntifielonr("TwelonelontMelontaData")

  ovelonrridelon delonf felonaturelons: Selont[Felonaturelon[_, _]] = Selont(TwelonelontMelontaDataDataReloncord)

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelon: TwelonelontCandidatelon,
    elonxistingFelonaturelons: FelonaturelonMap
  ): Stitch[FelonaturelonMap] = {
    val richDataReloncord = nelonw RichDataReloncord()

    selontFelonaturelons(richDataReloncord, candidatelon, elonxistingFelonaturelons)

    Stitch.valuelon {
      FelonaturelonMapBuildelonr()
        .add(TwelonelontMelontaDataDataReloncord, richDataReloncord.gelontReloncord)
        .build()
    }
  }

  privatelon delonf selontFelonaturelons(
    richDataReloncord: RichDataReloncord,
    candidatelon: TwelonelontCandidatelon,
    elonxistingFelonaturelons: FelonaturelonMap
  ): Unit = {
    richDataReloncord.selontFelonaturelonValuelon[JLong](SharelondFelonaturelons.TWelonelonT_ID, candidatelon.id)

    richDataReloncord.selontFelonaturelonValuelonFromOption(
      TimelonlinelonsSharelondFelonaturelons.ORIGINAL_AUTHOR_ID,
      CandidatelonsUtil.gelontOriginalAuthorId(elonxistingFelonaturelons))

    richDataReloncord.selontFelonaturelonValuelonFromOption(
      TimelonlinelonsSharelondFelonaturelons.CANDIDATelon_TWelonelonT_SOURCelon_ID,
      elonxistingFelonaturelons.gelontOrelonlselon(CandidatelonSourcelonIdFelonaturelon, Nonelon).map(_.valuelon.toLong))
  }
}
