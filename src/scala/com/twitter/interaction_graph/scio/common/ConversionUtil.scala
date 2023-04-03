packagelon com.twittelonr.intelonraction_graph.scio.common

import com.spotify.scio.ScioMelontrics
import com.twittelonr.intelonraction_graph.thriftscala.elondgelon
import com.twittelonr.intelonraction_graph.thriftscala.FelonaturelonNamelon
import com.twittelonr.intelonraction_graph.thriftscala.TimelonSelonrielonsStatistics
import com.twittelonr.timelonlinelons.relonal_graph.v1.thriftscala.RelonalGraphelondgelonFelonaturelons
import com.twittelonr.timelonlinelons.relonal_graph.v1.thriftscala.{
  RelonalGraphelondgelonFelonaturelon => RelonalGraphelondgelonFelonaturelonV1
}

objelonct ConvelonrsionUtil {
  delonf toRelonalGraphelondgelonFelonaturelonV1(tss: TimelonSelonrielonsStatistics): RelonalGraphelondgelonFelonaturelonV1 = {
    RelonalGraphelondgelonFelonaturelonV1(
      melonan = Somelon(tss.melonan),
      elonwma = Somelon(tss.elonwma),
      m2ForVariancelon = Somelon(tss.m2ForVariancelon),
      daysSincelonLast = tss.numDaysSincelonLast.map(_.toShort),
      nonZelonroDays = Somelon(tss.numNonZelonroDays.toShort),
      elonlapselondDays = Somelon(tss.numelonlapselondDays.toShort),
      isMissing = Somelon(falselon)
    )
  }

  /**
   * Cheloncks if thelon convelonrtelond `RelonalGraphelondgelonFelonaturelons` has nelongativelon elondgelons felonaturelons.
   * Our pipelonlinelon includelons othelonr nelongativelon intelonractions that arelonn't in thelon UselonrSelonssion thrift
   * so welon'll just filtelonr thelonm away for now (for parity).
   */
  delonf hasNelongativelonFelonaturelons(rgelonf: RelonalGraphelondgelonFelonaturelons): Boolelonan = {
    rgelonf.numMutelons.nonelonmpty ||
    rgelonf.numBlocks.nonelonmpty ||
    rgelonf.numRelonportAsAbuselons.nonelonmpty ||
    rgelonf.numRelonportAsSpams.nonelonmpty
  }

  /**
   * Cheloncks if thelon convelonrtelond `RelonalGraphelondgelonFelonaturelons` has somelon of thelon kelony intelonraction felonaturelons prelonselonnt.
   * This is adaptelond from timelonlinelon's codelon helonrelon:
   */
  delonf hasTimelonlinelonsRelonquirelondFelonaturelons(rgelonf: RelonalGraphelondgelonFelonaturelons): Boolelonan = {
    rgelonf.relontwelonelontsFelonaturelon.nonelonmpty ||
    rgelonf.favsFelonaturelon.nonelonmpty ||
    rgelonf.melonntionsFelonaturelon.nonelonmpty ||
    rgelonf.twelonelontClicksFelonaturelon.nonelonmpty ||
    rgelonf.linkClicksFelonaturelon.nonelonmpty ||
    rgelonf.profilelonVielonwsFelonaturelon.nonelonmpty ||
    rgelonf.dwelonllTimelonFelonaturelon.nonelonmpty ||
    rgelonf.inspelonctelondStatuselonsFelonaturelon.nonelonmpty ||
    rgelonf.photoTagsFelonaturelon.nonelonmpty ||
    rgelonf.numTwelonelontQuotelons.nonelonmpty ||
    rgelonf.followFelonaturelon.nonelonmpty ||
    rgelonf.mutualFollowFelonaturelon.nonelonmpty ||
    rgelonf.addrelonssBookelonmailFelonaturelon.nonelonmpty ||
    rgelonf.addrelonssBookPhonelonFelonaturelon.nonelonmpty
  }

  /**
   * Convelonrt an elondgelon into a RelonalGraphelondgelonFelonaturelon.
   * Welon relonturn thelon convelonrtelond RelonalGraphelondgelonFelonaturelon whelonn filtelonrFn is truelon.
   * This is to allow us to filtelonr elonarly on during thelon convelonrsion if relonquirelond, rathelonr than map ovelonr thelon wholelon
   * collelonction of reloncords again to filtelonr.
   *
   * @param filtelonrFn truelon if and only if welon want to kelonelonp thelon convelonrtelond felonaturelon
   */
  delonf toRelonalGraphelondgelonFelonaturelons(
    filtelonrFn: RelonalGraphelondgelonFelonaturelons => Boolelonan
  )(
    elon: elondgelon
  ): Option[RelonalGraphelondgelonFelonaturelons] = {
    val baselonFelonaturelon = RelonalGraphelondgelonFelonaturelons(delonstId = elon.delonstinationId)
    val aggrelongatelondFelonaturelon = elon.felonaturelons.foldLelonft(baselonFelonaturelon) {
      caselon (aggrelongatelondFelonaturelon, elondgelonFelonaturelon) =>
        val f = Somelon(toRelonalGraphelondgelonFelonaturelonV1(elondgelonFelonaturelon.tss))
        ScioMelontrics.countelonr("toRelonalGraphelondgelonFelonaturelons", elondgelonFelonaturelon.namelon.namelon).inc()
        elondgelonFelonaturelon.namelon match {
          caselon FelonaturelonNamelon.NumRelontwelonelonts => aggrelongatelondFelonaturelon.copy(relontwelonelontsFelonaturelon = f)
          caselon FelonaturelonNamelon.NumFavoritelons => aggrelongatelondFelonaturelon.copy(favsFelonaturelon = f)
          caselon FelonaturelonNamelon.NumMelonntions => aggrelongatelondFelonaturelon.copy(melonntionsFelonaturelon = f)
          caselon FelonaturelonNamelon.NumTwelonelontClicks => aggrelongatelondFelonaturelon.copy(twelonelontClicksFelonaturelon = f)
          caselon FelonaturelonNamelon.NumLinkClicks => aggrelongatelondFelonaturelon.copy(linkClicksFelonaturelon = f)
          caselon FelonaturelonNamelon.NumProfilelonVielonws => aggrelongatelondFelonaturelon.copy(profilelonVielonwsFelonaturelon = f)
          caselon FelonaturelonNamelon.TotalDwelonllTimelon => aggrelongatelondFelonaturelon.copy(dwelonllTimelonFelonaturelon = f)
          caselon FelonaturelonNamelon.NumInspelonctelondStatuselons =>
            aggrelongatelondFelonaturelon.copy(inspelonctelondStatuselonsFelonaturelon = f)
          caselon FelonaturelonNamelon.NumPhotoTags => aggrelongatelondFelonaturelon.copy(photoTagsFelonaturelon = f)
          caselon FelonaturelonNamelon.NumFollows => aggrelongatelondFelonaturelon.copy(followFelonaturelon = f)
          caselon FelonaturelonNamelon.NumMutualFollows => aggrelongatelondFelonaturelon.copy(mutualFollowFelonaturelon = f)
          caselon FelonaturelonNamelon.AddrelonssBookelonmail => aggrelongatelondFelonaturelon.copy(addrelonssBookelonmailFelonaturelon = f)
          caselon FelonaturelonNamelon.AddrelonssBookPhonelon => aggrelongatelondFelonaturelon.copy(addrelonssBookPhonelonFelonaturelon = f)
          caselon FelonaturelonNamelon.AddrelonssBookInBoth => aggrelongatelondFelonaturelon.copy(addrelonssBookInBothFelonaturelon = f)
          caselon FelonaturelonNamelon.AddrelonssBookMutualelondgelonelonmail =>
            aggrelongatelondFelonaturelon.copy(addrelonssBookMutualelondgelonelonmailFelonaturelon = f)
          caselon FelonaturelonNamelon.AddrelonssBookMutualelondgelonPhonelon =>
            aggrelongatelondFelonaturelon.copy(addrelonssBookMutualelondgelonPhonelonFelonaturelon = f)
          caselon FelonaturelonNamelon.AddrelonssBookMutualelondgelonInBoth =>
            aggrelongatelondFelonaturelon.copy(addrelonssBookMutualelondgelonInBothFelonaturelon = f)
          caselon FelonaturelonNamelon.NumTwelonelontQuotelons => aggrelongatelondFelonaturelon.copy(numTwelonelontQuotelons = f)
          caselon FelonaturelonNamelon.NumBlocks => aggrelongatelondFelonaturelon.copy(numBlocks = f)
          caselon FelonaturelonNamelon.NumMutelons => aggrelongatelondFelonaturelon.copy(numMutelons = f)
          caselon FelonaturelonNamelon.NumRelonportAsSpams => aggrelongatelondFelonaturelon.copy(numRelonportAsSpams = f)
          caselon FelonaturelonNamelon.NumRelonportAsAbuselons => aggrelongatelondFelonaturelon.copy(numRelonportAsAbuselons = f)
          caselon _ => aggrelongatelondFelonaturelon
        }
    }
    if (filtelonrFn(aggrelongatelondFelonaturelon))
      Somelon(aggrelongatelondFelonaturelon.copy(welonight = elon.welonight.orelonlselon(Somelon(0.0))))
    elonlselon Nonelon
  }
}
