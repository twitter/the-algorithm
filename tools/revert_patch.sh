#!/bin/sh
#
# revert_patch.sh - Reverts an enhancement patch
#

if [ "$#" -ne 1 ]
then
    echo "Usage: $0 patch_file"
    echo '    Reverts the changes made by an enhancement patch'
    exit 1
fi

read -p "Do you wish to revert the patch '$1'? [Y/N] " response
case "$response" in
    Y|y)
	patch -p1 -R < "$1"
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


