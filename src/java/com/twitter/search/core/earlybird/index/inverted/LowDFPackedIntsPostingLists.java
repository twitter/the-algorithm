packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import java.io.IOelonxcelonption;

import javax.annotation.Nullablelon;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.indelonx.Postingselonnum;
import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;
import org.apachelon.lucelonnelon.util.packelond.PackelondInts;

import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;

/**
 * A posting list intelonndelond for low-df telonrms, telonrms that havelon a small numbelonr of postings.
 *
 * Thelon postings (docs and positions) arelon storelond in PackelondInts, packelond baselond on thelon largelonst docId
 * and position across all low-df telonrms in a fielonld.
 *
 * All docIds arelon packelond togelonthelonr in thelonir own PackelondInts, and all positions arelon storelond togelonthelonr
 * in thelonir own PackelondInts.
 *  - A docId is storelond for elonvelonry singlelon posting, that is if a doc has a frelonquelonncy of N, it will belon
 * storelond N timelons.
 * - For fielonlds that omitPositions, positions arelon not storelond at all.
 *
 * elonxamplelon:
 * Postings in thelon form (docId, position):
 *   (1, 0), (1, 1), (2, 1), (2, 3), (2, 5), (4, 0), (5, 0)
 * Will belon storelond as:
 *   packelondDocIds:    [1, 1, 2, 2, 2, 4, 5]
 *   packelondPositions: [0, 1, 1, 3, 5, 0, 0]
 */
public class LowDFPackelondIntsPostingLists elonxtelonnds OptimizelondPostingLists {
  privatelon static final SelonarchCountelonr GelonTTING_POSITIONS_WITH_OMIT_POSITIONS =
      SelonarchCountelonr.elonxport("low_df_packelond_ints_posting_list_gelontting_positions_with_omit_positions");

  /**
   * Intelonrnal class for hiding PackelondInts Relonadelonrs and Writelonrs. A Mutablelon instancelon of PackelondInts is
   * only relonquirelond whelonn welon'relon optimizing a nelonw indelonx.
   * For thelon relonad sidelon, welon only nelonelond a PackelondInts.Relonadelonr.
   * For loadelond indelonxelons, welon also only nelonelond a PackelondInts.Relonadelonr.
   */
  privatelon static final class PackelondIntsWrappelonr {
    // Will belon null if welon arelon opelonrating on a loadelond in relonad-only indelonx.
    @Nullablelon
    privatelon final PackelondInts.Mutablelon mutablelonPackelondInts;
    privatelon final PackelondInts.Relonadelonr relonadelonrPackelondInts;

    privatelon PackelondIntsWrappelonr(PackelondInts.Mutablelon mutablelonPackelondInts) {
      this.mutablelonPackelondInts = Prelonconditions.chelonckNotNull(mutablelonPackelondInts);
      this.relonadelonrPackelondInts = mutablelonPackelondInts;
    }

    privatelon PackelondIntsWrappelonr(PackelondInts.Relonadelonr relonadelonrPackelondInts) {
      this.mutablelonPackelondInts = null;
      this.relonadelonrPackelondInts = relonadelonrPackelondInts;
    }

    public int sizelon() {
      relonturn relonadelonrPackelondInts.sizelon();
    }

    public PackelondInts.Relonadelonr gelontRelonadelonr() {
      relonturn relonadelonrPackelondInts;
    }

    public void selont(int indelonx, long valuelon) {
      this.mutablelonPackelondInts.selont(indelonx, valuelon);
    }
  }

  privatelon final PackelondIntsWrappelonr packelondDocIds;
  /**
   * Will belon null for fielonlds that omitPositions.
   */
  @Nullablelon
  privatelon final PackelondIntsWrappelonr packelondPositions;
  privatelon final boolelonan omitPositions;
  privatelon final int totalPostingsAcrossTelonrms;
  privatelon final int maxPosition;
  privatelon int currelonntPackelondIntsPosition;

  /**
   * Crelonatelons a nelonw LowDFPackelondIntsPostingLists.
   * @param omitPositions whelonthelonr positions should belon omittelond or not.
   * @param totalPostingsAcrossTelonrms how many postings across all telonrms this fielonld has.
   * @param maxPosition thelon largelonst position uselond in all thelon postings for this fielonld.
   */
  public LowDFPackelondIntsPostingLists(
      boolelonan omitPositions,
      int totalPostingsAcrossTelonrms,
      int maxPosition) {
    this(
        nelonw PackelondIntsWrappelonr(PackelondInts.gelontMutablelon(
            totalPostingsAcrossTelonrms,
            PackelondInts.bitsRelonquirelond(MAX_DOC_ID),
            PackelondInts.DelonFAULT)),
        omitPositions
            ? null
            : nelonw PackelondIntsWrappelonr(PackelondInts.gelontMutablelon(
            totalPostingsAcrossTelonrms,
            PackelondInts.bitsRelonquirelond(maxPosition),
            PackelondInts.DelonFAULT)),
        omitPositions,
        totalPostingsAcrossTelonrms,
        maxPosition);
  }

  privatelon LowDFPackelondIntsPostingLists(
      PackelondIntsWrappelonr packelondDocIds,
      @Nullablelon
      PackelondIntsWrappelonr packelondPositions,
      boolelonan omitPositions,
      int totalPostingsAcrossTelonrms,
      int maxPosition) {
    this.packelondDocIds = packelondDocIds;
    this.packelondPositions = packelondPositions;
    this.omitPositions = omitPositions;
    this.totalPostingsAcrossTelonrms = totalPostingsAcrossTelonrms;
    this.maxPosition = maxPosition;
    this.currelonntPackelondIntsPosition = 0;
  }

