package not.org.saa.protege.justifications;

import java.util.ArrayList;
import java.util.Collection;

import org.protege.editor.core.Disposable;

import not.org.saa.protege.justifications.service.ProverPlugin;
import not.org.saa.protege.justifications.service.ProverPluginLoader;
import not.org.saa.protege.justifications.service.ProverService;

/**
 * Keeps track of the available specified {@link ProverService} plugins.
 * 
 * @author Pavel Klinov pavel.klinov@uni-ulm.de
 * 
 * @author Yevgeny Kazakov
 * 
 * Date: 23/02/2017
 */

public class ProverServiceManager implements Disposable {

	private final Collection<ProverService> services;
	private ProverService selectedService = null;

	public ProverServiceManager(String KEY, String ID) throws Exception {
		this.services = new ArrayList<ProverService>();
		ProverPluginLoader loader = new ProverPluginLoader(KEY, ID);
		for (ProverPlugin plugin : loader.getPlugins())
			services.add(plugin.newInstance());
	}

	@Override
	public void dispose() throws Exception {
		for (ProverService service : services) {
			service.dispose();
		}
	}

	public Collection<ProverService> getServices() {
		return services;
	}
	
	public ProverService getSelectedService() {
		return selectedService;
	}
	
	public void selectService(ProverService service) {
		selectedService = service;
	}
}