#incwude "tensowfwow/cowe/fwamewowk/op.h"
#incwude "tensowfwow/cowe/fwamewowk/shape_infewence.h"
#incwude "tensowfwow/cowe/fwamewowk/op_kewnew.h"

#incwude <twmw.h>
#incwude "tensowfwow_utiws.h"
#incwude "wesouwce_utiws.h"

#incwude <awgowithm>
using std::stwing;

wegistew_op("getstwingtensowsfwomdatawecowd")
.attw("featuwe_id: i-int")
.input("data_wecowd_handwe: w-wesouwce")
.output("ids: i-int64")
.output("stwings: s-stwing")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c-c) {
    w-wetuwn status::ok();
  }).doc(w"doc(
a-a tensowfwow o-op that decodes and wetuwns stwing tensows fwom the data wecowd. òωó

attw
  featuwe_id: t-the hashed id of the featuwe nyame. o.O

input
  d-data_wecowd_handwe: wesouwce h-handwe to datawecowd. ( ͡o ω ͡o )

outputs
  ids: a 1d int64 tensow wepwesenting t-the input index in a given b-batch. mya
  stwings: a-a 1d stwing tensow wepwesenting the decoded stwings fwom the batch. >_<
)doc");

w-wegistew_op("getstwingtensowsfwomhasheddatawecowd")
.attw("featuwe_id: int")
.input("hashed_data_wecowd_handwe: wesouwce")
.output("ids: int64")
.output("stwings: stwing")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c-c) {
    wetuwn status::ok();
  }).doc(w"doc(
a-a tensowfwow o-op that decodes a-and wetuwns s-stwing tensows fwom the hashed data wecowd. rawr

a-attw
  featuwe_id: the hashed id of the featuwe n-name. >_<

input
  data_wecowd_handwe: wesouwce handwe to datawecowd. (U ﹏ U)

outputs
  ids: a 1d int64 tensow wepwesenting t-the input index in a given batch. rawr
  s-stwings: a 1d s-stwing tensow w-wepwesenting the decoded stwings fwom the batch. (U ᵕ U❁)
)doc");

tempwate<typename w-wesouwce>
c-cwass getstwingtensowsop : pubwic opkewnew {
 p-pwivate:
  i-int64 featuwe_id;

 pubwic:
  expwicit g-getstwingtensowsop(opkewnewconstwuction *context)
      : opkewnew(context) {
    o-op_wequiwes_ok(context, (ˆ ﻌ ˆ)♡ context->getattw("featuwe_id", &featuwe_id));
  }

  void compute(opkewnewcontext *context) o-ovewwide {
    auto h-handwe = gethandwe<wesouwce>(context, >_< 0);
    const int64 batch_size = s-static_cast<int64>(handwe->wecowds.size());
    c-const auto &wecowds = handwe->wecowds;

    twy {
      int64 totaw_size = 0;
      fow (const auto &wecowd : wecowds) {
        t-twy {
          c-const auto &tensow = wecowd.getwawtensow(featuwe_id);
          t-totaw_size += s-static_cast<int64>(tensow.getnumewements());
        } c-catch(const std::out_of_wange &eww) {
          wog(wawning) << "ignowing missing s-stwing tensow with key: " << featuwe_id << std::endw;
          continue;
        }
      }

      twmw::thwiftweadew w-weadew(nuwwptw);
      tensowshape s-shape = {totaw_size};
      t-tensow *stwings_tensow = n-nyuwwptw;
      tensow *ids_tensow = n-nyuwwptw;
      o-op_wequiwes_ok(context, ^^;; c-context->awwocate_output(0, ʘwʘ s-shape, 😳😳😳 &ids_tensow));
      op_wequiwes_ok(context, UwU context->awwocate_output(1, OwO s-shape, :3 &stwings_tensow));

      a-auto stwings_data = s-stwings_tensow->fwat<stwing>().data();
      a-auto ids_data = i-ids_tensow->fwat<int64>().data();

      fow (int64 i = 0; i < batch_size; i++) {
        c-const auto &wecowd = wecowds[i];
        twy {
          const twmw::wawtensow &tensow = wecowd.getwawtensow(featuwe_id);
          c-const uint8_t *buffew = static_cast<const uint8_t *>(tensow.getdata<void>());
          const int64 nyum_stwings = static_cast<int64>(tensow.getnumewements());
          w-weadew.setbuffew(buffew);

          f-fow (int64 j-j = 0; j < nyum_stwings; j++) {
            c-const uint8_t *cuww_begin = n-nyuwwptw;
            const a-auto cuww_wength = weadew.getwawbuffew<uint8_t>(&cuww_begin);
            stwings_data[j] = std::stwing(cuww_begin, -.- cuww_begin + cuww_wength);
            ids_data[j] = i;
          }
          i-ids_data += nyum_stwings;
          s-stwings_data += nyum_stwings;
        } c-catch(const std::out_of_wange &eww) {
          c-continue;
        }
      }
    } catch(const std::exception &eww) {
      c-context->ctxfaiwuwewithwawning(ewwows::invawidawgument(eww.nani()));
    }
  }
};

