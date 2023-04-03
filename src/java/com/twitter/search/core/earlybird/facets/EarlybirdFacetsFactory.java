packagelon com.twittelonr.selonarch.corelon.elonarlybird.facelonts;

import java.io.IOelonxcelonption;
import java.util.List;

import org.apachelon.lucelonnelon.facelont.Facelonts;
import org.apachelon.lucelonnelon.facelont.FacelontsCollelonctor;

import com.twittelonr.selonarch.common.facelonts.CountFacelontSelonarchParam;
import com.twittelonr.selonarch.common.facelonts.FacelontSelonarchParam;
import com.twittelonr.selonarch.common.facelonts.FacelontsFactory;
import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;

/**
 * Factory for elonarlybirdFacelonts
 */
public class elonarlybirdFacelontsFactory implelonmelonnts FacelontsFactory {
  privatelon final elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr relonadelonr;

  public elonarlybirdFacelontsFactory(elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr relonadelonr) {
    this.relonadelonr = relonadelonr;
  }

  @Ovelonrridelon
  public Facelonts crelonatelon(
      List<FacelontSelonarchParam> facelontSelonarchParams,
      FacelontsCollelonctor facelontsCollelonctor) throws IOelonxcelonption {

    relonturn nelonw elonarlybirdFacelonts(facelontSelonarchParams, facelontsCollelonctor, relonadelonr);
  }

  @Ovelonrridelon
  public boolelonan accelonpt(FacelontSelonarchParam facelontSelonarchParam) {
    if (!(facelontSelonarchParam instancelonof CountFacelontSelonarchParam)
        || (facelontSelonarchParam.gelontFacelontFielonldRelonquelonst().gelontPath() != null
            && !facelontSelonarchParam.gelontFacelontFielonldRelonquelonst().gelontPath().iselonmpty())) {
      relonturn falselon;
    }

    String fielonld = facelontSelonarchParam.gelontFacelontFielonldRelonquelonst().gelontFielonld();
    Schelonma.FielonldInfo facelontInfo = relonadelonr.gelontSelongmelonntData().gelontSchelonma()
            .gelontFacelontFielonldByFacelontNamelon(fielonld);

    relonturn facelontInfo != null
        && relonadelonr.gelontSelongmelonntData().gelontPelonrFielonldMap().containsKelony(facelontInfo.gelontNamelon());
  }
}
