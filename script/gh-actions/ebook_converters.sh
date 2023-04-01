#!/bin/bash

# We can't use apt-get to install wkhtmltopdf because we use several options
# (--log-level and --disable-smart-shrinking) that are not available on the
# version currently available through apt-get. So we have to install
# xfonts-75dpi and xfonts-base (which are dependencies for wkhtmltox) through
# apt-get, but install wkhtmltox itself through a direct link to the package.

sudo apt-get install -y calibre zip xfonts-75dpi xfonts-base

mkdir -p wkhtmltopdf
pushd wkhtmltopdf

FILE="wkhtmltox_0.12.5-1.bionic_amd64.deb"

if [[ ! -e $FILE ]]; then
  wget -N https://media.archiveofourown.org/systems/$FILE
fi

sudo dpkg -i $FILE

popd

ebook-convert --version && wkhtmltopdf --version
