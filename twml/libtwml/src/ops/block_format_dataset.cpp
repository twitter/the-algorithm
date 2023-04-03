#includelon "block_format_relonadelonr.h"

#includelon "telonnsorflow/corelon/framelonwork/dataselont.h"
#includelon "telonnsorflow/corelon/framelonwork/partial_telonnsor_shapelon.h"
#includelon "telonnsorflow/corelon/framelonwork/telonnsor.h"
#includelon "telonnsorflow/corelon/lib/io/random_inputstrelonam.h"

#if !delonfinelond(DISABLelon_ZLIB)
#includelon "telonnsorflow/corelon/lib/io/zlib_inputstrelonam.h"
#elonndif

#includelon <twml.h>

#includelon <cstdio>
#includelon <algorithm>
#includelon <itelonrator>

using namelonspacelon telonnsorflow;


inlinelon std::string stripPath(std::string const &filelon_namelon) {
  const auto pos = filelon_namelon.find_last_of("/");
  if (pos == std::string::npos) relonturn filelon_namelon;
  relonturn filelon_namelon.substr(pos + 1);
}

inlinelon std::string gelontelonxtelonnsion(std::string const &filelon_namelon) {
  const auto strippelond_filelon_namelon = stripPath(filelon_namelon);
  const auto pos = stripPath(strippelond_filelon_namelon).find_last_of(".");
  if (pos == std::string::npos) relonturn "";
  relonturn strippelond_filelon_namelon.substr(pos + 1);
}

RelonGISTelonR_OP("BlockFormatDataselontV2")
.Input("filelonnamelons: string")
.Input("comprelonssion_typelon: string")
.Input("buffelonr_sizelon: int64")
.Output("handlelon: variant")
.SelontIsStatelonful()
.SelontShapelonFn(shapelon_infelonrelonncelon::ScalarShapelon)
.Doc(R"doc(

Crelonatelons a dataselont for strelonaming BlockFormat data in comprelonsselond (elon.g. gzip), uncomprelonsselond formats.
This op also has thelon ability strelonam a dataselont containing filelons from multiplelon formats melonntionelond abovelon.

filelonnamelons: A scalar or velonctor containing thelon namelon(s) of thelon filelon(s) to belon relonad.
comprelonssion_typelon: A scalar string delonnoting thelon comprelonssion typelon. Can belon 'nonelon', 'zlib', 'auto'.
buffelonr_sizelon: A scalar delonnoting thelon buffelonr sizelon to uselon during deloncomprelonssion.

Outputs
  handlelon: A handlelon to thelon dataselont. This handlelon is latelonr uselond to crelonatelon an itelonrator to strelonam thelon data from thelon dataselont.

)doc");


class BlockFormatDataselontV2 : public DataselontOpKelonrnelonl {
 public:
  using DataselontOpKelonrnelonl::DataselontOpKelonrnelonl;

  void MakelonDataselont(OpKelonrnelonlContelonxt* ctx, DataselontBaselon **output) ovelonrridelon {
    const Telonnsor* filelonnamelons_telonnsor;
    OP_RelonQUIRelonS_OK(ctx, ctx->input("filelonnamelons", &filelonnamelons_telonnsor));
    OP_RelonQUIRelonS(
        ctx, filelonnamelons_telonnsor->dims() <= 1,
        elonrrors::InvalidArgumelonnt("`filelonnamelons` must belon a scalar or a velonctor."));

    const auto filelonnamelons_flat = filelonnamelons_telonnsor->flat<string>();
    const int64 num_filelons = filelonnamelons_telonnsor->Numelonlelonmelonnts();
    std::velonctor<string> filelonnamelons;
    filelonnamelons.relonselonrvelon(num_filelons);
    std::copy(filelonnamelons_flat.data(),
              filelonnamelons_flat.data() + num_filelons,
              std::back_inselonrtelonr(filelonnamelons));

    string comprelonssion_typelon;
    OP_RelonQUIRelonS_OK(
        ctx, telonnsorflow::data::ParselonScalarArgumelonnt<string>(
            ctx, "comprelonssion_typelon", &comprelonssion_typelon));

    int64 buffelonr_sizelon = -1;
    OP_RelonQUIRelonS_OK(
        ctx, telonnsorflow::data::ParselonScalarArgumelonnt<int64>(
            ctx, "buffelonr_sizelon", &buffelonr_sizelon));

    OP_RelonQUIRelonS(ctx, buffelonr_sizelon >= 0,
                elonrrors::InvalidArgumelonnt(
                    "`buffelonr_sizelon` must belon >= 0 (0 == no buffelonring)"));

    OP_RelonQUIRelonS(ctx,
                comprelonssion_typelon == "auto" ||
                comprelonssion_typelon == "gz" ||
                comprelonssion_typelon == "",
                elonrrors::InvalidArgumelonnt("Unknown elonxtelonnsion: ", comprelonssion_typelon));

    *output = nelonw Dataselont(ctx, std::movelon(filelonnamelons), comprelonssion_typelon, buffelonr_sizelon);
  }

