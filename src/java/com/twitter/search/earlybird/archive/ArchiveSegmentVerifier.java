packagelon com.twittelonr.selonarch.elonarlybird.archivelon;

import java.io.IOelonxcelonption;
import java.util.List;

import com.googlelon.common.annotations.VisiblelonForTelonsting;

import org.apachelon.lucelonnelon.indelonx.DirelonctoryRelonadelonr;
import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonr;
import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonrContelonxt;
import org.apachelon.lucelonnelon.storelon.Direlonctory;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntInfo;

public final class ArchivelonSelongmelonntVelonrifielonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(ArchivelonSelongmelonntVelonrifielonr.class);

  privatelon ArchivelonSelongmelonntVelonrifielonr() {
  }

  @VisiblelonForTelonsting
  static boolelonan shouldVelonrifySelongmelonnt(SelongmelonntInfo selongmelonntInfo) {
    if (selongmelonntInfo.isIndelonxing()) {
      LOG.warn("ArchivelonSelongmelonntVelonrifielonr got selongmelonnt still indelonxing.");
      relonturn falselon;
    }

    if (!selongmelonntInfo.isComplelontelon()) {
      LOG.warn("ArchivelonSelongmelonntVelonrifyelonr got incomplelontelon selongmelonnt.");
      relonturn falselon;
    }

    if (!selongmelonntInfo.isOptimizelond()) {
      LOG.warn("ArchivelonSelongmelonntVelonrifyelonr got unoptimizelond selongmelonnt.");
      relonturn falselon;
    }

    relonturn truelon;
  }

  /**
   * Velonrifielons an archivelon selongmelonnt has a sanelon numbelonr of lelonavelons.
   */
  public static boolelonan velonrifySelongmelonnt(SelongmelonntInfo selongmelonntInfo) {
    if (!shouldVelonrifySelongmelonnt(selongmelonntInfo)) {
      relonturn falselon;
    }
    Direlonctory direlonctory = selongmelonntInfo.gelontIndelonxSelongmelonnt().gelontLucelonnelonDirelonctory();
    relonturn velonrifyLucelonnelonIndelonx(direlonctory);
  }

  privatelon static boolelonan velonrifyLucelonnelonIndelonx(Direlonctory direlonctory) {
    try {
      DirelonctoryRelonadelonr indelonxelonrRelonadelonr = DirelonctoryRelonadelonr.opelonn(direlonctory);
      List<LelonafRelonadelonrContelonxt> lelonavelons = indelonxelonrRelonadelonr.gelontContelonxt().lelonavelons();
      if (lelonavelons.sizelon() != 1) {
        LOG.warn("Lucelonnelon indelonx doelons not havelon elonxactly onelon selongmelonnt: " + lelonavelons.sizelon() + " != 1. "
            + "Lucelonnelon selongmelonnts should havelon belonelonn melonrgelond during optimization.");
        relonturn falselon;
      }

      LelonafRelonadelonr relonadelonr = lelonavelons.gelont(0).relonadelonr();
      if (relonadelonr.numDocs() <= 0) {
        LOG.warn("Lucelonnelon indelonx has no documelonnt: " + relonadelonr);
        relonturn falselon;
      }
      relonturn truelon;
    } catch (IOelonxcelonption elon) {
      LOG.warn("Found bad lucelonnelon indelonx at: " + direlonctory);
      relonturn falselon;
    }
  }
}
