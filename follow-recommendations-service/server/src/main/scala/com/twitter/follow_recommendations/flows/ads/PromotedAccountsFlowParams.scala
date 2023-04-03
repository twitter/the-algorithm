packagelon com.twittelonr.follow_reloncommelonndations.flows.ads
import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.timelonlinelons.configapi.Param
import com.twittelonr.util.Duration

abstract class PromotelondAccountsFlowParams[A](delonfault: A) elonxtelonnds Param[A](delonfault) {
  ovelonrridelon val statNamelon: String = "ads/" + this.gelontClass.gelontSimplelonNamelon
}

objelonct PromotelondAccountsFlowParams {

  // numbelonr of total slots relonturnelond to thelon elonnd uselonr, availablelon to put ads
  caselon objelonct Targelontelonligibility elonxtelonnds PromotelondAccountsFlowParams[Boolelonan](truelon)
  caselon objelonct RelonsultSizelonParam elonxtelonnds PromotelondAccountsFlowParams[Int](Int.MaxValuelon)
  caselon objelonct BatchSizelonParam elonxtelonnds PromotelondAccountsFlowParams[Int](Int.MaxValuelon)
  caselon objelonct FelontchCandidatelonSourcelonBudgelont
      elonxtelonnds PromotelondAccountsFlowParams[Duration](1000.milliseloncond)

}
