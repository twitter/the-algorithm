# Reddit installer development instructions

This folder contains all of the installation scripts required to build reddit on a stock Ubuntu 14.04 (trusty) box.  Originally all of this was included in `../install-reddit.sh` but the need to fork the image into an base installer for testing as well as a full installer for local use meant some reorganization and fracturing of the original script.

When making updates to any of these files, Travis will test out the minimal install in `travis.sh` but not the full install.  *Please test on a fresh VM, and preferably using a modified web-install instructions:*

```bash
wget https://raw.github.com/$GITHUBUSER/reddit/$TESTBRANCH/install-reddit.sh
chmod +x install-reddit.sh
./install-reddit.sh
```

This will ensure that the installation is tested in the most basic installation scenario.

**NOTE** if you find yourself adding files to this folder, please update `$NEEDED` in `../install-reddit.sh` to reflect the addition.  
