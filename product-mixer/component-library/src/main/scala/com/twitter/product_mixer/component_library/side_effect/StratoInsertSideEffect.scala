packagelon com.twittelonr.product_mixelonr.componelonnt_library.sidelon_elonffelonct

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.sidelon_elonffelonct.PipelonlinelonRelonsultSidelonelonffelonct
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.HasMarshalling
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.clielonnt.Inselonrtelonr

/**
 * Sidelon elonffelonct that writelons to Strato column's Inselonrt Op. Crelonatelon an implelonmelonntation of this trait by
 * delonfining thelon `buildelonvelonnts` melonthod and providing a Strato Column inselonrtelonr of typelon
 * (StratoKelonyarg, StratoValuelon) -> Any.
 * Selonelon https://docbird.twittelonr.biz/strato/ColumnCatalog.html#inselonrt for information about
 * thelon Inselonrt opelonration in Strato.
 *
 * @tparam StratoKelonyarg Argumelonnt uselond as a kelony for Strato column. Could belon Unit for common uselon-caselons.
 * @tparam StratoValuelon Valuelon that is inselonrtelond at thelon Strato column.
 * @tparam Quelonry PipelonlinelonQuelonry
 * @tparam DomainRelonsponselonTypelon Timelonlinelon relonsponselon that is marshallelond to domain modelonl (elon.g. URT, Slicelon elontc).
 */
trait StratoInselonrtSidelonelonffelonct[
  StratoKelonyarg,
  StratoValuelon,
  Quelonry <: PipelonlinelonQuelonry,
  DomainRelonsponselonTypelon <: HasMarshalling]
    elonxtelonnds PipelonlinelonRelonsultSidelonelonffelonct[Quelonry, DomainRelonsponselonTypelon] {

  /**
   * Inselonrtelonr for thelon InselonrtOp on a StratoColumn. In Strato, thelon InselonrtOp is relonprelonselonntelond as
   * (Kelonyarg, Valuelon) => Kelony, whelonrelon Kelony relonprelonselonnts thelon relonsult relonturnelond by thelon Inselonrt opelonration.
   * For thelon sidelon-elonffelonct belonhavior, welon do not nelonelond thelon relonturn valuelon and uselon Any instelonad.
   */
  val stratoInselonrtelonr: Inselonrtelonr[StratoKelonyarg, StratoValuelon, Any]

  /**
   * Builds thelon elonvelonnts that arelon inselonrtelond to thelon Strato column. This melonthod supports gelonnelonrating
   * multiplelon elonvelonnts for a singlelon sidelon-elonffelonct invocation.
   *
   * @param quelonry PipelonlinelonQuelonry
   * @param selonlelonctelondCandidatelons Relonsult aftelonr Selonlelonctors arelon elonxeloncutelond
   * @param relonmainingCandidatelons Candidatelons which welonrelon not selonlelonctelond
   * @param droppelondCandidatelons Candidatelons droppelond during selonlelonction
   * @param relonsponselon Timelonlinelon relonsponselon that is marshallelond to domain modelonl (elon.g. URT, Slicelon elontc).
   * @relonturn Tuplelons of (StratoKelonyArg, StratoValuelon) that arelon uselond to call thelon stratoInselonrtelonr.
   */
  delonf buildelonvelonnts(
    quelonry: Quelonry,
    selonlelonctelondCandidatelons: Selonq[CandidatelonWithDelontails],
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    droppelondCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsponselon: DomainRelonsponselonTypelon
  ): Selonq[(StratoKelonyarg, StratoValuelon)]

  final ovelonrridelon delonf apply(
    inputs: PipelonlinelonRelonsultSidelonelonffelonct.Inputs[Quelonry, DomainRelonsponselonTypelon]
  ): Stitch[Unit] = {
    val elonvelonnts = buildelonvelonnts(
      quelonry = inputs.quelonry,
      selonlelonctelondCandidatelons = inputs.selonlelonctelondCandidatelons,
      relonmainingCandidatelons = inputs.relonmainingCandidatelons,
      droppelondCandidatelons = inputs.droppelondCandidatelons,
      relonsponselon = inputs.relonsponselon
    )

    Stitch
      .travelonrselon(elonvelonnts) { caselon (kelonyarg, valuelon) => stratoInselonrtelonr.inselonrt(kelonyarg, valuelon) }
      .unit
  }
}
