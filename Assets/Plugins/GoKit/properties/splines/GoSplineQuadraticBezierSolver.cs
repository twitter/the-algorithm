using UnityEngine;
using System.Collections;
using System.Collections.Generic;


/// <summary>
/// nodes should be in the order start, control, end
/// </summary>
public class GoSplineQuadraticBezierSolver : AbstractGoSplineSolver
{
	public GoSplineQuadraticBezierSolver( List<Vector3> nodes )
	{
		_nodes = nodes;
	}

	
	// http://www.gamedev.net/topic/551455-length-of-a-generalized-quadratic-bezier-curve-in-3d/
	protected float quadBezierLength( Vector3 startPoint, Vector3 controlPoint, Vector3 endPoint )
	{
	    // ASSERT: all inputs are distinct points.
		var A = new Vector3[2];
		A[0] = controlPoint - startPoint;
		A[1] = startPoint - 2f * controlPoint + endPoint;
	
	    float length;
	
	    if( A[1] != Vector3.zero )
	    {
	        // Coefficients of f(t) = c*t^2 + b*t + a.
	        float c = 4.0f * Vector3.Dot( A[1], A[1] ); // A[1].Dot(A[1]);  // c > 0 to be in this block of code
	        float b = 8.0f * Vector3.Dot( A[0], A[1] ); // A[0].Dot(A[1]);
	        float a = 4.0f * Vector3.Dot( A[0], A[0] ); // A[0].Dot(A[0]);  // a > 0 by assumption
	        float q = 4.0f * a * c - b * b;  // = 16*|Cross(A0,A1)| >= 0
	
	        float twoCpB = 2.0f * c + b;
	        float sumCBA = c + b + a;
	        float mult0 = 0.25f / c;
	        float mult1 = q / ( 8.0f * Mathf.Pow( c, 1.5f ) );
	        length = mult0 * ( twoCpB * Mathf.Sqrt( sumCBA ) - b * Mathf.Sqrt( a ) ) +
	            mult1 * ( Mathf.Log( 2.0f * Mathf.Sqrt( c * sumCBA ) + twoCpB ) - Mathf.Log( 2.0f * Mathf.Sqrt( c * a ) + b ) );
	    }
	    else
	    {
	        length = 2.0f * A[0].magnitude;
	    }
	
	    return length;
	}

	
	#region AbstractGoSplineSolver
	
	public override void closePath()
	{
		
	}
	
	
	public override Vector3 getPoint( float t )
	{
		float d = 1f - t;
		return d * d * _nodes[0] + 2f * d * t * _nodes[1] + t * t * _nodes[2];
	}

	
	public override void drawGizmos()
	{
		// draw the control points
		var originalColor = Gizmos.color;
		Gizmos.color = Color.red;
		
		Gizmos.DrawLine( _nodes[0], _nodes[1] );
		Gizmos.DrawLine( _nodes[1], _nodes[2] );
		
		Gizmos.color = originalColor;
	}
	
	#endregion

}