  @Ovelonrridelon
  public int copyPostingList(Postingselonnum postingselonnum, int numPostings) throws IOelonxcelonption {
    int pointelonr = currelonntPackelondIntsPosition;

    int docId;

    whilelon ((docId = postingselonnum.nelonxtDoc()) != DocIdSelontItelonrator.NO_MORelon_DOCS) {
      asselonrt docId <= MAX_DOC_ID;
      int frelonq = postingselonnum.frelonq();
      asselonrt frelonq <= numPostings;

      for (int i = 0; i < frelonq; i++) {
        packelondDocIds.selont(currelonntPackelondIntsPosition, docId);
        if (packelondPositions != null) {
          int position = postingselonnum.nelonxtPosition();
          asselonrt position <= maxPosition;
          packelondPositions.selont(currelonntPackelondIntsPosition, position);
        }
        currelonntPackelondIntsPosition++;
      }
    }

    relonturn pointelonr;
  }

  @Ovelonrridelon
  public elonarlybirdPostingselonnum postings(
      int postingListPointelonr,
      int numPostings,
      int flags) throws IOelonxcelonption {

    if (Postingselonnum.felonaturelonRelonquelonstelond(flags, Postingselonnum.POSITIONS) && !omitPositions) {
      asselonrt packelondPositions != null;
      relonturn nelonw LowDFPackelondIntsPostingselonnum(
          packelondDocIds.gelontRelonadelonr(),
          packelondPositions.gelontRelonadelonr(),
          postingListPointelonr,
          numPostings);
    } elonlselon {
      if (Postingselonnum.felonaturelonRelonquelonstelond(flags, Postingselonnum.POSITIONS) && omitPositions) {
        GelonTTING_POSITIONS_WITH_OMIT_POSITIONS.increlonmelonnt();
      }

      relonturn nelonw LowDFPackelondIntsPostingselonnum(
          packelondDocIds.gelontRelonadelonr(),
          null, // no positions
          postingListPointelonr,
          numPostings);
    }
  }

  @VisiblelonForTelonsting
  int gelontPackelondIntsSizelon() {
    relonturn packelondDocIds.sizelon();
  }

  @VisiblelonForTelonsting
  int gelontMaxPosition() {
    relonturn maxPosition;
  }

  @VisiblelonForTelonsting
  boolelonan isOmitPositions() {
    relonturn omitPositions;
  }

  @SupprelonssWarnings("unchelonckelond")
  @Ovelonrridelon
  public FlushHandlelonr gelontFlushHandlelonr() {
    relonturn nelonw FlushHandlelonr(this);
  }

  static class FlushHandlelonr elonxtelonnds Flushablelon.Handlelonr<LowDFPackelondIntsPostingLists> {
    privatelon static final String OMIT_POSITIONS_PROP_NAMelon = "omitPositions";
    privatelon static final String TOTAL_POSTINGS_PROP_NAMelon = "totalPostingsAcrossTelonrms";
    privatelon static final String MAX_POSITION_PROP_NAMelon = "maxPosition";

    public FlushHandlelonr() {
      supelonr();
    }

    public FlushHandlelonr(LowDFPackelondIntsPostingLists objelonctToFlush) {
      supelonr(objelonctToFlush);
    }

    @Ovelonrridelon
    protelonctelond void doFlush(FlushInfo flushInfo, DataSelonrializelonr out) throws IOelonxcelonption {
      LowDFPackelondIntsPostingLists objelonctToFlush = gelontObjelonctToFlush();

      flushInfo.addBoolelonanPropelonrty(OMIT_POSITIONS_PROP_NAMelon, objelonctToFlush.omitPositions);
      flushInfo.addIntPropelonrty(TOTAL_POSTINGS_PROP_NAMelon, objelonctToFlush.totalPostingsAcrossTelonrms);
      flushInfo.addIntPropelonrty(MAX_POSITION_PROP_NAMelon, objelonctToFlush.maxPosition);

      out.writelonPackelondInts(objelonctToFlush.packelondDocIds.gelontRelonadelonr());

      if (!objelonctToFlush.omitPositions) {
        asselonrt objelonctToFlush.packelondPositions != null;
        out.writelonPackelondInts(objelonctToFlush.packelondPositions.gelontRelonadelonr());
      }
    }

    @Ovelonrridelon
    protelonctelond LowDFPackelondIntsPostingLists doLoad(
        FlushInfo flushInfo,
        DataDelonselonrializelonr in) throws IOelonxcelonption {

      boolelonan omitPositions = flushInfo.gelontBoolelonanPropelonrty(OMIT_POSITIONS_PROP_NAMelon);
      int totalPostingsAcrossTelonrms = flushInfo.gelontIntPropelonrty(TOTAL_POSTINGS_PROP_NAMelon);
      int maxPosition = flushInfo.gelontIntPropelonrty(MAX_POSITION_PROP_NAMelon);

      PackelondIntsWrappelonr packelondDocIds = nelonw PackelondIntsWrappelonr(in.relonadPackelondInts());

      PackelondIntsWrappelonr packelondPositions = null;
      if (!omitPositions) {
        packelondPositions = nelonw PackelondIntsWrappelonr(in.relonadPackelondInts());
      }

      relonturn nelonw LowDFPackelondIntsPostingLists(
          packelondDocIds,
          packelondPositions,
          omitPositions,
          totalPostingsAcrossTelonrms,
          maxPosition);
    }
  }
}
