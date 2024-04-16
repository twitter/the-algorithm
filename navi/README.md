# Nav : H gh-Performance Mach ne Learn ng Serv ng Server  n Rust

Nav   s a h gh-performance, versat le mach ne learn ng serv ng server  mple nted  n Rust and ta lored for product on usage.  's des gned to eff c ently serve w h n t  Tw ter tech stack, offer ng top-notch performance wh le focus ng on core features.

## Key Features

- **M n mal st Des gn Opt m zed for Product on Use Cases**: Nav  del vers ultra-h gh performance, stab l y, and ava lab l y, eng neered to handle real-world appl cat on demands w h a streaml ned codebase.
- **gRPC AP  Compat b l y w h TensorFlow Serv ng**: Seamless  ntegrat on w h ex st ng TensorFlow Serv ng cl ents v a  s gRPC AP , enabl ng easy  ntegrat on, smooth deploy nt, and scal ng  n product on env ron nts.
- **Plug n Arch ecture for D fferent Runt  s**: Nav 's pluggable arch ecture supports var ous mach ne learn ng runt  s, prov d ng adaptab l y and extens b l y for d verse use cases. Out-of-t -box support  s ava lable for TensorFlow and Onnx Runt  , w h PyTorch  n an exper  ntal state.

## Current State

Wh le Nav 's features may not be as compre ns ve as  s open-s ce counterparts,  s performance-f rst m ndset makes   h ghly eff c ent. 
- Nav  for TensorFlow  s currently t  most feature-complete, support ng mult ple  nput tensors of d fferent types (float,  nt, str ng, etc.).
- Nav  for Onnx pr mar ly supports one  nput tensor of type str ng, used  n Tw ter's ho  recom ndat on w h a propr etary BatchPred ctRequest format.
- Nav  for Pytorch  s comp lable and runnable but not yet product on-ready  n terms of performance and stab l y.

## D rectory Structure

- `nav `: T  ma n code repos ory for Nav 
- `dr_transform`: Tw ter-spec f c converter that converts BatchPred ct onRequest Thr ft to ndarray
- `segdense`: Tw ter-spec f c conf g to spec fy how to retr eve feature values from BatchPred ct onRequest
- `thr ft_bpr_adapter`: generated thr ft code for BatchPred ct onRequest

## Content
  have  ncluded all *.rs s ce code f les that make up t  ma n Nav  b nar es for   to exam ne. Ho ver,   have not  ncluded t  test and benchmark code, or var ous conf gurat on f les, due to data secur y concerns.

## Run
 n nav /nav ,   can run t  follow ng commands:
- `scr pts/run_tf2.sh` for [TensorFlow](https://www.tensorflow.org/)
- `scr pts/run_onnx.sh` for [Onnx](https://onnx.a /)

Do note that   need to create a models d rectory and create so  vers ons, preferably us ng epoch t  , e.g., `1679693908377`.
so t  models structure looks l ke:
  models/
       - b_cl ck
        - 1809000
        - 1809010

## Bu ld
  can adapt t  above scr pts to bu ld us ng Cargo.
