using UnityEngine;
using System.Collections;


/// <summary>
/// tweens scale along a path at constant speed between nodes. isRelative makes the path scale
/// relative to the start scale of the object. a "from" tween will reverse the path and make the start
/// scale be the last node in the path.
/// </summary>
public class ScalePathTweenProperty : AbstractTweenProperty
{
	protected Transform _target;
	protected Vector3 _startValue;
	
	private GoSpline _path;
	
	
	public ScalePathTweenProperty( GoSpline path, bool isRelative = false ) : base( isRelative )
	{
		_path = path;
	}
	
	
	#region Object overrides
	
	public override int GetHashCode()
	{
		return base.GetHashCode();
	}
	
	
	public override bool Equals( object obj )
	{
		// if base already determined that we are equal no need to check further
		if( base.Equals( obj ) )
			return true;
		
		// we can be equal if the other object is a ScaleTweenProperty
		return obj.GetType() == typeof( ScaleTweenProperty );
	}
	
	#endregion
	
	
	public override void prepareForUse()
	{
		_target = _ownerTween.target as Transform;
		
		// if this is a from tween first reverse the path then build it
		if( _ownerTween.isFrom )
			_path.reverseNodes();
		else
			_path.unreverseNodes();
		
		_path.buildPath();
		
		// a from tween means the start value is the last node
		if( _ownerTween.isFrom )
			_startValue = _path.getLastNode();
		else
			_startValue = _target.localScale;
	}
	

	public override void tick( float totalElapsedTime )
	{
		var easedTime = _easeFunction( totalElapsedTime, 0, 1, _ownerTween.duration );
		var vec = _path.getPointOnPath( easedTime );
		
		// if we are relative, add the vec to our startValue
		if( _isRelative )
			vec += _startValue;
	
		_target.localScale = vec;
	}

}
