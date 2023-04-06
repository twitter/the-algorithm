using UnityEngine;
using System;
using System.Collections;


public class Vector2TweenProperty : AbstractTweenProperty, IGenericProperty
{
	public string propertyName { get; private set; }
	private Action<Vector2> _setter;
	
	protected Vector2 _originalEndValue;
	protected Vector2 _startValue;
	protected Vector2 _endValue;
	protected Vector2 _diffValue;
	
	
	public Vector2TweenProperty( string propertyName, Vector2 endValue, bool isRelative = false ) : base( isRelative )
	{
		this.propertyName = propertyName;
		_originalEndValue = endValue;
	}
	
	
	/// <summary>
	/// validation checks to make sure the target has a valid property with an accessible setter
	/// </summary>
	public override bool validateTarget( object target )
	{
		// cache the setter
		_setter = GoTweenUtils.setterForProperty<Action<Vector2>>( target, propertyName );
		return _setter != null;
	}

	
	public override void prepareForUse()
	{
		// retrieve the getter
		var getter = GoTweenUtils.getterForProperty<Func<Vector2>>( _ownerTween.target, propertyName );
		
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
		
		// prep the diff value
		if( _isRelative && !_ownerTween.isFrom )
			_diffValue = _endValue;
		else
			_diffValue = _endValue - _startValue;
	}

	
	public override void tick( float totalElapsedTime )
	{
		var easedTime = _easeFunction( totalElapsedTime, 0, 1, _ownerTween.duration );
		var vec = GoTweenUtils.unclampedVector2Lerp( _startValue, _diffValue, easedTime );
		
		_setter( vec );
	}

}
