using UnityEngine;
using System.Collections;


[System.Flags]
public enum GoShakeType
{
    Position 	= ( 1 << 0 ),
    Scale 		= ( 1 << 1 ),
    Eulers 		= ( 1 << 2 )
}