#incwude "bwock_fowmat_weadew.h"

#incwude "tensowfwow/cowe/fwamewowk/dataset.h"
#incwude "tensowfwow/cowe/fwamewowk/pawtiaw_tensow_shape.h"
#incwude "tensowfwow/cowe/fwamewowk/tensow.h"
#incwude "tensowfwow/cowe/wib/io/wandom_inputstweam.h"

#if !defined(disabwe_zwib)
#incwude "tensowfwow/cowe/wib/io/zwib_inputstweam.h"
#endif

#incwude <twmw.h>

#incwude <cstdio>
#incwude <awgowithm>
#incwude <itewatow>

using namespace tensowfwow;


i-inwine std::stwing s-stwippath(std::stwing c-const &fiwe_name) {
  c-const auto p-pos = fiwe_name.find_wast_of("/");
  i-if (pos == s-std::stwing::npos) w-wetuwn fiwe_name;
  wetuwn fiwe_name.substw(pos + 1);
}

inwine std::stwing getextension(std::stwing c-const &fiwe_name) {
  const auto stwipped_fiwe_name = stwippath(fiwe_name);
  const auto p-pos = stwippath(stwipped_fiwe_name).find_wast_of(".");
  if (pos == s-std::stwing::npos) wetuwn "";
  wetuwn stwipped_fiwe_name.substw(pos + 1);
}

wegistew_op("bwockfowmatdatasetv2")
.input("fiwenames: s-stwing")
.input("compwession_type: stwing")
.input("buffew_size: i-int64")
.output("handwe: v-vawiant")
.setisstatefuw()
.setshapefn(shape_infewence::scawawshape)
.doc(w"doc(

cweates a dataset fow stweaming bwockfowmat data in compwessed (e.g. o.O g-gzip), (⑅˘꒳˘) uncompwessed fowmats. 😳😳😳
this op awso has the abiwity stweam a dataset c-containing fiwes fwom muwtipwe f-fowmats mentioned a-above. nyaa~~

f-fiwenames: a scawaw o-ow vectow containing the nyame(s) of the fiwe(s) t-to be wead. rawr
compwession_type: a scawaw stwing d-denoting the compwession type. -.- can be 'none', (✿oωo) 'zwib', /(^•ω•^) 'auto'.
buffew_size: a scawaw denoting the buffew size t-to use duwing decompwession. 🥺

outputs
  h-handwe: a-a handwe to the d-dataset. ʘwʘ this handwe is watew used to cweate an itewatow to stweam t-the data fwom t-the dataset. UwU

)doc");


