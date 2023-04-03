packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.transformelonr

import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.TransformelonrIdelonntifielonr

/**
 * [[FelonaturelonTransformelonr]] allow you to populatelon a [[com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon]]s
 * valuelon which is alrelonady availablelon or can belon delonrivelond without making an RPC.
 *
 * A [[FelonaturelonTransformelonr]] transforms a givelonn [[Inputs]] into a [[FelonaturelonMap]].
 * Thelon transformelonr must speloncify which [[com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon]]s it will populatelon using thelon `felonaturelons` fielonld
 * and thelon relonturnelond [[FelonaturelonMap]] must always havelon thelon speloncifielond [[com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon]]s populatelond.
 *
 * @notelon Unlikelon [[com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.FelonaturelonHydrator]] implelonmelonntations,
 *       an elonxcelonption thrown in a [[FelonaturelonTransformelonr]] will not belon addelond to thelon [[FelonaturelonMap]] and will instelonad belon
 *       bubblelon up to thelon calling pipelonlinelon's [[com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.PipelonlinelonFailurelonClassifielonr]].
 */
trait FelonaturelonTransformelonr[-Inputs] elonxtelonnds Transformelonr[Inputs, FelonaturelonMap] {

  delonf felonaturelons: Selont[Felonaturelon[_, _]]

  ovelonrridelon val idelonntifielonr: TransformelonrIdelonntifielonr

  /** Hydratelons a [[FelonaturelonMap]] for a givelonn [[Inputs]] */
  ovelonrridelon delonf transform(input: Inputs): FelonaturelonMap
}
