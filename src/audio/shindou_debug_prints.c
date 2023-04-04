#include <ultra64.h>

#ifdef VERSION_SH
// synthesis.c
char shindouDebugPrint1[] = "Terminate-Canceled Channel %d,Phase %d\n";
char shindouDebugPrint2[] = "S->W\n";
char shindouDebugPrint3[] = "W->S\n";
char shindouDebugPrint4[] = "S-Resample Pitch %x (old %d -> delay %d)\n";
s32 shindouDebugPrintPadding1[] = {0,0,0};

// heap.c
char shindouDebugPrint5[] = "Warning:Kill Note  %x \n";
char shindouDebugPrint6[] = "Kill Voice %d (ID %d) %d\n";
char shindouDebugPrint7[] = "Warning: Running Sequence's data disappear!\n";
char shindouDebugPrint8[] = "%x %x %x\n";
char shindouDebugPrint9[] = "Audio:Memory:Heap OverFlow : Not Allocate %d!\n";
char shindouDebugPrint10[] = "%x %x %x\n"; // Again
char shindouDebugPrint11[] = "Audio:Memory:Heap OverFlow : Not Allocate %d!\n"; // Again
char shindouDebugPrint12[] = "Audio:Memory:DataHeap Not Allocate \n";
char shindouDebugPrint13[] = "StayHeap Not Allocate %d\n";
char shindouDebugPrint14[] = "AutoHeap Not Allocate %d\n";
char shindouDebugPrint15[] = "Status ID0 : %d  ID1 : %d\n";
char shindouDebugPrint16[] = "id 0 is Stopping\n";
char shindouDebugPrint17[] = "id 0 is Stop\n";
char shindouDebugPrint18[] = "id 1 is Stopping\n";
char shindouDebugPrint19[] = "id 1 is Stop\n";
char shindouDebugPrint20[] = "WARNING: NO FREE AUTOSEQ AREA.\n";
char shindouDebugPrint21[] = "WARNING: NO STOP AUTO AREA.\n";
char shindouDebugPrint22[] = "         AND TRY FORCE TO STOP SIDE \n";
char shindouDebugPrint23[] = "Check ID0  (seq ID %d) Useing ...\n";
char shindouDebugPrint24[] = "Check ID1  (seq ID %d) Useing ...\n";
char shindouDebugPrint25[] = "No Free Seq area.\n";
char shindouDebugPrint26[] = "CH %d: ID %d\n";
char shindouDebugPrint27[] = "TWO SIDES ARE LOADING... ALLOC CANCELED.\n";
char shindouDebugPrint28[] = "WARNING: Before Area Overlaid After.";
char shindouDebugPrint29[] = "WARNING: After Area Overlaid Before.";
char shindouDebugPrint30[] = "MEMORY:SzHeapAlloc ERROR: sza->side %d\n";
char shindouDebugPrint31[] = "Audio:MEMORY:SzHeap Overflow error. (%d bytes)\n";
char shindouDebugPrint32[] = "Auto Heap Unhit for ID %d\n";
char shindouDebugPrint33[] = "Heap Reconstruct Start %x\n";
char shindouDebugPrint34[] = "---------------------------------------TEMPO %d %f\n";
char shindouDebugPrint35[] = "%f \n";
char shindouDebugPrint36[] = "%f \n"; // Again
char shindouDebugPrint37[] = "AHPBASE %x\n";
char shindouDebugPrint38[] = "AHPCUR  %x\n";
char shindouDebugPrint39[] = "HeapTop %x\n";
char shindouDebugPrint40[] = "SynoutRate %d / %d \n";
char shindouDebugPrint41[] = "FXSIZE %d\n";
char shindouDebugPrint42[] = "FXCOMP %d\n";
char shindouDebugPrint43[] = "FXDOWN %d\n";
char shindouDebugPrint44[] = "WaveCacheLen: %d\n";
char shindouDebugPrint45[] = "SpecChange Finished\n";
char shindouDebugPrint46[] = "Warning:Emem Over,not alloc %d\n";
char shindouDebugPrint47[] = "Single AutoSize %d\n";
char shindouDebugPrint48[] = "Single Ptr %x\n";
char shindouDebugPrint49[] = "Request--------Single-Auto, %d\n";
char shindouDebugPrint50[] = "Retry %x, %x, len %x\n";
char shindouDebugPrint51[] = "DMAing list %d is killed.\n";
char shindouDebugPrint52[] = "Try Kill %d \n";
char shindouDebugPrint53[] = "Try Kill %x %x\n";
char shindouDebugPrint54[] = "Try Kill %x %x %x\n";
char shindouDebugPrint55[] = "Rom back %x %x \n";
char shindouDebugPrint56[] = "Error sw NULL \n";
char shindouDebugPrint57[] = "Request--------Single-Stay, %d\n";
char shindouDebugPrint58[] = "Try Kill %d \n";
char shindouDebugPrint59[] = "Try Kill %x %x\n";
char shindouDebugPrint60[] = "Try Kill %x %x %x\n";
s32 shindouDebugPrintPadding[] = {0, 0, 0};

