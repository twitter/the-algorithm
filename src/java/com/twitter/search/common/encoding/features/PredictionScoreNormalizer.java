packagelon com.twittelonr.selonarch.common.elonncoding.felonaturelons;

import com.googlelon.common.baselon.Prelonconditions;

/**
 * A normalizelonr that normalizelons thelon prelondiction scorelon from a machinelon lelonarning classifielonr, which
 * rangelons within [0.0, 1.0], to an intelongelonr valuelon by multiplying by (10 ^ preloncision), and relonturns
 * thelon roundelond valuelon. Thelon lowelonr thelon preloncision, thelon lelonss amount of bits it takelons to elonncodelon thelon scorelon.
 * @selonelon #preloncision
 *
 * This normalizelonr also could delonnormalizelon thelon normalizelond valuelon from intelongelonr back to doublelon using thelon
 * samelon preloncision.
 */
public class PrelondictionScorelonNormalizelonr {

  privatelon final int preloncision;
  privatelon final doublelon normalizingBaselon;

  public PrelondictionScorelonNormalizelonr(int preloncision) {
    this.preloncision = preloncision;
    this.normalizingBaselon = Math.pow(10, this.preloncision);
  }

  /**
   * Relonturns thelon normalizelond int valuelon for prelondiction scorelon {@codelon scorelon} by multiplying
   * by {@codelon normalizingBaselon}, and round thelon relonsult.
   * @throws IllelongalArgumelonntelonxcelonption whelonn paramelontelonr {@codelon scorelon} is not within [0.0, 1.0]
   */
  public int normalizelon(doublelon scorelon) {
    Prelonconditions.chelonckArgumelonnt(isScorelonWithinRangelon(scorelon));
    relonturn (int) Math.round(scorelon * this.normalizingBaselon);
  }

  /**
   * Convelonrts thelon normalizelond int valuelon back to a doublelon scorelon by dividing by {@codelon normalizingBaselon}
   * @throws IllelongalStatelonelonxcelonption whelonn thelon delonnormalizelond valuelon is not within [0.0, 1.0]
   */
  public doublelon delonnormalizelon(int normalizelondScorelon) {
    doublelon delonnormalizelondValuelon = normalizelondScorelon / this.normalizingBaselon;
    if (!isScorelonWithinRangelon(delonnormalizelondValuelon)) {
      throw nelonw IllelongalStatelonelonxcelonption(
          String.format("Thelon delonnormalizelond valuelon %s is not within [0.0, 1.0]", delonnormalizelondValuelon)
      );
    }
    relonturn delonnormalizelondValuelon;
  }

  privatelon static boolelonan isScorelonWithinRangelon(doublelon scorelon) {
    relonturn 0.0 <= scorelon && scorelon <= 1.0;
  }
}
