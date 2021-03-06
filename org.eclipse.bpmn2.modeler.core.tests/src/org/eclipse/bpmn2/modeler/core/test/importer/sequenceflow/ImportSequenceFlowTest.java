package org.eclipse.bpmn2.modeler.core.test.importer.sequenceflow;

import org.eclipse.bpmn2.modeler.core.importer.ModelImport;
import org.eclipse.bpmn2.modeler.core.test.importer.AbstractImportBpmnModelTest;
import org.eclipse.bpmn2.modeler.core.test.util.DiagramResource;
import org.junit.Test;

public class ImportSequenceFlowTest extends AbstractImportBpmnModelTest {
	
	@Test
	@DiagramResource
	public void testImportConditionalFlow() {
		ModelImport importer = new ModelImport(diagramTypeProvider, resource);
		importer.execute();
	}

	@Test
	@DiagramResource
	public void testImportUnconditionalFlow() {
		ModelImport importer = new ModelImport(diagramTypeProvider, resource);
		importer.execute();
	}
	
	@Test
	@DiagramResource
	public void testImportDefaultFlow() {
		ModelImport importer = new ModelImport(diagramTypeProvider, resource);
		importer.execute();
	}
}
