using UnityEngine;
using System;
using System.Collections;


public class IntTweenProperty : AbstractTweenProperty, IGenericProperty
{
	public string propertyName { get; private set; }
	
	private Action<int> _setter;
	
	protected int _originalEndValue;
	protected int _startValue;
	protected int _endValue;
	protected int _diffValue;
	
	
	public IntTweenProperty( string propertyName, int endValue, bool isRelative = false ) : base( isRelative )
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
		_setter = GoTweenUtils.setterForProperty<Action<int>>( target, propertyName );
		return _setter != null;
	}
	

	public override void prepareForUse()
	{
		// retrieve the getter
		var getter = GoTweenUtils.getterForProperty<Func<int>>( _ownerTween.target, propertyName );
		
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
		var easedValue = _easeFunction( totalElapsedTime, _startValue, _diffValue, _ownerTween.duration );
		_setter( (int)Math.Round( easedValue ) );
	}

}
