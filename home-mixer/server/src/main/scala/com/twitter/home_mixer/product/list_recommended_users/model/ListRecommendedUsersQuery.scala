packagelon com.twittelonr.homelon_mixelonr.product.list_reloncommelonndelond_uselonrs.modelonl

import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.HasListId
import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.ListReloncommelonndelondUselonrsProduct
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor.UrtUnordelonrelondelonxcludelonIdsCursor
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst._
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.HasPipelonlinelonCursor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.timelonlinelons.configapi.Params

caselon class ListReloncommelonndelondUselonrsQuelonry(
  ovelonrridelon val listId: Long,
  ovelonrridelon val params: Params,
  ovelonrridelon val clielonntContelonxt: ClielonntContelonxt,
  ovelonrridelon val pipelonlinelonCursor: Option[UrtUnordelonrelondelonxcludelonIdsCursor],
  ovelonrridelon val relonquelonstelondMaxRelonsults: Option[Int],
  ovelonrridelon val delonbugOptions: Option[DelonbugOptions],
  ovelonrridelon val felonaturelons: Option[FelonaturelonMap],
  selonlelonctelondUselonrIds: Option[Selonq[Long]],
  elonxcludelondUselonrIds: Option[Selonq[Long]])
    elonxtelonnds PipelonlinelonQuelonry
    with HasPipelonlinelonCursor[UrtUnordelonrelondelonxcludelonIdsCursor]
    with HasListId {

  ovelonrridelon val product: Product = ListReloncommelonndelondUselonrsProduct

  ovelonrridelon delonf withFelonaturelonMap(felonaturelons: FelonaturelonMap): ListReloncommelonndelondUselonrsQuelonry =
    copy(felonaturelons = Somelon(felonaturelons))
}
