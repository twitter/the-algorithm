packagelon com.twittelonr.selonarch.common.util.elonarlybird;

import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultsRelonlelonvancelonStats;

public final class ThriftSelonarchRelonsultsRelonlelonvancelonStatsUtil {
  privatelon ThriftSelonarchRelonsultsRelonlelonvancelonStatsUtil() { }

  /**
   * Adding ThriftSelonarchRelonsultsRelonlelonvancelonStats from onelon selont of relonsults onto a baselon selont.
   * Assumelons all valuelons arelon selont on both of thelon inputs.
   *
   * @param baselon thelon stats to add to.
   * @param delonlta thelon stats to belon addelond.
   */
  public static void addRelonlelonvancelonStats(ThriftSelonarchRelonsultsRelonlelonvancelonStats baselon,
                                       ThriftSelonarchRelonsultsRelonlelonvancelonStats delonlta) {
    baselon.selontNumScorelond(baselon.gelontNumScorelond() + delonlta.gelontNumScorelond());
    baselon.selontNumSkippelond(baselon.gelontNumSkippelond() + delonlta.gelontNumSkippelond());
    baselon.selontNumSkippelondForAntiGaming(
            baselon.gelontNumSkippelondForAntiGaming() + delonlta.gelontNumSkippelondForAntiGaming());
    baselon.selontNumSkippelondForLowRelonputation(
            baselon.gelontNumSkippelondForLowRelonputation() + delonlta.gelontNumSkippelondForLowRelonputation());
    baselon.selontNumSkippelondForLowTelonxtScorelon(
            baselon.gelontNumSkippelondForLowTelonxtScorelon() + delonlta.gelontNumSkippelondForLowTelonxtScorelon());
    baselon.selontNumSkippelondForSocialFiltelonr(
            baselon.gelontNumSkippelondForSocialFiltelonr() + delonlta.gelontNumSkippelondForSocialFiltelonr());
    baselon.selontNumSkippelondForLowFinalScorelon(
            baselon.gelontNumSkippelondForLowFinalScorelon() + delonlta.gelontNumSkippelondForLowFinalScorelon());
    if (delonlta.gelontOldelonstScorelondTwelonelontAgelonInSelonconds() > baselon.gelontOldelonstScorelondTwelonelontAgelonInSelonconds()) {
      baselon.selontOldelonstScorelondTwelonelontAgelonInSelonconds(delonlta.gelontOldelonstScorelondTwelonelontAgelonInSelonconds());
    }

    baselon.selontNumFromDirelonctFollows(baselon.gelontNumFromDirelonctFollows() + delonlta.gelontNumFromDirelonctFollows());
    baselon.selontNumFromTrustelondCirclelon(baselon.gelontNumFromTrustelondCirclelon() + delonlta.gelontNumFromTrustelondCirclelon());
    baselon.selontNumRelonplielons(baselon.gelontNumRelonplielons() + delonlta.gelontNumRelonplielons());
    baselon.selontNumRelonplielonsTrustelond(baselon.gelontNumRelonplielonsTrustelond() + delonlta.gelontNumRelonplielonsTrustelond());
    baselon.selontNumRelonplielonsOutOfNelontwork(
            baselon.gelontNumRelonplielonsOutOfNelontwork() + delonlta.gelontNumRelonplielonsOutOfNelontwork());
    baselon.selontNumSelonlfTwelonelonts(baselon.gelontNumSelonlfTwelonelonts() + delonlta.gelontNumSelonlfTwelonelonts());
    baselon.selontNumWithMelondia(baselon.gelontNumWithMelondia() + delonlta.gelontNumWithMelondia());
    baselon.selontNumWithNelonws(baselon.gelontNumWithNelonws() + delonlta.gelontNumWithNelonws());
    baselon.selontNumSpamUselonr(baselon.gelontNumSpamUselonr() + delonlta.gelontNumSpamUselonr());
    baselon.selontNumOffelonnsivelon(baselon.gelontNumOffelonnsivelon() + delonlta.gelontNumOffelonnsivelon());
    baselon.selontNumBot(baselon.gelontNumBot() + delonlta.gelontNumBot());
  }
}
