/******************************************************************************* 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * camunda services GmbH - initial API and implementation 
 *
 ******************************************************************************/

package org.eclipse.bpmn2.modeler.core.importer.handlers;

import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.di.BPMNEdge;
import org.eclipse.bpmn2.modeler.core.importer.InvalidContentException;
import org.eclipse.bpmn2.modeler.core.importer.ModelImport;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

/**
 * 
 * @author Nico Rehwaldt
 * @author Daniel Meyer
 */
public class SequenceFlowShapeHandler extends AbstractEdgeHandler<SequenceFlow> {

	public SequenceFlowShapeHandler(ModelImport bpmn2ModelImport) {
		super(bpmn2ModelImport);
	}

	@Override
	protected PictogramElement handleEdge(SequenceFlow bpmnElement, BPMNEdge edge, ContainerShape container) {
		
		FlowNode source = bpmnElement.getSourceRef();
		if (source == null) {
			InvalidContentException exception = new InvalidContentException("Could not resolve source", bpmnElement);
			modelImport.logAndThrow(exception);
		}
		
		FlowNode target = bpmnElement.getTargetRef();
		if (target == null) {
			InvalidContentException exception = new InvalidContentException("Could not resolve target", bpmnElement);
			modelImport.logAndThrow(exception);
		}
		
		PictogramElement sourcePictogram = getPictogramElement(source);
		PictogramElement targetPictogram = getPictogramElement(target);
		
		Connection connection = createConnectionAndSetBendpoints(edge, sourcePictogram, targetPictogram);
		return connection;
	}
}
