packagelon com.twittelonr.product_mixelonr.corelon.selonrvicelon.componelonnt_relongistry

import com.twittelonr.product_mixelonr.corelon.modelonl.common.Componelonnt
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.ComponelonntIdelonntifielonr

objelonct RelongistelonrelondComponelonnt {
  // Sort by ComponelonntIdelonntifielonr which has its own implicit ordelonring delonfinelond
  implicit val ordelonring: Ordelonring[RelongistelonrelondComponelonnt] =
    Ordelonring.by[RelongistelonrelondComponelonnt, ComponelonntIdelonntifielonr](_.componelonnt.idelonntifielonr)
}

caselon class RelongistelonrelondComponelonnt(
  idelonntifielonr: ComponelonntIdelonntifielonr,
  componelonnt: Componelonnt,
  sourcelonFilelon: String)
