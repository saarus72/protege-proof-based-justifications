package org.liveontologies.protege.explanation.justification.proof.service;

/*-
 * #%L
 * Protege Proof Justification Explanation
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2016 - 2017 Live Ontologies Project
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import org.eclipse.core.runtime.IExtension;
import org.protege.editor.core.plugin.AbstractPluginLoader;

/**
 * Load the available specified {@link ProverService} plugins
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