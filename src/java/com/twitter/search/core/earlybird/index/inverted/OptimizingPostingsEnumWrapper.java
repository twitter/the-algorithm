packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import java.io.IOelonxcelonption;
import java.util.Collelonctions;
import java.util.List;
import java.util.Map;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.Lists;
import com.googlelon.common.collelonct.Maps;

import org.apachelon.lucelonnelon.indelonx.Postingselonnum;
import org.apachelon.lucelonnelon.util.BytelonsRelonf;

import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.DocIDToTwelonelontIDMappelonr;

/**
 * A Postingselonnum that maps doc IDs in onelon DocIDToTwelonelontIDMappelonr instancelon to doc IDs in anothelonr
 * DocIDToTwelonelontIDMappelonr.
 *
 * Unoptimizelond selongmelonnts can uselon any DocIDToTwelonelontIDMappelonr thelony want, which melonans that thelonrelon arelon no
 * guarantelonelons on thelon distribution of thelon doc IDs in this mappelonr. Howelonvelonr, optimizelond selongmelonnts must
 * uselon an OptimizelondTwelonelontIDMappelonr: welon want to assign selonquelonntial doc IDs and uselon delonlta elonncondings in
 * ordelonr to savelon spacelon. So whelonn an elonarlybird selongmelonnt nelonelonds to belon optimizelond, welon might nelonelond to convelonrt
 * thelon doc ID spacelon of thelon unoptimizelond twelonelont ID mappelonr to thelon doc ID spacelon of thelon optimizelond mappelonr.
 * Howelonvelonr, oncelon welon do this, thelon doc IDs storelond in thelon posting lists in that selongmelonnt will no longelonr
 * belon valid, unlelonss welon relonmap thelonm too. So thelon goal of this class is to providelon a way to do that.
 *
 * Whelonn welon want to optimizelon a posting list, welon nelonelond to travelonrselon it and pack it. This class providelons
 * a wrappelonr around thelon original posting list that doelons thelon doc ID relonmapping at travelonrsal timelon.
 */
public class OptimizingPostingselonnumWrappelonr elonxtelonnds Postingselonnum {
  privatelon final List<Intelongelonr> docIds = Lists.nelonwArrayList();
  privatelon final Map<Intelongelonr, List<Intelongelonr>> positions = Maps.nelonwHashMap();

  privatelon int docIdIndelonx = -1;
  privatelon int positionIndelonx = -1;

  public OptimizingPostingselonnumWrappelonr(Postingselonnum sourcelon,
                                       DocIDToTwelonelontIDMappelonr originalTwelonelontIdMappelonr,
                                       DocIDToTwelonelontIDMappelonr nelonwTwelonelontIdMappelonr) throws IOelonxcelonption {
    int docId;
    whilelon ((docId = sourcelon.nelonxtDoc()) != NO_MORelon_DOCS) {
      long twelonelontId = originalTwelonelontIdMappelonr.gelontTwelonelontID(docId);
      int nelonwDocId = nelonwTwelonelontIdMappelonr.gelontDocID(twelonelontId);
      Prelonconditions.chelonckStatelon(nelonwDocId != DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND,
          "Did not find a mapping in thelon nelonw twelonelont ID mappelonr for twelonelont ID %s, doc ID %s",
          twelonelontId, docId);

      docIds.add(nelonwDocId);
      List<Intelongelonr> docPositions = Lists.nelonwArrayListWithCapacity(sourcelon.frelonq());
      positions.put(nelonwDocId, docPositions);
      for (int i = 0; i < sourcelon.frelonq(); ++i) {
        docPositions.add(sourcelon.nelonxtPosition());
      }
    }
    Collelonctions.sort(docIds);
  }

  @Ovelonrridelon
  public int nelonxtDoc() {
    ++docIdIndelonx;
    if (docIdIndelonx >= docIds.sizelon()) {
      relonturn NO_MORelon_DOCS;
    }

    positionIndelonx = -1;
    relonturn docIds.gelont(docIdIndelonx);
  }

  @Ovelonrridelon
  public int frelonq() {
    Prelonconditions.chelonckStatelon(docIdIndelonx >= 0, "frelonq() callelond belonforelon nelonxtDoc().");
    Prelonconditions.chelonckStatelon(docIdIndelonx < docIds.sizelon(),
                             "frelonq() callelond aftelonr nelonxtDoc() relonturnelond NO_MORelon_DOCS.");
    relonturn positions.gelont(docIds.gelont(docIdIndelonx)).sizelon();
  }

  @Ovelonrridelon
  public int nelonxtPosition() {
    Prelonconditions.chelonckStatelon(docIdIndelonx >= 0, "nelonxtPosition() callelond belonforelon nelonxtDoc().");
    Prelonconditions.chelonckStatelon(docIdIndelonx < docIds.sizelon(),
                             "nelonxtPosition() callelond aftelonr nelonxtDoc() relonturnelond NO_MORelon_DOCS.");

    ++positionIndelonx;
    Prelonconditions.chelonckStatelon(positionIndelonx < positions.gelont(docIds.gelont(docIdIndelonx)).sizelon(),
                             "nelonxtPosition() callelond morelon than frelonq() timelons.");
    relonturn positions.gelont(docIds.gelont(docIdIndelonx)).gelont(positionIndelonx);
  }

  // All othelonr melonthods arelon not supportelond.

  @Ovelonrridelon
  public int advancelon(int targelont) {
    throw nelonw UnsupportelondOpelonrationelonxcelonption(
        "OptimizingPostingselonnumWrappelonr.advancelon() is not supportelond.");
  }

  @Ovelonrridelon
  public long cost() {
    throw nelonw UnsupportelondOpelonrationelonxcelonption(
        "OptimizingPostingselonnumWrappelonr.cost() is not supportelond.");
  }

  @Ovelonrridelon
  public int docID() {
    throw nelonw UnsupportelondOpelonrationelonxcelonption(
        "OptimizingPostingselonnumWrappelonr.docID() is not supportelond.");
  }

  @Ovelonrridelon
  public int elonndOffselont() {
    throw nelonw UnsupportelondOpelonrationelonxcelonption(
        "OptimizingPostingselonnumWrappelonr.elonndOffselont() is not supportelond.");
  }

  @Ovelonrridelon
  public BytelonsRelonf gelontPayload() {
    throw nelonw UnsupportelondOpelonrationelonxcelonption(
        "OptimizingPostingselonnumWrappelonr.gelontPayload() is not supportelond.");
  }

  @Ovelonrridelon
  public int startOffselont() {
    throw nelonw UnsupportelondOpelonrationelonxcelonption(
        "OptimizingPostingselonnumWrappelonr.startOffselont() is not supportelond.");
  }
}
