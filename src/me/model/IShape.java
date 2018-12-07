/**
 * 
 */
package me.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * @author IT
 *
 */
public interface IShape {
	public void render(Graphics g);
	public boolean isInner(Point p);
}
