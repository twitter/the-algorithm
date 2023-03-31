{
  description = "Twitter development environment";

  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs";
    flake-utils.url = "github:numtide/flake-utils";
    devenv.url = "github:cachix/devenv/latest";
    pre-commit-hooks-nix.url = "github:cachix/pre-commit-hooks.nix";
  };

  outputs = { self, nixpkgs, flake-utils, devenv, pre-commit-hooks-nix, ... } @ inputs:
    flake-utils.lib.eachSystem [
      "x86_64-linux"
      "x86_64-darwin"
      "aarch64-linux"
      "aarch64-darwin"
    ]
      (system:
        let
          pkgs = import nixpkgs { inherit system; };
        in
        rec {

          hooks = import ./pre-commit-hooks.nix {
            inherit pkgs;
          };

          checks = {
            pre-commit-check = pre-commit-hooks-nix.lib.${system}.run {
              src = ./.;
              hooks = hooks;
            };
          };

          devShells = {
            default = mkTwitterShell;
          };

          mkTwitterShell = devenv.lib.mkShell {
            inherit inputs pkgs;

            modules = [{
              pre-commit.hooks = hooks;

              scripts.bazel.exec = "${pkgs.bazelisk}/bin/bazelisk";

              packages = [
                pkgs.git
                pkgs.pre-commit
                pkgs.which
              ];

              enterShell = ''
                # Remove potential previous Bazel aliases.
                [[ $(type -t bazel) == "alias" ]] && unalias bazel

                # Prettier color output for the ls command.
                alias ls='ls --color=auto'
              '';
            }];
          };
        });
}
