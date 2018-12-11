package me.common.event;

import java.awt.Color;

public interface ColorChangedListener {
	// naive event
	// just Color information
	// also Action{color}
	public void onColorChanged(Color c);
}
