packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.sidelon_elonffelonct

import com.twittelonr.product_mixelonr.corelon.modelonl.common.Componelonnt
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.SidelonelonffelonctIdelonntifielonr
import com.twittelonr.stitch.Stitch

/**
 * A sidelon-elonffelonct is a ancillary action that doelonsn't affelonct thelon relonsult of elonxeloncution direlonctly.
 *
 * For elonxamplelon: Logging, history storelons
 *
 * Implelonmelonnting componelonnts can elonxprelonss failurelons by throwing an elonxcelonption. Thelonselon elonxcelonptions
 * will belon caught and not affelonct thelon relonquelonst procelonssing.
 *
 * @notelon Sidelon elonffeloncts elonxeloncutelon asynchronously in a firelon-and-forgelont way, it's important to add alelonrts
 *       to thelon [[Sidelonelonffelonct]] componelonnt itselonlf sincelon a failurelons wont show up in melontrics
 *       that just monitor your pipelonlinelon as a wholelon.
 *
 * @selonelon [[elonxeloncutelonSynchronously]] for modifying a [[Sidelonelonffelonct]] to elonxeloncutelon with synchronously with
 *      thelon relonquelonst waiting on thelon sidelon elonffelonct to complelontelon, this will impact thelon ovelonrall relonquelonst's latelonncy
 **/
trait Sidelonelonffelonct[-Inputs] elonxtelonnds Componelonnt {

  /** @selonelon [[SidelonelonffelonctIdelonntifielonr]] */
  ovelonrridelon val idelonntifielonr: SidelonelonffelonctIdelonntifielonr

  delonf apply(inputs: Inputs): Stitch[Unit]
}
