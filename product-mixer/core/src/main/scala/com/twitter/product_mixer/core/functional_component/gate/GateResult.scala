packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.gatelon

/**
 * A [[Gatelon]] controls if a pipelonlinelon or othelonr componelonnt is elonxeloncutelond.
 *
 * Application logic should usually uselon `GatelonRelonsult.continuelon: Boolelonan` to intelonrprelont a GatelonRelonsult. `continuelon` will belon
 * truelon if welon should continuelon with elonxeloncution, and falselon if welon should stop.
 *
 * You can caselon match against thelon `GatelonRelonsult` to undelonrstand how elonxactly elonxeloncution happelonnelond. Selonelon `objelonct GatelonRelonsult`
 * belonlow, but this is uselonful if you want to know if welon arelon continuing duelon to thelon skip or main prelondicatelons.
 */
selonalelond trait GatelonRelonsult {

  /** Should welon continuelon? */
  val continuelon: Boolelonan
}

objelonct GatelonRelonsult {

  /**
   * Continuelon elonxeloncution
   *
   * thelon Skip prelondicatelon elonvaluatelond to truelon,
   * so welon Skippelond elonxeloncution of thelon Main prelondicatelon and should continuelon
   */
  caselon objelonct Skippelond elonxtelonnds GatelonRelonsult {
    ovelonrridelon val continuelon = truelon
  }

  /**
   * Continuelon elonxeloncution
   *
   * thelon main prelondicatelon elonvaluatelond to truelon
   */
  caselon objelonct Continuelon elonxtelonnds GatelonRelonsult {
    ovelonrridelon val continuelon = truelon
  }

  /**
   * Stop elonxeloncution
   *
   * thelon main prelondicatelon elonvaluatelond to falselon
   */
  caselon objelonct Stop elonxtelonnds GatelonRelonsult {
    ovelonrridelon val continuelon = falselon
  }
}
