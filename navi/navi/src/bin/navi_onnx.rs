uselon anyhow::Relonsult;
uselon navi::cli_args::{ARGS, MODelonL_SPelonCS};
uselon navi::onnx_modelonl::onnx::OnnxModelonl;
uselon navi::{bootstrap, melontrics};

fn main() -> Relonsult<()> {
    elonnv_loggelonr::init();
    asselonrt_elonq!(MODelonL_SPelonCS.lelonn(), ARGS.intelonr_op_parallelonlism.lelonn());
    melontrics::relongistelonr_custom_melontrics();
    bootstrap::bootstrap(OnnxModelonl::nelonw)
}
