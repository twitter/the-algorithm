using UnityEngine;
using System.Collections;


public class EulerAnglesTweenProperty : AbstractVector3TweenProperty
{
	private bool _useLocalEulers;
	public bool useLocalEulers { get { return _useLocalEulers; } }
	
	public EulerAnglesTweenProperty( Vector3 endValue, bool isRelative = false, bool useLocalEulers = false ) : base( endValue, isRelative )
	{
		_useLocalEulers = useLocalEulers;
	}
	
	
	#region Object overrides
	
	public override int GetHashCode()
	{
		return base.GetHashCode();
	}
	
	
	public override bool Equals( object obj )
	{
		// start with a base check and then compare if we are both using local values
		if( base.Equals( obj ) )
			return this._useLocalEulers == ((EulerAnglesTweenProperty)obj)._useLocalEulers;
		
		// if we get here, we need to see if the other object is a rotation tween of the same kind
		var otherAsRotation = obj as RotationTweenProperty;
		if( otherAsRotation != null )
			return this._useLocalEulers == otherAsRotation.useLocalRotation;
		
		return false;
	}
	
	#endregion
	
	
	public override void prepareForUse()
	{
		_target = _ownerTween.target as Transform;
		
		_endValue = _originalEndValue;
		
		// swap the start and end if this is a from tween
		if( _ownerTween.isFrom )
		{
			_startValue = _endValue;
			
			if( _useLocalEulers )
				_endValue = _target.localEulerAngles;
			else
				_endValue = _target.eulerAngles;
		}
		else
		{
			if( _useLocalEulers )
				_startValue = _target.localEulerAngles;
			else
				_startValue = _target.eulerAngles;
		}
		
		base.prepareForUse();
	}
	
	
	public override void tick( float totalElapsedTime )
	{
		var easedTime = _easeFunction( totalElapsedTime, 0, 1, _ownerTween.duration );
		var vec = GoTweenUtils.unclampedVector3Lerp( _startValue, _diffValue, easedTime );
		
		if( _useLocalEulers )
			_target.localEulerAngles = vec;
		else
			_target.eulerAngles = vec;
	}

}
