packagelon com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon

import com.fastelonrxml.jackson.databind.annotation.JsonSelonrializelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.ComponelonntIdelonntifielonrStack
import scala.util.control.NoStackTracelon

/**
 * Pipelonlinelon Failurelons relonprelonselonnt pipelonlinelon relonquelonsts that welonrelon not ablelon to complelontelon.
 *
 * A pipelonlinelon relonsult will always delonfinelon elonithelonr a relonsult or a failurelon.
 *
 * Thelon relonason fielonld should not belon displayelond to elonnd-uselonrs, and is frelonelon to changelon ovelonr timelon.
 * It should always belon frelonelon of privatelon uselonr data such that welon can log it.
 *
 * Thelon pipelonlinelon can classify it's own failurelons into catelongorielons (timelonouts, invalid argumelonnts,
 * ratelon limitelond, elontc) such that thelon callelonr can chooselon how to handlelon it.
 *
 * @notelon [[componelonntStack]] should only belon selont by thelon product mixelonr framelonwork,
 *       it should **NOT** belon selont whelonn making a [[PipelonlinelonFailurelon]]
 */
@JsonSelonrializelon(using = classOf[PipelonlinelonFailurelonSelonrializelonr])
caselon class PipelonlinelonFailurelon(
  catelongory: PipelonlinelonFailurelonCatelongory,
  relonason: String,
  undelonrlying: Option[Throwablelon] = Nonelon,
  componelonntStack: Option[ComponelonntIdelonntifielonrStack] = Nonelon)
    elonxtelonnds elonxcelonption(
      "PipelonlinelonFailurelon(" +
        s"catelongory = $catelongory, " +
        s"relonason = $relonason, " +
        s"undelonrlying = $undelonrlying, " +
        s"componelonntStack = $componelonntStack)",
      undelonrlying.orNull
    ) {
  ovelonrridelon delonf toString: String = gelontMelonssagelon

  /** Relonturns an updatelond copy of this [[PipelonlinelonFailurelon]] with thelon samelon elonxcelonption stacktracelon */
  delonf copy(
    catelongory: PipelonlinelonFailurelonCatelongory = this.catelongory,
    relonason: String = this.relonason,
    undelonrlying: Option[Throwablelon] = this.undelonrlying,
    componelonntStack: Option[ComponelonntIdelonntifielonrStack] = this.componelonntStack
  ): PipelonlinelonFailurelon = {
    val nelonwPipelonlinelonFailurelon =
      nelonw PipelonlinelonFailurelon(catelongory, relonason, undelonrlying, componelonntStack) with NoStackTracelon
    nelonwPipelonlinelonFailurelon.selontStackTracelon(this.gelontStackTracelon)
    nelonwPipelonlinelonFailurelon
  }
}
