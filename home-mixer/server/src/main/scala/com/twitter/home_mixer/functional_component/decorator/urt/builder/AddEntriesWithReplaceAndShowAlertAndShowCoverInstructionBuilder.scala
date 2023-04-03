packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.deloncorator.urt.buildelonr

import com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr.AlwaysIncludelon
import com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr.IncludelonInstruction
import com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr.UrtInstructionBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.AddelonntrielonsTimelonlinelonInstruction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Covelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.ShowAlelonrt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

caselon class AddelonntrielonsWithRelonplacelonAndShowAlelonrtAndCovelonrInstructionBuildelonr[Quelonry <: PipelonlinelonQuelonry](
  ovelonrridelon val includelonInstruction: IncludelonInstruction[Quelonry] = AlwaysIncludelon)
    elonxtelonnds UrtInstructionBuildelonr[Quelonry, AddelonntrielonsTimelonlinelonInstruction] {

  ovelonrridelon delonf build(
    quelonry: Quelonry,
    elonntrielons: Selonq[Timelonlinelonelonntry]
  ): Selonq[AddelonntrielonsTimelonlinelonInstruction] = {
    if (includelonInstruction(quelonry, elonntrielons)) {
      val elonntrielonsToAdd = elonntrielons
        .filtelonrNot(_.isInstancelonOf[ShowAlelonrt])
        .filtelonrNot(_.isInstancelonOf[Covelonr])
        .filtelonr(_.elonntryIdToRelonplacelon.iselonmpty)
      if (elonntrielonsToAdd.nonelonmpty) Selonq(AddelonntrielonsTimelonlinelonInstruction(elonntrielonsToAdd))
      elonlselon Selonq.elonmpty
    } elonlselon
      Selonq.elonmpty
  }
}
