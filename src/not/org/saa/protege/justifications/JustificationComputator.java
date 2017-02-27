package not.org.saa.protege.justifications;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.protege.editor.owl.OWLEditorKit;
import org.semanticweb.owlapi.model.OWLAxiom;

import not.org.saa.protege.explanation.joint.service.JustificationComputation;
import not.org.saa.protege.explanation.joint.service.JustificationComputationListener;

/**
 * 
 * @author Alexander
 * Date: 10/02/2017
 */

public class JustificationComputator extends JustificationComputation {

	private boolean isInterrupted = false;
	private OWLEditorKit kit;
	private List<JustificationComputationListener> listeners;
	private ProverServiceManager manager;

	public JustificationComputator(OWLAxiom entailment, OWLEditorKit kit, ProverServiceManager manager) {
		super(entailment);
		this.kit = kit;
		listeners = new ArrayList<JustificationComputationListener>();
		this.manager = manager;
	}

	@Override
	public void startComputation() {
		
		// should use a monitor later
		
		JustificationLogic logic = new JustificationLogic(kit);

		Set<? extends Set<OWLAxiom>> justifications = logic.getProofBasedJustifications(getEntailment(),
				manager.getSelectedService().getProver(kit));
		for (Set<OWLAxiom> justification : justifications)
			for (JustificationComputationListener listener : listeners)
				listener.foundJustification(justification);
	}

	@Override
	public void interruptComputation() {
		isInterrupted = true;
	}

	@Override
	public boolean isComputationInterrupted() {
		return isInterrupted;
	}

	@Override
	public void addComputationListener(JustificationComputationListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeComputationListener(JustificationComputationListener listener) {
		listeners.remove(listener);
	}
}