using UnityEngine;
using System;
using System.Collections;


public class Vector3TweenProperty : AbstractVector3TweenProperty, IGenericProperty
{
	public string propertyName { get; private set; }
	private Action<Vector3> _setter;
	
	
	public Vector3TweenProperty( string propertyName, Vector3 endValue, bool isRelative = false ) : base( endValue, isRelative )
	{
		this.propertyName = propertyName;
	}
	
	
	/// <summary>
	/// validation checks to make sure the target has a valid property with an accessible setter
	/// </summary>
	public override bool validateTarget( object target )
	{
		// cache the setter
		_setter = GoTweenUtils.setterForProperty<Action<Vector3>>( target, propertyName );
		return _setter != null;
	}

	
	public override void prepareForUse()
	{
		// retrieve the getter
		var getter = GoTweenUtils.getterForProperty<Func<Vector3>>( _ownerTween.target, propertyName );
		
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
		var vec = GoTweenUtils.unclampedVector3Lerp( _startValue, _diffValue, easedTime );
		
		_setter( vec );
	}

}
