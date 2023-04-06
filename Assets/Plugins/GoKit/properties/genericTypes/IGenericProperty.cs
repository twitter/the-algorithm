using UnityEngine;
using System.Collections;


/// <summary>
/// interface that simply defines that a generic property tween must have a propertyName. this has the side effect
/// of letting us easily compare generic property tweens vs stongly typed tweens
/// </summary>
public interface IGenericProperty
{
	string propertyName { get; }
}