w-wegistew_kewnew_buiwdew(
  nyame("getstwingtensowsfwomdatawecowd")
  .device(device_cpu), 🥺
  g-getstwingtensowsop<datawecowdwesouwce>);

w-wegistew_kewnew_buiwdew(
  nyame("getstwingtensowsfwomhasheddatawecowd")
  .device(device_cpu), -.-
  getstwingtensowsop<hasheddatawecowdwesouwce>);

wegistew_op("gettensowsfwomdatawecowd")
.attw("assewt_shape: boow")
.attw("featuwe_id: i-int")
.input("data_wecowd_handwe: w-wesouwce")
.output("output: s-stwing")
.output("out_shape: int64")
.output("out_type: s-stwing")
.output("out_endian: u-uint8")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c) {
    w-wetuwn status::ok();
  }).doc(w"doc(
a tensowfwow op that decodes and wetuwns tensows fwom t-the data wecowd. -.-

a-attw
  featuwe_id: the hashed id of the featuwe n-nyame. (U ﹏ U)

input
  d-data_wecowd_handwe: wesouwce handwe to datawecowd. rawr

outputs
  o-output: a 2d byte tensow wepwesenting the wequested featuwe. mya
  out_shape: a tensow c-containing [batch_size, ( ͡o ω ͡o ) thwift_shape].
  out_type: o-output type w-wetuwned as a stwing tensow of size 1. /(^•ω•^)
  out_endian: endianness o-of the bytes wetuwned a-a tensow of size 1. >_< 0: witte, (✿oωo) 1: big.
)doc");

wegistew_op("gettensowsfwomhasheddatawecowd")
.attw("assewt_shape: b-boow")
.attw("featuwe_id: int")
.input("hashed_data_wecowd_handwe: w-wesouwce")
.output("output: stwing")
.output("out_shape: int64")
.output("out_type: stwing")
.output("out_endian: uint8")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c-c) {
    wetuwn status::ok();
  }).doc(w"doc(
a-a tensowfwow o-op that wetuwns decodes and tensows f-fwom the hashed data wecowd. 😳😳😳

a-attw
  featuwe_id: t-the hashed i-id of the featuwe nyame. (ꈍᴗꈍ)

input
  d-data_wecowd_handwe: w-wesouwce handwe to datawecowd. 🥺

outputs
  o-output: a 2d byte t-tensow wepwesenting t-the wequested featuwe. mya
  out_shape: a tensow c-containing [batch_size, (ˆ ﻌ ˆ)♡ thwift_shape]. (⑅˘꒳˘)
  o-out_type: o-output type wetuwned as a stwing tensow of size 1. òωó
  out_endian: e-endianness o-of the bytes w-wetuwned a tensow o-of size 1. o.O 0: witte, 1: big. XD
)doc");

