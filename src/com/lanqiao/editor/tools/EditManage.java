package com.lanqiao.editor.tools;

import javax.swing.event.UndoableEditEvent;
import javax.swing.undo.UndoableEdit;

public class EditManage extends UndoableEditEvent{

	public EditManage(Object source, UndoableEdit edit) {
		super(source, edit);
		
	}

}
