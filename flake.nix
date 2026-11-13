{
  description = "Gradle flake";

  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs?ref=nixos-26.05";
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs = { self, nixpkgs, flake-utils }:
    flake-utils.lib.eachDefaultSystem (system:
      let
        pkgs = import nixpkgs { inherit system; };
        jdk = pkgs.jdk25;
        fhs = pkgs.buildFHSEnv {
          name = "gradle-env";
          targetPkgs = pkgs: with pkgs; [
            gradle
            jdk
          ];
        };
      in {
        devShells.default = pkgs.mkShell {
          nativeBuildInputs = [fhs];
          shellHook = ''
            export PS1=" [gradle-env]$ "
            export JAVA_HOME=${jdk}
            echo "Gradle development environmet ready!"
            if [ -z "$IN_GRADLE_FHS_ENV" ]; then
              export IN_GRADLE_FHS_ENV=1
              exec gradle-env
            fi
          '';
        };
      }
    );
}

