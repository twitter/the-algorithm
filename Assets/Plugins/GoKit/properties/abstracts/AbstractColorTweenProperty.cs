using UnityEngine;
using System.Collections;


/// <summary>
/// base class for any color tweens (MaterialColor and ColorTween)
/// </summary>
public abstract class AbstractColorTweenProperty : AbstractTweenProperty
{
	protected Material _target;
	
	protected Color _originalEndValue;
	protected Color _startValue;
	protected Color _endValue;
	protected Color _diffValue;
	
	
	public AbstractColorTweenProperty( Color endValue, bool isRelative ) : base( isRelative )
	{
		_originalEndValue = endValue;
	}


	public override bool validateTarget( object target )
	{
		return ( target is Material || target is GameObject || target is Transform || target is Renderer );
	}
	
	
	public override void init( GoTween owner )
	{
		// setup our target before initting
		if( owner.target is Material )
			_target = (Material)owner.target;
		else if( owner.target is GameObject )
			_target = ((GameObject)owner.target).GetComponent<Renderer>().material;
		else if( owner.target is Transform )
			_target = ((Transform)owner.target).GetComponent<Renderer>().material;
		else if( owner.target is Renderer )
			_target = ((Renderer)owner.target).material;
		
		base.init( owner );
	}
	
	
	public override void prepareForUse()
	{
		if( _isRelative && !_ownerTween.isFrom )
			_diffValue = _endValue;
		else
			_diffValue = _endValue - _startValue;
	}

}