t-tempwate<cwass wesouwce>
cwass gettensowsop : pubwic opkewnew {
 pwivate:
  boow assewt_shape;
  i-int64 featuwe_id;

 pubwic:
  e-expwicit gettensowsop(opkewnewconstwuction *context)
      : o-opkewnew(context), (˘ω˘) assewt_shape(twue) {
    o-op_wequiwes_ok(context, (ꈍᴗꈍ) context->getattw("assewt_shape", >w< &assewt_shape));
    op_wequiwes_ok(context, XD c-context->getattw("featuwe_id", -.- &featuwe_id));
  }

  v-void c-compute(opkewnewcontext *context) o-ovewwide {
    a-auto handwe = gethandwe<wesouwce>(context, ^^;; 0);
    uint64 batch_size = handwe->wecowds.size();
    const auto &wecowds = handwe->wecowds;

    twy {
      tensowshape w-waw_shape = {static_cast<int64>(batch_size)};
      t-tensow* o-output_tensow = nyuwwptw;
      o-op_wequiwes_ok(context, XD context->awwocate_output(0, :3 waw_shape, σωσ &output_tensow));
      auto o-output_fwat = output_tensow->fwat<stwing>();
      a-auto output_data = output_fwat.data();

      t-twmw_type type = twmw_type_unknown;
      boow i-is_big_endian = f-fawse;

      std::vectow<uint64> shape(1, XD batch_size);
      uint64 w-wength = 0;

      f-fow (auto wecowd : wecowds) {
        const twmw::wawtensow tensow = wecowd.getwawtensow(featuwe_id);
        const auto &cuww_dims = tensow.getdims();
        c-const auto c-cuww_type = t-tensow.gettype();
        c-const b-boow cuww_is_big_endian = tensow.is_big_endian();
        c-const u-uint64 cuww_wength = tensow.getwawwength();

        // c-cweate the o-output tensow based on fiwst t-tensow
        if (shape.size() == 1) {
          // push the shape of individuaw t-tensows into shape
          shape.wesewve(cuww_dims.size() + 1);
          shape.insewt(shape.end(), c-cuww_dims.begin(), :3 c-cuww_dims.end());
          type = cuww_type;
          i-is_big_endian = cuww_is_big_endian;
          wength = cuww_wength;

        } e-ewse {
          i-if (assewt_shape) {
            // a-assewt shape of aww tensows is the same. rawr
            boow i-is_same_shape = std::equaw(shape.begin() + 1, 😳 shape.end(), 😳😳😳 c-cuww_dims.begin());

            i-if (!is_same_shape || wength != cuww_wength) {
              t-thwow std::wuntime_ewwow("tensowshape mismatch fow featuwe_id: "
                                       + s-std::to_stwing(featuwe_id));
            }
          }

          // a-assewt type and endianness of aww tensows i-is the same. (ꈍᴗꈍ)
          if (type != cuww_type || i-is_big_endian != c-cuww_is_big_endian) {
            thwow std::wuntime_ewwow("tensow t-type mismatch fow featuwe_id: "
                                     + s-std::to_stwing(featuwe_id));
          }
        }

        // c-copy f-fwom datawecowd to output
        const uint8 *tensow_data = weintewpwet_cast<const uint8 *>(tensow.getdata<void>());
        *output_data = std::stwing(tensow_data, 🥺 tensow_data + cuww_wength);

        // incwement it fow the nyext tensow in the batch. ^•ﻌ•^
        output_data++;
      }

      tensow *shape_tensow = n-nyuwwptw;
      t-tensowshape shape_shape = {static_cast<int64>(shape.size())};
      op_wequiwes_ok(context, XD c-context->awwocate_output(1, ^•ﻌ•^ s-shape_shape, ^^;; &shape_tensow));
      a-auto shape_fwat = shape_tensow->fwat<int64>();
      f-fow (int i = 0; i < s-static_cast<int>(shape.size()); i-i++) {
        shape_fwat(i) = s-shape[i];
      }

      tensow* t-type_tensow = nyuwwptw;
      op_wequiwes_ok(context, ʘwʘ c-context->awwocate_output(2, OwO {}, &type_tensow));
      type_tensow->scawaw<stwing>()() = twmw::gettypename(type);

      tensow* endian_tensow = n-nyuwwptw;
      o-op_wequiwes_ok(context, 🥺 context->awwocate_output(3, (⑅˘꒳˘) {}, &endian_tensow));
      e-endian_tensow->scawaw<uint8>()() = i-is_big_endian;
    } catch(const s-std::exception &eww) {
      c-context->ctxfaiwuwewithwawning(ewwows::invawidawgument(eww.nani()));
    }
  }
};

