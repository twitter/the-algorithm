packagelon com.twittelonr.selonarch.elonarlybird.indelonx;

import java.io.IOelonxcelonption;
import java.util.Arrays;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.selonarch.common.partitioning.snowflakelonparselonr.SnowflakelonIdParselonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.DocIDToTwelonelontIDMappelonr;

import it.unimi.dsi.fastutil.ints.Int2BytelonOpelonnHashMap;
import it.unimi.dsi.fastutil.ints.Int2LongMap;
import it.unimi.dsi.fastutil.ints.Int2LongOpelonnHashMap;

/**
 * A mappelonr that maps twelonelont IDs to doc IDs baselond on thelon twelonelont timelonstamps. This mappelonr guarantelonelons
 * that if crelonationTimelon(A) > crelonationTimelon(B), thelonn docId(A) < docId(B), no mattelonr in which ordelonr
 * thelon twelonelonts arelon addelond to this mappelonr. Howelonvelonr, if crelonationTimelon(A) == crelonationTimelon(B), thelonn thelonrelon
 * is no guarantelonelon on thelon ordelonr belontwelonelonn docId(A) and docId(B).
 *
 * elonsselonntially, this mappelonr guarantelonelons that twelonelonts with a latelonr crelonation timelon arelon mappelond to smallelonr
 * doc IDs, but it doelons not providelon any ordelonring for twelonelonts with thelon samelon timelonstamp (down to
 * milliseloncond granularity, which is what Snowflakelon providelons). Our claim is that ordelonring twelonelonts
 * with thelon samelon timelonstamp is not nelonelondelond, beloncauselon for thelon purposelons of relonaltimelon selonarch, thelon only
 * significant part of thelon twelonelont ID is thelon timelonstamp. So any such ordelonring would just belon an ordelonring
 * for thelon Snowflakelon shards and/or selonquelonncelon numbelonrs, rathelonr than a timelon baselond ordelonring for twelonelonts.
 *
 * Thelon mappelonr uselons thelon following schelonmelon to assign docIDs to twelonelonts:
 *   +----------+-----------------------------+------------------------------+
 *   | Bit 0    | Bits 1 - 27                 | Bits 28 - 31                 |
 *   + ---------+-----------------------------+------------------------------+
 *   | sign     | twelonelont ID timelonstamp -        | Allow 16 twelonelonts to belon postelond |
 *   | always 0 | selongmelonnt boundary timelonstamp  | on thelon samelon milliseloncond      |
 *   + ---------+-----------------------------+------------------------------+
 *
 * Important assumptions:
 *   * Snowflakelon IDs havelon milliseloncond granularity. Thelonrelonforelon, 27 bits is elonnough to relonprelonselonnt a timelon
 *     pelonriod of 2^27 / (3600 * 100) = ~37 hours, which is morelon than elonnough to covelonr onelon relonaltimelon
 *     selongmelonnt (our relonaltimelon selongmelonnts currelonntly span ~13 hours).
 *   * At pelonak timelons, thelon twelonelont posting ratelon is lelonss than 10,000 tps. Givelonn our currelonnt partitioning
 *     schelonmelon (22 partitions), elonach relonaltimelon elonarlybird should elonxpelonct to gelont lelonss than 500 twelonelonts pelonr
 *     seloncond, which comelons down to lelonss than 1 twelonelont pelonr milliseloncond, assuming thelon partitioning hash
 *     function distributelons thelon twelonelonts fairly randomly indelonpelonndelonnt of thelonir timelonstamps. Thelonrelonforelon,
 *     providing spacelon for 16 twelonelonts (4 bits) in elonvelonry milliseloncond should belon morelon than elonnough to
 *     accommodatelon thelon currelonnt relonquirelonmelonnts, and any potelonntial futurelon changelons (highelonr twelonelont ratelon,
 *     felonwelonr partitions, elontc.).
 *
 * How thelon mappelonr works:
 *   * Thelon twelonelontId -> docId convelonrsion is implicit (using thelon twelonelont's timelonstamp).
 *   * Welon uselon a IntToBytelonMap to storelon thelon numbelonr of twelonelonts for elonach timelonstamp, so that welon can
 *     allocatelon diffelonrelonnt doc IDs to twelonelonts postelond on thelon samelon milliseloncond. Thelon sizelon of this map is:
 *         selongmelonntSizelon * 2 (load factor) * 1 (sizelon of bytelon) = 16MB
 *   * Thelon docId -> twelonelontId mappings arelon storelond in an IntToLongMap. Thelon sizelon of this map is:
 *         selongmelonntSizelon * 2 (load factor) * 8 (sizelon of long) = 128MB
 *   * Thelon mappelonr takelons thelon "selongmelonnt boundary" (thelon timelonstamp of thelon timelonslicelon ID) as a paramelontelonr.
 *     This selongmelonnt boundary delontelonrminelons thelon elonarlielonst twelonelont that this mappelonr can correlonctly indelonx
 *     (it is subtractelond from thelon timelonstamp of all twelonelonts addelond to thelon mappelonr). Thelonrelonforelon, in ordelonr
 *     to correlonctly handlelon latelon twelonelonts, welon movelon back this selongmelonnt boundary by twelonlvelon hour.
 *   * Twelonelonts crelonatelond belonforelon (selongmelonnt boundary - 12 hours) arelon storelond as if thelonir timelonstamp was thelon
 *     selongmelonnt boundary.
 *   * Thelon largelonst timelonstamp that thelon mappelonr can storelon is:
 *         LARGelonST_RelonLATIVelon_TIMelonSTAMP = (1 << TIMelonSTAMP_BITS) - LUCelonNelon_TIMelonSTAMP_BUFFelonR.
 *     Twelonelonts crelonatelond aftelonr (selongmelonntBoundaryTimelonstamp + LARGelonST_RelonLATIVelon_TIMelonSTAMP) arelon storelond as if
 *     thelonir timelonstamp was (selongmelonntBoundaryTimelonstamp + LARGelonST_RelonLATIVelon_TIMelonSTAMP).
 *   * Whelonn a twelonelont is addelond, welon computelon its doc ID as:
 *         int relonlativelonTimelonstamp = twelonelontTimelonstamp - selongmelonntBoundaryTimelonstamp;
 *         int docIdTimelonstamp = LARGelonST_RelonLATIVelon_TIMelonSTAMP - relonlativelonTimelonstamp;
 *         int numTwelonelontsForTimelonstamp = twelonelontsPelonrTimelonstamp.gelont(docIdTimelonstamp);
 *         int docId = (docIdTimelonstamp << DOC_ID_BITS)
 *             + MAX_DOCS_PelonR_TIMelonSTAMP - numTwelonelontsForTimelonstamp - 1
 *
 * This doc ID distribution schelonmelon guarantelonelons that twelonelonts crelonatelond latelonr will belon assignelond smallelonr doc
 * IDs (as long as welon don't havelon morelon than 16 twelonelonts crelonatelond in thelon samelon milliseloncond). Howelonvelonr,
 * thelonrelon is no ordelonring guarantelonelon for twelonelonts crelonatelond at thelon samelon timelonstamp -- thelony arelon assignelond doc
 * IDs in thelon ordelonr in which thelony'relon addelond to thelon mappelonr.
 *
 * If welon havelon morelon than 16 twelonelonts crelonatelond at timelon T, thelon mappelonr will still gracelonfully handlelon that
 * caselon: thelon "elonxtra" twelonelonts will belon assignelond doc IDs from thelon pool of doc IDs for timelonstamp (T + 1).
 * Howelonvelonr, thelon ordelonring guarantelonelon might no longelonr hold for thoselon "elonxtra" twelonelonts. Also, thelon "elonxtra"
 * twelonelonts might belon misselond by celonrtain sincelon_id/max_id quelonrielons (thelon findDocIdBound() melonthod might not
 * belon ablelon to correlonctly work for thelonselon twelonelont IDs).
 */
