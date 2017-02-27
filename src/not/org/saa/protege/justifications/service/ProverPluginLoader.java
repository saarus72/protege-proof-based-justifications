package not.org.saa.protege.justifications.service;

import org.eclipse.core.runtime.IExtension;
import org.protege.editor.core.plugin.AbstractPluginLoader;

/**
 * 
 * @author Alexander
 * Date: 23/02/2017
 */

public class ProverPluginLoader extends AbstractPluginLoader<ProverPlugin> {

	/**
	 * Constructs ProverPluginLoader
	 * 
	 * @param KEY	A string to specify the extension point to find plugins for
	 * @param ID	A string to specify the extension point to find plugins for
	 */
	public ProverPluginLoader(String KEY, String ID) {
		super(KEY, ID);
	}

	@Override
	protected ProverPlugin createInstance(IExtension extension) {
		return new ProverPlugin(extension);
	}
}