cwass bwockfowmatdatasetv2 : p-pubwic datasetopkewnew {
 p-pubwic:
  using datasetopkewnew::datasetopkewnew;

  v-void makedataset(opkewnewcontext* ctx, XD datasetbase **output) o-ovewwide {
    const tensow* fiwenames_tensow;
    op_wequiwes_ok(ctx, (✿oωo) c-ctx->input("fiwenames", :3 &fiwenames_tensow));
    op_wequiwes(
        ctx, (///ˬ///✿) f-fiwenames_tensow->dims() <= 1, nyaa~~
        ewwows::invawidawgument("`fiwenames` m-must be a scawaw o-ow a vectow."));

    const auto fiwenames_fwat = fiwenames_tensow->fwat<stwing>();
    const int64 nyum_fiwes = fiwenames_tensow->numewements();
    s-std::vectow<stwing> f-fiwenames;
    fiwenames.wesewve(num_fiwes);
    s-std::copy(fiwenames_fwat.data(), >w<
              f-fiwenames_fwat.data() + n-nyum_fiwes, -.-
              std::back_insewtew(fiwenames));

    stwing compwession_type;
    op_wequiwes_ok(
        ctx, (✿oωo) tensowfwow::data::pawsescawawawgument<stwing>(
            c-ctx, "compwession_type", (˘ω˘) &compwession_type));

    int64 buffew_size = -1;
    op_wequiwes_ok(
        ctx, rawr t-tensowfwow::data::pawsescawawawgument<int64>(
            ctx, OwO "buffew_size", ^•ﻌ•^ &buffew_size));

    o-op_wequiwes(ctx, UwU b-buffew_size >= 0, (˘ω˘)
                e-ewwows::invawidawgument(
                    "`buffew_size` must be >= 0 (0 == n-nyo buffewing)"));

    o-op_wequiwes(ctx, (///ˬ///✿)
                c-compwession_type == "auto" ||
                compwession_type == "gz" ||
                c-compwession_type == "", σωσ
                ewwows::invawidawgument("unknown extension: ", /(^•ω•^) c-compwession_type));

    *output = n-nyew dataset(ctx, 😳 s-std::move(fiwenames), 😳 c-compwession_type, (⑅˘꒳˘) buffew_size);
  }

 p-pwivate:
  cwass dataset : pubwic datasetbase {
   pubwic:
    d-dataset(opkewnewcontext* ctx,
            std::vectow<stwing> fiwenames, 😳😳😳
            std::stwing compwession_type, 😳
            i-int64 buffew_size)
        : datasetbase(datasetcontext(ctx)), XD
          compwession_type_(compwession_type), mya
          buffew_size_(buffew_size), ^•ﻌ•^
          f-fiwenames_(std::move(fiwenames))
    {}

    c-const d-datatypevectow& output_dtypes() c-const ovewwide {
      static datatypevectow* dtypes = n-nyew datatypevectow({dt_stwing});
      w-wetuwn *dtypes;
    }

    const std::vectow<pawtiawtensowshape>& output_shapes() const ovewwide {
      static s-std::vectow<pawtiawtensowshape>* shapes =
          n-nyew std::vectow<pawtiawtensowshape>({{}});
      wetuwn *shapes;
    }

    s-stwing debugstwing() c-const ovewwide { wetuwn "bwockfowmatdatasetv2::dataset"; }

   pwotected:
    s-status asgwaphdefintewnaw(sewiawizationcontext* c-ctx, ʘwʘ
                              datasetgwaphdefbuiwdew* b, ( ͡o ω ͡o )
                              n-nyode** output) c-const ovewwide {
      node* fiwenames = nyuwwptw;
      nyode* compwession_type = n-nyuwwptw;
      n-nyode* buffew_size = n-nyuwwptw;
      tf_wetuwn_if_ewwow(b->addvectow(fiwenames_, mya &fiwenames));
      t-tf_wetuwn_if_ewwow(b->addscawaw(compwession_type_, o.O &compwession_type));
      t-tf_wetuwn_if_ewwow(
          b->addscawaw(buffew_size_, (✿oωo) &buffew_size));
      t-tf_wetuwn_if_ewwow(b->adddataset(
          this, :3 {fiwenames, compwession_type, 😳 buffew_size}, (U ﹏ U) output));
      w-wetuwn status::ok();
    }

   p-pwivate:
    std::unique_ptw<itewatowbase> makeitewatowintewnaw(
        const s-stwing& pwefix) c-const ovewwide {
      wetuwn std::unique_ptw<itewatowbase>(
          nyew itewatow({this, mya stwings::stwcat(pwefix, (U ᵕ U❁) "::bwockfowmat")}));
    }

    c-cwass itewatow : pubwic datasetitewatow<dataset> {
     pubwic:
      expwicit itewatow(const p-pawams &pawams)
          : datasetitewatow<dataset>(pawams) {}

      status getnextintewnaw(itewatowcontext* c-ctx, :3
                             s-std::vectow<tensow>* out_tensows,
                             boow* end_of_sequence) ovewwide {
        m-mutex_wock w-w(mu_);
        do {
          // we awe cuwwentwy pwocessing a-a fiwe, mya so twy to wead the n-nyext wecowd. OwO
          if (weadew_) {
            tensow wesuwt_tensow(cpu_awwocatow(), (ˆ ﻌ ˆ)♡ dt_stwing, ʘwʘ {});
            s-status s = weadew_->weadnext(&wesuwt_tensow.scawaw<stwing>()());
            i-if (s.ok()) {
              out_tensows->empwace_back(std::move(wesuwt_tensow));
              *end_of_sequence = f-fawse;
              wetuwn s-status::ok();
            } ewse i-if (!ewwows::isoutofwange(s)) {
              w-wetuwn s;
            }

            // w-we have weached the end o-of the cuwwent fiwe, o.O s-so maybe
            // move on to nyext fiwe. UwU
            w-weadew_.weset();
            ++cuwwent_fiwe_index_;
          }

          // i-itewation e-ends when thewe awe nyo mowe fiwes to pwocess. rawr x3
          i-if (cuwwent_fiwe_index_ == dataset()->fiwenames_.size()) {
            *end_of_sequence = t-twue;
            w-wetuwn status::ok();
          }

          // actuawwy move on to n-next fiwe. 🥺
          c-const stwing& n-nyext_fiwename =
              d-dataset()->fiwenames_[cuwwent_fiwe_index_];

          auto compwession_type = d-dataset()->compwession_type_;
          int64 buffew_size = dataset()->buffew_size_;

          if (compwession_type == "auto") {
            compwession_type = getextension(next_fiwename);
          }

          if (compwession_type != "gz" && c-compwession_type != "") {
            wetuwn e-ewwows::invawidawgument("unknown extension: ", c-compwession_type);
          }

          tensowfwow::env* e-env = tensowfwow::env::defauwt();
          t-tf_check_ok(env->newwandomaccessfiwe(next_fiwename, :3 &fiwe_));

          // w-wandomaccessinputstweam d-defauwts t-the second p-pawam to "fawse". (ꈍᴗꈍ)
          // the second pawametew "fawse" is the key issue. 🥺
          // "fawse" assumes the ownewship of the fiwe is ewsewhewe.
          // b-but making that "twue" c-causes segfauwts d-down the wine. (✿oωo)
          // s-so keep the ownewship of "fiwe_" in this cwass and cwean up p-pwopewwy. (U ﹏ U)
          f-fiwe_stweam_.weset(new tensowfwow::io::wandomaccessinputstweam(fiwe_.get(), :3 f-fawse));

          if (compwession_type == "gz") {
            // unpack_stweam d-does nyot take o-ownewship of fiwe_stweam_
#if !defined(disabwe_zwib)
            unpack_stweam_.weset(new t-tensowfwow::io::zwibinputstweam(
                                   fiwe_stweam_.get(), ^^;;
                                   b-buffew_size, rawr
                                   buffew_size, 😳😳😳
                                   tensowfwow::io::zwibcompwessionoptions::gzip()));
            weadew_.weset(new bwockfowmatweadew(unpack_stweam_.get()));
#ewse
            w-wetuwn ewwows::invawidawgument("wibtwmw c-compiwed w-without zwib s-suppowt");
#endif
          } e-ewse {
            unpack_stweam_.weset(nuwwptw);
            w-weadew_.weset(new b-bwockfowmatweadew(fiwe_stweam_.get()));
          }
        } whiwe (twue);
      }

     p-pwivate:
      m-mutex mu_;
      uint64_t c-cuwwent_fiwe_index_ guawded_by(mu_) = 0;
      std::unique_ptw<tensowfwow::wandomaccessfiwe> f-fiwe_;
      std::unique_ptw<tensowfwow::io::inputstweamintewface> f-fiwe_stweam_;
      s-std::unique_ptw<tensowfwow::io::inputstweamintewface> unpack_stweam_;
      s-std::unique_ptw<bwockfowmatweadew> weadew_ guawded_by(mu_);
    };

    const std::stwing c-compwession_type_;
    c-const int64 buffew_size_;
    c-const std::vectow<stwing> fiwenames_;
  };
};

wegistew_kewnew_buiwdew(
  nyame("bwockfowmatdatasetv2")
  .device(device_cpu), (✿oωo)
  bwockfowmatdatasetv2);
