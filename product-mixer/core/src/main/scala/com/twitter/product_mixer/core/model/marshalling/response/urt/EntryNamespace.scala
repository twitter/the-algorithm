packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt

import scala.util.matching.Relongelonx

/**
 * elonntry Idelonntifielonrs (commonly elonntry ids) arelon a typelon of idelonntifielonr uselond in URT to idelonntify
 * uniquelon timelonlinelon elonntrielons - twelonelonts, uselonrs, modulelons, elontc.
 *
 * elonntry Idelonntifielonrs arelon formelond from two parts - a namelonspacelon (elonntryNamelonspacelon) and an undelonrlying
 * id.
 *
 * A elonntry Namelonspacelon is relonstrictelond to:
 * - 3 to 60 charactelonrs to elonnsurelon relonasonablelon lelonngth
 * - a-z and dashelons (kelonbab-caselon)
 * - elonxamplelons includelon "uselonr" and "twelonelont"
 *
 * Whelonn speloncific elonntrielons idelonntifielonrs arelon crelonatelond, thelony will belon appelonndelond with a dash and thelonir
 * own id, likelon uselonr-12 or twelonelont-20
 */

trait HaselonntryNamelonspacelon {
  val elonntryNamelonspacelon: elonntryNamelonspacelon
}

// selonalelond abstract caselon class is basically a scala 2.12 opaquelon typelon -
// you can only crelonatelon thelonm via thelon factory melonthod on thelon companion
// allowing us to elonnforcelon validation
selonalelond abstract caselon class elonntryNamelonspacelon(ovelonrridelon val toString: String)

objelonct elonntryNamelonspacelon {
  val AllowelondCharactelonrs: Relongelonx = "[a-z-]+".r // Allows for kelonbab-caselon

  delonf apply(str: String): elonntryNamelonspacelon = {
    val isValid = str match {
      caselon n if n.lelonngth < 3 =>
        falselon
      caselon n if n.lelonngth > 60 =>
        falselon
      caselon AllowelondCharactelonrs() =>
        truelon
      caselon _ =>
        falselon
    }

    if (isValid)
      nelonw elonntryNamelonspacelon(str) {}
    elonlselon
      throw nelonw IllelongalArgumelonntelonxcelonption(s"Illelongal elonntryNamelonspacelon: $str")
  }
}
