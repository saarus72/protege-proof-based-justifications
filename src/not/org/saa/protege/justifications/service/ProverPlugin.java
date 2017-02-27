package not.org.saa.protege.justifications.service;

import org.eclipse.core.runtime.IExtension;
import org.protege.editor.core.plugin.AbstractProtegePlugin;

/**
 * 
 * @author Alexander
 * Date: 23/02/2017
 */

public class ProverPlugin extends AbstractProtegePlugin<ProverService> {

	/**
	 * Constructs plugin object
	 * 
	 * @param extension	plugin extension
	 */
	public ProverPlugin(IExtension extension) {
		super(extension);
	}
}