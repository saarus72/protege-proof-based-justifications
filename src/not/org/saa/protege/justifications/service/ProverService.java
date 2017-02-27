package not.org.saa.protege.justifications.service;

import org.liveontologies.owlapi.proof.OWLProver;
import org.protege.editor.core.plugin.ProtegePluginInstance;
import org.protege.editor.owl.OWLEditorKit;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * 
 * @author Alexander
 * Date: 23/02/2017
 */

public abstract class ProverService implements ProtegePluginInstance {

	public abstract OWLProver getProver(OWLEditorKit ek);
	
	/**
	 * Should return a name for the plugin
	 * 
	 * @return	the name to be displayed in available plugins list
	 */
	public abstract String getName();
	
	@Override
	public String toString() {
		return getName();
	}
}