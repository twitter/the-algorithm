package com.tw ter.t etyp e.serverut l

/**
 * Parse a dev ce s ce  nto an OAuth app  d. T  mapp ng  s
 * neccesary w n   need to request  nformat on about a cl ent from
 * a serv ce that only knows about cl ents  n terms of oauth ds.
 *
 * T  happens e  r by pars ng out an expl c  "oauth:" app  d or
 * us ng a mapp ng from old non oauth cl ent ds l ke " b" and "sms"
 * to oauth ds that have retroact vely been ass gned to those cl ents.
 *  f t  legacy  d cannot be found  n t  map and  's a non-nu r c
 * str ng,  's converted to t  oauth d for tw ter.com.
 *
 * T ets w h non oauth cl ent ds are st ll be ng created because
 * thats how t  monora l creates t m.   also need to be able to
 * process any app  d str ng that  s  n old t et data.
 *
 */
object Dev ceS ceParser {

  /**
   * T  oauth  d for tw ter.com. Also used as a default oauth  d for
   * ot r cl ents w hout t  r own
   */
  val  b = 268278L

  /**
   * T  OAuth app  ds for known legacy dev ce s ces.
   */
  val legacyMapp ng: Map[Str ng, Long] = Map[Str ng, Long](
    " b" ->  b,
    "t etbutton" -> 6219130L,
    "ke a _ b" -> 38366L,
    "sms" -> 241256L
  )

  /**
   * Attempt to convert a cl ent appl cat on  d Str ng  nto an OAuth
   *  d.
   *
   * T  str ng must cons st of t  characters "oauth:" follo d by a
   * non-negat ve, dec mal long. T  text  s case- nsens  ve, and
   * wh espace at t  beg nn ng or end  s  gnored.
   *
   *   want to accept  nput as l berally as poss ble, because  f  
   * fa l to do that  re,   w ll get counted as a "legacy app  d"
   */
  val parseOAuthApp d: Str ng => Opt on[Long] = {
    // Case- nsens  ve, wh espace  nsens  ve. T  javaWh espace
    // character class  s cons stent w h Character. sWh espace, but  s
    // sadly d fferent from \s.   w ll l kely not matter  n t  long
    // run, but t  accepts more  nputs and  s eas er to test (because
    //   can use  sWh espace)
    val OAuthApp dRe = """(? )\p{javaWh espace}*oauth:(\d+)\p{javaWh espace}*""".r

    _ match {
      case OAuthApp dRe(d g s) =>
        //   should only get NumberFormatExcept on w n t  number  s
        // larger than a Long, because t  regex w ll rule out all of
        // t  ot r  nval d cases.
        try So (d g s.toLong)
        catch { case _: NumberFormatExcept on => None }
      case _ =>
        None
    }
  }

  /**
   * Attempt to convert a cl ent appl cat on  d Str ng  nto an OAuth  d or legacy  dent f er w hout
   * any fallback behav or.
   */
  val parseStr ct: Str ng => Opt on[Long] =
    app dStr =>
      parseOAuthApp d(app dStr)
        .orElse(legacyMapp ng.get(app dStr))

  /**
   * Return true  f a str ng can be used as a val d cl ent appl cat on  d or legacy  dent f er
   */
  val  sVal d: Str ng => Boolean = app dStr => parseStr ct(app dStr). sDef ned

  /**
   * Bu ld a parser that converts dev ce s ces to OAuth app  ds,
   *  nclud ng perform ng t  legacy mapp ng.
   */
  val parseApp d: Str ng => Opt on[Long] = {
    val  sNu r cRe = """-?[0-9]+""".r

    app dStr =>
      parseStr ct(app dStr)
        .orElse {
          app dStr match {
            //   just fa l t  lookup  f t  app  d looks l ke  's
            // nu r c.
            case  sNu r cRe() => None
            case _ => So ( b)
          }
        }
  }
}
