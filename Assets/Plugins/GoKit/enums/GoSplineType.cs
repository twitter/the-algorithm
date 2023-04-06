using UnityEngine;
using System.Collections;


public enum GoSplineType
{
	StraightLine, // 2 points
	QuadraticBezier, // 3 points
	CubicBezier, // 4 points
	CatmullRom // 5+ points
}