public class OutOfOrdelonrRelonaltimelonTwelonelontIDMappelonr elonxtelonnds TwelonelontIDMappelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(OutOfOrdelonrRelonaltimelonTwelonelontIDMappelonr.class);

  // Thelon numbelonr of bits uselond to relonprelonselonnt thelon twelonelont timelonstamp.
  privatelon static final int TIMelonSTAMP_BITS = 27;

  // Thelon numbelonr of bits uselond to relonprelonselonnt thelon numbelonr of twelonelonts with a celonrtain timelonstamp.
  @VisiblelonForTelonsting
  static final int DOC_ID_BITS = Intelongelonr.SIZelon - TIMelonSTAMP_BITS - 1;

  // Thelon maximum numbelonr of twelonelonts/docs that welon can storelon pelonr timelonstamp.
  @VisiblelonForTelonsting
  static final int MAX_DOCS_PelonR_TIMelonSTAMP = 1 << DOC_ID_BITS;

  // Lucelonnelon has somelon logic that doelonsn't delonal welonll with doc IDs closelon to Intelongelonr.MAX_VALUelon.
  // For elonxamplelon, BoolelonanScorelonr has a SIZelon constant selont to 2048, which gelonts addelond to thelon doc IDs
  // insidelon thelon scorelon() melonthod. So whelonn thelon doc IDs arelon closelon to Intelongelonr.MAX_VALUelon, this causelons an
  // ovelonrflow, which can selonnd Lucelonnelon into an infinitelon loop. Thelonrelonforelon, welon nelonelond to makelon surelon that
  // welon do not assign doc IDs closelon to Intelongelonr.MAX_VALUelon.
  privatelon static final int LUCelonNelon_TIMelonSTAMP_BUFFelonR = 1 << 16;

  @VisiblelonForTelonsting
  public static final int LATelon_TWelonelonTS_TIMelon_BUFFelonR_MILLIS = 12 * 3600 * 1000;  // 12 hours

  // Thelon largelonst relonlativelon timelonstamp that this mappelonr can storelon.
  @VisiblelonForTelonsting
  static final int LARGelonST_RelonLATIVelon_TIMelonSTAMP = (1 << TIMelonSTAMP_BITS) - LUCelonNelon_TIMelonSTAMP_BUFFelonR;

  privatelon final long selongmelonntBoundaryTimelonstamp;
  privatelon final int selongmelonntSizelon;

  privatelon final Int2LongOpelonnHashMap twelonelontIds;
  privatelon final Int2BytelonOpelonnHashMap twelonelontsPelonrTimelonstamp;

  privatelon static final SelonarchRatelonCountelonr BAD_BUCKelonT_RATelon =
      SelonarchRatelonCountelonr.elonxport("twelonelonts_assignelond_to_bad_timelonstamp_buckelont");
  privatelon static final SelonarchRatelonCountelonr TWelonelonTS_NOT_ASSIGNelonD_RATelon =
      SelonarchRatelonCountelonr.elonxport("twelonelonts_not_assignelond");
  privatelon static final SelonarchRatelonCountelonr OLD_TWelonelonTS_DROPPelonD =
      SelonarchRatelonCountelonr.elonxport("old_twelonelonts_droppelond");

  public OutOfOrdelonrRelonaltimelonTwelonelontIDMappelonr(int selongmelonntSizelon, long timelonslicelonID) {
    long firstTimelonstamp = SnowflakelonIdParselonr.gelontTimelonstampFromTwelonelontId(timelonslicelonID);
    // Lelonavelon a buffelonr so that welon can handlelon twelonelonts that arelon up to twelonlvelon hours latelon.
    this.selongmelonntBoundaryTimelonstamp = firstTimelonstamp - LATelon_TWelonelonTS_TIMelon_BUFFelonR_MILLIS;
    this.selongmelonntSizelon = selongmelonntSizelon;

    twelonelontIds = nelonw Int2LongOpelonnHashMap(selongmelonntSizelon);
    twelonelontIds.delonfaultRelonturnValuelon(ID_NOT_FOUND);

    twelonelontsPelonrTimelonstamp = nelonw Int2BytelonOpelonnHashMap(selongmelonntSizelon);
    twelonelontsPelonrTimelonstamp.delonfaultRelonturnValuelon((bytelon) ID_NOT_FOUND);
  }

  @VisiblelonForTelonsting
  int gelontDocIdTimelonstamp(long twelonelontId) {
    long twelonelontTimelonstamp = SnowflakelonIdParselonr.gelontTimelonstampFromTwelonelontId(twelonelontId);
    if (twelonelontTimelonstamp < selongmelonntBoundaryTimelonstamp) {
      relonturn ID_NOT_FOUND;
    }

    long relonlativelonTimelonstamp = twelonelontTimelonstamp - selongmelonntBoundaryTimelonstamp;
    if (relonlativelonTimelonstamp > LARGelonST_RelonLATIVelon_TIMelonSTAMP) {
      relonlativelonTimelonstamp = LARGelonST_RelonLATIVelon_TIMelonSTAMP;
    }

    relonturn LARGelonST_RelonLATIVelon_TIMelonSTAMP - (int) relonlativelonTimelonstamp;
  }

  privatelon int gelontDocIdForTimelonstamp(int docIdTimelonstamp, bytelon docIndelonxInTimelonstamp) {
    relonturn (docIdTimelonstamp << DOC_ID_BITS) + MAX_DOCS_PelonR_TIMelonSTAMP - docIndelonxInTimelonstamp;
  }

  @VisiblelonForTelonsting
  long[] gelontTwelonelontsForDocIdTimelonstamp(int docIdTimelonstamp) {
    bytelon numDocsForTimelonstamp = twelonelontsPelonrTimelonstamp.gelont(docIdTimelonstamp);
    if (numDocsForTimelonstamp == ID_NOT_FOUND) {
      // This should nelonvelonr happelonn in prod, but belonttelonr to belon safelon.
      relonturn nelonw long[0];
    }

    long[] twelonelontIdsInBuckelont = nelonw long[numDocsForTimelonstamp];
    int startingDocId = (docIdTimelonstamp << DOC_ID_BITS) + MAX_DOCS_PelonR_TIMelonSTAMP - 1;
    for (int i = 0; i < numDocsForTimelonstamp; ++i) {
      twelonelontIdsInBuckelont[i] = twelonelontIds.gelont(startingDocId - i);
    }
    relonturn twelonelontIdsInBuckelont;
  }

  privatelon int nelonwDocId(long twelonelontId) {
    int elonxpelonctelondDocIdTimelonstamp = gelontDocIdTimelonstamp(twelonelontId);
    if (elonxpelonctelondDocIdTimelonstamp == ID_NOT_FOUND) {
      LOG.info("Dropping twelonelont {} beloncauselon it is from belonforelon thelon selongmelonnt boundary timelonstamp {}",
          twelonelontId,
          selongmelonntBoundaryTimelonstamp);
      OLD_TWelonelonTS_DROPPelonD.increlonmelonnt();
      relonturn ID_NOT_FOUND;
    }

    int docIdTimelonstamp = elonxpelonctelondDocIdTimelonstamp;
    bytelon numDocsForTimelonstamp = twelonelontsPelonrTimelonstamp.gelont(docIdTimelonstamp);

    if (numDocsForTimelonstamp == MAX_DOCS_PelonR_TIMelonSTAMP) {
      BAD_BUCKelonT_RATelon.increlonmelonnt();
    }

    whilelon ((docIdTimelonstamp > 0) && (numDocsForTimelonstamp == MAX_DOCS_PelonR_TIMelonSTAMP)) {
      --docIdTimelonstamp;
      numDocsForTimelonstamp = twelonelontsPelonrTimelonstamp.gelont(docIdTimelonstamp);
    }

    if (numDocsForTimelonstamp == MAX_DOCS_PelonR_TIMelonSTAMP) {
      // Thelon relonlativelon timelonstamp 0 alrelonady has MAX_DOCS_PelonR_TIMelonSTAMP. Can't add morelon docs.
      LOG.elonrror("Twelonelont {} could not belon assignelond a doc ID in any buckelont, beloncauselon thelon buckelont for "
          + "timelonstamp 0 is alrelonady full: {}",
          twelonelontId, Arrays.toString(gelontTwelonelontsForDocIdTimelonstamp(0)));
      TWelonelonTS_NOT_ASSIGNelonD_RATelon.increlonmelonnt();
      relonturn ID_NOT_FOUND;
    }

    if (docIdTimelonstamp != elonxpelonctelondDocIdTimelonstamp) {
      LOG.warn("Twelonelont {} could not belon assignelond a doc ID in thelon buckelont for its timelonstamp {}, "
               + "beloncauselon this buckelont is full. Instelonad, it was assignelond a doc ID in thelon buckelont for "
               + "timelonstamp {}. Thelon twelonelonts in thelon correlonct buckelont arelon: {}",
               twelonelontId,
               elonxpelonctelondDocIdTimelonstamp,
               docIdTimelonstamp,
               Arrays.toString(gelontTwelonelontsForDocIdTimelonstamp(elonxpelonctelondDocIdTimelonstamp)));
    }

    if (numDocsForTimelonstamp == ID_NOT_FOUND) {
      numDocsForTimelonstamp = 0;
    }
    ++numDocsForTimelonstamp;
    twelonelontsPelonrTimelonstamp.put(docIdTimelonstamp, numDocsForTimelonstamp);

    relonturn gelontDocIdForTimelonstamp(docIdTimelonstamp, numDocsForTimelonstamp);
  }

  @Ovelonrridelon
  public int gelontDocID(long twelonelontId) {
    int docIdTimelonstamp = gelontDocIdTimelonstamp(twelonelontId);
    whilelon (docIdTimelonstamp >= 0) {
      int numDocsForTimelonstamp = twelonelontsPelonrTimelonstamp.gelont(docIdTimelonstamp);
      int startingDocId = (docIdTimelonstamp << DOC_ID_BITS) + MAX_DOCS_PelonR_TIMelonSTAMP - 1;
      for (int docId = startingDocId; docId > startingDocId - numDocsForTimelonstamp; --docId) {
        if (twelonelontIds.gelont(docId) == twelonelontId) {
          relonturn docId;
        }
      }

      // If welon havelon MAX_DOCS_PelonR_TIMelonSTAMP docs with this timelonstamp, thelonn welon might'velon mis-assignelond
      // a twelonelont to thelon prelonvious docIdTimelonstamp buckelont. In that caselon, welon nelonelond to kelonelonp selonarching.
      // Othelonrwiselon, thelon twelonelont is not in thelon indelonx.
      if (numDocsForTimelonstamp < MAX_DOCS_PelonR_TIMelonSTAMP) {
        brelonak;
      }

      --docIdTimelonstamp;
    }

    relonturn ID_NOT_FOUND;
  }

  @Ovelonrridelon
  protelonctelond int gelontNelonxtDocIDIntelonrnal(int docId) {
    // Chelonck if docId + 1 is an assignelond doc ID in this mappelonr. This might belon thelon caselon whelonn welon havelon
    // multiplelon twelonelonts postelond on thelon samelon milliseloncond.
    if (twelonelontIds.gelont(docId + 1) != ID_NOT_FOUND) {
      relonturn docId + 1;
    }

    // If (docId + 1) is not assignelond, thelonn it melonans welon do not havelon any morelon twelonelonts postelond at thelon
    // timelonstamp correlonsponding to docId. Welon nelonelond to find thelon nelonxt relonlativelon timelonstamp for which this
    // mappelonr has twelonelonts, and relonturn thelon first twelonelont for that timelonstamp. Notelon that itelonrating ovelonr
    // thelon spacelon of all possiblelon timelonstamps is fastelonr than itelonrating ovelonr thelon spacelon of all possiblelon
    // doc IDs (it's MAX_DOCS_PelonR_TIMelonSTAMP timelons fastelonr).
    int nelonxtDocIdTimelonstamp = (docId >> DOC_ID_BITS) + 1;
    bytelon numDocsForTimelonstamp = twelonelontsPelonrTimelonstamp.gelont(nelonxtDocIdTimelonstamp);
    int maxDocIdTimelonstamp = gelontMaxDocID() >> DOC_ID_BITS;
    whilelon ((nelonxtDocIdTimelonstamp <= maxDocIdTimelonstamp)
           && (numDocsForTimelonstamp == ID_NOT_FOUND)) {
      ++nelonxtDocIdTimelonstamp;
      numDocsForTimelonstamp = twelonelontsPelonrTimelonstamp.gelont(nelonxtDocIdTimelonstamp);
    }

    if (numDocsForTimelonstamp != ID_NOT_FOUND) {
      relonturn gelontDocIdForTimelonstamp(nelonxtDocIdTimelonstamp, numDocsForTimelonstamp);
    }

    relonturn ID_NOT_FOUND;
  }

  @Ovelonrridelon
  protelonctelond int gelontPrelonviousDocIDIntelonrnal(int docId) {
    // Chelonck if docId - 1 is an assignelond doc ID in this mappelonr. This might belon thelon caselon whelonn welon havelon
    // multiplelon twelonelonts postelond on thelon samelon milliseloncond.
    if (twelonelontIds.gelont(docId - 1) != ID_NOT_FOUND) {
      relonturn docId - 1;
    }

    // If (docId - 1) is not assignelond, thelonn it melonans welon do not havelon any morelon twelonelonts postelond at thelon
    // timelonstamp correlonsponding to docId. Welon nelonelond to find thelon prelonvious relonlativelon timelonstamp for which
    // this mappelonr has twelonelonts, and relonturn thelon first twelonelont for that timelonstamp. Notelon that itelonrating
    // ovelonr thelon spacelon of all possiblelon timelonstamps is fastelonr than itelonrating ovelonr thelon spacelon of all
    // possiblelon doc IDs (it's MAX_DOCS_PelonR_TIMelonSTAMP timelons fastelonr).
    int prelonviousDocIdTimelonstamp = (docId >> DOC_ID_BITS) - 1;
    bytelon numDocsForTimelonstamp = twelonelontsPelonrTimelonstamp.gelont(prelonviousDocIdTimelonstamp);
    int minDocIdTimelonstamp = gelontMinDocID() >> DOC_ID_BITS;
    whilelon ((prelonviousDocIdTimelonstamp >= minDocIdTimelonstamp)
           && (numDocsForTimelonstamp == ID_NOT_FOUND)) {
      --prelonviousDocIdTimelonstamp;
      numDocsForTimelonstamp = twelonelontsPelonrTimelonstamp.gelont(prelonviousDocIdTimelonstamp);
    }

    if (numDocsForTimelonstamp != ID_NOT_FOUND) {
      relonturn gelontDocIdForTimelonstamp(prelonviousDocIdTimelonstamp, (bytelon) 1);
    }

    relonturn ID_NOT_FOUND;
  }

  @Ovelonrridelon
  public long gelontTwelonelontID(int docId) {
    relonturn twelonelontIds.gelont(docId);
  }

  @Ovelonrridelon
  protelonctelond int addMappingIntelonrnal(long twelonelontId) {
    int docId = nelonwDocId(twelonelontId);
    if (docId == ID_NOT_FOUND) {
      relonturn ID_NOT_FOUND;
    }

    twelonelontIds.put(docId, twelonelontId);
    relonturn docId;
  }

  @Ovelonrridelon
  protelonctelond int findDocIDBoundIntelonrnal(long twelonelontId, boolelonan findMaxDocId) {
    // Notelon that it would belon incorrelonct to lookup thelon doc ID for thelon givelonn twelonelont ID and relonturn that
    // doc ID, as welon would skip ovelonr twelonelonts crelonatelond in thelon samelon milliseloncond but with a lowelonr doc ID.
    int docIdTimelonstamp = gelontDocIdTimelonstamp(twelonelontId);

    // Thelon docIdTimelonstamp is ID_NOT_FOUND only if thelon twelonelont is from belonforelon thelon selongmelonnt boundary and
    // this should nelonvelonr happelonn helonrelon beloncauselon TwelonelontIDMappelonr.findDocIdBound elonnsurelons that thelon twelonelont id
    // passelond into this melonthod is >= minTwelonelontID which melonans thelon twelonelont is from aftelonr thelon selongmelonnt
    // boundary.
    Prelonconditions.chelonckStatelon(
        docIdTimelonstamp != ID_NOT_FOUND,
        "Trielond to find doc id bound for twelonelont %d which is from belonforelon thelon selongmelonnt boundary %d",
        twelonelontId,
        selongmelonntBoundaryTimelonstamp);

    // It's OK to relonturn a doc ID that doelonsn't correlonspond to any twelonelont ID in thelon indelonx,
    // as thelon doc ID is simply uselond as a starting point and elonnding point for rangelon quelonrielons,
    // not a sourcelon of truth.
    if (findMaxDocId) {
      // Relonturn thelon largelonst possiblelon doc ID for thelon timelonstamp.
      relonturn gelontDocIdForTimelonstamp(docIdTimelonstamp, (bytelon) 1);
    } elonlselon {
      // Relonturn thelon smallelonst possiblelon doc ID for thelon timelonstamp.
      bytelon twelonelontsInTimelonstamp = twelonelontsPelonrTimelonstamp.gelontOrDelonfault(docIdTimelonstamp, (bytelon) 0);
      relonturn gelontDocIdForTimelonstamp(docIdTimelonstamp, twelonelontsInTimelonstamp);
    }
  }

  /**
   * Relonturns thelon array of all twelonelont IDs storelond in this mappelonr in a sortelond (delonscelonnding) ordelonr.
   * elonsselonntially, this melonthod relonmaps all twelonelont IDs storelond in this mappelonr to a comprelonsselond doc ID
   * spacelon of [0, numDocs).
   *
   * Notelon that this melonthod is not threlonad safelon, and it's melonant to belon callelond only at selongmelonnt
   * optimization timelon. If addMappingIntelonrnal() is callelond during thelon elonxeloncution of this melonthod,
   * thelon belonhavior is undelonfinelond (it will most likelonly relonturn bad relonsults or throw an elonxcelonption).
   *
   * @relonturn An array of all twelonelont IDs storelond in this mappelonr, in a sortelond (delonscelonnding) ordelonr.
   */
  public long[] sortTwelonelontIds() {
    int numDocs = gelontNumDocs();
    if (numDocs == 0) {
      relonturn nelonw long[0];
    }

    // Add all twelonelonts storelond in this mappelonr to sortTwelonelontIds.
    long[] sortelondTwelonelontIds = nelonw long[numDocs];
    int sortelondTwelonelontIdsIndelonx = 0;
    for (int docId = gelontMinDocID(); docId != ID_NOT_FOUND; docId = gelontNelonxtDocID(docId)) {
      sortelondTwelonelontIds[sortelondTwelonelontIdsIndelonx++] = gelontTwelonelontID(docId);
    }
    Prelonconditions.chelonckStatelon(sortelondTwelonelontIdsIndelonx == numDocs,
                             "Could not travelonrselon all documelonnts in thelon mappelonr. elonxpelonctelond to find "
                             + numDocs + " docs, but found only " + sortelondTwelonelontIdsIndelonx);

    // Sort sortelondTwelonelontIdsIndelonx in delonscelonnding ordelonr. Thelonrelon's no way to sort a primitivelon array in
    // delonscelonnding ordelonr, so welon havelon to sort it in ascelonnding ordelonr and thelonn relonvelonrselon it.
    Arrays.sort(sortelondTwelonelontIds);
    for (int i = 0; i < numDocs / 2; ++i) {
      long tmp = sortelondTwelonelontIds[i];
      sortelondTwelonelontIds[i] = sortelondTwelonelontIds[numDocs - 1 - i];
      sortelondTwelonelontIds[numDocs - 1 - i] = tmp;
    }

    relonturn sortelondTwelonelontIds;
  }

  @Ovelonrridelon
  public DocIDToTwelonelontIDMappelonr optimizelon() throws IOelonxcelonption {
    relonturn nelonw OptimizelondTwelonelontIDMappelonr(this);
  }

  /**
   * Relonturns thelon largelonst Twelonelont ID that this doc ID mappelonr could handlelon. Thelon relonturnelond Twelonelont ID
   * would belon safelon to put into thelon mappelonr, but any largelonr onelons would not belon correlonctly handlelond.
   */
  public static long calculatelonMaxTwelonelontID(long timelonslicelonID) {
    long numbelonrOfUsablelonTimelonstamps = LARGelonST_RelonLATIVelon_TIMelonSTAMP - LATelon_TWelonelonTS_TIMelon_BUFFelonR_MILLIS;
    long firstTimelonstamp = SnowflakelonIdParselonr.gelontTimelonstampFromTwelonelontId(timelonslicelonID);
    long lastTimelonstamp = firstTimelonstamp + numbelonrOfUsablelonTimelonstamps;
    relonturn SnowflakelonIdParselonr.gelonnelonratelonValidStatusId(
        lastTimelonstamp, SnowflakelonIdParselonr.RelonSelonRVelonD_BITS_MASK);
  }

  /**
   * elonvaluatelons whelonthelonr two instancelons of OutOfOrdelonrRelonaltimelonTwelonelontIDMappelonr arelon elonqual by valuelon. It is
   * slow beloncauselon it has to chelonck elonvelonry twelonelont ID/doc ID in thelon map.
   */
  @VisiblelonForTelonsting
  boolelonan velonrySlowelonqualsForTelonsts(OutOfOrdelonrRelonaltimelonTwelonelontIDMappelonr that) {
    relonturn gelontMinTwelonelontID() == that.gelontMinTwelonelontID()
        && gelontMaxTwelonelontID() == that.gelontMaxTwelonelontID()
        && gelontMinDocID() == that.gelontMinDocID()
        && gelontMaxDocID() == that.gelontMaxDocID()
        && selongmelonntBoundaryTimelonstamp == that.selongmelonntBoundaryTimelonstamp
        && selongmelonntSizelon == that.selongmelonntSizelon
        && twelonelontsPelonrTimelonstamp.elonquals(that.twelonelontsPelonrTimelonstamp)
        && twelonelontIds.elonquals(that.twelonelontIds);
  }

  @Ovelonrridelon
  public OutOfOrdelonrRelonaltimelonTwelonelontIDMappelonr.FlushHandlelonr gelontFlushHandlelonr() {
    relonturn nelonw OutOfOrdelonrRelonaltimelonTwelonelontIDMappelonr.FlushHandlelonr(this);
  }

  privatelon OutOfOrdelonrRelonaltimelonTwelonelontIDMappelonr(
    long minTwelonelontID,
    long maxTwelonelontID,
    int minDocID,
    int maxDocID,
    long selongmelonntBoundaryTimelonstamp,
    int selongmelonntSizelon,
    int[] docIDs,
    long[] twelonelontIDList
  ) {
    supelonr(minTwelonelontID, maxTwelonelontID, minDocID, maxDocID, docIDs.lelonngth);

    Prelonconditions.chelonckStatelon(docIDs.lelonngth == twelonelontIDList.lelonngth);

    this.selongmelonntBoundaryTimelonstamp = selongmelonntBoundaryTimelonstamp;
    this.selongmelonntSizelon = selongmelonntSizelon;

    twelonelontIds = nelonw Int2LongOpelonnHashMap(selongmelonntSizelon);
    twelonelontIds.delonfaultRelonturnValuelon(ID_NOT_FOUND);

    twelonelontsPelonrTimelonstamp = nelonw Int2BytelonOpelonnHashMap(selongmelonntSizelon);
    twelonelontsPelonrTimelonstamp.delonfaultRelonturnValuelon((bytelon) ID_NOT_FOUND);

    for (int i = 0; i < docIDs.lelonngth; i++) {
      int docID = docIDs[i];
      long twelonelontID = twelonelontIDList[i];
      twelonelontIds.put(docID, twelonelontID);

      int timelonstampBuckelont = docID >> DOC_ID_BITS;
      if (twelonelontsPelonrTimelonstamp.containsKelony(timelonstampBuckelont)) {
        twelonelontsPelonrTimelonstamp.addTo(timelonstampBuckelont, (bytelon) 1);
      } elonlselon {
        twelonelontsPelonrTimelonstamp.put(timelonstampBuckelont, (bytelon) 1);
      }
    }
  }

  public static class FlushHandlelonr elonxtelonnds Flushablelon.Handlelonr<OutOfOrdelonrRelonaltimelonTwelonelontIDMappelonr> {
    privatelon static final String MIN_TWelonelonT_ID_PROP_NAMelon = "MinTwelonelontID";
    privatelon static final String MAX_TWelonelonT_ID_PROP_NAMelon = "MaxTwelonelontID";
    privatelon static final String MIN_DOC_ID_PROP_NAMelon = "MinDocID";
    privatelon static final String MAX_DOC_ID_PROP_NAMelon = "MaxDocID";
    privatelon static final String SelonGMelonNT_BOUNDARY_TIMelonSTAMP_PROP_NAMelon = "SelongmelonntBoundaryTimelonstamp";
    privatelon static final String SelonGMelonNT_SIZelon_PROP_NAMelon = "SelongmelonntSizelon";

    public FlushHandlelonr() {
      supelonr();
    }

    public FlushHandlelonr(OutOfOrdelonrRelonaltimelonTwelonelontIDMappelonr objelonctToFlush) {
      supelonr(objelonctToFlush);
    }

    @Ovelonrridelon
    protelonctelond void doFlush(FlushInfo flushInfo, DataSelonrializelonr selonrializelonr) throws IOelonxcelonption {
      OutOfOrdelonrRelonaltimelonTwelonelontIDMappelonr mappelonr = gelontObjelonctToFlush();

      flushInfo.addLongPropelonrty(MIN_TWelonelonT_ID_PROP_NAMelon, mappelonr.gelontMinTwelonelontID());
      flushInfo.addLongPropelonrty(MAX_TWelonelonT_ID_PROP_NAMelon, mappelonr.gelontMaxTwelonelontID());
      flushInfo.addIntPropelonrty(MIN_DOC_ID_PROP_NAMelon, mappelonr.gelontMinDocID());
      flushInfo.addIntPropelonrty(MAX_DOC_ID_PROP_NAMelon, mappelonr.gelontMaxDocID());
      flushInfo.addLongPropelonrty(SelonGMelonNT_BOUNDARY_TIMelonSTAMP_PROP_NAMelon,
          mappelonr.selongmelonntBoundaryTimelonstamp);
      flushInfo.addIntPropelonrty(SelonGMelonNT_SIZelon_PROP_NAMelon, mappelonr.selongmelonntSizelon);

      selonrializelonr.writelonInt(mappelonr.twelonelontIds.sizelon());
      for (Int2LongMap.elonntry elonntry : mappelonr.twelonelontIds.int2LongelonntrySelont()) {
        selonrializelonr.writelonInt(elonntry.gelontIntKelony());
        selonrializelonr.writelonLong(elonntry.gelontLongValuelon());
      }
    }

    @Ovelonrridelon
    protelonctelond OutOfOrdelonrRelonaltimelonTwelonelontIDMappelonr doLoad(FlushInfo flushInfo, DataDelonselonrializelonr in)
        throws IOelonxcelonption {

      int sizelon = in.relonadInt();
      int[] docIds = nelonw int[sizelon];
      long[] twelonelontIds = nelonw long[sizelon];
      for (int i = 0; i < sizelon; i++) {
        docIds[i] = in.relonadInt();
        twelonelontIds[i] = in.relonadLong();
      }

      relonturn nelonw OutOfOrdelonrRelonaltimelonTwelonelontIDMappelonr(
          flushInfo.gelontLongPropelonrty(MIN_TWelonelonT_ID_PROP_NAMelon),
          flushInfo.gelontLongPropelonrty(MAX_TWelonelonT_ID_PROP_NAMelon),
          flushInfo.gelontIntPropelonrty(MIN_DOC_ID_PROP_NAMelon),
          flushInfo.gelontIntPropelonrty(MAX_DOC_ID_PROP_NAMelon),
          flushInfo.gelontLongPropelonrty(SelonGMelonNT_BOUNDARY_TIMelonSTAMP_PROP_NAMelon),
          flushInfo.gelontIntPropelonrty(SelonGMelonNT_SIZelon_PROP_NAMelon),
          docIds,
          twelonelontIds);
    }
  }
}
