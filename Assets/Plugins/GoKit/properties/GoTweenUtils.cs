using UnityEngine;
using System;
using System.Collections;
using System.Reflection;


public static class GoTweenUtils
{
	/// <summary>
	/// fetches the actual function for the given ease type
	/// </summary>
	public static Func<float,float,float,float,float> easeFunctionForType( GoEaseType easeType )
	{
		switch( easeType )
		{
			case GoEaseType.Linear:
				return GoEaseLinear.EaseNone;
			
			case GoEaseType.BackIn:
				return GoEaseBack.EaseIn;
			case GoEaseType.BackOut:
				return GoEaseBack.EaseOut;
			case GoEaseType.BackInOut:
				return GoEaseBack.EaseInOut;
			
			case GoEaseType.BounceIn:
				return GoEaseBounce.EaseIn;
			case GoEaseType.BounceOut:
				return GoEaseBounce.EaseOut;
			case GoEaseType.BounceInOut:
				return GoEaseBounce.EaseInOut;
			
			case GoEaseType.CircIn:
				return GoEaseCircular.EaseIn;
			case GoEaseType.CircOut:
				return GoEaseCircular.EaseOut;
			case GoEaseType.CircInOut:
				return GoEaseCircular.EaseInOut;
			
			case GoEaseType.CubicIn:
				return GoEaseCubic.EaseIn;
			case GoEaseType.CubicOut:
				return GoEaseCubic.EaseOut;
			case GoEaseType.CubicInOut:
				return GoEaseCubic.EaseInOut;
			
			case GoEaseType.ElasticIn:
				return GoEaseElastic.EaseIn;
			case GoEaseType.ElasticOut:
				return GoEaseElastic.EaseOut;
			case GoEaseType.ElasticInOut:
				return GoEaseElastic.EaseInOut;
			case GoEaseType.Punch:
				return GoEaseElastic.Punch;
			
			case GoEaseType.ExpoIn:
				return GoEaseExponential.EaseIn;
			case GoEaseType.ExpoOut:
				return GoEaseExponential.EaseOut;
			case GoEaseType.ExpoInOut:
				return GoEaseExponential.EaseInOut;
			
			case GoEaseType.QuadIn:
				return GoEaseQuadratic.EaseIn;
			case GoEaseType.QuadOut:
				return GoEaseQuadratic.EaseOut;
			case GoEaseType.QuadInOut:
				return GoEaseQuadratic.EaseInOut;
			
			case GoEaseType.QuartIn:
				return GoEaseQuartic.EaseIn;
			case GoEaseType.QuartOut:
				return GoEaseQuartic.EaseOut;
			case GoEaseType.QuartInOut:
				return GoEaseQuartic.EaseInOut;
			
			case GoEaseType.QuintIn:
				return GoEaseQuintic.EaseIn;
			case GoEaseType.QuintOut:
				return GoEaseQuintic.EaseOut;
			case GoEaseType.QuintInOut:
				return GoEaseQuintic.EaseInOut;
			
			case GoEaseType.SineIn:
				return GoEaseSinusoidal.EaseIn;
			case GoEaseType.SineOut:
				return GoEaseSinusoidal.EaseOut;
			case GoEaseType.SineInOut:
				return GoEaseSinusoidal.EaseInOut;
		}
		
		return GoEaseLinear.EaseNone;
	}
	
	
	/// <summary>
	/// either returns a super fast Delegate to set the given property or null if it couldn't be found
	/// via reflection
	/// </summary>
	public static T setterForProperty<T>( System.Object targetObject, string propertyName )
	{
			// first get the property
#if NETFX_CORE
			var propInfo = targetObject.GetType().GetRuntimeProperty( propertyName );
#else
			var propInfo = targetObject.GetType().GetProperty( propertyName );
#endif
			
			if( propInfo == null )
			{
				Debug.Log( "could not find property with name: " + propertyName );
				return default( T );
			}
			
#if NETFX_CORE
			// Windows Phone/Store new API
			return (T)(object)propInfo.SetMethod.CreateDelegate( typeof( T ), targetObject );
#else
			return (T)(object)Delegate.CreateDelegate( typeof( T ), targetObject, propInfo.GetSetMethod() );
#endif
	}
	
	
	/// <summary>
	/// either returns a super fast Delegate to get the given property or null if it couldn't be found
	/// via reflection
	/// </summary>
	public static T getterForProperty<T>( System.Object targetObject, string propertyName )
	{
			// first get the property
#if NETFX_CORE
			var propInfo = targetObject.GetType().GetRuntimeProperty( propertyName );
#else
			var propInfo = targetObject.GetType().GetProperty( propertyName );
#endif
			
			if( propInfo == null )
			{
				Debug.Log( "could not find property with name: " + propertyName );
				return default( T );
			}
			
#if NETFX_CORE
			// Windows Phone/Store new API
			return (T)(object)propInfo.GetMethod.CreateDelegate( typeof( T ), targetObject );
#else
			return (T)(object)Delegate.CreateDelegate( typeof( T ), targetObject, propInfo.GetGetMethod() );
#endif
	}
	
	
	#region math functions
	
	/// <summary>
	/// note for all lerps: normally a lerp would be something like the following:
	/// val1 + ( val2 - val1 ) * t
	/// or in more familiar terms:
	/// start + ( end - start ) * t
	///
	/// when lerping relatively, the formula simplifies to:
	/// start + end * t
	///
	/// for all the unclamped lerps in this class the diff value is precalculated and cached. that means these arent like normal
	/// lerps where you pass in the start and end values. the "diff" paramter in each method should be either the cached
	/// ( end - start ) for non-relative tweens or just end for relative tweens (that are not "from" tweens)
	/// </summary>
	
	
	/// <summary>
	/// unclamped lerp from c1 to c2. diff should be c2 - c1 (or just c2 for relative lerps)
	/// </summary>
	public static Color unclampedColorLerp( Color c1, Color diff, float value )
	{
        return new Color
		(
			c1.r + diff.r * value,
			c1.g + diff.g * value,
			c1.b + diff.b * value,
			c1.a + diff.a * value
		);
    }
	
	
	/// <summary>
	/// unclamped lerp from v1 to v2. diff should be v2 - v1 (or just v2 for relative lerps)
	/// </summary>
    public static Vector2 unclampedVector2Lerp( Vector2 v1, Vector2 diff, float value )
	{
        return new Vector2
		(
			v1.x + diff.x * value,
            v1.y + diff.y * value
		);
    }

	
	/// <summary>
	/// unclamped lerp from v1 to v2. diff should be v2 - v1 (or just v2 for relative lerps)
	/// </summary>
    public static Vector3 unclampedVector3Lerp( Vector3 v1, Vector3 diff, float value )
	{
        return new Vector3
		(
			v1.x + diff.x * value,
            v1.y + diff.y * value,
            v1.z + diff.z * value
		);
		
		/*
        return new Vector3
		(
			v1.x + ( v2.x - v1.x ) * value,
			v1.y + ( v2.y - v1.y ) * value,
			v1.z + ( v2.z - v1.z ) * value
		);
		*/
    }

	
	/// <summary>
	/// unclamped lerp from v1 to v2. diff should be v2 - v1 (or just v2 for relative lerps)
	/// </summary>
    public static Vector4 unclampedVector4Lerp( Vector4 v1, Vector4 diff, float value )
	{
        return new Vector4
		(
			v1.x + diff.x * value,
            v1.y + diff.y * value,
            v1.z + diff.z * value,
			v1.w + diff.w * value
		);
    }

	#endregion
	
}