// load.c
char shindouDebugPrint61[] = "CAUTION:WAVE CACHE FULL %d";
char shindouDebugPrint62[] = "SUPERDMA";
char shindouDebugPrint63[] = "Bank Change... top %d lba %d\n";
char shindouDebugPrint64[] = "BankCount %d\n";
char shindouDebugPrint65[] = "BANK LOAD MISS! FOR %d\n";
char shindouDebugPrint66[] = "BankCount %d\n";
char shindouDebugPrint67[] = "Flush Start\n";
char shindouDebugPrint68[] = "%d ->%d\n";
char shindouDebugPrint69[] = "useflag %d\n";
char shindouDebugPrint70[] = "BankCount %d\n";
char shindouDebugPrint71[] = "%2x ";
char shindouDebugPrint72[] = "StartSeq (Group %d,Seq %d) Process finish\n";
char shindouDebugPrint73[] = "LoadCtrl, Ptr %x and Media is %d\n";
char shindouDebugPrint74[] = "Load Bank, Type %d , ID %d\n";
char shindouDebugPrint75[] = "get auto\n";
char shindouDebugPrint76[] = "get s-auto %x\n";
char shindouDebugPrint77[] = "Seq %d Write ID OK %d!\n";
char shindouDebugPrint78[] = "Banknumber %d\n";
char shindouDebugPrint79[] = "Bank Offset %x %d %d\n";
char shindouDebugPrint80[] = "PEP Touch %x \n";
char shindouDebugPrint81[] = "FastCopy";
char shindouDebugPrint82[] = "FastCopy";
char shindouDebugPrint83[] = "Error: Cannot DMA Media [%d]\n";
char shindouDebugPrint84[] = "Warning: size not align 16 %x  (%s)\n";
char shindouDebugPrint85[] = "Load Bank BG, Type %d , ID %d\n";
char shindouDebugPrint86[] = "get auto\n";
char shindouDebugPrint87[] = "get s-auto %x\n";
char shindouDebugPrint88[] = "Clear Workarea %x -%x size %x \n";
char shindouDebugPrint89[] = "AudioHeap is %x\n";
char shindouDebugPrint90[] = "Heap reset.Synth Change %x \n";
char shindouDebugPrint91[] = "Heap %x %x %x\n";
char shindouDebugPrint92[] = "Main Heap Initialize.\n";
char shindouDebugPrint93[] = "%d :WaveA %d WaveB %d Inst %d,Perc %d\n";
char shindouDebugPrint94[] = "---------- Init Completed. ------------\n";
char shindouDebugPrint95[] = " Syndrv    :[%6d]\n";
char shindouDebugPrint96[] = " Seqdrv    :[%6d]\n";
char shindouDebugPrint97[] = " audiodata :[%6d]\n";
char shindouDebugPrint98[] = "---------------------------------------\n";
char shindouDebugPrint99[] = "Entry--- %d %d\n";
char shindouDebugPrint100[] = "---Block LPS here\n";
char shindouDebugPrint101[] = "===Block LPS end\n";
char shindouDebugPrint102[] = "SLOWCOPY";
char shindouDebugPrint103[] = "Req: Src %x Dest %x Len %x,media %d,retcode %d\n";
char shindouDebugPrint104[] = "Remain Size %d\n";
char shindouDebugPrint105[] = "---Block BG here\n";
char shindouDebugPrint106[] = "===Block BG end\n";
char shindouDebugPrint107[] = "Retcode %x\n";
char shindouDebugPrint108[] = "Other Type: Not Write ID.\n";
char shindouDebugPrint109[] = "BGLOAD:Error: dma length 0\n";
char shindouDebugPrint110[] = "BGCOPY";
char shindouDebugPrint111[] = "Error: Already wavetable is touched %x.\n";
char shindouDebugPrint112[] = "Touch Warning: Length zero %x\n";
char shindouDebugPrint113[] = "It's busy now!!!!! %d\n"; // This one's my favorite
char shindouDebugPrint114[] = "BG LOAD BUFFER is OVER.\n";
char shindouDebugPrint115[] = "Warning: Length zero %x\n";
char shindouDebugPrint116[] = "Wave Load %d \n";
char shindouDebugPrint117[] = "Total Bg Wave Load %d \n";
char shindouDebugPrint118[] = "Receive %d\n";
char shindouDebugPrint119[] = "============Error: Magic is Broken after loading.\n";
char shindouDebugPrint120[] = "Remain DMA: %d\n";
char shindouDebugPrint121[] = "N start %d\n";
char shindouDebugPrint122[] = "============Error: Magic is Broken: %x\n";
char shindouDebugPrint123[] = "Error: No Handle.\n";
char shindouDebugPrint124[] = "Success: %x\n";

// port_eu.c
char shindouDebugPrint125[] = "DAC:Lost 1 Frame.\n";
char shindouDebugPrint126[] = "DMA: Request queue over.( %d )\n";
char shindouDebugPrint127[] = "Spec Change Override. %d -> %d\n";
char shindouDebugPrint128[] = "Audio:now-max tasklen is %d / %d\n";
char shindouDebugPrint129[] = "Audio:Warning:ABI Tasklist length over (%d)\n";
s32 D_SH_80314FC8 = 0x80;
struct SPTask *D_SH_80314FCC = NULL;
char shindouDebugPrint130[] = "BGLOAD Start %d\n";
char shindouDebugPrint131[] = "Error: OverFlow Your Request\n";
char shindouDebugPrint132[] = "---AudioSending (%d->%d) \n";
// These continue in unk_shindou_audio_file.c
#endif
