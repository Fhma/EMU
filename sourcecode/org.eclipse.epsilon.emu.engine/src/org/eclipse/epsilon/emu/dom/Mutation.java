/*******************************************************************************
 * Copyright (c) 2018 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Faisal Alhwikem
 ******************************************************************************/
package org.eclipse.epsilon.emu.dom;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.emu.EmuModule;
import org.eclipse.epsilon.emu.parse.EmuParser;
import org.eclipse.epsilon.eol.dom.Annotation;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.dom.SimpleAnnotation;
import org.eclipse.epsilon.epl.dom.Role;
import org.eclipse.epsilon.erl.dom.NamedRule;

public class Mutation extends NamedRule {

	protected String propertyName = null;
	protected int mutationActionType = -1;
	protected List<Role> roles = new ArrayList<Role>();
	protected ExecutableBlock<Void> actionBlock = null;
	protected int maxMutations = EmuModule.INFINITY_MUTATION;

	public Mutation() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		// determine number of mutation
		Annotation maxAnnotation = getAnnotation("max");
		if (maxAnnotation != null && maxAnnotation instanceof SimpleAnnotation) {
			SimpleAnnotation simpleLevelAnnotation = (SimpleAnnotation) maxAnnotation;
			maxMutations = Integer.parseInt(simpleLevelAnnotation.getValue());
		}

		List<AST> roleAsts = AstUtil.getChildren(cst, EmuParser.ROLE);
		for (AST roleAst : roleAsts) {
			roles.add((Role) module.createAst(roleAst, this));
		}

		AST action = AstUtil.getChild(cst, EmuParser.ACTION);
		while (action != null) {
			String text = action.getText();
			if (text.equals(EmuModule.ADDITION_KEYWORD))
				mutationActionType = EmuModule.ADDITION_MUTATION_TYPE;
			else if (text.equals(EmuModule.DELETION_KEYWORD))
				mutationActionType = EmuModule.DELETION_MUTATION_TYPE;
			else if (text.equals(EmuModule.REPLACEMENT_KEYWORD))
				mutationActionType = EmuModule.REPLACEMENT_MUTATION_TYPE;
			else {
				mutationActionType = EmuModule.UNKNOWN_MUTATION_TYPE;
				break;
			}
			this.actionBlock = (ExecutableBlock<Void>) module.createAst(action, this);
			break;
		}
	}

	public List<Role> getRoles() {
		return roles;
	}

	public ExecutableBlock<Void> getActionBlock() {
		return actionBlock;
	}

	public int getMaxNumberOfMutations() {
		return maxMutations;
	}

	public int getMutationActionType() {
		return mutationActionType;
	}

	public String getPropertyName() {
		return propertyName;
	}
}
