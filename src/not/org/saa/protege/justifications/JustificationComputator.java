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
 *
 */
public class JustificationComputator extends JustificationComputation {

	private boolean isInterrupted = false;
	private List<JustificationComputationListener> listeners;
	private JustificationLogic logic;

	public JustificationComputator(OWLAxiom entailment, OWLEditorKit kit) {
		super(entailment);
		listeners = new ArrayList<JustificationComputationListener>();
		logic = new JustificationLogic(kit);
	}

	@Override
	public void startComputation() {

		// should use a monitor later

		Set<? extends Set<OWLAxiom>> justifications = logic.getProofBasedJustifications(getEntailment());
		for (Set<OWLAxiom> justification : justifications)
			for (JustificationComputationListener listener : new ArrayList<>(listeners))
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