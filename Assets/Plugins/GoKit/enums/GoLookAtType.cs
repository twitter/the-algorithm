using UnityEngine;
using System.Collections;


/// <summary>
/// used by paths to identify what the object being tweened should look at
/// </summary>
public enum GoLookAtType
{
	None,
	NextPathNode,
	TargetTransform
}
