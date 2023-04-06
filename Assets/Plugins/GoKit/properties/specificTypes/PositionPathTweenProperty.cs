using UnityEngine;
using System.Collections;


/// <summary>
/// tweens position along a path at constant speed between nodes. isRelative makes the path movement
/// relative to the start position of the object. a "from" tween will reverse the path and make the start
/// position be the last node in the path.
/// </summary>
public class PositionPathTweenProperty : AbstractTweenProperty
{
	protected bool _useLocalPosition;
	public bool useLocalPosition { get { return _useLocalPosition; } }
	
	protected Transform _target;
	protected Vector3 _startValue;
	
	private GoSpline _path;
	private GoLookAtType _lookAtType = GoLookAtType.None;
	private Transform _lookTarget;
	private GoSmoothedQuaternion _smoothedRotation;
	

	public PositionPathTweenProperty( GoSpline path, bool isRelative = false, bool useLocalPosition = false, GoLookAtType lookAtType = GoLookAtType.None, Transform lookTarget = null ) : base( isRelative )
	{
		_path = path;
		_useLocalPosition = useLocalPosition;
		_lookAtType = lookAtType;
		_lookTarget = lookTarget;
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
			return this._useLocalPosition == ((PositionPathTweenProperty)obj)._useLocalPosition;

		// if we get here, we need to see if the other object is a position tween of the same kind
		var otherAsPosition = obj as PositionTweenProperty;
		if( otherAsPosition != null )
			return this._useLocalPosition == otherAsPosition.useLocalPosition;
		
		return false;
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
		{
			_startValue = _path.getLastNode();
		}
		else
		{
			if( _useLocalPosition )
				_startValue = _target.localPosition;
			else
				_startValue = _target.position;
		}
		
		// validate the lookTarget if we are set to look at it
		if( _lookAtType == GoLookAtType.TargetTransform )
		{
			if( _lookTarget == null )
				_lookAtType = GoLookAtType.None;
		}
		
		// prep our smoothed rotation
		_smoothedRotation = _target.rotation;
	}
	

	public override void tick( float totalElapsedTime )
	{
		var easedTime = _easeFunction( totalElapsedTime, 0, 1, _ownerTween.duration );
		var vec = _path.getPointOnPath( easedTime );
		
		// if we are relative, add the vec to our startValue
		if( _isRelative )
			vec += _startValue;
		
		
		// handle look types
		switch( _lookAtType )
		{
			case GoLookAtType.NextPathNode:
			{
				_smoothedRotation.smoothValue = vec.Equals( _target.position ) ? Quaternion.identity : Quaternion.LookRotation( vec - _target.position );
				_target.rotation = _smoothedRotation.smoothValue;
				//var lookAtNode = ( _ownerTween.isReversed || _ownerTween.isLoopoingBackOnPingPong ) ? _path.getPreviousNode() : _path.getNextNode();
				//_target.LookAt( lookAtNode, Vector3.up );
				break;
			}
			case GoLookAtType.TargetTransform:
			{
				_target.LookAt( _lookTarget, Vector3.up );
				break;
			}
		}
		
		
		// assign the position
		if( _useLocalPosition )
			_target.localPosition = vec;
		else
			_target.position = vec;
	}

}
