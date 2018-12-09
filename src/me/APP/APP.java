package me.APP;

import me.model.*;
import me.view.*;
import me.control.*;
import me.common.*;
import me.common.statemachine.QState;

public final class APP {

	public APP() 
	{
		//statemachine.QState
		QState automata = QState.QSTATE_IDLE;
		
		// serialization model 
		Pages pages = new Pages();
		
		// view
		MainWindow window = new MainWindow("QiniCAD");
		window.bindStateMachine(automata);
		// view bind model & 
		// sink bind notification
		window.bindModel(pages);
		
		// control
		QController controller = new QController();
		controller.bindStateMachine(automata);
		// view bind model
		controller.bindModel(pages);
		
		// event -> controller
		// listener binding
		window.bindRectangleButton(controller.getRectangleButtonListener());
		window.bindMainPanel(controller.getMainPanelListener());
		window.bindEdgeColorPanel(controller.getEdgeColorChangedListener());
		window.bindFillColorPanel(controller.getFillColorChangedListener());
		window.bindEdgeButton(controller.getEdgeButtonListener());
		window.bindFillButton(controller.getFillButtonListener());
	}
	
}
