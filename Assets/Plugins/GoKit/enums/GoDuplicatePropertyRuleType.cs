using UnityEngine;
using System.Collections;



/// <summary>
/// defines what should be done in the event that a TweenProperty is being added to Go when an existing TweenProperty
/// of the same type with the same target object is already present
/// </summary>
public enum GoDuplicatePropertyRuleType
{
	None, // dont bother checking or doing anything
	RemoveRunningProperty, // removes the property from the Tween that was already running
	DontAddCurrentProperty // leaves the original property intact and doesnt add the current property
}
