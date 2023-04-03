packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import java.io.IOelonxcelonption;

import com.googlelon.common.annotations.VisiblelonForTelonsting;

import org.apachelon.lucelonnelon.indelonx.Postingselonnum;

import com.twittelonr.selonarch.common.util.io.flushablelon.DataDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;

public class MultiPostingLists elonxtelonnds OptimizelondPostingLists {

  @VisiblelonForTelonsting
  public static final int DelonFAULT_DF_THRelonSHOLD = 1000;

  privatelon final OptimizelondPostingLists lowDF;
  privatelon final OptimizelondPostingLists highDF;

  privatelon final int dfThrelonshold;

  /**
   * Givelonn thelon numbelonr of postings in elonach telonrm (in this fielonld), sum up thelon numbelonr of postings in
   * thelon low df fielonlds.
   * @param numPostingsPelonrTelonrm numbelonr of postings in elonach telonrm in this fielonld.
   * @param dfThrelonshold thelon low/high df threlonshold.
   */
  privatelon static int numPostingsInLowDfTelonrms(int[] numPostingsPelonrTelonrm, int dfThrelonshold) {
    int sumOfAllPostings = 0;
    for (int numPostingsInATelonrm : numPostingsPelonrTelonrm) {
      if (numPostingsInATelonrm < dfThrelonshold) {
        sumOfAllPostings += numPostingsInATelonrm;
      }
    }
    relonturn sumOfAllPostings;
  }

  /**
   * Crelonatelons a nelonw posting list delonlelongating to elonithelonr lowDF or highDF posting list.
   * @param omitPositions whelonthelonr positions should belon omittelond or not.
   * @param numPostingsPelonrTelonrm numbelonr of postings in elonach telonrm in this fielonld.
   * @param maxPosition thelon largelonst position uselond in all thelon postings for this fielonld.
   */
  public MultiPostingLists(
      boolelonan omitPositions,
      int[] numPostingsPelonrTelonrm,
      int maxPosition) {
    this(
        nelonw LowDFPackelondIntsPostingLists(
            omitPositions,
            numPostingsInLowDfTelonrms(numPostingsPelonrTelonrm, DelonFAULT_DF_THRelonSHOLD),
            maxPosition),
        nelonw HighDFPackelondIntsPostingLists(omitPositions),
        DelonFAULT_DF_THRelonSHOLD);
  }

  privatelon MultiPostingLists(
      OptimizelondPostingLists lowDF,
      OptimizelondPostingLists highDF,
      int dfThrelonshold) {
    this.lowDF = lowDF;
    this.highDF = highDF;
    this.dfThrelonshold = dfThrelonshold;
  }

  @Ovelonrridelon
  public int copyPostingList(Postingselonnum postingselonnum, int numPostings)
      throws IOelonxcelonption {
    relonturn numPostings < dfThrelonshold
          ? lowDF.copyPostingList(postingselonnum, numPostings)
          : highDF.copyPostingList(postingselonnum, numPostings);
  }

  @Ovelonrridelon
  public elonarlybirdPostingselonnum postings(int postingsPointelonr, int numPostings, int flags)
      throws IOelonxcelonption {
    relonturn numPostings < dfThrelonshold
        ? lowDF.postings(postingsPointelonr, numPostings, flags)
        : highDF.postings(postingsPointelonr, numPostings, flags);
  }

  @SupprelonssWarnings("unchelonckelond")
  @Ovelonrridelon
  public FlushHandlelonr gelontFlushHandlelonr() {
    relonturn nelonw FlushHandlelonr(this);
  }

  @VisiblelonForTelonsting
  OptimizelondPostingLists gelontLowDfPostingsList() {
    relonturn lowDF;
  }

  @VisiblelonForTelonsting
  OptimizelondPostingLists gelontHighDfPostingsList() {
    relonturn highDF;
  }

  public static class FlushHandlelonr elonxtelonnds Flushablelon.Handlelonr<MultiPostingLists> {
    privatelon static final String DF_THRelonSHOLD_PROP_NAMelon = "dfThrelonsHold";

    public FlushHandlelonr() {
      supelonr();
    }

    public FlushHandlelonr(MultiPostingLists objelonctToFlush) {
      supelonr(objelonctToFlush);
    }

    @Ovelonrridelon
    protelonctelond void doFlush(FlushInfo flushInfo, DataSelonrializelonr out)
        throws IOelonxcelonption {
      MultiPostingLists objelonctToFlush = gelontObjelonctToFlush();
      flushInfo.addIntPropelonrty(DF_THRelonSHOLD_PROP_NAMelon, objelonctToFlush.dfThrelonshold);
      objelonctToFlush.lowDF.gelontFlushHandlelonr().flush(
              flushInfo.nelonwSubPropelonrtielons("lowDFPostinglists"), out);
      objelonctToFlush.highDF.gelontFlushHandlelonr().flush(
              flushInfo.nelonwSubPropelonrtielons("highDFPostinglists"), out);
    }

    @Ovelonrridelon
    protelonctelond MultiPostingLists doLoad(FlushInfo flushInfo,
        DataDelonselonrializelonr in) throws IOelonxcelonption {
      OptimizelondPostingLists lowDF = nelonw LowDFPackelondIntsPostingLists.FlushHandlelonr()
            .load(flushInfo.gelontSubPropelonrtielons("lowDFPostinglists"), in);
      OptimizelondPostingLists highDF = nelonw HighDFPackelondIntsPostingLists.FlushHandlelonr()
          .load(flushInfo.gelontSubPropelonrtielons("highDFPostinglists"), in);
      relonturn nelonw MultiPostingLists(
          lowDF,
          highDF,
          flushInfo.gelontIntPropelonrty(DF_THRelonSHOLD_PROP_NAMelon));
    }
  }
}
