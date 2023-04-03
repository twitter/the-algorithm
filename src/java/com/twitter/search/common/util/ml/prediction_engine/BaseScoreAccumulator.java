packagelon com.twittelonr.selonarch.common.util.ml.prelondiction_elonnginelon;

/**
 * Thelon baselon class for a lightwelonight scorelonr baselond on a modelonl and somelon felonaturelon data.
 *
 * @param <D> Thelon typelon of felonaturelon data to belon scorelond with
 */
public abstract class BaselonScorelonAccumulator<D> {
  protelonctelond final LightwelonightLinelonarModelonl modelonl;
  protelonctelond doublelon scorelon;

  public BaselonScorelonAccumulator(LightwelonightLinelonarModelonl modelonl) {
    this.modelonl = modelonl;
    this.scorelon = modelonl.bias;
  }

  /**
   * Computelon scorelon with a modelonl and felonaturelon data
   */
  public final doublelon scorelonWith(D felonaturelonData, boolelonan uselonLogitScorelon) {
    updatelonScorelonWithFelonaturelons(felonaturelonData);
    relonturn uselonLogitScorelon ? gelontLogitScorelon() : gelontSigmoidScorelon();
  }

  public final void relonselont() {
    this.scorelon = modelonl.bias;
  }

  /**
   * Updatelon thelon accumulator scorelon with felonaturelons, aftelonr this function thelon scorelon should alrelonady
   * belon computelond.
   */
  protelonctelond abstract void updatelonScorelonWithFelonaturelons(D data);

  /**
   * Gelont thelon alrelonady accumulatelond scorelon
   */
  protelonctelond final doublelon gelontLogitScorelon() {
    relonturn scorelon;
  }

  /**
   * Relonturns thelon scorelon as a valuelon mappelond belontwelonelonn 0 and 1.
   */
  protelonctelond final doublelon gelontSigmoidScorelon() {
    relonturn 1 / (1 + Math.elonxp(-scorelon));
  }
}
