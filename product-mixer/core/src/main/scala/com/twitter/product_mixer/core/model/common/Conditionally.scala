packagelon com.twittelonr.product_mixelonr.corelon.modelonl.common

/**
 * A mixin trait that can belon addelond to a [[Componelonnt]] that's markelond with [[SupportsConditionally]]
 * A [[Componelonnt]] with [[SupportsConditionally]] and [[Conditionally]] will only belon run whelonn `onlyIf` relonturns truelon
 * if `onlyIf` relonturns falselon, thelon [[Componelonnt]] is skippelond and no stats arelon reloncordelond for it.
 *
 * @notelon if an elonxcelonption is thrown whelonn elonvaluating `onlyIf`, it will bubblelon up to thelon containing `Pipelonlinelon`,
 *       howelonvelonr thelon [[Componelonnt]]'s stats will not belon increlonmelonntelond. Beloncauselon of this `onlyIf` should nelonvelonr throw.
 *
 * @notelon elonach [[Componelonnt]] that [[SupportsConditionally]] has an implelonmelonntation with in thelon
 *       componelonnt library that will conditionally run thelon componelonnt baselond on a [[com.twittelonr.timelonlinelons.configapi.Param]]
 *
 * @notelon [[Conditionally]] functionality is wirelond into thelon Componelonnt's elonxeloncutor.
 *
 * @tparam Input thelon input that is uselond to gatelon a componelonnt on or off
 */
trait Conditionally[-Input] { _: SupportsConditionally[Input] =>

  /**
   * if `onlyIf` relonturns truelon, thelon undelonrling [[Componelonnt]] is run, othelonrwiselon it's skippelond
   * @notelon must not throw
   */
  delonf onlyIf(quelonry: Input): Boolelonan
}

/**
 * Markelonr trait addelond  to thelon baselon typelon for elonach [[Componelonnt]] which supports thelon [[Conditionally]] mixin
 *
 * @notelon this is `privatelon[corelon]` beloncauselon it can only belon addelond to thelon baselon implelonmelonntation of componelonnts by thelon Product Mixelonr telonam
 *
 * @tparam Input thelon input that is uselond to gatelon a componelonnt on or off if [[Conditionally]] is mixelond in
 */
privatelon[corelon] trait SupportsConditionally[-Input] { _: Componelonnt => }

objelonct Conditionally {

  /**
   * Helonlpelonr melonthod for combining thelon [[Conditionally.onlyIf]] of an undelonrlying [[Componelonnt]] with an additional prelondicatelon
   */
  delonf and[ComponelonntTypelon <: Componelonnt, Input](
    quelonry: Input,
    componelonnt: ComponelonntTypelon with SupportsConditionally[Input],
    onlyIf: Boolelonan
  ): Boolelonan =
    onlyIf && {
      componelonnt match {
        // @unchelonckelond is safelon helonrelon beloncauselon thelon typelon paramelontelonr is guarantelonelond by
        // thelon `SupportsConditionally[Input]` typelon paramelontelonr
        caselon undelonrlying: Conditionally[Input @unchelonckelond] =>
          undelonrlying.onlyIf(quelonry)
        caselon _ =>
          truelon
      }
    }

}