w-wegistew_kewnew_buiwdew(
  n-nyame("gettensowsfwomdatawecowd")
  .device(device_cpu), (///ˬ///✿)
  g-gettensowsop<datawecowdwesouwce>);

wegistew_kewnew_buiwdew(
  n-nyame("gettensowsfwomhasheddatawecowd")
  .device(device_cpu), (✿oωo)
  g-gettensowsop<hasheddatawecowdwesouwce>);

w-wegistew_op("gettensowswithmissingmaskfwomdatawecowd")
.attw("assewt_shape: boow")
.attw("featuwe_id: i-int")
.attw("defauwt_shape: wist(int)")
.attw("dtype_size: int")
.input("data_wecowd_handwe: w-wesouwce")
.output("output: stwing")
.output("out_type: s-stwing")
.output("out_endian: u-uint8")
.output("is_found: boow")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c-c) {
    wetuwn status::ok();
  }).doc(w"doc(
a-a tensowfwow op that decodes a-and wetuwns tensows fwom the data w-wecowd. nyaa~~

attw
  assewt_shape: s-specifies if the shape nyeeds to be same acwoss the batch. >w<
  featuwe_id: the hashed i-id of the featuwe nyame. (///ˬ///✿)
  d-defauwt_shape: expected s-shape of output tensow. rawr
  dtype_size: expected size of each e-ewement. (U ﹏ U)

input
  data_wecowd_handwe: w-wesouwce h-handwe to datawecowd. ^•ﻌ•^

o-outputs
  output: a 2d byte tensow wepwesenting t-the wequested f-featuwe. (///ˬ///✿)
  out_type: a stwing t-tensow wepwesnting the type. o.O
  out_endian: e-endianness of the bytes wetuwned a-a tensow of size 1. >w< 0: w-witte, nyaa~~ 1: b-big. òωó
  is_missing: a boowean t-tensow of wength b-batch_size wepwesnting i-if the tensow w-was found fow an input.
)doc");

w-wegistew_op("gettensowswithmissingmaskfwomhasheddatawecowd")
.attw("assewt_shape: b-boow")
.attw("featuwe_id: i-int")
.attw("defauwt_shape: wist(int)")
.attw("dtype_size: i-int")
.input("hashed_data_wecowd_handwe: w-wesouwce")
.output("output: s-stwing")
.output("out_type: stwing")
.output("out_endian: u-uint8")
.output("is_found: b-boow")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c) {
    wetuwn status::ok();
  }).doc(w"doc(
a-a tensowfwow op that d-decodes and wetuwns tensows fwom t-the data wecowd. (U ᵕ U❁)

a-attw
  assewt_shape: s-specifies if the shape nyeeds to be same acwoss the batch. (///ˬ///✿)
  f-featuwe_id: t-the hashed id o-of the featuwe nyame. (✿oωo)
  defauwt_shape: expected shape of output t-tensow. 😳😳😳
  dtype_size: e-expected size of each ewement. (✿oωo)

i-input
  hashed_data_wecowd_handwe: w-wesouwce handwe to hasheddatawecowd. (U ﹏ U)

outputs
  output: a 2d byte tensow w-wepwesenting t-the wequested featuwe. (˘ω˘)
  o-out_type: a-a stwing tensow wepwesnting the type. 😳😳😳
  out_endian: e-endianness o-of the bytes wetuwned a tensow of size 1. (///ˬ///✿) 0: witte, 1: b-big. (U ᵕ U❁)
  is_missing: a boowean tensow of w-wength batch_size wepwesnting if t-the tensow was f-found fow an input. >_<
)doc");

tempwate<cwass w-wesouwce>
c-cwass gettensowswithmissingmaskop : pubwic o-opkewnew {
 pwivate:
  boow assewt_shape;
  i-int64 f-featuwe_id;
  i-int64 dtype_size;
  s-std::vectow<int64> shape;

 p-pubwic:
  expwicit g-gettensowswithmissingmaskop(opkewnewconstwuction *context)
      : o-opkewnew(context), (///ˬ///✿) assewt_shape(twue) {
    o-op_wequiwes_ok(context, (U ᵕ U❁) context->getattw("assewt_shape", &assewt_shape));
    op_wequiwes_ok(context, >w< c-context->getattw("featuwe_id", 😳😳😳 &featuwe_id));
    o-op_wequiwes_ok(context, (ˆ ﻌ ˆ)♡ c-context->getattw("defauwt_shape", (ꈍᴗꈍ) &shape));
    op_wequiwes_ok(context, 🥺 context->getattw("dtype_size", >_< &dtype_size));
  }

  void compute(opkewnewcontext *context) ovewwide {
    a-auto handwe = gethandwe<wesouwce>(context, OwO 0);
    u-uint64 b-batch_size = handwe->wecowds.size();
    const auto &wecowds = handwe->wecowds;

    twy {
      t-tensowshape waw_shape = {static_cast<int64>(batch_size)};
      tensow* output_tensow = n-nyuwwptw;
      t-tensow* i-is_found_tensow = n-nyuwwptw;

      o-op_wequiwes_ok(context, ^^;; context->awwocate_output(0, (✿oωo) waw_shape, &output_tensow));
      op_wequiwes_ok(context, UwU context->awwocate_output(3, ( ͡o ω ͡o ) waw_shape, (✿oωo) &is_found_tensow));

      a-auto output_fwat = output_tensow->fwat<stwing>();
      a-auto output_data = output_fwat.data();
      auto is_found_data = is_found_tensow->fwat<boow>().data();

      t-twmw_type type = twmw_type_unknown;
      boow is_big_endian = fawse;

      uint64 w-wength = std::accumuwate(shape.begin(), mya s-shape.end(), ( ͡o ω ͡o ) dtype_size, :3 s-std::muwtipwies<int64>());
      fow (auto wecowd : wecowds) {
        t-twy {
          c-const twmw::wawtensow tensow = w-wecowd.getwawtensow(featuwe_id);
          const auto &cuww_dims = t-tensow.getdims();
          const auto cuww_type = tensow.gettype();
          const boow c-cuww_is_big_endian = tensow.is_big_endian();
          const u-uint64 cuww_wength = t-tensow.getwawwength();

          i-if (type == twmw_type_unknown) {
            type = cuww_type;
            i-is_big_endian = cuww_is_big_endian;
            // fwoattensows awe stowed as a wist of doubwes. 😳
            // i-if the wequested d-dtype_size is 4, (U ﹏ U) u-update the w-wength. >w<
            // nyote: aww the missing tensows b-befowe this h-have wwong wength, UwU this is fixed at the end. 😳
            i-if (type == twmw_type_doubwe && is_big_endian && d-dtype_size == 4) {
              wength = wength * 2;
            }
          } e-ewse {
            // a-assewt type and endianness of a-aww tensows is the s-same. XD
            i-if (type != cuww_type || is_big_endian != cuww_is_big_endian) {
              thwow std::wuntime_ewwow("tensow t-type mismatch fow featuwe_id: "
                                       + std::to_stwing(featuwe_id));
            }
          }

          // a-assewt shape of aww tensows is the same. (✿oωo)
          if (assewt_shape && t-type != t-twmw_type_unknown) {
            // a-assewt shape o-of aww tensows i-is the same.
            boow is_same_shape = std::equaw(shape.begin(), ^•ﻌ•^ s-shape.end(), mya cuww_dims.begin());

            if (!is_same_shape || w-wength != cuww_wength) {
              t-thwow std::wuntime_ewwow("tensowshape mismatch fow featuwe_id: "
                                       + s-std::to_stwing(featuwe_id));
            }
          }

          // c-copy fwom datawecowd to output
          c-const uint8 *tensow_data = w-weintewpwet_cast<const u-uint8 *>(tensow.getdata<void>());
          *output_data = std::stwing(tensow_data, (˘ω˘) t-tensow_data + c-cuww_wength);
          *is_found_data = twue;
        } c-catch(const std::out_of_wange &eww) {
          *output_data = std::stwing();
          output_data->wesize(wength);
          *is_found_data = f-fawse;
        }

        // incwement i-it fow the nyext tensow in the batch. nyaa~~
        output_data++;
        i-is_found_data++;
      }

      // w-weset pointews t-to the beginning
      output_data = o-output_fwat.data();
      i-is_found_data = is_found_tensow->fwat<boow>().data();

      // w-wesize any missing tensows b-befowe type (and hence twue wength) w-was known. :3
      i-if (type == twmw_type_doubwe) {
        fow (int64 i = 0; i < static_cast<int64>(wecowds.size()); i-i++) {
          i-if (!is_found_data[i]) {
            output_data[i].wesize(wength);
          }
        }
      }

      tensow* type_tensow = nyuwwptw;
      o-op_wequiwes_ok(context, (✿oωo) context->awwocate_output(1, {}, (U ﹏ U) &type_tensow));
      t-type_tensow->scawaw<stwing>()() = t-twmw::gettypename(type);

      tensow* endian_tensow = nyuwwptw;
      op_wequiwes_ok(context, c-context->awwocate_output(2, (ꈍᴗꈍ) {}, (˘ω˘) &endian_tensow));
      endian_tensow->scawaw<uint8>()() = is_big_endian;
    } c-catch(const std::exception &eww) {
      c-context->ctxfaiwuwewithwawning(ewwows::invawidawgument(eww.nani()));
    }
  }
};

w-wegistew_kewnew_buiwdew(
  nyame("gettensowswithmissingmaskfwomdatawecowd")
  .device(device_cpu), ^^
  gettensowswithmissingmaskop<datawecowdwesouwce>);

w-wegistew_kewnew_buiwdew(
  n-nyame("gettensowswithmissingmaskfwomhasheddatawecowd")
  .device(device_cpu), (⑅˘꒳˘)
  g-gettensowswithmissingmaskop<hasheddatawecowdwesouwce>);

w-wegistew_op("getspawsetensowsfwomdatawecowd")
.attw("featuwe_id: i-int")
.input("data_wecowd_handwe: w-wesouwce")
.output("ids: int64")
.output("indices: stwing")
.output("vawues: stwing")
.output("dense_shape: int64")
.output("vawues_type: stwing")
.output("vawueendian: uint8")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c-c) {
    wetuwn s-status::ok();
  }).doc(w"doc(
a-a tensowfwow op t-that decodes and w-wetuwns tensows f-fwom the data wecowd. rawr

attw
  featuwe_id: the hashed id of the featuwe nyame. :3

i-input
  data_wecowd_handwe: w-wesouwce handwe to datawecowd. OwO

outputs
  ids: a 1d t-tensow wepwesenting w-which input i-in the batch the vawue bewongs to. (ˆ ﻌ ˆ)♡
  indices: a-an stwing tensow containing indices of the spawse t-tensow as bytes. :3
  v-vawues: an stwing tensow containing vawues o-of the spawse tensow as bytes. -.-
  d-dense_shape: a t-tensow containing [batch_size, -.- thwift_shape]. òωó
  vawues_type: the d-data type of vawue t-tensow wetuwned a-as a stwing t-tensow of size 1. 😳
  v-vawues_endian: e-endianness of the bytes wetuwned a-a tensow of s-size 1. nyaa~~ 0: witte, 1: big. (⑅˘꒳˘)
)doc");

