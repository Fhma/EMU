/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.concordance.clients.xref;

import org.eclipse.epsilon.concordance.index.ConcordanceIndex;
import org.eclipse.epsilon.concordance.model.IConcordanceModel;
import org.eclipse.epsilon.concordance.model.ModelVisitor;

public class DanglingCrossReferenceReconciler {

	private final ConcordanceIndex index;
	
	public DanglingCrossReferenceReconciler(ConcordanceIndex index) {
		this.index = index;
	}
	
	public void reconcileCrossReferences(IConcordanceModel original, IConcordanceModel moved) {
		moved.reconcileCrossReferences(original, moved);
		
		final CrossReferenceTargetReconcilingVisitor reconciler = new CrossReferenceTargetReconcilingVisitor(original, moved);
		
		index.visitAllModelsWithCrossReferencesTo(original, reconciler);
		index.visitAllModelsWithCrossReferencesTo(moved,    reconciler);
	}
	
	static class CrossReferenceTargetReconcilingVisitor extends ModelVisitor {

		private final IConcordanceModel original, moved;
		
		public CrossReferenceTargetReconcilingVisitor(IConcordanceModel original, IConcordanceModel moved) {
			this.original = original;
			this.moved    = moved;
		}

		@Override
		public void visit(IConcordanceModel model) {
			model.reconcileCrossReferences(original, moved);
		}
	}

}
