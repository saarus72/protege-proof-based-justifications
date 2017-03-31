package not.org.saa.protege.justifications;

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
	private ProverServiceManager manager;
	private JustificationLogic logic;

	public JustificationComputator(OWLAxiom entailment, OWLEditorKit kit, ProverServiceManager manager) {
		super(entailment);
		this.kit = kit;
		this.manager = manager;
		this.logic = new JustificationLogic(kit);
	}

	@Override
	public void startComputation() {
		logic.computeProofBasedJustifications(getEntailment(), manager.getSelectedService().getProver(kit));
	}

	@Override
	public void interruptComputation() {
		logic.interruptComputation();
	}

	@Override
	public boolean isComputationInterrupted() {
		return logic.isComputationInterrupted();
	}

	@Override
	public void addComputationListener(JustificationComputationListener listener) {
		logic.addComputationListener(listener);
	}

	@Override
	public void removeComputationListener(JustificationComputationListener listener) {
		logic.removeComputationListener(listener);
	}
}