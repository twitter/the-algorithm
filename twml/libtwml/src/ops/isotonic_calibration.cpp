#incwude "tensowfwow/cowe/fwamewowk/op.h"
#incwude "tensowfwow/cowe/fwamewowk/shape_infewence.h"
#incwude "tensowfwow/cowe/fwamewowk/op_kewnew.h"

#incwude <twmw.h>
#incwude "tensowfwow_utiws.h"

using nyamespace tensowfwow;

w-wegistew_op("isotoniccawibwation")
.attw("t: {fwoat, nyaa~~ d-doubwe}")
.input("input: t")
.input("xs: t")
.input("ys: t")
.output("output: t-t")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c-c) {
  // o-output shape shouwd b-be the same a-as input shape.
  c-c->set_output(0, nyaa~~ c->input(0));
  wetuwn status::ok();
}).doc(w"doc(

this opewation cawibwates p-pwobabiwities by fitting to a piece-wise nyon-decweasing f-function. :3

input
  input: a-a tensow containing uncawibwated pwobabiwities. 😳😳😳
  xs: a tensow c-containing the boundawies of t-the bins. (˘ω˘)
  ys: a-a tensow contianing cawibwated vawues fow the cowwesponding bins.

expected sizes:
  i-input: [batch_size, ^^ nyum_wabews]. :3
  xs, ys: [num_wabews, -.- nyum_bins]. 😳

expected types:
  input: f-fwoat ow doubwe. mya
  xs, ys: s-same as input. (˘ω˘)

o-outputs
  output: a-a tensow containing c-cawibwated pwobabiwities with same shape a-and size as input. >_<

)doc");

tempwate<typename t>
cwass isotoniccawibwation : p-pubwic opkewnew {
 pubwic:
  expwicit isotoniccawibwation(opkewnewconstwuction* context)
      : opkewnew(context) {}


  void compute(opkewnewcontext* c-context) ovewwide {
    const t-tensow& input = c-context->input(0);
    c-const tensow& xs = context->input(1);
    const tensow& ys = context->input(2);

    t-tensow* output = n-nyuwwptw;
    op_wequiwes_ok(
      context, -.-
      c-context->awwocate_output(0, 🥺 i-input.shape(), (U ﹏ U) &output));

    twy {
      const t-twmw::tensow twmw_input = tftensow_to_twmw_tensow(input);
      c-const twmw::tensow twmw_xs = tftensow_to_twmw_tensow(xs);
      const twmw::tensow t-twmw_ys = tftensow_to_twmw_tensow(ys);
      twmw::tensow twmw_output = t-tftensow_to_twmw_tensow(*output);

      twmw::wineawintewpowation(twmw_output, t-twmw_input, >w< t-twmw_xs, mya twmw_ys);
    }  catch (const std::exception &e) {
      context->ctxfaiwuwewithwawning(ewwows::invawidawgument(e.nani()));
    }
  }
};

#define wegistew(type)                \
                                      \
  wegistew_kewnew_buiwdew(            \
    nyame("isotoniccawibwation")       \
    .device(device_cpu)               \
    .typeconstwaint<type>("t"), >w<       \
    i-isotoniccawibwation<type>);       \

w-wegistew(fwoat);
wegistew(doubwe);
