package me.APP;

import me.model.*;
import me.view.*;
import me.control.*;

import java.util.LinkedList;

import me.common.*;
import me.common.statemachine.QState;
import me.common.statemachine.QStateMachine;

public final class APP {

	public APP() 
	{
		//statemachine.QState
		QStateMachine automata = QStateMachine.createMachineInstance(QState.QSTATE_IDLE); 
		
		// serialization model
		LinkedList<Pages> model = new LinkedList<>(); 
		model.add(new Pages());
		
		// view
		MainWindow window = new MainWindow("QiniCAD");
		window.bindStateMachine(automata);
		// view bind model & 
		// sink bind notification
		window.bindModel(model);
		
		// control
		QController controller = new QController();
		controller.bindStateMachine(automata);
		// view bind model
		controller.bindModel(model);
		
		// event -> controller
		// listener binding
		window.bindRectangleButton(controller.getRectangleButtonListener());
		window.bindEllipseButton(controller.getEllipseButtonListener());
		window.bindTextButton(controller.getTextButtonListener());
		window.bindLineButton(controller.getLineButtonListener());
		window.bindPolylineButton(controller.getPolylineButtonListener());
		window.bindPolygonButton(controller.getPolygonButtonListener());
		
		window.bindMainPanel(controller.getMainPanelListener());
		window.bindEdgeColorPanel(controller.getEdgeColorChangedListener());
		window.bindFillColorPanel(controller.getFillColorChangedListener());
		window.bindEdgeButton(controller.getEdgeButtonListener());
		window.bindFillButton(controller.getFillButtonListener());
		window.bindStrokeSlider(controller.getStrokeSliderListener());
		window.bindFontSizeSlider(controller.getFontSizeSliderListener());
		window.bindDelete(controller.getDeleteListener());
	}
	
}
