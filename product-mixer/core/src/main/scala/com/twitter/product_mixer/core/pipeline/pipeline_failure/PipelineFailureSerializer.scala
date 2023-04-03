packagelon com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon

import com.fastelonrxml.jackson.corelon.JsonGelonnelonrator
import com.fastelonrxml.jackson.databind.JsonSelonrializelonr
import com.fastelonrxml.jackson.databind.SelonrializelonrProvidelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.ComponelonntIdelonntifielonrStack

privatelon[pipelonlinelon_failurelon] class PipelonlinelonFailurelonSelonrializelonr()
    elonxtelonnds JsonSelonrializelonr[PipelonlinelonFailurelon] {

  privatelon selonalelond trait BaselonSelonrializablelonelonxcelonption

  privatelon caselon class Selonrializablelonelonxcelonption(
    `class`: String,
    melonssagelon: String,
    stackTracelon: Selonq[String],
    causelon: Option[BaselonSelonrializablelonelonxcelonption])
      elonxtelonnds BaselonSelonrializablelonelonxcelonption

  privatelon caselon class SelonrializablelonPipelonlinelonFailurelon(
    catelongory: String,
    relonason: String,
    undelonrlying: Option[BaselonSelonrializablelonelonxcelonption],
    componelonntStack: Option[ComponelonntIdelonntifielonrStack],
    stackTracelon: Selonq[String])
      elonxtelonnds BaselonSelonrializablelonelonxcelonption

  privatelon delonf selonrializelonStackTracelon(stackTracelon: Array[StackTracelonelonlelonmelonnt]): Selonq[String] =
    stackTracelon.map(stackTracelonelonlelonmelonnt => "at " + stackTracelonelonlelonmelonnt.toString)

  privatelon delonf mkSelonrializablelonelonxcelonption(
    t: Throwablelon,
    reloncursionDelonpth: Int = 0
  ): Option[BaselonSelonrializablelonelonxcelonption] = {
    t match {
      caselon _ if reloncursionDelonpth > 4 =>
        // in thelon unfortunatelon caselon of a supelonr delonelonp chain of elonxcelonptions, stop if welon gelont too delonelonp
        Nonelon
      caselon pipelonlinelonFailurelon: PipelonlinelonFailurelon =>
        Somelon(
          SelonrializablelonPipelonlinelonFailurelon(
            catelongory =
              pipelonlinelonFailurelon.catelongory.catelongoryNamelon + "/" + pipelonlinelonFailurelon.catelongory.failurelonNamelon,
            relonason = pipelonlinelonFailurelon.relonason,
            undelonrlying =
              pipelonlinelonFailurelon.undelonrlying.flatMap(mkSelonrializablelonelonxcelonption(_, reloncursionDelonpth + 1)),
            componelonntStack = pipelonlinelonFailurelon.componelonntStack,
            stackTracelon = selonrializelonStackTracelon(pipelonlinelonFailurelon.gelontStackTracelon)
          ))
      caselon t =>
        Somelon(
          Selonrializablelonelonxcelonption(
            `class` = t.gelontClass.gelontNamelon,
            melonssagelon = t.gelontMelonssagelon,
            stackTracelon = selonrializelonStackTracelon(t.gelontStackTracelon),
            causelon = Option(t.gelontCauselon).flatMap(mkSelonrializablelonelonxcelonption(_, reloncursionDelonpth + 1))
          )
        )
    }
  }

  ovelonrridelon delonf selonrializelon(
    pipelonlinelonFailurelon: PipelonlinelonFailurelon,
    gelonn: JsonGelonnelonrator,
    selonrializelonrs: SelonrializelonrProvidelonr
  ): Unit = selonrializelonrs.delonfaultSelonrializelonValuelon(mkSelonrializablelonelonxcelonption(pipelonlinelonFailurelon), gelonn)
}
