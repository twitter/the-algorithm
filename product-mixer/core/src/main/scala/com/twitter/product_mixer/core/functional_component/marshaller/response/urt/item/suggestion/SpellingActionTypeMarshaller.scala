packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.suggelonstion

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.suggelonstion._
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Singlelonton

@Singlelonton
class SpelonllingActionTypelonMarshallelonr {

  delonf apply(spelonllingActionTypelon: SpelonllingActionTypelon): urt.SpelonllingActionTypelon =
    spelonllingActionTypelon match {
      caselon RelonplacelonSpelonllingActionTypelon => urt.SpelonllingActionTypelon.Relonplacelon
      caselon elonxpandSpelonllingActionTypelon => urt.SpelonllingActionTypelon.elonxpand
      caselon SuggelonstSpelonllingActionTypelon => urt.SpelonllingActionTypelon.Suggelonst
    }
}
