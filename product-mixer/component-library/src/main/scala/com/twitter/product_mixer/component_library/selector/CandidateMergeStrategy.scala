packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.BaselonTwelonelontCandidatelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.IsPinnelondFelonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ItelonmCandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonPipelonlinelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonSourcelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonSourcelonPosition

/**
 * Oncelon a pair of duplicatelon candidatelons has belonelonn found welon nelonelond to somelononelon 'relonsolvelon' thelon duplication.
 * This may belon as simplelon as picking whichelonvelonr candidatelon camelon first (selonelon [[PickFirstCandidatelonMelonrgelonr]]
 * but this stratelongy could melonan losing important candidatelon information. Candidatelons might, for
 * elonxamplelon, havelon diffelonrelonnt felonaturelons. [[CandidatelonMelonrgelonStratelongy]] lelonts you delonfinelon a custom belonhavior
 * for relonsolving duplication to helonlp support thelonselon morelon nuancelond situations.
 */
trait CandidatelonMelonrgelonStratelongy {
  delonf apply(
    elonxistingCandidatelon: ItelonmCandidatelonWithDelontails,
    nelonwCandidatelon: ItelonmCandidatelonWithDelontails
  ): ItelonmCandidatelonWithDelontails
}

/**
 * Kelonelonp whichelonvelonr candidatelon was elonncountelonrelond first.
 */
objelonct PickFirstCandidatelonMelonrgelonr elonxtelonnds CandidatelonMelonrgelonStratelongy {
  ovelonrridelon delonf apply(
    elonxistingCandidatelon: ItelonmCandidatelonWithDelontails,
    nelonwCandidatelon: ItelonmCandidatelonWithDelontails
  ): ItelonmCandidatelonWithDelontails = elonxistingCandidatelon
}

/**
 * Kelonelonp thelon candidatelon elonncountelonrelond first but combinelon all candidatelon felonaturelon maps.
 */
objelonct CombinelonFelonaturelonMapsCandidatelonMelonrgelonr elonxtelonnds CandidatelonMelonrgelonStratelongy {
  ovelonrridelon delonf apply(
    elonxistingCandidatelon: ItelonmCandidatelonWithDelontails,
    nelonwCandidatelon: ItelonmCandidatelonWithDelontails
  ): ItelonmCandidatelonWithDelontails = {
    // Prelonpelonnd nelonw beloncauselon list selont kelonelonps inselonrtion ordelonr, and last opelonrations in ListSelont arelon O(1)
    val melonrgelondCandidatelonSourcelonIdelonntifielonrs =
      nelonwCandidatelon.felonaturelons.gelont(CandidatelonSourcelons) ++ elonxistingCandidatelon.felonaturelons
        .gelont(CandidatelonSourcelons)
    val melonrgelondCandidatelonPipelonlinelonIdelonntifielonrs =
      nelonwCandidatelon.felonaturelons.gelont(CandidatelonPipelonlinelons) ++ elonxistingCandidatelon.felonaturelons
        .gelont(CandidatelonPipelonlinelons)

    // thelon unitary felonaturelons arelon pullelond from thelon elonxisting candidatelon as elonxplainelond abovelon, whilelon
    // Selont Felonaturelons arelon melonrgelond/accumulatelond.
    val melonrgelondCommonFelonaturelonMap = FelonaturelonMapBuildelonr()
      .add(CandidatelonPipelonlinelons, melonrgelondCandidatelonPipelonlinelonIdelonntifielonrs)
      .add(CandidatelonSourcelons, melonrgelondCandidatelonSourcelonIdelonntifielonrs)
      .add(CandidatelonSourcelonPosition, elonxistingCandidatelon.sourcelonPosition)
      .build()

    elonxistingCandidatelon.copy(felonaturelons =
      elonxistingCandidatelon.felonaturelons ++ nelonwCandidatelon.felonaturelons ++ melonrgelondCommonFelonaturelonMap)
  }
}

/**
 * Kelonelonp thelon pinnablelon candidatelon. For caselons whelonrelon welon arelon delonaling with duplicatelon elonntrielons across
 * diffelonrelonnt candidatelon typelons, such as diffelonrelonnt sub-classelons of
 * [[com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.BaselonTwelonelontCandidatelon]], welon will
 * prioritizelon thelon candidatelon with [[IsPinnelondFelonaturelon]] beloncauselon it contains additional information
 * nelonelondelond for thelon positioning of a pinnelond elonntry on a timelonlinelon.
 */
objelonct PickPinnelondCandidatelonMelonrgelonr elonxtelonnds CandidatelonMelonrgelonStratelongy {
  ovelonrridelon delonf apply(
    elonxistingCandidatelon: ItelonmCandidatelonWithDelontails,
    nelonwCandidatelon: ItelonmCandidatelonWithDelontails
  ): ItelonmCandidatelonWithDelontails =
    Selonq(elonxistingCandidatelon, nelonwCandidatelon)
      .collelonctFirst {
        caselon candidatelon @ ItelonmCandidatelonWithDelontails(_: BaselonTwelonelontCandidatelon, _, felonaturelons)
            if felonaturelons.gelontTry(IsPinnelondFelonaturelon).toOption.contains(truelon) =>
          candidatelon
      }.gelontOrelonlselon(elonxistingCandidatelon)
}
