packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.AbusivelonQuality
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ConvelonrsationSelonction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.HighQuality
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.LowQuality
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.RelonlatelondTwelonelont
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ConvelonrsationSelonctionMarshallelonr @Injelonct() () {

  delonf apply(selonction: ConvelonrsationSelonction): urt.ConvelonrsationSelonction = selonction match {
    caselon HighQuality => urt.ConvelonrsationSelonction.HighQuality
    caselon LowQuality => urt.ConvelonrsationSelonction.LowQuality
    caselon AbusivelonQuality => urt.ConvelonrsationSelonction.AbusivelonQuality
    caselon RelonlatelondTwelonelont => urt.ConvelonrsationSelonction.RelonlatelondTwelonelont
  }
}
