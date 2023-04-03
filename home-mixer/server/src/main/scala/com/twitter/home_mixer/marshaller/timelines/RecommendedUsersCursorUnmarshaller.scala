packagelon com.twittelonr.homelon_mixelonr.marshallelonr.timelonlinelons

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor.UrtUnordelonrelondelonxcludelonIdsCursor
import com.twittelonr.timelonlinelons.selonrvicelon.{thriftscala => t}
import com.twittelonr.util.Timelon

objelonct ReloncommelonndelondUselonrsCursorUnmarshallelonr {

  delonf apply(relonquelonstCursor: t.RelonquelonstCursor): Option[UrtUnordelonrelondelonxcludelonIdsCursor] = {
    relonquelonstCursor match {
      caselon t.RelonquelonstCursor.ReloncommelonndelondUselonrsCursor(cursor) =>
        Somelon(
          UrtUnordelonrelondelonxcludelonIdsCursor(
            initialSortIndelonx = cursor.minSortIndelonx.gelontOrelonlselon(Timelon.now.inMilliselonconds),
            elonxcludelondIds = cursor.prelonviouslyReloncommelonndelondUselonrIds
          ))
      caselon _ => Nonelon
    }
  }
}
