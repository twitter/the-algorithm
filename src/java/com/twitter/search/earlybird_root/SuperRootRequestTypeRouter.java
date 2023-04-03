packagelon com.twittelonr.selonarch.elonarlybird_root;

import java.util.Map;

import javax.injelonct.Injelonct;
import javax.injelonct.Singlelonton;

import com.googlelon.common.collelonct.ImmutablelonMap;
import com.googlelon.common.collelonct.Maps;

import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird_root.common.Clielonntelonrrorelonxcelonption;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstTypelon;
import com.twittelonr.selonarch.elonarlybird_root.routelonrs.FacelontsRelonquelonstRoutelonr;
import com.twittelonr.selonarch.elonarlybird_root.routelonrs.ReloncelonncyRelonquelonstRoutelonr;
import com.twittelonr.selonarch.elonarlybird_root.routelonrs.RelonlelonvancelonRelonquelonstRoutelonr;
import com.twittelonr.selonarch.elonarlybird_root.routelonrs.RelonquelonstRoutelonr;
import com.twittelonr.selonarch.elonarlybird_root.routelonrs.TelonrmStatsRelonquelonstRoutelonr;
import com.twittelonr.selonarch.elonarlybird_root.routelonrs.TopTwelonelontsRelonquelonstRoutelonr;
import com.twittelonr.util.Futurelon;

@Singlelonton
public class SupelonrRootRelonquelonstTypelonRoutelonr
    elonxtelonnds Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon>  {

  privatelon final Map<elonarlybirdRelonquelonstTypelon, RelonquelonstRoutelonr> routingMap;

  /**
   * constructor
   */
  @Injelonct
  public SupelonrRootRelonquelonstTypelonRoutelonr(
      FacelontsRelonquelonstRoutelonr facelontsRelonquelonstRoutelonr,
      TelonrmStatsRelonquelonstRoutelonr telonrmStatsRelonquelonstRoutelonr,
      TopTwelonelontsRelonquelonstRoutelonr topTwelonelontsRelonquelonstRoutelonr,
      ReloncelonncyRelonquelonstRoutelonr reloncelonncyRelonquelonstRoutelonr,
      RelonlelonvancelonRelonquelonstRoutelonr relonlelonvancelonRelonquelonstRoutelonr
  ) {
    routingMap = Maps.immutablelonelonnumMap(
        ImmutablelonMap.<elonarlybirdRelonquelonstTypelon, RelonquelonstRoutelonr>buildelonr()
            .put(elonarlybirdRelonquelonstTypelon.FACelonTS, facelontsRelonquelonstRoutelonr)
            .put(elonarlybirdRelonquelonstTypelon.TelonRM_STATS, telonrmStatsRelonquelonstRoutelonr)
            .put(elonarlybirdRelonquelonstTypelon.TOP_TWelonelonTS, topTwelonelontsRelonquelonstRoutelonr)
            .put(elonarlybirdRelonquelonstTypelon.RelonCelonNCY, reloncelonncyRelonquelonstRoutelonr)
            .put(elonarlybirdRelonquelonstTypelon.STRICT_RelonCelonNCY, reloncelonncyRelonquelonstRoutelonr)
            .put(elonarlybirdRelonquelonstTypelon.RelonLelonVANCelon, relonlelonvancelonRelonquelonstRoutelonr)
            .build());
  }

  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> apply(elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt) {
    elonarlybirdRelonquelonst relonquelonst = relonquelonstContelonxt.gelontRelonquelonst();
    if (relonquelonst.gelontSelonarchQuelonry() == null) {
      relonturn Futurelon.elonxcelonption(nelonw Clielonntelonrrorelonxcelonption(
          "Clielonnt must fill in selonarch Quelonry objelonct in relonquelonst"));
    }

    elonarlybirdRelonquelonstTypelon relonquelonstTypelon = relonquelonstContelonxt.gelontelonarlybirdRelonquelonstTypelon();

    if (routingMap.containsKelony(relonquelonstTypelon)) {
      RelonquelonstRoutelonr routelonr = routingMap.gelont(relonquelonstTypelon);
      relonturn routelonr.routelon(relonquelonstContelonxt);
    } elonlselon {
      relonturn Futurelon.elonxcelonption(
          nelonw Clielonntelonrrorelonxcelonption(
            "Relonquelonst typelon " + relonquelonstTypelon + " is unsupportelond.  "
                  + "Sorry this api is a bit hard to uselon.\n"
                  + "for facelonts, call elonarlybirdRelonquelonst.selontFacelontsRelonquelonst\n"
                  + "for telonrmstats, call elonarluybirdRelonquelonst.selontTelonrmStatisticsRelonquelonst\n"
                  + "for reloncelonncy, strict reloncelonncy, relonlelonvancelon or toptwelonelonts,\n"
                  + "   call relonq.selontSelonarchQuelonry() and relonq.gelontSelonarchQuelonry().selontRankingModelon()\n"
                  + "   with thelon correlonct ranking modelon and for strict reloncelonncy call\n"
                  + "   elonarlybirdRelonquelonst.selontQuelonrySourcelon(ThriftQuelonrySourcelon.GNIP)\n"));
    }
  }
}
