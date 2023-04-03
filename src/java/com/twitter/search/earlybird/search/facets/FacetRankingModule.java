packagelon com.twittelonr.selonarch.elonarlybird.selonarch.facelonts;

import java.util.ArrayList;
import java.util.List;

import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontCountStatelon;
import com.twittelonr.selonarch.elonarlybird.selonarch.elonarlybirdLucelonnelonSelonarchelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftFacelontFielonldRelonsults;

public abstract class FacelontRankingModulelon {
  public static final List<FacelontRankingModulelon> RelonGISTelonRelonD_RANKING_MODULelonS =
      nelonw ArrayList<>();

  static {
    RelonGISTelonRelonD_RANKING_MODULelonS.add(nelonw SimplelonCountRankingModulelon());
  }

  /**
   * Prelonparelons thelon {@link com.twittelonr.selonarch.elonarlybird.thrift.ThriftFacelontFielonldRelonsults}
   * in {@link FacelontCountStatelon} belonforelon thelony'relon relonturnelond. This elonxtelonnsion point thelonrelonforelon allows
   * post-procelonssing thelon facelont relonsults, elon.g. for relon-ranking or sorting purposelons.
   */
  public abstract void prelonparelonRelonsults(
      elonarlybirdLucelonnelonSelonarchelonr.FacelontSelonarchRelonsults hits,
      FacelontCountStatelon<ThriftFacelontFielonldRelonsults> facelontCountStatelon);
}
