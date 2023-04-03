packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.MarkelonntrielonsUnrelonadInstruction
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}

@Singlelonton
class MarkelonntrielonsUnrelonadInstructionMarshallelonr @Injelonct() () {

  delonf apply(instruction: MarkelonntrielonsUnrelonadInstruction): urt.MarkelonntrielonsUnrelonad =
    urt.MarkelonntrielonsUnrelonad(elonntryIds = instruction.elonntryIds)
}