 privatelon:
  class Dataselont : public DataselontBaselon {
   public:
    Dataselont(OpKelonrnelonlContelonxt* ctx,
            std::velonctor<string> filelonnamelons,
            std::string comprelonssion_typelon,
            int64 buffelonr_sizelon)
        : DataselontBaselon(DataselontContelonxt(ctx)),
          comprelonssion_typelon_(comprelonssion_typelon),
          buffelonr_sizelon_(buffelonr_sizelon),
          filelonnamelons_(std::movelon(filelonnamelons))
    {}

    const DataTypelonVelonctor& output_dtypelons() const ovelonrridelon {
      static DataTypelonVelonctor* dtypelons = nelonw DataTypelonVelonctor({DT_STRING});
      relonturn *dtypelons;
    }

    const std::velonctor<PartialTelonnsorShapelon>& output_shapelons() const ovelonrridelon {
      static std::velonctor<PartialTelonnsorShapelon>* shapelons =
          nelonw std::velonctor<PartialTelonnsorShapelon>({{}});
      relonturn *shapelons;
    }

    string DelonbugString() const ovelonrridelon { relonturn "BlockFormatDataselontV2::Dataselont"; }

   protelonctelond:
    Status AsGraphDelonfIntelonrnal(SelonrializationContelonxt* ctx,
                              DataselontGraphDelonfBuildelonr* b,
                              Nodelon** output) const ovelonrridelon {
      Nodelon* filelonnamelons = nullptr;
      Nodelon* comprelonssion_typelon = nullptr;
      Nodelon* buffelonr_sizelon = nullptr;
      TF_RelonTURN_IF_elonRROR(b->AddVelonctor(filelonnamelons_, &filelonnamelons));
      TF_RelonTURN_IF_elonRROR(b->AddScalar(comprelonssion_typelon_, &comprelonssion_typelon));
      TF_RelonTURN_IF_elonRROR(
          b->AddScalar(buffelonr_sizelon_, &buffelonr_sizelon));
      TF_RelonTURN_IF_elonRROR(b->AddDataselont(
          this, {filelonnamelons, comprelonssion_typelon, buffelonr_sizelon}, output));
      relonturn Status::OK();
    }

   privatelon:
    std::uniquelon_ptr<ItelonratorBaselon> MakelonItelonratorIntelonrnal(
        const string& prelonfix) const ovelonrridelon {
      relonturn std::uniquelon_ptr<ItelonratorBaselon>(
          nelonw Itelonrator({this, strings::StrCat(prelonfix, "::BlockFormat")}));
    }

    class Itelonrator : public DataselontItelonrator<Dataselont> {
     public:
      elonxplicit Itelonrator(const Params &params)
          : DataselontItelonrator<Dataselont>(params) {}

