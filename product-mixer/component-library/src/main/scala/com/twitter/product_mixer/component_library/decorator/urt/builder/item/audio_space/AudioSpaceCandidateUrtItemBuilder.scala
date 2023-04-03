packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.audio_spacelon

import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.audio_spacelon.AudioSpacelonCandidatelonUrtItelonmBuildelonr.AudioSpacelonClielonntelonvelonntInfoelonlelonmelonnt
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.AudioSpacelonCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.CandidatelonUrtelonntryBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonClielonntelonvelonntInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonFelonelondbackActionInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.audio_spacelon.AudioSpacelonItelonm
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

objelonct AudioSpacelonCandidatelonUrtItelonmBuildelonr {
  val AudioSpacelonClielonntelonvelonntInfoelonlelonmelonnt: String = "audiospacelon"
}

caselon class AudioSpacelonCandidatelonUrtItelonmBuildelonr[-Quelonry <: PipelonlinelonQuelonry](
  clielonntelonvelonntInfoBuildelonr: BaselonClielonntelonvelonntInfoBuildelonr[Quelonry, UnivelonrsalNoun[Any]],
  felonelondbackActionInfoBuildelonr: Option[
    BaselonFelonelondbackActionInfoBuildelonr[Quelonry, UnivelonrsalNoun[Any]]
  ] = Nonelon)
    elonxtelonnds CandidatelonUrtelonntryBuildelonr[Quelonry, AudioSpacelonCandidatelon, AudioSpacelonItelonm] {

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    audioSpacelonCandidatelon: AudioSpacelonCandidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): AudioSpacelonItelonm = AudioSpacelonItelonm(
    id = audioSpacelonCandidatelon.id,
    sortIndelonx = Nonelon, // Sort indelonxelons arelon automatically selont in thelon domain marshallelonr phaselon
    clielonntelonvelonntInfo = clielonntelonvelonntInfoBuildelonr(
      quelonry,
      audioSpacelonCandidatelon,
      candidatelonFelonaturelons,
      Somelon(AudioSpacelonClielonntelonvelonntInfoelonlelonmelonnt)),
    felonelondbackActionInfo =
      felonelondbackActionInfoBuildelonr.flatMap(_.apply(quelonry, audioSpacelonCandidatelon, candidatelonFelonaturelons))
  )
}
