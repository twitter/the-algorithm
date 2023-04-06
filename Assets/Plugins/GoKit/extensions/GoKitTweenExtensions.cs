using UnityEngine;
using System.Collections;


public static class GoKitTweenExtensions
{
	#region Transform extensions
	
	// to tweens
    public static GoTween rotationTo( this Transform self, float duration, Vector3 endValue, bool isRelative = false )
    {
        return Go.to( self, duration, new GoTweenConfig().rotation( endValue, isRelative ) );
    }


    public static GoTween localRotationTo( this Transform self, float duration, Vector3 endValue, bool isRelative = false )
    {
        return Go.to( self, duration, new GoTweenConfig().localRotation( endValue, isRelative ) );
    }


	public static GoTween eulerAnglesTo( this Transform self, float duration, Vector3 endValue, bool isRelative = false )
	{
		return Go.to( self, duration, new GoTweenConfig().eulerAngles( endValue, isRelative ) );
	}
	
	
	public static GoTween localEulerAnglesTo( this Transform self, float duration, Vector3 endValue, bool isRelative = false )
	{
		return Go.to( self, duration, new GoTweenConfig().localEulerAngles( endValue, isRelative ) );
	}
	
	
	public static GoTween positionTo( this Transform self, float duration, Vector3 endValue, bool isRelative = false )
	{
		return Go.to( self, duration, new GoTweenConfig().position( endValue, isRelative ) );
	}
	
	
	public static GoTween localPositionTo( this Transform self, float duration, Vector3 endValue, bool isRelative = false )
	{
		return Go.to( self, duration, new GoTweenConfig().localPosition( endValue, isRelative ) );
	}
	
	
	public static GoTween scaleTo( this Transform self, float duration, float endValue, bool isRelative = false )
	{
		return self.scaleTo( duration, new Vector3( endValue, endValue, endValue ), isRelative );
	}
	
	
	public static GoTween scaleTo( this Transform self, float duration, Vector3 endValue, bool isRelative = false )
	{
		return Go.to( self, duration, new GoTweenConfig().scale( endValue, isRelative ) );
	}
	
	
	public static GoTween shake( this Transform self, float duration, Vector3 shakeMagnitude, GoShakeType shakeType = GoShakeType.Position, int frameMod = 1, bool useLocalProperties = false )
	{
		return Go.to( self, duration, new GoTweenConfig().shake( shakeMagnitude, shakeType, frameMod, useLocalProperties ) );
	}
	
	
	// from tweens
    public static GoTween rotationFrom( this Transform self, float duration, Vector3 endValue, bool isRelative = false )
    {
        return Go.from( self, duration, new GoTweenConfig().rotation( endValue, isRelative ) );
    }


    public static GoTween localRotationFrom( this Transform self, float duration, Vector3 endValue, bool isRelative = false )
    {
        return Go.from( self, duration, new GoTweenConfig().localRotation( endValue, isRelative ) );
    }
	

	public static GoTween eulerAnglesFrom( this Transform self, float duration, Vector3 endValue, bool isRelative = false )
	{
		return Go.from( self, duration, new GoTweenConfig().eulerAngles( endValue, isRelative ) );
	}
	
	
	public static GoTween localEulerAnglesFrom( this Transform self, float duration, Vector3 endValue, bool isRelative = false )
	{
		return Go.from( self, duration, new GoTweenConfig().localEulerAngles( endValue, isRelative ) );
	}
	
	
	public static GoTween positionFrom( this Transform self, float duration, Vector3 endValue, bool isRelative = false )
	{
		return Go.from( self, duration, new GoTweenConfig().position( endValue, isRelative ) );
	}
	
	
	public static GoTween localPositionFrom( this Transform self, float duration, Vector3 endValue, bool isRelative = false )
	{
		return Go.from( self, duration, new GoTweenConfig().localPosition( endValue, isRelative ) );
	}
	
	
	public static GoTween scaleFrom( this Transform self, float duration, Vector3 endValue, bool isRelative = false )
	{
		return Go.from( self, duration, new GoTweenConfig().scale( endValue, isRelative ) );
	}
	
	#endregion
	
	
	#region Material extensions
	
	public static GoTween colorTo( this Material self, float duration, Color endValue, string colorName = "_Color" )
	{
		return Go.to( self, duration, new GoTweenConfig().materialColor( endValue, colorName ) );
	}
	
	
	public static GoTween colorFrom( this Material self, float duration, Color endValue, string colorName = "_Color" )
	{
		return Go.from( self, duration, new GoTweenConfig().materialColor( endValue, colorName ) );
	}
	
	#endregion

}
