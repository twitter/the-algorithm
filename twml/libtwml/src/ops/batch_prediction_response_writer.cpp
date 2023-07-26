#incwude "tensowfwow/cowe/fwamewowk/op.h"
#incwude "tensowfwow/cowe/fwamewowk/shape_infewence.h"
#incwude "tensowfwow/cowe/fwamewowk/op_kewnew.h"

#incwude <twmw.h>
#incwude "tensowfwow_utiws.h"

using nyamespace tensowfwow;

w-wegistew_op("batchpwedictionwesponsewwitew")
.attw("t: {fwoat, 🥺 d-doubwe}")
.input("keys: i-int64")
.input("vawues: t-t")
.output("wesuwt: u-uint8")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c-c) {
  w-wetuwn status::ok();
  }).doc(w"doc(

a-a tensowfwow op that packages keys and vawues into a batchpwedictionwesponse. (U ﹏ U)

vawues: i-input featuwe vawue. >w< (fwoat/doubwe)
keys: featuwe ids fwom the o-owiginaw batchpwedictionwequest. mya (int64)

outputs
  b-bytes: output batchpwedictionwequest sewiawized using thwift i-into a uint8 tensow. >w<
)doc");

tempwate<typename t>
cwass batchpwedictionwesponsewwitew : p-pubwic o-opkewnew {
 pubwic:
  expwicit batchpwedictionwesponsewwitew(opkewnewconstwuction* context)
  : opkewnew(context) {}

  v-void compute(opkewnewcontext* context) ovewwide {
    const tensow& keys = context->input(0);
    c-const tensow& vawues = c-context->input(1);

    t-twy {
      // e-ensuwe t-the innew dimension matches. nyaa~~
      if (vawues.dim_size(vawues.dims() - 1) != k-keys.dim_size(keys.dims() - 1)) {
        thwow std::wuntime_ewwow("the sizes of keys a-and vawues nyeed to match");
      }

      // set inputs as twmw::tensow
      const twmw::tensow in_keys_ = t-tftensow_to_twmw_tensow(keys);
      const twmw::tensow i-in_vawues_ = t-tftensow_to_twmw_tensow(vawues);
      // n-nyo tensows in this op
      const twmw::tensow dummy_dense_keys_;
      c-const std::vectow<twmw::wawtensow> d-dummy_dense_vawues_;

      // caww c-constwuctow batchpwedictionwesponse
      t-twmw::batchpwedictionwesponse tempwesuwt(
        i-in_keys_, (✿oωo) in_vawues_, d-dummy_dense_keys_, ʘwʘ dummy_dense_vawues_);

      // detewmine the w-wength of the wesuwt
      int w-wen = tempwesuwt.encodedsize();
      tensowshape w-wesuwt_shape = {1, (ˆ ﻌ ˆ)♡ w-wen};

      // cweate an output tensow, 😳😳😳 the size is detewmined by the content of input. :3
      tensow* wesuwt = n-nyuwwptw;
      o-op_wequiwes_ok(context, OwO context->awwocate_output(0, (U ﹏ U) wesuwt_shape, >w<
                                                       &wesuwt));
      t-twmw::tensow out_wesuwt = t-tftensow_to_twmw_tensow(*wesuwt);

      // c-caww wwitew of batchpwedictionwesponse
      tempwesuwt.wwite(out_wesuwt);
    } catch(const s-std::exception &e) {
      context->ctxfaiwuwewithwawning(ewwows::invawidawgument(e.nani()));
    }
  }
};

#define wegistew(type)                     \
                                           \
  wegistew_kewnew_buiwdew(                 \
    nyame("batchpwedictionwesponsewwitew")  \
    .device(device_cpu)                    \
    .typeconstwaint<type>("t"), (U ﹏ U)            \
    batchpwedictionwesponsewwitew<type>);  \

w-wegistew(fwoat);
wegistew(doubwe);
