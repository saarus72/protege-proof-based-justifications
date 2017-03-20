package not.org.saa.protege.justifications;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
	private Map<ProverService, String> serviceIds;
	private ProverService selectedService = null;
	public static String lastChoosenServiceId = null;

	public ProverServiceManager(String KEY, String ID) throws Exception {
		this.services = new ArrayList<ProverService>();
		this.serviceIds = new HashMap<ProverService, String>();
		ProverPluginLoader loader = new ProverPluginLoader(KEY, ID);
		for (ProverPlugin plugin : loader.getPlugins())
		{
			ProverService service = plugin.newInstance();
			services.add(service);
			serviceIds.put(service, plugin.getIExtension().getUniqueIdentifier());
		}
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
		lastChoosenServiceId = getIdForService(service);
	}
	
	public String getIdForService(ProverService service)
	{
		return serviceIds.get(service);
	}
}