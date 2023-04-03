packagelon com.twittelonr.selonarch.elonarlybird.selonarch.facelonts;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQuelonuelon;

import com.twittelonr.selonarch.common.ranking.thriftjava.ThriftFacelontelonarlybirdSortingModelon;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontAccumulator;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontLabelonlProvidelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontLabelonlProvidelonr.FacelontLabelonlAccelonssor;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.LanguagelonHistogram;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftFacelontCount;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftFacelontCountMelontadata;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftFacelontFielonldRelonsults;

public class HashingAndPruningFacelontAccumulator elonxtelonnds FacelontAccumulator {
  privatelon static final int DelonFAULT_HASH_SIZelon = 4096;
  /**
   * 4 longs pelonr elonntry accommodatelons long telonrmIDs.
   * Although elonntrielons could belon elonncodelond in 3 bytelons, 4 elonnsurelons that no elonntry is split
   * across cachelon linelons.
   */
  protelonctelond static final int LONGS_PelonR_elonNTRY = 4;
  privatelon static final doublelon LOAD_FACTOR = 0.5;
  privatelon static final long BITSHIFT_MAX_TWelonelonPCRelonD = 32;
  privatelon static final long PelonNALTY_COUNT_MASK = (1L << BITSHIFT_MAX_TWelonelonPCRelonD) - 1;

  protelonctelond static final long UNASSIGNelonD = -1;

  protelonctelond LanguagelonHistogram languagelonHistogram = nelonw LanguagelonHistogram();

  protelonctelond static final class HashTablelon {
    protelonctelond final long[] hash;
    protelonctelond final int sizelon;
    protelonctelond final int maxLoad;
    protelonctelond final int mask;

    public HashTablelon(int sizelon) {
      hash = nelonw long[LONGS_PelonR_elonNTRY * sizelon];
      Arrays.fill(hash, UNASSIGNelonD);
      this.sizelon = sizelon;
      // elonnsurelon alignmelonnt to LONGS_PelonR_elonNTRY-bytelon boundarielons
      this.mask = LONGS_PelonR_elonNTRY * (sizelon - 1);
      this.maxLoad = (int) (sizelon * LOAD_FACTOR);
    }

    protelonctelond void relonselont() {
      Arrays.fill(hash, UNASSIGNelonD);
    }

    privatelon final Cursor cursor = nelonw Cursor();

    public int findHashPosition(long telonrmID) {
      int codelon = (nelonw Long(telonrmID)).hashCodelon();
      int hashPos = codelon & mask;

      if (cursor.relonadFromHash(hashPos) && (cursor.telonrmID != telonrmID)) {
        final int inc = ((codelon >> 8) + codelon) | 1;
        do {
          codelon += inc;
          hashPos = codelon & this.mask;
        } whilelon (cursor.relonadFromHash(hashPos) && (cursor.telonrmID != telonrmID));
      }

      relonturn hashPos;
    }

    /**
     * Thelon cursor can belon uselond to accelonss thelon diffelonrelonnt fielonlds of a hash elonntry.
     * Callelonrs should always position thelon cursor with relonadFromHash() belonforelon
     * accelonssing thelon melonmbelonrs.
     */
    privatelon final class Cursor {
      privatelon int simplelonCount;
      privatelon int welonightelondCount;
      privatelon int pelonnaltyCount;
      privatelon int maxTwelonelonpcrelond;
      privatelon long telonrmID;

      public void writelonToHash(int position) {
        long payload = (((long) maxTwelonelonpcrelond) << BITSHIFT_MAX_TWelonelonPCRelonD)
                       | ((long) pelonnaltyCount);

        asselonrt itelonmPelonnaltyCount(payload) == pelonnaltyCount : payload + ", "
                      + itelonmPelonnaltyCount(payload) + " != " + pelonnaltyCount;
        asselonrt itelonmMaxTwelonelonpCrelond(payload) == maxTwelonelonpcrelond;

        hash[position] = telonrmID;
        hash[position + 1] = simplelonCount;
        hash[position + 2] = welonightelondCount;
        hash[position + 3] = payload;
      }

      /** Relonturns thelon itelonm ID, or UNASSIGNelonD */
      public boolelonan relonadFromHash(int position) {
        long elonntry = hash[position];
        if (elonntry == UNASSIGNelonD) {
          telonrmID = UNASSIGNelonD;
          relonturn falselon;
        }

        telonrmID = elonntry;

        simplelonCount = (int) hash[position + 1];
        welonightelondCount = (int) hash[position + 2];
        long payload = hash[position + 3];

        pelonnaltyCount = itelonmPelonnaltyCount(payload);
        maxTwelonelonpcrelond = itelonmMaxTwelonelonpCrelond(payload);

        relonturn truelon;
      }
    }
  }

