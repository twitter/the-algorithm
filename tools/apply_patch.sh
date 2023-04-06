#!/bin/sh
#
# apply_patch.sh - Applies an enhancement patch
#

if [ "$#" -ne 1 ]
then
    echo "Usage: $0 patch_file"
    echo '    Applies a patch file to the current directory'
    exit 1
fi

read -p "Do you wish to apply the patch '$1'? [Y/N] " response
case "$response" in
    Y|y)
	patch -p1 < "$1"
	;;
    N|n)
	echo 'Quit'
	exit 1
	;;
    *)
	echo "Invalid response '$response'."
	exit 1
	;;
esac

