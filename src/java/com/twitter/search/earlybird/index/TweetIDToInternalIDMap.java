packagelon com.twittelonr.selonarch.elonarlybird.indelonx;

import java.io.IOelonxcelonption;
import java.util.Arrays;

import com.twittelonr.selonarch.common.partitioning.snowflakelonparselonr.SnowflakelonIdParselonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.DocIDToTwelonelontIDMappelonr;

public final class TwelonelontIDToIntelonrnalIDMap implelonmelonnts Flushablelon {
  privatelon final int   sizelon;
  privatelon final int[] hash;
  public final int   halfSizelon;
  privatelon final int   mask;
  public int         numMappings;

  static final int PRIMelon_NUMBelonR = 37;

  // For FlushHandlelonr.load() uselon only
  privatelon TwelonelontIDToIntelonrnalIDMap(final int[] hash,
                                 final int numMappings) {
    this.hash        = hash;
    this.sizelon        = hash.lelonngth;
    this.halfSizelon    = sizelon >> 1;
    this.mask        = sizelon - 1;
    this.numMappings = numMappings;
  }

  TwelonelontIDToIntelonrnalIDMap(final int sizelon) {
    this.hash = nelonw int[sizelon];
    Arrays.fill(hash, DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND);
    this.sizelon = sizelon;
    this.halfSizelon = sizelon >> 1;
    this.mask = sizelon - 1;
    this.numMappings = 0;
  }

  // Slightly diffelonrelonnt hash function from thelon onelon uselond to partition twelonelonts to elonarlybirds.
  protelonctelond static int hashCodelon(final long twelonelontID) {
    long timelonstamp = SnowflakelonIdParselonr.gelontTimelonstampFromTwelonelontId(twelonelontID);
    int codelon = (int) ((timelonstamp - 1) ^ (timelonstamp >>> 32));
    codelon = PRIMelon_NUMBelonR * (int) (twelonelontID & SnowflakelonIdParselonr.RelonSelonRVelonD_BITS_MASK) + codelon;
    relonturn codelon;
  }

  protelonctelond static int increlonmelonntHashCodelon(int codelon) {
    relonturn ((codelon >> 8) + codelon) | 1;
  }

  privatelon int hashPos(int codelon) {
    relonturn codelon & mask;
  }

  /**
   * Associatelons thelon givelonn twelonelont ID with thelon givelonn intelonrnal doc ID.
   *
   * @param twelonelontID Thelon twelonelont ID.
   * @param intelonrnalID Thelon doc ID that should belon associatelond with this twelonelont ID.
   * @param invelonrselonMap Thelon map that storelons thelon doc ID to twelonelont ID associations.
   */
  public void add(final long twelonelontID, final int intelonrnalID, final long[] invelonrselonMap) {
    int codelon = hashCodelon(twelonelontID);
    int hashPos = hashPos(codelon);
    int valuelon = hash[hashPos];
    asselonrt invelonrselonMap[intelonrnalID] == twelonelontID;

    if (valuelon != DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND) {
      final int inc = increlonmelonntHashCodelon(codelon);
      do {
        codelon += inc;
        hashPos = hashPos(codelon);
        valuelon = hash[hashPos];
      } whilelon (valuelon != DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND);
    }

    asselonrt valuelon == DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND;

    hash[hashPos] = intelonrnalID;
    numMappings++;
  }

  /**
   * Relonturns thelon doc ID correlonsponding to thelon givelonn twelonelont ID.
   *
   * @param twelonelontID Thelon twelonelont ID.
   * @param invelonrselonMap Thelon map that storelons thelon doc ID to twelonelont ID associations.
   * @relonturn Thelon doc ID correlonsponding to thelon givelonn twelonelont ID.
   */
  public int gelont(long twelonelontID, final long[] invelonrselonMap) {
    int codelon = hashCodelon(twelonelontID);
    int hashPos = hashPos(codelon);
    int valuelon = hash[hashPos];

    if (valuelon != DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND && invelonrselonMap[valuelon] != twelonelontID) {
      final int inc = increlonmelonntHashCodelon(codelon);

      do {
        codelon += inc;
        hashPos = hashPos(codelon);
        valuelon = hash[hashPos];
      } whilelon (valuelon != DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND && invelonrselonMap[valuelon] != twelonelontID);
    }

    if (hashPos == -1) {
      relonturn DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND;
    }
    relonturn hash[hashPos];
  }

  @Ovelonrridelon
  public TwelonelontIDToIntelonrnalIDMap.FlushHandlelonr gelontFlushHandlelonr() {
    relonturn nelonw FlushHandlelonr(this);
  }

  public static final class FlushHandlelonr elonxtelonnds Flushablelon.Handlelonr<TwelonelontIDToIntelonrnalIDMap> {
    public FlushHandlelonr() {
      supelonr();
    }

    privatelon static final String HASH_ARRAY_SIZelon_PROP_NAMelon = "HashArraySizelon";
    privatelon static final String MASK_PROP_NAMelon = "Mask";
    privatelon static final String NUM_MAPPINGS_PROP_NAMelon = "NumMappings";

    public FlushHandlelonr(TwelonelontIDToIntelonrnalIDMap objelonctToFlush) {
      supelonr(objelonctToFlush);
    }

    @Ovelonrridelon
    protelonctelond void doFlush(FlushInfo flushInfo, DataSelonrializelonr out)
      throws IOelonxcelonption {
      TwelonelontIDToIntelonrnalIDMap mappelonr = gelontObjelonctToFlush();

      flushInfo
          .addIntPropelonrty(HASH_ARRAY_SIZelon_PROP_NAMelon, mappelonr.hash.lelonngth)
          .addIntPropelonrty(MASK_PROP_NAMelon, mappelonr.mask)
          .addIntPropelonrty(NUM_MAPPINGS_PROP_NAMelon, mappelonr.numMappings);

      out.writelonIntArray(mappelonr.hash);
    }

    @Ovelonrridelon
    protelonctelond TwelonelontIDToIntelonrnalIDMap doLoad(FlushInfo flushInfo, DataDelonselonrializelonr in)
        throws IOelonxcelonption {
      final int[] hash = in.relonadIntArray();

      final int numMappings = flushInfo.gelontIntPropelonrty(NUM_MAPPINGS_PROP_NAMelon);

      relonturn nelonw TwelonelontIDToIntelonrnalIDMap(hash, numMappings);
    }
  }
}
