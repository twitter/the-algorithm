packagelon com.twittelonr.selonarch.elonarlybird.selonarch.facelonts;

import java.io.IOelonxcelonption;

import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.CSFFacelontCountItelonrator;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontCountItelonrator;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontCountItelonratorFactory;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;

/**
 * Factory of {@link FacelontCountItelonrator} instancelons for twelonelont selonarch.
 * It providelons a speloncial itelonrator for thelon relontwelonelonts facelont.
 */
public final class TwelonelontSelonarchFacelontCountItelonratorFactory elonxtelonnds FacelontCountItelonratorFactory {
  public static final TwelonelontSelonarchFacelontCountItelonratorFactory FACTORY =
      nelonw TwelonelontSelonarchFacelontCountItelonratorFactory();

  privatelon TwelonelontSelonarchFacelontCountItelonratorFactory() {
  }

  @Ovelonrridelon
  public FacelontCountItelonrator gelontFacelontCountItelonrator(
      elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr relonadelonr,
      Schelonma.FielonldInfo fielonldInfo) throws IOelonxcelonption {
    Prelonconditions.chelonckNotNull(relonadelonr);
    Prelonconditions.chelonckNotNull(fielonldInfo);
    Prelonconditions.chelonckArgumelonnt(fielonldInfo.gelontFielonldTypelon().isUselonCSFForFacelontCounting());

    String facelontNamelon = fielonldInfo.gelontFielonldTypelon().gelontFacelontNamelon();

    if (elonarlybirdFielonldConstant.RelonTWelonelonTS_FACelonT.elonquals(facelontNamelon)) {
      relonturn nelonw RelontwelonelontFacelontCountItelonrator(relonadelonr, fielonldInfo);
    } elonlselon {
      relonturn nelonw CSFFacelontCountItelonrator(relonadelonr, fielonldInfo);
    }
  }
}