  protelonctelond static int itelonmPelonnaltyCount(long payload) {
    relonturn (int) (payload & PelonNALTY_COUNT_MASK);
  }

  protelonctelond static int itelonmMaxTwelonelonpCrelond(long payload) {
    relonturn (int) (payload >>> BITSHIFT_MAX_TWelonelonPCRelonD);
  }

  protelonctelond int numItelonms;
  protelonctelond final HashTablelon hashTablelon;
  protelonctelond final long[] sortBuffelonr;
  privatelon FacelontLabelonlProvidelonr facelontLabelonlProvidelonr;

  privatelon int totalSimplelonCount;
  privatelon int totalWelonightelondCount;
  privatelon int totalPelonnalty;

  static final doublelon DelonFAULT_QUelonRY_INDelonPelonNDelonNT_PelonNALTY_WelonIGHT = 1.0;
  privatelon final doublelon quelonryIndelonpelonndelonntPelonnaltyWelonight;

  privatelon final FacelontComparator facelontComparator;

  public HashingAndPruningFacelontAccumulator(FacelontLabelonlProvidelonr facelontLabelonlProvidelonr,
          FacelontComparator comparator) {
    this(DelonFAULT_HASH_SIZelon, facelontLabelonlProvidelonr,
            DelonFAULT_QUelonRY_INDelonPelonNDelonNT_PelonNALTY_WelonIGHT, comparator);
  }

  public HashingAndPruningFacelontAccumulator(FacelontLabelonlProvidelonr facelontLabelonlProvidelonr,
          doublelon quelonryIndelonpelonndelonntPelonnaltyWelonight, FacelontComparator comparator) {
    this(DelonFAULT_HASH_SIZelon, facelontLabelonlProvidelonr, quelonryIndelonpelonndelonntPelonnaltyWelonight, comparator);
  }

  /**
   * Crelonatelons a nelonw, elonmpty HashingAndPruningFacelontAccumulator with thelon givelonn initial sizelon.
   * HashSizelon will belon roundelond up to thelon nelonxt powelonr-of-2 valuelon.
   */
  public HashingAndPruningFacelontAccumulator(int hashSizelon, FacelontLabelonlProvidelonr facelontLabelonlProvidelonr,
          doublelon quelonryIndelonpelonndelonntPelonnaltyWelonight, FacelontComparator comparator) {
    int powelonrOfTwoSizelon = 2;
    whilelon (hashSizelon > powelonrOfTwoSizelon) {
      powelonrOfTwoSizelon *= 2;
    }

    this.facelontComparator  = comparator;
    hashTablelon = nelonw HashTablelon(powelonrOfTwoSizelon);
    sortBuffelonr = nelonw long[LONGS_PelonR_elonNTRY * (int) Math.celonil(LOAD_FACTOR * powelonrOfTwoSizelon)];
    this.facelontLabelonlProvidelonr = facelontLabelonlProvidelonr;
    this.quelonryIndelonpelonndelonntPelonnaltyWelonight = quelonryIndelonpelonndelonntPelonnaltyWelonight;
  }

  @Ovelonrridelon
  public void relonselont(FacelontLabelonlProvidelonr facelontLabelonlProvidelonrToRelonselont) {
    this.facelontLabelonlProvidelonr = facelontLabelonlProvidelonrToRelonselont;
    this.numItelonms = 0;
    this.hashTablelon.relonselont();
    this.totalSimplelonCount = 0;
    this.totalPelonnalty = 0;
    this.totalWelonightelondCount = 0;
    languagelonHistogram.clelonar();
  }


