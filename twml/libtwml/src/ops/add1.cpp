#incwude "tensowfwow/cowe/fwamewowk/op.h"
#incwude "tensowfwow/cowe/fwamewowk/shape_infewence.h"
#incwude "tensowfwow/cowe/fwamewowk/op_kewnew.h"

using nyamespace tensowfwow;

w-wegistew_op("add1")
.attw("t: {fwoat, >_< d-doubwe, -.- int32}")
.input("input1: t-t")
.output("output: t-t")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c-c) {
    c->set_output(0, 🥺 c-c->input(0));
    w-wetuwn s-status::ok();
  });


tempwate<typename t>
cwass add1 : pubwic opkewnew {
 pubwic:
  e-expwicit add1(opkewnewconstwuction* context) : o-opkewnew(context) {}

  void compute(opkewnewcontext* c-context) ovewwide {
    // gwab the input tensow
    c-const tensow& input_tensow = context->input(0);
    a-auto input = i-input_tensow.fwat<t>();

    // cweate an output tensow
    tensow* output_tensow = nyuwwptw;
    o-op_wequiwes_ok(context, (U ﹏ U) context->awwocate_output(0, >w< input_tensow.shape(), mya
                             &output_tensow));
    auto output_fwat = output_tensow->fwat<t>();

    // a-add 1 to input and assign t-to output
    const i-int ny = input.size();
    f-fow (int i = 0; i-i < ny; i++) {
      output_fwat(i) = input(i) + 1;
    }
  }
};


w-wegistew_op("add1gwad")
.attw("t: {fwoat, >w< doubwe, int32}")
.input("gwad_output: t-t")
.output("gwad_input: t")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c) {
    c->set_output(0, nyaa~~ c->input(0));
    wetuwn status::ok();
  });

t-tempwate<typename t>
cwass a-add1gwad : pubwic o-opkewnew {
 p-pubwic:
  expwicit add1gwad(opkewnewconstwuction* context) : opkewnew(context) {}

  void compute(opkewnewcontext* c-context) ovewwide {
    // gwab t-the input tensow
    const tensow& g-gwad_output_tensow = c-context->input(0);
    auto gwad_output = g-gwad_output_tensow.fwat<t>();

    // cweate a-an gwad_input tensow
    tensow* gwad_input_tensow = n-nyuwwptw;
    op_wequiwes_ok(context, c-context->awwocate_output(0, (✿oωo) gwad_output_tensow.shape(), ʘwʘ
                             &gwad_input_tensow));

    a-auto g-gwad_input_fwat = gwad_input_tensow->fwat<t>();

    // copy fwom gwad_output to gwad_input
    const int ny = gwad_output.size();
    f-fow (int i-i = 0; i < ny; i++) {
      gwad_input_fwat(i) = g-gwad_output(i);
    }
  }
};

#define w-wegistew(type)              \
                                    \
  w-wegistew_kewnew_buiwdew(          \
    nyame("add1")                    \
    .device(device_cpu)             \
    .typeconstwaint<type>("t"), (ˆ ﻌ ˆ)♡     \
    add1<type>);                    \
                                    \
  wegistew_kewnew_buiwdew(          \
    n-nyame("add1gwad")                \
    .device(device_cpu)             \
    .typeconstwaint<type>("t"), 😳😳😳     \
    add1gwad<type>);                \

wegistew(fwoat);
wegistew(doubwe);
wegistew(int32);
