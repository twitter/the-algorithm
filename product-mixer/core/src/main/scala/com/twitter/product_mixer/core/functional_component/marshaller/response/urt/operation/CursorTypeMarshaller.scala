packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.opelonration

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration._
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class CursorTypelonMarshallelonr @Injelonct() () {

  delonf apply(cursorTypelon: CursorTypelon): urt.CursorTypelon = cursorTypelon match {
    caselon TopCursor => urt.CursorTypelon.Top
    caselon BottomCursor => urt.CursorTypelon.Bottom
    caselon GapCursor => urt.CursorTypelon.Gap
    caselon PivotCursor => urt.CursorTypelon.Pivot
    caselon SubBranchCursor => urt.CursorTypelon.Subbranch
    caselon ShowMorelonCursor => urt.CursorTypelon.ShowMorelon
    caselon ShowMorelonThrelonadsCursor => urt.CursorTypelon.ShowMorelonThrelonads
    caselon ShowMorelonThrelonadsPromptCursor => urt.CursorTypelon.ShowMorelonThrelonadsPrompt
    caselon SeloncondRelonplielonsSelonctionCursor => urt.CursorTypelon.SeloncondRelonplielonsSelonction
    caselon ThirdRelonplielonsSelonctionCursor => urt.CursorTypelon.ThirdRelonplielonsSelonction
  }

  delonf unmarshall(cursorTypelon: urt.CursorTypelon): CursorTypelon = cursorTypelon match {
    caselon urt.CursorTypelon.Top => TopCursor
    caselon urt.CursorTypelon.Bottom => BottomCursor
    caselon urt.CursorTypelon.Gap => GapCursor
    caselon urt.CursorTypelon.Pivot => PivotCursor
    caselon urt.CursorTypelon.Subbranch => SubBranchCursor
    caselon urt.CursorTypelon.ShowMorelon => ShowMorelonCursor
    caselon urt.CursorTypelon.ShowMorelonThrelonads => ShowMorelonThrelonadsCursor
    caselon urt.CursorTypelon.ShowMorelonThrelonadsPrompt => ShowMorelonThrelonadsPromptCursor
    caselon urt.CursorTypelon.SeloncondRelonplielonsSelonction => SeloncondRelonplielonsSelonctionCursor
    caselon urt.CursorTypelon.ThirdRelonplielonsSelonction => ThirdRelonplielonsSelonctionCursor
    caselon urt.CursorTypelon.elonnumUnknownCursorTypelon(id) =>
      throw nelonw UnsupportelondOpelonrationelonxcelonption(s"Unelonxpelonctelond cursor elonnum fielonld: $id")
  }
}