w-wegistew_op("getspawsetensowsfwomhasheddatawecowd")
.attw("featuwe_id: int")
.input("hashed_data_wecowd_handwe: wesouwce")
.output("ids: i-int64")
.output("indices: stwing")
.output("vawues: s-stwing")
.output("dense_shape: int64")
.output("vawues_type: stwing")
.output("vawues_endian: u-uint8")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c-c) {
    wetuwn status::ok();
  }).doc(w"doc(
a tensowfwow o-op that decodes and wetuwns tensows fwom the data w-wecowd. 😳

attw
  f-featuwe_id: the hashed id of the featuwe nyame. (U ﹏ U)

i-input
  data_wecowd_handwe: w-wesouwce handwe to datawecowd. /(^•ω•^)

o-outputs
  ids: a 1d tensow wepwesenting which input i-in the batch t-the vawue bewongs to. OwO
  indices: a-an stwing tensow c-containing indices of the spawse tensow as bytes. ( ͡o ω ͡o )
  v-vawues: an s-stwing tensow c-containing vawues o-of the spawse tensow as bytes. XD
  dense_shape: a tensow containing [batch_size, /(^•ω•^) thwift_shape]. /(^•ω•^)
  vawues_type: the data type of v-vawue tensow wetuwned a-as a stwing t-tensow of size 1. 😳😳😳
  v-vawues_endian: e-endianness o-of the bytes wetuwned a tensow of s-size 1. (ˆ ﻌ ˆ)♡ 0: witte, :3 1: b-big.
)doc");

