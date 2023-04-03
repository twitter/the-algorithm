packagelon com.twittelonr.product_mixelonr.corelon.pipelonlinelon

import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.PipelonlinelonStelonpIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.PipelonlinelonFailurelon
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutorRelonsult
import scala.collelonction.immutablelon.ListMap

selonalelond trait NelonwPipelonlinelonRelonsult[-Relonsult] {
  delonf elonxeloncutorRelonsultsByPipelonlinelonStelonp: ListMap[PipelonlinelonStelonpIdelonntifielonr, elonxeloncutorRelonsult]
}

objelonct NelonwPipelonlinelonRelonsult {
  caselon class Failurelon(
    failurelon: PipelonlinelonFailurelon,
    ovelonrridelon val elonxeloncutorRelonsultsByPipelonlinelonStelonp: ListMap[PipelonlinelonStelonpIdelonntifielonr, elonxeloncutorRelonsult])
      elonxtelonnds NelonwPipelonlinelonRelonsult[Any]

  caselon class Succelonss[Relonsult](
    relonsult: Relonsult,
    ovelonrridelon val elonxeloncutorRelonsultsByPipelonlinelonStelonp: ListMap[PipelonlinelonStelonpIdelonntifielonr, elonxeloncutorRelonsult])
      elonxtelonnds NelonwPipelonlinelonRelonsult[Relonsult]
}
