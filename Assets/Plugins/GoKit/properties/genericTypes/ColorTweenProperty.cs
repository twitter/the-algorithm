using UnityEngine;
using System;
using System.Collections;


public class ColorTweenProperty : AbstractColorTweenProperty, IGenericProperty
{
	public string propertyName { get; private set; }
	private Action<Color> _setter;


	public ColorTweenProperty( string propertyName, Color endValue, bool isRelative = false ) : base( endValue, isRelative )
	{
		this.propertyName = propertyName;
	}


	/// <summary>
	/// validation checks to make sure the target has a valid property with an accessible setter
	/// </summary>
	public override bool validateTarget( object target )
	{
		// cache the setter
		_setter = GoTweenUtils.setterForProperty<Action<Color>>( target, propertyName );
		return _setter != null;
	}
	
	
	public override void prepareForUse()
	{
		// retrieve the getter
		var getter = GoTweenUtils.getterForProperty<Func<Color>>( _ownerTween.target, propertyName );
		
		_endValue = _originalEndValue;
		
		// if this is a from tween we need to swap the start and end values
		if( _ownerTween.isFrom )
		{
			_startValue = _endValue;
			_endValue = getter();
		}
		else
		{
			_startValue = getter();
		}
		
		base.prepareForUse();
	}
	
	
	public override void tick( float totalElapsedTime )
	{
		var easedTime = _easeFunction( totalElapsedTime, 0, 1, _ownerTween.duration );
		var newColor = GoTweenUtils.unclampedColorLerp( _startValue, _diffValue, easedTime );
		
		_setter( newColor );
	}

}
