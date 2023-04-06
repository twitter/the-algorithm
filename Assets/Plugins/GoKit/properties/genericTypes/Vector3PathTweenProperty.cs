using UnityEngine;
using System;
using System.Collections;


public class Vector3PathTweenProperty : AbstractTweenProperty, IGenericProperty
{
	public string propertyName { get; private set; }
	
	private Action<Vector3> _setter;
	private GoSpline _path;
	private Vector3 _startValue;
	
	
	public Vector3PathTweenProperty( string propertyName, GoSpline path, bool isRelative = false ) : base( isRelative )
	{
		this.propertyName = propertyName;
		_path = path;
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
		// if this is a from tween first reverse the path then build it. we unreverse in case we were copied
		if( _ownerTween.isFrom )
			_path.reverseNodes();
		else
			_path.unreverseNodes();
		
		_path.buildPath();
		
		// a from tween means the start value is the last node
		if( _ownerTween.isFrom )
		{
			_startValue = _path.getLastNode();
		}
		else
		{
			// retrieve the getter only when needed
			var getter = GoTweenUtils.getterForProperty<Func<Vector3>>( _ownerTween.target, propertyName );
			_startValue = getter();
		}
	}
	

	public override void tick( float totalElapsedTime )
	{
		var easedTime = _easeFunction( totalElapsedTime, 0, 1, _ownerTween.duration );
		var vec = _path.getPointOnPath( easedTime );
		
		// if we are relative, add the vec to our startValue
		if( _isRelative )
			vec += _startValue;
		
		_setter( vec );
	}

}
