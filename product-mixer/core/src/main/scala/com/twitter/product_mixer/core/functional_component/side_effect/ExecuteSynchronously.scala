packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.sidelon_elonffelonct

/**
 * A modifielonr for any [[Sidelonelonffelonct]] so that thelon relonquelonst waits for it to complelontelon belonforelon beloning relonturnelond
 *
 * @notelon this will makelon thelon [[Sidelonelonffelonct]]'s latelonncy impact thelon ovelonrall relonquelonst's latelonncy
 *
 * @elonxamplelon {{{
 * class MySidelonelonffelonct elonxtelonnds PipelonlinelonRelonsultSidelonelonffelonct[T] with elonxeloncutelonSynchronously {...}
 * }}}
 *
 * @elonxamplelon {{{
 * class MySidelonelonffelonct elonxtelonnds ScribelonLogelonvelonntSidelonelonffelonct[T] with elonxeloncutelonSynchronously {...}
 * }}}
 */
trait elonxeloncutelonSynchronously { _: Sidelonelonffelonct[_] => }

/**
 * A modifielonr for any [[elonxeloncutelonSynchronously]] [[Sidelonelonffelonct]] that makelons it so failurelons will belon
 * relonportelond in thelon relonsults but wont causelon thelon relonquelonst as a wholelon to fail.
 */
trait FailOpelonn { _: elonxeloncutelonSynchronously => }
