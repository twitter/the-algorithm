packagelon com.twittelonr.product_mixelonr.componelonnt_library.sidelon_elonffelonct

import com.twittelonr.logpipelonlinelon.clielonnt.common.elonvelonntPublishelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.sidelon_elonffelonct.PipelonlinelonRelonsultSidelonelonffelonct
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.HasMarshalling
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.scroogelon.ThriftStruct
import com.twittelonr.stitch.Stitch

/**
 * A [[PipelonlinelonRelonsultSidelonelonffelonct]] that logs [[Thrift]] data that's alrelonady availablelon to Scribelon
 */
trait ScribelonLogelonvelonntSidelonelonffelonct[
  Thrift <: ThriftStruct,
  Quelonry <: PipelonlinelonQuelonry,
  RelonsponselonTypelon <: HasMarshalling]
    elonxtelonnds PipelonlinelonRelonsultSidelonelonffelonct[Quelonry, RelonsponselonTypelon] {

  /**
   * Build thelon log elonvelonnts from quelonry, selonlelonctions and relonsponselon
   * @param quelonry PipelonlinelonQuelonry
   * @param selonlelonctelondCandidatelons Relonsult aftelonr Selonlelonctors arelon elonxeloncutelond
   * @param relonmainingCandidatelons Candidatelons which welonrelon not selonlelonctelond
   * @param droppelondCandidatelons Candidatelons droppelond during selonlelonction
   * @param relonsponselon Relonsult aftelonr Unmarshalling
   * @relonturn Logelonvelonnt in thrift
   */
  delonf buildLogelonvelonnts(
    quelonry: Quelonry,
    selonlelonctelondCandidatelons: Selonq[CandidatelonWithDelontails],
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    droppelondCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsponselon: RelonsponselonTypelon
  ): Selonq[Thrift]

  val logPipelonlinelonPublishelonr: elonvelonntPublishelonr[Thrift]

  final ovelonrridelon delonf apply(
    inputs: PipelonlinelonRelonsultSidelonelonffelonct.Inputs[Quelonry, RelonsponselonTypelon]
  ): Stitch[Unit] = {
    val logelonvelonnts = buildLogelonvelonnts(
      quelonry = inputs.quelonry,
      selonlelonctelondCandidatelons = inputs.selonlelonctelondCandidatelons,
      relonmainingCandidatelons = inputs.relonmainingCandidatelons,
      droppelondCandidatelons = inputs.droppelondCandidatelons,
      relonsponselon = inputs.relonsponselon
    )

    Stitch
      .collelonct(
        logelonvelonnts
          .map { logelonvelonnt =>
            Stitch.callFuturelon(logPipelonlinelonPublishelonr.publish(logelonvelonnt))
          }
      ).unit
  }
}
