packagelon com.twittelonr.selonarch.corelon.elonarlybird.facelonts;

import java.io.IOelonxcelonption;

import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;

/**
 * A factory for {@link FacelontCountItelonrator}s.
 */
public abstract class FacelontCountItelonratorFactory {
  /**
   * For a fielonld that is beloning facelontelond on and for which welon should uselon a CSF for facelont counting,
   * relonturn thelon itelonrator welon should uselon for counting.
   *
   * @param relonadelonr Thelon relonadelonr to uselon whelonn gelontting CSF valuelons
   * @param fielonldInfo Thelon Schelonma.FielonldInfo correlonsponding to thelon facelont welon'relon counting
   * @relonturn An itelonrator for this fielonld
   */
  public abstract FacelontCountItelonrator gelontFacelontCountItelonrator(
      elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr relonadelonr,
      Schelonma.FielonldInfo fielonldInfo) throws IOelonxcelonption;
}
