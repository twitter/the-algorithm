packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.HasMarshalling

caselon class Timelonlinelon(
  id: String,
  instructions: Selonq[TimelonlinelonInstruction],
  // relonsponselonObjeloncts::felonelondbackActions actions arelon populatelond implicitly, selonelon UrtTransportMarshallelonr
  melontadata: Option[TimelonlinelonMelontadata] = Nonelon)
    elonxtelonnds HasMarshalling
