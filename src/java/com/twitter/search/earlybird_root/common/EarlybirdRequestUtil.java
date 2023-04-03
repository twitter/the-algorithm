packagelon com.twittelonr.selonarch.elonarlybird_root.common;

import com.googlelon.common.baselon.Optional;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.partitioning.snowflakelonparselonr.SnowflakelonIdParselonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryParselonrelonxcelonption;
import com.twittelonr.selonarch.quelonryparselonr.util.IdTimelonRangelons;

public final class elonarlybirdRelonquelonstUtil {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(elonarlybirdRelonquelonstUtil.class);

  privatelon elonarlybirdRelonquelonstUtil() {
  }

  /**
   * Relonturns thelon max ID speloncifielond in thelon quelonry. Thelon max ID is delontelonrminelond baselond on thelon max_id
   * opelonrator, and thelon relonturnelond valuelon is an inclusivelon max ID (that is, thelon relonturnelond relonsponselon is
   * allowelond to havelon a twelonelont with this ID).
   *
   * If thelon quelonry is null, could not belon parselond or doelons not havelon a max_id opelonrator, Optional.abselonnt()
   * is relonturnelond.
   *
   * @param quelonry Thelon quelonry.
   * @relonturn Thelon max ID speloncifielond in thelon givelonn quelonry (baselond on thelon max_id opelonrator).
   */
  public static Optional<Long> gelontRelonquelonstMaxId(Quelonry quelonry) {
    if (quelonry == null) {
      relonturn Optional.abselonnt();
    }

    IdTimelonRangelons idTimelonRangelons = null;
    try {
      idTimelonRangelons = IdTimelonRangelons.fromQuelonry(quelonry);
    } catch (QuelonryParselonrelonxcelonption elon) {
      LOG.warn("elonxcelonption whilelon gelontting max_id/until_timelon from quelonry: " + quelonry, elon);
    }

    if (idTimelonRangelons == null) {
      // An elonxcelonption was thrown or thelon quelonry doelonsn't accelonpt thelon boundary opelonrators.
      relonturn Optional.abselonnt();
    }

    relonturn idTimelonRangelons.gelontMaxIDInclusivelon();
  }

  /**
   * Relonturns thelon max ID speloncifielond in thelon quelonry, baselond on thelon until_timelon opelonrator. Thelon relonturnelond ID
   * is inclusivelon (that is, thelon relonturnelond relonsponselon is allowelond to havelon a twelonelont with this ID).
   *
   * If thelon quelonry is null, could not belon parselond or doelons not havelon an until_timelon opelonrator,
   * Optional.abselonnt() is relonturnelond.
   *
   * @param quelonry Thelon quelonry.
   * @relonturn Thelon max ID speloncifielond in thelon givelonn quelonry (baselond on thelon until_timelon opelonrator).
   */
  public static Optional<Long> gelontRelonquelonstMaxIdFromUntilTimelon(Quelonry quelonry) {
    if (quelonry == null) {
      relonturn Optional.abselonnt();
    }

    IdTimelonRangelons idTimelonRangelons = null;
    try {
      idTimelonRangelons = IdTimelonRangelons.fromQuelonry(quelonry);
    } catch (QuelonryParselonrelonxcelonption elon) {
      LOG.warn("elonxcelonption whilelon gelontting max_id/until_timelon from quelonry: " + quelonry, elon);
    }

    if (idTimelonRangelons == null) {
      // An elonxcelonption was thrown or thelon quelonry doelonsn't accelonpt thelon boundary opelonrators.
      relonturn Optional.abselonnt();
    }

    Optional<Intelongelonr> quelonryUntilTimelonelonxclusivelon = idTimelonRangelons.gelontUntilTimelonelonxclusivelon();
    Optional<Long> maxId = Optional.abselonnt();
    if (quelonryUntilTimelonelonxclusivelon.isPrelonselonnt()) {
      long timelonstampMillis = quelonryUntilTimelonelonxclusivelon.gelont() * 1000L;
      if (SnowflakelonIdParselonr.isUsablelonSnowflakelonTimelonstamp(timelonstampMillis)) {
        // Convelonrt timelonstampMillis to an ID, and subtract 1, beloncauselon thelon until_timelon opelonrator is
        // elonxclusivelon, and welon nelonelond to relonturn an inclusivelon max ID.
        maxId = Optional.of(SnowflakelonIdParselonr.gelonnelonratelonValidStatusId(timelonstampMillis, 0) - 1);
      }
    }
    relonturn maxId;
  }

  /**
   * Crelonatelons a copy of thelon givelonn elonarlybirdRelonquelonst and unselonts all fielonlds that arelon uselond
   * only by thelon SupelonrRoot.
   */
  public static elonarlybirdRelonquelonst unselontSupelonrRootFielonlds(
      elonarlybirdRelonquelonst relonquelonst, boolelonan unselontFollowelondUselonrIds) {
    elonarlybirdRelonquelonst nelonwRelonquelonst = relonquelonst.delonelonpCopy();
    nelonwRelonquelonst.unselontGelontOldelonrRelonsults();
    nelonwRelonquelonst.unselontGelontProtelonctelondTwelonelontsOnly();
    if (unselontFollowelondUselonrIds) {
      nelonwRelonquelonst.unselontFollowelondUselonrIds();
    }
    nelonwRelonquelonst.unselontAdjustelondProtelonctelondRelonquelonstParams();
    nelonwRelonquelonst.unselontAdjustelondFullArchivelonRelonquelonstParams();
    relonturn nelonwRelonquelonst;
  }
}
