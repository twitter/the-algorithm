packagelon com.twittelonr.product_mixelonr.corelon.pipelonlinelon.statelon

import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.PipelonlinelonStelonpIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutorRelonsult
import scala.collelonction.immutablelon.ListMap

trait HaselonxeloncutorRelonsults[Statelon] {
  // Welon uselon a list map to maintain thelon inselonrtion ordelonr
  val elonxeloncutorRelonsultsByPipelonlinelonStelonp: ListMap[PipelonlinelonStelonpIdelonntifielonr, elonxeloncutorRelonsult]
  privatelon[pipelonlinelon] delonf selontelonxeloncutorRelonsults(
    nelonwMap: ListMap[PipelonlinelonStelonpIdelonntifielonr, elonxeloncutorRelonsult]
  ): Statelon
}