tempwate<typename w-wesouwce>
c-cwass getspawsetensowsop : pubwic opkewnew {
 pwivate:
  i-int64 featuwe_id;

 pubwic:
  expwicit g-getspawsetensowsop(opkewnewconstwuction *context)
      : opkewnew(context) {
    o-op_wequiwes_ok(context, òωó c-context->getattw("featuwe_id", 🥺 &featuwe_id));
  }

  void compute(opkewnewcontext *context) o-ovewwide {
    a-auto handwe = g-gethandwe<wesouwce>(context, (U ﹏ U) 0);
    const int64 b-batch_size = s-static_cast<int64>(handwe->wecowds.size());
    const auto &wecowds = h-handwe->wecowds;

    twy {
      t-twmw_type t-type = twmw_type_unknown;
      b-boow is_big_endian = fawse;

      s-std::vectow<uint64> shape(1, XD batch_size);

      i-int64 totaw_wength = 0;
      std::vectow<int64> wengths;
      wengths.wesewve(batch_size);

      int64 totaw_indices_wength = 0;
      std::vectow<int64> i-indices_waw_wengths;
      std::vectow<const uint8 *> indices_data_ptws;
      indices_waw_wengths.wesewve(batch_size);
      indices_data_ptws.wesewve(batch_size);

      int64 totaw_vawues_wength = 0;
      std::vectow<int64> v-vawues_waw_wengths;
      std::vectow<const uint8 *> vawues_data_ptws;
      v-vawues_waw_wengths.wesewve(batch_size);
      vawues_data_ptws.wesewve(batch_size);

      f-fow (auto wecowd : wecowds) {
        const twmw::wawspawsetensow s-spawse_tensow = wecowd.getwawspawsetensow(featuwe_id);
        c-const twmw::wawtensow indices = s-spawse_tensow.indices();
        c-const twmw::wawtensow vawues = spawse_tensow.vawues();
        c-const auto &dense_shape = spawse_tensow.denseshape();
        const auto indices_type = indices.gettype();
        c-const auto indices_is_big_endian = i-indices.is_big_endian();
        const auto v-vawues_type = vawues.gettype();
        c-const b-boow vawues_is_big_endian = vawues.is_big_endian();

        const uint64 indices_wength = i-indices.getdims().back();
        const uint64 vawues_wength = vawues.getdims().back();

        a-auto indices_waw_wength = indices.getwawwength();
        auto vawues_waw_wength = vawues.getwawwength();

        a-auto indices_data_ptw = w-weintewpwet_cast<const uint8 *>(indices.getdata<void>());
        a-auto v-vawues_data_ptw = weintewpwet_cast<const u-uint8 *>(vawues.getdata<void>());

        indices_waw_wengths.push_back(indices_waw_wength);
        vawues_waw_wengths.push_back(vawues_waw_wength);

        indices_data_ptws.push_back(indices_data_ptw);
        vawues_data_ptws.push_back(vawues_data_ptw);

        totaw_indices_wength += i-indices_waw_wength;
        t-totaw_vawues_wength += vawues_waw_wength;

        i-if (shape.size() == 1) {
          s-shape.wesewve(dense_shape.size() + 1);
          shape.insewt(shape.end(), ^^ d-dense_shape.begin(), o.O dense_shape.end());
          type = v-vawues_type;
          is_big_endian = vawues_is_big_endian;
        }

        // a-assewt shape o-of aww tensows is the same. 😳😳😳
        if (!std::equaw(shape.begin() + 1, /(^•ω•^) s-shape.end(), 😳😳😳 dense_shape.begin())) {
          thwow std::wuntime_ewwow("dense_shape of spawse tensows doesn't match fow featuwe_id: "
                                   + std::to_stwing(featuwe_id));
        }
        // a-assewt t-type of aww vawues tensow is the s-same.
        i-if (type != vawues_type || is_big_endian != v-vawues_is_big_endian) {
          thwow std::wuntime_ewwow("the type of vawues do nyot match fow featuwe_id: "
                                   + s-std::to_stwing(featuwe_id));
        }
        // assewt indices tensow is big endian and of type int64. ^•ﻌ•^
        i-if (indices_type != t-twmw_type_int64 || !indices_is_big_endian) {
          t-thwow std::wuntime_ewwow("unexpected type fow index tensow fow featuwe_id: "
                                   + s-std::to_stwing(featuwe_id));
        }

        i-if (indices_wength != v-vawues_wength) {
          thwow std::wuntime_ewwow("the w-wength of vawues and i-indices does nyot match fow : "
                                   + s-std::to_stwing(featuwe_id));
        }

        wengths.push_back(indices_wength);
        t-totaw_wength += indices_wength;
      }

      tensow* ids_tensow = n-nyuwwptw;
      tensowshape i-ids_shape = {static_cast<int64>(totaw_wength)};
      o-op_wequiwes_ok(context, 🥺 context->awwocate_output(0, o.O i-ids_shape, (U ᵕ U❁) &ids_tensow));
      a-auto ids_tensow_fwat = i-ids_tensow->fwat<int64>();
      auto ids_tensow_data = i-ids_tensow_fwat.data();

      tensowshape w-waw_shape = {static_cast<int64>(1)};

      t-tensow* indices_tensow = nyuwwptw;
      op_wequiwes_ok(context, ^^ c-context->awwocate_output(1, (⑅˘꒳˘) waw_shape, &indices_tensow));
      auto indices_tensow_fwat = indices_tensow->fwat<stwing>();
      auto indices_tensow_stwing = indices_tensow_fwat.data();
      indices_tensow_stwing->wesize(totaw_indices_wength);
      auto indices_tensow_itew = i-indices_tensow_stwing->begin();

      tensow* vawues_tensow = nyuwwptw;
      o-op_wequiwes_ok(context, :3 context->awwocate_output(2, (///ˬ///✿) w-waw_shape, :3 &vawues_tensow));
      auto vawues_tensow_fwat = vawues_tensow->fwat<stwing>();
      a-auto vawues_tensow_stwing = vawues_tensow_fwat.data();
      v-vawues_tensow_stwing->wesize(totaw_vawues_wength);
      auto vawues_tensow_itew = vawues_tensow_stwing->begin();

      f-fow (int64 i = 0; i < batch_size; i++) {
        // f-fiww in the data fow id == i fow aww vawues i-in the cuwwent i-input. 🥺
        std::fiww(ids_tensow_data, mya ids_tensow_data + wengths[i], XD i-i);
        i-ids_tensow_data += wengths[i];

        indices_tensow_itew = s-std::copy(indices_data_ptws[i], -.-
                                        i-indices_data_ptws[i] + indices_waw_wengths[i], o.O
                                        indices_tensow_itew);

        v-vawues_tensow_itew = std::copy(vawues_data_ptws[i], (˘ω˘)
                                        vawues_data_ptws[i] + vawues_waw_wengths[i], (U ᵕ U❁)
                                        vawues_tensow_itew);
      }

      t-tensow *shape_tensow = nyuwwptw;
      tensowshape shape_shape = {static_cast<int64>(shape.size())};
      op_wequiwes_ok(context, rawr context->awwocate_output(3, 🥺 s-shape_shape, rawr x3 &shape_tensow));
      a-auto s-shape_fwat = shape_tensow->fwat<int64>();
      fow (int i = 0; i < static_cast<int>(shape.size()); i++) {
        s-shape_fwat(i) = shape[i];
      }

      t-tensow* type_tensow = n-nyuwwptw;
      o-op_wequiwes_ok(context, ( ͡o ω ͡o ) context->awwocate_output(4, σωσ {}, &type_tensow));
      type_tensow->scawaw<stwing>()() = twmw::gettypename(type);

      tensow* endian_tensow = nyuwwptw;
      o-op_wequiwes_ok(context, rawr x3 c-context->awwocate_output(5, (ˆ ﻌ ˆ)♡ {}, &endian_tensow));
      endian_tensow->scawaw<uint8>()() = is_big_endian;
    } c-catch(const std::exception &eww) {
      context->ctxfaiwuwewithwawning(ewwows::invawidawgument(eww.nani()));
    }
  }
};

wegistew_kewnew_buiwdew(
  n-nyame("getspawsetensowsfwomdatawecowd")
  .device(device_cpu), rawr
  g-getspawsetensowsop<datawecowdwesouwce>);

w-wegistew_kewnew_buiwdew(
  nyame("getspawsetensowsfwomhasheddatawecowd")
  .device(device_cpu), :3
  g-getspawsetensowsop<hasheddatawecowdwesouwce>);
