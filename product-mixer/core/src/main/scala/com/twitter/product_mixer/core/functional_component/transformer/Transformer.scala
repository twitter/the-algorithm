packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.transformelonr

import com.twittelonr.product_mixelonr.corelon.modelonl.common.Componelonnt
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.TransformelonrIdelonntifielonr

/**
 * A transformelonr is a synchronous transformation that takelons thelon providelond [[Input]] and relonturns somelon
 * delonfinelond [[Output]]. For elonxamplelon, elonxtracting a scorelon from from a scorelond candidatelons.
 */
trait Transformelonr[-Inputs, +Output] elonxtelonnds Componelonnt {
  ovelonrridelon val idelonntifielonr: TransformelonrIdelonntifielonr

  /** Takelons [[Inputs]] and transformelonrs thelonm into somelon [[Output]] of your choosing. */
  delonf transform(input: Inputs): Output
}
