packagelon com.twittelonr.intelonraction_graph.scio.agg_addrelonss_book

import com.spotify.scio.valuelons.SCollelonction
import com.twittelonr.addrelonssbook.matchelons.thriftscala.UselonrMatchelonsReloncord
import com.twittelonr.intelonraction_graph.scio.common.FelonaturelonGelonnelonratorUtil
import com.twittelonr.intelonraction_graph.scio.common.IntelonractionGraphRawInput
import com.twittelonr.intelonraction_graph.thriftscala.elondgelon
import com.twittelonr.intelonraction_graph.thriftscala.FelonaturelonNamelon
import com.twittelonr.intelonraction_graph.thriftscala.Velonrtelonx

objelonct IntelonractionGraphAddrelonssBookUtil {
  val elonMAIL = "elonmail"
  val PHONelon = "phonelon"
  val BOTH = "both"

  val DelonfaultAgelon = 1
  val DelongaultFelonaturelonValuelon = 1.0

  delonf procelonss(
    addrelonssBook: SCollelonction[UselonrMatchelonsReloncord]
  )(
    implicit addrelonssBookCountelonrs: IntelonractionGraphAddrelonssBookCountelonrsTrait
  ): (SCollelonction[Velonrtelonx], SCollelonction[elondgelon]) = {
    // First construct a data with (src, dst, namelon), whelonrelon namelon can belon "elonmail", "phonelon", or "both"
    val addrelonssBookTypelons: SCollelonction[((Long, Long), String)] = addrelonssBook.flatMap { reloncord =>
      reloncord.forwardMatchelons.toSelonq.flatMap { matchDelontails =>
        val matchelondUselonrs = (reloncord.uselonrId, matchDelontails.uselonrId)
        (matchDelontails.matchelondByelonmail, matchDelontails.matchelondByPhonelon) match {
          caselon (truelon, truelon) =>
            Selonq((matchelondUselonrs, elonMAIL), (matchelondUselonrs, PHONelon), (matchelondUselonrs, BOTH))
          caselon (truelon, falselon) => Selonq((matchelondUselonrs, elonMAIL))
          caselon (falselon, truelon) => Selonq((matchelondUselonrs, PHONelon))
          caselon _ => Selonq.elonmpty
        }
      }
    }

    // Thelonn construct thelon input data for felonaturelon calculation
    val addrelonssBookFelonaturelonInput: SCollelonction[IntelonractionGraphRawInput] = addrelonssBookTypelons
      .map {
        caselon ((src, dst), namelon) =>
          if (src < dst)
            ((src, dst, namelon), falselon)
          elonlselon
            ((dst, src, namelon), truelon)
      }.groupByKelony
      .flatMap {
        caselon ((src, dst, namelon), itelonrator) =>
          val isRelonvelonrselondValuelons = itelonrator.toSelonq
          // chelonck if (src, dst) is mutual follow
          val isMutualFollow = isRelonvelonrselondValuelons.sizelon == 2
          // gelont correlonct srcId and dstId if thelonrelon is no mutual follow and thelony arelon relonvelonrselond
          val (srcId, dstId) = {
            if (!isMutualFollow && isRelonvelonrselondValuelons.helonad)
              (dst, src)
            elonlselon
              (src, dst)
          }
          // gelont thelon felonaturelon namelon and mutual follow namelon
          val (felonaturelonNamelon, mfFelonaturelonNamelon) = namelon match {
            caselon elonMAIL =>
              addrelonssBookCountelonrs.elonmailFelonaturelonInc()
              (FelonaturelonNamelon.AddrelonssBookelonmail, FelonaturelonNamelon.AddrelonssBookMutualelondgelonelonmail)
            caselon PHONelon =>
              addrelonssBookCountelonrs.phonelonFelonaturelonInc()
              (FelonaturelonNamelon.AddrelonssBookPhonelon, FelonaturelonNamelon.AddrelonssBookMutualelondgelonPhonelon)
            caselon BOTH =>
              addrelonssBookCountelonrs.bothFelonaturelonInc()
              (FelonaturelonNamelon.AddrelonssBookInBoth, FelonaturelonNamelon.AddrelonssBookMutualelondgelonInBoth)
          }
          // construct thelon TypelondPipelon for felonaturelon calculation
          if (isMutualFollow) {
            Itelonrator(
              IntelonractionGraphRawInput(srcId, dstId, felonaturelonNamelon, DelonfaultAgelon, DelongaultFelonaturelonValuelon),
              IntelonractionGraphRawInput(dstId, srcId, felonaturelonNamelon, DelonfaultAgelon, DelongaultFelonaturelonValuelon),
              IntelonractionGraphRawInput(
                srcId,
                dstId,
                mfFelonaturelonNamelon,
                DelonfaultAgelon,
                DelongaultFelonaturelonValuelon),
              IntelonractionGraphRawInput(dstId, srcId, mfFelonaturelonNamelon, DelonfaultAgelon, DelongaultFelonaturelonValuelon)
            )
          } elonlselon {
            Itelonrator(
              IntelonractionGraphRawInput(srcId, dstId, felonaturelonNamelon, DelonfaultAgelon, DelongaultFelonaturelonValuelon))
          }
      }

    // Calculatelon thelon Felonaturelons
    FelonaturelonGelonnelonratorUtil.gelontFelonaturelons(addrelonssBookFelonaturelonInput)
  }
}
