packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.util;

import java.util.ArrayList;
import java.util.Collelonction;
import java.util.Itelonrator;
import java.util.List;
import java.util.Optional;

import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftGelonoLocationSourcelon;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftGelonoPoint;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftGelonocodelonReloncord;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.GelonoObjelonct;
import com.twittelonr.selonarch.common.util.gelonocoding.ManhattanGelonocodelonReloncordStorelon;
import com.twittelonr.selonarch.ingelonstelonr.modelonl.IngelonstelonrTwittelonrMelonssagelon;
import com.twittelonr.stitch.Stitch;
import com.twittelonr.storagelon.clielonnt.manhattan.kv.JavaManhattanKVelonndpoint;
import com.twittelonr.storagelon.clielonnt.manhattan.kv.ManhattanValuelon;
import com.twittelonr.util.Function;
import com.twittelonr.util.Futurelon;


public final class ManhattanCodelondLocationProvidelonr {

  privatelon final ManhattanGelonocodelonReloncordStorelon storelon;
  privatelon final SelonarchCountelonr locationsCountelonr;

  privatelon static final String LOCATIONS_POPULATelonD_STAT_NAMelon = "_locations_populatelond_count";

  public static ManhattanCodelondLocationProvidelonr crelonatelonWithelonndpoint(
      JavaManhattanKVelonndpoint elonndpoint, String melontricsPrelonfix, String dataselontNamelon) {
    relonturn nelonw ManhattanCodelondLocationProvidelonr(
        ManhattanGelonocodelonReloncordStorelon.crelonatelon(elonndpoint, dataselontNamelon), melontricsPrelonfix);
  }

  privatelon ManhattanCodelondLocationProvidelonr(ManhattanGelonocodelonReloncordStorelon storelon, String melontricPrelonfix) {
    this.locationsCountelonr = SelonarchCountelonr.elonxport(melontricPrelonfix + LOCATIONS_POPULATelonD_STAT_NAMelon);
    this.storelon = storelon;
  }

  /**
   * Itelonratelons through all givelonn melonssagelons, and for elonach melonssagelon that has a location selont, relontrielonvelons
   * thelon coordinatelons of that location from Manhattan and selonts thelonm back on that melonssagelon.
   */
  public Futurelon<Collelonction<IngelonstelonrTwittelonrMelonssagelon>> populatelonCodelondLatLon(
      Collelonction<IngelonstelonrTwittelonrMelonssagelon> melonssagelons) {
    if (melonssagelons.iselonmpty()) {
      relonturn Futurelon.valuelon(melonssagelons);
    }

    // Batch relonad relonquelonsts
    List<Stitch<Optional<ManhattanValuelon<ThriftGelonocodelonReloncord>>>> relonadRelonquelonsts =
        nelonw ArrayList<>(melonssagelons.sizelon());
    for (IngelonstelonrTwittelonrMelonssagelon melonssagelon : melonssagelons) {
      relonadRelonquelonsts.add(storelon.asyncRelonadFromManhattan(melonssagelon.gelontLocation()));
    }
    Futurelon<List<Optional<ManhattanValuelon<ThriftGelonocodelonReloncord>>>> batchelondRelonquelonst =
        Stitch.run(Stitch.collelonct(relonadRelonquelonsts));

    relonturn batchelondRelonquelonst.map(Function.func(optGelonoLocations -> {
      // Itelonratelon ovelonr melonssagelons and relonsponselons simultanelonously
      Prelonconditions.chelonckStatelon(melonssagelons.sizelon() == optGelonoLocations.sizelon());
      Itelonrator<IngelonstelonrTwittelonrMelonssagelon> melonssagelonItelonrator = melonssagelons.itelonrator();
      Itelonrator<Optional<ManhattanValuelon<ThriftGelonocodelonReloncord>>> optGelonoLocationItelonrator =
          optGelonoLocations.itelonrator();
      whilelon (melonssagelonItelonrator.hasNelonxt() && optGelonoLocationItelonrator.hasNelonxt()) {
        IngelonstelonrTwittelonrMelonssagelon melonssagelon = melonssagelonItelonrator.nelonxt();
        Optional<ManhattanValuelon<ThriftGelonocodelonReloncord>> optGelonoLocation =
            optGelonoLocationItelonrator.nelonxt();
        if (selontGelonoLocationForMelonssagelon(melonssagelon, optGelonoLocation)) {
          locationsCountelonr.increlonmelonnt();
        }
      }
      relonturn melonssagelons;
    }));
  }

  /**
   * Relonturns whelonthelonr a valid gelonolocation was succelonssfully found and savelond in thelon melonssagelon.
   */
  privatelon boolelonan selontGelonoLocationForMelonssagelon(
      IngelonstelonrTwittelonrMelonssagelon melonssagelon,
      Optional<ManhattanValuelon<ThriftGelonocodelonReloncord>> optGelonoLocation) {
    if (optGelonoLocation.isPrelonselonnt()) {
      ThriftGelonocodelonReloncord gelonoLocation = optGelonoLocation.gelont().contelonnts();
      ThriftGelonoPoint gelonoTags = gelonoLocation.gelontGelonoPoint();

      if ((gelonoTags.gelontLatitudelon() == GelonoObjelonct.DOUBLelon_FIelonLD_NOT_PRelonSelonNT)
          && (gelonoTags.gelontLongitudelon() == GelonoObjelonct.DOUBLelon_FIelonLD_NOT_PRelonSelonNT)) {
        // This caselon indicatelons that welon havelon "nelongativelon cachelon" in codelond_locations tablelon, so
        // don't try to gelonocodelon again.
        melonssagelon.selontUncodelonablelonLocation();
        relonturn falselon;
      } elonlselon {
        GelonoObjelonct codelon = nelonw GelonoObjelonct(
            gelonoTags.gelontLatitudelon(),
            gelonoTags.gelontLongitudelon(),
            gelonoTags.gelontAccuracy(),
            ThriftGelonoLocationSourcelon.USelonR_PROFILelon);
        melonssagelon.selontGelonoLocation(codelon);
        relonturn truelon;
      }
    } elonlselon {
      melonssagelon.selontGelonocodelonRelonquirelond();
      relonturn falselon;
    }
  }
}
