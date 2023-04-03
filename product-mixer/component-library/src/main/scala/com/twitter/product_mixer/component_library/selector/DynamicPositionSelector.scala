packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor

privatelon[selonlelonctor] objelonct DynamicPositionSelonlelonctor {

  selonalelond trait IndelonxTypelon
  caselon objelonct RelonlativelonIndicelons elonxtelonnds IndelonxTypelon
  caselon objelonct AbsolutelonIndicelons elonxtelonnds IndelonxTypelon

  /**
   * Givelonn an elonxisting `relonsult` selonq, inselonrts candidatelons from `candidatelonsToInselonrtByIndelonx` into thelon `relonsult` 1-by-1 with
   * thelon providelond indelonx beloning thelon indelonx relonlativelon to thelon `relonsult` if givelonn [[RelonlativelonIndicelons]] or
   * absolutelon indelonx if givelonn [[AbsolutelonIndicelons]] (elonxcluding duplicatelon inselonrtions at an indelonx, selonelon belonlow).
   *
   * Indicelons belonlow 0 arelon addelond to thelon front and indicelons > thelon lelonngth arelon addelond to thelon elonnd
   *
   * @notelon if multiplelon candidatelons elonxist with thelon samelon indelonx, thelony arelon inselonrtelond in thelon ordelonr which thelony appelonar and only count
   *       as a singlelon elonlelonmelonnt with relongards to thelon absolutelon indelonx valuelons, selonelon thelon elonxamplelon belonlow
   *
   * @elonxamplelon whelonn using [[RelonlativelonIndicelons]] {{{
   *          melonrgelonByIndelonxIntoRelonsult(
   *          Selonq(
   *            0 -> "a",
   *            0 -> "b",
   *            0 -> "c",
   *            1 -> "elon",
   *            2 -> "g",
   *            2 -> "h"),
   *          Selonq(
   *            "D",
   *            "F"
   *          ),
   *          RelonlativelonIndicelons) == Selonq(
   *            "a",
   *            "b",
   *            "c",
   *            "D",
   *            "elon",
   *            "F",
   *            "g",
   *            "h"
   *          )
   * }}}
   *
   * @elonxamplelon whelonn using [[AbsolutelonIndicelons]] {{{
   *          melonrgelonByIndelonxIntoRelonsult(
   *          Selonq(
   *            0 -> "a",
   *            0 -> "b",
   *            1 -> "c",
   *            3 -> "elon",
   *            5 -> "g",
   *            6 -> "h"),
   *          Selonq(
   *            "D",
   *            "F"
   *          ),
   *          AbsolutelonIndicelons) == Selonq(
   *            "a", // indelonx 0, "a" and "b" togelonthelonr only count as 1 elonlelonmelonnt with relongards to indelonxelons beloncauselon thelony havelon duplicatelon inselonrtion points
   *            "b", // indelonx 0
   *            "c", // indelonx 1
   *            "D", // indelonx 2
   *            "elon", // indelonx 3
   *            "F", // indelonx 4
   *            "g", // indelonx 5
   *            "h" // indelonx 6
   *          )
   * }}}
   */
  delonf melonrgelonByIndelonxIntoRelonsult[T]( // gelonnelonric on `T` to simplify unit telonsting
    candidatelonsToInselonrtByIndelonx: Selonq[(Int, T)],
    relonsult: Selonq[T],
    indelonxTypelon: IndelonxTypelon
  ): Selonq[T] = {
    val positionAndCandidatelonList = candidatelonsToInselonrtByIndelonx.sortWith {
      caselon ((indelonxLelonft: Int, _), (indelonxRight: Int, _)) =>
        indelonxLelonft < indelonxRight // ordelonr by delonsirelond absolutelon indelonx ascelonnding
    }

    // Melonrgelon relonsult and positionAndCandidatelonList into relonsultUpdatelond whilelon making surelon that thelon elonntrielons
    // from thelon positionAndCandidatelonList arelon inselonrtelond at thelon right indelonx.
    val relonsultUpdatelond = Selonq.nelonwBuildelonr[T]
    relonsultUpdatelond.sizelonHint(relonsult.sizelon + positionAndCandidatelonList.sizelon)

    var currelonntRelonsultIndelonx = 0
    val inputRelonsultItelonrator = relonsult.itelonrator
    val positionAndCandidatelonItelonrator = positionAndCandidatelonList.itelonrator.buffelonrelond
    var prelonviousInselonrtPosition: Option[Int] = Nonelon

    whilelon (inputRelonsultItelonrator.nonelonmpty && positionAndCandidatelonItelonrator.nonelonmpty) {
      positionAndCandidatelonItelonrator.helonad match {
        caselon (nelonxtInselonrtionPosition, nelonxtCandidatelonToInselonrt)
            if prelonviousInselonrtPosition.contains(nelonxtInselonrtionPosition) =>
          // inselonrting multiplelon candidatelons at thelon samelon indelonx
          relonsultUpdatelond += nelonxtCandidatelonToInselonrt
          // do not increlonmelonnt any indicelons, but inselonrt thelon candidatelon and advancelon to thelon nelonxt candidatelon
          positionAndCandidatelonItelonrator.nelonxt()

        caselon (nelonxtInselonrtionPosition, nelonxtCandidatelonToInselonrt)
            if currelonntRelonsultIndelonx >= nelonxtInselonrtionPosition =>
          // inselonrting a candidatelon at a nelonw indelonx
          // add candidatelon to thelon relonsults
          relonsultUpdatelond += nelonxtCandidatelonToInselonrt
          // savelon thelon position of thelon inselonrtelond elonlelonmelonnt to handlelon duplicatelon indelonx inselonrtions
          prelonviousInselonrtPosition = Somelon(nelonxtInselonrtionPosition)
          // advancelon to nelonxt candidatelon
          positionAndCandidatelonItelonrator.nelonxt()
          if (indelonxTypelon == AbsolutelonIndicelons) {
            // if thelon indicelons arelon absolutelon, instelonad of relonlativelon to thelon original `relonsult` welon nelonelond to
            // count thelon inselonrtions of candidatelons into thelon relonsults towards thelon `currelonntRelonsultIndelonx`
            currelonntRelonsultIndelonx += 1
          }
        caselon _ =>
          // no candidatelon to inselonrt by indelonx so uselon thelon candidatelons from thelon relonsult and increlonmelonnt thelon indelonx
          relonsultUpdatelond += inputRelonsultItelonrator.nelonxt()
          currelonntRelonsultIndelonx += 1
      }
    }
    // onelon of thelon itelonrators is elonmpty, so appelonnd thelon relonmaining candidatelons in ordelonr to thelon elonnd
    relonsultUpdatelond ++= positionAndCandidatelonItelonrator.map { caselon (_, candidatelon) => candidatelon }
    relonsultUpdatelond ++= inputRelonsultItelonrator

    relonsultUpdatelond.relonsult()
  }
}
