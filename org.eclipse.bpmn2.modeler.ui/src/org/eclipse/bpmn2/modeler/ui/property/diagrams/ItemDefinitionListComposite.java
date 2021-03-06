package org.eclipse.bpmn2.modeler.ui.property.diagrams;

import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultListComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.ListCompositeColumnProvider;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.TableColumn;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.widgets.Composite;

public class ItemDefinitionListComposite extends DefaultListComposite {

	public ItemDefinitionListComposite(AbstractBpmn2PropertySection section, int style) {
		super(section, style);
	}

	public ItemDefinitionListComposite(AbstractBpmn2PropertySection section) {
		super(section, DEFAULT_STYLE|EDIT_BUTTON);
	}

	public ItemDefinitionListComposite(Composite parent, int style) {
		super(parent, style);
	}

	public ItemDefinitionListComposite(Composite parent) {
		super(parent, DEFAULT_STYLE|EDIT_BUTTON);
	}

	public ListCompositeColumnProvider getColumnProvider(EObject object, EStructuralFeature feature) {
		if (columnProvider==null) {
			columnProvider = new ListCompositeColumnProvider(this,true);
			EClass eclass = PACKAGE.getItemDefinition();
			
			columnProvider.add(object,PACKAGE.getItemDefinition_StructureRef()).setEditable(false);
			columnProvider.add(object,PACKAGE.getItemDefinition_ItemKind());
			columnProvider.add(object,PACKAGE.getItemDefinition_IsCollection());
		}
		return columnProvider;
	}
}
