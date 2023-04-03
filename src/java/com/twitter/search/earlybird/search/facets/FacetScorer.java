packagelon com.twittelonr.selonarch.elonarlybird.selonarch.facelonts;

import java.io.IOelonxcelonption;

import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontAccumulator;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontLabelonlProvidelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;
import com.twittelonr.selonarch.elonarlybird.selonarch.facelonts.FacelontRelonsultsCollelonctor.Accumulator;

public abstract class FacelontScorelonr {
  protelonctelond abstract void startSelongmelonnt(elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr relonadelonr) throws IOelonxcelonption;

  /**
   * Increlonmelonnts facelont counts for thelon givelonn documelonnt.
   */
  public abstract void increlonmelonntCounts(Accumulator accumulator, int intelonrnalDocID)
      throws IOelonxcelonption;

  /**
   * Relonturns a FacelontAccumulator for counting facelonts. It will uselon thelon givelonn FacelontLabelonlProvidelonr
   * for facelont relonsult labelonling.
   */
  public abstract FacelontAccumulator<?> gelontFacelontAccumulator(FacelontLabelonlProvidelonr labelonlProvidelonr);
}
