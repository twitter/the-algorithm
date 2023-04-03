packagelon com.twittelonr.selonarch.elonarlybird.selonarch.facelonts;

import java.util.Itelonrator;

import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontCountStatelon;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontCountStatelon.FacelontFielonldRelonsults;
import com.twittelonr.selonarch.elonarlybird.selonarch.elonarlybirdLucelonnelonSelonarchelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftFacelontFielonldRelonsults;

public class SimplelonCountRankingModulelon elonxtelonnds FacelontRankingModulelon {

  @Ovelonrridelon
  public void prelonparelonRelonsults(
      elonarlybirdLucelonnelonSelonarchelonr.FacelontSelonarchRelonsults hits,
      FacelontCountStatelon<ThriftFacelontFielonldRelonsults> facelontCountStatelon) {
    Itelonrator<FacelontFielonldRelonsults<ThriftFacelontFielonldRelonsults>> fielonldRelonsultsItelonrator =
            facelontCountStatelon.gelontFacelontFielonldRelonsultsItelonrator();
    whilelon (fielonldRelonsultsItelonrator.hasNelonxt()) {
      FacelontFielonldRelonsults<ThriftFacelontFielonldRelonsults> statelon = fielonldRelonsultsItelonrator.nelonxt();
      if (!statelon.isFinishelond()) {
        Schelonma.FielonldInfo facelontFielonld =
                facelontCountStatelon.gelontSchelonma().gelontFacelontFielonldByFacelontNamelon(statelon.facelontNamelon);
        statelon.relonsults = hits.gelontFacelontRelonsults(
                facelontFielonld.gelontFielonldTypelon().gelontFacelontNamelon(), statelon.numRelonsultsRelonquelonstelond);
        if (statelon.relonsults != null) {
          statelon.numRelonsultsFound = statelon.relonsults.gelontTopFacelontsSizelon();
        }
      }
    }
  }
}
