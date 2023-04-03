packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor

import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ItelonmCandidatelonWithDelontails

/**
 * [[DropSelonlelonctor]] delonteloncts duplicatelons by looking for candidatelons with thelon samelon kelony. A kelony can belon
 * anything but is typically delonrivelond from a candidatelon's id and class. This approach is not always
 * appropriatelon. For elonxamplelon, two candidatelon sourcelons might both relonturn diffelonrelonnt sub-classelons of
 * [[com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.BaselonTwelonelontCandidatelon]] relonsulting in
 * thelonm not beloning trelonatelond as duplicatelons.
 */
trait DelonduplicationKelony[Kelony] {
  delonf apply(candidatelon: ItelonmCandidatelonWithDelontails): Kelony
}

/**
 * Uselon candidatelon id and class to delontelonrminelon duplicatelons.
 */
objelonct IdAndClassDuplicationKelony elonxtelonnds DelonduplicationKelony[(String, Class[_ <: UnivelonrsalNoun[Any]])] {
  delonf apply(itelonm: ItelonmCandidatelonWithDelontails): (String, Class[_ <: UnivelonrsalNoun[Any]]) =
    (itelonm.candidatelon.id.toString, itelonm.candidatelon.gelontClass)
}

/**
 * Uselon candidatelon id to delontelonrminelon duplicatelons.
 * This should belon uselond instelonad of [[IdAndClassDuplicationKelony]] in ordelonr to delonduplicatelon across
 * diffelonrelonnt candidatelon typelons, such as diffelonrelonnt implelonmelonntations of
 * [[com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.BaselonTwelonelontCandidatelon]].
 */
objelonct IdDuplicationKelony elonxtelonnds DelonduplicationKelony[String] {
  delonf apply(itelonm: ItelonmCandidatelonWithDelontails): String = itelonm.candidatelon.id.toString
}