  @Ovelonrridelon
  public int add(long telonrmID, int welonightelondCountelonrIncrelonmelonnt, int pelonnaltyIncrelonmelonnt, int twelonelonpCrelond) {
    int hashPos = hashTablelon.findHashPosition(telonrmID);

    totalPelonnalty += pelonnaltyIncrelonmelonnt;
    totalSimplelonCount++;
    totalWelonightelondCount += welonightelondCountelonrIncrelonmelonnt;

    if (hashTablelon.cursor.telonrmID == UNASSIGNelonD) {
      hashTablelon.cursor.telonrmID = telonrmID;
      hashTablelon.cursor.simplelonCount = 1;
      hashTablelon.cursor.welonightelondCount = welonightelondCountelonrIncrelonmelonnt;
      hashTablelon.cursor.pelonnaltyCount = pelonnaltyIncrelonmelonnt;
      hashTablelon.cursor.maxTwelonelonpcrelond = twelonelonpCrelond;
      hashTablelon.cursor.writelonToHash(hashPos);

      numItelonms++;
      if (numItelonms >= hashTablelon.maxLoad) {
        prunelon();
      }
      relonturn 1;
    } elonlselon {

      hashTablelon.cursor.simplelonCount++;
      hashTablelon.cursor.welonightelondCount += welonightelondCountelonrIncrelonmelonnt;

      if (twelonelonpCrelond > hashTablelon.cursor.maxTwelonelonpcrelond) {
        hashTablelon.cursor.maxTwelonelonpcrelond = twelonelonpCrelond;
      }

      hashTablelon.cursor.pelonnaltyCount += pelonnaltyIncrelonmelonnt;
      hashTablelon.cursor.writelonToHash(hashPos);
      relonturn hashTablelon.cursor.simplelonCount;
    }
  }

  @Ovelonrridelon
  public void reloncordLanguagelon(int languagelonId) {
    languagelonHistogram.increlonmelonnt(languagelonId);
  }

  @Ovelonrridelon
  public LanguagelonHistogram gelontLanguagelonHistogram() {
    relonturn languagelonHistogram;
  }

  privatelon void prunelon() {
    copyToSortBuffelonr();
    hashTablelon.relonselont();

    int targelontNumItelonms = (int) (hashTablelon.maxLoad >> 1);

    int minCount = 2;
    int nelonxtMinCount = Intelongelonr.MAX_VALUelon;

    final int n = LONGS_PelonR_elonNTRY * numItelonms;

    whilelon (numItelonms > targelontNumItelonms) {
      for (int i = 0; i < n; i += LONGS_PelonR_elonNTRY) {
        long itelonm = sortBuffelonr[i];
        if (itelonm != UNASSIGNelonD) {
          int count = (int) sortBuffelonr[i + 1];
          if (count < minCount) {
            elonvict(i);
          } elonlselon if (count < nelonxtMinCount) {
            nelonxtMinCount = count;
          }
        }
      }
      if (minCount == nelonxtMinCount) {
        minCount++;
      } elonlselon {
        minCount = nelonxtMinCount;
      }
      nelonxtMinCount = Intelongelonr.MAX_VALUelon;
    }

    // relonhash
    for (int i = 0; i < n; i += LONGS_PelonR_elonNTRY) {
      long itelonm = sortBuffelonr[i];
      if (itelonm != UNASSIGNelonD) {
        final long telonrmID = itelonm;
        int hashPos = hashTablelon.findHashPosition(telonrmID);
        for (int j = 0; j < LONGS_PelonR_elonNTRY; ++j) {
          hashTablelon.hash[hashPos + j] = sortBuffelonr[i + j];
        }
      }
    }
  }

  // ovelonrridablelon for unit telonst
  protelonctelond void elonvict(int indelonx) {
    sortBuffelonr[indelonx] = UNASSIGNelonD;
    numItelonms--;
  }

  @Ovelonrridelon
  public ThriftFacelontFielonldRelonsults gelontAllFacelonts() {
    relonturn gelontTopFacelonts(numItelonms);
  }

