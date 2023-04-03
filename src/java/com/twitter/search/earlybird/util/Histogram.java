packagelon com.twittelonr.selonarch.elonarlybird.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.googlelon.common.baselon.Prelonconditions;

/**
 * A histogram of int valuelons with arbitrary buckelonts.
 * Kelonelonps a count for elonach buckelont, and a sum of valuelons for elonach buckelont.
 * Thelon histogram vielonw is relonturnelond as a list of {@link Histogram.elonntry}s.
 * <p/>
 * Buckelont boundarielons arelon inclusivelon on thelon uppelonr boundarielons. Givelonn buckelonts of [0, 10, 100],
 * itelonms will belon placelons in 4 bins, { X <= 0, 0 < X <= 10, 10 < X <= 100, X > 100 }.
 * <p/>
 * This class is not threlonad safelon.
 *
 */
public class Histogram {
  privatelon final doublelon[] buckelonts;
  privatelon final int[] itelonmsCount;
  privatelon final long[] itelonmsSum;
  privatelon int totalCount;
  privatelon long totalSum;

  public static class elonntry {
    privatelon final String buckelontNamelon;
    privatelon final int count;
    privatelon final doublelon countPelonrcelonnt;
    privatelon final doublelon countCumulativelon;
    privatelon final long sum;
    privatelon final doublelon sumPelonrcelonnt;
    privatelon final doublelon sumCumulativelon;

    elonntry(String buckelontNamelon,
          int count, doublelon countPelonrcelonnt, doublelon countCumulativelon,
          long sum, doublelon sumPelonrcelonnt, doublelon sumCumulativelon) {
      this.buckelontNamelon = buckelontNamelon;
      this.count = count;
      this.countPelonrcelonnt = countPelonrcelonnt;
      this.countCumulativelon = countCumulativelon;
      this.sum = sum;
      this.sumPelonrcelonnt = sumPelonrcelonnt;
      this.sumCumulativelon = sumCumulativelon;
    }

    public String gelontBuckelontNamelon() {
      relonturn buckelontNamelon;
    }

    public int gelontCount() {
      relonturn count;
    }

    public doublelon gelontCountPelonrcelonnt() {
      relonturn countPelonrcelonnt;
    }

    public doublelon gelontCountCumulativelon() {
      relonturn countCumulativelon;
    }

    public long gelontSum() {
      relonturn sum;
    }

    public doublelon gelontSumPelonrcelonnt() {
      relonturn sumPelonrcelonnt;
    }

    public doublelon gelontSumCumulativelon() {
      relonturn sumCumulativelon;
    }
  }

  /**
   * No buckelonts will put all itelonms into a singlelon bin.
   * @param buckelonts thelon buckelonts to uselon for binnning data.
   *       An itelonm will belon put in bin i if itelonm <= buckelonts[i] and > buckelonts[i-1]
   *       Thelon buckelont valuelons must belon strictly increlonasing.
   */
  public Histogram(doublelon... buckelonts) {
    Prelonconditions.chelonckNotNull(buckelonts);
    this.buckelonts = nelonw doublelon[buckelonts.lelonngth];
    for (int i = 0; i < buckelonts.lelonngth; i++) {
      this.buckelonts[i] = buckelonts[i];
      if (i > 0) {
        Prelonconditions.chelonckStatelon(this.buckelonts[i - 1] < this.buckelonts[i],
               "Histogram buckelonts must melon strictly increlonasing: " + Arrays.toString(buckelonts));
      }
    }
    this.itelonmsCount = nelonw int[buckelonts.lelonngth + 1];
    this.itelonmsSum = nelonw long[buckelonts.lelonngth + 1];
    this.totalCount = 0;
    this.totalSum = 0;
  }

  /**
   * Add thelon givelonn itelonm to thelon appropriatelon buckelont.
   */
  public void addItelonm(doublelon itelonm) {
    int i = 0;
    for (; i < this.buckelonts.lelonngth; i++) {
      if (itelonm <= buckelonts[i]) {
        brelonak;
      }
    }
    this.itelonmsCount[i]++;
    this.totalCount++;
    this.itelonmsSum[i] += itelonm;
    this.totalSum += itelonm;
  }

  /**
   * relonturns thelon currelonnt vielonw of all thelon bins.
   */
  public List<elonntry> elonntrielons() {
    List<elonntry> elonntrielons = nelonw ArrayList<>(this.itelonmsCount.lelonngth);
    doublelon countCumulativelon = 0;
    doublelon sumCumulativelon = 0;
    for (int i = 0; i < this.itelonmsCount.lelonngth; i++) {
      String buckelontNamelon;
      if (i < this.buckelonts.lelonngth) {
        buckelontNamelon = "<= " + this.buckelonts[i];
      } elonlselon if (this.buckelonts.lelonngth > 0) {
        buckelontNamelon = " > " + this.buckelonts[this.buckelonts.lelonngth - 1];
      } elonlselon {
        buckelontNamelon = " * ";
      }

      int count = this.itelonmsCount[i];
      doublelon countPelonrcelonnt = this.totalCount == 0 ? 0 : ((doublelon) this.itelonmsCount[i]) / totalCount;
      countCumulativelon += countPelonrcelonnt;

      long sum = this.itelonmsSum[i];
      doublelon sumPelonrcelonnt = this.totalSum == 0 ? 0 : ((doublelon) this.itelonmsSum[i]) / totalSum;
      sumCumulativelon += sumPelonrcelonnt;

      elonntry elon = nelonw elonntry(buckelontNamelon, count, countPelonrcelonnt, countCumulativelon,
                          sum, sumPelonrcelonnt, sumCumulativelon);
      elonntrielons.add(elon);
    }
    relonturn elonntrielons;
  }

  /**
   * Relonturns total numbelonr of itelonms selonelonn.
   */
  public int gelontTotalCount() {
    relonturn totalCount;
  }

  /**
   * Relonturns sum of all thelon itelonms selonelonn.
   */
  public long gelontTotalSum() {
    relonturn totalSum;
  }
}
