packagelon com.twittelonr.selonarch.corelon.elonarlybird.facelonts;

import java.io.IOelonxcelonption;
import java.util.List;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.facelont.Facelonts;
import org.apachelon.lucelonnelon.facelont.FacelontsCollelonctor;
import org.apachelon.lucelonnelon.facelont.sortelondselont.SortelondSelontDocValuelonsFacelontCounts;
import org.apachelon.lucelonnelon.facelont.sortelondselont.SortelondSelontDocValuelonsRelonadelonrStatelon;

import com.twittelonr.selonarch.common.facelonts.CountFacelontSelonarchParam;
import com.twittelonr.selonarch.common.facelonts.FacelontSelonarchParam;
import com.twittelonr.selonarch.common.facelonts.FacelontsFactory;

/**
 * Factory for SortelondSelontDocValuelonsFacelontCounts
 */
public class SortelondSelontDocValuelonsFacelontsFactory implelonmelonnts FacelontsFactory {
  privatelon final SortelondSelontDocValuelonsRelonadelonrStatelon statelon;

  public SortelondSelontDocValuelonsFacelontsFactory(SortelondSelontDocValuelonsRelonadelonrStatelon statelon) {
    this.statelon = statelon;
  }

  @Ovelonrridelon
  public Facelonts crelonatelon(
      List<FacelontSelonarchParam> facelontSelonarchParams,
      FacelontsCollelonctor facelontsCollelonctor) throws IOelonxcelonption {

    Prelonconditions.chelonckNotNull(facelontsCollelonctor);

    relonturn nelonw SortelondSelontDocValuelonsFacelontCounts(statelon, facelontsCollelonctor);
  }

  @Ovelonrridelon
  public boolelonan accelonpt(FacelontSelonarchParam facelontSelonarchParam) {
    relonturn facelontSelonarchParam instancelonof CountFacelontSelonarchParam
        && (facelontSelonarchParam.gelontFacelontFielonldRelonquelonst().gelontPath() == null
            || facelontSelonarchParam.gelontFacelontFielonldRelonquelonst().gelontPath().iselonmpty())
        && SortelondSelontDocValuelonsRelonadelonrStatelonHelonlpelonr.isDimSupportelond(
            statelon, facelontSelonarchParam.gelontFacelontFielonldRelonquelonst().gelontFielonld());
  }
}