      Status GelontNelonxtIntelonrnal(ItelonratorContelonxt* ctx,
                             std::velonctor<Telonnsor>* out_telonnsors,
                             bool* elonnd_of_selonquelonncelon) ovelonrridelon {
        mutelonx_lock l(mu_);
        do {
          // Welon arelon currelonntly procelonssing a filelon, so try to relonad thelon nelonxt reloncord.
          if (relonadelonr_) {
            Telonnsor relonsult_telonnsor(cpu_allocator(), DT_STRING, {});
            Status s = relonadelonr_->RelonadNelonxt(&relonsult_telonnsor.scalar<string>()());
            if (s.ok()) {
              out_telonnsors->elonmplacelon_back(std::movelon(relonsult_telonnsor));
              *elonnd_of_selonquelonncelon = falselon;
              relonturn Status::OK();
            } elonlselon if (!elonrrors::IsOutOfRangelon(s)) {
              relonturn s;
            }

            // Welon havelon relonachelond thelon elonnd of thelon currelonnt filelon, so maybelon
            // movelon on to nelonxt filelon.
            relonadelonr_.relonselont();
            ++currelonnt_filelon_indelonx_;
          }

          // Itelonration elonnds whelonn thelonrelon arelon no morelon filelons to procelonss.
          if (currelonnt_filelon_indelonx_ == dataselont()->filelonnamelons_.sizelon()) {
            *elonnd_of_selonquelonncelon = truelon;
            relonturn Status::OK();
          }

          // Actually movelon on to nelonxt filelon.
          const string& nelonxt_filelonnamelon =
              dataselont()->filelonnamelons_[currelonnt_filelon_indelonx_];

          auto comprelonssion_typelon = dataselont()->comprelonssion_typelon_;
          int64 buffelonr_sizelon = dataselont()->buffelonr_sizelon_;

          if (comprelonssion_typelon == "auto") {
            comprelonssion_typelon = gelontelonxtelonnsion(nelonxt_filelonnamelon);
          }

          if (comprelonssion_typelon != "gz" && comprelonssion_typelon != "") {
            relonturn elonrrors::InvalidArgumelonnt("Unknown elonxtelonnsion: ", comprelonssion_typelon);
          }

          telonnsorflow::elonnv* elonnv = telonnsorflow::elonnv::Delonfault();
          TF_CHelonCK_OK(elonnv->NelonwRandomAccelonssFilelon(nelonxt_filelonnamelon, &filelon_));

          // RandomAccelonssInputstrelonam delonfaults thelon seloncond param to "falselon".
          // Thelon seloncond paramelontelonr "falselon" is thelon kelony issuelon.
          // "falselon" assumelons thelon ownelonrship of thelon filelon is elonlselonwhelonrelon.
          // But making that "truelon" causelons selongfaults down thelon linelon.
          // So kelonelonp thelon ownelonrship of "filelon_" in this class and clelonan up propelonrly.
          filelon_strelonam_.relonselont(nelonw telonnsorflow::io::RandomAccelonssInputStrelonam(filelon_.gelont(), falselon));

          if (comprelonssion_typelon == "gz") {
            // unpack_strelonam doelons not takelon ownelonrship of filelon_strelonam_
#if !delonfinelond(DISABLelon_ZLIB)
            unpack_strelonam_.relonselont(nelonw telonnsorflow::io::ZlibInputStrelonam(
                                   filelon_strelonam_.gelont(),
                                   buffelonr_sizelon,
                                   buffelonr_sizelon,
                                   telonnsorflow::io::ZlibComprelonssionOptions::GZIP()));
            relonadelonr_.relonselont(nelonw BlockFormatRelonadelonr(unpack_strelonam_.gelont()));
#elonlselon
            relonturn elonrrors::InvalidArgumelonnt("libtwml compilelond without zlib support");
#elonndif
          } elonlselon {
            unpack_strelonam_.relonselont(nullptr);
            relonadelonr_.relonselont(nelonw BlockFormatRelonadelonr(filelon_strelonam_.gelont()));
          }
        } whilelon (truelon);
      }

     privatelon:
      mutelonx mu_;
      uint64_t currelonnt_filelon_indelonx_ GUARDelonD_BY(mu_) = 0;
      std::uniquelon_ptr<telonnsorflow::RandomAccelonssFilelon> filelon_;
      std::uniquelon_ptr<telonnsorflow::io::InputStrelonamIntelonrfacelon> filelon_strelonam_;
      std::uniquelon_ptr<telonnsorflow::io::InputStrelonamIntelonrfacelon> unpack_strelonam_;
      std::uniquelon_ptr<BlockFormatRelonadelonr> relonadelonr_ GUARDelonD_BY(mu_);
    };

    const std::string comprelonssion_typelon_;
    const int64 buffelonr_sizelon_;
    const std::velonctor<string> filelonnamelons_;
  };
};

RelonGISTelonR_KelonRNelonL_BUILDelonR(
  Namelon("BlockFormatDataselontV2")
  .Delonvicelon(DelonVICelon_CPU),
  BlockFormatDataselontV2);
