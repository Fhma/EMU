/**
 * 
 * @author Faisal Alhwikem
 * @version 1.0
 * @since March-2017
 */

package org.eclipse.epsilon.emu.mutation;

import org.eclipse.epsilon.emc.mutant.IProperty;
import org.eclipse.epsilon.emu.EmuModule;

public interface IMutationGenerator {
	public final Object NOTIMPLEMENTED = "not_implemented";
	public final Object VALID = "valid";
	public final Object INVALID = "invalid";
	public final Object IMPOSSIBLE = "impossible";

	public final String ADD_MUTATION_ACTION = "add";
	public final String DEL_MUTATION_ACTION = "delete";
	public final String REPLACE_MUTATION_ACTION = "replace";

	public Object mutate(Object roleBinding, IProperty property, Object value, EmuModule module,
			String mutation_action);

	public Object checkConditions(IProperty property, Object value, Object valueNew, String mutation_action);
}