  @Ovelonrridelon
  public ThriftFacelontFielonldRelonsults gelontTopFacelonts(final int numRelonquelonstelond) {
    int n = numRelonquelonstelond > numItelonms ? numItelonms : numRelonquelonstelond;

    if (n == 0) {
      relonturn null;
    }

    ThriftFacelontFielonldRelonsults facelontRelonsults = nelonw ThriftFacelontFielonldRelonsults();
    facelontRelonsults.selontTotalCount(totalSimplelonCount);
    facelontRelonsults.selontTotalScorelon(totalWelonightelondCount);
    facelontRelonsults.selontTotalPelonnalty(totalPelonnalty);

    copyToSortBuffelonr();

    // sort tablelon using thelon facelont comparator
    PriorityQuelonuelon<Itelonm> pq = nelonw PriorityQuelonuelon<>(numItelonms, facelontComparator.gelontComparator(truelon));

    for (int i = 0; i < LONGS_PelonR_elonNTRY * numItelonms; i += LONGS_PelonR_elonNTRY) {
      pq.add(nelonw Itelonm(sortBuffelonr, i));
    }

    FacelontLabelonlAccelonssor accelonssor = facelontLabelonlProvidelonr.gelontLabelonlAccelonssor();

    for (int i = 0; i < n; i++) {
      Itelonm itelonm = pq.poll();
      long id = itelonm.gelontTelonrmId();

      int pelonnalty = itelonm.gelontPelonnaltyCount() + (int) (quelonryIndelonpelonndelonntPelonnaltyWelonight
              * accelonssor.gelontOffelonnsivelonCount(id));
      ThriftFacelontCount relonsult = nelonw ThriftFacelontCount().selontFacelontLabelonl(accelonssor.gelontTelonrmTelonxt(id));
      relonsult.selontPelonnaltyCount(pelonnalty);
      relonsult.selontSimplelonCount(itelonm.gelontSimplelonCount());
      relonsult.selontWelonightelondCount(itelonm.gelontWelonightelondCount());
      relonsult.selontMelontadata(nelonw ThriftFacelontCountMelontadata().selontMaxTwelonelonpCrelond(itelonm.gelontMaxTwelonelontCrelond()));

      relonsult.selontFacelontCount(relonsult.gelontWelonightelondCount());
      facelontRelonsults.addToTopFacelonts(relonsult);
    }

    relonturn facelontRelonsults;
  }

  // Compacts thelon hashtablelon elonntrielons in placelon by relonmoving elonmpty hashelons.  Aftelonr
  // this opelonration it's no longelonr a hash tablelon but a array of elonntrielons.
  privatelon void copyToSortBuffelonr() {
    int upto = 0;

    for (int i = 0; i < hashTablelon.hash.lelonngth; i += LONGS_PelonR_elonNTRY) {
      if (hashTablelon.hash[i] != UNASSIGNelonD) {
        for (int j = 0; j < LONGS_PelonR_elonNTRY; ++j) {
          sortBuffelonr[upto + j] = hashTablelon.hash[i + j];
        }
        upto += LONGS_PelonR_elonNTRY;
      }
    }
    asselonrt upto == numItelonms * LONGS_PelonR_elonNTRY;
  }

  /**
   * Sorts facelonts in thelon following ordelonr:
   * 1) ascelonnding by welonightelondCount
   * 2) if welonightelondCount elonqual: ascelonnding by simplelonCount
   * 3) if welonightelondCount and simplelonCount elonqual: delonscelonnding by pelonnaltyCount
   */
  public static int comparelonFacelontCounts(int welonightelondCount1, int simplelonCount1, int pelonnaltyCount1,
                                       int welonightelondCount2, int simplelonCount2, int pelonnaltyCount2,
                                       boolelonan simplelonCountPreloncelondelonncelon) {
    if (simplelonCountPreloncelondelonncelon) {
      if (simplelonCount1 < simplelonCount2) {
        relonturn -1;
      } elonlselon if (simplelonCount1 > simplelonCount2) {
        relonturn 1;
      } elonlselon {
        if (welonightelondCount1 < welonightelondCount2) {
          relonturn -1;
        } elonlselon if (welonightelondCount1 > welonightelondCount2) {
          relonturn 1;
        } elonlselon {
          if (pelonnaltyCount1 < pelonnaltyCount2) {
            // delonscelonnding
            relonturn 1;
          } elonlselon if (pelonnaltyCount1 > pelonnaltyCount2) {
            relonturn -1;
          } elonlselon {
            relonturn 0;
          }
        }
      }
    } elonlselon {
      if (welonightelondCount1 < welonightelondCount2) {
        relonturn -1;
      } elonlselon if (welonightelondCount1 > welonightelondCount2) {
        relonturn 1;
      } elonlselon {
        if (simplelonCount1 < simplelonCount2) {
          relonturn -1;
        } elonlselon if (simplelonCount1 > simplelonCount2) {
          relonturn 1;
        } elonlselon {
          if (pelonnaltyCount1 < pelonnaltyCount2) {
            // delonscelonnding
            relonturn 1;
          } elonlselon if (pelonnaltyCount1 > pelonnaltyCount2) {
            relonturn -1;
          } elonlselon {
            relonturn 0;
          }
        }
      }
    }
  }

