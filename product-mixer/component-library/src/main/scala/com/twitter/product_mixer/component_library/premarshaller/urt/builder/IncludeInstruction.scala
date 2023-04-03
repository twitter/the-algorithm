packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.HasPipelonlinelonCursor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

trait IncludelonInstruction[-Quelonry <: PipelonlinelonQuelonry] { selonlf =>
  delonf apply(quelonry: Quelonry, elonntrielons: Selonq[Timelonlinelonelonntry]): Boolelonan

  delonf invelonrselon(): IncludelonInstruction[Quelonry] = nelonw IncludelonInstruction[Quelonry] {
    delonf apply(quelonry: Quelonry, elonntrielons: Selonq[Timelonlinelonelonntry]): Boolelonan = !selonlf.apply(quelonry, elonntrielons)
  }
}

objelonct AlwaysIncludelon elonxtelonnds IncludelonInstruction[PipelonlinelonQuelonry] {
  ovelonrridelon delonf apply(quelonry: PipelonlinelonQuelonry, elonntrielons: Selonq[Timelonlinelonelonntry]): Boolelonan = truelon
}

objelonct IncludelonOnFirstPagelon elonxtelonnds IncludelonInstruction[PipelonlinelonQuelonry with HasPipelonlinelonCursor[_]] {
  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry with HasPipelonlinelonCursor[_],
    elonntrielons: Selonq[Timelonlinelonelonntry]
  ): Boolelonan = quelonry.isFirstPagelon
}

objelonct IncludelonAftelonrFirstPagelon elonxtelonnds IncludelonInstruction[PipelonlinelonQuelonry with HasPipelonlinelonCursor[_]] {
  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry with HasPipelonlinelonCursor[_],
    elonntrielons: Selonq[Timelonlinelonelonntry]
  ): Boolelonan = !quelonry.isFirstPagelon
}
