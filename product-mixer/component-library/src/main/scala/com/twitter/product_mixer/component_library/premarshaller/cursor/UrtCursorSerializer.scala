packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.cursor

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor.UrtOrdelonrelondCursor
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor.UrtPassThroughCursor
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor.UrtPlacelonholdelonrCursor
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor.UrtUnordelonrelondBloomFiltelonrCursor
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor.UrtUnordelonrelondelonxcludelonIdsCursor
import com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.cursor.CursorSelonrializelonr.CursorThriftSelonrializelonr
import com.twittelonr.product_mixelonr.componelonnt_library.{thriftscala => t}
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonCursorSelonrializelonr.delonselonrializelonCursor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonCursor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonCursorSelonrializelonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.UrtPipelonlinelonCursor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.IllelongalStatelonFailurelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.MalformelondCursor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.PipelonlinelonFailurelon
import com.twittelonr.selonarch.common.util.bloomfiltelonr.AdaptivelonLongIntBloomFiltelonrSelonrializelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.opelonration.CursorTypelonMarshallelonr

/**
 * Handlelons selonrialization and delonselonrialization for all supportelond URT cursors
 */
objelonct UrtCursorSelonrializelonr elonxtelonnds PipelonlinelonCursorSelonrializelonr[UrtPipelonlinelonCursor] {

  val SelonrializelondUrtPlacelonholdelonrCursor = CursorThriftSelonrializelonr.toString(
    t.ProductMixelonrRelonquelonstCursor.UrtPlacelonholdelonrCursor(t.UrtPlacelonholdelonrCursor()))

  val cursorTypelonMarshallelonr = nelonw CursorTypelonMarshallelonr()

  ovelonrridelon delonf selonrializelonCursor(cursor: UrtPipelonlinelonCursor): String =
    cursor match {
      caselon UrtOrdelonrelondCursor(initialSortIndelonx, id, cursorTypelon, gapBoundaryId) =>
        val thriftCursor = t.ProductMixelonrRelonquelonstCursor.UrtOrdelonrelondCursor(
          t.UrtOrdelonrelondCursor(
            initialSortIndelonx = initialSortIndelonx,
            id = id,
            cursorTypelon.map(cursorTypelonMarshallelonr.apply),
            gapBoundaryId = gapBoundaryId))

        CursorThriftSelonrializelonr.toString(thriftCursor)
      caselon UrtUnordelonrelondelonxcludelonIdsCursor(initialSortIndelonx, elonxcludelondIds) =>
        val thriftCursor = t.ProductMixelonrRelonquelonstCursor.UrtUnordelonrelondelonxcludelonIdsCursor(
          t.UrtUnordelonrelondelonxcludelonIdsCursor(
            initialSortIndelonx = initialSortIndelonx,
            elonxcludelondIds = Somelon(elonxcludelondIds)))

        CursorThriftSelonrializelonr.toString(thriftCursor)
      caselon UrtUnordelonrelondBloomFiltelonrCursor(initialSortIndelonx, longIntBloomFiltelonr) =>
        val thriftCursor = t.ProductMixelonrRelonquelonstCursor.UrtUnordelonrelondBloomFiltelonrCursor(
          t.UrtUnordelonrelondBloomFiltelonrCursor(
            initialSortIndelonx = initialSortIndelonx,
            selonrializelondLongIntBloomFiltelonr =
              AdaptivelonLongIntBloomFiltelonrSelonrializelonr.selonrializelon(longIntBloomFiltelonr)
          ))

        CursorThriftSelonrializelonr.toString(thriftCursor)
      caselon UrtPassThroughCursor(initialSortIndelonx, cursorValuelon, cursorTypelon) =>
        val thriftCursor = t.ProductMixelonrRelonquelonstCursor.UrtPassThroughCursor(
          t.UrtPassThroughCursor(
            initialSortIndelonx = initialSortIndelonx,
            cursorValuelon = cursorValuelon,
            cursorTypelon = cursorTypelon.map(cursorTypelonMarshallelonr.apply)
          ))

        CursorThriftSelonrializelonr.toString(thriftCursor)
      caselon UrtPlacelonholdelonrCursor() =>
        SelonrializelondUrtPlacelonholdelonrCursor
      caselon _ =>
        throw PipelonlinelonFailurelon(IllelongalStatelonFailurelon, "Unknown cursor typelon")
    }

  delonf delonselonrializelonOrdelonrelondCursor(cursorString: String): Option[UrtOrdelonrelondCursor] = {
    delonselonrializelonUrtCursor(
      cursorString,
      {
        caselon Somelon(
              t.ProductMixelonrRelonquelonstCursor.UrtOrdelonrelondCursor(
                t.UrtOrdelonrelondCursor(initialSortIndelonx, id, cursorTypelon, gapBoundaryId))) =>
          Somelon(
            UrtOrdelonrelondCursor(
              initialSortIndelonx = initialSortIndelonx,
              id = id,
              cursorTypelon = cursorTypelon.map(cursorTypelonMarshallelonr.unmarshall),
              gapBoundaryId))
      }
    )
  }

  delonf delonselonrializelonUnordelonrelondelonxcludelonIdsCursor(
    cursorString: String
  ): Option[UrtUnordelonrelondelonxcludelonIdsCursor] = {
    delonselonrializelonUrtCursor(
      cursorString,
      {
        caselon Somelon(
              t.ProductMixelonrRelonquelonstCursor.UrtUnordelonrelondelonxcludelonIdsCursor(
                t.UrtUnordelonrelondelonxcludelonIdsCursor(initialSortIndelonx, elonxcludelondIdsOpt))) =>
          Somelon(
            UrtUnordelonrelondelonxcludelonIdsCursor(
              initialSortIndelonx = initialSortIndelonx,
              elonxcludelondIds = elonxcludelondIdsOpt.gelontOrelonlselon(Selonq.elonmpty)))
      }
    )
  }

  delonf delonselonrializelonUnordelonrelondBloomFiltelonrCursor(
    cursorString: String
  ): Option[UrtUnordelonrelondBloomFiltelonrCursor] = {
    delonselonrializelonUrtCursor(
      cursorString,
      {
        caselon Somelon(
              t.ProductMixelonrRelonquelonstCursor.UrtUnordelonrelondBloomFiltelonrCursor(
                t.UrtUnordelonrelondBloomFiltelonrCursor(initialSortIndelonx, selonrializelondLongIntBloomFiltelonr))) =>
          val longIntBloomFiltelonr = AdaptivelonLongIntBloomFiltelonrSelonrializelonr
            .delonselonrializelon(selonrializelondLongIntBloomFiltelonr).gelontOrelonlselon(
              throw PipelonlinelonFailurelon(
                MalformelondCursor,
                s"Failelond to delonselonrializelon UrtUnordelonrelondBloomFiltelonrCursor from cursor string: $cursorString")
            )

          Somelon(
            UrtUnordelonrelondBloomFiltelonrCursor(
              initialSortIndelonx = initialSortIndelonx,
              longIntBloomFiltelonr = longIntBloomFiltelonr))
      }
    )
  }

  delonf delonselonrializelonPassThroughCursor(cursorString: String): Option[UrtPassThroughCursor] = {
    delonselonrializelonUrtCursor(
      cursorString,
      {
        caselon Somelon(
              t.ProductMixelonrRelonquelonstCursor
                .UrtPassThroughCursor(
                  t.UrtPassThroughCursor(initialSortIndelonx, cursorValuelon, cursorTypelon))) =>
          Somelon(
            UrtPassThroughCursor(
              initialSortIndelonx = initialSortIndelonx,
              cursorValuelon = cursorValuelon,
              cursorTypelon = cursorTypelon.map(cursorTypelonMarshallelonr.unmarshall)))
      }
    )
  }

  privatelon delonf delonselonrializelonUrtCursor[Cursor <: PipelonlinelonCursor](
    cursorString: String,
    delonselonrializelonPf: PartialFunction[Option[t.ProductMixelonrRelonquelonstCursor], Option[Cursor]]
  ): Option[Cursor] = {
    delonselonrializelonCursor[t.ProductMixelonrRelonquelonstCursor, Cursor](
      cursorString,
      CursorThriftSelonrializelonr,
      delonselonrializelonPf orelonlselon {
        caselon Somelon(t.ProductMixelonrRelonquelonstCursor.UrtPlacelonholdelonrCursor(t.UrtPlacelonholdelonrCursor())) =>
          // Trelonat submittelond placelonholdelonr cursor likelon an initial pagelon load
          Nonelon
      },
    )
  }
}