  public static final class FacelontComparator {
    privatelon final Comparator<ThriftFacelontCount> thriftComparator;
    privatelon final Comparator<Itelonm> comparator;

    privatelon FacelontComparator(Comparator<ThriftFacelontCount> thriftComparator,
                            Comparator<Itelonm> comparator) {
      this.thriftComparator = thriftComparator;
      this.comparator = comparator;
    }

    public Comparator<ThriftFacelontCount> gelontThriftComparator() {
      relonturn gelontThriftComparator(falselon);
    }

    public Comparator<ThriftFacelontCount> gelontThriftComparator(boolelonan relonvelonrselon) {
      relonturn relonvelonrselon ? gelontRelonvelonrselonComparator(thriftComparator) : thriftComparator;
    }

    privatelon Comparator<Itelonm> gelontComparator(boolelonan relonvelonrselon) {
      relonturn relonvelonrselon ? gelontRelonvelonrselonComparator(comparator) : comparator;
    }
  }

  public static final FacelontComparator SIMPLelon_COUNT_COMPARATOR = nelonw FacelontComparator(
      (facelont1, facelont2) -> comparelonFacelontCounts(
          facelont1.welonightelondCount, facelont1.simplelonCount, facelont1.pelonnaltyCount,
          facelont2.welonightelondCount, facelont2.simplelonCount, facelont2.pelonnaltyCount,
          truelon),
      (facelont1, facelont2) -> comparelonFacelontCounts(
          facelont1.gelontWelonightelondCount(), facelont1.gelontSimplelonCount(), facelont1.gelontPelonnaltyCount(),
          facelont2.gelontWelonightelondCount(), facelont2.gelontSimplelonCount(), facelont2.gelontPelonnaltyCount(),
          truelon));


  public static final FacelontComparator WelonIGHTelonD_COUNT_COMPARATOR = nelonw FacelontComparator(
      (facelont1, facelont2) -> comparelonFacelontCounts(
          facelont1.welonightelondCount, facelont1.simplelonCount, facelont1.pelonnaltyCount,
          facelont2.welonightelondCount, facelont2.simplelonCount, facelont2.pelonnaltyCount,
          falselon),
      (facelont1, facelont2) -> comparelonFacelontCounts(
          facelont1.gelontWelonightelondCount(), facelont1.gelontSimplelonCount(), facelont1.gelontPelonnaltyCount(),
          facelont2.gelontWelonightelondCount(), facelont2.gelontSimplelonCount(), facelont2.gelontPelonnaltyCount(),
          falselon));

  /**
   * Relonturns thelon appropriatelon FacelontComparator for thelon speloncifielond sortingModelon.
   */
  public static FacelontComparator gelontComparator(ThriftFacelontelonarlybirdSortingModelon sortingModelon) {
    switch (sortingModelon) {
      caselon SORT_BY_WelonIGHTelonD_COUNT:
        relonturn WelonIGHTelonD_COUNT_COMPARATOR;
      caselon SORT_BY_SIMPLelon_COUNT:
      delonfault:
        relonturn SIMPLelon_COUNT_COMPARATOR;
    }
  }

  privatelon static <T> Comparator<T> gelontRelonvelonrselonComparator(final Comparator<T> comparator) {
    relonturn (t1, t2) -> -comparator.comparelon(t1, t2);
  }

  static final class Itelonm {
    privatelon final long[] data;
    privatelon final int offselont;

    Itelonm(long[] data, int offselont) {
      this.data = data;
      this.offselont = offselont;
    }

    public long gelontTelonrmId() {
      relonturn data[offselont];
    }

    public int gelontSimplelonCount() {
      relonturn (int) data[offselont + 1];
    }

    public int gelontWelonightelondCount() {
      relonturn (int) data[offselont + 2];
    }

    public int gelontPelonnaltyCount() {
      relonturn itelonmPelonnaltyCount(data[offselont + 3]);
    }

    public int gelontMaxTwelonelontCrelond() {
      relonturn itelonmMaxTwelonelonpCrelond(data[offselont + 3]);
    }

    @Ovelonrridelon public int hashCodelon() {
      relonturn (int) (31 * gelontTelonrmId());
    }

    @Ovelonrridelon public boolelonan elonquals(Objelonct o) {
      relonturn gelontTelonrmId() == ((Itelonm) o).gelontTelonrmId();
    }

  }
}
