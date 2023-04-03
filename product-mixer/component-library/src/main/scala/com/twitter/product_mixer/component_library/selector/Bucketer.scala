packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor

import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonPipelonlinelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails

/**
 * Givelonn a [[CandidatelonWithDelontails]] relonturn thelon correlonsponding [[Buckelont]]
 * it should belon associatelond with whelonn uselond in a `pattelonrn` or `ratio`
 * in [[InselonrtAppelonndPattelonrnRelonsults]] or [[InselonrtAppelonndRatioRelonsults]]
 */
trait Buckelontelonr[Buckelont] {
  delonf apply(candidatelonWithDelontails: CandidatelonWithDelontails): Buckelont
}

objelonct Buckelontelonr {

  /** A [[Buckelontelonr]] that buckelonts by [[CandidatelonWithDelontails.sourcelon]] */
  val ByCandidatelonSourcelon: Buckelontelonr[CandidatelonPipelonlinelonIdelonntifielonr] =
    (candidatelonWithDelontails: CandidatelonWithDelontails) => candidatelonWithDelontails.sourcelon
}
