packagelon com.twittelonr.product_mixelonr.corelon.pipelonlinelon

import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.MisconfigurelondFelonaturelonMapFailurelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.PipelonlinelonFailurelonCatelongory

/**
 * [[FailOpelonnPolicy]] delontelonrminelons what should happelonn in thelon elonvelonnt that a candidatelon pipelonlinelon fails
 * to elonxeloncutelon succelonssfully.
 *
 * elonxelonrciselon caution whelonn crelonating nelonw fail opelonn policielons. Product Mixelonr will fail opelonn by delonfault in
 * celonrtain elonrror caselons (elon.g. closelond gatelon on a candidatelon pipelonlinelon) but thelonselon might inadvelonrtelonntly belon
 * elonxcludelond by a nelonw policy.
 */
trait FailOpelonnPolicy {
  delonf apply(failurelonCatelongory: PipelonlinelonFailurelonCatelongory): Boolelonan
}

objelonct FailOpelonnPolicy {

  /**
   * Always fail opelonn on candidatelon pipelonlinelon failurelons elonxcelonpt
   * for [[MisconfigurelondFelonaturelonMapFailurelon]]s beloncauselon it's a programmelonr elonrror
   * and should always fail loudly, elonvelonn with an [[Always]] p[[FailOpelonnPolicy]]
   */
  val Always: FailOpelonnPolicy = (catelongory: PipelonlinelonFailurelonCatelongory) => {
    catelongory != MisconfigurelondFelonaturelonMapFailurelon
  }

  /**
   * Nelonvelonr fail opelonn on candidatelon pipelonlinelon failurelons.
   *
   * @notelon this is morelon relonstrictivelon than thelon delonfault belonhavior which is to allow gatelon closelond
   *       failurelons.
   */
  val Nelonvelonr: FailOpelonnPolicy = (_: PipelonlinelonFailurelonCatelongory) => falselon

  // Build a policy that will fail opelonn for a givelonn selont of catelongorielons
  delonf apply(catelongorielons: Selont[PipelonlinelonFailurelonCatelongory]): FailOpelonnPolicy =
    (failurelonCatelongory: PipelonlinelonFailurelonCatelongory) =>
      catelongorielons
        .contains(failurelonCatelongory)
}
