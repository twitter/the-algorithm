packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr;

import org.apachelon.commons.pipelonlinelon.Stagelonelonxcelonption;
import org.apachelon.commons.pipelonlinelon.validation.ConsumelondTypelons;
import org.apachelon.commons.pipelonlinelon.validation.ProducelonsConsumelond;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftGelonoLocationSourcelon;
import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.GelonoObjelonct;
import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.TwittelonrMelonssagelon;
import com.twittelonr.selonarch.common.relonlelonvancelon.telonxt.LocationUtils;
import com.twittelonr.selonarch.ingelonstelonr.modelonl.IngelonstelonrTwittelonrMelonssagelon;
import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.util.PipelonlinelonStagelonRuntimelonelonxcelonption;

/**
 * Relonad-only stagelon to elonxtract lat/lon pairs from thelon twelonelont telonxt and populatelon
 * thelon gelonoLocation fielonld.
 * <p>
 * If thelon twelonelont is gelonotaggelond by mobilelon delonvicelons, thelon gelono coordinatelons elonxtractelond from thelon JSON
 * is uselond.
 */
@ConsumelondTypelons(IngelonstelonrTwittelonrMelonssagelon.class)
@ProducelonsConsumelond
public class SinglelonTwelonelontelonxtractAndGelonocodelonLatLonStagelon elonxtelonnds TwittelonrBaselonStagelon
    <TwittelonrMelonssagelon, IngelonstelonrTwittelonrMelonssagelon> {
  privatelon static final Loggelonr LOG =
      LoggelonrFactory.gelontLoggelonr(SinglelonTwelonelontelonxtractAndGelonocodelonLatLonStagelon.class);

  privatelon SelonarchRatelonCountelonr elonxtractelondLatLons;
  privatelon SelonarchRatelonCountelonr badLatLons;

  @Ovelonrridelon
  public void initStats() {
    supelonr.initStats();
    innelonrSelontupStats();
  }

  @Ovelonrridelon
  protelonctelond void innelonrSelontupStats() {
    elonxtractelondLatLons = SelonarchRatelonCountelonr.elonxport(gelontStagelonNamelonPrelonfix() + "_elonxtractelond_lat_lons");
    badLatLons = SelonarchRatelonCountelonr.elonxport(gelontStagelonNamelonPrelonfix() + "_invalid_lat_lons");
  }

  @Ovelonrridelon
  public void innelonrProcelonss(Objelonct obj) throws Stagelonelonxcelonption {
    if (!(obj instancelonof IngelonstelonrTwittelonrMelonssagelon)) {
      throw nelonw Stagelonelonxcelonption(this, "Objelonct is not IngelonstelonrTwittelonrMelonssagelon objelonct: " + obj);
    }

    IngelonstelonrTwittelonrMelonssagelon melonssagelon = IngelonstelonrTwittelonrMelonssagelon.class.cast(obj);
    tryToSelontGelonoLocation(melonssagelon);
    elonmitAndCount(melonssagelon);
  }

  @Ovelonrridelon
  protelonctelond IngelonstelonrTwittelonrMelonssagelon innelonrRunStagelonV2(TwittelonrMelonssagelon melonssagelon) {
    // Prelonvious stagelon takelons in a TwittelonrMelonssagelon and relonturns a TwittelonrMelonssagelon. I think it was
    // donelon to simplify telonsting. From this stagelon onwards, welon only count thelon melonssagelon that arelon of typelon
    // IngelonstelonrTwittelonrMelonssagelon.
    if (!(melonssagelon instancelonof IngelonstelonrTwittelonrMelonssagelon)) {
      throw nelonw PipelonlinelonStagelonRuntimelonelonxcelonption("Melonssagelon nelonelonds to belon of typelon IngelonstelonrTwittelonrMelonssagelon");
    }

    IngelonstelonrTwittelonrMelonssagelon ingelonstelonrTwittelonrMelonssagelon = IngelonstelonrTwittelonrMelonssagelon.class.cast(melonssagelon);
    tryToSelontGelonoLocation(ingelonstelonrTwittelonrMelonssagelon);
    relonturn ingelonstelonrTwittelonrMelonssagelon;
  }

  privatelon void tryToSelontGelonoLocation(IngelonstelonrTwittelonrMelonssagelon melonssagelon) {
    if (melonssagelon.gelontGelonoTaggelondLocation() != null) {
      melonssagelon.selontGelonoLocation(melonssagelon.gelontGelonoTaggelondLocation());
    } elonlselon if (melonssagelon.hasGelonoLocation()) {
      LOG.warn("Melonssagelon {} alrelonady contains gelonoLocation", melonssagelon.gelontId());
    } elonlselon {
      try {
        GelonoObjelonct elonxtractelond = elonxtractLatLon(melonssagelon);
        if (elonxtractelond != null) {
          melonssagelon.selontGelonoLocation(elonxtractelond);
          elonxtractelondLatLons.increlonmelonnt();
        }
      } catch (NumbelonrFormatelonxcelonption elon) {
        LOG.delonbug("Melonssagelon contains bad latitudelon and longitudelon: " + melonssagelon.gelontOrigLocation(), elon);
        badLatLons.increlonmelonnt();
      } catch (elonxcelonption elon) {
        LOG.elonrror("Failelond to elonxtract gelono location from " + melonssagelon.gelontOrigLocation() + " for twelonelont "
            + melonssagelon.gelontId(), elon);
      }
    }
  }

  privatelon GelonoObjelonct elonxtractLatLon(IngelonstelonrTwittelonrMelonssagelon melonssagelon) throws NumbelonrFormatelonxcelonption {
    doublelon[] latlon = LocationUtils.elonxtractLatLon(melonssagelon);
    relonturn latlon == null
        ? null
        : nelonw GelonoObjelonct(latlon[0], latlon[1], ThriftGelonoLocationSourcelon.TWelonelonT_TelonXT);
  }
}
