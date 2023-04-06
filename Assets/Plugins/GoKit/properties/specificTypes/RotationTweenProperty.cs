using UnityEngine;
using System.Collections;


public class RotationTweenProperty : AbstractVector3TweenProperty
{
	private bool _useLocalRotation;
	public bool useLocalRotation { get { return _useLocalRotation; } }
	
	
	public RotationTweenProperty( Vector3 endValue, bool isRelative = false, bool useLocalRotation = false ) : base( endValue, isRelative )
	{
		_useLocalRotation = useLocalRotation;
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
			return this._useLocalRotation == ((RotationTweenProperty)obj)._useLocalRotation;
		
		// if we get here, we need to see if the other object is a eulerAngles tween of the same kind
		var otherAsEuler = obj as EulerAnglesTweenProperty;
		if( otherAsEuler != null )
			return this._useLocalRotation == otherAsEuler.useLocalEulers;
		
		return false;
	}
	
	#endregion
	
	
	public override void prepareForUse()
	{
		_target = _ownerTween.target as Transform;
		
		_endValue = _originalEndValue;
		
		// if this is a from tween we need to swap the start and end values
		if( _ownerTween.isFrom )
		{
			_startValue = _endValue;
			
			if( _useLocalRotation )
				_endValue = _target.localRotation.eulerAngles;
			else
				_endValue = _target.rotation.eulerAngles;
		}
		else
		{
			if( _useLocalRotation )
				_startValue = _target.localRotation.eulerAngles;
			else
				_startValue = _target.rotation.eulerAngles;
		}
		
		// handle rotation carefully: when not relative, we always want to go the shortest possible distance to the new angle
		if( _isRelative && !_ownerTween.isFrom )
			_diffValue = _startValue + _endValue;
		else
			_diffValue = new Vector3( Mathf.DeltaAngle( _startValue.x, _endValue.x ), Mathf.DeltaAngle( _startValue.y, _endValue.y ), Mathf.DeltaAngle( _startValue.z, _endValue.z ) );
	}
	
	
	public override void tick( float totalElapsedTime )
	{
		var easedTime = _easeFunction( totalElapsedTime, 0, 1, _ownerTween.duration );
		var vec = GoTweenUtils.unclampedVector3Lerp( _startValue, _diffValue, easedTime );
		
		if( _useLocalRotation )
			_target.localRotation = Quaternion.Euler( vec );
		else
			_target.rotation = Quaternion.Euler( vec );
	}

}
