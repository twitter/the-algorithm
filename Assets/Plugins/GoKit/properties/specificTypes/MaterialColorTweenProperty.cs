using UnityEngine;
using System.Collections;


public class MaterialColorTweenProperty : AbstractColorTweenProperty
{
	private string _materialColorName;
	
	
	public MaterialColorTweenProperty( Color endValue, string colorName = "_Color", bool isRelative = false ) : base( endValue, isRelative )
	{
		_materialColorName = colorName;
	}
	
	
	#region Object overrides
	
	public override int GetHashCode()
	{
		return base.GetHashCode();
	}
	
	
	public override bool Equals( object obj )
	{
		// start with a base check and then compare our material names
		if( base.Equals( obj ) )
			return this._materialColorName == ((MaterialColorTweenProperty)obj)._materialColorName;
		
		return false;
	}
	
	#endregion
	

	public override void prepareForUse()
	{
		_endValue = _originalEndValue;
		
		// if this is a from tween we need to swap the start and end values
		if( _ownerTween.isFrom )
		{
			_startValue = _endValue;
			_endValue = _target.GetColor( _materialColorName );
		}
		else
		{
			_startValue = _target.GetColor( _materialColorName );
		}
		
		base.prepareForUse();
	}
	
	
	public override void tick( float totalElapsedTime )
	{
		var easedTime = _easeFunction( totalElapsedTime, 0, 1, _ownerTween.duration );
		var color = GoTweenUtils.unclampedColorLerp( _startValue, _diffValue, easedTime );
		
		_target.SetColor( _materialColorName, color );
	}

}
