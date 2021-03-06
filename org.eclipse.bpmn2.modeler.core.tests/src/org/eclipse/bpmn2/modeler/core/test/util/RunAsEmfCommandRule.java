package org.eclipse.bpmn2.modeler.core.test.util;

import java.lang.reflect.Method;

import org.eclipse.bpmn2.modeler.core.test.AbstractBpmnEditorTest;
import org.eclipse.bpmn2.modeler.core.test.AbstractTestCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class RunAsEmfCommandRule implements MethodRule {

	@Override
	public Statement apply(final Statement base, final FrameworkMethod method, Object target) {

		DiagramResource resource = method.getAnnotation(DiagramResource.class);
		
		if (resource != null && target instanceof AbstractBpmnEditorTest) {
			final AbstractBpmnEditorTest testCase = (AbstractBpmnEditorTest) target;
			
			String resourceUrl = getResourceUrl(resource, target, method.getMethod());
			
			return new StatementExtension(testCase, resourceUrl, base);
		} else {
			return base;
		}
	}
	
	private String getResourceUrl(DiagramResource resource, Object o, Method m) {
		String url = resource.value();
		if (url == null || url.isEmpty()) {
			url = o.getClass().getName().replaceAll("\\.", "/") + "." + m.getName() + ".bpmn";
		}
		
		return url;
	}

	private class StatementExtension extends Statement {
		
		private final AbstractBpmnEditorTest testCase;
		private final String resourceUrl;
		private final Statement base;
		
		private StatementExtension(AbstractBpmnEditorTest testCase, String resourceUrl, Statement base) {
			this.testCase = testCase;
			this.resourceUrl = resourceUrl;
			this.base = base;
		}

		@Override
		public void evaluate() throws Throwable {
			try {
				TransactionalEditingDomain domain = testCase.createEditingDomain(resourceUrl);
				
				AbstractTestCommand command = new AbstractTestCommand(testCase, resourceUrl) {
					
					@Override
					public void test(IDiagramTypeProvider diagramTypeProvider, Diagram diagram) throws Throwable {
						testCase.setDiagramTypeProvider(diagramTypeProvider);
						testCase.setDiagram(diagram);
						
						base.evaluate();
						
						// save diagram
						// TODO: Uncomment when actually doing stuff
						// diagramResource.save(Collections.emptyMap());
					}
				};
				
				domain.getCommandStack().execute(command);
				
				Throwable e = command.getRecordedException();
				if (e != null) {
					throw e;
				}
			} finally {
				testCase.disposeEditingDomain();
			}
		}
	}
}
